package alpha.codegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * A collection of static helper methods for creating AST nodes for the simplified C AST.
 * Some methods are used as a short-hand for populating the AST nodes with some
 * default information and indicate as such.
 * 
 * For more information on what these AST nodes represent and how to populate them,
 * see "model/simpleC.xcore".
 */
@SuppressWarnings("all")
public class Factory {
  /**
   * The eCore instance of the factory for creating new object instances.
   */
  protected static final CodegenFactory factory = CodegenFactory.eINSTANCE;

  /**
   * Creates a "#include" directive using angle brackets (&lt; and &gt;).
   */
  public static Include include(final String file) {
    return Factory.include(file, false);
  }

  public static Include include(final String file, final boolean useQuotes) {
    final Include include = Factory.factory.createInclude();
    include.setFile(file);
    include.setUseQuotes(useQuotes);
    return include;
  }

  /**
   * Creates a non-pointer data type.
   */
  public static DataType dataType(final BaseDataType baseType) {
    return Factory.dataType(baseType, 0);
  }

  public static DataType dataType(final BaseDataType baseType, final int indirectionLevel) {
    final DataType dataType = Factory.factory.createDataType();
    dataType.setBaseType(baseType);
    dataType.setIndirectionLevel(indirectionLevel);
    return dataType;
  }

  public static VariableDecl variableDecl(final DataType dataType, final String name) {
    return Factory.variableDecl(false, dataType, name);
  }

  public static VariableDecl variableDecl(final boolean isStatic, final DataType dataType, final String name) {
    final VariableDecl variableDecl = Factory.factory.createVariableDecl();
    variableDecl.setIsStatic(isStatic);
    variableDecl.setDataType(dataType);
    variableDecl.setName(name);
    return variableDecl;
  }

  public static Parameter parameter(final DataType dataType, final String name) {
    final Parameter parameter = Factory.factory.createParameter();
    parameter.setDataType(dataType);
    parameter.setName(name);
    return parameter;
  }

  public static EmptyLineStmt emptyLineStmt() {
    return Factory.factory.createEmptyLineStmt();
  }

  public static CommentStmt commentStmt(final String... commentLines) {
    final CommentStmt comment = Factory.factory.createCommentStmt();
    comment.addAll(((Iterable<String>)Conversions.doWrapArray(commentLines)));
    return comment;
  }

  public static ExpressionStmt expressionStmt(final Expression expression) {
    final ExpressionStmt stmt = Factory.factory.createExpressionStmt();
    stmt.setExpression(expression);
    return stmt;
  }

  public static MacroStmt macroStmt(final String name, final String[] arguments, final String replacement) {
    final CustomExpr replacementExpr = Factory.customExpr(replacement);
    return Factory.macroStmt(name, arguments, replacementExpr);
  }

  public static MacroStmt macroStmt(final String name, final String[] arguments, final Expression replacement) {
    final MacroStmt macro = Factory.factory.createMacroStmt();
    macro.setName(name);
    CollectionExtensions.<String>addAll(macro.getArguments(), arguments);
    macro.setReplacement(replacement);
    return macro;
  }

  public static UndefStmt undefStmt(final String name) {
    final UndefStmt undef = Factory.factory.createUndefStmt();
    undef.setName(name);
    return undef;
  }

  public static LoopStmt loopStmt(final String loopVariable, final Expression initializer, final Expression conditional, final Expression incrementBy, final Statement... body) {
    final LoopStmt stmt = Factory.factory.createLoopStmt();
    stmt.setLoopVariable(loopVariable);
    stmt.setInitializer(initializer);
    stmt.setConditional(conditional);
    stmt.setIncrementBy(incrementBy);
    CollectionExtensions.<Statement>addAll(stmt.getBody(), body);
    return stmt;
  }

  public static AssignmentStmt assignmentStmt(final String left, final String right) {
    return Factory.assignmentStmt(Factory.customExpr(left), Factory.customExpr(right));
  }

  public static AssignmentStmt assignmentStmt(final String left, final Expression right) {
    return Factory.assignmentStmt(Factory.customExpr(left), right);
  }

  public static AssignmentStmt assignmentStmt(final Expression left, final Expression right) {
    return Factory.assignmentStmt(left, AssignmentOperator.STANDARD, right);
  }

  public static AssignmentStmt assignmentStmt(final Expression left, final AssignmentOperator assignType, final Expression right) {
    final AssignmentStmt stmt = Factory.factory.createAssignmentStmt();
    stmt.setLeft(left);
    stmt.setAssignType(assignType);
    stmt.setRight(right);
    return stmt;
  }

  public static ReturnStmt returnStmt(final Expression expression) {
    final ReturnStmt stmt = Factory.factory.createReturnStmt();
    stmt.setExpression(expression);
    return stmt;
  }

  public static ExpressionStmt customStmt(final String stmt) {
    return Factory.expressionStmt(Factory.customExpr(stmt));
  }

  public static ExpressionStmt callStmt(final String functionName, final String... arguments) {
    return Factory.expressionStmt(Factory.callExpr(functionName, arguments));
  }

  public static ExpressionStmt callStmt(final String functionName, final Expression... arguments) {
    return Factory.expressionStmt(Factory.callExpr(functionName, arguments));
  }

  /**
   * Creates a call to "exit".
   */
  public static ExpressionStmt exitCall(final int exitCode) {
    return Factory.callStmt("exit", Factory.customExpr(Integer.valueOf(exitCode).toString()));
  }

  /**
   * Creates a call to "printf".
   * The format string is used exactly as-provided,
   * so make sure you wrap it in quotation mark characters.
   */
  public static ExpressionStmt printfCall(final String format, final String... arguments) {
    final ArrayList<String> allArgs = CollectionLiterals.<String>newArrayList();
    allArgs.add(format);
    CollectionExtensions.<String>addAll(allArgs, arguments);
    return Factory.callStmt("printf", ((String[])Conversions.unwrapArray(allArgs, String.class)));
  }

  public static CustomExpr customExpr(final String expression) {
    final CustomExpr expr = Factory.factory.createCustomExpr();
    expr.setExpression(expression);
    return expr;
  }

  /**
   * Creates a custom expression wrapped in a parenthesized expression.
   */
  public static ParenthesizedExpr parenthesizedExpr(final String expression) {
    return Factory.parenthesizedExpr(Factory.customExpr(expression));
  }

  public static ParenthesizedExpr parenthesizedExpr(final Expression expression) {
    final ParenthesizedExpr expr = Factory.factory.createParenthesizedExpr();
    expr.setExpression(expression);
    return expr;
  }

  public static CastExpr castExpr(final DataType dataType, final Expression expression) {
    final CastExpr expr = Factory.factory.createCastExpr();
    expr.setDataType(dataType);
    expr.setExpression(expression);
    return expr;
  }

  /**
   * Indexes an array using custom expressions for the indices.
   */
  public static ArrayAccessExpr arrayAccessExpr(final String variableName, final String... indexExpressions) {
    final Function1<String, CustomExpr> _function = (String it) -> {
      return Factory.customExpr(it);
    };
    return Factory.arrayAccessExpr(variableName, ((Expression[])Conversions.unwrapArray(ListExtensions.<String, CustomExpr>map(((List<String>)Conversions.doWrapArray(indexExpressions)), _function), Expression.class)));
  }

  public static ArrayAccessExpr arrayAccessExpr(final String variableName, final Expression... indexExpressions) {
    final ArrayAccessExpr expr = Factory.factory.createArrayAccessExpr();
    expr.setVariableName(variableName);
    CollectionExtensions.<Expression>addAll(expr.getIndexExpressions(), indexExpressions);
    return expr;
  }

  public static CallExpr callExpr(final String functionName, final String... arguments) {
    final Function1<String, CustomExpr> _function = (String it) -> {
      return Factory.customExpr(it);
    };
    return Factory.callExpr(functionName, ((Expression[])Conversions.unwrapArray(ListExtensions.<String, CustomExpr>map(((List<String>)Conversions.doWrapArray(arguments)), _function), Expression.class)));
  }

  public static CallExpr callExpr(final String functionName, final Expression... arguments) {
    final CallExpr expr = Factory.factory.createCallExpr();
    expr.setFunctionName(functionName);
    CollectionExtensions.<Expression>addAll(expr.getArguments(), arguments);
    return expr;
  }

  /**
   * Creates a call to which allocates the desired number of bytes,
   * then casts it as the appropriate data type.
   */
  public static CastExpr mallocCall(final DataType dataType, final Expression amount) {
    BaseDataType _baseType = dataType.getBaseType();
    int _indirectionLevel = dataType.getIndirectionLevel();
    int _minus = (_indirectionLevel - 1);
    final DataType sizeofType = Factory.dataType(_baseType, _minus);
    final CustomExpr dataTypeExpr = Factory.customExpr(ProgramPrinter.print(sizeofType));
    final CallExpr sizeofCall = Factory.callExpr("sizeof", dataTypeExpr);
    final BinaryExpr bytesAllocated = Factory.binaryExpr(BinaryOperator.TIMES, sizeofCall, amount);
    final CallExpr mallocCall = Factory.callExpr("malloc", bytesAllocated);
    return Factory.castExpr(dataType, mallocCall);
  }

  /**
   * Creates a call to which allocates the desired number of bytes,
   * then casts it as the appropriate data type.
   */
  public static CastExpr callocCall(final DataType dataType, final Expression amount) {
    BaseDataType _baseType = dataType.getBaseType();
    int _indirectionLevel = dataType.getIndirectionLevel();
    int _minus = (_indirectionLevel - 1);
    final DataType sizeofType = Factory.dataType(_baseType, _minus);
    final CustomExpr dataTypeExpr = Factory.customExpr(ProgramPrinter.print(sizeofType));
    final CallExpr sizeofCall = Factory.callExpr("sizeof", dataTypeExpr);
    final List<? extends Expression> args = Collections.<Expression>unmodifiableList(CollectionLiterals.<Expression>newArrayList(amount, sizeofCall));
    final CallExpr mallocCall = Factory.callExpr("calloc", ((Expression[])Conversions.unwrapArray(args, Expression.class)));
    return Factory.castExpr(dataType, mallocCall);
  }

  public static UnaryExpr unaryExpr(final UnaryOperator operator, final Expression expression) {
    final UnaryExpr expr = Factory.factory.createUnaryExpr();
    expr.setOperator(operator);
    expr.setExpression(expression);
    return expr;
  }

  /**
   * Creates a tree of binary expressions which use the same operator.
   */
  public static Expression binaryExprTree(final BinaryOperator operator, final Expression... exprs) {
    final Function2<Expression, Expression, Expression> _function = (Expression left, Expression right) -> {
      return Factory.binaryExpr(operator, left, right);
    };
    return IterableExtensions.<Expression, Expression>fold(IterableExtensions.<Expression>tail(((Iterable<Expression>)Conversions.doWrapArray(exprs))), IterableExtensions.<Expression>head(((Iterable<Expression>)Conversions.doWrapArray(exprs))), _function);
  }

  public static BinaryExpr binaryExpr(final BinaryOperator operator, final Expression left, final Expression right) {
    final BinaryExpr expr = Factory.factory.createBinaryExpr();
    expr.setOperator(operator);
    expr.setLeft(left);
    expr.setRight(right);
    return expr;
  }

  public static TernaryExpr ternaryExpr(final Expression conditional, final Expression thenExpr, final Expression elseExpr) {
    final TernaryExpr expr = Factory.factory.createTernaryExpr();
    expr.setConditional(conditional);
    expr.setThenExpr(thenExpr);
    expr.setElseExpr(elseExpr);
    return expr;
  }
}
