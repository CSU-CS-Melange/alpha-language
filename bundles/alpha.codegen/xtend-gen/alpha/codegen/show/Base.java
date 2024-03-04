package alpha.codegen.show;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.GlobalMacro;
import alpha.codegen.GlobalVariable;
import alpha.codegen.Include;
import alpha.codegen.Macro;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.Node;
import alpha.codegen.Program;
import alpha.codegen.StatementMacro;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;
import alpha.codegen.util.CodegenSwitch;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class Base extends CodegenSwitch<CharSequence> {
  public static <T extends Visitable> String print(final T v) {
    String _xblockexpression = null;
    {
      final Base show = new Base();
      _xblockexpression = show.doSwitch(v).toString();
    }
    return _xblockexpression;
  }

  public CharSequence caseNode(final Node cn) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  @Override
  public CharSequence caseVisitable(final Visitable v) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  @Override
  public CharSequence caseVisitor(final Visitor v) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  public CharSequence caseInclude(final Include i) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include <");
    String _name = i.getName();
    _builder.append(_name);
    _builder.append(".h>");
    return _builder;
  }

  public CharSequence caseMacro(final Macro m) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define ");
    String _left = m.getLeft();
    _builder.append(_left);
    _builder.append(" ");
    String _right = m.getRight();
    _builder.append(_right);
    return _builder;
  }

  protected CharSequence _localDefinition(final BaseVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _dataType = v.dataType();
    _builder.append(_dataType);
    _builder.append(" ");
    CharSequence _localName = this.localName(v);
    _builder.append(_localName);
    return _builder;
  }

  protected CharSequence _localDefinition(final ArrayVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _dataType = v.dataType();
    _builder.append(_dataType);
    _builder.append(" ");
    CharSequence _localName = this.localName(v);
    _builder.append(_localName);
    return _builder;
  }

  protected CharSequence _localName(final BaseVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = v.getName();
    _builder.append(_name);
    return _builder;
  }

  protected CharSequence _localName(final ArrayVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("_local_");
    String _name = v.getName();
    _builder.append(_name);
    return _builder;
  }

  public CharSequence caseBaseVariable(final BaseVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _dataType = v.dataType();
    _builder.append(_dataType);
    _builder.append(" ");
    CharSequence _localName = this.localName(v);
    _builder.append(_localName);
    return _builder;
  }

  public CharSequence caseMemoryAllocation(final MemoryAllocation ma) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    {
      int _numDims = ma.getVariable().getNumDims();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _numDims, true);
      for(final Integer i : _doubleDotLessThan) {
        _builder.append("  ");
        _builder.append("int D");
        _builder.append(i, "  ");
        _builder.append(" = ...");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// TODO - mallocs for local memory reflecting memory maps");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence caseFunctionBody(final FunctionBody fb) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<StatementMacro, CharSequence> _function = (StatementMacro it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<StatementMacro, CharSequence>map(fb.getStatementMacros(), _function), "\n");
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    String _cString = fb.getISLASTNode().toCString();
    _builder.append(_cString);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  public CharSequence signature(final Function f) {
    StringConcatenation _builder = new StringConcatenation();
    DataType _returnType = f.getReturnType();
    _builder.append(_returnType);
    _builder.append(" ");
    String _name = f.getName();
    _builder.append(_name);
    _builder.append("(");
    final Function1<BaseVariable, CharSequence> _function = (BaseVariable it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<BaseVariable, CharSequence>map(f.args(), _function), ", ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public CharSequence declaration(final BaseVariable v) {
    StringConcatenation _builder = new StringConcatenation();
    String _dataType = v.dataType();
    _builder.append(_dataType);
    _builder.append(" ");
    String _name = v.getName();
    _builder.append(_name);
    return _builder;
  }

  public CharSequence caseProgram(final Program p) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Include, CharSequence> _function = (Include it) -> {
      return this.doSwitch(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<Include, CharSequence>map(p.getIncludes(), _function), "\n");
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// common macros");
    _builder.newLine();
    final Function1<GlobalMacro, CharSequence> _function_1 = (GlobalMacro it) -> {
      return this.doSwitch(it);
    };
    String _join_1 = IterableExtensions.join(ListExtensions.<GlobalMacro, CharSequence>map(p.getCommonMacros(), _function_1), "\n");
    _builder.append(_join_1);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// global variable declarations");
    _builder.newLine();
    final Function1<GlobalVariable, String> _function_2 = (GlobalVariable it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("static ");
      CharSequence _declaration = this.declaration(it);
      _builder_1.append(_declaration);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join_2 = IterableExtensions.join(ListExtensions.<GlobalVariable, String>map(p.getGlobalVariables(), _function_2), "\n");
    _builder.append(_join_2);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// memory macros");
    _builder.newLine();
    final Function1<GlobalVariable, CharSequence> _function_3 = (GlobalVariable it) -> {
      return this.doSwitch(it.getMemoryMacro());
    };
    String _join_3 = IterableExtensions.join(ListExtensions.<GlobalVariable, CharSequence>map(p.getGlobalVariables(), _function_3), "\n");
    _builder.append(_join_3);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// local function declarations");
    _builder.newLine();
    final Function1<Function, Boolean> _function_4 = (Function f) -> {
      return Boolean.valueOf((f instanceof EvalFunction));
    };
    final Function1<Function, String> _function_5 = (Function it) -> {
      CharSequence _signature = this.signature(it);
      return (_signature + ";");
    };
    String _join_4 = IterableExtensions.join(IterableExtensions.<Function, String>map(IterableExtensions.<Function>filter(p.getFunctions(), _function_4), _function_5), "\n");
    _builder.append(_join_4);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    final Function1<Function, CharSequence> _function_6 = (Function it) -> {
      return this.doSwitch(it);
    };
    String _join_5 = IterableExtensions.join(ListExtensions.<Function, CharSequence>map(p.getFunctions(), _function_6), "\n");
    _builder.append(_join_5);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  @Override
  public CharSequence defaultCase(final EObject o) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }

  public CharSequence localDefinition(final BaseVariable v) {
    if (v instanceof ArrayVariable) {
      return _localDefinition((ArrayVariable)v);
    } else if (v != null) {
      return _localDefinition(v);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(v).toString());
    }
  }

  public CharSequence localName(final BaseVariable v) {
    if (v instanceof ArrayVariable) {
      return _localName((ArrayVariable)v);
    } else if (v != null) {
      return _localName(v);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(v).toString());
    }
  }
}
