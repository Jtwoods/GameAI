package tree;

import motion.Sprite;
import ai.AI;
import ai.DecisionTreeAI;
import control.Controller;

/**
 * Decision_Node is a Node in a Decision tree
 * structure that uses a Comparison object to 
 * perform some check and moves to other nodes in the tree.
 * @author James Woods
 *
 */
public class Decision_Node extends Node {

	private Comparison compare;
	private Node truth;
	private Node falsity;
	
	public Decision_Node(DecisionTreeAI ai, Controller controller, Comparison compare, Node t, Node f) {
		super(ai, controller);
		this.compare = compare;
		this.truth = t;
		this.falsity = f;
	}

	@Override
	public void perform(Sprite sprite, float time) {
		boolean result = compare.make(sprite);
		if(result)
			truth.perform(sprite, time);
		else
			falsity.perform(sprite, time);
	}

}
