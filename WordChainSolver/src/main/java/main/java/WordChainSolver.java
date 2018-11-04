package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author bhard
 * @desc: 
 *  - this class constructor takes dictionary file path from argument.
 *  - all word comparison is in lower case.
 *  - search is based breath search (greedy algorithm) and then depth search.
 *  - if input variables (source, target) are not in dictionary, system will not accept.
 *  - change in words: 
 *  	- only one operation is allowed at a time.
 *  	- length of word is same. so no deletion or insertion of new letter.
 *  	- this program handle all words, which are available in given dictionary.
 *  	- no operation on special character.
 */
public class WordChainSolver {

	private Set<String> dictSet = new HashSet<>();
	private List<List<String>> result = new ArrayList<>();
	private Hashtable<String, Integer> dictTable = new Hashtable<>();
	private Stack<String> pathStack = new Stack<>();
	private Set<String> visitedSet = new HashSet<>();
	private Map<String, List<String>> nextLevelMap = new HashMap<>();
	
	public Set<String> getDictSet() {
		return dictSet;
	}

	public void setDictSet(Set<String> dictSet) {
		this.dictSet = dictSet;
	}

	public WordChainSolver(final String filePath) {
		this.dictSet = loadDictionary(filePath);		//load dictionary into Set
	}

	private Set<String> loadDictionary(final String filenName) {
		final Set<String> dictSet = new HashSet<String>();
		BufferedReader bfreader = null;
		try {
			bfreader = new BufferedReader(new FileReader(filenName));
	        while(true) {
	            String line;
				
					line = bfreader.readLine();
				
	            if (line != null) {
	            	dictSet.add(line.toLowerCase());
	            }
	            else break;
	        }
	        bfreader.close();
        } catch (final IOException e) {
        	System.out.println("Error while reading text dictionary file.");
        	if(bfreader != null) {
        		try {
					bfreader.close();
				} catch (final IOException e1) {
					return null;
				}
        	}
        	return null;
		}
		return dictSet;
	}


	public Map<String, List<String>> createNextLevelForGivenWords(final String first) {
		
		final List<String> list = new ArrayList<>();		
		
		StringBuilder sb = new StringBuilder(first);
		for(int i = 0; i < first.length(); i++) {
			
			for (char chr = 97; chr < 123; chr++) {		//from a to z, everything in lowercase
				sb.setCharAt(i, chr);
                
				if (first.equals(sb.toString()))
                	continue;
                
                if (isInDictionary(sb.toString()) 
                		&& !list.contains(sb.toString())
                		&& !visitedSet.contains(sb.toString())) {
                	list.add(sb.toString());
                }
            }
			sb = new StringBuilder(first);			
		}
		nextLevelMap.put(first, list);
		
		visitedSet.add(first);
		
		return nextLevelMap;
	}

	private boolean isInDictionary(final String word) {
		if(this.dictSet.contains(word)) {
			return true;
		}
		return false;
	}

	public boolean isValidInput(final String first, final String last) {
		if(this.dictSet.contains(first.toLowerCase()) && this.dictSet.contains(last.toLowerCase())) {
			return true;
		}
		return false;
	}

	
	public void wordChain(final String first, final String last) {
		
		createNextLevelForGivenWords(first);
		final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
		queue.offer(first);
		dictTable.put(first, 0);
		
		while(!queue.isEmpty()) {
            boolean end = false;
            for(int i = 0; i < queue.size(); i++) {
                final String cur = queue.poll();
                final List<String> neighbours = nextLevelMap.get(cur);

                for(final String neighbour : neighbours) {
                	createNextLevelForGivenWords(neighbour);
                	
                    if(!dictTable.containsKey(neighbour)) {
                        dictTable.put(neighbour, dictTable.get(cur)+1);
                        if(last.equals(neighbour))
                        	end = true;
                        else {
                            queue.offer(neighbour);
                        }
                    }
                }
            }
            if (end)
            	break;
        }
		
		searchPath(first, last);
	}
	
	
	private void searchPath(final String first, final String last) {
		pathStack.push(first);
		
		if(last.equals(first)) {
			result.add(new ArrayList<>(pathStack));
		}
		else {
			for(final String next : nextLevelMap.get(first)) {
				
				if(dictTable.containsKey(next) && dictTable.containsKey(first)) {
					if(dictTable.get(next) == dictTable.get(first) + 1) {
						searchPath(next, last);
					}
				}
			}			
		}
		pathStack.pop();
	}

	public boolean showPath() {		
		if(result != null && !result.isEmpty() && result.get(0).size() > 0) {
			int temp = 1;
	        for(final List<String> list : result) {
	            System.out.println("\nWord Chain " + temp);
	            
	            for(final String item : list)
	            	System.out.print(" > " + item);
	            temp++;
	        }
	        return true;
		}
		return false;	
	}	
}
