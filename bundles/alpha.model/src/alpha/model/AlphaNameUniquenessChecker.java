package alpha.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import alpha.model.issue.AlphaIssue;
import alpha.model.issue.DuplicatePolyObjectIssue;
import alpha.model.issue.DuplicateStandardEquationIssue;
import alpha.model.issue.DuplicateSystemIssue;
import alpha.model.issue.DuplicateUseEquationIssue;
import alpha.model.issue.DuplicateVariableIssue;

public class AlphaNameUniquenessChecker {
	
	private static final IQualifiedNameProvider provider = new DefaultDeclarativeQualifiedNameProvider();
	
	public static List<AlphaIssue> check(List<AlphaRoot> roots) {
		List<AlphaIssue> issues = new LinkedList<>();
		
		if (roots.isEmpty()) return issues;

		ResourceSet rset = roots.get(0).eResource().getResourceSet();
		
		if (!roots.stream().allMatch(r->r.eResource().getResourceSet() == rset)) {
			throw new RuntimeException("Expecting Alpha programs in the same resource set.");
		}

		Map<String, List<AlphaSystem>> nameMap = new HashMap<>();
		//Collect all systems by its qname
		TreeIterator<Object> iterator = EcoreUtil.getAllProperContents(rset, false);
		
		while (iterator.hasNext()) {
			Object current = iterator.next();
			if (current instanceof AlphaSystem) {
				iterator.prune();
				String qname = provider.getFullyQualifiedName((AlphaSystem)current).toString();
				checkAndAdd(qname, (AlphaSystem)current, nameMap);
				issues.addAll(check((AlphaSystem)current));
			}
		}
		
		nameMap.values().stream().filter(l->l.size()>1).forEach(l->{
			for (AlphaSystem system : l) {
				issues.add(new DuplicateSystemIssue(system));
			}
		});
		
		return issues;
	}
	
	public static List<AlphaIssue> check(AlphaRoot root) {
		return check(Arrays.asList(root));
	}

	public static List<AlphaIssue> check(AlphaSystem system) {
		List<AlphaIssue> issues = new LinkedList<>();
		
		
		//First check for name conflicts between variables and polyhedral objects
		{
			Map<String, List<AlphaNode>> nameMap = new HashMap<>();
			
			system.getVariables().stream().forEach(v->{
				checkAndAdd(v.getName(), v, nameMap);
			});
			
			system.getDefinedObjects().stream().forEach(v->{
				checkAndAdd(v.getName(), v, nameMap);
			});
			
			nameMap.values().stream().filter(l->l.size()>1).forEach(l->{
				for (AlphaNode node : l) {
					if (node instanceof Variable)
						issues.add(new DuplicateVariableIssue((Variable)node));
					if (node instanceof PolyhedralObject)
						issues.add(new DuplicatePolyObjectIssue((PolyhedralObject)node));
				}
			});
		}
		
		//Then check name conflicts between equations
		{

			Map<String, List<AlphaNode>> nameMap = new HashMap<>();
			
			system.getEquations().stream().forEach(e->{
				checkAndAdd(e.getVariable().getName(), e, nameMap);
			});
			
			system.getUseEquations().stream().forEach(e->{
				EcoreUtil.getAllContents(e.getOutputExprs()).forEachRemaining(l->{
					if (l instanceof VariableExpression) {
						String name = ((VariableExpression)l).getVariable().getName();
						checkAndAdd(name, e, nameMap);
						
					}
				});
			});
			
			nameMap.values().stream().filter(l->l.size()>1).forEach(l->{
				for (AlphaNode node : l) {
					if (node instanceof StandardEquation)
						issues.add(new DuplicateStandardEquationIssue((StandardEquation)node));
					if (node instanceof UseEquation)
						issues.add(new DuplicateUseEquationIssue((UseEquation)node));
				}
			});
		}
		
		return issues;
	}
	
	private static <T> void checkAndAdd(String key, T obj, Map<String,List<T>> map) {
		if (!map.containsKey(key)) {
			map.put(key, new LinkedList<T>());
		}
		map.get(key).add(obj);
	}
}