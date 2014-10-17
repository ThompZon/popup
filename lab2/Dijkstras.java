package popup.lab2;

/**
 * @author ThompZon
 *
 */
public class Dijkstras {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Dijkstras();
	}
	
	public Dijkstras(){
		Kattio kio = new Kattio(System.in);
		int nodes = kio.getInt();
		int edges = kio.getInt();
		int queries = kio.getInt();
		int start = kio.getInt();
		while(true){
			if(nodes == 0 && edges == 0 && queries == 0 && start == 0){
				//end of test cases
				kio.flush();
				kio.close();
				return;
			}
			
			//Read next case or end of test cases
			nodes = kio.getInt();
			edges = kio.getInt();
			queries = kio.getInt();
			start = kio.getInt();
		}
	}

}
