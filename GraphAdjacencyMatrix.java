package cs583;
/*
 * @author: Achudan TS
 * DFT of graph with edges
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class GraphAdjacencyMatrix {

	public class Vertex{
		int data;
		String color;
		Vertex parent;
		int d, f;
		Vertex(int val){
			data=val;
			color="white";
			parent = null;
			d =0;
			f=0;
		}
	}

	private int[][] matrix;
	private int verticesSize, time = 0;

	public static HashSet<ArrayList<Vertex>> treeEdge = new HashSet<ArrayList<Vertex>>(),forwardEdge = new HashSet<ArrayList<Vertex>>(),backEdge = new HashSet<ArrayList<Vertex>>(),crossEdge = new HashSet<ArrayList<Vertex>>();

	GraphAdjacencyMatrix(int size){
		verticesSize = size;
		matrix = new int[size][size];
	}

	public void insert(int from, int to) {
		matrix[from][to] = 1;
	}

	public ArrayList<Vertex> createPairs(Vertex u, Vertex v){
		ArrayList<Vertex> temp = new ArrayList<>();
		temp.add(u);
		temp.add(v);
		return temp;
	}

	private void depthVisit(Vertex[] vertexArray, int val) {
		Vertex u = vertexArray[val];
		time++;
		u.d = time;
		u.color = "gray";
		IntStream.range(0,verticesSize).forEach(
				index->{
					if(matrix[val][index] == 1) {
						Vertex v = vertexArray[index];
						if(v.color.equals("white")) {
							v.parent = u;
							treeEdge.add(createPairs(u,v));
							depthVisit(vertexArray, index);
						}
						else {
							if(v.color.equals("gray")) {
								backEdge.add(createPairs(u,v));
							}
							if(v.color.equals("black")) {
								if(u.d < v.d) {
									forwardEdge.add(createPairs(u,v));
								}
								else if(u.d > v.d) {
									crossEdge.add(createPairs(u,v));
								}
							}

						}
					}
				});
		u.color = "black";
		time++;
		u.f = time;


	}

	private void depthFirstTraverse(int val) {

		Vertex[] vertexArray = new Vertex[verticesSize];

		IntStream.range(0, verticesSize).forEach(index->{
			vertexArray[index] = new Vertex(index);});
		IntStream.range(0, verticesSize).forEach(u->{
			if(vertexArray[u].color.equals("white"))
				depthVisit(vertexArray, u);
		});
	}

	public static void main(String[] args) {
		GraphAdjacencyMatrix graph = new GraphAdjacencyMatrix(6);

		graph.insert(0, 1);
		graph.insert(0, 3);
		graph.insert(1, 2);
		graph.insert(2, 3);
		graph.insert(3, 1);
		graph.insert(4, 2);
		graph.insert(4, 5);
		graph.insert(5, 5);

		graph.depthFirstTraverse(0);

		System.out.println("Tree Edge");
		treeEdge.iterator().forEachRemaining(i->{
			System.out.println(i.get(0).data +"--->"+ i.get(1).data);
		});

		System.out.println("Back Edge");
		backEdge.iterator().forEachRemaining(i->{
			System.out.println(i.get(0).data +"--->"+ i.get(1).data);
		});

		System.out.println("Forward Edge");
		forwardEdge.iterator().forEachRemaining(i->{
			System.out.println(i.get(0).data +"--->"+ i.get(1).data);
		});

		System.out.println("Cross Edge");
		crossEdge.iterator().forEachRemaining(i->{
			System.out.println(i.get(0).data +"--->"+ i.get(1).data);
		});

	}



}
