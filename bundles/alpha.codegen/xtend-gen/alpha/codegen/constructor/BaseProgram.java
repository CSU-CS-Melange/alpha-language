package alpha.codegen.constructor;

import alpha.codegen.ArrayVariable;
import alpha.codegen.GlobalMacro;
import alpha.codegen.Include;
import alpha.codegen.Program;
import alpha.codegen.factory.Factory;
import alpha.codegen.util.CodegenUtil;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import alpha.model.transformation.Normalize;
import alpha.model.util.AbstractAlphaCompleteVisitor;
import alpha.targetmapping.TargetMapping;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public abstract class BaseProgram extends AbstractAlphaCompleteVisitor {
  protected Program program;

  protected TargetMapping tm;

  protected Map<Variable, ArrayVariable> inputCVs;

  protected Map<Variable, ArrayVariable> localCVs;

  protected Map<Variable, ArrayVariable> outputCVs;

  public BaseProgram() {
    this.inputCVs = CollectionLiterals.<Variable, ArrayVariable>newHashMap();
    this.localCVs = CollectionLiterals.<Variable, ArrayVariable>newHashMap();
    this.outputCVs = CollectionLiterals.<Variable, ArrayVariable>newHashMap();
  }

  public ArrayVariable getArrayVariable(final Variable v) {
    ArrayVariable _xifexpression = null;
    Boolean _isInput = v.isInput();
    if ((_isInput).booleanValue()) {
      _xifexpression = this.inputCVs.get(v);
    } else {
      ArrayVariable _xifexpression_1 = null;
      Boolean _isLocal = v.isLocal();
      if ((_isLocal).booleanValue()) {
        _xifexpression_1 = this.localCVs.get(v);
      } else {
        ArrayVariable _xifexpression_2 = null;
        Boolean _isOutput = v.isOutput();
        if ((_isOutput).booleanValue()) {
          _xifexpression_2 = this.outputCVs.get(v);
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    }
    return _xifexpression;
  }

  public boolean defaultIncludes() {
    boolean _xblockexpression = false;
    {
      final List<String> includes = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("stdio", "stdlib", "stdbool", "math", "string", "limits", "float"));
      final Function1<String, Include> _function = (String it) -> {
        return CodegenUtil.toInclude(it);
      };
      _xblockexpression = this.program.getIncludes().addAll(ListExtensions.<String, Include>map(includes, _function));
    }
    return _xblockexpression;
  }

  public boolean defaultGlobalMacros() {
    boolean _xblockexpression = false;
    {
      final List<List<String>> macros = Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("max(a,b)", "((a)>(b)?(a):(b))")), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("min(a,b)", "((a)>(b)?(b):(a))")), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("ceild(a,b)", "(int)ceil(((double)(a))/((double)(b)))")), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("floord(a,b)", "(int)floor(((double)(a))/((double)(b)))")), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("mod(a,b)", "((a)%(b))")), Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("mallocCheck(v,s,d)", "if ((v) == NULL) { printf(\"Failed to allocate memory for %s : size=%lu\\n\", \"sizeof(d)*(s)\", sizeof(d)*(s)); exit(-1); }"))));
      final Function1<List<String>, GlobalMacro> _function = (List<String> it) -> {
        return CodegenUtil.globalMacro(it.get(0), it.get(1));
      };
      _xblockexpression = this.program.getCommonMacros().addAll(ListExtensions.<List<String>, GlobalMacro>map(macros, _function));
    }
    return _xblockexpression;
  }

  @Override
  public void inAlphaSystem(final AlphaSystem s) {
    Normalize.apply(s);
    this.program.setSystem(s);
    this.defaultIncludes();
    this.defaultGlobalMacros();
    final Consumer<Variable> _function = (Variable v) -> {
      this.inputCVs.put(v, Factory.createArrayVariable(v));
    };
    s.getInputs().forEach(_function);
    final Consumer<Variable> _function_1 = (Variable v) -> {
      this.localCVs.put(v, Factory.createArrayVariable(v));
    };
    s.getLocals().forEach(_function_1);
    final Consumer<Variable> _function_2 = (Variable v) -> {
      this.outputCVs.put(v, Factory.createArrayVariable(v));
    };
    s.getOutputs().forEach(_function_2);
  }
}
