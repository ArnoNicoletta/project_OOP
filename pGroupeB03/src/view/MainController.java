package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainController extends StackPane {
	
	private ImageView ivHome;
	
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
	private GameController gameController;
	private Highscore highscore;
	private Rules rules;
	private Credit credit;
	private Settings settings;
	
	
	public MainController() {
		
		BorderPane bp = new BorderPane();
		bp.setTop(getIvHome());
		
		this.getChildren().addAll(getWaitingScreen(), getMainMenu(), getGameController(), getHighscore(),
				getRules(), getCredit(), getSettings());
		hideVisible();
		getWaitingScreen().setVisible(true);
	}
	
	private void hideVisible() {
		for(Node n : this.getChildren()) {
			if(n.isVisible()) {
				n.setVisible(false);
			}
		}
	}
	
	public ImageView getIvHome() {
		if(ivHome==null) {
			ivHome = new ImageView("file:./src/resources/images/logo.png");
			ivHome.setVisible(false);
			ivHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					hideVisible();
					getMainMenu().setVisible(true);
				}
			});
		}
		return ivHome;
	}
	public WaitingScreen getWaitingScreen() {
		if(waitingScreen==null) {
			waitingScreen = new WaitingScreen();
		}
		return waitingScreen;
	}
	public MainMenu getMainMenu() {
		if(mainMenu==null) {
			mainMenu = new MainMenu();
		}
		return mainMenu;
	}
	public GameController getGameController() {
		if(gameController==null) {
			gameController = new GameController();
		}
		return gameController;
	}
	public Highscore getHighscore() {
		if(highscore==null) {
			highscore = new Highscore();
		}
		return highscore;
	}
	public Rules getRules() {
		if(rules==null) {
			rules = new Rules();
		}
		return rules;
	}
	public Credit getCredit() {
		if(credit==null) {
			credit = new Credit();
		}
		return credit;
	}
	public Settings getSettings() {
		if(settings==null) {
			settings = new Settings();
		}
		return settings;
	}
	
	
	
class WaitingScreen extends BorderPane {
	
	private Label lblPressToContinue;
	
	public WaitingScreen() {
		
		
		HBox hbBottom = new HBox();
		hbBottom.setPadding(new Insets(5));
		hbBottom.setSpacing(5);
		hbBottom.getChildren().addAll(this.getLblPressToContinue());
		hbBottom.setAlignment(Pos.TOP_CENTER);
		this.setBottom(hbBottom);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainController.this.hideVisible();
				MainController.this.getMainMenu().setVisible(true);
			}
		});
	}
	
	public Label getLblPressToContinue() {
		if(lblPressToContinue==null) {
			lblPressToContinue = new Label("Click to continue");
		}
		return lblPressToContinue;
	}
}
}