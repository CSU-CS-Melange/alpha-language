package alpha.codegen.demandDriven;

import alpha.codegen.AssignmentStmt;
import alpha.codegen.BaseDataType;
import alpha.codegen.BinaryExpr;
import alpha.codegen.BinaryOperator;
import alpha.codegen.CallExpr;
import alpha.codegen.CustomExpr;
import alpha.codegen.DataType;
import alpha.codegen.Expression;
import alpha.codegen.ExpressionStmt;
import alpha.codegen.Factory;
import alpha.codegen.Function;
import alpha.codegen.FunctionBuilder;
import alpha.codegen.MacroStmt;
import alpha.codegen.Parameter;
import alpha.codegen.ParenthesizedExpr;
import alpha.codegen.ProgramBuilder;
import alpha.codegen.ProgramPrinter;
import alpha.codegen.Statement;
import alpha.codegen.VariableDecl;
import alpha.codegen.alphaBase.AlphaBaseHelpers;
import alpha.codegen.alphaBase.AlphaNameChecker;
import alpha.codegen.alphaBase.ExprConverter;
import alpha.codegen.isl.ASTConversionResult;
import alpha.codegen.isl.ASTConverter;
import alpha.codegen.isl.AffineConverter;
import alpha.codegen.isl.LoopGenerator;
import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.AutoRestrictExpression;
import alpha.model.BinaryExpression;
import alpha.model.CaseExpression;
import alpha.model.ConstantExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.IfExpression;
import alpha.model.IndexExpression;
import alpha.model.MultiArgExpression;
import alpha.model.PolynomialIndexExpression;
import alpha.model.ReduceExpression;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.UnaryExpression;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.transformation.reduction.SplitReduction;
import alpha.model.util.AlphaUtil;
import alpha.model.util.CommonExtensions;
import alpha.model.util.Face;
import alpha.model.util.FaceLattice;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import fr.irisa.cairn.jnimap.isl.ISLUnionSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Converts Alpha expressions to simpleC expressions.
 * Adds support for reduce expressions.
 */
@SuppressWarnings("all")
public class WriteCExprConverter extends ExprConverter {
  /**
   * The name of the reduction variable inside of reduce functions.
   */
  protected static final String reduceVarName = "reduceVar";

  /**
   * The program being built.
   */
  protected final ProgramBuilder program;

  /**
   * Generates data types compatible with WriteC.
   */
  protected final WriteCTypeGenerator typeGenerator;

  /**
   * A counter for the number of reductions that have been created.
   * This is used for determining the names of functions and macros which will be emitted.
   */
  protected int nextReductionId = 0;

  /**
   * Constructs a new converter for expressions.
   */
  public WriteCExprConverter(final WriteCTypeGenerator typeGenerator, final AlphaNameChecker nameChecker, final ProgramBuilder program) {
    super(typeGenerator, nameChecker);
    this.program = program;
    this.typeGenerator = typeGenerator;
  }

  /**
   * Converts an Alpha reduce expression into the appropriate C AST nodes.
   * A new function is created and added to the program which computes the reduction,
   * and the appropriate function call expression is returned.
   */
  protected Expression _convertExpr(final ReduceExpression expr) {
    Boolean _fractalSimplify = expr.getFractalSimplify();
    if ((_fractalSimplify).booleanValue()) {
      final Function reduceFunction = this.createFractalReduceFunction(this.program, expr);
      this.program.addFunction(reduceFunction);
      List<String> _paramNames = expr.getContextDomain().getParamNames();
      List<String> _indexNames = expr.getContextDomain().getIndexNames();
      final Iterable<String> callArguments = Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames, _indexNames)));
      return Factory.callExpr(reduceFunction.getName(), ((String[])Conversions.unwrapArray(callArguments, String.class)));
    } else {
      final Function reduceFunction_1 = this.createReduceFunction(this.program, expr);
      this.program.addFunction(reduceFunction_1);
      List<String> _paramNames_1 = expr.getContextDomain().getParamNames();
      List<String> _indexNames_1 = expr.getContextDomain().getIndexNames();
      final Iterable<String> callArguments_1 = Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames_1, _indexNames_1)));
      return Factory.callExpr(reduceFunction_1.getName(), ((String[])Conversions.unwrapArray(callArguments_1, String.class)));
    }
  }

  protected String getFractalReduceFunctionName(final ReduceExpression re) {
    String _xblockexpression = null;
    {
      Equation _containerEquation = AlphaUtil.getContainerEquation(re);
      final Variable variable = ((StandardEquation) _containerEquation).getVariable();
      String _name = variable.getName();
      _xblockexpression = ("freduce_" + _name);
    }
    return _xblockexpression;
  }

  protected AlphaSystem getSystem(final ReduceExpression re) {
    return AlphaUtil.getContainerSystem(re);
  }

  protected Function createFractalReduceFunction(final ProgramBuilder program, final ReduceExpression expr) {
    try {
      final ISLMultiAff fp = expr.getProjection();
      final ISLMultiAff fd = SplitReduction.getReuseMaff(expr.getBody());
      final ISLSet D = expr.getFacet().toSet().removeRedundancies();
      final String reduceFunctionName = this.getFractalReduceFunctionName(expr);
      Equation _containerEquation = AlphaUtil.getContainerEquation(expr);
      final String lhsVar = ((StandardEquation) _containerEquation).getVariable().getName();
      final List<VariableExpression> varExprs = EcoreUtil2.<VariableExpression>getAllContentsOfType(expr, VariableExpression.class);
      int _size = varExprs.size();
      boolean _notEquals = (_size != 1);
      if (_notEquals) {
        throw new Exception("This implementation only supports a single variable expression in the reduction body");
      }
      final String lhsVarCopy = (lhsVar + "_copy");
      final String rhsVar = varExprs.get(0).getVariable().getName();
      final ISLMultiAff rho = SplitReduction.construct1DBasis(fd);
      final ISLSet acc = ISLUtil.nullSpace(fp);
      final FaceLattice lattice = FaceLattice.create(D.copy());
      int _dimensionality = ISLUtil.dimensionality(D);
      boolean _notEquals_1 = (_dimensionality != 2);
      if (_notEquals_1) {
        throw new Exception("fractal codegen only works for 2D domains (or embeddings)");
      }
      final ArrayList<ISLSet> domains = CommonExtensions.<ISLSet>toArrayList(WriteCExprConverter.fractalSplits(lattice.getRoot(), fp, fd));
      final Function1<ISLSet, Pair<Integer, ISLSet>> _function = (ISLSet d) -> {
        Pair<Integer, ISLSet> _xblockexpression = null;
        {
          final Iterable<Pair<Face, Face.Label>> labeledResidualEdges = WriteCExprConverter.getResidualEdges(d, acc, rho);
          final Function1<Pair<Face, Face.Label>, Boolean> _function_1 = (Pair<Face, Face.Label> it) -> {
            Face.Label _value = it.getValue();
            return Boolean.valueOf(Objects.equal(_value, Face.Label.ZERO));
          };
          final Pair<Face, Face.Label> invariantEdge = IterableExtensions.<Pair<Face, Face.Label>>findFirst(labeledResidualEdges, _function_1);
          Pair<Integer, ISLSet> _xifexpression = null;
          if ((invariantEdge == null)) {
            _xifexpression = Pair.<Integer, ISLSet>of(Integer.valueOf(0), d);
          } else {
            _xifexpression = WriteCExprConverter.getScanComputation(d, ((Pair<Face, Face.Label>[])Conversions.unwrapArray(labeledResidualEdges, Pair.class)), rho);
          }
          _xblockexpression = _xifexpression;
        }
        return _xblockexpression;
      };
      final ArrayList<Pair<Integer, ISLSet>> residualComputations = CommonExtensions.<Pair<Integer, ISLSet>>toArrayList(ListExtensions.<ISLSet, Pair<Integer, ISLSet>>map(domains, _function));
      final Function1<Pair<Integer, ISLSet>, Boolean> _function_1 = (Pair<Integer, ISLSet> it) -> {
        Integer _key = it.getKey();
        return Boolean.valueOf(((_key).intValue() == 1));
      };
      final ISLSet forwardScan = IterableExtensions.<Pair<Integer, ISLSet>>findFirst(residualComputations, _function_1).getValue();
      final Function1<Pair<Integer, ISLSet>, Boolean> _function_2 = (Pair<Integer, ISLSet> it) -> {
        Integer _key = it.getKey();
        return Boolean.valueOf(((_key).intValue() == (-1)));
      };
      final ISLSet backwardScan = IterableExtensions.<Pair<Integer, ISLSet>>findFirst(residualComputations, _function_2).getValue();
      final Function1<Pair<Integer, ISLSet>, Boolean> _function_3 = (Pair<Integer, ISLSet> it) -> {
        Integer _key = it.getKey();
        return Boolean.valueOf(((_key).intValue() == 0));
      };
      final ISLSet baseCase = IterableExtensions.<Pair<Integer, ISLSet>>findFirst(residualComputations, _function_3).getValue();
      final ISLSet fDomain = forwardScan.copy().subtract(forwardScan.copy().apply(rho.copy().toMap()));
      final ISLSet bDomain = backwardScan.copy().subtract(backwardScan.copy().preimage(rho.copy()));
      final FunctionBuilder function = program.startFunction(true, false, Factory.dataType(BaseDataType.VOID), reduceFunctionName);
      final AlphaSystem system = AlphaUtil.getContainerSystem(expr);
      final Function1<VariableDecl, Boolean> _function_4 = (VariableDecl it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, lhsVar));
      };
      final DataType lhsType = IterableExtensions.<VariableDecl>findFirst(program.getInstance().getGlobalVariables(), _function_4).getDataType();
      final Function1<VariableDecl, Boolean> _function_5 = (VariableDecl it) -> {
        String _name = it.getName();
        return Boolean.valueOf(Objects.equal(_name, rhsVar));
      };
      final DataType rhsType = IterableExtensions.<VariableDecl>findFirst(program.getInstance().getGlobalVariables(), _function_5).getDataType();
      final Function1<String, Parameter> _function_6 = (String it) -> {
        return this.toParameter(it);
      };
      function.addParameter(((Parameter[])Conversions.unwrapArray(ListExtensions.<String, Parameter>map(system.getParameterDomain().getParamNames(), _function_6), Parameter.class)));
      function.addParameter(Factory.dataType(lhsType.getBaseType(), lhsType.getIndirectionLevel()), lhsVar);
      function.addParameter(Factory.dataType(lhsType.getBaseType(), lhsType.getIndirectionLevel()), (lhsVar + "_copy"));
      function.addParameter(Factory.dataType(rhsType.getBaseType(), rhsType.getIndirectionLevel()), rhsVar);
      final CharSequence code = this.createFractalReduceFunctionCodeGen(program, expr, lhsVar, lhsVarCopy, rhsVar, D, fDomain, bDomain, baseCase, fp, fd, rho);
      final ExpressionStmt stmt = Factory.customStmt(code.toString());
      function.addStatement(stmt);
      function.addEmptyLine();
      function.addStatement(Factory.undefStmt("S"));
      return function.getInstance();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  protected CharSequence createFractalReduceFunctionCodeGen(final ProgramBuilder program, final ReduceExpression expr, final String lhsVar, final String lhsVarCopy, final String rhsVar, final ISLSet inputDomain, final ISLSet fDomainRaw, final ISLSet bDomainRaw, final ISLSet baseCase, final ISLMultiAff fp, final ISLMultiAff fd, final ISLMultiAff rho) {
    CharSequence _xblockexpression = null;
    {
      final boolean ADD_DEBUG_STMTS = false;
      final String funcName = this.getFractalReduceFunctionName(expr);
      final String paramStr = IterableExtensions.join(baseCase.getParamNames(), ",");
      final List<String> indexNames = baseCase.getIndexNames();
      final String indexNameStr = IterableExtensions.join(indexNames, ",");
      final ISLMultiAff idMaff = ISLMultiAff.buildIdentity(rho.getSpace().copy());
      final ISLMultiAff oppositeRho = idMaff.copy().add(idMaff.copy()).sub(rho.copy());
      final CharSequence lhsAcc = ProgramPrinter.printExpr(Factory.callExpr(lhsVar, ((Expression[])Conversions.unwrapArray(AffineConverter.convertMultiAff(fp), Expression.class))));
      final CharSequence lhsCopyAcc = ProgramPrinter.printExpr(Factory.callExpr(lhsVarCopy, ((Expression[])Conversions.unwrapArray(AffineConverter.convertMultiAff(fp), Expression.class))));
      final CharSequence lhsCopyRightAcc = ProgramPrinter.printExpr(Factory.callExpr(lhsVarCopy, ((Expression[])Conversions.unwrapArray(AffineConverter.convertMultiAff(fp.copy().pullback(rho.copy())), Expression.class))));
      final CharSequence lhsLeftAcc = ProgramPrinter.printExpr(Factory.callExpr(lhsVar, ((Expression[])Conversions.unwrapArray(AffineConverter.convertMultiAff(fp.copy().pullback(oppositeRho.copy())), Expression.class))));
      final CharSequence rhsAcc = ProgramPrinter.printExpr(Factory.callExpr(rhsVar, ((Expression[])Conversions.unwrapArray(AffineConverter.convertMultiAff(fd), Expression.class))));
      final List<ReduceExpression> reduceExprs = EcoreUtil2.<ReduceExpression>getAllContentsOfType(AlphaUtil.getContainerSystem(expr), ReduceExpression.class);
      final int thisReduceExprIdx = reduceExprs.indexOf(expr);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("S");
      _builder.append(thisReduceExprIdx);
      _builder.append("_flatten_top");
      final String flattenTupleTop = _builder.toString();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("S");
      _builder_1.append(thisReduceExprIdx);
      _builder_1.append("_flatten_bottom");
      final String flattenTupleBottom = _builder_1.toString();
      final ISLUnionSet flattenDomain = fDomainRaw.copy().setTupleName(flattenTupleTop).toUnionSet().union(bDomainRaw.copy().setTupleName(flattenTupleBottom).toUnionSet());
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("domain: \"");
      String _string = flattenDomain.toString();
      _builder_2.append(_string);
      _builder_2.append("\"");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("child:");
      _builder_2.newLine();
      _builder_2.append("  ");
      _builder_2.append("sequence:");
      _builder_2.newLine();
      _builder_2.append("  ");
      _builder_2.append("- filter: \"{ ");
      _builder_2.append(flattenTupleTop, "  ");
      _builder_2.append("[");
      _builder_2.append(indexNameStr, "  ");
      _builder_2.append("] }\"");
      _builder_2.newLineIfNotEmpty();
      _builder_2.append("  ");
      _builder_2.append("- filter: \"{ ");
      _builder_2.append(flattenTupleBottom, "  ");
      _builder_2.append("[");
      _builder_2.append(indexNameStr, "  ");
      _builder_2.append("] }\"");
      _builder_2.newLineIfNotEmpty();
      final ISLSchedule flattenSchedule = ISLUtil.toISLSchedule(_builder_2);
      final ISLASTBuild flattenBuild = ISLASTBuild.buildFromContext(flattenSchedule.getDomain().copy().params());
      final ISLASTNode flattenNode = flattenBuild.generate(flattenSchedule.copy());
      final ISLSet fAnswerDomain = AlphaUtil.renameIndices(fDomainRaw.copy().apply(fp.copy().toMap()), indexNames);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append("S");
      _builder_3.append(thisReduceExprIdx);
      _builder_3.append("_forward_init");
      final String fInitTuple = _builder_3.toString();
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("S");
      _builder_4.append(thisReduceExprIdx);
      _builder_4.append("_forward_acc");
      final String fAccTuple = _builder_4.toString();
      final String fIndexNameStr = IterableExtensions.join(fAnswerDomain.getIndexNames(), ",");
      final ISLUnionSet fInit = fAnswerDomain.copy().lexMin().setTupleName(fInitTuple).toUnionSet();
      final ISLUnionSet fAcc = fAnswerDomain.copy().subtract(fAnswerDomain.copy().lexMin()).setTupleName(fAccTuple).toUnionSet();
      final ISLUnionSet fDomain = fInit.copy().union(fAcc.copy());
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("domain: \"");
      _builder_5.append(fAcc);
      _builder_5.append("\"");
      _builder_5.newLineIfNotEmpty();
      _builder_5.append("child:");
      _builder_5.newLine();
      _builder_5.append("  ");
      _builder_5.append("schedule: \"[");
      _builder_5.append(paramStr, "  ");
      _builder_5.append("]->[");
      final Function1<String, String> _function = (String i) -> {
        StringConcatenation _builder_6 = new StringConcatenation();
        _builder_6.append("{ ");
        _builder_6.append(fAccTuple);
        _builder_6.append("[");
        _builder_6.append(fIndexNameStr);
        _builder_6.append("]->[");
        _builder_6.append(i);
        _builder_6.append("] }");
        return _builder_6.toString();
      };
      String _join = IterableExtensions.join(ListExtensions.<String, String>map(fAnswerDomain.getIndexNames(), _function), ",");
      _builder_5.append(_join, "  ");
      _builder_5.append("]\"");
      _builder_5.newLineIfNotEmpty();
      final ISLSchedule forwardSchedule = ISLUtil.toISLSchedule(_builder_5);
      final ISLASTBuild forwardBuild = ISLASTBuild.buildFromContext(forwardSchedule.getDomain().copy().params());
      final ISLASTNode forwardNode = forwardBuild.generate(forwardSchedule.copy());
      StringConcatenation _builder_6 = new StringConcatenation();
      _builder_6.append("domain: \"");
      ISLUnionSet _unionSet = fAnswerDomain.copy().setTupleName("D").toUnionSet();
      _builder_6.append(_unionSet);
      _builder_6.append("\"");
      final ISLSchedule fDbgSched = ISLUtil.toISLSchedule(_builder_6);
      final ISLASTNode fDbgNode = ISLASTBuild.buildFromContext(fDbgSched.getDomain().copy().params()).generate(fDbgSched.copy());
      final ISLSet bAnswerDomain = AlphaUtil.renameIndices(bDomainRaw.copy().apply(fp.copy().toMap()), indexNames);
      StringConcatenation _builder_7 = new StringConcatenation();
      _builder_7.append("S");
      _builder_7.append(thisReduceExprIdx);
      _builder_7.append("_backward_acc");
      final String bAccTuple = _builder_7.toString();
      final String bIndexNameStr = IterableExtensions.join(bAnswerDomain.getIndexNames(), ",");
      final ISLUnionSet bAcc = bAnswerDomain.copy().subtract(bAnswerDomain.copy().lexMax()).setTupleName(bAccTuple).toUnionSet();
      StringConcatenation _builder_8 = new StringConcatenation();
      _builder_8.append("domain: \"");
      _builder_8.append(bAcc);
      _builder_8.append("\"");
      _builder_8.newLineIfNotEmpty();
      _builder_8.append("child:");
      _builder_8.newLine();
      _builder_8.append("  ");
      _builder_8.append("schedule: \"[");
      _builder_8.append(paramStr, "  ");
      _builder_8.append("]->[");
      final Function1<String, String> _function_1 = (String i) -> {
        StringConcatenation _builder_9 = new StringConcatenation();
        _builder_9.append("{ ");
        _builder_9.append(bAccTuple);
        _builder_9.append("[");
        _builder_9.append(bIndexNameStr);
        _builder_9.append("]->[N-");
        _builder_9.append(i);
        _builder_9.append("] }");
        return _builder_9.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<String, String>map(bAnswerDomain.getIndexNames(), _function_1), ",");
      _builder_8.append(_join_1, "  ");
      _builder_8.append("]\"");
      _builder_8.newLineIfNotEmpty();
      final ISLSchedule backwardSchedule = ISLUtil.toISLSchedule(_builder_8);
      final ISLASTBuild backwardBuild = ISLASTBuild.buildFromContext(backwardSchedule.getDomain().copy().params());
      final ISLASTNode backwardNode = backwardBuild.generate(backwardSchedule.copy());
      StringConcatenation _builder_9 = new StringConcatenation();
      _builder_9.append("domain: \"");
      ISLUnionSet _unionSet_1 = bAnswerDomain.copy().setTupleName("D").toUnionSet();
      _builder_9.append(_unionSet_1);
      _builder_9.append("\"");
      final ISLSchedule bDbgSched = ISLUtil.toISLSchedule(_builder_9);
      final ISLASTNode bDbgNode = ISLASTBuild.buildFromContext(bDbgSched.getDomain().copy().params()).generate(bDbgSched.copy());
      StringConcatenation _builder_10 = new StringConcatenation();
      _builder_10.append("S");
      _builder_10.append(thisReduceExprIdx);
      _builder_10.append("_combine");
      final String cTuple = _builder_10.toString();
      final ISLUnionSet cDomain = fAnswerDomain.copy().union(bAnswerDomain.copy()).coalesce().setTupleName(cTuple).toUnionSet();
      final String cIndexNameStr = bIndexNameStr;
      StringConcatenation _builder_11 = new StringConcatenation();
      _builder_11.append("domain: \"");
      _builder_11.append(cDomain);
      _builder_11.append("\"");
      final ISLSchedule combineSchedule = ISLUtil.toISLSchedule(_builder_11);
      final ISLASTBuild combineBuild = ISLASTBuild.buildFromContext(combineSchedule.getDomain().copy().params());
      final ISLASTNode combineNode = combineBuild.generate(combineSchedule.copy());
      StringConcatenation _builder_12 = new StringConcatenation();
      _builder_12.append("domain: \"");
      ISLUnionSet _unionSet_2 = fAnswerDomain.copy().union(bAnswerDomain.copy()).coalesce().setTupleName("D").toUnionSet();
      _builder_12.append(_unionSet_2);
      _builder_12.append("\"");
      final ISLSchedule cDbgSched = ISLUtil.toISLSchedule(_builder_12);
      final ISLASTNode cDbgNode = ISLASTBuild.buildFromContext(cDbgSched.getDomain().copy().params()).generate(cDbgSched.copy());
      StringConcatenation _builder_13 = new StringConcatenation();
      _builder_13.append("S");
      _builder_13.append(thisReduceExprIdx);
      final String baseTuple = _builder_13.toString();
      StringConcatenation _builder_14 = new StringConcatenation();
      _builder_14.append("domain: \"");
      String _string_1 = inputDomain.copy().setTupleName(baseTuple).toString();
      _builder_14.append(_string_1);
      _builder_14.append("\"");
      final ISLSchedule baseSchedule = ISLUtil.toISLSchedule(_builder_14);
      final ISLASTBuild baseBuild = ISLASTBuild.buildFromContext(baseSchedule.getDomain().copy().params());
      final ISLASTNode baseNode = baseBuild.generate(baseSchedule.copy());
      StringConcatenation _builder_15 = new StringConcatenation();
      _builder_15.append("// base case");
      _builder_15.newLine();
      _builder_15.append("if (N<10) {");
      _builder_15.newLine();
      _builder_15.append("\t");
      _builder_15.append("#define ");
      _builder_15.append(baseTuple, "\t");
      _builder_15.append("(");
      _builder_15.append(indexNameStr, "\t");
      _builder_15.append(") ");
      _builder_15.append(lhsAcc, "\t");
      _builder_15.append(" = max(");
      _builder_15.append(lhsAcc, "\t");
      _builder_15.append(",");
      _builder_15.append(rhsAcc, "\t");
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("    ");
      String _cString = baseNode.toCString();
      _builder_15.append(_cString, "    ");
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("    ");
      _builder_15.append("#undef ");
      _builder_15.append(baseTuple, "    ");
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("    ");
      _builder_15.append("return;");
      _builder_15.newLine();
      _builder_15.append("  ");
      _builder_15.append("}");
      _builder_15.newLine();
      _builder_15.newLine();
      _builder_15.append("// flatten (thick) faces");
      _builder_15.newLine();
      _builder_15.append("#define ");
      _builder_15.append(flattenTupleTop);
      _builder_15.append("(");
      _builder_15.append(indexNameStr);
      _builder_15.append(") ");
      _builder_15.append(lhsAcc);
      _builder_15.append(" = max(");
      _builder_15.append(lhsAcc);
      _builder_15.append(",");
      _builder_15.append(rhsAcc);
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#define ");
      _builder_15.append(flattenTupleBottom);
      _builder_15.append("(");
      _builder_15.append(indexNameStr);
      _builder_15.append(") ");
      _builder_15.append(lhsCopyAcc);
      _builder_15.append(" = max(");
      _builder_15.append(lhsCopyAcc);
      _builder_15.append(",");
      _builder_15.append(rhsAcc);
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      String _cString_1 = flattenNode.toCString();
      _builder_15.append(_cString_1);
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#undef ");
      _builder_15.append(flattenTupleTop);
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#undef ");
      _builder_15.append(flattenTupleBottom);
      _builder_15.newLineIfNotEmpty();
      _builder_15.newLine();
      _builder_15.append("// forward scan");
      _builder_15.newLine();
      _builder_15.append("#define ");
      _builder_15.append(fAccTuple);
      _builder_15.append("(");
      _builder_15.append(fIndexNameStr);
      _builder_15.append(") ");
      _builder_15.append(lhsAcc);
      _builder_15.append(" = max(");
      _builder_15.append(lhsAcc);
      _builder_15.append(",");
      _builder_15.append(lhsLeftAcc);
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      String _cString_2 = forwardNode.toCString();
      _builder_15.append(_cString_2);
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#undef ");
      _builder_15.append(fAccTuple);
      _builder_15.newLineIfNotEmpty();
      {
        if (ADD_DEBUG_STMTS) {
          _builder_15.append("// debug");
          _builder_15.newLine();
          _builder_15.append("#define D(i) printf(\"f(%2d) = %2ld\\n\", i, ");
          _builder_15.append(lhsAcc);
          _builder_15.append(")");
          _builder_15.newLineIfNotEmpty();
          String _cString_3 = fDbgNode.toCString();
          _builder_15.append(_cString_3);
          _builder_15.newLineIfNotEmpty();
          _builder_15.append("#undef D");
          _builder_15.newLine();
          _builder_15.append("printf(\"\\n\");");
          _builder_15.newLine();
        }
      }
      _builder_15.newLine();
      _builder_15.append("// backward scan");
      _builder_15.newLine();
      _builder_15.append("#define ");
      _builder_15.append(bAccTuple);
      _builder_15.append("(");
      _builder_15.append(bIndexNameStr);
      _builder_15.append(") ");
      _builder_15.append(lhsCopyAcc);
      _builder_15.append(" = max(");
      _builder_15.append(lhsCopyAcc);
      _builder_15.append(",");
      _builder_15.append(lhsCopyRightAcc);
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      String _cString_4 = backwardNode.toCString();
      _builder_15.append(_cString_4);
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#undef ");
      _builder_15.append(bAccTuple);
      _builder_15.newLineIfNotEmpty();
      {
        if (ADD_DEBUG_STMTS) {
          _builder_15.append("// debug");
          _builder_15.newLine();
          _builder_15.append("#define D(i) printf(\"b(%2d) = %2ld\\n\", i, ");
          _builder_15.append(lhsCopyAcc);
          _builder_15.append(")");
          _builder_15.newLineIfNotEmpty();
          String _cString_5 = bDbgNode.toCString();
          _builder_15.append(_cString_5);
          _builder_15.newLineIfNotEmpty();
          _builder_15.append("#undef D");
          _builder_15.newLine();
          _builder_15.append("printf(\"\\n\");");
          _builder_15.newLine();
        }
      }
      _builder_15.newLine();
      _builder_15.append("// combine forward and backward scan results");
      _builder_15.newLine();
      _builder_15.append("#define ");
      _builder_15.append(cTuple);
      _builder_15.append("(");
      _builder_15.append(cIndexNameStr);
      _builder_15.append(") ");
      _builder_15.append(lhsAcc);
      _builder_15.append(" = max(");
      _builder_15.append(lhsAcc);
      _builder_15.append(",");
      _builder_15.append(lhsCopyAcc);
      _builder_15.append(")");
      _builder_15.newLineIfNotEmpty();
      String _cString_6 = combineNode.toCString();
      _builder_15.append(_cString_6);
      _builder_15.newLineIfNotEmpty();
      _builder_15.append("#undef ");
      _builder_15.append(cTuple);
      _builder_15.newLineIfNotEmpty();
      {
        if (ADD_DEBUG_STMTS) {
          _builder_15.append("// debug");
          _builder_15.newLine();
          _builder_15.append("#define D(i) printf(\"c(%2d) = %2ld\\n\", i, ");
          _builder_15.append(lhsCopyAcc);
          _builder_15.append(")");
          _builder_15.newLineIfNotEmpty();
          String _cString_7 = cDbgNode.toCString();
          _builder_15.append(_cString_7);
          _builder_15.newLineIfNotEmpty();
          _builder_15.append("#undef D");
          _builder_15.newLine();
          _builder_15.append("printf(\"===\\n\");");
          _builder_15.newLine();
        }
      }
      _builder_15.newLine();
      _builder_15.append("// recursive call");
      _builder_15.newLine();
      _builder_15.append(funcName);
      _builder_15.append("((N/2)-1, ");
      _builder_15.append(lhsVar);
      _builder_15.append(", ");
      _builder_15.append(lhsVarCopy);
      _builder_15.append(", ");
      _builder_15.append(rhsVar);
      _builder_15.append(")");
      _xblockexpression = _builder_15;
    }
    return _xblockexpression;
  }

  /**
   * Creates the function which evaluates the reduction at a specific output point.
   */
  protected Function createReduceFunction(final ProgramBuilder program, final ReduceExpression expr) {
    String reduceFunctionName = null;
    String reducePointMacroName = null;
    String accumulateMacroName = null;
    do {
      {
        reduceFunctionName = ("reduce" + Integer.valueOf(this.nextReductionId));
        reducePointMacroName = ("RP" + Integer.valueOf(this.nextReductionId));
        accumulateMacroName = ("R" + Integer.valueOf(this.nextReductionId));
        int _nextReductionId = this.nextReductionId;
        this.nextReductionId = (_nextReductionId + 1);
      }
    } while(program.getNameChecker().isGlobalOrKeyword(reduceFunctionName, reducePointMacroName, accumulateMacroName));
    final FunctionBuilder function = program.startFunction(true, false, this.typeGenerator.getAlphaValueType(), reduceFunctionName);
    final AssignmentStmt initializeStmt = Factory.assignmentStmt(WriteCExprConverter.reduceVarName, AlphaBaseHelpers.getReductionInitialValue(this.typeGenerator.getAlphaValueBaseType(), expr.getOperator()));
    function.addVariable(this.typeGenerator.getAlphaValueType(), WriteCExprConverter.reduceVarName).addStatement(initializeStmt);
    final MacroStmt reducePointMacro = this.createReducePointMacro(reducePointMacroName, program, expr);
    final MacroStmt accumulateMacro = this.createAccumulationMacro(accumulateMacroName, expr, reducePointMacro);
    function.addStatement(reducePointMacro, accumulateMacro);
    final ISLSet loopDomain = this.createReduceLoopDomain(expr);
    final ISLASTNode islAST = LoopGenerator.generateLoops(accumulateMacro.getName(), loopDomain);
    final Function1<String, Parameter> _function = (String it) -> {
      return this.toParameter(it);
    };
    function.addParameter(((Parameter[])Conversions.unwrapArray(ListExtensions.<String, Parameter>map(loopDomain.getParamNames(), _function), Parameter.class)));
    final ASTConversionResult loopResult = ASTConverter.convert(islAST);
    final Consumer<String> _function_1 = (String it) -> {
      function.addVariable(this.typeGenerator.getIndexType(), it);
    };
    loopResult.getDeclarations().forEach(_function_1);
    function.addStatement(((Statement[])Conversions.unwrapArray(loopResult.getStatements(), Statement.class)));
    function.addUndefine(reducePointMacro, accumulateMacro).addReturn(WriteCExprConverter.reduceVarExpr());
    return function.getInstance();
  }

  /**
   * Constructs the domain which will represent the loop nest that isl will produce.
   */
  protected ISLSet createReduceLoopDomain(final ReduceExpression reduceExpr) {
    ISLSet pointsToReduce = reduceExpr.getBody().getContextDomain().copy();
    final HashSet<String> existingNames = CollectionLiterals.<String>newHashSet();
    existingNames.addAll(pointsToReduce.getParamNames());
    existingNames.addAll(pointsToReduce.getIndexNames());
    final ISLSet outputDomain = reduceExpr.getContextDomain();
    int _nbIndices = outputDomain.getNbIndices();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbIndices, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final String outputName = outputDomain.getIndexName((i).intValue());
        final String parameterName = this.nameChecker.getUniqueLocalName(existingNames, outputName, "p");
        existingNames.add(parameterName);
        pointsToReduce = pointsToReduce.<ISLSet>addParams(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(parameterName)));
        final ISLAff outputExpr = reduceExpr.getProjection().getAff((i).intValue());
        final ISLConstraint outputConstraint = WriteCExprConverter.constrainAddedParameter(pointsToReduce.getSpace(), outputExpr);
        pointsToReduce = pointsToReduce.addConstraint(outputConstraint);
      }
    }
    return pointsToReduce;
  }

  /**
   * Adds an equality constraint to the recently added parameter
   * (i.e., the parameter with the largest index).
   * This parameter is set equal to the given affine expression
   * (which comes from the reduction's projection function).
   */
  protected static ISLConstraint constrainAddedParameter(final ISLSpace reductionSpace, final ISLAff outputExpr) {
    int _nbParams = reductionSpace.getNbParams();
    final int parameterIndex = (_nbParams - 1);
    ISLConstraint equality = ISLConstraint.buildEquality(reductionSpace.copy()).setCoefficient(ISLDimType.isl_dim_param, parameterIndex, (-1));
    int _nbParams_1 = outputExpr.getNbParams();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _nbParams_1, true);
    for (final Integer paramIdx : _doubleDotLessThan) {
      {
        final ISLVal coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_param, (paramIdx).intValue());
        equality = equality.setCoefficient(ISLDimType.isl_dim_param, (paramIdx).intValue(), coefficient);
      }
    }
    int _nbInputs = outputExpr.getNbInputs();
    ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _nbInputs, true);
    for (final Integer inIdx : _doubleDotLessThan_1) {
      {
        final ISLVal coefficient = outputExpr.getCoefficientVal(ISLDimType.isl_dim_in, (inIdx).intValue());
        equality = equality.setCoefficient(ISLDimType.isl_dim_set, (inIdx).intValue(), coefficient);
      }
    }
    return equality;
  }

  /**
   * Gets an expression representing the reduce variable.
   */
  protected static CustomExpr reduceVarExpr() {
    return Factory.customExpr(WriteCExprConverter.reduceVarName);
  }

  /**
   * Constructs a parameter for the reduce function.
   */
  protected Parameter toParameter(final String name) {
    return Factory.parameter(this.typeGenerator.getIndexType(), name);
  }

  /**
   * Constructs the macro that evaluates a point within the reduction body.
   */
  protected MacroStmt createReducePointMacro(final String macroName, final ProgramBuilder program, final ReduceExpression expr) {
    final List<String> arguments = expr.getBody().getContextDomain().getIndexNames();
    final Expression replacement = this.convertExpr(expr.getBody());
    return Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(arguments, String.class)), replacement);
  }

  /**
   * Constructs the macro used to accumulate points of the reduction body into the reduce variable.
   */
  protected MacroStmt createAccumulationMacro(final String macroName, final ReduceExpression expr, final MacroStmt reducePointMacro) {
    final Function1<String, ParenthesizedExpr> _function = (String it) -> {
      return Factory.parenthesizedExpr(it);
    };
    final List<ParenthesizedExpr> reducePointArguments = ListExtensions.<String, ParenthesizedExpr>map(expr.getBody().getContextDomain().getIndexNames(), _function);
    final CallExpr reducePointCall = Factory.callExpr(reducePointMacro.getName(), ((Expression[])Conversions.unwrapArray(reducePointArguments, Expression.class)));
    final BinaryOperator operator = AlphaBaseHelpers.getOperator(expr.getOperator());
    final BinaryExpr accumulateExpr = Factory.binaryExpr(operator, WriteCExprConverter.reduceVarExpr(), reducePointCall);
    final AssignmentStmt accumulateStmt = Factory.assignmentStmt(WriteCExprConverter.reduceVarExpr(), accumulateExpr);
    return Factory.macroStmt(macroName, ((String[])Conversions.unwrapArray(expr.getBody().getContextDomain().getIndexNames(), String.class)), accumulateStmt);
  }

  public static List<ISLBasicSet> makeSplit(final ISLBasicSet set, final ISLMultiAff fp, final ISLMultiAff fd) {
    final ISLConstraint[] splits = SplitReduction.enumerateCandidateSplits(FaceLattice.create(set.copy()).getRoot(), fp, fd);
    int _size = ((List<ISLConstraint>)Conversions.doWrapArray(splits)).size();
    boolean _equals = (_size == 1);
    if (_equals) {
      final ISLConstraint split = splits[0];
      final ISLBasicSet DS1 = split.getAff().toInequalityConstraint().toBasicSet().intersect(set.copy());
      long _constant = split.getAff().getConstant();
      final int const1 = Long.valueOf((_constant - 1)).intValue();
      final ISLBasicSet DS2 = split.getAff().negate().setConstant(const1).toInequalityConstraint().toBasicSet().intersect(set.copy());
      return Collections.<ISLBasicSet>unmodifiableList(CollectionLiterals.<ISLBasicSet>newArrayList(DS1, DS2));
    }
    return Collections.<ISLBasicSet>unmodifiableList(CollectionLiterals.<ISLBasicSet>newArrayList(set));
  }

  public static Iterable<ISLSet> fractalSplits(final Face face, final ISLMultiAff fp, final ISLMultiAff fd) {
    final Function1<ISLBasicSet, List<ISLBasicSet>> _function = (ISLBasicSet it) -> {
      return WriteCExprConverter.makeSplit(it, fp, fd);
    };
    final Function1<ISLBasicSet, ISLBasicSet> _function_1 = (ISLBasicSet it) -> {
      return it.removeRedundancies();
    };
    final Function1<ISLBasicSet, ISLSet> _function_2 = (ISLBasicSet it) -> {
      return it.toSet();
    };
    return IterableExtensions.<ISLBasicSet, ISLSet>map(IterableExtensions.<ISLBasicSet, ISLBasicSet>map(Iterables.<ISLBasicSet>concat(ListExtensions.<ISLBasicSet, List<ISLBasicSet>>map(WriteCExprConverter.makeSplit(face.toBasicSet(), fp, fd), _function)), _function_1), _function_2);
  }

  public static Iterable<Pair<Face, Face.Label>> getResidualEdges(final ISLSet domain, final ISLSet accumulationSpace, final ISLMultiAff rhoMaff) {
    try {
      Iterable<Pair<Face, Face.Label>> _xblockexpression = null;
      {
        final Function1<ISLAff, Long> _function = (ISLAff it) -> {
          return Long.valueOf(it.getConstant());
        };
        final List<Long> rho = ListExtensions.<ISLAff, Long>map(rhoMaff.getAffs(), _function);
        final Face root = FaceLattice.create(domain).getRoot();
        final ArrayList<Face> edges = root.generateChildren();
        int _size = edges.size();
        boolean _notEquals = (_size != 3);
        if (_notEquals) {
          throw new Exception("Fractal pieces should be triangular");
        }
        final List<Face.Label> labeling = root.getLabeling(((long[])Conversions.unwrapArray(rho, long.class)));
        final Function1<Pair<Face, Face.Label>, Boolean> _function_1 = (Pair<Face, Face.Label> it) -> {
          Face.Boundary _boundaryLabel = it.getKey().boundaryLabel(accumulationSpace);
          return Boolean.valueOf(Objects.equal(_boundaryLabel, Face.Boundary.STRONG));
        };
        _xblockexpression = IterableExtensions.<Pair<Face, Face.Label>>reject(CommonExtensions.<Face, Face.Label>zipWith(edges, labeling), _function_1);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static Pair<Integer, ISLSet> getScanComputation(final ISLSet domain, final Pair<Face, Face.Label>[] labeledEdges, final ISLMultiAff rho) {
    Pair<Integer, ISLSet> _xblockexpression = null;
    {
      final Function1<Pair<Face, Face.Label>, Boolean> _function = (Pair<Face, Face.Label> it) -> {
        Face.Label _value = it.getValue();
        return Boolean.valueOf((!Objects.equal(_value, Face.Label.ZERO)));
      };
      final Pair<Face, Face.Label> labeledEdge = IterableExtensions.<Pair<Face, Face.Label>>findFirst(((Iterable<Pair<Face, Face.Label>>)Conversions.doWrapArray(labeledEdges)), _function);
      final Face.Label label = labeledEdge.getValue();
      Pair<Integer, ISLSet> _switchResult = null;
      if (label != null) {
        switch (label) {
          case POS:
            _switchResult = Pair.<Integer, ISLSet>of(Integer.valueOf(1), domain);
            break;
          default:
            _switchResult = Pair.<Integer, ISLSet>of(Integer.valueOf((-1)), domain);
            break;
        }
      } else {
        _switchResult = Pair.<Integer, ISLSet>of(Integer.valueOf((-1)), domain);
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }

  public Expression convertExpr(final AlphaExpression expr) {
    if (expr instanceof ReduceExpression) {
      return _convertExpr((ReduceExpression)expr);
    } else if (expr instanceof AutoRestrictExpression) {
      return _convertExpr((AutoRestrictExpression)expr);
    } else if (expr instanceof BinaryExpression) {
      return _convertExpr((BinaryExpression)expr);
    } else if (expr instanceof CaseExpression) {
      return _convertExpr((CaseExpression)expr);
    } else if (expr instanceof ConstantExpression) {
      return _convertExpr((ConstantExpression)expr);
    } else if (expr instanceof DependenceExpression) {
      return _convertExpr((DependenceExpression)expr);
    } else if (expr instanceof IfExpression) {
      return _convertExpr((IfExpression)expr);
    } else if (expr instanceof IndexExpression) {
      return _convertExpr((IndexExpression)expr);
    } else if (expr instanceof MultiArgExpression) {
      return _convertExpr((MultiArgExpression)expr);
    } else if (expr instanceof PolynomialIndexExpression) {
      return _convertExpr((PolynomialIndexExpression)expr);
    } else if (expr instanceof RestrictExpression) {
      return _convertExpr((RestrictExpression)expr);
    } else if (expr instanceof UnaryExpression) {
      return _convertExpr((UnaryExpression)expr);
    } else if (expr instanceof VariableExpression) {
      return _convertExpr((VariableExpression)expr);
    } else if (expr != null) {
      return _convertExpr(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
}
