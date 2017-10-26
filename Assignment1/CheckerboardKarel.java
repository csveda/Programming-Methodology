/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	/*
	 * Method : run()
	 * 
	 * pre-condition :
	 * Location : (1, 1)
	 * Checkerboard is empty
	 * 
	 * post-condition :
	 * Location : (1, M) or (N, M)
	 * Location depends on the number of rows
	 * If this number is odd (1, M) otherwise (N, M)
	 * There are beepers on the white squares of checkerboard
	 */
	public void run() {
		putBeeper();
		completeOddLine();
	}
	
	/*
	 * Method : completeOddLine();
	 * 
	 * pre-condition :
	 * Karel stands on line with number of odd
	 * This line is empty
	 * 
	 * post-condition :
	 * Karel stands on the same line but on another corner
	 * Line has been filled
	 * Karel starts filling another next line
	 */
	private void completeOddLine() {
		fillThisLine();
		turnLeft();
		startFillingEvenLine();
	}
	
	/*
	 * Method : completeEvenLine();
	 * 
	 * pre-condition :
	 * Karel stands on line with number of Even
	 * This line is empty
	 * 
	 * post-condition :
	 * Karel stands on the same line but on another corner
	 * Line has been filled
	 * Karel starts filling another next line
	 */
	private void completeEvenLine() {
		fillThisLine();
		turnRight();
		startFillingOddLine();
	}
	
	/*
	 * Method : fillThisLine()
	 * 
	 * pre-condition :
	 * The line karel stands on is empty
	 * 
	 * post-condition
	 * The line has been filled
	 */
	private void fillThisLine() {
		while (frontIsClear()) {
			if (beepersPresent()) {
				move();
			}
			else {
				move();
				putBeeper();
			}
		}
	}
	
	/*
	 * Method : startFillingOddLine()
	 * 
	 * pre-condition :
	 * Karel stands on line with number of even
	 * 
	 * post-condition :
	 * If next line exists karel starts filling it
	 */
	private void startFillingOddLine() {
		if (frontIsClear()) {
			if (beepersPresent()) {
				move();
				turnRight();
				if (frontIsClear()) {
					move();
					putBeeper();
				}
			}
			else {
				move();
				turnRight();
				putBeeper();
			}
			
			completeOddLine();
		}
	}
	
	/*
	 * Method : startFillingEvenLine()
	 * 
	 * pre-condition :
	 * Karel stands on line with number of odd
	 * 
	 * post-condition :
	 * If next line exists karel starts filling it
	 */
	private void startFillingEvenLine() {
		if (frontIsClear()) {
			if (beepersPresent()) {
				move();
				turnLeft();
				if (frontIsClear()) {
					move();
					putBeeper();
				}	
			}
			else {
				move();
				putBeeper();
				turnLeft();
			}
			
			completeEvenLine();
		}
	}
}
