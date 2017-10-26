/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
	
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// Fill North side of the screen
		addLabel("Name ", "NORTH");
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		
		addButton("Add", "NORTH");
		addButton("Delete", "NORTH");
		addButton("Lookup", "NORTH");
		
		// Fill West side of the screen
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		
		addButton("Change Status", "WEST");
		addLabel(EMPTY_LABEL_TEXT, "WEST");
		
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		add(pictureField, WEST);
		
		addButton("Change Picture", "WEST");
		addLabel(EMPTY_LABEL_TEXT, "WEST");
		
		friendField = new JTextField(TEXT_FIELD_SIZE);
		add(friendField, WEST);
		addButton("Add Friend", "WEST");
		
		// Add action listeners
		addActionListeners();
		statusField.addActionListener(this);
		pictureField.addActionListener(this);
		friendField.addActionListener(this);
		
		// Add canvas
		canvas = new FacePamphletCanvas();
		add(canvas);
		
		profileData = new FacePamphletDatabase();
		currentProfile = null;
    }
	
	/**
	 * Method : void addLabel(String, String)
	 * Add label
	 * @param str
	 * @param side
	 */
	private void addLabel(String str, String side) {
		if (side.equals("NORTH")) {
			add(new JLabel(str), NORTH);
		} else {
			add(new JLabel(str), WEST);
		}
	}
	
	/**
	 * Method : void addButton(String, String)
	 * Add button
	 * @param str
	 * @param side
	 */
	private void addButton(String str, String side) {
		if (side.equals("NORTH")) {
			add(new JButton(str), NORTH);
		} else {
			add(new JButton(str), WEST);
		}
	}
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
	
    public void actionPerformed(ActionEvent e) {
    	
    	String name = nameField.getText();
    	
    	// If "Add" button is clicked
    	if (e.getActionCommand().equals("Add") && name.length() != 0) {
    		if (profileData.containsProfile(name) == false) {
    			FacePamphletProfile profile = new FacePamphletProfile(name);
    			profileData.addProfile(profile);
    			
    			canvas.displayProfile(profile);
    			canvas.showMessage("New profile created");
    			currentProfile = profile;
    		} else {
    			FacePamphletProfile profile = profileData.getProfile(name);
    			canvas.displayProfile(profile);
    			canvas.showMessage("A profile with name " + name + " already exists.");
    			currentProfile = profile;
    		}
    	}
    	
    	// If "Delete" button is clicked
    	if (e.getActionCommand().equals("Delete") && name.length() != 0) {
    		canvas.removeAll();
    		currentProfile = null;
    		
    		if (profileData.containsProfile(name) == true) {
    			profileData.deleteProfile(name);
    			canvas.showMessage("Profile of " + name + " deleted");
    		} else {
    			canvas.showMessage("A profile with name " + name + " does not exist.");
    		}
    	}
    	
    	// If "Lookup" button is clicked
    	if (e.getActionCommand().equals("Lookup") && name.length() != 0) {
    		canvas.removeAll();
    		if (profileData.containsProfile(name) == true) {
    			FacePamphletProfile profile = profileData.getProfile(name);
    			canvas.displayProfile(profile);
    			canvas.showMessage("Displaying " + name);
    			currentProfile = profile;
    		} else {
    			canvas.showMessage("A profile with name " + name + " does not exist.");
    			currentProfile = null;
    		}
    	}
    	
    	// If "Change Status" button is clicked
    	if ((e.getActionCommand().equals("Change Status") || e.getSource() == statusField) && !statusField.getText().equals("")){
    		String status = statusField.getText();
    		if (currentProfile != null) {
    			FacePamphletProfile profile = profileData.getProfile(currentProfile.getName());
    			profile.setStatus(profile.getName() + " is " + status);
    			canvas.displayProfile(profile);
    			canvas.showMessage("Status updated to " + status);
    		} else {
    			canvas.showMessage("Please select a profile to change status");
    		}
    	}
    	
    	// If "Change Picture" button is clicked
    	if (e.getActionCommand().equals("Change Picture") || e.getSource() == pictureField && !pictureField.getText().equals("")){
    		String filename = pictureField.getText();
    		if (currentProfile != null) {
    			FacePamphletProfile profile = profileData.getProfile(currentProfile.getName());
    			GImage image = null;
    			try {
    				image = new GImage(filename);
    				profile.setImage(image);
    			} catch (ErrorException ex) {
    				image = null;
    			}
    			canvas.displayProfile(profile);
    			if (image == null) {
    				canvas.showMessage("Unable to open image file: " + filename);
    			}
    			else {
    				canvas.showMessage("Picture updated");
    			}
    		}
    		else {
    			canvas.showMessage("Please select a profile to change picture");
    		}
    	}
    	
    	// If "Add Friend" button is clicked
    	if (e.getActionCommand().equals("Add Friend") || e.getSource() == friendField && !friendField.getText().equals("")) {
    		String friendName = friendField.getText();
    		// Check if current profile exists
    		if (currentProfile == null) {
    			canvas.showMessage(friendName + " does not exist.");
    		} else {
    			FacePamphletProfile profile = profileData.getProfile(currentProfile.getName());
    			// Profile isn't able to add himself/herself
    			if (profile.getName().equals(friendName)) {
    				canvas.showMessage("It's your profile.");
    			}
    			// Check if the profile with the entered name exists
    			else if (profileData.containsProfile(friendName)) {
    				FacePamphletProfile friendProfile = profileData.getProfile(friendName);
    				
    				// These two profiles may already be friends
    				if (profile.addFriend(friendName) == false) {
    					canvas.showMessage("You are already friends.");
    				} else {
    					profile.addFriend(friendName);
    					friendProfile.addFriend(name);
    					canvas.displayProfile(profile);
    					canvas.showMessage(friendName + " added as a friend.");
    				}
    			}
    			else {
    				canvas.showMessage("A profile with the name " + friendName + " does not exist.");
    			}
    		}	
    	}	
    }
    
    /* Private instance variables */
  	private JTextField nameField;
  	private JTextField statusField;
  	private JTextField pictureField;
  	private JTextField friendField;
  	
  	private FacePamphletDatabase profileData;
  	private FacePamphletCanvas canvas;
  	private FacePamphletProfile currentProfile;
  	
}
