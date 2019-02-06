/**
 */
package alpha.model.impl;

import alpha.model.AlphaVisitor;
import alpha.model.JNIDomain;
import alpha.model.ModelPackage;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;

import fr.irisa.cairn.jnimap.isl.jni.JNIISLSet;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>System Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getParameterDomainExpr <em>Parameter Domain Expr</em>}</li>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getUseEquations <em>Use Equations</em>}</li>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getEquations <em>Equations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SystemBodyImpl extends MinimalEObjectImpl.Container implements SystemBody {
	/**
	 * The cached value of the '{@link #getParameterDomainExpr() <em>Parameter Domain Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDomainExpr()
	 * @generated
	 * @ordered
	 */
	protected JNIDomain parameterDomainExpr;

	/**
	 * The cached value of the '{@link #getUseEquations() <em>Use Equations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUseEquations()
	 * @generated
	 * @ordered
	 */
	protected EList<UseEquation> useEquations;

	/**
	 * The cached value of the '{@link #getEquations() <em>Equations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquations()
	 * @generated
	 * @ordered
	 */
	protected EList<StandardEquation> equations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemBodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SYSTEM_BODY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JNIDomain getParameterDomainExpr() {
		return parameterDomainExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameterDomainExpr(JNIDomain newParameterDomainExpr, NotificationChain msgs) {
		JNIDomain oldParameterDomainExpr = parameterDomainExpr;
		parameterDomainExpr = newParameterDomainExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR, oldParameterDomainExpr, newParameterDomainExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameterDomainExpr(JNIDomain newParameterDomainExpr) {
		if (newParameterDomainExpr != parameterDomainExpr) {
			NotificationChain msgs = null;
			if (parameterDomainExpr != null)
				msgs = ((InternalEObject)parameterDomainExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR, null, msgs);
			if (newParameterDomainExpr != null)
				msgs = ((InternalEObject)newParameterDomainExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR, null, msgs);
			msgs = basicSetParameterDomainExpr(newParameterDomainExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR, newParameterDomainExpr, newParameterDomainExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UseEquation> getUseEquations() {
		if (useEquations == null) {
			useEquations = new EObjectContainmentEList<UseEquation>(UseEquation.class, this, ModelPackage.SYSTEM_BODY__USE_EQUATIONS);
		}
		return useEquations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StandardEquation> getEquations() {
		if (equations == null) {
			equations = new EObjectContainmentEList<StandardEquation>(StandardEquation.class, this, ModelPackage.SYSTEM_BODY__EQUATIONS);
		}
		return equations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JNIISLSet getParameterDomain() {
		return this.getParameterDomainExpr().getISLSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final AlphaVisitor visitor) {
		visitor.visitSystemBody(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return basicSetParameterDomainExpr(null, msgs);
			case ModelPackage.SYSTEM_BODY__USE_EQUATIONS:
				return ((InternalEList<?>)getUseEquations()).basicRemove(otherEnd, msgs);
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				return ((InternalEList<?>)getEquations()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return getParameterDomainExpr();
			case ModelPackage.SYSTEM_BODY__USE_EQUATIONS:
				return getUseEquations();
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				return getEquations();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				setParameterDomainExpr((JNIDomain)newValue);
				return;
			case ModelPackage.SYSTEM_BODY__USE_EQUATIONS:
				getUseEquations().clear();
				getUseEquations().addAll((Collection<? extends UseEquation>)newValue);
				return;
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				getEquations().clear();
				getEquations().addAll((Collection<? extends StandardEquation>)newValue);
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
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				setParameterDomainExpr((JNIDomain)null);
				return;
			case ModelPackage.SYSTEM_BODY__USE_EQUATIONS:
				getUseEquations().clear();
				return;
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				getEquations().clear();
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
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return parameterDomainExpr != null;
			case ModelPackage.SYSTEM_BODY__USE_EQUATIONS:
				return useEquations != null && !useEquations.isEmpty();
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				return equations != null && !equations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SystemBodyImpl
