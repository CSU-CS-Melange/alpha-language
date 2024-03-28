package alpha.codegen.util;

import alpha.model.matrix.Matrix;
import alpha.model.matrix.MatrixRow;
import alpha.model.matrix.factory.MatrixUserFactory;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import java.util.Collection;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ISLPrintingUtils {
  /**
   * checks whether a constraint involves at least one
   *  index dimension
   *  «»
   */
  public static boolean involvesAtLeastOneDim(final ISLConstraint c, final ISLDimType dim_type) {
    Boolean _xblockexpression = null;
    {
      final int dim = c.getNbDims(dim_type);
      final Function1<Integer, Boolean> _function = (Integer it) -> {
        return Boolean.valueOf(c.involvesDims(dim_type, (it).intValue(), 1));
      };
      final Function2<Boolean, Boolean, Boolean> _function_1 = (Boolean v0, Boolean v1) -> {
        return Boolean.valueOf(((v0).booleanValue() || (v1).booleanValue()));
      };
      _xblockexpression = IterableExtensions.<Boolean>reduce(IterableExtensions.<Integer, Boolean>map(new ExclusiveRange(0, dim, true), _function), _function_1);
    }
    return (_xblockexpression).booleanValue();
  }

  public static String toCString(final ISLConstraint c) {
    StringConcatenation _builder = new StringConcatenation();
    String _replace = c.getAff().toString(ISL_FORMAT.C).replace(" ", "");
    _builder.append(_replace);
    String _xifexpression = null;
    boolean _isEquality = c.isEquality();
    if (_isEquality) {
      _xifexpression = "=";
    } else {
      _xifexpression = ">=";
    }
    _builder.append(_xifexpression);
    _builder.append("0");
    return _builder.toString();
  }

  public static String indexConstraintsToConditionals(final ISLSet set) {
    final Function1<ISLBasicSet, String> _function = (ISLBasicSet it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint c) -> {
        return Boolean.valueOf(ISLPrintingUtils.involvesAtLeastOneDim(c, ISLDimType.isl_dim_out));
      };
      final Function1<ISLConstraint, String> _function_2 = (ISLConstraint it_1) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(");
        String _cString = ISLPrintingUtils.toCString(it_1);
        _builder_1.append(_cString);
        _builder_1.append(")");
        return _builder_1.toString();
      };
      String _join = IterableExtensions.join(IterableExtensions.<ISLConstraint, String>map(IterableExtensions.<ISLConstraint>filter(it.getConstraints(), _function_1), _function_2), " && ");
      _builder.append(_join);
      _builder.append(")");
      return _builder.toString();
    };
    return IterableExtensions.join(ListExtensions.<ISLBasicSet, String>map(set.getBasicSets(), _function), " || ");
  }

  public static String paramConstraintsToConditionals(final ISLSet set) {
    final Function1<ISLBasicSet, String> _function = (ISLBasicSet it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      final Function1<ISLConstraint, Boolean> _function_1 = (ISLConstraint c) -> {
        return Boolean.valueOf(ISLPrintingUtils.involvesAtLeastOneDim(c, ISLDimType.isl_dim_param));
      };
      final Function1<ISLConstraint, String> _function_2 = (ISLConstraint it_1) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(");
        String _cString = ISLPrintingUtils.toCString(it_1);
        _builder_1.append(_cString);
        _builder_1.append(")");
        return _builder_1.toString();
      };
      String _join = IterableExtensions.join(IterableExtensions.<ISLConstraint, String>map(IterableExtensions.<ISLConstraint>filter(it.getConstraints(), _function_1), _function_2), " && ");
      _builder.append(_join);
      _builder.append(")");
      return _builder.toString();
    };
    return IterableExtensions.join(ListExtensions.<ISLBasicSet, String>map(set.getBasicSets(), _function), " || ");
  }

  public static String toCString(final ISLMultiAff maff) {
    String _xblockexpression = null;
    {
      final int dim = maff.dim(ISLDimType.isl_dim_out);
      if ((dim == 0)) {
        return "";
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      final Function1<ISLAff, String> _function = (ISLAff it) -> {
        return it.toString(ISL_FORMAT.C).replace(" ", "");
      };
      String _join = IterableExtensions.join(ListExtensions.<ISLAff, String>map(maff.getAffs(), _function), ",");
      _builder.append(_join);
      _builder.append(")");
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }

  /**
   * input:  [A, B] -> { [i, j, k] -> [(i)] }
   * output: [A, B] -> { [i, j, k] -> [(A), (B), (i)] }
   */
  public static ISLMultiAff moveParamsToArgs(final ISLMultiAff maff) {
    ISLMultiAff _xblockexpression = null;
    {
      final Matrix matrix = AffineFunctionOperations.toMatrix(maff);
      final int numParams = matrix.getNbParams();
      final MatrixRow[] paramRows = ISLPrintingUtils.getRowsInRange(matrix, 0, numParams);
      matrix.getRows().addAll(numParams, ((Collection<? extends MatrixRow>)Conversions.doWrapArray(paramRows)));
      _xblockexpression = matrix.toMultiAff();
    }
    return _xblockexpression;
  }

  public static MatrixRow[] getRowsInRange(final Matrix m, final int a, final int b) {
    final Function1<MatrixRow, Boolean> _function = (MatrixRow it) -> {
      return Boolean.valueOf(((a <= m.getRows().indexOf(it)) && (m.getRows().indexOf(it) < b)));
    };
    final Function1<MatrixRow, MatrixRow> _function_1 = (MatrixRow it) -> {
      return MatrixUserFactory.createMatrixRow(((long[])Conversions.unwrapArray(it.getValues(), long.class)));
    };
    return ((MatrixRow[])Conversions.unwrapArray(IterableExtensions.<MatrixRow, MatrixRow>map(IterableExtensions.<MatrixRow>filter(m.getRows(), _function), _function_1), MatrixRow.class));
  }
}
