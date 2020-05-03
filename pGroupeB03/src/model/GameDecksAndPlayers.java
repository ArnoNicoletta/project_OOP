package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exception.EmptyPseudoException;
import exception.IdenticalPseudoException;
import exception.NotEnoughDeckException;
import exception.TooMuchCharException;

public class GameDecksAndPlayers extends GameDecks {
	
	private List<Player> players;
	private int posPlayer;
	private List<Deck> usedDecks;
	private int posQuestion;
	
	protected static GameDecksAndPlayers instance;
	
	protected GameDecksAndPlayers() {
		super();
		this.players = new ArrayList<>();
		this.usedDecks = new ArrayList<>();
	}
	
	public static GameDecksAndPlayers getInstance() {
		if(instance==null) {
			instance = new GameDecksAndPlayers();
		}
		return (GameDecksAndPlayers) instance;
	}
	/**
	 * Reset the {@link GameDecks} instance.
	 */
	public static GameDecksAndPlayers reset() {
		GameDecks.reset();
		instance = new GameDecksAndPlayers();
		return instance;
	}
	/**
	 * Allows to replay a game by keeping the players and choosing new decks.
	 */
	public void replay() throws NotEnoughDeckException {
		this.usedDecks = new ArrayList<>();
		addAllDeck();
		shuffleDecks();
		players.forEach(p -> p.setScore(0));
		randomChoice();
		setPosPlayer(0);
		setPosQuestion(0);
	}

	/**
	 * Get the number of the players in the current game.
	 * @return {@link Integer}. The number of players.
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}
	
	/**
	 * Stores a <code>pseudo-random</code> {@link List} of {@link Deck} from the current {@link GameDecks} decks with a given size.
	 * This list of deck will replace all the decks in the game.
	 * @throws NotEnoughDeckException 
	 */
	public void randomChoice() throws NotEnoughDeckException {
		super.updateAllDecks(super.randomChoice(getNumberOfPlayers()));
	}
	
	/**
	 * Gets the clues at the specific index in argument.
	 * @param index : {@link Integer}. The index of the clue asked.
	 * @return {@link String}. The clue asked.
	 */
	public String getClue(int index) {
		return getUsingDeck().getQuestion(getPosQuestion()).getClues().get(index);
	}
	

	/**
	 * Checks if the deck d is finished,
	 * i.e. if the last question displayed is the last in the deck.
	 * @param d : {@link Deck}. 
	 * @return {@link Boolean}. <code>true</code> if finished.
	 */
	public boolean isDeckFinished() {
		return getPosQuestion() >= getUsingDeck().getSizeQuestions()-1 || getPlayer(getPosPlayer()).getScore() == RulesSettings.getMax_score();
	}
	
	/**
	 * Checks if the current game is finished,
	 * i.e. if all players have already played.
	 * @return {@link Boolean}. <code>true</code> if finished
	 */
	public boolean isFinished() {
		return getPosPlayer() >= getNumberOfPlayers() - 1;
	}

	
	/**
	 * 
	 * @param playerAnswer : {@link String}. The player input text.
	 * @return
	 */
	public boolean isRightAnswer(String playerAnswer) {
		return getUsingDeck().getQuestion(getPosQuestion()).getAnswer().equalsIgnoreCase(playerAnswer.trim());
	}
	
	/**
	 * Add 1 point in the score of the current player.
	 * @return {@link Boolean}. <code>true</code> if well added.
	 */
	public boolean addPoint() {
		return getPlayer().addPoint();
	}
	/**
	 * Defines if a {@link Deck} has already be used in the game,
	 * i.e. if any players has already been asked about this {@link Deck} d.
	 * @param d : {@link Deck}. The deck to check.
	 * @return {@link Boolean}. <code>true</code> if the deck has been used.
	 */
	public boolean hasBeenUsed(Deck d) {
		return usedDecks.contains(d);
	}
	
	/**
	 * Sort the players according to their score and time.
	 * @see PlayersComparator
	 */
	private void sortPlayers() {
		Collections.sort(players, new PlayersComparator());
	}
	
	
	/**
	 * End the game by sorting the players depending on their respective score and time.
	 * @see PlayersComparator
	 */
	public void endGame() {
		sortPlayers();
		Highscores.getInstance().compareAndSave(players);
	}
	
	// ************
	// CRUD methods
	// ************
	
	
	
	// On players
	
	
	
	/**
	 * Allows to add a {@link Player} in the current {@link GameDecks}.
	 * @param p : {@link Player}. The {@link Player} to add
	 * @return {@link Boolean}. <code>true</code> if the {@link Player} is well added.
	 * @throws IdenticalPseudoException if pseudo already in the current game.
	 */
	public boolean addPlayer(Player p) throws IdenticalPseudoException {
		if(players.contains(p)) throw new IdenticalPseudoException(p.getPseudo());
		return players.add(p);
	}
	
	/**
	 * Allows to add a {@link Player} in the current {@link GameDecks} by giving only a {@link String} pseudo.
	 * @param pseudo : {@link String}. The pseudo of the {@link Player} to add
	 * @return {@link Boolean}. <code>true</code> if the {@link Player} is well added.
	 * @throws IdenticalPseudoException if pseudo already in the current game.
	 * @throws TooMuchCharException 
	 */
	public boolean addPlayer(String pseudo) throws IdenticalPseudoException, TooMuchCharException, EmptyPseudoException {
		Player p = new Player(pseudo);
		return addPlayer(p);
	}
	
	/**
	 * Allows to add multiple players from a list.
	 * @param newPlayers : {@link List}<{@link Player}>. Players to be added.
	 * @return {@link Boolean}. <code>true</code> if players have been well added.
	 * @throws IdenticalPseudoException if pseudo already in the current game.
	 */
	public boolean addAllPlayers(List<Player> newPlayers) throws IdenticalPseudoException {
		for(Player p : newPlayers) {
			this.addPlayer(p);
		}
		return true;
	}
	
	/**
	 * Allows to remove a {@link Player} from the Game.
	 * @param p : {@link Player}. The {@link Player} to remove.
	 * @return {@link Boolean}. <code>true</code> if the {@link Player} is well removed.
	 */
	public boolean removePlayer(Player p) {
		if(players.contains(p)) {
			players.remove(p);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to remove a {@link Player} at a specific index.
	 * @param index : {@link Integer}. The index of the {@link Player} to remove.
	 * @return {@link Boolean}. <code>true</code> if the {@link Player} is well removed.
	 */
	public boolean removePlayer(int index) {
		if(players.size()>index && index>=0) {
			players.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Allows to remove all the {@link Player} in the current {@link GameDecks}.
	 * @return {@link Boolean}. <code>true</code> when all players have been removed.
	 */
	public boolean removeAllPlayers() {
		players.clear();
		return true;
	}
	
	/**
	 * Get a cloned list of players included in the current {@link GameDecks} decks.
	 * @return {@link List}<{@link Player}>.
	 */
	public List<Player> getPlayers() {
		List<Player> ret = new ArrayList<>();
		for(Player p : players) {
			ret.add(p.clone());
		}
		return ret;
	}
	
	/**
	 * Get a specific {@link Player} p.
	 * @param p : {@link Player}. The {@link Player} to be returned.
	 * @return {@link Player}. Return the {@link Player} p if found or <code>null</code> if not.
	 */
	public Player getPlayer(Player p) {
		if(!players.contains(p)) return null;
		return players.get(players.indexOf(p));
	}
	
	/**
	 * Get a {@link Player} from a specific pseudo.
	 * @param pseudo {@link String}. The pseudo of the {@link Player} to be returned.
	 * @return {@link Player}. Return the {@link Player} p if found or <code>null</code> if not.
	 */
	public Player getPlayer(String pseudo) {
		for(Player in : players) {
			if(in.getPseudo().equalsIgnoreCase(pseudo)) {
				return in;
			}
		}
		return null;
	}
	
	/**
	 * Get a {@link Player} from a specific position.
	 * @param index {@link Integer}. The position of the {@link Player} to be returned.
	 * @return {@link Player}. Return the {@link Player} p if found or <code>null</code> if not.
	 */
	public Player getPlayer(int index) {
		if(index<0 || index>=players.size()) return null;
		return players.get(index);
	}
	
	
	/**
	 * Get the current {@link Player}.
	 * This method gives the player supposed to be playing at the moment.
	 * @return {@link Player}.
	 */
	public Player getPlayer() {
		return getPlayer(getPosPlayer());
	}
	
	
	// On usedDecks -> Decks already used or being used
	
	
	
	/**
	 * Takes a deck available in the current ( i.e. in decks ) and add it to usedDecks.
	 * @param d : {@link Deck}. The {@link Deck} to add.
	 * @return {@link Boolean}. <code>true</code> if found and added, <code>false</code> otherwise.
	 */
	public boolean addUsedDeck(Deck d) {
		return usedDecks.add(this.getDeck(d));
	}
	
	/**
	 * Takes a deck available in the current ( i.e. in decks ) and add it to usedDecks.
	 * @param theme : {@link String}. The theme of the {@link Deck} to add.
	 * @return {@link Boolean}. <code>true</code> if found and added, <code>false</code> otherwise.
	 */
	public boolean addUsedDeck(String theme) {
		setPosQuestion(0);
		return usedDecks.add(this.getDeck(theme));
	}
	
	/**
	 * Get a specific {@link Deck} d in usedDecks.
	 * @param d : {@link Deck}. The {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getUsedDeck(Deck d) {
		if(!usedDecks.contains(d)) return null;
		return usedDecks.get(usedDecks.indexOf(d)).clone();
	}
	
	/**
	 * Get a {@link Deck} d from a specific theme in usedDecks.
	 * @param theme {@link String}. The theme of the {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getUsedDeck(String theme) {
		for(Deck in : usedDecks) {
			if(in.getTheme().equalsIgnoreCase(theme)) {
				return in.clone();
			}
		}
		return null;
	}
	
	/**
	 * Get a {@link Deck} d at a specific position in usedDecks.
	 * @param index : {@link Integer}. The position of the {@link Deck} to be returned.
	 * @return {@link Deck}. Return the {@link Deck} d if found or <code>null</code> if not.
	 */
	public Deck getUsedDeck(int index) {
		if(usedDecks.isEmpty() || index<0 || index>=usedDecks.size()) return null;
		return usedDecks.get(index).clone();
	}
	
	/**
	 * Get the last {@link Deck} d of the usedDecks which is the one played at this moment.
	 * @return {@link Deck}. Return the {@link Deck} d.
	 */
	public Deck getUsingDeck() {
		return getUsedDeck(usedDecks.size()-1);
	}
	
	
	
	
	// Basic getters and setters
	
	
	
	public int getPosPlayer() {
		return posPlayer;
	}
	public void setPosPlayer(int posPlayer) {
		if(posPlayer>=0 && posPlayer<=getNumberOfPlayers()) this.posPlayer = posPlayer;
	}
	public void nextPosPlayer() {
		setPosPlayer(getPosPlayer()+1);
	}
	public int getPosQuestion() {
		return posQuestion;
	}
	public void setPosQuestion(int posQuestion) {
		if(posQuestion>=0) this.posQuestion = posQuestion;
	}
	public void nextPosQuestion() {
		setPosQuestion(getPosQuestion()+1);
	}
}
