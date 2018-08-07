package password;

import java.io.Serializable;

public class Password implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private String site;
	private String name;
	private String notes;

	public Password(String name, String site, String password, String notes) {
		this.password = password;
		this.site = site;
		this.name = name;
		this.notes = notes;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String s) {
		password = s;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String s) {
		site = s;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String s) {
		notes = s;
	}
	
	public void decrypt(String key) {
		site = Encryptor.decrypt(site, key);
		password = Encryptor.decrypt(password, key);
		notes = Encryptor.decrypt(notes, key);
	}
	
	public void encrypt(String key) {
		site = Encryptor.encrypt(site, key);
		password = Encryptor.encrypt(password, key);
		notes = Encryptor.encrypt(notes, key);
	}
	
	public String toString() {
		String s = "";
		s += "Name: " + name;
		s += "\nSite: " + site;
		s += "\nPassword: " + password;
		s += "\nNotes: " + notes;
		s += "\n";
		
		return s;
	}
	
}
