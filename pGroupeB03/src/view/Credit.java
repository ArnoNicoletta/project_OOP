package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class Credit extends BorderPane {

	private Label lblCredit;
	private Button btnOK;
	
	public Credit() {
		this.setBackground(new Background(new BackgroundImage(
				new Image("file:./src/resources/images/background_mainmenu.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
	}
	
	public Label getLabelCredit() {
		if(lblCredit==null) {
			lblCredit = new Label();
		}
		return lblCredit;
	}
	public Button getBtnOK() {
		if(btnOK==null) {
			btnOK = new Button("OK");
		}
		return btnOK;
	}
	
	
}
