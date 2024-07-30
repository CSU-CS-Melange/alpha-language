package alpha.abft.codegen.util;

import fr.irisa.cairn.jnimap.isl.IISLScheduleNodeVisitor;
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
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class ScheduleVisitor implements IISLScheduleNodeVisitor {
  public void defaultVisit(final ISLScheduleNode obj) {
    boolean _hasChildren = obj.hasChildren();
    boolean _not = (!_hasChildren);
    if (_not) {
      return;
    }
    int _nbChildren = obj.getNbChildren();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbChildren, true);
    for (final Integer i : _doubleDotLessThan) {
      obj.child((i).intValue()).accept(this);
    }
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
    this.defaultVisit(obj);
  }

  @Override
  public void visitISLScheduleSetNode(final ISLScheduleSetNode obj) {
    this.defaultVisit(obj);
  }
}
