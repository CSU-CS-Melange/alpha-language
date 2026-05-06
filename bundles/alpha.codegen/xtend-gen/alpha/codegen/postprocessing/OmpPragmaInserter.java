package alpha.codegen.postprocessing;

import alpha.codegen.ConditionalBranch;
import alpha.codegen.Factory;
import alpha.codegen.LoopStmt;
import alpha.codegen.Statement;
import alpha.codegen.isl.ASTConversionResult;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class OmpPragmaInserter extends CExpressionVisitor {
  private int timeDim;

  private int loopDepth;

  private Set<String> publicVars;

  private Set<String> loopVars;

  public OmpPragmaInserter(final int timeDim) {
    this.timeDim = timeDim;
    this.loopDepth = 0;
    HashSet<String> _hashSet = new HashSet<String>();
    this.publicVars = _hashSet;
    HashSet<String> _hashSet_1 = new HashSet<String>();
    this.loopVars = _hashSet_1;
  }

  public static void apply(final ASTConversionResult loopNest, final int timeDim) {
    final OmpPragmaInserter inserter = new OmpPragmaInserter(timeDim);
    inserter.visitLoopNest(loopNest);
  }

  protected void visitLoopNest(final ASTConversionResult loopNest) {
    ArrayList<String> _declarations = loopNest.getDeclarations();
    Iterables.<String>addAll(this.loopVars, _declarations);
    if ((this.timeDim == 0)) {
      loopNest.getStatements().add(0, this.buildMacro(this.loopVars));
    }
    final Consumer<Statement> _function = (Statement it) -> {
      this.visit(it);
    };
    loopNest.getStatements().forEach(_function);
  }

  @Override
  public void inConditionalBranch(final ConditionalBranch stmt) {
    if ((this.loopDepth == this.timeDim)) {
      this.appendPragmas(stmt.getBody());
    }
  }

  @Override
  public void inLoopStmt(final LoopStmt stmt) {
    this.loopDepth++;
    this.publicVars.add(stmt.getLoopVariable());
    if ((this.loopDepth == this.timeDim)) {
      this.appendPragmas(stmt.getBody());
    }
  }

  @Override
  public void outLoopStmt(final LoopStmt stmt) {
    this.loopDepth--;
    this.publicVars.remove(stmt.getLoopVariable());
  }

  protected void appendPragmas(final EList<Statement> stmts) {
    final ArrayList<Statement> newStatements = new ArrayList<Statement>();
    for (final Statement statement : stmts) {
      {
        if ((statement instanceof LoopStmt)) {
          Statement _buildMacro = this.buildMacro(this.getPrivateVars());
          newStatements.add(_buildMacro);
        }
        newStatements.add(statement);
      }
    }
    stmts.clear();
    stmts.addAll(newStatements);
  }

  protected Statement buildMacro(final Iterable<String> privateVars) {
    return Factory.ompStmt(((String[])Conversions.unwrapArray(privateVars, String.class)));
  }

  protected Iterable<String> getPrivateVars() {
    final Function1<String, Boolean> _function = (String it) -> {
      return Boolean.valueOf(this.publicVars.contains(it));
    };
    return IterableExtensions.<String>reject(this.loopVars, _function);
  }
}
