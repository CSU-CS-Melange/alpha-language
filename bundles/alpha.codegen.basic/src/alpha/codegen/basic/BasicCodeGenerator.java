package alpha.codegen.basic;

import alpha.codegen.basic.statement.BasicCStatementGenerator;
import alpha.codegen.basic.targetMapping.BasicTargetMapping;
import fr.irisa.cairn.jnimap.isl.ISLASTBuild;
import fr.irisa.cairn.jnimap.isl.ISLASTNode;

/**
 * Basic code generator that takes piece-wise affine schedules an dumps
 * the loops and the statement bodies. A lot more effort is needed to 
 * generate code that can compile and run.
 * 
 * @author tomofumi
 *
 */
public class BasicCodeGenerator {

	
	public static void generate(BasicTargetMapping schedule) {
		
		ISLASTBuild build = ISLASTBuild.buildFromContext(schedule.getSystemBody().getParameterDomain());
		ISLASTNode node = build.generate(schedule.getISLUnionMap());
		
		System.out.println(node.toCString());
		
		BasicCStatementGenerator statementGen = new BasicCStatementGenerator(schedule);
		var statements = statementGen.generate();
		for (var piece : statements.keySet()) {
			String stmt = piece.getStatementID() + "(" + String.join(",", piece.getEquation().getVariable().getDomain().getIndexNames()) + ")";
			
			System.out.println(stmt + " -> " + statements.get(piece));
		}
	}
}
