package alpha.model.tests.scheduling;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.scheduler.FoutrierScheduler;
import alpha.model.scheduler.ScheduleVerifier;
import alpha.model.transformation.Normalize;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Test;

@SuppressWarnings("all")
public class FoutrierSchedulerTest {
  private AlphaSystem sys;

  private PRDG prdg;

  public PRDG readFile(final String file) {
    try {
      PRDG _xblockexpression = null;
      {
        AlphaRoot root = AlphaModelLoader.loadModel(file);
        this.sys = root.getSystems().get(0);
        Normalize.apply(this.sys);
        _xblockexpression = this.prdg = PRDGGenerator.apply(this.sys);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public void assertScheduleValid() {
    FoutrierScheduler _foutrierScheduler = new FoutrierScheduler(this.prdg);
    ScheduleVerifier.verify(this.sys, _foutrierScheduler);
  }

  @Test
  public void testDot() {
    this.readFile("resources/src-valid/basic/matmult.unit/dot.alpha");
    this.assertScheduleValid();
  }

  @Test
  public void testBPMax() {
    this.readFile("resources/src-valid/kernels/bpmax.alpha");
    this.assertScheduleValid();
  }

  @Test
  public void cholesky() {
    this.readFile("resources/src-valid/kernels/cholesky.alpha");
    this.assertScheduleValid();
  }
}
