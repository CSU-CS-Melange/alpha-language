package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.List

import static extension alpha.model.util.ISLUtil.*

class WavefrontTiler extends DTiler {
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		super(tileSizes, scheduleSpace, startDim, endDim)
		
		val affs = tileMap.toMultiAff.affs;
		
		//Applies a wavefront transformation to the tile dims
		val newAffs = #[affs.subList(0, endDim-startDim+1)
				.reduce[a, b| a.copy.add(b.copy)]]
			+ (0..<endDim-startDim)
				.map[int dim | affs.get(dim).copy] 
			+ affs.subList(endDim-startDim+1, affs.size)
		
		tileMap = newAffs.toList.convertToMultiAff.toMap
	}
	
	new(List<Integer> tileSizes, Scheduler scheduler, int startDim, int endDim) {
		this(tileSizes, scheduler.getMaps.getMaps.get(0).getRange.getSpace, startDim, endDim)
	}
}