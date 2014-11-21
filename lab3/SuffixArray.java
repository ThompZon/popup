package popup.lab3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Suffix Array
 *  TODO comments
 * @author Thomas Sjoholm, Alexander Gomez
 * @since 2014-11-21
 */
public class SuffixArray {
	//private final String text;
	private final int length;
	private final int[] suffixArr;

	/**
	 * TODO comments
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in);
		
		while(kio.hasMoreTokens()){
			SuffixArray sa = new SuffixArray(kio.getLine());
			int quaries = kio.getInt();
			for(int i = 0; i < quaries; i++){
				kio.print(sa.getSuffix(kio.getInt()));
				if(i < quaries - 1){
					kio.print(' ');
				}
			}
			kio.print('\n');
			//TODO remove this!1
			kio.flush();
		}
		
		kio.flush();
		kio.close();
	}

	private class Suffix
	{
		int index; // To store original index, the character it points to
		int[] charValues; // To store ranks and next rank pair, values used to sort
		
		/**
		 * TODO comments
		 * @param i initial index in the string
		 * @param r1
		 * @param r2
		 */
		public Suffix(int i, int r1, int r2){
			this.index = i;
			this.charValues = new int[] {r1, r2};
		}
	};

	//TODO comments
	class SuffixCompare implements Comparator<Suffix>{
		@Override
		public int compare(Suffix a, Suffix b) {
			if(a.charValues[0] == b.charValues[0]){
				//if first char is equal, then use the 2nd char
				return Integer.compare(a.charValues[1], b.charValues[1]);
			}else{
				//if first char is not equal, compare them
				return Integer.compare(a.charValues[0], b.charValues[0]);
			}
		}
	}

	//TODO
	public int getSuffix(int index){
		return suffixArr[index];
	}

	//TODO comments, builds suffix array
	private int[] buildSuffixArray(String text)
	{
		Suffix[] suffixes = new Suffix[length];
		// Stores indexes of suffixes and gets char value of current and next char
		// Storing indexes will keep their "substring" even when sorted
		for (int i = 0; i < length; i++)
		{
			suffixes[i] = new Suffix (
					i, 
					text.charAt(i), 
					((i+1) < length) ? (text.charAt(i + 1)): -1 //Out of bounds gives a -1, making shorter words appear higher up in the sort
			);
		}

		//Sort on the first 2 char values
		final SuffixCompare comparer = new SuffixCompare();
		Arrays.sort(suffixes, comparer);

		//Then sort according to the first 4 chars, 8, 16...
		int[] indexes = new int[length];  // This array is needed to get the index in suffixes[]
		for (int k = 2; k < length; k = k*2) //O(log n) times, DO:
		{
			// Assigning char values and index values to first suffix
			int currCharVal = 0;		//arbitrary value that will be incremented for non-same
			int prevCharVal = suffixes[0].charValues[0];	//Prev val = current value;
			suffixes[0].charValues[0] = currCharVal;
			indexes[suffixes[0].index] = 0;			 		//Know where the first one is!

			// update char values[0]
			for (int i = 1; i < length; i++)
			{
				// If current has the same value as previous
				if (suffixes[i].charValues[0] == prevCharVal && 
					suffixes[i].charValues[1] == suffixes[i-1].charValues[1])
				{
					//Set current value to this one as well:
					suffixes[i].charValues[0] = currCharVal;
				} else {
					//NOT EQUAL: Assign it to a higher value, some int higher than it was previously
					suffixes[i].charValues[0] = ++currCharVal;
				}
				prevCharVal = suffixes[i].charValues[0];	//Update prev value to current value:
				indexes[suffixes[i].index] = i; 			//
			}

			// Update char values [1]
			for (int i = 0; i < length; i++)
			{
				int nextindex = suffixes[i].index + k;
				if(nextindex < length){
					//Next char value is the value of (the index of the next character).
					suffixes[i].charValues[1] = suffixes[indexes[nextindex]].charValues[0];
				}else{
					//Next charvalue is out of bounds!
					//Soreted above non-out-of-bounds values!
					suffixes[i].charValues[1] = -1;
				}
			}

			// Sort the result of this round, another 2*k chars sorted!
			// Takes O(n log n)
			Arrays.sort(suffixes, comparer);
		}
		//Resulting in O(n log^2 n)
		
		//No need to keep the char values, lets just keep the int array;
		final int[] suffixArray = new int[length];
	    for (int i = 0; i < length; i++){
	        suffixArray[i] = suffixes[i].index;
	    }
		
		// Return the suffix array
		return suffixArray;
	}

	// Driver program to test above functions
	public SuffixArray(String in)
	{
		//this.text = in;
		this.length = in.length();
		//String txt = "apaapa";
		this.suffixArr = buildSuffixArray(in);
		//		for(int i = 0; i < length; i++){
//			System.out.println(text.substring(suffixArr[i]));
//		}
		//System.out.println(numberOfCompares);
		//printArr(suffixArr, n);
	}
}
