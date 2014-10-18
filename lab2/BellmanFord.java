package popup.lab2;

import java.util.Comparator;

/**
 * Authors Thomas Sjöholm and Alexander Gomez
 * 
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

    private int[] distances;
    private int[] parent;
    private boolean[] hasNegativeCycle;
    private boolean[] checked;

    public BellmanFord(Edge[] edges, int nodes, int start) {
        distances = new int[nodes];
        parent = new int[nodes];
        hasNegativeCycle = new boolean[nodes];
        checked = new boolean[nodes];
        boolean negativeCycle = false;

        for (int i = 0; i < nodes; i++) {
            distances[i] = Integer.MAX_VALUE;
            parent[i] = NOPARENT;
            hasNegativeCycle[i] = false;
        }
        distances[start] = 0;
        parent[start] = start; //start is itselves parent

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

        for (int i = 0; i < edges.length; i++) {
            
            int from = edges[i].from;
            int to = edges[i].to;

            if(hasNegativeCycle[from] && hasNegativeCycle[to]){
                continue;
            }
            if(parent[from] == -1){
                checked[from] = true;
                continue;
            }
            if (distances[from] + edges[i].weight < distances[to]) {
                negativeCycle = true;
                int tmp = from;
                while(tmp != to && tmp >= 0 && !checked[tmp]){
                    hasNegativeCycle[tmp] = true;
                    checked[tmp] = true;
                    tmp = parent[tmp];
                }
                //hasNegativeCycle[from] = true;
                //checked[from] = true;
                hasNegativeCycle[to] = true;
                checked[to] = true;
            }
        }
        if(!negativeCycle){
            return;
        }
        checked[start] = true; //?
        //ArrayList<Integer> path = new ArrayList();
        for(int i = 0; i <nodes; i++){
            if(!checked[i]){
                
                int tmp = parent[i];
                checked[i] = true;
                //checked[tmp] = true;
                while(true){
                    if(tmp<0){
                        break;
                    }else if(checked[tmp] && !hasNegativeCycle[tmp]){
                        break;
                    }else if(hasNegativeCycle[tmp]){
                        
                        tmp = i;
                        while(!hasNegativeCycle[tmp]){
                            hasNegativeCycle[tmp] = true;
                            checked[tmp] = true; //?
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

    public String kattisQueryDistance(int destination) {
        
        if (hasNegativeCycle[destination]) {
            return FAILNEGCYCLE;
        }
        
        return (distances[destination] == Integer.MAX_VALUE ? FAILNOPATH : distances[destination] + "");
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
            Edge[] edgeList = new Edge[edges];
            for (int i = 0; i < edges; i++) {
                int from = kio.getInt();
                int to = kio.getInt();
                int weight = kio.getInt();
                edgeList[i] = new Edge(from, to, weight);
            }

            BellmanFord bellman = new BellmanFord(edgeList, nodes, start);

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
            kio.println();
        }
    }

}
