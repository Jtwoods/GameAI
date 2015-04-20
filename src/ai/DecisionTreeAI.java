package ai;

import java.util.ArrayList;
import java.util.Random;

import motion.Character;
import motion.Sprite;
import processing.core.PVector;
import tree.Behavior_Tree;
import tree.Decision_Node;
import tree.Node;
import tree_factory.BehaviorTreeFactory;
import tree_factory.DecisionTreeFactory;
import all_together.All_Together_Control;
import characters.Bonzo;
import characters.Monster;

public class DecisionTreeAI extends AI {

	private static final float DIAMETER = 50;
	private All_Together_Control control;
	public Bonzo bonzo;
	public Character bonzoTarget;
	public Monster monster;
	private Decision_Node bonzoDecides;
	public int bonzoTimer;
	private ArrayList<ArrayList<Character>> roomPositions;
	private Random rand;
	private Node monsterBehaves;

	/**
	 * Creates a new All_Together_AI with default values.
	 * 
	 * @param control
	 */
	public DecisionTreeAI(All_Together_Control control) {
		super();
		this.control = control;
		rand = new Random(System.currentTimeMillis());
		DecisionTreeFactory fact = new DecisionTreeFactory(this, control);
		BehaviorTreeFactory monFact = new BehaviorTreeFactory(this, control);
		bonzoDecides = fact.getDecisionTree();
		monsterBehaves = (Node)monFact.getBehaviorTree();

		bonzoTimer = 0;
		this.roomPositions = new ArrayList<ArrayList<Character>>();

		ArrayList<Character> positions_zero = new ArrayList<Character>();
		positions_zero.add(new Character(null, "", 120, 120, 0));
		positions_zero.add(new Character(null, "", 120, 360, 0));
		positions_zero.add(new Character(null, "", 120, 555, 0));

		roomPositions.add(positions_zero);

		ArrayList<Character> positions_one = new ArrayList<Character>();
		positions_one.add(new Character(null, "", 355, 120, 1));
		positions_one.add(new Character(null, "", 500, 400, 1));
		positions_one.add(new Character(null, "", 570, 120, 1));

		roomPositions.add(positions_one);

		ArrayList<Character> positions_two = new ArrayList<Character>();
		positions_two.add(new Character(null, "", 790, 200, 2));

		roomPositions.add(positions_two);

		ArrayList<Character> positions_three = new ArrayList<Character>();
		positions_three.add(new Character(null, "", 875, 585, 3));
		positions_three.add(new Character(null, "", 400, 590, 3));
		positions_three.add(new Character(null, "", 800, 590, 3));

		roomPositions.add(positions_three);
	}
	
	/**
	 * this setter just allows a different tree to be dropped into the
	 * ai for the monster.
	 * @param decision_Node the new tree
	 */
	public void setMonsterBehavior(Decision_Node decision_Node) {
		System.out.println("Setting learned behavior.");
		monsterBehaves = decision_Node;
	}

	/**
	 * update updates the character given the current target.
	 */
	public void update(Sprite sprite, Character target, float time) {

		if (sprite instanceof Bonzo) {
			bonzo = (Bonzo) sprite;
			((Path_Seek) super.seek).setGraph(bonzo.currentGraph);
			bonzoDecides.perform(bonzo, time);
		}
		if (sprite instanceof Monster) {
			monster = (Monster) sprite;
			((Path_Seek) super.seek).setGraph(monster.currentGraph);
			monsterBehaves.perform(monster, time);
		}
		if (monster != null && bonzo != null) {
			if (!collision(monster, bonzo)) {

				// super.update(sprite, target, time);
				// seek.seek(sprite, target, time);
				// if(!sprite.arrived)
				// orienter.updateOrientation(sprite);
			} else {
				monster.hasCollision = true;
				// monster.x = 550;
				// monster.z = 850;
				// bonzo.x = 110;
				// bonzo.z = 110;
				// bonzo.setRoom(0);
				// monster.setRoom(3);
				// monster.hasPath = false;
				// bonzo.hasPath = false;
				// bonzo.wander = true;
				// monster.currentGraph =
				// monster.getRooms().get(monster.getRoom());
				// bonzo.currentGraph = bonzo.getRooms().get(bonzo.getRoom());
				// super.wander(monster, time);
				// super.wander(bonzo, time);
				// super.newPath(bonzo, bonzo.target, time);
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
		float sep = (float) Math.sqrt(zSep * zSep + xSep * xSep);

		if ((t >= 0 && t < .3 && minSeparation <= toCheck.radiusOfSatisfaction()) || sep <= toCheck.radiusOfSatisfaction()) {
			return true;
			
		} 
		
		return false;
	}

	public void wander(Sprite sprite, float time) {
		super.wander(sprite, time);
	}

	public Character getClosestLocation(Sprite sprite) {
		Character closest = null;
		float min = Float.MAX_VALUE;
		for (int i = 0; i < roomPositions.size(); i++) {
			if (i != sprite.getRoom()) {
				ArrayList<Character> points = roomPositions.get(i);
				for (Character toMin : points) {
					int x = toMin.x - sprite.x;
					int z = toMin.z - sprite.z;
					float toCheck = (float) Math.sqrt(x * x + z * z);
					if (toCheck < min) {
						min = toCheck;
						closest = toMin;
					}
				}
			}
		}
		return closest;
	}

	public Character getCenterLocation(Sprite sprite) {
		Character furthest = roomPositions.get(sprite.getRoom()).get(0);
		int xInit = furthest.x - sprite.x;
		int zInit = furthest.z - sprite.z;
		float max = (float) Math.sqrt(xInit * xInit + zInit * zInit);

		for (Character toMax : roomPositions.get(sprite.getRoom())) {
			int x = toMax.x - sprite.x;
			int z = toMax.z - sprite.z;
			float toCheck = (float) Math.sqrt(x * x + z * z);
			if (toCheck > max) {
				max = toCheck;
				furthest = toMax;
			}
		}
		return furthest;
	}

	public boolean getRandom(Sprite sprite) {
		if (rand.nextInt(10000) > 9980)
			return true;
		return false;
	}

	public float getDistance() {
		float toReturn = Float.MAX_VALUE;
		if (bonzo != null && monster != null) {
			int x = bonzo.x - monster.x;
			int z = bonzo.z - monster.z;
			toReturn = (float) Math.sqrt(x * x + z * z);
		}
		return toReturn;
	}

	public Character getBonzoNext(Sprite sprite, float time) {
		if (bonzo != null) {
			float x = bonzo.x - monster.x;
			float z = bonzo.z - monster.z;
			float dist = (float) Math.sqrt(x * x + z * z);
			float direction = Math.abs(monster.velocity.heading() - bonzo.velocity.heading());
			if (dist < monster.radiusOfDeceleration() && direction > Math.PI/4) {
				x = bonzo.x + bonzo.velocity.y * time;
				z = bonzo.z + bonzo.velocity.x * time;
			} else {
				x = bonzo.x;
				z = bonzo.z;
			}
			return new Character(null, "", (int) z, (int) x, bonzo.getRoom());
		}
		return new Character(null, "", sprite.z, sprite.x, sprite.getRoom());
	}

	public Character getRoomNotBonzo(Sprite sprite) {
		if (bonzo != null) {
			return getClosestLocation(bonzo);
		}

		return getClosestLocation(sprite);
	}

	public void setGrabbed(float time) {
		monster.resetCapture();
		bonzo.resetCapture();
		monster.x = 550;
		monster.z = 850;
		bonzo.x = 110;
		bonzo.z = 110;
		bonzo.arrived = false;
		bonzo.arriving = true;
		bonzo.hasPath = false;
		monster.arrived = false;
		monster.arriving = true;
		monster.hasPath = false;
		bonzo.setRoom(0);
		monster.setRoom(2);
		super.wander(monster, time);
		super.wander(bonzo, time);
		super.newPath(monster, monster.target, time);
		super.newPath(bonzo, bonzo.target, time);
	}

	public boolean getLOS() {
		if (bonzo != null && monster != null) {

			int room = monster.getRoom();
			int other = bonzo.getRoom();
			if (room == other)
				return true;

			if ((nearDoor1(room) && seenFromDoor1(room, other))
					|| (nearDoor3(room) && seenFromDoor3(room, other))
					|| (nearDoor2(room) && seenFromDoor2(room, other)))
				return true;

		}

		return false;
	}

	private boolean seenFromDoor2(int room, int other) {
		switch (room) {
		case 0: {
			if (other == 1)
				return true;
		}
			break;
		case 1: {
			if (other == 2)
				return true;
		}
			break;
		case 2: {
			if (other == 3 && bonzo.z > 800)
				return true;
		}
			break;
		case 3: {
			if ((other == 0 && bonzo.x > 700) || other == 1)
				return true;
		}
		}
		return false;
	}

	private boolean nearDoor2(int room) {
		if(room == 0 && monster.x < 300)
			return true;
		else if (room == 1 && monster.x < 350 && monster.z > 650)
			return true;
		else if (room == 2 && monster.z > 800)
			return true;
		else if (room == 3 && monster.z < 550 && monster.x > 700)
			return true;
		return false;
		
	}
	
	private boolean seenFromDoor3(int room, int other) {
		switch (room) {
		case 0: {
			return false;
		}
		case 1: {
			if ((other == 3 && bonzo.z < 450) || other == 0
					&& (bonzo.z > 200 && bonzo.x > 570))
				return true;
		}
			break;
		case 2: {
			return false;
		}
		case 3: {
			if (other == 1 )
				return true;
		}
		}
		return false;
	}

	private boolean nearDoor3(int room) {

		if (room == 1 && monster.x < 550 && monster.x < 650 && monster.z > 500)
			return true;
		else if (room == 3 && monster.x > 570)
			return true;
		return false;
	}

	private boolean seenFromDoor1(int room, int other) {
		switch (room) {
		case 0: {
			if (other == 3 && bonzo.x > 560)
				return true;
		}
			break;
		case 1: {
			if (other == 0 && bonzo.x < 450)
				return true;
		}
			break;
		case 2: {
			if (other == 1 && bonzo.z < 400)
				return true;
		}
			break;
		case 3: {
			if (other == 2)
				return true;
		}
		}
		return false;
	}

	private boolean nearDoor1(int room) {
		if(room == 0 && monster.x > 560)
			return true;
		else if (room == 1 && monster.x < 350 && monster.z < 650)
			return true;
		else if (room == 2 && monster.x < 300 && monster.z < 650)
			return true;
		else if (room == 3 && monster.x < 500 && monster.z > 650)
			return true;
		return false;
	}

	public Character getRandomRoom(Sprite sprite) {
		int randomChoice = rand.nextInt(4);
		while (randomChoice == sprite.getRoom())
			randomChoice = rand.nextInt(4);

		return this.roomPositions.get(randomChoice).get(0);
	}

}
