package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class AdminSettings extends StackPane {
	
	
	
	
	
	/*
	 * *****************************
	 * 		  INNER CLASSES
	 * *****************************
	 */
	
	class AdminLogin extends GridPane {
		
		private Label lblLog;
		private TextField txtLog;
		
		private Label lblPass;
		private PasswordField pwfPass;
		
		private Button btnOk;
		
		public AdminLogin() {
			super();
			this.setPadding(new Insets(10));
			this.setHgap(5);
			this.setVgap(5);
			//this.setGridLinesVisible(true);
			
			GridPane.setHalignment(getLblLog(), HPos.LEFT);
			this.add(getLblLog(), 0, 0);
			GridPane.setHalignment(getTxtLog(), HPos.CENTER);
			this.add(getTxtLog(), 1, 0);
			GridPane.setHalignment(getLblPass(), HPos.LEFT);
			this.add(getLblPass(), 0, 1);
			GridPane.setHalignment(getPwfPass(), HPos.CENTER);
			this.add(getPwfPass(), 1, 1);
			GridPane.setHalignment(getBtnOk(), HPos.RIGHT);
			this.add(getBtnOk(), 0, 2, 2, 1);
		}
		
		public Label getLblLog() {
			if(lblLog==null) {
				lblLog = new Label("Username : ");
			}
			return lblLog;
		}
		public TextField getTxtLog() {
			if(txtLog==null) {
				txtLog = new TextField();
			}
			return txtLog;
		}
		public Label getLblPass() {
			if(lblPass==null) {
				lblPass = new Label("Password : ");
			}
			return lblPass;
		}
		public PasswordField getPwfPass() {
			if(pwfPass==null) {
				pwfPass = new PasswordField();
			}
			return pwfPass;
		}
		public Button getBtnOk() {
			if(btnOk==null) {
				btnOk = new Button("OK");
			}
			return btnOk;
		}
	}
}
