package alpha.model.tests;

import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class SimplificationTest {
  @Test
  public void testReuseIntegerPoint_1() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j]: i>0 and j=0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[1, 0]");
  }

  @Test
  public void testReuseIntegerPoint_2() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j]: i<0 and j=0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[-1, 0]");
  }

  @Test
  public void testReuseIntegerPoint_3() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j]: i>0 and j>0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[1, 1]");
  }

  @Test
  public void testReuseIntegerPoint_4() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j]: i<0 and j<0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[-1, -1]");
  }

  @Test
  public void testReuseIntegerPoint_5() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j]: i<0 and j>0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[-1, 1]");
  }

  @Test
  public void testReuseIntegerPoint_6() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N]->{[i,j,k]: i<0 and j>7i and k<-4i+4}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[-1, 0, 0]");
  }

  @Test
  public void testReuseIntegerPoint_7() {
    final ISLBasicSet reuse = ISLUtil.toISLBasicSet("[N,M,L]->{[i,j]: i<0 and j>0}");
    Assert.assertEquals(((List<Long>)Conversions.doWrapArray(ISLUtil.integerPointClosestToOrigin(reuse))).toString(), "[-1, 1]");
  }

  @Test
  public void testIsTrivial_1() {
    Assert.assertFalse(ISLUtil.isTrivial(ISLUtil.toISLBasicSet("[N]->{[i,j,k]}")));
    Assert.assertTrue(ISLUtil.isTrivial(ISLUtil.toISLBasicSet("[N]->{[i,j,k] : i=0 and j=0 and k=0 }")));
    Assert.assertFalse(ISLUtil.isTrivial(ISLUtil.toISLSet("[N]->{[i,j,k] : i=0 and j=0 and (k=0 or k=1)}")));
    Assert.assertTrue(ISLUtil.isTrivial(ISLUtil.toISLSet("[N]->{[i,j,k] : 1=0}")));
  }
}
