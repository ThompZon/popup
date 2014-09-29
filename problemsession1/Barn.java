package popup.problemsession1;

public class Barn implements Comparable<Barn>{
	public static boolean ACENDING = true;
	public int req;
	public int diff;


	public Barn(int l, int r) {
		req = l;
		diff = r;
	}

	@Override
	public int compareTo(Barn o) {
		if(ACENDING){
			return Integer.compare(diff, o.diff);
		}else {
			return Integer.compare(o.diff, diff);
		}
		
	}
}
