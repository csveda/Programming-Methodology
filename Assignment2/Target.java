/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	
	/** Size of large circle in CM */
	private static final double SIZE_IN_CM1 = 2.54;
	
	/** Size of medium circle in CM */
	private static final double SIZE_IN_CM2 = 1.65;
	
	/** Size of small circle in CM */
	private static final double SIZE_IN_CM3 = 0.76;
	
	/** Size of large circle in PIXEL */
	private static final double SIZE_IN_PIXEL1 = 72.0;
	
	/*
	 * Method : run()
	 * Draw three circles and fill them with colors using GOval
	 */
	public void run() {
		drawOvals();	
	}
	
	/*
	 * Method : drawOvals(double, double, double, double)
	 * Draw all ovals
	 */
	private void drawOvals() {
		drawLarge();
		drawMedium();
		drawSmall();
	}
	
	/*
	 * Method : sizeOfPixel()
	 * Count PIXEL/CM ratio
	 */
	private double sizeOfPixel() {
		return SIZE_IN_CM1 / SIZE_IN_PIXEL1;
	}
	
	
	/*
	 * Method : drawLarge()
	 * Draw large circle
	 * Fill with color red
	 */
	private void drawLarge() {
		
		double x = (double)getWidth() / 2.0 - SIZE_IN_PIXEL1;
		double y = (double)getHeight() / 2.0 - SIZE_IN_PIXEL1;
		
		GOval oval = new GOval(x, y, 2 * SIZE_IN_PIXEL1, 2 * SIZE_IN_PIXEL1);
		oval.setFillColor(Color.RED);
		oval.setFilled(true);
		add(oval);
		
	}
	
	/*
	 * Method : drawMedium(double)
	 * Draw medium circle
	 * Fill with color white
	 */
	private void drawMedium() {
		
		double size = SIZE_IN_CM2 / sizeOfPixel();
		double x = (double)getWidth() / 2.0 - size;
		double y = (double)getHeight() / 2.0 - size;
		
		GOval oval = new GOval(x, y, 2 * size, 2 * size);
		oval.setFillColor(Color.WHITE);
		oval.setFilled(true);
		add(oval);
		
	}

	/*
	 * Method : drawSmall(double)
	 * Draw small circle
	 * Fill with color red
	 */
	private void drawSmall() {
		
		double size = SIZE_IN_CM3 / sizeOfPixel();
		double x = (double)getWidth() / 2.0 - size;
		double y = (double)getHeight() / 2.0 - size;
		
		GOval oval = new GOval(x, y, 2 * size, 2 * size);
		oval.setFillColor(Color.RED);
		oval.setFilled(true);
		add(oval);
		
	}
}