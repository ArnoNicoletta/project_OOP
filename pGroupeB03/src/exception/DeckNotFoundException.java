package exception;

import model.Deck;

@SuppressWarnings("serial")
public class DeckNotFoundException extends Exception {
	
	private String theme;
	
	public DeckNotFoundException(String theme) {
		super("This doesn't seem to be in the game !");
		this.theme = theme;;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage() + 
				"\nTheme : " + theme ;
	}
}
