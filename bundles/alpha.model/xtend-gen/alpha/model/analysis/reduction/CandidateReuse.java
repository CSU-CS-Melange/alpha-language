package alpha.model.analysis.reduction;

import alpha.model.AbstractReduceExpression;
import alpha.model.transformation.reduction.SimplifyingReductions;
import alpha.model.util.AffineFunctionOperations;
import alpha.model.util.CommonExtensions;
import alpha.model.util.DomainOperations;
import alpha.model.util.Face;
import alpha.model.util.ISLUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLBasicSet;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class CandidateReuse {
  public static boolean DEBUG = false;

  private static void debug(final String msg) {
    if (CandidateReuse.DEBUG) {
      InputOutput.<String>println(("[CandidateReuse] " + msg));
    }
  }

  private AbstractReduceExpression are;

  private ShareSpaceAnalysisResult SSAR;

  private long[] reuseVectorWithIdenticalAnswers;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private ISLSet decompositionDomain;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<long[]> vectors;

  @Accessors(AccessorType.PUBLIC_GETTER)
  private boolean hasIdenticalAnswers;

  public CandidateReuse(final AbstractReduceExpression are) {
    this(are, ShareSpaceAnalysis.apply(are));
  }

  public CandidateReuse(final AbstractReduceExpression are, final ShareSpaceAnalysisResult SSAR) {
    this.are = are;
    this.SSAR = SSAR;
    LinkedList<long[]> _linkedList = new LinkedList<long[]>();
    this.vectors = _linkedList;
    this.hasIdenticalAnswers = false;
    this.generateCandidateReuseVectors();
  }

  /**
   * Returns the reuse vector spanning the dimension along which identical
   * answers are computed
   */
  public ISLMultiAff identicalAnswerBasis() {
    if ((this.reuseVectorWithIdenticalAnswers == null)) {
      return null;
    }
    final ISLSpace space = this.are.getBody().getContextDomain().copy().toIdentityMap().getSpace();
    final int nbParams = space.dim(ISLDimType.isl_dim_param);
    final Function1<Integer, Long> _function = (Integer it) -> {
      return Long.valueOf(0L);
    };
    final Iterable<Long> zeros = IterableExtensions.<Integer, Long>map(new ExclusiveRange(0, nbParams, true), _function);
    Iterable<Long> _plus = Iterables.<Long>concat(zeros, ((Iterable<? extends Long>)Conversions.doWrapArray(this.reuseVectorWithIdenticalAnswers)));
    final ISLMultiAff rho = AffineFunctionOperations.createUniformFunction(space, ((long[])Conversions.unwrapArray(_plus, long.class)));
    return rho;
  }

  /**
   * Creates a list of ISLMultiAff that are valid reuse vectors given the share space.
   * Exposed to be used by SimplifyingReductionExploration.
   */
  private void generateCandidateReuseVectors() {
    final long[][] areSS = this.SSAR.getShareSpace(this.are.getBody());
    if ((areSS == null)) {
      return;
    }
    final ISLBasicSet reuseSpace = DomainOperations.toBasicSetFromKernel(areSS, this.are.getBody().getContextDomain().getSpace());
    final Face face = this.are.getFacet();
    String _string = face.toLinearSpace().toString();
    String _plus = ("(candidateReuse) Lp = " + _string);
    CandidateReuse.debug(_plus);
    final List<Face> facets = IterableExtensions.<Face>toList(face.generateChildren());
    int _size = facets.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return;
    }
    final List<ArrayList<Face.Label>> labelings = IterableExtensions.<ArrayList<Face.Label>>toList(Face.enumerateAllPossibleLabelings(facets.size(), true));
    final Function1<ArrayList<Face.Label>, Pair<Face.Label[], ISLBasicSet>> _function = (ArrayList<Face.Label> l) -> {
      return face.getLabelingDomain(((Face.Label[])Conversions.unwrapArray(l, Face.Label.class)));
    };
    final Function1<Pair<Face.Label[], ISLBasicSet>, Boolean> _function_1 = (Pair<Face.Label[], ISLBasicSet> ld) -> {
      return Boolean.valueOf(ISLUtil.isTrivial(ld.getValue()));
    };
    final Function1<Pair<Face.Label[], ISLBasicSet>, Pair<Face.Label[], ISLBasicSet>> _function_2 = (Pair<Face.Label[], ISLBasicSet> ld) -> {
      Face.Label[] _key = ld.getKey();
      ISLBasicSet _intersect = ld.getValue().intersect(reuseSpace.copy());
      return Pair.<Face.Label[], ISLBasicSet>of(_key, _intersect);
    };
    final Function1<Pair<Face.Label[], ISLBasicSet>, Boolean> _function_3 = (Pair<Face.Label[], ISLBasicSet> ld) -> {
      return Boolean.valueOf(ISLUtil.isTrivial(ld.getValue()));
    };
    final List<Pair<Face.Label[], ISLBasicSet>> labelingInducingDomains = IterableExtensions.<Pair<Face.Label[], ISLBasicSet>>toList(IterableExtensions.<Pair<Face.Label[], ISLBasicSet>>reject(IterableExtensions.<Pair<Face.Label[], ISLBasicSet>, Pair<Face.Label[], ISLBasicSet>>map(IterableExtensions.<Pair<Face.Label[], ISLBasicSet>>reject(ListExtensions.<ArrayList<Face.Label>, Pair<Face.Label[], ISLBasicSet>>map(labelings, _function), _function_1), _function_2), _function_3));
    final Function1<Pair<Face.Label[], ISLBasicSet>, Pair<Face.Label[], long[]>> _function_4 = (Pair<Face.Label[], ISLBasicSet> ld) -> {
      Face.Label[] _key = ld.getKey();
      long[] _integerPointClosestToOrigin = ISLUtil.integerPointClosestToOrigin(ld.getValue());
      return Pair.<Face.Label[], long[]>of(_key, _integerPointClosestToOrigin);
    };
    final List<Pair<Face.Label[], long[]>> candidateReuseVectors = ListExtensions.<Pair<Face.Label[], ISLBasicSet>, Pair<Face.Label[], long[]>>map(labelingInducingDomains, _function_4);
    final Function1<Pair<Face.Label[], long[]>, Boolean> _function_5 = (Pair<Face.Label[], long[]> lv) -> {
      return Boolean.valueOf(SimplifyingReductions.testLegality(this.are, lv.getValue()));
    };
    final Iterable<Pair<Face.Label[], long[]>> validReuseVectors = IterableExtensions.<Pair<Face.Label[], long[]>>filter(candidateReuseVectors, _function_5);
    if (CandidateReuse.DEBUG) {
      for (final Face f : facets) {
        int _indexOf = facets.indexOf(f);
        String _plus_1 = ("facet-" + Integer.valueOf(_indexOf));
        String _plus_2 = (_plus_1 + ": ");
        ISLBasicSet _basicSet = f.toBasicSet();
        String _plus_3 = (_plus_2 + _basicSet);
        CandidateReuse.debug(_plus_3);
      }
    }
    for (final Pair<Face.Label[], long[]> labelingAndReuse : validReuseVectors) {
      {
        final Face.Label[] labeling = labelingAndReuse.getKey();
        final long[] reuseVector = labelingAndReuse.getValue();
        String _string_1 = ((List<Face.Label>)Conversions.doWrapArray(labeling)).toString();
        String _plus_4 = ("labeling " + _string_1);
        String _plus_5 = (_plus_4 + " induced by ");
        String _string_2 = ((List<Long>)Conversions.doWrapArray(reuseVector)).toString();
        String _plus_6 = (_plus_5 + _string_2);
        CandidateReuse.debug(_plus_6);
        this.decompositionDomain = CandidateReuse.allZeroNonBoundariesDecomposition(labeling, ((Face[])Conversions.unwrapArray(facets, Face.class)), this.are.getProjection());
        boolean _isEmpty = this.decompositionDomain.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          CandidateReuse.debug("results in identical answers");
          this.hasIdenticalAnswers = true;
          this.reuseVectorWithIdenticalAnswers = reuseVector;
          return;
        }
        this.vectors.add(reuseVector);
      }
    }
    final Function1<Pair<Face.Label[], long[]>, long[]> _function_6 = (Pair<Face.Label[], long[]> lv) -> {
      return lv.getValue();
    };
    Iterables.<long[]>addAll(this.vectors, IterableExtensions.<Pair<Face.Label[], long[]>, long[]>map(validReuseVectors, _function_6));
  }

  /**
   * Returns true if all POS and NEG faces can be simultaneously transformed into strong boundaries
   * 
   * A face is a weak boundary if its linear space has a non-trivial intersection with the accumulation
   * space. A weak boundary can be made into a strong boundary with an appropriate choice of reduction
   * decomposition. Multiple weak boundary face can simultaneously be made into strong boundaries if the
   * combined intersection of their linear spaces with the accumulation space is at least 1-dimensional.
   */
  public static ISLSet allZeroNonBoundariesDecomposition(final Face.Label[] labeling, final Face[] facets, final ISLMultiAff fp) {
    final ISLSet accumulationSpace = ISLUtil.nullSpace(fp);
    final Function1<Pair<Face, Face.Label>, Boolean> _function = (Pair<Face, Face.Label> fl) -> {
      Face.Label _value = fl.getValue();
      return Boolean.valueOf(Objects.equal(_value, Face.Label.ZERO));
    };
    final Function1<Pair<Face, Face.Label>, Face> _function_1 = (Pair<Face, Face.Label> fl) -> {
      return fl.getKey();
    };
    final Iterable<Face> nonZeroFacets = IterableExtensions.<Pair<Face, Face.Label>, Face>map(IterableExtensions.<Pair<Face, Face.Label>>reject(CommonExtensions.<Face, Face.Label>zipWith(((Iterable<Face>)Conversions.doWrapArray(facets)), ((Iterable<Face.Label>)Conversions.doWrapArray(labeling))), _function), _function_1);
    final Function1<Face, Boolean> _function_2 = (Face f) -> {
      Face.Boundary _boundaryLabel = f.boundaryLabel(accumulationSpace);
      return Boolean.valueOf(Objects.equal(_boundaryLabel, Face.Boundary.WEAK));
    };
    final Iterable<Face> weakNonZeroFacets = IterableExtensions.<Face>filter(nonZeroFacets, _function_2);
    CandidateReuse.debug("---");
    CandidateReuse.debug(("accumulation " + accumulationSpace));
    CandidateReuse.debug("---");
    final Consumer<Face> _function_3 = (Face f) -> {
      ISLBasicSet _linearSpace = f.toLinearSpace();
      String _plus = ((("facet-" + f) + " Lp = ") + _linearSpace);
      CandidateReuse.debug(_plus);
    };
    ((List<Face>)Conversions.doWrapArray(facets)).forEach(_function_3);
    CandidateReuse.debug("---");
    final Consumer<Pair<Face, Face.Label>> _function_4 = (Pair<Face, Face.Label> fl) -> {
      final Face f = fl.getKey();
      final Face.Label l = fl.getValue();
      final Face.Boundary b = f.boundaryLabel(accumulationSpace);
      final boolean flag = (Objects.equal(b, Face.Boundary.WEAK) && (!Objects.equal(l, Face.Label.ZERO)));
      String _xifexpression = null;
      if (flag) {
        _xifexpression = "   <-- potential";
      } else {
        _xifexpression = "";
      }
      String _plus = (((((("facet-" + f) + " ") + b) + " ") + l) + _xifexpression);
      CandidateReuse.debug(_plus);
    };
    CommonExtensions.<Face, Face.Label>zipWith(((Iterable<Face>)Conversions.doWrapArray(facets)), ((Iterable<Face.Label>)Conversions.doWrapArray(labeling))).forEach(_function_4);
    CandidateReuse.debug("---");
    final ISLSet universe = ISLSet.buildUniverse(fp.getSpace().domain());
    final Function1<Face, ISLSet> _function_5 = (Face it) -> {
      return it.toLinearSpace().toSet();
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_6 = (ISLSet ret, ISLSet lp) -> {
      return ret.intersect(lp);
    };
    final ISLSet commonWeakSpace = IterableExtensions.<ISLSet, ISLSet>fold(IterableExtensions.<Face, ISLSet>map(weakNonZeroFacets, _function_5), universe.copy(), _function_6);
    final ISLSet decompositionDomain = commonWeakSpace.copy().intersect(accumulationSpace.copy());
    int _dimensionality = ISLUtil.dimensionality(decompositionDomain);
    boolean _greaterThan = (_dimensionality > 0);
    if (_greaterThan) {
      return decompositionDomain;
    }
    return ISLSet.buildEmpty(universe.getSpace());
  }

  @Pure
  public ISLSet getDecompositionDomain() {
    return this.decompositionDomain;
  }

  @Pure
  public List<long[]> getVectors() {
    return this.vectors;
  }

  @Pure
  public boolean isHasIdenticalAnswers() {
    return this.hasIdenticalAnswers;
  }
}
