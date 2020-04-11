package exception;

@SuppressWarnings("serial")
public class EmptyPseudoException extends Exception {
	
	
	public EmptyPseudoException() {
		super("One or more pseudo are empty !");
	}
	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
