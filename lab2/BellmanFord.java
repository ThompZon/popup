/**
 * 
 */
package popup.lab2;

import popup.lab2.Dijkstras.Edge;

/**
 * @author ThompZon
 *
 */
public class BellmanFord {
	private static final String FAILNOPATH = "Impossible";
	private static final String FAILNEGCYCLE = "-Infinity";
	
	
	public BellmanFord(Edge[] edges, int nodes, int start){
	}
	
	public String kattisQueryDistance(int destination){
		return "";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in, System.out);
		int nodes = kio.getInt();
		int edges = kio.getInt();
		int queries = kio.getInt();
		int start = kio.getInt();
		//Node[] ns = new Node[10000];
		while(true){
			Edge[] edgeList = new Edge[edges];
			for(int i = 0; i < edges; i++){
				int from = kio.getInt();
				int to = kio.getInt();
				int weight = kio.getInt();
				edgeList[i] = new Edge(from, to, weight);
			}
			
			BellmanFord bellman = new BellmanFord(edgeList, nodes, start);
			
			for(int q = 0; q < queries; q++){
				kio.println(bellman.kattisQueryDistance(kio.getInt()));
			}
			
			//Read next case or end of test cases
			nodes = kio.getInt();
			edges = kio.getInt();
			queries = kio.getInt();
			start = kio.getInt();
			if(nodes == 0 && edges == 0 && queries == 0 && start == 0){
				//end of test cases
				kio.flush();
				kio.close();
				return;
			}
			kio.println();
		}
	}

}
