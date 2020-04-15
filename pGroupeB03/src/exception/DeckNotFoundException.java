package exception;

import model.Deck;

@SuppressWarnings("serial")
public class DeckNotFoundException extends Exception {
	
	private Deck d;
	
	public DeckNotFoundException(Deck d) {
		super("This doesn't seem to be in the game !");
		this.d = d;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage() + 
				"\nTheme : " + d.getTheme() ;
	}
}
