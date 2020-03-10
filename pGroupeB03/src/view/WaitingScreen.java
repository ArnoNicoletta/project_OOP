package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class WaitingScreen extends BorderPane {
	
	//private Label lblPressToContinue;
	
	public WaitingScreen() {
		
		
		HBox hbBottom = new HBox();
		hbBottom.setPadding(new Insets(5));
		hbBottom.setSpacing(5);
		//hbBottom.getChildren().addAll(this.getLblPressToContinue());
		hbBottom.setAlignment(Pos.TOP_CENTER);
		this.setBottom(hbBottom);
		
		this.setOnKeyTyped(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
			}
		});
	}
	
//	public Label getLblPressToContinue() {
//		if(lblPressToContinue==null) {
//			lblPressToContinue = new Label("Click to continue");
//		}
//		return lblPressToContinue;
//	}
	
}
