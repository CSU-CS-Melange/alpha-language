package alpha.model.tests.transformations;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.xtext.EcoreUtil2;
import org.junit.Assert;
import org.junit.runners.Parameterized.Parameters;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaSystem;
import alpha.model.BinaryExpression;
import alpha.model.MultiArgExpression;
import alpha.model.tests.GenericAlphaSystemTest;
import alpha.model.tests.data.IAlphaTestInput;
import alpha.model.tests.util.AlphaTestUtil;
import alpha.model.transformation.reduction.HoistOutOfReduction;

public class HoistOutOfReductionTest extends GenericAlphaSystemTest {

	private static final String ROOT_DIR = "resources/src-valid/transformation-tests/hoist-out-of-reduction/";
	
	public HoistOutOfReductionTest(final IAlphaTestInput test) {
		super(test);
	}
	
	@Parameters(name="{0}")
	public static Collection<Object[]> data() {
		return AlphaTestUtil.gatherTestInputs(new File(ROOT_DIR).toPath()).stream().map(f -> new Object[] { f }).collect(Collectors.toList());
	}
	
	@Override
	protected void doTest(AlphaSystem system) {
		List<BinaryExpression> bexprs = EcoreUtil2.getAllContentsOfType(system, BinaryExpression.class);
		List<MultiArgExpression> maexprs = EcoreUtil2.getAllContentsOfType(system, MultiArgExpression.class);
		
		Assert.assertTrue("HoistOutOfReductionTest assumes each system as exactly one candidate target to apply the transformation.", 
				bexprs.size() + maexprs.size() == 1);
		
		if (bexprs.size() == 1) {
			HoistOutOfReduction.apply(bexprs.get(0));
		} else {
			HoistOutOfReduction.apply(maexprs.get(0));
		}
		EcoreUtil2.getAllContentsOfType(system, AbstractReduceExpression.class).
		forEach(x->Assert.assertTrue(
				EcoreUtil2.getAllContentsOfType(x, BinaryExpression.class).isEmpty() &&
				EcoreUtil2.getAllContentsOfType(x, MultiArgExpression.class).isEmpty()));
	}
}
