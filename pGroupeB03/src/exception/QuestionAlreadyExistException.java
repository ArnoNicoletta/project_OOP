package exception;

import model.Question;

public class QuestionAlreadyExistException extends Exception {
	
	private Question q;
	
	public QuestionAlreadyExistException(Question q) {
		super("There is already a question about the same theme\n and with the same answer in this game !");
		this.q = q;
	}
	@Override
	public String getMessage() {
		return super.getMessage() + 
				"\nQuestion : " + q.toString();
	}
}
