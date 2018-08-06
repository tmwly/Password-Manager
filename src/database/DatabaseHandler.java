package database;

//TODO make this work with a db

import password.Password;

public class DatabaseHandler implements DatabaseInterface{
	private DatabaseHashMap database;
	public DatabaseHandler() {
		loadDB();
		database = new DatabaseHashMap();
		
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
	
	

	@Override
	public void loadDB() {
		// TODO Auto-generated method stub
		
	}
}
