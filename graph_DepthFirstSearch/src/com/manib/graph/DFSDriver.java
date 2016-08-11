package com.manib.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFSDriver {
	
	private Graph graph = null;
	
	public static void main(String[] args) {
		DFSDriver dd = new DFSDriver();
		
		dd.initGraph();
		dd.topologicalSort();
	}
	
	public void initGraph() {
		this.graph = new Graph(5);
		this.graph.addEdge(1, 2);
		this.graph.addEdge(1, 3);
		this.graph.addEdge(1, 4);
		this.graph.addEdge(1, 5);
		//this.graph.addEdge(2, 3);
		this.graph.addEdge(2, 4);
		this.graph.addEdge(3, 4);
		this.graph.addEdge(3, 5);
		this.graph.addEdge(4, 5);
		this.graph.printGraph();
	}
	
	public void topologicalSort() {
		if (graph == null) {
			System.out.println("graph is null, no topological sorting possible!");
			return;
		}
		graph.topologicalSort();
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
	
	public void topologicalSort() {
		Stack<Integer> stack = new Stack<Integer>();
		boolean [] visited = new boolean[this.vertices+1];
		int [] finishTimes = new int[this.vertices+1];
		for (int i=1; i<=this.vertices; i++) {
			if (visited[i]) continue;
			dfs(i, visited, finishTimes, stack);
		}
		
		System.out.println("topological ordering: ");
		while(!stack.isEmpty()) {
			System.out.print(stack.pop() + " -> ");
		}
		System.out.println("done!");
	}
	
	public void dfs(int u, boolean [] visited, int [] finishTimes, Stack<Integer> stack) {
		visited[u] = true;
		List<GraphNode> vList = gnodes[u].neighbors;
		int size = vList.size();
		
		for (int i=0; i<size; i++) {
			GraphNode v = vList.get(i);
			if (!visited[v.label]) {
				dfs(v.label, visited, finishTimes, stack);
			}
		}
		stack.push(u);
				
	}
	
	public void addEdge(int u, int v) {
		//directed graph, connect u to v 
		this.gnodes[u].neighbors.add(gnodes[v]);
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