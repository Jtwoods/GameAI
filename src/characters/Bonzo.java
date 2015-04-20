package characters;

import java.util.Random;

import motion.Sprite;
import processing.core.PApplet;
import processing.core.PImage;
import structure.Drawable;

public class Bonzo extends Sprite implements Drawable {

	private PApplet graphics;
	private PImage body;
	private PImage head;
	private PImage current_head;
	private PImage[] heads;
	private Random rand;
	private float max_velocity;
	private static final float VEL = 2;
	private static final float RUN_VEL = 3;
	private boolean capture;

	public Bonzo(PApplet graphics, int z, int x, float theta, int room) {
		super(z, x, theta, room);
		heads = new PImage[5];
		rand = new Random();
		setGraphics(graphics);
		max_velocity = VEL;
		capture = false;
	}

	public void walk() {
		max_velocity = VEL;
	}

	public void run() {
		max_velocity = RUN_VEL;
	}

	public float getVelocity() {
		return max_velocity;
	}

	@Override
	public void render() {
		if (!capture) {
			limitTheta(super.theta);
			setHead();
			float z_center = z;
			float x_center = x;
			graphics.pushMatrix();
			graphics.translate(z_center, x_center);
			graphics.rotate((float) (theta - Math.PI / 2));
			graphics.translate(-(z_center), -(x_center));
			graphics.image(body, z - head.width / 2, x - head.height / 2);
			graphics.image(current_head, z - head.width / 2, x - head.height
					/ 2);
			graphics.popMatrix();
		}

	}

	private void setHead() {
		// if(rand.nextInt(50) > 45)
		// current_head = heads[rand.nextInt(5)];
		// else if(rand.nextInt(50) > 47)
		// current_head = head;
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.graphics = graphics;
		body = graphics.loadImage("Character_body_1.png");
		head = graphics.loadImage("Character_head_0.png");
		current_head = head;
	}

	public float radiusOfDeceleration() {
		return 60f;
	}

	public float radiusOfSatisfaction() {
		return 40f;
	}

	@Override
	public void setCapture() {
		capture = true;
	}

	@Override
	public void resetCapture() {
		capture = false;
	}

}
