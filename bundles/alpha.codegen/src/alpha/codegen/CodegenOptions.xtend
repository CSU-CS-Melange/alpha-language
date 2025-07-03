package alpha.codegen

import alpha.model.memorymapper.MemoryMapper
import alpha.model.tiler.Tiler
import alpha.model.memorymapper.IdentityMemoryMapper

/** 
 * A class to store all the various codegen options.
 * This way, the signatures of the code generators do not need to change.
 */
class CodegenOptions {
	/* The base data type of the program (typically float) */ 
	BaseDataType valueType
	
	/* The tiler (optional) */ 
	Tiler tiler = null
	
	/* The memory mapper object */ 
	MemoryMapper mapper
	
	/* Whether to normalize the system before codegen */ 
	boolean normalize 			= false	
	
	/* Whether to add the inline keyword to the evaluate function */
	boolean inlineFunction 		= false 	
	
	/* Whether to manually inline functions (replace the function call with the actual function body) */
	boolean inlineCode		 	= false 	
	
	/* Whether to accumulate points in a reduction according to a schedule as opposed to ad hoc reductions */
	boolean scheduledReductions = false		
	
	/* Whether to detect cycles in codegen. May or may not work with ScheduledC. */
	boolean cycleDetection		= false
	
	/* Whether to insert OpenMP parallel pragmas automatically. */
	boolean ompPragmas			= false		
	
	/* Whether to allocate only the memory needed for variables, as opposed to allocating the bounding box. */
	boolean polyhedralMemory 	= false 	
	
	new(BaseDataType valueType) {
		this.valueType = valueType
		mapper = new IdentityMemoryMapper()
	}
	
	static def create(BaseDataType valueType) {
		return new CodegenOptions(valueType)
	}
	
	def setTiler(Tiler tiler) 			{this.tiler = tiler; 		this}
	def setMapper(MemoryMapper mapper) 	{this.mapper = mapper; 		this}
	
	
	def setNormalize() 				{normalize = true; 				this}
	def setInlineFunction() 		{inlineFunction = true; 		this}
	def setInlineCode() 			{inlineCode = true; 			this}
	def setScheduledReductions()	{scheduledReductions = true;	this}
	def setCycleDetection()			{cycleDetection = true;			this}
	def setOmpPragmas()				{ompPragmas = true;				this}
	def setPolyhedralMemory()		{polyhedralMemory = true;       this}
	
	def setNormalize(boolean b) 			{normalize = b; 				this}
	def setInlineFunction(boolean b) 		{inlineFunction = b; 			this}
	def setInlineCode(boolean b) 			{inlineCode = b; 				this}
	def setScheduledReductions(boolean b)	{scheduledReductions = b;		this}
	def setCycleDetection(boolean b)		{cycleDetection = b;			this}
	def setOmpPragmas(boolean b)			{ompPragmas = b;				this}
	def setPolyhedralMemory(boolean b)		{polyhedralMemory = b; 			this}
	
	
	def getValueType() 				{valueType}
	def getTiler() 					{tiler}
	def getMapper() 				{mapper}
	def getNormalize()				{normalize}
	def getInlineFunction() 		{inlineFunction}
	def getInlineCode() 			{inlineCode}
	def getScheduledReductions() 	{scheduledReductions}
	def getCycleDetection() 		{cycleDetection}
	def getOmpPragmas() 			{ompPragmas}
	def getPolyhedralMemory() 		{polyhedralMemory}
}