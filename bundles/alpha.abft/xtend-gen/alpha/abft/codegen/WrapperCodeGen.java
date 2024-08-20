package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.ISLASTNodeVisitor;
import alpha.abft.codegen.util.MemoryMap;
import alpha.codegen.Factory;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.isl.ConditionalConverter;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class WrapperCodeGen extends SystemCodeGen {
  private static String goldSuffix = "_GOLD";

  private final AlphaSystem v1System;

  private final AlphaSystem v2System;

  private final AlphaSystem v3System;

  private final int[] v2TileSizes;

  public static String generateWrapper(final AlphaSystem baselineSystem, final AlphaSystem v1System, final AlphaSystem v2System, final AlphaSystem v3System, final MemoryMap memoryMap, final Version version, final int[] v1TileSizes, final int[] v2TileSizes) {
    String _xblockexpression = null;
    {
      final WrapperCodeGen generator = new WrapperCodeGen(baselineSystem, v1System, v2System, v3System, memoryMap, version, v1TileSizes, v2TileSizes);
      _xblockexpression = generator.generate();
    }
    return _xblockexpression;
  }

  public WrapperCodeGen(final AlphaSystem system, final AlphaSystem v1System, final AlphaSystem v2System, final AlphaSystem v3System, final MemoryMap memoryMap, final Version version, final int[] v1TileSizes, final int[] v2TileSizes) {
    super(system, memoryMap, version, v1TileSizes);
    this.v1System = v1System;
    this.v2System = v2System;
    this.v3System = v3System;
    this.v2TileSizes = v2TileSizes;
  }

  public boolean notV3() {
    Version _version = this.getVersion();
    return (!Objects.equal(_version, Version.ABFT_V3));
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
    _builder.append("#include<string.h>");
    _builder.newLine();
    _builder.append("#include<time.h>");
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
    _builder.append("#define new_result() { .valid=0, .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .inj={.");
    String _xifexpression = null;
    boolean _notV3 = this.notV3();
    if (_notV3) {
      _xifexpression = "t";
    }
    _builder.append(_xifexpression);
    _builder.append("t=0, .");
    String _xifexpression_1 = null;
    boolean _notV3_1 = this.notV3();
    if (_notV3_1) {
      _xifexpression_1 = "t";
    }
    _builder.append(_xifexpression_1);
    _builder.append("i=0, .");
    String _xifexpression_2 = null;
    boolean _notV3_2 = this.notV3();
    if (_notV3_2) {
      _xifexpression_2 = "t";
    }
    _builder.append(_xifexpression_2);
    _builder.append("j=0, .");
    String _xifexpression_3 = null;
    boolean _notV3_3 = this.notV3();
    if (_notV3_3) {
      _xifexpression_3 = "t";
    }
    _builder.append(_xifexpression_3);
    _builder.append("k=0}, .result=0.0f, .noise=0.0f}");
    _builder.newLineIfNotEmpty();
    _builder.append("#define new_result_summary() { .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .nb_detected=0L, .nb_results=0L, .result=0.0f, .noise=0.0f, .max_error=0.0f}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// External system declarations");
    _builder.newLine();
    CharSequence _signature = this.signature(this.getSystem(), Version.BASELINE);
    _builder.append(_signature);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("#if defined ");
    _builder.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY);
    _builder.newLineIfNotEmpty();
    CharSequence _sigantureParamsAsFloats = this.sigantureParamsAsFloats(this.v1System);
    _builder.append(_sigantureParamsAsFloats);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _sigantureParamsAsFloats_1 = this.sigantureParamsAsFloats(this.v2System);
    _builder.append(_sigantureParamsAsFloats_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _sigantureParamsAsFloats_2 = this.sigantureParamsAsFloats(this.v3System);
    _builder.append(_sigantureParamsAsFloats_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("#else");
    _builder.newLine();
    CharSequence _signature_1 = this.signature(this.v1System);
    _builder.append(_signature_1);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _signature_2 = this.signature(this.v2System);
    _builder.append(_signature_2);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    CharSequence _signature_3 = this.signature(this.v3System);
    _builder.append(_signature_3);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    _builder.append("struct INJ {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int ");
    String _xifexpression_4 = null;
    boolean _notV3_4 = this.notV3();
    if (_notV3_4) {
      _xifexpression_4 = "t";
    }
    _builder.append(_xifexpression_4, "\t");
    _builder.append("t;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int ");
    String _xifexpression_5 = null;
    boolean _notV3_5 = this.notV3();
    if (_notV3_5) {
      _xifexpression_5 = "t";
    }
    _builder.append(_xifexpression_5, "\t");
    _builder.append("i;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int ");
    String _xifexpression_6 = null;
    boolean _notV3_6 = this.notV3();
    if (_notV3_6) {
      _xifexpression_6 = "t";
    }
    _builder.append(_xifexpression_6, "\t");
    _builder.append("j;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("int ");
    String _xifexpression_7 = null;
    boolean _notV3_7 = this.notV3();
    if (_notV3_7) {
      _xifexpression_7 = "t";
    }
    _builder.append(_xifexpression_7, "\t");
    _builder.append("k;");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("struct Result {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int valid;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long TP;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long FP;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long TN;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long FN;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float TPR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FPR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FNR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int bit;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("struct INJ inj;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("double result;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("double noise;");
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    _builder.append("struct ResultsSummary {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float TP;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FP;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float TN;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FN;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float TPR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FPR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("float FNR;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("int bit;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long nb_detected;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long nb_results;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("double result;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("double noise;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("double max_error;");
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
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
    _builder.append("#if defined ");
    _builder.append(SystemCodeGen.ERROR_INJECTION);
    _builder.newLineIfNotEmpty();
    final Function1<Variable, Pair<String, ISLSet>> _function_3 = (Variable it) -> {
      String _name_1 = this.getMemoryMap().getName(it.getName());
      String _plus = (_name_1 + WrapperCodeGen.goldSuffix);
      ISLSet _domain = it.getDomain();
      return Pair.<String, ISLSet>of(_plus, _domain);
    };
    final Function1<Pair<String, ISLSet>, String> _function_4 = (Pair<String, ISLSet> it) -> {
      return this.memoryTargetMacro(it);
    };
    String _join_1 = IterableExtensions.join(ListExtensions.<Pair<String, ISLSet>, String>map(ListExtensions.<Variable, Pair<String, ISLSet>>map(this.getSystem().getOutputs(), _function_3), _function_4), "\n");
    _builder.append(_join_1);
    _builder.newLineIfNotEmpty();
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    _builder.append("// Memory access functions");
    _builder.newLine();
    final Function1<Variable, Boolean> _function_5 = (Variable it) -> {
      return it.isLocal();
    };
    final Function1<Variable, CharSequence> _function_6 = (Variable it) -> {
      return this.memoryMacro(it);
    };
    String _join_2 = IterableExtensions.join(IterableExtensions.<Variable, CharSequence>map(IterableExtensions.<Variable>reject(this.getSystem().getVariables(), _function_5), _function_6), "\n");
    _builder.append(_join_2);
    _builder.newLineIfNotEmpty();
    _builder.append("#if defined ");
    _builder.append(SystemCodeGen.ERROR_INJECTION);
    _builder.newLineIfNotEmpty();
    final Function1<Variable, CharSequence> _function_7 = (Variable it) -> {
      return this.memoryMacro(it, WrapperCodeGen.goldSuffix);
    };
    String _join_3 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(this.getSystem().getOutputs(), _function_7), "\n");
    _builder.append(_join_3);
    _builder.newLineIfNotEmpty();
    _builder.append("#endif");
    _builder.newLine();
    _builder.newLine();
    String _mainFunction = this.mainFunction(this.getSystem());
    _builder.append(_mainFunction);
    _builder.newLineIfNotEmpty();
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
      final Function1<String, String> _function_1 = (String P) -> {
        String _xblockexpression_1 = null;
        {
          final int i = paramNames.indexOf(P);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("float ");
          _builder.append(P);
          _builder.append(" = atof(argv[");
          _builder.append((i + 1));
          _builder.append("])");
          _xblockexpression_1 = _builder.toString();
        }
        return _xblockexpression_1;
      };
      final List<String> paramAsFloatsInits = ListExtensions.<String, String>map(paramNames, _function_1);
      final List<String> indexNames = this.getStencilVar().getDomain().getIndexNames();
      final int sDims = indexNames.size();
      final Function1<Integer, String> _function_2 = (Integer it) -> {
        return "%*d";
      };
      final String indexNameStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_2), ",");
      final Function1<Integer, String> _function_3 = (Integer i) -> {
        String _get = this.getStencilVar().getDomain().getIndexNames().get((i).intValue());
        return ("sBox, r.inj.t" + _get);
      };
      final String injStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_3), ", ");
      final Function1<Integer, String> _function_4 = (Integer it) -> {
        return "%*s";
      };
      final String indexNameHeaderStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_4), ",");
      final Function1<Integer, String> _function_5 = (Integer i) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("sBox, \"inj.t");
        String _get = this.getStencilVar().getDomain().getIndexNames().get((i).intValue());
        _builder.append(_get);
        _builder.append("\"");
        return _builder.toString();
      };
      final String injHeaderStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_5), ", ");
      final Function1<Integer, String> _function_6 = (Integer i) -> {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("sBox, \"-\"");
        return _builder.toString();
      };
      final String injSummaryStr = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_6), ", ");
      final int TT = this.getTileSizes()[0];
      int _size = ((List<Integer>)Conversions.doWrapArray(this.getTileSizes())).size();
      final Function1<Integer, Integer> _function_7 = (Integer i) -> {
        return Integer.valueOf(this.getTileSizes()[(i).intValue()]);
      };
      final Iterable<Integer> TXs = IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size, true), _function_7);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("(rand() % ((T/");
      _builder.append(TT);
      _builder.append(")-2) + 2)");
      final List<String> tInjectionSite = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_builder.toString()));
      final Function1<Integer, String> _function_8 = (Integer TX) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("(rand() % ((N/");
        _builder_1.append(TX);
        _builder_1.append(")-2) + 1)");
        return _builder_1.toString();
      };
      final Iterable<String> sInjectionSite = IterableExtensions.<Integer, String>map(TXs, _function_8);
      final Iterable<String> injectionSite = Iterables.<String>concat(tInjectionSite, sInjectionSite);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("(rand() % (T-2*");
      _builder_1.append(TT);
      _builder_1.append(") + ");
      _builder_1.append(TT);
      _builder_1.append(")");
      final List<String> v3tInjectionSite = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(_builder_1.toString()));
      final Function1<Integer, String> _function_9 = (Integer TX) -> {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("(rand() % (N-2*");
        _builder_2.append(TX);
        _builder_2.append(") + ");
        _builder_2.append(TX);
        _builder_2.append(")");
        return _builder_2.toString();
      };
      final Iterable<String> v3sInjectionSite = IterableExtensions.<Integer, String>map(TXs, _function_9);
      final Iterable<String> v3InjectionSite = Iterables.<String>concat(v3tInjectionSite, v3sInjectionSite);
      final String thresholdVarV1 = "threshold_v1";
      final String thresholdVarV2 = "threshold_v2";
      final String thresholdVarV3 = "threshold_v3";
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("#ifdef ");
      _builder_2.append(SystemCodeGen.ERROR_INJECTION);
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("static int tBox;");
      _builder_2.newLine();
      _builder_2.append("static int sBox;");
      _builder_2.newLine();
      _builder_2.append("static int rBox;");
      _builder_2.newLine();
      _builder_2.append("static int runBox;");
      _builder_2.newLine();
      _builder_2.append("static char eol[2];");
      _builder_2.newLine();
      _builder_2.append("static int run;");
      _builder_2.newLine();
      _builder_2.append("static int R;");
      _builder_2.newLine();
      _builder_2.append("static int log_flag;");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("void printHeader() {");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("int S = 300;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("char header_str[S];");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("sprintf(header_str, \"   %*s : (%*s,");
      _builder_2.append(indexNameHeaderStr, "\t");
      _builder_2.append(") : (%*s,%*s,%*s,%*s) : %*s, %*s, %*s, %*s\", 4, \"bit\", tBox, \"inj.tt\", ");
      _builder_2.append(injHeaderStr, "\t");
      _builder_2.append(", rBox, \"TP\", rBox, \"FP\", rBox, \"TN\", rBox, \"FN\", 12, \"Detected (%)\", 7, \"FPR (%)\", 14, \"Max rel. error\", 2 * runBox + 1, \"Runs\");");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("char header_bar[S]; for (int i=0; i<S; i++) header_bar[i] = \'-\';");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("fprintf(stdout, \"%.*s\\n\", (int)strlen(header_str), header_bar);");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("fprintf(stdout, \"%s\\n\", header_str);");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("fprintf(stdout, \"%.*s\\n\", (int)strlen(header_str), header_bar);");
      _builder_2.newLine();
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("void print(int version, struct Result r, double max_error) {");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("if (!log_flag) {");
      _builder_2.newLine();
      _builder_2.append("\t\t");
      _builder_2.append("return;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("int detected = r.TP > 0 ? 1 : 0;");
      _builder_2.newLine();
      _builder_2.append("    ");
      _builder_2.append("printf(\"v%d,%*d : (%*d,");
      _builder_2.append(indexNameStr, "    ");
      _builder_2.append(") : (%*ld,%*ld,%*ld,%*ld) : %*d, %*.2f, %*E, %*d/%d%s\", version, 4, r.bit, tBox, r.inj.tt, ");
      _builder_2.append(injStr, "    ");
      _builder_2.append(", rBox, r.TP, rBox, r.FP, rBox, r.TN, rBox, r.FN, 12, detected, 7, r.FPR, 14, max_error, runBox, run, R, eol);");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("    ");
      _builder_2.append("fflush(stdout);");
      _builder_2.newLine();
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("void print_summary(int version, struct ResultsSummary *s) {");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->TP = s->TP / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->FP = s->FP / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->TN = s->TN / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->FN = s->FN / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->TPR = s->TPR / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->FPR = s->FPR / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("s->FNR = s->FNR / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("float detected_rate =100 * s->nb_detected / s->nb_results;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("printf(\"v%d,%*d : (%*s,");
      _builder_2.append(indexNameHeaderStr, "\t");
      _builder_2.append(") : (%*.2f,%*.2f,%*.2f,%*.2f) : %*.2f, %*.2f, %*E, %*d/%d\\n\", version, 4, s->bit, tBox, \"-\", ");
      _builder_2.append(injSummaryStr, "\t");
      _builder_2.append(", rBox, s->TP, rBox, s->FP, rBox, s->TN, rBox, s->FN, 12, detected_rate, 7, s->FPR, 14, s->max_error, runBox, run, R);");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("void accumulate_result(struct ResultsSummary *acc, struct Result r) {");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->TP += r.TP;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->FP += r.FP;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->TN += r.TN;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->FN += r.FN;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->TPR += r.TPR;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->FPR += r.FPR;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->FNR += r.FNR;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->bit = r.bit;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("if (r.TP > 0) {");
      _builder_2.newLine();
      _builder_2.append("\t\t");
      _builder_2.append("acc->nb_detected++;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("acc->nb_results++;");
      _builder_2.newLine();
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.append("#endif");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("int main(int argc, char **argv) ");
      _builder_2.newLine();
      _builder_2.append("{");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("if (argc < ");
      int _size_1 = paramNames.size();
      int _plus = (_size_1 + 1);
      _builder_2.append(_plus, "\t");
      _builder_2.append(") {");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t\t");
      _builder_2.append("printf(\"usage: %s ");
      String _join = IterableExtensions.join(paramNames, " ");
      _builder_2.append(_join, "\t\t");
      _builder_2.append("\\n\", argv[0]);");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t\t");
      _builder_2.append("return 1;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("// Parse parameter sizes");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#if defined ");
      _builder_2.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      String _join_1 = IterableExtensions.join(paramAsFloatsInits, ";\n");
      _builder_2.append(_join_1, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("#else");
      _builder_2.newLine();
      _builder_2.append("\t");
      String _join_2 = IterableExtensions.join(paramInits, ";\n");
      _builder_2.append(_join_2, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("#endif");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("// Check parameter values");
      _builder_2.newLine();
      _builder_2.append("\t");
      CharSequence _checkParamValues = this.checkParamValues();
      _builder_2.append(_checkParamValues, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#if defined ");
      _builder_2.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      CharSequence _variableDeclarations = this.variableDeclarations();
      _builder_2.append(_variableDeclarations, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#else");
      _builder_2.newLine();
      _builder_2.append("\t");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder_2.append(_localMemoryAllocation, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("srand(time(NULL));");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      final Function1<Variable, String> _function_10 = (Variable it) -> {
        return this.inputInitialization(it);
      };
      String _join_3 = IterableExtensions.join(ListExtensions.<Variable, String>map(system.getInputs(), _function_10), "\n");
      _builder_2.append(_join_3, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("#endif");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#if defined ");
      _builder_2.append(SystemCodeGen.TIMING, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("struct Result r0 = ");
      CharSequence _call = this.call(system);
      _builder_2.append(_call, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("printf(\"v0 time: %0.4f sec\\n\", r0.result);");
      _builder_2.newLine();
      {
        if ((this.v1System != null)) {
          _builder_2.append("\t");
          _builder_2.append("struct Result r1 = ");
          CharSequence _call_1 = this.call(this.v1System);
          _builder_2.append(_call_1, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("printf(\"v1 time: %0.4f sec\\n\", r1.result);");
          _builder_2.newLine();
        }
      }
      {
        if ((this.v2System != null)) {
          _builder_2.append("\t");
          _builder_2.append("struct Result r2 = ");
          CharSequence _call_2 = this.call(this.v2System);
          _builder_2.append(_call_2, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("printf(\"v2 time: %0.4f sec\\n\", r2.result);");
          _builder_2.newLine();
        }
      }
      {
        if ((this.v3System != null)) {
          _builder_2.append("\t");
          _builder_2.append("struct Result r3 = ");
          CharSequence _call_3 = this.call(this.v3System);
          _builder_2.append(_call_3, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("printf(\"v3 time: %0.4f sec\\n\", r3.result);");
          _builder_2.newLine();
        }
      }
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#elif defined ");
      _builder_2.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder_2.newLineIfNotEmpty();
      {
        if ((this.v1System != null)) {
          _builder_2.append("\t");
          CharSequence _call_4 = this.call(this.v1System);
          _builder_2.append(_call_4, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
        }
      }
      {
        if ((this.v2System != null)) {
          _builder_2.append("\t");
          CharSequence _call_5 = this.call(this.v2System);
          _builder_2.append(_call_5, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
        }
      }
      {
        if ((this.v3System != null)) {
          _builder_2.append("\t");
          CharSequence _call_6 = this.call(this.v3System);
          _builder_2.append(_call_6, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
        }
      }
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#elif defined ");
      _builder_2.append(SystemCodeGen.ERROR_INJECTION, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("tBox = max((int)log10(T) + 1, 7);");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("sBox = max((int)log10(N) + 1, 7);");
      _builder_2.newLine();
      {
        if ((this.v3System != null)) {
          _builder_2.append("\t");
          _builder_2.append("rBox = (int)log10(2*(T/(float)");
          int _get = this.getTileSizes()[0];
          _builder_2.append(_get, "\t");
          _builder_2.append(")*(N/(float)");
          int _get_1 = this.getTileSizes()[1];
          _builder_2.append(_get_1, "\t");
          _builder_2.append(")");
          String _xifexpression = null;
          if ((sDims > 2)) {
            _xifexpression = "*";
          }
          _builder_2.append(_xifexpression, "\t");
          final Function1<Integer, String> _function_11 = (Integer it) -> {
            return "N";
          };
          String _join_4 = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(2, sDims, true), _function_11), "*");
          _builder_2.append(_join_4, "\t");
          _builder_2.append(") + 4;");
          _builder_2.newLineIfNotEmpty();
        } else {
          _builder_2.append("\t");
          _builder_2.append("rBox = (int)log10(2*(T/(float)");
          int _get_2 = this.getTileSizes()[0];
          _builder_2.append(_get_2, "\t");
          _builder_2.append(")*");
          final Function1<Integer, String> _function_12 = (Integer i) -> {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("(N/(float)");
            int _get_3 = this.getTileSizes()[1];
            _builder_3.append(_get_3);
            _builder_3.append(")");
            return _builder_3.toString();
          };
          String _join_5 = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(1, sDims, true), _function_12), "*");
          _builder_2.append(_join_5, "\t");
          _builder_2.append(") + 4;");
          _builder_2.newLineIfNotEmpty();
        }
      }
      _builder_2.append("\t");
      _builder_2.append("if (getenv(\"VERBOSE\") != NULL) {");
      _builder_2.newLine();
      _builder_2.append("\t\t");
      _builder_2.append("strcpy(eol, \"\\n\");");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("} else {");
      _builder_2.newLine();
      _builder_2.append("\t\t");
      _builder_2.append("strcpy(eol, \"\\r\");");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("}");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("log_flag = (getenv(\"LOG\") == NULL) ? 1 : 0;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#define export_injs() do { ");
      final Function1<Pair<String, String>, String> _function_13 = (Pair<String, String> it) -> {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("{ int ival = ");
        String _value = it.getValue();
        _builder_3.append(_value);
        _builder_3.append("; sprintf(val, \"%d\", ival); setenv(\"t");
        String _key = it.getKey();
        _builder_3.append(_key);
        _builder_3.append("_INJ\", val, 1); }");
        return _builder_3.toString();
      };
      String _join_6 = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(indexNames, injectionSite), _function_13), " ");
      _builder_2.append(_join_6, "\t");
      _builder_2.append(" } while(0)");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("R = (getenv(\"NUM_RUNS\") != NULL) ? atoi(getenv(\"NUM_RUNS\")) : 100;");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("runBox = (int)log10(R) + 1;");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("// if THRESHOLD not explicitly set, do short profiling to estimate the");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("// noise due to floating point round off errors");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("const char* threshold = getenv(\"THRESHOLD\");");
      _builder_2.newLine();
      {
        if ((this.v1System != null)) {
          _builder_2.append("\t");
          _builder_2.append("float ");
          _builder_2.append(thresholdVarV1, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("if (threshold == NULL) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct Result result;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("long input_T = T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = ");
          Integer _max = IterableExtensions.<Integer>max(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(20), Integer.valueOf((4 * TT)))));
          _builder_2.append(_max, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("result = ");
          CharSequence _call_7 = this.call(this.v1System);
          _builder_2.append(_call_7, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\"floating point noise: %E\\n\", result.noise);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("float thresholds[10] = { 1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (int i=9; i>=0; i--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append(thresholdVarV1, "\t\t\t");
          _builder_2.append(" = thresholds[i];");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("if (");
          _builder_2.append(thresholdVarV1, "\t\t\t");
          _builder_2.append(" >= fabs(result.noise))");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t\t");
          _builder_2.append("break;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\" ");
          _builder_2.append(thresholdVarV1, "\t\t");
          _builder_2.append(" set to: %E\\n\", ");
          _builder_2.append(thresholdVarV1, "\t\t");
          _builder_2.append(");");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = input_T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("} else {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append(thresholdVarV1, "\t\t");
          _builder_2.append(" = atof(getenv(\"THRESHOLD\"));");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      {
        if ((this.v2System != null)) {
          _builder_2.append("\t");
          _builder_2.append("float ");
          _builder_2.append(thresholdVarV2, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("if (threshold == NULL) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct Result result;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("long input_T = T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = ");
          Integer _max_1 = IterableExtensions.<Integer>max(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(20), Integer.valueOf((4 * TT)))));
          _builder_2.append(_max_1, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("result = ");
          CharSequence _call_8 = this.call(this.v2System);
          _builder_2.append(_call_8, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\"floating point noise: %E\\n\", result.noise);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("float thresholds[10] = { 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (int i=9; i>=0; i--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append(thresholdVarV2, "\t\t\t");
          _builder_2.append(" = thresholds[i];");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("if (");
          _builder_2.append(thresholdVarV2, "\t\t\t");
          _builder_2.append(" >= fabs(result.noise))");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t\t");
          _builder_2.append("break;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\" ");
          _builder_2.append(thresholdVarV2, "\t\t");
          _builder_2.append(" set to: %E\\n\", ");
          _builder_2.append(thresholdVarV2, "\t\t");
          _builder_2.append(");");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = input_T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("} else {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append(thresholdVarV2, "\t\t");
          _builder_2.append(" = atof(getenv(\"THRESHOLD\"));");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      {
        if ((this.v3System != null)) {
          _builder_2.append("\t");
          _builder_2.append("float ");
          _builder_2.append(thresholdVarV3, "\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("if (threshold == NULL) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct Result result;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("long input_T = T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = ");
          Integer _max_2 = IterableExtensions.<Integer>max(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(20), Integer.valueOf((4 * TT)))));
          _builder_2.append(_max_2, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("result = ");
          CharSequence _call_9 = this.call(this.v3System);
          _builder_2.append(_call_9, "\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\"floating point noise: %E\\n\", result.noise);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("float thresholds[10] = { 1e-2, 1e-3, 1e-4, 1e-5, 1e-6, 1e-7, 1e-8, 1e-9, 1e-10, 0.0 };");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (int i=9; i>=0; i--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append(thresholdVarV3, "\t\t\t");
          _builder_2.append(" = thresholds[i];");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("if (");
          _builder_2.append(thresholdVarV3, "\t\t\t");
          _builder_2.append(" >= fabs(result.noise))");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t\t");
          _builder_2.append("break;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("printf(\" ");
          _builder_2.append(thresholdVarV3, "\t\t");
          _builder_2.append(" set to: %E\\n\", ");
          _builder_2.append(thresholdVarV3, "\t\t");
          _builder_2.append(");");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("T = input_T;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("} else {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append(thresholdVarV3, "\t\t");
          _builder_2.append(" = atof(getenv(\"THRESHOLD\"));");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      _builder_2.append("\t");
      _builder_2.append("printHeader();");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      final Function1<String, String> _function_14 = (String i) -> {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("int ");
        _builder_3.append(i);
        _builder_3.append("_INJ");
        return _builder_3.toString();
      };
      String _join_7 = IterableExtensions.join(ListExtensions.<String, String>map(this.getStencilVar().getDomain().getIndexNames(), _function_14), "; \n");
      _builder_2.append(_join_7, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("// Get GOLD result, run the input program");
      _builder_2.newLine();
      _builder_2.append("\t");
      CharSequence _call_10 = this.call(system, WrapperCodeGen.goldSuffix);
      _builder_2.append(_call_10, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("const char* verbose = getenv(\"VERBOSE\");");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("const char* single_bit = getenv(\"BIT\");");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("char val[50];");
      _builder_2.newLine();
      {
        if ((this.v1System != null)) {
          _builder_2.append("\t");
          _builder_2.append("// ABFTv1");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%E\", ");
          _builder_2.append(thresholdVarV1, "\t");
          _builder_2.append("); ");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("setenv(\"THRESHOLD\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("for (int bit=31; bit>=8; bit--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("if (single_bit != NULL && atoi(single_bit) != bit)");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("continue;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("char val[50];");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%d\", bit); ");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("setenv(\"bit\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct ResultsSummary v_avg = new_result_summary();");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (run=0; run<R; run++) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          final Function1<Pair<String, String>, String> _function_15 = (Pair<String, String> it) -> {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("{ int ival = ");
            String _value = it.getValue();
            _builder_3.append(_value);
            _builder_3.append("; sprintf(val, \"%d\", ival); setenv(\"t");
            String _key = it.getKey();
            _builder_3.append(_key);
            _builder_3.append("_INJ\", val, 1); }");
            return _builder_3.toString();
          };
          String _join_8 = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(indexNames, injectionSite), _function_15), " ");
          _builder_2.append(_join_8, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("struct Result v = ");
          CharSequence _call_11 = this.call(this.v1System);
          _builder_2.append(_call_11, "\t\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("// Compare output with GOLD");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          String _compareWithGold = this.compareWithGold(thresholdVarV1);
          _builder_2.append(_compareWithGold, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("v_avg.max_error = max(v_avg.max_error, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("accumulate_result(&v_avg, v);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("print(1, v, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("print_summary(1, &v_avg);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      {
        if ((this.v2System != null)) {
          {
            if ((this.v1System != null)) {
              _builder_2.append("\t");
              _builder_2.append("printHeader();");
              _builder_2.newLine();
            }
          }
          _builder_2.append("\t");
          _builder_2.append("// ABFTv2");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%E\", ");
          _builder_2.append(thresholdVarV2, "\t");
          _builder_2.append("); ");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("setenv(\"THRESHOLD\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("for (int bit=31; bit>=8; bit--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("if (single_bit != NULL && atoi(single_bit) != bit)");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("continue;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("char val[50];");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%d\", bit); ");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("setenv(\"bit\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct ResultsSummary v_avg = new_result_summary();");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (run=0; run<R; run++) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          final Function1<Pair<String, String>, String> _function_16 = (Pair<String, String> it) -> {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("{ int ival = ");
            String _value = it.getValue();
            _builder_3.append(_value);
            _builder_3.append("; sprintf(val, \"%d\", ival); setenv(\"t");
            String _key = it.getKey();
            _builder_3.append(_key);
            _builder_3.append("_INJ\", val, 1); }");
            return _builder_3.toString();
          };
          String _join_9 = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(indexNames, injectionSite), _function_16), " ");
          _builder_2.append(_join_9, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("struct Result v = ");
          CharSequence _call_12 = this.call(this.v2System);
          _builder_2.append(_call_12, "\t\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("// Compare output with GOLD");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          String _compareWithGold_1 = this.compareWithGold(thresholdVarV2);
          _builder_2.append(_compareWithGold_1, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("v_avg.max_error = max(v_avg.max_error, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("accumulate_result(&v_avg, v);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("print(2, v, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("print_summary(2, &v_avg);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      _builder_2.newLine();
      {
        if ((this.v3System != null)) {
          {
            if (((this.v1System != null) && (this.v2System != null))) {
              _builder_2.append("\t");
              _builder_2.append("printHeader();");
              _builder_2.newLine();
            }
          }
          _builder_2.append("\t");
          _builder_2.append("// ABFTv3");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%E\", ");
          _builder_2.append(thresholdVarV3, "\t");
          _builder_2.append("); ");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("setenv(\"THRESHOLD\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("for (int bit=31; bit>=0; bit--) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("if (single_bit != NULL && atoi(single_bit) != bit)");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("continue;");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("char val[50];");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("sprintf(val, \"%d\", bit); ");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("setenv(\"bit\", val, 1);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("struct ResultsSummary v_avg = new_result_summary();");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("for (run=0; run<R; run++) {");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          final Function1<Pair<String, String>, String> _function_17 = (Pair<String, String> it) -> {
            String _xblockexpression_1 = null;
            {
              int _xifexpression_1 = (int) 0;
              String _key = it.getKey();
              boolean _equals = Objects.equal(_key, "t");
              if (_equals) {
                _xifexpression_1 = this.getTileSizes()[0];
              } else {
                _xifexpression_1 = this.getTileSizes()[1];
              }
              final int TS = _xifexpression_1;
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("{ int ival=0; while (ival % ");
              _builder_3.append(TS);
              _builder_3.append(" == 0) {ival = ");
              String _value = it.getValue();
              _builder_3.append(_value);
              _builder_3.append("; sprintf(val, \"%d\", ival); setenv(\"");
              String _key_1 = it.getKey();
              _builder_3.append(_key_1);
              _builder_3.append("_INJ\", val, 1); }}");
              _xblockexpression_1 = _builder_3.toString();
            }
            return _xblockexpression_1;
          };
          String _join_10 = IterableExtensions.join(ListExtensions.<Pair<String, String>, String>map(CommonExtensions.<String, String>zipWith(indexNames, v3InjectionSite), _function_17), "\n");
          _builder_2.append(_join_10, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("struct Result v = ");
          CharSequence _call_13 = this.call(this.v3System);
          _builder_2.append(_call_13, "\t\t\t");
          _builder_2.append(";");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("// Compare output with GOLD");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          String _compareWithGold_2 = this.compareWithGold(thresholdVarV3);
          _builder_2.append(_compareWithGold_2, "\t\t\t");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("v_avg.max_error = max(v_avg.max_error, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("accumulate_result(&v_avg, v);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t\t");
          _builder_2.append("print(3, v, max_error);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("\t");
          _builder_2.append("print_summary(3, &v_avg);");
          _builder_2.newLine();
          _builder_2.append("\t");
          _builder_2.append("}");
          _builder_2.newLine();
        }
      }
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("#endif");
      _builder_2.newLine();
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("return 0;");
      _builder_2.newLine();
      _builder_2.append("}");
      _builder_2.newLine();
      final String code = _builder_2.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String compareWithGold(final String thresholdVar) {
    final Function1<Variable, String> _function = (Variable v) -> {
      return this.compareWithGold(v, thresholdVar);
    };
    return IterableExtensions.join(ListExtensions.<Variable, String>map(this.getSystem().getOutputs(), _function), "\n");
  }

  public String compareWithGold(final Variable variable, final String thresholdVar) {
    String _xblockexpression = null;
    {
      final String name = variable.getName();
      final List<String> indexNames = variable.getDomain().getIndexNames();
      final String idxStr = IterableExtensions.join(indexNames, ",");
      String _join = IterableExtensions.join(variable.getDomain().copy().params().getParamNames(), ",");
      String _plus = ("[" + _join);
      final String paramStr = (_plus + "]");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(paramStr);
      _builder.append("->{[");
      _builder.append(idxStr);
      _builder.append("] : t=T}");
      ISLSet _intersect = variable.getDomain().copy().intersect(ISLUtil.toISLSet(_builder.toString()));
      String _stmtPrefix = this.getStmtPrefix();
      String _plus_1 = (_stmtPrefix + name);
      final ISLUnionSet domain = _intersect.setTupleName(_plus_1).toUnionSet();
      StringConcatenation _builder_1 = new StringConcatenation();
      String _stmtPrefix_1 = this.getStmtPrefix();
      _builder_1.append(_stmtPrefix_1);
      _builder_1.append(name);
      _builder_1.append("[");
      _builder_1.append(idxStr);
      _builder_1.append("]");
      final String SVar = _builder_1.toString();
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("domain: \"");
      String _string = domain.toString();
      _builder_2.append(_string);
      _builder_2.append("\"");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("child:");
      _builder_2.newLine();
      _builder_2.append("  ");
      _builder_2.append("schedule: \"");
      _builder_2.append(paramStr, "  ");
      _builder_2.append("->[");
      final Function1<String, String> _function = (String i) -> {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("{ ");
        _builder_3.append(SVar);
        _builder_3.append("->[");
        _builder_3.append(i);
        _builder_3.append("] }");
        return _builder_3.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function), ",");
      _builder_2.append(_join_1, "  ");
      _builder_2.append("]\"");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("  ");
      _builder_2.newLine();
      final ISLSchedule ISchedule = ISLUtil.toISLSchedule(_builder_2);
      final ISLIdentifierList iterators = ISLUtil.toISLIdentifierList(((String[])Conversions.unwrapArray(indexNames, String.class)));
      final ISLASTBuild build = ISLASTBuild.buildFromContext(ISchedule.getDomain().copy().params()).setIterators(iterators.copy());
      final ISLASTNode node = build.generate(ISchedule.copy());
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(name);
      _builder_3.append("(");
      _builder_3.append(idxStr);
      _builder_3.append(")");
      final String varAcc = _builder_3.toString();
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append((name + WrapperCodeGen.goldSuffix));
      _builder_4.append("(");
      _builder_4.append(idxStr);
      _builder_4.append(")");
      final String goldVarAcc = _builder_4.toString();
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("double max_error = 0.0f;");
      _builder_5.newLine();
      _builder_5.append("#define ");
      String _stmtPrefix_2 = this.getStmtPrefix();
      _builder_5.append(_stmtPrefix_2);
      _builder_5.append(name);
      _builder_5.append("(");
      _builder_5.append(idxStr);
      _builder_5.append(") max_error = max(max_error, fabs((");
      _builder_5.append(varAcc);
      _builder_5.append(" / ");
      _builder_5.append(goldVarAcc);
      _builder_5.append(") - 1))");
      _builder_5.newLineIfNotEmpty();
      String _code = codegenVisitor.toCode();
      _builder_5.append(_code);
      _builder_5.newLineIfNotEmpty();
      _builder_5.append("#undef ");
      String _stmtPrefix_3 = this.getStmtPrefix();
      _builder_5.append(_stmtPrefix_3);
      _builder_5.append(name);
      _builder_5.newLineIfNotEmpty();
      final String code = _builder_5.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public CharSequence checkParamValues() {
    CharSequence _xblockexpression = null;
    {
      final int nbParams = this.getSystem().getParameterDomain().dim(ISLDimType.isl_dim_param);
      final ISLSet domain = this.getSystem().getParameterDomain().copy().moveDims(ISLDimType.isl_dim_out, 0, ISLDimType.isl_dim_param, 0, nbParams);
      final ISLSet universe = ISLSet.buildUniverse(domain.getSpace().copy());
      final ISLSet illegalParamValues = universe.subtract(domain.copy());
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("if ((");
      CharSequence _printExpr = ProgramPrinter.printExpr(ConditionalConverter.convert(illegalParamValues));
      _builder.append(_printExpr);
      _builder.append(") || T < ");
      int _get = this.getTileSizes()[0];
      int _multiply = (3 * _get);
      _builder.append(_multiply);
      _builder.append(") {");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("printf(\"Illegal parameter values, must be in ");
      _builder.append(domain, "\t");
      _builder.append(" and T > ");
      int _get_1 = this.getTileSizes()[0];
      int _multiply_1 = (3 * _get_1);
      _builder.append(_multiply_1, "\t");
      _builder.append("\\n\");");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("return 1;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder;
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
      _builder_1.append(") = rand()");
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
    CharSequence _call = null;
    if (system!=null) {
      _call=this.call(system, "");
    }
    return _call;
  }

  public CharSequence call(final AlphaSystem system, final String suffix) {
    CharSequence _xblockexpression = null;
    {
      final List<String> paramArgs = system.getParameterDomain().getParamNames();
      final Function1<Variable, Variable> _function = (Variable it) -> {
        return AlphaUtil.<Variable>copyAE(it);
      };
      final Function1<Variable, Variable> _function_1 = (Variable it) -> {
        Variable _xblockexpression_1 = null;
        {
          String _name = it.getName();
          String _plus = (_name + suffix);
          it.setName(_plus);
          _xblockexpression_1 = it;
        }
        return _xblockexpression_1;
      };
      final List<Variable> outputs = ListExtensions.<Variable, Variable>map(ListExtensions.<Variable, Variable>map(system.getOutputs(), _function), _function_1);
      EList<Variable> _inputs = system.getInputs();
      final Function1<Variable, String> _function_2 = (Variable it) -> {
        return it.getName();
      };
      final Iterable<String> ioArgs = IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, outputs), _function_2);
      StringConcatenation _builder = new StringConcatenation();
      String _name = system.getName();
      _builder.append(_name);
      _builder.append("(");
      String _join = IterableExtensions.join(Iterables.<String>concat(paramArgs, ioArgs), ", ");
      _builder.append(_join);
      _builder.append(")");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  @Override
  public String localMemoryAllocation() {
    StringConcatenation _builder = new StringConcatenation();
    EList<Variable> _inputs = this.getSystem().getInputs();
    EList<Variable> _outputs = this.getSystem().getOutputs();
    String _memoryAllocation = this.memoryAllocation(((Variable[])Conversions.unwrapArray(Iterables.<Variable>concat(_inputs, _outputs), Variable.class)));
    _builder.append(_memoryAllocation);
    _builder.newLineIfNotEmpty();
    String _memoryAllocation_1 = this.memoryAllocation(((Variable[])Conversions.unwrapArray(this.getSystem().getOutputs(), Variable.class)), WrapperCodeGen.goldSuffix);
    _builder.append(_memoryAllocation_1);
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }

  public CharSequence variableDeclarations() {
    StringConcatenation _builder = new StringConcatenation();
    EList<Variable> _inputs = this.getSystem().getInputs();
    EList<Variable> _outputs = this.getSystem().getOutputs();
    final Function1<Variable, CharSequence> _function = (Variable it) -> {
      return this.variableDeclaration(it);
    };
    String _join = IterableExtensions.join(IterableExtensions.<Variable, CharSequence>map(Iterables.<Variable>concat(_inputs, _outputs), _function), ";\n");
    _builder.append(_join);
    _builder.append(";");
    return _builder;
  }

  public CharSequence variableDeclaration(final Variable variable) {
    CharSequence _xblockexpression = null;
    {
      final int dim = variable.getDomain().dim(ISLDimType.isl_dim_out);
      StringConcatenation _builder = new StringConcatenation();
      String _print = ProgramPrinter.print(Factory.dataType(this.getDataType(), dim));
      _builder.append(_print);
      _builder.append(" ");
      String _name = variable.getName();
      _builder.append(_name);
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
}
