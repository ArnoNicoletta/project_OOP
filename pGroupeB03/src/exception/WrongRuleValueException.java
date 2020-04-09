package exception;

@SuppressWarnings("serial")
public class WrongRuleValueException extends Exception {
	
	private String s;
	
	public WrongRuleValueException(String s) {
		super("You have entered a wrong value. ");
		this.s = s;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + "\nValue :" +this.s;
	}
	
}
