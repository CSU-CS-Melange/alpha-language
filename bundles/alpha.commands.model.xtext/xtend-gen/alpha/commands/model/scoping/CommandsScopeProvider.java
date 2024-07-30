/**
 * generated by Xtext 2.15.0
 */
package alpha.commands.model.scoping;

import alpha.commands.model.AlphaCommand;
import alpha.commands.model.ArgumentBinding;
import alpha.commands.model.ArgumentRenaming;
import alpha.commands.model.CommandBinding;
import alpha.commands.model.ModelPackage;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
@SuppressWarnings("all")
public class CommandsScopeProvider extends AbstractCommandsScopeProvider {
  @Inject
  private IQualifiedNameProvider nameProvider;

  public IScope getScope(final EObject context, final EReference reference) {
    IScope _xblockexpression = null;
    {
      if (((context instanceof CommandBinding) && Objects.equal(reference, ModelPackage.Literals.COMMAND_BINDING__BIND_TARGET_COMMAND))) {
        final List<AlphaCommand> scope = EcoreUtil2.<AlphaCommand>getAllContentsOfType(EcoreUtil2.getRootContainer(context), AlphaCommand.class);
        return Scopes.<EObject>scopeFor(scope, this.nameProvider, IScope.NULLSCOPE);
      }
      if (((context instanceof ArgumentBinding) && Objects.equal(reference, ModelPackage.Literals.ARGUMENT_BINDING__BIND_TARGET))) {
        EObject _eContainer = context.eContainer();
        final AlphaCommand targetCommand = ((CommandBinding) _eContainer).getBindTargetCommand();
        if (((targetCommand != null) && (targetCommand.getSignature() != null))) {
          return Scopes.scopeFor(targetCommand.getSignature().getArguments());
        }
      }
      if (((context instanceof ArgumentBinding) && Objects.equal(reference, ModelPackage.Literals.ARGUMENT_BINDING__BIND_SOURCE))) {
        EObject _eContainer_1 = context.eContainer().eContainer();
        final AlphaCommand sourceCommand = ((AlphaCommand) _eContainer_1);
        return Scopes.scopeFor(sourceCommand.getSignature().getArguments());
      }
      if (((context instanceof ArgumentRenaming) && Objects.equal(reference, ModelPackage.Literals.ARGUMENT_RENAMING__BIND_TARGET))) {
        EObject _eContainer_2 = context.eContainer();
        final AlphaCommand targetCommand_1 = ((CommandBinding) _eContainer_2).getBindTargetCommand();
        if (((targetCommand_1 != null) && (targetCommand_1.getSignature() != null))) {
          return Scopes.scopeFor(targetCommand_1.getSignature().getArguments());
        }
      }
      if (((context instanceof ArgumentRenaming) && Objects.equal(reference, ModelPackage.Literals.ARGUMENT_RENAMING__BIND_SOURCE))) {
        EObject _eContainer_3 = context.eContainer().eContainer();
        final AlphaCommand sourceCommand_1 = ((AlphaCommand) _eContainer_3);
        return Scopes.scopeFor(sourceCommand_1.getSignature().getArguments());
      }
      _xblockexpression = super.getScope(context, reference);
    }
    return _xblockexpression;
  }
}