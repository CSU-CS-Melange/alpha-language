package alpha.abft;

import alpha.codegen.BaseDataType;
import alpha.codegen.Program;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.demandDriven.WriteC;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaModelSaver;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.REDUCTION_OP;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.transformation.Normalize;
import alpha.model.transformation.SubstituteByDef;
import alpha.model.transformation.automation.OptimalSimplifyingReductions;
import alpha.model.transformation.reduction.ReductionComposition;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * This class augments the input program by inserting checksums over the
 * output variables. We need a checksum expression pair (i.e., two independent
 * ways to compute the checksum) for each free dimension of each output variable.
 * Then for each pair, we need another variable to store/compute their difference.
 * 
 * For example, given an output variable with "d" free dimensions, such as:
 * 
 *   ...
 *   outputs
 *     O[i,j] : {[i,j] : ...}
 *     ...
 *   let
 * 	   // equations
 *   ...
 * 
 * the default behavior inserts 3*2*d = 6*d new equations
 * 
 *   ...
 *   outputs
 *     O[i,j] : {[i,j] : ...}
 *     ...
 *   locals
 *     O_C_i_0 : {[i] : ...}
 *     O_C_i_1 : {[i] : ...}
 * 
 *     O_C_j_0 : {[j] : ...}
 *     O_C_j_1 : {[j] : ...}
 * 
 *     Inv_C_i : {[i] : ...}
 *     Inv_C_j : {[j] : ...}
 *   let
 * 	   // equations
 * 
 *     O_C_i_0[i] = reduce(+, [j], O[i,j]);
 *     O_C_i_1[i] = reduce(+, [j], O[i,j]);  // substitute and run simplification on this
 * 
 *     O_C_j_0[j] = reduce(+, [i], O[i,j]);
 *     O_C_j_1[j] = reduce(+, [i], O[i,j]);  // substitute and run simplification on this
 * 
 *     Inv_C_i[i] = (O_C_i_0 - O_C_i_1) / O_C_i_0;
 *     Inv_C_j[j] = (O_C_j_0 - O_C_j_1) / O_C_j_0;
 *   ...
 */
@SuppressWarnings("all")
public class InsertChecksums {
  public static void runOSR(final AlphaSystem system) {
    final int limit = 1;
    final int targetComplexity = 2;
    final boolean debug = true;
    final boolean trySplit = false;
    final Map<Integer, List<OptimalSimplifyingReductions.State>> opts = OptimalSimplifyingReductions.apply(system, limit, targetComplexity, trySplit, debug).optimizations;
    final List<OptimalSimplifyingReductions.State> states = opts.get(Integer.valueOf(targetComplexity));
    InputOutput.<String>println("\n\n\nSimplifications:\n");
    final Function1<OptimalSimplifyingReductions.State, Pair<Integer, OptimalSimplifyingReductions.State>> _function = (OptimalSimplifyingReductions.State s) -> {
      int _indexOf = states.indexOf(s);
      return Pair.<Integer, OptimalSimplifyingReductions.State>of(Integer.valueOf(_indexOf), s);
    };
    final Consumer<Pair<Integer, OptimalSimplifyingReductions.State>> _function_1 = (Pair<Integer, OptimalSimplifyingReductions.State> pair) -> {
      final OptimalSimplifyingReductions.State state = pair.getValue();
      InputOutput.println();
      InputOutput.<CharSequence>println(state.show());
    };
    ListExtensions.<OptimalSimplifyingReductions.State, Pair<Integer, OptimalSimplifyingReductions.State>>map(states, _function).forEach(_function_1);
  }

  public static AlphaExpression createM2VChecksumExpression(final Variable v, final String maff_str, final String fp_str) {
    ISLMultiAff maff = ISLUtil.toISLMultiAff(maff_str);
    VariableExpression var_exp = AlphaUserFactory.createVariableExpression(v);
    DependenceExpression dep_exp = AlphaUserFactory.createDependenceExpression(maff, var_exp);
    ISLMultiAff fp_maff = ISLUtil.toISLMultiAff(fp_str);
    ReduceExpression red_exp = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, fp_maff, dep_exp);
    return red_exp;
  }

  public static AlphaExpression createV2SChecksumExpression(final Variable v, final String maff_str, final String fp_str) {
    InputOutput.<String>println("--------------------------------------");
    ISLMultiAff maff = ISLUtil.toISLMultiAff(maff_str);
    VariableExpression var_exp = AlphaUserFactory.createVariableExpression(v);
    DependenceExpression dep_exp = AlphaUserFactory.createDependenceExpression(maff, var_exp);
    ISLMultiAff fp_maff = ISLUtil.toISLMultiAff(fp_str);
    ReduceExpression red_exp = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, fp_maff, dep_exp);
    return red_exp;
  }

  public static AlphaExpression createInvariantExpression(final Variable v_0, final Variable v_1) {
    VariableExpression c_prod = AlphaUserFactory.createVariableExpression(v_0);
    VariableExpression c_val = AlphaUserFactory.createVariableExpression(v_1);
    BinaryExpression diff = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, AlphaUtil.<VariableExpression>copyAE(c_prod), c_val);
    BinaryExpression quot = AlphaUserFactory.createBinaryExpression(BINARY_OP.DIV, diff, c_prod);
    return quot;
  }

  public static Variable getOutputVariable(final AlphaSystem system, final String name, final int index) throws IndexOutOfBoundsException {
    final Function1<Variable, Boolean> _function = (Variable v) -> {
      String _name = v.getName();
      return Boolean.valueOf(Objects.equal(_name, name));
    };
    Variable vbl = IterableExtensions.<Variable>findFirst(system.getOutputs(), _function);
    if ((vbl == null)) {
      vbl = system.getOutputs().get(index);
    }
    return vbl;
  }

  public static Boolean varDomainContains(final Variable v, final String domainStr) {
    return Boolean.valueOf(v.getDomain().toString().replaceAll("\\s", "").contains(domainStr));
  }

  public static void ABFT_matmult(final AlphaSystem system) {
    final SystemBody systemBody = system.getSystemBodies().get(0);
    final ISLSet row_domain = ISLUtil.toISLSet("[N] -> {[i]: 0<=i<N}");
    final ISLSet col_domain = ISLUtil.toISLSet("[N] -> {[j]: 0<=j<N}");
    final String c_maff = "[N] -> {[i,j] -> [i,j]}";
    final String fp_maff_r = "[N] -> {[i,j] -> [i]}";
    final String fp_maff_c = "[N] -> {[i,j] -> [j]}";
    final Variable Inv_C_r = AlphaUserFactory.createVariable("Inv_C_r", row_domain);
    final Variable Inv_C_c = AlphaUserFactory.createVariable("Inv_C_c", col_domain);
    EList<Variable> _outputs = system.getOutputs();
    _outputs.add(Inv_C_r);
    EList<Variable> _outputs_1 = system.getOutputs();
    _outputs_1.add(Inv_C_c);
    final Variable C_r_0 = AlphaUserFactory.createVariable("C_r_0", row_domain);
    final Variable C_r_1 = AlphaUserFactory.createVariable("C_r_1", row_domain);
    final Variable C_c_0 = AlphaUserFactory.createVariable("C_c_0", col_domain);
    final Variable C_c_1 = AlphaUserFactory.createVariable("C_c_1", col_domain);
    EList<Variable> _locals = system.getLocals();
    _locals.add(C_r_0);
    EList<Variable> _locals_1 = system.getLocals();
    _locals_1.add(C_r_1);
    EList<Variable> _locals_2 = system.getLocals();
    _locals_2.add(C_c_0);
    EList<Variable> _locals_3 = system.getLocals();
    _locals_3.add(C_c_1);
    Variable c = null;
    try {
      c = InsertChecksums.getOutputVariable(system, "C", 0);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.<String>println("No output variable found. Exiting...");
        System.exit(1);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final AlphaExpression c_red_exp_r = InsertChecksums.createM2VChecksumExpression(c, c_maff, fp_maff_r);
    final StandardEquation cr0_eq = AlphaUserFactory.createStandardEquation(C_r_0, c_red_exp_r);
    final StandardEquation cr1_eq = AlphaUserFactory.createStandardEquation(C_r_1, AlphaUtil.<AlphaExpression>copyAE(c_red_exp_r));
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(cr0_eq);
    EList<Equation> _equations_1 = systemBody.getEquations();
    _equations_1.add(cr1_eq);
    SubstituteByDef.apply(system, cr1_eq, c);
    final AlphaExpression c_red_exp_c = InsertChecksums.createM2VChecksumExpression(c, c_maff, fp_maff_c);
    final StandardEquation cc0_eq = AlphaUserFactory.createStandardEquation(C_c_0, c_red_exp_c);
    final StandardEquation cc1_eq = AlphaUserFactory.createStandardEquation(C_c_1, AlphaUtil.<AlphaExpression>copyAE(c_red_exp_c));
    EList<Equation> _equations_2 = systemBody.getEquations();
    _equations_2.add(cc0_eq);
    EList<Equation> _equations_3 = systemBody.getEquations();
    _equations_3.add(cc1_eq);
    SubstituteByDef.apply(system, cc1_eq, c);
    final AlphaExpression c_r_inv_exp = InsertChecksums.createInvariantExpression(C_r_0, C_r_1);
    final StandardEquation c_inv_r = AlphaUserFactory.createStandardEquation(Inv_C_r, c_r_inv_exp);
    final AlphaExpression c_c_inv_exp = InsertChecksums.createInvariantExpression(C_c_0, C_c_1);
    final StandardEquation c_inv_c = AlphaUserFactory.createStandardEquation(Inv_C_c, c_c_inv_exp);
    EList<Equation> _equations_4 = systemBody.getEquations();
    _equations_4.add(c_inv_r);
    EList<Equation> _equations_5 = systemBody.getEquations();
    _equations_5.add(c_inv_c);
  }

  public static void ABFT_sub(final AlphaSystem system) {
    final SystemBody systemBody = system.getSystemBodies().get(0);
    final ISLSet c_domain = ISLUtil.toISLSet("{[]: }");
    final String x_maff = "{[i] -> [i]}";
    final String c_maff = "{[i] -> []}";
    final Variable Inv_x_c = AlphaUserFactory.createVariable("Inv_x_c", c_domain);
    EList<Variable> _outputs = system.getOutputs();
    _outputs.add(Inv_x_c);
    final Variable x_c_0 = AlphaUserFactory.createVariable("x_c_0", c_domain);
    final Variable x_c_1 = AlphaUserFactory.createVariable("x_c_1", c_domain);
    EList<Variable> _locals = system.getLocals();
    _locals.add(x_c_0);
    EList<Variable> _locals_1 = system.getLocals();
    _locals_1.add(x_c_1);
    Variable x = null;
    try {
      x = InsertChecksums.getOutputVariable(system, "x", 0);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.<String>println("No output variable found. Exiting...");
        System.exit(1);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    final AlphaExpression x_red_exp_c = InsertChecksums.createV2SChecksumExpression(x, x_maff, c_maff);
    final StandardEquation xc0_eq = AlphaUserFactory.createStandardEquation(x_c_0, x_red_exp_c);
    final StandardEquation xc1_eq = AlphaUserFactory.createStandardEquation(x_c_1, AlphaUtil.<AlphaExpression>copyAE(x_red_exp_c));
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(xc0_eq);
    EList<Equation> _equations_1 = systemBody.getEquations();
    _equations_1.add(xc1_eq);
    SubstituteByDef.apply(system, xc1_eq, x);
    final AlphaExpression x_c_inv_exp = InsertChecksums.createInvariantExpression(x_c_0, x_c_1);
    final StandardEquation x_inv_c = AlphaUserFactory.createStandardEquation(Inv_x_c, x_c_inv_exp);
    EList<Equation> _equations_2 = systemBody.getEquations();
    _equations_2.add(x_inv_c);
  }

  public static void ABFT_lud(final AlphaSystem system) {
    final SystemBody systemBody = system.getSystemBodies().get(0);
    final ISLSet row_domain = ISLUtil.toISLSet("[N] -> {[i]: 0<=i<N}");
    final ISLSet col_domain = ISLUtil.toISLSet("[N] -> {[j]: 0<=j<N}");
    final String lu_maff = "[N] -> {[i,j] -> [i,j]}";
    final String fp_maff_r = "[N] -> {[i,j] -> [i]}";
    final String fp_maff_c = "[N] -> {[i,j] -> [j]}";
    final Variable Inv_L_c = AlphaUserFactory.createVariable("Inv_L_c", col_domain);
    final Variable Inv_U_r = AlphaUserFactory.createVariable("Inv_U_r", row_domain);
    EList<Variable> _outputs = system.getOutputs();
    _outputs.add(Inv_L_c);
    EList<Variable> _outputs_1 = system.getOutputs();
    _outputs_1.add(Inv_U_r);
    final Variable L_c_0 = AlphaUserFactory.createVariable("L_c_0", col_domain);
    final Variable L_c_1 = AlphaUserFactory.createVariable("L_c_1", col_domain);
    final Variable U_r_0 = AlphaUserFactory.createVariable("U_r_0", row_domain);
    final Variable U_r_1 = AlphaUserFactory.createVariable("U_r_1", row_domain);
    EList<Variable> _locals = system.getLocals();
    _locals.add(L_c_0);
    EList<Variable> _locals_1 = system.getLocals();
    _locals_1.add(L_c_1);
    EList<Variable> _locals_2 = system.getLocals();
    _locals_2.add(U_r_0);
    EList<Variable> _locals_3 = system.getLocals();
    _locals_3.add(U_r_1);
    Variable l = null;
    Variable u = null;
    try {
      l = InsertChecksums.getOutputVariable(system, "L", 0);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.<String>println("No output variable found. Exiting...");
        System.exit(1);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    try {
      u = InsertChecksums.getOutputVariable(system, "U", 1);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        InputOutput.<String>println("No output variable found. Exiting...");
        System.exit(1);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    Boolean _varDomainContains = InsertChecksums.varDomainContains(l, "0<=j<=i");
    boolean _not = (!(_varDomainContains).booleanValue());
    if (_not) {
      Boolean _varDomainContains_1 = InsertChecksums.varDomainContains(u, "0<=j<=i");
      if ((_varDomainContains_1).booleanValue()) {
        Variable temp = u;
        u = l;
        l = temp;
      } else {
        InputOutput.<String>println("No valid output variables found. Exiting...");
        System.exit(1);
      }
    }
    ISLSet _domain = l.getDomain();
    String _plus = ("L: " + _domain);
    InputOutput.<String>println(_plus);
    InputOutput.<Boolean>println(Boolean.valueOf(l.getDomain().toString().replaceAll("\\s", "").contains("0<=j<=i")));
    ISLSet _domain_1 = u.getDomain();
    String _plus_1 = ("U: " + _domain_1);
    InputOutput.<String>println(_plus_1);
    InputOutput.<Boolean>println(Boolean.valueOf(u.getDomain().toString().replaceAll("\\s", "").contains("0<=j<=i")));
    final AlphaExpression l_red_exp_c = InsertChecksums.createM2VChecksumExpression(l, lu_maff, fp_maff_c);
    final StandardEquation lc0_eq = AlphaUserFactory.createStandardEquation(L_c_0, l_red_exp_c);
    final StandardEquation lc1_eq = AlphaUserFactory.createStandardEquation(L_c_1, AlphaUtil.<AlphaExpression>copyAE(l_red_exp_c));
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(lc0_eq);
    EList<Equation> _equations_1 = systemBody.getEquations();
    _equations_1.add(lc1_eq);
    SubstituteByDef.apply(system, lc1_eq, l);
    final AlphaExpression u_red_exp_r = InsertChecksums.createM2VChecksumExpression(u, lu_maff, fp_maff_r);
    final StandardEquation ur0_eq = AlphaUserFactory.createStandardEquation(U_r_0, u_red_exp_r);
    final StandardEquation ur1_eq = AlphaUserFactory.createStandardEquation(U_r_1, AlphaUtil.<AlphaExpression>copyAE(u_red_exp_r));
    EList<Equation> _equations_2 = systemBody.getEquations();
    _equations_2.add(ur0_eq);
    EList<Equation> _equations_3 = systemBody.getEquations();
    _equations_3.add(ur1_eq);
    SubstituteByDef.apply(system, ur1_eq, u);
    final AlphaExpression l_c_inv_exp = InsertChecksums.createInvariantExpression(L_c_0, L_c_1);
    final StandardEquation l_inv_c = AlphaUserFactory.createStandardEquation(Inv_L_c, l_c_inv_exp);
    final AlphaExpression u_r_inv_exp = InsertChecksums.createInvariantExpression(U_r_0, U_r_1);
    final StandardEquation u_inv_r = AlphaUserFactory.createStandardEquation(Inv_U_r, u_r_inv_exp);
    EList<Equation> _equations_4 = systemBody.getEquations();
    _equations_4.add(l_inv_c);
    EList<Equation> _equations_5 = systemBody.getEquations();
    _equations_5.add(u_inv_r);
  }

  public static void test() {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/algo/cbd.alpha");
      final AlphaSystem system = root.getSystems().get(0);
      InputOutput.<String>println(system.getName());
      final Consumer<Variable> _function = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("input: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getInputs().forEach(_function);
      final Consumer<Variable> _function_1 = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("output: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getOutputs().forEach(_function_1);
      final Consumer<Variable> _function_2 = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("local: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getLocals().forEach(_function_2);
      InputOutput.<String>println("-------------------\nBase system:\n");
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      final Program program = WriteC.convert(system, BaseDataType.FLOAT, true);
      final String code = ProgramPrinter.print(program).toString();
      InputOutput.<String>println(code);
      System.exit(0);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static void main(final String[] args) {
    try {
      final String in_dir = "resources/blas/";
      final String out_dir = "resources/auto/";
      final List<String> mm_names = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("matmult", "matrixmult", "mat_mult", "matrix_mult", "matrix_multiplication"));
      final List<String> fb_names = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("fsub", "bsub", "forward_sub", "back_sub", "forward_substitution", "back_substitution", "fs", "bs"));
      final List<String> lu_names = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("lud", "lu_decomp", "lu_decomposition", "l_u_decomposition", "l_u_d"));
      final Scanner scanner = new Scanner(System.in);
      InputOutput.<String>print((("Enter input alpha file in \'" + in_dir) + "\': "));
      String sys_name = scanner.nextLine();
      scanner.close();
      boolean _endsWith = sys_name.endsWith(".alpha");
      boolean _not = (!_endsWith);
      if (_not) {
        sys_name = sys_name.concat(".alpha");
      }
      final String in_file = (in_dir + sys_name);
      final String out_file = (out_dir + sys_name);
      final File file = new File(in_file);
      if (((!file.exists()) || file.isDirectory())) {
        InputOutput.<String>println((("ERROR:  \'" + in_file) + "\' does not exist. Exiting..."));
        System.exit(1);
      } else {
        InputOutput.<String>println((("Reading \'" + in_file) + "\'"));
      }
      InputOutput.println();
      final AlphaRoot root = AlphaLoader.loadAlpha(in_file);
      final AlphaSystem system = root.getSystems().get(0);
      InputOutput.<String>println(system.getName());
      final Consumer<Variable> _function = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("input: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getInputs().forEach(_function);
      final Consumer<Variable> _function_1 = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("output: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getOutputs().forEach(_function_1);
      final Consumer<Variable> _function_2 = (Variable v) -> {
        String _name = v.getName();
        String _plus = ("local: " + _name);
        String _plus_1 = (_plus + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_2 = (_plus_1 + _domain);
        InputOutput.<String>println(_plus_2);
      };
      system.getLocals().forEach(_function_2);
      boolean _contains = mm_names.contains(system.getName());
      if (_contains) {
        InputOutput.<String>println("Applying ABFT to Matrix Multiplication...");
        InsertChecksums.ABFT_matmult(system);
      } else {
        boolean _contains_1 = fb_names.contains(system.getName());
        if (_contains_1) {
          InputOutput.<String>println("Applying ABFT to Forward/Back Substitution...");
          InsertChecksums.ABFT_sub(system);
        } else {
          boolean _contains_2 = lu_names.contains(system.getName());
          if (_contains_2) {
            InputOutput.<String>println("Applying ABFT to LU Decomposition...");
            InsertChecksums.ABFT_lud(system);
          } else {
            InputOutput.<String>print("Unknown system. Exiting...");
            System.exit(2);
          }
        }
      }
      String _name = system.getName();
      String _plus = (_name + "_aabft");
      system.setName(_plus);
      InputOutput.<String>println("-------------------\nBase system:\n");
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      Normalize.apply(system);
      ReductionComposition.apply(system);
      AlphaInternalStateConstructor.recomputeContextDomain(system);
      InputOutput.<String>println("-------------------\nNormalized system:\n");
      InputOutput.<String>println(AShow.print(system));
      InputOutput.<String>println((("Model saved to \'" + out_file) + "\'"));
      AlphaModelSaver.ASave(root, out_file);
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
