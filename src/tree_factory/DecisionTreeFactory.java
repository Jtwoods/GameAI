package tree_factory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import processing.core.PVector;
import characters.Monster;
import motion.Sprite;
import ai.DecisionTreeAI;
import all_together.All_Together_Control;
import tree.Action;
import tree.Action_Node;
import tree.Behavior_Tree;
import tree.Comparison;
import tree.Decision_Node;

public class DecisionTreeFactory {

	public DecisionTreeAI ai;
	public All_Together_Control control;
	private ParametersWriter writer;
	private static final int STRIKING_DISTANCE = 90;
	private static final int PAUSE_TIME = 85;

	public DecisionTreeFactory(DecisionTreeAI decisionTreeAI,
			All_Together_Control control) {
		ai = decisionTreeAI;
		this.control = control;
		writer = new ParametersWriter("output.txt");
	}

	public Decision_Node getDecisionTree() {

		Decision_Node head = null;

		Decision_Node arrived = new Decision_Node(ai, control, new Arrived(ai),
				new Action_Node(ai, control, new SpinAction(ai)),
				new Action_Node(ai, control, new SeekAction(ai)));

		Decision_Node isWandering = new Decision_Node(ai, control,
				new IsWandering(ai), new Action_Node(ai, control,
						new WanderAction(ai)), arrived);

		Decision_Node roomTimer = new Decision_Node(ai, control, new RoomTimer(
				ai), new Action_Node(ai, control, new MoveCloseAction(ai)),
				isWandering);

		head = new Decision_Node(ai, control, new NearWalls(ai),
				new Action_Node(ai, control, new MoveCenterAction(ai)),
				roomTimer);

		return head;
	}

	class SeekAction extends Action {
		private DecisionTreeAI ai;

		public SeekAction(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("SeekAction", time, sprite);
			}
			ai.seek(sprite, sprite.target, time);
			ai.orient(sprite);
		}

	}

	class SpinAction extends Action {
		private DecisionTreeAI ai;
		private int count;
		private int timer;

		public SpinAction(DecisionTreeAI ai) {
			this.ai = ai;
			count = 0;
			timer = 0;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("SpinAction", time, sprite);
			}

			if (timer == 8) {
				if (count <= 4) {
					sprite.theta = (float) (sprite.theta + Math.PI / 8);
					count++;
				} else if (count <= 12) {
					sprite.theta = (float) (sprite.theta - Math.PI / 8);
					count++;
				} else if (count <= 16) {
					sprite.theta = (float) (sprite.theta + Math.PI / 8);
					count++;
				} else {
					sprite.walk();
					sprite.wander = true;
					sprite.timer = 0;
					count = 0;
					timer = 0;
				}
				timer = 0;
			} else {
				timer++;
			}
		}
	}

	class WanderAction extends Action {
		private DecisionTreeAI ai;

		public WanderAction(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("WanderAction", time, sprite);
			}
			sprite.currentGraph = sprite.getRooms().get(sprite.getRoom());
			ai.wander(sprite, time);
			sprite.timer += time;
		}

	}

	class MoveCloseAction extends Action {
		private DecisionTreeAI ai;
		private boolean toDo;

		public MoveCloseAction(DecisionTreeAI ai) {
			this.ai = ai;
			toDo = true;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("MoveCloseAction", time, sprite);
				sprite.wander = false;
				sprite.arrived = false;
			}
			if (!sprite.hasPath || toDo) {
				sprite.target = ai.getClosestLocation(sprite);
				sprite.currentGraph = sprite.getGraph();
				sprite.setRoom(sprite.target.getRoom());
				ai.newPath(sprite, sprite.target, time);
				toDo = false;
			}
			sprite.run();
			
			ai.seek(sprite, sprite.target, time);
			ai.orient(sprite);
			if(sprite.arrived)
				toDo = true;
			sprite.timer = 0;
		}
	}

	class MoveCenterAction extends Action {
		private DecisionTreeAI ai;
		private boolean toDo;

		public MoveCenterAction(DecisionTreeAI ai) {
			this.ai = ai;
			toDo = true;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("MoveCenterAction", time, sprite);
				sprite.wander = false;
				sprite.arrived = false;
			}
			if (!sprite.hasPath || toDo) {
				sprite.target = ai.getCenterLocation(sprite);
				sprite.setRoom(sprite.target.getRoom());
				toDo = false;
			}
			if (sprite.arrived)
				toDo = true;
			ai.seek(sprite, sprite.target, time);
			ai.orient(sprite);

		}

	}

	class PersueAction extends Action {
		private DecisionTreeAI ai;

		public PersueAction(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("PersueAction", time, sprite);
			}
			sprite.target = ai.getBonzoNext(sprite, time);
			sprite.currentGraph = sprite.getGraph();
			sprite.run();
			sprite.canRun = false;
			sprite.timer = 0;
			if (ai.getDistance() < sprite.radiusOfDeceleration()) {
				ai.persueSeek(sprite, sprite.target, time);
				int x = ai.bonzo.x - sprite.x;
				int z = ai.bonzo.z - sprite.z;

				PVector direction = new PVector(z, x);
				sprite.theta = (float) (direction.heading());
			} else {
				ai.seek(sprite, sprite.target, time);
				ai.orient(sprite);
			}

			sprite.walk();

		}

	}

	class BloodAndGuts extends Action {

		private DecisionTreeAI ai;
		private int count;
		private int timer;

		public BloodAndGuts(DecisionTreeAI ai) {
			this.ai = ai;
			count = 0;
			timer = 0;

		}

		public void take(Sprite sprite, float time) {

			ai.monster.setCapture();
			ai.bonzo.setCapture();
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("BloodAndGuts", time, sprite);
			}

			if (timer == 8) {
				if (count <= 4) {
					sprite.theta = (float) (sprite.theta + Math.PI / 8);
					count++;
				} else if (count <= 12) {
					sprite.theta = (float) (sprite.theta - Math.PI / 8);
					count++;
				} else if (count <= 16) {
					sprite.theta = (float) (sprite.theta + Math.PI / 8);
					count++;
				} else {
					sprite.walk();
					sprite.wander = true;
					sprite.timer = 0;
					count = 0;
					timer = 0;
				}
				timer = 0;
			} else {
				timer++;
			}
		}

	}

	/**
	 * moves the Monster to a room next to Bonzo that does not contain Bonzo.
	 * 
	 * @author James Woods
	 * 
	 */
	class NextRoomAction extends Action {

		private DecisionTreeAI ai;
		private boolean toDo;

		public NextRoomAction(DecisionTreeAI ai) {
			this.ai = ai;
			toDo = true;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("NextRoomAction", time, sprite);
				sprite.wander = false;
				sprite.arrived = false;
			}

			if (sprite.hasPath || toDo) {
				sprite.target = ai.getRoomNotBonzo(sprite);
				sprite.currentGraph = sprite.getGraph();
				sprite.run();
				sprite.setRoom(sprite.target.getRoom());
				toDo = false;
			}

			ai.seek(sprite, sprite.target, time);
			ai.orient(sprite);
			sprite.walk();

			if (sprite.arrived)
				toDo = true;

		}
	}

	/**
	 * resets the variables for the monster and bonzo placing them in their
	 * starting positions.
	 * 
	 * @author James Woods
	 * 
	 */
	class GrabEnemyAction extends Action {

		private DecisionTreeAI ai;

		public GrabEnemyAction(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("GrabEnemyAction", time, sprite);
			}
			ai.setGrabbed(time);
		}
	}

	/**
	 * returns true if the enemy is within striking distance of the monster.
	 * 
	 * @author James Woods
	 * 
	 */
	class EnemyFar extends Comparison {
		private DecisionTreeAI ai;

		public EnemyFar(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			return ai.getDistance() < STRIKING_DISTANCE;
		}
	}

	/**
	 * returns true when the enemy is within sight of the monster.
	 * 
	 * @author James Woods
	 * 
	 */
	class EnemyInSight extends Comparison {
		private DecisionTreeAI ai;

		public EnemyInSight(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			boolean toReturn = ai.getLOS();
			return toReturn;
		}
	}

	/**
	 * returns true when the sprite is running.
	 * 
	 * @author James Woods
	 * 
	 */
	class Running extends Comparison {

		private static final int RUNNING_SPEED = 3;

		public Running() {

		}

		@Override
		public boolean make(Sprite sprite) {

			return sprite.velocity.mag() > RUNNING_SPEED;
		}

	}

	/**
	 * returns true if the arriving variable is set.
	 * 
	 * @author James Woods
	 * 
	 */
	class Arriving extends Comparison {

		public Arriving() {

		}

		@Override
		public boolean make(Sprite sprite) {
			return sprite.arriving;
		}

	}

	/**
	 * returns true if the room variable for Bonzo and the Monster are the same.
	 * 
	 * @author James Woods
	 * 
	 */
	class InSameRoom extends Comparison {
		private DecisionTreeAI ai;

		public InSameRoom(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			if (ai.bonzo == null)
				return false;
			return ai.monster.getRoom() == ai.bonzo.getRoom();
		}

	}

	/**
	 * returns true when the monster has rested long enough to run again.
	 * 
	 * @author James Woods
	 * 
	 */
	class CanRun extends Comparison {
		private DecisionTreeAI ai;

		public CanRun(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			if (sprite.timer > 300) {
				sprite.canRun = true;
			}

			return sprite.canRun;
		}
	}

	class PauseTimer extends Comparison {

		private DecisionTreeAI ai;
		private int count;

		public PauseTimer(DecisionTreeAI ai) {
			this.ai = ai;
			count = 0;
		}

		@Override
		public boolean make(Sprite sprite) {
			if (sprite.pauseCount > PAUSE_TIME) {
				sprite.pauseCount = 0;
				return true;
			}

			if (sprite instanceof Monster && ai.getLOS()) {
				int x = ai.bonzo.x - sprite.x;
				int z = ai.bonzo.z - sprite.z;

				PVector direction = new PVector(z, x);
				sprite.theta = (float) (direction.heading());
			}

			sprite.pauseCount++;

			return false;
		}
	}

	class CollisionTimer extends PauseTimer {

		public CollisionTimer(DecisionTreeAI ai) {
			super(ai);
		}

		public boolean make(Sprite sprite) {
			boolean hasCollision = ai.collision(sprite, sprite.target);
			if (super.make(sprite) || hasCollision) {
				super.count = 0;
				return true;
			}
			return false;
		}

	}

	class Collision extends Comparison {

		private DecisionTreeAI ai;

		Collision(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			boolean colide = sprite.hasCollision;
			return colide;
		}

	}

	class NearWalls extends Comparison {
		private DecisionTreeAI ai;

		public NearWalls(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			boolean toReturn = ai.getRandom(sprite);
			return toReturn;
		}

	}

	class RoomTimer extends Comparison {

		private DecisionTreeAI ai;

		public RoomTimer(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			if (sprite.timer > 500) {
				sprite.timer = 0;
				return true;
			}

			return false;
		}

	}

	class IsWandering extends Comparison {

		private DecisionTreeAI ai;

		public IsWandering(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			return sprite.wander;
		}

	}

	class Arrived extends Comparison {

		private DecisionTreeAI ai;

		public Arrived(DecisionTreeAI ai) {
			this.ai = ai;
		}

		@Override
		public boolean make(Sprite sprite) {
			return !sprite.hasPath;
		}
	}

	class NoAction extends Action {

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("NoAction", time, sprite);
			}

			if (sprite instanceof Monster && ai.getLOS()) {
				int x = ai.bonzo.x - sprite.x;
				int z = ai.bonzo.z - sprite.z;

				PVector direction = new PVector(z, x);
				sprite.theta = (float) (direction.heading());
			}
		}

	}

	class MoveRandomAction extends Action {

		private DecisionTreeAI ai;
		private boolean toDo;

		public MoveRandomAction(DecisionTreeAI ai) {
			this.ai = ai;
			toDo = true;
		}

		@Override
		public void take(Sprite sprite, float time) {
			if (sprite instanceof Monster && super.isPrintable()) {
				super.printing();
				writer.printParameters("MoveRandomAction", time, sprite);
				sprite.wander = false;
				sprite.arrived = false;
			}
			if (!sprite.hasPath || toDo) {
				sprite.target = ai.getRandomRoom(sprite);
				sprite.currentGraph = sprite.getGraph();
				sprite.setRoom(sprite.target.getRoom());
				toDo = false;
			}
			// ai.newPath(sprite, sprite.target, time);
			ai.seek(sprite, sprite.target, time);
			ai.orient(sprite);
			if (sprite.arrived)
				toDo = true;
		}

	}

	/**
	 * ParameterWriter writes all parameters to a file.
	 * 
	 * @author James Woods
	 * 
	 */
	public class ParametersWriter {

		private static final String REGEX = ":";
		private File toWriteTo;
		private FileWriter toWrite;
		private BufferedWriter toBuffer;
		public float elapsed;
		public int seconds;

		/**
		 * Creates a parameter writer with the given file name.
		 * 
		 * @param fileName
		 *            the file to use.
		 * @param tree
		 *            the Behavior tree.
		 */
		public ParametersWriter(String fileName) {
			seconds = 0;
			elapsed = System.currentTimeMillis();
			toWriteTo = new File(fileName);

		}

		/**
		 * Prints the parameter data to an output file.
		 * 
		 * @param action
		 * @param time
		 * @param sprite
		 */
		public void printParameters(String action, float time, Sprite sprite) {
			if (sprite instanceof Monster) {

				String speed;
				String distance;

				if (ai.getDistance() < STRIKING_DISTANCE)
					distance = "true";
				else
					distance = "false";

				if (sprite.velocity.mag() > 3)
					speed = "true";
				else
					speed = "false";

				try {
					toWrite = new FileWriter(toWriteTo.getAbsolutePath(), true);
					toBuffer = new BufferedWriter(toWrite);
					toBuffer.write((sprite.getRoom() == ai.bonzo.getRoom())
							+ REGEX + sprite.arrived + REGEX
							+ sprite.hasCollision + REGEX + ai.monster.canRun
							+ REGEX + distance + REGEX + ai.getLOS() + REGEX
							+ (ai.monster.pauseCount > PAUSE_TIME) + REGEX
							+ action);

					toBuffer.newLine();
					toBuffer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
