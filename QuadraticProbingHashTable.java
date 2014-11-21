// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 3 
// QuadraticProbingHashTable.java
// hash table for strings; 
// uses quadratic probing to resolve collisions
// based on Weiss's QuadraticProbingHashTable code 
// *****************************

public class QuadraticProbingHashTable {

	private static final int DEFAULT_TABLE_SIZE = 101; 
	private HashEntry[] array; // array of elements 
	private int currentSize; 			// number of occupied cells 

	private static class HashEntry {
		public String element; 
		public boolean isActive; 
		public HashEntry(String e) {
			this(e, true); 
		}
		private HashEntry(String e, boolean i) {
			element = e; 
			isActive = i;
		}
	}

	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE); 
	}

	public QuadraticProbingHashTable(int size) {
		allocateArray(size); 
		makeEmpty(); 
	}

	public void makeEmpty() {
		currentSize = 0; 
		for (int i = 0; i < array.length; i++) {
			array[i] = null; 
		}
	}

	public boolean contains(String x) {
		int currentPos = findPos(x); 
		return isActive(currentPos); 
	}

	public void insert (String x) {
		int currentPos = findPos(x); 
		if (isActive(currentPos)) {
			return; // repeated entry 
		}
		array[currentPos] = new HashEntry(x, true); 
		if (++currentSize > array.length/2) {
			rehash(); // rehash if more than half full 
		}
	}

	public void remove (String x) {
		int currentPos = findPos(x); 
		if (isActive(currentPos)) {
			array[currentPos].isActive = false; 
		}
	}

	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)]; 
	}

	private boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive; 
	}

	private int findPos(String x) {
		// performs quadratic probing resolution 
		int offset = 1; 
		int currentPos = hash(x); 
		while (array[currentPos] != null 
				&& !array[currentPos].element.equals(x)) {
			currentPos += offset; 
			offset += 2; 
			if (currentPos >= array.length) {
				currentPos -= array.length; 
			}
		}
		return currentPos; 
	}

	private void rehash() {
		HashEntry[] oldArray = array; 
		allocateArray(nextPrime(2*oldArray.length)); 
		currentSize = 0; 
		for (int i = 0; i < oldArray.length; i++) {
			if (oldArray[i] != null && oldArray[i].isActive) {
				insert(oldArray[i].element); 
			}
		}
	} 

	private int hash(String x){ 
		int hashVal = 0; 
		for (int i = 0; i < x.length(); i++) {
			hashVal = 37*hashVal+((int) x.charAt(i)); 
		}
		hashVal %= array.length; 
		if (hashVal < 0) {
			hashVal += array.length; 
		}
		return hashVal;
	}

	private int hashCode(String key) {
		int hashVal = 0; 
		for (int i = 0; i < key.length(); i++) {
			hashVal = 37*hashVal*key.charAt(i); 
		}
		return hashVal;
	}

	private static int nextPrime(int n) {
		if (n%2 == 0) {
			n++ ;
		}
		for ( ; !isPrime(n); n+=2){
			; 
		}
		return n; 
	}

	private static boolean isPrime(int n) {
		if (n==2 || n==3) {
			return true; 
		}
		if (n==1 || n%2 == 0) {
			return false;
		}
		for (int i = 3; i*i<=n; i+=2) {
			if (n%i == 0) {
				return false; 
			}
		}
		return true; 
	}

	public String toString() {
		String string = "["; 
		for (HashEntry entry : array) {
			if (entry == null) {
				string += " "; 
			} else {
				string += entry.element; 
			}
			string += ", "; 
		}
		string += "]"; 
		return string;
	}

}