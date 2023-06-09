package alpha.commands;

import alpha.model.AlphaExpression;
import alpha.model.AlphaSystem;
import alpha.model.Equation;
import alpha.model.SystemBody;


public class Utility extends UtilityBase {
	public static SystemBody GetSystemBody(AlphaSystem system) {
		int bodyID = 0;
		return GetSystemBody(system, bodyID);
	}
	public static AlphaExpression GetExpression(Equation eq) {
		String exprID = "0";
		return GetExpression(eq, exprID);
	}
	public static AlphaExpression GetExpression(SystemBody body, String varName, String exprID) {
		Equation eq = ValueConverter.toEquation(body, varName);
		return GetExpression(eq, exprID);
	}
	public static AlphaExpression GetExpression(SystemBody body, String varName) {
		String exprID = "0";
		return GetExpression(body, varName, exprID);
	}
	public static AlphaExpression GetExpression(AlphaSystem system, int bodyID, String varName, String exprID) {
		SystemBody body = ValueConverter.toSystemBody(system, bodyID);
		return GetExpression(body, varName, exprID);
	}
	public static AlphaExpression GetExpression(AlphaSystem system, String varName, String exprID) {
		int bodyID = 0;
		return GetExpression(system, bodyID, varName, exprID);
	}
	public static AlphaExpression GetExpression(AlphaSystem system, int bodyID, String varName) {
		String exprID = "0";
		return GetExpression(system, bodyID, varName, exprID);
	}
	public static AlphaExpression GetExpression(AlphaSystem system, String varName) {
		int bodyID = 0;
		String exprID = "0";
		return GetExpression(system, bodyID, varName, exprID);
	}
}
