/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.OmpStmt;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Omp Stmt</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.OmpStmtImpl#getPrivateVars <em>Private Vars</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OmpStmtImpl extends MinimalEObjectImpl.Container implements OmpStmt {
	/**
	 * The cached value of the '{@link #getPrivateVars() <em>Private Vars</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateVars()
	 * @generated
	 * @ordered
	 */
	protected EList<String> privateVars;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OmpStmtImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.OMP_STMT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPrivateVars() {
		if (privateVars == null) {
			privateVars = new EDataTypeEList<String>(String.class, this, CodegenPackage.OMP_STMT__PRIVATE_VARS);
		}
		return privateVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.OMP_STMT__PRIVATE_VARS:
				return getPrivateVars();
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
			case CodegenPackage.OMP_STMT__PRIVATE_VARS:
				getPrivateVars().clear();
				getPrivateVars().addAll((Collection<? extends String>)newValue);
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
			case CodegenPackage.OMP_STMT__PRIVATE_VARS:
				getPrivateVars().clear();
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
			case CodegenPackage.OMP_STMT__PRIVATE_VARS:
				return privateVars != null && !privateVars.isEmpty();
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
		result.append(" (privateVars: ");
		result.append(privateVars);
		result.append(')');
		return result.toString();
	}

} //OmpStmtImpl
