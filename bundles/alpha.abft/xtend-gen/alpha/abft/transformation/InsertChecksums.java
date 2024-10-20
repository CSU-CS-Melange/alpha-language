package alpha.abft.transformation;

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
import java.util.List;
import java.util.Map;
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
      final AlphaSystem stateSystem = state.root().getSystems().get(0);
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

  public static void main(final String[] args) {
    try {
      final String in_dir = "resources/blas/";
      final String out_dir = "resources/auto/";
      final String sys_name = "matmult.alpha";
      final AlphaRoot root = AlphaLoader.loadAlpha((in_dir + sys_name));
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
      final SystemBody systemBody = system.getSystemBodies().get(0);
      final StandardEquation equation = systemBody.getStandardEquations().get(0);
      String _name = equation.getVariable().getName();
      String _plus = ("equation var: " + _name);
      InputOutput.<String>println(_plus);
      final AlphaExpression expr = equation.getExpr();
      String _print = Show.<AlphaExpression>print(expr);
      String _plus_1 = ("equation expr: " + _print);
      InputOutput.<String>println(_plus_1);
      final ISLSet row_domain = ISLUtil.toISLSet("[N] -> {[i]: 0<=i<N}");
      final ISLSet col_domain = ISLUtil.toISLSet("[N] -> {[j]: 0<=j<N}");
      final String c_maff = "[N] -> {[i,j] -> [i,j]}";
      final String fp_maff_i = "[N] -> {[i,j] -> [i]}";
      final String fp_maff_j = "[N] -> {[i,j] -> [j]}";
      final Variable Inv_C_i = AlphaUserFactory.createVariable("Inv_C_i", row_domain);
      final Variable Inv_C_j = AlphaUserFactory.createVariable("Inv_C_j", col_domain);
      EList<Variable> _outputs = system.getOutputs();
      _outputs.add(Inv_C_i);
      EList<Variable> _outputs_1 = system.getOutputs();
      _outputs_1.add(Inv_C_j);
      final Variable C_C_i_0 = AlphaUserFactory.createVariable("C_C_i_0", row_domain);
      final Variable C_C_i_1 = AlphaUserFactory.createVariable("C_C_i_1", row_domain);
      final Variable C_C_j_0 = AlphaUserFactory.createVariable("C_C_j_0", col_domain);
      final Variable C_C_j_1 = AlphaUserFactory.createVariable("C_C_j_1", col_domain);
      EList<Variable> _outputs_2 = system.getOutputs();
      _outputs_2.add(C_C_i_0);
      EList<Variable> _outputs_3 = system.getOutputs();
      _outputs_3.add(C_C_i_1);
      EList<Variable> _outputs_4 = system.getOutputs();
      _outputs_4.add(C_C_j_0);
      EList<Variable> _locals = system.getLocals();
      _locals.add(C_C_j_1);
      final Function1<Variable, Boolean> _function_3 = (Variable v) -> {
        String _name_1 = v.getName();
        return Boolean.valueOf(Objects.equal(_name_1, "C"));
      };
      final Variable c = IterableExtensions.<Variable>findFirst(system.getOutputs(), _function_3);
      final AlphaExpression c_red_exp_i = InsertChecksums.createChecksumExpression(c, c_maff, fp_maff_i);
      final StandardEquation cci0_eq = AlphaUserFactory.createStandardEquation(C_C_i_0, c_red_exp_i);
      final StandardEquation cci1_eq = AlphaUserFactory.createStandardEquation(C_C_i_1, AlphaUtil.<AlphaExpression>copyAE(c_red_exp_i));
      EList<Equation> _equations = systemBody.getEquations();
      _equations.add(cci0_eq);
      EList<Equation> _equations_1 = systemBody.getEquations();
      _equations_1.add(cci1_eq);
      SubstituteByDef.apply(system, cci1_eq, c);
      final AlphaExpression c_red_exp_j = InsertChecksums.createChecksumExpression(c, c_maff, fp_maff_j);
      final StandardEquation ccj0_eq = AlphaUserFactory.createStandardEquation(C_C_j_0, c_red_exp_j);
      final StandardEquation ccj1_eq = AlphaUserFactory.createStandardEquation(C_C_j_1, AlphaUtil.<AlphaExpression>copyAE(c_red_exp_j));
      EList<Equation> _equations_2 = systemBody.getEquations();
      _equations_2.add(ccj0_eq);
      EList<Equation> _equations_3 = systemBody.getEquations();
      _equations_3.add(ccj1_eq);
      SubstituteByDef.apply(system, ccj1_eq, c);
      final AlphaExpression c_i_inv_exp = InsertChecksums.createInvariantExpression(C_C_i_0, C_C_i_1);
      final StandardEquation c_inv_i = AlphaUserFactory.createStandardEquation(Inv_C_i, c_i_inv_exp);
      final AlphaExpression c_j_inv_exp = InsertChecksums.createInvariantExpression(C_C_j_0, C_C_j_1);
      final StandardEquation c_inv_j = AlphaUserFactory.createStandardEquation(Inv_C_j, c_j_inv_exp);
      EList<Equation> _equations_4 = systemBody.getEquations();
      _equations_4.add(c_inv_i);
      EList<Equation> _equations_5 = systemBody.getEquations();
      _equations_5.add(c_inv_j);
      InputOutput.<String>println("-------------------\nBase system:\n");
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      Normalize.apply(system);
      ReductionComposition.apply(system);
      AlphaInternalStateConstructor.recomputeContextDomain(system);
      InputOutput.<String>println("-------------------\nNormalized system:\n");
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
      InputOutput.<String>println("-------------------\nAShow, normalized:\n");
      InputOutput.<String>println(AShow.print(system));
      InputOutput.<String>println((("Saving model to " + out_dir) + sys_name));
      AlphaModelSaver.ASave(root, (out_dir + sys_name));
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
