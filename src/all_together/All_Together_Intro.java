package all_together;

import java.io.IOException;
import java.util.ArrayList;

import ai.DecisionTreeAI;
import levels.BehaviorTree;
import levels.DecisionTree;
import motion.BackGround;
import control.Controller;
import path_finding.A_Star;
import path_finding.CompareEtc;
import path_finding.H_two;
import processing.core.PApplet;
import structure.Drawable;
import structure.Level;
import tree_factory.DecisionTreeLearning;

/**
 * A class that provides a simple interactive level introduction for homework
 * two "Putting it all Together"
 * 
 * @author James Woods
 * 
 */
public class All_Together_Intro extends Level {

	/**
	 * All_Together_Intro creates a Level with the given parameters.
	 * 
	 * @param view
	 *            the Controller
	 * @param maxZ
	 *            the maximum horizontal size of the screen.
	 * @param maxX
	 *            the maximum vertical size of the screen.
	 */
	public All_Together_Intro(Controller view, int maxZ, int maxX) {
		super(view, maxZ, maxX);
	}

	PApplet graphix;

	@Override
	public void setup(PApplet graphix) {
		this.graphix = graphix;
		super.running = true;
		super.background = new BackGround(graphix);
		super.sprites.add(new All_Together_Selections(graphix, maxZ / 2,
				maxX / 2));
	}

	@Override
	public boolean hasNextFrame() {

		if (super.view.hasAction()) {
			int z = graphix.mouseX;
			if (z > 2 * maxZ / 3) {
				DecisionTreeLearning learner = new DecisionTreeLearning("output.txt", (DecisionTreeAI)((All_Together_Control)view).ai, (All_Together_Control)super.view);
				try {
					((DecisionTreeAI)((All_Together_Control)view).ai).setMonsterBehavior(learner.learnTree());
				} catch (IOException e) {
					e.printStackTrace();
				} 
				A_Star search = new A_Star();
				search.setHeuristic(new H_two());
				search.setComparator(new CompareEtc());
				Level next = new BehaviorTree(super.view, maxZ, maxX,
						search);
				next.setup((PApplet) super.view);
				super.view.setCurrent(next);
				((All_Together_Control) super.view).resetAction();
				return next.hasNextFrame();
			} else if (z > maxZ / 3) {
				A_Star search = new A_Star();
				search.setHeuristic(new H_two());
				search.setComparator(new CompareEtc());
				Level next = new BehaviorTree(super.view, maxZ, maxX,
						search);
				next.setup((PApplet) super.view);
				super.view.setCurrent(next);
				((All_Together_Control) super.view).resetAction();
				return next.hasNextFrame();
			} else {
				A_Star search = new A_Star();
				search.setHeuristic(new H_two());
				search.setComparator(new CompareEtc());
				Level next = new DecisionTree(super.view, maxZ, maxX, search);
				next.setup((PApplet) super.view);
				super.view.setCurrent(next);
				((All_Together_Control)super.view).resetAction();
				return next.hasNextFrame();
			}
		} else {
			return super.hasNextFrame();
		}
	}

	@Override
	public ArrayList<Drawable> getNextFrame() {
		return super.sprites;
	}

	@Override
	public Drawable getBackGround() {
		return super.getBackGround();
	}

}
