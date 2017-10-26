package extension;

/* 
 /* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.io.IODialog;
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
		
		for (int i = 0; i < 12; i++) {
			addLabel(EMPTY_LABEL_TEXT, "NORTH");
		}
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		
		searchButton = new JButton("Search");
		add(searchButton, NORTH);
		
		followButton = new JButton("Follow");
		add(followButton, NORTH);
		
		messagesButton = new JButton("Messages");
		add(messagesButton, NORTH);
		
		matualFriendsButton = new JButton("Matual Friends");
		add(matualFriendsButton, NORTH);
		
		changeStatusButton = new JButton("Change Status");
		add(changeStatusButton, NORTH);
		
		changePictureButton = new JButton("Change Picture");
		add(changePictureButton, NORTH);
		
		changePasswordButton = new JButton("Change Password");
		add(changePasswordButton, NORTH);
		
		// Fill West side of the screen
		addLabel("Username", "WEST");
		loginUsernameField = new JTextField(TEXT_FIELD_SIZE);
		add(loginUsernameField, WEST);
		
		addLabel("Password", "WEST");
		loginPasswordField = new JTextField(TEXT_FIELD_SIZE);
		add(loginPasswordField, WEST);
		
		loginButton = new JButton("Log In");
		add(loginButton, WEST);
		
		logoutButton = new JButton("Log Out");
		add(logoutButton, WEST);
		
		addLabel(EMPTY_LABEL_TEXT, "WEST");
		
		addLabel("Username", "WEST");
		registerUsernameField = new JTextField(TEXT_FIELD_SIZE);
		add(registerUsernameField, WEST);
		
		addLabel("Name", "WEST");
		registerNameField = new JTextField(TEXT_FIELD_SIZE);
		add(registerNameField, WEST);
		
		addLabel("Surname", "WEST");
		registerSurnameField = new JTextField(TEXT_FIELD_SIZE);
		add(registerSurnameField, WEST);
		
		addLabel("Password", "WEST");
		registerPasswordField = new JTextField(TEXT_FIELD_SIZE);
		add(registerPasswordField, WEST);
		
		addLabel("Repeat Password", "WEST");
		registerRepeatedPasswordField = new JTextField(TEXT_FIELD_SIZE);
		add(registerRepeatedPasswordField, WEST);
		
		// Add combobox to choose gender
		gender = new JComboBox <String>();
		gender.addItem("Male");
		gender.addItem("Female");
		add(gender, WEST);
		
		addLabel("City", WEST);
		cityField = new JTextField(TEXT_FIELD_SIZE);
		add(cityField, WEST);
		
		addLabel("Country", WEST);
		countryField = new JTextField(TEXT_FIELD_SIZE);
		add(countryField, WEST);
		
		registerButton = new JButton("Register");
		add(registerButton, WEST);
		
		for (int i = 0; i < 10; i++) {
			addLabel(EMPTY_LABEL_TEXT, "WEST");
		}
		
		/** Add action listeners */
		addActionListeners();
		
		// Buttons' listeners
		searchButton.addActionListener(this);
		followButton.addActionListener(this);
		messagesButton.addActionListener(this);
		matualFriendsButton.addActionListener(this);
		loginButton.addActionListener(this);
		logoutButton.addActionListener(this);
		registerButton.addActionListener(this);
		changeStatusButton.addActionListener(this);
		changePictureButton.addActionListener(this);
		changePasswordButton.addActionListener(this);
		
		// JTextFields' listeners
		loginUsernameField.addActionListener(this);
		loginPasswordField.addActionListener(this);
		registerUsernameField.addActionListener(this);
		registerNameField.addActionListener(this);
		registerSurnameField.addActionListener(this);
		registerPasswordField.addActionListener(this);
		registerRepeatedPasswordField.addActionListener(this);
		cityField.addActionListener(this);
		countryField.addActionListener(this);
		
		canvas = new FacePamphletCanvas();
		add(canvas);
		profilesData = new FacePamphletDatabase();
		profile = null;
		
		// Disable some buttons
		searchButton.setEnabled(false);
		logoutButton.setEnabled(false);
		followButton.setEnabled(false);
		messagesButton.setEnabled(false);
		matualFriendsButton.setEnabled(false);
		changeStatusButton.setEnabled(false);
		changePictureButton.setEnabled(false);
		changePasswordButton.setEnabled(false);
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
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Log In")) {
    		String username = loginUsernameField.getText();
    		String password = loginPasswordField.getText();
    		
    		profile = profilesData.getProfile(username);
    		if (profile == null) {
    			canvas.showMessage("Such profile doesn't exist");
    		} else {
    			if (profile.getPassword().equals(password)) {
    				canvas.displayProfile(profile);
    				loginButton.setEnabled(false);
    				followButton.setEnabled(true);
    				logoutButton.setEnabled(true);
    				registerButton.setEnabled(false);
    				searchButton.setEnabled(true);
    				messagesButton.setEnabled(true);
    				changeStatusButton.setEnabled(true);
    				changePictureButton.setEnabled(true);
    				changePasswordButton.setEnabled(true);
    			} else {
    				canvas.showMessage("Wrong Password");
    			}
    		}
    	}
    	
    	if (e.getActionCommand().equals("Log Out")) {
    		canvas.removeAll();
    		loginButton.setEnabled(true);
    		registerButton.setEnabled(true);
    		logoutButton.setEnabled(false);
    		searchButton.setEnabled(false);
    		followButton.setEnabled(false);
    		messagesButton.setEnabled(false);
    		changeStatusButton.setEnabled(false);
    		changePictureButton.setEnabled(false);
    		changePasswordButton.setEnabled(false);
    		profile = null;
    		
    		// Save data in text document
    		profilesData.saveData();
    	}
    	
    	if (e.getActionCommand().equals("Register")) {   		
    		String username = registerUsernameField.getText();
    		String name = registerNameField.getText();
    		String surname = registerSurnameField.getText();
    		String password = registerPasswordField.getText();
    		String repeatedPassword = registerRepeatedPasswordField.getText();
    		String city = cityField.getText();
    		String country = countryField.getText();
    		
    		if (profilesData.containsProfile(username)) {
    			canvas.showMessage("Profile with the username " + username + " has already created");
    		} else {
    			if (!password.equals(repeatedPassword)) {
    				canvas.showMessage("Passwords don't match");
    			} else {
    				if (username.length() == 0 || name.length() == 0 || surname.length() == 0 || password.length() == 0 || repeatedPassword.length() == 0) {
    					canvas.showMessage("Data can't be null");
    				} else {
    					String profileGender;
    					profileGender = gender.getSelectedItem().toString();
        				
    					String status = "No current status";
    					profile = new FacePamphletProfile(username, name, surname, password, profileGender, status, city, country);
    					profilesData.addProfile(profile);
    					canvas.displayProfile(profile);
        				
    					registerButton.setEnabled(false);
    					loginButton.setEnabled(false);
        				logoutButton.setEnabled(true);
        				followButton.setEnabled(true);
        				registerButton.setEnabled(false);
        				searchButton.setEnabled(true);
        				messagesButton.setEnabled(true);
        				changeStatusButton.setEnabled(true);
        				changePictureButton.setEnabled(true);
        				changePasswordButton.setEnabled(true);
    				}
    			}
    		}
    	}
    	
    	if (e.getActionCommand().equals("Search") && nameField.getText().length() != 0) {
    		String personUsername = nameField.getText();
    		if (!profilesData.containsProfile(personUsername)) {
    			canvas.showMessage("Such profile doesn't exist");
    		} else {
    			person = profilesData.getProfile(personUsername);
    			canvas.displayProfile(person);
        		if (!profile.getUsername().equals(person.getUsername())) {
        			canvas.showMessage("mafiebi");
        			messagesButton.setEnabled(false);
        			changeStatusButton.setEnabled(false);
        			changePictureButton.setEnabled(false);
        			changePasswordButton.setEnabled(false);
        		} else {
        			person = null;
        		}
    		}
    	}
    	
    	if (e.getActionCommand().equals("Follow")) {
    		String name = nameField.getText();
    		if (person == null) {
    			canvas.showMessage("You can't follow yourself");
    		} else {
    			if (profile.addFriend(name) == false) {
    				canvas.showMessage("You are already following this person.");
    			} else {
    				profile.addFriend(name);
					canvas.displayProfile(profile);
					canvas.showMessage("Following " + name);
    			}
    		}
    	}
    	
    	if (e.getActionCommand().equals("Change Status")) {
    		String status = dialog.readLine("Enter new status");
    		profile.setStatus(profile.getName() + " is " + status);
			canvas.displayProfile(profile);
			canvas.showMessage("Status updated to " + status);
    	}
    	
    	if (e.getActionCommand().equals("Change Picture")) {
    		String pictureName = dialog.readLine("Enter picture name");
    		GImage image;
    		
    		try {
    			image = new GImage(pictureName);
        		profile.setImage(image);
			} catch (ErrorException ex) {
				image = null;
			}
    		
    		canvas.displayProfile(profile);
			if (image == null) {
				canvas.showMessage("Unable to open image file: " + pictureName);
			}
			else {
				canvas.showMessage("Picture updated");
			}
    	}
    	
    	if (e.getActionCommand().equals("Change Password")) {
    		String newPassword = dialog.readLine("Enter new password");
    		profile.setPassword(newPassword);
    		profilesData.saveData();
    	}
    	
    	if (e.getActionCommand().equals("Messages")) {
    		message();
    	}
    }
    
    private void message() {
 		IODialog dialog = getDialog();
 		String username;
 		boolean end = false;
 		username = dialog.readLine("Enter username: ");
 		
 		if (end == false) {
 			FacePamphletMessages ms = new FacePamphletMessages(profile.getUsername(), username);
 			
 	 		if (dialog.readBoolean(ms.getConvrsation(),"Send message","close")){
 	 			String text = dialog.readLine();
 	 			ms.sendMessage(profile.getName() + " : " + text);
 	 			
 	 			if (dialog.readBoolean(ms.getConvrsation(),"Send message","close")) {
 	 				sendSMS(ms, dialog);
 	 			}
 	 			
 	 		}
 	 		
 		}
 		
 		end = false;
 	}
    
    private void sendSMS(FacePamphletMessages ms, IODialog dialog){
 		String messageText = dialog.readLine();
		ms.sendMessage(profile.getName() + ": " + messageText);
		if(dialog.readBoolean(ms.getConvrsation(),"Send message","close")){
			sendSMS(ms, dialog);
		}
 	}
    
    /* Private instance variables */
    private FacePamphletCanvas canvas;
    private FacePamphletDatabase profilesData;
    private FacePamphletProfile profile;
    private FacePamphletProfile person;
    
  	private JTextField nameField;
  	private JButton searchButton;
  	private JButton followButton;
  	private JButton messagesButton;
  	private JButton matualFriendsButton;
  	private JButton changeStatusButton;
  	private JButton changePictureButton;
  	private JButton changePasswordButton;
  	private IODialog dialog = getDialog();
  	private JComboBox <String> gender;
  	
  	// Login fields and buttons
  	private JTextField loginUsernameField;
  	private JTextField loginPasswordField;
  	private JButton loginButton;
  	private JButton logoutButton;
  	
  	// Register fields and buttons
  	private JTextField registerUsernameField;
  	private JTextField registerNameField;
  	private JTextField registerSurnameField;
  	private JTextField registerPasswordField; 
  	private JTextField registerRepeatedPasswordField;
  	private JTextField cityField;
  	private JTextField countryField;
  	private JButton registerButton;
}
