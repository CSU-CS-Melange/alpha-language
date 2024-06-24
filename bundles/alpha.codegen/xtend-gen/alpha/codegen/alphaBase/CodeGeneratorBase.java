package alpha.codegen.alphaBase;

import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.IfStmt;
import alpha.codegen.IfStmtBuilder;
import alpha.codegen.Include;
import alpha.codegen.MacroStmt;
import alpha.codegen.Program;
import alpha.codegen.ProgramBuilder;
import alpha.codegen.Statement;
import alpha.codegen.UnaryOperator;
import alpha.codegen.isl.ConditionalConverter;
import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.UseEquation;
import alpha.model.Variable;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.StandardizeNames;
import alpha.model.transformation.reduction.NormalizeReduction;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public abstract class CodeGeneratorBase {
  /**
   * The system body being converted into a "simpleC" AST.
   */
  protected final SystemBody systemBody;

  /**
   * The name checker to use to ensure no duplicate names are produced.
   */
  protected final AlphaNameChecker nameChecker;

  /**
   * Generates data types for Alpha variables.
   */
  protected final TypeGeneratorBase typeGenerator;

  /**
   * If true, the flags variables used for cycle detection will be generated. Otherwise, the won't be.
   */
  protected final boolean cycleDetection;

  /**
   * The builder for the program representing this system body.
   */
  protected final ProgramBuilder program;

  /**
   * The builder for the entry point to the program.
   */
  protected final FunctionBuilder entryPoint;

  /**
   * The list of all variables allocated by the entry point function which need to be freed before returning.
   */
  protected final ArrayList<String> allocatedVariables;

  /**
   * Constructs a new instance of this class.
   * 
   * @param systemBody    The system body to convert into a "simpleC" AST. This may be modified.
   * @param nameChecker   The name checker to use to ensure no duplicate names are produced.
   * @param typeGenerator Generates data types for Alpha variables.
   * @param cycleDetection If true, the flags variables used for cycle detection will be generated. Otherwise, the won't be.
   * @throws IllegalArgumentException If the link from the system body to the parent Alpha system is null. This can happen
   *         if the "alpha.model.util.AlphaUtil.copyAE" function was used to copy only the system body, and not the entire system.
   */
  public CodeGeneratorBase(final SystemBody systemBody, final AlphaNameChecker nameChecker, final TypeGeneratorBase typeGenerator, final boolean cycleDetection) {
    this.systemBody = systemBody;
    this.nameChecker = nameChecker;
    this.typeGenerator = typeGenerator;
    this.cycleDetection = cycleDetection;
    this.program = ProgramBuilder.start(nameChecker);
    this.entryPoint = this.program.startFunction(BaseDataType.VOID, systemBody.getSystem().getName());
    this.allocatedVariables = CollectionLiterals.<String>newArrayList();
  }

  /**
   * Converts the given system into a "simpleC" Program instance.
   * 
   * @throws An UnsupportedOperationException is thrown if the system cannot
   *         be converted. The reason for this should be populated into the
   *         exception message.
   */
  public Program convertSystemBody() throws UnsupportedOperationException {
    this.preprocess();
    this.addDefaultHeaderComment();
    this.addDefaultIncludes();
    this.addDefaultFunctionMacros();
    final AlphaSystem system = this.systemBody.getSystem();
    final List<String> parameters = system.getParameterDomain().getParamNames();
    final Consumer<String> _function = (String it) -> {
      this.declareParameter(it);
    };
    parameters.forEach(_function);
    final Consumer<Variable> _function_1 = (Variable it) -> {
      this.declareGlobalVariable(it);
    };
    system.getVariables().forEach(_function_1);
    final Consumer<Variable> _function_2 = (Variable it) -> {
      this.declareMemoryMacro(it);
    };
    system.getVariables().forEach(_function_2);
    if (this.cycleDetection) {
      final Consumer<Variable> _function_3 = (Variable it) -> {
        this.declareFlagVariable(it);
      };
      system.getOutputs().forEach(_function_3);
      final Consumer<Variable> _function_4 = (Variable it) -> {
        this.declareFlagMemoryMacro(it);
      };
      system.getOutputs().forEach(_function_4);
      final Consumer<Variable> _function_5 = (Variable it) -> {
        this.declareFlagVariable(it);
      };
      system.getLocals().forEach(_function_5);
      final Consumer<Variable> _function_6 = (Variable it) -> {
        this.declareFlagMemoryMacro(it);
      };
      system.getLocals().forEach(_function_6);
    }
    final Consumer<StandardEquation> _function_7 = (StandardEquation it) -> {
      this.declareEvaluation(it);
    };
    this.systemBody.getStandardEquations().forEach(_function_7);
    final Consumer<UseEquation> _function_8 = (UseEquation it) -> {
      this.declareEvaluation(it);
    };
    this.systemBody.getUseEquations().forEach(_function_8);
    this.addEntryPoint();
    return this.program.getInstance();
  }

  /**
   * Adds the entry point to the program.
   */
  public void addEntryPoint() {
    final AlphaSystem system = this.systemBody.getSystem();
    final List<String> parameters = system.getParameterDomain().getParamNames();
    final Consumer<String> _function = (String it) -> {
      this.declareSystemParameterArgument(it);
    };
    parameters.forEach(_function);
    final Consumer<Variable> _function_1 = (Variable it) -> {
      this.declareSystemVariableArgument(it);
    };
    system.getInputs().forEach(_function_1);
    final Consumer<Variable> _function_2 = (Variable it) -> {
      this.declareSystemVariableArgument(it);
    };
    system.getOutputs().forEach(_function_2);
    this.entryPoint.addComment("Copy arguments to the global variables.");
    final Consumer<String> _function_3 = (String it) -> {
      this.copySystemParameter(it);
    };
    parameters.forEach(_function_3);
    final Consumer<Variable> _function_4 = (Variable it) -> {
      this.copySystemVariable(it);
    };
    system.getInputs().forEach(_function_4);
    final Consumer<Variable> _function_5 = (Variable it) -> {
      this.copySystemVariable(it);
    };
    system.getOutputs().forEach(_function_5);
    this.entryPoint.addEmptyLine();
    this.checkParameters();
    this.entryPoint.addComment("Allocate memory for local storage.");
    final Consumer<Variable> _function_6 = (Variable it) -> {
      this.allocateVariable(it);
    };
    system.getLocals().forEach(_function_6);
    this.entryPoint.addEmptyLine();
    this.entryPoint.addComment("Allocate and initialize flag variables.");
    if (this.cycleDetection) {
      final Consumer<Variable> _function_7 = (Variable it) -> {
        this.allocateFlagsVariable(it);
      };
      system.getOutputs().forEach(_function_7);
      final Consumer<Variable> _function_8 = (Variable it) -> {
        this.allocateFlagsVariable(it);
      };
      system.getLocals().forEach(_function_8);
    }
    this.entryPoint.addEmptyLine();
    this.performEvaluations();
    this.freeAllocatedVariables();
    this.program.addFunction(this.entryPoint.getInstance());
  }

  /**
   * Normalizes the system body and standardizes all names prior to conversion.
   */
  public void preprocess() {
    Normalize.apply(this.systemBody);
    NormalizeReduction.apply(this.systemBody);
    StandardizeNames.apply(this.systemBody);
  }

  /**
   * Adds the header comment lines to add to the program.
   */
  public void addDefaultHeaderComment() {
    this.program.addHeaderComment("This code was auto-generated with AlphaZ.");
  }

  /**
   * Adds the "#include" directives to add to the program.
   */
  public void addDefaultIncludes() {
    final Function1<String, Include> _function = (String it) -> {
      return Factory.include(it);
    };
    this.program.addInclude(
      ((Include[])Conversions.unwrapArray(ListExtensions.<String, Include>map(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("float.h", "limits.h", "math.h", "stdbool.h", "stdio.h", "stdlib.h", "string.h")), _function), Include.class)));
  }

  /**
   * Adds the "#define" function macros to add to the program.
   */
  public void addDefaultFunctionMacros() {
    MacroStmt _macroStmt = Factory.macroStmt("ceild", new String[] { "n", "d" }, "((int)ceil(((double)(n))/((double)(d))))");
    MacroStmt _macroStmt_1 = Factory.macroStmt("floord", new String[] { "n", "d" }, "((int)floor(((double)(n))/((double)(d))))");
    MacroStmt _macroStmt_2 = Factory.macroStmt("div", new String[] { "a", "b" }, "(ceild((a),(b)))");
    MacroStmt _macroStmt_3 = Factory.macroStmt("max", new String[] { "a", "b" }, "(((a)>(b))?(a):(b))");
    MacroStmt _macroStmt_4 = Factory.macroStmt("min", new String[] { "a", "b" }, "(((a)<(b))?(a):(b))");
    MacroStmt _macroStmt_5 = Factory.macroStmt("mallocCheck", new String[] { "v", "s" }, "if ((v) == NULL) { printf(\"Failed to allocate memory for variable: %s\\n\", (s)); exit(-1); }");
    this.program.addFunctionMacro(
      new MacroStmt[] { _macroStmt, _macroStmt_1, _macroStmt_2, _macroStmt_3, _macroStmt_4, _macroStmt_5 });
  }

  /**
   * Declares a global variable for the given parameter.
   */
  public void declareParameter(final String systemParameter) {
    final String name = this.nameChecker.getParameterStorageName(systemParameter);
    this.program.addGlobalVariable(true, this.typeGenerator.getIndexType(), name);
  }

  /**
   * Declares a global variable for the given Alpha variable.
   */
  public void declareGlobalVariable(final Variable variable) {
    final DataType dataType = this.typeGenerator.getAlphaVariableType(variable);
    final String name = this.nameChecker.getVariableStorageName(variable);
    this.program.addGlobalVariable(true, dataType, name);
  }

  /**
   * Declares a memory macro for the given Alpha variable.
   */
  public abstract void declareMemoryMacro(final Variable variable);

  /**
   * Declares a global variable for the "flags" variable
   * associated with the given Alpha variable.
   */
  public void declareFlagVariable(final Variable variable) {
    final DataType dataType = this.typeGenerator.getFlagVariableType(variable);
    final String name = this.nameChecker.getFlagName(variable);
    this.program.addGlobalVariable(true, dataType, name);
  }

  /**
   * Declares a memory macro for the "flags" variable
   * associated with the given Alpha variable.
   */
  public abstract void declareFlagMemoryMacro(final Variable variable);

  /**
   * Declares whatever is needed (macro, function, etc.) for evaluating the given equation.
   */
  public abstract void declareEvaluation(final StandardEquation equation);

  /**
   * Declares whatever is needed (macro, function, etc.) for evaluating the given equation.
   */
  public abstract void declareEvaluation(final UseEquation equation);

  /**
   * Creates a function argument for the given Alpha system parameter.
   */
  public void declareSystemParameterArgument(final String systemParameter) {
    final DataType dataType = this.typeGenerator.getIndexType();
    final String name = this.nameChecker.getParameterArgumentName(systemParameter);
    this.entryPoint.addParameter(dataType, name);
  }

  /**
   * Creates a function argument for the given Alpha variable.
   */
  public void declareSystemVariableArgument(final Variable variable) {
    final DataType dataType = this.typeGenerator.getAlphaVariableType(variable);
    final String name = this.nameChecker.getVariableArgumentName(variable);
    this.entryPoint.addParameter(dataType, name);
  }

  /**
   * Creates a statement to copy the Alpha system parameter from the
   * entry point's argument to the associated global variable.
   */
  public void copySystemParameter(final String systemParameter) {
    final String argName = this.nameChecker.getParameterArgumentName(systemParameter);
    final String storageName = this.nameChecker.getParameterStorageName(systemParameter);
    final AssignmentStmt assignment = Factory.assignmentStmt(storageName, argName);
    this.entryPoint.addStatement(assignment);
  }

  /**
   * Copies the Alpha variable from the entry point's argument
   * to the associated global variable.
   */
  public void copySystemVariable(final Variable variable) {
    final String argName = this.nameChecker.getVariableArgumentName(variable);
    final String storageName = this.nameChecker.getVariableStorageName(variable);
    final AssignmentStmt assignment = Factory.assignmentStmt(storageName, argName);
    this.entryPoint.addStatement(assignment);
  }

  /**
   * Creates the statements that check if the values of the Alpha system parameters
   * are valid for this system body.
   */
  public void checkParameters() {
    final Function1<ISLBasicSet, Boolean> _function = (ISLBasicSet it) -> {
      int _nbConstraints = it.getNbConstraints();
      return Boolean.valueOf((_nbConstraints == 0));
    };
    boolean _forall = IterableExtensions.<ISLBasicSet>forall(this.systemBody.getParameterDomain().getBasicSets(), _function);
    if (_forall) {
      return;
    }
    this.entryPoint.addComment("Check parameter validity.");
    final Expression isValid = ConditionalConverter.convert(this.systemBody.getParameterDomain());
    final IfStmt parameterCheck = IfStmtBuilder.start(Factory.unaryExpr(UnaryOperator.NOT, isValid)).addStatement(
      Factory.callStmt("printf", "\"The value of the parameters are invalid.\\n\""), 
      Factory.callStmt("exit", "-1")).getInstance();
    this.entryPoint.addStatement(parameterCheck);
    this.entryPoint.addEmptyLine();
  }

  /**
   * Allocates memory for the given variable.
   */
  public abstract void allocateVariable(final Variable variable);

  /**
   * Allocates memory for the flags variable associated with the given variable.
   */
  public abstract void allocateFlagsVariable(final Variable variable);

  /**
   * Generates the loops of the entry point which perform all the evaluations
   * necessary for the Alpha system to do what it is intended to do.
   */
  public abstract void performEvaluations();

  /**
   * Frees all allocated variables.
   */
  public void freeAllocatedVariables() {
    this.entryPoint.addComment("Free all allocated memory.");
    final Function1<String, ExpressionStmt> _function = (String it) -> {
      return Factory.callStmt("free", it);
    };
    final List<ExpressionStmt> freeStmts = ListExtensions.<String, ExpressionStmt>map(this.allocatedVariables, _function);
    this.entryPoint.addStatement(((Statement[])Conversions.unwrapArray(freeStmts, Statement.class)));
  }
}
