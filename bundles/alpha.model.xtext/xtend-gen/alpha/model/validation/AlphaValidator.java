/**
 * generated by Xtext 2.13.0
 */
package alpha.model.validation;

import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.issue.AlphaIssue;
import alpha.model.validation.AbstractAlphaValidator;
import com.google.common.base.Objects;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class AlphaValidator extends AbstractAlphaValidator {
  private void flagEditor(final AlphaIssue.TYPE type, final String message, final EObject source, final EStructuralFeature feature, final int index) {
    boolean _equals = Objects.equal(type, AlphaIssue.TYPE.ERROR);
    if (_equals) {
      this.error(message, source, feature, index);
    }
    boolean _equals_1 = Objects.equal(type, AlphaIssue.TYPE.WARNING);
    if (_equals_1) {
      this.warning(message, source, feature, index);
    }
  }
  
  @Check
  public void checkRoot(final AlphaRoot root) {
    final List<AlphaIssue> issues = AlphaInternalStateConstructor.compute(root);
    final Function1<AlphaIssue, Boolean> _function = (AlphaIssue i) -> {
      return Boolean.valueOf(EcoreUtil.isAncestor(root, i.getSource()));
    };
    final Consumer<AlphaIssue> _function_1 = (AlphaIssue i) -> {
      this.flagEditor(i.getType(), i.getMessage(), i.getSource(), i.getFeature(), ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
    };
    IterableExtensions.<AlphaIssue>filter(issues, _function).forEach(_function_1);
  }
  
  @Check
  public Object checkSystem(final AlphaSystem system) {
    return null;
  }
}
