package alpha.model.util;

import alpha.model.matrix.MatrixOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLHermiteResult;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Factorizes a set of multi-dimensional affine maps (i.e., <code>ISLMultiAff</code>).
 * The main way this class is expected to be used is by calling the <code>factorizeExpressions</code>
 * static method with a set of affine expressions.
 * This returns a key->value pair, where the key is the "common factor" that can be pulled out,
 * and the value is a dictionary mapping each of the original expressions
 * to the "remaining term" that is left after pulling out the common factor.
 * Each expression can be rewritten as the composition of the common factor and the remaining term
 * as follows: <code>remainingTerm.pullback(commonFactor)</code>
 * 
 * @see <a href="https://impact-workshop.org/papers/paper3.pdf">Reuse Analysis via Affine Factorization</a>
 */
@SuppressWarnings("all")
public class AffineFactorizer {
  /**
   * Factors a set of affine expressions which have the same domain.
   * 
   * @param  expressions A list or array of affine multi-dimensional expressions to factor.
   * @return A key->value pair where the key is the "common factor" and the value is a map
   *         from each of the input expressions to the "remaining term" that is left after
   *         pulling out the common factor. That is, the following would return true,
   *         where <code>expr</code> is one of the input expressions and <code>result</code>
   *         is the return value of this function:
   *         <code>expr.isPlainEqual(result.value.get(expr).pullback(result.key.copy))</code>
   */
  public static Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> factorizeExpressions(final ISLMultiAff... expressions) {
    final HashMap<ISLMultiAff, ISLMultiAff> named = AffineFactorizer.nameExpressionOutputs(expressions);
    final Function1<ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff it) -> {
      return it.copy();
    };
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(AffineFunctionOperations.mergeExpressions(IterableExtensions.<ISLMultiAff>toList(IterableExtensions.<ISLMultiAff, ISLMultiAff>map(named.values(), _function))));
    final ISLMultiAff hExpression = decomposed.getKey();
    final ISLMultiAff qExpression = decomposed.getValue();
    final Function1<ISLMultiAff, ISLMultiAff> _function_1 = (ISLMultiAff expr) -> {
      return AffineFactorizer.getDecompositionProjection(named.get(expr), qExpression);
    };
    final HashMap<ISLMultiAff, ISLMultiAff> projectionMap = CommonExtensions.<ISLMultiAff, ISLMultiAff>toHashMap(IterableExtensions.<ISLMultiAff, ISLMultiAff>toInvertedMap(((Iterable<? extends ISLMultiAff>)Conversions.doWrapArray(expressions)), _function_1));
    return Pair.<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>>of(hExpression, projectionMap);
  }

  /**
   * Creates a copy of each input expression, but renames all the outputs so they can be tracked.
   * Returns a map from the original expressions to the named ones.
   */
  public static HashMap<ISLMultiAff, ISLMultiAff> nameExpressionOutputs(final ISLMultiAff... expressions) {
    final Iterator<String> names = AffineFactorizer.getNameGenerator("orig_out");
    final Function1<ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff it) -> {
      return AffineFactorizer.nameSingleExpressionOutputs(it, names);
    };
    return CommonExtensions.<ISLMultiAff, ISLMultiAff>toHashMap(IterableExtensions.<ISLMultiAff, ISLMultiAff>toInvertedMap(((Iterable<? extends ISLMultiAff>)Conversions.doWrapArray(expressions)), _function));
  }

  /**
   * Generates names containing the given prefix and an incrementing value, separated by an underscore.
   */
  private static Iterator<String> getNameGenerator(final String prefix) {
    final Function1<Integer, String> _function = (Integer it) -> {
      String _string = it.toString();
      return ((prefix + "_") + _string);
    };
    return IterableExtensions.<Integer, String>map(new IntegerRange(0, Integer.MAX_VALUE), _function).iterator();
  }

  /**
   * Gives each output of the provided expression a unique name, overwriting any existing names.
   */
  private static ISLMultiAff nameSingleExpressionOutputs(final ISLMultiAff expression, final Iterator<String> names) {
    final int outCount = expression.getNbOutputs();
    final Function2<ISLMultiAff, Integer, ISLMultiAff> _function = (ISLMultiAff expr, Integer idx) -> {
      return AffineFunctionOperations.nameOutput(expr, (idx).intValue(), names.next());
    };
    final ISLMultiAff merged = IterableExtensions.<Integer, ISLMultiAff>fold(new ExclusiveRange(0, outCount, true), expression.copy(), _function);
    return merged;
  }

  /**
   * Converts an expression into a matrix of its parameters, input indexes, and constants.
   * The matrix is column-oriented (i.e., each affine expression is one column).
   * Throws an error if the expression uses division, as that may not work correctly.
   */
  public static ISLMatrix expressionToMatrix(final ISLMultiAff expression) {
    int _nbDivs = expression.getNbDivs();
    boolean _greaterThan = (_nbDivs > 0);
    if (_greaterThan) {
      throw new IllegalArgumentException("Affine expressions with division are not currently supported.");
    }
    long[][] _transpose = MatrixOperations.transpose(AffineFunctionOperations.toMatrix(expression).toArray());
    int _nbParams = expression.getNbParams();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbParams, true);
    final ISLMatrix matrix = ISLMatrix.buildFromLongMatrix(MatrixOperations.removeColumns(_transpose, ((int[])Conversions.unwrapArray(_doubleDotLessThan, int.class))));
    return matrix;
  }

  /**
   * Performs the column-oriented Hermite decomposition of the given
   * matrix, returning H and Q as the two outputs (as a key->value pair,
   * in that order respectively). Any columns of 0's have been dropped
   * from the right of H, along with the same number of rows from the
   * bottom of Q.
   */
  public static Pair<ISLMatrix, ISLMatrix> hermiteMatrixDecomposition(final ISLMatrix matrix) {
    final ISLHermiteResult hermiteResult = matrix.leftHermite();
    return AffineFactorizer.reduceHermiteDimensionality(hermiteResult.getH(), hermiteResult.getQ());
  }

  /**
   * Reduces the dimensionality of the matrices constructed by the
   * Hermite Normal Form calculation.
   * This is done by dropping the columns of zeros from the right of H
   * along with the same number of rows from the bottom of Q.
   * This assumes that all columns of zeros are on the right of H,
   * which matches ISL's implementation.
   */
  public static Pair<ISLMatrix, ISLMatrix> reduceHermiteDimensionality(final ISLMatrix h, final ISLMatrix q) {
    final int emptyCols = AffineFactorizer.countEmptyCols(h);
    if ((emptyCols == 0)) {
      return Pair.<ISLMatrix, ISLMatrix>of(h, q);
    }
    int _nbCols = h.getNbCols();
    final int firstToDrop = (_nbCols - emptyCols);
    final ISLMatrix hUpdated = h.dropCols(firstToDrop, emptyCols);
    final ISLMatrix qUpdated = q.dropRows(firstToDrop, emptyCols);
    return Pair.<ISLMatrix, ISLMatrix>of(hUpdated, qUpdated);
  }

  /**
   * Returns the number of empty columns in the given matrix.
   * Assumes that the empty rows appear only on the top of the matrix.
   */
  private static int countEmptyCols(final ISLMatrix matrix) {
    final int cols = matrix.getNbCols();
    final Function1<Integer, Boolean> _function = (Integer col) -> {
      return Boolean.valueOf(AffineFactorizer.isColEmpty(matrix, (col).intValue()));
    };
    final int emptyColCount = IterableExtensions.size(IterableExtensions.<Integer>takeWhile(new ExclusiveRange(cols, 0, false), _function));
    return emptyColCount;
  }

  /**
   * Checks if a column of the given matrix is empty or not.
   */
  private static boolean isColEmpty(final ISLMatrix matrix, final int col) {
    final int rows = matrix.getNbRows();
    final Function1<Integer, Boolean> _function = (Integer row) -> {
      long _element = matrix.getElement((row).intValue(), col);
      return Boolean.valueOf((_element != 0));
    };
    boolean _exists = IterableExtensions.<Integer>exists(new ExclusiveRange(0, rows, true), _function);
    return (!_exists);
  }

  /**
   * Converts a matrix into an expression. Each column is for one of the output dimensions.
   */
  public static ISLMultiAff toExpression(final ISLMatrix matrix, final ISLSpace space) {
    int _nbOutputs = space.getNbOutputs();
    boolean _equals = (_nbOutputs == 0);
    if (_equals) {
      return AffineFactorizer.mapToEmptySet(space.getParamNames(), space.getInputNames());
    }
    final List<String> paramNames = space.getParamNames();
    final List<String> inputNames = space.getInputNames();
    int _nbRows = matrix.getNbRows();
    int _nbParams = space.getNbParams();
    int _nbInputs = space.getNbInputs();
    int _plus = (_nbParams + _nbInputs);
    final boolean linearOnly = (_nbRows == _plus);
    ISLMultiAff expression = MatrixOperations.toMatrix(MatrixOperations.transpose(matrix.toLongMatrix()), paramNames, inputNames, linearOnly, true).toMultiAff();
    List<String> _elvis = null;
    List<String> _outputNames = space.getOutputNames();
    if (_outputNames != null) {
      _elvis = _outputNames;
    } else {
      _elvis = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList());
    }
    final List<String> outputNames = _elvis;
    int _length = ((Object[])Conversions.unwrapArray(outputNames, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      expression = AffineFunctionOperations.nameOutput(expression, (i).intValue(), outputNames.get((i).intValue()));
    }
    return expression;
  }

  private static ISLMultiAff mapToEmptySet(final Collection<String> paramNames, final Collection<String> inputNames) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("[");
    String _join = IterableExtensions.join(paramNames, ",");
    _builder.append(_join);
    _builder.append("] -> { [");
    String _join_1 = IterableExtensions.join(inputNames, ",");
    _builder.append(_join_1);
    _builder.append("] -> [] }");
    return ISLUtil.toISLMultiAff(_builder.toString());
  }

  /**
   * Decomposes an expression using the Hermite decomposition of the matrix
   * which represents the given expression. The decomposition is returned as
   * a key->value pair, where the key has the same domain as the original
   * expression, and the value has the same range as the original expression.
   * The range of the key is the same as the domain of the value.
   */
  public static Pair<ISLMultiAff, ISLMultiAff> hermiteExpressionDecomposition(final ISLMultiAff expression) {
    int _nbOutputs = expression.getNbOutputs();
    boolean _equals = (_nbOutputs == 0);
    if (_equals) {
      ISLMultiAff _emptyExpr = AffineFactorizer.emptyExpr(expression.getContext());
      return Pair.<ISLMultiAff, ISLMultiAff>of(expression, _emptyExpr);
    }
    final ISLMatrix fullMatrix = AffineFactorizer.expressionToMatrix(expression);
    ISLMatrix _copy = fullMatrix.copy();
    int _nbParams = expression.getNbParams();
    int _nbInputs = expression.getNbInputs();
    int _plus = (_nbInputs + 1);
    final ISLMatrix paramsRows = _copy.dropRows(_nbParams, _plus);
    final ISLMatrix indexRows = fullMatrix.copy().dropRows(0, expression.getNbParams()).dropRows(expression.getNbInputs(), 1);
    int _nbParams_1 = expression.getNbParams();
    int _nbInputs_1 = expression.getNbInputs();
    int _plus_1 = (_nbParams_1 + _nbInputs_1);
    final ISLMatrix constantsRow = fullMatrix.dropRows(0, _plus_1);
    final Pair<ISLMatrix, ISLMatrix> decomposed = AffineFactorizer.hermiteMatrixDecomposition(indexRows);
    final int hCols = decomposed.getKey().getNbCols();
    final ISLMatrix hMatrix = AffineFactorizer.zeroMatrix(expression.getNbParams(), hCols).concat(decomposed.getKey()).concat(AffineFactorizer.zeroMatrix(1, hCols));
    final ISLMatrix qMatrix = paramsRows.concat(decomposed.getValue()).concat(constantsRow);
    final ISLSpace originalSpace = expression.getSpace();
    final Pair<ISLSpace, ISLSpace> spaces = AffineFactorizer.createDecompositionSpaces(originalSpace, hMatrix.getNbCols());
    final ISLSpace hSpace = spaces.getKey();
    final ISLSpace qSpace = spaces.getValue();
    final ISLMultiAff hExpression = AffineFactorizer.toExpression(hMatrix, hSpace);
    final ISLMultiAff qExpression = AffineFactorizer.toExpression(qMatrix, qSpace);
    return Pair.<ISLMultiAff, ISLMultiAff>of(hExpression, qExpression);
  }

  /**
   * Returns an empty expression. I.e., { [] -> [] }
   */
  private static ISLMultiAff emptyExpr(final ISLContext context) {
    return ISLMultiAff.buildFromString(context, "{ [] -> [] }");
  }

  /**
   * Returns a matrix of all zeros.
   */
  private static ISLMatrix zeroMatrix(final int rows, final int cols) {
    ISLMatrix matrix = ISLMatrix.build(ISLContext.getInstance(), rows, cols);
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, rows, true);
    for (final Integer r : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, cols, true);
      for (final Integer c : _doubleDotLessThan_1) {
        matrix = matrix.setElement((r).intValue(), (c).intValue(), 0);
      }
    }
    return matrix;
  }

  /**
   * Creates the new spaces for the decomposition of the original space,
   * returned as a key->value pair. The key has the same domain as the
   * original space, and the value has the same range as the original space.
   * The unspecified range/domain are the same, with a number of dimensions
   * equal to the desired amount.
   */
  private static Pair<ISLSpace, ISLSpace> createDecompositionSpaces(final ISLSpace originalSpace, final int innerDimensionCount) {
    final int namesToMake = innerDimensionCount;
    final List<String> innerNames = IteratorExtensions.<String>toList(IteratorExtensions.<String>take(AffineFactorizer.getNameGenerator("mid"), namesToMake));
    final ISLSpace firstSpace = originalSpace.copy().dropDims(ISLDimType.isl_dim_out, 0, originalSpace.getNbOutputs()).<ISLSpace>addOutputs(innerNames);
    final ISLSpace secondSpace = originalSpace.copy().dropDims(ISLDimType.isl_dim_in, 0, originalSpace.getNbInputs()).<ISLSpace>addInputs(innerNames);
    return Pair.<ISLSpace, ISLSpace>of(firstSpace, secondSpace);
  }

  /**
   * Returns the projection of the decomposed expression (Q)
   * which can be used to recreate the given original expression.
   */
  private static ISLMultiAff getDecompositionProjection(final ISLMultiAff original, final ISLMultiAff q) {
    int _nbOutputs = q.getNbOutputs();
    boolean _equals = (_nbOutputs == 0);
    if (_equals) {
      return AffineFactorizer.emptyExpr(original.getContext());
    }
    int _nbOutputs_1 = original.getNbOutputs();
    boolean _equals_1 = (_nbOutputs_1 == 0);
    if (_equals_1) {
      final ISLLocalSpace qRange = q.getSpace().domain().toLocalSpace();
      final ISLMultiAff mapToZero = ISLAff.buildZero(qRange).toMultiAff();
      final int outs = mapToZero.getNbOutputs();
      return mapToZero.dropDims(ISLDimType.isl_dim_out, 0, outs);
    }
    final ISLSpace domain = q.getSpace().range();
    final Function1<String, Integer> _function = (String name) -> {
      return Integer.valueOf(domain.findDimByName(ISLDimType.isl_dim_out, name));
    };
    final List<Integer> wantedIndexes = ListExtensions.<String, Integer>map(original.getSpace().getOutputNames(), _function);
    final ISLLocalSpace localDomain = domain.copy().toLocalSpace();
    final Function1<Integer, ISLAff> _function_1 = (Integer i) -> {
      return ISLAff.buildVarOnDomain(localDomain.copy(), ISLDimType.isl_dim_out, (i).intValue());
    };
    final Function1<ISLAff, ISLMultiAff> _function_2 = (ISLAff expr) -> {
      return expr.toMultiAff();
    };
    final ISLMultiAff projection = AffineFunctionOperations.mergeExpressions(ListExtensions.<ISLAff, ISLMultiAff>map(ListExtensions.<Integer, ISLAff>map(wantedIndexes, _function_1), _function_2)).pullback(q.copy());
    return projection;
  }
}
