import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BreakoutExtension extends GraphicsProgram {
	
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
	
/** If brick should be changed (change color or delete it) */
	private boolean change_it = true;
	
/** If ball attacks first half of the ball 1, else -0 */
	private boolean cross_side = false;
	
	/** Max change in x direction if you hit on the very edge of the paddle */
    private static final double MAX_CHANGE = 1.5;

    /** Max and min starting X speeds */
    private static final double LOW_VX = 1.5;
    private static final double HIGH_VX = 3.0;

 // Computer paddle constants
    /** How far the computer paddle moves towards the ball every frame */
    /** It determines the difficulty of the game */
    private double paddle_speed = 0.0;

    private static final double PROBABILITY_OF_MOVING = 0.5;

    /** Number of points needed to win */
    private static final int POINTS_TO_WIN = 3;

    /** Audio for the ball when it touches walls of paddles */
    AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
    
    /** Sets the color of the ball */
    private static final Color BALL_COLOR = Color.GREEN;

    /** Sets the speed of the ball in the y direction */
    private static final double BALL_Y_SPEED = 2.0;
    
    /** Ball radius in level3 */
    private static final int BALL_RAD = 7;
    
    /** Instance variable for the player and computer paddle respectively */
    public GObject playerPaddle = new GRect(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
    public GObject computerPaddle = new GRect(0, 0, PADDLE_WIDTH, PADDLE_HEIGHT);
    
    /** Audio for the level3 */
    AudioClip music = MediaTools.loadAudioClip("music.au");
    
    /** Number of the current level*/
    public int level = 0;
    
    /** Paddle color */
    private static final Color PADDLE_COLOR = Color.RED;
    
    GImage win = new GImage("win.gif");
    GImage lose = new GImage("lose.gif");
	
	public void run () {
		if (level1()) {
			waitForClick();
			removeAll();
			if (level2()) {
				waitForClick();
				removeAll();
				level = 3;
				removeAll();
				music.play();
				
				welcomeLabel3();
				waitForClick();
				removeAll();
				level3();
			}
		}
	}
	
	
	
	private boolean level1() {
		level = 1;
		welcomeLabel1();
		waitForClick();
		setup();
		
		while (lost < NTURNS) {
			
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
			play1();
			
			lost++;
			if (bricks_deleted == BRICKS_NUMBER) {
				removeAll();
				playerWon1();
				return true;
			}
		}
		
		removeAll();
		playerLost1();
		return false;
	}
	
	private void setup() {
		removeAll();
		drawBricks();
		drawPaddle();
		drawBall();
	}
	
	private void welcomeLabel1() {
		double x = WIDTH / 6;
		double y = HEIGHT / 2;
		GLabel text = new GLabel("Welcome to the Level1!", x, y);
		text.setFont("Monoscape-20");
		add(text);
		
		y += 40;
		text = new GLabel("Click to continue", x, y);
		text.setFont("Monoscape-20");
		add(text);
	}
	
	private void play1() {
		
		waitForClick();
		getBallVelocity();
		
		while (true) {
			moveBall1();
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
	
	private void moveBall1() {
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
		
		
		GObject collider = getCollidingObject1();
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
		if (level == 1 || level == 2) {
			if (e.getX() <= WIDTH - PADDLE_WIDTH) {
				paddle.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			}
		}
		
		if (level == 3) {
			// The center x coordinate of the paddle
			double paddleX = e.getX() - (PADDLE_WIDTH / 2);

			// The top y coordinate of the paddle
			double paddleY = getHeight() - PADDLE_Y_OFFSET;
			
			if (paddleOnScreen(e.getX())) {
				playerPaddle.setLocation(paddleX, paddleY);
			}
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
	
	private GObject getCollidingObject1() {
		
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
	
	private void playerWon1() {
		String s = "You happy? LOL! It's jut a beginning!";
		addResultText(s);
	}
	
	private void playerLost1() {
		String s = "NooB! You Lost!";
		addResultText(s);
	}
	
	private void addResultText(String s) {
		double x = WIDTH / 6 - 50;
		double y = HEIGHT / 2;
		GLabel text = new GLabel(s, x, y);
		text.setFont("Monoscape-20");
		add(text);
		
		y += 40;
		text = new GLabel("Click to continue", x, y);
		text.setFont("Monoscape-20");
		add(text);
	}
	
	
	
	
	
	
	
	private boolean level2() {
		level = 2;
		bricks_deleted = 0;
		lost = 0;
		
		welcomeLabel2();
		waitForClick();
		setup();
		
		while (lost < NTURNS) {
			
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
			play2();
			
			lost++;
			if (bricks_deleted == BRICKS_NUMBER) {
				removeAll();
				playerWon2();
				return true;
			}
		}
		
		removeAll();
		playerLost2();
		return false;
	}
	
	private void welcomeLabel2() {
		double x = WIDTH / 6;
		double y = HEIGHT / 2;
		GLabel text = new GLabel("Welcome to the Level2!", x, y);
		text.setFont("Monoscape-20");
		add(text);
		
		y += 40;
		text = new GLabel("Click to continue", x, y);
		text.setFont("Monoscape-20");
		add(text);
	}
	
	private void play2() {
		
		waitForClick();
		getBallVelocity();
		
		while (true) {
			moveBall2();
			if (checkIfDone()) {
				break;
			}
		}
	}
	
	private void moveBall2() {
		ball.move(vx, vy);
		
		if (ball.getX() - vx < 0 && vx < 0) {
			change_it = true;
			vx = -vx;
		}
		
		if (ball.getX() + 2 * BALL_RADIUS + vx > WIDTH && vx > 0) {
			change_it = true;
			vx = -vx;
		}
		
		if (ball.getY() - vy < 0 && vy < 0) {
			change_it = true;
			vy = -vy;
		}
		
		
		GObject collider = getCollidingObject2();
		if (collider == paddle) {
			change_it = true;
			
			if (cross_side == true) {
				vx = -vx;
				vy = -vy;
			}
			else {
				vy = -vy;
			}
		}
		else if (collider != null) {
			/* If collider isn't paddle and we know it's not NULL it must be brick */
			if (collider.getColor() == Color.RED && change_it == true) {
				collider.setColor(Color.ORANGE);
				change_it = false;
			}
			
			if (collider.getColor() == Color.ORANGE && change_it == true) {
				collider.setColor(Color.YELLOW);
				change_it = false;
			}
			
			if (collider.getColor() == Color.YELLOW && change_it == true) {
				collider.setColor(Color.GREEN);
				change_it = false;
			}
			
			if (collider.getColor() == Color.GREEN && change_it == true) {
				collider.setColor(Color.CYAN);
				change_it = false;
			}
			
			if (collider.getColor() == Color.CYAN && change_it == true) {
				remove(collider);
				change_it = false;
				bricks_deleted++;
			}
			
			vy = -vy;
		}
		
		pause(DELAY);
	}
	
	private GObject getCollidingObject2() {
		
		GObject object;
		
		object = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1);
		if (object != null) {
			if ((ball.getX() + BALL_RADIUS < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() + BALL_RADIUS >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX() - 1, ball.getY() + BALL_RADIUS);
		if (object != null) {
			if ((ball.getX() - 1 < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() - 1 >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX() + 2 * BALL_RADIUS + 1, ball.getY() + BALL_RADIUS);
		if (object != null) {
			if ((ball.getX() + 2 * BALL_RADIUS + 1 < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() + 2 * BALL_RADIUS + 1 >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + 1);
		if (object != null) {
			if ((ball.getX() + BALL_RADIUS < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() + BALL_RADIUS >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX(), ball.getY());
		if (object != null) {
			if ((ball.getX() < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		if (object != null) {
			if ((ball.getX() < paddle.getX() + 2 * BALL_RADIUS + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() >= paddle.getX() + 2 * BALL_RADIUS + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		if (object != null) {
			if ((ball.getX() < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		if (object != null) {
			if ((ball.getX() + 2 * BALL_RADIUS < paddle.getX() + PADDLE_WIDTH / 2 && vx > 0) || (ball.getX() + 2 * BALL_RADIUS >= paddle.getX() + PADDLE_WIDTH / 2 && vx < 0)) {
				cross_side = true;
			}
			else {
				cross_side = false;
			}
			
			return object;
		}
		
		return null;
	}
	
	private void playerWon2() {
		String s = "You happy again? LOL! Just continue!";
		addResultText(s);
		
		s = "Boss is waiting for you!";
		double x = WIDTH / 6 - 50;
		double y = HEIGHT / 2 + 80;
		GLabel text = new GLabel(s, x, y);
		text.setFont("Monoscape-20");
		add(text);
	}
	
	private void playerLost2() {
		String s = "HaHa! I warned u!";
		addResultText(s);
	}
	
	
	
	
	
	

	private void level3() {
		drawPaddle(playerPaddle, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
        drawPaddle(computerPaddle, PADDLE_Y_OFFSET);

        startScreen();
        moveBall();
        level3();
	}
	
	public void init() {
        addMouseListeners();
        addKeyListeners();
    }
	
	private void welcomeLabel3() {
		double x = WIDTH / 6;
		double y = HEIGHT / 2;
		GLabel text = new GLabel("Welcome to the Level3!", x, y);
		text.setFont("Monoscape-20");
		add(text);
		
		y += 40;
		text = new GLabel("Click to continue", x, y);
		text.setFont("Monoscape-20");
		add(text);
	}

    // Animates the ball and all of the changes of direction associated with the ball
    private void moveBall() {
        // The score of the player and the computer
        int playerScore = 0;
        int computerScore = 0;

        // The labels for the player and computers score
        GLabel playerScoreLabel = returnPlayerScores();
        add(playerScoreLabel);

        GLabel computerScoreLabel = returnComputerScores();
        add(computerScoreLabel);

        // The speed of the ball
        double vx = randomVX();
        double vy = BALL_Y_SPEED;

        // The intercept is the point at which the ball will cross past the computer paddle
        double intercept = WIDTH / 2;

        GOval ball = ball(WIDTH / 2, HEIGHT / 2);
        add(ball);

        if (vx == 0) {
        	vx = randomVX();
        }

        if (vy == 0) {
        	vy = BALL_Y_SPEED;
        }

        while (playerScore < POINTS_TO_WIN && computerScore < POINTS_TO_WIN) {
            ball.move(vx, vy);

            /* Bounces the ball off the sides */
            if (offScreenX(ballXCenter(ball))) {
                vx = -vx;
                bounceClip.play();

                /* Ensures that the ball does not get glued to the side */
                while (offScreenX(ballXCenter(ball))) {
                	ball.move(vx, vy);
                }

            }

            /*
             * Increase player's score if the ball goes off the top
             * Reset the ball to the center of the screen
             * Update the score label
             */
            if (offTop(ball.getY())) {
                playerScore++;
                playerScoreLabel.setLabel("Player: " + playerScore);
                ballReset(ball, vx, vy);
                intercept = WIDTH / 2;
                vy = BALL_Y_SPEED;
            }

            /*
             * Adds one to the score of the computer if the ball goes off the bottom
             * Also resets the ball to the center of the screen and updates the score label
             */
            if (offBottom(ball.getY())) {
                computerScore++;
                computerScoreLabel.setLabel("Computer : " + computerScore);
                ball.setLocation(WIDTH / 2, HEIGHT / 2);
                ballReset(ball, vx, vy);
            }

            /*
             * Bounces the ball off the player paddle
             * Also calculates where the ball will hit the computer paddle
           	 */
            if (hitSomething(ballXCenter(ball), ballYCenter(ball)) == playerPaddle) {
                vy = -vy;
                vx -= pointHitOnPaddle(playerPaddle, ball);

                intercept = xIntercept(vx, vy, ballXCenter(ball), ballYCenter(ball));
                bounceClip.play();
            }

            /*
             * Bounces the ball off the computer paddle
             * Also resets the intercept point to the middle of the screen
             */
            if (hitSomething(ballXCenter(ball), ballYCenter(ball)) == computerPaddle) {
                vy = -vy;
                vx -= pointHitOnPaddle(computerPaddle, ball);

                intercept = WIDTH / 2;
                bounceClip.play();
            }

            if (vx == 0) {
            	vx = randomVX();
            }

            if (vy == 0) {
            	vy = BALL_Y_SPEED;
            }

            /* Keeps moving the paddle so long as it is not already where the ball will hit it */
            if (intercept != paddleXCenter(computerPaddle)) {
                moveComputerPaddle(intercept);
            }

            pause(DELAY);
        }

        endScreen(playerScore, computerScore, ball, computerScoreLabel, playerScoreLabel);
    }

    /*
     * Method : void paddleSetUp(GOject, double)
     * Draw paddle in location (x, y)
     */
    private void drawPaddle(GObject paddle, double y) {
        double paddleX = (WIDTH - PADDLE_WIDTH) / 2;
        paddle.setLocation(paddleX, y);
        paddle.setColor(PADDLE_COLOR);
        add(paddle);
    }

    /*
     * Method : GOval ball(double, double)
     * Sets ball with center on location (x, y)
     */
    private GOval ball(double x, double y) {
        GOval ball = new GOval(x - BALL_RAD, y - BALL_RAD, 2 * BALL_RAD, 2 * BALL_RAD);
        ball.setFilled(true);
        ball.setColor(BALL_COLOR);
        return ball;
    }

    /*
     * Method : boolean offScreenX(double)
     * Using center coordinate of the ball checks whether the ball is in x direction
     */
    private boolean offScreenX(double x) {
        if (x - BALL_RAD <= 0 || x + BALL_RAD >= WIDTH) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Method : double randomDX()
     * Generate X direction randomly
     */
    private double randomVX() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        double result = rgen.nextDouble(LOW_VX, HIGH_VX);

        if (rgen.nextBoolean()) {
            result *= -1;
        }

        return result;
    }

    /*
     * Method : GObject hitSomething(double, double)
     * Check if ball hits anything
     */
    private GObject hitSomething(double x, double y) {
    	GObject collider = null;

    	collider = getElementAt(x, y - BALL_RAD - 1);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x, y + BALL_RAD + 1);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x - BALL_RAD - 1, y);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x + BALL_RAD + 1, y);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x - BALL_RAD, y - BALL_RAD);
    	if (collider != null) {
    		return collider;
    	}


    	collider = getElementAt(x - BALL_RAD, y + BALL_RAD);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x + BALL_RAD, y - BALL_RAD);
    	if (collider != null) {
    		return collider;
    	}

    	collider = getElementAt(x + BALL_RAD, y + BALL_RAD);
    	if (collider != null) {
    		return collider;
    	}

    	return collider;
    }

    /*
     * Method : boolean offTop(double)
     * Check if the ball goes off the top
     */
    private boolean offTop(double y) {
        if (y + 2 * BALL_RAD < PADDLE_Y_OFFSET + PADDLE_HEIGHT) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Method : boolean offBottom(double)
     * Check if the ball goes off the bot
     */
    private boolean offBottom(double y) {
        if (y > HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT) {
        	return true;
        }
        return false;
    }

    /*
     * Method : double ballXCenter(GObject)
     * Returns center X coordinate of the ball
     */
    private double ballXCenter(GObject ball) {
        return ball.getX() + BALL_RAD;
    }

    /*
     * Method : double ballYCenter(GObject)
     * Returns center Y coordinate of the ball
     */
    private double ballYCenter(GObject ball) {
        return ball.getY() + BALL_RAD;
    }

    /*
     * Method : double xIntercept(double, double, double, double)
     * Predict X coordinate for computer's paddle to move there
     */
    private double xIntercept(double vx, double vy, double ballX, double ballY) {
        GOval predictedBall = ball(ballX, ballY);

        while (ballYCenter(predictedBall) > PADDLE_Y_OFFSET + PADDLE_HEIGHT) {
            predictedBall.move(vx, vy);
            if (offScreenX(ballXCenter(predictedBall))) {
                vx *= -1;
            }
        }

        return ballXCenter(predictedBall);
    }

    /*
     * Method : void moveComputerPaddle(double)
     * Move computer paddle randomly
     */
    private void moveComputerPaddle(double intercept) {
        int leftOrRight = 1;
        if (paddleXCenter(computerPaddle) > intercept) {
            leftOrRight = -1;
        }

        RandomGenerator rgen = RandomGenerator.getInstance();
        if (rgen.nextBoolean(PROBABILITY_OF_MOVING)) {
            computerPaddle.move(paddle_speed * leftOrRight, 0);
        }
    }

    /*
     * Method : double paddleXCenter(GObject)
     * Return X center coordinate of the paddle
     */
    private double paddleXCenter(GObject object) {
        return object.getX() + (PADDLE_WIDTH) / 2;
    }

    /*
     * Method : boolean paddleOnScreen(double)
     * Find out whether paddle is on the screen or not
     */
    private boolean paddleOnScreen(double x) {
        if (x >= (PADDLE_WIDTH / 2) && x <= WIDTH - (PADDLE_WIDTH / 2)) {
        	return true;
        }
        return false;
    }

    /*
     * Method : double pointHitOnPaddle(GObject, GObject)
     * Find out how far the ball hits from the center of the paddle
 	 */
    private double pointHitOnPaddle(GObject paddle, GObject ball) {
        double distanceFromCenter = paddleXCenter(paddle) - ballXCenter(ball);
        return (distanceFromCenter / (PADDLE_WIDTH / 2)) * MAX_CHANGE;
    }

    /*
     * Method : GLabel label(String, double, double)
     * Return label
     */
    private GLabel label(String s, double x, double y) {
        GLabel label = new GLabel(s, x, y);
        label.setColor(Color.WHITE);
        return label;
    }

    /*
     * Method GLabel returnPlayerScores()
     * Return label which shows player's score
     */
    private GLabel returnPlayerScores() {
        GLabel playerScoreLabel = label("Player : 0", 0, 0);
        playerScoreLabel.setLocation(0, getHeight() - playerScoreLabel.getDescent());
        return playerScoreLabel;
    }

    /*
     * Method GLabel returnComputerScores()
     * Return label which shows computer's score
     */
    private GLabel returnComputerScores() {
        GLabel computerScoreLabel = label("Computer : 0", 0, 0);
        computerScoreLabel.setLocation(0, computerScoreLabel.getHeight());
        return computerScoreLabel;
    }

    /*
     * Method : void BallReset(GObject, double, double)
     * Reset ball location after it goes off the screen
     */
    private void ballReset(GObject ball, double vx, double vy) {
        ball.setLocation(WIDTH / 2, HEIGHT / 2);
        waitForClick();
        vx = randomVX();
    }

    /*
     * Method : void startingScreen()
     * Draw the starting screen
     */
    private void startScreen() {
        setBackground(Color.BLACK);
        GLabel openingScreenLabel = label("Welcome to Paddle Fight! " + POINTS_TO_WIN + " points to win. Click to start!", 0, 0);

        double openingScreenLabelX = (WIDTH - openingScreenLabel.getWidth()) / 2;
        double openingScreenLabelY = (HEIGHT - openingScreenLabel.getHeight()) / 2;

        openingScreenLabel.setLocation(openingScreenLabelX, openingScreenLabelY);
        add(openingScreenLabel);

        waitForClick();
        remove(openingScreenLabel);
    }

    /*
     * Method : void endScreen(int, int, GObject, GLabel, GLabel)
     * Remove all objects from the screen
     * Tell if the player won or lost
     */
    private void endScreen(int playerScore, int computerScore, GObject ball, GLabel computerScoreLabel, GLabel playerScoreLabel) {
        removeAll();
        GLabel endScreenLabel = label("", 0, 0);

        if (playerScore == POINTS_TO_WIN) {
        	add(win);
            endScreenLabel.setLabel("Wow, you did it! Click to play again!");
        }

        if (computerScore == POINTS_TO_WIN) {
        	add(lose);
            endScreenLabel.setLabel("Noob, you lost! Try again later!");
        }

        double endScreenLabelX = (WIDTH - endScreenLabel.getWidth()) / 2;
        double endScreenLabelY = (HEIGHT - endScreenLabel.getHeight()) / 2;

        endScreenLabel.setLocation(endScreenLabelX, endScreenLabelY);
        add(endScreenLabel);

        waitForClick();
        removeAll();
    }
}
