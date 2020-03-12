package model;

public enum AcceptedAdmins {
	
	HELHA("Admin", "Helha");
	
	
	private String username;
	private String password;
	
	private AcceptedAdmins(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
}
