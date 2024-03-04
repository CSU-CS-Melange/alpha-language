/**
 */
package alpha.commands.model.impl;

import alpha.commands.model.AlphaCommand;
import alpha.commands.model.AlphaCommandArgument;
import alpha.commands.model.AlphaCommandCategory;
import alpha.commands.model.AlphaCommandSignature;
import alpha.commands.model.AlphaCommandSpecialization;
import alpha.commands.model.AlphaCommandsRoot;
import alpha.commands.model.ArgumentBinding;
import alpha.commands.model.ArgumentRenaming;
import alpha.commands.model.ArgumentType;
import alpha.commands.model.CommandArgumentSpecialization;
import alpha.commands.model.CommandBinding;
import alpha.commands.model.DefaultValueArgument;
import alpha.commands.model.ModelFactory;
import alpha.commands.model.ModelPackage;
import alpha.commands.model.OverrideArgument;
import alpha.commands.model.SameAsParentArgument;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandsRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandCategoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commandBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentRenamingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandSignatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandSpecializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass alphaCommandArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass commandArgumentSpecializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass overrideArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass defaultValueArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sameAsParentArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum argumentTypeEEnum = null;

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
	 * @see alpha.commands.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ModelPackageImpl theModelPackage = registeredModelPackage instanceof ModelPackageImpl ? (ModelPackageImpl)registeredModelPackage : new ModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommandsRoot() {
		return alphaCommandsRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommandsRoot_Categories() {
		return (EReference)alphaCommandsRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommandCategory() {
		return alphaCommandCategoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommandCategory_Name() {
		return (EAttribute)alphaCommandCategoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommandCategory_Commands() {
		return (EReference)alphaCommandCategoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommand() {
		return alphaCommandEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommand_Category() {
		return (EReference)alphaCommandEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommand_Label() {
		return (EAttribute)alphaCommandEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommand_Name() {
		return (EAttribute)alphaCommandEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommand_Signature() {
		return (EReference)alphaCommandEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommand_Specializations() {
		return (EReference)alphaCommandEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommand_Binding() {
		return (EReference)alphaCommandEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommandBinding() {
		return commandBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommandBinding_BindTargetCommand() {
		return (EReference)commandBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommandBinding_ArgumentBindings() {
		return (EReference)commandBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCommandBinding_ArgumentRenamings() {
		return (EReference)commandBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgumentBinding() {
		return argumentBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentBinding_BindTarget() {
		return (EReference)argumentBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentBinding_BindSource() {
		return (EReference)argumentBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgumentRenaming() {
		return argumentRenamingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentRenaming_BindTarget() {
		return (EReference)argumentRenamingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentRenaming_BindSource() {
		return (EReference)argumentRenamingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommandSignature() {
		return alphaCommandSignatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommandSignature_Arguments() {
		return (EReference)alphaCommandSignatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommandSignature_ReturnType() {
		return (EAttribute)alphaCommandSignatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommandSpecialization() {
		return alphaCommandSpecializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommandSpecialization_Command() {
		return (EReference)alphaCommandSpecializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAlphaCommandSpecialization_Arguments() {
		return (EReference)alphaCommandSpecializationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAlphaCommandArgument() {
		return alphaCommandArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommandArgument_Name() {
		return (EAttribute)alphaCommandArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAlphaCommandArgument_ArgumentType() {
		return (EAttribute)alphaCommandArgumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommandArgumentSpecialization() {
		return commandArgumentSpecializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOverrideArgument() {
		return overrideArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOverrideArgument_Name() {
		return (EAttribute)overrideArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOverrideArgument_ArgumentType() {
		return (EAttribute)overrideArgumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDefaultValueArgument() {
		return defaultValueArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDefaultValueArgument_Value() {
		return (EAttribute)defaultValueArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSameAsParentArgument() {
		return sameAsParentArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getArgumentType() {
		return argumentTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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
		alphaCommandsRootEClass = createEClass(ALPHA_COMMANDS_ROOT);
		createEReference(alphaCommandsRootEClass, ALPHA_COMMANDS_ROOT__CATEGORIES);

		alphaCommandCategoryEClass = createEClass(ALPHA_COMMAND_CATEGORY);
		createEAttribute(alphaCommandCategoryEClass, ALPHA_COMMAND_CATEGORY__NAME);
		createEReference(alphaCommandCategoryEClass, ALPHA_COMMAND_CATEGORY__COMMANDS);

		alphaCommandEClass = createEClass(ALPHA_COMMAND);
		createEReference(alphaCommandEClass, ALPHA_COMMAND__CATEGORY);
		createEAttribute(alphaCommandEClass, ALPHA_COMMAND__LABEL);
		createEAttribute(alphaCommandEClass, ALPHA_COMMAND__NAME);
		createEReference(alphaCommandEClass, ALPHA_COMMAND__SIGNATURE);
		createEReference(alphaCommandEClass, ALPHA_COMMAND__SPECIALIZATIONS);
		createEReference(alphaCommandEClass, ALPHA_COMMAND__BINDING);

		commandBindingEClass = createEClass(COMMAND_BINDING);
		createEReference(commandBindingEClass, COMMAND_BINDING__BIND_TARGET_COMMAND);
		createEReference(commandBindingEClass, COMMAND_BINDING__ARGUMENT_BINDINGS);
		createEReference(commandBindingEClass, COMMAND_BINDING__ARGUMENT_RENAMINGS);

		argumentBindingEClass = createEClass(ARGUMENT_BINDING);
		createEReference(argumentBindingEClass, ARGUMENT_BINDING__BIND_TARGET);
		createEReference(argumentBindingEClass, ARGUMENT_BINDING__BIND_SOURCE);

		argumentRenamingEClass = createEClass(ARGUMENT_RENAMING);
		createEReference(argumentRenamingEClass, ARGUMENT_RENAMING__BIND_TARGET);
		createEReference(argumentRenamingEClass, ARGUMENT_RENAMING__BIND_SOURCE);

		alphaCommandSignatureEClass = createEClass(ALPHA_COMMAND_SIGNATURE);
		createEReference(alphaCommandSignatureEClass, ALPHA_COMMAND_SIGNATURE__ARGUMENTS);
		createEAttribute(alphaCommandSignatureEClass, ALPHA_COMMAND_SIGNATURE__RETURN_TYPE);

		alphaCommandSpecializationEClass = createEClass(ALPHA_COMMAND_SPECIALIZATION);
		createEReference(alphaCommandSpecializationEClass, ALPHA_COMMAND_SPECIALIZATION__COMMAND);
		createEReference(alphaCommandSpecializationEClass, ALPHA_COMMAND_SPECIALIZATION__ARGUMENTS);

		alphaCommandArgumentEClass = createEClass(ALPHA_COMMAND_ARGUMENT);
		createEAttribute(alphaCommandArgumentEClass, ALPHA_COMMAND_ARGUMENT__NAME);
		createEAttribute(alphaCommandArgumentEClass, ALPHA_COMMAND_ARGUMENT__ARGUMENT_TYPE);

		commandArgumentSpecializationEClass = createEClass(COMMAND_ARGUMENT_SPECIALIZATION);

		overrideArgumentEClass = createEClass(OVERRIDE_ARGUMENT);
		createEAttribute(overrideArgumentEClass, OVERRIDE_ARGUMENT__NAME);
		createEAttribute(overrideArgumentEClass, OVERRIDE_ARGUMENT__ARGUMENT_TYPE);

		defaultValueArgumentEClass = createEClass(DEFAULT_VALUE_ARGUMENT);
		createEAttribute(defaultValueArgumentEClass, DEFAULT_VALUE_ARGUMENT__VALUE);

		sameAsParentArgumentEClass = createEClass(SAME_AS_PARENT_ARGUMENT);

		// Create enums
		argumentTypeEEnum = createEEnum(ARGUMENT_TYPE);
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
		overrideArgumentEClass.getESuperTypes().add(this.getCommandArgumentSpecialization());
		defaultValueArgumentEClass.getESuperTypes().add(this.getCommandArgumentSpecialization());
		sameAsParentArgumentEClass.getESuperTypes().add(this.getCommandArgumentSpecialization());

		// Initialize classes and features; add operations and parameters
		initEClass(alphaCommandsRootEClass, AlphaCommandsRoot.class, "AlphaCommandsRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaCommandsRoot_Categories(), this.getAlphaCommandCategory(), null, "categories", null, 0, -1, AlphaCommandsRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(alphaCommandCategoryEClass, AlphaCommandCategory.class, "AlphaCommandCategory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaCommandCategory_Name(), theEcorePackage.getEString(), "name", null, 0, 1, AlphaCommandCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaCommandCategory_Commands(), this.getAlphaCommand(), this.getAlphaCommand_Category(), "commands", null, 0, -1, AlphaCommandCategory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(alphaCommandEClass, AlphaCommand.class, "AlphaCommand", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaCommand_Category(), this.getAlphaCommandCategory(), this.getAlphaCommandCategory_Commands(), "category", null, 0, 1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaCommand_Label(), theEcorePackage.getEString(), "label", null, 0, 1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaCommand_Name(), theEcorePackage.getEString(), "name", null, 0, 1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaCommand_Signature(), this.getAlphaCommandSignature(), null, "signature", null, 0, 1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaCommand_Specializations(), this.getAlphaCommandSpecialization(), this.getAlphaCommandSpecialization_Command(), "specializations", null, 0, -1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaCommand_Binding(), this.getCommandBinding(), null, "binding", null, 0, 1, AlphaCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commandBindingEClass, CommandBinding.class, "CommandBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCommandBinding_BindTargetCommand(), this.getAlphaCommand(), null, "bindTargetCommand", null, 0, 1, CommandBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommandBinding_ArgumentBindings(), this.getArgumentBinding(), null, "argumentBindings", null, 0, -1, CommandBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCommandBinding_ArgumentRenamings(), this.getArgumentRenaming(), null, "argumentRenamings", null, 0, -1, CommandBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentBindingEClass, ArgumentBinding.class, "ArgumentBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgumentBinding_BindTarget(), this.getAlphaCommandArgument(), null, "bindTarget", null, 0, 1, ArgumentBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgumentBinding_BindSource(), this.getAlphaCommandArgument(), null, "bindSource", null, 0, -1, ArgumentBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentRenamingEClass, ArgumentRenaming.class, "ArgumentRenaming", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgumentRenaming_BindTarget(), this.getAlphaCommandArgument(), null, "bindTarget", null, 0, 1, ArgumentRenaming.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgumentRenaming_BindSource(), this.getAlphaCommandArgument(), null, "bindSource", null, 0, 1, ArgumentRenaming.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(alphaCommandSignatureEClass, AlphaCommandSignature.class, "AlphaCommandSignature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaCommandSignature_Arguments(), this.getAlphaCommandArgument(), null, "arguments", null, 0, -1, AlphaCommandSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaCommandSignature_ReturnType(), this.getArgumentType(), "returnType", "void", 0, 1, AlphaCommandSignature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(alphaCommandSpecializationEClass, AlphaCommandSpecialization.class, "AlphaCommandSpecialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAlphaCommandSpecialization_Command(), this.getAlphaCommand(), this.getAlphaCommand_Specializations(), "command", null, 0, 1, AlphaCommandSpecialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAlphaCommandSpecialization_Arguments(), this.getCommandArgumentSpecialization(), null, "arguments", null, 0, -1, AlphaCommandSpecialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(alphaCommandArgumentEClass, AlphaCommandArgument.class, "AlphaCommandArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAlphaCommandArgument_Name(), theEcorePackage.getEString(), "name", null, 0, 1, AlphaCommandArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAlphaCommandArgument_ArgumentType(), this.getArgumentType(), "argumentType", null, 0, 1, AlphaCommandArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commandArgumentSpecializationEClass, CommandArgumentSpecialization.class, "CommandArgumentSpecialization", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(overrideArgumentEClass, OverrideArgument.class, "OverrideArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOverrideArgument_Name(), theEcorePackage.getEString(), "name", null, 0, 1, OverrideArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOverrideArgument_ArgumentType(), this.getArgumentType(), "argumentType", null, 0, 1, OverrideArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(defaultValueArgumentEClass, DefaultValueArgument.class, "DefaultValueArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDefaultValueArgument_Value(), theEcorePackage.getEString(), "value", null, 0, 1, DefaultValueArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sameAsParentArgumentEClass, SameAsParentArgument.class, "SameAsParentArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(argumentTypeEEnum, ArgumentType.class, "ArgumentType");
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.VOID);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_NODE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_COMPLETE_VISITABLE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_VISITABLE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_EXPRESSION_VISITABLE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_ROOT);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_SYSTEM);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.SYSTEM_BODY);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.VARIABLE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.EQUATION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.STANDARD_EQUATION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.USE_EQUATION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ABSTRACT_REDUCE_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.AUTO_RESTRICT_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.BINARY_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.DEPENDENCE_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.MULTI_ARG_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.REDUCE_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.RESTRICT_EXPRESSION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.AFFINE_FUNCTION);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.DOMAIN);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.PARAMETER_DOMAIN);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.ALPHA_BUNDLE);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.STRING);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.STRING_LIST);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.STRING_ARRAY);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.INTEGER);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.INTEGER_LIST);
		addEEnumLiteral(argumentTypeEEnum, ArgumentType.INTEGER_ARRAY);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
