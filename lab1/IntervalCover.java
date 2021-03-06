package popup.lab1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ThompZon
 */
public class IntervalCover {
	public static boolean DEBUGGING = false;
	private static final String FAIL = "impossible";

	public class Tupel implements Comparable<Tupel>{
		public double left;
		public double right;
		public int originalIndex;

		public Tupel(double l, double r, int i) {
			left = l;
			right = r;
			originalIndex = i;
		}

		@Override
		public int compareTo(Tupel o) {
			return Double.compare(o.right, right);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new IntervalCover();
	}

	public IntervalCover(){
		if(DEBUGGING == false){
			Kattio kio = new Kattio(System.in);
			while(kio.hasMoreTokens())
			{
				//Handle Input
				double left = kio.getDouble();
				double right = kio.getDouble();
				int num = kio.getInt();
				Tupel[] intervals = new Tupel[num];
				for(int i = 0; i < num; i++){
					intervals[i] = new Tupel(kio.getDouble(), kio.getDouble(), i);
				}
				
				//Solve problem
				Arrays.sort(intervals);
				ArrayList<Integer> ans = interval(left, right, intervals);
				
				//Handle Output
				if(ans == null){
					System.out.println(FAIL);
					continue;
				}
				StringBuilder sb = new StringBuilder(ans.size() * 2);
				for(int i = 0; i < ans.size(); i++){
					sb.append(ans.get(i) + " ");
				}
				System.out.println(ans.size());
				System.out.println(sb.toString().trim()); //this or delete last char, this was faster to implement
			}
			kio.close();
		}
	}

	/**
	 * Solves the interval problem
	 * @param left the left interval (minimum)
	 * @param right the right interval (maximum)
	 * @param intervals the intervals to cover, sorted by Array.sort or something like that
	 * @return an array with index of intervals in the solution
	 */
	public ArrayList<Integer> interval(double left, double right, Tupel[] intervals){
		//Highest right does not cover right protection
		if(intervals[0].right < right) {
			//System.out.println("Highest Interval does not cover!");
			return null; 
		}
		
		double prev;
		double curr = left;
		ArrayList<Integer> keepers = new ArrayList<Integer>(intervals.length);
		while(curr <= right){
			prev = curr;
			for(int i = 0; i < intervals.length; i++){
				if(intervals[i].left <= curr){
					//System.out.println("picking left: " + intervals[i].left + ", right: " + intervals[i].right);
					keepers.add(intervals[i].originalIndex);
					curr = intervals[i].right;
					break;
				}
			}
			if(prev == curr){
				//If nothing is covering left, then this will happen
				//System.out.println("Nothing Happened, Fail!");
				return null; //nothing happened, fail!
			}
			
		}
		return keepers;
	}

	//sortera grejerna p� X (left elelr right).
	/*
	 * left, interval vars left �r t�ckt av curr, och har st�rst right. v�lj den.
	 * 
	 * 1. total ignorera allt som har right < curr
	 * 2. hitta snabbt intervall som har left <= curr. 
	 */

}
