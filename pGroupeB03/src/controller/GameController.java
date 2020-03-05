package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import view.MainMenu;
import view.WaitingScreen;

public class GameController extends StackPane {
	
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
	
	public GameController() {
		
		this.getChildren().addAll(getWaitingScreen(), getMainMenu());
		this.getChildren().forEach((n) -> n.setVisible(false));
		this.getWaitingScreen().setVisible(true);
	}
	
	public WaitingScreen getWaitingScreen() {
		if(waitingScreen==null) {
			waitingScreen = new WaitingScreen();
			waitingScreen.setVisible(false);
			waitingScreen.setOnKeyTyped(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					System.out.println("key pressed with ON : " + event.getCharacter());
					waitingScreen.setVisible(false);
					getMainMenu().setVisible(true);
				}
			});
			waitingScreen.addEventHandler(KeyEvent.ANY, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					System.out.println("key pressed with EventHandler + " + event.getCharacter());
					waitingScreen.setVisible(false);
					getMainMenu().setVisible(true);
				}
			});
			waitingScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					System.out.println("CLICK");
					waitingScreen.setVisible(false);
					getMainMenu().setVisible(true);
				}
			});
		}
		return waitingScreen;
	}
	public MainMenu getMainMenu() {
		if(mainMenu==null) {
			mainMenu = new MainMenu();
			mainMenu.setVisible(false);
		}
		return mainMenu;
	}
	
}
