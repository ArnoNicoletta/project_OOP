package exception;

public class TooMuchCharException extends Exception {
	
	private String s;
	
	public TooMuchCharException(String s) {
		super("Pseudo can have 16 charaters max ");
		this.s = s;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + this.s;
	}
	
}
