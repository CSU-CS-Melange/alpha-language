package alpha.targetmapping.scratch;

import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PRDGEdge {
  private PRDGNode consumer;

  private PRDGNode producer;

  private ISLMap map;

  public PRDGEdge(final PRDGNode c, final PRDGNode p, final ISLMultiAff ma, final ISLSet d) {
    this.consumer = c;
    this.producer = p;
    final Function1<ISLBasicSet, ISLSet> _function = (ISLBasicSet it) -> {
      return it.setTupleName(c.getVariable().getName()).toSet();
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet d0, ISLSet d1) -> {
      return d0.copy().union(d1);
    };
    final ISLSet domain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(d.getBasicSets(), _function), _function_1);
    this.map = ma.toBasicMap().setTupleName(ISLDimType.isl_dim_in, c.getVariable().getName()).setTupleName(ISLDimType.isl_dim_out, p.getVariable().getName()).toMap().intersectDomain(domain);
  }

  public PRDGNode getConsumer() {
    return this.consumer;
  }

  public PRDGNode getProducer() {
    return this.producer;
  }

  public ISLMap getMap() {
    return this.map;
  }

  @Override
  public String toString() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.map);
    return _builder.toString();
  }
}
