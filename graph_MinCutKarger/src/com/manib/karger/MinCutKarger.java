package com.manib.karger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinCutKarger {

	public static void main(String[] args) {
		MinCutKarger mck = new MinCutKarger();
		
		int vertices = 200;
		
		
		int mincut = 0;
		int range = 200*200;
		for (int i = 0; i < 400; i++) {
			Graph graph = new Graph(vertices);
			mincut = graph.vertices() - 1;
			mck.readInput(graph);
			// graph.printGraph();
			int cuts = mck.minCut(graph);
			mincut = Math.min(cuts, mincut);
		}
		System.out.println("graph cut: " + mincut);
	}
	
	public int minCut(Graph graph) {
		int mincut = graph.vertices()-1;
		for (int i=0; i<1; i++) {
			//Graph g = new Graph(graph);
			mincut = Math.min(mincut, minCutUtil(graph));
		}
		return mincut;
	}
	
	private int minCutUtil(Graph graph) {
		int [] pool = new int[graph.vertices()];
		for (int i=0; i<pool.length; i++) {
			pool[i] = i+1;
		}
		int count = graph.vertices();
		Random rand = new Random();
		while (count > 2) {
			int i = rand.nextInt(count);
			int tmp = pool[i];
			pool[i] = pool[count-1];
			pool[count-1] = tmp;
			int v = pool[count-1];
			int size = graph.getNodes()[v].neighbors.size();
			//System.out.println("hot seat v: " + v);
			//System.out.println(" hot seat v neighbors: " + graph.getNodes()[v].neighbors.toString());
			int r = rand.nextInt(size);
			int u = graph.getNodes()[v].neighbors.get(r);
			//System.out.println("merging: " + u +" + " + v);
			mergeVertices(graph, u, v);
			--count;
		}
		int u = pool[0];
//		int v = pool[1];
//		System.out.println("u;v => " + u +";" + v);
//		System.out.println("u neighbors =>" + graph.getNodes()[u].toString());
//		System.out.println("v neighbors => " + graph.getNodes()[v].toString());
		int cut = graph.getNodes()[u].neighbors.size();
		//int cut1 = graph.getNodes()[v].neighbors.size();
		return cut;
	}
	
	private void mergeVertices(Graph graph, int u, int v) {
		ArrayList<Integer> neighbors = (ArrayList<Integer>)graph.getNodes()[v].neighbors;
		
		int size = neighbors.size();
		for (int i=0; i<size; i++) {
			int w = neighbors.get(i);
			ArrayList<Integer> wList = (ArrayList<Integer>)graph.getNodes()[w].neighbors;
			int wsize = wList.size();
			for (int j=0; j<wsize; j++) {
				if (wList.get(j).intValue() == v) {
					wList.set(j, u);
					if (u != w) {
						graph.getNodes()[u].neighbors.add(w);
					}
				}
			}
			//update w's neighbors
			graph.getNodes()[w].neighbors = wList;
			//remove self loops
			graph.getNodes()[u].neighbors.removeAll(Collections.singleton(u));
		}
	}
	
	
	public void readInput(Graph graph) {
		BufferedReader  br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader("data/mincut_input.txt"));
			while ((line = br.readLine()) != null) {
				String [] input = line.trim().split("\t");
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
	
	public Graph(Graph other) {
		this.gnodes = other.gnodes;
		this.vertices = other.vertices;
	}
	
	public GraphNode [] getNodes() {
		return this.gnodes;
	}
	
	public void populateAdjList(int u, ArrayList<Integer> vList) {
		gnodes[u].neighbors.addAll(vList);
	}
	
	public void printGraph() {
		System.out.println("printing input graph: ");
		for (int i=1; i<=this.vertices; i++) {
			System.out.println(gnodes[i].toString());
		}
	}
	
	public int vertices() {
		return this.vertices;
	}
}


class GraphNode {
	int label;
	List<Integer> neighbors;
	
	public GraphNode (int label) {
		this.label = label;
		neighbors = new ArrayList<Integer>();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("label: " + this.label + "\n");
		if (this.neighbors != null) {
			sb.append("neighbors: " );
			int size = this.neighbors.size();
			for (int i=0; i<size; i++) {
				Integer gn = this.neighbors.get(i);
				if (gn != null) {
					sb.append(gn + "; ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
