package ai;

import all_together.All_Together_Control;
import motion.Character;
import motion.Sprite;

/**
 * AI provides an object that can modify a Character to perform certain
 * functionality required by the game environment.
 * 
 * @author James Woods
 * 
 */
public class AI {

	// MAX_ACCEL is the maximum acceleration for steering movement.
	protected static final float MAX_ACCEL = .5f;

	protected Seek seek;
	private Orient orienter;
	private Wander_Two wander;

	private Seek_One closeSeek;


	public AI() {
		closeSeek = new Seek_One();
		wander = new Wander_Two(All_Together_Control.MAX_Z,
				All_Together_Control.MAX_X, new Character(null, "",
						All_Together_Control.MAX_Z / 2,
						All_Together_Control.MAX_X / 2, 2), this);
		orienter = new Orient();

	}

	/**
	 * setAI allows the Seek algorithm to be switched out.
	 * 
	 * @param seek
	 *            the new Seek algorithm.
	 */
	public void setAI(Seek seek) {
		this.seek = seek;
	}

	/**
	 * update changes the position of the sprite given the current state of the
	 * game.
	 * 
	 * @param sprite
	 *            the sprite to update.
	 * @param target
	 *            the target of the sprite.
	 */
	public void update(Sprite sprite, Character target, float time) {

		wander.wander(sprite, time);
//		 seek.seek(sprite, target, time);
//		 if(!sprite.arrived)
//		 orienter.updateOrientation(sprite);
	}

	public void newPath(Sprite sprite, Character character, float time) {
		((Path_Seek)seek).newPath(sprite, character, time);
	}

	public void seek(Sprite sprite, Character character, float time) {
		seek.seek(sprite, character, time);
	}

	public void persueSeek(Sprite sprite, Character character, float time) {
		closeSeek.seek(sprite, character, time);
	}
	
	public void orientByValue(Sprite sprite) {
		orienter.updateOrientationByValue(sprite, 3);
	}
	
	public void orient(Sprite sprite) {
		orienter.updateOrientation(sprite);
	}

	public void wander(Sprite sprite, float time) {
		wander.wander(sprite, time);
	}

}
