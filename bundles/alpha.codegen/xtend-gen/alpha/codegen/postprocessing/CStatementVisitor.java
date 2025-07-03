package alpha.codegen.postprocessing;

import alpha.codegen.AssignmentStmt;
import alpha.codegen.Branch;
import alpha.codegen.CommentStmt;
import alpha.codegen.ConditionalBranch;
import alpha.codegen.EmptyLineStmt;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Function;
import alpha.codegen.IfStmt;
import alpha.codegen.Include;
import alpha.codegen.LoopStmt;
import alpha.codegen.MacroStmt;
import alpha.codegen.Program;
import alpha.codegen.ReturnStmt;
import alpha.codegen.Statement;
import alpha.codegen.UndefStmt;
import alpha.codegen.VariableDecl;
import java.util.Arrays;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
public abstract class CStatementVisitor {
  protected void _visit(final Void v) {
  }

  protected void _visit(final Program program) {
    this.visitProgram(program);
  }

  protected void _visit(final Function func) {
    this.visitFunction(func);
  }

  protected void _visit(final Include incl) {
    this.visitInclude(incl);
  }

  protected void _visit(final VariableDecl decl) {
    this.visitVariableDecl(decl);
  }

  protected void _visit(final Statement stmt) {
    this.visitStatement(stmt);
  }

  protected void _visit(final EmptyLineStmt stmt) {
    this.visitEmptyLineStmt(stmt);
  }

  protected void _visit(final CommentStmt stmt) {
    this.visitCommentStmt(stmt);
  }

  protected void _visit(final ExpressionStmt stmt) {
    this.visitExpressionStmt(stmt);
  }

  protected void _visit(final MacroStmt stmt) {
    this.visitMacroStmt(stmt);
  }

  protected void _visit(final UndefStmt stmt) {
    this.visitUndefStmt(stmt);
  }

  protected void _visit(final IfStmt stmt) {
    this.visitIfStmt(stmt);
  }

  protected void _visit(final LoopStmt stmt) {
    this.visitLoopStmt(stmt);
  }

  protected void _visit(final AssignmentStmt stmt) {
    this.visitAssignmentStmt(stmt);
  }

  protected void _visit(final ReturnStmt stmt) {
    this.visitReturnStmt(stmt);
  }

  protected void _visit(final Branch branch) {
    this.visitBranch(branch);
  }

  protected void _visit(final ConditionalBranch branch) {
    this.visitConditionalBranch(branch);
  }

  public void visitProgram(final Program program) {
    final Consumer<Include> _function = (Include it) -> {
      this.visit(it);
    };
    program.getIncludes().forEach(_function);
    final Consumer<VariableDecl> _function_1 = (VariableDecl it) -> {
      this.visit(it);
    };
    program.getGlobalVariables().forEach(_function_1);
    final Consumer<Function> _function_2 = (Function it) -> {
      this.visit(it);
    };
    program.getFunctions().forEach(_function_2);
  }

  public void visitFunction(final Function func) {
    this.inFunction(func);
    final Consumer<VariableDecl> _function = (VariableDecl it) -> {
      this.visit(it);
    };
    func.getDeclarations().forEach(_function);
    final Consumer<Statement> _function_1 = (Statement it) -> {
      this.visit(it);
    };
    func.getStatements().forEach(_function_1);
    this.outFunction(func);
  }

  public void visitInclude(final Include incl) {
    this.inInclude(incl);
    this.outInclude(incl);
  }

  public void visitVariableDecl(final VariableDecl decl) {
    this.inVariableDecl(decl);
    this.outVariableDecl(decl);
  }

  public void visitStatement(final Statement stmt) {
    this.inStatement(stmt);
    this.outStatement(stmt);
  }

  public void visitEmptyLineStmt(final EmptyLineStmt stmt) {
    this.inEmptyLineStmt(stmt);
    this.outEmptyLineStmt(stmt);
  }

  public void visitCommentStmt(final CommentStmt stmt) {
    this.inCommentStmt(stmt);
    this.outCommentStmt(stmt);
  }

  public void visitExpressionStmt(final ExpressionStmt stmt) {
    this.inExpressionStmt(stmt);
    this.outExpressionStmt(stmt);
  }

  public void visitMacroStmt(final MacroStmt stmt) {
    this.inMacroStmt(stmt);
    this.outMacroStmt(stmt);
  }

  public void visitUndefStmt(final UndefStmt stmt) {
    this.inUndefStmt(stmt);
    this.outUndefStmt(stmt);
  }

  public void visitIfStmt(final IfStmt stmt) {
    this.inIfStmt(stmt);
    this.visit(stmt.getIfBranch());
    final Consumer<ConditionalBranch> _function = (ConditionalBranch it) -> {
      this.visit(it);
    };
    stmt.getElseIfBranches().forEach(_function);
    this.visit(stmt.getElseBranch());
    this.outIfStmt(stmt);
  }

  public void visitLoopStmt(final LoopStmt stmt) {
    this.inLoopStmt(stmt);
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    stmt.getBody().forEach(_function);
    this.outLoopStmt(stmt);
  }

  public void visitAssignmentStmt(final AssignmentStmt stmt) {
    this.inAssignmentStmt(stmt);
    this.outAssignmentStmt(stmt);
  }

  public void visitReturnStmt(final ReturnStmt stmt) {
    this.inReturnStmt(stmt);
    this.outReturnStmt(stmt);
  }

  public void visitBranch(final Branch branch) {
    this.inBranch(branch);
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    branch.getBody().forEach(_function);
    this.outBranch(branch);
  }

  public void visitConditionalBranch(final ConditionalBranch branch) {
    this.inConditionalBranch(branch);
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    branch.getBody().forEach(_function);
    this.outConditionalBranch(branch);
  }

  public void inFunction(final Function func) {
  }

  public void inInclude(final Include incl) {
  }

  public void inVariableDecl(final VariableDecl decl) {
  }

  public void inStatement(final Statement statement) {
  }

  public void inEmptyLineStmt(final EmptyLineStmt stmt) {
  }

  public void inCommentStmt(final CommentStmt stmt) {
  }

  public void inExpressionStmt(final ExpressionStmt stmt) {
  }

  public void inMacroStmt(final MacroStmt stmt) {
  }

  public void inUndefStmt(final UndefStmt stmt) {
  }

  public void inIfStmt(final IfStmt stmt) {
  }

  public void inLoopStmt(final LoopStmt stmt) {
  }

  public void inAssignmentStmt(final AssignmentStmt stmt) {
  }

  public void inReturnStmt(final ReturnStmt stmt) {
  }

  public void inBranch(final Branch branch) {
  }

  public void inConditionalBranch(final ConditionalBranch branch) {
  }

  public void outFunction(final Function func) {
  }

  public void outInclude(final Include incl) {
  }

  public void outVariableDecl(final VariableDecl decl) {
  }

  public void outStatement(final Statement statement) {
  }

  public void outEmptyLineStmt(final EmptyLineStmt stmt) {
  }

  public void outCommentStmt(final CommentStmt stmt) {
  }

  public void outExpressionStmt(final ExpressionStmt stmt) {
  }

  public void outMacroStmt(final MacroStmt stmt) {
  }

  public void outUndefStmt(final UndefStmt stmt) {
  }

  public void outIfStmt(final IfStmt stmt) {
  }

  public void outLoopStmt(final LoopStmt stmt) {
  }

  public void outAssignmentStmt(final AssignmentStmt stmt) {
  }

  public void outReturnStmt(final ReturnStmt stmt) {
  }

  public void outBranch(final Branch branch) {
  }

  public void outConditionalBranch(final ConditionalBranch branch) {
  }

  public void visit(final EObject stmt) {
    if (stmt instanceof AssignmentStmt) {
      _visit((AssignmentStmt)stmt);
      return;
    } else if (stmt instanceof CommentStmt) {
      _visit((CommentStmt)stmt);
      return;
    } else if (stmt instanceof ConditionalBranch) {
      _visit((ConditionalBranch)stmt);
      return;
    } else if (stmt instanceof EmptyLineStmt) {
      _visit((EmptyLineStmt)stmt);
      return;
    } else if (stmt instanceof ExpressionStmt) {
      _visit((ExpressionStmt)stmt);
      return;
    } else if (stmt instanceof IfStmt) {
      _visit((IfStmt)stmt);
      return;
    } else if (stmt instanceof LoopStmt) {
      _visit((LoopStmt)stmt);
      return;
    } else if (stmt instanceof MacroStmt) {
      _visit((MacroStmt)stmt);
      return;
    } else if (stmt instanceof ReturnStmt) {
      _visit((ReturnStmt)stmt);
      return;
    } else if (stmt instanceof UndefStmt) {
      _visit((UndefStmt)stmt);
      return;
    } else if (stmt instanceof Branch) {
      _visit((Branch)stmt);
      return;
    } else if (stmt instanceof Function) {
      _visit((Function)stmt);
      return;
    } else if (stmt instanceof Include) {
      _visit((Include)stmt);
      return;
    } else if (stmt instanceof Program) {
      _visit((Program)stmt);
      return;
    } else if (stmt instanceof Statement) {
      _visit((Statement)stmt);
      return;
    } else if (stmt instanceof VariableDecl) {
      _visit((VariableDecl)stmt);
      return;
    } else if (stmt == null) {
      _visit((Void)null);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(stmt).toString());
    }
  }
}
