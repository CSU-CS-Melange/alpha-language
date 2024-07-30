package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSchedule
import fr.irisa.cairn.jnimap.isl.ISLSet

interface Scheduler {
 	def ISLMap getScheduleMap(String variable)
 	def ISLSet getScheduleDomain(String macro)
 	def ISLSchedule getSchedule()
}