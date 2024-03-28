package alpha.commands.model.generator;

import alpha.commands.model.AlphaCommand;
import alpha.commands.model.AlphaCommandArgument;
import alpha.commands.model.AlphaCommandCategory;
import alpha.commands.model.AlphaCommandSignature;
import alpha.commands.model.AlphaCommandSpecialization;
import alpha.commands.model.AlphaCommandsRoot;
import alpha.commands.model.ArgumentType;
import alpha.commands.model.CommandArgumentSpecialization;
import alpha.commands.model.DefaultValueArgument;
import alpha.commands.model.OverrideArgument;
import alpha.commands.model.SameAsParentArgument;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class CommonExtensions {
  public String typeName(final AlphaCommandArgument arg) {
    return this.typeName(arg.getArgumentType());
  }

  public String typeName(final ArgumentType type) {
    String _switchResult = null;
    if (type != null) {
      switch (type) {
        case DOMAIN:
        case PARAMETER_DOMAIN:
          _switchResult = "ISLSet";
          break;
        case AFFINE_FUNCTION:
          _switchResult = "ISLMultiAff";
          break;
        default:
          _switchResult = type.getLiteral();
          break;
      }
    } else {
      _switchResult = type.getLiteral();
    }
    return _switchResult;
  }

  public CharSequence qualifiedName(final AlphaCommand command) {
    StringConcatenation _builder = new StringConcatenation();
    String _firstUpper = StringExtensions.toFirstUpper(command.getCategory().getName());
    _builder.append(_firstUpper);
    _builder.append(".");
    String _firstUpper_1 = StringExtensions.toFirstUpper(command.getName());
    _builder.append(_firstUpper_1);
    return _builder;
  }

  public CharSequence protectedRegionID(final AlphaCommand command) {
    CharSequence _xblockexpression = null;
    {
      String _xifexpression = null;
      String _label = command.getLabel();
      boolean _tripleNotEquals = (_label != null);
      if (_tripleNotEquals) {
        _xifexpression = command.getLabel();
      } else {
        String _xblockexpression_1 = null;
        {
          final Function1<AlphaCommand, Boolean> _function = (AlphaCommand c) -> {
            return Boolean.valueOf(c.getName().contentEquals(command.getName()));
          };
          final int index = IterableExtensions.<AlphaCommand>toList(IterableExtensions.<AlphaCommand>filter(command.getCategory().getCommands(), _function)).indexOf(command);
          String _xifexpression_1 = null;
          if ((index == 0)) {
            StringConcatenation _builder = new StringConcatenation();
            _xifexpression_1 = _builder.toString();
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("_");
            _builder_1.append(index);
            _xifexpression_1 = _builder_1.toString();
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _xifexpression = _xblockexpression_1;
      }
      final String namePrefix = _xifexpression;
      StringConcatenation _builder = new StringConcatenation();
      CharSequence _qualifiedName = this.qualifiedName(command);
      _builder.append(_qualifiedName);
      _builder.append("_");
      _builder.append(namePrefix);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public String returnTypeName(final AlphaCommandSignature sig) {
    String _xifexpression = null;
    ArgumentType _returnType = sig.getReturnType();
    boolean _tripleEquals = (_returnType == null);
    if (_tripleEquals) {
      return "void";
    } else {
      _xifexpression = this.typeName(sig.getReturnType());
    }
    return _xifexpression;
  }

  public String inputsList(final AlphaCommandSignature sig) {
    final Function1<AlphaCommandArgument, CharSequence> _function = (AlphaCommandArgument a) -> {
      StringConcatenation _builder = new StringConcatenation();
      String _typeName = this.typeName(a.getArgumentType());
      _builder.append(_typeName);
      _builder.append(" ");
      String _name = a.getName();
      _builder.append(_name);
      return _builder.toString();
    };
    return IterableExtensions.<AlphaCommandArgument>join(sig.getArguments(), ", ", _function);
  }

  public String callArgumentsList(final AlphaCommandSignature sig) {
    final Function1<AlphaCommandArgument, CharSequence> _function = (AlphaCommandArgument a) -> {
      StringConcatenation _builder = new StringConcatenation();
      String _name = a.getName();
      _builder.append(_name);
      return _builder.toString();
    };
    return IterableExtensions.<AlphaCommandArgument>join(sig.getArguments(), ", ", _function);
  }

  public String callArgumentsList(final AlphaCommandSpecialization specialization) {
    final BiFunction<AlphaCommandArgument, CommandArgumentSpecialization, Object> _function = (AlphaCommandArgument orig, CommandArgumentSpecialization spec) -> {
      return this.specializationInputName(spec, orig);
    };
    final Stream<Object> list = this.<Object>zipArgumentPairs(specialization, _function);
    final Predicate<Object> _function_1 = (Object v) -> {
      return (v != null);
    };
    return IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(list.filter(_function_1).toArray())), ", ");
  }

  public Set<CharSequence> fullPath(final ArgumentType type) {
    Set<CharSequence> _switchResult = null;
    if (type != null) {
      switch (type) {
        case VOID:
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet());
          break;
        case ALPHA_NODE:
        case ALPHA_COMPLETE_VISITABLE:
        case ALPHA_VISITABLE:
        case ALPHA_EXPRESSION_VISITABLE:
        case ALPHA_ROOT:
        case ALPHA_SYSTEM:
        case SYSTEM_BODY:
        case VARIABLE:
        case EQUATION:
        case STANDARD_EQUATION:
        case USE_EQUATION:
        case ALPHA_EXPRESSION:
        case ABSTRACT_REDUCE_EXPRESSION:
        case AUTO_RESTRICT_EXPRESSION:
        case BINARY_EXPRESSION:
        case DEPENDENCE_EXPRESSION:
        case MULTI_ARG_EXPRESSION:
        case REDUCE_EXPRESSION:
        case RESTRICT_EXPRESSION:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("alpha.model.");
          String _literal = type.getLiteral();
          _builder.append(_literal);
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet(_builder));
          break;
        case AFFINE_FUNCTION:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("fr.irisa.cairn.jnimap.isl.JNIISLMultiAff");
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet(_builder_1));
          break;
        case DOMAIN:
        case PARAMETER_DOMAIN:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("fr.irisa.cairn.jnimap.isl.JNIISLSet");
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet(_builder_2));
          break;
        case ALPHA_BUNDLE:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("alpha.model.AlphaRoot");
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("java.util.List");
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet(_builder_3, _builder_4));
          break;
        case STRING:
        case STRING_ARRAY:
        case INTEGER:
        case INTEGER_ARRAY:
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet());
          break;
        case STRING_LIST:
        case INTEGER_LIST:
          StringConcatenation _builder_5 = new StringConcatenation();
          _builder_5.append("java.util.List");
          _switchResult = Collections.<CharSequence>unmodifiableSet(CollectionLiterals.<CharSequence>newHashSet(_builder_5));
          break;
        default:
          break;
      }
    }
    return _switchResult;
  }

  public Iterable<CharSequence> collectUsedClassesInBaseMethods(final AlphaCommandCategory cat) {
    final Function1<AlphaCommandSignature, ArgumentType> _function = (AlphaCommandSignature o) -> {
      return o.getReturnType();
    };
    List<ArgumentType> _map = ListExtensions.<AlphaCommandSignature, ArgumentType>map(EcoreUtil2.<AlphaCommandSignature>getAllContentsOfType(cat, AlphaCommandSignature.class), _function);
    final Function1<AlphaCommandArgument, ArgumentType> _function_1 = (AlphaCommandArgument o) -> {
      return o.getArgumentType();
    };
    List<ArgumentType> _map_1 = ListExtensions.<AlphaCommandArgument, ArgumentType>map(EcoreUtil2.<AlphaCommandArgument>getAllContentsOfType(cat, AlphaCommandArgument.class), _function_1);
    final Function1<ArgumentType, Set<CharSequence>> _function_2 = (ArgumentType t) -> {
      return this.fullPath(t);
    };
    return IterableExtensions.<ArgumentType, CharSequence>flatMap(IterableExtensions.<ArgumentType>toSet(Iterables.<ArgumentType>concat(_map, _map_1)), _function_2);
  }

  public Iterable<CharSequence> collectUsedClasses(final AlphaCommandCategory cat) {
    final Function1<AlphaCommandSignature, ArgumentType> _function = (AlphaCommandSignature o) -> {
      return o.getReturnType();
    };
    List<ArgumentType> _map = ListExtensions.<AlphaCommandSignature, ArgumentType>map(EcoreUtil2.<AlphaCommandSignature>getAllContentsOfType(cat, AlphaCommandSignature.class), _function);
    final Function1<AlphaCommandArgument, ArgumentType> _function_1 = (AlphaCommandArgument o) -> {
      return o.getArgumentType();
    };
    List<ArgumentType> _map_1 = ListExtensions.<AlphaCommandArgument, ArgumentType>map(EcoreUtil2.<AlphaCommandArgument>getAllContentsOfType(cat, AlphaCommandArgument.class), _function_1);
    Iterable<ArgumentType> _plus = Iterables.<ArgumentType>concat(_map, _map_1);
    final Function1<OverrideArgument, ArgumentType> _function_2 = (OverrideArgument o) -> {
      return o.getArgumentType();
    };
    List<ArgumentType> _map_2 = ListExtensions.<OverrideArgument, ArgumentType>map(EcoreUtil2.<OverrideArgument>getAllContentsOfType(cat, OverrideArgument.class), _function_2);
    final Function1<ArgumentType, Set<CharSequence>> _function_3 = (ArgumentType t) -> {
      return this.fullPath(t);
    };
    return IterableExtensions.<ArgumentType, CharSequence>flatMap(IterableExtensions.<ArgumentType>toSet(Iterables.<ArgumentType>concat(_plus, _map_2)), _function_3);
  }

  public Iterable<CharSequence> collectUsedClasses(final AlphaCommandsRoot root) {
    final Function1<AlphaCommandCategory, Iterable<CharSequence>> _function = (AlphaCommandCategory it) -> {
      return this.collectUsedClasses(it);
    };
    return IterableExtensions.<AlphaCommandCategory, CharSequence>flatMap(root.getCategories(), _function);
  }

  /**
   * Helper function to manage traversal of parallel lists holding arguments of
   * the base and the specialized command.
   */
  public <R extends Object> Stream<R> zipArgumentPairs(final AlphaCommandSpecialization specialization, final BiFunction<? super AlphaCommandArgument, ? super CommandArgumentSpecialization, R> f) {
    Stream<R> _xblockexpression = null;
    {
      final EList<AlphaCommandArgument> origInputs = specialization.getCommand().getSignature().getArguments();
      final EList<CommandArgumentSpecialization> specInputs = specialization.getArguments();
      int _length = ((Object[])Conversions.unwrapArray(origInputs, Object.class)).length;
      int _length_1 = ((Object[])Conversions.unwrapArray(specInputs, Object.class)).length;
      boolean _notEquals = (_length != _length_1);
      if (_notEquals) {
        throw new RuntimeException("Specialization must have the same number of inputs as its base command.");
      }
      _xblockexpression = Streams.<AlphaCommandArgument, CommandArgumentSpecialization, R>zip(origInputs.stream(), specInputs.stream(), f);
    }
    return _xblockexpression;
  }

  /**
   * Generates input arguments list to a specialized command.
   */
  public String inputsList(final AlphaCommandSpecialization specialization) {
    final BiFunction<AlphaCommandArgument, CommandArgumentSpecialization, Object> _function = (AlphaCommandArgument orig, CommandArgumentSpecialization spec) -> {
      return this.getSpecializedArgument(spec, orig);
    };
    final Stream<Object> list = this.<Object>zipArgumentPairs(specialization, _function);
    final Predicate<Object> _function_1 = (Object v) -> {
      return (v != null);
    };
    return IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(list.filter(_function_1).toArray())), ", ");
  }

  protected CharSequence _specializationInputName(final OverrideArgument cas, final AlphaCommandArgument orig) {
    CharSequence _xifexpression = null;
    boolean _contentEquals = cas.getName().contentEquals(orig.getName());
    if (_contentEquals) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("s_");
      String _name = cas.getName();
      _builder.append(_name);
      _xifexpression = _builder;
    } else {
      _xifexpression = cas.getName();
    }
    return _xifexpression;
  }

  protected CharSequence _specializationInputName(final DefaultValueArgument dva, final AlphaCommandArgument orig) {
    return null;
  }

  protected CharSequence _specializationInputName(final CommandArgumentSpecialization cas, final AlphaCommandArgument orig) {
    return orig.getName();
  }

  protected CharSequence _getSpecializedArgument(final OverrideArgument cas, final AlphaCommandArgument orig) {
    CharSequence _xblockexpression = null;
    {
      final CharSequence specInputName = this.specializationInputName(cas, orig);
      StringConcatenation _builder = new StringConcatenation();
      String _typeName = this.typeName(cas.getArgumentType());
      _builder.append(_typeName);
      _builder.append(" ");
      _builder.append(specInputName);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  protected CharSequence _getSpecializedArgument(final DefaultValueArgument cas, final AlphaCommandArgument orig) {
    return null;
  }

  protected CharSequence _getSpecializedArgument(final SameAsParentArgument cas, final AlphaCommandArgument orig) {
    CharSequence _xblockexpression = null;
    {
      final CharSequence specInputName = this.specializationInputName(cas, orig);
      StringConcatenation _builder = new StringConcatenation();
      String _typeName = this.typeName(orig.getArgumentType());
      _builder.append(_typeName);
      _builder.append(" ");
      _builder.append(specInputName);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public CharSequence specializationInputName(final CommandArgumentSpecialization dva, final AlphaCommandArgument orig) {
    if (dva instanceof DefaultValueArgument) {
      return _specializationInputName((DefaultValueArgument)dva, orig);
    } else if (dva instanceof OverrideArgument) {
      return _specializationInputName((OverrideArgument)dva, orig);
    } else if (dva != null) {
      return _specializationInputName(dva, orig);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(dva, orig).toString());
    }
  }

  public CharSequence getSpecializedArgument(final CommandArgumentSpecialization cas, final AlphaCommandArgument orig) {
    if (cas instanceof DefaultValueArgument) {
      return _getSpecializedArgument((DefaultValueArgument)cas, orig);
    } else if (cas instanceof OverrideArgument) {
      return _getSpecializedArgument((OverrideArgument)cas, orig);
    } else if (cas instanceof SameAsParentArgument) {
      return _getSpecializedArgument((SameAsParentArgument)cas, orig);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(cas, orig).toString());
    }
  }
}
