package alpha.targetmapping.util

import alpha.model.AlphaSystem
import alpha.model.SystemBody
import alpha.targetmapping.TargetMapping
import alpha.targetmapping.TargetMappingForSystemBody
import alpha.targetmapping.TargetMappingNode
import fr.irisa.cairn.jnimap.isl.ISLASTLoopType
import fr.irisa.cairn.jnimap.isl.ISLAff
import fr.irisa.cairn.jnimap.isl.ISLAffList
import fr.irisa.cairn.jnimap.isl.ISLContext
import fr.irisa.cairn.jnimap.isl.ISLDimType
import fr.irisa.cairn.jnimap.isl.ISLLocalSpace
import fr.irisa.cairn.jnimap.isl.ISLMultiAff
import fr.irisa.cairn.jnimap.isl.ISLSpace
import java.util.List
import java.util.function.BiFunction
import java.util.function.Function
import alpha.model.Variable
import alpha.targetmapping.DataType

class TargetMappingUtil {
	
	public static Function<Integer,String> DEFAULT_SCHEDULE_DIMENSION_NAME_PROVIDER = [x|'''i«x»''']
	
	static def TargetMapping getContainerTM(TargetMappingNode node) {
		if (node instanceof TargetMapping)
			return node as TargetMapping
		
		if (node.eContainer() === null)
			return null
		
		return TargetMappingUtil.getContainerTM(node.eContainer() as TargetMappingNode)
	}
	
	static def TargetMappingForSystemBody getContainerTMforSystemBody(TargetMappingNode node) {
		if (node instanceof TargetMappingForSystemBody)
			return node as TargetMappingForSystemBody
		
		if (node.eContainer() === null)
			return null
		
		return TargetMappingUtil.getContainerTMforSystemBody(node.eContainer() as TargetMappingNode)
	}

	static def AlphaSystem getTargetSystem(TargetMappingNode node) {
		val tm = TargetMappingUtil.getContainerTM(node)
		
		if (tm === null) return null;
		
		return tm.targetSystem
	}

	static def SystemBody getTargetSystemBody(TargetMappingNode node) {
		val tm = TargetMappingUtil.getContainerTMforSystemBody(node)
		
		if (tm === null) return null;
		
		return tm.targetBody
	}
	
	static def toString(ISLASTLoopType value) {
		switch (value.getValue()) {
			case ISLASTLoopType.ISL_AST_LOOP_DEFAULT:
				return "default"
			case ISLASTLoopType.ISL_AST_LOOP_ATOMIC:
				return "atomic"
			case ISLASTLoopType.ISL_AST_LOOP_UNROLL:
				return "unroll"
			case ISLASTLoopType.ISL_AST_LOOP_SEPARATE:
				return "separate"
			default:
				throw new IllegalArgumentException()
		}
	}
	
	
	/*
	 * Returns a mult-dimensional affine function expressing the transformation applied to express the tile loop schedule.
	 * Each dimension of the returned function has the form (i -> i - i%ts)
	 * 
	 * Input ISLSpace is not consumed.
	 */
	static def tileLoopTransformation(ISLSpace space, List<Integer> tileSizes) {
		return tilingTransformationHelper(
			[ls,dim| 
				val divisor = ISLAff.buildZero(ls.copy).addConstant(tileSizes.get(dim))
				var aff = ISLAff.buildZero(ls)
				aff.addCoefficient(ISLDimType.isl_dim_in, dim, 1).
				div(divisor)
			], space, tileSizes
		);
		//version modulo
//		return tilingTransformationHelper(
//			[aff,dim| 
//				aff.addCoefficient(ISLDimType.isl_dim_in, dim, 1).
//				mod(tileSizes.get(dim)).
//				negate.
//				addCoefficient(ISLDimType.isl_dim_in, dim, 1)
//			], space, tileSizes
//		);
	}
	
	/*
	 * Returns a mult-dimensional affine function expressing the transformation applied to express the point loop schedule.
	 * Each dimension of the returned function has the form (i -> i%ts)
	 * 
	 * Input ISLSpace is not consumed.
	 */
	static def pointLoopTransformation(ISLSpace space, List<Integer> tileSizes) {
		return tilingTransformationHelper(
			[ls,dim| 			
				var aff = ISLAff.buildZero(ls)
				aff.addCoefficient(ISLDimType.isl_dim_in, dim, 1).
				mod(tileSizes.get(dim))
			], space, tileSizes
		);
	}
	
	/*
	 * Helper to avoid replicating all the setup code for construction tile and point loop functions.
	 */
	private static def tilingTransformationHelper(BiFunction<ISLLocalSpace,Integer,ISLAff> f, ISLSpace space, List<Integer> tileSizes) {
		if (space.nbOutputs != tileSizes.size)
			throw new RuntimeException(String.format("Space and tile sizes do not match: %s %s", space, tileSizes))
		
		val domainSpace = if (space.isSetSpace) space.copy else space.copy.domain
		val localSpace = domainSpace.copy.toLocalSpace
		var affList = ISLAffList.build(ISLContext.instance, tileSizes.size)
		for (dim : 0..<tileSizes.size) {
			val aff = f.apply(localSpace.copy, dim)
			affList = affList.add(aff)
		}
		
		val affSpace = domainSpace.toMapSpaceFromSetSpace
		return ISLMultiAff.buildFromAffList(affSpace, affList)
	}
	
	static def DataType dataType(Variable variable) {
		DataType.FLOAT
	}
}