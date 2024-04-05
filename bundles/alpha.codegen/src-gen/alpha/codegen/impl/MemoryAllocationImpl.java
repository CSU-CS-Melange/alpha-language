/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;

import alpha.codegen.show.WriteC;

import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Memory Allocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.MemoryAllocationImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link alpha.codegen.impl.MemoryAllocationImpl#getMap <em>Map</em>}</li>
 *   <li>{@link alpha.codegen.impl.MemoryAllocationImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link alpha.codegen.impl.MemoryAllocationImpl#getISLASTNode <em>ISLAST Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MemoryAllocationImpl extends NodeImpl implements MemoryAllocation {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected ArrayVariable variable;

	/**
	 * The default value of the '{@link #getMap() <em>Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMap()
	 * @generated
	 * @ordered
	 */
	protected static final ISLMap MAP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMap() <em>Map</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMap()
	 * @generated
	 * @ordered
	 */
	protected ISLMap map = MAP_EDEFAULT;

	/**
	 * The default value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected static final ISLSet DOMAIN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected ISLSet domain = DOMAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getISLASTNode() <em>ISLAST Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getISLASTNode()
	 * @generated
	 * @ordered
	 */
	protected static final ISLASTNode ISLAST_NODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getISLASTNode() <em>ISLAST Node</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getISLASTNode()
	 * @generated
	 * @ordered
	 */
	protected ISLASTNode islastNode = ISLAST_NODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MemoryAllocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.MEMORY_ALLOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (ArrayVariable)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.MEMORY_ALLOCATION__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayVariable basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(ArrayVariable newVariable) {
		ArrayVariable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_ALLOCATION__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLMap getMap() {
		return map;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMap(ISLMap newMap) {
		ISLMap oldMap = map;
		map = newMap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_ALLOCATION__MAP, oldMap, map));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLSet getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(ISLSet newDomain) {
		ISLSet oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_ALLOCATION__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISLASTNode getISLASTNode() {
		return islastNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setISLASTNode(ISLASTNode newISLASTNode) {
		ISLASTNode oldISLASTNode = islastNode;
		islastNode = newISLASTNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.MEMORY_ALLOCATION__ISLAST_NODE, oldISLASTNode, islastNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitMemoryAllocation(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toSString() {
		return WriteC.<Visitable>print(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.MEMORY_ALLOCATION__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case CodegenPackage.MEMORY_ALLOCATION__MAP:
				return getMap();
			case CodegenPackage.MEMORY_ALLOCATION__DOMAIN:
				return getDomain();
			case CodegenPackage.MEMORY_ALLOCATION__ISLAST_NODE:
				return getISLASTNode();
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
			case CodegenPackage.MEMORY_ALLOCATION__VARIABLE:
				setVariable((ArrayVariable)newValue);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__MAP:
				setMap((ISLMap)newValue);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__DOMAIN:
				setDomain((ISLSet)newValue);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__ISLAST_NODE:
				setISLASTNode((ISLASTNode)newValue);
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
			case CodegenPackage.MEMORY_ALLOCATION__VARIABLE:
				setVariable((ArrayVariable)null);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__MAP:
				setMap(MAP_EDEFAULT);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__DOMAIN:
				setDomain(DOMAIN_EDEFAULT);
				return;
			case CodegenPackage.MEMORY_ALLOCATION__ISLAST_NODE:
				setISLASTNode(ISLAST_NODE_EDEFAULT);
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
			case CodegenPackage.MEMORY_ALLOCATION__VARIABLE:
				return variable != null;
			case CodegenPackage.MEMORY_ALLOCATION__MAP:
				return MAP_EDEFAULT == null ? map != null : !MAP_EDEFAULT.equals(map);
			case CodegenPackage.MEMORY_ALLOCATION__DOMAIN:
				return DOMAIN_EDEFAULT == null ? domain != null : !DOMAIN_EDEFAULT.equals(domain);
			case CodegenPackage.MEMORY_ALLOCATION__ISLAST_NODE:
				return ISLAST_NODE_EDEFAULT == null ? islastNode != null : !ISLAST_NODE_EDEFAULT.equals(islastNode);
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
		result.append(" (map: ");
		result.append(map);
		result.append(", domain: ");
		result.append(domain);
		result.append(", ISLASTNode: ");
		result.append(islastNode);
		result.append(')');
		return result.toString();
	}

} //MemoryAllocationImpl
