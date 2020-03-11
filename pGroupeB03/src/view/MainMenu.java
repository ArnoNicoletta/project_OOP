package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends BorderPane {
	
	
	
	private Button btnPlay;
	private Button btnHighscores;
	private Button btnCredits;
	private Button btnRules;
	
	private ImageView ivSettings;
	
	public MainMenu() {
		
		
		//CENTER
		VBox vbCenter = new VBox();
		vbCenter.setPadding(new Insets(5));
		vbCenter.setSpacing(5);
		vbCenter.getChildren().addAll(getBtnPlay(), getBtnHighScores(), getBtnRules(), getBtnCredits());
		vbCenter.setAlignment(Pos.CENTER);
		this.setCenter(vbCenter);
		
		//BOTTOM
		HBox hbBottom = new HBox();
		hbBottom.setPadding(new Insets(5));
		hbBottom.setSpacing(5);
		hbBottom.getChildren().addAll(getIvSettings());
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		this.setBottom(hbBottom);
	}

	
	public Button getBtnPlay() {
		if(btnPlay==null) {
			btnPlay = new Button("Play");
			btnPlay.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
		}
		return btnPlay;
	}
	
	public Button getBtnHighScores() {
		if(btnHighscores==null) {
			btnHighscores = new Button("Highscores");
			btnHighscores.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
		}
		return btnHighscores;
	}
	
	public Button getBtnCredits() {
		if(btnCredits==null) {
			btnCredits = new Button("Credits");
			btnCredits.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
		}
		return btnCredits;
	}
	public Button getBtnRules() {
		if(btnRules==null) {
			btnRules = new Button("Rules");
			btnRules.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
		}
		return btnRules;
	}
	public ImageView getIvSettings() {
		if(ivSettings==null) {
			ivSettings = new ImageView("file:./src/resources/images/Icon_25px.png");
		}
		return ivSettings;
	}
	
	
}
