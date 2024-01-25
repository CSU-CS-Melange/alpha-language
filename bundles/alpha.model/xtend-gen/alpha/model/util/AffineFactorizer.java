package alpha.model.util;

import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLHermiteResult;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Factorizes a set of multi-dimensional affine maps (i.e., <code>ISLMultiAff</code>).
 * 
 * @see <a href="https://impact-workshop.org/papers/paper3.pdf">Reuse Analysis via Affine Factorization</a>
 */
@SuppressWarnings("all")
public class AffineFactorizer {
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
    final int outCount = expression.dim(ISLDimType.isl_dim_out);
    final Function2<ISLMultiAff, Integer, ISLMultiAff> _function = (ISLMultiAff expr, Integer outIndex) -> {
      return expr.setDimName(ISLDimType.isl_dim_out, outIndex, names.next());
    };
    final Function2<ISLMultiAff, Integer, ISLMultiAff> setName = _function;
    return IterableExtensions.<Integer, ISLMultiAff>fold(new ExclusiveRange(0, outCount, true), expression.copy(), setName);
  }

  /**
   * Gives each output of all the provided expressions a unique name, overwriting any existing names.
   */
  public static ArrayList<ISLMultiAff> nameExpressionOutputs(final ISLMultiAff... expressions) {
    final Iterator<String> names = AffineFactorizer.getNameGenerator("orig_out");
    final Function1<ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff it) -> {
      return AffineFactorizer.nameSingleExpressionOutputs(it, names);
    };
    final List<ISLMultiAff> retVal = ListExtensions.<ISLMultiAff, ISLMultiAff>map(((List<ISLMultiAff>)Conversions.doWrapArray(expressions)), _function);
    return new ArrayList<ISLMultiAff>(retVal);
  }

  /**
   * Merges a set of affine expressions into a single expression via their flat product.
   */
  public static ISLMultiAff mergeExpressions(final ISLMultiAff... expressions) {
    final Function2<ISLMultiAff, ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff left, ISLMultiAff right) -> {
      return left.flatRangeProduct(right);
    };
    return IterableExtensions.<ISLMultiAff>reduce(((Iterable<? extends ISLMultiAff>)Conversions.doWrapArray(expressions)), _function);
  }

  /**
   * Copies the coefficients from one expression in a multi-expression into a matrix.
   */
  private static ISLMatrix outputToMatrixCol(final ISLAff expression, final ISLMatrix matrix, final int col) {
    ISLMatrix updatedMatrix = matrix;
    final int paramCount = expression.dim(ISLDimType.isl_dim_param);
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, paramCount, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final ISLVal coefficient = expression.getCoefficientVal(ISLDimType.isl_dim_param, (i).intValue());
        updatedMatrix = updatedMatrix.setElement((i).intValue(), col, coefficient);
      }
    }
    final int inCount = expression.dim(ISLDimType.isl_dim_in);
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, inCount, true);
    for (final Integer i_1 : _doubleDotLessThan_1) {
      {
        final ISLVal coefficient = expression.getCoefficientVal(ISLDimType.isl_dim_in, (i_1).intValue());
        updatedMatrix = updatedMatrix.setElement((paramCount + (i_1).intValue()), col, coefficient);
      }
    }
    final ISLVal constant = expression.getConstantVal();
    updatedMatrix = updatedMatrix.setElement((paramCount + inCount), col, constant);
    return updatedMatrix;
  }

  /**
   * Converts an expression into a matrix of its parameters, input indexes, and constants.
   * The matrix is column-oriented (i.e., each affine expression is one column).
   * Throws an error if the expression uses division, as that may not work correctly.
   */
  public static ISLMatrix expressionToMatrix(final ISLMultiAff expression) {
    int _dim = expression.dim(ISLDimType.isl_dim_div);
    boolean _greaterThan = (_dim > 0);
    if (_greaterThan) {
      throw new IllegalArgumentException("Affine expressions with division are not currently supported.");
    }
    final int cols = expression.dim(ISLDimType.isl_dim_out);
    int _dim_1 = expression.dim(ISLDimType.isl_dim_param);
    int _dim_2 = expression.dim(ISLDimType.isl_dim_in);
    int _plus = (_dim_1 + _dim_2);
    final int rows = (_plus + 1);
    final List<ISLAff> affs = expression.getAffs();
    final Function2<ISLMatrix, Integer, ISLMatrix> _function = (ISLMatrix mat, Integer col) -> {
      return AffineFactorizer.outputToMatrixCol(affs.get(col), mat, col);
    };
    final Function2<ISLMatrix, Integer, ISLMatrix> updateCol = _function;
    final ISLContext ctx = expression.getContext();
    final ISLMatrix matrix = IterableExtensions.<Integer, ISLMatrix>fold(new ExclusiveRange(0, cols, true), ISLMatrix.build(ctx, rows, cols), updateCol);
    return matrix;
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
   * Returns the number of empty columns in the given matrix.
   * Assumes that the empty rows appear only on the right of the matrix.
   */
  private static int countEmptyCols(final ISLMatrix matrix) {
    final int cols = matrix.getNbCols();
    final Function1<Integer, Boolean> _function = (Integer col) -> {
      return Boolean.valueOf(AffineFactorizer.isColEmpty(matrix, (col).intValue()));
    };
    final int emptyRowCount = IterableExtensions.size(IterableExtensions.<Integer>takeWhile(new ExclusiveRange(cols, 0, false), _function));
    return emptyRowCount;
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
   * Performs the column-oriented Hermite decomposition of the given
   * matrix, returning H and Q as the two outputs (as a key->value pair,
   * in that order respectively). Any columns of 0's have been dropped
   * from the right of H, along with the same number of rows from the
   * bottom of Q.
   */
  public static Pair<ISLMatrix, ISLMatrix> hermiteDecomposition(final ISLMatrix matrix) {
    final ISLHermiteResult hermiteResult = matrix.leftHermite();
    return AffineFactorizer.reduceHermiteDimensionality(hermiteResult.getH(), hermiteResult.getQ());
  }
}
