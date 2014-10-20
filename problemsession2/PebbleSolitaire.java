package popup.problemsession2;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
5
---oo----------oo------
-o--o-oo-----o--o-oo---
-o----ooo----o----ooo--
ooooooooooooooooooooooo
oooooooooo-ooooooooooo-
 */
public class PebbleSolitaire {
	private static final boolean DEBUGGING = false;
	private static final int BOARDLENGTH = 23;
	private static final int HOPLEFT = 3, HOPRIGHT = 6, HOPMASK = 7;

	private class state implements Comparable<state>{
		public int board;
		public int pebbles;

		public state(int board){
			this.board = board;
			this.pebbles = Integer.bitCount(board);
		}

		@Override
		public int compareTo(state o) {
			return Integer.compare(this.pebbles, o.pebbles);
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new PebbleSolitaire();
	}

	public PebbleSolitaire(){
		Kattio kio = new Kattio(System.in);
		int testCases = kio.getInt();

		for(int test = 0; test < testCases; test++){
			if(DEBUGGING)System.err.println("================= NEW CASE ==================");
			String board = kio.getWord();
			int bitRep = 0;
			for(int i = 0; i < board.length(); i++){
				if(board.charAt(i) == 'o'){
					bitRep |= (1 << i);
				}
			}

			kio.println(getSolution(bitRep));
		}

		kio.flush();
		kio.close();
	}

	public int getSolution(int initBoard){
		state initState = new state(initBoard);
		int pebblesLeft = initState.pebbles;

		PriorityQueue<state> que = new PriorityQueue<state>();
		HashSet<Integer> visitedStates = new HashSet<Integer>();
		que.add(initState);
		while(que.isEmpty() == false){
			state curr = que.poll();
			visitedStates.add(curr.board);
			pebblesLeft = Math.min(pebblesLeft, curr.pebbles);
			int hopleft = HOPLEFT;
			int hopright = HOPRIGHT;
			int hopmask = HOPMASK;
			for(int i = 0; i < BOARDLENGTH - 2; i++){
				if(DEBUGGING)System.err.println("Curr stuff: " + (Integer.toBinaryString(hopmask & curr.board)));
				if((hopmask & curr.board) == hopleft){
					int hopstate = curr.board + (1 << i);
					if(DEBUGGING)System.err.println("CURRSTATE: " + Integer.toBinaryString(curr.board) + ", HOP LEFT: " + Integer.toBinaryString(hopstate));
					if(visitedStates.contains(hopstate) == false){
						que.add(new state(hopstate));
						visitedStates.add(hopstate);
					}
				}
				if((hopmask & curr.board) == hopright){
					int hopstate = curr.board - (5 << i);
					if(DEBUGGING)System.err.println("CURRSTATE: " + Integer.toBinaryString(curr.board) + ", HOP RIGHT: " + Integer.toBinaryString(hopstate));
					if(visitedStates.contains(hopstate) == false){
						que.add(new state(hopstate));
						visitedStates.add(hopstate);
					}
				}
				hopleft = hopleft << 1;
				hopright = hopright << 1;
				hopmask = hopmask << 1;
			}
		}
		return pebblesLeft;
	}

}
