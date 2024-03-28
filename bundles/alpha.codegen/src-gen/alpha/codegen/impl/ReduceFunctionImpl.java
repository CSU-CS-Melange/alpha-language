/**
 */
package alpha.codegen.impl;

import alpha.codegen.BaseVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.ReduceFunction;
import alpha.codegen.Visitor;

import alpha.model.ReduceExpression;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reduce Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.ReduceFunctionImpl#getReduceExpr <em>Reduce Expr</em>}</li>
 *   <li>{@link alpha.codegen.impl.ReduceFunctionImpl#getReduceVar <em>Reduce Var</em>}</li>
 *   <li>{@link alpha.codegen.impl.ReduceFunctionImpl#getMacroName <em>Macro Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReduceFunctionImpl extends FunctionImpl implements ReduceFunction {
	/**
	 * The cached value of the '{@link #getReduceExpr() <em>Reduce Expr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReduceExpr()
	 * @generated
	 * @ordered
	 */
	protected ReduceExpression reduceExpr;

	/**
	 * The cached value of the '{@link #getReduceVar() <em>Reduce Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReduceVar()
	 * @generated
	 * @ordered
	 */
	protected BaseVariable reduceVar;

	/**
	 * The default value of the '{@link #getMacroName() <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroName()
	 * @generated
	 * @ordered
	 */
	protected static final String MACRO_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMacroName() <em>Macro Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacroName()
	 * @generated
	 * @ordered
	 */
	protected String macroName = MACRO_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReduceFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.REDUCE_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReduceExpression getReduceExpr() {
		if (reduceExpr != null && reduceExpr.eIsProxy()) {
			InternalEObject oldReduceExpr = (InternalEObject)reduceExpr;
			reduceExpr = (ReduceExpression)eResolveProxy(oldReduceExpr);
			if (reduceExpr != oldReduceExpr) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR, oldReduceExpr, reduceExpr));
			}
		}
		return reduceExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReduceExpression basicGetReduceExpr() {
		return reduceExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReduceExpr(ReduceExpression newReduceExpr) {
		ReduceExpression oldReduceExpr = reduceExpr;
		reduceExpr = newReduceExpr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR, oldReduceExpr, reduceExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseVariable getReduceVar() {
		if (reduceVar != null && reduceVar.eIsProxy()) {
			InternalEObject oldReduceVar = (InternalEObject)reduceVar;
			reduceVar = (BaseVariable)eResolveProxy(oldReduceVar);
			if (reduceVar != oldReduceVar) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR, oldReduceVar, reduceVar));
			}
		}
		return reduceVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseVariable basicGetReduceVar() {
		return reduceVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReduceVar(BaseVariable newReduceVar) {
		BaseVariable oldReduceVar = reduceVar;
		reduceVar = newReduceVar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR, oldReduceVar, reduceVar));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMacroName() {
		return macroName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacroName(String newMacroName) {
		String oldMacroName = macroName;
		macroName = newMacroName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.REDUCE_FUNCTION__MACRO_NAME, oldMacroName, macroName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitReduceFunction(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR:
				if (resolve) return getReduceExpr();
				return basicGetReduceExpr();
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR:
				if (resolve) return getReduceVar();
				return basicGetReduceVar();
			case CodegenPackage.REDUCE_FUNCTION__MACRO_NAME:
				return getMacroName();
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
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR:
				setReduceExpr((ReduceExpression)newValue);
				return;
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR:
				setReduceVar((BaseVariable)newValue);
				return;
			case CodegenPackage.REDUCE_FUNCTION__MACRO_NAME:
				setMacroName((String)newValue);
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
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR:
				setReduceExpr((ReduceExpression)null);
				return;
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR:
				setReduceVar((BaseVariable)null);
				return;
			case CodegenPackage.REDUCE_FUNCTION__MACRO_NAME:
				setMacroName(MACRO_NAME_EDEFAULT);
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
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_EXPR:
				return reduceExpr != null;
			case CodegenPackage.REDUCE_FUNCTION__REDUCE_VAR:
				return reduceVar != null;
			case CodegenPackage.REDUCE_FUNCTION__MACRO_NAME:
				return MACRO_NAME_EDEFAULT == null ? macroName != null : !MACRO_NAME_EDEFAULT.equals(macroName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (macroName: ");
		result.append(macroName);
		result.append(')');
		return result.toString();
	}

} //ReduceFunctionImpl
