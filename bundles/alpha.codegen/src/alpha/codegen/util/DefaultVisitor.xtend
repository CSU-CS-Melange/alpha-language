package alpha.codegen.util

import alpha.codegen.ArrayVariable
import alpha.codegen.BaseVariable
import alpha.codegen.EvalFunction
import alpha.codegen.Function
import alpha.codegen.FunctionBody
import alpha.codegen.GlobalMacro
import alpha.codegen.GlobalMemoryMacro
import alpha.codegen.GlobalVariable
import alpha.codegen.Include
import alpha.codegen.Macro
import alpha.codegen.MemoryAllocation
import alpha.codegen.Program
import alpha.codegen.StatementMacro
import alpha.codegen.Visitable
import alpha.codegen.Visitor

interface DefaultVisitor extends Visitor {
	
	def defaultIn(Visitable v) {}
	def defaultOut(Visitable v) {}
	
	override visitProgram(Program p) {
		inProgram(p)
		p.includes.forEach[include | include.accept(this)]
		p.commonMacros.forEach[cm | cm.accept(this)]
		p.globalVariables.forEach[gv | gv.accept(this)]
		p.functions.forEach(f | f.accept(this))
		outProgram(p)
	}
	
	override visitInclude(Include i) {
		inInclude(i)
		outInclude(i)
	}
	
	override visitMacro(Macro m) {
		inMacro(m)
		outMacro(m)
	}
	
	override visitGlobalMacro(GlobalMacro gm) {
		inGlobalMacro(gm)
		outGlobalMacro(gm)
	}
		
	override visitGlobalMemoryMacro(GlobalMemoryMacro gmm) {
		inGlobalMemoryMacro(gmm)
		outGlobalMemoryMacro(gmm)
	}
	
	override visitStatementMacro(StatementMacro sm) {
		inStatementMacro(sm)
		outStatementMacro(sm)
	}
	
	override visitBaseVariable(BaseVariable v) {
		inBaseVariable(v)
		outBaseVariable(v)
	}
	
	override visitArrayVariable(ArrayVariable av) {
		inArrayVariable(av)
		outArrayVariable(av)
	}
		
	override visitFunction(Function f) {
		inFunction(f)
		f.args.forEach[v | v.accept(this)]
		f.body.accept(this)
		outFunction(f)
	}
	
	override visitEvalFunction(EvalFunction ef) {
		inEvalFunction(ef)
		ef.args.forEach[v | v.accept(this)]
		ef.body.accept(this)
		outEvalFunction(ef)
	}
	
	override visitMemoryAllocation(MemoryAllocation ma) {
		inMemoryAllocation(ma)
		outMemoryAllocation(ma)
	}
	
	override visitFunctionBody(FunctionBody cs) {
		inFunctionBody(cs)
		outFunctionBody(cs)
	}
	
	override visitGlobalVariable(GlobalVariable cgv) {
		inGlobalVariable(cgv)
		cgv.memoryMacro.accept(this)
		outGlobalVariable(cgv)
	}
	
	
	override inProgram(Program p) {
		defaultIn(p)
	}
	
	override inInclude(Include i) {
		defaultIn(i)
	}
	
	override inMacro(Macro m) {
		defaultIn(m)
	}
	
	override inGlobalMacro(GlobalMacro m) {
		defaultIn(m)
	}
	
	override inGlobalMemoryMacro(GlobalMemoryMacro m) {
		defaultIn(m)
	}
	
	override inStatementMacro(StatementMacro m) {
		defaultIn(m)
	}
	
	override inBaseVariable(BaseVariable v) {
		defaultIn(v)
	}
	
	override inArrayVariable(ArrayVariable v) {
		defaultIn(v)
	}
	
	override inFunction(Function f) {
		defaultIn(f)
	}
	
	override inMemoryAllocation(MemoryAllocation ma) {
		defaultIn(ma)
	}
	
	override inFunctionBody(FunctionBody cs) {
		defaultIn(cs)
	}
	
	override inEvalFunction(EvalFunction ef) {
		defaultIn(ef)
	}
	
	override inGlobalVariable(GlobalVariable cgv) {
		defaultIn(cgv)
	}
	
	
	override outProgram(Program p) {
		defaultOut(p)
	}
	
	override outInclude(Include i) {
		defaultOut(i)
	}
	
	override outMacro(Macro m) {
		defaultOut(m)
	}
	
	override outGlobalMacro(GlobalMacro m) {
		defaultIn(m)
	}
	
	override outGlobalMemoryMacro(GlobalMemoryMacro m) {
		defaultIn(m)
	}
	
	override outStatementMacro(StatementMacro m) {
		defaultIn(m)
	}
	
	override outBaseVariable(BaseVariable v) {
		defaultOut(v)
	}
	
	override outArrayVariable(ArrayVariable v) {
		defaultOut(v)
	}
	
	override outFunction(Function f) {
		defaultOut(f)
	}
	
	override outMemoryAllocation(MemoryAllocation ma) {
		defaultOut(ma)
	}
	
	override outFunctionBody(FunctionBody cs) {
		defaultOut(cs)
	}
	
	override outEvalFunction(EvalFunction ef) {
		defaultOut(ef)
	}
	
	override outGlobalVariable(GlobalVariable cgv) {
		defaultOut(cgv)
	}
		
}