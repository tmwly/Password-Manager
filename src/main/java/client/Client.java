package client;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import password.Password;

import java.util.ArrayList;
import java.util.Observable;

public class Client {
	
	private DatabaseHandler passwords;
	private String key;
	private ObservableList<Password> observableList;

	public Client(){
		key = "";
		passwords = new DatabaseHandler();
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public Password getPassword(String name) throws NullPointerException {
		try {
			Password p = passwords.getPasswordFromName(name);
			p.decrypt(key);
			return p;

		}  catch(NullPointerException e) {
			System.out.println("Item with name \"" + name + "\" does not exist");
		}
		return null;
	}

	public void setDatabase(String location) throws MismatchedInputException {
		passwords.setDatabase(location);
		System.out.println("Client");
	}
	public int getTotalPasswords() {
		return passwords.getTotalPasswords();
	}


	public void deletePassword(Password p) {
		passwords.deletePassword(p);
	}



	public void addPassword(String name, String site, String password, String notes) {
		try {
			if(!key.isEmpty()) {
				Password p = new Password(name, site, password, notes);
				p.encrypt(key);
				passwords.AddPassword(p);
			}
			
		} catch(NullPointerException e) {
			System.out.println("Please provide an encryption key before adding a password");
			
		}
	}

	public ObservableList<Password> getObservableList() {
		if(observableList==null) {
			observableList = FXCollections.observableList(passwords.getPasswords());
		}
		return observableList;
	}
}
