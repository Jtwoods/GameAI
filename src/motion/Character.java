package motion;

import processing.core.PApplet;
import processing.core.PVector;
import structure.Drawable;

/**
 * Character provides a movable character platform that
 * will use the Drawable interface to perform the render
 * operation on the provided png file. 
 * @author James Woods
 *
 */
public class Character extends Sprite implements Drawable{
	
	private float velocity;
	/**
	 * Point is used to mark the points of a triangle.
	 * @author James Woods
	 *
	 */
	class Point {
		//z and x are the coordinates for the point.
		protected int z;
		protected int x;
	}

	/**
	 * HEAD_DISTANCE is the distance from the 
	 * center of the Turtle to the tip of the head.
	 */
	static final int HEAD_DISTANCE = 22;
	
	/**
	 * RADIUS is the radius of the body of the Turtle.
	 */
	static final int RADIUS = 9;
	
	/**
	 * DIAMETER is the diameter of the body of the turtle 
	 * used to calculate the distance between the corners of the 
	 * head.
	 */
	static final int DIAMETER = 19;
	
	/**
	 * PI_THIRDS is pi divided by three.
	 */
	static final double PI_THIRDS = Math.PI / 3;
	
	/**
	 * parent is the PApplet that will render the turtle
	 * on the PApplet.
	 */
	PApplet parent;
	
	//a is the outer point of the triangle used for the Turtle's head.
	Point a;
	
	//b is the left point of the triangle used for the Turtle's head.
	Point b;
	
	//c is the right point of the triangle used for the Turtle's head.
	Point c;
	
	public void update() {
		createAngle(super.theta);
		parent.fill(0);
		parent.ellipse(super.z, super.x,DIAMETER,DIAMETER);
		parent.triangle(a.z,a.x,b.z,b.x,c.z,c.x);
	}
	
	/**
	 * setAngle sets the directional angle of the Turtle's head.
	 * @param theta the directional angle that the head is to be set to.
	 */
	public void createAngle(float theta) {
		//Limit the size of theta.
		if(theta > TWO_PI)
				theta -= TWO_PI;
		super.theta = theta;
		
		//set the position of the head point.
		a.z = (int)((HEAD_DISTANCE *  Math.cos(super.theta)) + z);
		a.x = (int)((HEAD_DISTANCE *  Math.sin(super.theta)) + x);
	
		//set the position of the left Point.
		b.z = (int)((RADIUS *  Math.cos(super.theta + PI_THIRDS)) + z);
		b.x = (int)((RADIUS *  Math.sin(super.theta + PI_THIRDS)) + x);
		
		//set the position of the right Point.
		c.z = (int)((RADIUS *  Math.cos(super.theta - PI_THIRDS)) + z);
		c.x = (int)((RADIUS *  Math.sin(super.theta - PI_THIRDS)) + x);
		
	}
	
	/**
	 * Character is the constructor for the class
	 * and provides instantiation of all private data
	 * and creates a pixel representation of the given 
	 * file name for a .png file.
	 * @param process the processing object.
	 * @param picture the path to the picture to display.
	 * @param z the initial z position.
	 * @param x the initial x position.
	 */
	public Character(PApplet process, String picture, int z, int x, int room) {
		super(z,x,0, room);
		a = new Point();
		b = new Point();
		c = new Point();
		this.parent = process;
		velocity = 0;
//		super.x = x;
//		super.z = z;
//		super.theta = 0;
//		super.velocity = new PVector(0,0);
//		super.acceleration = new PVector(0,0);
		
	}

	@Override
	public void render() {
		this.update();
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.parent = graphics;
	}

	@Override
	public float getVelocity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCapture() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCapture() {
		// TODO Auto-generated method stub
		
	}





}
