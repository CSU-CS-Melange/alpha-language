/**
 */
package alpha.codegen;

import alpha.model.BINARY_OP;
import alpha.model.REDUCTION_OP;
import alpha.model.UNARY_OP;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alpha Op</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see alpha.codegen.CodegenPackage#getAlphaOp()
 * @model
 * @generated
 */
public interface AlphaOp extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" oUnique="false"
	 * @generated
	 */
	C_UNARY_OP toCUnaryOp(UNARY_OP o);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" oUnique="false"
	 * @generated
	 */
	C_BINARY_OP toCBinaryOp(BINARY_OP o);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" oUnique="false"
	 * @generated
	 */
	C_REDUCTION_OP toCReductionOp(REDUCTION_OP o);

} // AlphaOp
