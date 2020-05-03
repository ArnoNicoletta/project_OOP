package model;

import java.util.List;

import exception.DeckNotFoundException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;

/**
 * This interface is used to bind {@link Admin} and {@link GameDecks} as a Proxy Pattern.
 * @author ArRaLo
 * @see Admin
 * @see GameDecks
 */
public interface QuestionsOperation {
	
	/**
	 * Allows to add a question in the decks.
	 * If this question has the same theme as an already existing deck, the question will be added in this deck.
	 * If no deck has the same theme, a new Deck will be created and added.
	 * @param q : {@link Question}. The question to add.
	 */
	public abstract boolean addQuestion(String author, String theme, List<String> clues, String anwser) 
			throws QuestionAlreadyExistException;
	
	
	/**
	 * Allows to delete a question from the decks.
	 * @param q : {@link Question}. The question to delete.
	 * @throws QuestionNotFoundException
	 * @throws DeckNotFoundException 
	 */
	public abstract boolean deleteQuestion(Question q) 
			throws QuestionNotFoundException, DeckNotFoundException;
	
	/**
	 * Allows to delete an entire deck.
	 * @param theme : {@link String}. The theme of the deck to be delete.
	 * @throws DeckNotFoundException
	 */
	public abstract boolean deleteDeck(String theme) 
			throws DeckNotFoundException;
	
	/**
	 * Allows to modify a question.
	 * @param oldQ : {@link Question}. The question to modify.
	 * @param newQ : {@link Question}. The new question to put in place of the oldQ.
	 * @throws DeckNotFoundException
	 * @throws QuestionNotFoundException
	 */
	public abstract boolean modifyQuestion( Question oldQ, Question newQ) 
			throws QuestionAlreadyExistException, DeckNotFoundException, QuestionNotFoundException;
	
}
