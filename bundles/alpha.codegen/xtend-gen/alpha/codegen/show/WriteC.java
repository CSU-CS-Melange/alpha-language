package alpha.codegen.show;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.DataType;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Visitable;
import alpha.codegen.util.AlphaEquationPrinter;
import alpha.codegen.util.ISLPrintingUtils;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class WriteC extends Base {
  public static boolean DEBUG = true;

  public static <T extends Visitable> String print(final T v) {
    String _xblockexpression = null;
    {
      final WriteC show = new WriteC();
      _xblockexpression = show.doSwitch(v).toString();
    }
    return _xblockexpression;
  }

  public CharSequence caseFunction(final Function f) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _signature = this.signature(f);
    _builder.append(_signature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("// parameter checking");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("if (");
    String _paramConstraintsToConditionals = ISLPrintingUtils.paramConstraintsToConditionals(f.system().getParameterDomain());
    _builder.append(_paramConstraintsToConditionals, "  ");
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("printf(\"The value of parameters are not valid.\\n\");");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("exit(-1);");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// copy to global");
    _builder.newLine();
    _builder.append("  ");
    final Function1<ArrayVariable, String> _function = (ArrayVariable it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _writeName = it.writeName();
      _builder_1.append(_writeName);
      _builder_1.append(" = ");
      CharSequence _localName = this.localName(it);
      _builder_1.append(_localName);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.join(ListExtensions.<ArrayVariable, String>map(f.getArrayArgs(), _function), "\n");
    _builder.append(_join, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// local memory allocation and macros");
    _builder.newLine();
    _builder.append("  ");
    final Function1<MemoryMacro, CharSequence> _function_1 = (MemoryMacro it) -> {
      return this.doSwitch(it);
    };
    String _join_1 = IterableExtensions.join(ListExtensions.<MemoryMacro, CharSequence>map(f.getMemoryMacros(), _function_1), "\n");
    _builder.append(_join_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("// statements");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _doSwitch = this.doSwitch(f.getBody());
    _builder.append(_doSwitch, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence caseEvalFunction(final EvalFunction ef) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _signature = this.signature(ef);
    _builder.append(_signature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("// check flag");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("if (");
    String _identityAccess = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess, "  ");
    _builder.append(" == \'N\') {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _identityAccess_1 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_1, "    ");
    _builder.append(" = \'I\';");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("// Body for ");
    String _name = ef.getVariable().getName();
    _builder.append(_name, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _printStandardEquation = AlphaEquationPrinter.printStandardEquation(ef.getEquation(), ef.getProgram());
    _builder.append(_printStandardEquation, "    ");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    String _identityAccess_2 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_2, "    ");
    _builder.append(" = \'F\';");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("} else if (");
    String _identityAccess_3 = ef.getFlagVariable().identityAccess();
    _builder.append(_identityAccess_3, "  ");
    _builder.append(" == \'I\') {");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("printf(\"There is a self dependence on ");
    String _name_1 = ef.getVariable().getName();
    _builder.append(_name_1, "    ");
    _builder.append(" at (");
    final Function1<String, String> _function = (String it) -> {
      return "%d";
    };
    String _join = IterableExtensions.join(ListExtensions.<String, String>map(ef.getVariable().indices(), _function), ",");
    _builder.append(_join, "    ");
    _builder.append(")\\n\", ");
    String _join_1 = IterableExtensions.join(ef.getVariable().indices(), ",");
    _builder.append(_join_1, "    ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("return ");
    String _identityAccess_4 = ef.getVariable().identityAccess();
    _builder.append(_identityAccess_4, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  @Override
  public CharSequence signature(final Function f) {
    StringConcatenation _builder = new StringConcatenation();
    DataType _returnType = f.getReturnType();
    _builder.append(_returnType);
    _builder.append(" ");
    String _name = f.getName();
    _builder.append(_name);
    _builder.append("(");
    final Function1<BaseVariable, CharSequence> _function = (BaseVariable it) -> {
      return this.localDefinition(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<BaseVariable, CharSequence>map(f.args(), _function), ", ");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }
}
