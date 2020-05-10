package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DeckNotFoundException;
import exception.NotEnoughDeckException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongDeckFormatException;
import model.Deck;
import model.GameDecks;
import model.Question;

@SuppressWarnings("unchecked")
class TestGameDecks {
	
	static GameDecks g;
	static List<Deck> decks;
	static Deck d;
	static List<Question> questions;
	Question q = new Question("author", "theme", Arrays.asList("","",""), "answer");
	
	
	@BeforeAll
	public static void initG() {
		g = GameDecks.getInstance();
		decks = (List<Deck>) Explorateur.getField(g, "decks");
		d = new Deck();
		questions = (List<Question>) Explorateur.getField(d, "questions");
	}

	@BeforeEach
	public void resetG() {
		g.removeAllDecks();
		decks.clear();
		d = new Deck();
		questions = (List<Question>) Explorateur.getField(d, "questions");
	}
	
	@AfterAll
	public static void deleteUnused() {
		File f = new File("./src/resources/questions/deck_theme.json");
		if(f.exists()) f.delete();
		File test = new File("test.json");
		if(test.exists()) test.delete();
	}
	
	
	// ***********************
	//			TESTS
	// ***********************
	
	@Test
	public void testReset() {
		questions.add(q);
		decks.add(d);
		assertNotEquals(g, GameDecks.reset());
	}
	
	// On questions
	
	@Test
	public void addOneQuestion() throws QuestionAlreadyExistException {
		g.addQuestion(q);
		assertEquals(g.getDecks().size(), 1);
		assertEquals(g.getNumberOfDecks(), 1);
	}
	
	@Test
	public void addSameQuestion() throws QuestionAlreadyExistException {
		g.addQuestion(q);
		Assert.assertThrows(QuestionAlreadyExistException.class, 
				() -> g.addQuestion(new Question("author", "theme", Arrays.asList("","",""), "answer")));
	}
	
	@Test
	public void addQuestionInAnExistingDeck() throws QuestionAlreadyExistException {
		Deck d = new Deck();
		d.addQuestion(q);
		g.addDeck(d);
		Question different = new Question("author", q.getTheme(), Arrays.asList("","",""), "differentAnswer");
		g.addQuestion(different);
		assertEquals(decks.size(), 1);
	}
	
	@Test
	public void testDeleteQuestion() throws QuestionAlreadyExistException, DeckNotFoundException, QuestionNotFoundException {
		Assert.assertThrows( DeckNotFoundException.class, 
				() -> g.deleteQuestion(q));
		questions.add(q);
		decks.add(d);
		Question different = new Question("author", q.getTheme(), Arrays.asList("","",""), "differentAnswer");
		Assert.assertThrows(QuestionNotFoundException.class, 
				() -> g.deleteQuestion(different));
		//Deleting a question in a deck which contains more than 1
		g.addQuestion(different);
		assertTrue(questions.contains(different));
		g.deleteQuestion(different);
		assertFalse(questions.contains(different));
		//Deleting the only one question of a deck (will delete the deck)
		assertTrue(decks.contains(d));
		g.deleteQuestion(q);
		assertFalse(decks.contains(d));
	}
	
	@Test
	public void testModifyQuestion() throws DeckNotFoundException, QuestionNotFoundException {
		assertFalse(g.modifyQuestion(null, null));
		questions.add(q);
		decks.add(d);
		Assert.assertThrows(DeckNotFoundException.class, 
				() -> g.modifyQuestion(new Question("", "", Arrays.asList("","",""), ""), q));
		Assert.assertThrows(QuestionNotFoundException.class, 
				() -> g.modifyQuestion(new Question("", q.getTheme(), Arrays.asList("","",""), ""), q));
		Question different = new Question("author", q.getTheme(), Arrays.asList("","",""), "differentAnswer");
		assertTrue(g.modifyQuestion(q, different));
	}
	
	// On deck
	
	@Test
	public void testUpdateAllDecks() {
		g.updateAllDecks(null);
		assertNotEquals(null, g.getDecks());
		List<Deck> tmp = new ArrayList<>();
		questions.add(q);
		tmp.add(d);
		tmp.add(new Deck());
		g.updateAllDecks(tmp);
		assertEquals(tmp, decks);
	}
	
	@Test
	public void testDeleteDeck() throws DeckNotFoundException {
		Assert.assertThrows(DeckNotFoundException.class, () -> g.deleteDeck(""));
		decks.add(d);
		assertTrue(decks.contains(d));
		assertTrue(g.deleteDeck(d.getTheme()));
		assertFalse(decks.contains(d));
	}
	
	@Test
	public void testGetDeck() {
		questions.add(q);
		decks.add(d);
		//With Deck d
		assertEquals(d, g.getDeck(d));
		assertNull(g.getDeck(new Deck()));
		//With int index
		assertEquals(d, g.getDeck(decks.indexOf(d)));
		assertNull(g.getDeck(decks.size()));
		//With String theme
		assertEquals(d, g.getDeck(d.getTheme()));
		assertNull(g.getDeck("this theme doesnt exist at all"));
	}
	
	@Test
	public void testRemoveDeck() throws DeckNotFoundException {
		questions.add(q);
		decks.add(d);
		//With Deck d
		Assert.assertThrows( DeckNotFoundException.class , () -> g.removeDeck(new Deck()));
		assertTrue(g.removeDeck(d));
		//With int index
		decks.add(d);
		assertFalse(g.removeDeck(decks.size()+1));
		assertTrue(g.removeDeck(decks.indexOf(d)));
	}
	
	
	//IO
	
	@Test
	public void testImport() throws QuestionAlreadyExistException, WrongDeckFormatException {
		questions.add(q);
		questions.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "anotherAnswer"));
		assertEquals(2, questions.size());
		assertFalse(d.hasUniqueTheme());
		decks.add(d);
		File test = new File("test.json");
		Deck.toJson(d, test);
		assertEquals(1, decks.size());
		decks.clear();
		g.addDeck(test);
		assertEquals(2, decks.size());
	}
	
	@Test
	public void testExport() {
		questions.add(q);
		decks.add(d);
		File test = new File("test.json");
		g.exportAllDecks(test);
		assertTrue(test.exists());
	}
	
	
	//Random choice
	
	@Test
	public void testRandomChoice() throws NotEnoughDeckException {
		Assert.assertThrows(NotEnoughDeckException.class, () -> g.randomChoice(0));
		Assert.assertThrows(NotEnoughDeckException.class, () -> g.randomChoice(decks.size()+1));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "1"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "2"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "3"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "4"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "5"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "6"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "7"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "8"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "9"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "10"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "11"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "12"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "13"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "14"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "15"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "16"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "17"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "18"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "19"));
		questions.add(new Question("author", "theme", Arrays.asList("","",""), "20"));
		decks.add(d);
		Deck anotherDeck = new Deck();
		List<Question> questionsAnotherDeck = (List<Question>) Explorateur.getField(anotherDeck, "questions");
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "1"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "2"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "3"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "4"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "5"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "6"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "7"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "8"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "9"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "10"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "11"));
		questionsAnotherDeck.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "12"));
		decks.add(anotherDeck);
		Deck thirdDeck = new Deck();
		List<Question> questionsThirdDeck = (List<Question>) Explorateur.getField(thirdDeck, "questions");
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "1"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "2"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "3"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "4"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "5"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "6"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "7"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "8"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "9"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "10"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "11"));
		questionsThirdDeck.add(new Question("author", "thirdTheme", Arrays.asList("","",""), "12"));
		decks.add(thirdDeck);
		assertNotEquals(decks, g.randomChoice(1));
	}
	
	//toString
	
	@Test
	public void testToString() {
		questions.add(q);
		decks.add(d);
		String s="";
		for(Deck d : decks) {
			s += d.toString() + "---------\n"; 
		}
		assertEquals(s, g.toString());
	}
}
