package path_finding;

import java.util.ArrayList;

import path_following.Edge;
import path_following.Graph;
import path_following.Path;
import path_following.Vertex;

public class A_Star extends Search {

	/**
	 * The heuristic to be used by A*
	 */
	private Heuristic heuristic;

	/**
	 * setHeuristic allows the caller to determine which heuristic to use.
	 * 
	 * @param h
	 *            the heuristic to be used by A*
	 */
	public void setHeuristic(Heuristic h) {
		this.heuristic = h;
	}

	@Override
	public Path findPath(Vertex start, Vertex end, Graph graph) {
		// 1. initilize and put the start vertex in the open list.
		if (!start.equals(end)) {
			initialize(start);
			boolean notFound = true;
			Pair endPair = new Pair(end, new Edge());

			while (notFound) {
				// 2. get the vertex with least CSF.
				Pair xPair = openList.remove();
				Vertex x = xPair.getVert();
				// Every expanded node will be blue.
				x.setColor(2);

				// 3. For each neighbor y of x add to the open list
				// with CSF = CSFx + weight(x to y).
				count++;
				ArrayList<Edge> edges = x.getEdges();
				for (int i = 0; i < edges.size(); i++) {
					Edge currentEdge = edges.get(i);
					Vertex y = currentEdge.getEnd();
					y.setColor(1);
					float csf = x.getCsf() + currentEdge.getWeight();
					float etc = csf
							+ heuristic.h(x.getPosition(), end.getPosition());
					Pair yPair = new Pair(y, currentEdge);

					// If y is on the open list, and CSF of new y
					// less than CSF of y replace y.
					boolean on_list = openList.contains(yPair);
					boolean on_closedList = closedList.containsKey(y);
					if (on_list || on_closedList) {
						if (yPair.getVert().getCsf() > csf) {
							if (on_closedList)
								closedList.remove(yPair);
							if (on_list)
								openList.remove(yPair);
							y.setEtc(etc);
							y.setCsf(csf);
							openList.add(yPair);

						}
					} else {
						y.setEtc(etc);
						y.setCsf(csf);
						openList.add(yPair);
					}

				}

				// 4. Move x to the closed list.
				closedList.put(x, xPair);

				// 5. if the next vertex is the end, stop.
				Pair nextPair = openList.peek();
				if (nextPair.getVert().equals(end)) {
					notFound = false;
					endPair = openList.remove();
				}

				if (closedList.size() > maxClosed)
					maxClosed = closedList.size();
				if (openList.size() > maxOpen)
					maxOpen = closedList.size();

			}

			hasComp = false;

			return makePath(start, end, endPair);
		}
		return new Path();
	}

}
