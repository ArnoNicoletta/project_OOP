package model;

public enum AcceptedAdmins {
	
	HELHA("Admin", "helha"),
	ARNO_NICOLETTA("Arno Nicoletta", "helha"),
	RAYAN_TAHRI("Rayan Tahri", "helha"),
	LOIC_LECOLIER("Loïc Lécolier", "helha");
	
	
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
