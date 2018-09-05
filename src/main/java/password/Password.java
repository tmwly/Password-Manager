package password;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.crypto.BadPaddingException;
import java.io.Serializable;
import java.util.Objects;



public class Password implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String password;
	private String site;
	private String name;
	private String notes;
	private Boolean encrypted;

	public Password(String name, String site, String password, String notes) {
		this.password = password;
		this.site = site;
		this.name = name;
		this.notes = notes;
		encrypted = false;
	}

	public Password() {
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

	public Boolean getEncrypted() {
		return encrypted;
	}


	public Password decryptAndReturn(String key) {
		if(encrypted) {
			String newSite = Encryptor.decrypt(site, key);
			String newPassword = Encryptor.decrypt(password, key);
			String newNotes = Encryptor.decrypt(notes, key);

			if(!(site.equals(newSite) && password.equals(newPassword) && notes.equals(newNotes))) {

				Password p = new Password(name, newSite, newPassword, newNotes);
				return p;
			}
		}
		return null;
	}


	public void decrypt(String key) {
		if(encrypted) {
				String newSite = Encryptor.decrypt(site, key);
				String newPassword = Encryptor.decrypt(password, key);
				String newNotes = Encryptor.decrypt(notes, key);

				if(!(site.equals(newSite) && password.equals(newPassword) && notes.equals(newNotes))) {
					site = newSite;
					password = newPassword;
					notes = newNotes;
					encrypted = false;
				}
		}
	}
	
	public void encrypt(String key) {
		if(!encrypted) {
			System.out.println("Encrypting password: " + deepToString());

			String newSite = Encryptor.encrypt(site, key);
			String newPassword = Encryptor.encrypt(password, key);
			String newNotes = Encryptor.encrypt(notes, key);


			if(!(site.equals(newSite) && password.equals(newPassword) && notes.equals(newNotes))) {
				site = newSite;
				password = newPassword;
				notes = newNotes;
				encrypted = true;
			}



			System.out.println("Finished encrypting pass: " + deepToString());
		}
	}
	
	public String toString() {
		return name;
	}

	public String deepToString() {
		String s = "";
		s += "Name: " + name + "\n"
				+ "Site: " + site + "\n"
				+ "Pass: " + password + "\n"
				+ "Notes: " + notes + "\n"
				+ "Encrypted: " + encrypted + "\n";
		return s;
	}


	@Override
	public int hashCode() {

		return Objects.hash(password, site, name, notes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Password password1 = (Password) o;
		return Objects.equals(password, password1.password) &&
				Objects.equals(site, password1.site) &&
				Objects.equals(name, password1.name) &&
				Objects.equals(notes, password1.notes);
	}
}
