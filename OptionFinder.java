
// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 3 
// OptionFinder.java
// given a misspelled word and dictionary hashtable, 
// suggests possible corrected spellings  
// *****************************

import java.util.*; 

public class OptionFinder {

	private String mispelled; 					// the mispelled word
	private QuadraticProbingHashTable table; 	// contains all correct words
	private ArrayList<String> variations; 		// all variations obtainable 
	private ArrayList<String> options; 			// variations contained in table
	
	private ArrayList<Character> mispelledChars; 
	private static final char[] ADDED_CHARACTERS  = 
				"abcdefghijklmnopqrstuvqxyz'".toCharArray(); 

	public OptionFinder(String m, QuadraticProbingHashTable t) {
		mispelled = m; 
		table = t; 
		variations = new ArrayList<String>(); 
		options = new ArrayList<String>(); 
		mispelledChars = wordToChars(); 
		findVariations(); 
		findOptions(); 
	}

	private void findVariations() {
		addCharacter(); 
		removeCharacter(); 
		switchAdjacent(); 
	}

	private void findOptions() {
		// checks all variations against table, adds correct ones to options
		for (String variation : variations) {
			if (table.contains(variation) && !options.contains(variation)) {
				options.add(variation); 
			} 
		}
	}

	public boolean areOptions() {
		return !options.isEmpty(); 
	}

	public String getOptions() {
		String string = ""; 
		for (String word : options) {
			string += word; 
			string += " "; 
		}
		return string; 
	}

	private void addCharacter() {
		for (char c : ADDED_CHARACTERS) {
			for (int i = 0; i < mispelled.length()+1; i++) {
				ArrayList addOne = new ArrayList<Character>(mispelledChars);
				addOne.add(i, c); 
				variations.add(arrayToString(addOne));
			}
		}
	}

	private void removeCharacter() {
		for (int i = 0; i < mispelled.length(); i++){
			ArrayList removeOne = new ArrayList<Character>(mispelledChars);
			removeOne.remove(i); 
			variations.add(arrayToString(removeOne)); 
		} 
	}

	private void switchAdjacent() {
		for (int i = 0; i < mispelled.length()-1; i++) {
			ArrayList switchNext = new ArrayList<Character>(mispelledChars);
			Collections.swap(switchNext, i, i+1); 
			variations.add(arrayToString(switchNext)); 
		}
	}

	private ArrayList<Character> wordToChars() {
		char[] chars = mispelled.toCharArray(); 
		ArrayList<Character> list = new ArrayList<Character>();
		for (char c : chars) {
			list.add(c); 
		}
		return list; 
	}

	private String arrayToString(ArrayList<Character> list) {
		String string = ""; 
		for (char c : list) {
			string += c; 
		}
		return string; 
	}


}