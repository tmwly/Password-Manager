package database;

import password.Password;

public interface DatabaseInterface {
	public void AddPassword(Password p);
	
	public Password getPassword(String name);

	public void loadDB();
}

