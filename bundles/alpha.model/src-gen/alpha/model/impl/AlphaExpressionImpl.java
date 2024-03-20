/**
 */
package alpha.model.impl;

import alpha.model.AlphaExpression;
import alpha.model.AlphaExpressionVisitor;
import alpha.model.ModelPackage;

import alpha.model.util.AlphaUtil;

import fr.irisa.cairn.jnimap.isl.ISLSet;

import java.util.List;
import java.util.Queue;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alpha Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.model.impl.AlphaExpressionImpl#getZ__internal_cache_exprDom <em>Zinternal cache expr Dom</em>}</li>
 *   <li>{@link alpha.model.impl.AlphaExpressionImpl#getZ__internal_cache_contextDom <em>Zinternal cache context Dom</em>}</li>
 *   <li>{@link alpha.model.impl.AlphaExpressionImpl#getExpressionID <em>Expression ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AlphaExpressionImpl extends AlphaNodeImpl implements AlphaExpression {
	/**
	 * The default value of the '{@link #getZ__internal_cache_exprDom() <em>Zinternal cache expr Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_exprDom()
	 * @generated
	 * @ordered
	 */
	protected static final ISLSet ZINTERNAL_CACHE_EXPR_DOM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZ__internal_cache_exprDom() <em>Zinternal cache expr Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_exprDom()
	 * @generated
	 * @ordered
	 */
	protected ISLSet z__internal_cache_exprDom = ZINTERNAL_CACHE_EXPR_DOM_EDEFAULT;

	/**
	 * The default value of the '{@link #getZ__internal_cache_contextDom() <em>Zinternal cache context Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_contextDom()
	 * @generated
	 * @ordered
	 */
	protected static final ISLSet ZINTERNAL_CACHE_CONTEXT_DOM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZ__internal_cache_contextDom() <em>Zinternal cache context Dom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getZ__internal_cache_contextDom()
	 * @generated
	 * @ordered
	 */
	protected ISLSet z__internal_cache_contextDom = ZINTERNAL_CACHE_CONTEXT_DOM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlphaExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ALPHA_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ISLSet getZ__internal_cache_exprDom() {
		return z__internal_cache_exprDom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setZ__internal_cache_exprDom(ISLSet newZ__internal_cache_exprDom) {
		ISLSet oldZ__internal_cache_exprDom = z__internal_cache_exprDom;
		z__internal_cache_exprDom = newZ__internal_cache_exprDom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM, oldZ__internal_cache_exprDom, z__internal_cache_exprDom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ISLSet getZ__internal_cache_contextDom() {
		return z__internal_cache_contextDom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setZ__internal_cache_contextDom(ISLSet newZ__internal_cache_contextDom) {
		ISLSet oldZ__internal_cache_contextDom = z__internal_cache_contextDom;
		z__internal_cache_contextDom = newZ__internal_cache_contextDom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM, oldZ__internal_cache_contextDom, z__internal_cache_contextDom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Integer> getExpressionID() {
		BasicEList<Integer> _xifexpression = null;
		EObject _eContainer = this.eContainer();
		if ((_eContainer instanceof AlphaExpression)) {
			EObject _eContainer_1 = this.eContainer();
			EList<Integer> _expressionID = ((AlphaExpression) _eContainer_1).getExpressionID();
			_xifexpression = new BasicEList<Integer>(_expressionID);
		}
		else {
			_xifexpression = new BasicEList<Integer>();
		}
		final BasicEList<Integer> parentID = _xifexpression;
		parentID.add(Integer.valueOf(this.eContainer().eContents().indexOf(this)));
		return parentID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExpressionDomain(final ISLSet dom) {
		this.setZ__internal_cache_exprDom(this.ensureDomainIsNamed(dom.coalesce()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ISLSet getExpressionDomain() {
		ISLSet _xifexpression = null;
		ISLSet _z__internal_cache_exprDom = this.getZ__internal_cache_exprDom();
		boolean _tripleNotEquals = (_z__internal_cache_exprDom != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getZ__internal_cache_exprDom().copy();
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
	@Override
	public void setContextDomain(final ISLSet dom) {
		this.setZ__internal_cache_contextDom(this.ensureDomainIsNamed(dom.coalesce()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ISLSet getContextDomain() {
		ISLSet _xifexpression = null;
		ISLSet _z__internal_cache_contextDom = this.getZ__internal_cache_contextDom();
		boolean _tripleNotEquals = (_z__internal_cache_contextDom != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getZ__internal_cache_contextDom().copy();
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
	@Override
	public AlphaExpression getExpression(final Queue<Integer> exprID) {
		AlphaExpression _xblockexpression = null;
		{
			final Integer next = exprID.poll();
			AlphaExpression _xifexpression = null;
			if ((next == null)) {
				_xifexpression = this;
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
	@Override
	public ISLSet ensureDomainIsNamed(final ISLSet dom) {
		final List<String> indexNames = dom.getIndexNames();
		final int indexCount = dom.getNbIndices();
		if (((indexNames != null) && (indexNames.size() == indexCount))) {
			return dom;
		}
		return AlphaUtil.renameIndices(dom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void accept(AlphaExpressionVisitor visitor) {
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM:
				return getZ__internal_cache_exprDom();
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM:
				return getZ__internal_cache_contextDom();
			case ModelPackage.ALPHA_EXPRESSION__EXPRESSION_ID:
				return getExpressionID();
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
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM:
				setZ__internal_cache_exprDom((ISLSet)newValue);
				return;
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM:
				setZ__internal_cache_contextDom((ISLSet)newValue);
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
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM:
				setZ__internal_cache_exprDom(ZINTERNAL_CACHE_EXPR_DOM_EDEFAULT);
				return;
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM:
				setZ__internal_cache_contextDom(ZINTERNAL_CACHE_CONTEXT_DOM_EDEFAULT);
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
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_EXPR_DOM:
				return ZINTERNAL_CACHE_EXPR_DOM_EDEFAULT == null ? z__internal_cache_exprDom != null : !ZINTERNAL_CACHE_EXPR_DOM_EDEFAULT.equals(z__internal_cache_exprDom);
			case ModelPackage.ALPHA_EXPRESSION__ZINTERNAL_CACHE_CONTEXT_DOM:
				return ZINTERNAL_CACHE_CONTEXT_DOM_EDEFAULT == null ? z__internal_cache_contextDom != null : !ZINTERNAL_CACHE_CONTEXT_DOM_EDEFAULT.equals(z__internal_cache_contextDom);
			case ModelPackage.ALPHA_EXPRESSION__EXPRESSION_ID:
				return !getExpressionID().isEmpty();
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
		result.append(" (z__internal_cache_exprDom: ");
		result.append(z__internal_cache_exprDom);
		result.append(", z__internal_cache_contextDom: ");
		result.append(z__internal_cache_contextDom);
		result.append(')');
		return result.toString();
	}

} //AlphaExpressionImpl
