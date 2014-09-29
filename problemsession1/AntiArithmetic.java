package popup.problemsession1;

/**
 * @author ThompZon
 *
 */
public class AntiArithmetic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new AntiArithmetic();
	}
	
	public AntiArithmetic(){
		Kattio kio = new Kattio(System.in);
		int n = Integer.parseInt( kio.getWord().split(":")[0] );
		while(kio.hasMoreTokens()){
			for(int i = 0; i < n; i++){
				//TODO
			}
			
			//NEXT CASE CHECK:
			n = Integer.parseInt( kio.getWord().split(":")[0] );
			if(n == 0){
				break;
			}
		}
		kio.flush();
		kio.close();
	}

}
