package popup.problemsession1;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Lutna {

	
/*
== IN ==
5 3
1
3
2
== OUT ==
1

== IN ==
10 4
4
5
2
3

== IN ==
18 5
5
5
5
5
5

 */
	public static void main(String[] args) {
		new Lutna();
	}
	
	public Lutna(){
		
		Kattio kio = new Kattio(System.in);
		int m = kio.getInt();
		int n = kio.getInt();
		long sum = 0;
		ArrayList<Barn> a = new ArrayList<Barn>(n);

		for(int i = 0; i < n; i++){
			int val = kio.getInt();
			a.add(new Barn(val, 0) );
			sum += a.get(i).req;
		}
		kio.println(getAnger(a, sum, n, m));
		kio.flush();
		kio.close();
		
	}
	
	public long getAnger(ArrayList<Barn> a, long sum, int children, int candies){
		long totalAnger = 0;
		double kvot = candies / (double)sum;
		long totalGiven = 0;
		int given;
		long unhappiness;
		
		long maxDiff = -1;
		long minDiff = Integer.MAX_VALUE;
		for(int i = 0; i < children; i++){
			given = (int) Math.round(a.get(i).req * kvot);
			totalGiven += given;
			//System.out.println("i: " + i + ", given: " + given + ", req: "+ a.get(i)+" kvot: " + a.get(i) * kvot);
			a.get(i).diff = a.get(i).req - given;
			unhappiness = a.get(i).diff;
			if(maxDiff < unhappiness){
				maxDiff = unhappiness;
			}
			if(minDiff > unhappiness){
				minDiff = unhappiness;
			}
			
			unhappiness *= unhappiness;
			totalAnger += unhappiness;
		}
		if(candies - totalGiven == 0){
			return totalAnger;
		}
		Barn.ACENDING = (candies - totalGiven) < 0;
		PriorityQueue<Barn> pq = new PriorityQueue<Barn>(a);
		//System.out.println("Candies Left: " + (candies - totalGiven));
		/*
		 * Avrundningsfel kan göra att vi get för många/för få (mer än en!!!)
		 * Vi måste ta från de som vi givit mest/minst till (som vi faktiskt givit till!)
		 * och minimera angern
		 * */

		if(candies - totalGiven < 0){
			int candiesToTake = (int) Math.abs(candies - totalGiven);
			while (candiesToTake > 0 && pq.isEmpty() == false){
				Barn curr = pq.poll();
				if(curr.req == 0){
					continue;
				}
				if(curr.req - curr.diff > 0){
					totalAnger -= curr.diff * curr.diff;
					curr.diff += 1;
					totalAnger += curr.diff * curr.diff;
					candiesToTake -= 1;
					pq.add(curr);
				}
				
			}
		} else if(candies - totalGiven > 0){
			int candiesToGive = (int) Math.abs(candies - totalGiven);
			while (candiesToGive > 0 && pq.isEmpty() == false){
				Barn curr = pq.poll();
				if(curr.req == 0){
					continue;
				}
				if(curr.req - curr.diff > 0){
					totalAnger -= curr.diff * curr.diff;
					curr.diff -= 1;
					totalAnger += curr.diff * curr.diff;
					candiesToGive -= 1;
					pq.add(curr);
				}
			}
		}
		
		return totalAnger;
	}

}
