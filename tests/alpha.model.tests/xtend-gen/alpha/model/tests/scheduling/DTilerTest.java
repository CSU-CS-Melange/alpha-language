package alpha.model.tests.scheduling;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.prdg.PRDG;
import alpha.model.prdg.PRDGGenerator;
import alpha.model.scheduler.FoutrierScheduler;
import alpha.model.scheduler.Scheduler;
import alpha.model.tiler.DTiler;
import alpha.model.tiler.Tiler;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class DTilerTest {
  private PRDG prdg;

  private AlphaSystem sys;

  private Scheduler scheduler;

  private Tiler tiler;

  public Scheduler readFile(final String file) {
    try {
      Scheduler _xblockexpression = null;
      {
        AlphaRoot root = AlphaModelLoader.loadModel(file);
        this.sys = root.getSystems().get(0);
        this.prdg = PRDGGenerator.apply(this.sys);
        FoutrierScheduler _foutrierScheduler = new FoutrierScheduler(this.prdg);
        _xblockexpression = this.scheduler = _foutrierScheduler;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public void assertBijection() {
    final Consumer<ISLMap> _function = (ISLMap map) -> {
      Assert.assertTrue(this.tiler.getTileMap().isBijective());
    };
    this.scheduler.getMaps().getMaps().forEach(_function);
  }

  public Tiler cubicTile(final int width, final int startDim, final int endDim) {
    Tiler _xblockexpression = null;
    {
      List<Integer> _nCopies = Collections.<Integer>nCopies(((endDim - startDim) + 1), Integer.valueOf(width));
      final ArrayList<Integer> tileSizes = new ArrayList<Integer>(_nCopies);
      DTiler _dTiler = new DTiler(tileSizes, this.scheduler, startDim, endDim);
      _xblockexpression = this.tiler = _dTiler;
    }
    return _xblockexpression;
  }

  @Test
  public void testDot() {
    this.readFile("resources/src-valid/basic/matmult.unit/dot.alpha");
    this.cubicTile(64, 1, 1);
    this.assertBijection();
  }

  @Test
  public void testBPMax() {
    this.readFile("resources/src-valid/kernels/bpmax.alpha");
    this.cubicTile(64, 0, 5);
    this.assertBijection();
  }

  @Test
  public void testCholesky() {
    this.readFile("resources/src-valid/kernels/cholesky.alpha");
    this.cubicTile(64, 0, 2);
    this.assertBijection();
  }
}
