package alpha.commands.model.generator.java;

import alpha.commands.model.AlphaCommand;
import alpha.commands.model.AlphaCommandArgument;
import alpha.commands.model.AlphaCommandSpecialization;
import alpha.commands.model.ArgumentBinding;
import alpha.commands.model.ArgumentRenaming;
import alpha.commands.model.ArgumentType;
import alpha.commands.model.CommandArgumentSpecialization;
import alpha.commands.model.CommandBinding;
import alpha.commands.model.DefaultValueArgument;
import alpha.commands.model.OverrideArgument;
import alpha.commands.model.SameAsParentArgument;
import alpha.commands.model.generator.CommonExtensions;
import com.google.common.base.Objects;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class AlphaJavaCommand {
  @Extension
  private CommonExtensions commonEx = new CommonExtensions();

  /**
   * Generates AlphaCommand where its body is defined manually in the protected region,
   * followed by a series of specializations that eventually invoke the base method.
   * 
   * All the specializations are automatically generated including the necessary code to
   * appropriately invoke the base method.
   */
  public CharSequence generateBaseCommand(final AlphaCommand command) {
    CharSequence _xifexpression = null;
    CommandBinding _binding = command.getBinding();
    boolean _tripleEquals = (_binding == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("public static ");
      String _returnTypeName = this.commonEx.returnTypeName(command.getSignature());
      _builder.append(_returnTypeName);
      _builder.append(" ");
      String _name = command.getName();
      _builder.append(_name);
      _builder.append("(");
      String _inputsList = this.commonEx.inputsList(command.getSignature());
      _builder.append(_inputsList);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("/*PROTECTED REGION ID(");
      CharSequence _protectedRegionID = this.commonEx.protectedRegionID(command);
      _builder.append(_protectedRegionID, "\t");
      _builder.append(") ENABLED START*/");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("throw new UnsupportedOperationException(\"Not implemented.\");");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/*PROTECTED REGION END*/");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xifexpression = _builder;
    }
    return _xifexpression;
  }

  public CharSequence generateSpecializationCommand(final AlphaCommand command) {
    CharSequence _xifexpression = null;
    CommandBinding _binding = command.getBinding();
    boolean _tripleEquals = (_binding == null);
    if (_tripleEquals) {
      StringConcatenation _builder = new StringConcatenation();
      {
        EList<AlphaCommandSpecialization> _specializations = command.getSpecializations();
        for(final AlphaCommandSpecialization specialization : _specializations) {
          CharSequence _generate = this.generate(specialization);
          _builder.append(_generate);
          _builder.newLineIfNotEmpty();
        }
      }
      _xifexpression = _builder;
    }
    return _xifexpression;
  }

  public CharSequence generateCommandWithBindings(final AlphaCommand command) {
    CharSequence _xifexpression = null;
    CommandBinding _binding = command.getBinding();
    boolean _tripleNotEquals = (_binding != null);
    if (_tripleNotEquals) {
      CharSequence _xblockexpression = null;
      {
        final AlphaCommand bindTarget = command.getBinding().getBindTargetCommand();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("public static ");
        String _returnTypeName = this.commonEx.returnTypeName(command.getSignature());
        _builder.append(_returnTypeName);
        _builder.append(" ");
        String _name = command.getName();
        _builder.append(_name);
        _builder.append("(");
        String _inputsList = this.commonEx.inputsList(command.getSignature());
        _builder.append(_inputsList);
        _builder.append(") {");
        _builder.newLineIfNotEmpty();
        {
          EList<ArgumentBinding> _argumentBindings = command.getBinding().getArgumentBindings();
          for(final ArgumentBinding binding : _argumentBindings) {
            _builder.append("\t");
            CharSequence _bindingCallPreparation = this.bindingCallPreparation(binding);
            _builder.append(_bindingCallPreparation, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<ArgumentRenaming> _argumentRenamings = command.getBinding().getArgumentRenamings();
          for(final ArgumentRenaming renaming : _argumentRenamings) {
            _builder.append("\t");
            CharSequence _bindingCallPreparation_1 = this.bindingCallPreparation(renaming);
            _builder.append(_bindingCallPreparation_1, "\t");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t");
        {
          ArgumentType _returnType = command.getSignature().getReturnType();
          boolean _notEquals = (!Objects.equal(_returnType, ArgumentType.VOID));
          if (_notEquals) {
            _builder.append("return ");
          }
        }
        String _name_1 = bindTarget.getName();
        _builder.append(_name_1, "\t");
        _builder.append("(");
        String _callArgumentsList = this.commonEx.callArgumentsList(bindTarget.getSignature());
        _builder.append(_callArgumentsList, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        {
          EList<AlphaCommandSpecialization> _specializations = command.getSpecializations();
          for(final AlphaCommandSpecialization specialization : _specializations) {
            CharSequence _generate = this.generate(specialization);
            _builder.append(_generate);
            _builder.newLineIfNotEmpty();
          }
        }
        _xblockexpression = _builder;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  public CharSequence bindingCallPreparation(final ArgumentBinding binding) {
    CharSequence _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      String _typeName = this.commonEx.typeName(binding.getBindTarget());
      _builder.append(_typeName);
      _builder.append(" ");
      String _name = binding.getBindTarget().getName();
      _builder.append(_name);
      final String target = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(target);
      _builder_1.append(" = ");
      CharSequence _bindingMethodCall = this.bindingMethodCall(binding);
      _builder_1.append(_bindingMethodCall);
      _builder_1.append(";");
      _xblockexpression = _builder_1;
    }
    return _xblockexpression;
  }

  public CharSequence bindingCallPreparation(final ArgumentRenaming renaming) {
    CharSequence _xifexpression = null;
    String _name = renaming.getBindSource().getName();
    String _name_1 = renaming.getBindTarget().getName();
    boolean _notEquals = (!Objects.equal(_name, _name_1));
    if (_notEquals) {
      CharSequence _xblockexpression = null;
      {
        StringConcatenation _builder = new StringConcatenation();
        String _typeName = this.commonEx.typeName(renaming.getBindSource());
        _builder.append(_typeName);
        _builder.append(" ");
        String _name_2 = renaming.getBindTarget().getName();
        _builder.append(_name_2);
        final String target = _builder.toString();
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(target);
        _builder_1.append(" = ");
        String _name_3 = renaming.getBindSource().getName();
        _builder_1.append(_name_3);
        _builder_1.append(";");
        _xblockexpression = _builder_1;
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  public CharSequence bindingMethodCall(final ArgumentBinding binding) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ValueConverter.to");
    String _typeName = this.commonEx.typeName(binding.getBindTarget());
    _builder.append(_typeName);
    _builder.append("(");
    final Function1<AlphaCommandArgument, CharSequence> _function = (AlphaCommandArgument b) -> {
      return b.getName();
    };
    String _join = IterableExtensions.<AlphaCommandArgument>join(binding.getBindSource(), ", ", _function);
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  public CharSequence generate(final AlphaCommandSpecialization specialization) {
    CharSequence _xblockexpression = null;
    {
      final AlphaCommand command = specialization.getCommand();
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("public static ");
      String _returnTypeName = this.commonEx.returnTypeName(command.getSignature());
      _builder.append(_returnTypeName);
      _builder.append(" ");
      String _name = command.getName();
      _builder.append(_name);
      _builder.append("(");
      String _inputsList = this.commonEx.inputsList(specialization);
      _builder.append(_inputsList);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      {
        Object[] _specializedCallPreparations = this.specializedCallPreparations(specialization);
        for(final Object scp : _specializedCallPreparations) {
          _builder.append("\t");
          _builder.append(scp, "\t");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("\t");
      {
        ArgumentType _returnType = command.getSignature().getReturnType();
        boolean _notEquals = (!Objects.equal(_returnType, ArgumentType.VOID));
        if (_notEquals) {
          _builder.append("return ");
        }
      }
      String _name_1 = command.getName();
      _builder.append(_name_1, "\t");
      _builder.append("(");
      String _callArgumentsList = this.commonEx.callArgumentsList(command.getSignature());
      _builder.append(_callArgumentsList, "\t");
      _builder.append(");");
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * Generates code that are necessary to convert the specialized inputs into
   * appropriate input arguments for the base method.
   * 
   * The variables with names corresponding to the base command inputs are
   * assumed to have the right type after this block of code.
   */
  public Object[] specializedCallPreparations(final AlphaCommandSpecialization specialization) {
    final BiFunction<AlphaCommandArgument, CommandArgumentSpecialization, CharSequence> _function = (AlphaCommandArgument orig, CommandArgumentSpecialization spec) -> {
      return this.getCallPreparation(spec, orig);
    };
    final Stream<CharSequence> list = this.commonEx.<CharSequence>zipArgumentPairs(specialization, _function);
    final Predicate<CharSequence> _function_1 = (CharSequence v) -> {
      return (v != null);
    };
    return list.filter(_function_1).toArray();
  }

  protected CharSequence _getCallPreparation(final OverrideArgument cas, final AlphaCommandArgument orig) {
    StringConcatenation _builder = new StringConcatenation();
    String _typeName = this.commonEx.typeName(orig.getArgumentType());
    _builder.append(_typeName);
    _builder.append(" ");
    String _name = orig.getName();
    _builder.append(_name);
    _builder.append(" = ");
    CharSequence _valueConverterCall = this.valueConverterCall(cas, orig);
    _builder.append(_valueConverterCall);
    _builder.append(";");
    return _builder;
  }

  protected CharSequence _getCallPreparation(final DefaultValueArgument cas, final AlphaCommandArgument orig) {
    CharSequence _xifexpression = null;
    ArgumentType _argumentType = orig.getArgumentType();
    boolean _equals = Objects.equal(_argumentType, ArgumentType.STRING);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      String _typeName = this.commonEx.typeName(orig.getArgumentType());
      _builder.append(_typeName);
      _builder.append(" ");
      String _name = orig.getName();
      _builder.append(_name);
      _builder.append(" = \"");
      String _value = cas.getValue();
      _builder.append(_value);
      _builder.append("\";");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _typeName_1 = this.commonEx.typeName(orig.getArgumentType());
      _builder_1.append(_typeName_1);
      _builder_1.append(" ");
      String _name_1 = orig.getName();
      _builder_1.append(_name_1);
      _builder_1.append(" = ");
      String _value_1 = cas.getValue();
      _builder_1.append(_value_1);
      _builder_1.append(";");
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  protected CharSequence _getCallPreparation(final SameAsParentArgument cas, final AlphaCommandArgument orig) {
    return null;
  }

  public CharSequence valueConverterCall(final OverrideArgument cas, final AlphaCommandArgument orig) {
    CharSequence _xblockexpression = null;
    {
      ArgumentType _argumentType = cas.getArgumentType();
      boolean _tripleNotEquals = (_argumentType != ArgumentType.STRING);
      if (_tripleNotEquals) {
        throw new UnsupportedOperationException("Only conversion from String is supported.");
      }
      final List<AlphaCommandArgument> origArgs = EcoreUtil2.<AlphaCommandArgument>getAllContentsOfType(orig.eContainer(), AlphaCommandArgument.class);
      Iterable<AlphaCommandArgument> _switchResult = null;
      ArgumentType _argumentType_1 = orig.getArgumentType();
      if (_argumentType_1 != null) {
        switch (_argumentType_1) {
          case ALPHA_SYSTEM:
            final Function1<AlphaCommandArgument, Boolean> _function = (AlphaCommandArgument a) -> {
              ArgumentType _argumentType_2 = a.getArgumentType();
              return Boolean.valueOf(Objects.equal(_argumentType_2, ArgumentType.ALPHA_ROOT));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function);
            break;
          case SYSTEM_BODY:
            final Function1<AlphaCommandArgument, Boolean> _function_1 = (AlphaCommandArgument a) -> {
              ArgumentType _argumentType_2 = a.getArgumentType();
              return Boolean.valueOf(Objects.equal(_argumentType_2, ArgumentType.ALPHA_SYSTEM));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function_1);
            break;
          case VARIABLE:
            final Function1<AlphaCommandArgument, Boolean> _function_2 = (AlphaCommandArgument a) -> {
              return Boolean.valueOf((((((Objects.equal(a.getArgumentType(), ArgumentType.ALPHA_SYSTEM) || 
                Objects.equal(a.getArgumentType(), ArgumentType.SYSTEM_BODY)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.STANDARD_EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.USE_EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.ALPHA_EXPRESSION)));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function_2);
            break;
          case EQUATION:
          case STANDARD_EQUATION:
          case USE_EQUATION:
            final Function1<AlphaCommandArgument, Boolean> _function_3 = (AlphaCommandArgument a) -> {
              ArgumentType _argumentType_2 = a.getArgumentType();
              return Boolean.valueOf(Objects.equal(_argumentType_2, ArgumentType.SYSTEM_BODY));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function_3);
            break;
          case ALPHA_EXPRESSION:
          case ABSTRACT_REDUCE_EXPRESSION:
          case BINARY_EXPRESSION:
          case DEPENDENCE_EXPRESSION:
          case REDUCE_EXPRESSION:
            final Function1<AlphaCommandArgument, Boolean> _function_4 = (AlphaCommandArgument a) -> {
              return Boolean.valueOf(((Objects.equal(a.getArgumentType(), ArgumentType.EQUATION) || 
                Objects.equal(a.getArgumentType(), ArgumentType.STANDARD_EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.USE_EQUATION)));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function_4);
            break;
          case AFFINE_FUNCTION:
          case DOMAIN:
            final Function1<AlphaCommandArgument, Boolean> _function_5 = (AlphaCommandArgument a) -> {
              return Boolean.valueOf((((((((((Objects.equal(a.getArgumentType(), ArgumentType.ALPHA_SYSTEM) || 
                Objects.equal(a.getArgumentType(), ArgumentType.SYSTEM_BODY)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.STANDARD_EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.USE_EQUATION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.ALPHA_EXPRESSION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.ABSTRACT_REDUCE_EXPRESSION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.BINARY_EXPRESSION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.DEPENDENCE_EXPRESSION)) || 
                Objects.equal(a.getArgumentType(), ArgumentType.REDUCE_EXPRESSION)));
            };
            _switchResult = IterableExtensions.<AlphaCommandArgument>filter(origArgs, _function_5);
            break;
          case ALPHA_COMPLETE_VISITABLE:
          case ALPHA_VISITABLE:
          case ALPHA_EXPRESSION_VISITABLE:
          case INTEGER:
          case INTEGER_LIST:
          case INTEGER_ARRAY:
            _switchResult = null;
            break;
          default:
            ArgumentType _argumentType_2 = orig.getArgumentType();
            String _plus = ("Conversion not supported: " + _argumentType_2);
            throw new UnsupportedOperationException(_plus);
        }
      } else {
        ArgumentType _argumentType_2 = orig.getArgumentType();
        String _plus = ("Conversion not supported: " + _argumentType_2);
        throw new UnsupportedOperationException(_plus);
      }
      final Iterable<AlphaCommandArgument> requiredType = _switchResult;
      if (((requiredType != null) && IterableExtensions.isEmpty(requiredType))) {
        ArgumentType _argumentType_3 = orig.getArgumentType();
        String _plus_1 = ("Necessary variable for converting " + _argumentType_3);
        String _plus_2 = (_plus_1 + " is missing.");
        throw new UnsupportedOperationException(_plus_2);
      }
      if (((requiredType != null) && (((Object[])Conversions.unwrapArray(requiredType, Object.class)).length > 1))) {
        ArgumentType _argumentType_4 = orig.getArgumentType();
        String _plus_3 = ("There are more than one variable for converting " + _argumentType_4);
        String _plus_4 = (_plus_3 + " and is ambiguous.");
        throw new UnsupportedOperationException(_plus_4);
      }
      final CharSequence specInputName = this.commonEx.specializationInputName(cas, orig);
      CharSequence _xifexpression = null;
      if ((requiredType == null)) {
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _valueConverterMethodName = this.valueConverterMethodName(orig);
        _builder.append(_valueConverterMethodName);
        _builder.append("(");
        _builder.append(specInputName);
        _builder.append(")");
        _xifexpression = _builder;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _valueConverterMethodName_1 = this.valueConverterMethodName(orig);
        _builder_1.append(_valueConverterMethodName_1);
        _builder_1.append("(");
        String _name = (((AlphaCommandArgument[])Conversions.unwrapArray(requiredType, AlphaCommandArgument.class))[0]).getName();
        _builder_1.append(_name);
        _builder_1.append(", ");
        _builder_1.append(specInputName);
        _builder_1.append(")");
        _xifexpression = _builder_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  private CharSequence valueConverterMethodName(final AlphaCommandArgument orig) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ValueConverter.to");
    String _name = orig.getArgumentType().getName();
    _builder.append(_name);
    return _builder;
  }

  public CharSequence getCallPreparation(final CommandArgumentSpecialization cas, final AlphaCommandArgument orig) {
    if (cas instanceof DefaultValueArgument) {
      return _getCallPreparation((DefaultValueArgument)cas, orig);
    } else if (cas instanceof OverrideArgument) {
      return _getCallPreparation((OverrideArgument)cas, orig);
    } else if (cas instanceof SameAsParentArgument) {
      return _getCallPreparation((SameAsParentArgument)cas, orig);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(cas, orig).toString());
    }
  }
}
