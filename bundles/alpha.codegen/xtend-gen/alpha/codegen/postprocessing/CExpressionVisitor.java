package alpha.codegen.postprocessing;

import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BinaryExpr;
import alpha.codegen.Branch;
import alpha.codegen.CallExpr;
import alpha.codegen.CastExpr;
import alpha.codegen.CommentStmt;
import alpha.codegen.ConditionalBranch;
import alpha.codegen.CustomExpr;
import alpha.codegen.EmptyLineStmt;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Function;
import alpha.codegen.IfStmt;
import alpha.codegen.Include;
import alpha.codegen.LoopStmt;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.Program;
import alpha.codegen.ReturnStmt;
import alpha.codegen.Statement;
import alpha.codegen.TernaryExpr;
import alpha.codegen.UnaryExpr;
import alpha.codegen.UndefStmt;
import alpha.codegen.VariableDecl;
import java.util.Arrays;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
public class CExpressionVisitor extends CStatementVisitor {
  protected void _visit(final Expression expr) {
    this.visitExpression(expr);
  }

  protected void _visit(final CustomExpr expr) {
    this.visitCustomExpr(expr);
  }

  protected void _visit(final ParenthesizedExpr expr) {
    this.visitParenthesizedExpr(expr);
  }

  protected void _visit(final CastExpr expr) {
    this.visitCastExpr(expr);
  }

  protected void _visit(final ArrayAccessExpr expr) {
    this.visitArrayAccessExpr(expr);
  }

  protected void _visit(final CallExpr expr) {
    this.visitCallExpr(expr);
  }

  protected void _visit(final UnaryExpr expr) {
    this.visitUnaryExpr(expr);
  }

  protected void _visit(final BinaryExpr expr) {
    this.visitBinaryExpr(expr);
  }

  protected void _visit(final TernaryExpr expr) {
    this.visitTernaryExpr(expr);
  }

  @Override
  public void visitProgram(final Program program) {
    this.visit(program.getHeaderComment());
    final Consumer<Include> _function = (Include it) -> {
      this.visit(it);
    };
    program.getIncludes().forEach(_function);
    final Consumer<MacroStmt> _function_1 = (MacroStmt it) -> {
      this.visit(it);
    };
    program.getFunctionMacros().forEach(_function_1);
    final Consumer<VariableDecl> _function_2 = (VariableDecl it) -> {
      this.visit(it);
    };
    program.getGlobalVariables().forEach(_function_2);
    final Consumer<MacroStmt> _function_3 = (MacroStmt it) -> {
      this.visit(it);
    };
    program.getMemoryMacros().forEach(_function_3);
    final Consumer<Function> _function_4 = (Function it) -> {
      this.visit(it);
    };
    program.getFunctions().forEach(_function_4);
  }

  @Override
  public void visitExpressionStmt(final ExpressionStmt stmt) {
    this.inExpressionStmt(stmt);
    this.visit(stmt.getExpression());
    this.outExpressionStmt(stmt);
  }

  @Override
  public void visitMacroStmt(final MacroStmt stmt) {
    this.inMacroStmt(stmt);
    this.visit(stmt.getReplacement());
    this.outMacroStmt(stmt);
  }

  @Override
  public void visitLoopStmt(final LoopStmt stmt) {
    this.inLoopStmt(stmt);
    this.visit(stmt.getInitializer());
    this.visit(stmt.getConditional());
    this.visit(stmt.getIncrementBy());
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    stmt.getBody().forEach(_function);
    this.outLoopStmt(stmt);
  }

  @Override
  public void visitAssignmentStmt(final AssignmentStmt stmt) {
    this.inAssignmentStmt(stmt);
    this.visit(stmt.getLeft());
    this.visit(stmt.getRight());
    this.outAssignmentStmt(stmt);
  }

  @Override
  public void visitReturnStmt(final ReturnStmt stmt) {
    this.inReturnStmt(stmt);
    this.visit(stmt.getExpression());
    this.outReturnStmt(stmt);
  }

  @Override
  public void visitConditionalBranch(final ConditionalBranch branch) {
    this.inConditionalBranch(branch);
    this.visit(branch.getConditional());
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    branch.getBody().forEach(_function);
    this.outConditionalBranch(branch);
  }

  public void visitExpression(final Expression expr) {
    this.inExpression(expr);
    this.outExpression(expr);
  }

  public void visitCustomExpr(final CustomExpr expr) {
    this.inCustomExpr(expr);
    this.outCustomExpr(expr);
  }

  public void visitParenthesizedExpr(final ParenthesizedExpr expr) {
    this.inParenthesizedExpr(expr);
    this.visit(expr.getExpression());
    this.outParenthesizedExpr(expr);
  }

  public void visitCastExpr(final CastExpr expr) {
    this.inCastExpr(expr);
    this.visit(expr.getExpression());
    this.outCastExpr(expr);
  }

  public void visitArrayAccessExpr(final ArrayAccessExpr expr) {
    this.inArrayAccessExpr(expr);
    final Consumer<Expression> _function = (Expression it) -> {
      this.visit(it);
    };
    expr.getIndexExpressions().forEach(_function);
    this.outArrayAccessExpr(expr);
  }

  public void visitCallExpr(final CallExpr expr) {
    this.inCallExpr(expr);
    final Consumer<Expression> _function = (Expression it) -> {
      this.visit(it);
    };
    expr.getArguments().forEach(_function);
    this.outCallExpr(expr);
  }

  public void visitUnaryExpr(final UnaryExpr expr) {
    this.inUnaryExpr(expr);
    this.visit(expr.getExpression());
    this.outUnaryExpr(expr);
  }

  public void visitBinaryExpr(final BinaryExpr expr) {
    this.inBinaryExpr(expr);
    this.visit(expr.getLeft());
    this.visit(expr.getRight());
    this.outBinaryExpr(expr);
  }

  public void visitTernaryExpr(final TernaryExpr expr) {
    this.inTernaryExpr(expr);
    this.visit(expr.getConditional());
    this.visit(expr.getThenExpr());
    this.visit(expr.getElseExpr());
    this.outTernaryExpr(expr);
  }

  public void inExpression(final Expression expr) {
  }

  public void inCustomExpr(final CustomExpr expr) {
  }

  public void inParenthesizedExpr(final ParenthesizedExpr expr) {
  }

  public void inCastExpr(final CastExpr expr) {
  }

  public void inArrayAccessExpr(final ArrayAccessExpr expr) {
  }

  public void inCallExpr(final CallExpr expr) {
  }

  public void inUnaryExpr(final UnaryExpr expr) {
  }

  public void inBinaryExpr(final BinaryExpr expr) {
  }

  public void inTernaryExpr(final TernaryExpr expr) {
  }

  public void outExpression(final Expression expr) {
  }

  public void outCustomExpr(final CustomExpr expr) {
  }

  public void outParenthesizedExpr(final ParenthesizedExpr expr) {
  }

  public void outCastExpr(final CastExpr expr) {
  }

  public void outArrayAccessExpr(final ArrayAccessExpr expr) {
  }

  public void outCallExpr(final CallExpr expr) {
  }

  public void outUnaryExpr(final UnaryExpr expr) {
  }

  public void outBinaryExpr(final BinaryExpr expr) {
  }

  public void outTernaryExpr(final TernaryExpr expr) {
  }

  public void visit(final EObject expr) {
    if (expr instanceof ArrayAccessExpr) {
      _visit((ArrayAccessExpr)expr);
      return;
    } else if (expr instanceof AssignmentStmt) {
      _visit((AssignmentStmt)expr);
      return;
    } else if (expr instanceof BinaryExpr) {
      _visit((BinaryExpr)expr);
      return;
    } else if (expr instanceof CallExpr) {
      _visit((CallExpr)expr);
      return;
    } else if (expr instanceof CastExpr) {
      _visit((CastExpr)expr);
      return;
    } else if (expr instanceof CommentStmt) {
      _visit((CommentStmt)expr);
      return;
    } else if (expr instanceof ConditionalBranch) {
      _visit((ConditionalBranch)expr);
      return;
    } else if (expr instanceof CustomExpr) {
      _visit((CustomExpr)expr);
      return;
    } else if (expr instanceof EmptyLineStmt) {
      _visit((EmptyLineStmt)expr);
      return;
    } else if (expr instanceof ExpressionStmt) {
      _visit((ExpressionStmt)expr);
      return;
    } else if (expr instanceof IfStmt) {
      _visit((IfStmt)expr);
      return;
    } else if (expr instanceof LoopStmt) {
      _visit((LoopStmt)expr);
      return;
    } else if (expr instanceof MacroStmt) {
      _visit((MacroStmt)expr);
      return;
    } else if (expr instanceof ParenthesizedExpr) {
      _visit((ParenthesizedExpr)expr);
      return;
    } else if (expr instanceof ReturnStmt) {
      _visit((ReturnStmt)expr);
      return;
    } else if (expr instanceof TernaryExpr) {
      _visit((TernaryExpr)expr);
      return;
    } else if (expr instanceof UnaryExpr) {
      _visit((UnaryExpr)expr);
      return;
    } else if (expr instanceof UndefStmt) {
      _visit((UndefStmt)expr);
      return;
    } else if (expr instanceof Branch) {
      _visit((Branch)expr);
      return;
    } else if (expr instanceof Expression) {
      _visit((Expression)expr);
      return;
    } else if (expr instanceof Function) {
      _visit((Function)expr);
      return;
    } else if (expr instanceof Include) {
      _visit((Include)expr);
      return;
    } else if (expr instanceof Program) {
      _visit((Program)expr);
      return;
    } else if (expr instanceof Statement) {
      _visit((Statement)expr);
      return;
    } else if (expr instanceof VariableDecl) {
      _visit((VariableDecl)expr);
      return;
    } else if (expr == null) {
      _visit((Void)null);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
