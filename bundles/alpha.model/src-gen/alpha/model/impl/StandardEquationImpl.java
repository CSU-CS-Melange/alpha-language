/**
 */
package alpha.model.impl;

import alpha.model.AlphaExpression;
import alpha.model.AlphaVisitor;
import alpha.model.ModelPackage;
import alpha.model.StandardEquation;
import alpha.model.Variable;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Standard Equation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.StandardEquationImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.model.impl.StandardEquationImpl#getIndexNames <em>Index Names</em>}</li>
 *   <li>{@link alpha.model.impl.StandardEquationImpl#getExpr <em>Expr</em>}</li>
 *   <li>{@link alpha.model.impl.StandardEquationImpl#getZ__explored <em>Zexplored</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StandardEquationImpl extends EquationImpl implements StandardEquation {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable variable;

	/**
	 * The cached value of the '{@link #getIndexNames() <em>Index Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> indexNames;

	/**
	 * The cached value of the '{@link #getExpr() <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpr()
	 * @generated
	 * @ordered
	 */
	protected AlphaExpression expr;

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
	protected StandardEquationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.STANDARD_EQUATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (Variable)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.STANDARD_EQUATION__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(Variable newVariable) {
		Variable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STANDARD_EQUATION__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getIndexNames() {
		if (indexNames == null) {
			indexNames = new EDataTypeEList<String>(String.class, this, ModelPackage.STANDARD_EQUATION__INDEX_NAMES);
		}
		return indexNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaExpression getExpr() {
		return expr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpr(AlphaExpression newExpr, NotificationChain msgs) {
		AlphaExpression oldExpr = expr;
		expr = newExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.STANDARD_EQUATION__EXPR, oldExpr, newExpr);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpr(AlphaExpression newExpr) {
		if (newExpr != expr) {
			NotificationChain msgs = null;
			if (expr != null)
				msgs = ((InternalEObject)expr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.STANDARD_EQUATION__EXPR, null, msgs);
			if (newExpr != null)
				msgs = ((InternalEObject)newExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.STANDARD_EQUATION__EXPR, null, msgs);
			msgs = basicSetExpr(newExpr, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STANDARD_EQUATION__EXPR, newExpr, newExpr));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.STANDARD_EQUATION__ZEXPLORED, oldZ__explored, z__explored));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		String _xifexpression = null;
		Variable _variable = this.getVariable();
		boolean _tripleNotEquals = (_variable != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getVariable().getName();
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final AlphaVisitor visitor) {
		visitor.visitStandardEquation(this);
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
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.STANDARD_EQUATION__EXPR:
				return basicSetExpr(null, msgs);
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
			case ModelPackage.STANDARD_EQUATION__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case ModelPackage.STANDARD_EQUATION__INDEX_NAMES:
				return getIndexNames();
			case ModelPackage.STANDARD_EQUATION__EXPR:
				return getExpr();
			case ModelPackage.STANDARD_EQUATION__ZEXPLORED:
				return getZ__explored();
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
			case ModelPackage.STANDARD_EQUATION__VARIABLE:
				setVariable((Variable)newValue);
				return;
			case ModelPackage.STANDARD_EQUATION__INDEX_NAMES:
				getIndexNames().clear();
				getIndexNames().addAll((Collection<? extends String>)newValue);
				return;
			case ModelPackage.STANDARD_EQUATION__EXPR:
				setExpr((AlphaExpression)newValue);
				return;
			case ModelPackage.STANDARD_EQUATION__ZEXPLORED:
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
			case ModelPackage.STANDARD_EQUATION__VARIABLE:
				setVariable((Variable)null);
				return;
			case ModelPackage.STANDARD_EQUATION__INDEX_NAMES:
				getIndexNames().clear();
				return;
			case ModelPackage.STANDARD_EQUATION__EXPR:
				setExpr((AlphaExpression)null);
				return;
			case ModelPackage.STANDARD_EQUATION__ZEXPLORED:
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
			case ModelPackage.STANDARD_EQUATION__VARIABLE:
				return variable != null;
			case ModelPackage.STANDARD_EQUATION__INDEX_NAMES:
				return indexNames != null && !indexNames.isEmpty();
			case ModelPackage.STANDARD_EQUATION__EXPR:
				return expr != null;
			case ModelPackage.STANDARD_EQUATION__ZEXPLORED:
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
		result.append(" (indexNames: ");
		result.append(indexNames);
		result.append(", z__explored: ");
		result.append(z__explored);
		result.append(')');
		return result.toString();
	}

} //StandardEquationImpl
