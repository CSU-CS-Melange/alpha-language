package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet

interface Scheduler {
 	def ISLMap getScheduleMap(String variable)
 	def ISLSet getScheduleDomain(String variable)
 	def ISLUnionMap getMaps()
 	def ISLUnionSet getDomains()
}