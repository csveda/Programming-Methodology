/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Brick object */
	private GRect brick;
	
/** Paddle object */
	private GRect paddle;
	
/** Ball object */
	private GOval ball;
	
/** Ball velocity */
	private double vx, vy;
	
/** Random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();

/** Number of bricks */
	private static final int BRICKS_NUMBER = NBRICK_ROWS * NBRICKS_PER_ROW;
	
/** Number of bricks deleted at the moment */
	private int bricks_deleted = 0;
	
/** Delay number */
	private static final int DELAY = 20;
	
/** Number of unsuccessful tries */
	private int lost = 0;
	
/** Width of the rectangle which shows result */
	private static final int RESULT_WIDTH = 100;
	
/** Height of the rectangle which shows result */
	private static final int RESULT_HEIGHT = 50;

	
	/*
	 * Method : run()
	 * Runs the Breakout game
	 */
	public void run() {
		
		setup();
		
		while (lost < NTURNS) {
			
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
			play();
			
			lost++;
			if (bricks_deleted == BRICKS_NUMBER) {
				removeAll();
				playerWon();
				break;
			}
		}
		
		if (bricks_deleted < BRICKS_NUMBER) {
			removeAll();
			playerLost();
		}
		
	}
	
	private void setup() {
		drawBricks();
		drawPaddle();
		drawBall();
	}
	
	private void play() {
		
		waitForClick();
		getBallVelocity();
		
		while (true) {
			moveBall();
			if (checkIfDone()) {
				break;
			}
		}
	}
	
	private void drawBricks() {
		for (int row  = 0; row < NBRICK_ROWS; row++) {
			for (int column = 0; column < NBRICKS_PER_ROW; column++) {
				/* To get X coordinate do some calculation :
				 * 		1. start at the center of the width
				 * 		2. subtract half of the bricks width in the row
				 * 		3. subtract half of the separations between bricks
				 * Now X is the coordinate of first brick in the row, so :
				 * 		4. add bricks width
				 * 		5. add separations' width
				 */
				
				double x = WIDTH / 2;
				x -= NBRICKS_PER_ROW * BRICK_WIDTH / 2;
				x -= (NBRICKS_PER_ROW - 1) * BRICK_SEP / 2;
				x += column * BRICK_WIDTH;
				x += column * BRICK_SEP;
				
				/* To get Y coordinate to some calculations :
				 *		1. start at the height between first row and ceiling
				 *		2. add bricks height
				 *		3. add separations height
				 */
				
				double y = BRICK_Y_OFFSET;
				y += row * BRICK_HEIGHT;
				y += row * BRICK_SEP;
				
				
				/* Draw brick */
				addBrick(x, y);
				setBrickColor(row);
			}
		}
	}
	
	private void drawPaddle() {
		/* To get X coordinate :
		 * 		1. start at the center of the width
		 * 		2. subtract half of the brick's width
		 */
		
		double x = WIDTH / 2;
		x -= PADDLE_WIDTH / 2;
		
		/* To get Y coordinate :
		 * 		1. start at the application height
		 * 		2. subtract distance between paddle and floor
		 * 		3. subtract height of the paddle
		 */
		
		double y = HEIGHT;
		y -= PADDLE_Y_OFFSET;
		y -= PADDLE_HEIGHT;
		
		/* Draw paddle */
		addPaddle(x, y);
		setPaddleColor();
	}
	
	private void drawBall() {
		 /* To get X and Y coordinates :
		  * 	1. take center of the width and height
		  * 	2. subtract radius of the ball
		  */
		
		double x = WIDTH / 2 - BALL_RADIUS;
		double y = HEIGHT / 2 - BALL_RADIUS;
		
		/* Draw ball */
		addBall(x, y);
		setBallColor();
	}
	
	private void addBrick(double x, double y) {
		brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		add(brick);
	}
	
	private void setBrickColor(int row) {
		
		row /= 2;
		brick.setFilled(true);
		
		switch(row) {
			case 0 : brick.setColor(Color.RED);
					 break;
			case 1 : brick.setColor(Color.ORANGE);
			 		 break;
			case 2 : brick.setColor(Color.YELLOW);
			 		 break;
			case 3 : brick.setColor(Color.GREEN);
			 		 break;
			case 4 : brick.setColor(Color.CYAN);
			 		 break;
			default : break;
		}
		
	}
	
	private void addPaddle(double x, double y) {
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		addMouseListeners();
		add(paddle);
	}
	
	private void setPaddleColor() {
		paddle.setColor(Color.BLACK);
		paddle.setFilled(true);
	}
	
	private void addBall(double x, double y) {
		ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		add(ball);
	}
	
	private void setBallColor() {
		ball.setColor(Color.BLACK);
		ball.setFilled(true);
	}
	
	private void getBallVelocity() {
		
		vx = rgen.nextDouble(1.0, 3.0);
		vy = 4.0;
		
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		
	}
	
	private void moveBall() {
		ball.move(vx, vy);
		
		if (ball.getX() - vx < 0 && vx < 0) {
			vx = -vx;
		}
		
		if (ball.getX() + 2 * BALL_RADIUS + vx > WIDTH && vx > 0) {
			vx = -vx;
		}
		
		if (ball.getY() - vy < 0 && vy < 0) {
			vy = -vy;
		}
		
		
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			vy = - vy;
		}
		else if (collider != null ) {
			/* If collider isn't paddle and we know it's not NULL it must be brick */
			remove(collider);
			bricks_deleted++;
			vy = -vy;
		}
		
		pause(DELAY);
	}
	
	public void mouseMoved(MouseEvent e) {
		if (e.getX() <= WIDTH - PADDLE_WIDTH) {
			paddle.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	/*
	 * Method : checkIfDone()
	 * Check if player won the game or lost "health"
	 */
	private boolean checkIfDone() {
		
		if (ball.getY() + BALL_RADIUS > HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
			return true;
		}		
		
		if (bricks_deleted == BRICKS_NUMBER)
		{
			return true;
		}
		
		return false;
	}
	
	private GObject getCollidingObject() {
		
		GObject object;
		
		object = getElementAt(ball.getX(), ball.getY());
		if (object != null) {
			return object;
		}
		
		object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		if (object != null) {
			return object;
		}
		
		object = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		if (object != null) {
			return object;
		}
		
		object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		if (object != null) {
			return object;
		}
		
		return null;
	}
	
	private void playerWon() {
		addResultRect();
		String s = "You Won!";
		addResultText(s);
	}
	
	private void playerLost() {
		addResultRect();
		String s = "You Lost!";
		addResultText(s);
	}
	
	private void addResultRect() {
		double x = (WIDTH - RESULT_WIDTH) / 2;
		double y = (HEIGHT - RESULT_HEIGHT) / 2;
		GRect result = new GRect(x, y, RESULT_WIDTH, RESULT_HEIGHT);
		add(result);
	}
	
	private void addResultText(String s) {
//		GLabel text = new GLabel(s, (WIDTH - RESULT_WIDTH) / 2, (HEIGHT + RESULT_HEIGHT) / 2);
//		text.move((RESULT_WIDTH - text.getWidth()) / 2, - (RESULT_HEIGHT / 2 + text.getAscent() - text.getDescent()));
//		add(text);
	}
}