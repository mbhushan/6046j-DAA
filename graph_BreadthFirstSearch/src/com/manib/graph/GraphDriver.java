package com.manib.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphDriver {
	private Graph graph;
	
	public static void main(String[] args) {
		GraphDriver gd = new GraphDriver();
		gd.initGraph();
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
	
	public void doBFS() {
		
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
