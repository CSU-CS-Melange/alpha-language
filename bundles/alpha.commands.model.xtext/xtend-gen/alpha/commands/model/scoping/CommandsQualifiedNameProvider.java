package alpha.commands.model.scoping;

import alpha.commands.model.AlphaCommand;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

@SuppressWarnings("all")
public class CommandsQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {
  @Override
  protected QualifiedName qualifiedName(final Object ele) {
    QualifiedName _xblockexpression = null;
    {
      if ((ele instanceof AlphaCommand)) {
        return QualifiedName.create(((AlphaCommand) ele).getLabel());
      }
      _xblockexpression = super.qualifiedName(ele);
    }
    return _xblockexpression;
  }
}
