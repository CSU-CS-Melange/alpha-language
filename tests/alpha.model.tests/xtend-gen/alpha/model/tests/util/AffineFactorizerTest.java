package alpha.model.tests.util;

import alpha.model.util.AffineFactorizer;
import alpha.model.util.AffineFunctionOperations;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLHermiteResult;
import fr.irisa.cairn.jnimap.isl.ISLMatrix;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class AffineFactorizerTest {
  private static ISLMultiAff stringToMultiAff(final String str) {
    return ISLMultiAff.buildFromString(ISLContext.getInstance(), str);
  }

  private static ISLMatrix stringToMatrix(final String str) {
    return AffineFactorizer.expressionToMatrix(AffineFactorizerTest.stringToMultiAff(str));
  }

  private static ArrayList<ISLMultiAff> stringsToMultiAffs(final String... strs) {
    final Function1<String, ISLMultiAff> _function = (String it) -> {
      return AffineFactorizerTest.stringToMultiAff(it);
    };
    List<ISLMultiAff> _map = ListExtensions.<String, ISLMultiAff>map(((List<String>)Conversions.doWrapArray(strs)), _function);
    return new ArrayList<ISLMultiAff>(_map);
  }

  private static void mergeExpressionsTest(final String expectedOutput, final String... inputs) {
    final ArrayList<ISLMultiAff> inputAffs = AffineFactorizerTest.stringsToMultiAffs(inputs);
    final ISLMultiAff expectedAff = AffineFactorizerTest.stringToMultiAff(expectedOutput);
    final ISLMultiAff actualAff = AffineFunctionOperations.mergeExpressions(inputAffs);
    Assert.assertTrue(expectedAff.isPlainEqual(actualAff));
  }

  private static void assertMatrixIsCorrect(final List<List<Integer>> expected, final ISLMatrix actual) {
    final int rows = expected.size();
    final int cols = expected.get(0).size();
    Assert.assertEquals("Wrong number of rows", rows, actual.getNbRows());
    Assert.assertEquals("Wrong number of cols", cols, actual.getNbCols());
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, rows, true);
    for (final Integer row : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, cols, true);
      for (final Integer col : _doubleDotLessThan_1) {
        {
          final String message = ((("Wrong value at row " + row) + ", col ") + col);
          Assert.assertEquals(message, (expected.get((row).intValue()).get((col).intValue())).intValue(), actual.getElement((row).intValue(), (col).intValue()));
        }
      }
    }
  }

  private static void assertPlainEqual(final ISLMultiAff expected, final ISLMultiAff actual) {
    Assert.assertTrue(expected.isPlainEqual(actual));
  }

  private static void assertPlainEqual(final String message, final ISLMultiAff expected, final ISLMultiAff actual) {
    Assert.assertTrue(message, expected.isPlainEqual(actual));
  }

  private static ISLMultiAff getRemainingTerm(final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result, final int index, final ISLMultiAff... inputs) {
    return result.getValue().get(inputs[index]);
  }

  private static void assertFactorizationIsCorrect(final int expectedInnerDims, final String... inputs) {
    final ArrayList<ISLMultiAff> expressions = AffineFactorizerTest.stringsToMultiAffs(inputs);
    final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result = AffineFactorizer.factorizeExpressions(((ISLMultiAff[])Conversions.unwrapArray(expressions, ISLMultiAff.class)));
    final int actualInnerDims = result.getKey().dim(ISLDimType.isl_dim_out);
    Assert.assertEquals("Wrong number of inner dimensions.", expectedInnerDims, actualInnerDims);
    int _length = inputs.length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final ISLMultiAff expr = expressions.get((i).intValue());
        final ISLMultiAff remainingTerm = result.getValue().get(expr);
        final ISLMultiAff reconstructed = remainingTerm.pullback(result.getKey().copy());
        AffineFactorizerTest.assertPlainEqual((("Remaining term " + i) + " is wrong."), expr, reconstructed);
      }
    }
  }

  @Test
  public void dropCols() {
    ISLMatrix mat = AffineFactorizerTest.stringToMatrix("{ [i,j,k] -> [k,i,j] }");
    Assert.assertEquals(3, mat.getNbCols());
    mat = mat.dropCols(2, 1);
    Assert.assertEquals(2, mat.getNbCols());
  }

  @Test
  public void hermiteNormalForm() {
    final ISLMatrix input = AffineFactorizerTest.stringToMatrix("{ [i,j,k] -> [i+k,i+k+j]}");
    final ISLHermiteResult hnf_result = input.leftHermite();
    final ISLMatrix h = hnf_result.getH();
    final ISLMatrix u = hnf_result.getU();
    final ISLMatrix q = hnf_result.getQ();
    final List<List<Integer>> hExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0)))));
    final List<List<Integer>> uExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf((-1)))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1)))));
    final List<List<Integer>> qExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1)))));
    AffineFactorizerTest.assertMatrixIsCorrect(hExpected, h);
    AffineFactorizerTest.assertMatrixIsCorrect(uExpected, u);
    AffineFactorizerTest.assertMatrixIsCorrect(qExpected, q);
  }

  @Test
  public void nameOutputs_01() {
    final ArrayList<ISLMultiAff> input = AffineFactorizerTest.stringsToMultiAffs(
      "[N] -> { [i,j] -> [j,i] }", 
      "[N] -> { [i,j] -> [i+j,i-j] }");
    final HashMap<ISLMultiAff, ISLMultiAff> result = AffineFactorizer.nameExpressionOutputs(((ISLMultiAff[])Conversions.unwrapArray(input, ISLMultiAff.class)));
    Assert.assertEquals("orig_out_0", result.get(input.get(0)).getSpace().getDimName(ISLDimType.isl_dim_out, 0));
    Assert.assertEquals("orig_out_1", result.get(input.get(0)).getSpace().getDimName(ISLDimType.isl_dim_out, 1));
    Assert.assertEquals("orig_out_2", result.get(input.get(1)).getSpace().getDimName(ISLDimType.isl_dim_out, 0));
    Assert.assertEquals("orig_out_3", result.get(input.get(1)).getSpace().getDimName(ISLDimType.isl_dim_out, 1));
    Assert.assertEquals("orig_out_0", result.get(input.get(0)).getSpace().getDimName(ISLDimType.isl_dim_out, 0));
    Assert.assertEquals("orig_out_1", result.get(input.get(0)).getSpace().getDimName(ISLDimType.isl_dim_out, 1));
    Assert.assertEquals("orig_out_2", result.get(input.get(1)).getSpace().getDimName(ISLDimType.isl_dim_out, 0));
    Assert.assertEquals("orig_out_3", result.get(input.get(1)).getSpace().getDimName(ISLDimType.isl_dim_out, 1));
  }

  @Test
  public void mergeExpressions_oneInput() {
    final List<String> inputs = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("[N] -> { [x,y,z] -> [x+y,z] }"));
    final String expectedOutput = inputs.get(0);
    AffineFactorizerTest.mergeExpressionsTest(expectedOutput, ((String[])Conversions.unwrapArray(inputs, String.class)));
  }

  @Test
  public void mergeExpressions_twoInputs() {
    final List<String> inputs = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("[N] -> { [p,q,r] -> [p+q,q+r] }", "[N] -> { [p,q,r] -> [r,N-q,p+2] }"));
    final String expectedOutput = "[N] -> { [p,q,r] -> [p+q,q+r,r,N-q,p+2] }";
    AffineFactorizerTest.mergeExpressionsTest(expectedOutput, ((String[])Conversions.unwrapArray(inputs, String.class)));
  }

  @Test
  public void mergeExpressions_threeInputs() {
    final List<String> inputs = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("[N] -> { [i,j,k,l] -> [j,k] }", "[N] -> { [i,j,k,l] -> [k-j] }", "[N] -> { [i,j,k,l] -> [j-k, i-j-k+l] }"));
    final String expectedOutput = "[N] -> { [i,j,k,l] -> [j,k,k-j,j-k,i-j-k+l] }";
    AffineFactorizerTest.mergeExpressionsTest(expectedOutput, ((String[])Conversions.unwrapArray(inputs, String.class)));
  }

  @Test
  public void expressionToMatrix_01() {
    final ISLMultiAff input = AffineFactorizerTest.stringToMultiAff("{ [i,j] -> [0, 0] }");
    final ISLMatrix result = AffineFactorizer.expressionToMatrix(input);
    Assert.assertEquals(3, result.getNbRows());
    Assert.assertEquals(2, result.getNbCols());
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, 3, true);
    for (final Integer row : _doubleDotLessThan) {
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, 2, true);
      for (final Integer col : _doubleDotLessThan_1) {
        Assert.assertEquals(0, result.getElement((row).intValue(), (col).intValue()));
      }
    }
  }

  @Test
  public void expressionToMatrix_02() {
    final ISLMultiAff input = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j] -> [2N -3j +4i +1, 7-2i+3j-4N] }");
    final ISLMatrix result = AffineFactorizer.expressionToMatrix(input);
    Assert.assertEquals(4, result.getNbRows());
    Assert.assertEquals(2, result.getNbCols());
    Assert.assertEquals(2, result.getElement(0, 0));
    Assert.assertEquals(4, result.getElement(1, 0));
    Assert.assertEquals((-3), result.getElement(2, 0));
    Assert.assertEquals(1, result.getElement(3, 0));
    Assert.assertEquals((-4), result.getElement(0, 1));
    Assert.assertEquals((-2), result.getElement(1, 1));
    Assert.assertEquals(3, result.getElement(2, 1));
    Assert.assertEquals(7, result.getElement(3, 1));
  }

  @Test
  public void expressionToMatrix_03() {
    final ISLMultiAff input = AffineFactorizerTest.stringToMultiAff("{ [] -> [7] }");
    final ISLMatrix result = AffineFactorizer.expressionToMatrix(input);
    Assert.assertEquals(1, result.getNbRows());
    Assert.assertEquals(1, result.getNbCols());
    Assert.assertEquals(7, result.getElement(0, 0));
  }

  @Test
  public void reduceHermiteDimensionality_01() {
    final ISLMatrix hOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 0, 0 }, new long[] { 1, 1 }, new long[] { 2, 0 } });
    final ISLMatrix qOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 1, 1 }, new long[] { 2, 2 } });
    final Pair<ISLMatrix, ISLMatrix> result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal);
    final List<List<Integer>> expectedH = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(0)))));
    final List<List<Integer>> expectedQ = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2), Integer.valueOf(2)))));
    AffineFactorizerTest.assertMatrixIsCorrect(expectedH, result.getKey());
    AffineFactorizerTest.assertMatrixIsCorrect(expectedQ, result.getValue());
  }

  @Test
  public void reduceHermiteDimensionality_02() {
    final ISLMatrix hOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 0, 0 }, new long[] { 1, 0 }, new long[] { 2, 0 } });
    final ISLMatrix qOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 1, 1 }, new long[] { 2, 2 } });
    final Pair<ISLMatrix, ISLMatrix> result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal);
    final List<List<Integer>> expectedH = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2)))));
    final List<List<Integer>> expectedQ = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1)))));
    AffineFactorizerTest.assertMatrixIsCorrect(expectedH, result.getKey());
    AffineFactorizerTest.assertMatrixIsCorrect(expectedQ, result.getValue());
  }

  @Test
  public void reduceHermiteDimensionality_03() {
    final ISLMatrix hOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 0, 0, 0 }, new long[] { 1, 0, 0 }, new long[] { 2, 0, 0 } });
    final ISLMatrix qOriginal = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 1, 1 }, new long[] { 2, 2 }, new long[] { 3, 3 } });
    final Pair<ISLMatrix, ISLMatrix> result = AffineFactorizer.reduceHermiteDimensionality(hOriginal, qOriginal);
    final List<List<Integer>> expectedH = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(2)))));
    final List<List<Integer>> expectedQ = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1)))));
    AffineFactorizerTest.assertMatrixIsCorrect(expectedH, result.getKey());
    AffineFactorizerTest.assertMatrixIsCorrect(expectedQ, result.getValue());
  }

  @Test
  public void hermiteMatrixDecomposition_01() {
    final ISLMatrix original = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 1, 0, 0 }, new long[] { 0, 1, 0 }, new long[] { 0, 0, 1 } });
    final Pair<ISLMatrix, ISLMatrix> decomposeResult = AffineFactorizer.hermiteMatrixDecomposition(original);
    final ISLMatrix hActual = decomposeResult.getKey();
    final ISLMatrix qActual = decomposeResult.getValue();
    final List<List<Integer>> bothExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1)))));
    AffineFactorizerTest.assertMatrixIsCorrect(bothExpected, hActual);
    AffineFactorizerTest.assertMatrixIsCorrect(bothExpected, qActual);
  }

  @Test
  public void hermiteMatrixDecomposition_02() {
    final ISLMatrix original = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 1, 1, 1 }, new long[] { 0, 1, 0 }, new long[] { 1, 1, 1 } });
    final Pair<ISLMatrix, ISLMatrix> decomposeResult = AffineFactorizer.hermiteMatrixDecomposition(original);
    final ISLMatrix hActual = decomposeResult.getKey();
    final ISLMatrix qActual = decomposeResult.getValue();
    final List<List<Integer>> hExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(0)))));
    final List<List<Integer>> qExpected = Collections.<List<Integer>>unmodifiableList(CollectionLiterals.<List<Integer>>newArrayList(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1))), Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0)))));
    AffineFactorizerTest.assertMatrixIsCorrect(hExpected, hActual);
    AffineFactorizerTest.assertMatrixIsCorrect(qExpected, qActual);
  }

  @Test
  public void matrixToExpression_01() {
    final ISLMatrix matrix = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 0, 0 }, new long[] { 0, 0 }, new long[] { 0, 0 } });
    final ISLMultiAff expected = AffineFactorizerTest.stringToMultiAff("{ [i,j] -> [0, 0] }");
    final ISLMultiAff result = AffineFactorizer.toExpression(matrix, expected.getSpace());
    AffineFactorizerTest.assertPlainEqual(expected, result);
  }

  @Test
  public void matrixToExpression_02() {
    final ISLMatrix matrix = ISLMatrix.buildFromLongMatrix(
      new long[][] { new long[] { 2, (-4) }, new long[] { 4, (-2) }, new long[] { (-3), 3 }, new long[] { 1, 7 } });
    final ISLMultiAff expected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j] -> [2N -3j +4i +1, 7-2i+3j-4N] }");
    final ISLMultiAff result = AffineFactorizer.toExpression(matrix, expected.getSpace());
    AffineFactorizerTest.assertPlainEqual(expected, result);
  }

  @Test
  public void matrixToExpression_03() {
    final ISLMatrix matrix = ISLMatrix.buildFromLongMatrix(new long[][] { new long[] { 7 } });
    final ISLMultiAff expected = AffineFactorizerTest.stringToMultiAff("{ [] -> [7] }");
    final ISLMultiAff result = AffineFactorizer.toExpression(matrix, expected.getSpace());
    AffineFactorizerTest.assertPlainEqual(expected, result);
  }

  @Test
  public void hermiteExpressionDecomposition_constants() {
    final ISLMultiAff original = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [] }");
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(original);
    final ISLMultiAff hActual = decomposed.getKey();
    final ISLMultiAff qActual = decomposed.getValue();
    final ISLMultiAff hExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [] }");
    final ISLMultiAff qExpected = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    Assert.assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual));
    Assert.assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual));
  }

  @Test
  public void hermiteExpressionDecomposition_01() {
    final ISLMultiAff original = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i,j,k] }");
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(original);
    final ISLMultiAff hActual = decomposed.getKey();
    final ISLMultiAff qActual = decomposed.getValue();
    final ISLMultiAff hExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i,j,k] }");
    final ISLMultiAff qExpected = AffineFactorizerTest.stringToMultiAff("{ [i,j,k] -> [i,j,k] }");
    Assert.assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual));
    Assert.assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual));
  }

  @Test
  public void hermiteExpressionDecomposition_02() {
    final ISLMultiAff original = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i+k,i+j+k] }");
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(original);
    final ISLMultiAff hActual = decomposed.getKey();
    final ISLMultiAff qActual = decomposed.getValue();
    final ISLMultiAff hExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i+k,j] }");
    final ISLMultiAff qExpected = AffineFactorizerTest.stringToMultiAff("{ [x,y] -> [x,x+y] }");
    Assert.assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual));
    Assert.assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual));
  }

  @Test
  public void hermiteExpressionDecomposition_03() {
    final ISLMultiAff original = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i+k,i+j+k,i+k] }");
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(original);
    final ISLMultiAff hActual = decomposed.getKey();
    final ISLMultiAff qActual = decomposed.getValue();
    final ISLMultiAff hExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [i+k,j] }");
    final ISLMultiAff qExpected = AffineFactorizerTest.stringToMultiAff("{ [x,y] -> [x,x+y,x] }");
    Assert.assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual));
    Assert.assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual));
  }

  @Test
  public void hermiteExpressionDecomposition_04() {
    final ISLMultiAff original = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [0,0,0,0] }");
    final Pair<ISLMultiAff, ISLMultiAff> decomposed = AffineFactorizer.hermiteExpressionDecomposition(original);
    final ISLMultiAff hActual = decomposed.getKey();
    final ISLMultiAff qActual = decomposed.getValue();
    final ISLMultiAff hExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j,k] -> [0] }");
    final ISLMultiAff qExpected = AffineFactorizerTest.stringToMultiAff("{ [i] -> [i,0,0,0] }");
    Assert.assertTrue("The decomposed H is incorrect.", hExpected.isPlainEqual(hActual));
    Assert.assertTrue("The decomposed Q is incorrect.", qExpected.isPlainEqual(qActual));
  }

  @Test
  public void factorizeExpressions_nothing01() {
    final ISLMultiAff input = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result = AffineFactorizer.factorizeExpressions(input);
    final ISLMultiAff allExpected = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    final ISLMultiAff remainingActual = AffineFactorizerTest.getRemainingTerm(result, 0, input);
    AffineFactorizerTest.assertPlainEqual("Common factor is wrong.", allExpected, result.getKey());
    Assert.assertEquals("Number of remaining terms is wrong.", 1, result.getValue().size());
    AffineFactorizerTest.assertPlainEqual("Remaining term is wrong.", allExpected, remainingActual);
  }

  @Test
  public void factorizeExpressions_nothing02() {
    final ArrayList<ISLMultiAff> inputs = AffineFactorizerTest.stringsToMultiAffs(
      "{ [] -> [] }", 
      "{ [] -> [] }", 
      "{ [] -> [] }");
    final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result = AffineFactorizer.factorizeExpressions(((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class)));
    final ISLMultiAff allExpected = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    AffineFactorizerTest.assertPlainEqual("Common factor is wrong.", allExpected, result.getKey());
    Assert.assertEquals("Number of remaining terms is wrong.", 3, result.getValue().size());
    AffineFactorizerTest.assertPlainEqual("Remaining term 0 is wrong.", allExpected, AffineFactorizerTest.getRemainingTerm(result, 0, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
    AffineFactorizerTest.assertPlainEqual("Remaining term 1 is wrong.", allExpected, AffineFactorizerTest.getRemainingTerm(result, 1, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
    AffineFactorizerTest.assertPlainEqual("Remaining term 2 is wrong.", allExpected, AffineFactorizerTest.getRemainingTerm(result, 2, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
  }

  @Test
  public void factorizeExpressions_oneConstant() {
    final ISLMultiAff input = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j] -> [] }");
    final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result = AffineFactorizer.factorizeExpressions(input);
    final ISLMultiAff commonExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j] -> [] }");
    final ISLMultiAff remainingExpected = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    final ISLMultiAff remainingActual = AffineFactorizerTest.getRemainingTerm(result, 0, input);
    AffineFactorizerTest.assertPlainEqual("Common factor is wrong.", commonExpected, result.getKey());
    Assert.assertEquals("Number of remaining terms is wrong.", 1, result.getValue().size());
    AffineFactorizerTest.assertPlainEqual("Remaining term is wrong.", remainingExpected, remainingActual);
  }

  @Test
  public void factorizeExpressions_multipleConstants() {
    final ArrayList<ISLMultiAff> inputs = AffineFactorizerTest.stringsToMultiAffs(
      "[N] -> { [i,j] -> [] }", 
      "[N] -> { [i,j] -> [] }", 
      "[N] -> { [i,j] -> [] }");
    final Pair<ISLMultiAff, HashMap<ISLMultiAff, ISLMultiAff>> result = AffineFactorizer.factorizeExpressions(((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class)));
    final ISLMultiAff commonExpected = AffineFactorizerTest.stringToMultiAff("[N] -> { [i,j] -> [] }");
    final ISLMultiAff remainingExpected = AffineFactorizerTest.stringToMultiAff("{ [] -> [] }");
    AffineFactorizerTest.assertPlainEqual("Common factor is wrong.", commonExpected, result.getKey());
    Assert.assertEquals("Number of remaining terms is wrong.", 3, result.getValue().size());
    AffineFactorizerTest.assertPlainEqual("Remaining term 0 is wrong.", remainingExpected, AffineFactorizerTest.getRemainingTerm(result, 0, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
    AffineFactorizerTest.assertPlainEqual("Remaining term 1 is wrong.", remainingExpected, AffineFactorizerTest.getRemainingTerm(result, 1, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
    AffineFactorizerTest.assertPlainEqual("Remaining term 2 is wrong.", remainingExpected, AffineFactorizerTest.getRemainingTerm(result, 2, ((ISLMultiAff[])Conversions.unwrapArray(inputs, ISLMultiAff.class))));
  }

  @Test
  public void factorizeExpressions_01() {
    final int expectedInnerDimensions = 3;
    AffineFactorizerTest.assertFactorizationIsCorrect(expectedInnerDimensions, 
      "[N] -> { [i,j,k] -> [i,j,k] }");
  }

  @Test
  public void factorizeExpressions_02() {
    final int expectedInnerDimensions = 3;
    AffineFactorizerTest.assertFactorizationIsCorrect(expectedInnerDimensions, 
      "[N] -> { [i,j,k] -> [k,j,i] }");
  }

  @Test
  public void factorizeExpressions_03() {
    final int expectedInnerDimensions = 5;
    AffineFactorizerTest.assertFactorizationIsCorrect(expectedInnerDimensions, 
      "[N] -> { [i,j,k] -> [k,j,i,N,4] }");
  }

  @Test
  public void factorizeExpressions_04() {
    final int expectedInnerDimensions = 4;
    AffineFactorizerTest.assertFactorizationIsCorrect(expectedInnerDimensions, 
      "[N] -> { [i,j,k] -> [i+j, N+k] }", 
      "[N] -> { [i,j,k] -> [j+k, N+j+3] }");
  }

  @Test
  public void factorizeExpressions_05() {
    final int expectedInnerDimensions = 4;
    AffineFactorizerTest.assertFactorizationIsCorrect(expectedInnerDimensions, 
      "[N,M] -> { [i,j,k] -> [i+k, N-j+4] }", 
      "[N,M] -> { [i,j,k] -> [M+N+4, N+M+4] }", 
      "[N,M] -> { [i,j,k] -> [] }", 
      "[N,M] -> { [i,j,k] -> [i+k+M] }");
  }
}
