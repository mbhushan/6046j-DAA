package graph_DijkstraSSP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DijkstraSSP {
	private Graph graph = null;
	
	public static void main(String[] args) {
		int vertices = 5;
		DijkstraSSP dssp = new DijkstraSSP();
		
		dssp.init(vertices);
		
		int src = 1;
		dssp.dijkstra(src);
	}
	
	public void init(int vertices) {
		this.graph = new Graph(vertices);
		
		this.graph.addEdge(1, 2, 2);
		this.graph.addEdge(1, 3, 8);
		this.graph.addEdge(1, 4, 5);
		this.graph.addEdge(2, 3, 1);
		this.graph.addEdge(3, 5, 3);
		this.graph.addEdge(4, 5, 4);
		
		this.graph.printGraph();
	}
	
	public void dijkstra(int src) {
		this.graph.dijkstraSSP(src);
	}
}

class Distance {
	int vertex;
	int dist;
	
	Distance(int vertex) {
		this.vertex = vertex;
		dist = Integer.MAX_VALUE;
	}

	@Override
	public String toString() {
		return "Distance [vertex=" + vertex + ", dist=" + dist + "]";
	}
}

class DistanceComparator implements Comparator<Distance> {

	@Override
	public int compare(Distance o1, Distance o2) {
		return o1.dist - o2.dist;
	}
	
}

class Graph {
	GraphNode [] gnodes;
	int vertices;
	
	public Graph( int vertices) {
		this.vertices = vertices;
		this.gnodes = new GraphNode[this.vertices+1];
		for (int i=1; i<=this.vertices; i++) {
			gnodes[i] = new GraphNode(i);
		}
	}
	
	public void addEdge(int u, int v, int w) {
		this.gnodes[u].neighbors.add(gnodes[v]);
		this.gnodes[u].weights.add(w);
	}
	
	public void dijkstraSSP(int src) {
		int [] parent = new int[this.vertices+1];
		Distance [] distances = new Distance[this.vertices+1];
		PriorityQueue<Distance> minHeap = new PriorityQueue<Distance>(this.vertices, new DistanceComparator());
		
		Arrays.fill(parent, -1);
		for (int i=1; i<=this.vertices; i++) {
			distances[i] = new Distance(i);
			minHeap.add(distances[i]);
		}
		
		distances[src].dist = 0;
		minHeap.add(distances[src]);
		
		while (!minHeap.isEmpty()) {
			Distance dnode = minHeap.remove();
			int u = dnode.vertex;
			int uDist = dnode.dist;
			List<GraphNode> vlist = gnodes[u].neighbors;
			List<Integer> wlist = gnodes[u].weights;
			int size = vlist.size();
			for (int i=0; i<size; i++) {
				GraphNode v = vlist.get(i);
				int w = wlist.get(i);
				if (distances[v.label].dist > uDist + w) {
					distances[v.label].dist = uDist + w;
					minHeap.add(distances[v.label]);
					parent[v.label] = u;
				}
			}
		}
		
		System.out.println("(vertex -> parent, shortest distance from src)");

		for (int i=1; i<=this.vertices; i++) {
			System.out.println("(" + i + " -> " + parent[i] + "),  " + distances[i]);
		}
	}

	public void printGraph() {
		System.out.println("input graph is: ");
		for (int i=1; i<=this.vertices; i++) {
			System.out.println(gnodes[i].toString());
		}
	}
	
	@Override
	public String toString() {
		return "Graph [gnodes=" + Arrays.toString(gnodes) + "\nvertices="
				+ vertices + "]";
	}
}

class GraphNode {
	int label;
	List<GraphNode> neighbors;
	List<Integer> weights;
	
	public GraphNode(int label) {
		this.label = label;
		this.neighbors = new ArrayList<GraphNode>();
		weights = new ArrayList<Integer>();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("label: " + this.label + "; (neighbor, weight): ");
		if (this.neighbors != null) {
			int size = neighbors.size();
			for (int i=0; i<size; i++) {
				sb.append("(" + this.neighbors.get(i).label +", " + this.weights.get(i) + ")" + "; ");
			}
		}
		return sb.toString();
	}
}
