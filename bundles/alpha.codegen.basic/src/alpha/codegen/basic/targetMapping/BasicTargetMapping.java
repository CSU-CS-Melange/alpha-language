package alpha.codegen.basic.targetMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import fr.irisa.cairn.jnimap.isl.ISLSet;
import fr.irisa.cairn.jnimap.isl.ISLUnionMap;

/**
 * Basic target mapping.
 * 
 * Only support piece-wise affine schedules for now.
 * 
 * @author tomofumi
 *
 */
public class BasicTargetMapping {

	private final SystemBody systemBody;
	private final Map<StandardEquation, List<BasicAffineSchedulePiece>> schedulePieces = new HashMap<>();

	public BasicTargetMapping(SystemBody systemBody) {
		this.systemBody = systemBody;

		if (systemBody.getUseEquations().size() != 0) {
			throw new RuntimeException("A system with UseEquations are not supported.");
		}
	}

	public void addSchedule(BasicAffineSchedulePiece schedulePiece) {
		if (!schedulePieces.containsKey(schedulePiece.getEquation())) {
			schedulePieces.put(schedulePiece.getEquation(), new LinkedList<>());
		}
		schedulePieces.get(schedulePiece.getEquation()).add(schedulePiece);
	}

	public boolean areSchedulesConsistent() {

		boolean inconsistent = false;

		for (StandardEquation eq : systemBody.getStandardEquations()) {
			if (!schedulePieces.containsKey(eq)) {
				System.out.println("Schedules not defined for Equation: " + eq.getName());
				inconsistent = true;
				continue;
			}

			ISLSet domainUnion = null;
			for (var p : schedulePieces.get(eq)) {
				if (domainUnion == null) {
					domainUnion = p.getDomain();
					continue;
				}

				if (!domainUnion.isDisjoint(p.getDomain())) {
					System.out.println("A piece '" + p.getDomain() +  "' is not disjoint with other pieces for Equation: " + eq.getName());
					inconsistent = true;
					domainUnion = domainUnion.union(p.getDomain());
				}

				if (domainUnion.isStrictSubset(eq.getVariable().getDomain())) {
					System.out.println("The schedules for Equation: " + eq.getName() + " does not fully cover its domain.");
					inconsistent = true;
				}
			}
		}

		return !inconsistent;
	}

	public SystemBody getSystemBody() {
		return systemBody;
	}

	public List<BasicAffineSchedulePiece> getSchedulePieces(StandardEquation eq) {
		return schedulePieces.get(eq);
	}

	public ISLUnionMap getISLUnionMap() {
		if (!areSchedulesConsistent()) {
			throw new RuntimeException("Inconsistent Schedule.");
		}

		ISLUnionMap unionSchedule = null;
		for (var key : schedulePieces.keySet()) {
			ISLSet varDomain = key.getVariable().getDomain();
			for (var p : schedulePieces.get(key)) {
				ISLUnionMap pieceSchedule = p.getISLMap().copy()
						.intersectDomain(varDomain.copy().setTupleName(p.getStatementID())).toUnionMap();
				if (unionSchedule == null) {
					unionSchedule = pieceSchedule;
				} else {
					unionSchedule = unionSchedule.union(pieceSchedule);
				}
			}
		}

		return unionSchedule;
	}
}
