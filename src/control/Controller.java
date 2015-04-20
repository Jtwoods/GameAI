package control;

import structure.Level;
import motion.Character;
import ai.Seek;

/**
 * Controller provides a handle to the game controller.
 * @author James Woods
 *
 */
public interface Controller{

	/**
	 * setSeek sets the seek algorithm used by the controller.
	 * @param seek the new seek algorithm.
	 */
	public void setSeek(Seek seek);

	/**
	 * setTarget is used to set a target location to be used by the seek 
	 * or path finding algorithm.
	 * @param character a Character at the new target location.
	 */
	public void setTarget(Character character);

	/**
	 * hasAction returns true if there is an action.
	 * @return true if there is an action false if not.
	 */
	public boolean hasAction();

	/**
	 * getTargetZ returns the z dimension of the current target.
	 * @return the current target's z location.
	 */
	public int getTargetZ();

	/**
	 * setCurrent sets the current level.
	 * @param next the Level to be set.
	 */
	public void setCurrent(Level next);


}
