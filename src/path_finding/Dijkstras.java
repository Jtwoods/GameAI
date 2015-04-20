package path_finding;

import java.util.ArrayList;
import path_following.Edge;
import path_following.Graph;
import path_following.Path;
import path_following.Vertex;

public class Dijkstras extends Search {

	/**
	 * findPath finds the shortest path from start to end in the graph.
	 * @param start the start vertex for the search
	 * @param end the end vertex for the search
	 * @param graph the graph to be searched.
	 * @return the Path from start to end.
	 */
	public Path findPath(Vertex start, Vertex end, Graph graph) {
		// 1. initilize and put the start vertex in the open list.
		initialize(start);
		boolean notFound = true;
		Pair endPair = new Pair(end, new Edge());

		while (notFound) {
			// 2. get the vertex with least CSF.
			Pair xPair = openList.remove();
			Vertex x = xPair.getVert();
			//Every expanded node will be blue.
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
						y.setCsf(csf);
						openList.add(yPair);

					}
				} else if (!on_list) {
					y.setCsf(csf);
					openList.add(yPair);
				}
			}

			// 4. Move x to the closed list.
			closedList.put(x, xPair);

			// 5. if the next vertex is the end, stop.
			Pair nextPair = openList.peek();
			if (nextPair == null || nextPair.getVert().equals(end)) {
				notFound = false;
				endPair = openList.remove();
			}
			
			if(closedList.size() > maxClosed)
				maxClosed = closedList.size();
			if(openList.size() > maxOpen)
				maxOpen = closedList.size();

		}

		hasComp = false;
		
		return makePath(start, end, endPair);
	}

}
