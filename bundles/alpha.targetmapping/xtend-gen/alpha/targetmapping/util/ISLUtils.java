package alpha.targetmapping.util;

import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLIdentifier;
import fr.irisa.cairn.jnimap.isl.ISLIdentifierList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class ISLUtils {
  public static ISLIdentifierList toIdentifierList(final List<String> iterators, final ISLContext context) {
    ISLIdentifierList _xblockexpression = null;
    {
      ISLIdentifierList ret = ISLIdentifierList.build(context, iterators.size());
      int _size = iterators.size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
      for (final Integer i : _doubleDotLessThan) {
        ret = ret.insert((i).intValue(), ISLIdentifier.alloc(context, iterators.get((i).intValue())));
      }
      _xblockexpression = ret;
    }
    return _xblockexpression;
  }
}
