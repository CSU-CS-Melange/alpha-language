/**
 */
package alpha.codegen;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visitor</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see alpha.codegen.CodegenPackage#getVisitor()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Visitor extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cfUnique="false"
	 * @generated
	 */
	void visitProgram(Program cf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model iUnique="false"
	 * @generated
	 */
	void visitInclude(Include i);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void visitMacro(Macro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void visitGlobalMacro(GlobalMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void visitGlobalMemoryMacro(GlobalMemoryMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void visitStatementMacro(StatementMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vUnique="false"
	 * @generated
	 */
	void visitBaseVariable(BaseVariable v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vUnique="false"
	 * @generated
	 */
	void visitArrayVariable(ArrayVariable v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model fUnique="false"
	 * @generated
	 */
	void visitFunction(Function f);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model maUnique="false"
	 * @generated
	 */
	void visitMemoryAllocation(MemoryAllocation ma);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model csUnique="false"
	 * @generated
	 */
	void visitFunctionBody(FunctionBody cs);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model efUnique="false"
	 * @generated
	 */
	void visitEvalFunction(EvalFunction ef);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model rfUnique="false"
	 * @generated
	 */
	void visitReduceFunction(ReduceFunction rf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cgvUnique="false"
	 * @generated
	 */
	void visitGlobalVariable(GlobalVariable cgv);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cfUnique="false"
	 * @generated
	 */
	void inProgram(Program cf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model iUnique="false"
	 * @generated
	 */
	void inInclude(Include i);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void inMacro(Macro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void inGlobalMacro(GlobalMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void inGlobalMemoryMacro(GlobalMemoryMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void inStatementMacro(StatementMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vUnique="false"
	 * @generated
	 */
	void inBaseVariable(BaseVariable v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model avUnique="false"
	 * @generated
	 */
	void inArrayVariable(ArrayVariable av);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model fUnique="false"
	 * @generated
	 */
	void inFunction(Function f);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model maUnique="false"
	 * @generated
	 */
	void inMemoryAllocation(MemoryAllocation ma);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model csUnique="false"
	 * @generated
	 */
	void inFunctionBody(FunctionBody cs);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model efUnique="false"
	 * @generated
	 */
	void inEvalFunction(EvalFunction ef);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model rfUnique="false"
	 * @generated
	 */
	void inReduceFunction(ReduceFunction rf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cgvUnique="false"
	 * @generated
	 */
	void inGlobalVariable(GlobalVariable cgv);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cfUnique="false"
	 * @generated
	 */
	void outProgram(Program cf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model iUnique="false"
	 * @generated
	 */
	void outInclude(Include i);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void outMacro(Macro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void outGlobalMacro(GlobalMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void outGlobalMemoryMacro(GlobalMemoryMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model mUnique="false"
	 * @generated
	 */
	void outStatementMacro(StatementMacro m);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vUnique="false"
	 * @generated
	 */
	void outBaseVariable(BaseVariable v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model avUnique="false"
	 * @generated
	 */
	void outArrayVariable(ArrayVariable av);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model fUnique="false"
	 * @generated
	 */
	void outFunction(Function f);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model maUnique="false"
	 * @generated
	 */
	void outMemoryAllocation(MemoryAllocation ma);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model csUnique="false"
	 * @generated
	 */
	void outFunctionBody(FunctionBody cs);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model efUnique="false"
	 * @generated
	 */
	void outEvalFunction(EvalFunction ef);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model rfUnique="false"
	 * @generated
	 */
	void outReduceFunction(ReduceFunction rf);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model cgvUnique="false"
	 * @generated
	 */
	void outGlobalVariable(GlobalVariable cgv);

} // Visitor
