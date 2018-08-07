package database;

//TODO make this work with a db

import com.fasterxml.jackson.databind.ObjectMapper;
import password.Password;

import java.io.*;

public class DatabaseHandler implements DatabaseInterface{
	private DatabaseHashMap database;
    private static final File file = new File("storage.boob");


	public DatabaseHandler() {
		loadDB();
		if(!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO error handlin
            }
        }
	}
	

	public void close() {
		writeDB();
	}



	private void writeDB() {

        try {
            new ObjectMapper().writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }



	}


	@Override
	public void loadDB() {
		try{
			FileInputStream fin = new FileInputStream("storage.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			database = (DatabaseHashMap) ois.readObject();
		} catch(FileNotFoundException e) {
			//TODO write error handling
		} catch (ClassNotFoundException e) {
			//TODO write error handling
		} catch (IOException e ) {
			//TODO write error handling
		}

	}




	
	@Override
	public Password getPassword(String name) {
		
		return database.GetPassword(name);
	}

	@Override
	public void AddPassword(Password p) {
		database.AddPassword(p);
		
		//TODO check for concurrency
	
	}
	
	public int getTotalPasswords() {
		return database.getTotalValues();
	}
	
	


}
