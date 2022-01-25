package graphalgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Readfile {
	
	public Readfile() {}
	
	public ArrayList<ArrayList<Integer>> ReadFileIntoGraph() throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		boolean filenamedone = false;
		File nodeFile = null;
		while (!filenamedone) {
			filenamedone = true;
			System.out.print ("\n Node File Name: "); //roadNet-CA.txt
			try {
				nodeFile = new File(scan.nextLine());
				if (!nodeFile.exists()) {
					throw new FileNotFoundException("File not found. Please try again.");
				}
			}
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				filenamedone = false;
			}
		}
		
		System.out.println("Please wait while we process the Node File. This might take awhile for large files.");
		
		Scanner tempsc, sc;
		int fromNode, toNode;
		int maxNodeID = 0;
		// run a loop through whole file once to determine highest NodeID (so that we do not waste space)
		tempsc = new Scanner(nodeFile);
		// process nodefile
		// remove first 4 lines, not needed
		for (int i = 0; i < 4; i ++) {
			tempsc.nextLine();
		}

		while (tempsc.hasNextLine()) {
			fromNode = tempsc.nextInt();
			tempsc.nextLine();
			//System.out.println("fromNode: " + fromNode);
			if (fromNode > maxNodeID) maxNodeID = fromNode;
		}
		tempsc.close();
		
		Graph result = new Graph(maxNodeID+1);
		sc = new Scanner(nodeFile);
		// process nodefile
		// remove first 4 lines, not needed
		for (int i = 0; i < 4; i ++) {
			sc.nextLine();
		}
		while (sc.hasNextLine()) {
			fromNode = sc.nextInt();
			toNode = sc.nextInt();
			result.addEdge(fromNode, toNode);
			sc.nextLine();
		}
				
		sc.close();
		
		return result.getEdges();
	}
	
	public int[] ReadTxtfile() throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		boolean filenamedone = false;
		File txtFile = null;
		while (!filenamedone) {
			filenamedone = true;
			System.out.print ("\n Text (Hospital) File Name: "); //txtfile.txt
			try {
				txtFile = new File(scan.nextLine());
				if (!txtFile.exists()) {
					throw new FileNotFoundException("Text File not found. Please try again.");
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				filenamedone = false;
			}
		}
		
		Scanner sc = new Scanner(txtFile);
		// remove #
		sc.next();
		int noOfHosp = sc.nextInt();
		int[] result = new int[noOfHosp];
		for (int i = 0; i < noOfHosp; i++) {
			sc.nextLine();
			result[i] = sc.nextInt();
		}
		return result;
	}

}
