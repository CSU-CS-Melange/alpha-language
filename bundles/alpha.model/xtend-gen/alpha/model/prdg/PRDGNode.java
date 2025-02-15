package alpha.model.prdg;

import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PRDGNode {
  private String name;

  private ISLSet domain;

  private boolean reductionNode;

  public PRDGNode(final String name, final ISLSet domain) {
    this.name = name;
    this.domain = domain;
    this.reductionNode = false;
  }

  public PRDGNode(final String name, final ISLSet domain, final boolean reduction) {
    this.name = name;
    this.domain = domain;
    this.reductionNode = reduction;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    String _string = this.domain.toString();
    return ((this.name + ", ") + _string);
  }

  public ISLSet getDomain() {
    return this.domain.copy();
  }

  public ISLSpace getSpace() {
    return this.getDomain().getSpace().copy();
  }

  public ISLLocalSpace getLocalSpace() {
    return this.getSpace().copy().toLocalSpace();
  }

  public boolean isReductionNode() {
    return this.reductionNode;
  }

  public Variable getOriginVariable(final AlphaSystem sys) {
    if (this.reductionNode) {
      final String varName = this.name.substring(0, this.name.lastIndexOf("_reduce"));
      return sys.getVariable(varName);
    } else {
      return sys.getVariable(this.name);
    }
  }

  public StandardEquation getOriginEquation(final AlphaSystem sys) {
    final Function1<SystemBody, StandardEquation> _function = (SystemBody body) -> {
      return body.getStandardEquation(this.getOriginVariable(sys));
    };
    final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation se) -> {
      return Boolean.valueOf((se != null));
    };
    return IterableExtensions.<StandardEquation>findFirst(ListExtensions.<SystemBody, StandardEquation>map(sys.getSystemBodies(), _function), _function_1);
  }

  @Override
  public boolean equals(final Object other) {
    boolean _xifexpression = false;
    if ((other instanceof PRDGNode)) {
      _xifexpression = this.name.equals(((PRDGNode)other).getName());
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
