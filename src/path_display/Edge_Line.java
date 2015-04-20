package path_display;

import processing.core.PApplet;
import structure.Drawable;

/**
 * Edge_Line is a line that represents an edge.
 * @author James Woods
 *
 */
public class Edge_Line implements Drawable {
	
	private static final float TWO_POINT_FIVE = 2.5f;
	private static final double FOUR = 4;

	/**
	 * Point is used to mark the points of a triangle.
	 * @author James Woods
	 *
	 */
	private class Point {
		//z and x are the coordinates for the point.
		protected int z;
		protected int x;
	}
	
	/**
	 * x and z are the x and z position
	 * of the start position of the line in the viewer.
	 */
	private float startX;
	private float startZ;
	
	/**
	 * x and z are the x and z position
	 * of the end position of the line in the viewer.
	 */
	private float endX;
	private float endZ;
	private Point a;
	private Point b;
	private Point c;
	

	/**
	 * Edge_Line constructs an Edge_Line using the start and end positions of 
	 * vertices and the graphic object.
	 * @param startX
	 * @param startZ
	 * @param endX
	 * @param endZ
	 * @param graphic
	 */
	public Edge_Line(int startZ, int startX, int endZ, int endX) {
		super();
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
		this.color = 0;
		
		float theta = (float)Math.atan2((double)(startX - endX), (double)(startZ - endZ));
		
		float piFourths = (float)Math.PI/4;
	
		a = new Point();
		b = new Point();
		c = new Point();
		
		a.z = (int)(endZ + (TWO_POINT_FIVE * Math.cos(theta)));
		a.x = (int)(endX + (TWO_POINT_FIVE * Math.sin(theta)));
		
		//set the position of the left Point.
		b.z = (int)((FOUR * Math.cos(theta + piFourths)) + a.z);
		b.x = (int)((FOUR * Math.sin(theta + piFourths)) + a.x);
		
		//set the position of the right Point.
		c.z = (int)((FOUR * Math.cos(theta - piFourths)) + a.z);
		c.x = (int)((FOUR * Math.sin(theta - piFourths)) + a.x);
	}
	

	/**
	 * graphic is the graphic object from the API.
	 */
	PApplet graphic;
	
	private int color;

	
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
		graphic.fill(color);
		graphic.triangle(a.z, a.x, c.z, c.x, b.z, b.x);
		graphic.stroke(color);
		graphic.line(startZ, startX, endZ, endX);
	}
	
	/**
	 * update color allows the color of this Edge_Line to be changed.
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
