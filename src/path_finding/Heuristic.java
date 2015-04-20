package path_finding;

import path_following.Position;

/**
 * Heuristic provides an implementation of two different heuristic functions
 * to be used in Dijkstra's algorithm to produce A*. 
 * @author James Woods
 *
 */
public abstract class Heuristic {
	
	/**
	 * h provides an abstract method that allows inheritance of the 
	 * heuristic trait and returns a heuristic value depending on the 
	 * type needed.
	 * @param current the current position.
	 * @param next the next position.
	 * @return the estimated distance from the goal.
	 */
	public abstract float h(Position current, Position next);
	
	
	/**
	 * returns the Euclidean distance between two positions.
	 * @param current the current position.
	 * @param next the next position.
	 * @return the Euclidean distance.
	 */
	public static float euclideanDistance(Position current, Position next) {
		float x = current.getX() - next.getX();
		float z = current.getZ() - next.getZ();
		return (float)Math.sqrt(x*x + z*z);
	}
	
	/**
	 * returns the Manhatan distance between two points.
	 * @param current the current position.
	 * @param next the next position.
	 * @return the Manhatan distance.
	 */
	public static float manhatanDistance(Position current, Position next) {
		float x = (float) Math.sqrt(current.getX() - next.getX());
		float z = (float) Math.sqrt(current.getZ() - next.getZ());
		return x + z;
	}
	
	
}
