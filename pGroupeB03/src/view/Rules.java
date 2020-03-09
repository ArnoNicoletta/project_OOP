package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class Rules extends BorderPane {
	
	private Button btnMainMenu;
	
	public Rules() {
		
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
