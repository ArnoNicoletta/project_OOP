package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WaitingScreen extends BorderPane {
	
	private Label lblWelcome;
	private Label lblPressToContinue;
	
	public WaitingScreen() {
		
		VBox vbCenter = new VBox();
		vbCenter.setPadding(new Insets(5));
		vbCenter.setSpacing(5);
		vbCenter.getChildren().addAll(this.getLblWelcome());
		vbCenter.setAlignment(Pos.CENTER);
		this.setCenter(vbCenter);
		
		HBox hbBottom = new HBox();
		hbBottom.setPadding(new Insets(5));
		hbBottom.setSpacing(5);
		hbBottom.getChildren().addAll(this.getLblPressToContinue());
		hbBottom.setAlignment(Pos.TOP_CENTER);
		this.setBottom(hbBottom);
		
		this.setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
			}
		});
	}
	
	public Label getLblWelcome() {
		if(lblWelcome==null) {
			lblWelcome = new Label("WELCOME IN THE GAME");
			lblWelcome.setId("lblWelcome");
		}
		return lblWelcome;
	}
	public Label getLblPressToContinue() {
		if(lblPressToContinue==null) {
			lblPressToContinue = new Label("Click to continue");
		}
		return lblPressToContinue;
	}
	
}
