package all_together;

import java.util.ArrayList;

import motion.Character;
import motion.Sprite;
import processing.core.PApplet;
import structure.Drawable;
import structure.Level;
import ai.AI;
import ai.BehaviorTreeAI;
import ai.DecisionTreeAI;
import ai.Seek;
import control.Controller;

/**
 * Creates a controller that will handle display and processing of 
 * user interactions into the underlying model.
 * @author James Woods
 *
 */
public class All_Together_Control  extends PApplet implements Controller {
	
	/**
	 * Default ID.
	 */
	private static final long serialVersionUID = 1L;
	

	// The current level for the game.
	private Level current;
	// The limit of the screen in the x direction.
	public static final int MAX_X = 800;
	// The limit of the screen in the z direction.
	public static final int MAX_Z = 1000;
	// target is the target of seek.
	public Character target;
	// ai is the AI object used to control Characters in the game.
	public AI ai;
	// action contains the whether an action needs to be performed or not.
	private boolean action;
	// frames tracks the number of frames that have been rendered since
	// the last spot has been made.
	private int frames;

	private float time;
	private double actual;
	private double prevActual;
	
	/**
	 * setup creates initializes all the data needed to create the game
	 * environment.
	 */
	public void setup() {
		// Set up the control.
		action = false;
		current = (Level) new All_Together_Intro(this, MAX_Z, MAX_X);
		current.setup(this);
		target = new Character(this, "turtle.png", MAX_Z / 2, MAX_X / 2, 1);
		ai = new DecisionTreeAI(this);
		frames = 0;

		// Set up the viewer.
		size(MAX_Z, MAX_X);
		frameRate(60);
		
		time = 0;
		actual = 0;
		prevActual = System.currentTimeMillis();
		
		
	}

	/**
	 * draw runs the animation loop, and provides data to the current Level to
	 * update the visible elements.
	 */
	public void draw() {

		if (current.hasNextFrame()) {
			actual = System.currentTimeMillis();
			time = (float)(actual - prevActual)/10;
			//Clear the screen
			this.clear();
			// Render the background.
			current.getBackGround().render();
			// Get the next frame of Drawable objects.
			ArrayList<Drawable> sprites = current.getNextFrame();
			@SuppressWarnings("unchecked")
			ArrayList<Drawable> toUpdate = (ArrayList<Drawable>) sprites
					.clone();
			Drawable sprite;
			// Update and render each one.
			int last = toUpdate.size();
			for (int i = 0; i < last; i++) {
				sprite = toUpdate.get(i);
				if (sprite instanceof Sprite) {
					// Every ten frames create a spot where the character is.
//					if (frames == 10) {
//						sprites.add(new Spot(this, ((Sprite) sprite).z,
//								((Sprite) sprite).x));
//					}
					update((Sprite) sprite);
				}
				sprite.render();
			}
			if (frames > 10)
				frames = 0;
			else
				frames++;

			prevActual = System.currentTimeMillis();
		}

	}

	/**
	 * This is where the magic happens, thats right. We are making the AI for
	 * this project happen here.
	 * 
	 * @param sprite
	 *            the sprite to update.
	 */
	private void update(Sprite sprite) {
		ai.update(sprite, target, time);
	}

	/**
	 * mousePressed performs some action when the mouse is pressed by the user.
	 */
	public void mousePressed() {
		if (mouseY < MAX_X && mouseX < MAX_Z) {
			action = true;
			target = new Character(this, "", mouseX, mouseY, 1);
		}
	}

	/**
	 * setCurrent sets the current level and allows the current level to set a
	 * new level if needed.
	 * 
	 * @param current
	 *            the level to be set as current.
	 */
	public void setCurrent(Level current) {
		this.current = current;
	}

	/**
	 * hasAction allows clients of ControllerView to know if an action has been
	 * logged.
	 * 
	 * @return true if an action was logged false if not.
	 */
	public boolean hasAction() {
		return action;
	}
	
	/**
	 * setSeek allows clients of ControllerViewer to set 
	 * the seek algorithm used by the AI.
	 * @param seek
	 */
	public void setSeek(Seek seek) {
		ai.setAI(seek);
	}
	
	@Override
	public void setTarget(Character character) {
		this.target = character;
	}

	@Override
	public int getTargetZ() {
		return target.z;
	}

	/**
	 * reset action lets the controller know
	 * that an action has been resolved by setting
	 * action to false.
	 */
	public void resetAction() {
		action = false;
	}
}
