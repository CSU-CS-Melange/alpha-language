/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.Polynomial;
import alpha.codegen.PolynomialPiece;
import alpha.codegen.PolynomialTerm;
import alpha.codegen.PolynomialVisitor;

import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Polynomial Piece</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.PolynomialPieceImpl#getPolynomial <em>Polynomial</em>}</li>
 *   <li>{@link alpha.codegen.impl.PolynomialPieceImpl#getTerms <em>Terms</em>}</li>
 *   <li>{@link alpha.codegen.impl.PolynomialPieceImpl#getIslPiece <em>Isl Piece</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PolynomialPieceImpl extends PolynomialNodeImpl implements PolynomialPiece {
	/**
	 * The cached value of the '{@link #getTerms() <em>Terms</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerms()
	 * @generated
	 * @ordered
	 */
	protected EList<PolynomialTerm> terms;

	/**
	 * The default value of the '{@link #getIslPiece() <em>Isl Piece</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslPiece()
	 * @generated
	 * @ordered
	 */
	protected static final ISLQPolynomialPiece ISL_PIECE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIslPiece() <em>Isl Piece</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIslPiece()
	 * @generated
	 * @ordered
	 */
	protected ISLQPolynomialPiece islPiece = ISL_PIECE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PolynomialPieceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.POLYNOMIAL_PIECE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Polynomial getPolynomial() {
		if (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL) return null;
		return (Polynomial)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Polynomial basicGetPolynomial() {
		if (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL) return null;
		return (Polynomial)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPolynomial(Polynomial newPolynomial, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newPolynomial, CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPolynomial(Polynomial newPolynomial) {
		if (newPolynomial != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL && newPolynomial != null)) {
			if (EcoreUtil.isAncestor(this, newPolynomial))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPolynomial != null)
				msgs = ((InternalEObject)newPolynomial).eInverseAdd(this, CodegenPackage.POLYNOMIAL__PIECES, Polynomial.class, msgs);
			msgs = basicSetPolynomial(newPolynomial, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL, newPolynomial, newPolynomial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PolynomialTerm> getTerms() {
		if (terms == null) {
			terms = new EObjectContainmentWithInverseEList<PolynomialTerm>(PolynomialTerm.class, this, CodegenPackage.POLYNOMIAL_PIECE__TERMS, CodegenPackage.POLYNOMIAL_TERM__POLYNOMIAL_PIECE);
		}
		return terms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLQPolynomialPiece getIslPiece() {
		return islPiece;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIslPiece(ISLQPolynomialPiece newIslPiece) {
		ISLQPolynomialPiece oldIslPiece = islPiece;
		islPiece = newIslPiece;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.POLYNOMIAL_PIECE__ISL_PIECE, oldIslPiece, islPiece));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet getSet() {
		return this.getIslPiece().getSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final PolynomialVisitor visitor) {
		visitor.visitPolynomialPiece(this);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetPolynomial((Polynomial)otherEnd, msgs);
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTerms()).basicAdd(otherEnd, msgs);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				return basicSetPolynomial(null, msgs);
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				return ((InternalEList<?>)getTerms()).basicRemove(otherEnd, msgs);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.POLYNOMIAL__PIECES, Polynomial.class, msgs);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				if (resolve) return getPolynomial();
				return basicGetPolynomial();
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				return getTerms();
			case CodegenPackage.POLYNOMIAL_PIECE__ISL_PIECE:
				return getIslPiece();
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				setPolynomial((Polynomial)newValue);
				return;
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				getTerms().clear();
				getTerms().addAll((Collection<? extends PolynomialTerm>)newValue);
				return;
			case CodegenPackage.POLYNOMIAL_PIECE__ISL_PIECE:
				setIslPiece((ISLQPolynomialPiece)newValue);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				setPolynomial((Polynomial)null);
				return;
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				getTerms().clear();
				return;
			case CodegenPackage.POLYNOMIAL_PIECE__ISL_PIECE:
				setIslPiece(ISL_PIECE_EDEFAULT);
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
			case CodegenPackage.POLYNOMIAL_PIECE__POLYNOMIAL:
				return basicGetPolynomial() != null;
			case CodegenPackage.POLYNOMIAL_PIECE__TERMS:
				return terms != null && !terms.isEmpty();
			case CodegenPackage.POLYNOMIAL_PIECE__ISL_PIECE:
				return ISL_PIECE_EDEFAULT == null ? islPiece != null : !ISL_PIECE_EDEFAULT.equals(islPiece);
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
		result.append(" (islPiece: ");
		result.append(islPiece);
		result.append(')');
		return result.toString();
	}

} //PolynomialPieceImpl
