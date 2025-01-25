package alpha.model.prdg;

import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;

@SuppressWarnings("all")
public class PRDGEdge {
  private PRDGNode source;

  private PRDGNode dest;

  private ISLMap map;

  public PRDGEdge(final PRDGNode source, final PRDGNode dest, final ISLSet domain, final ISLMap map) {
    this.source = source;
    this.dest = dest;
    this.map = map.copy().intersectDomain(domain.copy());
  }

  public PRDGEdge(final PRDGNode source, final PRDGNode dest, final ISLMap map) {
    this.source = source;
    this.dest = dest;
    this.map = map;
  }

  public ISLMap getMap() {
    return this.map.copy();
  }

  public ISLSet getDomain() {
    return this.map.getDomain().copy();
  }

  public ISLSet getRange() {
    return this.map.getRange().copy();
  }

  public PRDGNode getSource() {
    return this.source;
  }

  public PRDGNode getDest() {
    return this.dest;
  }

  public boolean isReductionEdge() {
    return this.dest.isReductionNode();
  }

  @Override
  public String toString() {
    String _name = this.source.getName();
    String _plus = (_name + " -> ");
    String _name_1 = this.dest.getName();
    String _plus_1 = (_plus + _name_1);
    String _plus_2 = (_plus_1 + ": ");
    String _string = this.map.toString();
    return (_plus_2 + _string);
  }

  @Override
  public boolean equals(final Object other) {
    boolean _xifexpression = false;
    if ((other instanceof PRDGEdge)) {
      return ((this.source.equals(((PRDGEdge)other).getSource()) && this.dest.equals(((PRDGEdge)other).getDest())) && this.map.isPlainEqual(((PRDGEdge)other).map));
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
