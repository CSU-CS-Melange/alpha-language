package alpha.commands.model.generator;

import alpha.commands.model.AlphaCommandCategory;
import alpha.commands.model.AlphaCommandsRoot;
import alpha.commands.model.generator.groovy.AlphaGroovyCommandCategory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class AlphaGroovyCommandsGenerator implements IGenerator {
  @Extension
  private CommonExtensions commonEx = new CommonExtensions();

  @Extension
  private AlphaGroovyCommandCategory groovyCat = new AlphaGroovyCommandCategory();

  public CharSequence groovyRootPath() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("./src-gen/");
    return _builder;
  }

  public String targetProject() {
    return "alpha.commands.groovy";
  }

  @Override
  public void doGenerate(final Resource input, final IFileSystemAccess fsa) {
    EObject _get = input.getContents().get(0);
    final AlphaCommandsRoot root = ((AlphaCommandsRoot) _get);
    fsa.generateFile(this.alphaScriptPath().toString(), this.generate(root));
  }

  public CharSequence alphaScriptPath() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _groovyRootPath = this.groovyRootPath();
    _builder.append(_groovyRootPath);
    _builder.append("/alpha/commands/groovy/AlphaScript.groovy");
    return _builder;
  }

  public CharSequence generate(final AlphaCommandsRoot root) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package alpha.commands.groovy");
    _builder.newLine();
    _builder.newLine();
    final Function1<CharSequence, CharSequence> _function = (CharSequence o) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("import ");
      _builder_1.append(o);
      return _builder_1.toString();
    };
    String _join = IterableExtensions.<CharSequence>join(this.commonEx.collectUsedClasses(root), "\n", _function);
    _builder.append(_join);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("abstract class AlphaScript extends AbstractAlphaScript {");
    _builder.newLine();
    _builder.newLine();
    {
      EList<AlphaCommandCategory> _categories = root.getCategories();
      for(final AlphaCommandCategory cat : _categories) {
        _builder.append("\t");
        CharSequence _generate = this.groovyCat.generate(cat);
        _builder.append(_generate, "\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
