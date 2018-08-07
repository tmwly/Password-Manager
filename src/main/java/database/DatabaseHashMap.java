package database;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import password.Password;

public class DatabaseHashMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConcurrentHashMap<String, Password> map;
	
	
	public DatabaseHashMap() {
		this.map = new ConcurrentHashMap<String, Password>();
	}
	
	public void AddPassword(Password p) {
		map.put(p.getName(),p);
	}
	
	public int getTotalValues() {
		return map.size();
	}
	
	public Password GetPassword(String name) throws NullPointerException {
		
		 return map.get(name);
		 	
	}
}
