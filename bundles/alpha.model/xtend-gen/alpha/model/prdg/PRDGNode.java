package alpha.model.prdg;

import alpha.model.AlphaNode;
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;

@SuppressWarnings("all")
public class PRDGNode {
  private String name;

  private ISLSet domain;

  private boolean reductionNode;

  private AlphaNode origin;

  public PRDGNode(final String name, final ISLSet domain) {
    this.name = name;
    this.domain = domain;
    this.reductionNode = false;
    this.origin = null;
  }

  public PRDGNode(final String name, final ISLSet domain, final boolean reduction) {
    this.name = name;
    this.domain = domain;
    this.reductionNode = reduction;
    this.origin = null;
  }

  public PRDGNode(final String name, final ISLSet domain, final boolean reduction, final AlphaNode origin) {
    this.name = name;
    this.domain = domain;
    this.reductionNode = reduction;
    this.origin = origin;
  }

  public String getName() {
    return this.name;
  }

  public AlphaNode getOrigin() {
    return this.origin;
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
