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
	private static double round_time_seconds = 45;
	//UI rules
	private static boolean faced_joker = true;
	private static boolean sound_enabled = true;
	
	//Setters
	public static void setNumber_round(int number_round) throws WrongRuleValueException {
		if(number_round<1 || number_round>5) throw new WrongRuleValueException(""+number_round);
		RulesSettings.number_round = number_round;
	}
	public static void setRound_time_seconds(double round_time_seconds) throws WrongRuleValueException {
		if(round_time_seconds<15 || round_time_seconds>90) throw new WrongRuleValueException(""+ round_time_seconds);
		RulesSettings.round_time_seconds = round_time_seconds;
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
	public static double getRound_time_seconds() {
		return round_time_seconds;
	}
	public static boolean getFaced_joker() {
		return faced_joker;
	}
	public static boolean getSound_enabled() {
		return sound_enabled;
	}
	
}
