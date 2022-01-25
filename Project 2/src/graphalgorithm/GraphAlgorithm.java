package graphalgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
public class GraphAlgorithm {
	public static void main(String args[]) throws FileNotFoundException {
		// Creating a File object that represents the disk file. 
        PrintStream o = new PrintStream(new File("upto50.txt")); 
        // Store current System.out before assigning a new value 
        PrintStream console = System.out;
		Scanner scan = new Scanner(System.in);
		Readfile readfile = new Readfile();
		ArrayList<ArrayList<Integer>> edges = readfile.ReadFileIntoGraph();
		int[] hospitals = readfile.ReadTxtfile();
	
		int choice=0;
		
		boolean done = false;
		int start = 0;

		
		do {	
			try {
				System.setOut(console);
				System.out.println("\n(1) Print all Nodes \n(2) Nearest 2 Hospital \n(3) Nearest k Hospital \n(4) Exit");
				System.out.println("Enter the number of your choice: ");
				switch(choice= scan.nextInt()) {
		        case 1: 
		        	System.out.println("Results in output file!");
		        	System.setOut(o);
		    		for (int i=0; i<10;i++) {
		    			searchForHosp(edges, i, hospitals, 1);		
		    		}
		    		System.out.println("================================End of Output=======================================");
		    		break;
		        case 2: 
		    		while (!done) {
		    			System.out.println("Starting Node ID: ");
		    			start = scan.nextInt();
		    			if (!edges.get(start).isEmpty()) done = true;
		    		}
		          System.out.println("Results in output file!");
		          System.setOut(o);
		          System.out.println("Nearest 2 hospitals to Node "+ start +" are:");
		          searchForHosp(edges, start, hospitals, 2);  
		          System.out.println("================================End of Output=======================================");
		          break;
		      
		        case 3:
		    		while (!done) {
		    			System.out.println("Starting Node ID: ");
		    			start = scan.nextInt();
		    			if (!edges.get(start).isEmpty()) done = true;
		    		}
			      System.out.println("Value of k (top-k nearest hospitals): ");
			      int k = scan.nextInt();
		          System.out.println("Results in output file!");
		          System.setOut(o); 
		          System.out.println("Nearest "+ k + " hospitals to Node "+ start +" are:");
		          searchForHosp(edges, start, hospitals, k);
		          System.out.println("================================End of Output=======================================");
		          break;
		        case 4:
		     		System.out.println("Program terminating..");
		     		System.exit(0);
		     		break;
				default:
					System.out.println("Invalid input");
		     		System.out.println("Program terminating..");
		     		System.exit(0);
		    } 
				}  catch(Exception e) {System.out.println("Invalid input");
					System.out.println("Program terminating..");
					break;
		    };
		}while (choice > 0 && choice != 4);

			
	}
	
	public static void searchForHosp(ArrayList<ArrayList<Integer>> edges, int start, int[] hospitals, int k) throws FileNotFoundException {
		System.out.println("For Node " + (start) + ",");
		int[] predecessor = new int[edges.size()]; 
        int[] dist = new int[edges.size()]; 
  
        int[] destination_hospitals = BFS(edges, start, hospitals, k, predecessor, dist);
        if (destination_hospitals[k] == 0) { 
            System.out.println("Source does not connect to any hospital.");
        } 
        
        else {
        	int curr;
        	// find route for every hospital that start managed to reach in range of k
        	for (int i = 0; i < destination_hospitals[k]; i++) {
        		System.out.println("Hospital Node ID: " + destination_hospitals[i]);
        		// forming route
        		LinkedList<Integer> route = new LinkedList<Integer>();
        		curr = destination_hospitals[i];
        		route.add(curr);
        		while (predecessor[curr] != -1) {
        			route.add(predecessor[curr]);
        			curr = predecessor[curr];
        		}
        		
        		// printing out distance
        		System.out.println("Shortest distance is: " + dist[destination_hospitals[i]]); 
        		
        		// printing out route
        		System.out.println("Route is:"); 
                for (int j = route.size()-1; j >= 0; j--) { 
                    System.out.print(route.get(j) + " "); 
                }
                System.out.println("");
        	}
        }
        System.out.println("");
	}
	
	private static int[] BFS(ArrayList<ArrayList<Integer>> edges, int start, int[] hospitals, int k, int[] predecessor, int[] dist) {
		int[] result = new int[k+1];
		result[k] = 0; // last element is a counter of no. of destination hospitals, stop when reach k
		LinkedList<Integer> queue = new LinkedList<Integer>(); 
		boolean[] visited = new boolean[edges.size()]; 
		// initialise
		for (int i = 0; i < edges.size(); i++) { 
            visited[i] = false; 
            dist[i] = Integer.MAX_VALUE; //infinity
            predecessor[i] = -1;
        } 
		
		// for starting point
		visited[start] = true; 
        dist[start] = 0; 
        queue.add(start); 
        
        while (!queue.isEmpty()) { 
            int curr = queue.remove(); 
            for (int j = 0; j < edges.get(curr).size(); j++) { 
                if (visited[edges.get(curr).get(j)] == false) { 
                    visited[edges.get(curr).get(j)] = true; 
                    dist[edges.get(curr).get(j)] = dist[curr] + 1; // curr's dist + 1 = curr's "child"'s dist
                    predecessor[edges.get(curr).get(j)] = curr;
                    queue.add(edges.get(curr).get(j)); // add curr's "child" to queue
  
                    // checking if curr's "child" is destination (hospital)
                    for (int h = 0; h < hospitals.length; h++)
                    	if (edges.get(curr).get(j) == hospitals[h]) {
                    		result[result[k]] = hospitals[h];
                    		result[k] += 1;
                    		if (result[k] == k) return result; // destination has k number of hospitals, stop
                    	}
                } 
            } 
        } 
        return result; 
	}
}
