package tree;

import java.util.ArrayList;

import motion.Sprite;
import ai.AI;
import control.Controller;

public class Selector extends Composite {

	public Selector(AI ai, Controller controller, ArrayList<B_Node> toDo) {
		super(ai, controller, toDo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform(Sprite sprite, float time) {

		if (current.working) {
			current.perform(sprite, time);
		} else {
			current.returnValue();
			current.reset();
			if (returned) {
				working = false;
			} else {
				if (toDo.size() > 0) {
					current = toDo.remove(0);
					complete.add(current);
				} else {
					returned = false;
					working = false;
				}
			}
		}
	}

	public void reset() {
		current.reset();
		toDo.addAll(complete);
		complete = new ArrayList<B_Node>();
		working = true;
		returned = false;
		current = toDo.remove(0);
		complete.add(current);
	}

}
