/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.Visitor;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Array Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ArrayVariableImpl extends GlobalVariableImpl implements ArrayVariable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArrayVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.ARRAY_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitArrayVariable(this);
	}

} //ArrayVariableImpl
