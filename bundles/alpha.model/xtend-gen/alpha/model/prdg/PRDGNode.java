package alpha.model.prdg;

import fr.irisa.cairn.jnimap.isl.ISLSet;

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
