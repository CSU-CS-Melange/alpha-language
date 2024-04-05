package scratch;

import alpha.codegen.Polynomial;
import alpha.codegen.Program;
import alpha.codegen.constructor.WriteCProgram;
import alpha.codegen.factory.Factory;
import alpha.codegen.polynomial.PolynomialPrinter;
import alpha.codegen.show.WriteC;
import alpha.codegen.util.CodegenUtil;
import alpha.codegen.util.MemoryUtils;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.transformation.Normalize;
import alpha.model.util.AShow;
import alpha.targetmapping.TargetMapping;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLPWQPolynomial;
import fr.irisa.cairn.jnimap.isl.ISLPoint;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLVal;
import fr.irisa.cairn.jnimap.isl.ISL_FORMAT;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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

  public static void old3(final String[] args) {
    final ISLSet domain = CodegenUtil.toSet("[N,M]->{[i,j]: 0<=i<=j<N }");
    final ISLMap map = CodegenUtil.toMap("[N,M]->{[i,j]->[i,j]}");
    final ISLSet M = CodegenUtil.toSet("[N,M,i,j]->{[i\',j\']: 0<=i\'<=j\'<N and 0<=i<=j<N and i\'<=i and j\'<=j}");
    final ISLSet allocationDomain = CodegenUtil.appli(domain, map);
    final Polynomial polynomial = Factory.createPolynomial(MemoryUtils.card(allocationDomain));
    final Polynomial P = Factory.createPolynomial(MemoryUtils.card(M));
    InputOutput.<CharSequence>println(PolynomialPrinter.print(P, "..."));
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include<stdio.h>");
    _builder.newLine();
    _builder.append("#include<stdlib.h>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("int main(int argc, char** argv) {");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long N = atoi(argv[0]);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("long M = atoi(argv[1]);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    String _cString = polynomial.toCString("X");
    _builder.append(_cString, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("float* X = (float*)malloc(sizeof(float) * _card_X);");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("#define X(");
    String _join = IterableExtensions.join(domain.getIndexNames(), ",");
    _builder.append(_join, "\t");
    _builder.append(") X[");
    String _linearAccessFunction = Scratch.linearAccessFunction(domain);
    _builder.append(_linearAccessFunction, "\t");
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    final String lines = _builder.toString();
    InputOutput.<String>println(lines);
  }

  public static String linearAccessFunction(final ISLSet set) {
    InputOutput.println();
    return "...";
  }
}
