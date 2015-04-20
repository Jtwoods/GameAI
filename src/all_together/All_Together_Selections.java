package all_together;

import processing.core.PApplet;
import processing.core.PFont;
import structure.Drawable;

public class All_Together_Selections implements Drawable {

	/**
	 * x and z are the z and x coordinates of the Selections.
	 */
	public int x;
	public int z;
	
	PFont font;
	PApplet graphic;
	
	public All_Together_Selections(PApplet graphic, int z, int x) {
		this.x = x;
		this.z = z;
		this.graphic = graphic;
		font = graphic.createFont("Arial", 16, true);
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public void render() {
		graphic.textFont(font);
		graphic.fill(0);
		graphic.textAlign(graphic.CENTER);
		graphic.text("Part 1                                                    Part 2                                                     Part 3", z, x);
		
	}


	@Override
	public void setGraphics(PApplet graphics) {
		this.graphic = graphics;
	}

}
