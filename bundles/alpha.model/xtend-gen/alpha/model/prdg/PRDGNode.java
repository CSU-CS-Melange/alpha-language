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
    return this.domain;
  }

  public boolean isReductionNode() {
    return this.reductionNode;
  }
}
