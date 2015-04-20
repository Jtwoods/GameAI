package tree;

import characters.Monster;
import ai.AI;
import control.Controller;
import motion.Sprite;

public class B_Action extends B_Node {

	private Action toPerform;
	private Comparison toReturn;
	private boolean compareType;
	private Action preAction;
	private int count;
	private boolean onSuccess;
	private boolean single;

	public B_Action(AI ai, Controller controller, Action toPerform,
			Comparison toReturn, boolean compareType) {
		super(ai, controller);
		this.toPerform = toPerform;
		this.toReturn = toReturn;
		this.compareType = compareType;
		single = false;
		this.preAction = null;
		onSuccess = true;
		count = 0;
	}

	public B_Action(AI ai, Controller controller, Action preAction,
			Action toPerform, Comparison toReturn, boolean compareType,
			boolean returnOnSuccess) {
		super(ai, controller);
		this.toPerform = toPerform;
		this.toReturn = toReturn;
		this.compareType = compareType;
		this.preAction = preAction;
		this.onSuccess = returnOnSuccess;
		single = true;
		count = 0;
	}

	@Override
	public void perform(Sprite sprite, float time) {

		if (count == 0 && preAction != null) {
			preAction.take(sprite, time);
			count++;
		} else {
			toPerform.take(sprite, time);
		}
		boolean theCheck = toReturn.make(sprite);
		if (this.compareType && theCheck) {
			working = false;
			if (single)
				returned = onSuccess;
			else
				returned = theCheck;
			count = 0;
		}

	}

	@Override
	public void reset() {
		working = true;
		returned = false;
		toPerform.resetPrintable();
		if (preAction != null)
			preAction.resetPrintable();
	}

}
