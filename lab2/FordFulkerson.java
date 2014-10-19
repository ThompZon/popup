package popup.lab2;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author ThompZon
4 5 0 3
0 1 10
1 2 1
1 3 1
0 2 1
2 3 10
 */
public class FordFulkerson {
	public static final int NOPARENT = -1;
	public static final boolean DEBUGGING = false;
	
	public static class Node{
		public Map<Integer, Edge> neigbors;
		public int index;
		public int parent;
		public boolean visited;
		
		public Node(int i){
			this.neigbors = new HashMap<Integer, Edge>();
			this.index = i;
			this.visited = false;
			this.parent = NOPARENT;
		}
	}
	
	public static class Edge {
		public int from;
		public int to;
		
		public int capacity;
		public int flow;

		/**
		 * Initialize an edge This connects two nodes
		 *
		 * @param from the starting point of this edge
		 * @param to the destination of this edge
		 * @param capacity the distance or cost for this edge
		 */
		public Edge(int from, int to, int capacity) {
			this.from = from;
			this.to = to;
			this.capacity = capacity;
			this.flow = 0;
		}
	}
	
	private Set<Edge> edgesUsed;
	private Node[] nodes;
	private int source;
	private int sink;
	private int maxFlow;
	
	public FordFulkerson(int nodesNumberOf){
		this.nodes = new Node[nodesNumberOf];
	}
	
	public FordFulkerson(Node[] nodeArray, int source, int sink){
		this.nodes = nodeArray;
		this.source = source;
		this.sink = sink;
		this.edgesUsed = new HashSet<Edge>();
	}
	
	public void setSource(int nodeIndex){
		this.source = nodeIndex;
	}
	
	public void setSink(int nodeIndex){
		this.sink = nodeIndex;
	}
	
	public void setNode(int nodeIndex, Node newNode){
		this.nodes[nodeIndex] = newNode;
	}
	
	public void addEdge(int from, int to, int capacity){
		this.nodes[from].neigbors.put(to, new Edge(from, to, capacity));
		this.nodes[to].neigbors.put(from, new Edge(to, from, 0));
	}
	
	public int getMaxFlow(){
		return maxFlow;
	}
	
	public Set<Edge> getEdgesUsed(){
		return edgesUsed;
	}
	
	public Node[] getNodes(){
		return nodes;
	}
	
	public void runAlgorithm() {
		ArrayDeque<Node> queue = new ArrayDeque<Node>();
		this.maxFlow = 0;
		while (true) 
		{
			//Start with source:
			queue.offer(this.nodes[this.source]);
			for(short i = 0; i < nodes.length; i++)
			{
				//Reset path to find additional paths:
				nodes[i].visited = false;
				nodes[i].parent = NOPARENT;
			}
			//Visited start:
			nodes[this.source].visited = true;
			//DFS from source to sink
			while (queue.isEmpty() == false)
			{
				Node current = queue.poll();
				for(Edge e : current.neigbors.values())
				{
					if(e.capacity - e.flow > 0 && nodes[e.to].visited == false)
					{
						//if we have capacity left on this edge
						queue.offer(nodes[e.to]);
						nodes[e.to].visited = true;
						nodes[e.to].parent = e.from;
						if(e.to == this.sink)
						{
							//found sink, done for this round!
							queue.clear();
							break;
						}
					}
				}
			}
			
			//we compute the path maxCapacity
			//where = target
			int to = sink;
			if(nodes[to].parent == NOPARENT)
			{
				if(DEBUGGING)System.err.println("NO PATH FOUND");
				//If we failed to find path, we are finished here!
				break;
			} 
			else {
				int pathCapacity = Integer.MAX_VALUE;
				if(DEBUGGING)System.err.print("\n Backtracking: ");
				while (nodes[to].parent != NOPARENT) 
				{
					if(DEBUGGING)System.err.print(to + ", ");
					//Backtrack to see how much flow that this path can take
					Node from = nodes[nodes[to].parent]; 
					Edge e = from.neigbors.get(to);
					pathCapacity = Math.min(pathCapacity, e.capacity - e.flow);
					to = from.index;
				}
				if(DEBUGGING)System.err.println("\n");

				//Backtrack to set the flow to the maximum flow this path can take
				to = sink;
				while (nodes[to].parent != NOPARENT) 
				{
					Node from = nodes[nodes[to].parent]; 
					Edge e = from.neigbors.get(to);
					Edge rE = nodes[to].neigbors.get(from.index);
					
					e.flow += pathCapacity;
					rE.flow = -e.flow;
					
					if(DEBUGGING)System.err.println("EDGE - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
					if(DEBUGGING)System.err.println("BACKWARDS EDGE - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);

					//store used edges:
					if(e.flow > 0){
						if(DEBUGGING)System.err.println("EDGE IN SOLUTION - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
						edgesUsed.add(e);
					}else{
						if(DEBUGGING)System.err.println("EDGE DELETED - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
						edgesUsed.remove(e);
					}
					if(rE.flow > 0){
						if(DEBUGGING)System.err.println("BACK EDGE IN SOLUTION - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);
						edgesUsed.add(rE);
					}else{
						if(DEBUGGING)System.err.println("BACK DELETED - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);
						edgesUsed.remove(rE);
					}
					
					//Go to previous node:
					to = from.index;
				}
				//Update maxflow
				this.maxFlow += pathCapacity;
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in);
		
		//Intput:
		int nodeNumberOf = kio.getInt();
		int edgeNumberOf = kio.getInt();
		int source = kio.getInt();
		int sink = kio.getInt();
		Node[] nodes = new Node[nodeNumberOf];
		for(int i = 0; i < nodeNumberOf; i++){
			nodes[i] = new Node(i);
		}
		for(int i = 0; i < edgeNumberOf; i++){
			int from = kio.getInt();
			int to = kio.getInt();
			int capacity = kio.getInt();
			//forwards edge:
			nodes[from].neigbors.put(to, new Edge(from, to, capacity));
			//Backwards edge:
			nodes[to].neigbors.put(from, new Edge(to, from, 0));
		}
		
		//Run Algorithm:
		FordFulkerson ff = new FordFulkerson(nodes, source, sink);
		ff.runAlgorithm();
		
		//Output:
		int maxFlow = ff.getMaxFlow();
		Set<Edge> used = ff.getEdgesUsed();
		
		StringBuilder sb = new StringBuilder();
		sb.append(nodeNumberOf);
		sb.append(' ');
		sb.append(maxFlow);
		sb.append(' ');
		sb.append(used.size());
		//sb.append('\n');
		for(Edge e : used){
			sb.append('\n');
			sb.append(e.from);
			sb.append(' ');
			sb.append(e.to);
			sb.append(' ');
			sb.append(e.flow);
		}
		kio.println(sb.toString());
		
		//Close:
		kio.flush();
		kio.close();
	}
}