/**
 */
package alpha.codegen.impl;

import alpha.codegen.ArrayVariable;
import alpha.codegen.BaseVariable;
import alpha.codegen.CodegenPackage;
import alpha.codegen.DataType;
import alpha.codegen.Function;
import alpha.codegen.FunctionBody;
import alpha.codegen.MemoryAllocation;
import alpha.codegen.Program;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;

import alpha.codegen.show.WriteC;

import alpha.model.AlphaSystem;

import com.google.common.collect.Iterables;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getProgram <em>Program</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getName <em>Name</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getScalarArgs <em>Scalar Args</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getArrayArgs <em>Array Args</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getMemoryAllocations <em>Memory Allocations</em>}</li>
 *   <li>{@link alpha.codegen.impl.FunctionImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionImpl extends NodeImpl implements Function {
	/**
	 * The default value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected static final DataType RETURN_TYPE_EDEFAULT = DataType.INT;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected DataType returnType = RETURN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getScalarArgs() <em>Scalar Args</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScalarArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<BaseVariable> scalarArgs;

	/**
	 * The cached value of the '{@link #getArrayArgs() <em>Array Args</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<ArrayVariable> arrayArgs;

	/**
	 * The cached value of the '{@link #getMemoryAllocations() <em>Memory Allocations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryAllocations()
	 * @generated
	 * @ordered
	 */
	protected EList<MemoryAllocation> memoryAllocations;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected FunctionBody body;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program getProgram() {
		if (eContainerFeatureID() != CodegenPackage.FUNCTION__PROGRAM) return null;
		return (Program)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program basicGetProgram() {
		if (eContainerFeatureID() != CodegenPackage.FUNCTION__PROGRAM) return null;
		return (Program)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProgram(Program newProgram, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProgram, CodegenPackage.FUNCTION__PROGRAM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProgram(Program newProgram) {
		if (newProgram != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.FUNCTION__PROGRAM && newProgram != null)) {
			if (EcoreUtil.isAncestor(this, newProgram))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProgram != null)
				msgs = ((InternalEObject)newProgram).eInverseAdd(this, CodegenPackage.PROGRAM__FUNCTIONS, Program.class, msgs);
			msgs = basicSetProgram(newProgram, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION__PROGRAM, newProgram, newProgram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(DataType newReturnType) {
		DataType oldReturnType = returnType;
		returnType = newReturnType == null ? RETURN_TYPE_EDEFAULT : newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BaseVariable> getScalarArgs() {
		if (scalarArgs == null) {
			scalarArgs = new EObjectResolvingEList<BaseVariable>(BaseVariable.class, this, CodegenPackage.FUNCTION__SCALAR_ARGS);
		}
		return scalarArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArrayVariable> getArrayArgs() {
		if (arrayArgs == null) {
			arrayArgs = new EObjectResolvingEList<ArrayVariable>(ArrayVariable.class, this, CodegenPackage.FUNCTION__ARRAY_ARGS);
		}
		return arrayArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MemoryAllocation> getMemoryAllocations() {
		if (memoryAllocations == null) {
			memoryAllocations = new EObjectResolvingEList<MemoryAllocation>(MemoryAllocation.class, this, CodegenPackage.FUNCTION__MEMORY_ALLOCATIONS);
		}
		return memoryAllocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionBody getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(FunctionBody newBody, NotificationChain msgs) {
		FunctionBody oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(FunctionBody newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, CodegenPackage.FUNCTION_BODY__FUNCTION, FunctionBody.class, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, CodegenPackage.FUNCTION_BODY__FUNCTION, FunctionBody.class, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.FUNCTION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BaseVariable> args() {
		EList<BaseVariable> _scalarArgs = this.getScalarArgs();
		EList<ArrayVariable> _arrayArgs = this.getArrayArgs();
		return ECollections.<BaseVariable>toEList(Iterables.<BaseVariable>concat(_scalarArgs, _arrayArgs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitFunction(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaSystem system() {
		return this.getProgram().getSystem();
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
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.FUNCTION__PROGRAM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProgram((Program)otherEnd, msgs);
			case CodegenPackage.FUNCTION__BODY:
				if (body != null)
					msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodegenPackage.FUNCTION__BODY, null, msgs);
				return basicSetBody((FunctionBody)otherEnd, msgs);
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
			case CodegenPackage.FUNCTION__PROGRAM:
				return basicSetProgram(null, msgs);
			case CodegenPackage.FUNCTION__BODY:
				return basicSetBody(null, msgs);
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
			case CodegenPackage.FUNCTION__PROGRAM:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.PROGRAM__FUNCTIONS, Program.class, msgs);
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
			case CodegenPackage.FUNCTION__PROGRAM:
				if (resolve) return getProgram();
				return basicGetProgram();
			case CodegenPackage.FUNCTION__RETURN_TYPE:
				return getReturnType();
			case CodegenPackage.FUNCTION__NAME:
				return getName();
			case CodegenPackage.FUNCTION__SCALAR_ARGS:
				return getScalarArgs();
			case CodegenPackage.FUNCTION__ARRAY_ARGS:
				return getArrayArgs();
			case CodegenPackage.FUNCTION__MEMORY_ALLOCATIONS:
				return getMemoryAllocations();
			case CodegenPackage.FUNCTION__BODY:
				return getBody();
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
			case CodegenPackage.FUNCTION__PROGRAM:
				setProgram((Program)newValue);
				return;
			case CodegenPackage.FUNCTION__RETURN_TYPE:
				setReturnType((DataType)newValue);
				return;
			case CodegenPackage.FUNCTION__NAME:
				setName((String)newValue);
				return;
			case CodegenPackage.FUNCTION__SCALAR_ARGS:
				getScalarArgs().clear();
				getScalarArgs().addAll((Collection<? extends BaseVariable>)newValue);
				return;
			case CodegenPackage.FUNCTION__ARRAY_ARGS:
				getArrayArgs().clear();
				getArrayArgs().addAll((Collection<? extends ArrayVariable>)newValue);
				return;
			case CodegenPackage.FUNCTION__MEMORY_ALLOCATIONS:
				getMemoryAllocations().clear();
				getMemoryAllocations().addAll((Collection<? extends MemoryAllocation>)newValue);
				return;
			case CodegenPackage.FUNCTION__BODY:
				setBody((FunctionBody)newValue);
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
			case CodegenPackage.FUNCTION__PROGRAM:
				setProgram((Program)null);
				return;
			case CodegenPackage.FUNCTION__RETURN_TYPE:
				setReturnType(RETURN_TYPE_EDEFAULT);
				return;
			case CodegenPackage.FUNCTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CodegenPackage.FUNCTION__SCALAR_ARGS:
				getScalarArgs().clear();
				return;
			case CodegenPackage.FUNCTION__ARRAY_ARGS:
				getArrayArgs().clear();
				return;
			case CodegenPackage.FUNCTION__MEMORY_ALLOCATIONS:
				getMemoryAllocations().clear();
				return;
			case CodegenPackage.FUNCTION__BODY:
				setBody((FunctionBody)null);
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
			case CodegenPackage.FUNCTION__PROGRAM:
				return basicGetProgram() != null;
			case CodegenPackage.FUNCTION__RETURN_TYPE:
				return returnType != RETURN_TYPE_EDEFAULT;
			case CodegenPackage.FUNCTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CodegenPackage.FUNCTION__SCALAR_ARGS:
				return scalarArgs != null && !scalarArgs.isEmpty();
			case CodegenPackage.FUNCTION__ARRAY_ARGS:
				return arrayArgs != null && !arrayArgs.isEmpty();
			case CodegenPackage.FUNCTION__MEMORY_ALLOCATIONS:
				return memoryAllocations != null && !memoryAllocations.isEmpty();
			case CodegenPackage.FUNCTION__BODY:
				return body != null;
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
		result.append(" (returnType: ");
		result.append(returnType);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //FunctionImpl
