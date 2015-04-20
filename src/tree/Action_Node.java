package tree;

import motion.Sprite;
import ai.AI;
import ai.DecisionTreeAI;
import control.Controller;

/**
 * Action_Node is a Node that is a leaf in a decision 
 * tree hierarchy and performs an action.
 * @author James Woods
 *
 */
public class Action_Node extends Node {

	/**
	 * action is the action performed by this Action_Node.
	 */
	protected Action action;
	
	/**
	 * Constructs the Action_Node.
	 * @param ai the game ai.
	 * @param controller the game controller.
	 * @param action the action to perform.
	 */
	public Action_Node(DecisionTreeAI ai, Controller controller, Action action) {
		super(ai, controller);
		this.action = action;
	}

	@Override
	public void perform(Sprite sprite, float time) {
		action.take(sprite, time);
	}

}
