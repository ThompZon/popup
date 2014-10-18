package popup.lab2;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Authors Thomas Sjöholm and Alexander Gomez
 */
public class BellmanFord {
    private static final String FAILNOPATH = "Impossible";
    private static final String FAILNEGCYCLE = "-Infinity";
    private static final int NOPARENT = -1;

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

    private int[] distances;	//The distances from start to [i]
    private int[] parent;		//When traveling from start to [i], where did I come from?
    private boolean[] hasNegativeCycle;		//[i] is part of a negative cycle if true, false otherwise

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
    public BellmanFord(Edge[] edges, int nodes, int start) {
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
        distances[start] = 0;
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

                if (distances[from] + edges[i].weight < distances[to]) {
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
    
    /**
     * Queries the distance from start to Destination.
	 * 0 if destination == start
	 * "Impossible" if destination is unreachable
	 * "-Infinity" if destination is part of a negative cycle (or reached through a negative cycle)
	 * The value otherwise
     * @param destination The node index to get the distance from start to destination
     * @return the distance from start to destination
     */
    public String kattisQueryDistance(int destination) {
        if (hasNegativeCycle[destination]) {
            return FAILNEGCYCLE;
        }
        return (distances[destination] == Integer.MAX_VALUE ? FAILNOPATH : distances[destination] + "");
    }
    
    /**
	 * Distances from start node when initialized this object. 
	 * The nodes will have the following values:
	 * 0 if node index == start (OR just happens to pass through nodes so this happens!)
	 * Integer.MAX_VALUE if it is disconnected from start (or just happen to be that value)
	 * Integer.MIN_VALUE if destination is part of a negative cycle (or passes through a negative cycle, OR just happen to be that value)
	 * 
	 * For most input data, Integer.MIN_VALUE and Integer.MAX_VALUE is indicating negative cycle/unreachable
	 * But for seeing negative cycle, use the method to get that!
	 * @return distances from start node
	 */
	public int[] getDistances(){
		return distances;
	}
	
	/**
	 * Gets distance from start to destination
	 * 0 if destination == start
	 * Integer.MAX_VALUE if it is disconnected from start (or just happen to be that value)
	 * Integer.MIN_VALUE if destination is part of a negative cycle (or passes through a negative cycle, OR just happen to be that value)
	 * 
	 * For most input data, Integer.MIN_VALUE and Integer.MAX_VALUE is indicating negative cycle/unreachable
	 * But for seeing negative cycle, use the method to get that!
	 * @param destination the node index to get distance for
	 * @return the distance from start to destination. 
	 */
	public int queryDistance(int destination){
		return distances[destination];
	}
	
	/**
	 * Check if the destination is part of a negative cycle or not. 
	 * @param destination The node index to see if it is part of negative cycle or not
	 * @return true if part of negative cycle, false otherwise
	 */
	public boolean isNegativeCycle(int destination){
		return hasNegativeCycle[destination];
	}
	
	/**
	 * Gets indications if nodes are part of negative cycle or not.
	 * Array with value true if the node with index [i] is part of negative cycle, false otherwise
	 * @return array indicating if some node is part of negatie cycle
	 */
	public boolean[] getNegativeCycleArray(){
		return hasNegativeCycle;
	}
	
	/**
	 * Gets the index for the nodes visited when traveling from start to destination in reversed order. 
	 * Takes O(n) time when n is the number of nodes visited when traveling from start to destination
	 * @param destination index of the destination node. Will crash if it is not a valid node index, return null if not visited or part of negative cycle
	 * @return A list with the indexes of nodes starting with destination, ..., start going towards start. Return null if not visited or part of negative cycle. Return only start node if destination == start
	 */
	public ArrayList<Integer> getPath(int destination){
		if(parent[destination] == NOPARENT || hasNegativeCycle[destination] == true){ //will crash if out of bounds!
			return null; //Never visited or is part of negative cycle, path is undefined!
		}
		int curr = destination; 
		//Actually build the path
		ArrayList<Integer> nodesInPath = new ArrayList<Integer>();
		while(parent[curr] != curr){ //has itself as parent
			nodesInPath.add(curr);
			curr = parent[curr];
		}
		nodesInPath.add(curr); //add start node too!
		return nodesInPath;
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
        while (true) {
        	//Read input for this test case:
            Edge[] edgeList = new Edge[edges];
            for (int i = 0; i < edges; i++) {
                int from = kio.getInt();
                int to = kio.getInt();
                int weight = kio.getInt();
                edgeList[i] = new Edge(from, to, weight);
            }
            
            //Run the algorithm:
            BellmanFord bellman = new BellmanFord(edgeList, nodes, start);
            
            //Run the queries:
            for (int q = 0; q < queries; q++) {
                kio.println(bellman.kattisQueryDistance(kio.getInt()));
            }

            //Read next case or end of test cases
            nodes = kio.getInt();
            edges = kio.getInt();
            queries = kio.getInt();
            start = kio.getInt();
            if (nodes == 0 && edges == 0 && queries == 0 && start == 0) {
                //end of test cases
                kio.flush();
                kio.close();
                return;
            }
            //New line between test cases:
            kio.println();
        }
    }
}