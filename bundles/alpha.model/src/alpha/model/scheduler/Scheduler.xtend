package alpha.model.scheduler

import fr.irisa.cairn.jnimap.isl.ISLMap
import fr.irisa.cairn.jnimap.isl.ISLUnionMap
import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.isl.ISLUnionSet
import fr.irisa.cairn.jnimap.isl.ISLSchedule

abstract class Scheduler {
	/** Returns the generated ISLSchedule object, if it exists. */
	def protected ISLSchedule getSchedule()
	
	/** Returns a schedule map for a specific variable. */
 	def ISLMap getScheduleMap(String variable) 		{maps.maps.findFirst[inputTupleName == variable]?.copy}
 	
	/** Returns the domain for a specific variable. */
 	def ISLSet getScheduleDomain(String variable)	{domains.sets.findFirst[tupleName == variable]?.copy}
 	
	/** 
	 * Returns all of the schedule maps as a single ISLUnionMap object.
	 * The inputTupleName of each map corresponds to the scheduled variable.
	 */
 	def ISLUnionMap getMaps() 						
	/** Returns a schedule map for a specific variable. */	{schedule.map.copy}
 	
	/** 
	 * Returns all of the domains as a single ISLUnionSet object.
	 * The inputTupleName of each domain corresponds to the respective variable.
	 */
 	def ISLUnionSet getDomains() 					{schedule.domain.copy}

	/** 
	 * Returns the images of the domains under the schedule map as a single ISLUnionSet object.
	 * The inputTupleName of each range corresponds to the respective variable.
	 */
	def ISLUnionSet getRanges() 					{getDomains.apply(getMaps)}
 		
	/** Returns a schedule map for a specific variable, with the inputTupleName removed. */
 	def ISLMap getAnonymousMap(String variable)		{getScheduleMap(variable).clearInputTupleName}
}