package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Credit extends BorderPane {

	private Label lblCredit;
	private Button btnOK;
	
	public Credit() {
		
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
