/**
 */
package alpha.model.util;

import alpha.model.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see alpha.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
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
	protected ModelSwitch<Adapter> modelSwitch =
		new ModelSwitch<Adapter>() {
			@Override
			public Adapter caseAlphaNode(AlphaNode object) {
				return createAlphaNodeAdapter();
			}
			@Override
			public Adapter caseAlphaCompleteVisitable(AlphaCompleteVisitable object) {
				return createAlphaCompleteVisitableAdapter();
			}
			@Override
			public Adapter caseAlphaVisitable(AlphaVisitable object) {
				return createAlphaVisitableAdapter();
			}
			@Override
			public Adapter caseAlphaExpressionVisitable(AlphaExpressionVisitable object) {
				return createAlphaExpressionVisitableAdapter();
			}
			@Override
			public Adapter caseCalculatorExpressionVisitable(CalculatorExpressionVisitable object) {
				return createCalculatorExpressionVisitableAdapter();
			}
			@Override
			public Adapter caseAlphaSystemElement(AlphaSystemElement object) {
				return createAlphaSystemElementAdapter();
			}
			@Override
			public Adapter caseAlphaScheduleTarget(AlphaScheduleTarget object) {
				return createAlphaScheduleTargetAdapter();
			}
			@Override
			public Adapter caseAlphaVisitor(AlphaVisitor object) {
				return createAlphaVisitorAdapter();
			}
			@Override
			public Adapter caseAlphaExpressionVisitor(AlphaExpressionVisitor object) {
				return createAlphaExpressionVisitorAdapter();
			}
			@Override
			public Adapter caseCalculatorExpressionVisitor(CalculatorExpressionVisitor object) {
				return createCalculatorExpressionVisitorAdapter();
			}
			@Override
			public Adapter caseAlphaRoot(AlphaRoot object) {
				return createAlphaRootAdapter();
			}
			@Override
			public Adapter caseAlphaElement(AlphaElement object) {
				return createAlphaElementAdapter();
			}
			@Override
			public Adapter caseImports(Imports object) {
				return createImportsAdapter();
			}
			@Override
			public Adapter caseAlphaPackage(AlphaPackage object) {
				return createAlphaPackageAdapter();
			}
			@Override
			public Adapter caseAlphaConstant(AlphaConstant object) {
				return createAlphaConstantAdapter();
			}
			@Override
			public Adapter caseExternalFunction(ExternalFunction object) {
				return createExternalFunctionAdapter();
			}
			@Override
			public Adapter caseAlphaSystem(AlphaSystem object) {
				return createAlphaSystemAdapter();
			}
			@Override
			public Adapter caseVariable(Variable object) {
				return createVariableAdapter();
			}
			@Override
			public Adapter caseFuzzyVariable(FuzzyVariable object) {
				return createFuzzyVariableAdapter();
			}
			@Override
			public Adapter caseSystemBody(SystemBody object) {
				return createSystemBodyAdapter();
			}
			@Override
			public Adapter caseEquation(Equation object) {
				return createEquationAdapter();
			}
			@Override
			public Adapter caseStandardEquation(StandardEquation object) {
				return createStandardEquationAdapter();
			}
			@Override
			public Adapter caseUseEquation(UseEquation object) {
				return createUseEquationAdapter();
			}
			@Override
			public Adapter caseAlphaExpression(AlphaExpression object) {
				return createAlphaExpressionAdapter();
			}
			@Override
			public Adapter caseRestrictExpression(RestrictExpression object) {
				return createRestrictExpressionAdapter();
			}
			@Override
			public Adapter caseAutoRestrictExpression(AutoRestrictExpression object) {
				return createAutoRestrictExpressionAdapter();
			}
			@Override
			public Adapter caseCaseExpression(CaseExpression object) {
				return createCaseExpressionAdapter();
			}
			@Override
			public Adapter caseDependenceExpression(DependenceExpression object) {
				return createDependenceExpressionAdapter();
			}
			@Override
			public Adapter caseFuzzyDependenceExpression(FuzzyDependenceExpression object) {
				return createFuzzyDependenceExpressionAdapter();
			}
			@Override
			public Adapter caseIfExpression(IfExpression object) {
				return createIfExpressionAdapter();
			}
			@Override
			public Adapter caseIndexExpression(IndexExpression object) {
				return createIndexExpressionAdapter();
			}
			@Override
			public Adapter casePolynomialIndexExpression(PolynomialIndexExpression object) {
				return createPolynomialIndexExpressionAdapter();
			}
			@Override
			public Adapter caseFuzzyIndexExpression(FuzzyIndexExpression object) {
				return createFuzzyIndexExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractReduceExpression(AbstractReduceExpression object) {
				return createAbstractReduceExpressionAdapter();
			}
			@Override
			public Adapter caseReduceExpression(ReduceExpression object) {
				return createReduceExpressionAdapter();
			}
			@Override
			public Adapter caseExternalReduceExpression(ExternalReduceExpression object) {
				return createExternalReduceExpressionAdapter();
			}
			@Override
			public Adapter caseArgReduceExpression(ArgReduceExpression object) {
				return createArgReduceExpressionAdapter();
			}
			@Override
			public Adapter caseExternalArgReduceExpression(ExternalArgReduceExpression object) {
				return createExternalArgReduceExpressionAdapter();
			}
			@Override
			public Adapter caseConvolutionExpression(ConvolutionExpression object) {
				return createConvolutionExpressionAdapter();
			}
			@Override
			public Adapter caseSelectExpression(SelectExpression object) {
				return createSelectExpressionAdapter();
			}
			@Override
			public Adapter caseVariableExpression(VariableExpression object) {
				return createVariableExpressionAdapter();
			}
			@Override
			public Adapter caseConstantExpression(ConstantExpression object) {
				return createConstantExpressionAdapter();
			}
			@Override
			public Adapter caseIntegerExpression(IntegerExpression object) {
				return createIntegerExpressionAdapter();
			}
			@Override
			public Adapter caseRealExpression(RealExpression object) {
				return createRealExpressionAdapter();
			}
			@Override
			public Adapter caseBooleanExpression(BooleanExpression object) {
				return createBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseUnaryExpression(UnaryExpression object) {
				return createUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseBinaryExpression(BinaryExpression object) {
				return createBinaryExpressionAdapter();
			}
			@Override
			public Adapter caseMultiArgExpression(MultiArgExpression object) {
				return createMultiArgExpressionAdapter();
			}
			@Override
			public Adapter caseExternalMultiArgExpression(ExternalMultiArgExpression object) {
				return createExternalMultiArgExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractFuzzyReduceExpression(AbstractFuzzyReduceExpression object) {
				return createAbstractFuzzyReduceExpressionAdapter();
			}
			@Override
			public Adapter caseFuzzyReduceExpression(FuzzyReduceExpression object) {
				return createFuzzyReduceExpressionAdapter();
			}
			@Override
			public Adapter caseExternalFuzzyReduceExpression(ExternalFuzzyReduceExpression object) {
				return createExternalFuzzyReduceExpressionAdapter();
			}
			@Override
			public Adapter caseFuzzyArgReduceExpression(FuzzyArgReduceExpression object) {
				return createFuzzyArgReduceExpressionAdapter();
			}
			@Override
			public Adapter caseExternalFuzzyArgReduceExpression(ExternalFuzzyArgReduceExpression object) {
				return createExternalFuzzyArgReduceExpressionAdapter();
			}
			@Override
			public Adapter caseCalculatorNode(CalculatorNode object) {
				return createCalculatorNodeAdapter();
			}
			@Override
			public Adapter casePolyhedralObject(PolyhedralObject object) {
				return createPolyhedralObjectAdapter();
			}
			@Override
			public Adapter caseCalculatorExpression(CalculatorExpression object) {
				return createCalculatorExpressionAdapter();
			}
			@Override
			public Adapter caseJNIDomain(JNIDomain object) {
				return createJNIDomainAdapter();
			}
			@Override
			public Adapter caseJNIDomainInArrayNotation(JNIDomainInArrayNotation object) {
				return createJNIDomainInArrayNotationAdapter();
			}
			@Override
			public Adapter caseJNIRelation(JNIRelation object) {
				return createJNIRelationAdapter();
			}
			@Override
			public Adapter caseJNIFunction(JNIFunction object) {
				return createJNIFunctionAdapter();
			}
			@Override
			public Adapter caseJNIFunctionInArrayNotation(JNIFunctionInArrayNotation object) {
				return createJNIFunctionInArrayNotationAdapter();
			}
			@Override
			public Adapter caseJNIPolynomial(JNIPolynomial object) {
				return createJNIPolynomialAdapter();
			}
			@Override
			public Adapter caseJNIPolynomialInArrayNotation(JNIPolynomialInArrayNotation object) {
				return createJNIPolynomialInArrayNotationAdapter();
			}
			@Override
			public Adapter caseFuzzyFunction(FuzzyFunction object) {
				return createFuzzyFunctionAdapter();
			}
			@Override
			public Adapter caseFuzzyVariableUse(FuzzyVariableUse object) {
				return createFuzzyVariableUseAdapter();
			}
			@Override
			public Adapter caseNestedFuzzyFunction(NestedFuzzyFunction object) {
				return createNestedFuzzyFunctionAdapter();
			}
			@Override
			public Adapter caseAffineFuzzyVariableUse(AffineFuzzyVariableUse object) {
				return createAffineFuzzyVariableUseAdapter();
			}
			@Override
			public Adapter caseFuzzyFunctionInArrayNotation(FuzzyFunctionInArrayNotation object) {
				return createFuzzyFunctionInArrayNotationAdapter();
			}
			@Override
			public Adapter caseUnaryCalculatorExpression(UnaryCalculatorExpression object) {
				return createUnaryCalculatorExpressionAdapter();
			}
			@Override
			public Adapter caseBinaryCalculatorExpression(BinaryCalculatorExpression object) {
				return createBinaryCalculatorExpressionAdapter();
			}
			@Override
			public Adapter caseVariableDomain(VariableDomain object) {
				return createVariableDomainAdapter();
			}
			@Override
			public Adapter caseRectangularDomain(RectangularDomain object) {
				return createRectangularDomainAdapter();
			}
			@Override
			public Adapter caseDefinedObject(DefinedObject object) {
				return createDefinedObjectAdapter();
			}
			@Override
			public Adapter caseAlphaFunction(AlphaFunction object) {
				return createAlphaFunctionAdapter();
			}
			@Override
			public Adapter caseAlphaFunctionExpression(AlphaFunctionExpression object) {
				return createAlphaFunctionExpressionAdapter();
			}
			@Override
			public Adapter caseAlphaFunctionBinaryExpression(AlphaFunctionBinaryExpression object) {
				return createAlphaFunctionBinaryExpressionAdapter();
			}
			@Override
			public Adapter caseAlphaFunctionLiteral(AlphaFunctionLiteral object) {
				return createAlphaFunctionLiteralAdapter();
			}
			@Override
			public Adapter caseAlphaFunctionFloor(AlphaFunctionFloor object) {
				return createAlphaFunctionFloorAdapter();
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
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaNode <em>Alpha Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaNode
	 * @generated
	 */
	public Adapter createAlphaNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaCompleteVisitable <em>Alpha Complete Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaCompleteVisitable
	 * @generated
	 */
	public Adapter createAlphaCompleteVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaVisitable <em>Alpha Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaVisitable
	 * @generated
	 */
	public Adapter createAlphaVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaExpressionVisitable <em>Alpha Expression Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaExpressionVisitable
	 * @generated
	 */
	public Adapter createAlphaExpressionVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.CalculatorExpressionVisitable <em>Calculator Expression Visitable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.CalculatorExpressionVisitable
	 * @generated
	 */
	public Adapter createCalculatorExpressionVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaSystemElement <em>Alpha System Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaSystemElement
	 * @generated
	 */
	public Adapter createAlphaSystemElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaScheduleTarget <em>Alpha Schedule Target</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaScheduleTarget
	 * @generated
	 */
	public Adapter createAlphaScheduleTargetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaVisitor <em>Alpha Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaVisitor
	 * @generated
	 */
	public Adapter createAlphaVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaExpressionVisitor <em>Alpha Expression Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaExpressionVisitor
	 * @generated
	 */
	public Adapter createAlphaExpressionVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.CalculatorExpressionVisitor <em>Calculator Expression Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.CalculatorExpressionVisitor
	 * @generated
	 */
	public Adapter createCalculatorExpressionVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaRoot <em>Alpha Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaRoot
	 * @generated
	 */
	public Adapter createAlphaRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaElement <em>Alpha Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaElement
	 * @generated
	 */
	public Adapter createAlphaElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.Imports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.Imports
	 * @generated
	 */
	public Adapter createImportsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaPackage <em>Alpha Package</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaPackage
	 * @generated
	 */
	public Adapter createAlphaPackageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaConstant <em>Alpha Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaConstant
	 * @generated
	 */
	public Adapter createAlphaConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalFunction <em>External Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalFunction
	 * @generated
	 */
	public Adapter createExternalFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaSystem <em>Alpha System</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaSystem
	 * @generated
	 */
	public Adapter createAlphaSystemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyVariable <em>Fuzzy Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyVariable
	 * @generated
	 */
	public Adapter createFuzzyVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.SystemBody <em>System Body</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.SystemBody
	 * @generated
	 */
	public Adapter createSystemBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.Equation <em>Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.Equation
	 * @generated
	 */
	public Adapter createEquationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.StandardEquation <em>Standard Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.StandardEquation
	 * @generated
	 */
	public Adapter createStandardEquationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.UseEquation <em>Use Equation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.UseEquation
	 * @generated
	 */
	public Adapter createUseEquationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaExpression <em>Alpha Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaExpression
	 * @generated
	 */
	public Adapter createAlphaExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.RestrictExpression <em>Restrict Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.RestrictExpression
	 * @generated
	 */
	public Adapter createRestrictExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AutoRestrictExpression <em>Auto Restrict Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AutoRestrictExpression
	 * @generated
	 */
	public Adapter createAutoRestrictExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.CaseExpression <em>Case Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.CaseExpression
	 * @generated
	 */
	public Adapter createCaseExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.DependenceExpression <em>Dependence Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.DependenceExpression
	 * @generated
	 */
	public Adapter createDependenceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyDependenceExpression <em>Fuzzy Dependence Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyDependenceExpression
	 * @generated
	 */
	public Adapter createFuzzyDependenceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.IfExpression <em>If Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.IfExpression
	 * @generated
	 */
	public Adapter createIfExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.IndexExpression <em>Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.IndexExpression
	 * @generated
	 */
	public Adapter createIndexExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.PolynomialIndexExpression <em>Polynomial Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.PolynomialIndexExpression
	 * @generated
	 */
	public Adapter createPolynomialIndexExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyIndexExpression <em>Fuzzy Index Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyIndexExpression
	 * @generated
	 */
	public Adapter createFuzzyIndexExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AbstractReduceExpression <em>Abstract Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AbstractReduceExpression
	 * @generated
	 */
	public Adapter createAbstractReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ReduceExpression <em>Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ReduceExpression
	 * @generated
	 */
	public Adapter createReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalReduceExpression <em>External Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalReduceExpression
	 * @generated
	 */
	public Adapter createExternalReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ArgReduceExpression <em>Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ArgReduceExpression
	 * @generated
	 */
	public Adapter createArgReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalArgReduceExpression <em>External Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalArgReduceExpression
	 * @generated
	 */
	public Adapter createExternalArgReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ConvolutionExpression <em>Convolution Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ConvolutionExpression
	 * @generated
	 */
	public Adapter createConvolutionExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.SelectExpression <em>Select Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.SelectExpression
	 * @generated
	 */
	public Adapter createSelectExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.VariableExpression <em>Variable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.VariableExpression
	 * @generated
	 */
	public Adapter createVariableExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ConstantExpression <em>Constant Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ConstantExpression
	 * @generated
	 */
	public Adapter createConstantExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.IntegerExpression <em>Integer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.IntegerExpression
	 * @generated
	 */
	public Adapter createIntegerExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.RealExpression <em>Real Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.RealExpression
	 * @generated
	 */
	public Adapter createRealExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.BooleanExpression
	 * @generated
	 */
	public Adapter createBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.UnaryExpression
	 * @generated
	 */
	public Adapter createUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.BinaryExpression <em>Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.BinaryExpression
	 * @generated
	 */
	public Adapter createBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.MultiArgExpression <em>Multi Arg Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.MultiArgExpression
	 * @generated
	 */
	public Adapter createMultiArgExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalMultiArgExpression <em>External Multi Arg Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalMultiArgExpression
	 * @generated
	 */
	public Adapter createExternalMultiArgExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AbstractFuzzyReduceExpression <em>Abstract Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AbstractFuzzyReduceExpression
	 * @generated
	 */
	public Adapter createAbstractFuzzyReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyReduceExpression <em>Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyReduceExpression
	 * @generated
	 */
	public Adapter createFuzzyReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalFuzzyReduceExpression <em>External Fuzzy Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalFuzzyReduceExpression
	 * @generated
	 */
	public Adapter createExternalFuzzyReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyArgReduceExpression <em>Fuzzy Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyArgReduceExpression
	 * @generated
	 */
	public Adapter createFuzzyArgReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.ExternalFuzzyArgReduceExpression <em>External Fuzzy Arg Reduce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.ExternalFuzzyArgReduceExpression
	 * @generated
	 */
	public Adapter createExternalFuzzyArgReduceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.CalculatorNode <em>Calculator Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.CalculatorNode
	 * @generated
	 */
	public Adapter createCalculatorNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.PolyhedralObject <em>Polyhedral Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.PolyhedralObject
	 * @generated
	 */
	public Adapter createPolyhedralObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.CalculatorExpression <em>Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.CalculatorExpression
	 * @generated
	 */
	public Adapter createCalculatorExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIDomain <em>JNI Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIDomain
	 * @generated
	 */
	public Adapter createJNIDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIDomainInArrayNotation <em>JNI Domain In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIDomainInArrayNotation
	 * @generated
	 */
	public Adapter createJNIDomainInArrayNotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIRelation <em>JNI Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIRelation
	 * @generated
	 */
	public Adapter createJNIRelationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIFunction <em>JNI Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIFunction
	 * @generated
	 */
	public Adapter createJNIFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIFunctionInArrayNotation <em>JNI Function In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIFunctionInArrayNotation
	 * @generated
	 */
	public Adapter createJNIFunctionInArrayNotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIPolynomial <em>JNI Polynomial</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIPolynomial
	 * @generated
	 */
	public Adapter createJNIPolynomialAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.JNIPolynomialInArrayNotation <em>JNI Polynomial In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.JNIPolynomialInArrayNotation
	 * @generated
	 */
	public Adapter createJNIPolynomialInArrayNotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyFunction <em>Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyFunction
	 * @generated
	 */
	public Adapter createFuzzyFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyVariableUse <em>Fuzzy Variable Use</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyVariableUse
	 * @generated
	 */
	public Adapter createFuzzyVariableUseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.NestedFuzzyFunction <em>Nested Fuzzy Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.NestedFuzzyFunction
	 * @generated
	 */
	public Adapter createNestedFuzzyFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AffineFuzzyVariableUse <em>Affine Fuzzy Variable Use</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AffineFuzzyVariableUse
	 * @generated
	 */
	public Adapter createAffineFuzzyVariableUseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.FuzzyFunctionInArrayNotation <em>Fuzzy Function In Array Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.FuzzyFunctionInArrayNotation
	 * @generated
	 */
	public Adapter createFuzzyFunctionInArrayNotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.UnaryCalculatorExpression <em>Unary Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.UnaryCalculatorExpression
	 * @generated
	 */
	public Adapter createUnaryCalculatorExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.BinaryCalculatorExpression <em>Binary Calculator Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.BinaryCalculatorExpression
	 * @generated
	 */
	public Adapter createBinaryCalculatorExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.VariableDomain <em>Variable Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.VariableDomain
	 * @generated
	 */
	public Adapter createVariableDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.RectangularDomain <em>Rectangular Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.RectangularDomain
	 * @generated
	 */
	public Adapter createRectangularDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.DefinedObject <em>Defined Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.DefinedObject
	 * @generated
	 */
	public Adapter createDefinedObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaFunction <em>Alpha Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaFunction
	 * @generated
	 */
	public Adapter createAlphaFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaFunctionExpression <em>Alpha Function Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaFunctionExpression
	 * @generated
	 */
	public Adapter createAlphaFunctionExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaFunctionBinaryExpression <em>Alpha Function Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaFunctionBinaryExpression
	 * @generated
	 */
	public Adapter createAlphaFunctionBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaFunctionLiteral <em>Alpha Function Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaFunctionLiteral
	 * @generated
	 */
	public Adapter createAlphaFunctionLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link alpha.model.AlphaFunctionFloor <em>Alpha Function Floor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see alpha.model.AlphaFunctionFloor
	 * @generated
	 */
	public Adapter createAlphaFunctionFloorAdapter() {
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

} //ModelAdapterFactory
