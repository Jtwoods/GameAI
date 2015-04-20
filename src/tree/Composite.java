package tree;

import java.util.ArrayList;

import ai.AI;
import control.Controller;
import motion.Sprite;

public abstract class Composite extends B_Node {

	protected ArrayList<B_Node> toDo;
	protected ArrayList<B_Node> complete;
	protected B_Node current;
	
	public Composite(AI ai, Controller controller, ArrayList<B_Node> toDo) {
		super(ai, controller);
		this.toDo = toDo;
		complete = new ArrayList<B_Node>();
		for(B_Node n: toDo)
			n.setParent(this);
		current = toDo.remove(0);
		complete.add(current);
		returned = false;
		working = true;
		
		
	}


	@Override
	public abstract void perform(Sprite sprite, float time);
	
	
}
