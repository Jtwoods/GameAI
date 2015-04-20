package tree;

import motion.Sprite;
import ai.AI;
import control.Controller;

public class Interupt extends B_Node {

	private Comparison toMake;
	private B_Node toDo;
	private boolean compareType;

	public Interupt(AI ai, Controller controller, B_Node toDo,
			Comparison toMake, boolean compareType) {
		super(ai, controller);
		this.toMake = toMake;
		this.toDo = toDo;
		this.compareType = compareType;
		toDo.setParent(this);
	}

	@Override
	public void perform(Sprite sprite, float time) {
		
		if (toMake.make(sprite) && this.compareType) {
			working = false;
			super.returned = true;
			toDo.reset();
		} else if (toDo.working) {
			toDo.perform(sprite, time);
		} else {
			toDo.reset();
		}
	}

	@Override
	public void reset() {
		this.working = true;
		this.returned = false;
	}

}
