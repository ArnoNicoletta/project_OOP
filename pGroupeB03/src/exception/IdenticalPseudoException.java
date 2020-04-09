package exception;

@SuppressWarnings("serial")
public class IdenticalPseudoException extends Exception {
	
	private String pseudo;
	
	public IdenticalPseudoException(String pseudo) {
		super("Two or more pseudos are identical");
		this.pseudo = pseudo;
	}
	@Override
	public String getMessage() {
		return super.getMessage() + " : " + this.pseudo;
	}
}
