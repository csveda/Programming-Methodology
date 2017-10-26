package extension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

import acm.graphics.GImage;
import acmx.export.java.io.FileReader;

public class FacePamphletDatabase implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		profiles = new HashMap <String, FacePamphletProfile> ();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		if (!profiles.containsKey(profile.getUsername())) {
			profiles.put(profile.getUsername(), profile);
		} else {
			profiles.remove(profile.getUsername());
			profiles.put(profile.getUsername(), profile);
		}
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		if (profiles.containsKey(name)) {
			return profiles.get(name);
		} else {
			return null;
		}	
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (profiles.containsKey(name)) {
			FacePamphletProfile profileToRemove = profiles.get(name);
			Iterator <String> it = profileToRemove.getFriends();
			
			while (it.hasNext()) {
				String friendName = it.next();
				FacePamphletProfile friendsProfile = profiles.get(friendName);
				friendsProfile.removeFriend(name);
			}
			profiles.remove(name);
		}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (profiles.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void readData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("data.txt"));
			while (true) {
				String line = null;
				line = br.readLine();
				if (line == null) {
					break;
				}
				
				String username = br.readLine();
				String name = br.readLine();
				String surname = br.readLine();
				String password = br.readLine();
				String gender = br.readLine();
				String status = br.readLine();
				String city = br.readLine();
				String country = br.readLine();
				
				FacePamphletProfile profile = new FacePamphletProfile(username, name, surname, password, gender, status, city, country);
				profilesData.addProfile(profile);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveData() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("data.txt"));
			String line = null;
			String skip = "\n";
			Iterator <String> it = profiles.keySet().iterator();
			
			while (it.hasNext()) {
				String username = it.next();
				FacePamphletProfile profile = profiles.get(username);
				
				line = "<profile>\n";
				bw.write(line);

				line = username + skip;
				bw.write(line);
				
				line = profile.getName() + skip;
				bw.write(line);
				
				line = profile.getSurname() + skip;
				bw.write(line);
				
				line = profile.getPassword() + skip;
				bw.write(line);
				
				line = profile.getGender() + skip;
				bw.write(line);
				
				line = profile.getStatus() + skip;
				bw.write(line);
				
				line = profile.getCity() + skip;
				bw.write(line);
				
				line = profile.getCountry() + skip;
				bw.write(line);
			}
				bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* private instance variables */
	private Map <String, FacePamphletProfile> profiles;
	private FacePamphletDatabase profilesData;
}

