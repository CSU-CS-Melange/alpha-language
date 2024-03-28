package alpha.commands.model.generator.java;

import alpha.commands.model.AlphaCommand;
import alpha.commands.model.AlphaCommandCategory;
import alpha.commands.model.CommandBinding;
import alpha.commands.model.generator.CommonExtensions;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class AlphaJavaCommandCategory {
  @Extension
  private CommonExtensions commonEx = new CommonExtensions();

  @Extension
  private AlphaJavaCommand javaCommands = new AlphaJavaCommand();

  public CharSequence generateBase(final AlphaCommandCategory cat) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package alpha.commands;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/*PROTECTED REGION ID(");
    String _firstUpper = StringExtensions.toFirstUpper(cat.getName());
    _builder.append(_firstUpper);
    _builder.append("Base_Imports) ENABLED START*/");
    _builder.newLineIfNotEmpty();
    _builder.append("//Add custom imports here");
    _builder.newLine();
    _builder.append("/*PROTECTED REGION END*/");
    _builder.newLine();
    _builder.newLine();
    final Function1<CharSequence, CharSequence> _function = (CharSequence o) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("import ");
      _builder_1.append(o);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.<CharSequence>join(this.commonEx.collectUsedClassesInBaseMethods(cat), "\n", _function);
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _firstUpper_1 = StringExtensions.toFirstUpper(cat.getName());
    _builder.append(_firstUpper_1);
    _builder.append("Base {");
    _builder.newLineIfNotEmpty();
    {
      EList<AlphaCommand> _commands = cat.getCommands();
      for(final AlphaCommand command : _commands) {
        _builder.append("\t");
        CharSequence _generateBaseCommand = this.javaCommands.generateBaseCommand(command);
        _builder.append(_generateBaseCommand, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public CharSequence generate(final AlphaCommandCategory cat) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package alpha.commands;");
    _builder.newLine();
    _builder.newLine();
    final Function1<CharSequence, CharSequence> _function = (CharSequence o) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("import ");
      _builder_1.append(o);
      _builder_1.append(";");
      return _builder_1.toString();
    };
    String _join = IterableExtensions.<CharSequence>join(this.commonEx.collectUsedClasses(cat), "\n", _function);
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _firstUpper = StringExtensions.toFirstUpper(cat.getName());
    _builder.append(_firstUpper);
    _builder.append(" extends ");
    String _firstUpper_1 = StringExtensions.toFirstUpper(cat.getName());
    _builder.append(_firstUpper_1);
    _builder.append("Base {");
    _builder.newLineIfNotEmpty();
    {
      EList<AlphaCommand> _commands = cat.getCommands();
      for(final AlphaCommand command : _commands) {
        _builder.append("\t");
        {
          CommandBinding _binding = command.getBinding();
          boolean _tripleEquals = (_binding == null);
          if (_tripleEquals) {
            CharSequence _generateSpecializationCommand = this.javaCommands.generateSpecializationCommand(command);
            _builder.append(_generateSpecializationCommand, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        {
          CommandBinding _binding_1 = command.getBinding();
          boolean _tripleNotEquals = (_binding_1 != null);
          if (_tripleNotEquals) {
            CharSequence _generateCommandWithBindings = this.javaCommands.generateCommandWithBindings(command);
            _builder.append(_generateCommandWithBindings, "\t");
          }
        }
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
