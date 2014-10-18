package popup.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class does Dijkstra's Algorithm. It calculates the shortest path from a
 * start node to every other node
 *
 * When constructed, it will do the bulk work. After the construction, querying
 * the distance from start to some destination is a fast operation (constant
 * time actually).
 *
 * Getting the path from start to destination takes O(n) time with n nodes
 * between start and destination.
 *
 * Kattis url: https://kth.kattis.com/problems/shortestpath2
 * 
 * 
4 4 4 0
0 1 15 10 5
1 2 15 10 5
0 2 5 5 30
3 0 0 1 1
0
1
2
3
2 1 1 0
0 1 100 0 5
1
0 0 0 0
 */
public class DijkstrasTime {

    private static final String FAIL = "Impossible";

    /**
     * The Node is a node in the graph of this problem The selfIndex and parent
     * is used to be able to do the construction of some path If parent is -1,
     * then it is disconnected from the start!
     */
    public static class Node {

        public boolean visited;
        public ArrayList<Edge> neighbourList;

        public int selfIndex;
        public int parent;

        /**
         * Creates a node, initialize the neighbor list.
         *
         * @param nodeIndex Index of this node, used to create paths to some
         * object
         */
        public Node(int nodeIndex) {
            this.neighbourList = new ArrayList<Edge>();
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
        public int tZero;
        public int p;
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

        private Edge(int from, int to, int tZero, int p, int weight) {
            this.from = from;
            this.to = to;
            this.tZero = tZero;
            this.p = p;
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

    private int[] distances;
    private Node[] graph;

    /**
     * Create this object to run Dijkstra's Algorithm The size of the graph
     * (Node[] graph.length) is only used to create an equal sized distance
     * array. This will only run from start, create a new object for other start
     * positions.
     *
     * @param graph Initialized nodes with neighbors in their neighbor lists.
     * @param start Start node to do Dijkstra's from
     */
    public DijkstrasTime(Node[] graph, int start) {
        this.graph = graph;
        //Setup distances
        distances = new int[graph.length];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        //Setup Queue and start node
        PriorityQueue<Edge> q = new PriorityQueue<Edge>();
        
        
        for(Edge nextEdge:graph[start].neighbourList){
            nextEdge.weight += nextEdge.tZero;
            q.add(nextEdge);
        }
        //q.addAll(graph[start].neighbourList);
        graph[start].visited = true;
        graph[start].parent = start;
        distances[start] = 0;

        Edge e;
        while (q.isEmpty() == false) {
            e = q.poll();
            if (graph[e.to].visited == false) {
                for (Edge nextEdge : graph[e.to].neighbourList) {
                    //Add the current distance traveled to the edges before adding them
                    
                    if(nextEdge.p==0){
                        if(e.weight>nextEdge.tZero){
                            continue;
                        }
                        nextEdge.weight += e.weight + nextEdge.tZero-e.weight;
                        
                    }else{               
                        
                        nextEdge.weight += e.weight + (nextEdge.tZero + nextEdge.p*((int)Math.ceil((double)Math.max(0.0,e.weight-nextEdge.tZero)/nextEdge.p))) - e.weight;
                    }
                    
                    q.add(nextEdge);
                }
                //Add all edges to the queue
                
                //q.addAll(graph[e.to].neighbourList);

                //Update current node
                graph[e.to].visited = true;
                distances[e.to] = e.weight;
                graph[e.to].parent = e.from;
            }
        }
    }

    /**
     * Distances from start node when initialized this object
     *
     * @return distances from start node. Start node will be 0, others depends
     * on the weight between the nodes
     */
    public int[] getDistances() {
        return distances;
    }

    /**
     * Gets distance from start to destination 0 if destination == start
     * Integer.MAX_VALUE if it is disconnected from start (or just happen to be
     * that value)
     *
     * @param destination the node index to get distance for
     * @return the distance from start to destination.
     */
    public int queryDistance(int destination) {
        return distances[destination];
    }

    /**
     * Queries the distance from start to Destination. 0 if destination == start
     * "Impossible" if destination is unreachable The value otherwise
     *
     * @param destination the node index
     * @return value from start to destination, "Impossible" if unreachable
     */
    public String kattisQueryDistance(int destination) {
        return (distances[destination] == Integer.MAX_VALUE ? FAIL : distances[destination] + "");
    }

    /**
     * Gets the index for the nodes visited when traveling from start to
     * destination in reversed order Takes O(n) time when n is the number of
     * nodes visited when traveling from start to destination
     *
     * @param destination index of the destination node. Will crash if it is not
     * a valid node index, return null if not visited
     * @return A list with the indexes of nodes starting with destination, ...,
     * start going towards start. Return null if not visited. Return only start
     * node if destination == start
     */
    public ArrayList<Integer> getPath(int destination) {
        Node curr = graph[destination]; //will crash if out of bounds!
        if (curr.visited == false || curr.parent == -1) {
            return null; //Never visited!
        }
        //Acutally build the path
        ArrayList<Integer> nodesInPath = new ArrayList<Integer>();
        while (curr.parent != curr.selfIndex) {
            nodesInPath.add(curr.selfIndex);
            curr = graph[curr.parent];
        }
        nodesInPath.add(curr.selfIndex); //add start node too!
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
            Node[] ns = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                ns[i] = new Node(i);
            }

            for (int i = 0; i < edges; i++) {
                int from = kio.getInt();
                int to = kio.getInt();
                int tZero = kio.getInt();
                int p = kio.getInt();
                int weight = kio.getInt();
                ns[from].neighbourList.add(new Edge(from, to,tZero,p, weight));
                //ns[to].neighbourList.add(new Edge(to, from, weight)); //With this, it is undirected with same values for both directions
            }
			//READ AND SOLVE!

            DijkstrasTime dij = new DijkstrasTime(ns, start);

            for (int q = 0; q < queries; q++) {
                kio.println(dij.kattisQueryDistance(kio.getInt()));
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
            kio.println();
        }
    }
}
