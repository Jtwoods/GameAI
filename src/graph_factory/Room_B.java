package graph_factory;

public class Room_B extends Graph_Factory {

	private static final int SEP = 10;

	/**
	 * creates an interactive graph for the visible level used in homework two
	 * part four.
	 */
	public Room_B() {
		super();
		super.grid(350, 60, 750, 460, SEP, SEP);
		super.createHole(360, 60, 570, 350);
		super.createHole(580, 60, 790, 390);//Done
	}
}
