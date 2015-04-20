package ai;

import motion.Sprite;

public class Orient {

	// PI.
	private static final float PI = (float) Math.PI;

	// CHUNKS_OF_ROTATION is the number of chunks
	// of time that must elapse before the orientation
	// will match the direction of movement for the
	// Kinematic.
	private static final float CHUNKS_OF_ROTATION = 12;

	// frames is the number of "frames" or chunks of time that
	// have elapsed with movement of the Kinematic orientaton
	// taking place.
	private int frames;

	public Orient() {
		frames = 1;
	}

	public void updateOrientation(Sprite character) {
		// We assume that the amount of elapsed time will be relatively
		// consistent
		// from frame to frame. We want the transition from current to the
		// direction of motion
		// to take about 5 chunks of time.

		// If there is no difference in orientation and direction set frames to
		// 5;

		if (Math.abs(character.theta - character.velocity.heading()) < .1)
			frames = 1;
		else {
			float oldTheta = character.theta;
			float newTheta = mapToRange(character.velocity.heading() - oldTheta);
			
			newTheta = mapToRange(newTheta / CHUNKS_OF_ROTATION) * frames;
			frames++;

			newTheta += oldTheta;
			character.setAngle(newTheta);
		}
		if (frames >= CHUNKS_OF_ROTATION)
			frames = 1;
	}

	/**
	 * mapToRange reduces a range of theta in radians to a range of pi to -pi.
	 * 
	 * @param theta
	 *            the theta to map to pi to -pi.
	 * @return the mapped theta.
	 */
	private float mapToRange(float theta) {

		//This makes movement pretty....
		// If we are in the range of pi and -pi return what we have.
		if (Math.abs(theta) < PI) {
			return theta;
		} else if (theta > PI)
			return theta - 2*PI;
		else if (theta <= -PI)
			return theta + 2*PI;

		return 0;
	}

	public void updateOrientationByValue(Sprite character, int i) {
		// TODO Auto-generated method stub
		if (Math.abs(character.theta - character.velocity.heading()) < .2)
			frames = 1;
		else {
			float oldTheta = character.theta;
			float newTheta = mapToRange(character.velocity.heading() - oldTheta);
			
			newTheta = mapToRange(newTheta / i) * frames;
			frames++;

			newTheta += oldTheta;
			character.setAngle(newTheta);
		}
		if (frames >= i)
			frames = 1;
	}
}
