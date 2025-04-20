package alpha.model.transformation;

import alpha.model.AlphaExpression;
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
    final String name = se.getVariable().getName();
    final int dim = se.getVariable().getDomain().getNbIndices();
    final List<String> indices = se.getVariable().getDomain().getIndexNames();
    AABFT.checkDim(name, dim);
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
   * @param name - The name of the variable
   * @param dim - The number of dimensions in the variable's domain
   */
  public static String checkDim(final String name, final int dim) {
    String _xblockexpression = null;
    {
      final int check = (dim - 1);
      String msg = (((((("The variable \'" + name) + "\' has a (") + Integer.valueOf(dim)) + ")-dimensional domain, which would produce a ") + Integer.valueOf(check)) + "-dimensional checksum.");
      final int index = 83;
      String _xifexpression = null;
      if ((check < 0)) {
        InputOutput.<String>println(("ERROR: Invalid variable detected. " + msg));
        System.exit(1);
      } else {
        String _xifexpression_1 = null;
        if ((check == 0)) {
          String _xblockexpression_1 = null;
          {
            StringBuilder sb = new StringBuilder(msg);
            sb.insert(index, "(scalar) ");
            msg = sb.toString();
            _xblockexpression_1 = InputOutput.<String>println(msg);
          }
          _xifexpression_1 = _xblockexpression_1;
        } else {
          String _xifexpression_2 = null;
          if ((check == 1)) {
            String _xblockexpression_2 = null;
            {
              StringBuilder sb = new StringBuilder(msg);
              sb.insert(index, "(vector) ");
              msg = sb.toString();
              _xblockexpression_2 = InputOutput.<String>println(msg);
            }
            _xifexpression_2 = _xblockexpression_2;
          } else {
            _xifexpression_2 = InputOutput.<String>println(msg);
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }

  /**
   * Duplicates variable expression (input) variable and inserts into AlphaZ system.
   * @param v - An AlphaZ Variable
   * @param s - An AlphaZ System
   * @returns The new AlphaZ Variable that was added to the system.
   */
  public static Variable duplicateVariable(final Variable v, final AlphaSystem s) {
    final String baseName = v.getName();
    final ISLSet baseDomain = v.getDomain();
    final String newName = (baseName + "_2");
    final ISLSet newDomain = baseDomain.copy();
    final Variable newVar = AlphaUserFactory.createVariable(newName, newDomain);
    int _variableGroup = AABFT.getVariableGroup(v);
    switch (_variableGroup) {
      case 1:
        EList<Variable> _inputs = s.getInputs();
        _inputs.add(newVar);
        break;
      case 2:
        EList<Variable> _locals = s.getLocals();
        _locals.add(newVar);
        break;
      case 3:
        EList<Variable> _outputs = s.getOutputs();
        _outputs.add(newVar);
        break;
      default:
        {
          InputOutput.<String>println("Base variable not found in Alpha system.");
          return null;
        }
    }
    return newVar;
  }

  /**
   * Duplicates standard equation (output) variable and inserts into AlphaZ system.
   * Overloaded function that calls variable expression version.
   * @param v - An AlphaZ Variable
   * @param s - An AlphaZ System
   * @param e - An AlphaZ AlphaExpression
   */
  public static void duplicateVariable(final Variable v, final AlphaSystem s, final AlphaExpression e) {
    final Variable newVar = AABFT.duplicateVariable(v, s);
    if (((newVar != null) && false)) {
      final StandardEquation newEq = AlphaUserFactory.createStandardEquation(newVar, e);
      EList<Equation> _equations = s.getSystemBodies().get(0).getEquations();
      _equations.add(newEq);
      SubstituteByDef.apply(s, newEq, newVar);
    }
  }

  /**
   * Utility function. Gets the system group of a variable.
   * @param v - An AlphaZ Variable
   * @returns An integer representing the variable's group:
   * 1 = input,
   * 2 = local,
   * 3 = output,
   * 0 = none
   */
  public static int getVariableGroup(final Variable v) {
    Boolean _isInput = v.isInput();
    if ((_isInput).booleanValue()) {
      return 1;
    } else {
      Boolean _isLocal = v.isLocal();
      if ((_isLocal).booleanValue()) {
        return 2;
      } else {
        Boolean _isOutput = v.isOutput();
        if ((_isOutput).booleanValue()) {
          return 3;
        } else {
          return 0;
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
    EList<Variable> _locals = sys.getLocals();
    _locals.add(check);
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
    EList<Variable> _locals = sys.getLocals();
    _locals.add(comp);
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

  public static void makeChecksum(final Variable v, final AlphaSystem s, final String index, final String indices) {
    final String name = AABFT.getNameTemplate(v, index);
    final ISLSet domain = ISLUtil.toISLSet(String.format("[N] -> {[%s]: 0<=%s<N}", index, index));
    InputOutput.<String>println(("domain: " + domain));
    final ISLMultiAff maff = ISLUtil.toISLMultiAff(String.format("[N] -> {%s -> %s}", indices, indices));
    final ISLMultiAff fp_maff = ISLUtil.toISLMultiAff(String.format("[N] -> {%s -> [%s]}", indices, index));
    final Pair<Variable, ReduceExpression> check = AABFT.addChecksum(s, name, domain, maff, fp_maff, v);
    final ReduceExpression redExp = check.getValue();
    final Variable prime = check.getKey();
    final Variable checkComp = AABFT.addComparator(s, name, domain, redExp, v);
    AABFT.addInvariant(s, name, domain, prime, checkComp);
  }
}
