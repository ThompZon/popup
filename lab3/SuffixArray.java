package popup.lab3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Suffix Array
 *  TODO comments
 * @author Thomas Sjöholm, Alexander Gomez
 * @since 2014-11-21
 */
public class SuffixArray {
	private final String text;
	private final int length;

	/**
	 * TODO comments
	 * @param args
	 */
	public static void main(String[] args) {
		new SuffixArray();
	}

	private class Suffix
	{
		int index; // To store original index, the character it points to
		int[] rank; // To store ranks and next rank pair, values used to sort
		
		/**
		 * TODO comments
		 * @param i initial index in the string
		 * @param r1
		 * @param r2
		 */
		public Suffix(int i, int r1, int r2){
			this.index = i;
			this.rank = new int[] {r1, r2};
		}
		
		//TODO comments
		public String getSubstring(){
			return text.substring(index);
		}
	};

	//TODO comments
	class SuffixCompare implements Comparator<Suffix>{
		@Override
		public int compare(Suffix a, Suffix b) {
			if(a.rank[0] == b.rank[0]){
				//if first char is equal, then use the 2nd char
				return Integer.compare(a.rank[1], b.rank[1]);
			}else{
				//if first char is not equal, compare them
				return Integer.compare(a.rank[0], b.rank[0]);
			}
		}
	}




	//TODO comments, builds suffix array
	private Suffix[] buildSuffixArray()
	{
		Suffix[] suffixes = new Suffix[length];

		// Store suffixes and their indexes in an array of structures.
		// The structure is needed to sort the suffixes alphabatically
		// and maintain their old indexes while sorting
		final int LOWEST_ASKII = 32;
		for (int i = 0; i < length; i++)
		{
			suffixes[i] = new Suffix(
					i, 
					text.charAt(i) - LOWEST_ASKII, 
					((i+1) < length)? (text.charAt(i + 1) - LOWEST_ASKII): -1
			);
		}

		// Sort the suffixes using the comparison function
		// defined above.
		SuffixCompare cmp = new SuffixCompare();
		Arrays.sort(suffixes, cmp);
		//sort(suffixes, suffixes+n, cmp);

		// At his point, all suffixes are sorted according to first
		// 2 characters.  Let us sort suffixes according to first 4
		// characters, then first 8 and so on
		int[] ind = new int[length];  // This array is needed to get the index in suffixes[]
		// from original index.  This mapping is needed to get
		// next suffix.
		for (int k = 2; k < length; k = k*2)
		{
			// Assigning rank and index values to first suffix
			int rank = 0;
			int prev_rank = suffixes[0].rank[0];
			suffixes[0].rank[0] = rank;
			ind[suffixes[0].index] = 0;

			// Assigning rank to suffixes
			for (int i = 1; i < length; i++)
			{
				// If first rank and next ranks are same as that of previous
				// suffix in array, assign the same new rank to this suffix
				if (suffixes[i].rank[0] == prev_rank &&
						suffixes[i].rank[1] == suffixes[i-1].rank[1])
				{
					prev_rank = suffixes[i].rank[0];
					suffixes[i].rank[0] = rank;
				}
				else // Otherwise increment rank and assign
				{
					prev_rank = suffixes[i].rank[0];
					suffixes[i].rank[0] = ++rank;
				}
				ind[suffixes[i].index] = i;
			}

			// Assign next rank to every suffix
			for (int i = 0; i < length; i++)
			{
				int nextindex = suffixes[i].index + k;
				suffixes[i].rank[1] = (nextindex < length)?
						suffixes[ind[nextindex]].rank[0]: -1;
			}

			// Sort the suffixes according to first k characters
			Arrays.sort(suffixes, cmp);
		}
		
		// Return the suffix array
		return  suffixes;
	}

	// Driver program to test above functions
	public SuffixArray()
	{
		text = "mississippi";
		length = text.length();
		//String txt = "apaapa";
		Suffix[] suffixArr = buildSuffixArray();
		//cout << "Following is suffix array for " << txt << endl;
		for(int i = 0; i < length; i++){
			System.out.println(suffixArr[i].getSubstring());
		}
		//System.out.println(numberOfCompares);
		//printArr(suffixArr, n);
	}
}
