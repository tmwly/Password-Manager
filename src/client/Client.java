package client;
import database.DatabaseHandler;
import password.Password;

public class Client {
	
	private DatabaseHandler passwords = new DatabaseHandler();
	private String key;
	
	
	public void setKey(String key) {
		this.key = key;
	}
	
	
	public Password getPassword(String name) {
		try {
			Password p = passwords.getPassword(name);
			p.decrypt(key);
			return p;
			
		}	catch(NullPointerException e) {
			System.out.println("Item with name \"" + name + "\" does not exist");
		}
		return null;
	}
	
	public int getTotalPasswords() {
		return passwords.getTotalPasswords();
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
}
