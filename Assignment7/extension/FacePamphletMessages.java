package extension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FacePamphletMessages implements FacePamphletConstants {
	
	private String sendPerson;
	private String getPerson;
	ArrayList <String> list;
	private String fileName;
	
	public FacePamphletMessages(String sender, String listener) {
		list = new ArrayList <> ();
		fileName = "";
		
		this.sendPerson = sender;
		this.getPerson = listener;
		
		modify();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				list.add(line);
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getConvrsation() {
		int size = list.size();
	    String str = "";
		
		for (int i = 0; i < size; i++){
			str += list.get(i) + "\n";
		}
		return str;
	}
	
	public void sendMessage(String message) {
		String str = message;
		list.add(str);
		update();
	}
	
	
	
	private void modify(){
		int len = 0;
		if (sendPerson.length() <= getPerson.length()) {
			len = sendPerson.length();
		} else {
			len = getPerson.length();
		}
		
		for (int i = 0; i < len; i++) {
			int dif = sendPerson.charAt(i) - getPerson.charAt(i);
			if (dif < 0) {
				fileName = sendPerson + "&" + getPerson + ".txt";
				return;
			} else if (dif > 0) {
				fileName = getPerson + "&" + sendPerson + ".txt";
				return;
			}
		}
		
		if (sendPerson.length() <= getPerson.length()) {
			modifyFileName(false);
		} else {
			modifyFileName(true);
		}
	}
	
	private void update(){
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < list.size(); i++){
				String line = list.get(i);
				wr.write(line + "\n");
			}
			wr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void modifyFileName(boolean bl) {
		if (!bl) {
			fileName = sendPerson + "£" + getPerson + ".txt";
			return;
		}
		fileName = getPerson + "£" + sendPerson + ".txt";
	}
}