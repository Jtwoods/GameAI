package path_following;

import java.util.ArrayList;

import motion.Character;

/**
 * Path represents a path used by a game 
 * AI and is a list of vertices with indices.
 * @author James Woods
 *
 */
public class Path {
	
	private static final int MAX = 8;
	/**
	 * vertices is the ordered path of Vertex objects.
	 * Start is index 0 and Goal is at index vertices.size() - 1.
	 */
	ArrayList<Vertex> vertices;
	public int index;
	public Character toSeek;
	
	/**
	 * Path creates a new empty Path.
	 */
	public Path() {
		toSeek = null;
		index = 0;
		vertices = new ArrayList<Vertex>();
	}

	/**
	 * add appends the vertex to the end of the path.
	 * @param vertex to be added.
	 */
	public void add(Vertex vertex) {
		vertices.add(vertex);
	}

	/**
	 * getPosition gets the vertex at the given index.
	 * @param i the index.
	 * @return the Vertex at the index.
	 */
	public Position getPosition(int i) {
		return vertices.get(i).getPosition();
	}

	/**
	 * getIndex returns the index of the next node.
	 * @param index the current index, used for concurrence.
	 * @param position the current character position.
	 * @return the index of the next node on the path to move to.
	 */
	public int getIndex(int index, Position position) {
		
		int toReturn = index;
		int max = index + MAX;
		float closest = Float.MAX_VALUE;
		float distance = 0;
		
		//Move through the array starting at the given index.
		for(index++ ;index < vertices.size() && index < max; index++) {
			Vertex current = vertices.get(index);
			int x = current.getX() - position.getX();
			int z = current.getZ() - position.getZ();
			distance = (float) Math.sqrt((x*x) + (z*z));
			
			if(distance < closest) {
				closest = distance;
				toReturn = index;
			}
				
		}
		
		return toReturn;
	}
	
	/**
	 * size returns the size of the path (number of nodes).
	 * @return the size of the path.
	 */
	public int size() {
		return vertices.size();
	}
	
	
}
