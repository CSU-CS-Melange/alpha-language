/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Memory Allocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.MemoryAllocation#getMacro <em>Macro</em>}</li>
 *   <li>{@link alpha.codegen.MemoryAllocation#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.codegen.MemoryAllocation#getMap <em>Map</em>}</li>
 *   <li>{@link alpha.codegen.MemoryAllocation#getDomain <em>Domain</em>}</li>
 *   <li>{@link alpha.codegen.MemoryAllocation#getISLASTNode <em>ISLAST Node</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getMemoryAllocation()
 * @model
 * @generated
 */
public interface MemoryAllocation extends Node, Visitable {
	/**
	 * Returns the value of the '<em><b>Macro</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Macro</em>' reference.
	 * @see #setMacro(MemoryMacro)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Macro()
	 * @model
	 * @generated
	 */
	MemoryMacro getMacro();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getMacro <em>Macro</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Macro</em>' reference.
	 * @see #getMacro()
	 * @generated
	 */
	void setMacro(MemoryMacro value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(ArrayVariable)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Variable()
	 * @model
	 * @generated
	 */
	ArrayVariable getVariable();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(ArrayVariable value);

	/**
	 * Returns the value of the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map</em>' attribute.
	 * @see #setMap(ISLMap)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Map()
	 * @model unique="false" dataType="alpha.codegen.ISLMap"
	 * @generated
	 */
	ISLMap getMap();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getMap <em>Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map</em>' attribute.
	 * @see #getMap()
	 * @generated
	 */
	void setMap(ISLMap value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' attribute.
	 * @see #setDomain(ISLSet)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_Domain()
	 * @model unique="false" dataType="alpha.codegen.ISLSet"
	 * @generated
	 */
	ISLSet getDomain();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getDomain <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' attribute.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(ISLSet value);

	/**
	 * Returns the value of the '<em><b>ISLAST Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ISLAST Node</em>' attribute.
	 * @see #setISLASTNode(ISLASTNode)
	 * @see alpha.codegen.CodegenPackage#getMemoryAllocation_ISLASTNode()
	 * @model unique="false" dataType="alpha.codegen.ISLASTNode"
	 * @generated
	 */
	ISLASTNode getISLASTNode();

	/**
	 * Sets the value of the '{@link alpha.codegen.MemoryAllocation#getISLASTNode <em>ISLAST Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ISLAST Node</em>' attribute.
	 * @see #getISLASTNode()
	 * @generated
	 */
	void setISLASTNode(ISLASTNode value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void card();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="alpha.codegen.ISLMap" unique="false"
	 * @generated
	 */
	ISLMap map();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // MemoryAllocation
