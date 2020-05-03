package model;

import java.util.List;

import exception.DeckNotFoundException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongLoginException;

/**
 * This class manages admin.
 * @author ArRaLo
 * @see QuestionOperation
 * @see AcceptedAdmins
 */
public class Admin implements QuestionOperation {
	
	private String username;
	private String password;
	
	private GameDecks g = GameDecks.getInstance();
	
	public Admin(String username, String password) throws WrongLoginException {
		
		for(AcceptedAdmins aa : AcceptedAdmins.values()) {
			if(username.equals(aa.getUsername()) && password.equals(aa.getPassword())) {
				this.username = username;
				this.password = password;
			}
		}
		if(this.username==null && this.password == null) throw new WrongLoginException();
	}
	
	@Override
	public boolean addQuestion(String author, String theme, List<String> clues, String answer) throws QuestionAlreadyExistException {
		return g.addQuestion(username, theme, clues, answer);
	}
	
	public boolean addQuestion(String theme, List<String> clues, String answer) throws QuestionAlreadyExistException {
		return g.addQuestion(username, theme, clues, answer);
	}
	
	@Override
	public boolean deleteQuestion(Question q) throws QuestionNotFoundException, DeckNotFoundException {
		return g.deleteQuestion(q);
	}
	
	@Override
	public boolean deleteDeck(String theme) throws DeckNotFoundException {
		return g.deleteDeck(theme);
	}
	
	@Override
	public boolean modifyQuestion(Question oldQ, Question newQ) throws DeckNotFoundException, QuestionNotFoundException {
		return g.modifyQuestion(oldQ, newQ);
	}
	//Getters
	public String getUsername() {
		return username;
	}
	//Basic methods
	@Override
	public String toString() {
		return this.username;
	}
}
