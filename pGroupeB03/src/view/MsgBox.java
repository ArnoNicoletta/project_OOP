package view;

import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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
		IGraphicConst.styleLabel(lbl);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnYes = new Button("Yes");
		IGraphicConst.styleButton(btnYes);
		btnYes.setOnAction(ev -> {
			answer = true;
			window.close();
		});
		Button btnNo = new Button("No");
		IGraphicConst.styleButton(btnNo);
		btnNo.setOnAction(ev -> {
			answer = false;
			window.close();
		});
		
		BorderPane bp = new BorderPane(lbl);
		bp.setBackground(new Background(new BackgroundFill(
				new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, Arrays.asList(
						new Stop(0.1f, Color.web(IGraphicConst.BACKGROUND_TOPCOLOR)), 
						new Stop(1.0f,  Color.web(IGraphicConst.BACKGROUND_BOTCOLOR)))), 
				null, null)));
		
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
		IGraphicConst.styleLabel(lbl);
		lbl.setAlignment(Pos.CENTER);
		lbl.setPadding(new Insets(5));
		lbl.setWrapText(true);
		Button btnOk = new Button("Ok");
		IGraphicConst.styleButton(btnOk);
		btnOk.setOnAction(ev -> window.close());
		
		BorderPane bp = new BorderPane(lbl);
		bp.setBackground(new Background(new BackgroundFill(
				new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, Arrays.asList(
						new Stop(0.1f, Color.web(IGraphicConst.BACKGROUND_TOPCOLOR)), 
						new Stop(1.0f,  Color.web(IGraphicConst.BACKGROUND_BOTCOLOR)))), 
				null, null)));
		
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
		IGraphicConst.styleBiggerLabel(lblPause);
		lblPause.setTextFill(Color.DARKGRAY);
		lblPause.setAlignment(Pos.CENTER);
		lblPause.setPadding(new Insets(5));
		
		Label lblInfos = new Label("Player : " + player 
				+ "\nTheme : " + theme);
		IGraphicConst.styleLabel(lblInfos);
		lblInfos.setAlignment(Pos.CENTER);
		lblInfos.setPadding(new Insets(5));
		lblInfos.setWrapText(true);
		
		Button btnPlay = new Button("PLAY");
		IGraphicConst.styleButton(btnPlay);
		btnPlay.setPrefSize(Integer.MAX_VALUE, IGraphicConst.HEIGHT_BUTTON);
		btnPlay.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		BorderPane bp = new BorderPane(lblInfos);
		bp.setBackground(new Background(new BackgroundFill(
				new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, Arrays.asList(
						new Stop(0.1f, Color.web(IGraphicConst.BACKGROUND_TOPCOLOR)), 
						new Stop(1.0f,  Color.web(IGraphicConst.BACKGROUND_BOTCOLOR)))), 
				null, null)));
		HBox hbTop = new HBox();
		hbTop.setAlignment(Pos.CENTER);
		hbTop.getChildren().add(lblPause);
		bp.setTop(hbTop);
		HBox hbBottom = new HBox(5);
		hbBottom.setAlignment(Pos.CENTER_RIGHT);
		hbBottom.setPadding(new Insets(5));
		hbBottom.getChildren().addAll(btnPlay);
		hbBottom.setPrefWidth(Integer.MAX_VALUE);
		bp.setBottom(hbBottom);
		
		Scene scene = new Scene(bp, 512, 288);
		window.setScene(scene);
		
		window.showAndWait();
		
		return answer;
	}
}
