package tree;

import ai.AI;
import control.Controller;
import motion.Sprite;

public class Decorator extends B_Node {

	private Comparison toMake;
	private B_Node toDo;
	private boolean compareType;
	
	public Decorator(AI ai, Controller controller, Comparison toMake,
			B_Node toDo, boolean compareType) {
		super(ai, controller);
		this.toMake = toMake;
		this.toDo = toDo;
		this.compareType = compareType;
		toDo.setParent(this);
	}

	@Override
	public void perform(Sprite sprite, float time) {
		
		if (toDo.working) {
			toDo.perform(sprite, time);
		} else if (toMake.make(sprite) && this.compareType) {
			working = false;
			toDo.returnValue();
			toDo.reset();
		} else {
			toDo.working = true;
			toDo.reset();
		}
	}

	@Override
	public void reset() {
		this.working = true;
		this.returned = false;
	}

}
