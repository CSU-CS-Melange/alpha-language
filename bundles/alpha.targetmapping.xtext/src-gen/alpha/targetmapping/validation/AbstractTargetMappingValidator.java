/*
 * generated by Xtext 2.18.0.M3
 */
package alpha.targetmapping.validation;

import alpha.model.validation.AlphaValidator;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public abstract class AbstractTargetMappingValidator extends AlphaValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>(super.getEPackages());
		result.add(EPackage.Registry.INSTANCE.getEPackage("alpha.targetmapping"));
		result.add(EPackage.Registry.INSTANCE.getEPackage("alpha.model"));
		return result;
	}
}
