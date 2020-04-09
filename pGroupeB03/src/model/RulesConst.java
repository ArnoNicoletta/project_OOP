package model;

import exception.WrongRuleValueException;

public abstract class RulesConst {
	
	//Player rules
	public final static int MAX_CHAR = 16;
	public final static int MAX_PLAYER = 3;
	//Question and Deck rules
	public final static int MIN_QUESTIONS = 10;
	//Game rules
	private static int number_round = 1;
	private static double round_time_seconds = 45;
	
	
	//Setters
	public static void setNumber_round(int number_round) throws WrongRuleValueException {
		if(number_round<1 || number_round>5) throw new WrongRuleValueException(""+number_round);
		RulesConst.number_round = number_round;
	}
	public static void setRound_time_seconds(double round_time_seconds) throws WrongRuleValueException {
		if(round_time_seconds<15 || round_time_seconds>90) throw new WrongRuleValueException(""+ round_time_seconds);
		RulesConst.round_time_seconds = round_time_seconds;
	}
	
	//Getters
	public static int getNumber_round() {
		return number_round;
	}
	public static double getRound_time_seconds() {
		return round_time_seconds;
	}
	
	
}
