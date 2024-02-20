/**
 */
package alpha.model.impl;

import alpha.model.AlphaExpression;
import alpha.model.AlphaVisitor;
import alpha.model.Equation;
import alpha.model.ModelPackage;
import alpha.model.SystemBody;

import alpha.model.util.AlphaUtil;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Equation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.EquationImpl#getSystemBody <em>System Body</em>}</li>
 *   <li>{@link alpha.model.impl.EquationImpl#getZ__explored <em>Zexplored</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class EquationImpl extends AlphaNodeImpl implements Equation {
	/**
	 * The default value of the '{@link #getZ__explored() <em>Zexplored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__explored()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ZEXPLORED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZ__explored() <em>Zexplored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__explored()
	 * @generated
	 * @ordered
	 */
	protected Boolean z__explored = ZEXPLORED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EquationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.EQUATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemBody getSystemBody() {
		if (eContainerFeatureID() != ModelPackage.EQUATION__SYSTEM_BODY) return null;
		return (SystemBody)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemBody basicGetSystemBody() {
		if (eContainerFeatureID() != ModelPackage.EQUATION__SYSTEM_BODY) return null;
		return (SystemBody)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSystemBody(SystemBody newSystemBody, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSystemBody, ModelPackage.EQUATION__SYSTEM_BODY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystemBody(SystemBody newSystemBody) {
		if (newSystemBody != eInternalContainer() || (eContainerFeatureID() != ModelPackage.EQUATION__SYSTEM_BODY && newSystemBody != null)) {
			if (EcoreUtil.isAncestor(this, newSystemBody))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSystemBody != null)
				msgs = ((InternalEObject)newSystemBody).eInverseAdd(this, ModelPackage.SYSTEM_BODY__EQUATIONS, SystemBody.class, msgs);
			msgs = basicSetSystemBody(newSystemBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EQUATION__SYSTEM_BODY, newSystemBody, newSystemBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getZ__explored() {
		return z__explored;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setZ__explored(Boolean newZ__explored) {
		Boolean oldZ__explored = z__explored;
		z__explored = newZ__explored;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.EQUATION__ZEXPLORED, oldZ__explored, z__explored));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaExpression getExpression(final Queue<Integer> exprID) {
		AlphaExpression _xblockexpression = null;
		{
			final Integer next = exprID.poll();
			AlphaExpression _xifexpression = null;
			if ((next == null)) {
				throw new RuntimeException("Invalid Expression ID");
			}
			else {
				EObject _get = this.eContents().get((next).intValue());
				_xifexpression = ((AlphaExpression) _get).getExpression(exprID);
			}
			_xblockexpression = _xifexpression;
		}
		return _xblockexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaExpression getExpression(final EList<Integer> exprID) {
		LinkedList<Integer> _linkedList = new LinkedList<Integer>(exprID);
		return this.getExpression(_linkedList);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaExpression getExpression(final String exprIDstr) {
		int[] _parseIntArray = AlphaUtil.parseIntArray(exprIDstr);
		LinkedList<Integer> _linkedList = new LinkedList<Integer>((Collection<? extends Integer>)org.eclipse.xtext.xbase.lib.Conversions.doWrapArray(_parseIntArray));
		return this.getExpression(_linkedList);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getExplored() {
		Boolean _z__explored = this.getZ__explored();
		boolean _tripleEquals = (_z__explored == null);
		if (_tripleEquals) {
			return Boolean.valueOf(false);
		}
		return this.getZ__explored();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExplored() {
		this.setZ__explored(Boolean.valueOf(true));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExplored(final Boolean explored) {
		this.setZ__explored(explored);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(AlphaVisitor visitor) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.EQUATION__SYSTEM_BODY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSystemBody((SystemBody)otherEnd, msgs);
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				return basicSetSystemBody(null, msgs);
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				return eInternalContainer().eInverseRemove(this, ModelPackage.SYSTEM_BODY__EQUATIONS, SystemBody.class, msgs);
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				if (resolve) return getSystemBody();
				return basicGetSystemBody();
			case ModelPackage.EQUATION__ZEXPLORED:
				return getZ__explored();
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				setSystemBody((SystemBody)newValue);
				return;
			case ModelPackage.EQUATION__ZEXPLORED:
				setZ__explored((Boolean)newValue);
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				setSystemBody((SystemBody)null);
				return;
			case ModelPackage.EQUATION__ZEXPLORED:
				setZ__explored(ZEXPLORED_EDEFAULT);
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
			case ModelPackage.EQUATION__SYSTEM_BODY:
				return basicGetSystemBody() != null;
			case ModelPackage.EQUATION__ZEXPLORED:
				return ZEXPLORED_EDEFAULT == null ? z__explored != null : !ZEXPLORED_EDEFAULT.equals(z__explored);
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
		result.append(" (z__explored: ");
		result.append(z__explored);
		result.append(')');
		return result.toString();
	}

} //EquationImpl
