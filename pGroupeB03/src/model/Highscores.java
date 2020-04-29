package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Highscores {
	
	private final String PATH = "./src/resources/user/highscores.json";
	private List<Player> highscores;
	
	private static Highscores instance;
	
	private Highscores() {
		this.highscores = new ArrayList<>();
	}
	
	public static Highscores getInstance() {
		if(instance==null) {
			instance = new Highscores();
			instance.initHighscores();
			instance.sortHighscores();
		}
		return instance;
	}
	
	public List<Player> getHighscores(){
		List<Player> ret = new ArrayList<>();
		for(Player p : highscores) {
			ret.add(p);
		}
		return ret;
	}
	
	/**
	 * Initialize the highscores list from the json file where are stored the top 5 players.
	 */
	private void initHighscores() {
		ArrayList<Player> ret = new ArrayList<>();
		Gson gson = new Gson();
		try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
			ret = gson.fromJson(br, new TypeToken<ArrayList<Player>>() {}.getType());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(ret != null) highscores = ret;
	}
	/**
	 * Sort the highscores.
	 */
	private void sortHighscores() {
		Collections.sort(highscores, new PlayersComparator());
		if(highscores.size()>5) {
			highscores = highscores.subList(0, 5);
		}
	}
	
	/**
	 * Saves the highscores list in the json file where are stored the top 5 players.
	 */
	private void saveHighscores() {
		sortHighscores();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(highscores);
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))){
			bw.write(json);
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Compare the saved highscores with the new scores, add them if needed and save them.
	 * @param newScores : {@link List}<{@link Player}>. The new scores to compare.
	 */
	public void compareAndSave(List<Player> newScores) {
		compare(newScores);
		saveHighscores();
	}
	
	/**
	 * Compare the saved highscores with the new scores and add them if needed.
	 * @param newScores
	 */
	private void compare(List<Player> newScores) {
		highscores.addAll(newScores);
		sortHighscores();
	}
}
