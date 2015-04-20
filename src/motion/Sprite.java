package motion;

import java.util.ArrayList;

import path_following.Graph;
import processing.core.PVector;

public abstract class Sprite {

	protected static final float TWO_PI = (float) (Math.PI * 2);
	public float theta;
	public PVector velocity;
	public PVector acceleration;
	public int x;
	public int z;
	public boolean arrived;
	public boolean arriving;
	public boolean hasPath;
	public Character target;
	public boolean wander;
	public float timer;
	
	public Graph currentGraph;
	//These must be set in the Level instance.
	private Graph graph;

	private ArrayList<Graph> rooms;
	private int room;
	public boolean canRun;
	public boolean hasCollision;
	public int pauseCount;

	public Sprite(int z, int x, float theta, int room) {
		super();
		pauseCount = 0;
		hasPath = false;
		arrived = false;
		arriving = true;
		hasCollision = false;
		velocity = new PVector(0,0);
		acceleration = new PVector(0,0);
		this.z = z;
		this.x = x;
		this.theta = theta;
		target = null;
		wander = true;
		timer = 0;
		this.room = room;
		this.canRun = true;
	}
	
	public void setAngle(float theta) {
		limitTheta(theta);
	}

	public float radiusOfDeceleration() {
		return 30f;
	}

	public float radiusOfSatisfaction() {
		return 15f;
	}

	protected void limitTheta(float f) {
		if (f > TWO_PI)
			f -= TWO_PI;
		if (f < -TWO_PI)
			f += TWO_PI;
		this.theta = f;
	}

	public abstract float getVelocity();
	
	public abstract void walk();
	
	public abstract void run();

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public void setRooms(ArrayList<Graph> rooms) {
		this.rooms = rooms;
		this.currentGraph = rooms.get(room);
	}
	

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}
	
	public Graph getGraph() {
		return graph;
	}

	public ArrayList<Graph> getRooms() {
		return rooms;
	}
	
	public abstract void setCapture();
	
	public abstract void resetCapture();

}