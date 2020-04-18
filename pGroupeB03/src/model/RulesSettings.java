package model;

import exception.WrongRuleValueException;

public class RulesSettings {
	
	//Player rules
	public final static int MAX_CHAR = 16;
	public final static int MAX_PLAYER = 3;
	//Question and Deck rules
	public final static int MIN_QUESTIONS = 10;
	//Game rules
	public final static double JOKER_TIME = 10;
	private static int number_round = 1;
	private static int max_score = 4;
	private static double round_time_seconds = 45;
	private static double time_gap_millis = 25;
	//UI rules
	private static boolean faced_joker = true;
	private static boolean sound_enabled = true;
	
	//Setters
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
	public static void setFaced_joker(boolean faced_joker) {
		RulesSettings.faced_joker = faced_joker;
	}
	public static void setSound_enabled(boolean sound_enabled) {
		RulesSettings.sound_enabled = sound_enabled;
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
	public static boolean getFaced_joker() {
		return faced_joker;
	}
	public static boolean getSound_enabled() {
		return sound_enabled;
	}
	
}
