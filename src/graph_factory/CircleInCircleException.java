package graph_factory;

/**
 * This is used to prevent inserting intersects for areas that are inside other areas.
 * @author James Woods
 *
 */
public class CircleInCircleException extends Exception {

	/**
	 * Default id.
	 */
	private static final long serialVersionUID = 1L;
	
	public CircleInCircleException() {
		super();
	}
}
