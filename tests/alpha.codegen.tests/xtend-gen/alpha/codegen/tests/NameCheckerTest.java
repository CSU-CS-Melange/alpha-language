package alpha.codegen.tests;

import alpha.codegen.BaseDataType;
import alpha.codegen.DataType;
import alpha.codegen.Factory;
import alpha.codegen.NameChecker;
import alpha.codegen.NameConflictException;
import alpha.codegen.VariableDecl;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

@SuppressWarnings("all")
public class NameCheckerTest {
  @Test
  public void globalNameExists_01() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a", "b", "c");
    Assert.assertTrue(checker.isGlobalOrKeyword("c"));
    Assert.assertTrue(checker.isGlobalOrKeyword("a"));
    Assert.assertTrue(checker.isGlobalOrKeyword("b"));
    Assert.assertFalse(checker.isGlobalOrKeyword("d"));
    Assert.assertFalse(checker.isGlobalOrKeyword("A"));
    Assert.assertFalse(checker.isGlobalOrKeyword("aaaa"));
  }

  @Test
  public void checkAddGlobal_01() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a");
    checker.checkAddGlobal("b");
    checker.checkAddGlobal("c");
    checker.checkAddGlobal("A");
    checker.checkAddGlobal("aa");
    checker.checkAddGlobal("aaa");
  }

  @Test
  public void checkAddGlobal_02() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a");
    final ThrowingRunnable _function = new ThrowingRunnable() {
      public void run() throws Throwable {
        checker.checkAddGlobal("a");
      }
    };
    Assert.<Throwable>assertThrows(NameConflictException.class, _function);
  }

  @Test
  public void checkAddGlobal_03() {
    final NameChecker checker = new NameChecker();
    final ThrowingRunnable _function = new ThrowingRunnable() {
      public void run() throws Throwable {
        checker.checkAddGlobal("a", "a");
      }
    };
    Assert.<Throwable>assertThrows(NameConflictException.class, _function);
  }

  @Test
  public void getUniqueGlobalName_01() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a", "aa", "aaa");
    Assert.assertEquals("aaaa", checker.getUniqueGlobalName("a", "a"));
  }

  @Test
  public void preventShadowing_01() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a");
    final DataType dataType = Factory.dataType(BaseDataType.LONG);
    final VariableDecl variable = Factory.variableDecl(dataType, "a");
    final ThrowingRunnable _function = new ThrowingRunnable() {
      public void run() throws Throwable {
        checker.checkAddLocal(variable, CollectionLiterals.<Object>newArrayList());
      }
    };
    Assert.<Throwable>assertThrows(NameConflictException.class, _function);
  }

  @Test
  public void allowShadowing_01() {
    final NameChecker checker = new NameChecker(false);
    checker.checkAddGlobal("a");
    final DataType dataType = Factory.dataType(BaseDataType.LONG);
    final VariableDecl variable = Factory.variableDecl(dataType, "a");
    checker.checkAddLocal(variable, CollectionLiterals.<Object>newArrayList());
  }

  @Test
  public void checkAddLocal_01() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field name is undefined for the type Object"
      + "\n== cannot be resolved");
  }

  @Test
  public void getUniqueLocalName_01() {
    final NameChecker checker = new NameChecker();
    checker.checkAddGlobal("a", "aa");
    final List<String> locals = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("aaa", "aaaa"));
    Assert.assertEquals("aaaaa", checker.getUniqueLocalName(locals, "a", "a"));
  }
}
