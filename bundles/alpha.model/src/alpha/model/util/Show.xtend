package alpha.model.util

import alpha.model.AbstractReduceExpression
import alpha.model.AlphaConstant
import alpha.model.AlphaExpression
import alpha.model.AlphaPackage
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.AlphaVisitable
import alpha.model.ArgReduceExpression
import alpha.model.AutoRestrictExpression
import alpha.model.BinaryCalculatorExpression
import alpha.model.BinaryExpression
import alpha.model.BooleanExpression
import alpha.model.CalculatorExpression
import alpha.model.CaseExpression
import alpha.model.ConvolutionExpression
import alpha.model.DefinedObject
import alpha.model.DependenceExpression
import alpha.model.ExternalArgReduceExpression
import alpha.model.ExternalFunction
import alpha.model.ExternalMultiArgExpression
import alpha.model.ExternalReduceExpression
import alpha.model.IfExpression
import alpha.model.Imports
import alpha.model.IndexExpression
import alpha.model.IntegerExpression
import alpha.model.JNIDomain
import alpha.model.JNIDomainInArrayNotation
import alpha.model.JNIFunctionInArrayNotation
import alpha.model.MultiArgExpression
import alpha.model.REDUCTION_OP
import alpha.model.RealExpression
import alpha.model.RectangularDomain
import alpha.model.ReduceExpression
import alpha.model.RestrictExpression
import alpha.model.SelectExpression
import alpha.model.StandardEquation
import alpha.model.UnaryCalculatorExpression
import alpha.model.UnaryExpression
import alpha.model.UseEquation
import alpha.model.Variable
import alpha.model.VariableDomain
import alpha.model.VariableExpression
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMultiAff
import fr.irisa.cairn.jnimap.isl.jni.JNIISLSet
import fr.irisa.cairn.jnimap.isl.jni.JNIISLDimType
import alpha.model.ConstantExpression
import fr.irisa.cairn.jnimap.isl.jni.JNIISLMap

/**
 * Prints the Alpha program in Show notation. The show notation
 * is mostly context-free and each node can be printed on its own.
 * The only exception is when printing the domains, the parameter
 * domain of the enclosing system is used to simplify the constraints.
 * 
 * For now, Show will completely remove the calculator expression.
 */
class Show extends ModelSwitch<String> {
	
	protected JNIISLSet parameterContext = null;

	static def <T extends AlphaVisitable> print(T av) {
		val show = new Show();
		show.doSwitch(av)
	}

	/*
	 * CalculatorExpressions are printed differently depending on the context.
	 */
	
	protected def printParameterDomain(JNIDomain dom) {
		dom.islString
	}
	
	protected def printVariableDeclarationDomain(JNIISLSet set) {
		if (set.getNbDims(JNIISLDimType.isl_dim_set) == 0)
			""
		else
			AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printUECallParams(JNIFunctionInArrayNotation f) {
		f.alphaString
	}
	
	protected def printInstantiationDomain(JNIISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printWhileDomain(JNIISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printDomain(JNIISLSet set) {
		AlphaPrintingUtil.toShowString(set, parameterContext)
	}
	
	protected def printFunction(JNIISLMultiAff f) {
		AlphaPrintingUtil.toShowString(f)
	}
	
	protected def printRelation(JNIISLMap rel) {
		AlphaPrintingUtil.toShowString(rel)
	}
	
	protected def printSubsystemCallParams(JNIFunctionInArrayNotation f, JNIISLSet instantiationDomain) {
		val maff = f.ISLMultiAff
		'''«AlphaPrintingUtil.toAShowString(maff, instantiationDomain.indicesNames)»'''
	}

	override caseAlphaRoot(AlphaRoot root) {
		root.elements.map[doSwitch].join("\n");
	}

	override caseAlphaConstant(AlphaConstant ac) {
		'''constant «ac.name» = «ac.value»'''
	}

	override caseExternalFunction(ExternalFunction ef) {
		'''external «ef.name»(«ef.cardinality»)'''
	}

	override caseImports(Imports i) {
		'''import «i.importedNamespace»'''
	}

	override caseAlphaPackage(AlphaPackage ap) {
		'''
			package «ap.name» {
				«ap.elements.map[doSwitch].join("\n")»
			}
			
		'''
	}

	override caseAlphaSystem(AlphaSystem s) {
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
				«IF !s.useEquations.isEmpty || !s.equations.isEmpty»
					let
						«(s.useEquations + s.equations).map[doSwitch].join("\n\n")»
				«ENDIF»
			.
		'''
	}

	override caseVariable(Variable v) {
		'''«v.name» : «v.domain.printVariableDeclarationDomain»'''
	}
		
	override caseStandardEquation(StandardEquation se) {
		'''«se.variable.name» = «se.expr.doSwitch»;'''
	}
	
	override caseUseEquation(UseEquation ue) {
		val idom = if (ue.instantiationDomainExpr !== null && ue.instantiationDomain.nbDims > 0) 
			'''over «ue.instantiationDomain.printInstantiationDomain» : ''' else ''''''
		
		val callParam = ue.callParamsExpr.printSubsystemCallParams(ue.instantiationDomain)
		
		'''«idom»(«ue.outputExprs.map[doSwitch].join(", ")») = «ue.system.name»«callParam»(«ue.inputExprs.map[doSwitch].join(", ")»);'''
	}
	
	/* AlphaExpression */
	
	override caseIfExpression(IfExpression ie) {
		'''if «ie.condExpr.doSwitch» then «ie.thenExpr.doSwitch» else «ie.elseExpr.doSwitch»'''
	}
	
	override caseRestrictExpression(RestrictExpression re) {
		val domStr = if (re.domainExpr instanceof JNIDomain || re.domainExpr instanceof JNIDomainInArrayNotation)
		re.restrictDomain.printDomain else '''{«re.restrictDomain.printDomain»}''' 
		
		if (re.eContainer instanceof UnaryExpression || re.eContainer instanceof BinaryExpression) {
			'''(«domStr» : «re.expr.doSwitch»)'''
		} else {
			'''«domStr» : «re.expr.doSwitch»'''
		}
	}
	
	override caseAutoRestrictExpression(AutoRestrictExpression are) {
		if (are.eContainer instanceof UnaryExpression || are.eContainer instanceof BinaryExpression) {
			'''(auto : «are.expr.doSwitch»)'''
		} else {
			'''auto : «are.expr.doSwitch»'''
		}
	}
	
	override caseCaseExpression(CaseExpression ce) {
		'''
			case «IF ce.name !== null»«ce.name»«ENDIF» {
				«ce.exprs.join("", ";\n", ";", [e|e.doSwitch])»
			}'''
	}

	override caseDependenceExpression(DependenceExpression de) {
		if (de.expr instanceof IfExpression || de.expr instanceof RestrictExpression || de.expr instanceof AutoRestrictExpression|| de.expr instanceof UnaryExpression || de.expr instanceof BinaryExpression) {
			'''«de.function.printFunction»@(«de.expr.doSwitch»)'''
		} else {
			'''«de.function.printFunction»@«de.expr.doSwitch»'''
		}
	}
	
	override caseIndexExpression(IndexExpression ie) {
		'''val «ie.function.printFunction»'''
	}
	
	override caseReduceExpression(ReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	override caseExternalReduceExpression(ExternalReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	override caseArgReduceExpression(ArgReduceExpression re) {
		re.printAbstractReduceExpression
	}
	
	override caseExternalArgReduceExpression(ExternalArgReduceExpression ere) {
		ere.printAbstractReduceExpression
	}
	
	
	protected def dispatch printReduceExpression(ReduceExpression re, String proj, String body) {
		'''reduce(«re.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalReduceExpression ere, String proj, String body) {
		'''reduce(«ere.externalFunction.name», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ArgReduceExpression are, String proj, String body) {
		'''argreduce(«are.operator.printReductionOP», «proj», «body»)'''
	}
	protected def dispatch printReduceExpression(ExternalArgReduceExpression aere, String proj, String body) {
		'''argreduce(«aere.externalFunction.name», «proj», «body»)'''
	}
	
	protected def printProjectionFunction(JNIISLMultiAff maff) {
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
	
	override caseConvolutionExpression(ConvolutionExpression ce) {
		'''conv(«ce.kernelDomain.printDomain», «ce.kernelExpression.doSwitch», «ce.dataExpression.doSwitch»)'''
	}
	
	override caseSelectExpression(SelectExpression se) {
		'''select «se.selectRelation.printRelation» from «se.expr.doSwitch»'''
	}
	
	override caseBinaryExpression(BinaryExpression be) {
		'''(«be.left.doSwitch» «be.operator» «be.right.doSwitch»)'''
	}
	
	override caseMultiArgExpression(MultiArgExpression  mae) {
		'''«mae.operator»(«mae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	override caseExternalMultiArgExpression(ExternalMultiArgExpression  emae) {
		'''«emae.externalFunction.name»(«emae.exprs.map[doSwitch].join(", ")»)'''
	}
	
	override caseUnaryExpression(UnaryExpression ue) {
		'''«ue.operator» «ue.expr.doSwitch»'''
	}
	
	override caseVariableExpression(VariableExpression ve) {
		ve.variable.name
	}
	
	override caseBooleanExpression(BooleanExpression be) {
		be.value.toString
	}
	
	override caseIntegerExpression(IntegerExpression ie) {
		ie.value.toString
	}
	
	override caseRealExpression(RealExpression re) {
		re.value.toString
	}
	
	/* CalculatorExpression */
	override caseBinaryCalculatorExpression(BinaryCalculatorExpression bce) {
		'''(«bce.left.doSwitch» «bce.operator» «bce.right.doSwitch»)'''
	}
	
	override caseUnaryCalculatorExpression(UnaryCalculatorExpression uce) {
		'''«uce.operator» «uce.expr.doSwitch»)'''
	}
	
	override caseVariableDomain(VariableDomain vd) {
		'''{ «vd.variable.name» }'''
	}
	
	override caseRectangularDomain(RectangularDomain rd) {
		val inames = if (rd.indexNames !== null && rd.indexNames.length == rd.upperBounds.length)
		'''as [«rd.indexNames.join(",")»]''' else '''''' 
		'''[«rd.upperBounds.join(",")»] «inames»'''
	}
	
	override caseDefinedObject(DefinedObject dobj) {
		dobj.object.name
	}
	
}