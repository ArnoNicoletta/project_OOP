package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Highscore extends BorderPane{

	private Label lblTopLeft;
	private Label lblCenterLeft;
	private Label lblTopRight;
	private Button btnPlay;
	private Button btnHome;
	public Highscore() {
		
		//LEFT
		VBox vbLeft = new VBox();
		vbLeft.getChildren().addAll(getLabelTopLeft(),getLabelCenterLeft());
		vbLeft.setSpacing(10);
		vbLeft.setPadding(new Insets(20,20,20,20));
		vbLeft.setAlignment(Pos.CENTER);
		this.setLeft(vbLeft);
		
		//RIGHT
		HBox hbRight = new HBox();
		hbRight.getChildren().addAll(getLabelTopRight(),getBtnPlay(),getBtnHome());
		hbRight.setSpacing(10);
		hbRight.setPadding(new Insets(20,20,20,20));
		hbRight.setAlignment(Pos.CENTER);
		this.setRight(hbRight);
	}
	public Label getLabelTopLeft() {
		if(lblTopLeft==null) {
			lblTopLeft = new Label();
		}
		return lblTopLeft;
	}
	public Label getLabelCenterLeft() {
		if(lblCenterLeft==null) {
			lblCenterLeft = new Label();
		}
		return lblTopLeft;
	}
	public Label getLabelTopRight() {
		if(lblTopRight==null) {
			lblTopRight = new Label();
		}
		return lblTopRight;
	}
	public Button getBtnPlay() {
		if(btnPlay==null) {
			btnPlay = new Button("Play");
		}
		return btnPlay;
	}
	public Button getBtnHome() {
		if(btnHome==null) {
			btnHome = new Button("Home");
		}
		return btnHome;
	}
	
	
	
}
