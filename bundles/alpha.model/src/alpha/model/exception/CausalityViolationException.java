package alpha.model.exception;

import alpha.model.DependenceExpression;
import alpha.model.scheduler.Scheduler;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/*
 * Indicates that a schedule executes some computations before or
 * at the same time as their dependences.
 * 
 * Used by the schedule verifier when manual schedules are provided.
 */

public class CausalityViolationException extends RuntimeException {
	private static final long serialVersionUID = 6671759112318252538L;

	public CausalityViolationException(ISLMultiAff de, ISLMultiAff writeTimestampMaff, 
			ISLMultiAff readTimestampMaff, ISLSet set, int dimension) {
		super(
			"Causality failed for dependence: " + de
			+ "\nwith write timestamp function: " + writeTimestampMaff
			+ "\nwith read timestamp function: " + readTimestampMaff
			+ "\nat dimension: " + dimension 
			+ "\nfor points: " + set
		);
	}
}
