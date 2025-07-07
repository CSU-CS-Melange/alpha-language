package alpha.codegen.isl;

import alpha.model.util.CommonExtensions;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.barvinok.BarvinokBindings;
import fr.irisa.cairn.jnimap.isl.IISLSingleSpaceSetMethods;
import fr.irisa.cairn.jnimap.isl.ISLConstraint;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMultiPWAff;
import fr.irisa.cairn.jnimap.isl.ISLPWAff;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLQPolynomialPiece;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Helper methods intended for memory allocation and accessing using isl objects.
 */
@SuppressWarnings("all")
public class MemoryUtils {
  /**
   * Returns a piecewise quasi-affine polynomial indicating
   * the number points in the given set (i.e., its cardinality).
   * 
   * @param domain The domain to get the cardinality of. This set is not destroyed ("isl_keep").
   * @returns A piecewise quasi-affine polynomial that represents the cardinality of the given domain.
   */
  public static ISLPWQPolynomial card(final ISLSet domain) {
    return BarvinokBindings.card(domain.copy());
  }

  public static ISLPWQPolynomial boxCard(final ISLSet domain) {
    final ISLMultiPWAff widths = ISLUtil.boundingBoxWidths(domain).toMultiPWAff();
    int _dim = domain.dim(ISLDimType.isl_dim_set);
    final Function1<Integer, ISLPWAff> _function = (Integer dim) -> {
      return widths.getAt((dim).intValue());
    };
    final Function1<ISLPWAff, ISLPWQPolynomial> _function_1 = (ISLPWAff it) -> {
      return ISLUtil.toPWQPolynomial(it);
    };
    final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_2 = (ISLPWQPolynomial a, ISLPWQPolynomial b) -> {
      return a.mul(b);
    };
    ISLPWQPolynomial polynomial = IterableExtensions.<ISLPWQPolynomial>reduce(IterableExtensions.<ISLPWAff, ISLPWQPolynomial>map(IterableExtensions.<Integer, ISLPWAff>map(new ExclusiveRange(0, _dim, true), _function), _function_1), _function_2);
    int _nbPieces = polynomial.getNbPieces();
    boolean _equals = (_nbPieces == 1);
    if (_equals) {
      polynomial = IterableExtensions.<ISLQPolynomialPiece>head(polynomial.getPieces()).getQp().toPWQPolynomial();
    }
    return polynomial;
  }

  /**
   * Returns a piecewise quasi-affine polynomial which can compute the "rank" of a point.
   * The "rank" is defined as the number of points which are lexicographically
   * less than that point.
   * We will compute this by building the set of such points, then using the Barvinok
   * library to compute the cardinality of such a set.
   * This will return a piecewise quasi-affine polynomial.
   * 
   * Note: the polynomial returned may look like it's undefined for the lex min of the given set.
   * However, this is not actually the case.
   * These polynomials are defined over the universe, and will evaluate to zero
   * if the point you're asking for lies outside the domains of each piece.
   * In other words: there is an implicit "default" case that always evaluates to zero
   * if the point being evaluated lies outside all the domains of all the pieces of the polynomial.
   * 
   * For example, the set "[N] -> {[i]: 0 <= i <= N}" (which forms a line from 0 to N)
   * would return the polynomial "[N,i] -> { i : 0 < i <= N }".
   * Per the previous paragraph, this is still valid for i=0,
   * as it falls under the implicit extra case that always evaluates to zero.
   * 
   * The intended use case of this is for determining the index of a point
   * within a linearized array of our set. This produces a 0-based indexing.
   * Per the second paragraph, it may look like this polynomial is not defined for index 0,
   * but it actually is due to the implicit default case always evaluating to 0
   * if the point you're evaluating at is not in any of the domains of the pieces.
   * 
   * @param domain The domain to get the ranking polynomial for. This set is not destroyed ("isl_keep").
   * @returns A piecewise quasi-affine polynomial that can be used to compute the rank of a point within the given domain.
   * @throws IllegalArgumentException if the domain given is null.
   */
  public static ISLPWQPolynomial rank(final ISLSet domain) {
    if ((domain == null)) {
      throw new IllegalArgumentException("The domain provided is null.");
    }
    boolean _isEmpty = domain.isEmpty();
    if (_isEmpty) {
      return ISLPWQPolynomial.buildZero(domain.getSpace());
    }
    List<String> _paramNames = domain.getParamNames();
    List<String> _indexNames = domain.getIndexNames();
    final ArrayList<String> existingNames = CommonExtensions.<String>toArrayList(Iterables.<String>concat(Collections.<List<String>>unmodifiableList(CollectionLiterals.<List<String>>newArrayList(_paramNames, _indexNames))));
    final Function1<Integer, String> _function = (Integer it) -> {
      return ("i" + it);
    };
    final Function1<String, Boolean> _function_1 = (String it) -> {
      return Boolean.valueOf(existingNames.contains(it));
    };
    final List<String> names = IterableExtensions.<String>toList(IterableExtensions.<String>take(IterableExtensions.<String>reject(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, Integer.MAX_VALUE, true), _function), _function_1), domain.getNbIndices()));
    final ISLSet extendedSet = domain.copy().<IISLSingleSpaceSetMethods>moveIndicesToParameters().<ISLSet>addIndices(names);
    int _nbIndices = domain.getNbIndices();
    final Function1<Integer, ISLSet> _function_2 = (Integer i) -> {
      return MemoryUtils.createOrderingForIndex(extendedSet, domain.getNbParams(), (i).intValue());
    };
    final Function2<ISLSet, ISLSet, ISLSet> _function_3 = (ISLSet d1, ISLSet d2) -> {
      return d1.union(d2);
    };
    final ISLSet lessThan = IterableExtensions.<ISLSet, ISLSet>fold(IterableExtensions.<Integer, ISLSet>map(new ExclusiveRange(0, _nbIndices, true), _function_2), ISLSet.buildEmpty(domain.getSpace()), _function_3);
    return MemoryUtils.card(lessThan.intersect(domain.copy()));
  }

  public static ISLPWQPolynomial boxRank(final ISLSet domain) {
    final ISLMultiPWAff widths = ISLUtil.boundingBoxWidths(domain).toMultiPWAff();
    final ISLMultiPWAff mins = domain.copy().lexMinAsPWMultiAff().toMultiPWAff();
    int _dim = domain.dim(ISLDimType.isl_dim_set);
    final Function1<Integer, ISLPWQPolynomial> _function = (Integer dim) -> {
      ISLPWQPolynomial _xblockexpression = null;
      {
        final ISLPWQPolynomial index = ISLQPolynomial.buildVarOnDomain(domain.getSpace().copy(), ISLDimType.isl_dim_set, (dim).intValue()).toPWQPolynomial().sub(ISLUtil.toPWQPolynomial(mins.getAt((dim).intValue())).addDims(ISLDimType.isl_dim_in, domain.dim(ISLDimType.isl_dim_set)));
        int _dim_1 = domain.dim(ISLDimType.isl_dim_set);
        final Function1<Integer, ISLPWAff> _function_1 = (Integer i) -> {
          return widths.getAt((i).intValue()).copy();
        };
        final Function1<ISLPWAff, ISLPWQPolynomial> _function_2 = (ISLPWAff it) -> {
          return ISLUtil.toPWQPolynomial(it);
        };
        final Function1<ISLPWQPolynomial, ISLPWQPolynomial> _function_3 = (ISLPWQPolynomial it) -> {
          return it.addDims(ISLDimType.isl_dim_in, domain.dim(ISLDimType.isl_dim_set));
        };
        final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_4 = (ISLPWQPolynomial a, ISLPWQPolynomial b) -> {
          return a.mul(b);
        };
        _xblockexpression = IterableExtensions.<ISLPWQPolynomial, ISLPWQPolynomial>fold(IterableExtensions.<ISLPWQPolynomial, ISLPWQPolynomial>map(IterableExtensions.<ISLPWAff, ISLPWQPolynomial>map(IterableExtensions.<Integer, ISLPWAff>map(new ExclusiveRange(((dim).intValue() + 1), _dim_1, true), _function_1), _function_2), _function_3), index, _function_4);
      }
      return _xblockexpression;
    };
    final Function2<ISLPWQPolynomial, ISLPWQPolynomial, ISLPWQPolynomial> _function_1 = (ISLPWQPolynomial a, ISLPWQPolynomial b) -> {
      return a.add(b);
    };
    ISLPWQPolynomial polynomial = IterableExtensions.<ISLPWQPolynomial>reduce(IterableExtensions.<Integer, ISLPWQPolynomial>map(new ExclusiveRange(0, _dim, true), _function), _function_1).intersectDomain(domain.copy());
    final Function1<ISLQPolynomialPiece, Boolean> _function_2 = (ISLQPolynomialPiece it) -> {
      return Boolean.valueOf(it.getSet().isEmpty());
    };
    final Iterable<ISLQPolynomialPiece> pieces = IterableExtensions.<ISLQPolynomialPiece>reject(polynomial.getPieces(), _function_2);
    int _size = IterableExtensions.size(pieces);
    boolean _equals = (_size == 1);
    if (_equals) {
      polynomial = IterableExtensions.<ISLQPolynomialPiece>head(pieces).getQp().toPWQPolynomial();
    }
    return polynomial;
  }

  /**
   * Constructs a set that's a copy of the given one, but with added constraints such that
   * index i is less than the parameter for that index,
   * and all indices j<i are equal to their parameter.
   */
  private static ISLSet createOrderingForIndex(final ISLSet domain, final int originalParamCount, final int index) {
    final Function2<ISLSet, Integer, ISLSet> _function = (ISLSet d, Integer i) -> {
      return MemoryUtils.addTotalOrderEquality(d, originalParamCount, (i).intValue());
    };
    return MemoryUtils.addTotalOrderInequality(IterableExtensions.<Integer, ISLSet>fold(new ExclusiveRange(0, index, true), domain.copy(), _function), originalParamCount, index);
  }

  /**
   * Constructs an equality constraint that index i equals the parameter for that index.
   */
  private static ISLSet addTotalOrderEquality(final ISLSet domain, final int originalParamCount, final int index) {
    final ISLConstraint constraint = ISLConstraint.buildEquality(domain.getSpace()).setCoefficient(ISLDimType.isl_dim_param, (originalParamCount + index), 1).setCoefficient(ISLDimType.isl_dim_set, index, (-1));
    return domain.addConstraint(constraint);
  }

  /**
   * Constructs an inequality that index i is less than the parameter for that index.
   */
  private static ISLSet addTotalOrderInequality(final ISLSet domain, final int originalParamCount, final int index) {
    final ISLConstraint constraint = ISLConstraint.buildInequality(domain.getSpace()).setCoefficient(ISLDimType.isl_dim_param, (originalParamCount + index), 1).setCoefficient(ISLDimType.isl_dim_set, index, (-1)).setConstant((-1));
    return domain.addConstraint(constraint);
  }
}
