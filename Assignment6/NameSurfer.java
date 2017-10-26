/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	
	/* Private instance variables */ 
	private JLabel label;
	private JTextField Name;
	private JButton Graph;
	private JButton Clear;
	private NameSurferDataBase namesdb;
	private NameSurferGraph graph;

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		label = new JLabel("Name ");
		add(label, SOUTH);
		
		Name = new JTextField("");
		Name.setEnabled(true);
		Name.setColumns(20);
		Name.addActionListener(this);
		add(Name, SOUTH);
		
		Graph = new JButton("Graph");
		add(Graph, SOUTH);
		
		Clear = new JButton("Clear");
		add(Clear, SOUTH);
		
		graph = new NameSurferGraph();
		add(graph);
		
		addActionListeners();	
		namesdb = new NameSurferDataBase(NAMES_DATA_FILE);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Clear")) {
			graph.clear();
			graph.update();
		}
		else {
			String enteredName = Name.getText();
			NameSurferEntry rankings = namesdb.findEntry(enteredName);
			
			if (rankings != null) {
				graph.addEntry(rankings);
				graph.update();
			}
		}
	}
}
