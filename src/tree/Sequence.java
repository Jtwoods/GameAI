package tree;

import java.util.ArrayList;

import ai.AI;
import control.Controller;
import motion.Sprite;

public class Sequence extends Composite {

	public Sequence(AI ai, Controller controller, ArrayList<B_Node> toDo) {
		super(ai, controller, toDo);
	}

	@Override
	public void perform(Sprite sprite, float time) {
		
		if (current.working) {
			current.perform(sprite, time);
		} else {
			current.returnValue();
			current.reset();
			if(!returned) {
				working = false;
			}
			else {
				if(toDo.size() > 0) {
					current = toDo.remove(0);
					complete.add(current);
				} else {
					returned = true;
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
