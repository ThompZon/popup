/**
 * 
 */
package popup.problemsession1;

/**
 * @author ThompZon
 *
 */
public class Commersial {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Commersial();
	}

	public Commersial(){
		Kattio kio = new Kattio(System.in);
		int n = kio.getInt();
		int price = kio.getInt();
		int[] a = new int[n];
		for(int i = 0; i< n; i++){
			a[i] = kio.getInt() - price;
		}
		kio.println(maxSubArraySum(a, n));
		kio.flush();
		kio.close();
	}

	public int maxSubArraySum2(int[] a, int size){
		int best = 0;
		int currSum;
		for(int i = 0; i < size; i++){
			if(a[i] < 0) continue;
			currSum = 0;
			for(int j = i; j < size; j++){
				currSum += a[j];
				best = Math.max(currSum, best);
			}
		}
		return best;
	}

	public int maxSubArraySum(int a[], int size)
	{
		int best = 0;
		int currBest = 0;
		for(int i = 0; i < size; i++)
		{
			currBest = currBest + a[i];
			if(currBest < 0){
				currBest = 0;
			}
			if(best < currBest){
				best = currBest;
			}
		}
		return best;
	} 

}
