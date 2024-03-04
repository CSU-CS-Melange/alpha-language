/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.EvalFunction;
import alpha.codegen.Visitor;

import alpha.model.StandardEquation;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Eval Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.EvalFunctionImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.codegen.impl.EvalFunctionImpl#getFlagVariable <em>Flag Variable</em>}</li>
 *   <li>{@link alpha.codegen.impl.EvalFunctionImpl#getEquation <em>Equation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EvalFunctionImpl extends FunctionImpl implements EvalFunction {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected ArrayVariable variable;

	/**
	 * The cached value of the '{@link #getFlagVariable() <em>Flag Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlagVariable()
	 * @generated
	 * @ordered
	 */
	protected ArrayVariable flagVariable;

	/**
	 * The cached value of the '{@link #getEquation() <em>Equation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquation()
	 * @generated
	 * @ordered
	 */
	protected StandardEquation equation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EvalFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.EVAL_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (ArrayVariable)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.EVAL_FUNCTION__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(ArrayVariable newVariable) {
		ArrayVariable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.EVAL_FUNCTION__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable getFlagVariable() {
		if (flagVariable != null && flagVariable.eIsProxy()) {
			InternalEObject oldFlagVariable = (InternalEObject)flagVariable;
			flagVariable = (ArrayVariable)eResolveProxy(oldFlagVariable);
			if (flagVariable != oldFlagVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE, oldFlagVariable, flagVariable));
			}
		}
		return flagVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable basicGetFlagVariable() {
		return flagVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFlagVariable(ArrayVariable newFlagVariable) {
		ArrayVariable oldFlagVariable = flagVariable;
		flagVariable = newFlagVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE, oldFlagVariable, flagVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardEquation getEquation() {
		if (equation != null && equation.eIsProxy()) {
			InternalEObject oldEquation = (InternalEObject)equation;
			equation = (StandardEquation)eResolveProxy(oldEquation);
			if (equation != oldEquation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.EVAL_FUNCTION__EQUATION, oldEquation, equation));
			}
		}
		return equation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardEquation basicGetEquation() {
		return equation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEquation(StandardEquation newEquation) {
		StandardEquation oldEquation = equation;
		equation = newEquation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.EVAL_FUNCTION__EQUATION, oldEquation, equation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitEvalFunction(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.EVAL_FUNCTION__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE:
				if (resolve) return getFlagVariable();
				return basicGetFlagVariable();
			case CodegenPackage.EVAL_FUNCTION__EQUATION:
				if (resolve) return getEquation();
				return basicGetEquation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CodegenPackage.EVAL_FUNCTION__VARIABLE:
				setVariable((ArrayVariable)newValue);
				return;
			case CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE:
				setFlagVariable((ArrayVariable)newValue);
				return;
			case CodegenPackage.EVAL_FUNCTION__EQUATION:
				setEquation((StandardEquation)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CodegenPackage.EVAL_FUNCTION__VARIABLE:
				setVariable((ArrayVariable)null);
				return;
			case CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE:
				setFlagVariable((ArrayVariable)null);
				return;
			case CodegenPackage.EVAL_FUNCTION__EQUATION:
				setEquation((StandardEquation)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CodegenPackage.EVAL_FUNCTION__VARIABLE:
				return variable != null;
			case CodegenPackage.EVAL_FUNCTION__FLAG_VARIABLE:
				return flagVariable != null;
			case CodegenPackage.EVAL_FUNCTION__EQUATION:
				return equation != null;
		}
		return super.eIsSet(featureID);
	}

} //EvalFunctionImpl
