/**
 * 
 */
package popup.problemsession1;

/**
 * @author ThompZon
 *
 */
public class SpiderMan {
	private final static boolean DEBUGGING = true;
	
	private final static String FAIL = "IMPOSSIBLE";
	private final static String DOWN = "D";
	private final static String UP = "U";
	
	private static int LIMIT = 500;

	public class tupel {
		public int maxHeight;
		public int previousHeight;
		
		public tupel(){
			maxHeight = Integer.MAX_VALUE;
			previousHeight = 0;
		}
	}
	
	private tupel[][] m; //Matrix. Dynprog this shit! 
	int[] steps;
	
	private Kattio kio;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SpiderMan();
	}

	public SpiderMan(){
		if(DEBUGGING == false){
			kio = new Kattio(System.in);
			//if(DEBUGGING){System.out.println("Number of test cases: ");}
			int testCases = kio.getInt();	//number of times to run
			int[] d;
			
			for (int i = 0; i < testCases; i++) {
				int distanses = kio.getInt();
				d = new int[distanses];
				for(int k = 0; k < distanses; k++)
				{
					d[k] = kio.getInt();
				}
				System.out.println(getDirections(distanses, d));
			}
		}
	}
	
	public String getDirections(int distances, int[] d){
		int distanceSum = sum(d);
		if((distanceSum % 2) == 1 || distances <= 1) {
			return FAIL;
		}
		steps = d;
		
		LIMIT = distanceSum / 2; 
		m = new tupel[distances][LIMIT + 1]; //step, max height
		m[0][steps[0]] = new tupel();
		m[0][steps[0]].maxHeight = steps[0];
		recursiveChecker(steps[0], steps[0], 0); //check up and down, cannot go over LIMIT (halv of sum + 2), cannot go under 0;
		
		//Build the string:
		if(m[distances - 1][0] == null){
			if(DEBUGGING) System.out.println("FAIL: Final Node == NULL");
			return FAIL;
		}
		
		StringBuilder sb = new StringBuilder(distances);
		int heightToCheck = 0;
		for(int i = distances; i > 0; i--) {
			tupel curTupel =  m[i - 1][heightToCheck];
			if(heightToCheck < curTupel.previousHeight) //previous was heigher than current, than we went down last time
			{
				sb.append(DOWN);
			}
			else
			{
				sb.append(UP);
			}
			heightToCheck = curTupel.previousHeight;
		}
		return sb.reverse().toString();
	}
	
	private void recursiveChecker(int curHeight, int prevMaxHeight, int step){
		if(step == steps.length - 1)
		{
			//done!
			if(DEBUGGING) 
			{
				tupel t = m[step][curHeight];
				System.out.println("!! DONE, Height: " + curHeight + ", step: " + step + ", MAX height: " + t.maxHeight); 
			}
			return;
		}
		if(curHeight + steps[step + 1] <= LIMIT) { //If we do not go above LIMIT by taking a step upwards, try to do it
			if(DEBUGGING)System.out.println("Trying Step Up - Height: " + curHeight + ", step: " + step);
			if(m[step + 1][curHeight + steps[step + 1]] == null) m[step + 1][curHeight + steps[step + 1]] = new tupel();
			
			tupel t = m[step + 1][curHeight + steps[step + 1]];
			t.previousHeight = curHeight;
			t.maxHeight = Math.max(curHeight + steps[step + 1], prevMaxHeight);
			recursiveChecker(curHeight + steps[step + 1], Math.max(curHeight + steps[step + 1], prevMaxHeight), step + 1);
		}
		if(curHeight - steps[step + 1] >= 0) { //If we do not go below street level by taking a step downwards, try to do it
			if(DEBUGGING)System.out.println("Trying Step Down - Height: " + curHeight + ", step: " + step);
			if(m[step + 1][curHeight - steps[step + 1]] == null) m[step + 1][curHeight - steps[step + 1]] = new tupel();
			
			tupel t = m[step + 1][curHeight - steps[step + 1]];
			if(prevMaxHeight < t.maxHeight)
			{
				t.previousHeight = curHeight;
				t.maxHeight = prevMaxHeight;
				recursiveChecker(curHeight - steps[step + 1], prevMaxHeight, step + 1);
			}
		}
	}
	/*
	public String getDirections(int distances, int[] d){
		int remaningSum = 0;
		int biggest = -1;
		for(int i = 0; i < distances; i++){
			remaningSum += d[i];
			biggest = Math.max(biggest, d[i]);
		}
		if (remaningSum % 2 == 1) {
			return FAIL;
		}
		
		StringBuilder sb = new StringBuilder(distances);
		int currentHeight = 0;
		int currentMax = biggest + 2;
		int limit = remaningSum / 2;
		
		for(int i = 0; i < distances; i++){
			if(currentHeight - d[i] <= 0){
				currentHeight += d[i];
				sb.append("U");
				continue;
			} else if(currentHeight + d[i] >= limit){
				currentHeight -= d[i];
				sb.append("D");
				continue;
			}
		}
		////////////////////////////////// I do bad things!
		int[] sumDist = d.clone();
		for(int i = 0; i < distances; i++){
			if(currentHeight - d[i] <= 0){
				currentHeight += d[i];
				sumDist[i] = Math.abs(sumDist[i]);
				continue;
			} else if(currentHeight + d[i] >= limit){
				currentHeight -= d[i];
				sumDist[i] = - Math.abs(sumDist[i]);
				continue;
			} else if(sumDist[i] < 0) {
				currentHeight += d[i];
				sumDist[i] = Math.abs(sumDist[i]);
				continue;
			} else {
				currentHeight -= d[i];
				sumDist[i] = - Math.abs(sumDist[i]);
				continue;
			}
			
		}
		System.out.println("SumDist: " + sum(sumDist));
		
		
		return sb.toString();
	}
	
	public StringBuilder asdf(int distances, int[] d, StringBuilder sb){
		
		return sb;
	}
	*/
	
	private int sum(int[] l){
		int sum = 0;
		for(int i = 0; i < l.length; i++){
			sum += l[i];
		}
		return sum;
	}
}
