package alpha.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

import alpha.model.AlphaInternalStateConstructor;
import alpha.model.AlphaRoot;
import alpha.model.AlphaVisitable;
import alpha.model.ModelPackage;
import alpha.model.exception.AlphaIssueException;
import alpha.model.issue.AlphaIssue;
import alpha.targetmapping.TargetMapping;
import alpha.targetmapping.TargetMappingInternalStateConstructor;
import alpha.targetmapping.TargetMappingStandaloneSetup;
import alpha.targetmapping.TargetmappingPackage;

/**
 * Class with static methods for interfacing with the textual representation of
 * the Alpha model and TargetMapping. The various load methods parse the Alpha file with 
 * Xtext parser, and then construct the ISL objects for each Alpha program.
 * 
 * An instance of resource set is maintained so that separate calls to load methods
 * can refer to previously loaded programs. This context is reset by calling initialize.
 * 
 * @author tyuki
 *
 */
public class AlphaLoader {
	
	private static Injector INJECTOR;
	private static XtextResourceSet RESOURCE_SET;
	
	static {
		initialize();
	}
	
	public static void initialize() {
		INJECTOR = new TargetMappingStandaloneSetup().createInjectorAndDoEMFRegistration();
		RESOURCE_SET = INJECTOR.getInstance(XtextResourceSet.class);
		RESOURCE_SET.getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
		RESOURCE_SET.getPackageRegistry().put(TargetmappingPackage.eNS_URI, TargetmappingPackage.eINSTANCE);
	}

	private AlphaLoader() {
	}

	/**
	 * Load the specified file as an Alpha program.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static AlphaRoot loadAlpha(String filename) throws IOException {
		final Resource res = RESOURCE_SET.getResource(URI.createFileURI(filename), true);
		
		EObject root = res.getContents().get(0);
		AlphaRoot toplevel = (AlphaRoot) root;
		List<Issue> xtextIssues = validate(toplevel);
		if (!xtextIssues.isEmpty()) {
			throw new RuntimeException(xtextIssues.toString());
		}
		
		List<AlphaIssue> issues = AlphaInternalStateConstructor.compute(toplevel);
		if (!issues.isEmpty()) {
			issues.stream().forEach(i->System.err.println(i.getMessage()));
			throw new AlphaIssueException(issues);
		}

		return toplevel;
	}
	public static AlphaRoot loadAlpha(File file) throws IOException {
		return loadAlpha(file.getAbsolutePath());
	}

	/**
	 * Load all files under the given directory. Calls
	 * {@link #loadModel(Collection)} after collecting the files in the given
	 * directory.
	 * 
	 * @param directory
	 * @return List<AlphaRoot>: list of top-level model elements for all the files
	 *         in the given directory
	 * @throws IOException
	 */
	public static List<AlphaRoot> loadAlphaDir(File dir) throws IOException {

		if (!dir.isDirectory())
			throw new RuntimeException("Expecting path to a directory @ loadModelDir");

		return loadAlpha(Arrays.asList(dir.listFiles()));
	}
	public static List<AlphaRoot> loadAlphaDir(String dirname) throws IOException {
		File dir = new File(dirname);
		return loadAlphaDir(dir);
	}

	/**
	 * A wrapper for calling {@link #loadModel(Collection)} with Paths instead of
	 * Files.
	 * 
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static List<AlphaRoot> loadAlphaFromPaths(Collection<Path> files) throws IOException {
		return loadAlpha(files.stream().map(f -> f.toFile()).collect(Collectors.toList()));
	}

	/**
	 * Load a list of files
	 * 
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public static List<AlphaRoot> loadAlpha(Collection<File> files) throws IOException {

		RESOURCE_SET.getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
		RESOURCE_SET.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

		List<AlphaRoot> roots = new ArrayList<>(files.size());

		for (File file : files) {
			RESOURCE_SET.getResources().add(RESOURCE_SET.createResource(URI.createFileURI(file.getPath())));
		}
		EcoreUtil.resolveAll(RESOURCE_SET);

		List<Issue> xtextIssues = new LinkedList<>();
		for (File file : files) {
			final Resource res = RESOURCE_SET.getResource(URI.createFileURI(file.getPath()), true);
			if (res.getContents().size() == 0) continue;
			
			EObject root = res.getContents().get(0);
			AlphaRoot toplevel = (AlphaRoot) root;
			roots.add(toplevel);
			xtextIssues.addAll(validate(toplevel));
		}

		if (!xtextIssues.isEmpty()) {
			throw new RuntimeException(xtextIssues.toString());
		}
		
		List<AlphaIssue> issues = AlphaInternalStateConstructor.compute(roots);
		if (!issues.isEmpty()) {
			throw new AlphaIssueException(issues);
		}

		return roots;
	}

	public static List<Issue> validate(AlphaVisitable alpha) {
		Resource resource = alpha.eResource();
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		
		//CheckMode.EXPENSIVE triggers CheckProgram
		List<Issue> issues = validator.validate(resource, CheckMode.FAST_ONLY, CancelIndicator.NullImpl);
		return issues;
	}
	
	public static void formatAlpha(String filename) throws IOException {
		AlphaRoot prog = loadAlpha(filename);

		RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				INJECTOR.getInstance(XtextResourceFactory.class));

		SaveOptions opt = SaveOptions.newBuilder().format().getOptions();

		XtextResource resource = (XtextResource) RESOURCE_SET.createResource(URI.createFileURI(filename));
		resource.getContents().add(prog);
		resource.save(opt.toOptionsMap());
	}

	public static void saveAsXML(AlphaRoot seq, String modelfilename) throws IOException {
		RESOURCE_SET.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMIResourceFactoryImpl());

		Resource resource = RESOURCE_SET.createResource(URI.createFileURI(modelfilename));
		resource.getContents().add(seq);
		resource.save(null);
	}

	/**
	 * Load the specified file as an Alpha program.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static TargetMapping loadTargetMapping(String filename) throws IOException {
		final Resource res = RESOURCE_SET.getResource(URI.createFileURI(filename), true);
		
		EObject root = res.getContents().get(0);
		TargetMapping toplevel = (TargetMapping) root;
		List<Issue> xtextIssues = validate(toplevel);
		if (!xtextIssues.isEmpty()) {
			throw new RuntimeException(xtextIssues.toString());
		}
		
		List<AlphaIssue> issues = TargetMappingInternalStateConstructor.compute(toplevel);
		if (!issues.isEmpty()) {
			issues.stream().forEach(i->System.err.println(i.getMessage()));
			throw new AlphaIssueException(issues);
		}

		return toplevel;
	}
	public static TargetMapping loadTargetMapping(File file) throws IOException {
		return loadTargetMapping(file.getAbsolutePath());
	}

	public static List<Issue> validate(TargetMapping tm) {
		Resource resource = tm.eResource();
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		
		List<Issue> issues = validator.validate(resource, CheckMode.FAST_ONLY, CancelIndicator.NullImpl);
		return issues;
	}
}
