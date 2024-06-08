package alpha.model.analysis.reduction;

import alpha.model.util.CommonExtensions;
import alpha.model.util.Face;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ReductionUtil {
  /**
   * Returns true if face is a boundary face under the projection function, or false otherwise
   * per Theorem 6 of GR06.
   */
  public static boolean isBoundary(final Face face, final ISLMultiAff projection) {
    final ISLSet lp = face.toLinearSpace().toSet();
    final ISLSet accumulationSpace = ISLUtil.nullSpace(projection);
    return accumulationSpace.isSubset(lp);
  }

  /**
   * Returns true if all POS and NEG faces are boundaries, or false otherwise
   */
  public static boolean hasAllZeroNonBoundaries(final Face.Label[] labeling, final Face[] facets, final ISLMultiAff fp) {
    final Function1<Pair<Face.Label, Face>, Boolean> _function = (Pair<Face.Label, Face> lf) -> {
      return Boolean.valueOf((Objects.equal(lf.getKey(), Face.Label.ZERO) || ReductionUtil.isBoundary(lf.getValue(), fp)));
    };
    return IterableExtensions.<Pair<Face.Label, Face>>forall(CommonExtensions.<Face.Label, Face>zipWith(((Iterable<Face.Label>)Conversions.doWrapArray(labeling)), ((Iterable<Face>)Conversions.doWrapArray(facets))), _function);
  }
}
