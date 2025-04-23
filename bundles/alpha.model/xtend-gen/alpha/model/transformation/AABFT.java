package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.BINARY_OP;
import alpha.model.BinaryExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.REDUCTION_OP;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class AABFT extends AbstractAlphaCompleteVisitor {
  private static Boolean DEBUG = Boolean.valueOf(false);

  private AlphaSystem sys;

  private AABFT(final AlphaSystem system) {
    this.sys = system;
  }

  public static void apply(final AlphaSystem system) {
    final AABFT aabft = new AABFT(system);
    system.accept(aabft);
  }

  /**
   * Visits standard equations (Outputs) in the AlphaZ system.
   * @param se - An AlphaZ StandardEquation
   */
  @Override
  public void inStandardEquation(final StandardEquation se) {
    final Variable v = se.getVariable();
    final List<String> indices = v.getDomain().getIndexNames();
    AABFT.checkDim(v, Boolean.valueOf(false));
    final Consumer<String> _function = (String index) -> {
      AABFT.makeChecksum(v, this.sys, index, indices.toString());
    };
    indices.forEach(_function);
  }

  /**
   * Visits variable expressions (Inputs) in the AlphaZ system.
   * @param ve - An AlphaZ VariableExpression
   */
  @Override
  public void inVariableExpression(final VariableExpression ve) {
  }

  /**
   * Utility function. Checks dimensions of variable domain to ensure a checksum (T-1 dimensions) could be generated.
   * @param v - The variable being checked
   * @param verbose - Flag for printing non-error information about the variable
   */
  public static void checkDim(final Variable v, final Boolean verbose) {
    final String name = v.getName();
    final int dim = v.getDomain().getNbIndices();
    final int check = (dim - 1);
    String msg = (((((("The variable \'" + name) + "\' has a (") + Integer.valueOf(dim)) + ")-dimensional domain, which would produce a ") + Integer.valueOf(check)) + "-dimensional checksum.");
    final int index = 83;
    if ((check < 0)) {
      InputOutput.<String>println(("ERROR: Invalid variable detected. " + msg));
      System.exit(1);
    } else {
      if ((check == 0)) {
        StringBuilder sb = new StringBuilder(msg);
        sb.insert(index, "(scalar) ");
        msg = sb.toString();
        if ((verbose).booleanValue()) {
          InputOutput.<String>println(msg);
        }
      } else {
        if ((check == 1)) {
          StringBuilder sb_1 = new StringBuilder(msg);
          sb_1.insert(index, "(vector) ");
          msg = sb_1.toString();
          if ((verbose).booleanValue()) {
            InputOutput.<String>println(msg);
          }
        } else {
          if ((verbose).booleanValue()) {
            InputOutput.<String>println(msg);
          }
        }
      }
    }
  }

  /**
   * Utility function to generate checksum variable name template.
   * @param v - base variable being checksummed
   * @param index - the index name of the variable
   * 
   * @return formatted name template
   */
  public static String getNameTemplate(final Variable v, final String index) {
    return String.format("check_%s_%s_", v.getName(), index);
  }

  /**
   * Generates checksum, adds it to AlphaZ system, and returns variable and it's ReduceExpression.
   * @param sys - AlphaZ system
   * @param name - checksum name template
   * @param domain - the domain of the checksum variable
   * @param b_maff - base MultiAff
   * @param p_maff - projected MultiAff
   * @param base - base variable
   * 
   * @return Pair containing the checksum variable (key) and its ReduceExpression (value)
   */
  public static Pair<Variable, ReduceExpression> addChecksum(final AlphaSystem sys, final String name, final ISLSet domain, final ISLMultiAff b_maff, final ISLMultiAff p_maff, final Variable base) {
    final Variable check = AlphaUserFactory.createVariable((name + "0"), domain);
    final VariableExpression varExp = AlphaUserFactory.createVariableExpression(base);
    final DependenceExpression depExp = AlphaUserFactory.createDependenceExpression(b_maff, varExp);
    final ReduceExpression redExp = AlphaUserFactory.createReduceExpression(REDUCTION_OP.SUM, p_maff, depExp);
    final StandardEquation stdEq = AlphaUserFactory.createStandardEquation(check, redExp);
    if ((AABFT.DEBUG).booleanValue()) {
      EList<Variable> _outputs = sys.getOutputs();
      _outputs.add(check);
    } else {
      EList<Variable> _locals = sys.getLocals();
      _locals.add(check);
    }
    EList<Equation> _equations = sys.getSystemBodies().get(0).getEquations();
    _equations.add(stdEq);
    return Pair.<Variable, ReduceExpression>of(check, redExp);
  }

  /**
   * Generates checksum comparator, adds it to AlphaZ system, and returns the generated variable.
   * @param sys - AlphaZ system
   * @param name - checksum name template
   * @param redExp - the ReduceExpression of the primary checksum variable
   * @param base - base variable
   * 
   * @return The checksum comparator variable
   */
  public static Variable addComparator(final AlphaSystem sys, final String name, final ISLSet domain, final ReduceExpression redExp, final Variable base) {
    final Variable comp = AlphaUserFactory.createVariable((name + "1"), domain);
    if ((AABFT.DEBUG).booleanValue()) {
      EList<Variable> _outputs = sys.getOutputs();
      _outputs.add(comp);
    } else {
      EList<Variable> _locals = sys.getLocals();
      _locals.add(comp);
    }
    final StandardEquation stdEq = AlphaUserFactory.createStandardEquation(comp, AlphaUtil.<ReduceExpression>copyAE(redExp));
    EList<Equation> _equations = sys.getSystemBodies().get(0).getEquations();
    _equations.add(stdEq);
    SubstituteByDef.apply(sys, stdEq, base);
    return comp;
  }

  /**
   * Adds the checksum invariant to the AlphaZ system.
   * @param invariant - Declared AlphaZ variable
   * @param prime - Primary checksum variable
   * @param comp - Comparison checksum variable
   * 
   * @return Standard equation of the checksum invariant
   */
  public static void addInvariant(final AlphaSystem sys, final String name, final ISLSet domain, final Variable prime, final Variable comp) {
    final Variable inv = AlphaUserFactory.createVariable((name + "inv"), domain);
    final VariableExpression prod = AlphaUserFactory.createVariableExpression(prime);
    final VariableExpression value = AlphaUserFactory.createVariableExpression(comp);
    final BinaryExpression diff = AlphaUserFactory.createBinaryExpression(BINARY_OP.SUB, AlphaUtil.<VariableExpression>copyAE(prod), value);
    final BinaryExpression expr = AlphaUserFactory.createBinaryExpression(BINARY_OP.DIV, diff, prod);
    final StandardEquation stdEx = AlphaUserFactory.createStandardEquation(inv, expr);
    EList<Variable> _outputs = sys.getOutputs();
    _outputs.add(inv);
    EList<Equation> _equations = sys.getSystemBodies().get(0).getEquations();
    _equations.add(stdEx);
  }

  /**
   * Generates the domain definition of the checksum variable.
   * @param v - the base variable being checksummed
   * @param index - the current index iterator along which the checksum is produced
   * 
   * @return The checksum variable's domain as an ISLSet
   */
  public static ISLSet getDomain(final Variable v, final String index) {
    final int dim = v.getDomain().getNbIndices();
    final int p_dim = (dim - 1);
    String _xifexpression = null;
    if ((p_dim == 0)) {
      _xifexpression = "[N] -> {[]\t :\t  \t }";
    } else {
      _xifexpression = String.format("[N] -> {[%s]: 0<=%s<N}", index, index);
    }
    String domain = _xifexpression;
    return ISLUtil.toISLSet(domain);
  }

  /**
   * Generates the ISL MultiAff definitions for the checksum variable.
   * @param v - the base variable being checksummed
   * @param currentIndex - the current index iterator along which the checksum is produced
   * @param indices - the indices associated with the base variable
   * 
   * @return Pair containing the base MultiAff (key) and the projection MultiAff (value)
   */
  public static Pair<ISLMultiAff, ISLMultiAff> getMultiAffs(final Variable v, final String currentIndex, final String indices) {
    final int dim = v.getDomain().getNbIndices();
    final int p_dim = (dim - 1);
    final ISLMultiAff b_maff = ISLUtil.toISLMultiAff(String.format("[N] -> {%s -> %s}", indices, indices));
    String _xifexpression = null;
    if ((p_dim == 0)) {
      _xifexpression = String.format("[N] -> {%s ->[]}", indices);
    } else {
      _xifexpression = String.format("[N] -> {%s -> [%s]}", indices, currentIndex);
    }
    final String pmStr = _xifexpression;
    final ISLMultiAff p_maff = ISLUtil.toISLMultiAff(pmStr);
    return Pair.<ISLMultiAff, ISLMultiAff>of(b_maff, p_maff);
  }

  public static void makeChecksum(final Variable v, final AlphaSystem s, final String index, final String indices) {
    final String name = AABFT.getNameTemplate(v, index);
    final ISLSet domain = AABFT.getDomain(v, index);
    Pair<ISLMultiAff, ISLMultiAff> maffs = AABFT.getMultiAffs(v, index, indices);
    final ISLMultiAff b_maff = maffs.getKey();
    final ISLMultiAff p_maff = maffs.getValue();
    final Pair<Variable, ReduceExpression> check = AABFT.addChecksum(s, name, domain, b_maff, p_maff, v);
    final ReduceExpression redExp = check.getValue();
    final Variable prime = check.getKey();
    final Variable checkComp = AABFT.addComparator(s, name, domain, redExp, v);
    AABFT.addInvariant(s, name, domain, prime, checkComp);
  }
}
