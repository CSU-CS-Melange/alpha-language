/**
 */
package alpha.codegen.util;

import alpha.codegen.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenPackage
 * @generated
 */
public class CodegenSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodegenPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenSwitch() {
		if (modelPackage == null) {
			modelPackage = CodegenPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CodegenPackage.NODE: {
				Node node = (Node)theEObject;
				T result = caseNode(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.VISITABLE: {
				Visitable visitable = (Visitable)theEObject;
				T result = caseVisitable(visitable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.VISITOR: {
				Visitor visitor = (Visitor)theEObject;
				T result = caseVisitor(visitor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.INCLUDE: {
				Include include = (Include)theEObject;
				T result = caseInclude(include);
				if (result == null) result = caseNode(include);
				if (result == null) result = caseVisitable(include);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.MACRO: {
				Macro macro = (Macro)theEObject;
				T result = caseMacro(macro);
				if (result == null) result = caseNode(macro);
				if (result == null) result = caseVisitable(macro);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.GLOBAL_MACRO: {
				GlobalMacro globalMacro = (GlobalMacro)theEObject;
				T result = caseGlobalMacro(globalMacro);
				if (result == null) result = caseMacro(globalMacro);
				if (result == null) result = caseNode(globalMacro);
				if (result == null) result = caseVisitable(globalMacro);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.ABSTRACT_MEMORY_MACRO: {
				AbstractMemoryMacro abstractMemoryMacro = (AbstractMemoryMacro)theEObject;
				T result = caseAbstractMemoryMacro(abstractMemoryMacro);
				if (result == null) result = caseMacro(abstractMemoryMacro);
				if (result == null) result = caseNode(abstractMemoryMacro);
				if (result == null) result = caseVisitable(abstractMemoryMacro);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.MEMORY_ALLOCATION: {
				MemoryAllocation memoryAllocation = (MemoryAllocation)theEObject;
				T result = caseMemoryAllocation(memoryAllocation);
				if (result == null) result = caseNode(memoryAllocation);
				if (result == null) result = caseVisitable(memoryAllocation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.GLOBAL_MEMORY_MACRO: {
				GlobalMemoryMacro globalMemoryMacro = (GlobalMemoryMacro)theEObject;
				T result = caseGlobalMemoryMacro(globalMemoryMacro);
				if (result == null) result = caseAbstractMemoryMacro(globalMemoryMacro);
				if (result == null) result = caseMacro(globalMemoryMacro);
				if (result == null) result = caseNode(globalMemoryMacro);
				if (result == null) result = caseVisitable(globalMemoryMacro);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.STATEMENT_MACRO: {
				StatementMacro statementMacro = (StatementMacro)theEObject;
				T result = caseStatementMacro(statementMacro);
				if (result == null) result = caseMacro(statementMacro);
				if (result == null) result = caseNode(statementMacro);
				if (result == null) result = caseVisitable(statementMacro);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.BASE_VARIABLE: {
				BaseVariable baseVariable = (BaseVariable)theEObject;
				T result = caseBaseVariable(baseVariable);
				if (result == null) result = caseNode(baseVariable);
				if (result == null) result = caseVisitable(baseVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.GLOBAL_VARIABLE: {
				GlobalVariable globalVariable = (GlobalVariable)theEObject;
				T result = caseGlobalVariable(globalVariable);
				if (result == null) result = caseBaseVariable(globalVariable);
				if (result == null) result = caseNode(globalVariable);
				if (result == null) result = caseVisitable(globalVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.ARRAY_VARIABLE: {
				ArrayVariable arrayVariable = (ArrayVariable)theEObject;
				T result = caseArrayVariable(arrayVariable);
				if (result == null) result = caseGlobalVariable(arrayVariable);
				if (result == null) result = caseBaseVariable(arrayVariable);
				if (result == null) result = caseNode(arrayVariable);
				if (result == null) result = caseVisitable(arrayVariable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.FUNCTION_BODY: {
				FunctionBody functionBody = (FunctionBody)theEObject;
				T result = caseFunctionBody(functionBody);
				if (result == null) result = caseNode(functionBody);
				if (result == null) result = caseVisitable(functionBody);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseNode(function);
				if (result == null) result = caseVisitable(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.EVAL_FUNCTION: {
				EvalFunction evalFunction = (EvalFunction)theEObject;
				T result = caseEvalFunction(evalFunction);
				if (result == null) result = caseFunction(evalFunction);
				if (result == null) result = caseNode(evalFunction);
				if (result == null) result = caseVisitable(evalFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.REDUCE_FUNCTION: {
				ReduceFunction reduceFunction = (ReduceFunction)theEObject;
				T result = caseReduceFunction(reduceFunction);
				if (result == null) result = caseFunction(reduceFunction);
				if (result == null) result = caseNode(reduceFunction);
				if (result == null) result = caseVisitable(reduceFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.PROGRAM: {
				Program program = (Program)theEObject;
				T result = caseProgram(program);
				if (result == null) result = caseNode(program);
				if (result == null) result = caseVisitable(program);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.ALPHA_OP: {
				AlphaOp alphaOp = (AlphaOp)theEObject;
				T result = caseAlphaOp(alphaOp);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CodegenPackage.POLYNOMIAL: {
				Polynomial polynomial = (Polynomial)theEObject;
				T result = casePolynomial(polynomial);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visitable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visitable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisitable(Visitable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Visitor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Visitor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisitor(Visitor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Include</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Include</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInclude(Include object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacro(Macro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Macro</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalMacro(GlobalMacro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Memory Macro</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Memory Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractMemoryMacro(AbstractMemoryMacro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Memory Allocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Memory Allocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemoryAllocation(MemoryAllocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Memory Macro</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Memory Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalMemoryMacro(GlobalMemoryMacro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement Macro</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatementMacro(StatementMacro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseVariable(BaseVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalVariable(GlobalVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Array Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Array Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayVariable(ArrayVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionBody(FunctionBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Eval Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Eval Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEvalFunction(EvalFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reduce Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reduce Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReduceFunction(ReduceFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Program</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Program</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProgram(Program object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Alpha Op</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Alpha Op</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAlphaOp(AlphaOp object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Polynomial</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Polynomial</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePolynomial(Polynomial object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //CodegenSwitch
