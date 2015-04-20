package all_together;

import processing.core.PApplet;
import processing.core.PImage;
import motion.BackGround;

public class All_Together_BackGround extends BackGround {

	private PImage room;
	
	public All_Together_BackGround(PApplet process) {
		super(process);
		room = super.process.loadImage("room.png");
	}
	
	@Override
	public void render() {
	
		super.process.image(room,0,0);
	}

}
