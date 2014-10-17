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

	public static class Node{
		public boolean visited;
		public int selfIndex;
		public ArrayList<Edge> neighbourList;
		public int parent;
		
		public Node(int index){
			this.neighbourList = new ArrayList<Edge>();
			this.visited = false;
			this.selfIndex = index;
			this.parent = -1; //Unknown at this time
		}
	}
	
	public static class Edge implements Comparator<Edge>, Comparable<Edge>{
		public int weight;
		public int from;
		public int to;
		
		public Edge(int from, int to, int weight){
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compare(Edge edge1, Edge edge2) {
			return Integer.compare(edge1.weight, edge2.weight);
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.weight, o.weight);
		}
		

	}
	
	private Node[] nodes;
	private int[] distances;
	
	
	
	public Dijkstras(Node[] graph, int start){
		nodes = graph;
		//System.err.println(start);
		distances = new int[graph.length];
		for(int i = 0; i < distances.length; i++){
			distances[i] = Integer.MAX_VALUE;
		}
		
		PriorityQueue<Edge> q = new PriorityQueue<Edge>();
		q.addAll(nodes[start].neighbourList);
		distances[start] = 0;
		nodes[start].visited = true;
		
		Edge e;
		while(q.isEmpty() == false){
			e = q.poll();
			if(nodes[e.to].visited == false){
				nodes[e.to].visited = true;
				for(Edge nextEdge : nodes[e.to].neighbourList){
					nextEdge.weight += e.weight;
				}
				q.addAll(nodes[e.to].neighbourList);
				distances[e.to] = e.weight;
				nodes[e.to].parent = e.from;
			}
		}
	}
	
	public int[] getDistancesFrom(){
		return distances;
	}
	
	public int queryDistance(int destination){
		return distances[destination];
	}
	
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
