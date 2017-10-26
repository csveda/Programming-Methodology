/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	/*
	 * Method : run()
	 * 
	 * pre-condition :
	 * Location : (1, 1)
	 * There are missing stones in the columns
	 * 
	 * post-condition
	 * Location : (n, 1)
	 * Missing stones has been replaced
	 */
	public void run() {
		
		while (frontIsClear()) {
			fillColumn();
			goBack();
			fourStepsForward();
		}
		
		fillColumn();
		goBack();
	}
	
	/*
	 * Method : fillColumn()
	 * 
	 * pre-condition :
	 * Location : (N, 1)
	 * There are missing stones in column N
	 * 
	 * post-condition :
	 * Location : (N, M)      M is a height of the column N
	 * Missing stones has been replaced
	 */
	private void fillColumn() {
		turnLeft();
		putBeeperIfEmpty();
		
		while (frontIsClear()) {
			move();
			putBeeperIfEmpty();
		}
	}

	/*
	 * Method : putBeeperIfEmpty()
	 * 
	 * pre-condition :
	 * There may not be beeper on current location
	 * 
	 * post-condition :
	 * There is beeper on current location
	 */
	private void putBeeperIfEmpty() {
		if (noBeepersPresent()) {
			putBeeper();
		}
	}

	/*
	 * Method : goBak()
	 * 
	 * pre-condition :
	 * Location : (N, M)           M is a height of the column N
	 * Karel is facing North 
	 * 
	 * post-condition :
	 * Location : (N, 1)
	 * Karel if facing East
	 */
	private void goBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
	/*
	 * Method : fourStepsForward()
	 * 
	 * pre-condition :
	 * Location : (N, 1)
	 * 
	 * post-condition :
	 * Location : (N + 4, 1) if there is another column
	 */
	private void fourStepsForward() {
		for (int i = 0; i < 4; i ++) {
			if (frontIsClear()) {
				move();
			}
		}
	}
}
