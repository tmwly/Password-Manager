package database;

//TODO make this work with a db

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import password.Password;

import java.io.*;
import java.util.ArrayList;

public class DatabaseHandler implements DatabaseInterface{
	private ArrayList<Password> passwords = new ArrayList<>();
    private static File file = new File("storage.json");


	public DatabaseHandler() {

		if(!file.exists()) {

	        writeDB();

        }
	}

	public ArrayList<Password> getPasswords() {
		return passwords;
	}

	public void setPasswords(ArrayList<Password> passwords) {
		this.passwords = passwords;
	}

	public void close() {

		writeDB();
		passwords = new ArrayList<>();
	}



	private void writeDB() {

        try {
            new ObjectMapper().writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}



	public static DatabaseHandler loadDB() throws MismatchedInputException{
		try{
			if(file.exists()) {
				DatabaseHandler d = new ObjectMapper().readValue(file, DatabaseHandler.class);


				return d ;
			} else {
				return new DatabaseHandler();
			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e ) {
			e.printStackTrace();
		}
		return null;
	}




	
	@Override
	public Password getPasswordFromName(String name) {
		
		for(Password p: passwords){
			String s = p.getName();
			if(name.equals(s)) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void AddPassword(Password p) {
		if(passwords.stream().noneMatch(password -> p.getName().equals(password.getName()))) {
			passwords.add(p);
		}
	}

	@JsonIgnore
	public int getTotalPasswords() {
		return passwords.size();
	}
	
	
	public void deletePassword(Password p){
		passwords.remove(p);
		writeDB();
	}

    public void setDatabase(String location) throws MismatchedInputException {
		file = new File(location);
		loadDB();
		System.out.println("Database");
	}
}
