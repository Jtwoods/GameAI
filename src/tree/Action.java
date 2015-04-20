package tree;

import motion.Sprite;

/**
 * Action performs some action that can change the game
 * environment either directly or indirectly.
 * @author James Woods
 *
 */
public abstract class Action {

	private boolean printable;
	
	public Action() {
		printable = true;
	}
	
	public abstract void take(Sprite sprite, float time);
	
	public void resetPrintable() {
		printable = true;
	}
	
	public boolean isPrintable() {
		return printable;
	}
	
	public void printing() {
		printable = false;
	}

}
