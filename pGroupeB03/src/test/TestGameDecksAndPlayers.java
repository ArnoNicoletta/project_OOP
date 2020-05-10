package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.GameDecksAndPlayers;

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
	public void testUsedDeck() {
		
	}
	
}
