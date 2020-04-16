package exception;

import java.io.File;

@SuppressWarnings("serial")
public class WrongDeckFormatException extends Exception {
	
	private File f;
	
	public WrongDeckFormatException(File f) {
		super("Impossible to convert this file into a Deck!");
		this.f = f;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() 
				+ "\nFile : " + this.f;
	}
}
