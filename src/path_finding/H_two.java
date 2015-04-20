package path_finding;

import path_following.Position;

/**
 * H_two provides a handle for a Heuristic with h method
 * that provides a Euclidean distance value.
 * @author James Woods
 *
 */
public class H_two extends Heuristic {

	@Override
	public float h(Position current, Position next) {
		return Heuristic.euclideanDistance(current, next);
	}

}
