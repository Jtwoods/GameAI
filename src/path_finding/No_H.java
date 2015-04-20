package path_finding;

import path_following.Position;

/**
 * No_H is a hearistic that allows Dijkstra's to retain it's properties
 * by producing a constant zero heuristic.
 * @author James Woods
 *
 */
public class No_H extends Heuristic {

	@Override
	public float h(Position current, Position next) {
		return 0;
	}

}
