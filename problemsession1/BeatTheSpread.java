/**
 * 
 */
package popup.problemsession1;

/**
 * @author ThompZon
 *
 */
public class BeatTheSpread {
	private static final String fail = "impossible";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BeatTheSpread();
	}
	
	public BeatTheSpread(){
		Kattio kio = new Kattio(System.in);
		
		int tests = kio.getInt();
		for(int t = 0; t < tests; t++){
			int sum = kio.getInt();
			int diff = kio.getInt();
			int[] scores = getScores(sum, diff);
			if(scores == null){
				kio.println(fail);
			}else{
				kio.println(scores[0] + " " + scores[1]);
			}
			
		}
		kio.flush();
		kio.close();
	}
	
	public int[] getScores(int sum, int diff){
		int[] scores = new int[2];
		
		int score1 = -(diff - sum);
		if((score1 % 2) == 1){
			return null;
		}else{
			score1 = score1/2;
		}
		int score2 = sum - score1;
		
		if(score1 < 0 || score2 < 0){
			return null;
		}
		
		scores[0] = Math.max(score1, score2);
		scores[1] = Math.min(score1, score2);
		return scores;
	}

}
