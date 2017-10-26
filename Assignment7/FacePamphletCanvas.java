/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
	}
	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message = new GLabel(msg);
		double x = (getWidth() - message.getWidth()) / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		
		if (getElementAt(p, q) != null) {
			remove(getElementAt(p, q));
		}
		
		p = x;
		q = y;
		message.setFont(MESSAGE_FONT);
		add(message, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		addFriends(profile.getFriends());
		addImage(profile.getImage());
		addName(profile.getName());
		addStatus(profile.getStatus());
	}
	
	private void addName(String name) {
		nameLabel = new GLabel(name);
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(Color.BLUE);
		nameHeight = nameLabel.getHeight();
		
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameHeight;
		add(nameLabel, x, y);
	}
	
	private void addStatus(String status) {
		GLabel statusLabel = new GLabel(status);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameHeight + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getHeight();
		
		if (getElementAt(x, y) != null) {
			remove(getElementAt(x, y));
		}
		add(statusLabel, x, y);
	}
	
	private void addImage(GImage image) {
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameHeight + IMAGE_MARGIN; 
		
		if (image != null) {
			image.setBounds(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
		}
		else {
			GRect imageRect = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageRect);
			
			GLabel empty = new GLabel("No Image");
			empty.setFont(PROFILE_IMAGE_FONT);
			
			double width = x + (IMAGE_WIDTH - empty.getWidth()) / 2;
			double height = y + IMAGE_HEIGHT / 2;
			add(empty, width, height);
		}
	}
	
	

	private void addFriends(Iterator <String> friends) {
		friendsLabel = new GLabel("Friends : ");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		
		double x = getWidth() / 2;
		double y = TOP_MARGIN + nameHeight;
		
		add(friendsLabel, x, y);
		Iterator <String> it = friends;
		for (int i = 1; it.hasNext(); i++) {
			GLabel friendName = new GLabel(it.next());
			friendName.setFont(PROFILE_FRIEND_FONT);
			
			double dis = y + friendsLabel.getHeight() * i;
			add(friendName, x, dis);
		}
	}
	
	/* Private instance variables */
	private GLabel message;
	private GLabel nameLabel;
	private GLabel statusLabel;
	private GLabel friendsLabel;
	private double nameHeight = 0;
	private double p = 0;
	private double q = 0;
}
