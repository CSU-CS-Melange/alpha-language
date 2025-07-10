package alpha.codegen;

import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * A builder to aid in the construction of C functions.
 * 
 * To start building, use one of the static "start" methods.
 * To get the final result, use the "getInstance()" method.
 * 
 * Parameters, local variables, and statements can be added
 * at any point while building the function.
 * 
 * Parameters will appear in the order they're added.
 * Local variables will all be declared (with no initial value)
 * at the start of the function in the order they're added.
 * Statements, comments, etc. will appear after the variable
 * declarations in the order they're added.
 */
@SuppressWarnings("all")
public class FunctionBuilder {
  /**
   * The function instance being built.
   */
  protected final Function instance;

  /**
   * The set of names declared in the global scope which aren't
   * allowed to be re-defined here (to prevent variable shadowing).
   */
  protected NameChecker nameChecker;

  /**
   * Retrieves the instance of the function which was built.
   */
  public Function getInstance() {
    return this.instance;
  }

  /**
   * Starts building a new, non-static, non-inlined C function with a non-pointer return type.
   */
  public static FunctionBuilder start(final BaseDataType returnType, final String name, final NameChecker nameChecker) {
    final DataType dataType = Factory.dataType(returnType, 0);
    return new FunctionBuilder(false, false, dataType, name, nameChecker);
  }

  /**
   * Starts building a new, non-static, non-inlined C function.
   */
  public static FunctionBuilder start(final DataType returnType, final String name, final NameChecker nameChecker) {
    return new FunctionBuilder(false, false, returnType, name, nameChecker);
  }

  /**
   * Starts building a new, non-static C function.
   */
  public static FunctionBuilder start(final boolean isInline, final DataType returnType, final String name, final NameChecker nameChecker) {
    return new FunctionBuilder(false, isInline, returnType, name, nameChecker);
  }

  public static FunctionBuilder start(final boolean isStatic, final boolean isInline, final DataType returnType, final String name, final NameChecker nameChecker) {
    return new FunctionBuilder(isStatic, isInline, returnType, name, nameChecker);
  }

  /**
   * Protected constructor.
   */
  protected FunctionBuilder(final boolean isStatic, final boolean isInline, final DataType returnType, final String name, final NameChecker nameChecker) {
    this.instance = CodegenFactory.eINSTANCE.createFunction();
    this.instance.setIsStatic(Boolean.valueOf(isStatic));
    this.instance.setIsInline(Boolean.valueOf(isInline));
    this.instance.setReturnType(returnType);
    this.instance.setName(name);
    this.nameChecker = nameChecker;
  }

  /**
   * Adds a new parameter to the function.
   * Note: this does NOT check for name conflicts.
   */
  public FunctionBuilder addParameter(final DataType dataType, final String name) {
    final Parameter parameter = Factory.parameter(dataType, name);
    return this.addParameter(parameter);
  }

  /**
   * Adds new parameters to the function.
   * Note: this does NOT check for name conflicts.
   */
  public FunctionBuilder addParameter(final Parameter... parameters) {
    CollectionExtensions.<Parameter>addAll(this.instance.getParameters(), parameters);
    return this;
  }

  /**
   * Declares a new variable for use in the function being built.
   * Performs name checking to prevent conflicts.
   */
  public FunctionBuilder addVariable(final DataType dataType, final String name) {
    final VariableDecl decl = Factory.variableDecl(dataType, name);
    return this.addVariable(decl);
  }

  /**
   * Declares new variables for use in the function being built.
   * Performs name checking to prevent conflicts.
   */
  public FunctionBuilder addVariable(final VariableDecl... variables) {
    final Consumer<VariableDecl> _function = new Consumer<VariableDecl>() {
      public void accept(final VariableDecl it) {
        FunctionBuilder.this.nameChecker.checkAddLocal(it, FunctionBuilder.this.instance.getDeclarations());
      }
    };
    ((List<VariableDecl>)Conversions.doWrapArray(variables)).forEach(_function);
    return this;
  }

  /**
   * Adds statements to the function being built.
   */
  public FunctionBuilder addStatement(final Statement... statements) {
    CollectionExtensions.<Statement>addAll(this.instance.getStatements(), statements);
    return this;
  }

  /**
   * Adds a comment to the function being built.
   */
  public FunctionBuilder addComment(final String... commentLines) {
    final CommentStmt comment = Factory.commentStmt(commentLines);
    return this.addStatement(comment);
  }

  /**
   * Adds an empty line to the function being built.
   */
  public FunctionBuilder addEmptyLine() {
    return this.addStatement(Factory.emptyLineStmt());
  }

  /**
   * Adds undefine statements for the given macros.
   */
  public FunctionBuilder addUndefine(final MacroStmt... macros) {
    final Function1<MacroStmt, UndefStmt> _function = new Function1<MacroStmt, UndefStmt>() {
      public UndefStmt apply(final MacroStmt it) {
        return Factory.undefStmt(it.getName());
      }
    };
    return this.addStatement(((Statement[])Conversions.unwrapArray(ListExtensions.<MacroStmt, UndefStmt>map(((List<MacroStmt>)Conversions.doWrapArray(macros)), _function), Statement.class)));
  }

  /**
   * Adds a return statement for the given expression.
   */
  public FunctionBuilder addReturn(final Expression expr) {
    return this.addStatement(Factory.returnStmt(expr));
  }
}
