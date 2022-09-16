package alpha.codegen.basic.targetMapping;

import alpha.model.StandardEquation;
import fr.irisa.cairn.jnimap.isl.ISLMap;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * Affine Schedule Piece is the smallest unit of scheduling in the basic codegen to express piece-wise schedules.
 * The pieces for an equation must be disjoint, and their union must cover the full variable domain. (Superset is fine)
 * 
 * @author tomofumi
 *
 */
public class BasicAffineSchedulePiece {
	
	private final StandardEquation equation;
	private final ISLSet piece;
	private final ISLMultiAff schedule;
	private final String statementID;
	private final ISLMap mapRepresentation;

	public BasicAffineSchedulePiece(StandardEquation eq, ISLSet dom, ISLMultiAff sch, String stmtID) {
		equation = eq;
		piece = dom;
		schedule = sch;
		statementID = stmtID;
		
		if (! equation.getVariable().getDomain().getSpace().isEqual(schedule.getDomainSpace())) {
			throw new RuntimeException("Domain of schedule must match the domain of the equation.");
		}
		
		mapRepresentation = schedule.copy().toMap().intersectDomain(piece.copy()).setInputTupleName(statementID);
	}
	
	public BasicAffineSchedulePiece(StandardEquation eq, ISLMultiAff sch, String stmtID) {
		this(eq, eq.getVariable().getDomain(), sch,	stmtID);
	}
	
	public BasicAffineSchedulePiece(StandardEquation eq, ISLMultiAff sch) {
		this(eq, eq.getVariable().getDomain(), sch,	eq.getName());
	}
	
	public BasicAffineSchedulePiece(StandardEquation eq) {
		this(eq, eq.getVariable().getDomain(), 
				ISLMultiAff.buildIdentity(eq.getVariable().getDomain().getSpace().toMapSpaceFromSetSpace()),
				eq.getName());
	}
	
	public StandardEquation getEquation() {
		return equation;
	}
	
	public ISLSet getDomain() {
		return piece.copy();
	}
	
	public String getStatementID() {
		return statementID;
	}
	
	public ISLMap getISLMap() {
		return mapRepresentation.copy();
	}

}
