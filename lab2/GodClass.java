package popup.lab2;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * @author ThompZon
4 5 0 3
0 1 10
1 2 1
1 3 1
0 2 1
2 3 10
 */
public class GodClass {
	public static final int NOPARENT = -1;
	public static final boolean DEBUGGING = false;

	public static class Edge{
		public int from;
		public int to;
		
		public int capacity;
		public int flow;
		public int restcapacity;
		public Edge(int from, int to, int capacity){
			this.from = from;
			this.to = to;
			this.capacity = capacity;
			this.restcapacity = capacity;
			this.flow = 0;
		}
	}

	private Set<Edge> edgesUsed;
	
	private Map<Integer, Edge>[] neighborList;
	
	private int source;
	private int sink;
	private int maxFlow;
	private int nodesNumberOf;

	public GodClass(Map<Integer, Edge>[] neighLists, int source, int sink, int nodes){
		this.neighborList = neighLists;
		this.source = source;
		this.sink = sink;
		this.edgesUsed = new HashSet<Edge>();
		this.nodesNumberOf = nodes;
	}

	public int getMaxFlow(){
		return maxFlow;
	}

	public Set<Edge> getEdgesUsed(){
		return edgesUsed;
	}

	public void runAlgorithm() {
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		boolean[] visitedList = new boolean[nodesNumberOf];
		int[] parentList = new int[nodesNumberOf];
		
		this.maxFlow = 0;
		while (true) 
		{
			//Start with source:
			queue.push(this.source);
			for(short i = 0; i < nodesNumberOf; i++)
			{
				//Reset path to find additional paths:
				visitedList[i] = false;
				parentList[i] = NOPARENT;
			}
			//Visited start:
			visitedList[this.source] = true;
			//DFS from source to sink
			while (queue.isEmpty() == false)
			{
				int current = queue.pop();
				for(Edge edge : neighborList[current].values())
				{
					if(visitedList[edge.to] == false && edge.restcapacity > 0)
					{
						parentList[edge.to] = edge.from;
						if(edge.to == this.sink)
						{
							//found sink, done for this round!
							queue.clear();
							break;
						}
						//if we have capacity left on this edge
						queue.push(edge.to);
						visitedList[edge.to] = true;
					}
				}
			}

			int to = sink;
			if(parentList[to] == NOPARENT)
			{
				if(DEBUGGING)System.err.println("NO PATH FOUND");
				//If we failed to find path, we are finished here!
				return;
			} 
			else {
				//we compute the path maxCapacity
				int pathCapacity = Integer.MAX_VALUE;
				if(DEBUGGING)System.err.print("\n Backtracking: ");
				while (parentList[to] != NOPARENT) 
				{
					if(DEBUGGING)System.err.print(to + ", ");
					//Backtrack to see how much flow that this path can take
					int from = parentList[to]; 
					Edge e = neighborList[from].get(to);
					pathCapacity = Math.min(pathCapacity, e.restcapacity);
					to = from;
				}
				if(DEBUGGING)System.err.println("\n");

				//Backtrack to set the flow to the maximum flow this path can take
				to = sink;
				while (parentList[to] != NOPARENT) 
				{
					int from = parentList[to];
					
					Edge e = neighborList[from].get(to);
					e.flow += pathCapacity;
					e.restcapacity = e.capacity - e.flow;
					if(DEBUGGING)System.err.println("EDGE - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
					//store used edges:
					if(e.flow > 0){
						if(DEBUGGING)System.err.println("EDGE IN SOLUTION - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
						edgesUsed.add(e);
					}else{
						if(DEBUGGING)System.err.println("EDGE DELETED - from: " + e.from + ", to: " + e.to + ", flow: " + e.flow);
						edgesUsed.remove(e);
					}

					Edge rE = neighborList[to].get(from);
					rE.flow -= pathCapacity;
					rE.restcapacity = rE.capacity - rE.flow;
					if(DEBUGGING)System.err.println("BACKWARDS EDGE - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);
					//store used back edges:
					if(rE.flow > 0){
						if(DEBUGGING)System.err.println("BACK EDGE IN SOLUTION - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);
						edgesUsed.add(rE);
					}else{
						if(DEBUGGING)System.err.println("BACK DELETED - from: " + rE.from + ", to: " + rE.to + ", flow: " + rE.flow);
						edgesUsed.remove(rE);
					}
					//Go to previous node:
					to = from;
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
		@SuppressWarnings("unchecked")
		Map<Integer, Edge>[] list = new Map[nodeNumberOf];
		for(int i = 0; i < nodeNumberOf; i++){
			list[i] = new TreeMap<Integer, Edge>();
		}
		for(int i = 0; i < edgeNumberOf; i++){
			int from = kio.getInt();
			int to = kio.getInt();
			int capacity = kio.getInt();
			//forwards edge:
			list[from].put(to, new Edge(from, to, capacity));
			//Backwards edge:
			list[to].put(from, new Edge(to, from, 0));
		}

		//Run Algorithm:
		GodClass ff = new GodClass(list, source, sink, nodeNumberOf);
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