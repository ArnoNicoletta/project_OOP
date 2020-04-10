package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Deck;
import model.Game;

public class MsgBox{
	
	static boolean answer;
	
	/**
	 * Displays a new window when the user is asked a question anwered by <code>YES</code> or <code>NO</code>.
	 * @param title : {@link String}. The title of the window.
	 * @param message : {@link String}. The question to display.
	 * @return {@link Boolean}. <code>true</code> if the answer is <code>YES</code>, <code>false</code> if it is <code>NO</code>.
	 */
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
	
	/**
	 * Displays a new window when an information has to be shown to the user.
	 * @param title : {@link String}. The title of the window.
	 * @param message : {@link String}. The message to display.
	 */
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
	
	/**
	 * Displays a new window when an exception is thrown with informations about this exception.
	 * @param e : {@link Exception}. The exception thrown.
	 */
	public static void dispalyException(Exception e) {
		MsgBox.dispalyOk(e.getClass().getSimpleName().substring(0, e.getClass().getSimpleName().length()-9), e.getMessage());
	}
	
	
	/**
	 * Displays a new window ( {@link Stage} ) called when the {@link Game} is paused.
	 * @param player : {@link String}. The pseudo of the player currently playing.
	 * @param theme : {@link String}. The theme of the {@link Deck} currently played.
	 * @return {@link Boolean} : <code>true</code> if the player want to continue, <code>false</code> otherwise.
	 */
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
