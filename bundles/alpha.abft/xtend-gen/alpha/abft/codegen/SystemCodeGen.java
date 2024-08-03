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
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class SystemCodeGen {
  private final AlphaSystem system;

  private final SystemBody systemBody;

  private final ISLSchedule schedule;

  private final String scheduleStr;

  private final ISLUnionSet scheduleDomain;

  private final MemoryMap memoryMap;

  private final ExprConverter exprConverter;

  private final String stmtPrefix;

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
    final WriteCTypeGenerator typeGenerator = new WriteCTypeGenerator(BaseDataType.FLOAT, false);
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

  private String generate() {
    String _xblockexpression = null;
    {
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
      _builder.newLine();
      _builder.append("// Memory mapped targets");
      _builder.newLine();
      final Function1<Pair<String, ISLSet>, String> _function = (Pair<String, ISLSet> it) -> {
        return this.memoryTargetMacro(it);
      };
      String _join = IterableExtensions.join(ListExtensions.<Pair<String, ISLSet>, String>map(this.memoryMap.uniqueTargets(), _function), "\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("// Memory access functions");
      _builder.newLine();
      final Function1<Variable, CharSequence> _function_1 = (Variable it) -> {
        return this.memoryMacro(it);
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<Variable, CharSequence>map(this.system.getVariables(), _function_1), "\n");
      _builder.append(_join_1);
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("void here(float* C2_NR, float* allW, float* Y, long T, long N, int tt, int ti, int p, int w);");
      _builder.newLine();
      _builder.newLine();
      CharSequence _signature = this.signature();
      _builder.append(_signature);
      _builder.newLineIfNotEmpty();
      _builder.append("{");
      _builder.newLine();
      _builder.newLine();
      _builder.append("  ");
      String _localMemoryAllocation = this.localMemoryAllocation();
      _builder.append(_localMemoryAllocation, "  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("  ");
      String _defStmtMacros = this.defStmtMacros();
      _builder.append(_defStmtMacros, "  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("  ");
      String _stmtLoops = this.stmtLoops();
      _builder.append(_stmtLoops, "  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("  ");
      String _undefStmtMacros = this.undefStmtMacros();
      _builder.append(_undefStmtMacros, "  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("  ");
      String _printResultLoops = this.printResultLoops("I");
      _builder.append(_printResultLoops, "  ");
      _builder.newLineIfNotEmpty();
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      String _debugMain = this.debugMain();
      _builder.append(_debugMain);
      _builder.newLineIfNotEmpty();
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
      InputOutput.println();
      _xblockexpression = code;
    }
    return _xblockexpression;
  }

  public String localMemoryAllocation() {
    String _xblockexpression = null;
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
        int _size = it.getValue().size();
        return Boolean.valueOf((_size > 0));
      };
      final Function1<Map.Entry<String, LinkedList<Variable>>, Boolean> _function_2 = (Map.Entry<String, LinkedList<Variable>> it) -> {
        return Boolean.valueOf(((IterableExtensions.size(IterableExtensions.<Variable>filter(it.getValue(), ((Function1<Variable, Boolean>) (Variable it_1) -> {
          return it_1.isLocal();
        }))) > 0) && (IterableExtensions.size(IterableExtensions.<Variable>filter(it.getValue(), ((Function1<Variable, Boolean>) (Variable it_1) -> {
          return Boolean.valueOf(((it_1.isInput()).booleanValue() || (it_1.isOutput()).booleanValue()));
        }))) > 0)));
      };
      final Consumer<Map.Entry<String, LinkedList<Variable>>> _function_3 = (Map.Entry<String, LinkedList<Variable>> it) -> {
        try {
          throw new Exception("Mappings across locals and inputs/outputs not currently supported");
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      };
      IterableExtensions.<Map.Entry<String, LinkedList<Variable>>>filter(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>>filter(chunkVariables.entrySet(), _function_1), _function_2).forEach(_function_3);
      final Function1<Map.Entry<String, LinkedList<Variable>>, Boolean> _function_4 = (Map.Entry<String, LinkedList<Variable>> it) -> {
        final Function1<Variable, Boolean> _function_5 = (Variable it_1) -> {
          return it_1.isLocal();
        };
        int _size = IterableExtensions.size(IterableExtensions.<Variable>filter(it.getValue(), _function_5));
        return Boolean.valueOf((_size > 0));
      };
      final Function1<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>> _function_5 = (Map.Entry<String, LinkedList<Variable>> it) -> {
        final Function1<Variable, ISLSet> _function_6 = (Variable it_1) -> {
          return it_1.getDomain().copy();
        };
        final Function2<ISLSet, ISLSet, ISLSet> _function_7 = (ISLSet v1, ISLSet v2) -> {
          return v1.union(v2);
        };
        ISLSet _coalesce = IterableExtensions.<ISLSet>reduce(ListExtensions.<Variable, ISLSet>map(it.getValue(), _function_6), _function_7).coalesce();
        String _key = it.getKey();
        return Pair.<ISLSet, String>of(_coalesce, _key);
      };
      final Function1<Pair<ISLSet, String>, AssignmentStmt> _function_6 = (Pair<ISLSet, String> it) -> {
        String _value = it.getValue();
        String _plus = ("float *" + _value);
        return this.mallocStmt(it.getKey(), _plus);
      };
      final Iterable<AssignmentStmt> mallocStmts = IterableExtensions.<Pair<ISLSet, String>, AssignmentStmt>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>, Pair<ISLSet, String>>map(IterableExtensions.<Map.Entry<String, LinkedList<Variable>>>filter(chunkVariables.entrySet(), _function_4), _function_5), _function_6);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("// Local memory allocation");
      _builder.newLine();
      final Function1<AssignmentStmt, CharSequence> _function_7 = (AssignmentStmt it) -> {
        return ProgramPrinter.printStmt(it);
      };
      String _join = IterableExtensions.join(IterableExtensions.<AssignmentStmt, CharSequence>map(mallocStmts, _function_7), "\n");
      _builder.append(_join);
      _builder.newLineIfNotEmpty();
      final String code = _builder.toString();
      _xblockexpression = code;
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
      _builder_2.append("// Print ");
      _builder_2.append(name);
      _builder_2.append(" values");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("#define ");
      _builder_2.append(this.stmtPrefix);
      _builder_2.append(name);
      _builder_2.append("(");
      _builder_2.append(idxStr);
      _builder_2.append(") printf(\"");
      _builder_2.append(name);
      _builder_2.append("(");
      final Function1<String, String> _function_2 = (String it) -> {
        return "%d";
      };
      String _join_2 = IterableExtensions.join(ListExtensions.<String, String>map(indexNames, _function_2), ",");
      _builder_2.append(_join_2);
      _builder_2.append(") = %E\\n\",");
      _builder_2.append(idxStr);
      _builder_2.append(",");
      _builder_2.append(name);
      _builder_2.append("(");
      _builder_2.append(idxStr);
      _builder_2.append("))");
      _builder_2.newLineIfNotEmpty();
      _builder_2.newLine();
      String _code = codegenVisitor.toCode();
      _builder_2.append(_code);
      _builder_2.newLineIfNotEmpty();
      _builder_2.newLine();
      _builder_2.append("#undef ");
      _builder_2.append(this.stmtPrefix);
      _builder_2.append(name);
      _builder_2.newLineIfNotEmpty();
      final String code = _builder_2.toString();
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

  public CharSequence signature() {
    CharSequence _xblockexpression = null;
    {
      final Function1<String, String> _function = (String p) -> {
        return ("long " + p);
      };
      final String paramArgs = IterableExtensions.join(ListExtensions.<String, String>map(this.system.getParameterDomain().getParamNames(), _function), ",");
      EList<Variable> _inputs = this.system.getInputs();
      EList<Variable> _outputs = this.system.getOutputs();
      final Function1<Variable, String> _function_1 = (Variable v) -> {
        String _name = v.getName();
        return ("float *" + _name);
      };
      final String ioArgs = IterableExtensions.join(IterableExtensions.<Variable, String>map(Iterables.<Variable>concat(_inputs, _outputs), _function_1), ",");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("void ");
      String _name = this.system.getName();
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

  public String debugMain() {
    String _xblockexpression = null;
    {
      final Variable XVar = this.system.getInputs().get(0);
      String _name = XVar.getName();
      final String stmtName = ("S" + _name);
      final ISLSet XDomain = XVar.getDomain().copy().setTupleName(stmtName);
      final Function1<String, String> _function = (String it) -> {
        return "(N+1)";
      };
      final String mallocSize = IterableExtensions.join(ListExtensions.<String, String>map(XDomain.getIndexNames(), _function), "*");
      final String paramStr = IterableExtensions.join(XDomain.copy().params().getParamNames(), ",");
      final List<String> idxs = XDomain.getIndexNames();
      String _join = IterableExtensions.join(idxs, ",");
      String _plus = ((stmtName + "[") + _join);
      final String X = (_plus + "]");
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("domain: \"");
      String _string = XDomain.toString();
      _builder.append(_string);
      _builder.append("\"");
      _builder.newLineIfNotEmpty();
      _builder.append("child:");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("schedule: \"[");
      _builder.append(paramStr, "  ");
      _builder.append("]->[\\");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      final Function1<String, String> _function_1 = (String idx) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{ ");
        _builder_1.append(X);
        _builder_1.append("->[");
        _builder_1.append(idx);
        _builder_1.append("] }");
        return _builder_1.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(idxs, _function_1), ", \\\n");
      _builder.append(_join_1, "    ");
      _builder.append(" \\");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("]\"");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      final String xInitSchedStr = _builder.toString();
      final ISLSchedule xInitSched = ISLUtil.toISLSchedule(xInitSchedStr);
      final ISLIdentifierList iterators = ISLUtil.toISLIdentifierList(((String[])Conversions.unwrapArray(idxs, String.class)));
      final ISLASTBuild build = ISLASTBuild.buildFromContext(xInitSched.getDomain().copy().params()).setIterators(iterators.copy());
      final ISLASTNode node = build.generate(xInitSched.copy());
      final ISLASTNodeVisitor codegenVisitor = new ISLASTNodeVisitor().genC(node);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("#define ");
      _builder_1.append(stmtName);
      _builder_1.append("(");
      String _join_2 = IterableExtensions.join(idxs, ",");
      _builder_1.append(_join_2);
      _builder_1.append(") ");
      String _name_1 = XVar.getName();
      _builder_1.append(_name_1);
      _builder_1.append("(");
      String _join_3 = IterableExtensions.join(idxs, ",");
      _builder_1.append(_join_3);
      _builder_1.append(") = rand() % 100 + 1");
      _builder_1.newLineIfNotEmpty();
      String _code = codegenVisitor.toCode();
      _builder_1.append(_code);
      _builder_1.newLineIfNotEmpty();
      _builder_1.append("#undef ");
      _builder_1.append(stmtName);
      _builder_1.newLineIfNotEmpty();
      _builder_1.newLine();
      final String XLoops = _builder_1.toString();
      final int nd = idxs.size();
      int _switchResult = (int) 0;
      switch (nd) {
        case 1:
          _switchResult = 100;
          break;
        case 2:
          _switchResult = 50;
          break;
        case 3:
          _switchResult = 15;
          break;
      }
      final int T = _switchResult;
      int _switchResult_1 = (int) 0;
      switch (nd) {
        case 1:
          _switchResult_1 = 1000;
          break;
        case 2:
          _switchResult_1 = 300;
          break;
        case 3:
          _switchResult_1 = 70;
          break;
      }
      final int N = _switchResult_1;
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("int main() {");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("long T = ");
      _builder_2.append(T, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("long N = ");
      _builder_2.append(N, "\t");
      _builder_2.append(";");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("float *X = malloc(sizeof(float)*");
      _builder_2.append(mallocSize, "\t");
      _builder_2.append(");");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.append("float *Y = malloc(sizeof(float)*2*");
      _builder_2.append(mallocSize, "\t");
      _builder_2.append(");");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append("srand(0);");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      _builder_2.append(XLoops, "\t");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
      _builder_2.newLine();
      _builder_2.append("\t");
      String _name_2 = this.system.getName();
      _builder_2.append(_name_2, "\t");
      _builder_2.append("(T, N, X, Y);");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("\t");
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
}
