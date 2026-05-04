package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import alpha.model.util.ISLUtil.Dims
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import java.util.List
import java.util.Set
import java.util.stream.Collectors
import java.util.stream.IntStream

import static extension alpha.model.util.ISLUtil.*

class DTiler implements Tiler {
	protected ISLMap tileMap
	protected int startDim
	protected int endDim
	protected List<Integer> tileSizes
	protected ISLSpace scheduleSpace
	
	/*
	 * Creates a D-Tiler with rectangular tiles of the given size, 
	 * from the start to the end dimension, inclusive.
	 */
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		this.scheduleSpace = scheduleSpace
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
	
	override ISLUnionMap tileSchedule(ISLUnionMap maps) {
		maps.copy.applyRange(tileMap.copy.toUnionMap)
	}
	
	override ISLMap tileSchedule(ISLMap map) {
		tileSchedule(map.copy.toUnionMap).maps.head
	}
	
	/*override ISLMap getUntileMap() {
		return tileMap.copy.projectOut(
			ISLDimType.isl_dim_out, 
			tiledDims.size,
			tileMap.dim(ISLDimType.isl_dim_out)-tiledDims.size
		).reverse
	}
	*/
	override ISLUnionMap getParameterizedIterators(ISLUnionMap maps) {
		return tileSchedule(maps).maps
			.map[moveDims(Dims.PARAM, 0, Dims.OUT, startDim, endDim-startDim+1)]
			.convertToUnionMap
	}
	
	override Set<Integer> getTiledDims() {
		IntStream.rangeClosed(startDim, endDim).boxed.collect(Collectors.toSet)
	}
	
	override ISLSet getApproximateOutset(ISLUnionSet domains) {
		getOutset(domains)
	}
	
	override boolean fixedTileSizes() {
		return true
	}
	
	/**
	 * Uses implicit ISL methods to get the exact output.
	 * This takes exponential time in the worst case.
	 */
	def ISLSet getOutset(ISLUnionSet domains) {
		domains.copy.sets
			.map[clearTupleName]
			.reduce[a, b | a.union(b)]
			.toIdentityMap
			.tileSchedule
			.getRange
			.projectOut(Dims.OUT, endDim+1, tileMap.getNbOutputs-endDim-1)
			.projectOut(Dims.OUT, 0, startDim)
			.simpleHull.toSet
	}
	
	override int getTileSize(int dim) {
		return this.tileSizes.get(dim - startDim)
	}
}