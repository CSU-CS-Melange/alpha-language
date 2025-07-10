package alpha.codegen.isl;

import alpha.codegen.CustomExpr;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.IfStmt;
import alpha.codegen.IfStmtBuilder;
import alpha.codegen.LoopStmt;
import alpha.codegen.Statement;
import fr.irisa.cairn.jnimap.isl.ISLASTBlockNode;
import fr.irisa.cairn.jnimap.isl.ISLASTExpression;
import fr.irisa.cairn.jnimap.isl.ISLASTForNode;
import fr.irisa.cairn.jnimap.isl.ISLASTIfNode;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLASTNodeList;
import fr.irisa.cairn.jnimap.isl.ISLASTUserNode;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Converts an isl AST node into equivalent C AST nodes and a list of any declared loop variable names.
 */
@SuppressWarnings("all")
public class ASTConverter {
  /**
   * A "user" node is just an expression statement, so we can directly
   * convert the expression it contains into a statement.
   */
  protected static ASTConversionResult _convert(final ISLASTUserNode node) {
    final ExpressionStmt stmt = Factory.customStmt(node.getExpression().toCString());
    return new ASTConversionResult(stmt);
  }

  /**
   * A "block" node just contains more nodes within it.
   */
  protected static ASTConversionResult _convert(final ISLASTBlockNode node) {
    final ISLASTNodeList childrenList = node.getChildren();
    int _nbNodes = childrenList.getNbNodes();
    final Function1<Integer, ASTConversionResult> _function = new Function1<Integer, ASTConversionResult>() {
      public ASTConversionResult apply(final Integer it) {
        return ASTConverter.convert(childrenList.get((it).intValue()));
      }
    };
    final Iterable<ASTConversionResult> results = IterableExtensions.<Integer, ASTConversionResult>map(new ExclusiveRange(0, _nbNodes, true), _function);
    final Function1<ASTConversionResult, ArrayList<String>> _function_1 = new Function1<ASTConversionResult, ArrayList<String>>() {
      public ArrayList<String> apply(final ASTConversionResult it) {
        return it.getDeclarations();
      }
    };
    final Iterable<String> declarations = IterableExtensions.<ASTConversionResult, String>flatMap(results, _function_1);
    final Function1<ASTConversionResult, ArrayList<Statement>> _function_2 = new Function1<ASTConversionResult, ArrayList<Statement>>() {
      public ArrayList<Statement> apply(final ASTConversionResult it) {
        return it.getStatements();
      }
    };
    final Iterable<Statement> statements = IterableExtensions.<ASTConversionResult, Statement>flatMap(results, _function_2);
    return new ASTConversionResult(declarations, ((Statement[])Conversions.unwrapArray(statements, Statement.class)));
  }

  /**
   * A "for" node corresponds to a loop statement.
   */
  protected static ASTConversionResult _convert(final ISLASTForNode node) {
    final String loopVariable = node.getIterator().toCString();
    final CustomExpr initializer = ASTConverter.getCustomExpr(node.getInit());
    final CustomExpr conditional = ASTConverter.getCustomExpr(node.getCond());
    final CustomExpr incrementBy = ASTConverter.getCustomExpr(node.getInc());
    final ASTConversionResult bodyResult = ASTConverter.convert(node.getBody());
    final ArrayList<String> declarations = CollectionLiterals.<String>newArrayList(loopVariable);
    declarations.addAll(bodyResult.getDeclarations());
    final LoopStmt statement = Factory.loopStmt(loopVariable, initializer, conditional, incrementBy, ((Statement[])Conversions.unwrapArray(bodyResult.getStatements(), Statement.class)));
    return new ASTConversionResult(declarations, statement);
  }

  /**
   * An "if" node corresponds to an If statement.
   * If there is an "else" node which is actually another "if" node,
   * then we convert that to an "else if" block using the "convertConditional"
   * dispatch methods.
   */
  protected static ASTConversionResult _convert(final ISLASTIfNode node) {
    final ASTConversionResult thenBlock = ASTConverter.convert(node.getThen());
    ArrayList<String> _declarations = thenBlock.getDeclarations();
    final ArrayList<String> declarations = new ArrayList<String>(_declarations);
    final IfStmtBuilder builder = IfStmtBuilder.start(ASTConverter.getCustomExpr(node.getCond())).addStatement(((Statement[])Conversions.unwrapArray(thenBlock.getStatements(), Statement.class)));
    int _hasElse = node.hasElse();
    boolean _greaterThan = (_hasElse > 0);
    if (_greaterThan) {
      final ArrayList<String> elseDeclarations = ASTConverter.convertConditional(node.getElse(), builder);
      declarations.addAll(elseDeclarations);
    }
    IfStmt _instance = builder.getInstance();
    return new ASTConversionResult(declarations, _instance);
  }

  /**
   * If the "else" of an "if" node is another "if" node, build an "else if" block.
   * Any declared variables are returned.
   */
  protected static ArrayList<String> _convertConditional(final ISLASTIfNode node, final IfStmtBuilder builder) {
    final ASTConversionResult thenBlock = ASTConverter.convert(node.getThen());
    ArrayList<String> _declarations = thenBlock.getDeclarations();
    final ArrayList<String> declarations = new ArrayList<String>(_declarations);
    builder.startElseIf(ASTConverter.getCustomExpr(node.getCond())).addStatement(((Statement[])Conversions.unwrapArray(thenBlock.getStatements(), Statement.class)));
    int _hasElse = node.hasElse();
    boolean _greaterThan = (_hasElse > 0);
    if (_greaterThan) {
      final ArrayList<String> elseDeclarations = ASTConverter.convertConditional(node.getElse(), builder);
      declarations.addAll(elseDeclarations);
    }
    return declarations;
  }

  /**
   * If the "else" of an "if" node isn't another "if" node, build an "else" block.
   * Any declared variables are returned.
   */
  protected static ArrayList<String> _convertConditional(final ISLASTNode node, final IfStmtBuilder builder) {
    final ASTConversionResult result = ASTConverter.convert(node);
    builder.startElse().addStatement(((Statement[])Conversions.unwrapArray(result.getStatements(), Statement.class)));
    return result.getDeclarations();
  }

  /**
   * Handles if the AST node type doesn't have a rule made for it.
   */
  protected static ASTConversionResult _convert(final ISLASTNode node) {
    try {
      throw new Exception("Not implemented yet!");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  /**
   * ISL expression nodes can just be converted to their C string.
   */
  public static CustomExpr getCustomExpr(final ISLASTExpression expression) {
    return Factory.customExpr(expression.toCString());
  }

  public static ASTConversionResult convert(final ISLASTNode node) {
    if (node instanceof ISLASTBlockNode) {
      return _convert((ISLASTBlockNode)node);
    } else if (node instanceof ISLASTForNode) {
      return _convert((ISLASTForNode)node);
    } else if (node instanceof ISLASTIfNode) {
      return _convert((ISLASTIfNode)node);
    } else if (node instanceof ISLASTUserNode) {
      return _convert((ISLASTUserNode)node);
    } else if (node != null) {
      return _convert(node);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(node).toString());
    }
  }

  protected static ArrayList<String> convertConditional(final ISLASTNode node, final IfStmtBuilder builder) {
    if (node instanceof ISLASTIfNode) {
      return _convertConditional((ISLASTIfNode)node, builder);
    } else if (node != null) {
      return _convertConditional(node, builder);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(node, builder).toString());
    }
  }
}
