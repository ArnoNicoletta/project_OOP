package model;

import java.util.Comparator;

/**
 * This class is used to compare and sort multiple players.
 * @author ArRaLo
 * @see model.Player
 * @see Comparator
 *
 */
public class PlayersComparator implements Comparator<Player> {

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.getScore() == o2.getScore()) {
			return (int) (o1.getTime() - o2.getTime());
		}
		return o2.getScore() - o1.getScore();
	}
	
}