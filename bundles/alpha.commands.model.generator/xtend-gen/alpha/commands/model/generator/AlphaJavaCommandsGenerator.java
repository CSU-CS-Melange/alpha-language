package alpha.commands.model.generator;

import alpha.commands.model.AlphaCommandCategory;
import alpha.commands.model.AlphaCommandsRoot;
import alpha.commands.model.generator.java.AlphaJavaCommandCategory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class AlphaJavaCommandsGenerator implements IGenerator {
  @Extension
  private AlphaJavaCommandCategory javaCat = new AlphaJavaCommandCategory();

  public CharSequence javaRootPath() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("./src-gen/");
    return _builder;
  }

  public String targetProject() {
    return "alpha.commands";
  }

  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    EObject _get = input.getContents().get(0);
    final AlphaCommandsRoot root = ((AlphaCommandsRoot) _get);
    EList<AlphaCommandCategory> _categories = root.getCategories();
    for (final AlphaCommandCategory cat : _categories) {
      {
        fsa.generateFile(this.categoryBaseFilepath(cat), this.javaCat.generateBase(cat));
        fsa.generateFile(this.categoryFilepath(cat), this.javaCat.generate(cat));
      }
    }
  }

  public String categoryFilepath(final AlphaCommandCategory cat) {
    CharSequence _javaRootPath = this.javaRootPath();
    String _plus = (_javaRootPath + "/alpha/commands/");
    String _firstUpper = StringExtensions.toFirstUpper(cat.getName());
    String _plus_1 = (_plus + _firstUpper);
    return (_plus_1 + ".java");
  }

  public String categoryBaseFilepath(final AlphaCommandCategory cat) {
    CharSequence _javaRootPath = this.javaRootPath();
    String _plus = (_javaRootPath + "/alpha/commands/");
    String _firstUpper = StringExtensions.toFirstUpper(cat.getName());
    String _plus_1 = (_plus + _firstUpper);
    return (_plus_1 + "Base.java");
  }
}
