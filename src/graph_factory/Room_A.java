package graph_factory;

public class Room_A extends Graph_Factory {

	private static final int SEP = 10;

	/**
	 * creates an interactive graph for the visible level used in homework two
	 * part four.
	 */
	public Room_A() {
		super();
		super.grid(100, 100, 240, 610, SEP, SEP);
		super.createHole(110, 220, 220, 300);
		super.createHole(110, 420, 220, 510);//These work.
	}
}
