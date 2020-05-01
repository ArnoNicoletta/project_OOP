package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongDeckFormatException;
import model.Deck;
import model.Question;

@SuppressWarnings("unchecked")
class TestDeck {
	
	private Deck d;
	private List<Question> questions;
	private Question q = new Question("author", "theme", Arrays.asList("","",""), "answer");
	
	
	@BeforeEach
	public void initVar() {
		d = new Deck();
		questions = (List<Question>) Explorateur.getField(d, "questions");
	}
	
	@AfterAll
	public static void deleteUnused() {
		File test = new File("test.json");
		if(test.exists()) test.delete();
	}
	
	
	@Test
	public void getQuestionsTest(){
		assertEquals(questions, d.getQuestions());
		questions.add(q);
		assertEquals(questions, d.getQuestions());
	}
	
	@Test
	public void getQuestionTest() {
		assertEquals(null, d.getQuestion(0));
		questions.add(q);
		assertEquals(q, d.getQuestion(0));
	}
	
	@Test
	public void sizeQuestionsTest() {
		assertEquals(0, d.getSizeQuestions());
		questions.add(q);
		assertEquals(1, d.getSizeQuestions());
	}
	
	@Test
	public void getThemeTest() {
		assertEquals("", d.getTheme());
		questions.add(q);
		assertEquals(q.getTheme(), d.getTheme());
	}
	
	@Test
	public void hasUniqueThemeTest() {
		questions.add(q);
		assertTrue(d.hasUniqueTheme());
		questions.add(new Question("author", "anotherTheme", Arrays.asList("","",""), "answer"));
		assertFalse(d.hasUniqueTheme());
	}
	
	@Test
	public void addQuestionTest() throws QuestionAlreadyExistException {
		d.addQuestion(q);
		assertEquals(1, questions.size());
		Assertions.assertThrows(QuestionAlreadyExistException.class, () -> d.addQuestion(q));
		d.addQuestion(new Question("author", " ", Arrays.asList("","",""), "answer")); //different theme
		assertEquals(1, questions.size());
		d.addQuestion(new Question("author", "theme", Arrays.asList("","",""), "differentanswer")); //same theme, different answer
		assertEquals(2, questions.size());
	}
	
	@Test
	public void deleteQuestionTest() throws QuestionNotFoundException {
		questions.add(q);
		assertTrue(d.deleteQuestion(q));
		assertEquals(0, questions.size());
		Assertions.assertThrows(QuestionNotFoundException.class, () -> d.deleteQuestion(q));
		assertFalse(d.deleteQuestion(1));
		questions.add(q);
		assertTrue(d.deleteQuestion(0));
		assertEquals(0,  questions.size());
	}
	
	@Test
	public void modifyQuestionTest() throws QuestionNotFoundException {
		questions.add(q);
		assertTrue(d.modifyQuestion(q, new Question("author", "theme", Arrays.asList("","",""), "differentanswer")));
		Assertions.assertThrows(QuestionNotFoundException.class, () -> d.modifyQuestion(q, null));
	}
	
	@Test
	public void cloneTest() {
		assertEquals(d, d.clone());
		questions.add(q);
		assertEquals(questions, d.clone().getQuestions());
	}
	
	@Test
	public void equalsTest() {
		assertTrue(d.equals(d));
		assertFalse(d.equals(null));
		assertFalse(d.equals(new Object()));
		Deck tmpD = new Deck();
		assertTrue(d.equals(tmpD));
		questions.add(q);
		assertFalse(d.equals(tmpD));
		questions = null;
		assertFalse(d.equals(tmpD));
	}
	
	@Test
	public void shuffleTest() {
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
		List<Question> tmp = new ArrayList<>();
		tmp.addAll(questions);
		d.shuffleQuestions();
		assertNotEquals(tmp, questions);
	}
	
	@Test
	public void testIO() throws WrongDeckFormatException {
		questions.add(q);
		Deck.toJson(d, new File("test.json"));
		assertTrue(new File("test.json").exists());
		Deck tmpD = new Deck();
		List<Question> tmpQ = (List<Question>) Explorateur.getField(tmpD, "questions");
		tmpQ.addAll(questions);
		d = Deck.fromJson(new File("test.json"));
		assertEquals(tmpD, d);
		Assert.assertThrows(WrongDeckFormatException.class, () -> d = Deck.fromJson(new File(".")));
		assertDoesNotThrow(() -> Deck.toJson(d, new File("")));
	}
	
}
