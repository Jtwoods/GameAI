package structure;

import java.util.ArrayList;

import motion.BackGround;
import control.Controller;
import processing.core.PApplet;

/**
 * Level provides a basic platform to provide different
 * levels inside the ControllerViewer.
 * @author James Woods
 */
public abstract class Level{
	
	//background is the displayed background for the level.
	protected BackGround background;
	//running stores the state of the game true if running and false if not.
	protected boolean running;
	//view is the ControllerViewer that contains this level.
	protected Controller view;
	//sprites is an ArrayList of Drawable sprites that can be rendered.
	protected ArrayList<Drawable> sprites;
	//max Z and X are the x and z sizes of the viewer.
	protected int maxZ;
	protected int maxX;
	
	/**
	 * Level is the constructor for the class and initializes
	 * the needed protected data.
	 * @param view the ControllerViewer that contains the level.
	 */
	public Level(Controller view, int maxZ, int maxX) {
		this.view = view;
		sprites = new ArrayList<Drawable>();
		this.maxZ = maxZ;
		this.maxX = maxX;
	}
	
	/**
	 * setup passes a PApplet object on to the
	 * Level allowing the Level
	 * to make use of the graphics API and
	 * creates the logical structures needed for 
	 * a game.
	 * @param PApplet a graphical object for the 
	 * graphics API.
	 */
	public abstract void setup(PApplet graphix);

	/**
	 * hasNextFrame returns true if there are more 
	 * frames to render by using internal logical structures.
	 * @return true if there are more frames to render
	 * false if not.
	 */
	public boolean hasNextFrame() {
		return running;
	}

	/**
	 * getNextFrame returns an array of Drawable
	 * objects that can be rendered by the graphics API.
	 * @return
	 */
	public ArrayList<Drawable> getNextFrame() {
		return sprites;
	}

	/**
	 * getBackGround returns a Drawable background. 
	 * @return
	 */
	public Drawable getBackGround() {
		return background;
	}
	
	/**
	 * getView returns the ControllerViewer for that is contolling the flow 
	 * of operations for this level.
	 * @return ControllerViewer
	 */
	public Controller getView() {
		return view;
	}

}
