package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages one question.
 * @author ArRaLo
 * @see model.Deck
 */
public class Question {
	
	
	private String author;
	private String theme;
	private List<String> clues;
	private String answer;
	
	/**
	 * Constructor.
	 * @param author : String. The author of the question.
	 * @param theme : String. The main theme of the question.
	 * @param clues : List<String>. The list of clues to find the answer.
	 * @param answer : String. The answer of the question.
	 */
	public Question(String author, String theme, List<String> clues, String answer) {
		this.setAuthor(author);
		this.setTheme(theme);
		this.setClues(clues);
		this.setAnswer(answer);
	}
	
	//Getters and setters
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public List<String> getClues() {
		return clues;
	}
	public void setClues(List<String> clues) {
		this.clues = clues;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	//Base methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [author=" + author + ", theme=" + theme + ", clues=" + clues + ", answer=" + answer + "]";
	}
	public Question clone() {
		List<String> c = new ArrayList<String>();
		for(String s : clues) {
			c.add(s);
		}
		return new Question(author, theme, c, answer);
	}
}
