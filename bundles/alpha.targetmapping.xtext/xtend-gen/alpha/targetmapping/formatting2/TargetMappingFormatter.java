/**
 * generated by Xtext 2.18.0.M3
 */
package alpha.targetmapping.formatting2;

import alpha.model.AlphaExpression;
import alpha.model.AlphaPackage;
import alpha.model.AlphaRoot;
import alpha.model.AlphaSystem;
import alpha.model.CaseExpression;
import alpha.model.StandardEquation;
import alpha.model.SystemBody;
import alpha.model.Variable;
import alpha.model.formatting2.AlphaFormatter;
import alpha.targetmapping.MemoryMapping;
import alpha.targetmapping.MemorySpace;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.services.TargetMappingGrammarAccess;
import com.google.inject.Inject;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class TargetMappingFormatter extends AlphaFormatter {
  @Inject
  @Extension
  private TargetMappingGrammarAccess _targetMappingGrammarAccess;
  
  protected void _format(final TargetMapping targetMapping, @Extension final IFormattableDocument document) {
    EList<MemorySpace> _memorySpaces = targetMapping.getMemorySpaces();
    for (final MemorySpace memorySpace : _memorySpaces) {
      document.<MemorySpace>format(memorySpace);
    }
  }
  
  protected void _format(final MemorySpace memorySpace, @Extension final IFormattableDocument document) {
    EList<MemoryMapping> _memoryMaps = memorySpace.getMemoryMaps();
    for (final MemoryMapping memoryMapping : _memoryMaps) {
      document.<MemoryMapping>format(memoryMapping);
    }
  }
  
  public void format(final Object targetMapping, final IFormattableDocument document) {
    if (targetMapping instanceof AlphaPackage) {
      _format((AlphaPackage)targetMapping, document);
      return;
    } else if (targetMapping instanceof AlphaSystem) {
      _format((AlphaSystem)targetMapping, document);
      return;
    } else if (targetMapping instanceof CaseExpression) {
      _format((CaseExpression)targetMapping, document);
      return;
    } else if (targetMapping instanceof StandardEquation) {
      _format((StandardEquation)targetMapping, document);
      return;
    } else if (targetMapping instanceof AlphaExpression) {
      _format((AlphaExpression)targetMapping, document);
      return;
    } else if (targetMapping instanceof AlphaRoot) {
      _format((AlphaRoot)targetMapping, document);
      return;
    } else if (targetMapping instanceof SystemBody) {
      _format((SystemBody)targetMapping, document);
      return;
    } else if (targetMapping instanceof Variable) {
      _format((Variable)targetMapping, document);
      return;
    } else if (targetMapping instanceof XtextResource) {
      _format((XtextResource)targetMapping, document);
      return;
    } else if (targetMapping instanceof TargetMapping) {
      _format((TargetMapping)targetMapping, document);
      return;
    } else if (targetMapping instanceof MemorySpace) {
      _format((MemorySpace)targetMapping, document);
      return;
    } else if (targetMapping instanceof EObject) {
      _format((EObject)targetMapping, document);
      return;
    } else if (targetMapping == null) {
      _format((Void)null, document);
      return;
    } else if (targetMapping != null) {
      _format(targetMapping, document);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(targetMapping, document).toString());
    }
  }
}
