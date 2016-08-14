package graph_DijkstraUndirected;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraUG {
	private Graph graph = null;
	
	public static void main(String[] args) {
		int vertices = 200;
		DijkstraUG dssp = new DijkstraUG();
		
		//dssp.init(vertices);
		dssp.readInput(vertices);
		
		int src = 1;
		dssp.dijkstra(src);
	}
	
	public void readInput(int vertices) {
		this.graph = new Graph(vertices);
		
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader("data/dijkstra_data.txt"));
			String input = null;
			while ((input = br.readLine()) != null) {
				String [] strs = input.trim().split("\t");
				int u = Integer.parseInt(strs[0].trim());
				for (int i=1; i < strs.length; i++) {
					String [] pair = strs[i].split(",");
					int v = Integer.parseInt(pair[0].trim());
					int w = Integer.parseInt(pair[1].trim());
					this.graph.addEdge(u, v, w);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
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
		//this.gnodes[v].neighbors.add(gnodes[u]);
		this.gnodes[u].weights.add(w);
		//this.gnodes[v].weights.add(w);
	}
	
	public void dijkstraSSP(int src) {
		int [] parent = new int[this.vertices+1];
		boolean [] visited = new boolean[this.vertices+1];
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
			visited[u] = true;
			List<GraphNode> vlist = gnodes[u].neighbors;
			List<Integer> wlist = gnodes[u].weights;
			int size = vlist.size();
			for (int i=0; i<size; i++) {
				GraphNode v = vlist.get(i);
				if (visited[v.label]) {
					continue;
				}
				int w = wlist.get(i);
				if (distances[v.label].dist > uDist + w) {
					distances[v.label].dist = uDist + w;
					minHeap.add(distances[v.label]);
					parent[v.label] = u;
				}
			}
		}
		
//		System.out.println("(vertex -> parent, shortest distance from src)");
//
//		for (int i=1; i<=this.vertices; i++) {
//			System.out.println("(" + i + " -> " + parent[i] + "),  " + distances[i]);
//		}
		int [] result = {7,37,59,82,99,115,133,165,188,197};
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<result.length; i++) {
			sb.append(distances[result[i]].dist+",");
		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println("required result: ");
		System.out.println(sb.toString());
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

