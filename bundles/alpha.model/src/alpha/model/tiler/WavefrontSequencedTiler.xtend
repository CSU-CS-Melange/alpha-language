package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import alpha.model.util.ISLUtil.Dims
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.ArrayList
import java.util.List

import static extension alpha.model.util.ISLUtil.*

class WavefrontSequencedTiler extends DTiler {
	ISLMap tileDimsMap
	ISLMap iteratorMap
	
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		super(tileSizes, scheduleSpace, startDim, endDim)
		
		val nTiles = endDim-startDim+1
		
		val List<ISLAff> affs = tileMap.toMultiAff.affs;
		
		//Applies a wavefront transformation to the tile dims
		var newAffs = new ArrayList<ISLAff>()
		
		newAffs += affs.subList(0, nTiles)
				.reduce[a, b| a.copy.add(b.copy)]
		
		newAffs += affs.subList(0, nTiles-1)
				
		newAffs += affs.subList(nTiles, nTiles*2)
				.reduce[a, b| a.copy.add(b.copy)]
		
		tileDimsMap = newAffs
			.toList.convertToMultiAff.toMap
		
		iteratorMap = affs.subList(nTiles, affs.size-1)
			.toList.convertToMultiAff.toMap
			
		this.endDim += 1
	}
	
	override ISLUnionMap tileSchedule(ISLUnionMap umap) {
		val sequencingMap = (0..<umap.maps.size).map[
			ISLAff.buildValOnDomain(umap.maps.get(it).getDomain.space.toLocalSpace, it)
				.toMultiAff.toMap
		].toList.convertToUnionMap
		
		var tiledMaps = umap.maps
			.map[tileSchedule(it, sequencingMap)]
			.convertToUnionMap
		
		return tiledMaps
	}
	
	def private ISLMap tileSchedule(ISLMap map, ISLUnionMap sequencingMap) {
		val sequencedMap = sequencingMap.maps.findFirst[inputTupleName == map.inputTupleName].copy
		
		return map.copy.applyRange(tileDimsMap.copy)
			.rangeProduct(sequencedMap)
			.rangeProduct(map.copy.applyRange(iteratorMap.copy))
			.flatten
			.setDimNames(Dims.OUT, [indexName])
	}
	
	override ISLSet getApproximateOutset(ISLUnionSet domains) {
		getOutset(domains)
			.setDimNames(Dims.OUT, [indexName])
	}
	
	def private String indexName(int i) {
		if(i == 0) return "tw"
		else if(i < tileDimsMap.nbOutputs) return "t" + i
		else return "c" + (i - tileDimsMap.nbOutputs)
	}
	
	new(List<Integer> tileSizes, Scheduler scheduler, int startDim, int endDim) {
		this(tileSizes, scheduler.getMaps.getMaps.get(0).getRange.getSpace, startDim, endDim)
	}
}