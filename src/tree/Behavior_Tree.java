package tree;

import motion.Sprite;
import ai.DecisionTreeAI;
import control.Controller;

public class Behavior_Tree extends Node {

	private Composite toDo;

	public Behavior_Tree(DecisionTreeAI ai, Controller controller,
			Composite toDo) {
		super(ai, controller);
		this.toDo = toDo;
	}

	@Override
	public void perform(Sprite sprite, float time) {

		if (toDo.working) {
			toDo.perform(sprite, time);
		} else {
		
				ai.monster.hasCollision = false;
				ai.setGrabbed(time);
				toDo.reset();
			
		}
	}

}
