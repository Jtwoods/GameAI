package path_finding;

import path_following.Position;

/**
 * H_one provides a handle to a Heuristic that provides
 * an h method which returns the Manhatan distance from the 
 * current point to the goal.
 * @author James Woods
 *
 */
public class H_one extends Heuristic {

	@Override
	public float h(Position current, Position next) {
		
		return Heuristic.manhatanDistance(current, next);
	}

}
