package zoe.montecarlo.simulation;

import processing.core.*;

public class View extends PApplet{
	
	final int ROWS = 128;
	final int COLS = 128;
	final int SPIN_WIDTH = 5;
	final int SPIN_HEIGHT = 5;
	public final int LEFT_OFFSET = 12;  // From left of window to left side of grid
	final int TOP_OFFSET = 12;   // From top of window to top of grid
	
	final double CRITICAL_TEMP = 2.26918531421;
	double T = CRITICAL_TEMP+0.2;
	final double h = 0;
	double PERCOLATION = 0.25;

	Spin[][] percolatedSpins = new Spin[ROWS][COLS];

	public static void main(String[] args) {
		PApplet.main("zoe.montecarlo.simulation.View");
	}
	
	public void settings() {
		size(SPIN_WIDTH * COLS + 2 * LEFT_OFFSET, SPIN_HEIGHT * ROWS + 2 * TOP_OFFSET);
	}
	
	public void setup() {
		background(0);
		stroke(0);
		initGrid();
	}
	
	public void initGrid() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				percolatedSpins[r][c] = new Spin(r, c);
				percolatedSpins[r][c].setSpin(PERCOLATION);
			}
		}
	}

	public void draw() {
			updateSpins();
			displaySpins();
			delay(50); // gives some time for the brain to process the visuals lol
			redraw();
	}
	
	public void displaySpins() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				percolatedSpins[r][c].display(this, // the PApplet
						LEFT_OFFSET + r * SPIN_WIDTH, // start x coordinate of spin
						TOP_OFFSET + c * SPIN_HEIGHT, // start y coordinate of spin
						SPIN_WIDTH, // width of spin
						SPIN_HEIGHT); // height of spin
			}
		}
	}
	
	public void updateSpins() {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				percolatedSpins[r][c].updateSpin(percolatedSpins, T, h);	
			}
		}
	}
}
	
	