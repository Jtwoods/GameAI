package tree;

import motion.Sprite;
import ai.AI;
import control.Controller;

public abstract class B_Node {
	
	/**
	 * ai is the ai for the game.
	 */
	protected AI ai;
	
	protected boolean returned;
	protected boolean working;
	
	public B_Node parent;
	
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
	public B_Node(AI ai, Controller controller) {
		this.ai = ai;
		this.controller = controller;
		working = true;
		returned = false;
	}
	
	/**
	 * perform performs the action of this node.
	 * This can be an action like seeking an enemy or
	 * can be a comparison.
	 */
	public abstract void perform(Sprite sprite, float time);
	
	public void returnValue() {
		parent.returned = this.returned;
	}

	public abstract void reset();

	public void setParent(B_Node composite) {
		this.parent = composite;
	}
	
}