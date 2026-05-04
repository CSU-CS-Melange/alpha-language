package alpha.model.tiler

import alpha.model.scheduler.Scheduler
import alpha.model.util.ISLUtil.Dims
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLConstraint
import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLSpace
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLVal
import java.util.List

import static extension alpha.model.util.ISLUtil.*
import static extension alpha.model.util.ISLValUtil.*
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLBasicSet

class WavefrontTiler extends DTiler {
	new(List<Integer> tileSizes, ISLSpace scheduleSpace, int startDim, int endDim) {
		super(tileSizes, scheduleSpace, startDim, endDim)
	}
	
	override ISLUnionMap tileSchedule(ISLUnionMap umap) {
		var tiledMaps = umap.maps
			.map[tileSchedule]
			.convertToUnionMap
		
		return tiledMaps
	}
	
	override ISLMap tileSchedule(ISLMap map) {
		return map.copy.applyRange(getTileMaff.toMap)
			.rangeProduct(map.copy)
			.flatten
			.setDimNames(Dims.OUT, [indexName])
	}
	
	/** Maps the schedule space to the tile space. */
	def private ISLMultiAff getTileMaff() {
		val maff = preTileMaff
		
		return wavefrontMaff.pullback((0..<tileSizes.size)
			.map[maff.getAff(it).scaleDown(tileSizes.get(it)).floor]
			.toList.convertToMultiAff)
	}
	
	override ISLSet getApproximateOutset(ISLUnionSet ranges) {
		val outset = ranges.copy.sets
			.map[clearTupleName]
			.reduce[a, b | a.union(b)]
			.apply(preTileMaff.toMap)
			.basicSets.map[applyWavefront]
			.reduce[a, b | a.union(b)]
			
		return outset
	}
	
	def private ISLSet applyWavefront(ISLBasicSet bset) {
		bset.constraints
			.map[scaleDownConstraint]
			.flatMap[extendConstraint]
			.convertToSet
			.apply(wavefrontMaff.toMap)
			.setDimNames(Dims.OUT, [indexName])
	}
	
	def private ISLMultiAff getWavefrontMaff() {
		var idAffs = (0..<tileSizes.size)
			.map[ISLAff.buildVarOnDomain(preTileMaff.toMap.getRange.space.toLocalSpace, Dims.SET, it)]
			
		var waveAff = idAffs
				.reduce[a, b| a.copy.add(b.copy)]
				
		return (#[waveAff] + idAffs.toList.subList(0, idAffs.size-1))
			.toList.convertToMultiAff
	}
	
	/** The map applied to the schedule space before scaling it down, and before adding the wavefront dim. */
	def private ISLMultiAff getPreTileMaff() {
		var idAffs = (startDim..endDim)
			.map[ISLAff.buildVarOnDomain(scheduleSpace.copy.toLocalSpace, Dims.SET, it)]
			
		return idAffs.toList.convertToMultiAff
	}
	
	override ISLUnionMap getParameterizedIterators(ISLUnionMap maps) {
		val tileConstraintSet = tileMaff.toMap
			.setDimNames(Dims.OUT, [indexName])
			.moveDims(Dims.PARAM, 0, Dims.OUT, 0, tileSizes.size)
			.getDomain
		
		maps.maps.map[alignParams(tileConstraintSet.space.copy)]
			.map[intersectRange(tileConstraintSet.copy)]
			.map[setDimNames(Dims.OUT, ["c"+it])]
			.toList.convertToUnionMap
	}
	
	/** Transforms a constraint such that it includes the tile origin of any tiles it originally touched. */
	def private Iterable<ISLConstraint> extendConstraint(ISLConstraint con) {
		if(con.isEquality) return con.toInequalityConstraints.flatMap[extendConstraint]
		
		val Iterable<ISLVal> betas = (0..<con.getNbDims(Dims.SET))
			.map[con.getCoefficientVal(Dims.SET, it)]
			.map[isPositive ? it : 0.asVal]
			
		val ISLVal bias = (0..<con.getNbDims(Dims.SET))
			.map[betas.get(it)]
			.reduce[a, b | a + b]
			.add(con.getConstantVal)
			
		return #[con.setConstant(bias)]
	}
	
	def private ISLConstraint scaleDownConstraint(ISLConstraint con) {
		return (0..<con.getNbDims(Dims.SET))
			.fold(con.copy, 
				[c, i | c.setCoefficient(Dims.SET, i, c.getCoefficientVal(Dims.SET, i) * tileSizes.get(i))]
			)
	}
	
	def private String indexName(int i) {
		if(i == 0) return "tw"
		else if(i < tileSizes.size) return "t" + i
		else return "c" + (i - tileSizes.size)
	}
	
	new(List<Integer> tileSizes, Scheduler scheduler, int startDim, int endDim) {
		this(tileSizes, scheduler.getMaps.getMaps.get(0).getRange.getSpace, startDim, endDim)
	}
}