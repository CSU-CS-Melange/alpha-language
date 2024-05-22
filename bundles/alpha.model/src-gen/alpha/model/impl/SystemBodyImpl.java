/**
 */
package alpha.model.impl;

import alpha.model.AlphaSystem;
import alpha.model.AlphaVisitor;
import alpha.model.Equation;
import alpha.model.JNIDomain;
import alpha.model.ModelPackage;

import alpha.model.ModelPackage.Literals;

import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;

import fr.irisa.cairn.jnimap.isl.ISLSet;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>System Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getSystem <em>System</em>}</li>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getParameterDomainExpr <em>Parameter Domain Expr</em>}</li>
 *   <li>{@link alpha.model.impl.SystemBodyImpl#getEquations <em>Equations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SystemBodyImpl extends AlphaNodeImpl implements SystemBody {
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
	 * The cached value of the '{@link #getEquations() <em>Equations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquations()
	 * @generated
	 * @ordered
	 */
	protected EList<Equation> equations;

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
	public AlphaSystem getSystem() {
		if (eContainerFeatureID() != ModelPackage.SYSTEM_BODY__SYSTEM) return null;
		return (AlphaSystem)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaSystem basicGetSystem() {
		if (eContainerFeatureID() != ModelPackage.SYSTEM_BODY__SYSTEM) return null;
		return (AlphaSystem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSystem(AlphaSystem newSystem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSystem, ModelPackage.SYSTEM_BODY__SYSTEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem(AlphaSystem newSystem) {
		if (newSystem != eInternalContainer() || (eContainerFeatureID() != ModelPackage.SYSTEM_BODY__SYSTEM && newSystem != null)) {
			if (EcoreUtil.isAncestor(this, newSystem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSystem != null)
				msgs = ((InternalEObject)newSystem).eInverseAdd(this, ModelPackage.ALPHA_SYSTEM__SYSTEM_BODIES, AlphaSystem.class, msgs);
			msgs = basicSetSystem(newSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SYSTEM_BODY__SYSTEM, newSystem, newSystem));
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
	public EList<Equation> getEquations() {
		if (equations == null) {
			equations = new EObjectContainmentWithInverseEList<Equation>(Equation.class, this, ModelPackage.SYSTEM_BODY__EQUATIONS, ModelPackage.EQUATION__SYSTEM_BODY);
		}
		return equations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet getParameterDomain() {
		return this.getParameterDomainExpr().getISLSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<UseEquation> getUseEquations() {
		return ECollections.<UseEquation>asEList(((UseEquation[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(EcoreUtil.<UseEquation>getObjectsByType(this.getEquations(), Literals.USE_EQUATION), UseEquation.class)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StandardEquation> getStandardEquations() {
		return ECollections.<StandardEquation>asEList(((StandardEquation[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(EcoreUtil.<StandardEquation>getObjectsByType(this.getEquations(), Literals.STANDARD_EQUATION), StandardEquation.class)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardEquation getStandardEquation(final String eqName) {
		final Function1<StandardEquation, Boolean> _function = new Function1<StandardEquation, Boolean>() {
			public Boolean apply(final StandardEquation eq) {
				return Boolean.valueOf(eq.getVariable().getName().contentEquals(eqName));
			}
		};
		return IterableExtensions.<StandardEquation>findFirst(this.getStandardEquations(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StandardEquation getStandardEquation(final Variable v) {
		final Function1<StandardEquation, Boolean> _function = new Function1<StandardEquation, Boolean>() {
			public Boolean apply(final StandardEquation eq) {
				return Boolean.valueOf(eq.getVariable().getName().equals(v.getName()));
			}
		};
		return IterableExtensions.<StandardEquation>findFirst(this.getStandardEquations(), _function);
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSystem((AlphaSystem)otherEnd, msgs);
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEquations()).basicAdd(otherEnd, msgs);
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
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				return basicSetSystem(null, msgs);
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return basicSetParameterDomainExpr(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				return eInternalContainer().eInverseRemove(this, ModelPackage.ALPHA_SYSTEM__SYSTEM_BODIES, AlphaSystem.class, msgs);
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
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				if (resolve) return getSystem();
				return basicGetSystem();
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return getParameterDomainExpr();
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
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				setSystem((AlphaSystem)newValue);
				return;
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				setParameterDomainExpr((JNIDomain)newValue);
				return;
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				getEquations().clear();
				getEquations().addAll((Collection<? extends Equation>)newValue);
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
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				setSystem((AlphaSystem)null);
				return;
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				setParameterDomainExpr((JNIDomain)null);
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
			case ModelPackage.SYSTEM_BODY__SYSTEM:
				return basicGetSystem() != null;
			case ModelPackage.SYSTEM_BODY__PARAMETER_DOMAIN_EXPR:
				return parameterDomainExpr != null;
			case ModelPackage.SYSTEM_BODY__EQUATIONS:
				return equations != null && !equations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SystemBodyImpl
