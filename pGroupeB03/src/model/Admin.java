package model;

import java.util.List;

import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongLoginException;

/**
 * This class manages admin.
 * @author ArRaLo
 * @see AcceptedAdmins
 */
public class Admin {
	
	private String username;
	private String password;
	
	private Game g = Game.getInstance();
	
	public Admin(String username, String password) throws WrongLoginException {
		
		for(AcceptedAdmins aa : AcceptedAdmins.values()) {
			if(username.equals(aa.getUsername()) && password.equals(aa.getPassword())) {
				this.username = username;
				this.password = password;
			}
		}
		if(this.username==null && this.password == null) throw new WrongLoginException();
	}
	
	public void addDeck(String theme, List<String> clues, String answer) throws QuestionAlreadyExistException {
		g.addQuestion(new Question(username, theme, clues, answer));
	}
	
	public void deleteDeck(String theme, String answer) throws QuestionNotFoundException {
		g.deleteQuestion(new Question(null, theme, null, answer));
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
