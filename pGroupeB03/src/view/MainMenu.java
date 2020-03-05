package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends BorderPane {
	
	private static final double WIDTH_BUTTON = 350, HEIGHT_BUTTON = 50;
	
	
	
	private Button btnPlay;
	private Button btnHighScores;
	private Button btnCredits;
	private Button btnAdmin;
	
	public MainMenu() {
		
		
		//CENTER
		VBox vbCenter = new VBox();
		vbCenter.setPadding(new Insets(5));
		vbCenter.setSpacing(5);
		vbCenter.getChildren().addAll(getBtnPlay(),getBtnHighScores(),getBtnCredits());
		vbCenter.setAlignment(Pos.CENTER);
		this.setCenter(vbCenter);
		
		//BOTTOM
		HBox hbBottom = new HBox();
		hbBottom.setPadding(new Insets(5));
		hbBottom.setSpacing(5);
		hbBottom.getChildren().addAll(getBtnAdmin());
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		this.setBottom(hbBottom);
	}

	
	public Button getBtnPlay() {
		if(btnPlay==null) {
			btnPlay = new Button("Play");
			btnPlay.setPrefSize(WIDTH_BUTTON, HEIGHT_BUTTON);
		}
		return btnPlay;
	}
	
	public Button getBtnHighScores() {
		if(btnHighScores==null) {
			btnHighScores = new Button("Highscores");
			btnHighScores.setPrefSize(WIDTH_BUTTON, HEIGHT_BUTTON);
		}
		return btnHighScores;
	}
	
	public Button getBtnCredits() {
		if(btnCredits==null) {
			btnCredits = new Button("Credits");
			btnCredits.setPrefSize(WIDTH_BUTTON, HEIGHT_BUTTON);
		}
		return btnCredits;
	}
	
	public Button getBtnAdmin() {
		if(btnAdmin==null) {
			btnAdmin = new Button("Admin");
			btnAdmin.setPrefSize(135, 30);
			btnAdmin.setId("btnAdmin");
		}
		return btnAdmin;
	}
	
	
}
