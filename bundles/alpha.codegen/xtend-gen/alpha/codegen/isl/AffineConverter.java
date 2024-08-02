package alpha.codegen.isl;

import alpha.codegen.CustomExpr;
import alpha.codegen.Factory;
import alpha.model.util.CommonExtensions;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Converts isl affine expressions to C expressions.
 * Note: some conversions produce a single C expression,
 * while others may produce a list of expressions.
 */
@SuppressWarnings("all")
public class AffineConverter {
  /**
   * Converts a multi-affine expression to a list of C expressions,
   * one for each output dimension.
   */
  public static ArrayList<CustomExpr> convertMultiAff(final ISLMultiAff multiAff) {
    final Function1<ISLAff, CustomExpr> _function = (ISLAff it) -> {
      return AffineConverter.convertAff(it);
    };
    return CommonExtensions.<CustomExpr>toArrayList(ListExtensions.<ISLAff, CustomExpr>map(multiAff.getAffs(), _function));
  }

  /**
   * Converts a single affine expression to a single C expression.
   */
  public static CustomExpr convertAff(final ISLAff aff) {
    final Function2<String, String, String> _function = (String ret, String index) -> {
      return ret.replace(index, (("(" + index) + ")"));
    };
    final String literal = IterableExtensions.<String, String>fold(aff.getInputNames(), 
      aff.toString(ISL_FORMAT.C), _function);
    return Factory.customExpr((("(" + literal) + ")"));
  }
}
