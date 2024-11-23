package alpha.codegen;

import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Prints the simplified C AST into a C program.
 * 
 * The intended entry point is the "print(Program)" method.
 * However, any of the methods can be used.
 * 
 * There are four main kinds of methods:
 * 1. The "printExpr" methods prints any node inheriting from "Expression".
 * 2. The "printStmt" methods prints any node inheriting from "Statement".
 * 3. There are a few methods like "printDeclaration" which are for printing
 *    parts of a node or for printing nodes in a specific way.
 * 4. Most everything else is handled with a "print" method.
 */
@SuppressWarnings("all")
public class ProgramPrinter {
  /**
   * Throws an exception to indicate a non-implemented function was reached.
   */
  public static CharSequence fault() {
    try {
      throw new Exception("Not implemented yet.");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static CharSequence print(final Program program) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printStmt = ProgramPrinter.printStmt(program.getHeaderComment());
    _builder.append(_printStmt);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      EList<Include> _includes = program.getIncludes();
      for(final Include include : _includes) {
        String _print = ProgramPrinter.print(include);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// Function Macros");
    _builder.newLine();
    {
      EList<MacroStmt> _functionMacros = program.getFunctionMacros();
      for(final MacroStmt macro : _functionMacros) {
        CharSequence _printStmt_1 = ProgramPrinter.printStmt(macro);
        _builder.append(_printStmt_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// Global Variables");
    _builder.newLine();
    {
      EList<VariableDecl> _globalVariables = program.getGlobalVariables();
      for(final VariableDecl variable : _globalVariables) {
        CharSequence _print_1 = ProgramPrinter.print(variable);
        _builder.append(_print_1);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// Memory Macros");
    _builder.newLine();
    {
      EList<MacroStmt> _memoryMacros = program.getMemoryMacros();
      for(final MacroStmt macro_1 : _memoryMacros) {
        CharSequence _printStmt_2 = ProgramPrinter.printStmt(macro_1);
        _builder.append(_printStmt_2);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("// Function Declarations");
    _builder.newLine();
    {
      EList<Function> _functions = program.getFunctions();
      for(final Function func : _functions) {
        CharSequence _printDeclaration = ProgramPrinter.printDeclaration(func);
        _builder.append(_printDeclaration);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<Function> _functions_1 = program.getFunctions();
      for(final Function func_1 : _functions_1) {
        CharSequence _printDefinition = ProgramPrinter.printDefinition(func_1);
        _builder.append(_printDefinition);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("// Undefine the Memory and Function Macros");
    _builder.newLine();
    {
      EList<MacroStmt> _memoryMacros_1 = program.getMemoryMacros();
      for(final MacroStmt macro_2 : _memoryMacros_1) {
        CharSequence _undefine = ProgramPrinter.undefine(macro_2);
        _builder.append(_undefine);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<MacroStmt> _functionMacros_1 = program.getFunctionMacros();
      for(final MacroStmt macro_3 : _functionMacros_1) {
        CharSequence _undefine_1 = ProgramPrinter.undefine(macro_3);
        _builder.append(_undefine_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }

  public static String print(final Include include) {
    boolean _isUseQuotes = include.isUseQuotes();
    if (_isUseQuotes) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"");
      String _file = include.getFile();
      _builder.append(_file);
      _builder.append("\"");
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("#include <");
      String _file_1 = include.getFile();
      _builder_1.append(_file_1);
      _builder_1.append(">");
      return _builder_1.toString();
    }
  }

  public static CharSequence print(final BaseDataType type) {
    CharSequence _switchResult = null;
    if (type != null) {
      switch (type) {
        case VOID:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("void");
          _switchResult = _builder.toString();
          break;
        case CHAR:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("char");
          _switchResult = _builder_1.toString();
          break;
        case INT:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("int");
          _switchResult = _builder_2.toString();
          break;
        case LONG:
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("long");
          _switchResult = _builder_3.toString();
          break;
        case FLOAT:
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("float");
          _switchResult = _builder_4.toString();
          break;
        case DOUBLE:
          StringConcatenation _builder_5 = new StringConcatenation();
          _builder_5.append("double");
          _switchResult = _builder_5.toString();
          break;
        default:
          _switchResult = ProgramPrinter.fault();
          break;
      }
    } else {
      _switchResult = ProgramPrinter.fault();
    }
    return _switchResult;
  }

  public static String print(final DataType type) {
    int _indirectionLevel = type.getIndirectionLevel();
    final Function1<Integer, String> _function = (Integer it) -> {
      return "*";
    };
    final String stars = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _indirectionLevel, true), _function));
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _print = ProgramPrinter.print(type.getBaseType());
    _builder.append(_print);
    _builder.append(stars);
    return _builder.toString();
  }

  public static CharSequence print(final VariableDecl decl) {
    StringConcatenation _builder = new StringConcatenation();
    String _printStatic = ProgramPrinter.printStatic(decl);
    _builder.append(_printStatic);
    String _print = ProgramPrinter.print(decl.getDataType());
    _builder.append(_print);
    _builder.append(" ");
    String _name = decl.getName();
    _builder.append(_name);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  public static String printStatic(final VariableDecl decl) {
    boolean _isIsStatic = decl.isIsStatic();
    if (_isIsStatic) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("static ");
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      return _builder_1.toString();
    }
  }

  public static CharSequence printDeclaration(final Function function) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printSignature = ProgramPrinter.printSignature(function);
    _builder.append(_printSignature);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  public static CharSequence printDefinition(final Function function) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printSignature = ProgramPrinter.printSignature(function);
    _builder.append(_printSignature);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      EList<VariableDecl> _declarations = function.getDeclarations();
      for(final VariableDecl decl : _declarations) {
        _builder.append("\t");
        CharSequence _print = ProgramPrinter.print(decl);
        _builder.append(_print, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Statement> _statements = function.getStatements();
      for(final Statement stmt : _statements) {
        _builder.append("\t");
        CharSequence _printStmt = ProgramPrinter.printStmt(stmt);
        _builder.append(_printStmt, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public static CharSequence printSignature(final Function function) {
    StringConcatenation _builder = new StringConcatenation();
    String _printStatic = ProgramPrinter.printStatic(function);
    _builder.append(_printStatic);
    String _printInline = ProgramPrinter.printInline(function);
    _builder.append(_printInline);
    String _print = ProgramPrinter.print(function.getReturnType());
    _builder.append(_print);
    _builder.append(" ");
    String _name = function.getName();
    _builder.append(_name);
    _builder.append("(");
    String _printParameters = ProgramPrinter.printParameters(function);
    _builder.append(_printParameters);
    _builder.append(")");
    return _builder;
  }

  public static String printInline(final Function function) {
    Boolean _isInline = function.getIsInline();
    if ((_isInline).booleanValue()) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("inline ");
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      return _builder_1.toString();
    }
  }

  public static String printStatic(final Function function) {
    Boolean _isStatic = function.getIsStatic();
    if ((_isStatic).booleanValue()) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("static ");
      return _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      return _builder_1.toString();
    }
  }

  public static String printParameters(final Function function) {
    final Function1<Parameter, CharSequence> _function = (Parameter it) -> {
      return ProgramPrinter.print(it);
    };
    return IterableExtensions.join(ListExtensions.<Parameter, CharSequence>map(function.getParameters(), _function), ", ");
  }

  public static CharSequence print(final Parameter parameter) {
    StringConcatenation _builder = new StringConcatenation();
    String _print = ProgramPrinter.print(parameter.getDataType());
    _builder.append(_print);
    _builder.append(" ");
    String _name = parameter.getName();
    _builder.append(_name);
    return _builder;
  }

  protected static CharSequence _printStmt(final EmptyLineStmt stmt) {
    return "\n";
  }

  protected static CharSequence _printStmt(final CommentStmt comment) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<String> _commentLines = comment.getCommentLines();
      for(final String line : _commentLines) {
        _builder.append("// ");
        _builder.append(line);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }

  protected static CharSequence _printStmt(final ExpressionStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printExpr = ProgramPrinter.printExpr(stmt.getExpression());
    _builder.append(_printExpr);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  protected static CharSequence _printStmt(final MacroStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#define ");
    String _name = stmt.getName();
    _builder.append(_name);
    _builder.append("(");
    String _join = IterableExtensions.join(stmt.getArguments(), ",");
    _builder.append(_join);
    _builder.append(") ");
    CharSequence _printExpr = ProgramPrinter.printExpr(stmt.getReplacement());
    _builder.append(_printExpr);
    return _builder.toString();
  }

  public static CharSequence undefine(final MacroStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#undef ");
    String _name = stmt.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  protected static CharSequence _printStmt(final UndefStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#undef ");
    String _name = stmt.getName();
    _builder.append(_name);
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  protected static CharSequence _printStmt(final IfStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printIf = ProgramPrinter.printIf(stmt.getIfBranch());
    _builder.append(_printIf);
    _builder.newLineIfNotEmpty();
    {
      EList<ConditionalBranch> _elseIfBranches = stmt.getElseIfBranches();
      for(final ConditionalBranch elif : _elseIfBranches) {
        CharSequence _printElseIf = ProgramPrinter.printElseIf(elif);
        _builder.append(_printElseIf);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      Branch _elseBranch = stmt.getElseBranch();
      boolean _tripleNotEquals = (_elseBranch != null);
      if (_tripleNotEquals) {
        CharSequence _printElse = ProgramPrinter.printElse(stmt.getElseBranch());
        _builder.append(_printElse);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }

  public static CharSequence printIf(final ConditionalBranch branch) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("if (");
    CharSequence _printExpr = ProgramPrinter.printExpr(branch.getConditional());
    _builder.append(_printExpr);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Statement> _body = branch.getBody();
      for(final Statement stmt : _body) {
        _builder.append("\t");
        CharSequence _printStmt = ProgramPrinter.printStmt(stmt);
        _builder.append(_printStmt, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public static CharSequence printElseIf(final ConditionalBranch branch) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("else if (");
    CharSequence _printExpr = ProgramPrinter.printExpr(branch.getConditional());
    _builder.append(_printExpr);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Statement> _body = branch.getBody();
      for(final Statement stmt : _body) {
        _builder.append("\t");
        CharSequence _printStmt = ProgramPrinter.printStmt(stmt);
        _builder.append(_printStmt, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  public static CharSequence printElse(final Branch branch) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("else {");
    _builder.newLine();
    {
      EList<Statement> _body = branch.getBody();
      for(final Statement stmt : _body) {
        _builder.append("\t");
        CharSequence _printStmt = ProgramPrinter.printStmt(stmt);
        _builder.append(_printStmt, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  protected static CharSequence _printStmt(final LoopStmt loop) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("for (");
    String _loopVariable = loop.getLoopVariable();
    _builder.append(_loopVariable);
    _builder.append(" = ");
    CharSequence _printExpr = ProgramPrinter.printExpr(loop.getInitializer());
    _builder.append(_printExpr);
    _builder.append("; ");
    CharSequence _printExpr_1 = ProgramPrinter.printExpr(loop.getConditional());
    _builder.append(_printExpr_1);
    _builder.append("; ");
    String _loopVariable_1 = loop.getLoopVariable();
    _builder.append(_loopVariable_1);
    _builder.append(" += ");
    CharSequence _printExpr_2 = ProgramPrinter.printExpr(loop.getIncrementBy());
    _builder.append(_printExpr_2);
    _builder.append(") {");
    _builder.newLineIfNotEmpty();
    {
      EList<Statement> _body = loop.getBody();
      for(final Statement stmt : _body) {
        _builder.append("\t");
        CharSequence _printStmt = ProgramPrinter.printStmt(stmt);
        _builder.append(_printStmt, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }

  protected static CharSequence _printStmt(final AssignmentStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printExpr = ProgramPrinter.printExpr(stmt);
    _builder.append(_printExpr);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  protected static CharSequence _printStmt(final ReturnStmt stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("return ");
    CharSequence _printExpr = ProgramPrinter.printExpr(stmt.getExpression());
    _builder.append(_printExpr);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    return _builder;
  }

  protected static CharSequence _printStmt(final Statement stmt) {
    return ProgramPrinter.fault();
  }

  protected static CharSequence _printExpr(final CustomExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    String _expression = expr.getExpression();
    _builder.append(_expression);
    return _builder;
  }

  protected static CharSequence _printExpr(final AssignmentStmt expr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printExpr = ProgramPrinter.printExpr(expr.getLeft());
    _builder.append(_printExpr);
    _builder.append(" ");
    CharSequence _print = ProgramPrinter.print(expr.getAssignType());
    _builder.append(_print);
    _builder.append(" ");
    CharSequence _printExpr_1 = ProgramPrinter.printExpr(expr.getRight());
    _builder.append(_printExpr_1);
    return _builder;
  }

  public static CharSequence print(final AssignmentOperator op) {
    CharSequence _switchResult = null;
    if (op != null) {
      switch (op) {
        case STANDARD:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("=");
          _switchResult = _builder.toString();
          break;
        case PLUS:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("+=");
          _switchResult = _builder_1.toString();
          break;
        case TIMES:
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("*=");
          _switchResult = _builder_2.toString();
          break;
        default:
          _switchResult = ProgramPrinter.fault();
          break;
      }
    } else {
      _switchResult = ProgramPrinter.fault();
    }
    return _switchResult;
  }

  protected static CharSequence _printExpr(final ParenthesizedExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _printExpr = ProgramPrinter.printExpr(expr.getExpression());
    _builder.append(_printExpr);
    _builder.append(")");
    return _builder;
  }

  protected static CharSequence _printExpr(final CastExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    String _print = ProgramPrinter.print(expr.getDataType());
    _builder.append(_print);
    _builder.append(")(");
    CharSequence _printExpr = ProgramPrinter.printExpr(expr.getExpression());
    _builder.append(_printExpr);
    _builder.append(")");
    return _builder;
  }

  protected static CharSequence _printExpr(final ArrayAccessExpr expr) {
    CharSequence _xifexpression = null;
    int _size = expr.getIndexExpressions().size();
    boolean _equals = (_size == 0);
    if (_equals) {
      StringConcatenation _builder = new StringConcatenation();
      String _variableName = expr.getVariableName();
      _builder.append(_variableName);
      _builder.append("[0]");
      _xifexpression = _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      String _variableName_1 = expr.getVariableName();
      _builder_1.append(_variableName_1);
      {
        EList<Expression> _indexExpressions = expr.getIndexExpressions();
        for(final Expression index : _indexExpressions) {
          _builder_1.append("[");
          CharSequence _printExpr = ProgramPrinter.printExpr(index);
          _builder_1.append(_printExpr);
          _builder_1.append("]");
        }
      }
      _xifexpression = _builder_1;
    }
    return _xifexpression;
  }

  protected static CharSequence _printExpr(final CallExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    String _functionName = expr.getFunctionName();
    _builder.append(_functionName);
    _builder.append("(");
    final Function1<Expression, CharSequence> _function = (Expression it) -> {
      return ProgramPrinter.printExpr(it);
    };
    String _join = IterableExtensions.join(ListExtensions.<Expression, CharSequence>map(expr.getArguments(), _function), ",");
    _builder.append(_join);
    _builder.append(")");
    return _builder;
  }

  protected static CharSequence _printExpr(final UnaryExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _print = ProgramPrinter.print(expr.getOperator());
    _builder.append(_print);
    _builder.append("(");
    CharSequence _printExpr = ProgramPrinter.printExpr(expr.getExpression());
    _builder.append(_printExpr);
    _builder.append(")");
    return _builder;
  }

  public static CharSequence print(final UnaryOperator op) {
    CharSequence _switchResult = null;
    if (op != null) {
      switch (op) {
        case NEGATE:
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("-");
          _switchResult = _builder.toString();
          break;
        case NOT:
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("!");
          _switchResult = _builder_1.toString();
          break;
        default:
          _switchResult = ProgramPrinter.fault();
          break;
      }
    } else {
      _switchResult = ProgramPrinter.fault();
    }
    return _switchResult;
  }

  protected static CharSequence _printExpr(final BinaryExpr expr) {
    boolean _isInfix = ProgramPrinter.isInfix(expr.getOperator());
    if (_isInfix) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(");
      CharSequence _printExpr = ProgramPrinter.printExpr(expr.getLeft());
      _builder.append(_printExpr);
      _builder.append(") ");
      CharSequence _print = ProgramPrinter.print(expr.getOperator());
      _builder.append(_print);
      _builder.append(" (");
      CharSequence _printExpr_1 = ProgramPrinter.printExpr(expr.getRight());
      _builder.append(_printExpr_1);
      _builder.append(")");
      return _builder;
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      CharSequence _print_1 = ProgramPrinter.print(expr.getOperator());
      _builder_1.append(_print_1);
      _builder_1.append("(");
      CharSequence _printExpr_2 = ProgramPrinter.printExpr(expr.getLeft());
      _builder_1.append(_printExpr_2);
      _builder_1.append(", ");
      CharSequence _printExpr_3 = ProgramPrinter.printExpr(expr.getRight());
      _builder_1.append(_printExpr_3);
      _builder_1.append(")");
      return _builder_1;
    }
  }

  public static boolean isInfix(final BinaryOperator op) {
    boolean _switchResult = false;
    if (op != null) {
      switch (op) {
        case MIN:
        case MAX:
          _switchResult = false;
          break;
        default:
          _switchResult = true;
          break;
      }
    } else {
      _switchResult = true;
    }
    return _switchResult;
  }

  public static CharSequence print(final BinaryOperator op) {
    CharSequence _switchResult = null;
    if (op != null) {
      switch (op) {
        case PLUS:
          _switchResult = "+";
          break;
        case MINUS:
          _switchResult = "-";
          break;
        case TIMES:
          _switchResult = "*";
          break;
        case DIVIDE:
          _switchResult = "/";
          break;
        case MODULUS:
          _switchResult = "%";
          break;
        case MIN:
          _switchResult = "min";
          break;
        case MAX:
          _switchResult = "max";
          break;
        case AND:
          _switchResult = "&&";
          break;
        case OR:
          _switchResult = "||";
          break;
        case LT:
          _switchResult = "<";
          break;
        case LEQ:
          _switchResult = "<=";
          break;
        case GT:
          _switchResult = ">";
          break;
        case GEQ:
          _switchResult = ">=";
          break;
        case EQ:
          _switchResult = "==";
          break;
        case NEQ:
          _switchResult = "!=";
          break;
        default:
          _switchResult = ProgramPrinter.fault();
          break;
      }
    } else {
      _switchResult = ProgramPrinter.fault();
    }
    return _switchResult;
  }

  protected static CharSequence _printExpr(final TernaryExpr expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("(");
    CharSequence _printExpr = ProgramPrinter.printExpr(expr.getConditional());
    _builder.append(_printExpr);
    _builder.append(") ? (");
    CharSequence _printExpr_1 = ProgramPrinter.printExpr(expr.getThenExpr());
    _builder.append(_printExpr_1);
    _builder.append(") : (");
    CharSequence _printExpr_2 = ProgramPrinter.printExpr(expr.getElseExpr());
    _builder.append(_printExpr_2);
    _builder.append(")");
    return _builder;
  }

  protected static CharSequence _printExpr(final Expression expr) {
    return ProgramPrinter.fault();
  }

  public static CharSequence printStmt(final Statement stmt) {
    if (stmt instanceof AssignmentStmt) {
      return _printStmt((AssignmentStmt)stmt);
    } else if (stmt instanceof CommentStmt) {
      return _printStmt((CommentStmt)stmt);
    } else if (stmt instanceof EmptyLineStmt) {
      return _printStmt((EmptyLineStmt)stmt);
    } else if (stmt instanceof ExpressionStmt) {
      return _printStmt((ExpressionStmt)stmt);
    } else if (stmt instanceof IfStmt) {
      return _printStmt((IfStmt)stmt);
    } else if (stmt instanceof LoopStmt) {
      return _printStmt((LoopStmt)stmt);
    } else if (stmt instanceof MacroStmt) {
      return _printStmt((MacroStmt)stmt);
    } else if (stmt instanceof ReturnStmt) {
      return _printStmt((ReturnStmt)stmt);
    } else if (stmt instanceof UndefStmt) {
      return _printStmt((UndefStmt)stmt);
    } else if (stmt != null) {
      return _printStmt(stmt);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(stmt).toString());
    }
  }

  public static CharSequence printExpr(final Expression expr) {
    if (expr instanceof ArrayAccessExpr) {
      return _printExpr((ArrayAccessExpr)expr);
    } else if (expr instanceof AssignmentStmt) {
      return _printExpr((AssignmentStmt)expr);
    } else if (expr instanceof BinaryExpr) {
      return _printExpr((BinaryExpr)expr);
    } else if (expr instanceof CallExpr) {
      return _printExpr((CallExpr)expr);
    } else if (expr instanceof CastExpr) {
      return _printExpr((CastExpr)expr);
    } else if (expr instanceof CustomExpr) {
      return _printExpr((CustomExpr)expr);
    } else if (expr instanceof ParenthesizedExpr) {
      return _printExpr((ParenthesizedExpr)expr);
    } else if (expr instanceof TernaryExpr) {
      return _printExpr((TernaryExpr)expr);
    } else if (expr instanceof UnaryExpr) {
      return _printExpr((UnaryExpr)expr);
    } else if (expr != null) {
      return _printExpr(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
