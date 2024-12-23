package alpha.model.prdg;

import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;

@SuppressWarnings("all")
public class PRDGEdge {
  private PRDGNode source;

  private PRDGNode dest;

  private ISLSet domain;

  private ISLMultiAff function;

  public PRDGEdge(final PRDGNode source, final PRDGNode dest, final ISLSet domain, final ISLMultiAff func) {
    this.source = source;
    this.dest = dest;
    this.domain = domain;
    this.function = func;
  }

  public ISLMultiAff getFunction() {
    return this.function.copy();
  }

  public ISLSet getDomain() {
    return this.domain.copy();
  }

  public PRDGNode getSource() {
    return this.source;
  }

  public PRDGNode getDest() {
    return this.dest;
  }

  public boolean isReductionEdge() {
    return (this.dest.isReductionNode() && this.source.isReductionNode());
  }

  @Override
  public String toString() {
    String _name = this.source.getName();
    String _plus = (_name + " -> ");
    String _name_1 = this.dest.getName();
    String _plus_1 = (_plus + _name_1);
    String _plus_2 = (_plus_1 + ": ");
    String _string = this.function.toString();
    String _plus_3 = (_plus_2 + _string);
    String _plus_4 = (_plus_3 + "@");
    String _string_1 = this.domain.toString();
    return (_plus_4 + _string_1);
  }

  @Override
  public boolean equals(final Object other) {
    boolean _xifexpression = false;
    if ((other instanceof PRDGEdge)) {
      return (((this.source.equals(((PRDGEdge)other).getSource()) && this.dest.equals(((PRDGEdge)other).getDest())) && this.domain.isPlainEqual(((PRDGEdge)other).getDomain())) && this.function.isPlainEqual(((PRDGEdge)other).function));
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }

  @Override
  public int hashCode() {
    String _name = this.source.getName();
    String _name_1 = this.dest.getName();
    return (_name + _name_1).hashCode();
  }
}
