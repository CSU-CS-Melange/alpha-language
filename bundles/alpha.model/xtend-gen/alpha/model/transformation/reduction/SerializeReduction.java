package alpha.model.transformation.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.AlphaExpression;
import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaSystem;
import alpha.model.BINARY_OP;
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
import alpha.model.util.Face;
import alpha.model.util.FaceLattice;
import alpha.model.util.ISLUtil;
import alpha.model.util.JavaUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.Functions.Function3;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Serializes a reduction along given reuse function(s).
 * 
 * This will modify the container system of the given ReduceExpression.
 * 
 * This class is not given enough information to automatically decompose
 * reductions, so it must serialize all dimensions of the reduction at once.
 * Users can manually apply DecomposeReduction beforehand if they only wish to
 * partially serialize the reduction.
 */
@SuppressWarnings("all")
public class SerializeReduction {
  /**
   * Applies a 1D serialization. Can only be used on reductions of rank 1.
   */
  public static void apply(final AbstractReduceExpression are, final ISLMultiAff reuseDep) {
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final Variable writeVar = ((StandardEquation) _containerEquation).getVariable();
    Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
    AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(are);
    String _name = writeVar.getName();
    String _plus = (_name + "_reduction");
    final String newName = _duplicateNameResolver.apply(_containerSystem, _plus, 
      "_");
    SerializeReduction.apply(are, reuseDep, newName);
  }

  /**
   * Serializes a reduction using an arbitrary set of basis vectors as reuse dependences.
   * This is not guaranteed to be a 'good' serialization, but it will certainly be valid.
   */
  public static void applyAuto(final AbstractReduceExpression are) {
    ISLSet nullSpace = ISLUtil.nullSpace(are.getProjectionExpr().getISLMultiAff().copy());
    final Function1<ISLPoint, ISLMultiAff> _function = (ISLPoint vec) -> {
      return ISLUtil.buildTranslationMaff(vec);
    };
    SerializeReduction.applySequential(are, ListExtensions.<ISLPoint, ISLMultiAff>map(ISLUtil.getBasisVectors(nullSpace), _function));
  }

  public static void apply(final AbstractReduceExpression are, final ISLMultiAff reuseDep, final String newName) {
    SerializeReduction.checkArguments(are, Collections.<ISLMultiAff>unmodifiableList(CollectionLiterals.<ISLMultiAff>newArrayList(reuseDep)), newName);
    SerializeReduction.serialize(are, reuseDep, newName);
  }

  /**
   * Applies a series of reductions, creating a new temporary variable for each.
   * Avoids a potentially exponential number of domains, at the cost of
   * potential schedule bloat when fed to FoutrierScheduler
   */
  public static void applySequential(final AbstractReduceExpression are, final Iterable<ISLMultiAff> partialReuseDeps) {
    SerializeReduction.checkArguments(are, partialReuseDeps, "");
    final Function1<ISLMultiAff, ISLMultiAff> _function = (ISLMultiAff a) -> {
      return a;
    };
    Iterable<ISLMultiAff> reuseDeps = IterableExtensions.<ISLMultiAff, ISLMultiAff>map(partialReuseDeps, _function);
    ISLSet nullSpace = ISLUtil.nullSpace(are.getProjectionExpr().getISLMultiAff().copy());
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
    Equation _containerEquation = AlphaUtil.getContainerEquation(are);
    final Variable writeVar = ((StandardEquation) _containerEquation).getVariable();
    for (int i = 0; (i < (((Object[])Conversions.unwrapArray(reuseDeps, Object.class)).length - 1)); i++) {
      {
        Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
        AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(are);
        String _name = writeVar.getName();
        String _plus_1 = (_name + "_reduction");
        final String newName = _duplicateNameResolver.apply(_containerSystem, _plus_1, 
          Integer.valueOf(i).toString());
        final ISLMultiAff writeMaff = are.getProjectionExpr().getISLMultiAff();
        final Iterable<ISLMultiAff> _converted_reuseDeps = (Iterable<ISLMultiAff>)reuseDeps;
        final ISLMultiAff reuseDep = ((ISLMultiAff[])Conversions.unwrapArray(_converted_reuseDeps, ISLMultiAff.class))[i];
        final ISLMultiAff f1 = ISLUtil.buildRejectionMaff(reuseDep.copy().toMap().deltas().samplePoint());
        final ISLMultiAff f2 = ISLUtil.toMultiAff(writeMaff.copy().toMap().applyDomain(f1.copy().toMap()));
        ReductionDecomposition.apply(are, f1, f2);
        AlphaExpression _body = are.getBody();
        SerializeReduction.serialize(((AbstractReduceExpression) _body), reuseDep, newName);
      }
    }
    Function3<AlphaSystem, String, String, String> _duplicateNameResolver = AlphaUtil.duplicateNameResolver();
    AlphaSystem _containerSystem = AlphaUtil.getContainerSystem(are);
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
    SerializeReduction.serialize(are, ((ISLMultiAff[])Conversions.unwrapArray(_converted_reuseDeps_1, ISLMultiAff.class))[_minus], newName);
  }

  public static void applyOneShot(final AbstractReduceExpression are, final ISLMultiAff rho, final String newName) {
    SerializeReduction.serializeOneShot(are, rho, newName);
  }

  /**
   * Serialize a reduction using only one accumulation vector
   * May have an exponential number of domains
   * But avoids schedule bloat from having more variables than needed
   */
  private static void serializeOneShot(final AbstractReduceExpression are, final ISLMultiAff rho, final String newName) {
    final ISLSet body = are.getBody().getContextDomain();
    final ISLMultiAff writeMaff = are.getProjectionExpr().getISLMultiAff();
    AlphaExpression coreExpr = are.getBody();
    if ((coreExpr instanceof RestrictExpression)) {
      coreExpr = ((RestrictExpression)coreExpr).getExpr();
    }
    AlphaSystem sys = AlphaUtil.getContainerSystem(are);
    final Variable reductionVar = AlphaUserFactory.createVariable(newName, body.copy());
    sys.getLocals().add(reductionVar);
    final ISLSet basin = body.copy().intersect(body.copy().apply(rho.copy().toMap().reverse()));
    final ISLSet top = body.copy().subtract(basin.copy()).simplify();
    final ISLSet bottom = body.copy().subtract(body.copy().apply(rho.copy().toMap())).simplify();
    final FaceLattice lattice = FaceLattice.create(body.getBasicSetAt(0).copy());
    final ISLSet rhoProjPreimage = rho.copy().toMap().deltas().preimage(
      ISLUtil.buildProjectionMaff(rho.copy().toMap().deltas().samplePoint()));
    int _dim = body.dim(ISLDimType.isl_dim_set);
    int _dimensionality = ISLUtil.dimensionality(ISLUtil.nullSpace(writeMaff.copy()));
    final int nExtraDims = (_dim - _dimensionality);
    final Function1<Face, ISLSet> _function = (Face edge) -> {
      return edge.toBasicSet().toSet();
    };
    final Function1<ISLSet, Boolean> _function_1 = (ISLSet set) -> {
      return Boolean.valueOf(set.copy().isSubset(top.copy()));
    };
    final Function1<ISLSet, ISLSet> _function_2 = (ISLSet set) -> {
      return ISLUtil.getSpan(ISLUtil.getBasisVectors(set)).intersect(
        ISLUtil.nullSpace(writeMaff.copy())).intersect(
        rhoProjPreimage.copy());
    };
    final Function1<ISLSet, Boolean> _function_3 = (ISLSet set) -> {
      boolean _isEmpty = set.isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    final Function1<ISLSet, ISLPoint> _function_4 = (ISLSet set) -> {
      return set.samplePoint();
    };
    final Iterable<ISLPoint> edgeFlowVecs = IterableExtensions.<ISLSet, ISLPoint>map(IterableExtensions.<ISLSet>filter(IterableExtensions.<ISLSet, ISLSet>map(IterableExtensions.<ISLSet>filter(ListExtensions.<Face, ISLSet>map(lattice.getFaces((1 + nExtraDims)), _function), _function_1), _function_2), _function_3), _function_4);
    ISLSet peak = top.copy();
    ArrayList<ISLMap> infoFlowMaps = new ArrayList<ISLMap>();
    for (final ISLPoint vec : edgeFlowVecs) {
      {
        final ISLSet accumulatedPoints = top.copy().intersect(top.copy().apply(rho.copy().toMap().reverse()));
        boolean _isEmpty = accumulatedPoints.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          ISLMap _intersectDomain = ISLUtil.buildTranslationMaff(vec).toMap().intersectDomain(accumulatedPoints.copy());
          infoFlowMaps.add(_intersectDomain);
          peak = peak.subtract(accumulatedPoints);
        }
      }
    }
    ISLMap _intersectDomain = rho.copy().toMap().intersectDomain(basin);
    infoFlowMaps.add(_intersectDomain);
    CaseExpression cases = SerializeReduction.infoFlowToCases(infoFlowMaps, 
      AlphaOperatorUtil.reductionOPtoBinaryOP(are.getOperator()), reductionVar, coreExpr);
    EList<AlphaExpression> _exprs = cases.getExprs();
    RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(bottom, 
      AlphaUtil.<AlphaExpression>copyAE(coreExpr));
    _exprs.add(_createRestrictExpression);
    EList<Equation> _equations = AlphaUtil.getContainerSystemBody(are).getEquations();
    StandardEquation _createStandardEquation = AlphaUserFactory.createStandardEquation(reductionVar, cases);
    _equations.add(_createStandardEquation);
    EcoreUtil.replace(are, 
      AlphaUserFactory.createReduceExpression(
        are.getOperator(), writeMaff, 
        AlphaUserFactory.createRestrictExpression(peak, 
          AlphaUserFactory.createVariableExpression(reductionVar))));
    AlphaInternalStateConstructor.recomputeContextDomain(sys);
  }

  /**
   * Takes flow information (domains plus the Maff along which they accumulate)
   * and turns it into dependences
   * Because the ranges of flow maps can intersect, the number of domains one needs to consider
   * becomes exponential wrt. dimension
   * 
   * O(2^d) for realistic cases
   * O(2^(2^d)) worst case
   */
  private static CaseExpression infoFlowToCases(final Iterable<ISLMap> infoFlowMaps, final BINARY_OP op, final Variable v, final AlphaExpression coreExpr) {
    final CaseExpression cases = AlphaUserFactory.createCaseExpression();
    final Consumer<Set<ISLMap>> _function = (Set<ISLMap> wantMaps) -> {
      final Function1<ISLMap, Boolean> _function_1 = (ISLMap map) -> {
        boolean _contains = wantMaps.contains(map);
        return Boolean.valueOf((!_contains));
      };
      final Iterable<ISLMap> unwantMaps = IterableExtensions.<ISLMap>filter(infoFlowMaps, _function_1);
      int _size = wantMaps.size();
      boolean _lessThan = (_size < 1);
      if (_lessThan) {
        return;
      }
      final Function1<ISLMap, ISLSet> _function_2 = (ISLMap map) -> {
        return map.getRange();
      };
      final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet a, ISLSet b) -> {
        return a.copy().intersect(b.copy());
      };
      ISLSet range = IterableExtensions.<ISLSet>reduce(IterableExtensions.<ISLMap, ISLSet>map(wantMaps, _function_2), _function_3);
      boolean _isEmpty = IterableExtensions.isEmpty(unwantMaps);
      boolean _not = (!_isEmpty);
      if (_not) {
        final Function1<ISLMap, ISLSet> _function_4 = (ISLMap map) -> {
          return map.getRange();
        };
        final Function2<ISLSet, ISLSet, ISLSet> _function_5 = (ISLSet a, ISLSet b) -> {
          return a.copy().union(b.copy());
        };
        range = range.subtract(
          IterableExtensions.<ISLSet>reduce(IterableExtensions.<ISLMap, ISLSet>map(unwantMaps, _function_4), _function_5));
      }
      boolean _isEmpty_1 = range.isEmpty();
      if (_isEmpty_1) {
        return;
      }
      EList<AlphaExpression> _exprs = cases.getExprs();
      final Function1<ISLMap, DependenceExpression> _function_6 = (ISLMap map) -> {
        return AlphaUserFactory.createDependenceExpression(
          ISLUtil.toMultiAff(map.copy().reverse()), 
          AlphaUserFactory.createVariableExpression(v));
      };
      Iterable<DependenceExpression> _map = IterableExtensions.<ISLMap, DependenceExpression>map(wantMaps, _function_6);
      AlphaExpression _copyAE = AlphaUtil.<AlphaExpression>copyAE(coreExpr);
      Iterable<AlphaExpression> _plus = Iterables.<AlphaExpression>concat(_map, Collections.<AlphaExpression>unmodifiableList(CollectionLiterals.<AlphaExpression>newArrayList(_copyAE)));
      RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(range, 
        AlphaUtil.createNaryExpression(op, _plus));
      _exprs.add(_createRestrictExpression);
    };
    JavaUtil.<ISLMap>powerSet(IterableExtensions.<ISLMap>toSet(infoFlowMaps)).forEach(_function);
    return cases;
  }

  /**
   * The main serialize method, which all public-facing methods eventually call.
   */
  private static void serialize(final AbstractReduceExpression are, final ISLMultiAff reuseDep, final String newName) {
    AlphaSystem sys = AlphaUtil.getContainerSystem(are);
    final SystemBody systemBody = AlphaUtil.getContainerSystemBody(are);
    final ISLSet body = are.getBody().getContextDomain();
    final ISLMultiAff writeMaff = are.getProjectionExpr().getISLMultiAff();
    AlphaExpression coreExpr = are.getBody();
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
            are.getOperator(), 
            writeMaff.copy(), 
            AlphaUserFactory.createRestrictExpression(
              top.copy(), readExpr));
        }
        EList<AlphaExpression> _exprs = writeCaseExpr.getExprs();
        RestrictExpression _createRestrictExpression = AlphaUserFactory.createRestrictExpression(shadow, dependenceExpr);
        _exprs.add(_createRestrictExpression);
      }
    }
    EcoreUtil.replace(are, writeCaseExpr);
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
        AlphaOperatorUtil.reductionOPtoBinaryOP(are.getOperator()), 
        EcoreUtil.<AlphaExpression>copy(coreExpr), selfDepExpr));
    _exprs_1.add(_createRestrictExpression_1);
    final StandardEquation standardEq = AlphaUserFactory.createStandardEquation(reductionVar, readCaseExpr);
    EList<Equation> _equations = systemBody.getEquations();
    _equations.add(standardEq);
    AlphaInternalStateConstructor.recomputeContextDomain(sys);
  }

  /**
   * Sanity check!
   */
  private static void checkArguments(final AbstractReduceExpression are, final Iterable<ISLMultiAff> reuseDeps, final String newName) {
    final ISLMultiAff writeMaff = are.getProjectionExpr().getISLMultiAff();
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
        "\ndo not all reside in the nullspace of the projection function: ") + are));
    }
    final int dimensionality = ISLUtil.dimensionality(ISLUtil.getSpan(reuseVectors));
    int _size = IterableExtensions.size(reuseVectors);
    boolean _lessThan = (dimensionality < _size);
    if (_lessThan) {
      throw new IllegalArgumentException((("[SerializeReduction] Reuse dependences: " + reuseDeps) + 
        "\ndo not form a linearly independent set."));
    }
    int _dimensionality = ISLUtil.dimensionality(nullSpace.copy());
    boolean _lessThan_1 = (dimensionality < _dimensionality);
    if (_lessThan_1) {
      throw new IllegalArgumentException(((("[SerializeReduction] Reuse dependences " + reuseDeps) + 
        " are insufficient to serialize the the given reduction: ") + are));
    }
    AlphaSystem sys = AlphaUtil.getContainerSystem(are);
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
