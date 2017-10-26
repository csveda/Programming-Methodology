/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import acm.util.ErrorException;
import acmx.export.java.io.FileReader;

public class HangmanLexicon {
	
	private ArrayList <String> wordList = new ArrayList <String> ();	
	
	public HangmanLexicon() {
		try {
			BufferedReader hangmanWords = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String line = hangmanWords.readLine();
				if (line == null) {
					break;
				}
				wordList.add(line);
			}
			hangmanWords.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}


/** Returns the word at the specified index. */
	public String getWord(int index) {
		return wordList.get(index);
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordList.size();
	}
}
