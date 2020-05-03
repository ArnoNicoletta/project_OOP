package model;

import exception.WrongRuleValueException;

public class RulesSettings {
	
	//Player rules
	private static int max_char = 16;
	private static int max_player = 3;
	//Question and Deck rules
	private static int min_questions = 10;
	//Game rules
	private static double joker_time = 10;
	private static int number_round = 1;
	private static int max_score = 4;
	private static double round_time_seconds = 45;
	private static double time_gap_millis = 25;
	private static double time_gap_answer = 30;
	//UI rules
	private static boolean faced_joker = true;
	private static boolean sound_enabled = true;
	
	private static boolean show_answer = false;
	//Setters
	
	public static void setMax_char(int max_char) throws WrongRuleValueException {
		if(max_char<5 || max_char > 32) throw new WrongRuleValueException(""+max_char);
		RulesSettings.max_char = max_char;
	}
	public static void setMax_player(int max_player) throws WrongRuleValueException {
		if(max_player<1 || max_player > 8) throw new WrongRuleValueException(""+max_player);
		RulesSettings.max_player = max_player;
	}
	public static void setMin_questions(int min_questions) throws WrongRuleValueException {
		if(min_questions<getMax_score() || min_questions > 20) throw new WrongRuleValueException(""+min_questions);
		RulesSettings.min_questions = min_questions;
	}
	public static void setJoker_time(double joker_time) throws WrongRuleValueException {
		if(joker_time<5 || joker_time > getRound_time_seconds()) throw new WrongRuleValueException(""+joker_time);
		RulesSettings.joker_time = joker_time;
	}
	public static void setNumber_round(int number_round) throws WrongRuleValueException {
		if(number_round<1 || number_round>5) throw new WrongRuleValueException(""+number_round);
		RulesSettings.number_round = number_round;
	}
	public static void setMax_score(int max_score) throws WrongRuleValueException {
		if(max_score<=0 || max_score > 10) throw new WrongRuleValueException(""+ max_score);
		RulesSettings.max_score = max_score;
	}
	public static void setRound_time_seconds(double round_time_seconds) throws WrongRuleValueException {
		if(round_time_seconds<15 || round_time_seconds>90) throw new WrongRuleValueException(""+ round_time_seconds);
		RulesSettings.round_time_seconds = round_time_seconds;
	}
	public static void setTime_gap_millis(double time_gap_millis) throws WrongRuleValueException {
		if(time_gap_millis>500 || time_gap_millis<20) throw new WrongRuleValueException(""+ time_gap_millis);
		RulesSettings.time_gap_millis = time_gap_millis;
	}
	public static void setTime_gap_answer(double time_gap_answer) throws WrongRuleValueException {
		if(time_gap_answer>1000 || time_gap_answer<20) throw new WrongRuleValueException(""+ time_gap_answer);
		RulesSettings.time_gap_answer = time_gap_answer;
	}
	public static void setFaced_joker(boolean faced_joker) {
		RulesSettings.faced_joker = faced_joker;
	}
	public static void setSound_enabled(boolean sound_enabled) {
		RulesSettings.sound_enabled = sound_enabled;
	}
	public static void setShow_answer(boolean show_answer) {
		RulesSettings.show_answer = show_answer;
	}
	
	//Getters
	
	public static int getNumber_round() {
		return number_round;
	}
	public static int getMax_score() {
		return max_score;
	}
	public static double getRound_time_seconds() {
		return round_time_seconds;
	}
	public static double getTime_gap_millis() {
		return time_gap_millis;
	}
	public static double getTime_gap_answer() {
		return time_gap_answer;
	}
	public static boolean getFaced_joker() {
		return faced_joker;
	}
	public static boolean getSound_enabled() {
		return sound_enabled;
	}
	public static int getMax_char() {
		return max_char;
	}
	public static int getMax_player() {
		return max_player;
	}
	public static int getMin_questions() {
		return min_questions;
	}
	public static double getJoker_time() {
		return joker_time;
	}
	public static boolean getShow_answer() {
		return show_answer;
	}
}
