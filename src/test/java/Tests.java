import database.DatabaseHandler;
import org.junit.Test;

import generators.PasswordGenerator;
import password.Encryptor;
import client.Client;
import password.Password;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Tests {
	@Test
	public void PasswordGeneratorOnlyLowercase() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(10, true, false, false, false);
		assertTrue(s.matches("[a-z]+"));
		
	}
	
	@Test
	public void PasswordGeneratorOnlyUppercase() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(10, false, true, false, false);
		assertTrue(s.matches("[A-Z]+"));
		
	}
	
	@Test
	public void PasswordGeneratorOnlyNumeric() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(10, false, false, true, false);
		assertTrue(s.matches("[0-9]+"));
		
	}
	
	@Test
	public void PasswordGeneratorOnlySymbols() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(10, false, false, false, true);
		assertTrue(s.matches("[!#$%&]+"));		
	}
	
	@Test
	public void PasswordGeneratorMix() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(30, true, false, true, true);
		
		assertTrue(s.matches("[a-z0-9!#$%&]+"));
	}
	
	@Test
	public void PasswordGeneratorCorrectLength() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(30, true, true, true, true);
		assertEquals(s.length() == 30, true);	
	}
	
	@Test
	public void PasswordGeneratorCorrectLengthEmpty() {
		PasswordGenerator gen = new PasswordGenerator();
		String s = gen.Generate(30, false, false, false, false);
		assertEquals(s.length() == 0, true);	
	}
	
	
	@Test
	public void AddPasswordCorrectly() {
		Client c = new Client();
		c.setKey("Please work.");
		c.addPassword("google", "tings", "yee", "nout");
		c.addPassword("twitter", "oijoijo", "fan", "toid");
		c.addPassword("facebook", "bigtiddy", "youknowit", "");
		assertEquals(c.getTotalPasswords(), 3);
	}
	
	
	
	//TODO write this test in a way that actually tests shit
	@Test(expected = NullPointerException.class)
	public void SearchForNonExistentKey() {
		Client c = new Client();
		c.getPassword("fart");
	}
	
	
	
	@Test 
	public void StringEncryptAndDecrypt() {	
		
		String password = "Please work.";
		String s = "Hello this is a test string.";
		
		String encrypted = Encryptor.encrypt(s, password);
		String decrypted = Encryptor.decrypt(encrypted, password);
		assertEquals(s, decrypted);
		
				
	}
	



	@Test
	public void WriteToJson(){
		Password p = new Password("this","is","a", "test");
		DatabaseHandler database = DatabaseHandler.loadDB();
		database.AddPassword(p);
		database.close();
		database = DatabaseHandler.loadDB();
		int i = database.getTotalPasswords();
		database.deletePassword(p);
		assertEquals(1, i);


	}

}
