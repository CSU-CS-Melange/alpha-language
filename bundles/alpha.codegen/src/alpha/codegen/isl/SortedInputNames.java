package alpha.codegen.isl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import fr.irisa.cairn.jnimap.isl.ISLAff;

public class SortedInputNames {
	
	public static List<String> getInputNames(ISLAff aff) {
		List<String> names = aff.getInputNames();
		
		names.sort(Comparator.comparingInt(String::length).reversed());
		
		return names;
	}

}
