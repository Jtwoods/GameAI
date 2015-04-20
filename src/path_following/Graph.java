package path_following;

import graph_factory.CircleInCircleException;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
	
	private HashMap<Vertex, Vertex> vertices; 
	
	/**
	 * Graph is a set of edges and vertices
	 */
	public Graph() {
		vertices = new HashMap<Vertex, Vertex>();
	}
	
	/**
	 * getEdges returns an array of Edges that lead away from
	 * the given vertex.
	 * @param vert the vertex to check.
	 * @return the Edges leading away from the vertex.
	 */
	public ArrayList<Edge> getEdges(Vertex vert) {
		Vertex toGet = vertices.get(vert);
		if(toGet == null)
			return null;

		ArrayList<Edge> edges = toGet.getEdges();

		return edges;
	}

	/**
	 * addVertex adds a vertex to the Graph.
	 * Note: Each Vertex added to the graph must have 
	 * a unique location. No two Vertices in the graph
	 * can have the same position.
	 * @param first
	 */
	public void addVertex(Vertex first) {
		vertices.put(first, first);
	}
	
	/**
	 * getVertices returns an ArrayList containing all the vertices in 
	 * the graph.
	 * @return ArrayList of all vertices in the graph.
	 */
	public ArrayList<Vertex> getVertices() {
		return new ArrayList<Vertex>(vertices.values());
	}

	/**
	 * removes the given Vertex from the graph.
	 * @param vert the vertex to remove.
	 */
	@SuppressWarnings("unchecked")
	public void removeVertex(Vertex vert) {
		ArrayList<Edge> edges = vert.getEdges();
		edges = (ArrayList<Edge>) edges.clone();
		for(int j = 0; j < edges.size(); j++) {
			Vertex end = edges.get(j).getEnd();
			ArrayList<Edge> endEdges = end.getEdges();
			endEdges = (ArrayList<Edge>) endEdges.clone();
			for(int i = 0; i < endEdges.size(); i++) {
				if(endEdges.get(i).getEnd().equals(vert)) {
					end.removeEdge(endEdges.get(i));
				}
			}
			vert.removeEdge(edges.get(j));
		}
		
		vertices.remove(vert);
	}

	/**
	 * getVertexAt returns the Vertex at the given z,x position.
	 * @param z the z position.
	 * @param x the x position.
	 * @return the Vertex at the position.
	 */
	public Vertex getVertexAt(int z, int x) {
		return vertices.get(new Vertex(z,x));
	}

	/**
	 * returns the number of vertices in the graph.
	 * @return the number of vertices
	 */
	public int size() {
		return vertices.size();
	}

	/**
	 * returns the closest vertex near the point inside the given distance area or returns null.
	 * @param z the z position
	 * @param x the x position
	 * @param distance the distance around the position to search.
	 * @return the closest Vertex or null if no vertex is found.
	 */
	public Vertex getVertexNear(int z, int x, int distance) {
		Vertex toReturn = null;
		ArrayList<Vertex> verts = this.getVertices();
		float lowest = Float.MAX_VALUE;
		for (Vertex v : verts) {
			int currentX = v.getX();
			int currentZ = v.getZ();
			float dist = distance(z,x,currentZ, currentX);
			if (dist < distance && dist < lowest) {
				lowest = dist;
				toReturn = v;
			}
		}
		return toReturn;
	}

	/**
	 * returns the distance between two points.
	 * 
	 * @param z1
	 *            z position of point 1
	 * @param x1
	 *            x position of point 1
	 * @param z2
	 *            z position of point 2
	 * @param x2
	 *            x position of point 2
	 * @return the distance as float
	 * @throws CircleInCircleException
	 */
	private float distance(int z1, int x1, int z2, int x2) {
		float z = (z1 - z2);
		float x = (x1 - x2);

		if (x == 0 && z == 0)
			return 0;

		z *= z;
		x *= x;

		return (float) Math.sqrt(z + x);
	}
	
}
