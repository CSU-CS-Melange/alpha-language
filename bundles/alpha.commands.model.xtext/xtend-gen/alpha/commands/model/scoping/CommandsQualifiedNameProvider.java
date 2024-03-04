package alpha.commands.model.scoping;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

@SuppressWarnings("all")
public class CommandsQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {
  @Override
  protected QualifiedName qualifiedName(final Object ele) {
    throw new Error("Unresolved compilation problems:"
      + "\nAlphaCommand cannot be resolved to a type."
      + "\nAlphaCommand cannot be resolved to a type."
      + "\nlabel cannot be resolved");
  }
}
