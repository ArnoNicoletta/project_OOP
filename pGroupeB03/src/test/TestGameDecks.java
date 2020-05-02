package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.QuestionAlreadyExistException;
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
	public void resetTest() {
		questions.add(q);
		decks.add(d);
		assertNotEquals(g, GameDecks.reset());
	}
	
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
	public void testIO() throws QuestionAlreadyExistException, WrongDeckFormatException {
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
	
	
}
