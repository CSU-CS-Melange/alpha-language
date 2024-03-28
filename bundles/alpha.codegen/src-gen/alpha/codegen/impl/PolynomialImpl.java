/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialVisitor;

import alpha.codegen.polynomial.PolynomialPrinter;

import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.ExclusiveRange;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Polynomial</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.PolynomialImpl#getPieces <em>Pieces</em>}</li>
 *   <li>{@link alpha.codegen.impl.PolynomialImpl#getIslPolynomial <em>Isl Polynomial</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PolynomialImpl extends PolynomialNodeImpl implements Polynomial {
	/**
	 * The cached value of the '{@link #getPieces() <em>Pieces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPieces()
	 * @generated
	 * @ordered
	 */
	protected EList<PolynomialPiece> pieces;

	/**
	 * The default value of the '{@link #getIslPolynomial() <em>Isl Polynomial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslPolynomial()
	 * @generated
	 * @ordered
	 */
	protected static final ISLPWQPolynomial ISL_POLYNOMIAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIslPolynomial() <em>Isl Polynomial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslPolynomial()
	 * @generated
	 * @ordered
	 */
	protected ISLPWQPolynomial islPolynomial = ISL_POLYNOMIAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolynomialImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.POLYNOMIAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PolynomialPiece> getPieces() {
		if (pieces == null) {
			pieces = new EObjectContainmentWithInverseEList<PolynomialPiece>(PolynomialPiece.class, this, CodegenPackage.POLYNOMIAL__PIECES, CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL);
		}
		return pieces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLPWQPolynomial getIslPolynomial() {
		return islPolynomial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIslPolynomial(ISLPWQPolynomial newIslPolynomial) {
		ISLPWQPolynomial oldIslPolynomial = islPolynomial;
		islPolynomial = newIslPolynomial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.POLYNOMIAL__ISL_POLYNOMIAL, oldIslPolynomial, islPolynomial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toCString(final String variableName) {
		return PolynomialPrinter.print(this, variableName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> params() {
		EList<String> _xblockexpression = null;
		{
			final int P = this.getIslPolynomial().getSpace().dim(ISLDimType.isl_dim_param);
			final Function1<Integer, String> _function = new Function1<Integer, String>() {
				public String apply(final Integer it) {
					return PolynomialImpl.this.getIslPolynomial().getSpace().getDimName(ISLDimType.isl_dim_param, (it).intValue());
				}
			};
			_xblockexpression = ECollections.<String>toEList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, P, true), _function));
		}
		return _xblockexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final PolynomialVisitor visitor) {
		visitor.visitPolynomial(this);
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPieces()).basicAdd(otherEnd, msgs);
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				return ((InternalEList<?>)getPieces()).basicRemove(otherEnd, msgs);
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				return getPieces();
			case CodegenPackage.POLYNOMIAL__ISL_POLYNOMIAL:
				return getIslPolynomial();
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				getPieces().clear();
				getPieces().addAll((Collection<? extends PolynomialPiece>)newValue);
				return;
			case CodegenPackage.POLYNOMIAL__ISL_POLYNOMIAL:
				setIslPolynomial((ISLPWQPolynomial)newValue);
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				getPieces().clear();
				return;
			case CodegenPackage.POLYNOMIAL__ISL_POLYNOMIAL:
				setIslPolynomial(ISL_POLYNOMIAL_EDEFAULT);
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
			case CodegenPackage.POLYNOMIAL__PIECES:
				return pieces != null && !pieces.isEmpty();
			case CodegenPackage.POLYNOMIAL__ISL_POLYNOMIAL:
				return ISL_POLYNOMIAL_EDEFAULT == null ? islPolynomial != null : !ISL_POLYNOMIAL_EDEFAULT.equals(islPolynomial);
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
		result.append(" (islPolynomial: ");
		result.append(islPolynomial);
		result.append(')');
		return result.toString();
	}

} //PolynomialImpl
