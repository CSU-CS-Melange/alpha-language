/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.Function;
import alpha.codegen.GlobalMacro;
import alpha.codegen.GlobalVariable;
import alpha.codegen.Include;
import alpha.codegen.Program;
import alpha.codegen.Visitable;
import alpha.codegen.Visitor;

import alpha.codegen.show.WriteC;

import alpha.model.AlphaSystem;
import alpha.model.Variable;

import com.google.common.base.Objects;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getSystem <em>System</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getCommonMacros <em>Common Macros</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getFunctions <em>Functions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProgramImpl extends NodeImpl implements Program {
	/**
	 * The cached value of the '{@link #getSystem() <em>System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem()
	 * @generated
	 * @ordered
	 */
	protected AlphaSystem system;

	/**
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected EList<Include> includes;

	/**
	 * The cached value of the '{@link #getCommonMacros() <em>Common Macros</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommonMacros()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalMacro> commonMacros;

	/**
	 * The cached value of the '{@link #getGlobalVariables() <em>Global Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<GlobalVariable> globalVariables;

	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> functions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProgramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CodegenPackage.Literals.PROGRAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaSystem getSystem() {
		if (system != null && system.eIsProxy()) {
			InternalEObject oldSystem = (InternalEObject)system;
			system = (AlphaSystem)eResolveProxy(oldSystem);
			if (system != oldSystem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.PROGRAM__SYSTEM, oldSystem, system));
			}
		}
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlphaSystem basicGetSystem() {
		return system;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem(AlphaSystem newSystem) {
		AlphaSystem oldSystem = system;
		system = newSystem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.PROGRAM__SYSTEM, oldSystem, system));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Include> getIncludes() {
		if (includes == null) {
			includes = new EObjectContainmentWithInverseEList<Include>(Include.class, this, CodegenPackage.PROGRAM__INCLUDES, CodegenPackage.INCLUDE__PROGRAM);
		}
		return includes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalMacro> getCommonMacros() {
		if (commonMacros == null) {
			commonMacros = new EObjectContainmentWithInverseEList<GlobalMacro>(GlobalMacro.class, this, CodegenPackage.PROGRAM__COMMON_MACROS, CodegenPackage.GLOBAL_MACRO__PROGRAM);
		}
		return commonMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GlobalVariable> getGlobalVariables() {
		if (globalVariables == null) {
			globalVariables = new EObjectContainmentWithInverseEList<GlobalVariable>(GlobalVariable.class, this, CodegenPackage.PROGRAM__GLOBAL_VARIABLES, CodegenPackage.GLOBAL_VARIABLE__PROGRAM);
		}
		return globalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Function> getFunctions() {
		if (functions == null) {
			functions = new EObjectContainmentWithInverseEList<Function>(Function.class, this, CodegenPackage.PROGRAM__FUNCTIONS, CodegenPackage.FUNCTION__PROGRAM);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalVariable getGlobalVariable(final Variable alphaVar) {
		GlobalVariable _xblockexpression = null;
		{
			final Function1<GlobalVariable, Boolean> _function = new Function1<GlobalVariable, Boolean>() {
				public Boolean apply(final GlobalVariable v) {
					Variable _alphaVariable = v.getAlphaVariable();
					return Boolean.valueOf(Objects.equal(_alphaVariable, alphaVar));
				}
			};
			final Iterable<GlobalVariable> vars = IterableExtensions.<GlobalVariable>filter(this.getGlobalVariables(), _function);
			_xblockexpression = ((GlobalVariable[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(vars, GlobalVariable.class))[0];
		}
		return _xblockexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(final Visitor visitor) {
		visitor.visitProgram(this);
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
			case CodegenPackage.PROGRAM__INCLUDES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncludes()).basicAdd(otherEnd, msgs);
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCommonMacros()).basicAdd(otherEnd, msgs);
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGlobalVariables()).basicAdd(otherEnd, msgs);
			case CodegenPackage.PROGRAM__FUNCTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFunctions()).basicAdd(otherEnd, msgs);
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
			case CodegenPackage.PROGRAM__INCLUDES:
				return ((InternalEList<?>)getIncludes()).basicRemove(otherEnd, msgs);
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				return ((InternalEList<?>)getCommonMacros()).basicRemove(otherEnd, msgs);
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return ((InternalEList<?>)getGlobalVariables()).basicRemove(otherEnd, msgs);
			case CodegenPackage.PROGRAM__FUNCTIONS:
				return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.PROGRAM__SYSTEM:
				if (resolve) return getSystem();
				return basicGetSystem();
			case CodegenPackage.PROGRAM__INCLUDES:
				return getIncludes();
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				return getCommonMacros();
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return getGlobalVariables();
			case CodegenPackage.PROGRAM__FUNCTIONS:
				return getFunctions();
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
			case CodegenPackage.PROGRAM__SYSTEM:
				setSystem((AlphaSystem)newValue);
				return;
			case CodegenPackage.PROGRAM__INCLUDES:
				getIncludes().clear();
				getIncludes().addAll((Collection<? extends Include>)newValue);
				return;
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				getCommonMacros().clear();
				getCommonMacros().addAll((Collection<? extends GlobalMacro>)newValue);
				return;
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				getGlobalVariables().addAll((Collection<? extends GlobalVariable>)newValue);
				return;
			case CodegenPackage.PROGRAM__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends Function>)newValue);
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
			case CodegenPackage.PROGRAM__SYSTEM:
				setSystem((AlphaSystem)null);
				return;
			case CodegenPackage.PROGRAM__INCLUDES:
				getIncludes().clear();
				return;
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				getCommonMacros().clear();
				return;
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				return;
			case CodegenPackage.PROGRAM__FUNCTIONS:
				getFunctions().clear();
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
			case CodegenPackage.PROGRAM__SYSTEM:
				return system != null;
			case CodegenPackage.PROGRAM__INCLUDES:
				return includes != null && !includes.isEmpty();
			case CodegenPackage.PROGRAM__COMMON_MACROS:
				return commonMacros != null && !commonMacros.isEmpty();
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return globalVariables != null && !globalVariables.isEmpty();
			case CodegenPackage.PROGRAM__FUNCTIONS:
				return functions != null && !functions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProgramImpl
