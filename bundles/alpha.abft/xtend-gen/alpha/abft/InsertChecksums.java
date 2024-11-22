package alpha.abft;

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
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
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

  public static AlphaExpression createChecksumExpression(final Variable v, final String maff_str, final String fp_str) {
    final ISLMultiAff maff = ISLUtil.toISLMultiAff(maff_str);
    final VariableExpression var_exp = AlphaUserFactory.createVariableExpression(v);
    final DependenceExpression dep_exp = AlphaUserFactory.createDependenceExpression(maff, var_exp);
    final ISLMultiAff fp_maff = ISLUtil.toISLMultiAff(fp_str);
    ReduceExpression red_exp = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, fp_maff, dep_exp);
    return red_exp;
  }

  public static AlphaExpression createInvariantExpression(final Variable v_0, final Variable v_1) {
    final VariableExpression c_prod = AlphaUserFactory.createVariableExpression(v_0);
    final VariableExpression c_val = AlphaUserFactory.createVariableExpression(v_1);
    final BinaryExpression diff = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, AlphaUtil.<VariableExpression>copyAE(c_prod), c_val);
    final BinaryExpression quot = AlphaUserFactory.createBinaryExpression(BINARY_OP.DIV, diff, c_prod);
    return quot;
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
    final Function1<Variable, Boolean> _function = (Variable v) -> {
      String _name = v.getName();
      return Boolean.valueOf(Objects.equal(_name, "C"));
    };
    final Variable c = IterableExtensions.<Variable>findFirst(system.getOutputs(), _function);
    final AlphaExpression c_red_exp_r = InsertChecksums.createChecksumExpression(c, c_maff, fp_maff_r);
    final StandardEquation cr0_eq = AlphaUserFactory.createStandardEquation(C_r_0, c_red_exp_r);
    final StandardEquation cr1_eq = AlphaUserFactory.createStandardEquation(C_r_1, AlphaUtil.<AlphaExpression>copyAE(c_red_exp_r));
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(cr0_eq);
    EList<Equation> _equations_1 = systemBody.getEquations();
    _equations_1.add(cr1_eq);
    SubstituteByDef.apply(system, cr1_eq, c);
    final AlphaExpression c_red_exp_c = InsertChecksums.createChecksumExpression(c, c_maff, fp_maff_c);
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

  public static void ABFT_fsub(final AlphaSystem system) {
    final SystemBody systemBody = system.getSystemBodies().get(0);
    final ISLSet s_domain = ISLUtil.toISLSet("{[s]: 0<=s<1}");
    final String x_maff = "{[i] -> [i]}";
    final Variable Inv_x_c = AlphaUserFactory.createVariable("Inv_x_c", s_domain);
    EList<Variable> _outputs = system.getOutputs();
    _outputs.add(Inv_x_c);
    final Variable x_c_0 = AlphaUserFactory.createVariable("x_c_0", s_domain);
    final Variable x_c_1 = AlphaUserFactory.createVariable("x_c_1", s_domain);
    EList<Variable> _locals = system.getLocals();
    _locals.add(x_c_0);
    EList<Variable> _locals_1 = system.getLocals();
    _locals_1.add(x_c_1);
    final Function1<Variable, Boolean> _function = (Variable v) -> {
      String _name = v.getName();
      return Boolean.valueOf(Objects.equal(_name, "x"));
    };
    final Variable x = IterableExtensions.<Variable>findFirst(system.getOutputs(), _function);
    final AlphaExpression x_red_exp_c = InsertChecksums.createChecksumExpression(x, x_maff, x_maff);
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

  public static void main(final String[] args) {
    try {
      final String in_dir = "resources/blas/";
      final String out_dir = "resources/auto/";
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
      InputOutput.<String>println((("Reading \'" + in_file) + "\'"));
      final File file = new File(in_file);
      if (((!file.exists()) || file.isDirectory())) {
        InputOutput.<String>println((("ERROR:  \'" + in_file) + "\' does not exist. Exiting..."));
        System.exit(1);
      }
      InputOutput.println();
      final AlphaRoot root = AlphaLoader.loadAlpha(in_file);
      final AlphaSystem system = root.getSystems().get(0);
      String _name = system.getName();
      String _plus = (_name + "_aabft");
      system.setName(_plus);
      InputOutput.<String>println(system.getName());
      final Consumer<Variable> _function = (Variable v) -> {
        String _name_1 = v.getName();
        String _plus_1 = ("input: " + _name_1);
        String _plus_2 = (_plus_1 + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_3 = (_plus_2 + _domain);
        InputOutput.<String>println(_plus_3);
      };
      system.getInputs().forEach(_function);
      final Consumer<Variable> _function_1 = (Variable v) -> {
        String _name_1 = v.getName();
        String _plus_1 = ("output: " + _name_1);
        String _plus_2 = (_plus_1 + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_3 = (_plus_2 + _domain);
        InputOutput.<String>println(_plus_3);
      };
      system.getOutputs().forEach(_function_1);
      final Consumer<Variable> _function_2 = (Variable v) -> {
        String _name_1 = v.getName();
        String _plus_1 = ("local: " + _name_1);
        String _plus_2 = (_plus_1 + " : ");
        ISLSet _domain = v.getDomain();
        String _plus_3 = (_plus_2 + _domain);
        InputOutput.<String>println(_plus_3);
      };
      system.getLocals().forEach(_function_2);
      boolean _contains = system.getName().contains("matmult");
      if (_contains) {
        InsertChecksums.ABFT_matmult(system);
      } else {
        boolean _contains_1 = system.getName().contains("fsub");
        if (_contains_1) {
          InsertChecksums.ABFT_fsub(system);
        } else {
          InputOutput.<String>print("Unknown system");
        }
      }
      InputOutput.<String>println("-------------------\nBase system:\n");
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      Normalize.apply(system);
      ReductionComposition.apply(system);
      AlphaInternalStateConstructor.recomputeContextDomain(system);
      InputOutput.<String>println("-------------------\nNormalized system:\n");
      InputOutput.<String>println(AShow.print(system));
      InputOutput.<String>println(("Saving model to " + out_file));
      AlphaModelSaver.ASave(root, out_file);
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
