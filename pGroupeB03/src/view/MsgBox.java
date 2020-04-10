package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MsgBox{
	
	static boolean answer;
	
	public static boolean displayYesNO(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.TRANSPARENT);
		window.setTitle(title);
		window.setResizable(false);
		window.setOnCloseRequest(e -> answer = false);
		
		Label lbl = new Label(message);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnYes = new Button("Yes");
		btnYes.setOnAction(ev -> {
			answer = true;
			window.close();
		});
		Button btnNo = new Button("No");
		btnNo.setOnAction(ev -> {
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
		window.initStyle(StageStyle.TRANSPARENT);
		window.setTitle(title);
		window.setResizable(false);
		
		Label lbl = new Label(message);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnOk = new Button("Ok");
		btnOk.setOnAction(ev -> window.close());
		
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
	
	public static void dispalyException(Exception e) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.TRANSPARENT);
		window.setTitle(e.getClass().getSimpleName().substring(0, e.getClass().getSimpleName().length()-9));
		window.setResizable(false);
		
		Label lbl = new Label(e.getMessage());
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnOk = new Button("Ok");
		btnOk.setOnAction(ev -> window.close());
		
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
	
	public static boolean displayPause(String player, String theme) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.TRANSPARENT);
		window.setTitle("Waiting");
		window.setResizable(false);
		window.setOnCloseRequest(e -> answer = false);
		
		Label lblPause = new Label("GAME WAITING");
		lblPause.setTextFill(Color.DARKGRAY);
		lblPause.setAlignment(Pos.CENTER);
		lblPause.setPadding(new Insets(5));
		
		Label lblInfos = new Label("Player : " + player 
				+ "\nTheme : " + theme);
		lblInfos.setAlignment(Pos.CENTER);
		lblInfos.setPadding(new Insets(5));
		lblInfos.setWrapText(true);
		
		Button btnExit = new Button("EXIT");
		btnExit.setPrefSize(IGraphicConst.WIDTH_BUTTON/2, IGraphicConst.HEIGHT_BUTTON);
		btnExit.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		Button btnPlay = new Button("PLAY");
		btnPlay.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
		btnPlay.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		BorderPane bp = new BorderPane(lblInfos);
		HBox hbTop = new HBox();
		hbTop.setAlignment(Pos.CENTER);
		hbTop.getChildren().add(lblPause);
		bp.setTop(hbTop);
		HBox hbBottom = new HBox(5);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		hbBottom.setPadding(new Insets(5));
		hbBottom.getChildren().addAll(btnExit, btnPlay);
		bp.setBottom(hbBottom);
		
		Scene scene = new Scene(bp, 512, 288);
		window.setScene(scene);
		
		window.showAndWait();
		
		return answer;
	}
}
