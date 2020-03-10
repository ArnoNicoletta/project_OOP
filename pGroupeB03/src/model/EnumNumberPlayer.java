package model;

public enum EnumNumberPlayer {

	ONE("ONE PLAYER",1),
	TWO("TWO PLAYER",2),
	THREE("THREE PLAYER",3);
	
	private String string;
	private int i;
	
	EnumNumberPlayer(String string, int i) {
		this.string = string;
		this.i = i;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.string;
	}
	
	public int getNumber() {
		return this.i;
	}
}
