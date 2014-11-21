// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 3 
// SpellChecker.java
// command line application to spell-check a text file  
// *****************************

import java.util.*; 
import java.io.*; 

public class SpellChecker {

	public static void main(String[] args) throws FileNotFoundException {

		File bigDictionary = new File(args[0]);
		File smallDictionary = new File(args[1]); 
		File inputFile = new File(args[2]); 

		Scanner bigRead = new Scanner(bigDictionary); 
		Scanner smallRead = new Scanner(smallDictionary);
		Scanner fileRead = new Scanner(inputFile); 

		// add dictionaries to a hash table 
		QuadraticProbingHashTable table = addDictionaries(bigRead, smallRead);
		
		int lineNumber = 1; 
		while(fileRead.hasNextLine()) {

			String[] stripped = stripAll(fileRead.nextLine().split("\\s+")); 
			
			for (String word : stripped) {
				if (table.contains(word) || word.equals("")) {
					// do nothing 
				} else {
					OptionFinder findOptions = new OptionFinder(word, table); 
					if (!findOptions.areOptions()) {
						System.out.println("line " + lineNumber + ": " + word 
							+ " - NO OPTIONS FOUND");
					} else { 
						System.out.println("line " + lineNumber + ": " + word
									+ " - " + findOptions.getOptions());
					}
				}
			lineNumber++; 
			}
		}
	}

	private static QuadraticProbingHashTable addDictionaries(Scanner bigRead, Scanner smallRead) {
		// reads two dictionary files, adds words to hash table, returns table 
		QuadraticProbingHashTable table = new QuadraticProbingHashTable(); 
		while (bigRead.hasNextLine()) {
			String[] stripped = strip(bigRead.nextLine().split("\\s+")); 
			for (String i : stripped) {
				table.insert(i); 
			}
		}
		while (smallRead.hasNextLine()) {
			String[] stripped = strip(smallRead.nextLine().split("\\s+")); 
			for (String i : stripped) {
				table.insert(i); 
			}
		}
		return table; 
	}

	private static String[] strip(String[] unstripped) {
		// lowercases and removes trailing . or , from all strings in an array 
		ArrayList<String> stripped = new ArrayList<String>(); 
		for (String i : unstripped) {
			stripped.add(i.toLowerCase().replaceAll("[.,]+$", "")); 
			// to remove all punctuation: 
			// stripped.add(i.toLowerCase().replaceAll("[^a-zA-Z]+$", "").replaceFirst("^[^a-zA-Z]+", "")); 
		}
		return stripped.toArray(new String[stripped.size()]); 
	}

	private static String[] stripAll(String[] unstripped) {
		// lowercases and removes trailing . or , from all strings in an array 
		ArrayList<String> stripped = new ArrayList<String>(); 
		for (String i : unstripped) {
			stripped.add(i.toLowerCase().replaceAll("[^a-zA-Z]+$", "").replaceFirst("^[^a-zA-Z]+", "")); 
		}
		return stripped.toArray(new String[stripped.size()]); 
	}

}