package alpha.abft.codegen.util;

import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLScheduleBandNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleContextNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleDomainNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleExpansionNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleExtensionNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleFilterNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleGuardNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleLeafNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleMarkNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleSequenceNode;
import fr.irisa.cairn.jnimap.isl.ISLScheduleSetNode;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ScheduleSequenceMerger extends ScheduleVisitor {
  private Set<ISLScheduleFilterNode> mergeTargets;

  private ISLScheduleFilterNode newFilterNode;

  private ISLScheduleNode parent;

  private ISLSchedule newSchedule;

  public ScheduleSequenceMerger(final Set<ISLScheduleFilterNode> nodes) {
    this.assertSameParent(nodes);
    this.mergeTargets = nodes;
    this.parent = (((ISLScheduleFilterNode[])Conversions.unwrapArray(nodes, ISLScheduleFilterNode.class))[0]).ancestor(1);
    this.newSchedule = null;
  }

  public void assertSameParent(final Set<ISLScheduleFilterNode> nodes) {
    try {
      final Function1<ISLScheduleFilterNode, ISLScheduleNode> _function = (ISLScheduleFilterNode it) -> {
        ISLScheduleNode _ancestor = it.copy().ancestor(1);
        return ((ISLScheduleNode) _ancestor);
      };
      final Iterable<ISLScheduleNode> parents = IterableExtensions.<ISLScheduleFilterNode, ISLScheduleNode>map(nodes, _function);
      final Function1<ISLScheduleNode, Boolean> _function_1 = (ISLScheduleNode it) -> {
        return Boolean.valueOf(it.isSamePositionInTheSameSchedule(((ISLScheduleNode[])Conversions.unwrapArray(parents, ISLScheduleNode.class))[0]));
      };
      final Function2<Boolean, Boolean, Boolean> _function_2 = (Boolean v0, Boolean v1) -> {
        return Boolean.valueOf((Objects.equal(v0, v1) && ((v0).booleanValue() == true)));
      };
      final Boolean ok = IterableExtensions.<Boolean>reduce(IterableExtensions.<ISLScheduleNode, Boolean>map(parents, _function_1), _function_2);
      if ((!(ok).booleanValue())) {
        throw new Exception("filter nodes must have the same parent in order to be merged");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static ISLScheduleNode merge(final Set<ISLScheduleFilterNode> nodes) {
    ISLScheduleFilterNode _xblockexpression = null;
    {
      final ScheduleSequenceMerger visitor = new ScheduleSequenceMerger(nodes);
      visitor.parent.accept(visitor);
      _xblockexpression = visitor.newFilterNode;
    }
    return _xblockexpression;
  }

  @Override
  public void visitISLScheduleBandNode(final ISLScheduleBandNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleContextNode(final ISLScheduleContextNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleDomainNode(final ISLScheduleDomainNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleExpansionNode(final ISLScheduleExpansionNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleExtensionNode(final ISLScheduleExtensionNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleFilterNode(final ISLScheduleFilterNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleGuardNode(final ISLScheduleGuardNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleLeafNode(final ISLScheduleLeafNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleMarkNode(final ISLScheduleMarkNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleNode(final ISLScheduleNode obj) {
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleSequenceNode(final ISLScheduleSequenceNode obj) {
    boolean _isSamePositionInTheSameSchedule = obj.isSamePositionInTheSameSchedule(this.parent);
    boolean _not = (!_isSamePositionInTheSameSchedule);
    if (_not) {
      super.visitISLScheduleSequenceNode(obj);
      return;
    }
    InputOutput.println();
  }

  @Override
  public void visitISLScheduleSetNode(final ISLScheduleSetNode obj) {
    this.defaultVisit(obj);
  }
}
