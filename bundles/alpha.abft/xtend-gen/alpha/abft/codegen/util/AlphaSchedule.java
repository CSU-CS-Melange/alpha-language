package alpha.abft.codegen.util;

import alpha.model.AlphaExpression;
import fr.irisa.cairn.jnimap.isl.ISLSchedule;
import java.util.HashMap;

@SuppressWarnings("all")
public class AlphaSchedule {
  private final ISLSchedule islSchedule;

  private final HashMap<String, AlphaExpression> exprStmtMap;

  public AlphaSchedule(final ISLSchedule islSchedule, final HashMap<String, AlphaExpression> exprStmtMap) {
    this.islSchedule = islSchedule;
    this.exprStmtMap = exprStmtMap;
  }

  public ISLSchedule schedule() {
    return this.islSchedule;
  }

  public HashMap<String, AlphaExpression> exprStmtMap() {
    return this.exprStmtMap;
  }
}
