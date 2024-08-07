package alpha.abft.codegen;

import alpha.abft.ABFT;
import alpha.abft.codegen.util.ISLASTNodeVisitor;
import alpha.abft.codegen.util.MemoryMap;
import alpha.codegen.ArrayAccessExpr;
import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.CastExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Factory;
import alpha.codegen.MacroStmt;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.ExprConverter;
import alpha.codegen.demandDriven.WriteC;
import alpha.codegen.demandDriven.WriteCTypeGenerator;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.MemoryUtils;
import alpha.codegen.isl.PolynomialConverter;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
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
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.JNIPtrBoolean;
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
      final Function1<StandardEquation, Pair<String, ISLSet>> _function = (StandardEquation it) -> {
        String _name = it.getVariable().getName();
        ISLSet _stmtDomain = SystemCodeGen.getStmtDomain(it.getVariable(), it.getExpr());
        return Pair.<String, ISLSet>of(_name, _stmtDomain);
      };
      final Function1<Pair<String, ISLSet>, ISLUnionSet> _function_1 = (Pair<String, ISLSet> it) -> {
        return it.getValue().setTupleName(it.getKey()).copy().toUnionSet();
      };
      final Function2<ISLUnionSet, ISLUnionSet, ISLUnionSet> _function_2 = (ISLUnionSet ret, ISLUnionSet d) -> {
        return ret.union(d);
      };
      this.scheduleDomain = IterableExtensions.<ISLUnionSet, ISLUnionSet>fold(ListExtensions.<Pair<String, ISLSet>, ISLUnionSet>map(ListExtensions.<StandardEquation, Pair<String, ISLSet>>map(this.systemBody.getStandardEquations(), _function), _function_1), ISLUtil.toEmptyUnionSet(this.systemBody.getParameterDomain().getSpace()), _function_2);
      this.scheduleStr = SystemCodeGen.injectIndices(schedule, this.scheduleDomain, this.stmtPrefix);
      this.stencilVar = system.getOutputs().get(0);
      this.version = version;
      this.tileSizes = tileSizes;
      this.schedule = ISLUtil.toISLSchedule(this.scheduleStr);
      StandardizeNames.apply(system);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("\t\t");
      String _aboutComments = this.aboutComments();
      _builder.append(_aboutComments, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("#include<stdio.h>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#include<stdlib.h>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#include<math.h>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#include<time.h>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#define max(x, y)   ((x)>(y) ? (x) : (y))");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#define min(x, y)   ((x)>(y) ? (y) : (x))");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#define floord(n,d) (int)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("#define mallocCheck(v,s,d) if ((v) == NULL) { printf(\"Failed to allocate memory for %s : size=%lu\\n\", \"sizeof(d)*(s)\", sizeof(d)*(s)); exit(-1); }");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("void initialize_timer();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("void reset_timer();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("void start_timer();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("void stop_timer();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("double elapsed_time();");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("struct INJ {");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int t;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int i;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int j;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int k;");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("};");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("struct Result {");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int valid;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("long TP;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("long FP;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("long TN;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("long FN;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("float TPR;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("float FPR;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("float FNR;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("int bit;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("struct INJ inj;");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("};");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("// Memory mapped targets");
      _builder.newLine();
      _builder.append("\t\t");
      final Function1<Pair<String, ISLSet>, String> _function = (Pair<String, ISLSet> it) -> {
        return this.memoryTargetMacro(it);
      };
      String _join = IterableExtensions.join(ListExtensions.<Pair<String, ISLSet>, String>map(this.memoryMap.uniqueTargets(), _function), "\n");
      _builder.append(_join, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("// Memory access functions");
      _builder.newLine();
      _builder.append("\t\t");
      final Function1<Variable, CharSequence> _function_1 = (Variable it) -> {
        return this.memoryMacro(it);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(this.system.getVariables(), _function_1), "\n");
      _builder.append(_join_1, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.newLine();
      {
        if ((Objects.equal(this.version, Version.ABFT_V1) || Objects.equal(this.version, Version.ABFT_V2))) {
          _builder.append("\t\t");
          _builder.append("#ifdef ERROR_INJECTION");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("// Error injection harness");
          _builder.newLine();
          _builder.append("\t\t");
          final Function1<String, String> _function_2 = (String i) -> {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("int ");
            _builder_1.append(i);
            _builder_1.append("_INJ");
            return _builder_1.toString();
          };
          String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_2), ";\n");
          _builder.append(_join_2, "\t\t");
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("int BIT;");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("void inject_");
          String _name = this.system.getName();
          _builder.append(_name, "\t\t");
          _builder.append("(float *val) {");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("int *bits;");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("bits = (int*)val;");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("*bits ^= 1 << BIT;");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("}");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("#endif");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.newLine();
        }
      }
      _builder.append("\t\t");
      CharSequence _signature = this.signature(this.system);
      _builder.append(_signature, "\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t");
      _builder.append("{");
      _builder.newLine();
      {
        if ((Objects.equal(this.version, Version.ABFT_V1) || Objects.equal(this.version, Version.ABFT_V2))) {
          _builder.append("#ifdef ERROR_INJECTION");
          _builder.newLine();
          _builder.append("// Error injection configuration");
          _builder.newLine();
          final Function1<String, String> _function_3 = (String i) -> {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append(i);
            _builder_1.append("_INJ = getenv(\"");
            _builder_1.append(i);
            _builder_1.append("_INJ\") != NULL ? atoi(getenv(\"");
            _builder_1.append(i);
            _builder_1.append("_INJ\")) : (int)(rand() % ");
            String _xifexpression = null;
            boolean _equals = Objects.equal(i, "t");
            if (_equals) {
              _xifexpression = "T";
            } else {
              _xifexpression = "N";
            }
            _builder_1.append(_xifexpression);
            _builder_1.append(")");
            return _builder_1.toString();
          };
          String _join_3 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_3), ";\n");
          _builder.append(_join_3);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
          _builder.append("BIT = getenv(\"BIT\") != NULL ? atoi(getenv(\"BIT\")) : (int)(rand() % ");
          int _xifexpression = (int) 0;
          boolean _equals = Objects.equal(this.dataType, BaseDataType.FLOAT);
          if (_equals) {
            _xifexpression = 32;
          } else {
            _xifexpression = 64;
          }
          _builder.append(_xifexpression);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append("#endif");
          _builder.newLine();
        }
      }
      _builder.append("\t\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder.append(_localMemoryAllocation, "\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t\t\t");
      String _defStmtMacros = this.defStmtMacros();
      _builder.append(_defStmtMacros, "\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("// Timers");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("double execution_time;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("initialize_timer();");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("start_timer();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      String _stmtLoops = this.stmtLoops();
      _builder.append(_stmtLoops, "\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("stop_timer();");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("execution_time = elapsed_time();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      String _undefStmtMacros = this.undefStmtMacros();
      _builder.append(_undefStmtMacros, "\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("#ifdef ERROR_INJECTION");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("struct INJ inj = { ");
      final Function1<String, String> _function_4 = (String i) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(i);
        _builder_1.append("_INJ");
        return _builder_1.toString();
      };
      String _join_4 = IterableExtensions.join(ListExtensions.<String, String>map(this.stencilVar.getDomain().getIndexNames(), _function_4), ", ");
      _builder.append(_join_4, "\t\t\t");
      _builder.append(" };");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("struct Result result = { 0, 0, 0, 0, 0, -1.0, -1.0, -1.0, BIT, inj };");
      _builder.newLine();
      _builder.append("\t\t\t");
      String _countErrors = this.countErrors();
      _builder.append(_countErrors, "\t\t\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("#else");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("struct Result result;");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("printf(\"");
      String _name_1 = this.system.getName();
      _builder.append(_name_1, "\t\t\t");
      _builder.append(": %lf sec\\n\", execution_time);");
      _builder.newLineIfNotEmpty();
      _builder.append("\t\t\t");
      _builder.append("#endif");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      String _localMemoryFree = this.localMemoryFree();
      _builder.append(_localMemoryFree, "\t\t\t");
      _builder.newLineIfNotEmpty();
      {
        boolean _notEquals = (!Objects.equal(this.version, Version.BASELINE));
        if (_notEquals) {
          _builder.append("\t\t\t");
          _builder.append("return result;");
          _builder.newLine();
        }
      }
      _builder.append("\t\t");
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

  public String localMemoryFree() {
    return this.memoryFree(((Variable[])Conversions.unwrapArray(this.system.getLocals(), Variable.class)));
  }

  public String memoryFree(final Variable[] variables) {
    final Function1<Map.Entry<String, LinkedList<Variable>>, String> _function = (Map.Entry<String, LinkedList<Variable>> it) -> {
      return it.getKey();
    };
    final Function1<String, String> _function_1 = (String chunkName) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("free(");
      _builder.append(chunkName);
      _builder.append(");");
      return _builder.toString();
    };
    return IterableExtensions.join(IterableExtensions.<String, String>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>, String>map(this.getMemoryChunks(variables), _function), _function_1), "\n");
  }

  public String localMemoryAllocation() {
    return this.memoryAllocation(((Variable[])Conversions.unwrapArray(this.system.getLocals(), Variable.class)));
  }

  public String memoryAllocation(final Variable[] variables) {
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
      final Function1<Pair<ISLSet, String>, AssignmentStmt> _function_1 = (Pair<ISLSet, String> it) -> {
        String _value = it.getValue();
        String _plus = ("float *" + _value);
        return this.mallocStmt(it.getKey(), _plus);
      };
      final Iterable<AssignmentStmt> mallocStmts = IterableExtensions.<Pair<ISLSet, String>, AssignmentStmt>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>>map(this.getMemoryChunks(variables), _function), _function_1);
      String _xifexpression = null;
      int _size = IterableExtensions.size(mallocStmts);
      boolean _greaterThan = (_size > 0);
      if (_greaterThan) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// Local memory allocation");
        _builder.newLine();
        final Function1<AssignmentStmt, CharSequence> _function_2 = (AssignmentStmt it) -> {
          return ProgramPrinter.printStmt(it);
        };
        String _join = IterableExtensions.join(IterableExtensions.<AssignmentStmt, CharSequence>map(mallocStmts, _function_2));
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

  public AssignmentStmt mallocStmt(final ISLSet domain, final String name) {
    AssignmentStmt _xblockexpression = null;
    {
      final ParenthesizedExpr cardinalityExpr = WriteC.getCardinalityExpr(domain);
      final DataType dataType = Factory.dataType(BaseDataType.FLOAT, 1);
      final CastExpr mallocCall = Factory.callocCall(dataType, cardinalityExpr);
      final AssignmentStmt mallocAssignment = Factory.assignmentStmt(name, mallocCall);
      _xblockexpression = mallocAssignment;
    }
    return _xblockexpression;
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
      final Function1<StandardEquation, String> _function = (StandardEquation it) -> {
        String _xblockexpression_1 = null;
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
          String _xifexpression = null;
          Variable _variable = it.getVariable();
          boolean _equals = Objects.equal(_variable, this.stencilVar);
          if (_equals) {
            String _xblockexpression_2 = null;
            {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("if (");
              final Function1<String, String> _function_1 = (String i) -> {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("(");
                _builder_3.append(i);
                _builder_3.append("==");
                _builder_3.append(i);
                _builder_3.append("_INJ)");
                return _builder_3.toString();
              };
              String _join = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_1), " && ");
              _builder_2.append(_join);
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
              _builder_3.newLine();
              _xblockexpression_2 = _builder_3.toString();
            }
            _xifexpression = _xblockexpression_2;
          } else {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("#define ");
            _builder_2.append(name);
            _builder_2.append("(");
            _builder_2.append(defIndexNamesStr);
            _builder_2.append(") ");
            _builder_2.append(stmtStr);
            _xifexpression = _builder_2.toString();
          }
          _xblockexpression_1 = _xifexpression;
        }
        return _xblockexpression_1;
      };
      final List<String> macros = IterableExtensions.<String>sort(ListExtensions.<StandardEquation, String>map(this.system.getSystemBodies().get(0).getStandardEquations(), _function));
      _xblockexpression = IterableExtensions.join(macros, "\n");
    }
    return _xblockexpression;
  }

  public String countErrors() {
    String _xblockexpression = null;
    {
      final String name = "I";
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      final Variable variable = IterableExtensions.<Variable>findFirst(this.system.getLocals(), _function);
      if ((variable == null)) {
        return "";
      }
      final ISLUnionSet domain = variable.getDomain().setTupleName((this.stmtPrefix + name)).toUnionSet();
      final List<String> indexNames = domain.getSets().get(0).getIndexNames();
      final String idxStr = IterableExtensions.join(indexNames, ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.stmtPrefix);
      _builder.append(name);
      _builder.append("[");
      _builder.append(idxStr);
      _builder.append("]");
      final String SVar = _builder.toString();
      String _join = IterableExtensions.join(domain.copy().params().getParamNames(), ",");
      String _plus = ("[" + _join);
      final String paramStr = (_plus + "]");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("domain: \"");
      String _string = domain.toString();
      _builder_1.append(_string);
      _builder_1.append("\"");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("child:");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("schedule: \"");
      _builder_1.append(paramStr, "  ");
      _builder_1.append("->[");
      final Function1<String, String> _function_1 = (String i) -> {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("{ ");
        _builder_2.append(SVar);
        _builder_2.append("->[");
        _builder_2.append(i);
        _builder_2.append("] }");
        return _builder_2.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_1), ",");
      _builder_1.append(_join_1, "  ");
      _builder_1.append("]\"");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("  ");
      _builder_1.newLine();
      final ISLSchedule ISchedule = ISLUtil.toISLSchedule(_builder_1);
      final ISLIdentifierList iterators = ISLUtil.toISLIdentifierList(((String[])Conversions.unwrapArray(indexNames, String.class)));
      final ISLASTBuild build = ISLASTBuild.buildFromContext(ISchedule.getDomain().copy().params()).setIterators(iterators.copy());
      final ISLASTNode node = build.generate(ISchedule.copy());
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(name);
      _builder_2.append("(");
      _builder_2.append(idxStr);
      _builder_2.append(")");
      final String varAcc = _builder_2.toString();
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
          default:
            break;
        }
      }
      final String vStr = _switchResult;
      final List<String> yVarIndexNames = this.stencilVar.getDomain().getIndexNames();
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("// Count checksum difference above THRESHOLD");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("// Returns 1 if signal was raised at (tt,ti,...) else 0");
      _builder_3.newLine();
      CharSequence _checkCoordinateMacro = this.checkCoordinateMacro(this.buildChecksumContainer());
      _builder_3.append(_checkCoordinateMacro);
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      _builder_3.append("const char* verbose = getenv(\"VERBOSE\");");
      _builder_3.newLine();
      _builder_3.newLine();
      _builder_3.append("#define print_");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.append("(");
      _builder_3.append(idxStr);
      _builder_3.append(") printf(\"");
      _builder_3.append(vStr);
      _builder_3.append("_");
      _builder_3.append(name);
      _builder_3.append("(");
      final Function1<String, String> _function_2 = (String it) -> {
        return "%d";
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_2), ",");
      _builder_3.append(_join_2);
      _builder_3.append(") = %E\\n\",");
      _builder_3.append(idxStr);
      _builder_3.append(", ");
      _builder_3.append(varAcc);
      _builder_3.append(")");
      _builder_3.newLineIfNotEmpty();
      _builder_3.append("#define ");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.append("(");
      _builder_3.append(idxStr);
      _builder_3.append(") do { if (verbose != NULL && fabs(");
      _builder_3.append(varAcc);
      _builder_3.append(")>=threshold) print_");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.append("(");
      _builder_3.append(idxStr);
      _builder_3.append("); if (containsInjectionCoordinate(");
      _builder_3.append(idxStr);
      _builder_3.append(") > 0) { if (fabs(");
      _builder_3.append(varAcc);
      _builder_3.append(")>=threshold) {result.TP++;} else {result.FN++;} } else { if (fabs(");
      _builder_3.append(varAcc);
      _builder_3.append(")>=threshold) {result.FP++;} else {result.TN++;} } } while(0)");
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      CharSequence _print = ProgramPrinter.print(this.dataType);
      _builder_3.append(_print);
      _builder_3.append(" threshold = 0;");
      _builder_3.newLineIfNotEmpty();
      _builder_3.append("const char* env_threshold = getenv(\"THRESHOLD\");");
      _builder_3.newLine();
      _builder_3.append("if (env_threshold != NULL) {");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("threshold = atof(env_threshold);");
      _builder_3.newLine();
      _builder_3.append("}");
      _builder_3.newLine();
      String _code = codegenVisitor.toCode();
      _builder_3.append(_code);
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      _builder_3.append("{");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("long N = result.FP + result.TN;");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("long P = result.FN + result.TP;");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("if (P != 0 && N != 0) {");
      _builder_3.newLine();
      _builder_3.append("\t\t");
      _builder_3.append("result.TPR = 100 * ((float)result.TP) / P;");
      _builder_3.newLine();
      _builder_3.append("\t\t");
      _builder_3.append("result.FPR = 100 * ((float)result.FP) / N;");
      _builder_3.newLine();
      _builder_3.append("\t\t");
      _builder_3.append("result.FNR = 100 * ((float)result.FN) / P;");
      _builder_3.newLine();
      _builder_3.append("\t\t");
      _builder_3.append("result.valid = 1;");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("}");
      _builder_3.newLine();
      _builder_3.append("}");
      _builder_3.newLine();
      _builder_3.append("#undef ");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.newLineIfNotEmpty();
      final String code = _builder_3.toString();
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

  /**
   * This function is used to identify the list of checksum pairs that surround the injection site
   * Used to identify whether or not a raised signal is a TP/FP/TN/FN
   */
  public ISLSet buildChecksumContainer() {
    ISLSet _xblockexpression = null;
    {
      if (((!Objects.equal(this.version, Version.ABFT_V1)) && (!Objects.equal(this.version, Version.ABFT_V2)))) {
        return null;
      }
      final Function1<Variable, Boolean> _function = (Variable it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, "C1"));
      };
      final Variable cVar = IterableExtensions.<Variable>findFirst(this.system.getLocals(), _function);
      int _size = cVar.getDomain().getIndexNames().size();
      final int nbSpatialDims = (_size - 1);
      ISLSet _xifexpression = null;
      boolean _equals = Objects.equal(this.version, Version.ABFT_V1);
      if (_equals) {
        final Function1<StandardEquation, Boolean> _function_1 = (StandardEquation it) -> {
          Variable _variable = it.getVariable();
          return Boolean.valueOf(Objects.equal(_variable, cVar));
        };
        AlphaExpression _expr = IterableExtensions.<StandardEquation>findFirst(this.systemBody.getStandardEquations(), _function_1).getExpr();
        _xifexpression = ((ReduceExpression) _expr).getBody().getContextDomain();
      } else {
        ISLSet _xblockexpression_1 = null;
        {
          final Function1<StandardEquation, Boolean> _function_2 = (StandardEquation it) -> {
            return Boolean.valueOf(it.getVariable().getName().startsWith("C2_"));
          };
          final Iterable<StandardEquation> c2nrs = IterableExtensions.<StandardEquation>filter(this.systemBody.getStandardEquations(), _function_2);
          int _size_1 = IterableExtensions.size(c2nrs);
          int _minus = (_size_1 - 1);
          AlphaExpression _expr_1 = (((StandardEquation[])Conversions.unwrapArray(c2nrs, StandardEquation.class))[_minus]).getExpr();
          _xblockexpression_1 = ((ReduceExpression) _expr_1).getBody().getContextDomain().copy().projectOut(ISLDimType.isl_dim_out, (1 + nbSpatialDims), (nbSpatialDims + 1));
        }
        _xifexpression = _xblockexpression_1;
      }
      final ISLSet face = _xifexpression;
      final Pair<Integer, Map<List<Integer>, Double>> convolutionKernel = ABFT.identify_convolution(this.system);
      final Integer radius = convolutionKernel.getKey();
      final Map<List<Integer>, Double> kernel = convolutionKernel.getValue();
      int _size_1 = this.stencilVar.getDomain().getIndexNames().size();
      final int spatialDims = (_size_1 - 1);
      ISLSet _switchResult = null;
      final Version version = this.version;
      if (version != null) {
        switch (version) {
          case ABFT_V1:
            _switchResult = this.buildV1Container(face, ABFT.buildParamStr(cVar), (radius).intValue(), spatialDims);
            break;
          case ABFT_V2:
            _switchResult = this.buildV2Container(face, ABFT.buildParamStr(cVar), (radius).intValue(), spatialDims);
            break;
          default:
            break;
        }
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }

  public ISLSet buildV1Container(final ISLSet face, final String paramStr, final int radius, final int spatialDims) {
    try {
      ISLSet _xblockexpression = null;
      {
        final Function1<Integer, String> _function = (Integer i) -> {
          return Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("i", "j", "k")).get((i).intValue());
        };
        final List<String> xs = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, spatialDims, true), _function));
        final Function1<String, String> _function_1 = (String x) -> {
          return ("t" + x);
        };
        final List<String> txs = IterableExtensions.<String>toList(ListExtensions.<String, String>map(xs, _function_1));
        final String xsStr = IterableExtensions.join(xs, ",");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("tt,");
        String _join = IterableExtensions.join(txs, ",");
        _builder.append(_join);
        final String txsStr = _builder.toString();
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(txsStr);
        _builder_1.append(",t,");
        _builder_1.append(xsStr);
        final String inStr = _builder_1.toString();
        final int TT = this.tileSizes[0];
        final Function1<ArrayList<Integer>, String> _function_2 = (ArrayList<Integer> ijks) -> {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("c[");
          _builder_2.append(inStr);
          _builder_2.append("]->c[");
          _builder_2.append(txsStr);
          _builder_2.append(",t-1,");
          final Function1<Pair<String, Integer>, String> _function_3 = (Pair<String, Integer> it) -> {
            String _xblockexpression_1 = null;
            {
              final String x = it.getKey();
              final Integer coeff = it.getValue();
              String _xifexpression = null;
              if (((coeff).intValue() > 0)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append(x);
                _builder_3.append("+");
                _builder_3.append(radius);
                _xifexpression = _builder_3.toString();
              } else {
                StringConcatenation _builder_4 = new StringConcatenation();
                _builder_4.append(x);
                _builder_4.append("-");
                _builder_4.append(radius);
                _xifexpression = _builder_4.toString();
              }
              _xblockexpression_1 = _xifexpression;
            }
            return _xblockexpression_1;
          };
          String _join_1 = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(xs, ijks), _function_3), ",");
          _builder_2.append(_join_1);
          _builder_2.append("]");
          return _builder_2.toString();
        };
        final Iterable<String> maps = IterableExtensions.<ArrayList<Integer>, String>map(CommonExtensions.<Integer>permutations(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf((-1)))), spatialDims), _function_2);
        final JNIPtrBoolean isExact = new JNIPtrBoolean();
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(paramStr);
        _builder_2.append("->{");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("[");
        _builder_2.append(txsStr, "\t");
        _builder_2.append(",");
        _builder_2.append(xsStr, "\t");
        _builder_2.append("]->c[");
        _builder_2.append(txsStr, "\t");
        _builder_2.append(",");
        _builder_2.append(TT, "\t");
        _builder_2.append("tt,");
        _builder_2.append(xsStr, "\t");
        _builder_2.append("];");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("c[");
        _builder_2.append(inStr, "\t");
        _builder_2.append("]->c[");
        _builder_2.append(inStr, "\t");
        _builder_2.append("];");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        String _join_1 = IterableExtensions.join(maps, ";\n");
        _builder_2.append(_join_1, "\t");
        _builder_2.append(";");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        final String closureStr = _builder_2.toString();
        final ISLUnionMap closure = ISLUtil.toISLUnionMap(closureStr).transitiveClosure(isExact);
        if ((!isExact.value)) {
          throw new Exception("Transitive closure should be exact, something went wrong");
        }
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append(paramStr);
        _builder_3.append("->{ c[");
        _builder_3.append(inStr);
        _builder_3.append("] : ");
        _builder_3.append(TT);
        _builder_3.append("tt-");
        _builder_3.append(TT);
        _builder_3.append("<=t<=");
        _builder_3.append(TT);
        _builder_3.append("tt}");
        final ISLUnionSet range = ISLUtil.toISLUnionSet(_builder_3);
        final ISLUnionMap map = closure.copy().intersectRange(range);
        final ISLUnionSet ret = face.copy().toUnionSet().apply(map.copy());
        int _size = ret.getSets().size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          throw new Exception("Issue applying transitive closure");
        }
        Iterable<String> _plus = Iterables.<String>concat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("tt")), txs);
        Iterable<String> _plus_1 = Iterables.<String>concat(_plus, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("t")));
        final List<String> names = IterableExtensions.<String>toList(Iterables.<String>concat(_plus_1, xs));
        final ISLSet container = AlphaUtil.renameIndices(ret.getSetAt(0).resetTupleID().coalesce(), names);
        _xblockexpression = container;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public ISLSet buildV2Container(final ISLSet face, final String paramStr, final int radius, final int spatialDims) {
    try {
      ISLSet _xblockexpression = null;
      {
        final Function1<Integer, String> _function = (Integer i) -> {
          return Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("i", "j", "k")).get((i).intValue());
        };
        final List<String> xs = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, spatialDims, true), _function));
        final Function1<String, String> _function_1 = (String x) -> {
          return ("t" + x);
        };
        final List<String> txs = IterableExtensions.<String>toList(ListExtensions.<String, String>map(xs, _function_1));
        final String xsStr = IterableExtensions.join(xs, ",");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("tt,");
        String _join = IterableExtensions.join(txs, ",");
        _builder.append(_join);
        final String txsStr = _builder.toString();
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(txsStr);
        _builder_1.append(",t,");
        _builder_1.append(xsStr);
        final String inStr = _builder_1.toString();
        final int TT = this.tileSizes[0];
        final Function1<ArrayList<Integer>, String> _function_2 = (ArrayList<Integer> ijks) -> {
          StringConcatenation _builder_2 = new StringConcatenation();
          _builder_2.append("c[");
          _builder_2.append(inStr);
          _builder_2.append("]->c[");
          _builder_2.append(txsStr);
          _builder_2.append(",t+1,");
          final Function1<Pair<String, Integer>, String> _function_3 = (Pair<String, Integer> it) -> {
            String _xblockexpression_1 = null;
            {
              final String x = it.getKey();
              final Integer coeff = it.getValue();
              String _xifexpression = null;
              if (((coeff).intValue() > 0)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append(x);
                _builder_3.append("+");
                _builder_3.append(radius);
                _xifexpression = _builder_3.toString();
              } else {
                StringConcatenation _builder_4 = new StringConcatenation();
                _builder_4.append(x);
                _builder_4.append("-");
                _builder_4.append(radius);
                _xifexpression = _builder_4.toString();
              }
              _xblockexpression_1 = _xifexpression;
            }
            return _xblockexpression_1;
          };
          String _join_1 = IterableExtensions.join(ListExtensions.<Pair<String, Integer>, String>map(CommonExtensions.<String, Integer>zipWith(xs, ijks), _function_3), ",");
          _builder_2.append(_join_1);
          _builder_2.append("]");
          return _builder_2.toString();
        };
        final Iterable<String> maps = IterableExtensions.<ArrayList<Integer>, String>map(CommonExtensions.<Integer>permutations(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf((-1)))), spatialDims), _function_2);
        final JNIPtrBoolean isExact = new JNIPtrBoolean();
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append(paramStr);
        _builder_2.append("->{");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("[");
        _builder_2.append(txsStr, "\t");
        _builder_2.append(",");
        _builder_2.append(xsStr, "\t");
        _builder_2.append("]->c[");
        _builder_2.append(txsStr, "\t");
        _builder_2.append(",");
        _builder_2.append(TT, "\t");
        _builder_2.append("tt-");
        _builder_2.append(TT, "\t");
        _builder_2.append(",");
        _builder_2.append(xsStr, "\t");
        _builder_2.append("];");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        _builder_2.append("c[");
        _builder_2.append(inStr, "\t");
        _builder_2.append("]->c[");
        _builder_2.append(inStr, "\t");
        _builder_2.append("];");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("\t");
        String _join_1 = IterableExtensions.join(maps, ";\n");
        _builder_2.append(_join_1, "\t");
        _builder_2.append(";");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("}");
        _builder_2.newLine();
        final String closureStr = _builder_2.toString();
        final ISLUnionMap closure = ISLUtil.toISLUnionMap(closureStr).transitiveClosure(isExact);
        if ((!isExact.value)) {
          throw new Exception("Transitive closure should be exact, something went wrong");
        }
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append(paramStr);
        _builder_3.append("->{ c[");
        _builder_3.append(inStr);
        _builder_3.append("] : ");
        _builder_3.append(TT);
        _builder_3.append("tt-");
        _builder_3.append(TT);
        _builder_3.append("<=t<=");
        _builder_3.append(TT);
        _builder_3.append("tt}");
        final ISLUnionSet range = ISLUtil.toISLUnionSet(_builder_3);
        final ISLUnionMap map = closure.copy().intersectRange(range);
        final ISLUnionSet ret = face.copy().toUnionSet().apply(map.copy());
        int _size = ret.getSets().size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          throw new Exception("Issue applying transitive closure");
        }
        Iterable<String> _plus = Iterables.<String>concat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("tt")), txs);
        Iterable<String> _plus_1 = Iterables.<String>concat(_plus, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("t")));
        final List<String> names = IterableExtensions.<String>toList(Iterables.<String>concat(_plus_1, xs));
        final ISLSet container = AlphaUtil.renameIndices(ret.getSetAt(0).resetTupleID().coalesce(), names);
        _xblockexpression = container;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public String printResultLoops(final String name) {
    String _xblockexpression = null;
    {
      final Function1<Variable, Boolean> _function = (Variable v) -> {
        String _name = v.getName();
        return Boolean.valueOf(Objects.equal(_name, name));
      };
      final Variable variable = IterableExtensions.<Variable>findFirst(this.system.getLocals(), _function);
      if ((variable == null)) {
        return "";
      }
      final ISLUnionSet domain = variable.getDomain().setTupleName((this.stmtPrefix + name)).toUnionSet();
      final List<String> indexNames = domain.getSets().get(0).getIndexNames();
      final String idxStr = IterableExtensions.join(indexNames, ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(this.stmtPrefix);
      _builder.append(name);
      _builder.append("[");
      _builder.append(idxStr);
      _builder.append("]");
      final String SVar = _builder.toString();
      String _join = IterableExtensions.join(domain.copy().params().getParamNames(), ",");
      String _plus = ("[" + _join);
      final String paramStr = (_plus + "]");
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("domain: \"");
      String _string = domain.toString();
      _builder_1.append(_string);
      _builder_1.append("\"");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("child:");
      _builder_1.newLine();
      _builder_1.append("  ");
      _builder_1.append("schedule: \"");
      _builder_1.append(paramStr, "  ");
      _builder_1.append("->[");
      final Function1<String, String> _function_1 = (String i) -> {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("{ ");
        _builder_2.append(SVar);
        _builder_2.append("->[");
        _builder_2.append(i);
        _builder_2.append("] }");
        return _builder_2.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_1), ",");
      _builder_1.append(_join_1, "  ");
      _builder_1.append("]\"");
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("  ");
      _builder_1.newLine();
      final ISLSchedule ISchedule = ISLUtil.toISLSchedule(_builder_1);
      final ISLIdentifierList iterators = ISLUtil.toISLIdentifierList(((String[])Conversions.unwrapArray(indexNames, String.class)));
      final ISLASTBuild build = ISLASTBuild.buildFromContext(ISchedule.getDomain().copy().params()).setIterators(iterators.copy());
      final ISLASTNode node = build.generate(ISchedule.copy());
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(name);
      _builder_2.append("(");
      _builder_2.append(idxStr);
      _builder_2.append(")");
      final String varAcc = _builder_2.toString();
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("// Print ");
      _builder_3.append(name);
      _builder_3.append(" values");
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      _builder_3.append("#define ");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.append("(");
      _builder_3.append(idxStr);
      _builder_3.append(") if (fabs(");
      _builder_3.append(varAcc);
      _builder_3.append(")>=threshold) printf(\"");
      String _name = this.system.getName();
      _builder_3.append(_name);
      _builder_3.append(".");
      _builder_3.append(name);
      _builder_3.append("(");
      final Function1<String, String> _function_2 = (String it) -> {
        return "%d";
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_2), ",");
      _builder_3.append(_join_2);
      _builder_3.append(") = %E\\n\",");
      _builder_3.append(idxStr);
      _builder_3.append(", ");
      _builder_3.append(varAcc);
      _builder_3.append(")");
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      CharSequence _print = ProgramPrinter.print(this.dataType);
      _builder_3.append(_print);
      _builder_3.append(" threshold = 0;");
      _builder_3.newLineIfNotEmpty();
      _builder_3.append("const char* env_threshold = getenv(\"THRESHOLD\");");
      _builder_3.newLine();
      _builder_3.append("if (env_threshold != NULL) {");
      _builder_3.newLine();
      _builder_3.append("\t");
      _builder_3.append("threshold = atof(env_threshold);");
      _builder_3.newLine();
      _builder_3.append("}");
      _builder_3.newLine();
      String _code = codegenVisitor.toCode();
      _builder_3.append(_code);
      _builder_3.newLineIfNotEmpty();
      _builder_3.newLine();
      _builder_3.append("#undef ");
      _builder_3.append(this.stmtPrefix);
      _builder_3.append(name);
      _builder_3.newLineIfNotEmpty();
      final String code = _builder_3.toString();
      _xblockexpression = code;
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

  public CharSequence signature(final AlphaSystem system) {
    return this.signature(system, this.version);
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
        String _name = v.getName();
        return ("float *" + _name);
      };
      final String ioArgs = IterableExtensions.join(IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, _outputs), _function_1), ", ");
      String _switchResult = null;
      if (_version != null) {
        switch (_version) {
          case BASELINE:
            _switchResult = "void";
            break;
          default:
            _switchResult = "struct Result";
            break;
        }
      } else {
        _switchResult = "struct Result";
      }
      final String returnType = _switchResult;
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
    try {
      CharSequence _xblockexpression = null;
      {
        final String stmtName = variable.getName();
        String _name = this.memoryMap.getName(variable.getName());
        final String mappedName = ("mem_" + _name);
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
    final ISLPWQPolynomial rank = MemoryUtils.rank(range);
    final ParenthesizedExpr accessExpression = PolynomialConverter.convert(rank);
    final ArrayAccessExpr macroReplacement = Factory.arrayAccessExpr(name, accessExpression);
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
