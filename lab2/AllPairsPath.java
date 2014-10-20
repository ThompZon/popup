/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup.lab2;

import java.util.Comparator;

/**
 *
 * @author Alexander
 */
public class AllPairsPath {

    private static final String FAILNOPATH = "Impossible";
    private static final String FAILNEGCYCLE = "-Infinity";
    private static final int INFINITY = (Integer.MAX_VALUE - 4) / 2;

    private String distance(int from, int to) {

        if (distances[from][to] == INFINITY) {
            return FAILNOPATH;
        }

        if (distances[from][from] < 0 || distances[to][to] < 0) {
            return FAILNEGCYCLE;
        }

        boolean infinite = true;
        int parent = to;
        for (int i = 0; i < parents.length; i++) {
            
            parent = parents[from][parent];
            if (parent == -1) {
                return FAILNOPATH;
            }
            if (parent == from) {
                infinite = false;
                break;
            }

            if ( distances[parent][parent] < 0) {
                return FAILNEGCYCLE;
            }

        }
        if (infinite) {
            return FAILNEGCYCLE;
        }

        return distances[from][to] + "";
    }

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
    private int[][] distances;
    private int[][] parents;

    AllPairsPath(Edge[] edgeList, int nodes) {
        distances = new int[nodes][nodes];
        parents = new int[nodes][nodes];

        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                distances[i][j] = INFINITY;
                parents[i][j] = -1;
            }
            distances[i][i] = 0;
            parents[i][i] = i;
        }

        for (Edge e : edgeList) {
            if (e.weight < distances[e.from][e.to]) {
                distances[e.from][e.to] = e.weight;
                parents[e.from][e.to] = e.from;
            }
        }

        for (int k = 0; k < nodes; k++) {
            for (int i = 0; i < nodes; i++) {
                for (int j = 0; j < nodes; j++) {

                    int tmp;
                    if (distances[i][k] == INFINITY || distances[k][j] == INFINITY) {
                        tmp = INFINITY;
                    } else {
                        tmp = distances[i][k] + distances[k][j];
                    }
                    if (distances[i][j] > tmp) {
                        distances[i][j] = tmp;
                        parents[i][j] = k;
                    }
                }
            }
        }

//        for (int i = 0; i < nodes; i++) {
//            for (int j = 0; j < nodes; j++) {
//                System.err.print(distances[i][j] + " ");
//            }
//            System.err.println();
//        }
//       System.err.println();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Kattio kio = new Kattio(System.in, System.out);

        int nodes, edges, queries, from, to, weight;
        Edge[] edgeList;
        AllPairsPath app;

        nodes = kio.getInt();
        edges = kio.getInt();
        queries = kio.getInt();

        while (true) {

            edgeList = new Edge[edges];

            for (int i = 0; i < edges; i++) {
                from = kio.getInt();
                to = kio.getInt();
                weight = kio.getInt();
                edgeList[i] = new Edge(from, to, weight);
            }
            app = new AllPairsPath(edgeList, nodes);

            for (int i = 0; i < queries; i++) {
                from = kio.getInt();
                to = kio.getInt();
                kio.println(app.distance(from, to));
            }

            nodes = kio.getInt();
            edges = kio.getInt();
            queries = kio.getInt();
            if (nodes == 0 && edges == 0 && queries == 0) {
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
