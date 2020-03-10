package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Highscore extends BorderPane{

	private Label labelTopLeft;
	private Label labelCenterLeft;
	private Label labelTopRight;
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
		if(labelTopLeft==null) {
			labelTopLeft = new Label();
		}
		return labelTopLeft;
	}
	public Label getLabelCenterLeft() {
		if(labelCenterLeft==null) {
			labelCenterLeft = new Label();
		}
		return labelTopLeft;
	}
	public Label getLabelTopRight() {
		if(labelTopRight==null) {
			labelTopRight = new Label();
		}
		return labelTopRight;
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
