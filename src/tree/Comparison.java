package tree;

import motion.Sprite;

/**
 * Comparison can hold logical information
 * that allows it to make comparisons between states 
 * of the game environment and return true or false 
 * based on this knowledge.
 * @author James Woods
 *
 */
public abstract class Comparison {

	public abstract boolean make(Sprite sprite);

}
