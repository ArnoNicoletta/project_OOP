package exception;

import model.Question;

@SuppressWarnings("serial")
public class QuestionNotFoundException extends Exception {
	
	private Question q;
	
	public QuestionNotFoundException(Question q) {
		super("This doesn't seem to be in the game !");
		this.q = q;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage() + 
				"\nQuestion : " + q.toString() ;
	}
}
