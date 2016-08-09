package com.manib.karger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MinCutKarger {

	public static void main(String[] args) {
		MinCutKarger mck = new MinCutKarger();
		
		int vertices = 6;
		Graph graph = new Graph(vertices);
//		graph.addEdge(1, 2);
//		graph.addEdge(1, 3);
//		graph.addEdge(2, 3);
//		graph.addEdge(2, 5);
//		graph.addEdge(3, 4);
//		graph.addEdge(4, 5);
//		graph.addEdge(4, 6);
		
		
		mck.readInput(graph);
		graph.printGraph();
	}
	
	public void readInput(Graph graph) {
		BufferedReader  br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader("data/test.txt"));
			while ((line = br.readLine()) != null) {
				String [] input = line.trim().split(" ");
				ArrayList<Integer> vList = new ArrayList<Integer>();
				int u = Integer.parseInt(input[0]);
				for (int i=1; i<input.length; i++) {
					int v = Integer.parseInt(input[i]);
					vList.add(v);
				}
				graph.populateAdjList(u, vList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Graph {
	private GraphNode [] gnodes;
	private int vertices;
	
	public Graph(int vertices) {
		this.vertices = vertices;
		gnodes = new GraphNode[this.vertices+1];
		for (int i=1; i<=this.vertices; i++) {
			gnodes[i] = new GraphNode(i);
		}
	}
	
	public void addEdge(int u, int v) {
		gnodes[u].neighbors.add(gnodes[v]);
		gnodes[v].neighbors.add(gnodes[u]);
	}
	
	public void populateAdjList(int u, ArrayList<Integer> vList) {
		for (int v: vList) {
			gnodes[u].neighbors.add(gnodes[v]);
		}
	}
	
	public int minCut() {
		
		
		
		return -1;
	}
	
	public void printGraph() {
		System.out.println("printing input graph: ");
		for (int i=1; i<=this.vertices; i++) {
			System.out.println(gnodes[i].toString());
		}
	}
}


class GraphNode {
	int label;
	List<GraphNode> neighbors;
	
	public GraphNode (int label) {
		this.label = label;
		neighbors = new ArrayList<GraphNode>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("label: " + this.label + "\n");
		if (this.neighbors != null) {
			sb.append("neighbors: " );
			int size = this.neighbors.size();
			for (int i=0; i<size; i++) {
				GraphNode gn = this.neighbors.get(i);
				if (gn != null) {
					sb.append(gn.label + "; ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
