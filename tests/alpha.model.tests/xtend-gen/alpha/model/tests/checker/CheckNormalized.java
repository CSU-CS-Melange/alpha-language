package alpha.model.tests.checker;

import alpha.model.AlphaVisitable;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.VariableExpression;
import alpha.model.transformation.Normalize;
import alpha.model.util.ModelSwitch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Quick check to test if the program is in normal form or not.
 * See {@link Normalize} for definition of its expected behavior.
 * 
 * The test do not consider use equations for now.
 */
@SuppressWarnings("all")
public class CheckNormalized extends ModelSwitch<Boolean> {
  private final boolean DEEP;

  protected CheckNormalized(final boolean deep) {
    this.DEEP = deep;
  }

  public static boolean apply(final AlphaVisitable av) {
    return CheckNormalized.apply(av, false);
  }

  public static boolean apply(final AlphaVisitable av, final boolean deep) {
    final CheckNormalized sw = new CheckNormalized(false);
    return (sw.doSwitch(av)).booleanValue();
  }

  @Override
  public Boolean defaultCase(final EObject eObject) {
    boolean _xblockexpression = false;
    {
      int _size = eObject.eContents().size();
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        final Function1<EObject, Boolean> _function = (EObject it) -> {
          return this.doSwitch(it);
        };
        final Function2<Boolean, Boolean, Boolean> _function_1 = (Boolean b1, Boolean b2) -> {
          return Boolean.valueOf(((b1).booleanValue() && (b2).booleanValue()));
        };
        IterableExtensions.<Boolean>reduce(ListExtensions.<EObject, Boolean>map(eObject.eContents(), _function), _function_1);
      }
      _xblockexpression = true;
    }
    return Boolean.valueOf(_xblockexpression);
  }

  /**
   * override
   */
  public Boolean caseCaseExpression(final CaseExpression ce) {
    boolean _xblockexpression = false;
    {
      if ((((ce.isNamed()).booleanValue() || (ce.eContainer() instanceof StandardEquation)) || (ce.eContainer() instanceof ReduceExpression))) {
        return this.defaultCase(ce);
      }
      _xblockexpression = false;
    }
    return Boolean.valueOf(_xblockexpression);
  }

  /**
   * override
   */
  public Boolean caseRestrictExpression(final RestrictExpression re) {
    boolean _xblockexpression = false;
    {
      if ((((re.eContainer() instanceof StandardEquation) || (re.eContainer() instanceof ReduceExpression)) || (re.eContainer() instanceof CaseExpression))) {
        return this.defaultCase(re);
      }
      _xblockexpression = false;
    }
    return Boolean.valueOf(_xblockexpression);
  }

  /**
   * override
   */
  public Boolean caseDependenceExpression(final DependenceExpression de) {
    return Boolean.valueOf(((de.getExpr() instanceof VariableExpression) || (de.getExpr() instanceof ConstantExpression)));
  }
}
