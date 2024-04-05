package scratch;

import alpha.codegen.Polynomial;
import alpha.codegen.Program;
import alpha.codegen.constructor.WriteCProgram;
import alpha.codegen.factory.Factory;
import alpha.codegen.polynomial.PolynomialPrinter;
import alpha.codegen.show.WriteC;
import alpha.codegen.util.MemoryUtils;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.transformation.Normalize;
import alpha.model.util.AShow;
import alpha.targetmapping.TargetMapping;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Scratch {
  public static ISLSet set(final String str) {
    return ISLSet.buildFromString(ISLContext.getInstance(), str);
  }

  public static ISLVal value(final long value) {
    return ISLVal.buildFromLong(ISLContext.getInstance(), value);
  }

  public static void main(final String[] args) {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/reduceExample.alpha");
      final AlphaSystem system = root.getSystem("reduceExample");
      final TargetMapping tm = AlphaLoader.loadTargetMapping("resources/reduceExample.tm");
      final Program program = WriteCProgram.build(system, tm);
      InputOutput.<String>println(WriteC.<Program>print(program));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static void main2(final String[] args) {
    final ISLSet domain = ISLSet.buildFromString(ISLContext.getInstance(), "[N] -> {[i]: 0<=i<=N}");
    final ISLPWQPolynomial rank = MemoryUtils.rank(domain.copy());
    InputOutput.<ISLPWQPolynomial>println(rank);
    final Polynomial poly = Factory.createPolynomial(rank.copy().coalesce());
    final CharSequence printed = PolynomialPrinter.print(poly);
    InputOutput.<CharSequence>println(printed);
    final CharSequence printed2 = PolynomialPrinter.printMinusOne(poly);
    InputOutput.<CharSequence>println(printed2);
    InputOutput.println();
    InputOutput.<String>println("ISL C printing:");
    InputOutput.<String>println(ISLPWQPolynomial._toString(rank.copy(), ISL_FORMAT.C.ordinal()));
    InputOutput.println();
    InputOutput.<String>println("Out of Bounds Test");
    final ISLPoint point = ISLPoint.buildZero(rank.getDomainSpace()).setCoordinate(ISLDimType.isl_dim_param, 0, Scratch.value((-1))).setCoordinate(ISLDimType.isl_dim_param, 1, Scratch.value((-2)));
    InputOutput.<ISLPoint>println(point);
    InputOutput.<ISLVal>println(rank.eval(point));
  }

  public static void main3(final String[] args) {
    final ISLSet domain = ISLSet.buildFromString(ISLContext.getInstance(), "[N] -> {[i,j]: 0<=j<=2i<=N}");
    final ISLPWQPolynomial rank = MemoryUtils.rank(domain);
    InputOutput.<String>println("Cardinality:");
    InputOutput.<ISLPWQPolynomial>println(rank);
  }

  public static void old2(final String[] args) {
    try {
      final AlphaRoot root = AlphaLoader.loadAlpha("resources/star1d1r.alpha");
      final TargetMapping tm = AlphaLoader.loadTargetMapping("resources/star1d1r.tm");
      EList<AlphaSystem> _systems = root.getSystems();
      for (final AlphaSystem system : _systems) {
        {
          Normalize.apply(system);
          InputOutput.<String>println(AShow.print(system));
          final Program program = WriteCProgram.build(system, tm);
          InputOutput.<String>println(WriteC.<Program>print(program));
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
