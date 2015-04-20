package motion;

import processing.core.PApplet;
import structure.Drawable;

/**
 * Spot is a very simple viewable object that
 * represents where a character has been.
 * @author James Woods
 *
 */
public class Spot implements Drawable {

	/**
	 * DIAMETER is the diameter of the circle 
	 * that represents the Spot in the viewer.
	 */
	private static final int DIAMETER = 5;
	
	/**
	 * x and z are the x and z position
	 * of the spot in the viewer.
	 */
	private int x;
	private int z;
	
	/**
	 * graphic is the graphic object from the API.
	 */
	PApplet graphic;
	
	/**
	 * Spot constructs a Spot in the given location.
	 * @param z the z coordinate of the spot.
	 * @param x the x coordinate of the spot.
	 */
	public Spot(PApplet graphic, int z, int x) {
		this.graphic = graphic;
		this.x = x;
		this.z = z;
	}
	
	@Override
	public void render() {
		graphic.fill(0);
		graphic.ellipse(z, x, DIAMETER, DIAMETER);
		
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.graphic = graphics;
	}

}
