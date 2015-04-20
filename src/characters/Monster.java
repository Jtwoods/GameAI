package characters;

import java.util.Random;

import motion.Sprite;
import processing.core.PApplet;
import processing.core.PImage;
import structure.Drawable;

public class Monster extends Sprite implements Drawable {

	private PApplet graphics;
	private PImage body;
	private PImage head;
	private PImage current_head;
	private PImage[] heads;
	private Random rand;
	private float max_velocity;
	private static final float VEL = 2.5f;
	private static final float RUN_VEL = 4f;

	public Monster(PApplet graphics, int z, int x, float theta, int room) {
		super(z, x, theta, room);
		heads = new PImage[5];
		rand = new Random();
		setGraphics(graphics);
		max_velocity = VEL;
		runCount = 0;
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
		limitTheta(super.theta);
		setHead();
		setBody();
		float z_center = z;
		float x_center = x;
		graphics.pushMatrix();
		graphics.translate(z_center, x_center);
		graphics.rotate((float) (theta - Math.PI / 2));
		graphics.translate(-(z_center), -(x_center));
		graphics.image(body, z - head.width / 2, x - head.height / 2);
		graphics.image(current_head, z - head.width / 2, x - head.height / 2);
		graphics.popMatrix();
		

	}

	private int runCount;
	private PImage bodyArmsOut;
	private PImage bodyArmsHalf;
	private PImage bodyArmsDown;
	private PImage bodyCapture;
	private boolean capture;

	private void setBody() {
		if (!capture) {
			if (velocity.mag() > 0) {
				if (runCount > 2) {
					if (runCount > 60) {
						body = bodyArmsOut;
					} else {
						body = bodyArmsHalf;
					}
				}
				runCount++;
			} else {
				body = bodyArmsDown;
				runCount = 0;
			}
		}

	}

	private void setHead() {
		if (rand.nextInt(50) > 45)
			current_head = heads[rand.nextInt(5)];
		else if (rand.nextInt(50) > 47)
			current_head = head;
	}

	@Override
	public void setGraphics(PApplet graphics) {
		this.graphics = graphics;
		body = graphics.loadImage("body_arms_down.png");
		head = graphics.loadImage("head_down.png");
		current_head = head;
		for (int i = 0; i < 5; i++) {
			heads[i] = graphics.loadImage("head_down_tenticles_" + (i + 1)
					+ ".png");
		}
		bodyArmsOut = graphics.loadImage("body_arms_out.png");
		bodyArmsHalf = graphics.loadImage("body_arms_half.png");
		bodyArmsDown = graphics.loadImage("body_arms_down.png");
		bodyCapture = graphics.loadImage("body_capture.png");
	}

	public float radiusOfDeceleration() {
		return 60f;
	}

	public float radiusOfSatisfaction() {
		return 45f;
	}

	public void setCapture() {
		capture = true;
		body = bodyCapture;
	}

	public void resetCapture() {
		capture = false;
		body = bodyArmsDown;
	}

}
