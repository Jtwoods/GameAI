package graph_factory;

public class Room_C extends Graph_Factory {

	private static final int SEP = 10;

	/**
	 * creates an interactive graph for the visible level used in homework two
	 * part four.
	 */
	public Room_C() {
		super();
		super.grid(680, 100, 900, 290, SEP, SEP);
		super.createHole(810, 50, 960, 180);
	}
}
