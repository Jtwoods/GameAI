package path_display;

import processing.core.PApplet;
import structure.Drawable;

public class Vertex_Dot implements Drawable {

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
	 * color is the color of the vertex.
	 */
	private int color;
	
	/**
	 * Vertex_Dot creates a Vertex_Dot at the given location.
	 * @param x the x position.
	 * @param z the z position.
	 * @param graphic the graphic object used to render.
	 */
	public Vertex_Dot(int z, int x) {
		super();
		this.x = x;
		this.z = z;
		this.color = 0;
	}

	/**
	 * graphic is the graphic object from the API.
	 */
	PApplet graphic;
	
	
	@Override
	public void render() {
		if(color == 0)
			this.color = (int)graphic.color(0,0,0);
		else if(color == 1)
			this.color = (int)graphic.color(255, 255, 0);
		else if(color == 2)
			this.color = (int)graphic.color(0,0,255);
		else if(color == 3)
			this.color = (int)graphic.color(34,139,34);
		graphic.stroke(color);
		graphic.fill(color);
		graphic.ellipse(z, x, DIAMETER, DIAMETER);
	}
	
	/**
	 * update color allows the color of this Vertex_Dot to be changed.
	 * @param next
	 */
	public void setColor(int next) {
		color = next;
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.graphic = graphics;
	}

}
