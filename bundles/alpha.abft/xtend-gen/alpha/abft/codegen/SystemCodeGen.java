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
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import java.util.Arrays;
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

  private final BaseDataType dataType;

  public SystemCodeGen(final AlphaSystem system, final MemoryMap memoryMap) {
    this(system, SystemCodeGen.defaultSchedule(system), memoryMap);
  }

  public SystemCodeGen(final AlphaSystem system, final String schedule, final MemoryMap memoryMap) {
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
    this.schedule = ISLUtil.toISLSchedule(this.scheduleStr);
    StandardizeNames.apply(system);
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

  public static String generateSystemCode(final AlphaSystem system, final CharSequence schedule, final MemoryMap memoryMap) {
    return SystemCodeGen.generateSystemCode(system, schedule.toString(), memoryMap);
  }

  public static String generateSystemCode(final AlphaSystem system, final String schedule, final MemoryMap memoryMap) {
    try {
      String _xblockexpression = null;
      {
        int _size = system.getSystemBodies().size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          throw new Exception("Only systems with a single body are currently supported");
        }
        final SystemCodeGen generator = new SystemCodeGen(system, schedule, memoryMap);
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
      _builder.append("\t");
      String _aboutComments = this.aboutComments();
      _builder.append(_aboutComments, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("#include<stdio.h>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#include<stdlib.h>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#include<math.h>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#include<time.h>");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#define max(x, y)   ((x)>(y) ? (x) : (y))");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#define min(x, y)   ((x)>(y) ? (y) : (x))");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#define ceild(n,d)  (int)ceil(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#define floord(n,d) (int)floor(((double)(n))/((double)(d)))");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("#define mallocCheck(v,s,d) if ((v) == NULL) { printf(\"Failed to allocate memory for %s : size=%lu\\n\", \"sizeof(d)*(s)\", sizeof(d)*(s)); exit(-1); }");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("void initialize_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("void reset_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("void start_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("void stop_timer();");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("double elapsed_time();");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Memory mapped targets");
      _builder.newLine();
      _builder.append("\t");
      final Function1<Pair<String, ISLSet>, String> _function = (Pair<String, ISLSet> it) -> {
        return this.memoryTargetMacro(it);
      };
      String _join = IterableExtensions.join(ListExtensions.<Pair<String, ISLSet>, String>map(this.memoryMap.uniqueTargets(), _function), "\n");
      _builder.append(_join, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("// Memory access functions");
      _builder.newLine();
      _builder.append("\t");
      final Function1<Variable, CharSequence> _function_1 = (Variable it) -> {
        return this.memoryMacro(it);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(this.system.getVariables(), _function_1), "\n");
      _builder.append(_join_1, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      CharSequence _signature = this.signature(this.system);
      _builder.append(_signature, "\t");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t  ");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder.append(_localMemoryAllocation, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t  ");
      String _defStmtMacros = this.defStmtMacros();
      _builder.append(_defStmtMacros, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("// Timers");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("double execution_time;");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("initialize_timer();");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("start_timer();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t  ");
      String _stmtLoops = this.stmtLoops();
      _builder.append(_stmtLoops, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("stop_timer();");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("execution_time = elapsed_time();");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t  ");
      String _undefStmtMacros = this.undefStmtMacros();
      _builder.append(_undefStmtMacros, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("\t  ");
      String _printResultLoops = this.printResultLoops("I");
      _builder.append(_printResultLoops, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.append("\t  ");
      _builder.newLine();
      _builder.append("\t  ");
      _builder.append("printf(\"");
      String _name = this.system.getName();
      _builder.append(_name, "\t  ");
      _builder.append(": %lf sec\\n\", execution_time);");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t  ");
      String _localMemoryFree = this.localMemoryFree();
      _builder.append(_localMemoryFree, "\t  ");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
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
          _builder.append("*   ");
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
          _builder.append("*   ");
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
          final String indexNamesStr = IterableExtensions.join(it.getExpr().getContextDomain().getIndexNames(), ",");
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
          _xblockexpression_1 = _builder_2.toString();
        }
        return _xblockexpression_1;
      };
      final List<String> macros = IterableExtensions.<String>sort(ListExtensions.<StandardEquation, String>map(this.system.getSystemBodies().get(0).getStandardEquations(), _function));
      _xblockexpression = IterableExtensions.join(macros, "\n");
    }
    return _xblockexpression;
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
      _builder_3.append("  ");
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
}
