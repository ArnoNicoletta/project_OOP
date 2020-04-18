package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongDeckFormatException;


/**
 * This class manages lists of {@link Question}.
 * @author ArRaLo
 * @see model.Question
 */
public class Deck {
	
	private List<Question> questions;
	
	public Deck() {
		this.questions = new ArrayList<>();
	}
	
	//Game methods
	
	/**
	 * Shuffles the questions in this game so they never are in the same order.
	 */
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}
	
	/**
	 * Gives the theme of the {@link Deck}
	 * @return {@link String}. The theme of the {@link Deck}.
	 */
	public String getTheme() {
		if(questions.isEmpty()) {
			return "";
		}
		return questions.get(0).getTheme();
	}
	
	/**
	 * Gives the all the questions in the deck
	 * @return {@link List}<{@link Question}>. The questions.
	 */
	public List<Question> getQuestions() {
		List<Question> ret = new ArrayList<>();
		questions.forEach(q -> ret.add(q.clone()));
		return ret;
	}
	
	/**
	 * Tells if there is only one theme in this instance.
	 * Useful when adding decks from a json file.
	 * @return {@link Boolean}. <code>true</code> if this has an unique theme, <code>false</code> otherwise
	 */
	public boolean hasUniqueTheme() {
		for(Question q : questions) {
			if(!q.getTheme().equalsIgnoreCase(this.getTheme())) {
				return false;
			}
		}
		return true;
	}
	
	
	//JSON methods
	
	/**
	 * Read a .json {@link File} and convert it into a {@link Deck}
	 * @param file : {@link File}. The file to read. Must be in .json file format.
	 * @return the created {@link Deck}
	 * @throws WrongDeckFormatException 
	 */
	public static Deck fromJson(File file) throws WrongDeckFormatException {
		Deck ret = null;
		Gson gson = new Gson();
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			ret = gson.fromJson(br, Deck.class);
			br.close();
		} catch (Exception e) {}
		if(ret==null) throw new WrongDeckFormatException(file);
		return ret.clone();
	}
	
	/** 
	 * Write a {@link Deck} in a json {@link File} format.
	 * @param d : {@link Deck}. The {@link Deck} you want to write.
	 * @param file : {@link File}. The json {@link File} which you want to write in.
	 */
	public static void toJson(Deck d, File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(d);
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
			bw.write(json);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// CRUD methods
	/** 
	 * Allows to add a {@link Question} to the {@link Deck} if not in the list yet.
	 * If the list isn't empty, the theme must be the same for all questions added.
	 * @param q : {@link Question}. The {@link Question} to add.
	 * @return {@link Boolean}. True if the {@link Question} q is well added.
	 * @throws QuestionAlreadyExistException
	 */
	public boolean addQuestion(Question q) throws QuestionAlreadyExistException {
		if(questions.isEmpty()) {
			return questions.add(q.clone());
		}
		if(this.getTheme().equalsIgnoreCase(q.getTheme())) {
			if(questions.contains(q)) throw new QuestionAlreadyExistException(q);
			return questions.add(q);
		}
		return false;
	}
	
	/**
	 * Allows to remove a {@link Question} from the {@link Deck}.
	 * @param q : {@link Question}. The {@link Question}  to remove.
	 * @return {@link Boolean}. True if the {@link Question} q is well removed.
	 * @throws QuestionNotFoundException 
	 */
	public boolean deleteQuestion(Question q) throws QuestionNotFoundException {
		if(!questions.contains(q)) throw new QuestionNotFoundException(q);
		return questions.remove(q);
	}
	
	/**
	 * Allows to remove a {@link Question} from the deck
	 * @param index : {@link Integer}. The index of the {@link Question} you want to remove from the {@link Deck}.
	 * @return {@link Boolean}. <code>true</code> if the {@link Question} q is well removed.
	 */
	public boolean deleteQuestion(int index) {
		if(questions.size()>index && index>=0) {
			questions.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to modify a question.
	 * @param oldQ : {@link Question}. The question to modify.
	 * @param newQ : {@link Question}. The new question to put in place of the oldQ.
	 * @return {@link Boolean}. <code>true</code> if the {@link Question} newQ is well added.
	 * @throws QuestionNotFoundException
	 */
	public boolean modifyQuestion(Question oldQ, Question newQ) throws QuestionNotFoundException {
		if(!questions.contains(oldQ)) {
			throw new QuestionNotFoundException(oldQ);
		}
		questions.set(questions.indexOf(oldQ), newQ.clone());
		return true;
	}
	
	/**
	 * Gets a {@link Question} at a specified index.
	 * @param index : {@link Integer}. Index of the question.
	 * @return {@link Question}. The question to return.
	 */
	public Question getQuestion(int index) {
		if( questions == null || questions.isEmpty() 
				|| index > questions.size() || index < 0) {
			return null;
		}
		return questions.get(index).clone();
	}
	
	/**
	 * Get the number of questions in this deck.
	 * @return {@link Integer}. The number of questions.
	 */
	public int getSizeQuestions() {
		return questions.size();
	}
	
	
	// Basic methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
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
		Deck other = (Deck) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String s="";
		for (Question q : questions) {
			s = s + q.toString() + "\n";
		}
		return s;
	}
	public Deck clone() {
		Deck ret = new Deck();
		for(Question q : questions) {
			ret.questions.add(q.clone()); // Not using ret.addQuestion(q) because
		}
		return ret;
	}
}
