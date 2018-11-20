package client;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import password.Password;

import java.io.File;
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
	}

	public DatabaseHandler getDatabase(){
		return passwords;
	}


	public int getTotalPasswords() {
		return getObservableList().size();
	}


	public void deletePassword(Password p) {

		//TODO get this working with just deleting, not editing
		System.out.println(p.toString());
		getObservableList().remove(p);
		passwords.writeDB();
		refreshObservableList();
	}



	public void addPassword(String name, String site, String password, String notes) {
		//todo add error checking for identical names?
		try {
			//Check whether master key is present
			if(!key.isEmpty()) {
				Password p = new Password(name, site, password, notes);
				p.encrypt(key);
				getObservableList().add(p);
				passwords.writeDB();
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

	public ObservableList<Password> refreshObservableList() {

		return observableList = FXCollections.observableList(passwords.getPasswords());

	}


	public void changeDatabaseKey(String newKey) {
		passwords.decryptAll(key);
		passwords.encryptAll(newKey);
		key = newKey;
	}

	public void createDatabase(File file) throws MismatchedInputException{
		passwords.createDatabase(file);
	}


	public void overwritePassword(Password oldPass, Password newPass) {
		deletePassword(oldPass);
		addPassword(newPass.getName(), newPass.getSite(), newPass.getPassword(), newPass.getNotes());

	}

	public void changeClientKey(String newKey) {
		passwords.encryptAll(key);
		key = newKey;
	}
}
