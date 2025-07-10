package alpha.codegen.isl;

import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CustomExpr;
import alpha.codegen.Expression;
import alpha.codegen.Factory;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Converts an isl set, basic set, or constraint into a conditional expression.
 */
@SuppressWarnings("all")
public class ConditionalConverter {
  /**
   * Generates a conditional expression only for the parameters of a set.
   */
  public static Expression convertParameters(final ISLSet set) {
    return ConditionalConverter.convert(set.params());
  }

  /**
   * Generates a conditional expression to determine if a point is within this set.
   */
  public static Expression convert(final ISLSet set) {
    final Function1<ISLBasicSet, Expression> _function = new Function1<ISLBasicSet, Expression>() {
      public Expression apply(final ISLBasicSet it) {
        return ConditionalConverter.convert(it);
      }
    };
    return Factory.binaryExprTree(BinaryOperator.OR, ((Expression[])Conversions.unwrapArray(ListExtensions.<ISLBasicSet, Expression>map(set.getBasicSets(), _function), Expression.class)));
  }

  /**
   * Generates a conditional expression to determine if a point is within this basic set.
   */
  public static Expression convert(final ISLBasicSet basicSet) {
    final Function1<ISLConstraint, BinaryExpr> _function = new Function1<ISLConstraint, BinaryExpr>() {
      public BinaryExpr apply(final ISLConstraint it) {
        return ConditionalConverter.convert(it);
      }
    };
    return Factory.binaryExprTree(BinaryOperator.AND, ((Expression[])Conversions.unwrapArray(ListExtensions.<ISLConstraint, BinaryExpr>map(basicSet.getConstraints(), _function), Expression.class)));
  }

  /**
   * Generates a conditional expression to determine if a point satisfies this constraint.
   */
  public static BinaryExpr convert(final ISLConstraint constraint) {
    BinaryOperator _xifexpression = null;
    boolean _isEquality = constraint.isEquality();
    if (_isEquality) {
      _xifexpression = BinaryOperator.EQ;
    } else {
      _xifexpression = BinaryOperator.GEQ;
    }
    final BinaryOperator comparison = _xifexpression;
    final CustomExpr leftSide = Factory.customExpr(constraint.getAff().toString(ISL_FORMAT.C));
    return Factory.binaryExpr(comparison, leftSide, Factory.customExpr("0"));
  }
}
