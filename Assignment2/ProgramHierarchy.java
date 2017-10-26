/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.graphics.*;
import acm.program.*;

public class ProgramHierarchy extends GraphicsProgram {
	
	/** Width of each box */
	private static final double WIDTH = 150.0;
	
	/** Height of each box */
	private static final double HEIGHT = 60.0;
	
	/** Distance between two boxes */
	private static final double DISTANCE = 50.0;
	
	/*
	 * Method : run()
	 * Draw all boxes, labels and lines
	 */
	public void run() {
		drawBoxes();
		drawLines();
	}
	
	/*
	 * Method : drawBoxes()
	 * Draw all boxes with labels
	 */
	private void drawBoxes() {
		drawProgramBox();
		drawConsoleBox();
		drawGraphicsBox();
		drawDialogBox();
	}
	
	/*
	 * Method : drawLines()
	 * Draw all lines
	 */
	private void drawLines() {
		drawConsoleLine();
		drawGraphicsLine();
		drawDialogLine();
	}
	
	private void drawProgramBox() {
		double x = (getWidth() - WIDTH) / 2;
		double y = (getHeight() - DISTANCE) / 2 - HEIGHT;
		
		/* Draw box */
		GRect box = new GRect(x, y, WIDTH, HEIGHT);
		add(box);
		
		/* Draw label */
		GLabel label = new GLabel("Program", x, y);
		add(label);
		label.move( (WIDTH - label.getWidth()) / 2 , (HEIGHT + label.getAscent()) / 2);
	}
	
	private void drawConsoleBox() {
		double x = (getWidth() - WIDTH) / 2;
		double y = (getHeight() + DISTANCE) / 2;
		
		/* Draw box */
		GRect box = new GRect(x, y, WIDTH, HEIGHT);
		add(box);
		
		/* Draw label */
		GLabel label = new GLabel("ConsoleProgram", x, y);
		add(label);
		label.move( (WIDTH - label.getWidth()) / 2 , (HEIGHT + label.getAscent()) / 2);
	}

	private void drawGraphicsBox() {
		double x = (getWidth() - WIDTH) / 2 - DISTANCE - WIDTH;
		double y = (getHeight() + DISTANCE) / 2;
		
		/* Draw box */
		GRect box = new GRect(x, y, WIDTH, HEIGHT);
		add(box);
		
		/* Draw label */
		GLabel label = new GLabel("GraphicsProgram", x, y);
		add(label);
		label.move( (WIDTH - label.getWidth()) / 2 , (HEIGHT + label.getAscent()) / 2);
	}

	private void drawDialogBox() {
		double x = (getWidth() + WIDTH) / 2 + DISTANCE;
		double y = (getHeight() + DISTANCE) / 2;
		
		/* Draw box */
		GRect box = new GRect(x, y, WIDTH, HEIGHT);
		add(box);
		
		/* Draw label */
		GLabel label = new GLabel("DialogProgram", x, y);
		add(label);
		label.move( (WIDTH - label.getWidth()) / 2 , (HEIGHT + label.getAscent()) / 2);
	}
	
	private void drawConsoleLine() {
		/* Starting coordinates of line */
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - DISTANCE) / 2;
	
		/* Ending coordinates of line */
		double x2 = getWidth() / 2;
		double y2 = (getHeight() + DISTANCE) / 2;
		
		/* Draw line */
		GLine line = new GLine(x1, y1, x2, y2);
		add(line);
	}

	private void drawGraphicsLine() {
		/* Starting coordinates of line */
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - DISTANCE) / 2;
	
		/* Ending coordinates of line */
		double x2 = getWidth() / 2 - DISTANCE - WIDTH;
		double y2 = (getHeight() + DISTANCE) / 2;
		
		/* Draw line */
		GLine line = new GLine(x1, y1, x2, y2);
		add(line);
	}

	private void drawDialogLine() {
		/* Starting coordinates of line */
		double x1 = getWidth() / 2;
		double y1 = (getHeight() - DISTANCE) / 2;
	
		/* Ending coordinates of line */
		double x2 = getWidth() / 2 + DISTANCE + WIDTH;
		double y2 = (getHeight() + DISTANCE) / 2;
		
		/* Draw line */
		GLine line = new GLine(x1, y1, x2, y2);
		add(line);
	}
}
