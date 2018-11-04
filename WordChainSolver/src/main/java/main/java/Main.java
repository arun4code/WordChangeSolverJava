package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {
		String filePath = null;
		if(args != null && args.length == 1) {
			filePath = args[0];
		}
		else {
			System.out.println("File path is missing. taking default dictionary file for src folder");
			System.exit(1);
		}
		
		/*
		 * Step1: Take user input words
		 */
		String first = "";
		String last = "";
		
		
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Please enter inputs with space seperated");
        
        StringTokenizer toekns;
		try {
			toekns = new StringTokenizer(br.readLine().toLowerCase());
			first = toekns.nextToken();
	        last = toekns.nextToken();
	        
	        br.close();
		} catch (IOException e) {
			System.out.println("ERROR: while giving input words");
		}
        
        
		/*
		 * Step2: validate user input words
		 */
        if(first.length() < 1 || last.length() < 1 || first.length() != last.length()) {
        	System.out.println("ERROR: wrong input data.");
        	System.exit(1);        	
        }        
        
        if(first.equals(last)) {
        	System.out.println("First and last word is same:" + first +" > " + last);
        	System.exit(0);
        }
    
        /*
		 * Step3: initialize dictionary, and validate user input against dictionary.
		 */
		WordChainSolver wcSolver = new WordChainSolver(filePath);
		if(!wcSolver.isValidInput(first, last)) {
			System.out.println("First and last word should be in dictionary");
			System.exit(1);
		}
		
		/*
		 * Step4: Find out word chain for given input, and show the path.
		 */
		wcSolver.wordChain(first, last);
		
		if(wcSolver.showPath()) {
			System.out.println("\nYes: Word chain is found.");
		}
		else {
			System.out.println("\nNO: There is no word chain exist for this input.");
		}
		System.exit(0);
	}

}
