package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class Rules extends BorderPane {
	
	private Button btnMainMenu;
	
	public Rules() {
		
		this.setBackground(new Background(new BackgroundImage(
				new Image("file:./src/resources/images/background_mainmenu.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		
		//TOP
		this.setCenter(getBtnMainMenu());
		
		//CENTER
	}
	
	public Button getBtnMainMenu() {
		if(btnMainMenu==null) {
			btnMainMenu = new Button("Home");
			btnMainMenu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
				}
			});
		}
		return btnMainMenu;
	}
}
