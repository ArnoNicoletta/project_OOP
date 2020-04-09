package model;

import java.lang.reflect.Field;

public class IRulesConst {
	
	//Player rules
	public static int MAX_CHAR = 16;
	public static int MAX_PLAYER = 3;
	//Question and Deck rules
	public static int MIN_QUESTIONS = 10;
	//Game rules
	public static int NUMBER_ROUND = 1;
	public static double ROUND_TIME_SECONDS = 45;
	
	public static void changeRule(String s, double newValue) {
		try {
			Field f = IRulesConst.class.getField(s);
			f.set(f.getClass(), newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
