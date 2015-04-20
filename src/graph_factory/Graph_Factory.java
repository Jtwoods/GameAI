package graph_factory;

import java.util.ArrayList;

import path_following.Edge;
import path_following.Graph;
import path_following.Vertex;
import structure.Drawable;

public abstract class Graph_Factory {

	private int offset;

	/**
	 * view is a viewable list of Drawable objects for the graph.
	 */
	protected ArrayList<Drawable> view;

	/**
	 * Grid parameters are set to zero initially and are initialized again when
	 * a grid is made.
	 */
	int startZ;
	int startX;
	int endZ;
	int endX;
	int zLength;
	int xLength;

	/**
	 * graph is the Graph to be built.
	 */
	protected Graph graph;

	public Graph_Factory() {
		graph = new Graph();
		view = new ArrayList<Drawable>();
		offset = 1;
	}

	/**
	 * returns the Graph.
	 * 
	 * @return the Graph.
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * returns the drawables.
	 * 
	 * @return the list of Drawable objects.
	 */
	public ArrayList<Drawable> getView() {
		buildGraph();
		return view;
	}

	/**
	 * helper method that builds the Graph when ready.
	 */
	protected void buildGraph() {
//		view = new ArrayList<Drawable>();
		ArrayList<Vertex> vert = graph.getVertices();
		for (Vertex v : vert) {
			view.add(v.getDraw());
			ArrayList<Edge> edge = v.getEdges();
			for (Edge e : edge) {
				view.add(e.getDraw());
			}
		}
	}

	/**
	 * helper method that doubly connects two edges and adds them to the Graph.
	 * 
	 * @param start
	 *            the start edge.
	 * @param end
	 *            the end edge.
	 */
	protected void connectDouble(Vertex start, Vertex end) {
		if (!start.equals(end)) {
			Edge to = new Edge(start, end);
			Edge from = new Edge(end, start);
			weighEdge(to);
			weighEdge(from);
			graph.addVertex(start);
			graph.addVertex(end);
		}
	}

	/**
	 * helper method that connects two edges and adds them to the Graph.
	 * 
	 * @param start
	 *            the start edge.
	 * @param end
	 *            the end edge.
	 */
	protected void connect(Vertex start, Vertex end) {
		Edge to = new Edge(start, end);
		weighEdge(to);
		graph.addVertex(start);
		graph.addVertex(end);
	}

	/**
	 * createHole removes a central square and increases the density of the
	 * surrounding graph.
	 * 
	 * @param minZ
	 *            the min z location.
	 * @param maxZ
	 *            the max z location.
	 * @param minX
	 *            the min x location.
	 * @param maxX
	 *            the max x location.
	 */
	protected void createHole(int minZ, int minX, int maxZ, int maxX) {
		removeVerts(minZ, maxZ, minX, maxX);
		//deepenArea(minZ, minX, maxZ, maxX);
	}

	/**
	 * deepenArea increases the density of the surrounding graph.
	 * 
	 * @param minZ
	 *            the min z location.
	 * @param maxZ
	 *            the max z location.
	 * @param minX
	 *            the min x location.
	 * @param maxX
	 *            the max x location.
	 */
	protected void deepenArea(int minZ, int minX, int maxZ, int maxX) {

		int firstZ = (int) Math.floor((minZ - this.startZ) / this.zLength);
		int firstX = (int) Math.floor((minX - this.startX) / this.xLength);
		int lastZ = (int) Math.ceil((maxZ - this.startZ) / this.zLength)
				+ offset;
		int lastX = (int) Math.ceil((maxX - this.startX) / this.xLength)
				+ offset;

		for (int z = firstZ - offset; z <= lastZ; z++) {
			if (firstX - offset >= 0) {
				deepen(z, firstX - offset);

			}
			if (z <= lastZ && z >= 0 && maxX < this.endX) {
				deepen(z, lastX);
			}
		}

		for (int x = firstX - offset; x <= lastX; x++) {
			if (firstZ - offset >= 0) {
				deepen(firstZ - offset, x);
			}
			if (x <= lastX && x >= 0 && maxZ < this.endZ) {
				deepen(lastZ, x);
			}
		}
	}

	/**
	 * deepen takes the converted indices for the upper left most node in the
	 * grid outside of an emptied area and adds middle vertices to each edge
	 * adds a central node and connects them all to the center.
	 * 
	 * @param z
	 *            the z index.
	 * @param x
	 *            the x index.
	 */
	private void deepen(int z, int x) {
		z = z * this.zLength + this.startZ;
		x = x * this.xLength + this.startX;

		ArrayList<Vertex> block = new ArrayList<Vertex>();
		Vertex topRight = this.getVertexAt(z, x);
		Vertex bottomRight = this.getVertexAt(z, x + this.xLength);
		Vertex topLeft = this.getVertexAt(z + this.zLength, x);
		Vertex bottomLeft = this
				.getVertexAt(z + this.zLength, x + this.xLength);

		block.add(0, topRight);
		block.add(1, bottomRight);
		block.add(2, topLeft);
		block.add(3, bottomLeft);

		block.add(this.getVertAtMid(topLeft, topRight));
		block.add(this.getVertAtMid(topLeft, bottomLeft));
		block.add(this.getVertAtMid(topRight, bottomRight));
		block.add(this.getVertAtMid(bottomRight, bottomLeft));

		for (Vertex v : block) {
			if (v == null) {
				return;
			}
		}

		addCenter(block);
	}

	protected Vertex getVertexAt(int z, int x) {
		ArrayList<Vertex> vert = this.getVerticesNear(z, x, 1, 1);
		if (vert.size() > 0)
			return vert.get(0);
		return null;
	}

	/**
	 * addCenter adds a central node to the given block and connects the nodes
	 * to the central node.
	 * 
	 * @param block
	 *            an ArrayList with topRight node at index 0 bottomRight at
	 *            index 1 topLeft at index 2 and bottomLeft at index 3 all other
	 *            nodes come after these in any order.
	 */
	protected void addCenter(ArrayList<Vertex> block) {

		int x = (int) (block.get(1).getX() + block.get(2).getX()) / 2;
		int z = (int) (block.get(0).getZ() + block.get(2).getZ()) / 2;

		Vertex center = this.getVertexAt(z, x);
		if (center == null) {
			center = new Vertex(z, x);
			for (Vertex v : block) {
				connectDouble(v, center);
			}
			graph.addVertex(center);
		}

	}

	/**
	 * removes the vertices in the given area.
	 * 
	 * @param minZ
	 *            the min z location.
	 * @param maxZ
	 *            the max z location.
	 * @param minX
	 *            the min x location.
	 * @param maxX
	 *            the max x location.
	 */
	protected void removeVerts(int minZ, int maxZ, int minX, int maxX) {
		int z = (minZ + maxZ) / 2;
		int x = (minX + maxX) / 2;
		int rangeX = maxX - minX;
		int rangeZ = maxZ - minZ;
		ArrayList<Vertex> vertsToRemove = getVerticesNear(z, x, rangeZ, rangeX);
		for (Vertex v : vertsToRemove) {
			graph.removeVertex(v);
		}
	}

	/**
	 * plantersRows creates a series of rows like a plowed field surrounded 
	 * by edges.
	 * 
	 * @param minZ
	 *            the minimum z position.
	 * @param minX
	 *            the minimum x position.
	 * @param maxZ
	 *            the maximum z position.
	 * @param maxX
	 *            the maximum x position.
	 * @param dimZ
	 *            the z dimension for the grid squares.
	 * @param dimX
	 *            the x dimension for the grid squares.
	 */
	protected void plantersRows(int minZ, int minX, int maxZ, int maxX, int dimZ,
			int dimX) {
		offset = (int)((maxX - minX));
		this.startZ = minZ + dimZ / 2;
		this.startX = minX + dimX / 2;
		this.zLength = dimZ;
		this.xLength = dimX;

		ArrayList<Vertex> previousColumn = new ArrayList<Vertex>();

		for (int i = minX + (dimX / 2); i < maxX; i += dimX) {
			ArrayList<Vertex> column = new ArrayList<Vertex>();
			for (int j = minZ + dimZ / 2, count = 0; j < maxZ; j += dimZ, count++) {
				column.add(new Vertex(j, i));
				if (count > 0) {
					connectDouble(column.get(count - 1), column.get(count));
					if (previousColumn.size() == column.size() || column.size() == 1) {
						connectDouble(column.get(column.size() - 1),
								previousColumn.get(column.size() - 1));
					}
				}
				if(count == 0 && previousColumn.size() > 0) {
					connectDouble(column.get(column.size() - 1),
							previousColumn.get(column.size() - 1));
				}
				this.endX = j;
			}
			previousColumn = column;
			this.endZ = i;
		}
	}
	
	/**
	 * grid creates a grid of connected vertices from the minZ, minX position to
	 * the maxZ, maxX position in the center of the dimZ, dimX square.
	 * 
	 * @param minZ
	 *            the minimum z position.
	 * @param minX
	 *            the minimum x position.
	 * @param maxZ
	 *            the maximum z position.
	 * @param maxX
	 *            the maximum x position.
	 * @param dimZ
	 *            the z dimension for the grid squares.
	 * @param dimX
	 *            the x dimension for the grid squares.
	 */
	protected void grid(int minZ, int minX, int maxZ, int maxX, int dimZ,
			int dimX) {

		this.startZ = minZ + dimZ / 2;
		this.startX = minX + dimX / 2;
		this.zLength = dimZ;
		this.xLength = dimX;

		ArrayList<Vertex> previousColumn = new ArrayList<Vertex>();

		for (int i = minZ + (dimZ / 2); i < maxZ; i += dimZ) {
			ArrayList<Vertex> column = new ArrayList<Vertex>();
			for (int j = minX + dimX / 2, count = 0; j < maxX; j += dimX, count++) {
				column.add(new Vertex(i, j));
				if (count > 0) {
					connectDouble(column.get(count - 1), column.get(count));
					if (previousColumn.size() > 0) {
						connectDouble(column.get(count - 1),
								previousColumn.get(count - 1));
						connectDouble(column.get(count),
								previousColumn.get(count));
					}
				}
				this.endX = j;
			}
			if (column.size() > 1) {
				connectDouble(column.get(column.size() - 2),
						column.get(column.size() - 1));
				if (previousColumn.size() > 1) {
					connectDouble(column.get(column.size() - 2),
							previousColumn.get(previousColumn.size() - 2));
					connectDouble(column.get(column.size() - 1),
							previousColumn.get(previousColumn.size() - 1));
				}
			}
			previousColumn = column;
			this.endZ = i;
		}
	}

	/**
	 * getVertAtMid creates and returns a Vertex that lies just between two
	 * Vertices if one does not already exist.
	 * 
	 * @param start
	 *            the first Vertex.
	 * @param end
	 *            the second Vertex.
	 * @return the new Vertex.
	 */
	protected Vertex getVertAtMid(Vertex start, Vertex end) {
		if (start != null && end != null) {
			long midX = start.getX() + end.getX();
			long midZ = start.getZ() + end.getZ();
			midX /= 2;
			midZ /= 2;
			ArrayList<Vertex> mid = this.getVerticesNear((int) midZ,
					(int) midX, (int) (this.zLength / 4),
					(int) (this.xLength / 4));
			if (mid.size() == 0) {
				mid.add(new Vertex((int) midZ, (int) midX));
				removeEdges(start, end);
				connectDouble(start, mid.get(0));
				connectDouble(mid.get(0), end);
			}
			return mid.get(0);
		}
		return null;
	}

	/**
	 * removeEdges removes the edges between the two vertices.
	 * 
	 * @param start
	 *            the first vertex.
	 * @param end
	 *            the second vertex.
	 */
	private void removeEdges(Vertex start, Vertex end) {
		ArrayList<Edge> edges = start.getEdges();
		removeEdge(start, end, edges);
		 edges = end.getEdges();
		 removeEdge(end, start, edges);
	}

	/**
	 * removeEdge removes the edge from the start to end vertex from the start
	 * vertex.
	 * 
	 * @param start
	 *            the start vertex.
	 * @param end
	 *            the end vertex.
	 * @param edges
	 *            the ArrayList of edges from the start vertex.
	 */
	private void removeEdge(Vertex start, Vertex end, ArrayList<Edge> edges) {
		for (Edge e : edges) {
			Vertex vert = e.getEnd();
			if (vert.equals(end)) {
				start.removeEdge(e);
				break;
			}
		}
	}

	/**
	 * Helper method that generates an edge weight for the edge. Currently uses
	 * the Euclidean distance for weight.
	 * 
	 * @param edge
	 *            the edge to weigh.
	 */
	protected void weighEdge(Edge edge) {
		Vertex start = edge.getStart();
		Vertex end = edge.getEnd();
		float length = getLength(start, end);
		edge.setWeight(length);
	}

	/**
	 * getLength is a helper method that gets the length from one Vertex to
	 * another.
	 * 
	 * @param start
	 *            the start vertex.
	 * @param end
	 *            the end vertex.
	 * @return the length or Euclidean distance between the Vertices.
	 */
	private float getLength(Vertex start, Vertex end) {
		float x = start.getX() - end.getX();
		float z = start.getZ() - end.getZ();
		x = x * x;
		z = z * z;
		float length = (float) Math.sqrt(z + x);
		return length;
	}

	/**
	 * getVerticesNear returns an ArrayList of Vertices near the z,x position
	 * within the specified range, if any exist. If there is not a Vertex in
	 * that range, an empty ArrayList is returned.
	 * 
	 * @param z
	 *            the z position to look near.
	 * @param x
	 *            the x position to look near.
	 * @param range
	 *            the range of the search.
	 * @return the ArrayList of vertices
	 */
	public ArrayList<Vertex> getVerticesNear(int z, int x, int rangeZ,
			int rangeX) {
		rangeX = (int) (rangeX / 2);
		rangeZ = (int) (rangeZ / 2);
		ArrayList<Vertex> toReturn = new ArrayList<Vertex>();
		ArrayList<Vertex> verts = graph.getVertices();
		for (Vertex v : verts) {
			int currentX = v.getX();
			int currentZ = v.getZ();
			if (currentX >= x - rangeX && currentX <= x + rangeX
					&& currentZ >= z - rangeZ && currentZ <= z + rangeZ) {
				toReturn.add(v);
			}
		}

		return toReturn;
	}


}
