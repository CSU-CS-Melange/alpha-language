/**
 */
package alpha.codegen.impl;

import alpha.codegen.CodegenPackage;
import alpha.codegen.CommentStmt;
import alpha.codegen.Function;
import alpha.codegen.Include;
import alpha.codegen.MacroStmt;
import alpha.codegen.Program;
import alpha.codegen.VariableDecl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Program</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getHeaderComment <em>Header Comment</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getIncludes <em>Includes</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getFunctionMacros <em>Function Macros</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getGlobalVariables <em>Global Variables</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getMemoryMacros <em>Memory Macros</em>}</li>
 *   <li>{@link alpha.codegen.impl.ProgramImpl#getFunctions <em>Functions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProgramImpl extends MinimalEObjectImpl.Container implements Program {
	/**
	 * The cached value of the '{@link #getHeaderComment() <em>Header Comment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderComment()
	 * @generated
	 * @ordered
	 */
	protected CommentStmt headerComment;

	/**
	 * The cached value of the '{@link #getIncludes() <em>Includes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncludes()
	 * @generated
	 * @ordered
	 */
	protected EList<Include> includes;

	/**
	 * The cached value of the '{@link #getFunctionMacros() <em>Function Macros</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionMacros()
	 * @generated
	 * @ordered
	 */
	protected EList<MacroStmt> functionMacros;

	/**
	 * The cached value of the '{@link #getGlobalVariables() <em>Global Variables</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGlobalVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableDecl> globalVariables;

	/**
	 * The cached value of the '{@link #getMemoryMacros() <em>Memory Macros</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryMacros()
	 * @generated
	 * @ordered
	 */
	protected EList<MacroStmt> memoryMacros;

	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' reference list.
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
	public CommentStmt getHeaderComment() {
		if (headerComment != null && headerComment.eIsProxy()) {
			InternalEObject oldHeaderComment = (InternalEObject)headerComment;
			headerComment = (CommentStmt)eResolveProxy(oldHeaderComment);
			if (headerComment != oldHeaderComment) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CodegenPackage.PROGRAM__HEADER_COMMENT, oldHeaderComment, headerComment));
			}
		}
		return headerComment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentStmt basicGetHeaderComment() {
		return headerComment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderComment(CommentStmt newHeaderComment) {
		CommentStmt oldHeaderComment = headerComment;
		headerComment = newHeaderComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CodegenPackage.PROGRAM__HEADER_COMMENT, oldHeaderComment, headerComment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Include> getIncludes() {
		if (includes == null) {
			includes = new EObjectResolvingEList<Include>(Include.class, this, CodegenPackage.PROGRAM__INCLUDES);
		}
		return includes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MacroStmt> getFunctionMacros() {
		if (functionMacros == null) {
			functionMacros = new EObjectResolvingEList<MacroStmt>(MacroStmt.class, this, CodegenPackage.PROGRAM__FUNCTION_MACROS);
		}
		return functionMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VariableDecl> getGlobalVariables() {
		if (globalVariables == null) {
			globalVariables = new EObjectResolvingEList<VariableDecl>(VariableDecl.class, this, CodegenPackage.PROGRAM__GLOBAL_VARIABLES);
		}
		return globalVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MacroStmt> getMemoryMacros() {
		if (memoryMacros == null) {
			memoryMacros = new EObjectResolvingEList<MacroStmt>(MacroStmt.class, this, CodegenPackage.PROGRAM__MEMORY_MACROS);
		}
		return memoryMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Function> getFunctions() {
		if (functions == null) {
			functions = new EObjectResolvingEList<Function>(Function.class, this, CodegenPackage.PROGRAM__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CodegenPackage.PROGRAM__HEADER_COMMENT:
				if (resolve) return getHeaderComment();
				return basicGetHeaderComment();
			case CodegenPackage.PROGRAM__INCLUDES:
				return getIncludes();
			case CodegenPackage.PROGRAM__FUNCTION_MACROS:
				return getFunctionMacros();
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return getGlobalVariables();
			case CodegenPackage.PROGRAM__MEMORY_MACROS:
				return getMemoryMacros();
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
			case CodegenPackage.PROGRAM__HEADER_COMMENT:
				setHeaderComment((CommentStmt)newValue);
				return;
			case CodegenPackage.PROGRAM__INCLUDES:
				getIncludes().clear();
				getIncludes().addAll((Collection<? extends Include>)newValue);
				return;
			case CodegenPackage.PROGRAM__FUNCTION_MACROS:
				getFunctionMacros().clear();
				getFunctionMacros().addAll((Collection<? extends MacroStmt>)newValue);
				return;
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				getGlobalVariables().addAll((Collection<? extends VariableDecl>)newValue);
				return;
			case CodegenPackage.PROGRAM__MEMORY_MACROS:
				getMemoryMacros().clear();
				getMemoryMacros().addAll((Collection<? extends MacroStmt>)newValue);
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
			case CodegenPackage.PROGRAM__HEADER_COMMENT:
				setHeaderComment((CommentStmt)null);
				return;
			case CodegenPackage.PROGRAM__INCLUDES:
				getIncludes().clear();
				return;
			case CodegenPackage.PROGRAM__FUNCTION_MACROS:
				getFunctionMacros().clear();
				return;
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				getGlobalVariables().clear();
				return;
			case CodegenPackage.PROGRAM__MEMORY_MACROS:
				getMemoryMacros().clear();
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
			case CodegenPackage.PROGRAM__HEADER_COMMENT:
				return headerComment != null;
			case CodegenPackage.PROGRAM__INCLUDES:
				return includes != null && !includes.isEmpty();
			case CodegenPackage.PROGRAM__FUNCTION_MACROS:
				return functionMacros != null && !functionMacros.isEmpty();
			case CodegenPackage.PROGRAM__GLOBAL_VARIABLES:
				return globalVariables != null && !globalVariables.isEmpty();
			case CodegenPackage.PROGRAM__MEMORY_MACROS:
				return memoryMacros != null && !memoryMacros.isEmpty();
			case CodegenPackage.PROGRAM__FUNCTIONS:
				return functions != null && !functions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProgramImpl
