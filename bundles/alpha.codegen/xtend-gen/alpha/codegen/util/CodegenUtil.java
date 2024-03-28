package alpha.codegen.util;

import alpha.codegen.BaseVariable;
import alpha.codegen.CodegenFactory;
import alpha.codegen.DataType;
import alpha.codegen.GlobalMacro;
import alpha.codegen.GlobalMemoryMacro;
import alpha.codegen.Include;
import alpha.codegen.MemoryMacro;
import alpha.codegen.StatementMacro;
import alpha.model.AlphaSystem;
import alpha.model.Variable;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLDimType;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class CodegenUtil {
  private static CodegenFactory factory = CodegenFactory.eINSTANCE;

  public static Include toInclude(final String s) {
    Include _xblockexpression = null;
    {
      final Include ret = CodegenUtil.factory.createInclude();
      ret.setName(s);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static GlobalMacro globalMacro(final String left, final String right) {
    GlobalMacro _xblockexpression = null;
    {
      final GlobalMacro ret = CodegenUtil.factory.createGlobalMacro();
      ret.setLeft(left);
      ret.setRight(right);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static MemoryMacro memoryMacro(final String left, final String right) {
    MemoryMacro _xblockexpression = null;
    {
      final MemoryMacro ret = CodegenUtil.factory.createMemoryMacro();
      ret.setLeft(left);
      ret.setRight(right);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static GlobalMemoryMacro globalMemoryMacro(final String left, final String right) {
    GlobalMemoryMacro _xblockexpression = null;
    {
      final GlobalMemoryMacro ret = CodegenUtil.factory.createGlobalMemoryMacro();
      ret.setLeft(left);
      ret.setRight(right);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static StatementMacro statementMacro(final String left, final String right) {
    StatementMacro _xblockexpression = null;
    {
      final StatementMacro ret = CodegenUtil.factory.createStatementMacro();
      ret.setLeft(left);
      ret.setRight(right);
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }

  public static BaseVariable baseVariable(final String name, final DataType type) {
    BaseVariable _xblockexpression = null;
    {
      final BaseVariable cv = CodegenUtil.factory.createBaseVariable();
      cv.setName(name);
      cv.setElemType(type);
      _xblockexpression = cv;
    }
    return _xblockexpression;
  }

  public static List<BaseVariable> paramScalarVariables(final AlphaSystem s) {
    final Function1<String, BaseVariable> _function = (String it) -> {
      return CodegenUtil.baseVariable(it, DataType.LONG);
    };
    return ListExtensions.<String, BaseVariable>map(s.getParameterDomain().getParamNames(), _function);
  }

  public static List<BaseVariable> indexScalarVariables(final Variable v) {
    final Function1<String, BaseVariable> _function = (String it) -> {
      return CodegenUtil.baseVariable(it, DataType.LONG);
    };
    return ListExtensions.<String, BaseVariable>map(v.getDomain().getIndexNames(), _function);
  }

  public static Pair<Integer, Integer> parseSetSizes(final String s) {
    Pair<Integer, Integer> _xblockexpression = null;
    {
      final Pattern params = Pattern.compile("(\\[)(.*?)(\\])");
      Matcher m = params.matcher(s);
      m.find();
      int _length = m.group(2).replaceAll("[^,]", "").length();
      final int numParams = (_length + 1);
      m.find();
      int _length_1 = m.group(2).replaceAll("[^,]", "").length();
      final int numIndices = (_length_1 + 1);
      _xblockexpression = Pair.<Integer, Integer>of(Integer.valueOf(numParams), Integer.valueOf(numIndices));
    }
    return _xblockexpression;
  }

  public static Pair<Integer, Pair<Integer, Integer>> parseMapSizes(final String s) {
    Pair<Integer, Pair<Integer, Integer>> _xblockexpression = null;
    {
      final Pattern params = Pattern.compile("(\\[)(.*?)(\\])");
      Matcher m = params.matcher(s);
      m.find();
      int _length = m.group(2).replaceAll("[^,]", "").length();
      final int numParams = (_length + 1);
      m.find();
      int _length_1 = m.group(2).replaceAll("[^,]", "").length();
      final int numIn = (_length_1 + 1);
      m.find();
      int _length_2 = m.group(2).replaceAll("[^,]", "").length();
      final int numOut = (_length_2 + 1);
      Pair<Integer, Integer> _mappedTo = Pair.<Integer, Integer>of(Integer.valueOf(numIn), Integer.valueOf(numOut));
      _xblockexpression = Pair.<Integer, Pair<Integer, Integer>>of(Integer.valueOf(numParams), _mappedTo);
    }
    return _xblockexpression;
  }

  public static ISLSet toSet(final String s) {
    ISLSet _xblockexpression = null;
    {
      final Pair<Integer, Integer> sizes = CodegenUtil.parseSetSizes(s);
      final Integer l = sizes.getKey();
      final Integer r = sizes.getValue();
      final ISLContext ctx = ISLSpace.allocSetSpace((l).intValue(), (r).intValue()).getContext();
      _xblockexpression = ISLSet.buildFromString(ctx, s);
    }
    return _xblockexpression;
  }

  public static ISLMap toMap(final String s) {
    ISLMap _xblockexpression = null;
    {
      final Pair<Integer, Pair<Integer, Integer>> sizes = CodegenUtil.parseMapSizes(s);
      final Integer l = sizes.getKey();
      final Integer m = sizes.getValue().getKey();
      final Integer r = sizes.getValue().getValue();
      final ISLSpace dSpace = ISLSpace.allocSetSpace((l).intValue(), (m).intValue());
      final ISLSpace rSpace = ISLSpace.allocSetSpace((l).intValue(), (r).intValue());
      final ISLContext ctx = ISLSpace.buildMapSpaceFromDomainAndRange(dSpace, rSpace).getContext();
      _xblockexpression = ISLMap.buildFromString(ctx, s);
    }
    return _xblockexpression;
  }

  public static List<String> names(final ISLSet set) {
    return CodegenUtil.names(set, ISLDimType.isl_dim_out);
  }

  public static List<String> names(final ISLSet set, final ISLDimType dim_type) {
    List<String> _xblockexpression = null;
    {
      final int dim = set.dim(dim_type);
      final Function1<Integer, String> _function = (Integer it) -> {
        return set.getDimName(dim_type, (it).intValue());
      };
      _xblockexpression = IterableExtensions.<String>toList(IterableExtensions.<Integer, String>map(new ExclusiveRange(0, dim, true), _function));
    }
    return _xblockexpression;
  }

  public static ISLSet appli(final ISLSet s, final ISLMap m) {
    return s.copy().apply(m.copy()).<ISLSet>renameIndices(CodegenUtil.names(s));
  }

  public static DataType dataType(final Variable variable) {
    return DataType.FLOAT;
  }
}
