package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.ISLASTNodeVisitor;
import alpha.abft.codegen.util.MemoryMap;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class WrapperCodeGen extends SystemCodeGen {
  private final AlphaSystem v1System;

  private final AlphaSystem v2System;

  public static String generateWrapper(final AlphaSystem baselineSystem, final AlphaSystem v1System, final AlphaSystem v2System, final MemoryMap memoryMap) {
    String _xblockexpression = null;
    {
      final WrapperCodeGen generator = new WrapperCodeGen(baselineSystem, v1System, v2System, memoryMap);
      _xblockexpression = generator.generate();
    }
    return _xblockexpression;
  }

  public WrapperCodeGen(final AlphaSystem system, final AlphaSystem v1System, final AlphaSystem v2System, final MemoryMap memoryMap) {
    super(system, memoryMap);
    this.v1System = v1System;
    this.v2System = v2System;
  }

  @Override
  public String generate() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ");
    String _name = this.getSystem().getName();
    _builder.append(_name);
    _builder.append("-wrapper.c");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("#include<stdio.h>");
    _builder.newLine();
    _builder.append("#include<stdlib.h>");
    _builder.newLine();
    _builder.append("#include<math.h>");
    _builder.newLine();
    _builder.append("#include<sys/time.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("#define max(x, y)   ((x)>(y) ? (x) : (y))");
    _builder.newLine();
    _builder.append("#define min(x, y)   ((x)>(y) ? (y) : (x))");
    _builder.newLine();
    _builder.append("#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))");
    _builder.newLine();
    _builder.append("#define floord(n,d) (int)floor(((double)(n))/((double)(d)))");
    _builder.newLine();
    _builder.append("#define mallocCheck(v,s,d) if ((v) == NULL) { printf(\"Failed to allocate memory for %s : size=%lu\\n\", \"sizeof(d)*(s)\", sizeof(d)*(s)); exit(-1); }");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// External system declarations");
    _builder.newLine();
    CharSequence _signature = this.signature(this.getSystem());
    _builder.append(_signature);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _signature_1 = this.signature(this.v1System);
    _builder.append(_signature_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _signature_2 = this.signature(this.v2System);
    _builder.append(_signature_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// Memory mapped targets");
    _builder.newLine();
    final Function1<Variable, Boolean> _function = (Variable it) -> {
      return it.isLocal();
    };
    final Function1<Variable, Pair<String, ISLSet>> _function_1 = (Variable it) -> {
      String _name_1 = this.getMemoryMap().getName(it.getName());
      ISLSet _domain = it.getDomain();
      return Pair.<String, ISLSet>of(_name_1, _domain);
    };
    final Function1<Pair<String, ISLSet>, String> _function_2 = (Pair<String, ISLSet> it) -> {
      return this.memoryTargetMacro(it);
    };
    String _join = IterableExtensions.join(IterableExtensions.<Pair<String, ISLSet>, String>map(IterableExtensions.<Variable, Pair<String, ISLSet>>map(IterableExtensions.<Variable>reject(this.getSystem().getVariables(), _function), _function_1), _function_2), "\n");
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("// Memory access functions");
    _builder.newLine();
    final Function1<Variable, Boolean> _function_3 = (Variable it) -> {
      return it.isLocal();
    };
    final Function1<Variable, CharSequence> _function_4 = (Variable it) -> {
      return this.memoryMacro(it);
    };
    String _join_1 = IterableExtensions.join(IterableExtensions.<Variable, CharSequence>map(IterableExtensions.<Variable>reject(this.getSystem().getVariables(), _function_3), _function_4), "\n");
    _builder.append(_join_1);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    String _mainFunction = this.mainFunction(this.getSystem());
    _builder.append(_mainFunction);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder.toString();
  }

  public String mainFunction(final AlphaSystem system) {
    String _xblockexpression = null;
    {
      final List<String> paramNames = system.getParameterDomain().getParamNames();
      final Function1<String, String> _function = (String P) -> {
        String _xblockexpression_1 = null;
        {
          final int i = paramNames.indexOf(P);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("long ");
          _builder.append(P);
          _builder.append(" = atol(argv[");
          _builder.append((i + 1));
          _builder.append("])");
          _xblockexpression_1 = _builder.toString();
        }
        return _xblockexpression_1;
      };
      final List<String> paramInits = ListExtensions.<String, String>map(paramNames, _function);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("int main(int argc, char **argv) ");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("if (argc < ");
      int _size = paramNames.size();
      int _plus = (_size + 1);
      _builder.append(_plus, "\t");
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("printf(\"usage: %s ");
      String _join = IterableExtensions.join(paramNames, " ");
      _builder.append(_join, "\t\t");
      _builder.append("\\n\", argv[0]);");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("return 1;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("}");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Parse parameter sizes");
      _builder.newLine();
      _builder.append("\t");
      String _join_1 = IterableExtensions.join(paramInits, ";\n");
      _builder.append(_join_1, "\t");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("float threshold = atof(argv[");
      int _size_1 = paramNames.size();
      _builder.append(_size_1, "\t");
      _builder.append("]);");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder.append(_localMemoryAllocation, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("srand(0);");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      final Function1<Variable, String> _function_1 = (Variable it) -> {
        return this.inputInitialization(it);
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<Variable, String>map(system.getInputs(), _function_1), "\n");
      _builder.append(_join_2, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      final Function1<Variable, String> _function_2 = (Variable it) -> {
        return this.inputInitialization(it);
      };
      String _join_3 = IterableExtensions.join(ListExtensions.<Variable, String>map(system.getOutputs(), _function_2), "\n");
      _builder.append(_join_3, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Baseline");
      _builder.newLine();
      _builder.append("\t");
      CharSequence _call = this.call(system);
      _builder.append(_call, "\t");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("// ABFTv1");
      _builder.newLine();
      _builder.append("\t");
      CharSequence _call_1 = this.call(this.v1System);
      _builder.append(_call_1, "\t");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("// ABFTv2");
      _builder.newLine();
      _builder.append("\t");
      CharSequence _call_2 = this.call(this.v2System);
      _builder.append(_call_2, "\t");
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("return 0;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final String code = _builder.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String inputInitialization(final Variable variable) {
    String _xblockexpression = null;
    {
      String _stmtPrefix = this.getStmtPrefix();
      String _name = variable.getName();
      final String stmtName = (_stmtPrefix + _name);
      final ISLSet domain = variable.getDomain().copy().setTupleName(stmtName);
      final String indexNameStr = IterableExtensions.join(domain.getIndexNames(), ",");
      final String paramStr = ABFT.buildParamStr(variable);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"");
      String _string = domain.toString();
      _builder.append(_string);
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("schedule: \"");
      _builder.append(paramStr, "  ");
      _builder.append("->[\\");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      final Function1<String, String> _function = (String i) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{ ");
        _builder_1.append(stmtName);
        _builder_1.append("[");
        _builder_1.append(indexNameStr);
        _builder_1.append("]->[");
        _builder_1.append(i);
        _builder_1.append("] }");
        return _builder_1.toString();
      };
      String _join = IterableExtensions.join(ListExtensions.<String, String>map(domain.getIndexNames(), _function), ", \\\n");
      _builder.append(_join, "    ");
      _builder.append(" \\");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("]\"");
      _builder.newLine();
      final ISLSchedule initSchedule = ISLUtil.toISLSchedule(_builder);
      final ISLIdentifierList iterators = ISLUtil.toISLIdentifierList(((String[])Conversions.unwrapArray(domain.getIndexNames(), String.class)));
      final ISLASTBuild build = ISLASTBuild.buildFromContext(initSchedule.getDomain().copy().params()).setIterators(iterators.copy());
      final ISLASTNode node = build.generate(initSchedule.copy());
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("// ");
      String _name_1 = variable.getName();
      _builder_1.append(_name_1);
      _builder_1.append(" initialization");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("#define ");
      _builder_1.append(stmtName);
      _builder_1.append("(");
      _builder_1.append(indexNameStr);
      _builder_1.append(") ");
      String _name_2 = variable.getName();
      _builder_1.append(_name_2);
      _builder_1.append("(");
      _builder_1.append(indexNameStr);
      _builder_1.append(") = rand() % 100 + 1");
      _builder_1.newLineIfNotEmpty();
      String _code = codegenVisitor.toCode();
      _builder_1.append(_code);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("#undef ");
      _builder_1.append(stmtName);
      _builder_1.newLineIfNotEmpty();
      final String code = _builder_1.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public CharSequence call(final AlphaSystem system) {
    return this.call(system, new String[] {});
  }

  public CharSequence call(final AlphaSystem system, final String[] extraArgs) {
    CharSequence _xblockexpression = null;
    {
      final List<String> paramArgs = system.getParameterDomain().getParamNames();
      EList<Variable> _inputs = system.getInputs();
      EList<Variable> _outputs = system.getOutputs();
      final Function1<Variable, String> _function = (Variable it) -> {
        return it.getName();
      };
      final Iterable<String> ioArgs = IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, _outputs), _function);
      StringConcatenation _builder = new StringConcatenation();
      String _name = system.getName();
      _builder.append(_name);
      _builder.append("(");
      Iterable<String> _plus = Iterables.<String>concat(paramArgs, ioArgs);
      String _join = IterableExtensions.join(Iterables.<String>concat(_plus, ((Iterable<? extends String>)Conversions.doWrapArray(extraArgs))), ", ");
      _builder.append(_join);
      _builder.append(")");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  @Override
  public String localMemoryAllocation() {
    EList<Variable> _inputs = this.getSystem().getInputs();
    EList<Variable> _outputs = this.getSystem().getOutputs();
    return this.memoryAllocation(((Variable[])Conversions.unwrapArray(Iterables.<Variable>concat(_inputs, _outputs), Variable.class)));
  }
}
