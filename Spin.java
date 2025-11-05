package zoe.montecarlo.simulation;
import processing.core.PApplet;

public class Spin {
	
	public final int J = 1; // coupling constant that describes the system (ferromagnetic)
	public int r; // row
	public int c; // column
	public int spin; // up/down spin represented by +-1
	public final int BORDER_LENGTH = 0; // can be set to an integer to create gaps between spin objects
	public Color useColor;
	public int neighbors; // sum of the spins of all nearest neighbors up/down/left/right
	
	private final Color UP_COLOR = new Color(70, 130, 180); // blue
	private final Color DOWN_COLOR = new Color(220, 20, 60); //red
	private final Color NON_MAGNET_COLOR = new Color(0, 0, 0); //white
	
	// creates a default spin object with no coordinates
	public Spin() { 
		r = 0;
		c = 0;
		spin = 1;
		setColor();
	}
	
	// custom spin object with array coordinates and default spin
	public Spin(int r, int c) { 
		this.r = r;
		this.c = c;
		spin = 1;
		setColor();
	}
	
	// sets up a random spin either up or down to represent the random system
	public void setSpin(double percol) {
		if (Math.random() < percol) {
			this.spin = 0;
		}
		else {
			this.spin = (Math.random() < 0.5)? -1 : 1;
		}
		setColor();
	}
	
	public void setColor() {
		if (spin == -1) {
			useColor = DOWN_COLOR;
		}
		else if (spin == 1) {
			useColor = UP_COLOR;
		}
		else {
			useColor = NON_MAGNET_COLOR;
		}
	}
	
	// draws the spin object as a small rectangle
	public void display(PApplet applet, int xoffset, int yoffset, int width, int height) {
		Color fillColor = useColor;
		applet.fill(fillColor.r, fillColor.g, fillColor.b);
		applet.rect(xoffset + BORDER_LENGTH, yoffset + BORDER_LENGTH, width - 2 * BORDER_LENGTH, height - 2 * BORDER_LENGTH);
	}
	
	// flips spin 
	public void flipSpin() {
		spin = -1 * spin;
		setColor();
	}
	
	// updates spin in the model but not the view
	public void updateSpin(Spin[][] arr, double T, double h) {
		calcNeighbors(arr);
		double deltaE = 2 * spin * (J * neighbors + h); // change in energy of a theoretical spin flip
		if (deltaE < 0) { // checks if flipping spin would be ideal energy wise
			flipSpin();
		}
		else if (spontaneous(deltaE, T)) { // adds randomness to the system through random spin flips determined by the critical exponent 𝛽
			flipSpin();
		}
	}
	
	public void calcNeighbors(Spin[][] arr) {
		// calculates what row is the row above and below
		int upNRow;
		int downNRow;
		
		if (r == 0) { // wrap around from the top to the bottom 
			upNRow = arr.length - 1;
			downNRow = 1;
		}
		else if (r == arr.length - 1) { // wrap around from the bottom to the top
			upNRow = r - 1;
			downNRow = 0;
		}
		else { // middle of the system 
			upNRow = r - 1;
			downNRow = r + 1;
		}
		
		// calculates what column is to the left and to the right
		int leftNCol;
		int rightNCol;
		
		if (c == 0) { // wrap around from left to right side
			leftNCol = arr[0].length - 1;
			rightNCol = 1;
		}
		else if (c == arr[0].length - 1) { // wrap around from right to left side
			leftNCol = c - 1;
			rightNCol = 0;
		}
		else { // middle of the system
			leftNCol = c - 1;
			rightNCol = c + 1;
		}
		
		// checks for out of bounds in the array
		if (leftNCol < 0 || leftNCol >= arr[0].length) {
	        throw new IllegalArgumentException("Left Col Out of Bounds");
	    }
		if (rightNCol < 0 || rightNCol >= arr[0].length) {
	        throw new IllegalArgumentException("Right Col Out of Bounds");
	    }
		if (upNRow < 0 || upNRow >= arr.length) {
	        throw new IllegalArgumentException("Up Row Out of Bounds");
	    }
		if (downNRow < 0 || downNRow >= arr.length) {
	        throw new IllegalArgumentException("Down Row Out of Bounds");
	    }
		
		// adds spin of each neighbor
		neighbors = arr[upNRow][c].spin + arr[downNRow][c].spin + arr[r][leftNCol].spin + arr[r][rightNCol].spin;
	}
	
	public boolean spontaneous(double deltaE, double T) {
		if (T == 0) { // checks for possible division by zero
			throw new IllegalArgumentException("Temperature T cannot be zero.");
		}
		double prob = Math.exp(-deltaE/T); // T normalized to boltzmann constant
		return Math.random() < prob;  
	} 

}



