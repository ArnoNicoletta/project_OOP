package exception;

import model.Game;

@SuppressWarnings("serial")
public class NotEnoughDeckException extends Exception {
	
	private int wanted;
	
	public NotEnoughDeckException(int wanted) {
		super("The current Game doesn't have enough deck, only : " + Game.getInstance().getDecks().size() + "decks.");
		this.wanted = wanted;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + " And you wanted : " + wanted + " decks.";
	}
}
