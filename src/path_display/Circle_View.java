package path_display;

import processing.core.PApplet;
import structure.Drawable;

public class Circle_View implements Drawable {
	/**
	 * x and z are the x and z position
	 * of the spot in the viewer.
	 */
	private int x;
	private int z;
	private double d;
	
	/**
	 * Vertex_Dot creates a Vertex_Dot at the given location.
	 * @param x the x position.
	 * @param z the z position.
	 * @param graphic the graphic object used to render.
	 */
	public Circle_View(int z, int x, double r) {
		super();
		this.x = x;
		this.z = z;
		this.d = r*2;
	}

	/**
	 * graphic is the graphic object from the API.
	 */
	PApplet graphic;
	
	
	@Override
	public void render() {
		graphic.noFill();
		graphic.ellipse(z, x, (float)d, (float)d);
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.graphic = graphics;
	}
}
