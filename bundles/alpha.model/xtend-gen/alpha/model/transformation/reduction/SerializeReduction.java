package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.DependenceExpression;
import alpha.model.Equation;
import alpha.model.RestrictExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.VariableExpression;
import alpha.model.factory.AlphaUserFactory;
import alpha.model.util.AlphaOperatorUtil;
import alpha.model.util.AlphaUtil;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function3;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Serializes a reduction along given reuse function(s).
 * This class is not given enough information to automatically decompose
 * reductions, so it must serialize all dimensions of the reduction at once.
 * Users can manually apply DecomposeReduction beforehand if they only wish to
 * partially serialize the reduction.
 */
@SuppressWarnings("all")
public class SerializeReduction {
  public static void apply(final AbstractReduceExpression reduce, final ISLMultiAff reuseDep) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(reduce);
    final Variable writeVar = ((StandardEquation) _containerEquation).getVariable();
    Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
    AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(reduce);
    String _name = writeVar.getName();
    String _plus = (_name + "_reduction");
    final String newName = _duplicateNameResolver.apply(_containerSystem, _plus, 
      "_");
    SerializeReduction.apply(reduce, reuseDep, newName);
  }

  public static void apply(final AbstractReduceExpression reduce, final ISLMultiAff reuseDep, final String newName) {
    SerializeReduction.checkArguments(reduce, Collections.<ISLMultiAff>unmodifiableList(CollectionLiterals.<ISLMultiAff>newArrayList(reuseDep)), newName);
    SerializeReduction.serialize(reduce, reuseDep, newName);
  }

  public static void applyAll(final AbstractReduceExpression reduce, final Iterable<ISLMultiAff> partialReuseDeps) {
    SerializeReduction.checkArguments(reduce, partialReuseDeps, "");
    final Function1<ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff a) -> {
      return a;
    };
    Iterable<ISLMultiAff> reuseDeps = IterableExtensions.<ISLMultiAff, ISLMultiAff>map(partialReuseDeps, _function);
    ISLSet nullSpace = ISLUtil.nullSpace(reduce.getProjectionExpr().getISLMultiAff().copy());
    int _size = IterableExtensions.size(reuseDeps);
    int _dimensionality = ISLUtil.dimensionality(nullSpace.copy());
    boolean _lessThan = (_size < _dimensionality);
    if (_lessThan) {
      final Function1<ISLMultiAff, ISLPoint> _function_1 = (ISLMultiAff dep) -> {
        return dep.copy().toMap().deltas().samplePoint();
      };
      final Iterable<ISLPoint> reuseVectors = IterableExtensions.<ISLMultiAff, ISLPoint>map(reuseDeps, _function_1);
      for (int i = 0; (i < IterableExtensions.size(reuseVectors)); i++) {
        nullSpace = nullSpace.apply(ISLUtil.buildRejectionMaff((((ISLPoint[])Conversions.unwrapArray(reuseVectors, ISLPoint.class))[i]).copy()).toMap());
      }
      final Function1<ISLPoint, ISLMultiAff> _function_2 = (ISLPoint vec) -> {
        return ISLUtil.buildTranslationMaff(vec);
      };
      List<ISLMultiAff> _map = ListExtensions.<ISLPoint, ISLMultiAff>map(ISLUtil.getBasisVectors(nullSpace), _function_2);
      Iterable<ISLMultiAff> _plus = Iterables.<ISLMultiAff>concat(reuseDeps, _map);
      reuseDeps = _plus;
    }
    Equation _containerEquation = AlphaUtil.getContainerEquation(reduce);
    final Variable writeVar = ((StandardEquation) _containerEquation).getVariable();
    for (int i = 0; (i < (((Object[])Conversions.unwrapArray(reuseDeps, Object.class)).length - 1)); i++) {
      {
        Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
        AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(reduce);
        String _name = writeVar.getName();
        String _plus_1 = (_name + "_reduction");
        final String newName = _duplicateNameResolver.apply(_containerSystem, _plus_1, 
          Integer.valueOf(i).toString());
        final ISLMultiAff writeMaff = reduce.getProjectionExpr().getISLMultiAff();
        final Iterable<ISLMultiAff> _converted_reuseDeps = (Iterable<ISLMultiAff>)reuseDeps;
        final ISLMultiAff reuseDep = ((ISLMultiAff[])Conversions.unwrapArray(_converted_reuseDeps, ISLMultiAff.class))[i];
        final ISLMultiAff f1 = ISLUtil.buildRejectionMaff(reuseDep.copy().toMap().deltas().samplePoint());
        final ISLMultiAff f2 = ISLUtil.toMultiAff(writeMaff.copy().toMap().applyDomain(f1.copy().toMap()));
        ReductionDecomposition.apply(reduce, f1, f2);
        AlphaExpression _body = reduce.getBody();
        SerializeReduction.serialize(((AbstractReduceExpression) _body), reuseDep, newName);
      }
    }
    Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
    AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(reduce);
    String _name = writeVar.getName();
    String _plus_1 = (_name + "_reduction");
    final Iterable<ISLMultiAff> _converted_reuseDeps = (Iterable<ISLMultiAff>)reuseDeps;
    int _length = ((Object[])Conversions.unwrapArray(_converted_reuseDeps, Object.class)).length;
    final String newName = _duplicateNameResolver.apply(_containerSystem, _plus_1, 
      Integer.valueOf((_length - 1)).toString());
    final Iterable<ISLMultiAff> _converted_reuseDeps_1 = (Iterable<ISLMultiAff>)reuseDeps;
    final Iterable<ISLMultiAff> _converted_reuseDeps_2 = (Iterable<ISLMultiAff>)reuseDeps;
    int _length_1 = ((Object[])Conversions.unwrapArray(_converted_reuseDeps_2, Object.class)).length;
    int _minus = (_length_1 - 1);
    SerializeReduction.serialize(reduce, ((ISLMultiAff[])Conversions.unwrapArray(_converted_reuseDeps_1, ISLMultiAff.class))[_minus], newName);
  }

  private static void serialize(final AbstractReduceExpression reduce, final ISLMultiAff reuseDep, final String newName) {
    AlphaSystem sys = AlphaUtil.getContainerSystem(reduce);
    final SystemBody systemBody = AlphaUtil.getContainerSystemBody(reduce);
    final ISLSet body = reduce.getBody().getContextDomain();
    final ISLMultiAff writeMaff = reduce.getProjectionExpr().getISLMultiAff();
    AlphaExpression coreExpr = reduce.getBody();
    if ((coreExpr instanceof RestrictExpression)) {
      coreExpr = ((RestrictExpression)coreExpr).getExpr();
    }
    final Variable reductionVar = AlphaUserFactory.createVariable(newName, body.copy());
    sys.getLocals().add(reductionVar);
    final ISLSet top = body.copy().subtract(body.copy().apply(reuseDep.copy().toMap())).simplify();
    final ISLSet bottom = body.copy().subtract(body.copy().apply(reuseDep.copy().toMap().reverse())).simplify();
    final CaseExpression writeCaseExpr = AlphaUserFactory.createCaseExpression();
    ISLSet coveredDomain = ISLSet.buildEmpty(body.copy().apply(writeMaff.copy().toMap()).getSpace());
    List<ISLBasicSet> _basicSets = top.getBasicSets();
    for (final ISLBasicSet basicFacet : _basicSets) {
      {
        final ISLSet facet = basicFacet.copy().toSet();
        final ISLMap shadowProject = writeMaff.copy().toMap().intersectDomain(facet.copy());
        final ISLSet shadow = facet.copy().apply(shadowProject.copy()).subtract(coveredDomain.copy());
        coveredDomain = coveredDomain.union(shadow.copy());
        final VariableExpression readExpr = AlphaUserFactory.createVariableExpression(reductionVar);
        AlphaExpression dependenceExpr = null;
        boolean _isSingleValued = shadowProject.copy().reverse().isSingleValued();
        if (_isSingleValued) {
          dependenceExpr = AlphaUserFactory.createDependenceExpression(
            ISLUtil.toMultiAff(shadowProject.copy().reverse()), readExpr);
        } else {
          dependenceExpr = AlphaUserFactory.createReduceExpression(
            reduce.getOperator(), 
            writeMaff.copy(), 
            AlphaUserFactory.createRestrictExpression(
              top.copy(), readExpr));
        }
        EList<AlphaExpression> _exprs = writeCaseExpr.getExprs();
        RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(shadow, dependenceExpr);
        _exprs.add(_createRestrictExpression);
      }
    }
    EcoreUtil.replace(reduce, writeCaseExpr);
    final CaseExpression readCaseExpr = AlphaUserFactory.createCaseExpression();
    final DependenceExpression selfDepExpr = AlphaUserFactory.createDependenceExpression(
      reuseDep.copy(), 
      AlphaUserFactory.createVariableExpression(reductionVar));
    EList<AlphaExpression> _exprs = readCaseExpr.getExprs();
    RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(
      bottom.copy(), 
      EcoreUtil.<AlphaExpression>copy(coreExpr));
    _exprs.add(_createRestrictExpression);
    EList<AlphaExpression> _exprs_1 = readCaseExpr.getExprs();
    RestrictExpression _createRestrictExpression_1 = AlphaUserFactory.createRestrictExpression(
      body.copy().subtract(bottom.copy()), 
      AlphaUserFactory.createBinaryExpression(
        AlphaOperatorUtil.reductionOPtoBinaryOP(reduce.getOperator()), 
        EcoreUtil.<AlphaExpression>copy(coreExpr), selfDepExpr));
    _exprs_1.add(_createRestrictExpression_1);
    final StandardEquation standardEq = AlphaUserFactory.createStandardEquation(reductionVar, readCaseExpr);
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(standardEq);
    AlphaInternalStateConstructor.recomputeContextDomain(sys);
  }

  private static void checkArguments(final AbstractReduceExpression reduce, final Iterable<ISLMultiAff> reuseDeps, final String newName) {
    final ISLMultiAff writeMaff = reduce.getProjectionExpr().getISLMultiAff();
    final ISLSet nullSpace = ISLUtil.nullSpace(writeMaff.copy());
    final Function1<ISLMultiAff, ISLPoint> _function = (ISLMultiAff dep) -> {
      return dep.copy().toMap().deltas().samplePoint();
    };
    final Iterable<ISLPoint> reuseVectors = IterableExtensions.<ISLMultiAff, ISLPoint>map(reuseDeps, _function);
    final Function1<ISLPoint, Boolean> _function_1 = (ISLPoint vector) -> {
      return Boolean.valueOf(vector.copy().toSet().isSubset(nullSpace.copy()));
    };
    boolean _forall = IterableExtensions.<ISLPoint>forall(reuseVectors, _function_1);
    boolean _not = (!_forall);
    if (_not) {
      throw new IllegalArgumentException(((("[SerializeReduction] Reuse dependences: " + reuseDeps) + 
        "\ndo not all reside in the nullspace of the projection function: ") + reduce));
    }
    final int dimensionality = ISLUtil.dimensionality(ISLUtil.getSpan(reuseVectors));
    int _size = IterableExtensions.size(reuseVectors);
    boolean _lessThan = (dimensionality < _size);
    if (_lessThan) {
      throw new IllegalArgumentException((("[SerializeReduction] Reuse dependences: " + reuseDeps) + 
        "\ndo not form a linearly independent set."));
    }
    AlphaSystem sys = AlphaUtil.getContainerSystem(reduce);
    if ((sys == null)) {
      throw new IllegalArgumentException("[SerializeReduction] Reduction Expression has no containing system.");
    }
    Variable _variable = sys.getVariable(newName);
    boolean _tripleNotEquals = (_variable != null);
    if (_tripleNotEquals) {
      throw new IllegalArgumentException((("[SerializeReduction] Variable with name " + newName) + " already exists in the system."));
    }
  }
}
