package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import model.Deck;

/**
 * This class manages the whole game.
 * @author ArRaLo
 * @version 1.0
 * @see model.Deck
 */
public class Game {
	
	private List<Deck> decks;
	
	private static Game instance;
	
	/**
	 * Constuctor.
	 * Private constructor so you can only create one instance of the class.
	 */
	private Game() {
		this.decks = new ArrayList<Deck>();
	}
	
	/**
	 * The only way to have an instance of Game.
	 * @return Game instance. The only one instance of the class Game.
	 */
	public static Game getInstance() {
		if(instance==null) {
			instance = new Game();
		}
		return instance;
	}
	
	/**
	 * Play the game ! 
	 * Decks have to be already in the game. ( And not empty ).
	 */
	public void play() {
		if(decks.isEmpty()) {
			System.out.println("No decks avalaible in the game ! ");
			return;
		}
		for(Deck d : decks) {
			System.out.println(d.getTheme());
			if(d.getSizeQuestions()!=0) {
				for(int i=0;i<d.getSizeQuestions();i++) {
					double startTime = System.currentTimeMillis();
					System.out.println(d.getQuestion(i).getClues().get(0));
					while(System.currentTimeMillis()-startTime<4000) {
						
					}
					System.out.println(d.getQuestion(i).getClues().get(1));
					while(System.currentTimeMillis()-startTime<8000) {
						
					}
					System.out.println(d.getQuestion(i).getClues().get(2));
					while(System.currentTimeMillis()-startTime<16000) {	
						
					}
					System.out.println("Answer : " + d.getQuestion(i).getAnswer());
				}
			}
		}
	}
	
	
	/**
	 * Allows to save all the decks in the current Game.
	 * Decks will be saved in json format file.
	 * @see model.Deck.toJson
	 */
	public void saveAllDecks() {
		int i = 0;
		for(Deck d : decks) {
			try {
				File f = new File("./src/resources/questions/deck_" + d.getTheme() + ".json");
				if(f.createNewFile()) {
					Deck.toJson(d, f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//CRUD methods
	/**
	 * Allows to add a deck in the current Game.
	 * @param d : Deck. The Deck to add
	 * @return boolean. True if the Deck is well removed.
	 */
	public boolean addDeck(Deck d) {
		if(!decks.contains(d)) {
			return decks.add(d.clone());
		}
		return false;
	}
	
	/**
	 * Allows to read a json format file which contains a deck.
	 * @param f : File. The json file to read.
	 * @return boolean. True if the deck is well created and added to the Game.
	 * @see this.addDeck(Deck d)
	 */
	public boolean addDeck(File f) {
		Deck d = Deck.fromJson(f);
		return this.addDeck(d.clone());
	}
	
	/**
	 * Allows to remove a deck from the Game.
	 * @param d : Deck. The deck to remove.
	 * @return boolean. True if the deck is well removed.
	 */
	public boolean removeDeck(Deck d) {
		if(decks.contains(d)) {
			decks.remove(d);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to remove a deck at a specific index.
	 * @param index : int. The index of the deck to remove.
	 * @return boolean. True if the deck is well removed.
	 */
	public boolean removeDeck(int index) {
		if(decks.size()>index && index>=0) {
			decks.remove(index);
			return true;
		}
		return false;
	}
	
	
	//Base methods
	@Override
	public String toString() {
		String s="";
		for(Deck d : decks) {
			s += "---------\n" + d.toString(); 
		}
		return s;
	}
}
