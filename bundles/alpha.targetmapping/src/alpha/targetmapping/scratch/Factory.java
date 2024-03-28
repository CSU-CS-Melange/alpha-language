package alpha.targetmapping.scratch;

import alpha.targetmapping.impl.TargetmappingFactoryImpl;
import alpha.targetmapping.TargetmappingFactory;

public class Factory {
	
	public static TargetmappingFactory init() {
		return TargetmappingFactoryImpl.init(); 
	}

}
