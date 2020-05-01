package test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.QuestionAlreadyExistException;
import model.GameDecksAndPlayers;
import model.Question;

class TestGameDecksAndPlayers {
	
	static GameDecksAndPlayers g;
	
	@BeforeAll
	public static void initG() {
		g = GameDecksAndPlayers.getInstance();
	}
	
	@BeforeEach
	public void resetG() {
		g.removeAllDecks();
		g.removeAllPlayers();
		g.setPosPlayer(0);
		g.setPosQuestion(0);
	}
	
	@Test
	public void addOneQuestion() throws QuestionAlreadyExistException {
		g.addQuestion(new Question("author", "theme", Arrays.asList("","",""), "answer"));
		assertEquals(g.getDecks().size(), 1);
		assertEquals(g.getNumberOfDecks(), 1);
	}
	
	@Test
	public void addSameQuestion() throws QuestionAlreadyExistException {
		g.addQuestion(new Question("author", "theme", Arrays.asList("","",""), "answer"));
		Assertions.assertThrows(QuestionAlreadyExistException.class, 
				() -> g.addQuestion(new Question("author", "theme", Arrays.asList("","",""), "answer")));
	}
	
}
