package alpha.model.tests.transformations;

import alpha.loader.AlphaLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.transformation.automation.SimplifyingReductionOptimalSimplificationAlgorithm;
import alpha.model.util.AlphaUtil;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class OptimalSimplificationAlgorithmTest {
  @Test
  public void testOSRA_1() {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/src-valid/simplifying-reductions/simplices.alpha");
      final String systemName = "ex_2d";
      final AlphaSystem system = AlphaUtil.getSystem(AlphaUtil.firstPackage(root), systemName);
      final SimplifyingReductionOptimalSimplificationAlgorithm optimizer = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system);
      final List<AlphaRoot> roots = optimizer.getOptimizedPrograms();
      Assert.assertEquals(roots.size(), 2);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  @Test
  public void testOSRA_2() {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/src-valid/simplifying-reductions/simplices.alpha");
      final String systemName = "ex_3d";
      final AlphaSystem system = AlphaUtil.getSystem(AlphaUtil.firstPackage(root), systemName);
      final SimplifyingReductionOptimalSimplificationAlgorithm optimizer = SimplifyingReductionOptimalSimplificationAlgorithm.apply(system);
      final List<AlphaRoot> roots = optimizer.getOptimizedPrograms();
      Assert.assertEquals(roots.size(), 12);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
