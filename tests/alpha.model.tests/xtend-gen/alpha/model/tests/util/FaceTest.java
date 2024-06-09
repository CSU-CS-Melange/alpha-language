package alpha.model.tests.util;

import alpha.model.util.Face;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class FaceTest {
  /**
   * Creates a face from the desired set.
   */
  public static Face makeFace(final String setDescriptor) {
    final ISLBasicSet set = ISLBasicSet.buildFromString(ISLContext.getInstance(), setDescriptor).removeRedundancies();
    return new Face(set);
  }

  /**
   * The empty set.
   */
  public static final String emptySetString = "{[i,j]: 0<i<j and j<0}";

  public static Face emptySet() {
    return FaceTest.makeFace(FaceTest.emptySetString);
  }

  /**
   * A set containing a single point.
   */
  public static final String vertexString = "{[i,j]: i=0 and j=1}";

  public static Face vertex() {
    return FaceTest.makeFace(FaceTest.vertexString);
  }

  /**
   * A line which is infinite in both directions.
   */
  public static final String infiniteLineString = "{[i]:}";

  public static Face infiniteLine() {
    return FaceTest.makeFace(FaceTest.infiniteLineString);
  }

  /**
   * A ray, or a line which has one point and extends infinitely in one direction.
   */
  public static final String rayString = "{[i]: 0<=i}";

  public static Face ray() {
    return FaceTest.makeFace(FaceTest.rayString);
  }

  /**
   * A line segment which is defined between two constants.
   */
  public static final String constantLineSegmentString = "{[i]: 0<=i<=10}";

  public static Face constantLineSegment() {
    return FaceTest.makeFace(FaceTest.constantLineSegmentString);
  }

  /**
   * A line segment from 0 to N.
   */
  public static final String parameterizedLineSegmentString = "[N]->{[i]: 0<=i<N}";

  public static Face parameterizedLineSegment() {
    return FaceTest.makeFace(FaceTest.parameterizedLineSegmentString);
  }

  /**
   * Checks that the number of children of a face is correct.
   */
  public static void assertChildCount(final Face face, final int expectedChildren) {
    Assert.assertEquals(expectedChildren, face.generateChildren().size());
  }

  @Test
  public void testChildCount_01() {
    FaceTest.assertChildCount(FaceTest.emptySet(), 0);
  }

  @Test
  public void testChildCount_02() {
    FaceTest.assertChildCount(FaceTest.vertex(), 0);
  }

  @Test
  public void testChildCount_03() {
    FaceTest.assertChildCount(FaceTest.infiniteLine(), 0);
  }

  @Test
  public void testChildCount_04() {
    FaceTest.assertChildCount(FaceTest.ray(), 1);
  }

  @Test
  public void testChildCount_05() {
    FaceTest.assertChildCount(FaceTest.constantLineSegment(), 0);
  }

  @Test
  public void testChildCount_06() {
    FaceTest.assertChildCount(FaceTest.parameterizedLineSegment(), 2);
  }

  /**
   * Checks that the dimensionality of a face is correct.
   */
  public static void assertDimensionality(final Face face, final int expectedDimensionality) {
    Assert.assertEquals(expectedDimensionality, face.getDimensionality());
  }

  @Test
  public void testDimensionality_01() {
    FaceTest.assertDimensionality(FaceTest.emptySet(), 0);
  }

  @Test
  public void testDimensionality_02() {
    FaceTest.assertDimensionality(FaceTest.vertex(), 0);
  }

  @Test
  public void testDimensionality_03() {
    FaceTest.assertDimensionality(FaceTest.infiniteLine(), 1);
  }

  @Test
  public void testDimensionality_04() {
    FaceTest.assertDimensionality(FaceTest.ray(), 1);
  }

  @Test
  public void testDimensionality_05() {
    FaceTest.assertDimensionality(FaceTest.parameterizedLineSegment(), 1);
  }

  /**
   * If a line segment is bounded by two constants, the constraints are effectively saturated,
   * resulting in the face being reported as being effectively the same as a vertex.
   */
  @Test
  public void testDimensionality_06() {
    FaceTest.assertDimensionality(FaceTest.constantLineSegment(), 0);
  }

  public static void assertBasicSetMatches(final String descriptor) {
    final ISLBasicSet basicSet = ISLBasicSet.buildFromString(ISLContext.getInstance(), descriptor);
    final Face face = FaceTest.makeFace(descriptor);
    Assert.assertTrue(basicSet.isEqual(face.toBasicSet()));
  }

  @Test
  public void testBasicSetMatches_01() {
    FaceTest.assertBasicSetMatches(FaceTest.emptySetString);
  }

  @Test
  public void testBasicSetMatches_02() {
    FaceTest.assertBasicSetMatches(FaceTest.vertexString);
  }

  @Test
  public void testBasicSetMatches_03() {
    FaceTest.assertBasicSetMatches(FaceTest.infiniteLineString);
  }

  @Test
  public void testBasicSetMatches_04() {
    FaceTest.assertBasicSetMatches(FaceTest.rayString);
  }

  @Test
  public void testBasicSetMatches_05() {
    FaceTest.assertBasicSetMatches(FaceTest.constantLineSegmentString);
  }

  @Test
  public void testBasicSetMatches_06() {
    FaceTest.assertBasicSetMatches(FaceTest.parameterizedLineSegmentString);
  }

  @Test
  public void testChildren_01() {
    final String descriptor = "[N] -> {[i]: 0<=i<=N and N>=1}";
    final Face face = FaceTest.makeFace(descriptor);
    final ArrayList<Face> children = face.generateChildren();
    Assert.assertEquals(2, children.size());
    final ISLBasicSet iEqualsZero = ISLBasicSet.buildFromString(ISLContext.getInstance(), "[N] -> {[i]: i=0 and N>=1}");
    final Function1<Face, Boolean> _function = (Face it) -> {
      return Boolean.valueOf(it.toBasicSet().isEqual(iEqualsZero.copy()));
    };
    Assert.assertTrue(IterableExtensions.<Face>exists(children, _function));
    final ISLBasicSet iEqualsN = ISLBasicSet.buildFromString(ISLContext.getInstance(), "[N] -> {[i]: i=N and N>=1}");
    final Function1<Face, Boolean> _function_1 = (Face it) -> {
      return Boolean.valueOf(it.toBasicSet().isEqual(iEqualsN.copy()));
    };
    Assert.assertTrue(IterableExtensions.<Face>exists(children, _function_1));
  }

  @Test
  public void testToLinearSpace_01() {
    final String descriptor = "[N] -> {[i,j]: 0<=i,j<=N}";
    final Face face = FaceTest.makeFace(descriptor);
    final Function1<Face, ISLBasicSet> _function = (Face it) -> {
      return it.toLinearSpace();
    };
    final List<ISLBasicSet> lps = ListExtensions.<Face, ISLBasicSet>map(face.generateChildren(), _function);
    Assert.assertEquals(lps.get(0).toString(), "[N] -> { [i, j] : i = 0 }");
    Assert.assertEquals(lps.get(1).toString(), "[N] -> { [i, j] : i = 0 }");
    Assert.assertEquals(lps.get(2).toString(), "[N] -> { [i, j] : j = 0 }");
    Assert.assertEquals(lps.get(3).toString(), "[N] -> { [i, j] : j = 0 }");
  }
}
