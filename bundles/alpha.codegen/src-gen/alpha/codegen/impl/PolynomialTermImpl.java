/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.PolynomialVisitor;

import com.google.common.collect.Iterables;

import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLTerm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.xtext.xbase.lib.ExclusiveRange;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Polynomial Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.PolynomialTermImpl#getPolynomialPiece <em>Polynomial Piece</em>}</li>
 *   <li>{@link alpha.codegen.impl.PolynomialTermImpl#getIslTerm <em>Isl Term</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PolynomialTermImpl extends PolynomialNodeImpl implements PolynomialTerm {
	/**
	 * The default value of the '{@link #getIslTerm() <em>Isl Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslTerm()
	 * @generated
	 * @ordered
	 */
	protected static final ISLTerm ISL_TERM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIslTerm() <em>Isl Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslTerm()
	 * @generated
	 * @ordered
	 */
	protected ISLTerm islTerm = ISL_TERM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolynomialTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.POLYNOMIAL_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialPiece getPolynomialPiece() {
		if (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE) return null;
		return (PolynomialPiece)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolynomialPiece basicGetPolynomialPiece() {
		if (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE) return null;
		return (PolynomialPiece)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolynomialPiece(PolynomialPiece newPolynomialPiece, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPolynomialPiece, CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolynomialPiece(PolynomialPiece newPolynomialPiece) {
		if (newPolynomialPiece != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE && newPolynomialPiece != null)) {
			if (EcoreUtil.isAncestor(this, newPolynomialPiece))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPolynomialPiece != null)
				msgs = ((InternalEObject)newPolynomialPiece).eInverseAdd(this, CodegenPackage.POLYNOMIAL_PIECE__TERMS, PolynomialPiece.class, msgs);
			msgs = basicSetPolynomialPiece(newPolynomialPiece, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE, newPolynomialPiece, newPolynomialPiece));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLTerm getIslTerm() {
		return islTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIslTerm(ISLTerm newIslTerm) {
		ISLTerm oldIslTerm = islTerm;
		islTerm = newIslTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.POLYNOMIAL_TERM__ISL_TERM, oldIslTerm, islTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String value() {
		return IterableExtensions.join(this.values(), "*");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> values() {
		String _coefficient = this.coefficient();
		int _dimParam = this.dimParam();
		final Function1<Integer, Boolean> _function = new Function1<Integer, Boolean>() {
			public Boolean apply(final Integer it) {
				Integer _get = PolynomialTermImpl.this.exponents().get((it).intValue());
				return Boolean.valueOf(((_get).intValue() != 0));
			}
		};
		final Function1<Integer, String> _function_1 = new Function1<Integer, String>() {
			public String apply(final Integer it) {
				return PolynomialTermImpl.this.exponent(it);
			}
		};
		Iterable<String> _map = IterableExtensions.<Integer, String>map(IterableExtensions.<Integer>filter(new ExclusiveRange(0, _dimParam, true), _function), _function_1);
		return ECollections.<String>toEList(Iterables.<String>concat(java.util.Collections.<String>unmodifiableList(org.eclipse.xtext.xbase.lib.CollectionLiterals.<String>newArrayList(_coefficient)), _map));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String coefficient() {
		return this.getIslTerm().getCoefficientVal().toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String exponent(final int i) {
		Integer _get = this.exponents().get(i);
		final Function1<Integer, String> _function = new Function1<Integer, String>() {
			public String apply(final Integer it) {
				return PolynomialTermImpl.this.paramNames().get(i);
			}
		};
		return IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (_get).intValue(), true), _function), "*");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> exponents() {
		int _dimParam = this.dimParam();
		final Function1<Integer, Integer> _function = new Function1<Integer, Integer>() {
			public Integer apply(final Integer it) {
				return Integer.valueOf(PolynomialTermImpl.this.getIslTerm().getExponent(ISLDimType.isl_dim_param, (it).intValue()));
			}
		};
		return ECollections.<Integer>toEList(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, _dimParam, true), _function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int dimParam() {
		return this.dim(ISLDimType.isl_dim_param);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int dim(final ISLDimType dim_type) {
		return this.getPolynomialPiece().getPolynomial().getIslPolynomial().dim(dim_type);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> paramNames() {
		return this.getPolynomialPiece().getPolynomial().params();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final PolynomialVisitor visitor) {
		visitor.visitPolynomialTerm(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPolynomialPiece((PolynomialPiece)otherEnd, msgs);
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				return basicSetPolynomialPiece(null, msgs);
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.POLYNOMIAL_PIECE__TERMS, PolynomialPiece.class, msgs);
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				if (resolve) return getPolynomialPiece();
				return basicGetPolynomialPiece();
			case CodegenPackage.POLYNOMIAL_TERM__ISL_TERM:
				return getIslTerm();
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				setPolynomialPiece((PolynomialPiece)newValue);
				return;
			case CodegenPackage.POLYNOMIAL_TERM__ISL_TERM:
				setIslTerm((ISLTerm)newValue);
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				setPolynomialPiece((PolynomialPiece)null);
				return;
			case CodegenPackage.POLYNOMIAL_TERM__ISL_TERM:
				setIslTerm(ISL_TERM_EDEFAULT);
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
			case CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE:
				return basicGetPolynomialPiece() != null;
			case CodegenPackage.POLYNOMIAL_TERM__ISL_TERM:
				return ISL_TERM_EDEFAULT == null ? islTerm != null : !ISL_TERM_EDEFAULT.equals(islTerm);
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
		result.append(" (islTerm: ");
		result.append(islTerm);
		result.append(')');
		return result.toString();
	}

} //PolynomialTermImpl
