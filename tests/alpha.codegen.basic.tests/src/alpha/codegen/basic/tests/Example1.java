package alpha.codegen.basic.tests;

import java.io.IOException;

import alpha.codegen.basic.BasicCodeGenerator;
import alpha.codegen.basic.targetMapping.BasicAffineSchedulePiece;
import alpha.codegen.basic.targetMapping.BasicTargetMapping;
import alpha.loader.AlphaLoader;
import alpha.model.AlphaRoot;
import alpha.model.SystemBody;
import fr.irisa.cairn.jnimap.isl.ISLContext;
import fr.irisa.cairn.jnimap.isl.ISLMultiAff;
import fr.irisa.cairn.jnimap.isl.ISLSet;

/**
 * This example is the classic matrix multiply where the array is initialized at k=0.
 * 
 * A piece-wise affine schedule is used to schedule the initialization as a separate loop.
 * 
 * Note that the basic codegen requires each branch of a case expression to be separate
 * pieces in the schedule so that statement generation is simplified.
 * 
 * @author tomofumi
 *
 */
public class Example1 {

	public static void main(String[] args) throws IOException {
		
		AlphaRoot root = AlphaLoader.loadAlpha("resources/matmult.alpha");
		
		SystemBody body = root.getSystems().get(0).getSystemBodies().get(0);
		
		BasicTargetMapping schedule = new BasicTargetMapping(body);
		
		ISLSet prologDom = ISLSet.buildFromString(ISLContext.getInstance(), "[N]-> { [i,j,k] : k=0 }");
		ISLMultiAff prologSch = ISLMultiAff.buildFromString(ISLContext.getInstance(), "[N]-> { [i,j,k] -> [0,i,j,k] }");
		
		ISLSet mainLoopDom = ISLSet.buildFromString(ISLContext.getInstance(), "[N]-> { [i,j,k] : k>0 }");
		ISLMultiAff mainLoopSch = ISLMultiAff.buildFromString(ISLContext.getInstance(), "[N]-> { [i,j,k] -> [1,i,j,k] }");
		
			
		schedule.addSchedule(new BasicAffineSchedulePiece(body.getStandardEquation("C"), prologDom, prologSch, "C0"));
		schedule.addSchedule(new BasicAffineSchedulePiece(body.getStandardEquation("C"), mainLoopDom, mainLoopSch, "C1"));
		
		System.out.println(schedule.getISLUnionMap());
		
		BasicCodeGenerator.generate(schedule);
	}
}
