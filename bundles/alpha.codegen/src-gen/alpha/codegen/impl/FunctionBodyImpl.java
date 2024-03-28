/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.StatementMacro;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;

import alpha.codegen.show.WriteC;

import fr.irisa.cairn.jnimap.isl.ISLASTNode;

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
 * An implementation of the model object '<em><b>Function Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.FunctionBodyImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionBodyImpl#getStatementMacros <em>Statement Macros</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionBodyImpl#getISLASTNode <em>ISLAST Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionBodyImpl extends NodeImpl implements FunctionBody {
	/**
	 * The cached value of the '{@link #getStatementMacros() <em>Statement Macros</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatementMacros()
	 * @generated
	 * @ordered
	 */
	protected EList<StatementMacro> statementMacros;

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
	protected FunctionBodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.FUNCTION_BODY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function getFunction() {
		if (eContainerFeatureID() != CodegenPackage.FUNCTION_BODY__FUNCTION) return null;
		return (Function)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function basicGetFunction() {
		if (eContainerFeatureID() != CodegenPackage.FUNCTION_BODY__FUNCTION) return null;
		return (Function)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunction(Function newFunction, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFunction, CodegenPackage.FUNCTION_BODY__FUNCTION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(Function newFunction) {
		if (newFunction != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.FUNCTION_BODY__FUNCTION && newFunction != null)) {
			if (EcoreUtil.isAncestor(this, newFunction))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFunction != null)
				msgs = ((InternalEObject)newFunction).eInverseAdd(this, CodegenPackage.FUNCTION__BODY, Function.class, msgs);
			msgs = basicSetFunction(newFunction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION_BODY__FUNCTION, newFunction, newFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StatementMacro> getStatementMacros() {
		if (statementMacros == null) {
			statementMacros = new EObjectContainmentWithInverseEList<StatementMacro>(StatementMacro.class, this, CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS, CodegenPackage.STATEMENT_MACRO__FUNCTION_BODY);
		}
		return statementMacros;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION_BODY__ISLAST_NODE, oldISLASTNode, islastNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitFunctionBody(this);
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
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFunction((Function)otherEnd, msgs);
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStatementMacros()).basicAdd(otherEnd, msgs);
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				return basicSetFunction(null, msgs);
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				return ((InternalEList<?>)getStatementMacros()).basicRemove(otherEnd, msgs);
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.FUNCTION__BODY, Function.class, msgs);
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				if (resolve) return getFunction();
				return basicGetFunction();
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				return getStatementMacros();
			case CodegenPackage.FUNCTION_BODY__ISLAST_NODE:
				return getISLASTNode();
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				setFunction((Function)newValue);
				return;
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				getStatementMacros().clear();
				getStatementMacros().addAll((Collection<? extends StatementMacro>)newValue);
				return;
			case CodegenPackage.FUNCTION_BODY__ISLAST_NODE:
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				setFunction((Function)null);
				return;
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				getStatementMacros().clear();
				return;
			case CodegenPackage.FUNCTION_BODY__ISLAST_NODE:
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
			case CodegenPackage.FUNCTION_BODY__FUNCTION:
				return basicGetFunction() != null;
			case CodegenPackage.FUNCTION_BODY__STATEMENT_MACROS:
				return statementMacros != null && !statementMacros.isEmpty();
			case CodegenPackage.FUNCTION_BODY__ISLAST_NODE:
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
		result.append(" (ISLASTNode: ");
		result.append(islastNode);
		result.append(')');
		return result.toString();
	}

} //FunctionBodyImpl
