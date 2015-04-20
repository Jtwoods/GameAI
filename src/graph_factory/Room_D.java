package graph_factory;

import java.util.ArrayList;

import path_following.Vertex;

public class Room_D extends Graph_Factory {

	private static final int DIST = 10;
	private static final int SEP = 10;

	/**
	 * creates an interactive graph for the visible level used in homework two
	 * part four.
	 */
	public Room_D() {
		super();
		super.grid(350, 570, 900, 600, SEP, SEP);
		super.grid(850, 400, 900, 500, SEP, SEP);
		
		ArrayList<Vertex> vert = super.getVerticesNear(870, 500, DIST, DIST);
		Vertex vert1 = vert.get(0);
		
		vert = super.getVerticesNear(870, 570, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		Vertex vert2 = super.getVertAtMid(vert1, vert.get(0));
		Vertex vert3 = super.getVertAtMid(vert2, vert.get(0));
		Vertex vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);
	}
}
