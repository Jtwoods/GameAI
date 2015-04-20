package path_finding;

import path_following.Edge;
import path_following.Vertex;

/**
 * Pair is a container that stores an Edge/Vertex pair.
 * @author James Woods
 *
 */
public class Pair {

	/**
	 * vert is the Vertex in the pair.
	 */
	private Vertex vert;
	
	/**
	 * edge is the Edge in the pair.
	 */
	private Edge edge;

	/**
	 * Pair constructs a Pair with the given vertex and edge.
	 * @param vert the Vertex.
	 * @param edge the Edge.
	 */
	public Pair(Vertex vert, Edge edge) {
		super();
		this.vert = vert;
		this.edge = edge;
	}

	/**
	 * getVert gets the Vertex from the Pair.
	 * @return the Vertex.
	 */
	public Vertex getVert() {
		return vert;
	}

	/**
	 * setVertex sets the Vertex for this Pair.
	 * @param vert the new Vertex.
	 */
	public void setVert(Vertex vert) {
		this.vert = vert;
	}

	/**
	 * getEdge gets the Edge for this Pair.
	 * @return the Edge.
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * setEdge sets the Edge for this Pair.
	 * @param edge the new Edge.
	 */
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vert == null) ? 0 : vert.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (vert == null) {
			if (other.vert != null)
				return false;
		} else if (!vert.equals(other.vert))
			return false;
		return true;
	}
	
	
	
}
