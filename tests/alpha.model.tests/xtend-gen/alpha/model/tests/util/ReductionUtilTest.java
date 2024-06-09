package alpha.model.tests.util;

import alpha.commands.UtilityBase;
import alpha.model.AlphaExpression;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.ComplexityCalculator;
import alpha.model.ReduceExpression;
import alpha.model.SystemBody;
import alpha.model.analysis.reduction.ReductionUtil;
import alpha.model.transformation.reduction.RemoveEmbedding;
import alpha.model.util.Face;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class ReductionUtilTest {
  @Test
  public void testNullSpace_01() {
    final ISLMultiAff maff = ISLUtil.toISLMultiAff("[N]->{[i,j,k]->[i,j]}");
    final ISLSet nullSpace = ISLUtil.nullSpace(maff);
    Assert.assertEquals(ISLUtil.dimensionality(nullSpace), 1);
  }

  @Test
  public void testNullSpace_02() {
    final ISLMultiAff maff = ISLUtil.toISLMultiAff("[N]->{[i,j,k]->[i,j-k]}");
    final ISLSet nullSpace = ISLUtil.nullSpace(maff);
    final ISLSet set = ISLUtil.toISLSet("[N]->{[i,j,k] : i=0 and j=k}");
    Assert.assertEquals(ISLUtil.dimensionality(nullSpace.copy()), 1);
    Assert.assertTrue(nullSpace.isEqual(set));
  }

  @Test
  public void testNullSpace_03() {
    final ISLMultiAff maff = ISLUtil.toISLMultiAff("[N]->{[i,j,k]->[i,j-k,k]}");
    final ISLSet nullSpace = ISLUtil.nullSpace(maff);
    Assert.assertEquals(ISLUtil.dimensionality(nullSpace.copy()), 0);
    Assert.assertTrue(nullSpace.isSingleton());
  }

  @Test
  public void testBoundary_01() {
    final ISLMultiAff fp = ISLUtil.toISLMultiAff("[N]->{[i,j]->[i]}");
    final Face face1 = FaceTest.makeFace("[N]->{[i,j] : i=j}");
    final Face face2 = FaceTest.makeFace("[N]->{[i,j] : j=2}");
    final Face face3 = FaceTest.makeFace("[N]->{[i,j] : i=N}");
    final Face face4 = FaceTest.makeFace("[N]->{[i,j] : i=0}");
    final Face face5 = FaceTest.makeFace("[N]->{[i,j] : i=2}");
    final Face face6 = FaceTest.makeFace("[N]->{[i,j] : i=17}");
    Assert.assertFalse(ReductionUtil.isBoundary(face1, fp));
    Assert.assertFalse(ReductionUtil.isBoundary(face2, fp));
    Assert.assertTrue(ReductionUtil.isBoundary(face3, fp));
    Assert.assertTrue(ReductionUtil.isBoundary(face4, fp));
    Assert.assertTrue(ReductionUtil.isBoundary(face5, fp));
    Assert.assertTrue(ReductionUtil.isBoundary(face6, fp));
  }

  @Test
  public void testRemoveRedundancy_01() {
    final AlphaRoot root = AlphaTestUtil.loadValidFile("transformation-reduction-tests/identical-answers/identicalAnswers.alpha");
    final AlphaSystem system = UtilityBase.GetSystem(root, "ex1");
    final SystemBody body = system.getSystemBodies().get(0);
    AlphaExpression _expr = UtilityBase.GetEquation(body, "Z").getExpr();
    final ReduceExpression reduceExpr = ((ReduceExpression) _expr);
    Assert.assertEquals(ComplexityCalculator.complexity(system), 3);
    final ISLMultiAff rho = ISLUtil.toISLMultiAff("[N]->{[i,j,k]->[i,j,k+1]}");
    RemoveEmbedding.apply(reduceExpr, rho);
    Assert.assertEquals(ComplexityCalculator.complexity(system), 2);
  }
}
