/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	/*
	 * Method : run()
	 * 
	 * pre-condition :
	 * Location : (1, 1)
	 * Line is empty
	 * 
	 * post-condition :
	 * Location : Midpoint
	 * Karel has put the beeper on midpoint
	 */
	public void run() {
		coverLine();
		eraseBeepers();
		moveOnMidpoint();
	}
	
	/*
	 * Method : coverLine()
	 * 
	 * pre-condition :
	 * Location : (1,1)
	 * Line is empty
	 * 
	 * post-condition
	 * Location : Midpoint
	 * Line is filled with beepers
	 */
	private void coverLine() {
		while (noBeepersPresent()){
			putBeeper();
			if (frontIsClear()) {
				move();
				putOnLatestEmpty();
			}
		}
	}
	
	/*
	 * Method : putOnLatestEmpty()
	 * 
	 * pre-condition :
	 * Location : x
	 * 
	 * post-condition :
	 * Loation : y
	 * Karel has put the beeper on (y - 1) or (y + 1) coordinate if they exist
	 * Direction has changed ( Karel has turned around)
	 */
	private void putOnLatestEmpty() {
		while (noBeepersPresent()) {
			if (frontIsClear()) {
				move();
			} else {
				putBeeper();
			}
		}
		
		turnAround();
		move();
	}
	
	/*
	 * Method : eraseBeepers()
	 * 
	 * pre-condition
	 * Location : Midpoint
	 * Line is filled with beepers
	 * 
	 * post-condition
	 * Location : Midpoint
	 * There are no beepers on the line except midpoint
	 */
	private void eraseBeepers() {
		eraseAfterThis();
		moveOnMidpoint();
		eraseAfterThis();
	}
	
	/*
	 * Method : eraseAfterThis()
	 * 
	 * pre-condition :
	 * Location : Midpoint
	 * The line after this coordinate is filled with beepers
	 * 
	 * post-condition :
	 * Location : Corner (1, 1) or (n, 1)
	 * Beepers after midpoint has been erased
	 */
	private void eraseAfterThis() {
		while (frontIsClear()) {
			move();
			pickBeeper();
		}
	}
	
	/*
	 * Method : moveOnMidpoint()
	 * 
	 * pre-condition :
	 * Location : Corner (1, 1) or (n, 1)
	 * 
	 * post-condition :
	 * Location : Midpoint
	 */
	private void moveOnMidpoint() {
		turnAround();
		while (noBeepersPresent()) {
			move();
		}
	}
}
