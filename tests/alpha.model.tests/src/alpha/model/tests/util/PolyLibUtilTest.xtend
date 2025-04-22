package alpha.model.tests.util

import fr.irisa.cairn.jnimap.isl.ISLSet
import fr.irisa.cairn.jnimap.polylib.PolyLibPolyhedron
import org.junit.Test

import static alpha.model.util.ISLUtil.*
import static alpha.model.util.PolyLibUtil.*
import static org.junit.Assert.*
import fr.irisa.cairn.jnimap.polylib.PolyLibMatrix
import fr.irisa.cairn.jnimap.isl.ISLMap

class PolyLibUtilTest {
	
	/*
	 * Helper Function(s)
	 */
	static def boolean isEqual(PolyLibMatrix m1, PolyLibMatrix m2) {
    	if(m1.nbColumns != m2.nbColumns) return false
    	else if(m1.nbRows != m2.nbRows) return false
    	else {
    		for(var i = 0; i < m1.nbRows; i++) {
    			for(var j = 0; j < m1.nbColumns; j++) {
    				if(m1.getAt(i, j) != m2.getAt(i, j)) { 
    					return false
    				}
    			}
    		}
    	}	
    	true
    }
	
	/*
	 * Tests
	 */
	 
	 @Test
	def testPolyLibMatrixToISLSetOne() {
    	val ISLSet set = toISLSet("[N] -> {[i, j] : 0 <= i <= N and 0 <= j <= i}")
    	val PolyLibPolyhedron polyhedron = PolyLibPolyhedron.createFromLongMatrix(set.copy.getBasicSetAt(0).toPolyLibArray)
    	val output = toISLSet(PolyLibMatrix.buildFromConstraints(polyhedron), set.space)
		assertTrue(set.copy.isEqual(output))
    }
    
    @Test
   	def testPolyLibPolyhedronToISLSetOne() {
    	val ISLSet set = toISLSet("[N] -> {[i, j] : 0 <= i <= N and 0 <= j <= i}")
    	val PolyLibPolyhedron polyhedron = PolyLibPolyhedron.createFromLongMatrix(set.copy.getBasicSetAt(0).toPolyLibArray)
    	val output = toISLSet(polyhedron, set.space)
		assertTrue(set.copy.isEqual(output))
    }
     
    @Test
    def testISLMapToPolyLibMatrixOne() {
    	val ISLMap map = toISLMap("{[i, j] -> [i, j]}")
    	val PolyLibMatrix expected = PolyLibMatrix.allocate(2, 3)
    	
    	expected.setAt(0, 0, 1)
    	expected.setAt(1, 1, 1)
    	
    	assertTrue(expected.isEqual(toPolyLibMatrix(map)))
    }
    
    @Test
    def testISLMapToPolyLibMatrixTwo() {
    	val ISLMap map = toISLMap("[N] -> {[i, j] -> [i + 2 * j + 1, N - j]}")
    	val PolyLibMatrix expected = PolyLibMatrix.allocate(2, 4)

    	expected.setAt(0, 0, 1)
    	expected.setAt(0, 1, 2)
    	expected.setAt(0, 3, 1)
    	expected.setAt(1, 1, -1)
    	expected.setAt(1, 2, 1)
    	
    	assertTrue(expected.isEqual(toPolyLibMatrix(map)))
    }

    //The set and it's dual should be the same
    @Test 
	def testRaysToDualSetOne() {
    	val ISLSet set = toISLSet("[N] -> {[i, j] : 0 <= i}")
    	val PolyLibPolyhedron polyhedron = PolyLibPolyhedron.createFromLongMatrix(set.copy.getBasicSetAt(0).toPolyLibArray)
    	val output = toDualISLSet(polyhedron, set.space)
    	println(output.copy)
		assertTrue(set.copy.isEqual(output))
    }
    
        
	@Test 
	def testRaysToDualSetTwo() {
    	val ISLSet set = toISLSet("[N] -> {[i, j] : 0 <= i <= N and i <= j <= 2 * i}")
    	val ISLSet correctOutput = toISLSet("[N] -> {[i, j] : -i <= j and -i <= 2 * j}")
    	val PolyLibPolyhedron polyhedron = PolyLibPolyhedron.createFromLongMatrix(set.copy.getBasicSetAt(0).toPolyLibArray)
    	val output = toDualISLSet(polyhedron, set.space)
		assertTrue(correctOutput.copy.isEqual(output))
    }
    
}