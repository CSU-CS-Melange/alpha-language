package alpha.model.tests.util;

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
    throw new Error("Unresolved compilation problems:"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face"
      + "\nThe method isBoundary(ISLMultiAff) is undefined for the type Face");
  }

  @Test
  public void testRemoveRedundancy_01() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method apply(AbstractReduceExpression) is not applicable for the arguments (ReduceExpression,ISLMultiAff)");
  }
}
