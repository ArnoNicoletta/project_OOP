package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
	
	public Question getQuestion(int index) {
		if( questions == null || questions.isEmpty() 
				|| index > questions.size() || index < 0) {
			return null;
		}
		return questions.get(index).clone();
	}
	
	public int getSizeQuestions() {
		return questions.size();
	}
	
	// Json methods
	
	/**
	 * Read a .json {@link File} and convert it into a {@link Deck}
	 * @param file : {@link File}. The file to read. Must be in .json file format.
	 * @return the created {@link Deck}
	 */
	public static Deck fromJson(File file) {
		Deck ret = null;
		Gson gson = new Gson();
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			ret = gson.fromJson(br, Deck.class);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.clone();
	}
	
	/** 
	 * Write a {@link Deck} in a json file format.
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
	 */
	public boolean addQuestion(Question q) {
		if(questions.isEmpty()) {
			return questions.add(q.clone());
		}
		if(!questions.contains(q) && this.getTheme().equalsIgnoreCase(q.getTheme())) {
			return questions.add(q);
		}
		return false;
	}
	
	/**
	 * Allows to remove a {@link Question} from the {@link Deck}.
	 * @param q : {@link Question}. The {@link Question}  to remove.
	 * @return {@link Boolean}. True if the {@link Question} q is well removed.
	 */
	public boolean deleteQuestion(Question q) {
		return questions.remove(q);
	}
	
	/**
	 * Allows to remove a {@link Question} from the deck
	 * @param index : {@link Integer}. The index of the {@link Question} you want to remove from the {@link Deck}.
	 * @return {@link Boolean}. True if the {@link Question} q is well removed.
	 */
	public boolean deleteQuestion(int index) {
		if(questions.size()>index && index>=0) {
			questions.remove(index);
			return true;
		}
		return false;
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
	
	// Base methods
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
			ret.addQuestion(q.clone());
		}
		return ret;
	}
}
