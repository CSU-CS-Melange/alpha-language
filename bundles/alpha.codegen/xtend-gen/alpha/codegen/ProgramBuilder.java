package alpha.codegen;

import com.google.common.collect.Iterables;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * A builder to aid in the construction of C programs.
 * 
 * To start building, use the static "start" method.
 * To get the final result, use the "getInstance()" method.
 * 
 * Each region of the program (header comment, includes, etc.)
 * can be added to at any point. The various regions are printed
 * in a deterministic order regardless of when you populated them.
 * However, elements within each region will appear in the order
 * you added them.
 */
@SuppressWarnings("all")
public class ProgramBuilder {
  /**
   * The program instance being built.
   */
  protected final Program instance;

  /**
   * A name checker to ensure all names are unique.
   */
  protected final NameChecker nameChecker;

  /**
   * Retrieves the instance of the program which was built.
   */
  public Program getInstance() {
    return this.instance;
  }

  /**
   * Retrieves the name checker being used for building this program.
   */
  public NameChecker getNameChecker() {
    return this.nameChecker;
  }

  /**
   * Starts building a new program using the given name checker.
   */
  public static ProgramBuilder start(final NameChecker nameChecker) {
    return new ProgramBuilder(nameChecker);
  }

  /**
   * Protected constructor.
   */
  protected ProgramBuilder(final NameChecker nameChecker) {
    final CodegenFactory factory = CodegenFactory.eINSTANCE;
    this.instance = factory.createProgram();
    this.instance.setHeaderComment(factory.createCommentStmt());
    this.nameChecker = nameChecker;
  }

  /**
   * Adds lines to the program's header comment.
   */
  public ProgramBuilder addHeaderComment(final String... commentLines) {
    this.instance.getHeaderComment().addAll(IterableExtensions.<String>filterNull(((Iterable<String>)Conversions.doWrapArray(commentLines))));
    return this;
  }

  /**
   * Adds #include directives to the program.
   */
  public ProgramBuilder addInclude(final Include... includes) {
    Iterables.<Include>addAll(this.instance.getIncludes(), IterableExtensions.<Include>filterNull(((Iterable<Include>)Conversions.doWrapArray(includes))));
    return this;
  }

  /**
   * Adds macros to the "function macros" section of the program.
   * Intended to be used for defining common functions like min(a,b) or floor(a).
   * If any of the names are duplicates of anything else in the global scope,
   * a NameConflictException will be thrown.
   */
  public ProgramBuilder addFunctionMacro(final MacroStmt... macros) {
    final Iterable<MacroStmt> filtered = IterableExtensions.<MacroStmt>filterNull(((Iterable<MacroStmt>)Conversions.doWrapArray(macros)));
    boolean _isEmpty = IterableExtensions.isEmpty(filtered);
    boolean _not = (!_isEmpty);
    if (_not) {
      final Consumer<MacroStmt> _function = (MacroStmt it) -> {
        this.nameChecker.checkAddGlobal(it.getName());
      };
      filtered.forEach(_function);
      Iterables.<MacroStmt>addAll(this.instance.getFunctionMacros(), filtered);
    }
    return this;
  }

  /**
   * Declares a new global variable.
   * If the variable to add is an exact duplicate of an existing one,
   * it will be silently skipped.
   * If it has the same name as an existing one, but different type,
   * an IllegalArgumentException will be thrown.
   * Otherwise, the variable will be added.
   */
  public ProgramBuilder addGlobalVariable(final boolean isStatic, final DataType dataType, final String name) {
    final VariableDecl decl = Factory.variableDecl(isStatic, dataType, name);
    return this.addGlobalVariable(decl);
  }

  /**
   * Adds global variable declarations.
   * If any of the names are duplicates of anything else in the global scope,
   * a NameConflictException will be thrown.
   */
  public ProgramBuilder addGlobalVariable(final VariableDecl... variables) {
    final Iterable<VariableDecl> filtered = IterableExtensions.<VariableDecl>filterNull(((Iterable<VariableDecl>)Conversions.doWrapArray(variables)));
    boolean _isEmpty = IterableExtensions.isEmpty(filtered);
    boolean _not = (!_isEmpty);
    if (_not) {
      final Consumer<VariableDecl> _function = (VariableDecl it) -> {
        this.nameChecker.checkAddGlobal(it.getName());
      };
      filtered.forEach(_function);
      Iterables.<VariableDecl>addAll(this.instance.getGlobalVariables(), filtered);
    }
    return this;
  }

  /**
   * Adds macros to the "memory macros" section of the program.
   * Intended to be used for defining how the global variables are accessed.
   * Name checking is not performed, as we often want the memory macro's name
   * to match a global variable's name.
   */
  public ProgramBuilder addMemoryMacro(final MacroStmt... macros) {
    Iterables.<MacroStmt>addAll(this.instance.getMemoryMacros(), IterableExtensions.<MacroStmt>filterNull(((Iterable<MacroStmt>)Conversions.doWrapArray(macros))));
    return this;
  }

  /**
   * Adds functions to the program.
   * If any of the names are duplicates of anything else in the global scope,
   * a NameConflictException will be thrown.
   */
  public ProgramBuilder addFunction(final Function... functions) {
    final Iterable<Function> filtered = IterableExtensions.<Function>filterNull(((Iterable<Function>)Conversions.doWrapArray(functions)));
    boolean _isEmpty = IterableExtensions.isEmpty(filtered);
    boolean _not = (!_isEmpty);
    if (_not) {
      final Consumer<Function> _function = (Function it) -> {
        this.nameChecker.checkAddGlobal(it.getName());
      };
      filtered.forEach(_function);
      Iterables.<Function>addAll(this.instance.getFunctions(), filtered);
    }
    return this;
  }

  /**
   * Starts building a function using this program's name checker.
   */
  public FunctionBuilder startFunction(final BaseDataType returnType, final String name) {
    return FunctionBuilder.start(returnType, name, this.nameChecker);
  }

  /**
   * Starts building a function using this program's name checker.
   */
  public FunctionBuilder startFunction(final boolean isStatic, final boolean isInline, final DataType returnType, final String name) {
    return FunctionBuilder.start(isStatic, isInline, returnType, name, this.nameChecker);
  }
}
