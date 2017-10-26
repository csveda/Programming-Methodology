/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	/*
	 * Method : run()
	 * Input number
	 * Change it's value while it isn't equal to 1
	 * Print how many steps were made
	 */
	public void run() {
		printAnswer(countAnswer());
	}
	
	/*
	 * Method : countAnswer()
	 * Count how many steps were made
	 */
	private int countAnswer() {
		
		int count = reachNumber1(inputNumber());
		return count;
		
	}
	
	/*
	 * Method : reachNumber1(int)
	 * 
	 * pre-condition :
	 * NUM equals to the input number
	 * count = 0
	 * 
	 * post-condition
	 * NUM = 1
	 * There is answer in COUNT
	 * 
	 */
	private int reachNumber1(int num) {
		
		int count = 0;
		while (num != 1) {
			num = changeValue(num);
			count++;
		}
		return count;
		
	}
	
	/*
	 * Method : inputNumber()
	 * Input number to count the  answer
	 */
	private int inputNumber() {
		
		int number = readInt("Enter a number: ");
		return number;
		
	}
	
	/*
	 * Method : changeValue(int)
	 * Change value of NUM
	 */
	private int changeValue(int num) {
		
		/* In case of num is odd */
		if (num % 2 == 1) {
			int newNum = 3 * num + 1;
			println(num + " is odd, so I make 3n + 1: " + newNum);
			return newNum;
		}
		
		/* In case of num is even */
		int newNum = num / 2;
		println(num + " is even, so I take half: " + newNum);
		return newNum;
		
	}
	
	/*
	 * Method : printAnswer()
	 * Print how many steps were made to reach number 1
	 */
	private void printAnswer(int count) {
		println("The process took " + count + " to reach 1");
	}
}


