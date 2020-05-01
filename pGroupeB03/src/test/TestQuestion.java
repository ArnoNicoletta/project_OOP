package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Question;

class TestQuestion {
	
	Question q;
	
	@BeforeEach
	public void initQ() {
		q = new Question("author", "theme", Arrays.asList("","",""), "answer");
	}
	
	@Test
	public void author() {
		assertEquals(q.getAuthor(), "author");
		assertNotEquals(q.getAuthor(), "Author");
		assertNotEquals(q.getAuthor(), null);
	}
	
	@Test
	public void theme() {
		assertEquals(q.getTheme(), "theme");
		assertNotEquals(q.getTheme(), "Theme");
		assertNotEquals(q.getTheme(), null);
	}
	
	@Test
	public void clues() {
		assertEquals(q.getClues(), Arrays.asList("","",""));
		assertNotEquals(q.getTheme(), null);
	}
	
	@Test
	public void answer() {
		assertEquals(q.getAnswer(), "answer");
		assertNotEquals(q.getAnswer(), "Answer");
		assertNotEquals(q.getAnswer(), null);
	}
	
	@Test
	public void equalsQuestion() {
		assertTrue(q.equals(q));
		assertTrue(q.equals(new Question("author", "theme", Arrays.asList("","",""), "answer")));
		assertTrue(q.equals(new Question("author", "theme", Arrays.asList("","",""), "Answer")));
		assertTrue(q.equals(new Question("author", "Theme", Arrays.asList("","",""), "answer")));
		//Tests on different strings
		assertFalse(q.equals(new Question("author", "themes", Arrays.asList("","",""), "answer")));
		assertFalse(q.equals(new Question("author", "theme", Arrays.asList("","",""), "answers")));
		//Tests with null strings
		assertFalse(q.equals(new Question("author", "theme", Arrays.asList("","",""), null)));
		assertFalse(q.equals(new Question("author", null, Arrays.asList("","",""), "answer")));
		//Tests with other objects
		assertFalse(q.equals(null));
		assertFalse(q.equals(new Object()));
		//Test with null variables
		q.setAnswer(null);
		assertTrue(q.equals(new Question("author", "theme", Arrays.asList("","",""), null)));
		assertFalse(q.equals(new Question("author", "theme", Arrays.asList("","",""), "answer")));
		q.setAnswer("answer");
		q.setTheme(null);
		assertTrue(q.equals(new Question("author", null, Arrays.asList("","",""), "answer")));
		assertFalse(q.equals(new Question("author", "theme", Arrays.asList("","",""), "answer")));
	}
	
	@Test
	public void cloneQuestion() {
		assertEquals(q, q.clone());
	}
	
	@Test
	public void toStringQuestion() {
		assertEquals(q.toString(), 
				"Question [author=" + q.getAuthor() + ", theme=" + q.getTheme() + 
				", clues=" + q.getClues() + ", answer=" + q.getAnswer() + "]");
	}
	
	@Test
	public void hashCodeQuestion() {
		assertEquals(q.hashCode(), new Question("author", "theme", Arrays.asList("","",""), "answer").hashCode());
		assertNotEquals(q.hashCode(), new Question("author", "theme", Arrays.asList("","",""), null).hashCode());
		assertNotEquals(q.hashCode(), new Question("author", null, Arrays.asList("","",""), "answer").hashCode());
	}
}
