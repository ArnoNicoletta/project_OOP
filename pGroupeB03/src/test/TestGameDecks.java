package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.QuestionAlreadyExistException;
import model.Deck;
import model.GameDecks;
import model.Question;

@SuppressWarnings("unchecked")
class TestGameDecks {
	
	static GameDecks g;
	static List<Deck> decks;
	static Deck d;
	Question q = new Question("author", "theme", Arrays.asList("","",""), "answer");
	
	
	@BeforeAll
	public static void initG() {
		g = GameDecks.getInstance();
		decks = (List<Deck>) Explorateur.getField(g, "decks");
		d = new Deck();
	}
	
	@AfterAll
	public static void deleteUnused() {
		File f = new File("./src/resources/questions/deck_theme.json");
		if(f.exists()) f.delete();
	}
	
	@BeforeEach
	public void resetG() {
		g.removeAllDecks();
		decks.clear();
		d = new Deck();
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
		Assertions.assertThrows(QuestionAlreadyExistException.class, 
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
}
