/**
 */
package alpha.codegen.util;

import alpha.codegen.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see alpha.codegen.CodegenPackage
 * @generated
 */
public class CodegenAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CodegenPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodegenAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CodegenPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CodegenSwitch<Adapter> modelSwitch =
		new CodegenSwitch<Adapter>() {
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter caseVisitable(Visitable object) {
				return createVisitableAdapter();
			}
			@Override
			public Adapter caseVisitor(Visitor object) {
				return createVisitorAdapter();
			}
			@Override
			public Adapter caseInclude(Include object) {
				return createIncludeAdapter();
			}
			@Override
			public Adapter caseMacro(Macro object) {
				return createMacroAdapter();
			}
			@Override
			public Adapter caseGlobalMacro(GlobalMacro object) {
				return createGlobalMacroAdapter();
			}
			@Override
			public Adapter caseAbstractMemoryMacro(AbstractMemoryMacro object) {
				return createAbstractMemoryMacroAdapter();
			}
			@Override
			public Adapter caseMemoryMacro(MemoryMacro object) {
				return createMemoryMacroAdapter();
			}
			@Override
			public Adapter caseMemoryAllocation(MemoryAllocation object) {
				return createMemoryAllocationAdapter();
			}
			@Override
			public Adapter caseGlobalMemoryMacro(GlobalMemoryMacro object) {
				return createGlobalMemoryMacroAdapter();
			}
			@Override
			public Adapter caseStatementMacro(StatementMacro object) {
				return createStatementMacroAdapter();
			}
			@Override
			public Adapter caseBaseVariable(BaseVariable object) {
				return createBaseVariableAdapter();
			}
			@Override
			public Adapter caseGlobalVariable(GlobalVariable object) {
				return createGlobalVariableAdapter();
			}
			@Override
			public Adapter caseArrayVariable(ArrayVariable object) {
				return createArrayVariableAdapter();
			}
			@Override
			public Adapter caseFunctionBody(FunctionBody object) {
				return createFunctionBodyAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseEvalFunction(EvalFunction object) {
				return createEvalFunctionAdapter();
			}
			@Override
			public Adapter caseProgram(Program object) {
				return createProgramAdapter();
			}
			@Override
			public Adapter caseAlphaOp(AlphaOp object) {
				return createAlphaOpAdapter();
			}
			@Override
			public Adapter casePolynomialVisitor(PolynomialVisitor object) {
				return createPolynomialVisitorAdapter();
			}
			@Override
			public Adapter casePolynomialNode(PolynomialNode object) {
				return createPolynomialNodeAdapter();
			}
			@Override
			public Adapter casePolynomialVisitable(PolynomialVisitable object) {
				return createPolynomialVisitableAdapter();
			}
			@Override
			public Adapter casePolynomial(Polynomial object) {
				return createPolynomialAdapter();
			}
			@Override
			public Adapter casePolynomialPiece(PolynomialPiece object) {
				return createPolynomialPieceAdapter();
			}
			@Override
			public Adapter casePolynomialTerm(PolynomialTerm object) {
				return createPolynomialTermAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Node
	 * @generated
	 */
	public Adapter createNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Visitable <em>Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Visitable
	 * @generated
	 */
	public Adapter createVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Visitor <em>Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Visitor
	 * @generated
	 */
	public Adapter createVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Include <em>Include</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Include
	 * @generated
	 */
	public Adapter createIncludeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Macro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Macro
	 * @generated
	 */
	public Adapter createMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.GlobalMacro <em>Global Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.GlobalMacro
	 * @generated
	 */
	public Adapter createGlobalMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.AbstractMemoryMacro <em>Abstract Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.AbstractMemoryMacro
	 * @generated
	 */
	public Adapter createAbstractMemoryMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.MemoryMacro <em>Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.MemoryMacro
	 * @generated
	 */
	public Adapter createMemoryMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.MemoryAllocation <em>Memory Allocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.MemoryAllocation
	 * @generated
	 */
	public Adapter createMemoryAllocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.GlobalMemoryMacro <em>Global Memory Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.GlobalMemoryMacro
	 * @generated
	 */
	public Adapter createGlobalMemoryMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.StatementMacro <em>Statement Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.StatementMacro
	 * @generated
	 */
	public Adapter createStatementMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.BaseVariable <em>Base Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.BaseVariable
	 * @generated
	 */
	public Adapter createBaseVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.GlobalVariable <em>Global Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.GlobalVariable
	 * @generated
	 */
	public Adapter createGlobalVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.ArrayVariable <em>Array Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.ArrayVariable
	 * @generated
	 */
	public Adapter createArrayVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.FunctionBody <em>Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.FunctionBody
	 * @generated
	 */
	public Adapter createFunctionBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.EvalFunction <em>Eval Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.EvalFunction
	 * @generated
	 */
	public Adapter createEvalFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Program <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Program
	 * @generated
	 */
	public Adapter createProgramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.AlphaOp <em>Alpha Op</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.AlphaOp
	 * @generated
	 */
	public Adapter createAlphaOpAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.PolynomialVisitor <em>Polynomial Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.PolynomialVisitor
	 * @generated
	 */
	public Adapter createPolynomialVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.PolynomialNode <em>Polynomial Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.PolynomialNode
	 * @generated
	 */
	public Adapter createPolynomialNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.PolynomialVisitable <em>Polynomial Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.PolynomialVisitable
	 * @generated
	 */
	public Adapter createPolynomialVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.Polynomial <em>Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.Polynomial
	 * @generated
	 */
	public Adapter createPolynomialAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.PolynomialPiece <em>Polynomial Piece</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.PolynomialPiece
	 * @generated
	 */
	public Adapter createPolynomialPieceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.codegen.PolynomialTerm <em>Polynomial Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.codegen.PolynomialTerm
	 * @generated
	 */
	public Adapter createPolynomialTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CodegenAdapterFactory
