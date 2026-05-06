/**
 */
package alpha.codegen;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Omp Stmt</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * * Inserts an omp statement.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.OmpStmt#getPrivateVars <em>Private Vars</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getOmpStmt()
 * @model
 * @generated
 */
public interface OmpStmt extends Statement {
	/**
	 * Returns the value of the '<em><b>Private Vars</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private Vars</em>' attribute list.
	 * @see alpha.codegen.CodegenPackage#getOmpStmt_PrivateVars()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getPrivateVars();

} // OmpStmt
