package model;

public class Player {
	
	private String pseudo;
	private int score;
	private double time;
	
	public Player(String pseudo){
		this.pseudo = pseudo;
	}
	
	public Player(String pseudo, int score, double time) {
		this.pseudo = pseudo;
		this.score = score;
		this.time = time;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return this.pseudo;
	}
	public Player clone() {
		return new Player(this.pseudo, this.score, this.time);
	}
}
