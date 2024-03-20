package alpha.targetmapping.scratch;

import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PRDGNode {
  private Variable variable;

  private ISLSet domain;

  public PRDGNode(final Variable v) {
    this.variable = v;
    final Function1<ISLBasicSet, ISLSet> _function = (ISLBasicSet it) -> {
      return it.setTupleName(v.getName()).toSet();
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet d0, ISLSet d1) -> {
      return d0.copy().union(d1);
    };
    this.domain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(v.getDomain().getBasicSets(), _function), _function_1);
  }

  public Variable getVariable() {
    return this.variable;
  }

  public ISLSet getDomain() {
    return this.domain;
  }

  @Override
  public String toString() {
    StringConcatenation _builder = new StringConcatenation();
    String _name = this.variable.getName();
    _builder.append(_name);
    _builder.append(": ");
    _builder.append(this.domain);
    return _builder.toString();
  }
}
