/**
 */
package alpha.targetmapping.impl;

import alpha.model.JNIDomain;
import alpha.model.POLY_OBJECT_TYPE;

import alpha.targetmapping.GuardExpression;
import alpha.targetmapping.ScheduleTreeExpression;
import alpha.targetmapping.TargetMappingVisitor;
import alpha.targetmapping.TargetmappingPackage;

import com.google.common.base.Objects;

import fr.irisa.cairn.jnimap.isl.ISLSet;

import fr.irisa.cairn.jnimap.runtime.JNIObject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Guard Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.targetmapping.impl.GuardExpressionImpl#getGuardDomainExpr <em>Guard Domain Expr</em>}</li>
 *   <li>{@link alpha.targetmapping.impl.GuardExpressionImpl#getChild <em>Child</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GuardExpressionImpl extends ScheduleTreeExpressionImpl implements GuardExpression {
	/**
	 * The cached value of the '{@link #getGuardDomainExpr() <em>Guard Domain Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardDomainExpr()
	 * @generated
	 * @ordered
	 */
	protected JNIDomain guardDomainExpr;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected ScheduleTreeExpression child;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GuardExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TargetmappingPackage.Literals.GUARD_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JNIDomain getGuardDomainExpr() {
		return guardDomainExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGuardDomainExpr(JNIDomain newGuardDomainExpr, NotificationChain msgs) {
		JNIDomain oldGuardDomainExpr = guardDomainExpr;
		guardDomainExpr = newGuardDomainExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR, oldGuardDomainExpr, newGuardDomainExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuardDomainExpr(JNIDomain newGuardDomainExpr) {
		if (newGuardDomainExpr != guardDomainExpr) {
			NotificationChain msgs = null;
			if (guardDomainExpr != null)
				msgs = ((InternalEObject)guardDomainExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR, null, msgs);
			if (newGuardDomainExpr != null)
				msgs = ((InternalEObject)newGuardDomainExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR, null, msgs);
			msgs = basicSetGuardDomainExpr(newGuardDomainExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR, newGuardDomainExpr, newGuardDomainExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScheduleTreeExpression getChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChild(ScheduleTreeExpression newChild, NotificationChain msgs) {
		ScheduleTreeExpression oldChild = child;
		child = newChild;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TargetmappingPackage.GUARD_EXPRESSION__CHILD, oldChild, newChild);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(ScheduleTreeExpression newChild) {
		if (newChild != child) {
			NotificationChain msgs = null;
			if (child != null)
				msgs = ((InternalEObject)child).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TargetmappingPackage.GUARD_EXPRESSION__CHILD, null, msgs);
			if (newChild != null)
				msgs = ((InternalEObject)newChild).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TargetmappingPackage.GUARD_EXPRESSION__CHILD, null, msgs);
			msgs = basicSetChild(newChild, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TargetmappingPackage.GUARD_EXPRESSION__CHILD, newChild, newChild));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet getGuardDomain() {
		ISLSet _xifexpression = null;
		POLY_OBJECT_TYPE _type = this.getGuardDomainExpr().getType();
		boolean _notEquals = (!Objects.equal(_type, POLY_OBJECT_TYPE.SET));
		if (_notEquals) {
			_xifexpression = null;
		}
		else {
			JNIObject _iSLObject = this.getGuardDomainExpr().getISLObject();
			_xifexpression = ((ISLSet) _iSLObject);
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final TargetMappingVisitor visitor) {
		visitor.visitGuardExpression(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR:
				return basicSetGuardDomainExpr(null, msgs);
			case TargetmappingPackage.GUARD_EXPRESSION__CHILD:
				return basicSetChild(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR:
				return getGuardDomainExpr();
			case TargetmappingPackage.GUARD_EXPRESSION__CHILD:
				return getChild();
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
			case TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR:
				setGuardDomainExpr((JNIDomain)newValue);
				return;
			case TargetmappingPackage.GUARD_EXPRESSION__CHILD:
				setChild((ScheduleTreeExpression)newValue);
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
			case TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR:
				setGuardDomainExpr((JNIDomain)null);
				return;
			case TargetmappingPackage.GUARD_EXPRESSION__CHILD:
				setChild((ScheduleTreeExpression)null);
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
			case TargetmappingPackage.GUARD_EXPRESSION__GUARD_DOMAIN_EXPR:
				return guardDomainExpr != null;
			case TargetmappingPackage.GUARD_EXPRESSION__CHILD:
				return child != null;
		}
		return super.eIsSet(featureID);
	}

} //GuardExpressionImpl
