package graphalgorithm;

import java.util.ArrayList;

public class Graph {
	private ArrayList<ArrayList<Integer>> edges;
	
	public Graph(int numOfNodes) {
		this.edges = new ArrayList<ArrayList<Integer>>(numOfNodes);
		for (int i = 0; i < numOfNodes; i++) {
			this.edges.add(new ArrayList<Integer>());
		}
	}
	
	public void addEdge(int fromNode, int toNode) {
		this.edges.get(fromNode).add(toNode);
	}
	
	public ArrayList<ArrayList<Integer>> getEdges() {
		return this.edges;
	}
}
