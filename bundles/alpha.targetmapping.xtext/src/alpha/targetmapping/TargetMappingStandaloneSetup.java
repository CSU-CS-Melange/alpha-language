/*
 * generated by Xtext 2.22.0
 */
package alpha.targetmapping;


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class TargetMappingStandaloneSetup extends TargetMappingStandaloneSetupGenerated {

	public static void doSetup() {
		new TargetMappingStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}