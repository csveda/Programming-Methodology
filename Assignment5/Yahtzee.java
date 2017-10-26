/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		
		// Number of players must be more than 1
		while (true) {
			nPlayers = dialog.readInt("Enter number of players");
			if (nPlayers > 1 && nPlayers <= MAX_PLAYERS) {
				break;
			}
		}	
		
		playerNames = new String[nPlayers];
		dieValues = new int[N_DICE];
		frequency = new int[DICE_NUM + 1];
		scores = new int[nPlayers + 1][N_CATEGORIES + 1];
		
		for (int i = 0; i <= nPlayers; i++) {
			for (int j = 0; j <= N_CATEGORIES; j++) {
				scores[i][j] = -1;
			}
		}
		
		for (int i = 1; i <= nPlayers; i++) {
			// Player's name can't be equal to ""
			while (true) {
				playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
				if (!playerNames[i - 1].equals("")) {
					break;
				}
			}
		}
		
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			for (int j = 1; j <= nPlayers; j++) {
				rollDice(j);
				selectCategory(j);
			}
		}
		
		// Calculate additional scores and total scores of players
		for (int i = 1; i <= nPlayers; i++){
			calculateAdditionalScores(i);
			calculateTotalScore(i);
		}
		
		findWinner();
	}
	
	/*
	 * Method : void rollDice()
	 * Roll dice 3 times and display them
	 */
	private void rollDice(int player) {
		for (int i = 0; i < N_DICE; i++) {
			int num = rgen.nextInt(1, 6);
			dieValues[i] = num;
		}
		
		display.printMessage(playerNames[player - 1] + "'s turn! Click the " + "\"Roll Dice\" " + "button to roll the dice.");
		display.waitForPlayerToClickRoll(player);
		display.displayDice(dieValues);
		
		for (int i = 0; i < 2; i++) {
			display.printMessage("Select the dice you wish to re-roll and click " + "\"Roll Again\"");
			display.waitForPlayerToSelectDice();
			
			for (int j = 0; j < N_DICE; j++) {
				if (display.isDieSelected(j) == true) {
					int num = rgen.nextInt(1,6);
					dieValues[j] = num;
				}
			}
			
			display.displayDice(dieValues);
		}
	}
	
	/*
	 * Method : void selectCategory(int)
	 * Select category to gain the score
	 * We shouldn't allow player to choose category which is already chosen
	 */
	private void selectCategory(int player) {
		display.printMessage("Select a category for this roll");
		while (true) {
			category = display.waitForPlayerToSelectCategory();
			if (scores[player][category] == -1) {
				calculateScore(player);
				break;
			}
			
			display.printMessage("You have already selected this category. Please select another one.");
		}
	}
	
	/*
	 * Method : void calulateScore(int)
	 * Find out if terms of the current category are true and update scores
	 */
	private void calculateScore(int player) {
		// Reset and then modify array which tells us quantity of each dice
		for (int i = 0; i <= DICE_NUM; i++) {
			frequency[i] = 0;
		}
				
		for (int i = 0; i < N_DICE; i++) {
			frequency[dieValues[i]]++;
		}
		
		// If category equals to : ONCES, TWOS, ..., SIXES
		if (category >= ONES && category <= SIXES) {
			int score = frequency[category] * category;
			scores[player][category] = score;
			
			display.updateScorecard(category, player, score);
			return ;
		}
		
		if (category == CHANCE || category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
			int score = 0;
			
			// If category equals to CHANCE or terms of THREE_OF_A_KIND and FOUR_OF_A_KIND are true
			if (category == CHANCE || (category == THREE_OF_A_KIND && checkCategory(3, 0)) || (category == FOUR_OF_A_KIND && checkCategory(4, 0))) {
				score = getTotalSum();
			}
			
			scores[player][category] = score;
			display.updateScorecard(category, player, score);
			return ;
		}
		
		if (category == FULL_HOUSE) {
			int score = 0;
			checkCategory(3, 2);
			
			if (bl1 && bl2) {
				score = 25;
			}
			
			scores[player][category] = score;
			display.updateScorecard(category, player, score);
			return ;
		}
				
		if (category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) {
			int score = 0;
			if (checkCategory(0, 0)) {
				score = (category - 9) * 10;
			}
			
			scores[player][category] = score;
			display.updateScorecard(category, player, score);
			return ;
		}
		
		if (category == YAHTZEE) {
			int score = 0;
			if (checkCategory(5, 0)){
				score = 50;
			}

			scores[player][category] = score;
			
			display.updateScorecard(category, player, score);
			return ;
		}
	}
	
	/*
	 * Method : boolean checkCategory(int, int)
	 * We give this function two integers
	 * If category equals to : THREE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, YAHTZE :
	 * it checks if maximum frequencies max1 >= x and max2 >= y. If true, changes bl1
	 * and bl2 values, returns true if max1 >= x, else returns false
	 * If category equals to : SMALL_STRAIGHT or LARGE_STRAIGHT :
	 * checks maximum length of straight and saves this value to integer length
	 */
	private boolean checkCategory(int x, int y) {
		if ((category >= THREE_OF_A_KIND && category <= FULL_HOUSE) || category == YAHTZEE) {
			int max1 = -1;
			int num = -1;
			int max2 = -1;
			
			for (int i = 1; i <= DICE_NUM; i++) {
				if (max1 <= frequency[i]) {
					max1 = frequency[i];
					num = i;
				}
			}
			
			for (int i = 1; i <= DICE_NUM; i++) {
				if (i != num) {
					if (max2 <= frequency[i]) {
						max2 = frequency[i];
					}
				}
			}
			
			if (max1 >= x) {
				bl1 = true;
			}
			else {
				bl1 = false;
			}
			
			if (max2 >= y) {
				bl2 = true;
			}
			else {
				bl2 = false;
			}
			
			if (max1 >= x) {
				return true;
			}
			
			return false;
		}
		
		// If category == SMALL_STRAIGHT || category == LARGE_STRAIGHT
		for (int i = 1; i <= 3; i++) {
			if (frequency[i] >= 1) {
				int length = 0;
				int j = 0;
				
				for (j = i + 1; j <= DICE_NUM; j++) {
					if (frequency[j] < 1) {
						break;
					}
				}
				
				length = j - i;
				if (category == SMALL_STRAIGHT && length >= 4) {
					return true;
				}
				
				if (category == LARGE_STRAIGHT && length == 5) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/*
	 * Method : int getTotalSum()
	 * Return sum of all elements in array dieValues
	 */
	private int getTotalSum() {
		int sum = 0;
		for (int  i = 0; i < N_DICE; i++) {
			sum += dieValues[i];
		}
		
		return sum;
	}
	
	/*
	 * Method : void calculateAdditionalScores()
	 * Calculate values of Upper Score, Upper Bonus, Lower Score
	 */
	private void calculateAdditionalScores(int player) {
		int score = calculate(ONES, SIXES, player);
		scores[player][UPPER_SCORE] = score;
		display.updateScorecard(UPPER_SCORE, player, score);
		
		if (scores[player][UPPER_SCORE] >= 63) {
			score = 35;
			scores[player][UPPER_BONUS] = score;
		}
		else {
			score = 0;
		}
		display.updateScorecard(UPPER_BONUS, player, score);

		score = calculate(THREE_OF_A_KIND, CHANCE, player);
		scores[player][LOWER_SCORE] = score;
		display.updateScorecard(LOWER_SCORE, player, score);
	}
	
	/*
	 * Method : calculateTotalScores(player)
	 * Calculate total score of current player
	 */
	private void calculateTotalScore(int player) {
		int score = calculate(ONES, LOWER_SCORE, player) + 1;
		scores[player][TOTAL] = score;
		display.updateScorecard(TOTAL, player, score);
	}
	
	/*
	 * Method : int calculate(int, int, int)
	 * I give 3 integers to this function : x, y, player
	 * It returns sum of the scores of player from x to y
	 */
	private int calculate(int x, int y, int player) {
		int sum = 0;
		
		for (int i = x; i <= y; i++) {
			sum += scores[player][i];
		}
		
		return sum;
	}
	
	/*
	 * Method : void findWinner()
	 * Find out who won the match and show winning message
	 */
	private void findWinner() {
		int max = scores[1][TOTAL];
		int num = 1;
		
		for (int i = 2; i <= nPlayers; i++) {
			if (max < scores[i][TOTAL]) {
				max = scores[i][TOTAL];
				num = i;
			}
		}
		
		// It may happen that two players have equal scores, so we should print message that it's draw
		for (int i = 1; i <= nPlayers; i++) {
			if (num != i) {
				if (max == scores[i][TOTAL]) {
					display.printMessage("Two players have the same scores, so it's draw!");
					return ;
				}
			}
		}
		
		display.printMessage("Congratulations, " + playerNames[num - 1] + ", is the winner with a total score of " + scores[num][TOTAL] + "!");
	}
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dieValues;
	private int[] frequency;
	private int category;
	private int[][] scores;
	private boolean bl1 = false;
	private boolean bl2 = false;
}
