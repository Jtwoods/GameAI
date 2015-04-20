package tree;

import java.util.ArrayList;
import java.util.Random;

import ai.AI;
import control.Controller;
import motion.Sprite;

public class RandomSelector extends Composite {

	private Random rand;
	
	public RandomSelector(AI ai, Controller controller, ArrayList<B_Node> toDo) {
		super(ai, controller, toDo);
		rand = new Random(System.currentTimeMillis());
		reset();
	}

	@Override
	public void perform(Sprite sprite, float time) {
		
		if (current.working) {
			current.perform(sprite, time);
		} else {
			current.returnValue();
			current.reset();
			if(returned) {
				working = false;
			}
			else {
				if(toDo.size() > 0) {
					int index = rand.nextInt(toDo.size());
					current = toDo.remove(index);
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
		int index = rand.nextInt(toDo.size());
		current = toDo.remove(index);
		complete.add(current);
	}

}
