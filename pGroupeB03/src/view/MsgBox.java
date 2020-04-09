package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MsgBox{
	
	static boolean answer;
	
	public static boolean displayYesNO(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setResizable(false);
		window.setOnCloseRequest(e -> answer = false);
		
		Label lbl = new Label(message);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnYes = new Button("Yes");
		btnYes.setOnAction(e -> {
			answer = true;
			window.close();
		});
		Button btnNo = new Button("No");
		btnNo.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		BorderPane bp = new BorderPane(lbl);
		HBox hbBottom = new HBox(10);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		hbBottom.setPadding(new Insets(5));
		hbBottom.getChildren().addAll(btnYes, btnNo);
		bp.setBottom(hbBottom);
		
		Scene scene = new Scene(bp);
		window.setScene(scene);
		
		window.showAndWait();
		
		return answer;
	}
	
	public static void dispalyOk(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setResizable(false);
		window.setOnCloseRequest(e -> answer = true);
		
		Label lbl = new Label(message);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnOk = new Button("Ok");
		btnOk.setOnAction(e -> window.close());
		
		BorderPane bp = new BorderPane(lbl);
		HBox hbBottom = new HBox(10);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		hbBottom.setPadding(new Insets(5));
		hbBottom.getChildren().addAll(btnOk);
		bp.setBottom(hbBottom);
		
		Scene scene = new Scene(bp);
		window.setScene(scene);
		
		window.showAndWait();
	}
	
}
