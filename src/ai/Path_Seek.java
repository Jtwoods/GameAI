package ai;

import characters.Bonzo;
import characters.Monster;
import path_finding.Search;
import path_following.Graph;
import path_following.Path;
import path_following.Position;
import path_following.Vertex;
import processing.core.PVector;
import motion.Character;
import motion.Sprite;

public class Path_Seek extends Seek_One {

	private Graph graph;
	private Path path;
	private Search search;
	private Path bonzoPath;
	private Path monsterPath;

	/**
	 * Path_Seek creates an empty Path_Seek object. The object must be
	 * initialized by setting the graph with the setGraph method before being
	 * used.
	 * 
	 * @param distance
	 *            the distance between nodes in the graph.
	 */
	public Path_Seek() {
		graph = null;
		path = null;
		bonzoPath = null;
		monsterPath = null;
		search = null;
	}

	/**
	 * setGraph allows clients of Path_Seek to set the graph used by the path
	 * following and finding algorithms.
	 * 
	 * @param graph
	 *            the level graph.
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * seek is a static method that allows the given character to seek out the
	 * target position.
	 * 
	 * @param sprite
	 *            the character that will seek.
	 * @param target
	 *            the target to be sought out.
	 * @param time
	 *            is the elapsed time since the last update.
	 */
	public void seek(Sprite sprite, Character target, float time) {

		setGraph(sprite.currentGraph);
		
		if (sprite instanceof Bonzo)
			this.path = bonzoPath;
		if (sprite instanceof Monster)
			this.path = monsterPath;

		

		
		
		if (path == null || !sprite.hasPath) {
		

			Vertex start = this.graph.getVertexNear(sprite.z, sprite.x,
					(int) sprite.radiusOfDeceleration());
			Vertex end = this.graph.getVertexNear(target.z, target.x,
					(int) sprite.radiusOfDeceleration());
			if (start != null && end != null) {
				this.path = this.search.findPath(start, end, this.graph);
				sprite.hasPath = true;
				sprite.arrived = false;
				path.index = 0;
			}
		} else if (sprite.arrived && path.index == this.path.size() - 1) {

			sprite.hasPath = false;
		} else if (sprite.arriving && path.index < this.path.size() - 1) {
			Position currentGuess = new Position(
					(int) (sprite.z + sprite.velocity.x * time),
					(int) (sprite.x + sprite.velocity.y * time));
			path.index = this.path.getIndex(path.index, currentGuess);
			Position next = this.path.getPosition(path.index);
			path.toSeek = new Character(null, "", next.getZ(), next.getX(), sprite.getRoom());
			super.seek(sprite, path.toSeek, time);
		} else if (path.toSeek != null) {

			super.seek(sprite, path.toSeek, time);
		}

		if (sprite instanceof Bonzo)
			bonzoPath = this.path;
		if (sprite instanceof Monster)
			monsterPath = this.path;

	}

	/**
	 * setSearch allows the search algorithm to be changed during operation.
	 * 
	 * @param search
	 *            the new search algorithm.
	 */
	public void setSearch(Search search) {
		this.search = search;

	}

	public void newPath(Sprite sprite, Character target, float time) {

		setGraph(sprite.currentGraph);

		
		if (sprite instanceof Bonzo)
			this.path = bonzoPath;
		if (sprite instanceof Monster)
			this.path = monsterPath;

		Vertex start = this.graph.getVertexNear(sprite.z, sprite.x,
				(int) sprite.radiusOfDeceleration());
		Vertex end = this.graph.getVertexNear(target.z, target.x,
				(int) sprite.radiusOfDeceleration());
		if (start != null && end != null) {
			this.path = this.search.findPath(start, end, this.graph);
			sprite.hasPath = true;
			path.index = 0;
			sprite.arriving = false;
			sprite.arrived = false;

			Position currentGuess = new Position(
					(int) (sprite.z + sprite.velocity.x * time),
					(int) (sprite.x + sprite.velocity.y * time));
			path.index = this.path.getIndex(path.index, currentGuess);
			if (path.index < path.size()) {
				Position next = this.path.getPosition(path.index);
				path.toSeek = new Character(null, "", next.getZ(), next.getX(), sprite.getRoom());
			}
		}

		if (sprite instanceof Bonzo)
			bonzoPath = this.path;
		if (sprite instanceof Monster)
			monsterPath = this.path;
	}

}
