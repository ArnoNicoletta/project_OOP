package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class manages players.
 * @author ArRaLo
 *
 */
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
	
	//JSON methods
	
	/**
	 * Read a .json {@link File} and convert it into a {@link Player}
	 * @param file : {@link File}. The file to read. Must be in .json file format.
	 * @return the created {@link Player}
	 */
	public static Player fromJson(File file) {
		Player ret = null;
		Gson gson = new Gson();
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			ret = gson.fromJson(br, Player.class);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.clone();
	}
	
	/**
	 * Write a {@link Player} in a json {@link File} format.
	 * @param p : {@link Player}. The {@link Player} you want to write.
	 * @param file : {@link File}. The json {@link File} which you want to write in.
	 */
	public static void toJson(Player p, File file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(p);
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
			bw.write(json);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//Getters and Setters
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
		if(score>=0 && score<=4)
			this.score = score;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		if(time>=0 && time<=IRulesConst.ROUND_TIME)
			this.time = time;
	}
	//Basic methods
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
