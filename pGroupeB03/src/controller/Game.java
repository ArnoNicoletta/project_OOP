package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Deck;

/**
 * This class manages the whole game.
 * @author ArRaLo
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
	 * The only way to have an instance of {@link Game}.
	 * @return {@link Game} instance. The only one instance of the class {@link Game}.
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
	 * Gives a pseudorandom {@link List} of {@link Deck} from the {@link Game} decks with a given size.
	 * @param nb {@link Integer}. The size of the returned {@link List}.
	 * @return {@link List} of {@link Deck}.
	 */
	public List<Deck> randomChoice(int nb){
		if(decks.size()<=nb) return null;
		List<Deck> ret = new ArrayList<>();
		List<Deck> tmp = this.getDecks();
		Random rand = new Random();
		for(int i=1;i<=nb;i++) {
			int index = rand.nextInt(tmp.size());
			ret.add(tmp.get(index));
			tmp.remove(index);
		}
		return ret;
	}
	
	/**
	 * Allows to save all the decks in the current {@link Game}.
	 * Decks will be saved in json format file.
	 * @see model.Deck.toJson
	 */
	public void saveAllDecks() {
		for(Deck d : decks) {
			try {
				File f = new File("./src/resources/questions/deck_" + d.getTheme() + ".json");
				f.createNewFile();
				Deck.toJson(d, f);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//CRUD methods
	/**
	 * Allows to add a {@link Deck} in the current {@link Game}.
	 * @param d : {@link Deck}. The {@link Deck} to add
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well removed.
	 */
	public boolean addDeck(Deck d) {
		if(!decks.contains(d)) {
			return decks.add(d.clone());
		}
		return false;
	}
	
	/**
	 * Allows to read a json format file which contains a deck.
	 * @param f : {@link File}. The json file to read.
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well created and added to the {@link Game}.
	 * @see this.addDeck(Deck d)
	 */
	public boolean addDeck(File f) {
		Deck d = Deck.fromJson(f);
		return this.addDeck(d.clone());
	}
	
	/**
	 * Read all .json {@link File} in a predefined directory and convert them into {@link Deck}.
	 * All created decks will be added in the current {@link Game}
	 */
	public void addAllDeck() {
		for(File f : new File("./src/resources/questions").listFiles()) {
			this.addDeck(f);
		}
	}
	/**
	 * Allows to remove a {@link Deck} from the Game.
	 * @param d : {@link Deck}. The {@link Deck} to remove.
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well removed.
	 */
	public boolean removeDeck(Deck d) {
		if(decks.contains(d)) {
			decks.remove(d);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to remove a {@link Deck} at a specific index.
	 * @param index : {@link Integer}. The index of the deck to remove.
	 * @return {@link Boolean}. <code>true</code> if the deck is well removed.
	 */
	public boolean removeDeck(int index) {
		if(decks.size()>index && index>=0) {
			decks.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Get a cloned list of decks included in the current {@link Game} decks.
	 * @return {@link List} of {@link Deck}
	 */
	public List<Deck> getDecks() {
		List<Deck> ret = new ArrayList<>();
		for(Deck d : decks) {
			ret.add(d.clone());
		}
		return ret;
	}
	
	/**
	 * @param d : {@link Deck}. The {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getDeck(Deck d) {
		if(!decks.contains(d)) return null;
		return decks.get(decks.indexOf(d)).clone();
	}
	/**
	 * @param theme {@link String}. The theme of the {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getDeck(String theme) {
		for(Deck in : decks) {
			if(in.getTheme().equalsIgnoreCase(theme)) {
				return in.clone();
			}
		}
		return null;
	}
	//Basic methods
	@Override
	public String toString() {
		String s="";
		for(Deck d : decks) {
			s += "---------\n" + d.toString(); 
		}
		return s;
	}
}
