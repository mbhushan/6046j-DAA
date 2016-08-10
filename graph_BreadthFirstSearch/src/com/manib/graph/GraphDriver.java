package com.manib.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GraphDriver {
	private Graph graph;
	
	public static void main(String[] args) {
		GraphDriver gd = new GraphDriver();
		gd.initGraph();
		
		int src = 1;
		gd.bfs(src);
		gd.shortestPath(src);
		
		gd.dfs(src);
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
	
	public void shortestPath(int src) {
		if (graph == null) {
			System.out.println("graph is empty, no shortest path possible!");
			return;
		}
		graph.shortestPath(src);
	}
	
	public void bfs(int src) {
		if (graph == null) {
			System.out.println("graph is empty, BFS not possible!");
			return;
		}
		graph.doBFS(src);
	}
	
	public void dfs(int src) {
		if (graph == null) {
			System.out.println("graph is empty, DFS not possible!");
			return;
		}
		graph.depthFirstSearch(src);
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
	
	//do DFS
	public void depthFirstSearch(int src) {
		boolean [] visited = new boolean[this.vertices + 1];
		List<Integer> result = new ArrayList<Integer>();
		dfsUtil(src, visited, result);
		
		Collections.reverse(result);
		System.out.println("dfs traversal: ");
		System.out.println(result.toString());
	}
	
	private void dfsUtil(int u, boolean [] visited, List<Integer> result) {
		visited[u] = true;
		List<GraphNode> vList = gnodes[u].neighbors;
		int size = vList.size();
		for (int i=0; i<size; i++) {
			GraphNode v = vList.get(i);
			if (!visited[v.label]) {
				dfsUtil(v.label, visited, result);
			}
		}
		result.add(u);
	}

	//computes shortest paths to all the vertices in the undirected graph. all edges have weight 1 here.
	public void shortestPath(int src) {
		boolean [] visited = new boolean[this.vertices+1];
		int [] distances = new int[this.vertices+1];
		Arrays.fill(distances, Integer.MAX_VALUE);
		
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		List<GraphNode> result  = new ArrayList<GraphNode>();
		//mark src visited and put it in queue
		queue.add(gnodes[src]);
		visited[src] = true;
		distances[src] = 0;
		
		while (!queue.isEmpty()) {
			GraphNode u = queue.remove();
			result.add(u);
			List<GraphNode> vList = u.neighbors;
			int size = vList.size();
			for (int i=0; i<size; i++) {
				GraphNode v = vList.get(i);
				if (!visited[v.label]) {
					visited[v.label] = true;
					distances[v.label] = distances[u.label] + 1;
					queue.add(v);
				}
			}
		}
		System.out.println("shortest distance from " + src + " to all vertices: ");
		for (int i=1; i<= this.vertices; i++) {
			System.out.println("src: " + src + "; dest: " + i +"; distance: " + distances[i]);
		}
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
