package louis.hook;

import alpha.loader.AlphaLoader;
import alpha.model.AlphaCompleteVisitable;
import alpha.model.AlphaExpressionVisitable;
import alpha.model.AlphaModelSaver;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.transformation.automation.OptimalSimplifyingReductions;
import alpha.model.transformation.reduction.NormalizeReduction;
import alpha.model.util.AShow;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class Lock {
  public static void main(final String[] args) {
    final AlphaSystem system = Lock.loadSystem("fractal.alpha", "fractal2d");
    Lock.pprint(system, "Input program");
    final AlphaSystem[] optSystems = Lock.runOSR(system, false);
    final AlphaSystem optSystem = optSystems[1];
    Lock.pprint(optSystem, "SR");
  }

  public static AlphaSystem[] runOSR(final AlphaSystem system, final boolean saveToFile) {
    List<AlphaSystem> _xblockexpression = null;
    {
      final int limit = 0;
      final int targetComplexity = 1;
      final boolean verbose = true;
      final boolean doSplitting = true;
      final OptimalSimplifyingReductions osr = OptimalSimplifyingReductions.apply(system, limit, targetComplexity, doSplitting, verbose);
      Lock.printSummary(osr, saveToFile);
      final Function1<OptimalSimplifyingReductions.State, AlphaSystem> _function = (OptimalSimplifyingReductions.State it) -> {
        return it.root().getSystems().get(0);
      };
      _xblockexpression = ListExtensions.<OptimalSimplifyingReductions.State, AlphaSystem>map(osr.optimizations.get(Integer.valueOf(targetComplexity)), _function);
    }
    return ((AlphaSystem[])Conversions.unwrapArray(_xblockexpression, AlphaSystem.class));
  }

  public static AlphaSystem loadSystem(final String filename, final String systemName) {
    try {
      AlphaSystem _xblockexpression = null;
      {
        final AlphaRoot root = AlphaLoader.loadAlpha(("resources/" + filename));
        if ((root == null)) {
          throw new Exception("Failed to load example, double check spelling");
        }
        _xblockexpression = root.getSystem(systemName);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static void printSummary(final OptimalSimplifyingReductions osr, final boolean saveToFile) {
    final Map<Integer, List<OptimalSimplifyingReductions.State>> optimizations = osr.optimizations;
    final Function1<Integer, Boolean> _function = (Integer k) -> {
      int _size = optimizations.get(k).size();
      return Boolean.valueOf((_size == 0));
    };
    final Function1<Integer, Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_1 = (Integer k) -> {
      List<OptimalSimplifyingReductions.State> _get = optimizations.get(k);
      return Pair.<Integer, List<OptimalSimplifyingReductions.State>>of(k, _get);
    };
    final List<Pair<Integer, List<OptimalSimplifyingReductions.State>>> foundOptimizations = IterableExtensions.<Pair<Integer, List<OptimalSimplifyingReductions.State>>>toList(IterableExtensions.<Integer, Pair<Integer, List<OptimalSimplifyingReductions.State>>>map(IterableExtensions.<Integer>reject(optimizations.keySet(), _function), _function_1));
    final Consumer<Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_2 = (Pair<Integer, List<OptimalSimplifyingReductions.State>> keyOpts) -> {
      final List<OptimalSimplifyingReductions.State> opts = keyOpts.getValue();
      final Consumer<OptimalSimplifyingReductions.State> _function_3 = (OptimalSimplifyingReductions.State it) -> {
        final AlphaSystem s = it.root().getSystems().get(0);
        NormalizeReduction.apply(s);
        final int idx = opts.indexOf(it);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// simplification v");
        _builder.append(idx);
        _builder.newLineIfNotEmpty();
        String _showSteps = it.showSteps();
        _builder.append(_showSteps);
        _builder.newLineIfNotEmpty();
        String _print = AShow.print(s);
        _builder.append(_print);
        _builder.newLineIfNotEmpty();
        final String retString = _builder.toString();
        InputOutput.<String>println(retString);
        if (saveToFile) {
          AlphaModelSaver.writeToFile((("resources/opt/v" + Integer.valueOf(idx)) + ".alpha"), retString);
        }
      };
      opts.forEach(_function_3);
    };
    foundOptimizations.forEach(_function_2);
    InputOutput.println();
    final Consumer<Pair<Integer, List<OptimalSimplifyingReductions.State>>> _function_3 = (Pair<Integer, List<OptimalSimplifyingReductions.State>> keyOpts) -> {
      final Integer key = keyOpts.getKey();
      final List<OptimalSimplifyingReductions.State> opts = keyOpts.getValue();
      int _size = opts.size();
      String _plus = ((("Number of " + key) + "D optimizations: ") + Integer.valueOf(_size));
      InputOutput.<String>println(_plus);
    };
    foundOptimizations.forEach(_function_3);
  }

  public static boolean reductionsNotNormalized(final AlphaSystem s) {
    final Function1<Object, Boolean> _function = (Object av) -> {
      return Boolean.valueOf((av instanceof ReduceExpression));
    };
    final Function1<Object, ReduceExpression> _function_1 = (Object it) -> {
      return ((ReduceExpression) it);
    };
    final Function1<ReduceExpression, Boolean> _function_2 = (ReduceExpression re) -> {
      EObject _eContainer = re.eContainer();
      return Boolean.valueOf((_eContainer instanceof StandardEquation));
    };
    int _size = IteratorExtensions.size(IteratorExtensions.<ReduceExpression>reject(IteratorExtensions.<Object, ReduceExpression>map(IteratorExtensions.<Object>filter(EcoreUtil.<Object>getAllContents(Collections.<Object>unmodifiableList(CollectionLiterals.<Object>newArrayList(s))), _function), _function_1), _function_2));
    return (_size > 0);
  }

  public static String pprint(final AlphaCompleteVisitable av, final String str) {
    String _xblockexpression = null;
    {
      InputOutput.<String>println((str + ":"));
      _xblockexpression = InputOutput.<String>println(AShow.print(av));
    }
    return _xblockexpression;
  }

  public static String pprint(final AlphaExpressionVisitable aev, final String str) {
    String _xblockexpression = null;
    {
      InputOutput.<String>println((str + ":"));
      _xblockexpression = InputOutput.<String>println(AShow.print(aev));
    }
    return _xblockexpression;
  }
}
