package extension;

/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import java.util.*;

import javax.swing.JComboBox;

public class FacePamphletProfile implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	public FacePamphletProfile(String username, String name, String surname, String password, String gender, String status, String city, String country) {
		profileUsername = username;
		profileName = name;
		profileSurname = surname;
		profilePassword = password;
		profileGender = gender;
		
		if (city == null) {
			profileCity = "Kutaisi";
		} else {
			profileCity = city;
		}
		
		if (country == null) {
			profileCountry = "Georgia";
		} else {
			profileCountry = city;
		}
		
		profilePicture = null;
		profileStatus = status;
		friends = new ArrayList <String> ();
	}

	/** This method returns the name associated with the profile. */ 
	public String getUsername() {
		return profileUsername;
	}
	
	/** This method returns the name of the profile. */ 
	public String getName() {
		return profileName;
	}
	
	/** This method returns the surname of the profile. */ 
	public String getSurname() {
		return profileSurname;
	}
	
	/** This method returns the password of the profile. */ 
	public String getPassword() {
		return profilePassword;
	}
	
	/** This method returns the gender of the profile. */ 
	public String getGender() {
		return profileGender;
	}
	
	/** This method returns the city of the person. */ 
	public String getCity() {
		return profileCity;
	}
	
	/** This method returns the country of the person. */
	public String getCountry() {
		return profileCountry;
	}

	/** 
	 * This method returns the image associated with the profile.  
	 * If there is no image associated with the profile, the method
	 * returns null. */ 
	public GImage getImage() {
		if (profilePicture == null) {
			return null;
		} else {
			return profilePicture;
		}
	}

	/** This method sets the image associated with the profile. */ 
	public void setImage(GImage image) {
		profilePicture = image;
	}
	
	/** This method updates password of the profile. */ 
	public void setPassword(String password) {
		profilePassword = password;
	}
	
	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() {
		return profileStatus;
	}
	
	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) {
		profileStatus = status;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		if (friends.contains(friend)) {
			return false;
		} else {
			friends.add(friend);
			return true;
		}
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (friends.contains(friend)) {
			friends.remove(friends.indexOf(friend));
			return true;
		} else {
			return false;
		}
	}

	/** 
	 * This method returns an iterator over the list of friends 
	 * associated with the profile.
	 */ 
	public Iterator <String> getFriends() {
		return friends.iterator();
	}
	
	/** 
	 * This method returns a string representation of the profile.  
	 * This string is of the form: "name (status): list of friends", 
	 * where name and status are set accordingly and the list of 
	 * friends is a comma separated list of the names of all of the 
	 * friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is 
	 * "coding" and who has friends Don, Chelsea, and Bob, this method 
	 * would return the string: "Alice (coding): Don, Chelsea, Bob"
	 */ 
	public String toString() {
		String profile = profileUsername + " (" + profileStatus + "): ";
		Iterator <String> it = friends.iterator();
		
		while (it.hasNext()) {
			profile += it.next() + ", ";
		}
		return profile;
	}
	
	/* Private instance variables */
	private String profileUsername;
	private String profileName;
	private String profileSurname;
	private String profilePassword;
	private String profileGender;
	private String profileCity;
	private String profileCountry;
	
	private GImage profilePicture;
	private String profileStatus;
	private ArrayList <String> friends;
}
