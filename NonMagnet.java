package zoe.montecarlo.simulation;
import processing.core.PApplet;

public class NonMagnet extends Spin{

	public NonMagnet() {
		r = 0;
		c = 0;
		spin = 0;
		setColor();
	}
	
	public NonMagnet(int r, int c) {
		this.r= r;
		this.c = c;
		spin = 0;
		setColor();
	}
	
	public void display(PApplet applet, int xoffset, int yoffset, int width, int height) {
		applet.fill(useColor.r, useColor.g, useColor.b);
		applet.rect(xoffset + BORDER_LENGTH, yoffset + BORDER_LENGTH, width - 2 * BORDER_LENGTH, height - 2 * BORDER_LENGTH);
	}
	
	
	public void updateSpin(NonMagnet[][] arr, double T, double h) {
		// do nothing
	}
	
	public void setSpin() {
		// do nothing
	}
	
}
