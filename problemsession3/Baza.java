package popup.problemsession3;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author ThompZon
 *
 */
public class Baza {
	private static final boolean DEBUGGING = true;
//	private String[] arr;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Baza();
	}
	
	public Baza(){
		Kattio kio = new Kattio(System.in);
		int n = kio.getInt();
//		arr = new String[n];
		Trie t = new Trie(n);
		for(int i = 0; i < n; i++){
			//arr[i] = kio.getWord();
			t.insertWord(kio.getWord(), i+1);
		}
		int q = kio.getInt();
		for(int i = 0; i < q; i++){
			//System.out.println("============= NEW CASE ==============");
			kio.println(t.getWordCounters(kio.getWord()));
//			kio.println(query(kio.getWord()));
		}
		kio.flush();
		kio.close();
	}
	/*
	public int query(String input){
		int counter = 0;
		for(int i = 0; i < arr.length; i++){
			int len = Math.min(input.length(), arr[i].length());
			for(int c = 0; c < len; c++){
				counter += 1;
				if(input.charAt(c) == arr[i].charAt(c)){
					if(c == len-1){
						counter += 1;
						if(input.equals(arr[i])){
							return counter;
						}
					}
					
				}else{
					break;
				}
			}
		}
		return counter;
	}
	*/
	
	public class Trie
	{
		TrieNode root;
		int noWords;
		public Trie(int words){
			root = new TrieNode('\0'); //content of root node is never used!
			noWords = words;
		}

		void insertWord(String word, int index)
		{
			int offset = 'a'; //ASKII offset from the alphabets first "letter"
			int l = word.length();
			char[] letters = word.toCharArray();
			TrieNode curNode = root;

			for (int i = 0; i < l; i++)
			{
				if (curNode.links[letters[i]-offset] == null){
					curNode.links[letters[i]-offset] = new TrieNode(letters[i]);
					//curNode.counter.put(0, 0);
				}
				curNode = curNode.links[letters[i]-offset];
				curNode.counter += 1;
				curNode.treeCont.put(index, curNode.counter);
			}
			curNode.fullWord = true;
			curNode.index = index;
		}
		
		int getWordCounters(String word){
			int count = 0;
			int offset = 'a'; //ASKII offset from the alphabets first "letter"
			int l = word.length();
			char[] letters = word.toCharArray();
			TrieNode curNode = root;
			if(root.links[letters[0]-offset] == null){
				return this.noWords;
			}
			ArrayList<TrieNode> nodes = new ArrayList<TrieNode>();
			curNode = curNode.links[letters[0]-offset];
			for (int i = 1; i < l; i++)
			{
				//count += curNode.counter;
				nodes.add(curNode);
				if (curNode.links[letters[i]-offset] == null){
					for(TrieNode tn : nodes){
						count += tn.counter;
					}
					return count + this.noWords;
				}
				curNode = curNode.links[letters[i]-offset];
				//System.out.println("CurrLetter: " + curNode.letter + ", Counter: " + curNode.treeCont);
			}
			nodes.add(curNode);
			if(curNode.fullWord){
				for(TrieNode tn : nodes){
					//System.out.println("CurrLetter: " + tn.letter + ", Counter: " + tn.treeCont);
					count += tn.treeCont.floorEntry(curNode.index).getValue();
				}
				return count + curNode.index;
			}else{
				for(TrieNode tn : nodes){
					count += tn.counter;
				}
				return count + this.noWords;
			}
		}
	}

	class TrieNode
	{
		int index;
		char letter;
		TrieNode[] links;
		boolean fullWord;
		TreeMap<Integer, Integer> treeCont;
		int counter;
		
		TrieNode(char letter)
		{
			this.letter = letter;
			this.links = new TrieNode[26];
			this.fullWord = false;
			this.counter = 0;
			this.treeCont = new TreeMap<Integer, Integer>();
			this.index = -1;
		}
	}
	
}
