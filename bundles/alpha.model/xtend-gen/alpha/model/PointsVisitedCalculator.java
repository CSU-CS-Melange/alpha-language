package alpha.model;

import alpha.model.util.AbstractAlphaCompleteVisitor;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PointsVisitedCalculator extends AbstractAlphaCompleteVisitor {
  private ISLPWQPolynomial complexity = null;

  public static ISLPWQPolynomial complexity(final AlphaVisitable av) {
    final PointsVisitedCalculator calculator = new PointsVisitedCalculator();
    calculator.accept(av);
    return calculator.complexity;
  }

  @Override
  public void outStandardEquation(final StandardEquation eq) {
    AlphaExpression _expr = eq.getExpr();
    if ((_expr instanceof ReduceExpression)) {
      AlphaExpression _expr_1 = eq.getExpr();
      final ReduceExpression reduce = ((ReduceExpression) _expr_1);
      this.addComplexity(reduce.getBody().getContextDomain());
    } else {
      this.addComplexity(eq.getExpr().getContextDomain());
    }
  }

  public ISLPWQPolynomial addComplexity(final ISLSet domain) {
    ISLPWQPolynomial _xblockexpression = null;
    {
      final ISLPWQPolynomial pointsVisited = BarvinokBindings.card(domain);
      ISLPWQPolynomial _xifexpression = null;
      if ((this.complexity == null)) {
        _xifexpression = this.complexity = pointsVisited;
      } else {
        _xifexpression = this.complexity = this.complexity.add(pointsVisited);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  @Override
  public void outUseEquation(final UseEquation eq) {
    try {
      throw new Exception("Use equations are not supported yet.");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Override
  public void outReduceExpression(final ReduceExpression expr) {
    try {
      EObject _eContainer = expr.eContainer();
      boolean _not = (!(_eContainer instanceof StandardEquation));
      if (_not) {
        throw new Exception("Reduce expressions must be the direct child of a standard equation.");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
