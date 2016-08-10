package com.manib.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphDriver {
	private Graph graph;
	
	public static void main(String[] args) {
		GraphDriver gd = new GraphDriver();
		gd.initGraph();
		
		int src = 1;
		gd.bfs(src);
	}
	
	public void initGraph() {
		this.graph = new Graph(6);
		this.graph.addEdge(1, 2);
		this.graph.addEdge(1, 3);
		this.graph.addEdge(2, 4);
		this.graph.addEdge(3, 4);
		this.graph.addEdge(3, 5);
		this.graph.addEdge(4, 5);
		this.graph.addEdge(4, 6);
		this.graph.addEdge(5, 6);
		this.graph.printGraph();
	}
	
	public void bfs(int src) {
		if (graph == null) {
			System.out.println("graph is empty, BFS not possible!");
			return;
		}
		graph.doBFS(src);
	}
}

class Graph {
	private int vertices;
	private GraphNode [] gnodes;
	
	public Graph(int vertices) {
		this.vertices = vertices;
		this.gnodes = new GraphNode[this.vertices+1];
		for (int i=1; i<=vertices; i++) {
			this.gnodes[i] = new GraphNode(i);
		}
	}
	
	public void addEdge(int u, int v) {
		//undirected graph, connect nodes u & v from either side.
		this.gnodes[u].neighbors.add(gnodes[v]);
		this.gnodes[v].neighbors.add(gnodes[u]);
	}

	
	public void doBFS(int src) {
		boolean [] visited = new boolean[this.vertices+1];
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		List<GraphNode> result  = new ArrayList<GraphNode>();
		//mark src visited and put it in queue
		queue.add(gnodes[src]);
		visited[src] = true;
		
		while (!queue.isEmpty()) {
			GraphNode u = queue.remove();
			result.add(u);
			List<GraphNode> vList = u.neighbors;
			int size = vList.size();
			for (int i=0; i<size; i++) {
				GraphNode v = vList.get(i);
				if (!visited[v.label]) {
					visited[v.label] = true;
					queue.add(v);
				}
			}
		}
		System.out.println("BFS traversal: ");
		for (GraphNode gn: result) {
			System.out.print(gn.label + " ");
		}
		System.out.println();
	}
	
	public void printGraph() {
		for (int i=1; i<=this.vertices; i++) {
			System.out.println(gnodes[i].toString());
		}
	}
}

class GraphNode {
	int label;
	List<GraphNode> neighbors;
	
	public GraphNode(int label) {
		this.label = label;
		this.neighbors = new ArrayList<GraphNode>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("label: " + this.label + "; neighbors: ");
		if (this.neighbors != null) {
			int size = neighbors.size();
			for (int i=0; i<size; i++) {
				sb.append(this.neighbors.get(i).label + "; ");
			}
		}
		return sb.toString();
	}
}
