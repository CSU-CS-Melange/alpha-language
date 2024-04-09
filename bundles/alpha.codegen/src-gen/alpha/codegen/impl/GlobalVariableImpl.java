/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.GlobalMemoryMacro;
import alpha.codegen.GlobalVariable;
import alpha.codegen.Program;
import alpha.codegen.VariableType;
import alpha.codegen.Visitor;

import alpha.model.Variable;

import alpha.model.util.AlphaUtil;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.xtext.xbase.lib.ExclusiveRange;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Global Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#getProgram <em>Program</em>}</li>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#getMemoryMacro <em>Memory Macro</em>}</li>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#getAlphaVariable <em>Alpha Variable</em>}</li>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#getType <em>Type</em>}</li>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#getNumDims <em>Num Dims</em>}</li>
 *   <li>{@link alpha.codegen.impl.GlobalVariableImpl#isFlagVariable <em>Flag Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GlobalVariableImpl extends BaseVariableImpl implements GlobalVariable {
	/**
	 * The cached value of the '{@link #getMemoryMacro() <em>Memory Macro</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryMacro()
	 * @generated
	 * @ordered
	 */
	protected GlobalMemoryMacro memoryMacro;

	/**
	 * The cached value of the '{@link #getAlphaVariable() <em>Alpha Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlphaVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable alphaVariable;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final VariableType TYPE_EDEFAULT = VariableType.INPUT;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected VariableType type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumDims() <em>Num Dims</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumDims()
	 * @generated
	 * @ordered
	 */
	protected static final int NUM_DIMS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumDims() <em>Num Dims</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumDims()
	 * @generated
	 * @ordered
	 */
	protected int numDims = NUM_DIMS_EDEFAULT;

	/**
	 * The default value of the '{@link #isFlagVariable() <em>Flag Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlagVariable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FLAG_VARIABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFlagVariable() <em>Flag Variable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFlagVariable()
	 * @generated
	 * @ordered
	 */
	protected boolean flagVariable = FLAG_VARIABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GlobalVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.GLOBAL_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program getProgram() {
		if (eContainerFeatureID() != CodegenPackage.GLOBAL_VARIABLE__PROGRAM) return null;
		return (Program)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Program basicGetProgram() {
		if (eContainerFeatureID() != CodegenPackage.GLOBAL_VARIABLE__PROGRAM) return null;
		return (Program)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProgram(Program newProgram, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newProgram, CodegenPackage.GLOBAL_VARIABLE__PROGRAM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProgram(Program newProgram) {
		if (newProgram != eInternalContainer() || (eContainerFeatureID() != CodegenPackage.GLOBAL_VARIABLE__PROGRAM && newProgram != null)) {
			if (EcoreUtil.isAncestor(this, newProgram))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProgram != null)
				msgs = ((InternalEObject)newProgram).eInverseAdd(this, CodegenPackage.PROGRAM__GLOBAL_VARIABLES, Program.class, msgs);
			msgs = basicSetProgram(newProgram, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__PROGRAM, newProgram, newProgram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalMemoryMacro getMemoryMacro() {
		return memoryMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMemoryMacro(GlobalMemoryMacro newMemoryMacro, NotificationChain msgs) {
		GlobalMemoryMacro oldMemoryMacro = memoryMacro;
		memoryMacro = newMemoryMacro;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO, oldMemoryMacro, newMemoryMacro);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryMacro(GlobalMemoryMacro newMemoryMacro) {
		if (newMemoryMacro != memoryMacro) {
			NotificationChain msgs = null;
			if (memoryMacro != null)
				msgs = ((InternalEObject)memoryMacro).eInverseRemove(this, CodegenPackage.GLOBAL_MEMORY_MACRO__VARIABLE, GlobalMemoryMacro.class, msgs);
			if (newMemoryMacro != null)
				msgs = ((InternalEObject)newMemoryMacro).eInverseAdd(this, CodegenPackage.GLOBAL_MEMORY_MACRO__VARIABLE, GlobalMemoryMacro.class, msgs);
			msgs = basicSetMemoryMacro(newMemoryMacro, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO, newMemoryMacro, newMemoryMacro));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getAlphaVariable() {
		if (alphaVariable != null && alphaVariable.eIsProxy()) {
			InternalEObject oldAlphaVariable = (InternalEObject)alphaVariable;
			alphaVariable = (Variable)eResolveProxy(oldAlphaVariable);
			if (alphaVariable != oldAlphaVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE, oldAlphaVariable, alphaVariable));
			}
		}
		return alphaVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetAlphaVariable() {
		return alphaVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlphaVariable(Variable newAlphaVariable) {
		Variable oldAlphaVariable = alphaVariable;
		alphaVariable = newAlphaVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE, oldAlphaVariable, alphaVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(VariableType newType) {
		VariableType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumDims() {
		return numDims;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumDims(int newNumDims) {
		int oldNumDims = numDims;
		numDims = newNumDims;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__NUM_DIMS, oldNumDims, numDims));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFlagVariable() {
		return flagVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFlagVariable(boolean newFlagVariable) {
		boolean oldFlagVariable = flagVariable;
		flagVariable = newFlagVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.GLOBAL_VARIABLE__FLAG_VARIABLE, oldFlagVariable, flagVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasAlphaVariable() {
		Variable _alphaVariable = this.getAlphaVariable();
		return (_alphaVariable != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasMemoryMacro() {
		GlobalMemoryMacro _memoryMacro = this.getMemoryMacro();
		return (_memoryMacro != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String pointers() {
		int _numDims = this.getNumDims();
		final Function1<Integer, String> _function = new Function1<Integer, String>() {
			public String apply(final Integer it) {
				return "*";
			}
		};
		return IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _numDims, true), _function), "");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String dataType() {
		String _dataType = super.dataType();
		String _pointers = this.pointers();
		return (_dataType + _pointers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String readName() {
		String _xifexpression = null;
		Boolean _isOutput = this.getAlphaVariable().isOutput();
		if ((_isOutput).booleanValue()) {
			String _name = this.getName();
			_xifexpression = ("eval_" + _name);
		}
		else {
			_xifexpression = this.getName();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> params() {
		return ECollections.<String>toEList(AlphaUtil.params(this.getAlphaVariable()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> indices() {
		return ECollections.<String>toEList(AlphaUtil.indices(this.getAlphaVariable()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String identityAccess() {
		String _name = this.getName();
		String _plus = (_name + "(");
		String _join = IterableExtensions.join(this.indices(), ",");
		String _plus_1 = (_plus + _join);
		return (_plus_1 + ")");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String writeName() {
		return this.getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitGlobalVariable(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetProgram((Program)otherEnd, msgs);
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				if (memoryMacro != null)
					msgs = ((InternalEObject)memoryMacro).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO, null, msgs);
				return basicSetMemoryMacro((GlobalMemoryMacro)otherEnd, msgs);
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				return basicSetProgram(null, msgs);
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				return basicSetMemoryMacro(null, msgs);
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				return eInternalContainer().eInverseRemove(this, CodegenPackage.PROGRAM__GLOBAL_VARIABLES, Program.class, msgs);
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				if (resolve) return getProgram();
				return basicGetProgram();
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				return getMemoryMacro();
			case CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE:
				if (resolve) return getAlphaVariable();
				return basicGetAlphaVariable();
			case CodegenPackage.GLOBAL_VARIABLE__TYPE:
				return getType();
			case CodegenPackage.GLOBAL_VARIABLE__NUM_DIMS:
				return getNumDims();
			case CodegenPackage.GLOBAL_VARIABLE__FLAG_VARIABLE:
				return isFlagVariable();
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				setProgram((Program)newValue);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				setMemoryMacro((GlobalMemoryMacro)newValue);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE:
				setAlphaVariable((Variable)newValue);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__TYPE:
				setType((VariableType)newValue);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__NUM_DIMS:
				setNumDims((Integer)newValue);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__FLAG_VARIABLE:
				setFlagVariable((Boolean)newValue);
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				setProgram((Program)null);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				setMemoryMacro((GlobalMemoryMacro)null);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE:
				setAlphaVariable((Variable)null);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__NUM_DIMS:
				setNumDims(NUM_DIMS_EDEFAULT);
				return;
			case CodegenPackage.GLOBAL_VARIABLE__FLAG_VARIABLE:
				setFlagVariable(FLAG_VARIABLE_EDEFAULT);
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
			case CodegenPackage.GLOBAL_VARIABLE__PROGRAM:
				return basicGetProgram() != null;
			case CodegenPackage.GLOBAL_VARIABLE__MEMORY_MACRO:
				return memoryMacro != null;
			case CodegenPackage.GLOBAL_VARIABLE__ALPHA_VARIABLE:
				return alphaVariable != null;
			case CodegenPackage.GLOBAL_VARIABLE__TYPE:
				return type != TYPE_EDEFAULT;
			case CodegenPackage.GLOBAL_VARIABLE__NUM_DIMS:
				return numDims != NUM_DIMS_EDEFAULT;
			case CodegenPackage.GLOBAL_VARIABLE__FLAG_VARIABLE:
				return flagVariable != FLAG_VARIABLE_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(", numDims: ");
		result.append(numDims);
		result.append(", flagVariable: ");
		result.append(flagVariable);
		result.append(')');
		return result.toString();
	}

} //GlobalVariableImpl
