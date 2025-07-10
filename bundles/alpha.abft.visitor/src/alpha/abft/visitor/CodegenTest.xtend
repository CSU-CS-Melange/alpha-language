package alpha.abft.visitor

import alpha.codegen.BaseDataType
import alpha.codegen.ProgramPrinter
import alpha.model.AlphaModelLoader
import alpha.model.prdg.PRDGGenerator
//import java.io.PrintWriter
import alpha.codegen.Program
import alpha.model.scheduler.FoutrierScheduler
import alpha.model.transformation.Normalize
import alpha.model.memorymapper.IdentityMemoryMapper
//import java.io.FileReader
//import alpha.model.scheduler.ManualScheduler
//import alpha.model.memorymapper.ManualMemoryMapper
//import java.util.HashMap
//import java.nio.CharBuffer
//import java.nio.file.Path
//import java.nio.file.Paths
//import java.nio.file.Files
import alpha.codegen.scheduledC.ScheduledC
import alpha.model.transformation.reduction.NormalizeReduction
import alpha.codegen.demandDriven.WriteC

//import alpha.model.transformation.automation.OptimalSimplifyingReductions

class CodegenTest {
	
	def static void main(String[] args) {
		val file = 'resources/auto/matmult_aabft.alpha'
		val root = AlphaModelLoader.loadModel(file)
		var system = root.systems.get(0)
		//println(AShow.print(root))
		//val prdg = PRDGGenerator.apply(system)
		var Program s_code
		var Program d_code
		var inlineKeyword = false
		var inlineCode = false
//		if(args.size >= 3) {
//			if(args.get(3) == "keyword") {
//				inlineKeyword = true
//			}
//			if(args.get(3) == "code") {
//				inlineCode = true
//			}
//		}
		var base_type = BaseDataType.FLOAT;
//		if(args.size >= 4) {
//			if(args.get(4) == "float") {
//				base_type = BaseDataType.FLOAT;
//			}
//			
//		}
		//if(file.contains("dependent-reductions")) {
		//var osr = OptimalSimplifyingReductions.apply(system, 1, 1, true, true)
		//	system = osr.optimizations.get(1).head.root.systems.get(0)
		//}

//		if(file.contains("star") && !(args.get(0) == "demand")) {
//			val Path path = Paths.get("workspace/AlphaTest/resources/schedule-v2.txt");
//			
//			val schedule = Files.readAllLines(path).join("\n")
//			
//			Normalize.apply(system)
//			val prdg = PRDGGenerator.apply(system)
//			var scheduler = new FoutrierScheduler(prdg)
//			println("Generated PRDG: ")
//			prdg.edges.forEach[edge | println(edge.source.name + " -> " + edge.dest.name + ": " + edge.function)]
//			//var scheduler = new ManualScheduler(schedule)
//			var maps = new HashMap()
//			maps.put("Y", "[T, N] -> { [t, i] -> [o0, i] : (t + o0) mod 2 = 0 and 0 <= o0 <= 1 }")
//			var dests = new HashMap()
//			dests.put("Y", "Y")
//			var mapper = new ManualMemoryMapper(maps, dests)
//			//var mapper = new IdentityMemoryMapper()
//			code = ScheduledC.convert(system, base_type, scheduler, mapper, true, inlineKeyword, inlineCode);
//		} else if(args.get(0) == "scheduled") {
//			Normalize.apply(system)
//			val prdg = PRDGGenerator.apply(system)
//			var scheduler = new FoutrierScheduler(prdg)
//			scheduler.maps.maps.forEach[map | println("Map: " + map)]
//			
//			var mapper = new IdentityMemoryMapper()
//			code = ScheduledC.convert(system, base_type, scheduler, mapper, true, inlineKeyword, inlineCode);
//		} else {
//			code = alpha.codegen.demandDriven.WriteC.convert(system, base_type, false);
//		}

		Normalize.apply(system)
		NormalizeReduction.apply(system)
		val prdg = PRDGGenerator.apply(system)
		var scheduler = new FoutrierScheduler(prdg)
//		scheduler.maps.maps.forEach[map | println("Map: " + map)]
		
		var mapper = new IdentityMemoryMapper()
		s_code = ScheduledC.convert(system, base_type, scheduler, null, mapper, true, inlineKeyword, inlineCode);
		d_code = WriteC.convert(system, BaseDataType.FLOAT, true)
		
		println(prdg.show())
		println(ProgramPrinter.print(s_code))
		println('-------------')
		println(ProgramPrinter.print(d_code))
		
//		val PrintWriter writer = new PrintWriter("./outputs/" + args.get(2), "UTF-8");
//		writer.println(ProgramPrinter.print(code));
//		writer.close();
		
		//val osr = OptimalSimplifyingReductions.apply(system, 1, 1, false, true)
		
	}
}