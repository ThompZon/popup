package popup.problemsession1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/*
2

In practice, the difference between theory and practice is always
greater than the difference between theory and practice in theory.
        - Anonymous

Man will occasionally stumble over the truth, but most of the
time he will pick himself up and continue on.
        - W. S. L. Churchill
EndOfText
 */

public class Zip {
	private static final String END = "EndOfText";
	private static final String FAIL = "There is no such word.";

	public static void main(String[] args) {
		new Zip();
	}
	
	public Zip(){
		Kattio kio = new Kattio(System.in);
		int occurences = kio.getInt();
		HashMap<String, Integer> wordOcc = new HashMap<String, Integer>(10000);
		HashSet<String> keepers = new HashSet<String>(10000);
		
		String token;
		while(kio.hasMoreTokens()){
			token = kio.getWord();
			if(token.equals(END)){
				//Hitta occurences
				printSortedKeepers(keepers, kio);
				//break;
				if(kio.hasMoreTokens()){
					System.out.println();
					//Nästa testfall!
					occurences = kio.getInt();
					wordOcc.clear();
					keepers.clear();
					continue;
				}
			}
			//Bygga struktur!
			String[] s = token.split("[^A-Za-z]");
			int currocc = 0;
			String tmpToken;
			for(int i = 0; i < s.length; i++){
				if(s[i].isEmpty()) continue;
				//Vet att ord
				tmpToken = s[i].toLowerCase();
				if(wordOcc.containsKey(tmpToken)){
					currocc = wordOcc.get(tmpToken) + 1;
					wordOcc.put(tmpToken, currocc);
					if(currocc == occurences){
						keepers.add(tmpToken);
					}else if(currocc - 1 == occurences){
						keepers.remove(tmpToken);
					}
				}else{
					wordOcc.put(tmpToken, 1);
					if(occurences == 1){
						keepers.add(tmpToken);
					}
				}
			}
		}
		kio.flush();
		kio.close();
	}
	
	public void printSortedKeepers(HashSet<String> keepers, Kattio kio){
//		String[] s = new String[keepers.size()];
//		for(int i = 0; i < keepers.size(); i++){
//			s[i] = 
//		}
		String[] s = keepers.toArray(new String[keepers.size()]);
		if(s.length == 0){
			kio.println(FAIL);
			return;
		}
		Arrays.sort(s);
		for(int i = 0; i < s.length; i++){
			kio.println(s[i]);
		}
	}

}
