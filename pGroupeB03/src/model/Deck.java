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
 * This class manages lists of Question.
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
	 * Read a .json file and convert it into a Deck
	 * @param file : File. The file to read. Must be in .json file format.
	 * @return the created Deck
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
	 * Write a deck in a json file format.
	 * @param d : Deck. The deck you want to write.
	 * @param file : File. The json file which you want to write in.
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
	 * Allows to add a question to the deck if not in the list yet.
	 * If the list isn't empty, the theme must be the same for all questions added.
	 * @param q : Question. The question to add.
	 * @return boolean. True if the question is well added.
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
	 * Allows to remove a question from the deck.
	 * @param q : Question. The question to remove.
	 * @return boolean. True if the question is well removed.
	 */
	public boolean deleteQuestion(Question q) {
		return questions.remove(q);
	}
	
	/**
	 * Allows to remove a question from the deck
	 * @param index : int. The index of the question you want to remove from the deck.
	 * @return boolean. True if the question is well removed.
	 */
	public boolean deleteQuestion(int index) {
		if(questions.size()>index && index>=0) {
			questions.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Gives the theme of the deck
	 * @return String. The theme of the deck.
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
