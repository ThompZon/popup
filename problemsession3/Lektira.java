package popup.problemsession3;

/**
 * @author ThompZon
 *
 */
public class Lektira {
	private static final boolean DEBUGGING = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Lektira();
	}
	
	public Lektira(){
		Kattio kio = new Kattio(System.in);
		kio.println(getSolution(kio.getLine()));
		kio.flush();
		kio.close();
	}
	
	public String getSolution(String word){
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		String smallestWord = word;
		for(int i = 1; i < word.length() - 1; i++){
			for(int k = i +1 ; k < word.length(); k++){
				sb1.setLength(0);
				sb2.setLength(0);
				sb3.setLength(0);
				sb1.append(word.substring(0, i));
				sb1.reverse();
				sb2.append(word.substring(i, k));
				sb2.reverse();
				sb3.append(word.substring(k));
				sb3.reverse();
				final String curr = sb1.append(sb2).append(sb3).toString();
				if(curr.compareTo(smallestWord) < 0){
					smallestWord = curr;
				}
			}
		}
		return smallestWord;
	}

}
