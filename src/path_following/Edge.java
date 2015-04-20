package path_following;

import path_display.Edge_Line;

/**
 * Edge represents an edge in a directed graph.
 * 
 * @author James Woods
 * 
 */
public class Edge {
	
	/**
	 * weight is the weight of the edge.
	 */
	private float weight;
	
	/**
	 * start is the Vertex that the edge leads away from.
	 */
	private Vertex start;
	
	/**
	 * end is the Vertex that the edge leads to.
	 */
	private Vertex end;
	
	/**
	 * number is the arbitrary order number given to this 
	 * edge during path finding.
	 */
	private int number;
	
	/**
	 * h is the heuristic value assigned to the edge.
	 */
	private float h;
	
	/**
	 * Edge_Line is a line that represents the physical edge.
	 */
	private Edge_Line line;

	/**
	 * Creates an empty Edge.
	 */
	public Edge() {
		this.h = 0;
		this.weight = 0;
		this.number = 0;
		this.start = null;
		this.end = null;
		line = null;
	}
	
	
	/**
	 * Edge creates an Edge.
	 */
	public Edge(Vertex start, Vertex end) {
		this.h = 0;
		this.weight = 0;
		this.number = 0;
		this.start = start;
		this.end = end;
		line = new Edge_Line(start.getZ(), start.getX(), end.getZ(), end.getX());
		start.addEdge(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + Float.floatToIntBits(h);
		result = prime * result + number;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + Float.floatToIntBits(weight);
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
		Edge other = (Edge) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (Float.floatToIntBits(h) != Float.floatToIntBits(other.h))
			return false;
		if (number != other.number)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}


	/**
	 * getDraw returns the Edge_Line for this Edge.
	 * @return the Edge_Line.
	 */
	public Edge_Line getDraw() {
		return line;
	}

	/**
	 * getWeight returns the weight of this edge.
	 * @return the weight of the Edge.
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * setWeigh sets the edge weight of the Edge.
	 * @param weight the new edge weight.
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getEnd() {
		return end;
	}

	public void setEnd(Vertex end) {
		this.end = end;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}


	public void setColor(int i) {
		line.setColor(i);
	}

	
}
