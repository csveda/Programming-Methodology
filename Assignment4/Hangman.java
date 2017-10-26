/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	
	private HangmanCanvas canvas;
	
	/* Word at the beginning. Contains "-" symbol only */
	private String currentWord;
	
	/* Word to guess*/
	private String word;
	
	/* Number of guesses left current moment of playing */
	private int guessesLeft;
	
	private String str;
	
	private char letter;
	
	private String guesses;
	
	/* If i index of list is true, this means entered letter is located on i index of the word */
	private boolean[] list;
	
	private HangmanLexicon hangmanWords;
	
	private String incorrectLetters = "";
	
	private int numberOfSymbols = 0;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private boolean win = false;
	
    public void run() {
		setUpGame();
		playGame();
    }
    
    /*
     * Method : void setUpGame()
     * Reset game to start another one
     * Print starting texts
     */
    private void setUpGame() {
    	resetGame();
    	println("Welcome to Hangman!");
    	println("The word now looks like this: " + currentWord);
    	println("You have " + guessesLeft + " guesses left.");
    }
    
    /*
     * Method : void resetGame()
     * Reset game to start another one
     */
    private void resetGame() {
    	canvas.reset();
    	guessesLeft = 8;
    	currentWord = "";
    	
    	guesses = "";
    	word = pickWord();
    	list = new boolean[word.length()];
    	
    	for (int i = 0; i < word.length(); i++) {
    		currentWord += "-";
    	}
    }
    
    /*
     * Method : playGame()
     * Start playing hangman
     * Change console
     * Change graphics
     */
    private void playGame() {
    	while (guessesLeft > 0) {
    		while (true) {
    			str = readLine("Your guess: ");
    			if (str.length() != 1) {
    				println("You can only enter one symbol!");
    				continue;
    			}
    			
    			boolean bl = false;
    			
    			// Change string and char to uppercase
    			str = str.toUpperCase();
    			char ch = str.charAt(0);
    			
    			for (int i = 0; i < guesses.length(); i++) {
    				if (ch == guesses.charAt(i)) {
    					println("This letter has already written. Enter another one!");
    					bl = true;
    					break;
    				}
    			}
    			
    			if (bl) {
    				continue;
    			}
    			
    			break;
    		}
    		
    		// Change string and char to uppercase
    		str = str.toUpperCase();
    		letter = str.charAt(0);

    		guesses += letter;
    		
    		// Check if word contains entered letter
    		if (checkWord()) {
    			println("That guess is correct.");
    		}
    		else {
    			println("There are no " + letter + "'s in the word.");
    			incorrectLetters += letter;
    			canvas.noteIncorrectGuess(incorrectLetters);
    			guessesLeft--;
    		}
    		
    		if (numberOfSymbols < word.length()) {
    			print("The word now looks like this: ");
    			for (int i = 0; i < word.length(); i++) {
    				if (list[i] == true) {
    					print(word.charAt(i));
    				}
    				else {
    					print("-");
    				}
    			}
    			println();
    			
    			if (guessesLeft == 1) {
    				println("You have only one guess left.");
    			}
    			else {
    				println("You have " + guessesLeft + " guesses left.");
    			}
    		}
    		else {
    			if (numberOfSymbols == word.length()) {
    				win = true;
    				println("You guessed the word: " + word);
    				println("You win.");
    				canvas.displayWord(word);
    				break;
    			}
    		}
				
    	}
    	
    	if (win == false) {
    		println("You're completely hung.");
    		println("The word was: " + word);
    		println("You loose.");
    		canvas.displayWord(word);
    	}
    	
    	startNext();
    }
    
    /*
     * Method : void startNext()
     * This method starts after we reset game
     */
    private void startNext() {
    	resetGame();
    	for (int i = 0; i < 5; i++) {
    		println();
    	}
    	
    	println("The word now looks like this: " + currentWord);
    	println("You have " + guessesLeft + " guesses left.");
    	playGame();
    }
    
    /*
     * Method : String pickWord()
     * Choose word
     */
    private String pickWord() {
    	hangmanWords = new HangmanLexicon();
    	int randomWord = rgen.nextInt(0, (hangmanWords.getWordCount() - 1)); 
    	String pickedWord = hangmanWords.getWord(randomWord);
    	return pickedWord;
    }
    
    /*
     * Method : void checkWord()
     * Check if word contains letter
     * If contains modify list
     */
    private boolean checkWord() {
    	boolean bl = false;
    	for (int i = 0; i < word.length(); i++) {
    		if (letter == word.charAt(i) && list[i] != true) {
    			list[i] = true;
    			numberOfSymbols++;
    			bl = true;
    		}
    	}
    	return bl;
    }
    
    public void init() {
    	canvas = new HangmanCanvas(); 
    	add(canvas); 
    }
}
