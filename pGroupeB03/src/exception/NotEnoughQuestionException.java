package exception;

import model.Deck;

public class NotEnoughQuestionException extends Exception {
	
	private Deck deck;
	
	public NotEnoughQuestionException(Deck deck) {
		super("The deck has to have at least 10 questions ");
		this.deck = deck;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + deck.toString();
	}
}
