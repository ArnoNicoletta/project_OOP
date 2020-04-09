package model;

import java.lang.reflect.Field;

public class RulesConst {
	
	//Player rules
	public static int MAX_CHAR = 16;
	public static int MAX_PLAYER = 3;
	//Question and Deck rules
	public static int MIN_QUESTIONS = 10;
	//Game rules
	public static int NUMBER_ROUND = 1;
	public static double ROUND_TIME_SECONDS = 45;
	
	public static void setMAX_CHAR(int mAX_CHAR) {
		MAX_CHAR = mAX_CHAR;
	}
}
