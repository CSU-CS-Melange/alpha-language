/**
 */
package alpha.model.matrix.impl;

import alpha.model.matrix.Matrix;
import alpha.model.matrix.MatrixFactory;
import alpha.model.matrix.MatrixPackage;
import alpha.model.matrix.MatrixRow;
import alpha.model.matrix.Space;

import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSpace;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MatrixPackageImpl extends EPackageImpl implements MatrixPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matrixEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matrixRowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass spaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislSpaceEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType jniislMultiAffEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType longMatrixEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see alpha.model.matrix.MatrixPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MatrixPackageImpl() {
		super(eNS_URI, MatrixFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link MatrixPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MatrixPackage init() {
		if (isInited) return (MatrixPackage)EPackage.Registry.INSTANCE.getEPackage(MatrixPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMatrixPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MatrixPackageImpl theMatrixPackage = registeredMatrixPackage instanceof MatrixPackageImpl ? (MatrixPackageImpl)registeredMatrixPackage : new MatrixPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMatrixPackage.createPackageContents();

		// Initialize created meta-data
		theMatrixPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMatrixPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MatrixPackage.eNS_URI, theMatrixPackage);
		return theMatrixPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatrix() {
		return matrixEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatrix_Space() {
		return (EReference)matrixEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatrix_Rows() {
		return (EReference)matrixEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMatrix_LinearPartOnly() {
		return (EAttribute)matrixEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatrixRow() {
		return matrixRowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMatrixRow_Values() {
		return (EAttribute)matrixRowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpace() {
		return spaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSpace_ParamNames() {
		return (EAttribute)spaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSpace_IndexNames() {
		return (EAttribute)spaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLSpace() {
		return jniislSpaceEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getJNIISLMultiAff() {
		return jniislMultiAffEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getLongMatrix() {
		return longMatrixEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MatrixFactory getMatrixFactory() {
		return (MatrixFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		matrixEClass = createEClass(MATRIX);
		createEReference(matrixEClass, MATRIX__SPACE);
		createEReference(matrixEClass, MATRIX__ROWS);
		createEAttribute(matrixEClass, MATRIX__LINEAR_PART_ONLY);

		matrixRowEClass = createEClass(MATRIX_ROW);
		createEAttribute(matrixRowEClass, MATRIX_ROW__VALUES);

		spaceEClass = createEClass(SPACE);
		createEAttribute(spaceEClass, SPACE__PARAM_NAMES);
		createEAttribute(spaceEClass, SPACE__INDEX_NAMES);

		// Create data types
		jniislSpaceEDataType = createEDataType(JNIISL_SPACE);
		jniislMultiAffEDataType = createEDataType(JNIISL_MULTI_AFF);
		longMatrixEDataType = createEDataType(LONG_MATRIX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(matrixEClass, Matrix.class, "Matrix", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMatrix_Space(), this.getSpace(), null, "space", null, 0, 1, Matrix.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMatrix_Rows(), this.getMatrixRow(), null, "rows", null, 0, -1, Matrix.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatrix_LinearPartOnly(), theEcorePackage.getEBoolean(), "linearPartOnly", "false", 0, 1, Matrix.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEBoolean(), "isConsistent", 0, 1, !IS_UNIQUE, IS_ORDERED);

		EOperation op = addEOperation(matrixEClass, theEcorePackage.getELong(), "getValue", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "r", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "c", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(matrixEClass, null, "setValue", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "r", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "c", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getELong(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEInt(), "getNbRows", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEInt(), "getNbColumns", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEInt(), "getNbParams", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEInt(), "getNbIndices", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEString(), "getParamNames", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEString(), "getIndexNames", 0, -1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, this.getLongMatrix(), "toArray", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixEClass, this.getJNIISLMultiAff(), "toMultiAff", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(matrixRowEClass, MatrixRow.class, "MatrixRow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMatrixRow_Values(), theEcorePackage.getELong(), "values", null, 0, -1, MatrixRow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(matrixRowEClass, theEcorePackage.getELong(), "getValue", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "c", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(matrixRowEClass, null, "setValue", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "c", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getELong(), "v", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(matrixRowEClass, theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(spaceEClass, Space.class, "Space", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSpace_ParamNames(), theEcorePackage.getEString(), "paramNames", null, 0, -1, Space.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSpace_IndexNames(), theEcorePackage.getEString(), "indexNames", null, 0, -1, Space.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(spaceEClass, theEcorePackage.getEInt(), "getNbParams", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(spaceEClass, theEcorePackage.getEInt(), "getNbIndices", 0, 1, !IS_UNIQUE, IS_ORDERED);

		addEOperation(spaceEClass, this.getJNIISLSpace(), "toJNIISLSetSpace", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = addEOperation(spaceEClass, this.getJNIISLSpace(), "toJNIISLMultiAffSpace", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "nbExprs", 0, 1, !IS_UNIQUE, IS_ORDERED);

		// Initialize data types
		initEDataType(jniislSpaceEDataType, ISLSpace.class, "JNIISLSpace", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(jniislMultiAffEDataType, ISLMultiAff.class, "JNIISLMultiAff", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(longMatrixEDataType, long[][].class, "LongMatrix", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //MatrixPackageImpl
