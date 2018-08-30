package database;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import password.Password;

import java.io.*;
import java.util.ArrayList;


@JsonIgnoreProperties(value = {"file"})

public class DatabaseHandler implements DatabaseInterface{
	private ArrayList<Password> passwords = new ArrayList<>();


    private static File file;


	public DatabaseHandler() {
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

	public File getFile(){
		return file;
	}

	public void writeDB() {

		try {
			new ObjectMapper().writeValue(file, this);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeDB(File f) {

        try {
            new ObjectMapper().writeValue(f, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

	}


	public void loadDB() throws MismatchedInputException{
		try{
			if(file.exists()) {
				DatabaseHandler d = new ObjectMapper().readValue(file, DatabaseHandler.class);
				passwords = d.getPasswords();

			}

		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e ) {
			e.printStackTrace();
		}

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
		System.out.println(location);
		loadDB();


	}

	public void decryptAll(String key) {

		for(int i = 0; i < passwords.size(); i++) {
			Password p = passwords.get(i);
			p.decrypt(key);
			passwords.set(i, p);
		}
	}
	public void encryptAll(String newKey) {
		for(int i = 0; i < passwords.size(); i++) {
			Password p = passwords.get(i);
			p.encrypt(newKey);
			passwords.set(i, p);
		}
	}

	public String toString(){
		return passwords.toString();
	}

	public void createDatabase(File f) throws MismatchedInputException {
		writeDB(f);
		setDatabase(f.getAbsolutePath());
	}
}
