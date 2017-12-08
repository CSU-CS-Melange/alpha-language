package alpha.testframework;

import java.io.IOException;

import org.junit.Test;

import alpha.model.AlphaModelLoader;
import alpha.model.AlphaRoot;
import alpha.model.JNIDomainCalculator;
import alpha.model.util.PrintAST;

public class Driver {
	
	
	public static void main(String[] args) {
//	@Test
//	public void test() {
		
		try {
			AlphaRoot root = AlphaModelLoader.loadModel("resources/src-alpha/basic/ANN.alpha");
//			AlphaRoot root = AlphaModelLoader.loadModel("/Users/tyuki/projects/Alpha/alpha-language/bundles/alpha.testframework/resources/src-alpha/basic/ANN.alpha");
			
			

			System.out.println(PrintAST.print(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
