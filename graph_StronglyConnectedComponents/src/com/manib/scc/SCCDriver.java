package com.manib.scc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class SCCDriver {

	private Graph graph = null;
	private int vertices = 0;

	public static void main(String[] args) {
		SCCDriver sd = new SCCDriver();
		
		sd.initGraph();
		
		//sd.readInput();
		sd.findStronglyConnectedComponents();
		
	}
	
	public void readInput() {
		this.vertices = 875714;
		this.graph = new Graph(this.vertices);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("data/scc.txt"));
			String input = null;
			while ((input =br.readLine()) != null) {
				String [] str = input.trim().split(" ");
				int u = Integer.parseInt(str[0].trim());
				int v = Integer.parseInt(str[1].trim());
				this.graph.edges().add(new Edge(u, v));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initGraph() {
		this.vertices = 9;
		this.graph = new Graph(this.vertices);
		this.graph.edges().add(new Edge(1, 4));
		this.graph.edges().add(new Edge(4, 7));
		this.graph.edges().add(new Edge(7, 1));
		this.graph.edges().add(new Edge(9, 7));
		this.graph.edges().add(new Edge(9, 3));
		this.graph.edges().add(new Edge(3, 6));
		this.graph.edges().add(new Edge(6, 9));
		this.graph.edges().add(new Edge(8, 6));
		this.graph.edges().add(new Edge(8, 5));
		this.graph.edges().add(new Edge(2, 8));
		this.graph.edges().add(new Edge(5, 2));
		
		int size = this.graph.edges().size();
		List<Edge> edges = this.graph.edges();
		for (int i=0; i<size; i++) {
			this.graph.addEdge(edges.get(i).u, edges.get(i).v);
		}
		
		this.graph.printGraph();
		
	}
	
	public void findStronglyConnectedComponents() {
		List<Edge> edges = this.graph.edges();
		this.graph = null;
//		for (Edge e: edges) {
//			System.out.println(e.toString());
//		}
		//reverse the edges and construct graph
		this.graph = new Graph(this.vertices); 
		int size= edges.size();
		System.out.println("edge size: " + size);
		for (int i=0; i<size; i++) {
			this.graph.addEdge(edges.get(i).v, edges.get(i).u);
		}
		
		Stack<Integer> finishTimeOrdering = this.graph.getOrdering();
		
		//revert to original graph;
		this.graph = null;
		this.graph = new Graph(this.vertices); 
		for (int i=0; i<size; i++) {
			this.graph.addEdge(edges.get(i).u, edges.get(i).v);
		}
		int sccCount = this.graph.findSCC(finishTimeOrdering);
		System.out.println("total SCC: " + sccCount);
		
	}
	
	
}

class Graph {
	 private int vertices;
	 private GraphNode [] gnodes;
	 private List<Edge> edges;
	
	public Graph(int vertices) {
		this.vertices = vertices;
		this.gnodes = new GraphNode[this.vertices+1];
		for (int i=1; i<=vertices; i++) {
			this.gnodes[i] = new GraphNode(i);
		}
		this.edges = new ArrayList<Edge>();
	}
	
	public List<Edge> edges() {
		return this.edges;
	}
	
	public void addEdge(int u, int v) {
		//directed graph, connect u to v 
		this.gnodes[u].neighbors.add(gnodes[v]);
	}
	
	public int findSCC(Stack<Integer> finishTimeOrder) {
		boolean [] visited = new boolean[this.vertices+1];
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(5);
		
		int sccCount = 0;
		while (!finishTimeOrder.isEmpty()) {
			int u = finishTimeOrder.pop();
			Stack<Integer> path = new Stack<Integer>();
			if (!visited[u]) {
				dfs(u, visited, path);
				System.out.println("SCC: " + path.toString());
				if (minHeap.size() <= 5) {
					minHeap.add(path.size());
				} else {
					int val = path.size();
					if (val > minHeap.peek()) {
						minHeap.remove();
						minHeap.add(val);
					}
				}
				++sccCount;
			}
		}
		System.out.println("top 5 scc sizes: " + minHeap.toString());
		return sccCount;
	}
	
	public Stack<Integer> getOrdering() {
		Stack<Integer> stack = new Stack<Integer>();
		boolean [] visited = new boolean[this.vertices+1];
		for (int i=1; i<=this.vertices; i++) {
			if (visited[i]) continue;
			dfs(i, visited, stack);
		}
		System.out.println("ordering got!");
//		System.out.println("ordering is: ");
//		System.out.println("top: " + stack.peek());
//		System.out.println(stack.toString());
		return stack;
	}
	
	public void dfs(int u, boolean [] visited, Stack<Integer> path) {
		visited[u] = true;
		
		List<GraphNode> vList = this.gnodes[u].neighbors;
		int size = vList.size();
		for (int i=0; i<size; i++) {
			GraphNode v = vList.get(i);
			if (!visited[v.label]) {
				dfs(v.label, visited, path);
			}
		}
		path.push(u);
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

class Edge {
	int u;
	int v;
	
	public Edge(int u, int v) {
		this.u = u;
		this.v = v;
	}
	
	public String toString() {
		return "u: " + this.u + ", v: " + this.v;
	}
}
