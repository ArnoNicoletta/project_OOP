package view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameController extends StackPane {
	
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
	private AdminLogin adminLogin;
	
	public GameController() {
		
		this.getChildren().addAll(getWaitingScreen(), getMainMenu());
		this.getChildren().forEach((n) -> n.setVisible(false));
		this.getWaitingScreen().setVisible(true);
		
		
	}
	
	public WaitingScreen getWaitingScreen() {
		if(waitingScreen==null) {
			waitingScreen = new WaitingScreen();
			waitingScreen.setVisible(false);
			
			waitingScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
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
