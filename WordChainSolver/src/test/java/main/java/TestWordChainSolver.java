package main.java;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestWordChainSolver {
	static WordChainSolver tester = null;
	static String _FIRST;
	static String _LAST;
	static String _FILEPATH = "C:\\words.txt";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		_FIRST = "cat";
		_LAST = "dog";
		
		tester = new WordChainSolver(_FILEPATH);
	}

	@Test
	public void testDictionarySize() {
		assertTrue(tester.getDictSet().size()== 355433);    	
	}
	
	@Test
	public void testCreateNextLevelForGivenWords() {
		
		Map<String, List<String>> map = tester.createNextLevelForGivenWords(_FIRST);
		
		List<String> list = map.get(_FIRST);
		boolean found = true;
		for(String item : list) {
			if(!tester.getDictSet().contains(item)) {
				found = false;
				break;
			}
		}
		
		assertTrue(found);
		
	}

	@Test
	public void testIsInDictionary() {
		boolean res = tester.isValidInput(_FIRST, _LAST);
		assertTrue(res);
	}
	
	@Test
	public void wordChainFound() {
		tester.wordChain(_FIRST, _LAST);
		boolean res = tester.showPath();
		
		assertTrue(res);
	}
	
	@Test
	public void wordChainNotFound() {
		tester.wordChain("gerald", "gerahs");
		boolean res = tester.showPath();
		
		assertFalse(res);
	}
}
