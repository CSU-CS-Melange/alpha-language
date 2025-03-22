package alpha.model.transformation;

import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.VariableExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class AABFT extends AbstractAlphaCompleteVisitor {
  public static void apply(final AlphaSystem system) {
    final AABFT aabft = new AABFT();
    system.accept(aabft);
    InputOutput.<String>println("done");
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    final String name = se.getVariable().getName();
    final int dim = se.getVariable().getDomain().getNbIndices();
    AABFT.checkDim(name, dim);
  }

  @Override
  public void inVariableExpression(final VariableExpression ve) {
    final String name = ve.getVariable().getName();
    final int dim = ve.getVariable().getDomain().getNbIndices();
    AABFT.checkDim(name, dim);
  }

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
}
