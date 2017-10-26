/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	/*
	 * Method : run()
	 * Draw Pyramid using GRect
	 */
	public void run() {
		drawPyramid();
	}
	
	/*
	 * Method : drawPyramid()
	 * Draw pyramid line by line
	 */
	private void drawPyramid() {
		
		double width = (getWidth() - BRICKS_IN_BASE * BRICK_WIDTH) / 2.0;
		double height = (getHeight() - 12) * 1.0;
		int bricksNumber = BRICKS_IN_BASE;
		
		while (bricksNumber > 0) {
			drawTheLine(bricksNumber, width, height);
			bricksNumber--;
			width += 15.0;
			height = (height - BRICK_HEIGHT) * 1.0;
		}
		
	}
	
	/*
	 * Method : drawTheLine(int, double, double)
	 * Draw the line which consists with brickNumber bricks
	 */
	private void drawTheLine(int bricksNumber, double width, double height) {
		
			double brickLoc1 = width;
			double brickLoc2 = height;
			
			for (int i = 0; i < bricksNumber; i++) {
				GRect rectangle = new GRect(brickLoc1, brickLoc2, BRICK_WIDTH, BRICK_HEIGHT);
				add(rectangle);
				brickLoc1 += BRICK_WIDTH;
			}
	}
}

