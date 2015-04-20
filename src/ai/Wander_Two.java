package ai;


import java.util.Random;

import characters.Bonzo;
import characters.Monster;
import processing.core.PVector;
import motion.Character;
import motion.Sprite;

public class Wander_Two implements Wander {

	
	// radiusOfSatisfaction is the distance the
	// center of the Kinematic should be from the edge of the screen
	// before turning.
	private int radiusOfSatisfaction = 20;

	private int radiusOfDeceleration = 30;
	
	private float extensionLength = 250;
	private float radiusOfSelection = 70;
	
	private int monsterTime;
	private int bonzoTime;

	// max X and Z are the max x and z for the viewer.
	private int maxX;
	private int maxZ;

	private Character center;

	private AI ai;


	/**
	 * Wander_Movement is the constructor for the class.
	 * 
	 */
	public Wander_Two(int x, int z, Character center, AI ai) {
		this.ai = ai; 
		this.center = center;
		maxX = x;
		maxZ = z;
		monsterTime = 0;
		bonzoTime = 0;

	}

	@Override
	public void wander(Sprite sprite, float time) {
		// If we have moved in a direction for a while
		// change direction randomly.
		
		//create an extension in the direction of motion.
		PVector vector = new PVector(sprite.velocity.x, sprite.velocity.y);
		vector.setMag(extensionLength + sprite.radiusOfDeceleration());
		
		float x = sprite.x + vector.y;
		float z = sprite.z + vector.x;
		
		vector = PVector.random2D();
		vector.setMag(radiusOfSelection + sprite.radiusOfDeceleration());
		
		x += vector.y;
		z += vector.x;

		Character dummy = new Character(null, "", (int)z,(int)x,sprite.getRoom());
		sprite.target = dummy;
		if(sprite instanceof Monster && monsterTime > 60) {
			monsterTime = 0;
			ai.newPath(sprite, dummy, time);
		}
		if(sprite instanceof Bonzo && bonzoTime > 60) {
			bonzoTime = 0;
			ai.newPath(sprite, dummy, time);
		}
		ai.seek(sprite, dummy, time);
		ai.orient(sprite);

		clip(sprite);

		// Stay on the screen.
		remainOnScreen(sprite, sprite.z, sprite.x, time);
		
		
	}


	/**
	 * reaminOnScreen verifies that the kinematic is able to move to the desired
	 * location and moves it there if possible, otherwise the turtle will be
	 * turned around pi radians.
	 * 
	 * @param kin
	 *            the Kinematic to operate on.
	 * @param x
	 *            the possible x position to move to.
	 * @param z
	 *            the possible z position to move to.
	 */
	private void remainOnScreen(Sprite sprite, int x, int z, float time) {

		if (z >= maxZ - radiusOfDeceleration - radiusOfSatisfaction
				|| z <= radiusOfDeceleration + radiusOfSatisfaction
				|| x >= maxX - radiusOfDeceleration - radiusOfSatisfaction
				|| x <= radiusOfDeceleration + radiusOfSatisfaction) {
			// Seek the center of the screen.
			ai.newPath(sprite, center, time);
			sprite.theta = sprite.velocity.heading();

		}

	}
	
	/**
	 * clip verifies that the velocity and acceleration of 
	 * the character are bellow the max for each.
	 * @param sprite the character to verify max for.
	 */
	public void clip(Sprite sprite) {
		if (Math.abs(sprite.velocity.mag()) > sprite.getVelocity())
			sprite.velocity.setMag(sprite.getVelocity());
		if (sprite.acceleration.mag() > AI.MAX_ACCEL)
			sprite.acceleration.setMag(AI.MAX_ACCEL);
	}

}

