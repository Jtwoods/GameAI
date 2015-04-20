package path_following;

import java.util.ArrayList;

import path_display.Vertex_Dot;

/**
 * Vertex represents a vertex in a directed graph.
 * 
 * @author James Woods
 */
public class Vertex extends Position {

	/**
	 * csf is the cost so far in moving from a start vertex to this vertex.
	 */
	private float csf;

	/**
	 * etc is the estimate of total cost to move to the vertex.
	 */
	private float etc;

	/**
	 * edges are edges that lead away from the current vertex.
	 */
	private ArrayList<Edge> edges;

	/**
	 * dot is the visual representation of the Vertex.
	 */
	private Vertex_Dot dot;
	

	/**
	 * Vertex is the default constructor for the vertex class and constructs a
	 * vertex at the given coordinates.
	 */
	public Vertex(int z, int x) {
		super(z, x);
		this.csf = 0.0f;
		this.etc = 0.0f;
		this.edges = new ArrayList<Edge>();
		this.dot = new Vertex_Dot(z, x);
	}

	/**
	 * getDraw returns the Vertex_Dot for this Vertex.
	 * 
	 * @return the dot.
	 */
	public Vertex_Dot getDraw() {
		return dot;
	}

	/**
	 * addEdge adds an edge that leads away from the Vertex.
	 * 
	 * @param toSet
	 *            the edge to be added.
	 */
	public void addEdge(Edge toSet) {
		toSet.setStart(this);
		edges.add(toSet);
	}
	
	public float getShortestEdgeWeight() {
		float toReturn = 0;
		for(Edge e: edges) {
			if(toReturn == 0)
				toReturn = e.getWeight();
			else if(e.getWeight() < toReturn)
				toReturn = e.getWeight();
		}
		return toReturn;
	}

	/**
	 * hashCode uses the hash function defined in the super class to produce a
	 * hash key.
	 */
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * equals uses the equals method of the super class to perform equals.
	 */
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * getEdges returns the list of edges leading away form the vertex.
	 * 
	 * @return an ArrayList of edges leading away from the vertex.
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	/**
	 * removes the given edge.
	 * 
	 * @param edge
	 *            to be removed.
	 */
	public void removeEdge(Edge edge) {
		edges.remove(edge);
	}

	public float getCsf() {
		return csf;
	}

	public void setCsf(float csf) {
		this.csf = csf;
	}

	public float getEtc() {
		return etc;
	}

	public void setEtc(float etc) {
		this.etc = etc;
	}

	public Position getPosition() {
		return new Position(super.getZ(), super.getX());
	}

	public void setColor(int f) {
		dot.setColor(f);
	}
}
