package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exception.DeckNotFoundException;
import exception.NotEnoughDeckException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongDeckFormatException;

/**
 * This class manages list of {@link Deck}.
 * @author ArRaLo
 * @see QuestionOperation
 * @see model.Deck
 * @see model.Question
 */
public class GameDecks implements QuestionOperation {
	
	private final String PATH = "./src/resources/questions";
	private List<Deck> decks;
	
	protected static GameDecks instance;
	
	/**
	 * Constuctor.
	 * Private constructor so you can only create one instance of the class.
	 */
	protected GameDecks() {
		this.decks = new ArrayList<>();
		this.addAllDeck();
		this.shuffleDecks();
	}
	
	/**
	 * The only way to get an instance of {@link GameDecks}. 
	 * This instance will be initialize with available decks in the default directory.
	 * If you only need an instance of {@link GameDecks}, you can still use removeAllDecks().
	 * @return {@link GameDecks} instance. The only one instance of the class {@link GameDecks}.
	 */
	public static GameDecks getInstance() {
		if(instance==null) {
			instance = new GameDecks();
		}
		return instance;
	}
	
	/**
	 * Reset the {@link GameDecks} instance.
	 * @return {@link GameDecks} instance.
	 */
	public static GameDecks reset() {
		instance = new GameDecks();
		instance.addAllDeck();
		instance.shuffleDecks();
		return instance;
	}
	
	/**
	 * Get the number of the decks available in the current game.
	 * @return {@link Integer}. The number of decks.
	 */
	public int getNumberOfDecks() {
		return decks.size();
	}
	
	
	/**
	 * Shuffles the questions inside each deck.
	 */
	public void shuffleDecks() {
		for(Deck d : decks) {
			d.shuffleQuestions();
		}
	}
	
	/**
	 * Gives a <code>pseudo-random</code> {@link List} of {@link Deck} from the current {@link GameDecks} decks with a given size.
	 * @param nb : {@link Integer}. The <code>size-1</code> of the returned {@link List}.
	 * As it is made to play the game, it will give <code>nb</code> elements instead of <code>nb-1</code> elements,
	 * and it will check if the decks have 
	 * @return {@link List}<{@link Deck}>.
	 * @throws NotEnoughDeckException 
	 */
	public List<Deck> randomChoice(int nb) throws NotEnoughDeckException{
		if(decks.size()<=nb) throw new NotEnoughDeckException(nb);
		List<Deck> ret = new ArrayList<>();
		List<Deck> tmp = getDecks().parallelStream()
							.filter(d -> d.getSizeQuestions()>=RulesSettings.getMin_questions())
							.collect(Collectors.toList());
		if(tmp.size()<nb) throw new NotEnoughDeckException(nb);
		Random rand = new Random();
		for(int i=0;i<=nb;i++) {
			int index = rand.nextInt(tmp.size());
			ret.add(tmp.get(index));
			tmp.remove(index);
		}
		return ret;
	}
	
	
	
	
	
	// JSON methods
	
	
	/**
	 * Allows to save all the decks in the current {@link GameDecks}.
	 * Decks will be saved in json format file.
	 * @return {@link Boolean}. <code>true</code> when decks have been saved, <code>false</code> if any error occurred.
	 * @see model.Deck
	 */
	public boolean saveAllDecks() {
		decks.parallelStream().filter(d -> d.hasUniqueTheme()).forEach((d) -> {
			try {
				File f = new File(PATH + "/deck_" + d.getTheme() + ".json");
				f.createNewFile();
				Deck.toJson(d, f);
				
			} catch (IOException e) {}
		});
		return true;
	}
	
	/**
	 * Allows to export all the decks in a specified file.
	 * @param f : {@link File}. The file to write in.
	 */
	public void exportAllDecks(File f) {
		List<Question> lq = new ArrayList<>();
		for(Deck in : decks) {
			lq.addAll(in.getQuestions());
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(lq);
		json = "{\r\n" + 
				"  \"questions\": " + json + "\r\n" + 
						"}";
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
			bw.write(json);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Methods to modify questions (used by admins)
	
	@Override
	public boolean addQuestion(String author, String theme, List<String> clues, String answer) throws QuestionAlreadyExistException {
		Question q = new Question(author, theme, clues, answer);
		if(getDeck(q.getTheme()) != null) {
			for(Deck in : decks) {
				if(in.getTheme().equalsIgnoreCase(q.getTheme())) {
					in.addQuestion(q);
				}
			}
		}
		else {
			Deck d = new Deck();
			d.addQuestion(q);
			this.addDeck(d);
		}
		saveAllDecks();
		return true;
	}
	
	public boolean addQuestion(Question q) throws QuestionAlreadyExistException {
		return this.addQuestion(q.getAuthor(), q.getTheme(), q.getClues(), q.getAnswer());
	}
	
	@Override
	public boolean deleteQuestion(Question q) throws QuestionNotFoundException, DeckNotFoundException {
		Deck d = null;
		for(Deck in : decks) {
			if(in.getTheme().equalsIgnoreCase(q.getTheme())) {
				d = in;
			}
		}
		if(d == null) throw new DeckNotFoundException(q.getTheme());
		if(d.getSizeQuestions() == 1 && d.getQuestion(0).equals(q)) {
			deleteDeck(d.getTheme());
		}
		else {
			d.deleteQuestion(q);
		}
		saveAllDecks();
		addAllDeck();
		return true;
	}
	
	@Override
	public boolean deleteDeck(String theme) throws DeckNotFoundException {
		if(getDeck(theme)==null) throw new DeckNotFoundException(theme);
		File f = new File("./src/resources/questions/deck_" + theme + ".json");
		if(!f.exists() || f.delete()) {
			addAllDeck();
		}
		return true;
	}
	
	@Override
	public boolean modifyQuestion(Question oldQ, Question newQ) throws DeckNotFoundException, QuestionNotFoundException {
		if(getDeck(oldQ.getTheme()) == null) throw new DeckNotFoundException(oldQ.getTheme());
		for(Deck in : decks) {
			if(in.getTheme().equalsIgnoreCase(oldQ.getTheme())) {
				in.modifyQuestion(oldQ, newQ);
				break;
			}
		}
		saveAllDecks();
		return true;
	}
	
	
	// ************
	// CRUD methods
	// ************
	
	
	
	// On decks
	
	
	
	/**
	 * Allows to add a {@link Deck} in the current {@link GameDecks}.
	 * @param d : {@link Deck}. The {@link Deck} to add
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well added.
	 */
	public boolean addDeck(Deck d) {
		if(!decks.contains(d)) {
			return decks.add(d.clone());
		}
		return false;
	}
	
	/**
	 * Allows to read a json format file which contains a deck and add it to the game.
	 * @param f : {@link File}. The json file to read.
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well created and added to the {@link GameDecks}.
	 * @throws WrongDeckFormatException 
	 */
	public boolean addDeck(File f) throws WrongDeckFormatException {
		Deck d = Deck.fromJson(f);
		if(!d.hasUniqueTheme()) {
			List<Question> lq = d.getQuestions();
			Map<String, List<Question>> map = lq.stream().collect(Collectors.groupingBy(Question::getTheme));
			map.keySet().forEach(s -> {
				Deck toAdd = new Deck();
				map.get(s).forEach(q -> {
					try {
						toAdd.addQuestion(q);
					} catch (QuestionAlreadyExistException e) {}
				});
				this.addDeck(toAdd.clone());
			});
			return true;
		}
		else return this.addDeck(d.clone());
	}
	
	/**
	 * Read all .json {@link File} in a predefined directory, convert them into {@link Deck}.
	 * All created decks will be added in the current {@link GameDecks}
	 */
	public void addAllDeck() {
		decks.clear();
		for(File f : new File(PATH).listFiles()) {
			try {
				this.addDeck(f);
			} catch (WrongDeckFormatException e) {}
		}
	}
	
	/**
	 * Allows to add decks from a {@link List}.
	 * @param newDecks : {@link List}<{@link Deck}>. Decks to be added.
	 */
	public void addAllDeck(List<Deck> newDecks) {
		for(Deck d : newDecks) {
			this.addDeck(d);
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
	 * @param index : {@link Integer}. The index of the {@link Deck} to remove.
	 * @return {@link Boolean}. <code>true</code> if the {@link Deck} is well removed.
	 */
	public boolean removeDeck(int index) {
		if(decks.size()>index && index>=0) {
			decks.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to remove all the {@link Deck} in the current {@link GameDecks}.
	 */
	public void removeAllDecks() {
		decks.clear();
	}
	
	/**
	 * Allows to update all decks from the current game into a new list of decks.
	 * This will remove all decks available and replace them with the specified new decks.
	 * @param newDecks : {@link List}<{@link Deck}>. Decks to be added.
	 */
	public void updateAllDecks(List<Deck> newDecks) {
		if(newDecks == null || newDecks.isEmpty()) return;
		this.removeAllDecks();
		this.addAllDeck(newDecks);
	}
	
	/**
	 * Get a cloned list of decks included in the current {@link GameDecks} decks.
	 * @return {@link List}<{@link Deck}>.
	 */
	public List<Deck> getDecks() {
		List<Deck> ret = new ArrayList<>();
		for(Deck d : decks) {
			ret.add(d.clone());
		}
		return ret;
	}
	
	/**
	 * Get a specific {@link Deck} d.
	 * @param d : {@link Deck}. The {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getDeck(Deck d) {
		if(!decks.contains(d)) return null;
		return decks.get(decks.indexOf(d)).clone();
	}
	
	/**
	 * Get a {@link Deck} from a specific theme.
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
	
	/**
	 * Get a {@link Deck} at a specific position.
	 * @param index : {@link Integer}. The position of the {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getDeck(int index) {
		if(index<0 || index>=decks.size()) return null;
		return decks.get(index);
	}
	
	
	
	
	
	
	//Basic methods
	@Override
	public String toString() {
		String s="";
		for(Deck d : decks) {
			s += d.toString() + "---------\n"; 
		}
		return s;
	}
}
