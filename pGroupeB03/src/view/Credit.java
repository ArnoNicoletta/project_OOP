package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Credit extends BorderPane {

	private Label labelCredit;
	private Button btnOK;
	
	public Credit() {
		
	}
	
	public Label getLabelCredit() {
		if(labelCredit==null) {
			labelCredit = new Label();
		}
		return labelCredit;
	}
	public Button getBtnOK() {
		if(btnOK==null) {
			btnOK = new Button("OK");
		}
		return btnOK;
	}
	
	
}
