package levels;

import java.util.ArrayList;

import graph_factory.Graph_Factory;
import graph_factory.Interactive_Graph;
import graph_factory.Room_A;
import graph_factory.Room_B;
import graph_factory.Room_C;
import graph_factory.Room_D;
import motion.Character;
import motion.Sprite;
import path_finding.Search;
import path_following.Graph;
import processing.core.PApplet;
import structure.Drawable;
import structure.Level;
import ai.Path_Seek;
import all_together.All_Together_BackGround;
import characters.Bonzo;
import control.Controller;

public class DecisionTree extends Level {

	private Search search;

	public DecisionTree(Controller view, int maxZ, int maxX,
			Search search) {
		super(view, maxZ, maxX);
		this.search = search;
	}

	@Override
	public void setup(PApplet graphix) {
		super.running = true;
		super.background = new All_Together_BackGround(graphix);
		Sprite bonzo = new Bonzo(graphix, maxZ / 2, maxX / 2, 0.0f, 1);
		super.sprites.add((Drawable) bonzo);
		super.view.setTarget(new Character(graphix, "", maxZ / 2, maxX / 2, 1));
		Path_Seek seek = new Path_Seek();
		Graph_Factory factory = new Interactive_Graph();
		bonzo.setGraph(factory.getGraph());
		ArrayList<Graph> rooms = new ArrayList<Graph>();
		factory = new Room_A();
		rooms.add(0,factory.getGraph());
		factory = new Room_B();
		rooms.add(1,factory.getGraph());
		factory = new Room_C();
		rooms.add(2,factory.getGraph());
		factory = new Room_D();
		rooms.add(3,factory.getGraph());
		bonzo.setRooms(rooms);
		seek.setSearch(search);
		super.view.setSeek(seek);

	}

}



