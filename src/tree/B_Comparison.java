package tree;

import ai.AI;
import control.Controller;
import motion.Sprite;

public class B_Comparison extends B_Node {

	private Comparison toMake;
	
	public B_Comparison(AI ai, Controller controller, Comparison toMake) {
		super(ai, controller);
		this.toMake = toMake;
	}

	@Override
	public void perform(Sprite sprite, float time) {
		returned = toMake.make(sprite);
		working = false;
	}

	@Override
	public void reset() {
		returned = false;
		working = true;
	}

}
