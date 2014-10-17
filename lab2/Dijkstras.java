package popup.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author ThompZon
4 3 4 0
0 1 2
1 2 2
3 0 2
0
1
2
3
2 1 1 0
0 1 100
1
0 0 0 0
 */
public class Dijkstras {
	private static final String FAIL = "Impossible";
	
	/**
	 * The Node is a node in the graph of this problem
	 * The selfIndex and parent is used to be able to do the construction of some path
	 * If parent is -1, then it is disconnected from the start!
	 */
	public static class Node{
		public boolean visited;
		public ArrayList<Edge> neighbourList;
		
		public int selfIndex;
		public int parent;
		
		/**
		 * Creates a node, initialize the neighbor list.
		 * @param nodeIndex Index of this node, used to create paths to some object
		 */
		public Node(int nodeIndex){
			this.neighbourList = new ArrayList<Edge>();
			this.visited = false;
			this.selfIndex = nodeIndex;
			this.parent = -1; //Unknown at this time
		}
	}
	
	/**
	 * Edges are sorted by weight.
	 * An edge is directed!
	 */
	public static class Edge implements Comparator<Edge>, Comparable<Edge>{
		public int weight;
		public int from;
		public int to;
		
		/**
		 * Initialize an edge
		 * This connects two nodes
		 * @param from the starting point of this edge
		 * @param to the destination of this edge
		 * @param weight the distance or cost for this edge
		 */
		public Edge(int from, int to, int weight){
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		/**
		 * Compare on two edges based on their weights
		 * @return the value 0 if edge1.weight == edge2.weight; 
		 * 		a value less than 0 if edge1.weight < edge2.weight; 
		 * 		and a value greater than 0 if edge1.weight > edge2.weight
		 */
		@Override
		public int compare(Edge edge1, Edge edge2) {
			return Integer.compare(edge1.weight, edge2.weight);
		}
		
		/**
		 * Compares two edges based on their weight
		 * @param edge
		 * @return the value 0 if this.weight == edge.weight; 
		 * 		a value less than 0 if this.weight < edge.weight; 
		 * 		and a value greater than 0 if this.weight > edge.weight
		 */
		@Override
		public int compareTo(Edge edge) {
			return Integer.compare(this.weight, edge.weight);
		}
	}
	
	private int[] distances;
	
	
	/**
	 * Create this object to run Dijkstra's Algorithm
	 * The size of the graph (Node[] graph.length) is only used to create an equal sized distance array.
	 * This will only run from start, create a new object for other start positions.
	 * @param graph Initialized nodes with neighbors in their neighbor lists. 
	 * @param start Start node to do Dijkstra's from
	 */
	public Dijkstras(Node[] graph, int start){
		//Setup distances
		distances = new int[graph.length];
		for(int i = 0; i < distances.length; i++){
			distances[i] = Integer.MAX_VALUE;
		}
		
		//Setup Queue and start node
		PriorityQueue<Edge> q = new PriorityQueue<Edge>();
		q.addAll(graph[start].neighbourList);
		graph[start].visited = true;
		distances[start] = 0;

		Edge e;
		while(q.isEmpty() == false){
			e = q.poll();
			if(graph[e.to].visited == false){
				for(Edge nextEdge : graph[e.to].neighbourList){
					//Add the current distance traveled to the edges before adding them
					nextEdge.weight += e.weight;
				}
				//Add all edges to the queue
				q.addAll(graph[e.to].neighbourList);
				
				//Update current node
				graph[e.to].visited = true;
				distances[e.to] = e.weight;
				graph[e.to].parent = e.from;
			}
		}
	}
	
	/**
	 * Distances from start node when initialized this object
	 * @return distances from start node. Start node will be 0, others depends on the weight between the nodes
	 */
	public int[] getDistancesFrom(){
		return distances;
	}
	
	/**
	 * Gets distance from start to destination
	 * 0 if destination == start
	 * Integer.MAX_VALUE if it is disconnected from start (or just happen to be that value)
	 * @param destination the node index to get distance for
	 * @return the distance from start to destination. 
	 */
	public int queryDistance(int destination){
		return distances[destination];
	}
	
	/**
	 * Queries the distance from start to Destination.
	 * 0 if destination == start
	 * "Impossible" if destination is unreachable
	 * The value otherwise
	 * @param destination the node index
	 * @return value from start to destination, "Impossible" if unreachable
	 */
	public String kattisQueryDistance(int destination){
		return (distances[destination] == Integer.MAX_VALUE ? FAIL : distances[destination] + "");
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
			Node[] ns = new Node[nodes];
			for(int i = 0; i < nodes; i++){
				ns[i] = new Node(i);
			}
			
			for(int i = 0; i < edges; i++){
				int from = kio.getInt();
				int to = kio.getInt();
				int weight = kio.getInt();
				ns[from].neighbourList.add(new Edge(from, to, weight));
				//ns[to].neighbourList.add(new Edge(to, from, weight));
			}
			//READ AND SOLVE!
			
			Dijkstras dij = new Dijkstras(ns, start);
			
			for(int q = 0; q < queries; q++){
				kio.println(dij.kattisQueryDistance(kio.getInt()));
			}
			
			kio.flush();
			//Read next case or end of test cases
			nodes = kio.getInt();
			edges = kio.getInt();
			queries = kio.getInt();
			start = kio.getInt();
			if(nodes == 0 && edges == 0 && queries == 0 && start == 0){
				//end of test cases
				//kio.flush();
				kio.close();
				return;
			}
			kio.println();
		}
	}
}
