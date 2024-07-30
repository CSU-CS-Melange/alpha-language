package alpha.abft.codegen.util;

import fr.irisa.cairn.jnimap.isl.IISLASTNodeVisitor;
import fr.irisa.cairn.jnimap.isl.ISLASTBlockNode;
import fr.irisa.cairn.jnimap.isl.ISLASTExpression;
import fr.irisa.cairn.jnimap.isl.ISLASTForNode;
import fr.irisa.cairn.jnimap.isl.ISLASTIfNode;
import fr.irisa.cairn.jnimap.isl.ISLASTMarkNode;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLASTNodeList;
import fr.irisa.cairn.jnimap.isl.ISLASTUnscannedNode;
import fr.irisa.cairn.jnimap.isl.ISLASTUserNode;
import java.util.List;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ISLASTNodeVisitor implements IISLASTNodeVisitor {
  private String code;

  private int indentLevel;

  public String indent() {
    final Function1<Integer, String> _function = (Integer it) -> {
      return "  ";
    };
    return IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, this.indentLevel, true), _function), "");
  }

  public static List<ISLASTNode> toList(final ISLASTNodeList nodeList) {
    int _nbNodes = nodeList.getNbNodes();
    final Function1<Integer, ISLASTNode> _function = (Integer it) -> {
      return nodeList.get((it).intValue()).copy();
    };
    return IterableExtensions.<ISLASTNode>toList(IterableExtensions.<Integer, ISLASTNode>map(new ExclusiveRange(0, _nbNodes, true), _function));
  }

  public ISLASTNodeVisitor() {
    this.indentLevel = 0;
  }

  public ISLASTNodeVisitor genC(final ISLASTNode node) {
    ISLASTNodeVisitor _xblockexpression = null;
    {
      node.accept(this);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }

  public String toCode() {
    return this.code;
  }

  public Object inISLASTBlockNode(final ISLASTBlockNode obj) {
    return null;
  }

  @Override
  public void visitISLASTBlockNode(final ISLASTBlockNode obj) {
    this.inISLASTBlockNode(obj);
    List<ISLASTNode> _list = ISLASTNodeVisitor.toList(obj.getChildren());
    for (final ISLASTNode child : _list) {
      child.accept(this);
    }
    this.outISLASTBlockNode(obj);
  }

  public Object outISLASTBlockNode(final ISLASTBlockNode obj) {
    return null;
  }

  public String inISLASTForNode(final ISLASTForNode obj) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.code);
    _builder.newLineIfNotEmpty();
    String _indent = this.indent();
    _builder.append(_indent);
    _builder.append("for (int ");
    ISLASTExpression _iterator = obj.getIterator();
    _builder.append(_iterator);
    _builder.append(" = ");
    ISLASTExpression _init = obj.getInit();
    _builder.append(_init);
    _builder.append("; ");
    ISLASTExpression _cond = obj.getCond();
    _builder.append(_cond);
    _builder.append("; ");
    ISLASTExpression _iterator_1 = obj.getIterator();
    _builder.append(_iterator_1);
    _builder.append(" += ");
    ISLASTExpression _inc = obj.getInc();
    _builder.append(_inc);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    return this.code = _builder.toString();
  }

  @Override
  public void visitISLASTForNode(final ISLASTForNode obj) {
    this.inISLASTForNode(obj);
    int _indentLevel = this.indentLevel;
    this.indentLevel = (_indentLevel + 1);
    obj.getBody().accept(this);
    int _indentLevel_1 = this.indentLevel;
    this.indentLevel = (_indentLevel_1 - 1);
    this.outISLASTForNode(obj);
  }

  public String outISLASTForNode(final ISLASTForNode obj) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.code);
    _builder.newLineIfNotEmpty();
    String _indent = this.indent();
    _builder.append(_indent);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    return this.code = _builder.toString();
  }

  public String inISLASTIfNode(final ISLASTIfNode obj) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.code);
    _builder.newLineIfNotEmpty();
    String _indent = this.indent();
    _builder.append(_indent);
    _builder.append("if (");
    ISLASTExpression _cond = obj.getCond();
    _builder.append(_cond);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    return this.code = _builder.toString();
  }

  @Override
  public void visitISLASTIfNode(final ISLASTIfNode obj) {
    this.inISLASTIfNode(obj);
    int _indentLevel = this.indentLevel;
    this.indentLevel = (_indentLevel + 1);
    obj.getThen().accept(this);
    int _indentLevel_1 = this.indentLevel;
    this.indentLevel = (_indentLevel_1 - 1);
    int _hasElse = obj.hasElse();
    boolean _equals = (_hasElse == 1);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.code);
      _builder.newLineIfNotEmpty();
      String _indent = this.indent();
      _builder.append(_indent);
      _builder.append("} else {");
      _builder.newLineIfNotEmpty();
      this.code = _builder.toString();
      int _indentLevel_2 = this.indentLevel;
      this.indentLevel = (_indentLevel_2 + 1);
      obj.getElse().accept(this);
      int _indentLevel_3 = this.indentLevel;
      this.indentLevel = (_indentLevel_3 - 1);
    }
    this.outISLASTIfNode(obj);
  }

  public String outISLASTIfNode(final ISLASTIfNode obj) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.code);
    _builder.newLineIfNotEmpty();
    String _indent = this.indent();
    _builder.append(_indent);
    _builder.append("}");
    _builder.newLineIfNotEmpty();
    return this.code = _builder.toString();
  }

  public Object inISLASTMarkNode(final ISLASTMarkNode obj) {
    return null;
  }

  @Override
  public void visitISLASTMarkNode(final ISLASTMarkNode obj) {
    this.inISLASTMarkNode(obj);
    this.outISLASTMarkNode(obj);
  }

  public Object outISLASTMarkNode(final ISLASTMarkNode obj) {
    return null;
  }

  public Object inISLASTNode(final ISLASTNode obj) {
    return null;
  }

  @Override
  public void visitISLASTNode(final ISLASTNode obj) {
    this.inISLASTNode(obj);
    this.outISLASTNode(obj);
  }

  public Object outISLASTNode(final ISLASTNode obj) {
    return null;
  }

  public Object inISLASTUnscannedNode(final ISLASTUnscannedNode obj) {
    return null;
  }

  @Override
  public void visitISLASTUnscannedNode(final ISLASTUnscannedNode obj) {
    this.inISLASTUnscannedNode(obj);
    this.outISLASTUnscannedNode(obj);
  }

  public Object outISLASTUnscannedNode(final ISLASTUnscannedNode obj) {
    return null;
  }

  public Object inISLASTUserNode(final ISLASTUserNode obj) {
    return null;
  }

  @Override
  public void visitISLASTUserNode(final ISLASTUserNode obj) {
    this.inISLASTUserNode(obj);
    this.outISLASTUserNode(obj);
  }

  public String outISLASTUserNode(final ISLASTUserNode obj) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(this.code);
    _builder.newLineIfNotEmpty();
    String _indent = this.indent();
    _builder.append(_indent);
    ISLASTExpression _expression = obj.getExpression();
    _builder.append(_expression);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return this.code = _builder.toString();
  }
}
