/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	
	/*
	 * Method : run()
	 * Print welcome message
	 * Input values of cathetus
	 * Count hypotenuse and print it's value
	 */
	public void run() {
		printMessage();
		printAnswer();
	}
	
	/* 
	 * Method : printMessage()
	 * Prints message which tells us what to do in run()
	 */
	private void printMessage() {
		println("Enter Values to compute Pythagorean theorem.");
	}
	
	/*
	 * Method printAnswer()
	 * Input values of cathetus
	 * Count hypotenuse and print it's value
	 */
	public void printAnswer() {
		int cathetus1 = inputNumber(true);
		int cathetus2 = inputNumber(false);
		countHypotenuse(cathetus1, cathetus2);
	}
	
	/*
	 * Method : inputNumber(boolean)
	 * Give entd number's value to cathetus
	 */
	private int inputNumber(boolean bl) {
		/* On first attempt we need to enter cathetus1 */
		if (bl) {
			int num = readInt("a: ");
			return num;
		}
		
		/* On second attempt we need to enter cathetus2 */
		int num = readInt("b: ");
		return num;
	}
	
	/* 
	 * Method : countHypotenuse(int, int)
	 * Enter values of cathetus and return value of hypotenuse
	 */
	private void countHypotenuse(int cathetus1, int cathetus2) {
		double hypotenuse = Math.sqrt(cathetus1 * cathetus1 + cathetus2 * cathetus2);
		println("c: " + hypotenuse);
	}
}