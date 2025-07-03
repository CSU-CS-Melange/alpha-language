package alpha.codegen

import alpha.model.memorymapper.MemoryMapper
import alpha.model.tiler.Tiler
import alpha.model.memorymapper.IdentityMemoryMapper

/** 
 * A class to store all the various codegen options.
 * This way, the signatures of the code generators do not need to change.
 */
class CodegenOptions {
	BaseDataType valueType
	Tiler tiler = null
	MemoryMapper mapper
	boolean normalize 			= false
	boolean inlineFunction 		= false 	// Tells the code generator to add the inline keyword to the evaluate function 
	boolean inlineCode		 	= false 	// Tells the code generator to manually inline the function (replace the function call with the actual function body
	boolean scheduledReductions = false		// Tells the code generator to accumulate points in a reduction according to a schedule
	boolean cycleDetection		= false
	boolean ompPragmas			= false		// Tells the code generator to insert OpenMP parallel for pragmas automatically.
	
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
	
	def setNormalize(boolean b) 			{normalize = b; 				this}
	def setInlineFunction(boolean b) 		{inlineFunction = b; 			this}
	def setInlineCode(boolean b) 			{inlineCode = b; 				this}
	def setScheduledReductions(boolean b)	{scheduledReductions = b;		this}
	def setCycleDetection(boolean b)		{cycleDetection = b;			this}
	def setOmpPragmas(boolean b)			{ompPragmas = b;				this}
	
	
	def getValueType() 				{valueType}
	def getTiler() 					{tiler}
	def getMapper() 				{mapper}
	def getNormalize()				{normalize}
	def getInlineFunction() 		{inlineFunction}
	def getInlineCode() 			{inlineCode}
	def getScheduledReductions() 	{scheduledReductions}
	def getCycleDetection() 		{cycleDetection}
	def getOmpPragmas() 			{ompPragmas}
}