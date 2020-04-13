package model;

public enum AcceptedAdmins {
	
	HELHA("Admin", "Helha"),
	ARNO_NICOLETTA("Arno Nicoletta", "Helha"),
	RAYAN_TAHRI("Rayan Tahri", "Helha"),
	LOIC_LECOLIER("Loïc Lécolier", "Helha");
	
	
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
