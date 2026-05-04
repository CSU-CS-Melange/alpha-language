package alpha.codegen.postprocessing;

import alpha.codegen.Branch;
import alpha.codegen.ConditionalBranch;
import alpha.codegen.CustomExpr;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.LoopStmt;
import alpha.codegen.Statement;
import alpha.codegen.isl.ASTConversionResult;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class ForLoopNester extends CExpressionVisitor {
  private ASTConversionResult innerNest;

  private String toReplace;

  public ForLoopNester(final ASTConversionResult innerNest, final String toReplace) {
    this.innerNest = innerNest;
    this.toReplace = toReplace;
  }

  public static void apply(final ASTConversionResult outerNest, final ASTConversionResult innerNest, final String toReplace) {
    final ForLoopNester nester = new ForLoopNester(innerNest, toReplace);
    nester.visitLoopNest(outerNest);
  }

  protected void visitLoopNest(final ASTConversionResult loopNest) {
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    loopNest.getStatements().forEach(_function);
  }

  @Override
  public void outBranch(final Branch branch) {
    final Function1<Statement, Iterable<Statement>> _function = (Statement it) -> {
      return this.replaceStatement(it);
    };
    final Iterable<Statement> newStatements = IterableExtensions.<Statement, Statement>flatMap(branch.getBody(), _function);
    branch.getBody().clear();
    Iterables.<Statement>addAll(branch.getBody(), newStatements);
  }

  @Override
  public void outConditionalBranch(final ConditionalBranch branch) {
    final Function1<Statement, Iterable<Statement>> _function = (Statement it) -> {
      return this.replaceStatement(it);
    };
    final Iterable<Statement> newStatements = IterableExtensions.<Statement, Statement>flatMap(branch.getBody(), _function);
    branch.getBody().clear();
    Iterables.<Statement>addAll(branch.getBody(), newStatements);
  }

  @Override
  public void outLoopStmt(final LoopStmt stmt) {
    final Function1<Statement, Iterable<Statement>> _function = (Statement it) -> {
      return this.replaceStatement(it);
    };
    final List<Statement> newStatements = IterableExtensions.<Statement>toList(IterableExtensions.<Statement, Statement>flatMap(stmt.getBody(), _function));
    stmt.getBody().clear();
    stmt.getBody().addAll(newStatements);
  }

  protected Iterable<Statement> replaceStatement(final Statement stmt) {
    if ((stmt instanceof ExpressionStmt)) {
      Expression _expression = ((ExpressionStmt)stmt).getExpression();
      if ((_expression instanceof CustomExpr)) {
        Expression _expression_1 = ((ExpressionStmt)stmt).getExpression();
        final String s = ((CustomExpr) _expression_1).getExpression();
        final String functionName = s.substring(0, s.indexOf("("));
        boolean _equals = Objects.equal(functionName, this.toReplace);
        if (_equals) {
          final Function1<Statement, Statement> _function = (Statement it) -> {
            return EcoreUtil.<Statement>copy(it);
          };
          return ListExtensions.<Statement, Statement>map(this.innerNest.getStatements(), _function);
        }
      }
    }
    return Collections.<Statement>unmodifiableList(CollectionLiterals.<Statement>newArrayList(stmt));
  }
}
