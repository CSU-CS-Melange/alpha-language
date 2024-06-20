package alpha.codegen;

import alpha.model.util.CommonExtensions;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Used for checking if a variable declaration exists in a list
 * and adding it to that list if it doesn't.
 */
@SuppressWarnings("all")
public class NameChecker {
  /**
   * The set of all names declared at the global scope.
   */
  protected final HashSet<String> globalNames;

  /**
   * If true, a local variable is not allowed to have the same name as a global variable.
   */
  protected final boolean preventShadowing;

  /**
   * The set of reserved keywords that aren't allowed to be used as names ever.
   * See: https://en.cppreference.com/w/c/keyword
   * See: https://en.cppreference.com/w/cpp/keyword
   */
  protected final HashSet<String> reservedKeywords;

  /**
   * Creates a new name checker that prevents shadowing global variables.
   */
  public NameChecker() {
    this(true);
  }

  /**
   * Creates a new name checker.
   */
  public NameChecker(final boolean preventShadowing) {
    this.globalNames = CollectionLiterals.<String>newHashSet();
    this.preventShadowing = preventShadowing;
    this.reservedKeywords = CollectionLiterals.<String>newHashSet(
      "alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept", 
      "auto", "bitand", "bitor", "bool", "break", "case", "catch", "char", "char16_t", "char32_t", 
      "char8_t", "class", "compl", "concept", "const", "consteval", "constexpr", "constinit", "const_cast", 
      "continue", "co_await", "co_return", "co_yield", "decltype", "default", "delete", "do", "double", 
      "dynamic_cast", "else", "enum", "explicit", "export", "extern", "false", "float", "for", "friend", 
      "goto", "if", "inline", "int", "long", "mutable", "namespace", "new", "noexcept", "not", "not_eq", 
      "nullptr", "operator", "or", "or_eq", "private", "protected", "public", "reflexpr", "register", 
      "reinterpret_cast", "requires", "restrict", "return", "short", "signed", "sizeof", "static", 
      "static_assert", "static_cast", "struct", "switch", "synchronized", "template", "this", "thread_local", 
      "throw", "true", "try", "typedef", "typeid", "typename", "typeof", "typeof_unqual", "union", "unsigned", 
      "using", "virtual", "void", "volatile", "wchar_t", "while", "xor", "xor_eq", "_Alignas", "_Alignof", 
      "_Atomic", "_BitInt", "_Bool", "_Complex", "_Decimal128", "_Decimal32", "_Decimal64", "_Generic", 
      "_Imaginary", "_Noreturn", "_Static_assert", "_Thread_local");
  }

  /**
   * Returns true if any of the given names exist in the global scope
   * or are a reserved keyword. Otherwise, returns false.
   */
  public boolean isGlobalOrKeyword(final String... names) {
    final Function1<String, Boolean> _function = (String it) -> {
      return Boolean.valueOf((this.globalNames.contains(it) || this.reservedKeywords.contains(it)));
    };
    return IterableExtensions.<String>exists(((Iterable<String>)Conversions.doWrapArray(names)), _function);
  }

  /**
   * Checks if all the given global variables are unique and records them if so.
   * If any have already been declared, a NameConflictException is thrown.
   */
  public void checkAddGlobal(final String... names) {
    try {
      for (final String name : names) {
        {
          boolean _contains = this.globalNames.contains(name);
          if (_contains) {
            throw new NameConflictException(name);
          }
          boolean _contains_1 = this.reservedKeywords.contains(name);
          if (_contains_1) {
            throw new ReservedKeywordException(name);
          }
          this.globalNames.add(name);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Checks if a local variable declaration is unique,
   * and adds it to the given list of declarations if so.
   * 
   * If the name already exists in the global scope and shadowing is disallowed,
   * or if the name exists in the local scope with a different data type,
   * a NameConflictException is thrown.
   * 
   * If the variable already exists at the local scope with the same data type,
   * the duplicate declaration is silently ignored.
   */
  public boolean checkAddLocal(final VariableDecl variable, final Collection<VariableDecl> existingLocals) {
    try {
      boolean _contains = this.reservedKeywords.contains(variable.getName());
      if (_contains) {
        String _name = variable.getName();
        throw new ReservedKeywordException(_name);
      }
      if ((this.preventShadowing && this.globalNames.contains(variable.getName()))) {
        String _name_1 = variable.getName();
        throw new NameConflictException(_name_1);
      }
      final Function1<VariableDecl, Boolean> _function = (VariableDecl it) -> {
        return Boolean.valueOf(NameChecker.hasSameNameAs(it, variable));
      };
      final ArrayList<VariableDecl> sameName = CommonExtensions.<VariableDecl>toArrayList(IterableExtensions.<VariableDecl>filter(existingLocals, _function));
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(sameName);
      if (_isNullOrEmpty) {
        existingLocals.add(variable);
        return true;
      }
      final Function1<VariableDecl, Boolean> _function_1 = (VariableDecl it) -> {
        return Boolean.valueOf(NameChecker.hasDifferentTypeThan(it, variable));
      };
      boolean _exists = IterableExtensions.<VariableDecl>exists(sameName, _function_1);
      if (_exists) {
        String _name_2 = variable.getName();
        throw new NameConflictException(_name_2);
      }
      return false;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * Returns true if the two variables have the same name, and false otherwise.
   */
  protected static boolean hasSameNameAs(final VariableDecl first, final VariableDecl second) {
    String _name = first.getName();
    String _name_1 = second.getName();
    return Objects.equal(_name, _name_1);
  }

  /**
   * Returns true if the two variables have different types, and false otherwise.
   */
  protected static boolean hasDifferentTypeThan(final VariableDecl first, final VariableDecl second) {
    return ((!Objects.equal(first.getDataType().getBaseType(), second.getDataType().getBaseType())) || (first.getDataType().getIndirectionLevel() != second.getDataType().getIndirectionLevel()));
  }

  /**
   * Repeatedly appends the given suffix to the base name until it is unique.
   * If the base name is already unique, the suffix is not added.
   * 
   * Note: the name returned is NOT added to the list of global names,
   * as that would be done when the name is actually declared.
   * This is just to get a name that's unique to use in a declaration.
   */
  public String getUniqueGlobalName(final String baseName, final String suffix) {
    String toAdd = baseName;
    while (this.globalNames.contains(toAdd)) {
      String _add = toAdd;
      toAdd = (_add + suffix);
    }
    return toAdd;
  }

  /**
   * Repeatedly appends the given suffix to the base name until it is unique.
   * If the base name is already unique, the suffix is not added.
   * 
   * Note: the name returned is NOT added to the list of global names,
   * as that would be done when the name is actually declared.
   * This is just to get a name that's unique to use in a declaration.
   */
  public String getUniqueLocalName(final Collection<String> localNames, final String baseName, final String suffix) {
    String toAdd = baseName;
    while (((this.globalNames.contains(toAdd) || localNames.contains(toAdd)) || this.reservedKeywords.contains(toAdd))) {
      String _add = toAdd;
      toAdd = (_add + suffix);
    }
    return toAdd;
  }

  /**
   * Gets a unique name for the given key by prepending the suffix,
   * then repeatedly appending underscores until the name is unique.
   * This newly generated name is then added to the given storage,
   * mapped to the given key.
   * If the key was already present in the storage, that name is
   * returned instead of generating a new one.
   */
  protected String getUniqueName(final String prefix, final String key, final HashMap<String, String> storage) {
    boolean _containsKey = storage.containsKey(key);
    boolean _not = (!_containsKey);
    if (_not) {
      final String uniqueName = this.getUniqueLocalName(storage.values(), (prefix + key), "_");
      storage.put(key, uniqueName);
    }
    return storage.get(key);
  }
}
