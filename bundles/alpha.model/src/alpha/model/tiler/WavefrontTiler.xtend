package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.List

import static extension alpha.model.util.ISLUtil.*
import fr.irisa.cairn.jnimap.isl.ISLAff
import java.util.ArrayList
import fr.irisa.cairn.jnimap.isl.ISLMap
import alpha.model.util.ISLUtil.Dims

class WavefrontTiler extends DTiler {
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		super(tileSizes, scheduleSpace, startDim, endDim)
		
		val nTiles = endDim-startDim+1
		
		val List<ISLAff> affs = tileMap.toMultiAff.affs;
		
		//Applies a wavefront transformation to the tile dims
		var newAffs = new ArrayList<ISLAff>()
		
		newAffs += affs.subList(0, nTiles)
				.reduce[a, b| a.copy.add(b.copy)]
		
		newAffs += (0..<nTiles-1)
				.map[int dim | affs.get(dim).copy] 
				
		newAffs += affs.subList(nTiles, affs.size)
		
		tileMap = newAffs.toList.convertToMultiAff.toMap
		
		tileMap = tileMap.setDimName(Dims.OUT, 0, "tw")
		
		tileMap = (1..<nTiles).fold(tileMap, 
			[ ISLMap map, i | map.setDimName(Dims.OUT, i, "t"+ i) ]
		)
		
		tileMap = (nTiles..<newAffs.size).fold(tileMap, 
			[ ISLMap map, i | map.setDimName(Dims.OUT, i, "c"+ (i-nTiles)) ]
		)
	}
	
	new(List<Integer> tileSizes, Scheduler scheduler, int startDim, int endDim) {
		this(tileSizes, scheduler.getMaps.getMaps.get(0).getRange.getSpace, startDim, endDim)
	}
}