/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.Function;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.MemoryMacro;
import alpha.codegen.Visitor;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Memory Macro</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.MemoryMacroImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link alpha.codegen.impl.MemoryMacroImpl#getAllocation <em>Allocation</em>}</li>
 *   <li>{@link alpha.codegen.impl.MemoryMacroImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemoryMacroImpl extends AbstractMemoryMacroImpl implements MemoryMacro {
	/**
	 * The cached value of the '{@link #getAllocation() <em>Allocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllocation()
	 * @generated
	 * @ordered
	 */
	protected MemoryAllocation allocation;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MemoryMacroImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.MEMORY_MACRO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function getFunction() {
		if (eContainerFeatureID() != CodegenPackage.MEMORY_MACRO__FUNCTION) return null;
		return (Function)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function basicGetFunction() {
		if (eContainerFeatureID() != CodegenPackage.MEMORY_MACRO__FUNCTION) return null;
		return (Function)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunction(Function newFunction, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFunction, CodegenPackage.MEMORY_MACRO__FUNCTION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(Function newFunction) {
		if (newFunction != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.MEMORY_MACRO__FUNCTION && newFunction != null)) {
			if (EcoreUtil.isAncestor(this, newFunction))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFunction != null)
				msgs = ((InternalEObject)newFunction).eInverseAdd(this, CodegenPackage.FUNCTION__MEMORY_MACROS, Function.class, msgs);
			msgs = basicSetFunction(newFunction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_MACRO__FUNCTION, newFunction, newFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryAllocation getAllocation() {
		if (allocation != null && allocation.eIsProxy()) {
			InternalEObject oldAllocation = (InternalEObject)allocation;
			allocation = (MemoryAllocation)eResolveProxy(oldAllocation);
			if (allocation != oldAllocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.MEMORY_MACRO__ALLOCATION, oldAllocation, allocation));
			}
		}
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemoryAllocation basicGetAllocation() {
		return allocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllocation(MemoryAllocation newAllocation) {
		MemoryAllocation oldAllocation = allocation;
		allocation = newAllocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_MACRO__ALLOCATION, oldAllocation, allocation));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.MEMORY_MACRO__VARIABLE, oldVariable, variable));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_MACRO__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitMemoryMacro(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFunction((Function)otherEnd, msgs);
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				return basicSetFunction(null, msgs);
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.FUNCTION__MEMORY_MACROS, Function.class, msgs);
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				if (resolve) return getFunction();
				return basicGetFunction();
			case CodegenPackage.MEMORY_MACRO__ALLOCATION:
				if (resolve) return getAllocation();
				return basicGetAllocation();
			case CodegenPackage.MEMORY_MACRO__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				setFunction((Function)newValue);
				return;
			case CodegenPackage.MEMORY_MACRO__ALLOCATION:
				setAllocation((MemoryAllocation)newValue);
				return;
			case CodegenPackage.MEMORY_MACRO__VARIABLE:
				setVariable((ArrayVariable)newValue);
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				setFunction((Function)null);
				return;
			case CodegenPackage.MEMORY_MACRO__ALLOCATION:
				setAllocation((MemoryAllocation)null);
				return;
			case CodegenPackage.MEMORY_MACRO__VARIABLE:
				setVariable((ArrayVariable)null);
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
			case CodegenPackage.MEMORY_MACRO__FUNCTION:
				return basicGetFunction() != null;
			case CodegenPackage.MEMORY_MACRO__ALLOCATION:
				return allocation != null;
			case CodegenPackage.MEMORY_MACRO__VARIABLE:
				return variable != null;
		}
		return super.eIsSet(featureID);
	}

} //MemoryMacroImpl
