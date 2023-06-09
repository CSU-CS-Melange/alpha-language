package alpha.commands;

/*PROTECTED REGION ID(UtilityBase_Imports) ENABLED START*/
//Add custom imports here
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import alpha.model.AlphaExpression;
import alpha.model.AlphaNode;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
/*PROTECTED REGION END*/
import alpha.model.util.AlphaUtil;


public class UtilityBase {
	public static void WriteToFile(String filepath, String content) {
		/*PROTECTED REGION ID(Utility.WriteToFile_) ENABLED START*/
		String fname = filepath;
		if (!filepath.startsWith("/") && !filepath.startsWith("./") && !filepath.startsWith("../")) {
			fname = "./"+filepath;
		}
		
		File file = new File(fname);
		if (!file.exists() && !(new File(file.getParent()).exists())) {
			new File(file.getParent()).mkdirs();
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			throw new RuntimeException("Error while writing to " + fname);
		}
		/*PROTECTED REGION END*/
	}
	public static AlphaRoot GetRoot(List<AlphaRoot> bundle, String systemName) {
		/*PROTECTED REGION ID(Utility.GetRoot_) ENABLED START*/
		return AlphaUtil.selectAlphaRoot(bundle, systemName);
		/*PROTECTED REGION END*/
	}
	public static AlphaSystem GetSystem(AlphaRoot root, String systemName) {
		/*PROTECTED REGION ID(Utility.GetSystem_) ENABLED START*/
		return root.getSystem(systemName);
		/*PROTECTED REGION END*/
	}
	public static SystemBody GetSystemBody(AlphaSystem system, int bodyID) {
		/*PROTECTED REGION ID(Utility.GetSystemBody_) ENABLED START*/
		return system.getSystemBodies().get(bodyID);
		/*PROTECTED REGION END*/
	}
	public static StandardEquation GetEquation(SystemBody body, String varName) {
		/*PROTECTED REGION ID(Utility.GetEquation_) ENABLED START*/
		return body.getStandardEquation(varName);
		/*PROTECTED REGION END*/
	}
	public static AlphaExpression GetExpression(Equation eq, String exprID) {
		/*PROTECTED REGION ID(Utility.GetExpression_GetExpr1) ENABLED START*/
		return eq.getExpression(exprID);
		/*PROTECTED REGION END*/
	}
	public static AlphaNode GetNode(AlphaRoot root, String nodeID) {
		/*PROTECTED REGION ID(Utility.GetNode_) ENABLED START*/
		return root.getNode(nodeID);
		/*PROTECTED REGION END*/
	}
	public static Variable GetVariable(AlphaSystem system, String varname) {
		/*PROTECTED REGION ID(Utility.GetVariable_) ENABLED START*/
		throw new UnsupportedOperationException("Not implemented.");
		/*PROTECTED REGION END*/
	}
	public static Variable GetVariable(SystemBody body, String varname) {
		/*PROTECTED REGION ID(Utility.GetVariable__1) ENABLED START*/
		throw new UnsupportedOperationException("Not implemented.");
		/*PROTECTED REGION END*/
	}
	public static void RenameVariable(AlphaSystem system, String oldName, String newName) {
		/*PROTECTED REGION ID(Utility.RenameVariable_) ENABLED START*/
		Variable v = system.getVariable(oldName);
		v.setName(newName);
		/*PROTECTED REGION END*/
	}
}
