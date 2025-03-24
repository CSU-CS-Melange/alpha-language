package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.StandardEquation;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
    final AlphaExpression e = se.getExpr();
    final String name = se.getVariable().getName();
    final int dim = se.getVariable().getDomain().getNbIndices();
    AABFT.checkDim(name, dim);
    AABFT.duplicateVariable(v, this.sys, e);
  }

  /**
   * Visits variable expressions (Inputs) in the AlphaZ system.
   * @param ve - An AlphaZ VariableExpression
   */
  @Override
  public void inVariableExpression(final VariableExpression ve) {
    final Variable v = ve.getVariable();
    final String name = ve.getVariable().getName();
    final int dim = ve.getVariable().getDomain().getNbIndices();
    AABFT.checkDim(name, dim);
    AABFT.duplicateVariable(v, this.sys);
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

  public static void makeChecksum(final String baseName, final ISLSet baseDomain) {
    final String newName = (baseName + "_2");
    final int baseDim = baseDomain.getNbIndices();
    final int newDim = (baseDim - 1);
    InputOutput.<String>println(((((((baseName + "->") + newName) + " | ") + Integer.valueOf(baseDim)) + " -> ") + Integer.valueOf(newDim)));
    if ((newDim == 0)) {
      String domain = "{[]:}";
    } else {
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, newDim, true);
      for (final Integer step : _doubleDotLessThan) {
        {
          String index = baseDomain.getIndexName((step).intValue());
          InputOutput.<String>println(index);
        }
      }
    }
  }
}
