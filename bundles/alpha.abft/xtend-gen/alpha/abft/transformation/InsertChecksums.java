package alpha.abft.transformation;

import alpha.loader.AlphaLoader;
import alpha.model.AlphaExpression;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.ISLUtil;
import alpha.model.util.Show;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
  public static void main(final String[] args) {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/blas/matmult.alpha");
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
      final ISLSet domain = ISLUtil.toISLSet("[N] -> {[i,j,k] : 0<=k<=j<=i<=N}");
      final Variable myNewVarName = AlphaUserFactory.createVariable("newVar", domain);
      EList<Variable> _locals = system.getLocals();
      _locals.add(myNewVarName);
      InputOutput.<String>println(Show.<AlphaSystem>print(system));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
