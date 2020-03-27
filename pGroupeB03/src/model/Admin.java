package model;

import exception.WrongLoginException;

/**
 * This class manages admin.
 * @author ArRaLo
 * @see AcceptedAdmins
 */
public class Admin {
	
	private String username;
	private String password;
	
	public Admin(String username, String password) throws WrongLoginException {
		
		for(AcceptedAdmins aa : AcceptedAdmins.values()) {
			if(username.equals(aa.getUsername()) && password.equals(aa.getPassword())) {
				this.username = username;
				this.password = password;
			}
		}
		if(this.username==null && this.password == null) throw new WrongLoginException();
	}
	//Getters and Setters
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	//Basic methods
	@Override
	public String toString() {
		return this.username;
	}
}
