package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.ArrayList
import java.util.List
import java.util.Set
import java.util.stream.Collectors
import java.util.stream.IntStream

import static extension alpha.model.util.ISLUtil.*
import alpha.model.util.ISLUtil.Dims

class DTiler implements Tiler {
	protected ISLMap tileMap
	int startDim
	int endDim
	List<Integer> tileSizes
	
	/*
	 * Creates a D-Tiler with rectangular tiles of the given size, 
	 * from the start to the end dimension, inclusive.
	 */
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		this.tileSizes = tileSizes
		this.startDim = startDim
		this.endDim = endDim
		val int scheduleDim = scheduleSpace.dim(ISLDimType.isl_dim_out)
		val int bandDim = endDim - startDim + 1
		
		argumentCheck(tileSizes, scheduleSpace, startDim, endDim, scheduleDim, bandDim)
	
		val ISLSpace space = scheduleSpace.copy
				
		var tileAffs = (startDim..endDim).map[ i |
			ISLAff.buildVarOnDomain(space.copy.toLocalSpace, Dims.OUT, i)
				.scaleDown(tileSizes.get(i - startDim)).floor
		]
		
		var iterAffs = (0..<scheduleDim).map[ i |
			ISLAff.buildVarOnDomain(space.copy.toLocalSpace, Dims.OUT, i)
		]
		
		this.tileMap = (tileAffs + iterAffs).toList
			.convertToMultiAff
			.toMap
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
	
	override ISLMap getUntileMap() {
		return tileMap.copy.projectOut(
			ISLDimType.isl_dim_out, 
			tiledDims.size,
			tileMap.dim(ISLDimType.isl_dim_out)-tiledDims.size
		).reverse
	}
	
	override Set<Integer> getTiledDims() {
		IntStream.rangeClosed(startDim, endDim).boxed.collect(Collectors.toSet)
	}
	
	override ISLSet getApproximateOutset(ISLSet domain) {
		getOutset(domain)
	}
	
	override boolean fixedTileSizes() {
		return true
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
	
	override int getTileSize(int dim) {
		return this.tileSizes.get(dim - startDim)
	}
}