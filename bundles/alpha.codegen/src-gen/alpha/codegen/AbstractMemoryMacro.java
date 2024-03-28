/**
 */
package alpha.codegen;

import fr.irisa.cairn.jnimap.isl.ISLMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Memory Macro</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.AbstractMemoryMacro#getMap <em>Map</em>}</li>
 * </ul>
 *
 * @see alpha.codegen.CodegenPackage#getAbstractMemoryMacro()
 * @model abstract="true"
 * @generated
 */
public interface AbstractMemoryMacro extends Macro {
	/**
	 * Returns the value of the '<em><b>Map</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map</em>' attribute.
	 * @see #setMap(ISLMap)
	 * @see alpha.codegen.CodegenPackage#getAbstractMemoryMacro_Map()
	 * @model unique="false" dataType="alpha.codegen.ISLMap"
	 * @generated
	 */
	ISLMap getMap();

	/**
	 * Sets the value of the '{@link alpha.codegen.AbstractMemoryMacro#getMap <em>Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map</em>' attribute.
	 * @see #getMap()
	 * @generated
	 */
	void setMap(ISLMap value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model visitorUnique="false"
	 * @generated
	 */
	void accept(Visitor visitor);

} // AbstractMemoryMacro
