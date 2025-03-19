package alpha.model.tiler

import fr.irisa.cairn.jnimap.isl.ISLMap
import java.util.stream.IntStream
import java.util.stream.Collectors
import java.util.Set
import fr.irisa.cairn.jnimap.isl.ISLSet
import java.util.List
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLAff
import java.util.ArrayList
import alpha.model.util.ISLUtil
import alpha.model.scheduler.Scheduler

class DTiler implements Tiler {
	ISLMap tileMap
	int startDim
	int endDim
	
	/*
	 * Creates a D-Tiler with rectangular tiles of the given size, 
	 * from the start to the end dimension, inclusive.
	 */
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		val int scheduleDim = scheduleSpace.dim(ISLDimType.isl_dim_out)
		val int bandDim = endDim - startDim + 1
		
		argumentCheck(tileSizes, scheduleSpace, startDim, endDim, scheduleDim, bandDim)
	
		val ISLSpace space = scheduleSpace.copy
		
		var affs = new ArrayList<ISLAff>
		
		for(var i = 0; i < scheduleDim; i++) {
			var aff = ISLAff.buildVarOnDomain(space.copy.toLocalSpace, ISLDimType.isl_dim_out, i)
			if(i >= startDim && i <= endDim)
				aff = aff.scaleDown(tileSizes.get(i - startDim)).floor
			affs.add(aff)
		} 
		
		for(var i = 0; i < bandDim; i++) {
			var aff = ISLAff.buildVarOnDomain(space.copy.toLocalSpace, ISLDimType.isl_dim_out, i+startDim)
			affs.add(aff)
		}
		
		this.startDim = startDim
		this.endDim = endDim
		this.tileMap = ISLUtil.convertToMultiAff(affs).toMap
	}
	
	def void argumentCheck(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim,
							int scheduleDim, int bandDim) {
		if(tileSizes.exists(size | size <= 0)) 
			throw new IllegalArgumentException("Tiles cannot have non-positive size.")
		if(startDim < 0 || endDim < 0 || startDim >= scheduleDim || endDim >= scheduleDim)	
			throw new IllegalArgumentException("Tiled dimensions are out of range.")
		if(startDim > endDim)
			throw new IllegalArgumentException("endDim must be greater than startDim.")
		if(tileSizes.size() != bandDim)
			throw new IllegalArgumentException("The size of tileSizes must match the number of tiled dimensions.")
	}
	
	new(List<Integer> tileSizes, Scheduler scheduler, int startDim, int endDim) {
		this(tileSizes, scheduler.getMaps.getMaps.get(0).getRange.getSpace, startDim, endDim)
	}
	
	override ISLMap getTileMap() {tileMap.copy}
	
	override Set<Integer> getTiledDims() {
		IntStream.rangeClosed(startDim, endDim).boxed.collect(Collectors.toSet)
	}
	
	override ISLSet getApproximateOutset(ISLSet domain) {
		getOutset(domain)
	}
	
	/**
	 * Uses implicit ISL methods to get the exact output.
	 * This takes exponential time in the worst case.
	 */
	def ISLSet getOutset(ISLSet domain) {
		domain.copy.apply(getTileMap)
			.projectOut(ISLDimType.isl_dim_out, endDim+1, tileMap.getNbOutputs-endDim-1)
			.projectOut(ISLDimType.isl_dim_out, 0, startDim)
	}
}