package generators;

import java.security.SecureRandom;
import java.util.Random;

public class PasswordGenerator {
	private String possibleChars;
	
	private final Random random = new SecureRandom();
	
	
	public PasswordGenerator() {
		possibleChars = "";
	}
	
	public String Generate(int length, Boolean lowercase, Boolean uppercase, Boolean digits, Boolean special) {
		if(length < 1) {
			throw new IllegalArgumentException("Length < 1:" + length);			
		}
			
		if(lowercase) {
			possibleChars += "abcdefghijklmnopqrstuvwxyz";
		}
		
		if(uppercase) {
			possibleChars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}
		
		if(digits) {
			possibleChars += "0123456789";
		}
		
		if(special) {
			possibleChars += "!#$%&";
		}
		
		
		char[] chars = new char[length];
		
		return fill(chars);
	}
	
	private String fill(char[] chars) {
		if(possibleChars.length() > 0 ) {
			for(int i = 0; i < chars.length; i++) {
				chars[i] = possibleChars.charAt(random.nextInt(possibleChars.length()));
			}
			return new String(chars);
		}
		return "";
		
	}
		
}
