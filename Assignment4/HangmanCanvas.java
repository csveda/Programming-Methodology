/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		drawScaffold();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		double x = getWidth() / 4;
		double y = getHeight() - HEAD_RADIUS * 2;
		
		GLabel hiddenWord = new GLabel(word, x, y);
		hiddenWord.setFont("Consolas-30");

		if (getElementAt(x, y) != null){
			remove(getElementAt(x, y));
		}
		add(hiddenWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(String incorrectGuesses) {
		
		double x = getWidth() / 4;
		double y = getHeight() - HEAD_RADIUS;
		GLabel incorrectLetters = new GLabel(incorrectGuesses, x, y);
		
		if(getElementAt(x,y) != null) {
			remove(getElementAt(x,y));
		}
		add(incorrectLetters);
		
		switch (incorrectGuesses.length()) {
		case 1: drawHead();
				break;
		case 2: drawBody();
				break;
		case 3: drawLeftArm();
				break;
		case 4: drawRightArm();
				break;
		case 5: drawLeftLeg();
				break;
		case 6: drawRightLeg();
				break;
		case 7: drawLeftFoot();
				break;
		case 8: drawRightFoot();
		}
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	/*
	 * Method : void drawScaffold()
	 * Draw scaffold
	 */
	private void drawScaffold() {
		double scaffoldTopX = getWidth() / 2 - UPPER_ARM_LENGTH;
		double scaffoldTopY = getHeight() / 2 - BODY_LENGTH - HEAD_RADIUS * 2 - ROPE_LENGTH;
		
		double scaffoldBottomY = scaffoldTopY + SCAFFOLD_HEIGHT;
		GLine scaffold= new GLine (scaffoldTopX, scaffoldTopY, scaffoldTopX, scaffoldBottomY);
		add(scaffold);
		
		double beamRightX = scaffoldTopX + BEAM_LENGTH;
		GLine beam = new GLine(scaffoldTopX, scaffoldTopY, beamRightX, scaffoldTopY);
		add(beam);
		
		double ropeBottomY = scaffoldTopY + ROPE_LENGTH;
		GLine rope = new GLine (beamRightX, scaffoldTopY, beamRightX, ropeBottomY);
		add(rope);
	}
	
	/*
	 * Method : void drawHead()
	 * Draw head
	 */
	private void drawHead() {
		double x = getWidth()/2 - UPPER_ARM_LENGTH + BEAM_LENGTH - HEAD_RADIUS;
		double y = getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2;
		
		GOval head = new GOval (x, y, HEAD_RADIUS*2, HEAD_RADIUS*2);
		add(head);
	}
	
	/*
	 * Method : void drawBody()
	 * Draw body
	 */
	private void drawBody() {
		double x = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS;
		double y1 = getHeight() / 2 - BODY_LENGTH;
		double y2 = y1 + BODY_LENGTH;
		drawLine(x, y1, x, y2);
	}
	
	/*
	 * Method : void drawLine()
	 * Draw line according to coordinates : (x1, y1) (x2, y2)
	 */
	private void drawLine(double x1, double y1, double x2, double y2) {
		GLine line = new GLine(x1, y1, x2, y2);
		add(line);
	}
	
	/*
	 * Method : void drawLeftArm()
	 * Draw left arm
	 */
	private void drawLeftArm() {
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS;
		double x2 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS - UPPER_ARM_LENGTH;
		double y1 = getHeight() / 2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		double y2 = y1 + LOWER_ARM_LENGTH;
		
		drawLine(x1, y1, x2, y1);
		drawLine(x2, y1, x2, y2);
	}
	
	/*
	 * Method : void drawRightArm()
	 * Draw right arm
	 */
	private void drawRightArm() {
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS;
		double x2 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS + UPPER_ARM_LENGTH;
		double y1 = getHeight() / 2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		double y2 = y1 + LOWER_ARM_LENGTH;
		
		drawLine(x1, y1, x2, y1);
		drawLine(x2, y2, x2, y1);
	}
	
	/*
	 * Method : void drawLeftleg()
	 * Draw left leg
	 */
	private void drawLeftLeg() {
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS;
		double x2 = x1 - HIP_WIDTH;
		double y1 = getHeight() / 2;
		double y2 = y1 + LEG_LENGTH;
		
		drawLine(x1, y1, x2, y1);
		drawLine(x2, y2, x2, y1);
	}
	
	/*
	 * Method : void drawRightLeg()
	 * Draw right leg
	 */
	private void drawRightLeg() {
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS;
		double x2 = x1 + HIP_WIDTH;
		double y1 = getHeight() / 2;
		double y2 = y1 + LEG_LENGTH;
		
		drawLine(x1, y1, x2, y1);
		drawLine(x2, y1, x2, y2);
	}
	
	/*
	 * Method : void drawLeftFoot()
	 * Draw left foot
	 */
	private void drawLeftFoot() {
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH / 2 + HEAD_RADIUS - HIP_WIDTH;
		double x2 = x1 - FOOT_LENGTH;
		double y = getHeight() / 2 + LEG_LENGTH;
		drawLine(x1, y, x2, y);
	}
	
	/*
	 * Method : void drawRightFoot()
	 * Draw right foot
	 */
	private void drawRightFoot() {
		double x1 = getWidth()/2 + UPPER_ARM_LENGTH/2 + HEAD_RADIUS + HIP_WIDTH;
		double x2 = x1 + FOOT_LENGTH;
		double y = getHeight()/2 + LEG_LENGTH;
		drawLine(x1, y, x2, y);
	}
}
