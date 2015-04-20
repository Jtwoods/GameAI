package structure;

import processing.core.PApplet;

/**
 * Drawable provides an update method that can 
 * be used by the graphics API to render an object.
 * @author James Woods
 *
 */
public interface Drawable {

	/**
	 * render renders the Drawable object using 
	 * the rules of the graphics API. 
	 */
	public void render();
	
	/**
	 * setGraphics allows the graphics object to be set outside the 
	 * constructor for this object when needed.
	 */
	public void setGraphics(PApplet graphics);

}
