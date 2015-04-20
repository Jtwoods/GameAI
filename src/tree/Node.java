package tree;

import motion.Sprite;
import ai.AI;
import ai.DecisionTreeAI;
import control.Controller;

/**
 * Node is a super class that provides basic
 * data storage for nodes in a decision tree and
 * a handle to these nodes.
 * @author James Woods
 *
 */
public abstract class Node {
	
	/**
	 * ai is the ai for the game.
	 */
	protected DecisionTreeAI ai;
	
	/**
	 * controller is the controller for the game.
	 */
	protected Controller controller;
	
	/**
	 * provides a default constructor that takes 
	 * the game ai and controller as parameters.
	 * @param ai
	 * @param controller
	 */
	public Node(DecisionTreeAI ai, Controller controller) {
		this.ai = ai;
		this.controller = controller;
	}
	
	/**
	 * perform performs the action of this node.
	 * This can be an action like seeking an enemy or
	 * can be a comparison.
	 */
	public abstract void perform(Sprite sprite, float time);
	
}
