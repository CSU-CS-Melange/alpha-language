package alpha.model.util

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaCompleteVisitable
import alpha.model.AlphaConstant
import alpha.model.AlphaExpression
import alpha.model.AlphaExpressionVisitable
import alpha.model.AlphaPackage
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.AlphaSystemElement
import alpha.model.ArgReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryCalculatorExpression
import alpha.model.BinaryExpression
import alpha.model.BooleanExpression
import alpha.model.CaseExpression
import alpha.model.ConstantExpression
import alpha.model.ConvolutionExpression
import alpha.model.DefinedObject
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalFunction
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.FuzzyDependenceExpression
import alpha.model.IfExpression
import alpha.model.Imports
import alpha.model.IndexExpression
import alpha.model.IntegerExpression
import alpha.model.JNIDomain
import alpha.model.JNIDomainInArrayNotation
import alpha.model.JNIFunctionInArrayNotation
import alpha.model.MultiArgExpression
import alpha.model.PolynomialIndexExpression
import alpha.model.REDUCTION_OP
import alpha.model.RealExpression
import alpha.model.RectangularDomain
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.SelectExpression
import alpha.model.StandardEquation
import alpha.model.SystemBody
import alpha.model.UnaryCalculatorExpression
import alpha.model.UnaryExpression
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableDomain
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial
import fr.irisa.cairn.jnimap.isl.ISLSet

/**
 * Prints the Alpha program in Show notation. The show notation
 * is mostly context-free and each node can be printed on its own.
 * The only exception is when printing the domains, the parameter
 * domain of the enclosing system is used to simplify the constraints.
 * 
 * For now, Show will completely remove the calculator expression.
 */
class Show extends ModelSwitch<CharSequence> {
	
	protected ISLSet parameterContext = null;

	static def <T extends AlphaCompleteVisitable> print(T av) {
		val show = new Show();
		
		//when printing a sub-tree within a system, find the parameter domain before visiting
		if (av instanceof AlphaSystemElement || av instanceof AlphaExpressionVisitable) {
			show.parameterContext = AlphaUtil.getContainerSystem(av).parameterDomain
		}
		
		show.doSwitch(av).toString()
	}

	/*
	 * CalculatorExpressions are printed differently depending on the context.
	 */
	
	protected def printParameterDomain(JNIDomain dom) {
		AlphaPrintingUtil.toShowStringParameterDomain(dom.ISLSet)
	}
	protected def printSystemBodyDomain(JNIDomain dom) {
		AlphaPrintingUtil.toShowStringSystemBodyDomain(dom.ISLSet)
	}
	
	protected def printVariableDeclarationDomain(ISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printUECallParams(JNIFunctionInArrayNotation f) {
		f.plainToString
	}
	
	protected def printInstantiationDomain(ISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printWhileDomain(ISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printDomain(ISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printFunction(ISLMultiAff f) {
		AlphaPrintingUtil.toShowString(f)
	}
	
	protected def printRelation(ISLMap rel) {
		AlphaPrintingUtil.toShowString(rel)
	}
	
	protected def printPolynomial(ISLPWQPolynomial p) {
		AlphaPrintingUtil.toShowString(p)
	}
	
	protected def printSubsystemCallParams(JNIFunctionInArrayNotation f, ISLSet instantiationDomain) {
		val maff = f.ISLMultiAff
		'''«AlphaPrintingUtil.toAShowString(maff, instantiationDomain.indexNames)»'''
	}

	/* override */ def caseAlphaRoot(AlphaRoot root) {
		'''
			«root.imports.map[doSwitch].join("\n")»
			
			«root.elements.map[doSwitch].join("\n")»
		'''
	}

	/* override */ def caseAlphaConstant(AlphaConstant ac) {
		'''constant «ac.name» = «ac.value»'''
	}

	/* override */ def caseExternalFunction(ExternalFunction ef) {
		'''external «ef.name»(«ef.cardinality»)'''
	}

	/* override */ def caseImports(Imports i) {
		'''import «i.importedNamespace»'''
	}

	/* override */ def caseAlphaPackage(AlphaPackage ap) {
		'''
			package «ap.name» {
				«ap.elements.map[doSwitch].join("\n")»
			}
			
		'''
	}

	/* override */ def caseAlphaSystem(AlphaSystem s) {
		parameterContext = s.parameterDomain;
//	No define in Show syntax
//				«IF !s.definedObjects.isEmpty»
//					define
//						«s.definedObjects.map[doSwitch].join("\n")»
//				«ENDIF»
		'''
			affine «s.name» «s.parameterDomainExpr.printParameterDomain»
				«IF !s.inputs.isEmpty»
					inputs
						«s.inputs.map[doSwitch].join("\n")»
				«ENDIF»
				«IF !s.outputs.isEmpty»
					outputs
						«s.outputs.map[doSwitch].join("\n")»
				«ENDIF»
				«IF !s.locals.isEmpty»
					locals
						«s.locals.map[doSwitch].join("\n")»
				«ENDIF»
				«IF s.whileDomainExpr !== null»
					over «s.whileDomain.printInstantiationDomain» while («s.testExpression.doSwitch»)
				«ENDIF»
				«s.systemBodies.map[doSwitch].join("\n")»
			.
		'''
	}
	
	/* override */ def caseSystemBody(SystemBody sysBody) '''
		«IF !sysBody.equations.isEmpty»
			«IF sysBody.parameterDomainExpr !== null»when «sysBody.parameterDomainExpr.printSystemBodyDomain» «ENDIF»let
				«(sysBody.equations).map[doSwitch].join("\n\n")»
		«ENDIF»
	'''

	/* override */ def caseVariable(Variable v) {
		'''«v.name» : «v.domain.printVariableDeclarationDomain»'''
	}
		
	/* override */ def caseStandardEquation(StandardEquation se) {
		'''«se.variable.name» = «se.expr.doSwitch»;'''
	}
	
	/* override */ def caseUseEquation(UseEquation ue) {
		val idom = if (ue.instantiationDomainExpr !== null && ue.instantiationDomain.nbIndices > 0) 
			'''over «ue.instantiationDomain.printInstantiationDomain» : ''' else ''''''
		
		val callParam = ue.callParamsExpr.printSubsystemCallParams(ue.instantiationDomain)
		
		'''«idom»(«ue.outputExprs.map[doSwitch].join(", ")») = «ue.system.name»«callParam»(«ue.inputExprs.map[doSwitch].join(", ")»);'''
	}
	
	/* AlphaExpression */
	
	/* override */ def caseIfExpression(IfExpression ie) {
		'''if «ie.condExpr.doSwitch» then «ie.thenExpr.doSwitch» else «ie.elseExpr.doSwitch»'''
	}
	
	/* override */ def caseRestrictExpression(RestrictExpression re) {
		val domStr = if (re.domainExpr instanceof JNIDomain || re.domainExpr instanceof JNIDomainInArrayNotation)
		re.restrictDomain.printDomain else '''{«re.restrictDomain.printDomain»}''' 
		
		if (re.eContainer instanceof UnaryExpression || re.eContainer instanceof BinaryExpression) {
			'''(«domStr» : «re.expr.doSwitch»)'''
		} else {
			'''«domStr» : «re.expr.doSwitch»'''
		}
	}
	
	/* override */ def caseAutoRestrictExpression(AutoRestrictExpression are) {
		if (are.eContainer instanceof UnaryExpression || are.eContainer instanceof BinaryExpression) {
			'''(auto : «are.expr.doSwitch»)'''
		} else {
			'''auto : «are.expr.doSwitch»'''
		}
	}
	
	/* override */ def caseCaseExpression(CaseExpression ce) {
		'''
			case «IF ce.name !== null»«ce.name»«ENDIF» {
				«ce.exprs.join("", ";\n", ";", [e|e.doSwitch])»
			}'''
	}

	/* override */ def caseDependenceExpression(DependenceExpression de) {
		if (de.expr instanceof IfExpression || de.expr instanceof RestrictExpression || de.expr instanceof AutoRestrictExpression|| de.expr instanceof UnaryExpression || de.expr instanceof BinaryExpression) {
			'''«de.function.printFunction»@(«de.expr.doSwitch»)'''
		} else {
			'''«de.function.printFunction»@«de.expr.doSwitch»'''
		}
	}
	
	/* override */ def caseIndexExpression(IndexExpression ie) {
		'''val«ie.function.printFunction»'''
	}
	
	/* override */ def casePolynomialIndexExpression(PolynomialIndexExpression pie) {
		'''val«pie.polynomial.gist(pie.contextDomain).printPolynomial»'''
	}
	
	/* override */ def caseReduceExpression(ReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	/* override */ def caseExternalReduceExpression(ExternalReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	/* override */ def caseArgReduceExpression(ArgReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	/* override */ def caseExternalArgReduceExpression(ExternalArgReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	
	protected def dispatch printReduceExpression(ReduceExpression re, CharSequence proj, CharSequence body) {
		'''reduce(«re.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalReduceExpression ere, CharSequence proj, CharSequence body) {
		'''reduce(«ere.externalFunction.name», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ArgReduceExpression are, CharSequence proj, CharSequence body) {
		'''argreduce(«are.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalArgReduceExpression aere, CharSequence proj, CharSequence body) {
		'''argreduce(«aere.externalFunction.name», «proj», «body»)'''
	}
	
	protected def printProjectionFunction(ISLMultiAff maff) {
		AlphaPrintingUtil.toShowString(maff)
	}
	
	protected def printReductionBody(AlphaExpression expr) {
		doSwitch(expr)
	}
	protected def printReductionOP(REDUCTION_OP op) {
		switch (op) {
			case SUM: {
				return "+"
			}
			case DIFF: {
				return "--"
			}
			case PROD: {
				return "*"
			}
			default: {
				return op.literal	
			}
		}	
	}
	
	protected def String printAbstractReduceExpression(AbstractReduceExpression are) {
		val proj = are.projection.printProjectionFunction
		val body = are.body.printReductionBody
		
		are.printReduceExpression(proj, body).toString
	}
	
	/* override */ def caseConvolutionExpression(ConvolutionExpression ce) {
		'''conv(«ce.kernelDomain.printDomain», «ce.kernelExpression.doSwitch», «ce.dataExpression.doSwitch»)'''
	}
	
	/* override */ def caseSelectExpression(SelectExpression se) {
		'''select «se.selectRelation.printRelation» from «se.expr.doSwitch»'''
	}
	
	/* override */ def caseBinaryExpression(BinaryExpression be) {
		'''(«be.left.doSwitch» «be.operator» «be.right.doSwitch»)'''
	}
	
	/* override */ def caseMultiArgExpression(MultiArgExpression  mae) {
		'''«mae.operator»(«mae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	/* override */ def caseExternalMultiArgExpression(ExternalMultiArgExpression  emae) {
		'''«emae.externalFunction.name»(«emae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	/* override */ def caseUnaryExpression(UnaryExpression ue) {
		if ((ue.expr instanceof ConstantExpression) || 
			(ue.expr instanceof DependenceExpression) || 
			(ue.expr instanceof FuzzyDependenceExpression)
		)  {
			'''«ue.operator» («ue.expr.doSwitch»)'''
		} else {
			'''«ue.operator» «ue.expr.doSwitch»'''
		}
	}
	
	/* override */ def caseVariableExpression(VariableExpression ve) {
		ve.variable.name
	}
	
	/* override */ def caseBooleanExpression(BooleanExpression be) {
		be.value.toString
	}
	
	/* override */ def caseIntegerExpression(IntegerExpression ie) {
		ie.value.toString
	}
	
	/* override */ def caseRealExpression(RealExpression re) {
		re.value.toString
	}
	
	/* CalculatorExpression */
	/* override */ def caseBinaryCalculatorExpression(BinaryCalculatorExpression bce) {
		'''(«bce.left.doSwitch» «bce.operator» «bce.right.doSwitch»)'''
	}
	
	/* override */ def caseUnaryCalculatorExpression(UnaryCalculatorExpression uce) {
		'''«uce.operator» «uce.expr.doSwitch»)'''
	}
	
	/* override */ def caseVariableDomain(VariableDomain vd) {
		'''{ «vd.variable.name» }'''
	}
	
	/* override */ def caseRectangularDomain(RectangularDomain rd) {
		val inames = if (rd.indexNames !== null && rd.indexNames.length == rd.upperBounds.length)
		'''as [«rd.indexNames.join(",")»]''' else '''''' 
		'''[«rd.upperBounds.join(",")»] «inames»'''
	}
	
	/* override */ def caseDefinedObject(DefinedObject dobj) {
		dobj.object.name
	}
	
}
