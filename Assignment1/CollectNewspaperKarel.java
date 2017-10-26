
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	/*
	 * Method : run()
	 * 
	 * pre-condition :
	 * Karel is on (3,4) coordinate with no beeper
	 * Beeper is on (6,3)
	 * 
	 * post-condition :
	 * Karel is on the same place
	 * There is also the beeper on this place which was on (6,3) previously
	 */
	public void run() {
		moveToBeeper();
		pickBeeper();
		returnToTheStartingPoint();
	}

	/*
	 * Method : moveToBeeper()
	 * 
	 * pre-condition :
	 * Karel is on the (3, 4) coordinate
	 * 
	 * post-condition :
	 * Karel is on (6, 3) coordinate
	 */
	private void moveToBeeper() {
		goStraight();
		goDown();
		goToBeeper();
	}
	
	/*
	 * Method : goStraight()
	 * 
	 * pre-condition :
	 * Karel's location : (x, y)
	 * 
	 * post-condition :
	 * Karel's location : (x + 2, y) if facing East
	 * 					  (x - 2, y) if facing West
	 */
	private void goStraight() {
		move();
		move();
	}
	
	/*
	 * Method : goDown()
	 * 
	 * pre-condition
	 * Karel if facing East
	 * Location : (5, 4)
	 * 
	 * post-condition :
	 * Karel is facing South
	 * Location : (5, 3)
	 */
	private void goDown() {
		turnRight();
		move();
	}
	
	/*
	 * Method : goToBeeper()
	 * 
	 * pre-condition :
	 * Karel is facing South
	 * Location : (5, 3)
	 * 
	 * post-condition :
	 * Karel is facing East
	 * Location : (6, 3)
	 * Karel has taken the beeper
	 */
	private void goToBeeper() {
		turnLeft();
		move();
	}

	/*
	 * Method : returnToTheStartingPoint()
	 * 
	 * pre-condition :
	 * Karel is on the (3, 4) coordinate
	 * 
	 * post-condition :
	 * Karel is on (6, 3) coordinate
	 */
	private void returnToTheStartingPoint() {
		goBack();
		goUp();
		turnLeft();
		goStraight();
		turnAround();
	}
	
	/*
	 * Method : goBack()
	 * 
	 * pre-condition :
	 * Location : (6, 3)
	 * Karel is facing  East
	 *
	 * post-condition :
	 * Location : (5, 3)
	 * Karel is facing West
	 */
	private void goBack() {
		turnAround();
		move();
	}

	/*
	 * Method : goUp()
	 * 
	 * pre-condition :
	 * Location : (5, 3)
	 * Karel if facing East
	 * 
	 * post-condition :
	 * Location : (5, 4)
	 * Karel if facing North
	 */
	private void goUp() {
		turnRight();
		move();
	}
}
