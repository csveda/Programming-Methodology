/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	/*
	 * Method : run()
	 * Enter number while it isn't equal to 0
	 * Print the smallest and largest numbers
	 */
	public void run() {
		
		printMessage();
		findRange();
		
	}
	
	/*
	 * Method : printMessage()
	 * Print message which tells us what to do in run()
	 */
	private void printMessage() {
		println("This program finds the largest and smallest numbers.");
	}
	
	/*
	 * Method : findRange()
	 */
	private void findRange() {
		
		int num = readInt("? ");
		int smallest = num;
		int largest = num;
		
		while (num != 0) {
			
			num = readInt("? ");
			smallest = changeSmallest(smallest, num);
			largest = changeLargest(largest, num);
			
		}
		
		printNumbers(smallest, largest);
	}
	
	/*
	 * Method : changeSmallest(int, int)
	 * Change value of smallest number
	 */
	private int changeSmallest(int smallest, int num) {
		
		if (smallest > num) {
			smallest = num;
		}
		return smallest;
		
	}
	
	/*
	 * Method : changeLargest(int, int)
	 * Change value of largest number
	 */
	private int changeLargest(int largest, int num) {
		
		if (largest < num) {
			largest = num;
		}
		return largest;
		
	}
	
	/*
	 * Method : printNumbers(int, int)
	 * Print values of the largest and smallest numbers
	 */
	private void printNumbers(int smallest,int largest) {
		println("smallest: " + smallest);
		println("largest: " + largest);
	}
}