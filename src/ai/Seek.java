package ai;
import motion.Character;
import motion.Sprite;

/**
 * Seek provides an interface that allows a seek
 * AI behavior to be changed easily.
 * @author James Woods
 *
 */
public interface Seek {
	
	/**
	 * seek is a static method that allows the given character to seek out the
	 * target.
	 * 
	 * @param sprite
	 *            the character that will seek.
	 * @param target
	 *            the target to be sought out.
	 */
	public void seek(Sprite sprite, Character target, float time);


}
