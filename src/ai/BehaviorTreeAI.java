package ai;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PVector;
import tree.Decision_Node;
import tree_factory.DecisionTreeFactory;
import characters.Bonzo;
import characters.Monster;
import all_together.All_Together_Control;
import motion.Character;
import motion.Sprite;

/**
 * Supports the functionality described in homework two part four.
 * 
 * @author James Woods
 * 
 */
public class BehaviorTreeAI extends DecisionTreeAI {

	private static final float DIAMETER = 50;
	private All_Together_Control control;
	public Bonzo bonzo;
	public Character bonzoTarget;
	public Monster monster;
	private Decision_Node bonzoDecides;
	public int bonzoTimer;
	private ArrayList<ArrayList<Character>> roomPositions;
	private Random rand;

	/**
	 * Creates a new All_Together_AI with default values.
	 * 
	 * @param control
	 */
	public BehaviorTreeAI(All_Together_Control control) {
		super(control);

	}
	
	/**
	 * update updates the character given the current target.
	 */
	public void update(Sprite sprite, Character target, float time) {

		if (sprite instanceof Bonzo) {
			super.update(sprite, target, time);
		}
		if (sprite instanceof Monster) {
			monster = (Monster) sprite;
		}
		if (monster != null && bonzo != null) {
			if (!collision(monster, bonzo)) {
				
				super.update(sprite, target, time);
				// seek.seek(sprite, target, time);
				// if(!sprite.arrived)
				// orienter.updateOrientation(sprite);
			} else {
				monster.setCapture();
				monster.x = 550;
				monster.z = 850;
				bonzo.x = 110;
				bonzo.z = 110;
				super.newPath(monster, control.target, time);
				super.newPath(bonzo, control.target, time);
			}
		}
		//
		// if (control.mousePressed) {
		// super.newPath(sprite, control.target, time);
		// } else
		// super.update(sprite, target, time);

	}

	public boolean collision(Sprite toCheck, Sprite other) {
		PVector dp = new PVector(other.z - toCheck.z, other.x - toCheck.x);
		PVector dv = new PVector();
		dv.add(other.velocity);
		dv.sub(toCheck.velocity);

		float t = -((dp.dot(dv)) / dv.magSq());

		// Check for collision.

		float minSeparation = dp.mag() - dv.mag() * t;
		
		float xSep = dp.y;
		float zSep = dp.x;
		float sep = (float)Math.sqrt(zSep*zSep + xSep*xSep);

		if ((t >= 0 && t < .3 && minSeparation < DIAMETER) || sep < DIAMETER)
			return true;
		
		return false;
	}
	
}
