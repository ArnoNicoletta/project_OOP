package view;

import javafx.scene.layout.StackPane;

public class GameController extends StackPane {
	
	private PlayerSelection playerSelection;
	private GamePane gamePane;
	
	public GameController() {
		this.getChildren().addAll(getPlayerSelection());
	}
	public PlayerSelection getPlayerSelection() {
		if(playerSelection==null) {
			playerSelection = new PlayerSelection();
		}
		return playerSelection;
	}
	
	
}