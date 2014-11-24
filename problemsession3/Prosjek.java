package popup.problemsession3;

/**
 * @author ThompZon
 *
 */
public class Prosjek {
	private static final boolean DEBUGGING = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Prosjek();
	}

	public Prosjek(){
		Kattio kio = new Kattio(System.in);
		kio.println(getSolution(kio.getLine()));
		kio.flush();
		kio.close();
	}

	public String getSolution(String numStr){
		int digits = numStr.length() - 1;//has the '.' included
		double numDouble = Double.parseDouble(numStr);
		
		long noPapers = Math.round(Math.pow(10, digits));
		long sum = Math.round(numDouble * noPapers);
		long divisor = 2;
		while(divisor != 1){
			divisor = gcd(noPapers, sum);
			noPapers /= divisor;
			sum /= divisor;
		}
		
		int[] papers = new int[5];
		papers[0] = (int) noPapers;
		sum -= noPapers;
		for(int i = 4; i > 0; i--){
			papers[i] = (int) sum/i;
			papers[0] -= papers[i];
			sum -= papers[i] * i;
		}
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(papers[0]);
		sb.append(' ');
		sb.append(papers[1]);
		sb.append(' ');
		sb.append(papers[2]);
		sb.append(' ');
		sb.append(papers[3]);
		sb.append(' ');
		sb.append(papers[4]);
		return sb.toString();
	}
	
	//Euclidian GCD
	private long gcd(long a, long b){
		long t;
	    while (b != 0){
	       t = b;
	       b = a % b;
	       a = t;
	    }
	    return a;
	}

}
