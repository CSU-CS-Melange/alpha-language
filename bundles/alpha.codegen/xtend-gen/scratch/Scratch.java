package scratch;

import alpha.codegen.Polynomial;
import alpha.codegen.Program;
import alpha.codegen.constructor.WriteCProgram;
import alpha.codegen.polynomial.ConstructPolynomial;
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
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import java.io.File;
import java.io.FileWriter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Scratch {
  public static void main(final String[] args) {
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

  public static void old(final String[] args) {
    try {
      final ISLSet domain = CodegenUtil.toSet("[N,M]->{[i,j]: 0<=i<=j<N }");
      final ISLMap map = CodegenUtil.toMap("[N,M]->{[i,j]->[i,j]}");
      final ISLSet M = CodegenUtil.toSet("[N,M,i,j]->{[i\',j\']: 0<=i\'<=j\'<N and 0<=i<=j<N and i\'<=i and j\'<=j}");
      final ISLSet allocationDomain = CodegenUtil.appli(domain, map);
      final Polynomial polynomial = ConstructPolynomial.toPolynomial(MemoryUtils.card(allocationDomain));
      final Polynomial P = ConstructPolynomial.toPolynomial(MemoryUtils.card(M));
      InputOutput.<String>println(PolynomialPrinter.print(P, "..."));
      try (final FileWriter fw = new Function0<FileWriter>() {
        @Override
        public FileWriter apply() {
          try {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("/Users/lw/tmp/scratch/main.c");
            File _file = new File(_builder.toString());
            return new FileWriter(_file, false);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      }.apply()) {
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
        fw.write(lines);
        fw.close();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }

  public static String linearAccessFunction(final ISLSet set) {
    String _xblockexpression = null;
    {
      InputOutput.println();
      _xblockexpression = "...";
    }
    return _xblockexpression;
  }
}
