/*
 * generated by Xtext 2.12.0
 */
package alpha


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class AlphaStandaloneSetup extends AlphaStandaloneSetupGenerated {

	def static void doSetup() {
		new AlphaStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
