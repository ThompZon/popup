/**
 * 
 */
package popup.lab2;

import java.util.ArrayList;

/**
 * @author ThompZon
 *
 */
public class FordFulkerson {
	public static class Node{
		public ArrayList<Edge> neigbors;
		
	}
	
	public static class Edge{
		public int capacity;
		public int from;
		public int to;
		public Edge rEdge;

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
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Kattio kio = new Kattio(System.in);
		int nodes = kio.getInt();
		int edgeNumberOf = kio.getInt();
		int source = kio.getInt();
		int sink = kio.getInt();
		Edge[] edges = new Edge[edgeNumberOf];
		for(int i = 0; i < edgeNumberOf; i++){
			edges[i] = new Edge(kio.getInt(), kio.getInt(), kio.getInt());
		}
		
		kio.flush();
		kio.close();
	}

}


/**
class Edge(object):
    def __init__(self, u, v, w):
        self.source = u
        self.sink = v  
        self.capacity = w
    def __repr__(self):
        return "%s->%s:%s" % (self.source, self.sink, self.capacity)

class FlowNetwork(object):
    def __init__(self):
        self.adj = {}
        self.flow = {}

    def add_vertex(self, vertex):
        self.adj[vertex] = []

    def get_edges(self, v):
        return self.adj[v]

    def add_edge(self, u, v, w=0):
        if u == v:
            raise ValueError("u == v")
        edge = Edge(u,v,w)
        redge = Edge(v,u,0)
        edge.redge = redge
        redge.redge = edge
        self.adj[u].append(edge)
        self.adj[v].append(redge)
        self.flow[edge] = 0
        self.flow[redge] = 0

    def find_path(self, source, sink, path):
        if source == sink:
            return path
        for edge in self.get_edges(source):
            residual = edge.capacity - self.flow[edge]
            if residual > 0 and edge not in path:
                result = self.find_path( edge.sink, sink, path + [edge]) 
                if result != None:
                    return result

    def max_flow(self, source, sink):
        path = self.find_path(source, sink, [])
        while path != None:
            residuals = [edge.capacity - self.flow[edge] for edge in path]
            flow = min(residuals)
            for edge in path:
                self.flow[edge] += flow
                self.flow[edge.redge] -= flow
            path = self.find_path(source, sink, [])
        return sum(self.flow[edge] for edge in self.get_edges(source))
 */