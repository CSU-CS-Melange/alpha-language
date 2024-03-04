/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.FunctionBody;
import alpha.codegen.StatementMacro;
import alpha.codegen.Visitor;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Statement Macro</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.StatementMacroImpl#getFunctionBody <em>Function Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StatementMacroImpl extends MacroImpl implements StatementMacro {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatementMacroImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.STATEMENT_MACRO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionBody getFunctionBody() {
		if (eContainerFeatureID() != CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY) return null;
		return (FunctionBody)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionBody basicGetFunctionBody() {
		if (eContainerFeatureID() != CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY) return null;
		return (FunctionBody)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionBody(FunctionBody newFunctionBody, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFunctionBody, CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionBody(FunctionBody newFunctionBody) {
		if (newFunctionBody != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY && newFunctionBody != null)) {
			if (EcoreUtil.isAncestor(this, newFunctionBody))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFunctionBody != null)
				msgs = ((InternalEObject)newFunctionBody).eInverseAdd(this, CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS, FunctionBody.class, msgs);
			msgs = basicSetFunctionBody(newFunctionBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY, newFunctionBody, newFunctionBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitStatementMacro(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFunctionBody((FunctionBody)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				return basicSetFunctionBody(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS, FunctionBody.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				if (resolve) return getFunctionBody();
				return basicGetFunctionBody();
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
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				setFunctionBody((FunctionBody)newValue);
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
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				setFunctionBody((FunctionBody)null);
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
			case CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY:
				return basicGetFunctionBody() != null;
		}
		return super.eIsSet(featureID);
	}

} //StatementMacroImpl
