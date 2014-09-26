package popup.lab1;

/**
 * @author ThompZon
 */
public class IntervalCover {
	private static boolean DEBUGGING = true;
	private static final String FAIL = "impossible";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new IntervalCover();
	}
	
	public IntervalCover(){
		if(DEBUGGING == false){
			Kattio kio = new Kattio(System.in);
			double left = kio.getDouble();
			double right = kio.getDouble();
			int num = kio.getInt();
			//TODO
		}
	}
	
	public int[] interval(double left, double right, double[][] intervals){
		//TODO
		int[] a = new int[1]; 
		return a;
	}

}
