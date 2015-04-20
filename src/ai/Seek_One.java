package ai;

import processing.core.PVector;
import motion.Character;
import motion.Sprite;

/**
 * Seek_One provides an implementation of the seek steering behavior.
 * 
 * @author James Woods
 * 
 */
public class Seek_One implements Seek{

	// radiusOfSatisfaction is the distance the
	// center of the Kinematic should be from the edge of the screen
	// before turning.
//	private int radiusOfSatisfaction = 15;
//
//	private int radiusOfDeceleration = 30;

	private int timeTo = 2;


	public Seek_One() {

	}

	/**
	 * seek is a static method that allows the given character to seek out the
	 * target.
	 * 
	 * @param sprite
	 *            the character that will seek.
	 * @param target
	 *            the target to be sought out.
	 * @param time is the elapsed time since the last update.
	 */
	public void seek(Sprite sprite, Character target, float time) {

		// Find the new direction.
		PVector direction = getDirection(sprite, target);

		sprite.acceleration.set(direction);
		sprite.acceleration.setMag(AI.MAX_ACCEL);

		// Clip to max velocity.
		clip(sprite);
		
		// Check for arrival.
		arrive(sprite, target, direction, time);

		// Adjust velocity.
		PVector nextVelocity = new PVector(0, 0);
		nextVelocity.add(sprite.acceleration);
		nextVelocity.mult(time);
		sprite.velocity.add(nextVelocity.x, nextVelocity.y, 0);

		// Clip to max velocity.
		clip(sprite);

		sprite.x += (int) (sprite.velocity.y * time);
		sprite.z += (int) (sprite.velocity.x * time);


	}

	/**
	 * getDirection determines the distance and direction to a target and
	 * returns a Kinematic with the distance as magnitude and direction as the
	 * direction.
	 * 
	 * @param sprite
	 *            to be moved.
	 * @param target
	 *            the target to move to.
	 * @return the PVector containing the distance and direction.
	 */
	public PVector getDirection(Sprite sprite, Character target) {

		float x = target.x - sprite.x;
		float z = target.z - sprite.z;
		
		PVector toReturn = new PVector(z,x);
		return toReturn;
	}

	/**
	 * arrive allows the character to stop when it is within a close proximity 
	 * to the target. This version reduces velocity when inside a radius of deceleration.
	 * @param sprite the sprite to be stopped.
	 * @param target the target at the position to stop at.
	 * @param direction the direction of the target.
	 * @param time the elapsed time since the last update.
	 */
	public void arrive(Sprite sprite, Sprite target, PVector direction,
			float time) {
		float goalSpeed = 0;

		if (direction.mag() < sprite.radiusOfSatisfaction()) {
			sprite.velocity.setMag(0);
			sprite.acceleration.setMag(0);
			sprite.arrived = true;
			sprite.arriving = true;
		} else if (direction.mag() > sprite.radiusOfDeceleration()) {
			goalSpeed = sprite.getVelocity();
			sprite.arrived = false;
			sprite.arriving = false;
		} else {
			goalSpeed = sprite.getVelocity() * direction.mag() / sprite.radiusOfDeceleration();
			goalSpeed -= sprite.velocity.mag();
			goalSpeed /= timeTo;
			sprite.arrived = false;
			sprite.arriving = true;
		}

		sprite.velocity.setMag(goalSpeed);
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
