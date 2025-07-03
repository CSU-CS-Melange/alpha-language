package alpha.model.tiler;

import alpha.model.scheduler.Scheduler;
import alpha.model.util.ISLUtil;
import com.google.common.collect.Iterables;
import fr.irisa.cairn.jnimap.isl.ISLAff;
import fr.irisa.cairn.jnimap.isl.ISLSpace;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class WavefrontTiler extends DTiler {
  public WavefrontTiler(final List<Integer> tileSizes, final ISLSpace scheduleSpace, final int startDim, final int endDim) {
    super(tileSizes, scheduleSpace, startDim, endDim);
    final List<ISLAff> affs = ISLUtil.toMultiAff(this.tileMap).getAffs();
    final Function2<ISLAff, ISLAff, ISLAff> _function = (ISLAff a, ISLAff b) -> {
      return a.copy().add(b.copy());
    };
    ISLAff _reduce = IterableExtensions.<ISLAff>reduce(affs.subList(0, ((endDim - startDim) + 1)), _function);
    final Function1<Integer, ISLAff> _function_1 = (Integer dim) -> {
      return affs.get(dim).copy();
    };
    Iterable<ISLAff> _map = IterableExtensions.<Integer, ISLAff>map(new ExclusiveRange(0, (endDim - startDim), true), _function_1);
    Iterable<ISLAff> _plus = Iterables.<ISLAff>concat(Collections.<ISLAff>unmodifiableList(CollectionLiterals.<ISLAff>newArrayList(_reduce)), _map);
    List<ISLAff> _subList = affs.subList(((endDim - startDim) + 1), affs.size());
    final Iterable<ISLAff> newAffs = Iterables.<ISLAff>concat(_plus, _subList);
    this.tileMap = ISLUtil.convertToMultiAff(IterableExtensions.<ISLAff>toList(newAffs)).toMap();
  }

  public WavefrontTiler(final List<Integer> tileSizes, final Scheduler scheduler, final int startDim, final int endDim) {
    this(tileSizes, scheduler.getMaps().getMaps().get(0).getRange().getSpace(), startDim, endDim);
  }
}
