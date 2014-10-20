package popup.problemsession2;

/**
 * 
 */
public class ProblemName {
	private static final boolean DEBUGGING = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new ProblemName();
	}

	public ProblemName(){
		Kattio kio = new Kattio(System.in);
		int testCases = kio.getInt();
		
		for(int i = 0; i < testCases; i++){
			int data = kio.getInt();
			kio.println(getSolution(data));
		}
		
		kio.flush();
		kio.close();
	}

	public String getSolution(int param){
		StringBuilder sb = new StringBuilder(100);
		sb.append(param);
		return sb.toString();
	}

}
