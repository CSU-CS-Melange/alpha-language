package alpha.model.transformation.reduction;

import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.issue.AlphaIssue;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class PushDependenceIntoReduction {
  public static void apply(final DependenceExpression de) {
    PushDependenceIntoReduction.transform(de, de.getExpr());
  }

  protected static List<AlphaIssue> _transform(final DependenceExpression de, final ReduceExpression re) {
    List<AlphaIssue> _xblockexpression = null;
    {
      EObject _eContainer = de.eContainer();
      boolean _not = (!(_eContainer instanceof StandardEquation));
      if (_not) {
        return null;
      }
      final AlphaSystem system = AlphaUtil.getContainerSystem(de);
      Equation _containerEquation = AlphaUtil.getContainerEquation(de);
      final StandardEquation eq = ((StandardEquation) _containerEquation);
      final ISLMultiAff maff = de.getFunction();
      final List<String> indexNames = de.getContextDomain().getIndexNames();
      final List<String> paramNames = de.getContextDomain().getParamNames();
      final Function2<ISLSet, Pair<ISLAff, String>, ISLSet> _function = (ISLSet ret, Pair<ISLAff, String> aff_name) -> {
        ISLSet _xblockexpression_1 = null;
        {
          final ISLAff aff = aff_name.getKey();
          final String name = aff_name.getValue();
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("[");
          String _join = IterableExtensions.join(paramNames, ",");
          _builder.append(_join);
          _builder.append("]->{");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("[");
          String _join_1 = IterableExtensions.join(indexNames, ",");
          _builder.append(_join_1, "\t");
          _builder.append("] : ");
          _builder.append(name, "\t");
          _builder.append(" = ");
          String _string = aff.toString(ISL_FORMAT.C);
          _builder.append(_string, "\t");
          _builder.newLineIfNotEmpty();
          _builder.append("}");
          _builder.newLine();
          final ISLSet set = ISLUtil.toISLSet(_builder.toString());
          _xblockexpression_1 = ret.intersect(set);
        }
        return _xblockexpression_1;
      };
      final ISLSet D = IterableExtensions.<Pair<ISLAff, String>, ISLSet>fold(CommonExtensions.<ISLAff, String>zipWith(maff.getAffs(), indexNames), 
        ISLSet.buildUniverse(de.getContextDomain().getSpace().copy()), _function);
      _xblockexpression = AlphaInternalStateConstructor.recomputeContextDomain(system);
    }
    return _xblockexpression;
  }

  protected static List<AlphaIssue> _transform(final DependenceExpression de, final AlphaExpression ae) {
    return null;
  }

  public static List<AlphaIssue> transform(final DependenceExpression de, final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _transform(de, (ReduceExpression)re);
    } else if (re != null) {
      return _transform(de, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(de, re).toString());
    }
  }
}
