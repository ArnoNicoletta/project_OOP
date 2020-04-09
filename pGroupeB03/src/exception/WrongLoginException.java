package exception;

@SuppressWarnings("serial")
public class WrongLoginException extends Exception {
	
	public WrongLoginException() {
		super("Wrong username or password");
	}
	
}
