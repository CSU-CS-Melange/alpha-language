package alpha.model.transformation;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.StandardEquation;
import alpha.model.VariableExpression;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class AABFT extends AbstractAlphaCompleteVisitor {
  public static void apply(final AlphaSystem system) {
    final AABFT aabft = new AABFT();
    system.accept(aabft);
    InputOutput.<String>println("done");
  }

  @Override
  public void inVariableExpression(final VariableExpression ve) {
    InputOutput.<String>println(ve.getVariable().getName());
    InputOutput.<ISLSet>println(ve.getVariable().getDomain());
    InputOutput.<String>println("ve done");
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    InputOutput.<String>println(se.getVariable().getName());
    InputOutput.<ISLSet>println(se.getVariable().getDomain());
    InputOutput.<AlphaExpression>println(se.getExpr());
    InputOutput.<String>println("se done");
  }
}
