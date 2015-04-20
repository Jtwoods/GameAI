package tree_factory;


import java.util.ArrayList;

import tree.Action;
import tree.B_Action;
import tree.B_Comparison;
import tree.B_Node;
import tree.Behavior_Tree;
import tree.Comparison;
import tree.Composite;
import tree.Decorator;
import tree.Interupt;
import tree.RandomSelector;
import tree.Selector;
import tree.Sequence;
import ai.DecisionTreeAI;
import all_together.All_Together_Control;

public class BehaviorTreeFactory extends DecisionTreeFactory {

	public BehaviorTreeFactory(DecisionTreeAI decisionTreeAI,
			All_Together_Control control) {
		super(decisionTreeAI, control);
		// TODO Auto-generated constructor stub
	}

	public Behavior_Tree getBehaviorTree() {
		//Build from the bottom up.
		ArrayList<B_Node> behave = new ArrayList<B_Node>();
		
		
		
		//Build the search for Bonzo selector.
		Comparison check = new PauseTimer(super.ai);
		Action action = new SpinAction(super.ai);
		
		B_Action check_Bonzo = new B_Action(super.ai, super.control, null, action, check, true, false);
		
		check = new Arrived(super.ai);
		//action = new SeekAction(super.ai);
		
		action = new MoveCenterAction(super.ai);
		
		B_Action move_across_room = new B_Action(super.ai, super.control, null, action, check, true, false);
		
		check = new Arrived(super.ai);
		action = new MoveRandomAction(super.ai);	
		
		B_Action move_next_room = new B_Action(super.ai, super.control, null, action, check, true, false);
		
		B_Comparison look = new B_Comparison(super.ai, super.control, new EnemyInSight(super.ai));
		
		ArrayList<B_Node> search = new ArrayList<B_Node>();
		search.add(check_Bonzo);
		search.add(look);
		search.add(move_across_room);
		search.add(check_Bonzo);
		search.add(look);
		search.add(move_next_room);
		
		//Add the left branch to the array.
		behave.add(new Selector(super.ai, super.control, search));
		
		//Build the catch branch.
		check = new PauseTimer(super.ai);
		action = new NoAction();
		
		B_Action wait = new B_Action(ai, control, new NoAction() ,action, check, true, false);
		
		check = new Arrived(super.ai);
		action = new NextRoomAction(super.ai); 
//		action = new SeekAction(super.ai);
		
		B_Action getCloser = new B_Action(ai, control, action, check, true);
		
		ArrayList<B_Node> getClose = new ArrayList<B_Node>();
		
		getClose.add(getCloser);
		getClose.add(wait);
		
		RandomSelector randomSelect = new RandomSelector(ai, control, getClose);
		
//		Comparison far = new EnemyFar(ai);
		
//		Decorator sneek = new Decorator(ai, control, far, randomSelect, true);
		
		Comparison can = new CanRun(ai);
		
		B_Comparison can_run = new B_Comparison(ai, control, can);
		
		
		Comparison collided = new Collision(ai);
		Action persue = new PersueAction(ai);
		
		B_Action running = new B_Action(ai, control, persue, collided, true);
		
		
		
		behave.add(randomSelect);
		behave.add(can_run);
		behave.add(running);
		
		Comparison same_room = new EnemyFar(ai);
		
		Interupt interupt = new Interupt(ai, control, new Sequence(ai, control, behave), same_room, true);
		
		behave = new ArrayList<B_Node>();
		
		running = new B_Action(ai, control, new NoAction(), persue, collided, true, true);
		
		action = new BloodAndGuts(ai);
		check = new PauseTimer(ai);
		
		B_Action success = new B_Action(ai, control, new NoAction(), action, check, true, true);
		
		behave.add(interupt);
		behave.add(running);
		behave.add(success);
	
		
		return new Behavior_Tree(super.ai, super.control, new Sequence(super.ai, super.control, behave));
	}

}
