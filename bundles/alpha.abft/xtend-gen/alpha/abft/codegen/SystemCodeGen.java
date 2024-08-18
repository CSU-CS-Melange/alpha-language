package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.ISLASTNodeVisitor;
import alpha.abft.codegen.util.MemoryMap;
import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.BaseDataType;
import alpha.codegen.CastExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.Factory;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.ExprConverter;
import alpha.codegen.demandDriven.WriteC;
import alpha.codegen.demandDriven.WriteCTypeGenerator;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.PolynomialConverter;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.Equation;
import alpha.model.ReduceExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.transformation.StandardizeNames;
import alpha.model.util.AShow;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ArrayExtensions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class SystemCodeGen {
  public static String ERROR_INJECTION = "ERROR_INJECTION";

  public static String TIMING = "TIMING";

  public static String REPORT_COMPLEXITY_ONLY = "REPORT_COMPLEXITY_ONLY";

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final AlphaSystem system;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final SystemBody systemBody;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final ISLSchedule schedule;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final String scheduleStr;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final ISLUnionSet scheduleDomain;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final ExprConverter exprConverter;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final MemoryMap memoryMap;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final String stmtPrefix;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final Variable stencilVar;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final Version version;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final int[] tileSizes;

  @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PROTECTED_SETTER })
  private final BaseDataType dataType;

  public SystemCodeGen(final AlphaSystem system, final MemoryMap memoryMap, final Version version, final int[] tileSizes) {
    this(system, SystemCodeGen.defaultSchedule(system), memoryMap, version, tileSizes);
  }

  public SystemCodeGen(final AlphaSystem system, final String schedule, final MemoryMap memoryMap, final Version version, final int[] tileSizes) {
    try {
      int _size = system.getOutputs().size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        throw new Exception("Codegen for systems with more than 1 output variable not currently implemented");
      }
      this.system = system;
      this.systemBody = system.getSystemBodies().get(0);
      MemoryMap _elvis = null;
      if (memoryMap != null) {
        _elvis = memoryMap;
      } else {
        MemoryMap _memoryMap = new MemoryMap(system);
        _elvis = _memoryMap;
      }
      this.memoryMap = _elvis;
      this.dataType = BaseDataType.FLOAT;
      final WriteCTypeGenerator typeGenerator = new WriteCTypeGenerator(this.dataType, false);
      final AlphaNameChecker nameChecker = new AlphaNameChecker(false);
      ExprConverter _exprConverter = new ExprConverter(typeGenerator, nameChecker);
      this.exprConverter = _exprConverter;
      this.stmtPrefix = "S";
      this.stencilVar = system.getOutputs().get(0);
      this.version = version;
      this.tileSizes = tileSizes;
      this.scheduleDomain = this.buildScheduleDomain();
      this.scheduleStr = SystemCodeGen.injectIndices(schedule, this.scheduleDomain, this.stmtPrefix);
      this.schedule = ISLUtil.toISLSchedule(this.scheduleStr);
      StandardizeNames.apply(system);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public ISLUnionSet buildScheduleDomain() {
    ISLUnionSet _xblockexpression = null;
    {
      final Function1<StandardEquation, Boolean> _function = (StandardEquation it) -> {
        Variable _variable = it.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, this.stencilVar));
      };
      AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function).getExpr();
      final EList<AlphaExpression> yCBEs = ((CaseExpression) _expr).getExprs();
      int _size = yCBEs.size();
      final Function1<Integer, Pair<Integer, AlphaExpression>> _function_1 = (Integer i) -> {
        AlphaExpression _get = yCBEs.get((i).intValue());
        return Pair.<Integer, AlphaExpression>of(i, _get);
      };
      final Function2<ISLUnionSet, Pair<Integer, AlphaExpression>, ISLUnionSet> _function_2 = (ISLUnionSet ret, Pair<Integer, AlphaExpression> p) -> {
        ISLUnionSet _xblockexpression_1 = null;
        {
          final Integer i = p.getKey();
          final AlphaExpression expr = p.getValue();
          ISLSet _copy = expr.getContextDomain().copy();
          String _name = this.stencilVar.getName();
          String _plus = (_name + "_cb");
          String _plus_1 = (_plus + i);
          _xblockexpression_1 = ret.union(_copy.setTupleName(_plus_1).toUnionSet());
        }
        return _xblockexpression_1;
      };
      final ISLUnionSet yDomain = IterableExtensions.<Pair<Integer, AlphaExpression>, ISLUnionSet>fold(IterableExtensions.<Integer, Pair<Integer, AlphaExpression>>map(new ExclusiveRange(0, _size, true), _function_1), 
        ISLUnionSet.buildEmpty(this.stencilVar.getDomain().getSpace()), _function_2);
      ISLUnionSet _xifexpression = null;
      boolean _equals = Objects.equal(this.version, Version.ABFT_V2);
      if (_equals) {
        ISLUnionSet _xblockexpression_1 = null;
        {
          final Function1<StandardEquation, Boolean> _function_3 = (StandardEquation it) -> {
            String _name = it.getVariable().getName();
            return Boolean.valueOf(Objects.equal(_name, "C2"));
          };
          final StandardEquation c2Eq = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function_3);
          AlphaExpression _expr_1 = c2Eq.getExpr();
          final EList<AlphaExpression> c2CBEs = ((CaseExpression) _expr_1).getExprs();
          int _size_1 = c2CBEs.size();
          final Function1<Integer, Pair<Integer, AlphaExpression>> _function_4 = (Integer i) -> {
            AlphaExpression _get = c2CBEs.get((i).intValue());
            return Pair.<Integer, AlphaExpression>of(i, _get);
          };
          final Function2<ISLUnionSet, Pair<Integer, AlphaExpression>, ISLUnionSet> _function_5 = (ISLUnionSet ret, Pair<Integer, AlphaExpression> p) -> {
            ISLUnionSet _xblockexpression_2 = null;
            {
              final Integer i = p.getKey();
              final AlphaExpression expr = p.getValue();
              _xblockexpression_2 = ret.union(expr.getContextDomain().copy().setTupleName(("C2_cb" + i)).toUnionSet());
            }
            return _xblockexpression_2;
          };
          _xblockexpression_1 = IterableExtensions.<Pair<Integer, AlphaExpression>, ISLUnionSet>fold(IterableExtensions.<Integer, Pair<Integer, AlphaExpression>>map(new ExclusiveRange(0, _size_1, true), _function_4), 
            ISLUnionSet.buildEmpty(c2Eq.getVariable().getDomain().getSpace()), _function_5);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        ISLUnionSet _xifexpression_1 = null;
        if ((Objects.equal(this.version, Version.BASELINE) || Objects.equal(this.version, Version.WRAPPER))) {
          _xifexpression_1 = ISLUnionSet.buildEmpty(this.stencilVar.getDomain().getSpace());
        } else {
          ISLUnionSet _xblockexpression_2 = null;
          {
            final Function1<StandardEquation, Boolean> _function_3 = (StandardEquation it) -> {
              String _name = it.getVariable().getName();
              return Boolean.valueOf(Objects.equal(_name, "C2"));
            };
            final StandardEquation c2Eq = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function_3);
            _xblockexpression_2 = SystemCodeGen.getStmtDomain(c2Eq.getVariable(), c2Eq.getExpr()).setTupleName("C2").toUnionSet();
          }
          _xifexpression_1 = _xblockexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      final ISLUnionSet c2Domain = _xifexpression;
      final Function1<StandardEquation, Boolean> _function_3 = (StandardEquation it) -> {
        return Boolean.valueOf((Objects.equal(it.getVariable(), this.stencilVar) || Objects.equal(it.getVariable().getName(), "C2")));
      };
      final Function1<StandardEquation, Pair<String, ISLSet>> _function_4 = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        ISLSet _stmtDomain = SystemCodeGen.getStmtDomain(it.getVariable(), it.getExpr());
        return Pair.<String, ISLSet>of(_name, _stmtDomain);
      };
      final Function1<Pair<String, ISLSet>, ISLUnionSet> _function_5 = (Pair<String, ISLSet> it) -> {
        return it.getValue().setTupleName(it.getKey()).copy().toUnionSet();
      };
      final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_6 = (ISLUnionSet ret, ISLUnionSet d) -> {
        return ret.union(d);
      };
      final ISLUnionSet domain = IterableExtensions.<ISLUnionSet, ISLUnionSet>fold(IterableExtensions.<Pair<String, ISLSet>, ISLUnionSet>map(IterableExtensions.<StandardEquation, Pair<String, ISLSet>>map(IterableExtensions.<StandardEquation>reject(this.systemBody.getStandardEquations(), _function_3), _function_4), _function_5), ISLUtil.toEmptyUnionSet(this.systemBody.getParameterDomain().getSpace()), _function_6);
      _xblockexpression = domain.union(yDomain).union(c2Domain);
    }
    return _xblockexpression;
  }

  public static String defaultSchedule(final AlphaSystem system) {
    String _xblockexpression = null;
    {
      final Function1<Variable, Boolean> _function = (Variable it) -> {
        return it.isInput();
      };
      final Function1<Variable, ISLSet> _function_1 = (Variable it) -> {
        return it.getDomain().setTupleName(it.getName());
      };
      final Function1<ISLSet, ISLUnionSet> _function_2 = (ISLSet it) -> {
        return it.toUnionSet();
      };
      final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_3 = (ISLUnionSet d1, ISLUnionSet d2) -> {
        return d1.union(d2);
      };
      final ISLUnionSet domain = IterableExtensions.<ISLUnionSet>reduce(IterableExtensions.<ISLSet, ISLUnionSet>map(IterableExtensions.<Variable, ISLSet>map(IterableExtensions.<Variable>reject(system.getVariables(), _function), _function_1), _function_2), _function_3);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"");
      _builder.append(domain);
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      final String schedule = _builder.toString();
      _xblockexpression = schedule.toString();
    }
    return _xblockexpression;
  }

  public static String generateSystemCode(final AlphaSystem system, final CharSequence schedule, final MemoryMap memoryMap, final Version version, final int[] tileSizes) {
    return SystemCodeGen.generateSystemCode(system, schedule.toString(), memoryMap, version, tileSizes);
  }

  public static String generateSystemCode(final AlphaSystem system, final String schedule, final MemoryMap memoryMap, final Version version, final int[] tileSizes) {
    try {
      String _xblockexpression = null;
      {
        int _size = system.getSystemBodies().size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          throw new Exception("Only systems with a single body are currently supported");
        }
        final SystemCodeGen generator = new SystemCodeGen(system, schedule, memoryMap, version, tileSizes);
        _xblockexpression = generator.generate();
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  protected String generate() {
    String _xblockexpression = null;
    {
      final int TT = this.tileSizes[0];
      int _size = ((List<Integer>)Conversions.doWrapArray(this.tileSizes)).size();
      final Function1<Integer, Integer> _function = (Integer i) -> {
        return Integer.valueOf(this.tileSizes[(i).intValue()]);
      };
      final Integer TS = IterableExtensions.<Integer>max(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size, true), _function));
      StringConcatenation _builder = new StringConcatenation();
      String _aboutComments = this.aboutComments();
      _builder.append(_aboutComments);
      _builder.newLineIfNotEmpty();
      _builder.append("#include<stdio.h>");
      _builder.newLine();
      _builder.append("#include<stdlib.h>");
      _builder.newLine();
      _builder.append("#include<math.h>");
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
      _builder.append("#define new_result() { .valid=0, .TP=0L, .FP=0L, .TN=0L, .FN=0L, .TPR=0.0f, .FPR=0.0f, .FNR=0.0f, .bit=0, .inj={.tt=0, .ti=0, .tj=0, .tk=0}, .result=0.0f, .noise=0.0f }");
      _builder.newLine();
      _builder.newLine();
      _builder.append("void initialize_timer();");
      _builder.newLine();
      _builder.append("void reset_timer();");
      _builder.newLine();
      _builder.append("void start_timer();");
      _builder.newLine();
      _builder.append("void stop_timer();");
      _builder.newLine();
      _builder.append("double elapsed_time();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("struct INJ {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int tt;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int ti;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int tj;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("int tk;");
      _builder.newLine();
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
      _builder.append("// Memory mapped targets");
      _builder.newLine();
      final Function1<Pair<String, ISLSet>, String> _function_1 = (Pair<String, ISLSet> it) -> {
        return this.memoryTargetMacro(it);
      };
      String _join = IterableExtensions.join(ListExtensions.<Pair<String, ISLSet>, String>map(this.memoryMap.uniqueTargets(), _function_1), "\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("// Memory access functions");
      _builder.newLine();
      final Function1<Variable, CharSequence> _function_2 = (Variable it) -> {
        return this.memoryMacro(it);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(this.system.getVariables(), _function_2), "\n");
      _builder.append(_join_1);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      {
        if (((Objects.equal(this.version, Version.ABFT_V1) || Objects.equal(this.version, Version.ABFT_V2)) || Objects.equal(this.version, Version.ABFT_V3))) {
          _builder.append("#ifdef ERROR_INJECTION");
          _builder.newLine();
          _builder.append("// Error injection harness");
          _builder.newLine();
          final Function1<String, String> _function_3 = (String i) -> {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("int t");
            _builder_1.append(i);
            _builder_1.append("_INJ");
            return _builder_1.toString();
          };
          String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_3), ";\n");
          _builder.append(_join_2);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("int BIT;");
          _builder.newLine();
          _builder.newLine();
          _builder.append("void inject_");
          String _name = this.system.getName();
          _builder.append(_name);
          _builder.append("(float *val) {");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("int *bits;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("bits = (int*)val;");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("*bits ^= 1 << BIT;");
          _builder.newLine();
          _builder.append("}");
          _builder.newLine();
          _builder.append("#endif");
          _builder.newLine();
          _builder.newLine();
        }
      }
      _builder.append("#if defined ");
      _builder.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY);
      _builder.newLineIfNotEmpty();
      CharSequence _sigantureParamsAsFloats = this.sigantureParamsAsFloats(this.system);
      _builder.append(_sigantureParamsAsFloats);
      _builder.newLineIfNotEmpty();
      _builder.append("#else");
      _builder.newLine();
      CharSequence _signature = this.signature(this.system);
      _builder.append(_signature);
      _builder.newLineIfNotEmpty();
      _builder.append("#endif");
      _builder.newLine();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#if defined ");
      _builder.append(SystemCodeGen.REPORT_COMPLEXITY_ONLY, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      CharSequence _computeComplexity = this.computeComplexity();
      _builder.append(_computeComplexity, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("#else");
      _builder.newLine();
      {
        if (((Objects.equal(this.version, Version.ABFT_V1) || Objects.equal(this.version, Version.ABFT_V2)) || Objects.equal(this.version, Version.ABFT_V3))) {
          _builder.append("\t");
          _builder.append("#if defined ERROR_INJECTION");
          _builder.newLine();
          _builder.append("\t");
          _builder.append("// Error injection configuration");
          _builder.newLine();
          _builder.append("\t");
          final Function1<String, String> _function_4 = (String i) -> {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("t");
            _builder_1.append(i);
            _builder_1.append("_INJ = getenv(\"t");
            _builder_1.append(i);
            _builder_1.append("_INJ\") != NULL ? atoi(getenv(\"t");
            _builder_1.append(i);
            _builder_1.append("_INJ\")) : -1");
            return _builder_1.toString();
          };
          String _join_3 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_4), ";\n");
          _builder.append(_join_3, "\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("BIT = getenv(\"bit\") != NULL ? atoi(getenv(\"bit\")) : (int)(rand() % ");
          int _xifexpression = (int) 0;
          boolean _equals = Objects.equal(this.dataType, BaseDataType.FLOAT);
          if (_equals) {
            _xifexpression = 32;
          } else {
            _xifexpression = 64;
          }
          _builder.append(_xifexpression, "\t");
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("\t");
          _builder.append("#endif");
          _builder.newLine();
        }
      }
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder.append(_localMemoryAllocation, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      String _defStmtMacros = this.defStmtMacros();
      _builder.append(_defStmtMacros, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Timers");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("double execution_time;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("initialize_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("start_timer();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      String _stmtLoops = this.stmtLoops();
      _builder.append(_stmtLoops, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("stop_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("execution_time = elapsed_time();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      String _undefStmtMacros = this.undefStmtMacros();
      _builder.append(_undefStmtMacros, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      String _prepareResult = this.prepareResult();
      _builder.append(_prepareResult, "\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t");
      String _localMemoryFree = this.localMemoryFree();
      _builder.append(_localMemoryFree, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("return result;");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#endif");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      final String code = _builder.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String aboutComments() {
    String _xblockexpression = null;
    {
      final String[] scheduleLines = this.scheduleStr.toString().split("\n");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/* ");
      String _name = this.system.getName();
      _builder.append(_name);
      _builder.append(".c");
      _builder.newLineIfNotEmpty();
      _builder.append(" ");
      _builder.append("* ");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("* Code generation of the following Alpha system:");
      _builder.newLine();
      {
        String[] _split = AShow.print(this.system).split("\n");
        for(final String line : _split) {
          _builder.append(" ");
          _builder.append("*\t ");
          _builder.append(line, " ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append(" ");
      _builder.append("*");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("* Uses the memory map:");
      _builder.newLine();
      {
        String[] _split_1 = this.memoryMap.toString().split("\n");
        for(final String line_1 : _split_1) {
          _builder.append(" ");
          _builder.append("*\t ");
          _builder.append(line_1, " ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append(" ");
      _builder.append("*");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("* Implements the schedule:");
      _builder.newLine();
      {
        int _size = ((List<String>)Conversions.doWrapArray(scheduleLines)).size();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _size, true);
        for(final Integer i : _doubleDotLessThan) {
          _builder.append(" ");
          _builder.append("*   ");
          String _get = scheduleLines[(i).intValue()];
          _builder.append(_get, " ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append(" ");
      _builder.append("*");
      _builder.newLine();
      _builder.append(" ");
      _builder.append("*/");
      _builder.newLine();
      final String code = _builder.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public CharSequence computeComplexity() {
    CharSequence _xblockexpression = null;
    {
      final ISLPWQPolynomial stencilVarPoints = this.computeComplexity(this.stencilVar);
      boolean _equals = Objects.equal(this.version, Version.BASELINE);
      if (_equals) {
        return "";
      }
      final Function1<StandardEquation, ISLPWQPolynomial> _function = (StandardEquation it) -> {
        return this.computeComplexity(it.getVariable());
      };
      final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_1 = (ISLPWQPolynomial p1, ISLPWQPolynomial p2) -> {
        return p1.add(p2);
      };
      final ISLPWQPolynomial checksumPoints = IterableExtensions.<ISLPWQPolynomial>reduce(ListExtensions.<StandardEquation, ISLPWQPolynomial>map(this.systemBody.getStandardEquations(), _function), _function_1);
      String _switchResult = null;
      final Version version = this.version;
      if (version != null) {
        switch (version) {
          case ABFT_V1:
            _switchResult = "v1 ";
            break;
          case ABFT_V2:
            _switchResult = "v2 ";
            break;
          case ABFT_V3:
            _switchResult = "v3 ";
            break;
          default:
            _switchResult = "";
            break;
        }
      } else {
        _switchResult = "";
      }
      final String versionStr = _switchResult;
      final String stencilVarCard = ProgramPrinter.printExpr(PolynomialConverter.convert(stencilVarPoints)).toString().replaceAll("([-]*[0-9][0-9]*)", "((double)$1)");
      final String checksumsCard = ProgramPrinter.printExpr(PolynomialConverter.convert(checksumPoints)).toString().replaceAll("([-]*[0-9][0-9]*)", "((double)$1)");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#undef ceild");
      _builder.newLine();
      _builder.append("#undef floord");
      _builder.newLine();
      _builder.append("#define ceild(n,d)  (double)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("#define floord(n,d) (double)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.newLine();
      _builder.append("double ");
      String _name = this.stencilVar.getName();
      _builder.append(_name);
      _builder.append("_card = ");
      _builder.append(stencilVarCard);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("double total_card = ");
      _builder.append(checksumsCard);
      _builder.append(";");
      _builder.newLineIfNotEmpty();
      _builder.append("double expected_overhead = total_card / ");
      String _name_1 = this.stencilVar.getName();
      _builder.append(_name_1);
      _builder.append("_card;");
      _builder.newLineIfNotEmpty();
      _builder.append("printf(\"");
      _builder.append(versionStr);
      _builder.append("(");
      String _name_2 = this.stencilVar.getName();
      _builder.append(_name_2);
      _builder.append(",C,overhead): %0.0lf,%0.0lf,%0.2lf\\n\", ");
      String _name_3 = this.stencilVar.getName();
      _builder.append(_name_3);
      _builder.append("_card, total_card, expected_overhead);");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("#undef ceild");
      _builder.newLine();
      _builder.append("#undef floord");
      _builder.newLine();
      _builder.append("#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("#define floord(n,d) (int)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public ISLPWQPolynomial computeComplexity(final Variable variable) {
    ISLPWQPolynomial _xblockexpression = null;
    {
      final Function1<StandardEquation, Boolean> _function = (StandardEquation e) -> {
        Variable _variable = e.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, variable));
      };
      final StandardEquation eq = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function);
      _xblockexpression = this.computeComplexity(variable, eq.getExpr());
    }
    return _xblockexpression;
  }

  protected ISLPWQPolynomial _computeComplexity(final Variable variable, final ReduceExpression re) {
    return this.computeComplexity(variable, re.getBody());
  }

  protected ISLPWQPolynomial _computeComplexity(final Variable variable, final AlphaExpression ae) {
    return BarvinokBindings.card(ae.getContextDomain());
  }

  public String localMemoryFree() {
    return this.memoryFree(((Variable[])Conversions.unwrapArray(this.system.getLocals(), Variable.class)));
  }

  public String memoryFree(final Variable[] variables) {
    String _xblockexpression = null;
    {
      final Function1<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>> _function = (Map.Entry<String, LinkedList<Variable>> it) -> {
        final Function1<Variable, ISLSet> _function_1 = (Variable it_1) -> {
          return this.memoryMap.getRange(it_1.getName()).copy();
        };
        final Function2<ISLSet, ISLSet, ISLSet> _function_2 = (ISLSet v1, ISLSet v2) -> {
          return v1.union(v2);
        };
        ISLSet _coalesce = IterableExtensions.<ISLSet>reduce(ListExtensions.<Variable, ISLSet>map(it.getValue(), _function_1), _function_2).coalesce();
        String _key = it.getKey();
        return Pair.<ISLSet, String>of(_coalesce, _key);
      };
      final Function1<Pair<ISLSet, String>, String> _function_1 = (Pair<ISLSet, String> it) -> {
        return this.memoryFree(it.getKey(), it.getValue());
      };
      final Iterable<String> freeStmts = IterableExtensions.<Pair<ISLSet, String>, String>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>>map(this.getMemoryChunks(variables), _function), _function_1);
      String _xifexpression = null;
      int _size = IterableExtensions.size(freeStmts);
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// Free local memory allocation");
        _builder.newLine();
        String _join = IterableExtensions.join(freeStmts, "\n");
        _builder.append(_join);
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1.toString();
      }
      final String code = _xifexpression;
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String memoryFree(final ISLSet domainWithParams, final String name) {
    String _xblockexpression = null;
    {
      final int dim = domainWithParams.getIndexNames().size();
      final Function1<ISLBasicSet, ISLSet> _function = (ISLBasicSet it) -> {
        return it.dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, dim).toSet();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet s1, ISLSet s2) -> {
        return s1.union(s2);
      };
      final ISLSet domain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(domainWithParams.getBasicSets(), _function), _function_1);
      final List<String> indexNames = domain.getIndexNames();
      final Function1<Integer, ISLMap> _function_2 = (Integer i) -> {
        final Function1<Integer, Boolean> _function_3 = (Integer j) -> {
          return Boolean.valueOf(Objects.equal(i, j));
        };
        final Function2<ISLMap, Integer, ISLMap> _function_4 = (ISLMap ret, Integer d) -> {
          return ret.projectOut(ISLDimType.isl_dim_out, (d).intValue(), 1);
        };
        return IterableExtensions.<Integer, ISLMap>fold(IterableExtensions.<Integer>reject(new ExclusiveRange(dim, 0, false), _function_3), 
          ISLMap.buildIdentity(domain.copy().toIdentityMap().getSpace()), _function_4);
      };
      final Function1<ISLMap, ISLSet> _function_3 = (ISLMap m) -> {
        return domain.copy().apply(m);
      };
      final Function1<ISLSet, ISLSet> _function_4 = (ISLSet it) -> {
        return it.coalesce();
      };
      final Function1<ISLSet, ParenthesizedExpr> _function_5 = (ISLSet it) -> {
        return WriteC.getCardinalityExpr(it);
      };
      final Iterable<ParenthesizedExpr> dimSizes = IterableExtensions.<ISLSet, ParenthesizedExpr>map(IterableExtensions.<ISLSet, ISLSet>map(IterableExtensions.<ISLMap, ISLSet>map(IterableExtensions.<Integer, ISLMap>map(new ExclusiveRange(0, dim, true), _function_2), _function_3), _function_4), _function_5);
      int _indirectionLevel = this.indirectionLevel(domain);
      int _minus = (_indirectionLevel - 1);
      ArrayList<Pair<Integer, String>> _zipWith = CommonExtensions.<Integer, String>zipWith(new ExclusiveRange(0, _minus, true), indexNames);
      StringConcatenation _builder = new StringConcatenation();
      final Function2<String, Pair<Integer, String>, String> _function_6 = (String ret, Pair<Integer, String> p) -> {
        String _xblockexpression_1 = null;
        {
          final Integer i = p.getKey();
          final String indexName = p.getValue();
          final ParenthesizedExpr expr = ((ParenthesizedExpr[])Conversions.unwrapArray(dimSizes, ParenthesizedExpr.class))[(i).intValue()];
          final Function1<Integer, String> _function_7 = (Integer it) -> {
            return "\t";
          };
          final String indentation = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_7));
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(ret);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append(indentation);
          _builder_1.append("for (int ");
          _builder_1.append(indexName);
          _builder_1.append("=0; ");
          _builder_1.append(indexName);
          _builder_1.append("<");
          CharSequence _printExpr = ProgramPrinter.printExpr(expr);
          _builder_1.append(_printExpr);
          _builder_1.append("; ");
          _builder_1.append(indexName);
          _builder_1.append("++) {");
          _xblockexpression_1 = _builder_1.toString();
        }
        return _xblockexpression_1;
      };
      String code = IterableExtensions.<Pair<Integer, String>, String>fold(_zipWith, _builder.toString(), _function_6);
      {
        int _indirectionLevel_1 = this.indirectionLevel(domain);
        int _minus_1 = (_indirectionLevel_1 - 1);
        final Function1<Integer, String> _function_7 = (Integer it) -> {
          return "\t";
        };
        final String indentation = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _minus_1, true), _function_7));
        String _xifexpression = null;
        int _size = indexNames.size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(name);
          _builder_1.append("[");
          int _indirectionLevel_2 = this.indirectionLevel(domain);
          int _minus_2 = (_indirectionLevel_2 - 1);
          final Function1<Integer, String> _function_8 = (Integer j) -> {
            return indexNames.get((j).intValue());
          };
          String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _minus_2, true), _function_8), "][");
          _builder_1.append(_join);
          _builder_1.append("]");
          _xifexpression = _builder_1.toString();
        } else {
          _xifexpression = name;
        }
        final String writeAcc = _xifexpression;
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(code);
        _builder_2.newLineIfNotEmpty();
        _builder_2.append(indentation);
        _builder_2.append(" free(");
        _builder_2.append(writeAcc);
        _builder_2.append(");");
        _builder_2.newLineIfNotEmpty();
        code = _builder_2.toString();
      }
      int _indirectionLevel_1 = this.indirectionLevel(domain);
      int _minus_1 = (_indirectionLevel_1 - 1);
      final Function2<String, Pair<Integer, String>, String> _function_7 = (String ret, Pair<Integer, String> p) -> {
        String _xblockexpression_1 = null;
        {
          final Integer i = p.getKey();
          String _xifexpression = null;
          if (((i).intValue() == 0)) {
            _xifexpression = name;
          } else {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(name);
            _builder_1.append("[");
            final Function1<Integer, String> _function_8 = (Integer j) -> {
              return indexNames.get((j).intValue());
            };
            String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_8), "][");
            _builder_1.append(_join);
            _builder_1.append("]");
            _xifexpression = _builder_1.toString();
          }
          final String writeAcc = _xifexpression;
          final Function1<Integer, String> _function_9 = (Integer it) -> {
            return "\t";
          };
          final String indentation = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_9));
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(ret);
          _builder_2.newLineIfNotEmpty();
          _builder_2.append(indentation);
          _builder_2.append("}");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append(indentation);
          _builder_2.append("free(");
          _builder_2.append(writeAcc);
          _builder_2.append(");");
          _builder_2.newLineIfNotEmpty();
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      code = IterableExtensions.<Pair<Integer, String>, String>fold(CommonExtensions.<Integer, String>zipWith(new ExclusiveRange(_minus_1, 0, false), indexNames), code, _function_7);
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String localMemoryAllocation() {
    return this.memoryAllocation(((Variable[])Conversions.unwrapArray(this.system.getLocals(), Variable.class)));
  }

  public String memoryAllocation(final Variable[] variables) {
    return this.memoryAllocation(variables, "");
  }

  public String memoryAllocation(final Variable[] variables, final String suffix) {
    String _xblockexpression = null;
    {
      final Function1<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>> _function = (Map.Entry<String, LinkedList<Variable>> it) -> {
        final Function1<Variable, ISLSet> _function_1 = (Variable it_1) -> {
          return this.memoryMap.getRange(it_1.getName()).copy();
        };
        final Function2<ISLSet, ISLSet, ISLSet> _function_2 = (ISLSet v1, ISLSet v2) -> {
          return v1.union(v2);
        };
        ISLSet _coalesce = IterableExtensions.<ISLSet>reduce(ListExtensions.<Variable, ISLSet>map(it.getValue(), _function_1), _function_2).coalesce();
        String _key = it.getKey();
        return Pair.<ISLSet, String>of(_coalesce, _key);
      };
      final Function1<Pair<ISLSet, String>, String> _function_1 = (Pair<ISLSet, String> it) -> {
        String _value = it.getValue();
        String _plus = (_value + suffix);
        return this.mallocStmt(it.getKey(), _plus);
      };
      final Iterable<String> mallocStmts = IterableExtensions.<Pair<ISLSet, String>, String>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>>map(this.getMemoryChunks(variables), _function), _function_1);
      String _xifexpression = null;
      int _size = IterableExtensions.size(mallocStmts);
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// Local memory allocation");
        _builder.newLine();
        String _join = IterableExtensions.join(mallocStmts, "\n");
        _builder.append(_join);
        _builder.newLineIfNotEmpty();
        _xifexpression = _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _xifexpression = _builder_1.toString();
      }
      final String code = _xifexpression;
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  /**
   * Returns the map of memory chunk name and its domain
   */
  public Iterable<Map.Entry<String, LinkedList<Variable>>> getMemoryChunks(final Variable[] variables) {
    Iterable<Map.Entry<String, LinkedList<Variable>>> _xblockexpression = null;
    {
      final HashMap<String, LinkedList<Variable>> chunkVariables = CollectionLiterals.<String, LinkedList<Variable>>newHashMap();
      final Consumer<Variable> _function = (Variable v) -> {
        final String mappedName = this.memoryMap.getName(v.getName());
        LinkedList<Variable> _elvis = null;
        LinkedList<Variable> _get = chunkVariables.get(mappedName);
        if (_get != null) {
          _elvis = _get;
        } else {
          LinkedList<Variable> _newLinkedList = CollectionLiterals.<Variable>newLinkedList();
          _elvis = _newLinkedList;
        }
        final LinkedList<Variable> list = _elvis;
        list.add(v);
        chunkVariables.put(mappedName, list);
      };
      this.system.getVariables().forEach(_function);
      final Function1<Map.Entry<String, LinkedList<Variable>>, Boolean> _function_1 = (Map.Entry<String, LinkedList<Variable>> it) -> {
        final Function1<Variable, Boolean> _function_2 = (Variable v) -> {
          return Boolean.valueOf(ArrayExtensions.contains(variables, v));
        };
        int _size = IterableExtensions.size(IterableExtensions.<Variable>filter(it.getValue(), _function_2));
        return Boolean.valueOf((_size > 0));
      };
      _xblockexpression = IterableExtensions.<Map.Entry<String, LinkedList<Variable>>>filter(chunkVariables.entrySet(), _function_1);
    }
    return _xblockexpression;
  }

  public String mallocStmt(final ISLSet domainWithParams, final String name) {
    String _xblockexpression = null;
    {
      final int dim = domainWithParams.getIndexNames().size();
      final Function1<ISLBasicSet, ISLSet> _function = (ISLBasicSet it) -> {
        return it.dropConstraintsNotInvolvingDims(ISLDimType.isl_dim_out, 0, dim).toSet();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_1 = (ISLSet s1, ISLSet s2) -> {
        return s1.union(s2);
      };
      final ISLSet domain = IterableExtensions.<ISLSet>reduce(ListExtensions.<ISLBasicSet, ISLSet>map(domainWithParams.getBasicSets(), _function), _function_1);
      final List<String> indexNames = domain.getIndexNames();
      final Function1<Integer, ISLMap> _function_2 = (Integer i) -> {
        final Function1<Integer, Boolean> _function_3 = (Integer j) -> {
          return Boolean.valueOf(Objects.equal(i, j));
        };
        final Function2<ISLMap, Integer, ISLMap> _function_4 = (ISLMap ret, Integer d) -> {
          return ret.projectOut(ISLDimType.isl_dim_out, (d).intValue(), 1);
        };
        return IterableExtensions.<Integer, ISLMap>fold(IterableExtensions.<Integer>reject(new ExclusiveRange(dim, 0, false), _function_3), 
          ISLMap.buildIdentity(domain.copy().toIdentityMap().getSpace()), _function_4);
      };
      final Function1<ISLMap, ISLSet> _function_3 = (ISLMap m) -> {
        return domain.copy().apply(m);
      };
      final Function1<ISLSet, ISLSet> _function_4 = (ISLSet it) -> {
        return it.coalesce();
      };
      final Function1<ISLSet, ParenthesizedExpr> _function_5 = (ISLSet it) -> {
        return WriteC.getCardinalityExpr(it);
      };
      final Iterable<ParenthesizedExpr> dimSizes = IterableExtensions.<ISLSet, ParenthesizedExpr>map(IterableExtensions.<ISLSet, ISLSet>map(IterableExtensions.<ISLMap, ISLSet>map(IterableExtensions.<Integer, ISLMap>map(new ExclusiveRange(0, dim, true), _function_2), _function_3), _function_4), _function_5);
      StringConcatenation _builder = new StringConcatenation();
      String _print = ProgramPrinter.print(Factory.dataType(this.dataType, dim));
      _builder.append(_print);
      _builder.append(" ");
      _builder.append(name);
      CharSequence _printExpr = ProgramPrinter.printExpr(Factory.assignmentStmt(_builder.toString(), 
        Factory.callocCall(Factory.dataType(this.dataType, dim), ((Expression[])Conversions.unwrapArray(dimSizes, Expression.class))[0])));
      final String mallocAssignment = (_printExpr + ";\n");
      int _indirectionLevel = this.indirectionLevel(domain);
      int _minus = (_indirectionLevel - 1);
      final Function2<String, Pair<Integer, String>, String> _function_6 = (String ret, Pair<Integer, String> p) -> {
        String _xblockexpression_1 = null;
        {
          final Integer i = p.getKey();
          final String indexName = p.getValue();
          final ParenthesizedExpr expr = ((ParenthesizedExpr[])Conversions.unwrapArray(dimSizes, ParenthesizedExpr.class))[(i).intValue()];
          final DataType dataType = Factory.dataType(this.dataType, ((dim - (i).intValue()) - 1));
          final CastExpr mallocCall = Factory.callocCall(dataType, ((Expression[])Conversions.unwrapArray(dimSizes, Expression.class))[((i).intValue() + 1)]);
          final Function1<Integer, String> _function_7 = (Integer it) -> {
            return "\t";
          };
          final String indentation = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_7));
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(name);
          _builder_1.append("[");
          final Function1<Integer, String> _function_8 = (Integer j) -> {
            return indexNames.get((j).intValue());
          };
          String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, ((i).intValue() + 1), true), _function_8), "][");
          _builder_1.append(_join);
          _builder_1.append("]");
          final String writeAcc = _builder_1.toString();
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append(ret);
          _builder_2.newLineIfNotEmpty();
          _builder_2.append(indentation);
          _builder_2.append("for (int ");
          _builder_2.append(indexName);
          _builder_2.append("=0; ");
          _builder_2.append(indexName);
          _builder_2.append("<");
          CharSequence _printExpr_1 = ProgramPrinter.printExpr(expr);
          _builder_2.append(_printExpr_1);
          _builder_2.append("; ");
          _builder_2.append(indexName);
          _builder_2.append("++) {");
          _builder_2.newLineIfNotEmpty();
          _builder_2.append(indentation);
          _builder_2.append("\t");
          _builder_2.append(writeAcc);
          _builder_2.append(" = ");
          CharSequence _printExpr_2 = ProgramPrinter.printExpr(mallocCall);
          _builder_2.append(_printExpr_2);
          _builder_2.append(";");
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      String code = IterableExtensions.<Pair<Integer, String>, String>fold(CommonExtensions.<Integer, String>zipWith(new ExclusiveRange(0, _minus, true), indexNames), mallocAssignment, _function_6);
      int _indirectionLevel_1 = this.indirectionLevel(domain);
      int _minus_1 = (_indirectionLevel_1 - 1);
      final Function2<String, Pair<Integer, String>, String> _function_7 = (String ret, Pair<Integer, String> p) -> {
        String _xblockexpression_1 = null;
        {
          final Integer i = p.getKey();
          final Function1<Integer, String> _function_8 = (Integer it) -> {
            return "\t";
          };
          final String indentation = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, (i).intValue(), true), _function_8));
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(ret);
          _builder_1.newLineIfNotEmpty();
          _builder_1.append(indentation);
          _builder_1.append("}");
          _builder_1.newLineIfNotEmpty();
          _xblockexpression_1 = _builder_1.toString();
        }
        return _xblockexpression_1;
      };
      code = IterableExtensions.<Pair<Integer, String>, String>fold(CommonExtensions.<Integer, String>zipWith(new ExclusiveRange(_minus_1, 0, false), indexNames), code, _function_7);
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public int indirectionLevel(final Variable variable) {
    return this.indirectionLevel(variable.getDomain());
  }

  public int indirectionLevel(final ISLSet domain) {
    return Integer.max(1, domain.getNbIndices());
  }

  public String undefStmtMacros() {
    final Function1<StandardEquation, String> _function = (StandardEquation it) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#undef S");
      String _name = it.getVariable().getName();
      _builder.append(_name);
      return _builder.toString();
    };
    return IterableExtensions.join(ListExtensions.<StandardEquation, String>map(this.system.getSystemBodies().get(0).getStandardEquations(), _function), "\n");
  }

  protected CharSequence _printStmtExpr(final ReduceExpression re) {
    return ProgramPrinter.printExpr(this.exprConverter.convertExpr(re.getBody()));
  }

  protected CharSequence _printStmtExpr(final AlphaExpression ae) {
    return ProgramPrinter.printExpr(this.exprConverter.convertExpr(ae));
  }

  public String defStmtMacros() {
    String _xblockexpression = null;
    {
      final int TT = this.tileSizes[0];
      int _size = this.stencilVar.getDomain().getIndexNames().size();
      final Function1<Integer, String> _function = (Integer i) -> {
        return this.stencilVar.getDomain().getIndexNames().get((i).intValue());
      };
      final Iterable<String> spatialIndexNames = IterableExtensions.<Integer, String>map(new ExclusiveRange(1, _size, true), _function);
      int _size_1 = ((List<Integer>)Conversions.doWrapArray(this.tileSizes)).size();
      final Function1<Integer, Integer> _function_1 = (Integer i) -> {
        return Integer.valueOf(this.tileSizes[(i).intValue()]);
      };
      final Iterable<Integer> spatialTileSizes = IterableExtensions.<Integer, Integer>map(new ExclusiveRange(1, _size_1, true), _function_1);
      final ArrayList<Pair<String, Integer>> spatialContext = CommonExtensions.<String, Integer>zipWith(spatialIndexNames, spatialTileSizes);
      int _size_2 = this.stencilVar.getDomain().getIndexNames().size();
      final int nbSpatialDims = (_size_2 - 1);
      String _xifexpression = null;
      if ((Objects.equal(this.version, Version.ABFT_V1) || Objects.equal(this.version, Version.ABFT_V3))) {
        _xifexpression = "C2";
      } else {
        final Function1<Integer, Integer> _function_2 = (Integer it) -> {
          return Integer.valueOf(3);
        };
        final Function2<Integer, Integer, Integer> _function_3 = (Integer v1, Integer v2) -> {
          return Integer.valueOf(((v1).intValue() * (v2).intValue()));
        };
        Integer _reduce = IterableExtensions.<Integer>reduce(IterableExtensions.<Integer, Integer>map(new ExclusiveRange(0, nbSpatialDims, true), _function_2), _function_3);
        _xifexpression = ("C2_NR" + _reduce);
      }
      final String injectionName = _xifexpression;
      final EList<StandardEquation> stdEqs = this.system.getSystemBodies().get(0).getStandardEquations();
      Iterable<String> _xblockexpression_1 = null;
      {
        final Function1<StandardEquation, Boolean> _function_4 = (StandardEquation it) -> {
          Variable _variable = it.getVariable();
          return Boolean.valueOf(Objects.equal(_variable, this.stencilVar));
        };
        AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(stdEqs, _function_4).getExpr();
        final EList<AlphaExpression> yCBEs = ((CaseExpression) _expr).getExprs();
        int _size_3 = yCBEs.size();
        final Function1<Integer, String> _function_5 = (Integer i) -> {
          String _xblockexpression_2 = null;
          {
            final AlphaExpression expr = yCBEs.get((i).intValue());
            final List<String> indexNames = expr.getContextDomain().getIndexNames();
            final String indexNamesStr = IterableExtensions.join(indexNames, ",");
            String _name = this.stencilVar.getName();
            String _plus = ("S" + _name);
            String _plus_1 = (_plus + "_cb");
            final String name = (_plus_1 + i);
            Equation _containerEquation = AlphaUtil.getContainerEquation(expr);
            final StandardEquation eq = ((StandardEquation) _containerEquation);
            final CharSequence rhs = this.printStmtExpr(expr);
            StringConcatenation _builder = new StringConcatenation();
            String _name_1 = eq.getVariable().getName();
            _builder.append(_name_1);
            _builder.append("(");
            _builder.append(indexNamesStr);
            _builder.append(")");
            final String lhs = _builder.toString();
            String defIndexNamesStr = indexNamesStr;
            String op = "=";
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(lhs);
            _builder_1.append(" ");
            _builder_1.append(op);
            _builder_1.append(" ");
            _builder_1.append(rhs);
            final String stmtStr = _builder_1.toString();
            String _xifexpression_1 = null;
            if (((i).intValue() == 2)) {
              String _xblockexpression_3 = null;
              {
                String _xifexpression_2 = null;
                boolean _equals = Objects.equal(this.version, Version.ABFT_V1);
                if (_equals) {
                  final Function1<Pair<String, Integer>, String> _function_6 = (Pair<String, Integer> it) -> {
                    StringConcatenation _builder_2 = new StringConcatenation();
                    String _key = it.getKey();
                    _builder_2.append(_key);
                    _builder_2.append("==");
                    Integer _value = it.getValue();
                    _builder_2.append(_value);
                    _builder_2.append("*t");
                    String _key_1 = it.getKey();
                    _builder_2.append(_key_1);
                    _builder_2.append("_INJ+");
                    Integer _value_1 = it.getValue();
                    int _divide = ((_value_1).intValue() / 2);
                    _builder_2.append(_divide);
                    return _builder_2.toString();
                  };
                  _xifexpression_2 = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(spatialContext, _function_6), " && ");
                } else {
                  final Function1<Pair<String, Integer>, String> _function_7 = (Pair<String, Integer> it) -> {
                    StringConcatenation _builder_2 = new StringConcatenation();
                    String _key = it.getKey();
                    _builder_2.append(_key);
                    _builder_2.append("==");
                    Integer _value = it.getValue();
                    int _minus = ((_value).intValue() - (2 * TT));
                    _builder_2.append(_minus);
                    _builder_2.append("*t");
                    String _key_1 = it.getKey();
                    _builder_2.append(_key_1);
                    _builder_2.append("_INJ+");
                    Integer _value_1 = it.getValue();
                    int _divide = ((_value_1).intValue() / 2);
                    _builder_2.append(_divide);
                    return _builder_2.toString();
                  };
                  _xifexpression_2 = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(spatialContext, _function_7), " && ");
                }
                final String injectionSVals = _xifexpression_2;
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("if (t==");
                _builder_2.append(TT);
                _builder_2.append("*(tt_INJ-1)+1 && ");
                _builder_2.append(injectionSVals);
                _builder_2.append(") inject_");
                String _name_2 = this.system.getName();
                _builder_2.append(_name_2);
                _builder_2.append("(&");
                _builder_2.append(lhs);
                _builder_2.append(")");
                final String injExpr = _builder_2.toString();
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("#define ");
                _builder_3.append(name);
                _builder_3.append("_hook(");
                _builder_3.append(defIndexNamesStr);
                _builder_3.append(") ");
                _builder_3.append(stmtStr);
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("#ifdef ERROR_INJECTION");
                _builder_3.newLine();
                _builder_3.append("#define ");
                _builder_3.append(name);
                _builder_3.append("(");
                _builder_3.append(defIndexNamesStr);
                _builder_3.append(") do { ");
                _builder_3.append(name);
                _builder_3.append("_hook(");
                _builder_3.append(defIndexNamesStr);
                _builder_3.append("); ");
                _builder_3.append(injExpr);
                _builder_3.append("; } while(0)");
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("#else");
                _builder_3.newLine();
                _builder_3.append("#define ");
                _builder_3.append(name);
                _builder_3.append("(");
                _builder_3.append(defIndexNamesStr);
                _builder_3.append(") ");
                _builder_3.append(name);
                _builder_3.append("_hook(");
                _builder_3.append(defIndexNamesStr);
                _builder_3.append(")");
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("#endif");
                _xblockexpression_3 = _builder_3.toString();
              }
              _xifexpression_1 = _xblockexpression_3;
            } else {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("#define ");
              _builder_2.append(name);
              _builder_2.append("(");
              _builder_2.append(defIndexNamesStr);
              _builder_2.append(") ");
              _builder_2.append(stmtStr);
              _xifexpression_1 = _builder_2.toString();
            }
            _xblockexpression_2 = _xifexpression_1;
          }
          return _xblockexpression_2;
        };
        _xblockexpression_1 = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size_3, true), _function_5);
      }
      final Iterable<String> stencilVarMacros = _xblockexpression_1;
      Iterable<String> _xifexpression_1 = null;
      boolean _equals = Objects.equal(this.version, Version.ABFT_V2);
      if (_equals) {
        Iterable<String> _xblockexpression_2 = null;
        {
          final Function1<StandardEquation, Boolean> _function_4 = (StandardEquation it) -> {
            String _name = it.getVariable().getName();
            return Boolean.valueOf(Objects.equal(_name, "C2"));
          };
          AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(stdEqs, _function_4).getExpr();
          final EList<AlphaExpression> c2CBEs = ((CaseExpression) _expr).getExprs();
          int _size_3 = c2CBEs.size();
          final Function1<Integer, String> _function_5 = (Integer i) -> {
            String _xblockexpression_3 = null;
            {
              final AlphaExpression expr = c2CBEs.get((i).intValue());
              final List<String> indexNames = expr.getContextDomain().getIndexNames();
              final String indexNamesStr = IterableExtensions.join(indexNames, ",");
              final String name = (("S" + "C2_cb") + i);
              Equation _containerEquation = AlphaUtil.getContainerEquation(expr);
              final StandardEquation eq = ((StandardEquation) _containerEquation);
              final CharSequence rhs = this.printStmtExpr(expr);
              StringConcatenation _builder = new StringConcatenation();
              String _name = eq.getVariable().getName();
              _builder.append(_name);
              _builder.append("(");
              _builder.append(indexNamesStr);
              _builder.append(")");
              final String lhs = _builder.toString();
              String defIndexNamesStr = indexNamesStr;
              String op = "=";
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append(lhs);
              _builder_1.append(" ");
              _builder_1.append(op);
              _builder_1.append(" ");
              _builder_1.append(rhs);
              final String stmtStr = _builder_1.toString();
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("#define ");
              _builder_2.append(name);
              _builder_2.append("(");
              _builder_2.append(defIndexNamesStr);
              _builder_2.append(") ");
              _builder_2.append(stmtStr);
              _xblockexpression_3 = _builder_2.toString();
            }
            return _xblockexpression_3;
          };
          _xblockexpression_2 = IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _size_3, true), _function_5);
        }
        _xifexpression_1 = _xblockexpression_2;
      } else {
        _xifexpression_1 = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList());
      }
      final Iterable<String> c2cbeMacros = _xifexpression_1;
      final Function1<StandardEquation, Boolean> _function_4 = (StandardEquation it) -> {
        Variable _variable = it.getVariable();
        return Boolean.valueOf(Objects.equal(_variable, this.stencilVar));
      };
      final Function1<StandardEquation, Boolean> _function_5 = (StandardEquation it) -> {
        return Boolean.valueOf((Objects.equal(it.getVariable().getName(), "C2") && Objects.equal(this.version, Version.ABFT_V2)));
      };
      final Function1<StandardEquation, String> _function_6 = (StandardEquation it) -> {
        String _xblockexpression_3 = null;
        {
          final List<String> indexNames = it.getExpr().getContextDomain().getIndexNames();
          final String indexNamesStr = IterableExtensions.join(indexNames, ",");
          String _name = it.getVariable().getName();
          final String name = ("S" + _name);
          Equation _containerEquation = AlphaUtil.getContainerEquation(it.getExpr());
          final StandardEquation eq = ((StandardEquation) _containerEquation);
          final CharSequence rhs = this.printStmtExpr(it.getExpr());
          StringConcatenation _builder = new StringConcatenation();
          String _name_1 = eq.getVariable().getName();
          _builder.append(_name_1);
          _builder.append("(");
          _builder.append(indexNamesStr);
          _builder.append(")");
          final String lhs = _builder.toString();
          String defIndexNamesStr = indexNamesStr;
          String op = "=";
          AlphaExpression _expr = eq.getExpr();
          if ((_expr instanceof ReduceExpression)) {
            AlphaExpression _expr_1 = eq.getExpr();
            final ReduceExpression re = ((ReduceExpression) _expr_1);
            final String reduceVarIndexNamesStr = IterableExtensions.join(re.getBody().getContextDomain().getIndexNames(), ",");
            defIndexNamesStr = reduceVarIndexNamesStr;
            op = "+=";
          }
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(lhs);
          _builder_1.append(" ");
          _builder_1.append(op);
          _builder_1.append(" ");
          _builder_1.append(rhs);
          final String stmtStr = _builder_1.toString();
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("#define ");
          _builder_2.append(name);
          _builder_2.append("(");
          _builder_2.append(defIndexNamesStr);
          _builder_2.append(") ");
          _builder_2.append(stmtStr);
          _xblockexpression_3 = _builder_2.toString();
        }
        return _xblockexpression_3;
      };
      final Iterable<String> macros = IterableExtensions.<StandardEquation, String>map(IterableExtensions.<StandardEquation>reject(IterableExtensions.<StandardEquation>reject(stdEqs, _function_4), _function_5), _function_6);
      Iterable<String> _plus = Iterables.<String>concat(macros, c2cbeMacros);
      _xblockexpression = IterableExtensions.join(IterableExtensions.<String>sort(Iterables.<String>concat(_plus, stencilVarMacros)), "\n");
    }
    return _xblockexpression;
  }

  public String prepareResult() {
    String _xblockexpression = null;
    {
      final String name = "I";
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      final Variable variable = IterableExtensions.<Variable>findFirst(this.system.getLocals(), _function);
      if ((variable == null)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("struct Result result = new_result(); ");
        _builder.newLine();
        _builder.append("result.result = execution_time;");
        _builder.newLine();
        return _builder.toString();
      }
      final ISLUnionSet domain = variable.getDomain().setTupleName((this.stmtPrefix + name)).toUnionSet();
      final List<String> indexNames = domain.getSets().get(0).getIndexNames();
      final String idxStr = IterableExtensions.join(indexNames, ",");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(this.stmtPrefix);
      _builder_1.append(name);
      _builder_1.append("[");
      _builder_1.append(idxStr);
      _builder_1.append("]");
      final String SVar = _builder_1.toString();
      String _join = IterableExtensions.join(domain.copy().params().getParamNames(), ",");
      String _plus = ("[" + _join);
      final String paramStr = (_plus + "]");
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
      final Function1<String, String> _function_1 = (String i) -> {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("{ ");
        _builder_3.append(SVar);
        _builder_3.append("->[");
        _builder_3.append(i);
        _builder_3.append("] }");
        return _builder_3.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_1), ",");
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
      String _switchResult = null;
      final Version version = this.version;
      if (version != null) {
        switch (version) {
          case ABFT_V1:
            _switchResult = "v1";
            break;
          case ABFT_V2:
            _switchResult = "v2";
            break;
          case ABFT_V3:
            _switchResult = "v3";
            break;
          default:
            break;
        }
      }
      final String vStr = _switchResult;
      final Function1<String, String> _function_2 = (String n) -> {
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append(n);
        _builder_4.append("==");
        _builder_4.append(n);
        _builder_4.append("_INJ");
        return _builder_4.toString();
      };
      final String containsInjectionConditions = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_2), " && ");
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("#if defined ");
      _builder_4.append(SystemCodeGen.TIMING);
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("struct Result result = new_result();");
      _builder_4.newLine();
      _builder_4.append("result.result = execution_time;");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("#elif defined ");
      _builder_4.append(SystemCodeGen.ERROR_INJECTION);
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("// Count checksum difference above THRESHOLD");
      _builder_4.newLine();
      _builder_4.append("struct INJ inj = { ");
      final Function1<String, String> _function_3 = (String i) -> {
        StringConcatenation _builder_5 = new StringConcatenation();
        _builder_5.append("t");
        _builder_5.append(i);
        _builder_5.append("_INJ");
        return _builder_5.toString();
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_3), ", ");
      _builder_4.append(_join_2);
      _builder_4.append(" };");
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("struct Result result = new_result();");
      _builder_4.newLine();
      _builder_4.append("result.bit = BIT;");
      _builder_4.newLine();
      _builder_4.append("result.inj = inj;");
      _builder_4.newLine();
      _builder_4.append("result.noise = -1;");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("const char* verbose = getenv(\"VERBOSE\");");
      _builder_4.newLine();
      _builder_4.newLine();
      _builder_4.append("#define print_");
      _builder_4.append(this.stmtPrefix);
      _builder_4.append(name);
      _builder_4.append("(");
      _builder_4.append(idxStr);
      _builder_4.append(") printf(\"");
      _builder_4.append(vStr);
      _builder_4.append("_");
      _builder_4.append(name);
      _builder_4.append("(");
      final Function1<String, String> _function_4 = (String it) -> {
        return "%d";
      };
      String _join_3 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_4), ",");
      _builder_4.append(_join_3);
      _builder_4.append(") = %E\\n\",");
      _builder_4.append(idxStr);
      _builder_4.append(", fabs(");
      _builder_4.append(varAcc);
      _builder_4.append("))");
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("#define acc_noise(");
      _builder_4.append(idxStr);
      _builder_4.append(") result.noise = max(result.noise, fabs(");
      _builder_4.append(varAcc);
      _builder_4.append("))");
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("#define ");
      _builder_4.append(this.stmtPrefix);
      _builder_4.append(name);
      _builder_4.append("(");
      _builder_4.append(idxStr);
      _builder_4.append(") do { if (verbose != NULL && fabs(");
      _builder_4.append(varAcc);
      _builder_4.append(")>=threshold) print_");
      _builder_4.append(this.stmtPrefix);
      _builder_4.append(name);
      _builder_4.append("(");
      _builder_4.append(idxStr);
      _builder_4.append("); acc_noise(");
      _builder_4.append(idxStr);
      _builder_4.append("); if (");
      _builder_4.append(containsInjectionConditions);
      _builder_4.append(") { if (fabs(");
      _builder_4.append(varAcc);
      _builder_4.append(")>=threshold) {result.TP++;} else {result.FN++;} } else { if (fabs(");
      _builder_4.append(varAcc);
      _builder_4.append(")>=threshold) {result.FP++;} else {result.TN++;} } } while(0)");
      _builder_4.newLineIfNotEmpty();
      _builder_4.newLine();
      CharSequence _print = ProgramPrinter.print(this.dataType);
      _builder_4.append(_print);
      _builder_4.append(" threshold = 0;");
      _builder_4.newLineIfNotEmpty();
      _builder_4.append("const char* env_threshold = getenv(\"THRESHOLD\");");
      _builder_4.newLine();
      _builder_4.append("if (env_threshold != NULL) {");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("threshold = atof(env_threshold);");
      _builder_4.newLine();
      _builder_4.append("}");
      _builder_4.newLine();
      _builder_4.newLine();
      String _code = codegenVisitor.toCode();
      _builder_4.append(_code);
      _builder_4.newLineIfNotEmpty();
      _builder_4.newLine();
      _builder_4.append("{");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("long N = result.FP + result.TN;");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("long P = result.FN + result.TP;");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("if (P != 0 && N != 0) {");
      _builder_4.newLine();
      _builder_4.append("\t\t");
      _builder_4.append("result.TPR = 100 * ((float)result.TP) / P;");
      _builder_4.newLine();
      _builder_4.append("\t\t");
      _builder_4.append("result.FPR = 100 * ((float)result.FP) / N;");
      _builder_4.newLine();
      _builder_4.append("\t\t");
      _builder_4.append("result.FNR = 100 * ((float)result.FN) / P;");
      _builder_4.newLine();
      _builder_4.append("\t");
      _builder_4.append("}");
      _builder_4.newLine();
      _builder_4.append("}");
      _builder_4.newLine();
      _builder_4.append("#undef ");
      _builder_4.append(this.stmtPrefix);
      _builder_4.append(name);
      _builder_4.newLineIfNotEmpty();
      _builder_4.newLine();
      _builder_4.append("#endif");
      _builder_4.newLine();
      final String code = _builder_4.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public CharSequence checkCoordinateMacro(final ISLSet container) {
    CharSequence _xblockexpression = null;
    {
      final List<String> names = this.stencilVar.getDomain().getIndexNames();
      final Function1<String, String> _function = (String n) -> {
        return ("t" + n);
      };
      final List<String> txs = ListExtensions.<String, String>map(names, _function);
      final Function1<String, String> _function_1 = (String n) -> {
        return (n + "_INJ");
      };
      final List<String> xInjs = ListExtensions.<String, String>map(names, _function_1);
      final String txsStr = IterableExtensions.join(txs, ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#define containsInjectionCoordinate(");
      _builder.append(txsStr);
      _builder.append(") 0");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  /**
   * This operation becomes very expensive as the dimensionality and size of TT increase
   */
  public CharSequence checkCoordinateMacro_old(final ISLSet container) {
    CharSequence _xblockexpression = null;
    {
      final List<String> names = this.stencilVar.getDomain().getIndexNames();
      final Function1<String, String> _function = (String n) -> {
        return ("t" + n);
      };
      final List<String> txs = ListExtensions.<String, String>map(names, _function);
      final Function1<String, String> _function_1 = (String n) -> {
        return (n + "_INJ");
      };
      final List<String> xInjs = ListExtensions.<String, String>map(names, _function_1);
      final String txsStr = IterableExtensions.join(txs, ",");
      final String xInjsStr = IterableExtensions.join(xInjs, ",");
      final String paramStr = IterableExtensions.join(container.getParamNames(), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      _builder.append(paramStr);
      _builder.append(",");
      _builder.append(txsStr);
      _builder.append(",");
      _builder.append(xInjsStr);
      _builder.append("]->{[");
      _builder.append(txsStr);
      _builder.append(",");
      _builder.append(xInjsStr);
      _builder.append("]}");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("[");
      _builder_1.append(paramStr);
      _builder_1.append(",");
      _builder_1.append(txsStr);
      _builder_1.append(",");
      _builder_1.append(xInjsStr);
      _builder_1.append("]->{[");
      _builder_1.append(txsStr);
      _builder_1.append(",");
      _builder_1.append(xInjsStr);
      _builder_1.append("]->[");
      _builder_1.append(txsStr);
      _builder_1.append("]}");
      ISLSet coords = container.copy().intersect(ISLUtil.toISLSet(_builder.toString())).removeRedundancies().coalesce().apply(ISLUtil.toISLMap(_builder_1.toString())).projectOut(ISLDimType.isl_dim_param, 0, container.dim(ISLDimType.isl_dim_param));
      coords = AlphaUtil.renameIndices(coords, IterableExtensions.<String>toList(txs));
      final ParenthesizedExpr expr = PolynomialConverter.convert(BarvinokBindings.card(coords.copy()));
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("#define containsInjectionCoordinate(");
      _builder_2.append(txsStr);
      _builder_2.append(") ");
      CharSequence _printExpr = ProgramPrinter.printExpr(expr);
      _builder_2.append(_printExpr);
      _xblockexpression = _builder_2;
    }
    return _xblockexpression;
  }

  public String stmtLoops() {
    String _xblockexpression = null;
    {
      final ISLASTBuild build = ISLASTBuild.buildFromContext(this.scheduleDomain.copy().params());
      final ISLASTNode node = build.generate(this.schedule.copy());
      final String[] scheduleLines = this.schedule.getRoot().toString().split("\n");
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/*");
      _builder.newLine();
      {
        int _size = ((List<String>)Conversions.doWrapArray(scheduleLines)).size();
        ExclusiveRange _doubleDotLessThan = new ExclusiveRange(1, _size, true);
        for(final Integer i : _doubleDotLessThan) {
          _builder.append(" ");
          _builder.append("* ");
          String _get = scheduleLines[(i).intValue()];
          _builder.append(_get, " ");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append(" ");
      _builder.append("*/");
      _builder.newLine();
      String _code = codegenVisitor.toCode();
      _builder.append(_code);
      _builder.newLineIfNotEmpty();
      final String code = _builder.toString();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public CharSequence sigantureParamsAsFloats(final AlphaSystem system) {
    CharSequence _sigantureParamsAsFloats = null;
    if (system!=null) {
      _sigantureParamsAsFloats=this.sigantureParamsAsFloats(system, this.version);
    }
    return _sigantureParamsAsFloats;
  }

  public CharSequence sigantureParamsAsFloats(final AlphaSystem system, final Version _version) {
    CharSequence _xblockexpression = null;
    {
      final Function1<String, String> _function = (String p) -> {
        return ("double " + p);
      };
      final String paramArgs = IterableExtensions.join(ListExtensions.<String, String>map(system.getParameterDomain().getParamNames(), _function), ", ");
      EList<Variable> _inputs = system.getInputs();
      EList<Variable> _outputs = system.getOutputs();
      final Function1<Variable, String> _function_1 = (Variable v) -> {
        int _indirectionLevel = this.indirectionLevel(v);
        final Function1<Integer, String> _function_2 = (Integer it) -> {
          return "*";
        };
        String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _indirectionLevel, true), _function_2));
        String _plus = ("float " + _join);
        String _name = v.getName();
        return (_plus + _name);
      };
      final String ioArgs = IterableExtensions.join(IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, _outputs), _function_1), ", ");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("void ");
      String _name = system.getName();
      _builder.append(_name);
      _builder.append("(");
      _builder.append(paramArgs);
      _builder.append(", ");
      _builder.append(ioArgs);
      _builder.append(")");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public CharSequence signature(final AlphaSystem system) {
    CharSequence _signature = null;
    if (system!=null) {
      _signature=this.signature(system, this.version);
    }
    return _signature;
  }

  public CharSequence signature(final AlphaSystem system, final Version _version) {
    CharSequence _xblockexpression = null;
    {
      final Function1<String, String> _function = (String p) -> {
        return ("long " + p);
      };
      final String paramArgs = IterableExtensions.join(ListExtensions.<String, String>map(system.getParameterDomain().getParamNames(), _function), ", ");
      EList<Variable> _inputs = system.getInputs();
      EList<Variable> _outputs = system.getOutputs();
      final Function1<Variable, String> _function_1 = (Variable v) -> {
        int _indirectionLevel = this.indirectionLevel(v);
        final Function1<Integer, String> _function_2 = (Integer it) -> {
          return "*";
        };
        String _join = IterableExtensions.join(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, _indirectionLevel, true), _function_2));
        String _plus = ("float " + _join);
        String _name = v.getName();
        return (_plus + _name);
      };
      final String ioArgs = IterableExtensions.join(IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, _outputs), _function_1), ", ");
      final String returnType = "struct Result";
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(returnType);
      _builder.append(" ");
      String _name = system.getName();
      _builder.append(_name);
      _builder.append("(");
      _builder.append(paramArgs);
      _builder.append(", ");
      _builder.append(ioArgs);
      _builder.append(")");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }

  public CharSequence memoryMacro(final Variable variable) {
    return this.memoryMacro(variable, "");
  }

  public CharSequence memoryMacro(final Variable variable, final String suffix) {
    try {
      CharSequence _xblockexpression = null;
      {
        String _name = variable.getName();
        final String stmtName = (_name + suffix);
        String _name_1 = this.memoryMap.getName(variable.getName());
        String _plus = ("mem_" + _name_1);
        final String mappedName = (_plus + suffix);
        final ISLPWMultiAff pwmaff = this.memoryMap.getMap(variable).toPWMultiAff();
        int _nbPieces = pwmaff.getNbPieces();
        boolean _notEquals = (_nbPieces != 1);
        if (_notEquals) {
          throw new Exception("Error constructing memory macro, multiple pieces in map.");
        }
        final ISLMultiAff maff = pwmaff.getPiece(0).getMaff();
        final String varAcc = IterableExtensions.join(variable.getDomain().getIndexNames(), ",");
        final Function1<CustomExpr, CharSequence> _function = (CustomExpr it) -> {
          return ProgramPrinter.printExpr(it);
        };
        final String memAcc = IterableExtensions.join(ListExtensions.<CustomExpr, CharSequence>map(AffineConverter.convertMultiAff(maff), _function), ",");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("#define ");
        _builder.append(stmtName);
        _builder.append("(");
        _builder.append(varAcc);
        _builder.append(") ");
        _builder.append(mappedName);
        _builder.append("(");
        _builder.append(memAcc);
        _builder.append(")");
        _xblockexpression = _builder;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String memoryTargetMacro(final Pair<String, ISLSet> entry) {
    final String name = entry.getKey();
    final ISLSet range = entry.getValue();
    final String stmtName = ("mem_" + name);
    final List<String> indexNames = range.getIndexNames();
    final String paramStr = IterableExtensions.join(range.getParamNames(), ",");
    final String idxStr = IterableExtensions.join(indexNames, ",");
    final Function1<String, ISLSet> _function = (String i) -> {
      ISLSet _xblockexpression = null;
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("[");
        _builder.append(paramStr);
        _builder.append("]->{[");
        _builder.append(idxStr);
        _builder.append("]->[");
        _builder.append(i);
        _builder.append("]}");
        final ISLMap m = ISLUtil.toISLMap(_builder.toString());
        _xblockexpression = range.copy().apply(m);
      }
      return _xblockexpression;
    };
    final Function1<ISLSet, ISLSet> _function_1 = (ISLSet it) -> {
      return it.coalesce();
    };
    final Function1<ISLSet, ISLPWMultiAff> _function_2 = (ISLSet it) -> {
      return it.lexMinAsPWMultiAff();
    };
    final Function1<ISLPWMultiAff, ISLPWMultiAff> _function_3 = (ISLPWMultiAff pwmaff) -> {
      try {
        ISLPWMultiAff _xblockexpression = null;
        {
          int _nbPieces = pwmaff.getNbPieces();
          boolean _greaterThan = (_nbPieces > 1);
          if (_greaterThan) {
            throw new Exception("Error computing lexMin for memory allocation (pwmaff)");
          }
          _xblockexpression = pwmaff;
        }
        return _xblockexpression;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    final Function1<ISLPWMultiAff, ISLMultiAff> _function_4 = (ISLPWMultiAff it) -> {
      return it.getPiece(0).getMaff();
    };
    final Function1<ISLMultiAff, ISLMultiAff> _function_5 = (ISLMultiAff maff) -> {
      try {
        ISLMultiAff _xblockexpression = null;
        {
          int _dim = maff.dim(ISLDimType.isl_dim_out);
          boolean _greaterThan = (_dim > 1);
          if (_greaterThan) {
            throw new Exception("Error computing lexMin for memory allocation (maff)");
          }
          _xblockexpression = maff;
        }
        return _xblockexpression;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    final Function1<ISLMultiAff, ISLAff> _function_6 = (ISLMultiAff it) -> {
      return it.getAff(0);
    };
    final Function1<ISLAff, CustomExpr> _function_7 = (ISLAff it) -> {
      return AffineConverter.convertAff(it, true);
    };
    final Function1<CustomExpr, CharSequence> _function_8 = (CustomExpr it) -> {
      return ProgramPrinter.printExpr(it);
    };
    final ArrayList<CharSequence> offsets = CommonExtensions.<CharSequence>toArrayList(ListExtensions.<CustomExpr, CharSequence>map(ListExtensions.<ISLAff, CustomExpr>map(ListExtensions.<ISLMultiAff, ISLAff>map(ListExtensions.<ISLMultiAff, ISLMultiAff>map(ListExtensions.<ISLPWMultiAff, ISLMultiAff>map(ListExtensions.<ISLPWMultiAff, ISLPWMultiAff>map(ListExtensions.<ISLSet, ISLPWMultiAff>map(ListExtensions.<ISLSet, ISLSet>map(ListExtensions.<String, ISLSet>map(indexNames, _function), _function_1), _function_2), _function_3), _function_4), _function_5), _function_6), _function_7), _function_8));
    final Function1<Pair<String, CharSequence>, String> _function_9 = (Pair<String, CharSequence> it) -> {
      String _xblockexpression = null;
      {
        final String idx = it.getKey();
        final CharSequence offset = it.getValue();
        String _xifexpression = null;
        boolean _notEquals = (!Objects.equal(offset, "(0)"));
        if (_notEquals) {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          _builder.append(idx);
          _builder.append(")-");
          _builder.append(offset);
          _xifexpression = _builder.toString();
        } else {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(idx);
          _xifexpression = _builder_1.toString();
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    };
    final ArrayList<String> indexExprs = CommonExtensions.<String>toArrayList(ListExtensions.<Pair<String, CharSequence>, String>map(CommonExtensions.<String, CharSequence>zipWith(indexNames, offsets), _function_9));
    final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(name, ((String[])Conversions.unwrapArray(indexExprs, String.class)));
    final MacroStmt macroStmt = Factory.macroStmt(stmtName, ((String[])Conversions.unwrapArray(range.getIndexNames(), String.class)), macroReplacement);
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _printStmt = ProgramPrinter.printStmt(macroStmt);
    _builder.append(_printStmt);
    return _builder.toString();
  }

  /**
   * Gets the tuple with the name 'name' and its indices
   */
  public static String stmt(final ISLUnionSet uset, final String name) {
    String _xblockexpression = null;
    {
      final Function1<ISLSet, Boolean> _function = (ISLSet s) -> {
        String _tupleName = s.getTupleName();
        return Boolean.valueOf(Objects.equal(_tupleName, name));
      };
      final ISLSet set = IterableExtensions.<ISLSet>findFirst(uset.getSets(), _function);
      String _tupleName = set.getTupleName();
      String _plus = (_tupleName + "[");
      String _join = IterableExtensions.join(set.getIndexNames(), ",");
      String _plus_1 = (_plus + _join);
      _xblockexpression = (_plus_1 + "]");
    }
    return _xblockexpression;
  }

  /**
   * Replaces the variable strings in the schedule with their statement names
   */
  public static String injectIndices(final CharSequence schedule, final ISLUnionSet domain) {
    return SystemCodeGen.injectIndices(schedule, domain, "S");
  }

  public static String injectIndices(final CharSequence schedule, final ISLUnionSet domain, final String stmtPrefix) {
    String _xblockexpression = null;
    {
      final Function1<ISLSet, ISLUnionSet> _function = (ISLSet it) -> {
        String _tupleName = it.getTupleName();
        String _plus = (stmtPrefix + _tupleName);
        return it.setTupleName(_plus).toUnionSet();
      };
      final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_1 = (ISLUnionSet d1, ISLUnionSet d2) -> {
        return d1.union(d2);
      };
      final String domStr = IterableExtensions.<ISLUnionSet>reduce(ListExtensions.<ISLSet, ISLUnionSet>map(domain.getSets(), _function), _function_1).toString();
      final Function1<ISLSet, String> _function_2 = (ISLSet it) -> {
        return it.getTupleName();
      };
      final Function2<String, String, String> _function_3 = (String ret, String n) -> {
        String _stmt = SystemCodeGen.stmt(domain, n);
        String _plus = (stmtPrefix + _stmt);
        return ret.replace((n + "\'"), _plus);
      };
      _xblockexpression = IterableExtensions.<String, String>fold(ListExtensions.<ISLSet, String>map(domain.getSets(), _function_2), schedule.toString(), _function_3).replace("domain\'", domStr).replace("params\'", ABFT.buildParamStr(domain.getSpace()));
    }
    return _xblockexpression;
  }

  /**
   * Returns the context domain of the expression or the body of the reduce expression
   */
  protected static ISLSet _getStmtDomain(final Variable variable, final ReduceExpression re) {
    return re.getBody().getContextDomain().copy();
  }

  protected static ISLSet _getStmtDomain(final Variable variable, final AlphaExpression ae) {
    return ae.getContextDomain().copy();
  }

  public ISLPWQPolynomial computeComplexity(final Variable variable, final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _computeComplexity(variable, (ReduceExpression)re);
    } else if (re != null) {
      return _computeComplexity(variable, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable, re).toString());
    }
  }

  public CharSequence printStmtExpr(final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _printStmtExpr((ReduceExpression)re);
    } else if (re != null) {
      return _printStmtExpr(re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(re).toString());
    }
  }

  public static ISLSet getStmtDomain(final Variable variable, final AlphaExpression re) {
    if (re instanceof ReduceExpression) {
      return _getStmtDomain(variable, (ReduceExpression)re);
    } else if (re != null) {
      return _getStmtDomain(variable, re);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable, re).toString());
    }
  }

  @Pure
  public AlphaSystem getSystem() {
    return this.system;
  }

  @Pure
  public SystemBody getSystemBody() {
    return this.systemBody;
  }

  @Pure
  public ISLSchedule getSchedule() {
    return this.schedule;
  }

  @Pure
  public String getScheduleStr() {
    return this.scheduleStr;
  }

  @Pure
  public ISLUnionSet getScheduleDomain() {
    return this.scheduleDomain;
  }

  @Pure
  public ExprConverter getExprConverter() {
    return this.exprConverter;
  }

  @Pure
  public MemoryMap getMemoryMap() {
    return this.memoryMap;
  }

  @Pure
  public String getStmtPrefix() {
    return this.stmtPrefix;
  }

  @Pure
  public Variable getStencilVar() {
    return this.stencilVar;
  }

  @Pure
  public Version getVersion() {
    return this.version;
  }

  @Pure
  public int[] getTileSizes() {
    return this.tileSizes;
  }

  @Pure
  public BaseDataType getDataType() {
    return this.dataType;
  }
}
