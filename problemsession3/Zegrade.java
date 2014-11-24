package popup.problemsession3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author ThompZon
((((1+2)+3)+4)+5)

((1+1)+(1+1))
((1+1))

()()()()()()
 */
public class Zegrade {
	private static final boolean DEBUGGING = true;
	
	class tupel{
		int left;
		int right;
		public tupel(int left, int right){
			this.left = left;
			this.right = right;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Zegrade();
	}
	
	public Zegrade(){
		Kattio kio = new Kattio(System.in);
		getSolution(kio,kio.getLine());
		kio.flush();
		kio.close();
	}
	
	public void getSolution(Kattio kio, String input){
		ArrayDeque<Integer> leftBracketIndex = new ArrayDeque<Integer>();
		ArrayList<tupel> brackets = new ArrayList<tupel>();
		for(int i = 0; i < input.length(); i++){
			if(input.charAt(i) == '('){
				leftBracketIndex.push(i);
			}else if(input.charAt(i) == ')'){
				brackets.add(new tupel(leftBracketIndex.pop(), i));
			}
		}
		int combinations = (int) Math.round(Math.pow(2, brackets.size()));
		//Bruteforce step
		PriorityQueue<String> pq = new PriorityQueue<String>();
		HashSet<String> hs = new HashSet<String>();
		StringBuilder currBuilder;
		for(int i = 1; i < combinations; i++){
			currBuilder = new StringBuilder(input);
			//delete something
			for(int k = 0; k < brackets.size(); k++){
				if(((i >> k) & 1) == 1){ //if remove tupel
					currBuilder.setCharAt(brackets.get(k).left, 's');
					currBuilder.setCharAt(brackets.get(k).right, 's');
				}
			}
			hs.add(currBuilder.toString().replaceAll("s", ""));
		}
		pq.addAll(hs);
		while(pq.isEmpty() == false){
			kio.println(pq.poll());
		}
	}
	
	
}
