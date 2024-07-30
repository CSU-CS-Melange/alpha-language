package alpha.abft.codegen.util;

import alpha.model.AlphaExpression;
import alpha.model.AlphaNode;
import alpha.model.AlphaSystem;
import alpha.model.AutoRestrictExpression;
import alpha.model.CaseExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class StatementVisitor extends AbstractAlphaCompleteVisitor {
  private final HashMap<Variable, List<AlphaExpression>> exprs = CollectionLiterals.<Variable, List<AlphaExpression>>newHashMap();

  public static HashMap<Variable, List<AlphaExpression>> apply(final AlphaSystem system) {
    HashMap<Variable, List<AlphaExpression>> _xblockexpression = null;
    {
      final StatementVisitor visitor = new StatementVisitor();
      system.accept(visitor);
      _xblockexpression = visitor.exprs;
    }
    return _xblockexpression;
  }

  @Override
  public void outAlphaExpression(final AlphaExpression ae) {
    EObject _eContainer = ae.eContainer();
    final AlphaNode parent = ((AlphaNode) _eContainer);
    this.rules(parent, ae);
  }

  protected List<AlphaExpression> _rules(final StandardEquation se, final ReduceExpression e) {
    return null;
  }

  protected List<AlphaExpression> _rules(final StandardEquation se, final CaseExpression e) {
    return null;
  }

  protected List<AlphaExpression> _rules(final StandardEquation se, final RestrictExpression re) {
    return this.addExpr(re.getExpr());
  }

  protected List<AlphaExpression> _rules(final StandardEquation se, final AlphaExpression e) {
    return this.addExpr(e);
  }

  protected List<AlphaExpression> _rules(final CaseExpression se, final AutoRestrictExpression re) {
    return this.addExpr(re.getExpr());
  }

  protected List<AlphaExpression> _rules(final CaseExpression se, final RestrictExpression re) {
    return this.addExpr(re.getExpr());
  }

  protected List<AlphaExpression> _rules(final CaseExpression ce, final AlphaExpression e) {
    return this.addExpr(e);
  }

  protected List<AlphaExpression> _rules(final ReduceExpression se, final RestrictExpression re) {
    return this.addExpr(re.getExpr());
  }

  protected List<AlphaExpression> _rules(final ReduceExpression re, final AlphaExpression e) {
    return this.addExpr(e);
  }

  protected List<AlphaExpression> _rules(final AlphaNode an, final AlphaExpression ae) {
    return null;
  }

  public List<AlphaExpression> addExpr(final AlphaExpression ae) {
    List<AlphaExpression> _xblockexpression = null;
    {
      Equation _containerEquation = AlphaUtil.getContainerEquation(ae);
      final Variable variable = ((StandardEquation) _containerEquation).getVariable();
      List<AlphaExpression> _elvis = null;
      List<AlphaExpression> _get = this.exprs.get(variable);
      if (_get != null) {
        _elvis = _get;
      } else {
        LinkedList<AlphaExpression> _newLinkedList = CollectionLiterals.<AlphaExpression>newLinkedList();
        _elvis = _newLinkedList;
      }
      final List<AlphaExpression> childExprs = _elvis;
      childExprs.add(ae);
      _xblockexpression = this.exprs.put(variable, childExprs);
    }
    return _xblockexpression;
  }

  public List<AlphaExpression> rules(final AlphaNode se, final AlphaExpression re) {
    if (se instanceof ReduceExpression
         && re instanceof RestrictExpression) {
      return _rules((ReduceExpression)se, (RestrictExpression)re);
    } else if (se instanceof ReduceExpression
         && re != null) {
      return _rules((ReduceExpression)se, re);
    } else if (se instanceof StandardEquation
         && re instanceof ReduceExpression) {
      return _rules((StandardEquation)se, (ReduceExpression)re);
    } else if (se instanceof CaseExpression
         && re instanceof AutoRestrictExpression) {
      return _rules((CaseExpression)se, (AutoRestrictExpression)re);
    } else if (se instanceof CaseExpression
         && re instanceof RestrictExpression) {
      return _rules((CaseExpression)se, (RestrictExpression)re);
    } else if (se instanceof StandardEquation
         && re instanceof CaseExpression) {
      return _rules((StandardEquation)se, (CaseExpression)re);
    } else if (se instanceof StandardEquation
         && re instanceof RestrictExpression) {
      return _rules((StandardEquation)se, (RestrictExpression)re);
    } else if (se instanceof CaseExpression
         && re != null) {
      return _rules((CaseExpression)se, re);
    } else if (se instanceof StandardEquation
         && re != null) {
      return _rules((StandardEquation)se, re);
    } else if (se != null
         && re != null) {
      return _rules(se, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(se, re).toString());
    }
  }
}
