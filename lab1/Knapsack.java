package popup.lab1;


/**
 * @author ThompZon
 */
public class Knapsack {
	private static final boolean DEBUGGING = true;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Knapsack();
	}
	
	public Knapsack(){
		if(DEBUGGING){
			//INTPUT:
			Kattio kio = new Kattio(System.in);
			//We are only interested in the int part of the cWord, 
			//but if the word is 10000.0 then the double might be 9999.9999... 
			//To be safe, call function that splits on '.' and returns the first part.
			String cWord = kio.getWord(); 
			//only the int part is needed because all objects has weights of int's...
			int c = getIntFromDoubleString(cWord);
			int n = kio.getInt(); //Number of objects
			int[] v = new int[n];
			int[] w = new int[n];
			for(int i = 0; i < n; i++){
				v[i] = kio.getInt();
				w[i] = kio.getInt();
			}
			kio.close();
			
			//SOLVE PROBLEM:
			int[] ans = getKnapsack(c, n, v, w);
			
			//OUTPUT PART:
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < ans.length; i++){
				sb.append(ans[i]);
				sb.append(" ");
			}
			System.out.println(ans.length);
			System.out.println(sb.toString().trim());
		}
	}
	
	/**
	 * Tries to get as much value as possible for the given capacity
	 * @param c capacity of knapsack (weight it can take)
	 * @param n number of items
	 * @param v array with value of items
	 * @param w array with weight of items
	 * @return int array with index to items to take
	 */
	public int[] getKnapsack(int c, int n, int[] v, int[] w){
		int[][] m = new int[n][c];
		for(int i = 1; i < n; i++){
			for(int j = 0; j < c; j++){
				if(w[i] <= j){
					m[i][j] = Math.max(m[i-1][j], m[i-1][j - w[i]] + v[i]);
				}
				else
				{
					m[i][j] = m[i-1][j];
				}
			}
		}
		int[] arr = new int[3];
		return arr;
	} 
	
	public int getIntFromDoubleString(String word){
		return Integer.parseInt(word.split("\\.")[0]);
	}
	
	/**
	 * Sorts two arrays, on one of the arrays
	 * @param a the MASTER array, will sort this one
	 * @param b the follower array, will follow the MASTER
	 * @return a matrix with [i][0] is from a, [i][1] if from b, for the same i.
	 */
	public int[][] getSortedArray(int[] a, int[] b){
		int[][] m = new int[a.length][2];
		return m;
	}
}
