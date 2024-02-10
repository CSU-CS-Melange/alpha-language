package alpha.model.tests;

import alpha.model.util.DomainOperations;
import alpha.model.util.ISLUtil;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class DomainOperationsTest {
  /**
   * Convert a nested list representation to primitive array
   */
  public static long[][] toLongArray(final List<List<Integer>> arr) {
    final Function1<List<Integer>, long[]> _function = (List<Integer> row) -> {
      final Function1<Integer, Long> _function_1 = (Integer v) -> {
        return Long.valueOf(v.longValue());
      };
      List<Long> _map = ListExtensions.<Integer, Long>map(row, _function_1);
      return ((long[]) ((long[])Conversions.unwrapArray(_map, long.class)));
    };
    List<long[]> _map = ListExtensions.<List<Integer>, long[]>map(arr, _function);
    return ((long[][]) ((long[][])Conversions.unwrapArray(_map, long[].class)));
  }

  /**
   * Asserts that two sets are equal
   */
  public static void assertEquals(final ISLBasicSet bset1, final ISLBasicSet bset2) {
    final ISLSet set1 = bset1.toSet();
    final ISLSet set2 = bset2.toSet();
    final ISLSet diff1 = set1.copy().subtract(set2.copy());
    final ISLSet diff2 = set2.copy().subtract(set1.copy());
    Assert.assertTrue(diff1.isEmpty());
    Assert.assertTrue(diff2.isEmpty());
  }

  @Test
  public void testToBasicSetFromKernel_1() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList()))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLBasicSet.buildUniverse(space));
  }

  @Test
  public void testToBasicSetFromKernel_2() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel1 = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0))))));
    final long[][] kernel2 = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))))));
    try {
      final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel1, space);
      Assert.fail("There are more columns than there are params + indices");
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        Assert.assertTrue(true);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    try {
      final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel2, space);
      Assert.fail("There are fewer columns than there are params + indices");
    } catch (final Throwable _t) {
      if (_t instanceof IllegalArgumentException) {
        Assert.assertTrue(true);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }

  @Test
  public void testToBasicSetFromKernel_3() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLUtil.toISLBasicSet("[N]->{[i,j]: i=0 and j=0}"));
  }

  @Test
  public void testToBasicSetFromKernel_4() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLUtil.toISLBasicSet("[N]->{[i,j]: j=0}"));
  }

  @Test
  public void testToBasicSetFromKernel_5() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(1))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLUtil.toISLBasicSet("[N]->{[i,j]: i=j}"));
  }

  @Test
  public void testToBasicSetFromKernel_6() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j,k]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLUtil.toISLBasicSet("[N]->{[i,j,k]: i=j}"));
  }

  @Test
  public void testToBasicSetFromKernel_7() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j,k]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLUtil.toISLBasicSet("[N]->{[i,j,k]: k=0}"));
  }

  @Test
  public void testToBasicSetFromKernel_8() {
    final ISLSpace space = ISLUtil.toISLBasicSet("[N]->{[i,j]}").getSpace();
    final long[][] kernel = DomainOperationsTest.toLongArray(Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1))))));
    final ISLBasicSet set = DomainOperations.toBasicSetFromKernel(kernel, space);
    DomainOperationsTest.assertEquals(set, ISLBasicSet.buildUniverse(space));
  }
}
