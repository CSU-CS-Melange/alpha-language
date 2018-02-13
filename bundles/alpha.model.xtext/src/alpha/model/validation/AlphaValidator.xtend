/*
 * generated by Xtext 2.13.0
 */
package alpha.model.validation

import alpha.model.AlphaNameUniquenessChecker
import alpha.model.AlphaRoot
import alpha.model.AlphaSystem
import alpha.model.JNIDomainCalculator
import alpha.model.issue.AlphaIssue
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.ValidationMessageAcceptor
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
class AlphaValidator extends AbstractAlphaValidator {
	
//	public static val INVALID_NAME = 'invalidName'
//
//	@Check
//	def checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.name.charAt(0))) {
//			warning('Name should start with a capital', 
//					AlphaPackage.Literals.GREETING__NAME,
//					INVALID_NAME)
//		}
//	}
//
//	@Inject
//	ResourceDescriptionsProvider resourceDescriptionsProvider;
//	@Inject
//	IContainer.Manager containerManager;

	//helper to switch between error/warning 
	private def flagEditor(AlphaIssue.TYPE type, String message, EObject source, EStructuralFeature feature, int index) {
		
		
		if (type == AlphaIssue.TYPE.ERROR) {
			error(message, source, feature, index)
		}
		if (type == AlphaIssue.TYPE.WARNING) {
			warning(message, source, feature, index)
		}	
	}
	
	@Check
	def checkRoot(AlphaRoot root) {
		val issues = AlphaNameUniquenessChecker.check(root);
		issues.filter[i|EcoreUtil.isAncestor(root, i.source)]
				.forEach[i|flagEditor(i.type, i.message, i.source, i.feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX)];
	}
	

	@Check
	def checkSystem(AlphaSystem system) {
		val issues = JNIDomainCalculator.calculate(system);
		
		issues.forEach[i|flagEditor(i.type, i.message, i.source, i.feature, ValidationMessageAcceptor.INSIGNIFICANT_INDEX)]
				
		//following from: http://www.eclipse.org/forums/index.php/mv/msg/261440/754503/#msg_754503
//		val names = new HashSet<QualifiedName>();
//		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(system.eResource());
//		val resourceDescription = resourceDescriptions.getResourceDescription(system.eResource().getURI());
//		for (IContainer c : containerManager.getVisibleContainers(resourceDescription, resourceDescriptions)) {
//			for (IEObjectDescription od : c.getExportedObjectsByType(ModelPackage.Literals.ALPHA_SYSTEM)) {
//				if (!names.add(od.getQualifiedName())) {
//					error("duplicate system", ModelPackage.Literals.ALPHA_SYSTEM__NAME);
//				}
//			}
//		}
		
//		error("Test Error", system, ModelPackage.Literals.ALPHA_SYSTEM__NAME, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
//		error("Test Error", system.inputs.get(0), ModelPackage.Literals.VARIABLE__NAME, ValidationMessageAcceptor.INSIGNIFICANT_INDEX);
	}

//	@Check
//	def checkVariable(Variable v) {
//		if (v.domainExpr.type != POLY_OBJECT_TYPE::SET) {
//			
//		}
////		if (v.errorMessage !== null && v.errorMessage.length > 0) {
////			error(v.errorMessage, ModelPackage.Literals.VARIABLE__DOMAIN_EXPR);
////		}	
//	}
//	
//	@Check
//	def checkJNIDomain(JNIDomain dom) {
//		if (dom.errorMessage !== null && dom.errorMessage.length > 0) {
//			error(dom.errorMessage, ModelPackage.Literals.JNI_DOMAIN__ISL_STRING);
//		}
//	}
//	
//	@Check
//	def checkJNIRelation(JNIRelation rel) {
//		if (rel.errorMessage !== null && rel.errorMessage.length > 0) {
//			error(rel.errorMessage, ModelPackage.Literals.JNI_RELATION__ISL_STRING);
//		}
//	}
//	
//	@Check
//	def checkJNIFunction(JNIFunction fun) {
//		if (fun.errorMessage !== null && fun.errorMessage.length > 0) {
//			error(fun.errorMessage, ModelPackage.Literals.JNI_FUNCTION__ALPHA_STRING);
//		}
//	}
//	
//	@Check
//	def checkDefinedObject(DefinedObject expr) {
//		if (expr.errorMessage !== null && expr.errorMessage.length > 0) {
//			error(expr.errorMessage, ModelPackage.Literals.DEFINED_OBJECT__OBJECT);
//		}
//	}
//	
//	@Check
//	def checkUnaryCalculatorExpression(UnaryCalculatorExpression expr) {
//		if (expr.errorMessage !== null && expr.errorMessage.length > 0) {
//			error(expr.errorMessage, ModelPackage.Literals.UNARY_CALCULATOR_EXPRESSION__OPERATOR);
//		}
//	}
//	
//	@Check
//	def checkBinaryCalculatorExpression(BinaryCalculatorExpression expr) {
//		if (expr.errorMessage !== null && expr.errorMessage.length > 0) {
//			error(expr.errorMessage, ModelPackage.Literals.BINARY_CALCULATOR_EXPRESSION__OPERATOR);
//		}
//	}
//	
//	@Check
//	def checkVariableDomain(VariableDomain vdom) {
//		if (vdom.errorMessage !== null && vdom.errorMessage.length > 0) {
//			error(vdom.errorMessage, ModelPackage.Literals.VARIABLE_DOMAIN__VARIABLE);
//		}
//	}
//	
//	@Check
//	def checkRectangularDomain(RectangularDomain rdom) {
//		if (rdom.errorMessage !== null && rdom.errorMessage.length > 0) {
//			error(rdom.errorMessage, ModelPackage.Literals.RECTANGULAR_DOMAIN__UPPER_BOUNDS);
//		}
//		if (rdom.upperBounds !== null && rdom.indexNames !== null && 
//			rdom.indexNames.length > 0 && rdom.upperBounds.length != rdom.indexNames.length) {
//			warning("Length of the index names do not match the domain. Specified names are unused.", ModelPackage.Literals.RECTANGULAR_DOMAIN__INDEX_NAMES);
//		}
//	}
	
//	@Check
//	def checkParameterDomain(ParameterDomain pdom) {
//		try {
//			pdom.parameterDomain
//		} catch (RuntimeException re) {
//			error(re.message, ModelPackage.Literals.PARAMETER_DOMAIN__ISL_STRING);
//		}
//	}
}
