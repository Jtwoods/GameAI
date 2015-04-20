package graph_factory;

import java.util.ArrayList;

import path_following.Vertex;


/**
 * Is an interactive graph that represents the physical environment created to
 * support functionality for homework two part four.
 * 
 * @author James Woods
 * 
 */
public class Interactive_Graph extends Graph_Factory {

	private static final int DIST = 10;
	private static final int SEP = 10;

	/**
	 * creates an interactive graph for the visible level used in homework two
	 * part four.
	 */
	public Interactive_Graph() {
		super();
		super.grid(100, 100, 240, 610, SEP, SEP);
		super.createHole(110, 220, 220, 300);
		super.createHole(110, 420, 220, 510);//These work.
		super.grid(350, 60, 750, 460, SEP, SEP);
		super.createHole(360, 60, 560, 350);
		super.createHole(570, 60, 790, 390);//Done
		super.grid(680, 100, 900, 290, SEP, SEP);
		super.createHole(810, 50, 960, 180);
		super.grid(350, 570, 900, 600, SEP, SEP);
		super.grid(850, 400, 900, 500, SEP, SEP);
		
//		//Top left door.
		ArrayList<Vertex> vert = super.getVerticesNear(240, 130, DIST, DIST);
		Vertex vert1 = vert.get(0);
		
		vert = super.getVerticesNear(350, 125, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		Vertex vert2 = super.getVertAtMid(vert1, vert.get(0));
		Vertex vert3 = super.getVertAtMid(vert2, vert.get(0));
		Vertex vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);

//		//Bottom left door.
		vert = super.getVerticesNear(240, 590, DIST, DIST);
		vert1 = vert.get(0);
		
		vert = super.getVerticesNear(350, 590, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		vert2 = super.getVertAtMid(vert1, vert.get(0));
		vert3 = super.getVertAtMid(vert2, vert.get(0));
		vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);

//		//Top right door.
		vert = super.getVerticesNear(570, 130, DIST, DIST);
		vert1 = vert.get(0);
		
		vert = super.getVerticesNear(680, 130, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		vert2 = super.getVertAtMid(vert1, vert.get(0));
		vert3 = super.getVertAtMid(vert2, vert.get(0));
		vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);
	
//		//Bottom right door.
		vert = super.getVerticesNear(880, 290, DIST, DIST);
		vert1 = vert.get(0);
		
		vert = super.getVerticesNear(880, 410, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		vert2 = super.getVertAtMid(vert1, vert.get(0));
		vert3 = super.getVertAtMid(vert2, vert.get(0));
		vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);

//		//Middle door.
		vert = super.getVerticesNear(440, 460, DIST, DIST);
		vert1 = vert.get(0);
		
		vert = super.getVerticesNear(445, 570, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		vert2 = super.getVertAtMid(vert1, vert.get(0));
		vert3 = super.getVertAtMid(vert2, vert.get(0));
		vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);

//		//Hallway.
		vert = super.getVerticesNear(870, 500, DIST, DIST);
		vert1 = vert.get(0);
		
		vert = super.getVerticesNear(870, 570, DIST, DIST);
		super.connectDouble(vert1, vert.get(0));
		vert2 = super.getVertAtMid(vert1, vert.get(0));
		vert3 = super.getVertAtMid(vert2, vert.get(0));
		vert4 = super.getVertAtMid(vert2, vert1);
		super.getVertAtMid(vert3, vert2);
		super.getVertAtMid(vert3, vert.get(0));
		super.getVertAtMid(vert4, vert2);
		super.getVertAtMid(vert4, vert1);

		

	}
}
