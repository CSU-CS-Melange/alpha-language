package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule

interface Scheduler {
 	def void generateSchedule()
 	def ISLMap getMacroSchedule(String macro)
 	def ISLSchedule getSchedule()
}