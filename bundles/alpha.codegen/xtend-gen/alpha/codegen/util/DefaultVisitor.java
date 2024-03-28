package alpha.codegen.util;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.EvalFunction;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.GlobalMacro;
import alpha.codegen.GlobalMemoryMacro;
import alpha.codegen.GlobalVariable;
import alpha.codegen.Include;
import alpha.codegen.Macro;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Program;
import alpha.codegen.StatementMacro;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;
import java.util.function.Consumer;

@SuppressWarnings("all")
public interface DefaultVisitor extends Visitor {
  default Object defaultIn(final Visitable v) {
    return null;
  }

  default Object defaultOut(final Visitable v) {
    return null;
  }

  @Override
  default void visitProgram(final Program p) {
    this.inProgram(p);
    final Consumer<Include> _function = (Include include) -> {
      include.accept(this);
    };
    p.getIncludes().forEach(_function);
    final Consumer<GlobalMacro> _function_1 = (GlobalMacro cm) -> {
      cm.accept(this);
    };
    p.getCommonMacros().forEach(_function_1);
    final Consumer<GlobalVariable> _function_2 = (GlobalVariable gv) -> {
      gv.accept(this);
    };
    p.getGlobalVariables().forEach(_function_2);
    final Consumer<Function> _function_3 = (Function f) -> {
      f.accept(this);
    };
    p.getFunctions().forEach(_function_3);
    this.outProgram(p);
  }

  @Override
  default void visitInclude(final Include i) {
    this.inInclude(i);
    this.outInclude(i);
  }

  @Override
  default void visitMacro(final Macro m) {
    this.inMacro(m);
    this.outMacro(m);
  }

  @Override
  default void visitGlobalMacro(final GlobalMacro gm) {
    this.inGlobalMacro(gm);
    this.outGlobalMacro(gm);
  }

  @Override
  default void visitGlobalMemoryMacro(final GlobalMemoryMacro gmm) {
    this.inGlobalMemoryMacro(gmm);
    this.outGlobalMemoryMacro(gmm);
  }

  @Override
  default void visitMemoryMacro(final MemoryMacro mm) {
    this.inMemoryMacro(mm);
    this.outMemoryMacro(mm);
  }

  @Override
  default void visitStatementMacro(final StatementMacro sm) {
    this.inStatementMacro(sm);
    this.outStatementMacro(sm);
  }

  @Override
  default void visitBaseVariable(final BaseVariable v) {
    this.inBaseVariable(v);
    this.outBaseVariable(v);
  }

  @Override
  default void visitArrayVariable(final ArrayVariable av) {
    this.inArrayVariable(av);
    this.outArrayVariable(av);
  }

  @Override
  default void visitFunction(final Function f) {
    this.inFunction(f);
    final Consumer<BaseVariable> _function = (BaseVariable v) -> {
      v.accept(this);
    };
    f.args().forEach(_function);
    final Consumer<MemoryMacro> _function_1 = (MemoryMacro mm) -> {
      mm.accept(this);
    };
    f.getMemoryMacros().forEach(_function_1);
    f.getBody().accept(this);
    this.outFunction(f);
  }

  @Override
  default void visitEvalFunction(final EvalFunction ef) {
    this.inEvalFunction(ef);
    final Consumer<BaseVariable> _function = (BaseVariable v) -> {
      v.accept(this);
    };
    ef.args().forEach(_function);
    final Consumer<MemoryMacro> _function_1 = (MemoryMacro mm) -> {
      mm.accept(this);
    };
    ef.getMemoryMacros().forEach(_function_1);
    ef.getBody().accept(this);
    this.outEvalFunction(ef);
  }

  @Override
  default void visitMemoryAllocation(final MemoryAllocation ma) {
    this.inMemoryAllocation(ma);
    this.outMemoryAllocation(ma);
  }

  @Override
  default void visitFunctionBody(final FunctionBody cs) {
    this.inFunctionBody(cs);
    this.outFunctionBody(cs);
  }

  @Override
  default void visitGlobalVariable(final GlobalVariable cgv) {
    this.inGlobalVariable(cgv);
    cgv.getMemoryMacro().accept(this);
    this.outGlobalVariable(cgv);
  }

  @Override
  default void inProgram(final Program p) {
    this.defaultIn(p);
  }

  @Override
  default void inInclude(final Include i) {
    this.defaultIn(i);
  }

  @Override
  default void inMacro(final Macro m) {
    this.defaultIn(m);
  }

  @Override
  default void inGlobalMacro(final GlobalMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void inGlobalMemoryMacro(final GlobalMemoryMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void inMemoryMacro(final MemoryMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void inStatementMacro(final StatementMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void inBaseVariable(final BaseVariable v) {
    this.defaultIn(v);
  }

  @Override
  default void inArrayVariable(final ArrayVariable v) {
    this.defaultIn(v);
  }

  @Override
  default void inFunction(final Function f) {
    this.defaultIn(f);
  }

  @Override
  default void inMemoryAllocation(final MemoryAllocation ma) {
    this.defaultIn(ma);
  }

  @Override
  default void inFunctionBody(final FunctionBody cs) {
    this.defaultIn(cs);
  }

  @Override
  default void inEvalFunction(final EvalFunction ef) {
    this.defaultIn(ef);
  }

  @Override
  default void inGlobalVariable(final GlobalVariable cgv) {
    this.defaultIn(cgv);
  }

  @Override
  default void outProgram(final Program p) {
    this.defaultOut(p);
  }

  @Override
  default void outInclude(final Include i) {
    this.defaultOut(i);
  }

  @Override
  default void outMacro(final Macro m) {
    this.defaultOut(m);
  }

  @Override
  default void outGlobalMacro(final GlobalMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void outGlobalMemoryMacro(final GlobalMemoryMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void outMemoryMacro(final MemoryMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void outStatementMacro(final StatementMacro m) {
    this.defaultIn(m);
  }

  @Override
  default void outBaseVariable(final BaseVariable v) {
    this.defaultOut(v);
  }

  @Override
  default void outArrayVariable(final ArrayVariable v) {
    this.defaultOut(v);
  }

  @Override
  default void outFunction(final Function f) {
    this.defaultOut(f);
  }

  @Override
  default void outMemoryAllocation(final MemoryAllocation ma) {
    this.defaultOut(ma);
  }

  @Override
  default void outFunctionBody(final FunctionBody cs) {
    this.defaultOut(cs);
  }

  @Override
  default void outEvalFunction(final EvalFunction ef) {
    this.defaultOut(ef);
  }

  @Override
  default void outGlobalVariable(final GlobalVariable cgv) {
    this.defaultOut(cgv);
  }
}
