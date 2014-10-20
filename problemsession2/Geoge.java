package popup.problemsession2;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
6 5
1 6 20 4
5 3 2 4
1 2 2
2 3 8
2 4 3
3 6 10
3 5 15


8 9
1 5 5 5
1 2 3 4 5
1 2 8
2 7 4
2 3 10
6 7 40
3 6 5
6 8 3
4 8 4
4 5 5
3 4 23
 */
public class Geoge {
	private static final boolean DEBUGGING = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//DEBUGGING = false;
		new Geoge();
	}

	public Geoge(){
		Kattio kio = new Kattio(System.in);
		int interesctions = kio.getInt();
		int streets = kio.getInt();

		Node[] graph = new Node[interesctions + 1];

		for(int i = 1; i < interesctions + 1; i++){
			graph[i] = new Node(i);
		}

		int lucaStartNode = kio.getInt();
		int lucaDestination = kio.getInt();
		int lucaDelay = kio.getInt();
		int georgeIntersections = kio.getInt();

		ArrayDeque<Integer> georgeVisit = new ArrayDeque<Integer>();
		for(int g = 0; g < georgeIntersections; g++){
			//1 kio.getint / g++
			georgeVisit.add(kio.getInt());
		}

		for(int m = 0; m < streets; m++){
			//connection from x -> y tar tid: T
			int from = kio.getInt();
			int to = kio.getInt();
			int time = kio.getInt();

			graph[from].neighbourList.put(to, new Edge(from, to, time));
			graph[to].neighbourList.put(from, new Edge(to, from, time));
		}

		int startTime = 0;
		int prev = georgeVisit.poll();
		while(georgeVisit.isEmpty() == false){
			int curr = georgeVisit.poll();

			graph[prev].neighbourList.get(curr).blockstart = startTime;
			graph[curr].neighbourList.get(prev).blockstart = startTime;

			startTime += graph[prev].neighbourList.get(curr).weight;
			prev = curr;
		}

		DijkstrasTime(graph, lucaStartNode, lucaDelay, lucaDestination);
		
		kio.println(distances[lucaDestination] - lucaDelay);
		kio.flush();
		kio.close();
	}

	int[] distances;

	/**
	 * Create this object to run Dijkstra's Algorithm The size of the graph
	 * (Node[] graph.length) is only used to create an equal sized distance
	 * array. This will only run from start, create a new object for other start
	 * positions.
	 *
	 * @param graph Initialized nodes with neighbors in their neighbor lists.
	 * @param startNode Start node to do Dijkstra's from
	 */
	public void DijkstrasTime(Node[] graph, int startNode, int startTime, int destination) {
		//Setup distances
		distances = new int[graph.length];
		for (int i = 0; i < distances.length; i++) {
			distances[i] = Integer.MAX_VALUE;
		}

		//Setup Queue and start node
		PriorityQueue<Edge> q = new PriorityQueue<Edge>();

		//Calculate weights for the edges from start when t = 0;
		for (Edge nextEdge : graph[startNode].neighbourList.values()) {
			if(startTime < nextEdge.blockstart || startTime >= nextEdge.blockstart + nextEdge.weight){
				nextEdge.weight += startTime;
				q.add(nextEdge);
			}else{
				nextEdge.weight += nextEdge.blockstart - startTime + nextEdge.weight;
				q.add(nextEdge);
			}
		}

		graph[startNode].visited = true;
		graph[startNode].parent = startNode;
		distances[startNode] = 0;

		Edge e;
		while (q.isEmpty() == false) {
			e = q.poll();
			if (graph[e.to].visited == false) {
				if(e.to == destination){
					distances[e.to] = e.weight;
					//LUCA FOUND DESTINATION!
					return;
				}
				for (Edge nextEdge : graph[e.to].neighbourList.values()) {
					//Calculate times on the edges and add them
					if(e.weight <= nextEdge.blockstart || e.weight >= nextEdge.blockstart + nextEdge.weight){
						nextEdge.weight += e.weight;
						//GO NO WAIT
						//q.add(nextEdge);
					}else{
						//WAIT!
						nextEdge.weight += nextEdge.blockstart + nextEdge.weight ;
						//q.add(nextEdge);
					}
					//Add edge to priority queue
					q.add(nextEdge);
				}
				//Update current node
				graph[e.to].visited = true;
				distances[e.to] = e.weight;
				graph[e.to].parent = e.from;
			}
		}
	}

	/**
	 * The Node is a node in the graph of this problem The selfIndex and parent
	 * is used to be able to do the construction of some path. If parent is -1,
	 * then it is disconnected from the start! 
	 */
	public static class Node {

		public boolean visited;
		public Map<Integer, Edge> neighbourList;

		public int selfIndex;
		public int parent;

		/**
		 * Creates a node, initialize the neighbor list.
		 *
		 * @param nodeIndex Index of this node, used to create paths to some
		 * object
		 */
		public Node(int nodeIndex) {
			this.neighbourList = new HashMap<Integer, Edge>();
			this.visited = false;
			this.selfIndex = nodeIndex;
			this.parent = -1; //Unknown at this time
		}
	}

	/**
	 * Edges are sorted by weight. An edge is directed!
	 */
	public static class Edge implements Comparator<Edge>, Comparable<Edge> {
		public int weight;
		public int from;
		public int to;

		public int blockstart;

		/**
		 * Initialize an edge This connects two nodes
		 *
		 * @param from the starting point of this edge
		 * @param to the destination of this edge
		 * @param weight the distance or cost for this edge
		 */
		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.blockstart = Integer.MAX_VALUE;
			this.weight = weight;
		}

		/**
		 * Initialize an edge that connects two nodes.
		 * 
		 * The times at which this edge can be traveled at is calculated by
		 * tZero+t*p for all 0=< t < 2^31. In case of p being zero the edge can only
		 * be passed when time is tZero.
		 * 
		 * @param from  the starting node of this edge
		 * @param to    the destination node of this edge
		 * @param tZero the earliest time this edge can be used at
		 * @param p     the interval after tZero which this node can be used at
		 * @param weight the time of cost for this edge
		 */
		private Edge(int from, int to, int tZero, int weight) {
			this.from = from;
			this.to = to;
			this.blockstart = tZero;
			this.weight = weight;
		}

		/**
		 * Compare on two edges based on their weights
		 *
		 * @return the value 0 if edge1.weight == edge2.weight; a value less
		 * than 0 if edge1.weight < edge2.weight; and a value greater than 0 if
		 * edge1.weight > edge2.weight
		 */
		@Override
		public int compare(Edge edge1, Edge edge2) {
			return Integer.compare(edge1.weight, edge2.weight);
		}

		/**
		 * Compares two edges based on their weight
		 *
		 * @param edge
		 * @return the value 0 if this.weight == edge.weight; a value less than
		 * 0 if this.weight < edge.weight; and a value greater than 0 if
		 * this.weight > edge.weight
		 */
		@Override
		public int compareTo(Edge edge) {
			return Integer.compare(this.weight, edge.weight);
		}
	}

}
