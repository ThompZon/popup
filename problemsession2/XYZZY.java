package popup.problemsession2;

import java.util.ArrayList;
import java.util.Comparator;

/**
5
0 1 2
-60 1 3
-60 1 4
20 1 5
0 0
5
0 1 2
20 1 3
-60 1 4
-60 1 5
0 0
5
0 1 2
21 1 3
-60 1 4
-60 1 5
0 0
5
0 1 2
20 2 1 3
-60 1 4
-60 1 5
0 0
-1
 */
public class XYZZY {
	private static final boolean DEBUGGING = true;
	private static final int NOPARENT = -1;
	private static final String FAIL = "hopeless";
	private static final String SUCCESS = "winnable";
	
	/**
     * Edges are sorted by weight. An edge is directed!
     */
    public static class Edge implements Comparator<Edge>, Comparable<Edge> {
        public int weight;
        public int from;
        public int to;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new XYZZY();
	}

	public XYZZY(){
		Kattio kio = new Kattio(System.in);
		int rooms = kio.getInt();
		int[] roomValue;
		ArrayList<Edge> edges = new ArrayList<Edge>();
		while(rooms != -1){
			//INPUT:
			roomValue = new int[rooms + 1];
			edges.clear();
			for(int i = 1; i < rooms + 1; i++){
				roomValue[i] = -(kio.getInt());
				int edgesFromThisRoom = kio.getInt();
				for(int j = 0; j < edgesFromThisRoom; j++){
					edges.add(new Edge(i, kio.getInt(), 0));
				}
			}
			for(int i = 0; i < edges.size(); i++){
				Edge e = edges.get(i);
				e.weight = roomValue[e.to];
			}
			
			//SOLVE:
			Edge[] es = edges.toArray(new Edge[edges.size()]);
			BellmanFord(es, rooms + 1, 1);
			
			//OUTPUT:
			if(distances[rooms] < 0){
				kio.println(SUCCESS);
			}else{
				kio.println(FAIL);
			}
			
			//NEXT CASE:
			rooms = kio.getInt();
		}
		kio.flush();
		kio.close();
	}
	
	boolean[] hasNegativeCycle;
	int[] distances;
	int[] parent;
	
	/**
     * When creating this object, it will run BellmanFord's algorithm on input data.
     * 
     * It will find the shortest path from start to all other nodes. The nodes are indexed 0 .. (n-1) where n is the number of nodes.
     * The graph is directed, for an undirected graph, use two edges to indicate that.
     * 
     * Edges can have negative values. If a negative cycle is detected, they will be marked and the distance for that node is not trustworthy
     * @param edges The edges in the graph
     * @param nodes The number of nodes in the graph
     * @param start The starting node that you want to know the minimum distance from.
     */
    public void BellmanFord(Edge[] edges, int nodes, int start) {
        distances = new int[nodes];
        parent = new int[nodes];
        hasNegativeCycle = new boolean[nodes];
        boolean[] checked = new boolean[nodes];	//used when checking for negative cycles
        boolean negativeCycle = false;			//has ANY negative cycle

        //Step 1: Initialize
        for (int i = 0; i < nodes; i++) {
            distances[i] = Integer.MAX_VALUE;
            parent[i] = NOPARENT;
            hasNegativeCycle[i] = false;
        }
        distances[start] = -100;
        parent[start] = start; //start is itselves parent
        
        //Step 2: Find shortest distance from start to any other node
        for (int step = 0; step < nodes; step++) {
            for (int i = 0; i < edges.length; i++) {
                int from = edges[i].from;
                if (distances[from] == Integer.MAX_VALUE) {
                    //infinite + something == infinite, therefore skip this one!
                    continue;
                }
                int to = edges[i].to;
                if (distances[from] + edges[i].weight < distances[to] && distances[from] + edges[i].weight < 0) {
                    distances[to] = distances[from] + edges[i].weight;
                    //System.err.println("from " + from + " to " + to + " dist[to] " + distances[to]);
                    parent[to] = from;
                }
            }
        }
        
        //Step 3-1: Detect Negative Cycles:
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i].from;
            int to = edges[i].to;
            
            if(hasNegativeCycle[from] && hasNegativeCycle[to]){
                continue;
            }
            if(parent[from] == NOPARENT){
                checked[from] = true;
                continue;
            }
            if (distances[from] + edges[i].weight < distances[to]) {
                negativeCycle = true;
                int tmp = from;
                while(tmp != to && tmp >= 0 && !checked[tmp]){
                	//Mark the whole negative cycle as negative cycle! 
                	//Note that this will not detect elements that branch from the negative cycle as negative cycle!
                    markNegativeCycle(checked, tmp);
                    tmp = parent[tmp];
                }
                markNegativeCycle(checked, to);
            }
        }
        if(negativeCycle == false){
        	//If no negative cycle detected, then we are done!
            return;
        }
        //Step 3-2: Mark ALL elements that are reached through a negative cycle as negative cycle!
        checked[start] = true; //We have checked start, prevents infinite loop
        for(int i = 0; i < nodes; i++){
            if(checked[i] == false){
                int tmp = parent[i];
                checked[i] = true;
                while(true){
                	//Mark elements reached t
                    if(tmp == NOPARENT){
                        break;
                    }else if(checked[tmp] && hasNegativeCycle[tmp] == false){
                    	//Reached start or a non-negative-cycle part of the graph, done checking this part
                        break;
                    }else if(hasNegativeCycle[tmp]){
                    	//If parent of i has negative cycle
                    	//then reach through everything reached by it and mark as negative cycle
                        tmp = i;
                        while(hasNegativeCycle[tmp] == false){
                            markNegativeCycle(checked, tmp);
                            tmp = parent[tmp];
                        }
                        break;
                    }
                    checked[tmp] = true;
                    tmp = parent[tmp];
                }
            }
        }
    }

	/**
	 * Marks the node as part of negative cycle
	 * @param checked
	 * @param nodeIndex
	 */
	private void markNegativeCycle(boolean[] checked, int nodeIndex) {
		hasNegativeCycle[nodeIndex] = true;
		checked[nodeIndex] = true;
		distances[nodeIndex] = Integer.MIN_VALUE;
	}

}
