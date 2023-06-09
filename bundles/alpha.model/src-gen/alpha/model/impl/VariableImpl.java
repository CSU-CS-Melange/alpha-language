/**
 */
package alpha.model.impl;

import alpha.model.AlphaVisitor;
import alpha.model.CalculatorExpression;
import alpha.model.ModelPackage;

import alpha.model.ModelPackage.Literals;

import alpha.model.POLY_OBJECT_TYPE;
import alpha.model.Variable;

import alpha.model.exception.CyclicDefinitionException;

import com.google.common.base.Objects;

import fr.irisa.cairn.jnimap.isl.ISLSet;

import fr.irisa.cairn.jnimap.runtime.JNIObject;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.VariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link alpha.model.impl.VariableImpl#getDomainExpr <em>Domain Expr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableImpl extends AlphaNodeImpl implements Variable {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDomainExpr() <em>Domain Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainExpr()
	 * @generated
	 * @ordered
	 */
	protected CalculatorExpression domainExpr;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.VARIABLE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CalculatorExpression getDomainExpr() {
		return domainExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomainExpr(CalculatorExpression newDomainExpr, NotificationChain msgs) {
		CalculatorExpression oldDomainExpr = domainExpr;
		domainExpr = newDomainExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.VARIABLE__DOMAIN_EXPR, oldDomainExpr, newDomainExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainExpr(CalculatorExpression newDomainExpr) {
		if (newDomainExpr != domainExpr) {
			NotificationChain msgs = null;
			if (domainExpr != null)
				msgs = ((InternalEObject)domainExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.VARIABLE__DOMAIN_EXPR, null, msgs);
			if (newDomainExpr != null)
				msgs = ((InternalEObject)newDomainExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.VARIABLE__DOMAIN_EXPR, null, msgs);
			msgs = basicSetDomainExpr(newDomainExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.VARIABLE__DOMAIN_EXPR, newDomainExpr, newDomainExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet getDomain() {
		if (((this.getDomainExpr() == null) || (!Objects.equal(this.getDomainExpr().getType(), POLY_OBJECT_TYPE.SET)))) {
			return null;
		}
		try {
			ISLSet _xifexpression = null;
			POLY_OBJECT_TYPE _type = this.getDomainExpr().getType();
			boolean _notEquals = (!Objects.equal(_type, POLY_OBJECT_TYPE.SET));
			if (_notEquals) {
				_xifexpression = null;
			}
			else {
				JNIObject _iSLObject = this.getDomainExpr().getISLObject();
				_xifexpression = ((ISLSet) _iSLObject);
			}
			return _xifexpression;
		}
		catch (final Throwable _t) {
			if (_t instanceof CyclicDefinitionException) {
				return null;
			}
			else {
				throw Exceptions.sneakyThrow(_t);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean isInput() {
		return Boolean.valueOf(((this.eContainmentFeature() != null) && (this.eContainmentFeature() == Literals.ALPHA_SYSTEM__INPUTS)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean isOutput() {
		return Boolean.valueOf(((this.eContainmentFeature() != null) && (this.eContainmentFeature() == Literals.ALPHA_SYSTEM__OUTPUTS)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean isLocal() {
		return Boolean.valueOf(((this.eContainmentFeature() != null) && (this.eContainmentFeature() == Literals.ALPHA_SYSTEM__LOCALS)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final AlphaVisitor visitor) {
		visitor.visitVariable(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.VARIABLE__DOMAIN_EXPR:
				return basicSetDomainExpr(null, msgs);
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
			case ModelPackage.VARIABLE__NAME:
				return getName();
			case ModelPackage.VARIABLE__DOMAIN_EXPR:
				return getDomainExpr();
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
			case ModelPackage.VARIABLE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.VARIABLE__DOMAIN_EXPR:
				setDomainExpr((CalculatorExpression)newValue);
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
			case ModelPackage.VARIABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.VARIABLE__DOMAIN_EXPR:
				setDomainExpr((CalculatorExpression)null);
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
			case ModelPackage.VARIABLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.VARIABLE__DOMAIN_EXPR:
				return domainExpr != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //VariableImpl
