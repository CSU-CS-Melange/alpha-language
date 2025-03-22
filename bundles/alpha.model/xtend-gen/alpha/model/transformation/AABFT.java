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
    InputOutput.<String>print("name: ");
    InputOutput.<String>println(ve.getVariable().getName());
    InputOutput.<String>print("domain: ");
    InputOutput.<ISLSet>println(ve.getVariable().getDomain());
    InputOutput.<String>print("basic sets: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbBasicSets()));
    InputOutput.<String>print("constants: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbConstants()));
    InputOutput.<String>print("divs: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbDivs()));
    InputOutput.<String>print("indices: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbIndices()));
    InputOutput.<String>print("params: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbParams()));
    InputOutput.<String>print("points: ");
    InputOutput.<Integer>println(Integer.valueOf(ve.getVariable().getDomain().getNbPoints()));
    InputOutput.<String>println("ve done");
  }

  @Override
  public void inStandardEquation(final StandardEquation se) {
    InputOutput.<String>print("name: ");
    InputOutput.<String>println(se.getVariable().getName());
    InputOutput.<String>print("domain: ");
    InputOutput.<ISLSet>println(se.getVariable().getDomain());
    InputOutput.<String>print("basic sets: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbBasicSets()));
    InputOutput.<String>print("constants: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbConstants()));
    InputOutput.<String>print("divs: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbDivs()));
    InputOutput.<String>print("indices: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbIndices()));
    InputOutput.<String>print("params: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbParams()));
    InputOutput.<String>print("points: ");
    InputOutput.<Integer>println(Integer.valueOf(se.getVariable().getDomain().getNbPoints()));
    InputOutput.<String>print("expr: ");
    InputOutput.<AlphaExpression>println(se.getExpr());
    InputOutput.<String>println("se done");
  }
}
