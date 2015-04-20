package path_finding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import path_following.Edge;
import path_following.Graph;
import path_following.Path;
import path_following.Vertex;

public abstract class Search {

	/**
	 * closedList is used to store the vertices with known shortest paths.
	 */
	protected HashMap<Vertex, Pair> closedList;
	/**
	 * openList is used to store the working set of vertices that need to be
	 * searched.
	 */
	protected PriorityQueue<Pair> openList;
	/**
	 * vertComp allows comparison of vertices by their CSF value.
	 */
	protected Comparator<Pair> vertComp;
	/**
	 * hasComp tracks whether the comparator for this Dijkstra's has been set.
	 */
	protected boolean hasComp;
	/**
	 * count is the number of vertices that are searched.
	 */
	protected int count;
	/**
	 * maxOpen is the maximum number of items in the open list.
	 */
	protected int maxOpen;
	/**
	 * maxClosed is the maximum number of items in the closed list.
	 */
	protected int maxClosed;
	
//	/**
//	 * start_time tracks the start time of the search.
//	 */
//	private long start_time;
	
	/**
	 * path_length records the path length.
	 */
	private long path_length;

	public Search() {
		super();
	}

	/**
	 * makePath makes a path given the start vertex the end vertex and 
	 * the end pair.
	 * @param start the starting vertex for the path.
	 * @param end the end vertex for the path.
	 * @param endPair the last pair of vertex and edge in the path.
	 * @return Path the path from start to end.
	 */
	protected Path makePath(Vertex start, Vertex end, Pair endPair) {
		Path toReturn = new Path();
		ArrayList<Vertex> reversePath = new ArrayList<Vertex>();
	
		// Generate the path and return it.
		reversePath.add(end);
		boolean notDone = true;
		end.setColor(3);
		
		while (notDone) {
			Edge joint = endPair.getEdge();
			path_length += joint.getWeight();
			Vertex cameFrom = joint.getStart();
			cameFrom.setColor(3);
			if (cameFrom.equals(start))
				notDone = false;
			else
				endPair = closedList.get(cameFrom);
			reversePath.add(cameFrom);
		}
		// Re-order the path
		for (int i = reversePath.size() - 1; i >= 0; i--) {
			toReturn.add(reversePath.get(i));
		}
		return toReturn;
	}

	/**
	 * initialize sets up the Dijkstra's 
	 * for operation.
	 * @param start the start position for the search.
	 */
	protected void initialize(Vertex start) {
		
//		start_time = System.currentTimeMillis();
		path_length = 0;
		count = 0;
		maxOpen = 0;
		maxClosed = 0;
		closedList = new HashMap<Vertex, Pair>();
		if(!hasComp)
			vertComp = new CompareVertices();
		openList = new PriorityQueue<Pair>(10, vertComp);
		openList.add(new Pair(start, new Edge()));
	}

	/**
	 * setComparator sets the comparator. This is used for testing.
	 * @param comp the Comparator to be used for comparisons.
	 */
	public void setComparator(Comparator<Pair> comp) {
			hasComp = true;
			vertComp = comp;
	}

	/**
	 * printCount is used for testing.
	 */
	public void printCount() {
//		double end_time = System.currentTimeMillis() - start_time;
//		end_time /= 1e6;
//		
//		System.out.println("Time to search: " + end_time + " seconds");
		System.out.println("Number of expanded nodes: " + count);
	
		System.out.println("Max number of open nodes: " + maxOpen);
	
		System.out.println("Max number of closed nodes: " + maxClosed);
		System.out.println("Path length: " + path_length);
		if(vertComp instanceof Search_Comparison)
			System.out.println("Number of comparisons made: " + ((Search_Comparison)vertComp).getComparisonCount());
	}

	public abstract Path findPath(Vertex start, Vertex end, Graph graph);

}