package ai;
import motion.Sprite;

/**
 * Seek provides an interface that allows a seek
 * AI behavior to be changed easily.
 * @author James Woods
 *
 */
public interface Wander {
	
	/**
	 * wander makes the character move randomly while 
	 * staying inside the boundaries of the screen.
	 * 
	 * @param sprite
	 *            the character that will seek.
	 */
	public void wander(Sprite sprite, float time);

}
