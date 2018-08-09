package database;

import password.Password;

public interface DatabaseInterface {
	public void AddPassword(Password p);
	
	public Password getPasswordFromName(String name);

	//public void loadDB();
}

