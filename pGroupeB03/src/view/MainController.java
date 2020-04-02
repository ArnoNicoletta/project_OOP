package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * {@link BorderPane} that manages all the views.
 * @author ArRaLo
 * @see {@link WaitingScreen}
 * @see {@link MainMenu}
 * @see {@link GameController}
 * @see {@link Highscore}
 * @see {@link Rules}
 * @see {@link Credit}
 * @see {@link Settings}
 */
public class MainController extends BorderPane {
	
	// Element of this pane
	private ImageView ivHome;
	private StackPane stack;
	
	// Other Panes included in stack
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
	private GameController gameController;
	private Highscore highscore;
	private Rules rules;
	private Credit credit;
	private Settings settings;
	
	
	public MainController() {
		
		this.setId("maincontroller");
		
		//TOP
		this.setTop(getIvHome());
		getIvHome().setVisible(false);
		
		
		//CENTER
		getStack().getChildren().addAll(getWaitingScreen(), getMainMenu(), getGameController(), getHighscore(),
				getRules(), getCredit(), getSettings());
		hideVisible();
		getWaitingScreen().setVisible(true);
		
		this.setCenter(getStack());
		
	}
	
	private void hideVisible() {
		for(Node n : getStack().getChildren()) {
			if(n.isVisible()) {
				n.setVisible(false);
			}
		}
	}
	
	private void showElement(Node element) {
		hideVisible();
		getIvHome().setVisible(true);
		element.setVisible(true);
	}
	
	public ImageView getIvHome() {
		if(ivHome==null) {
			ivHome = new ImageView("file:./src/resources/images/icon_home.png");
			ivHome.setId("buttonHome");
			ivHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					boolean sure = MsgBox.displayYesNO("Back to menu?", "Are you sure you want to go back to menu?");
					if(sure) {
						ivHome.setVisible(false);
						hideVisible();
						getMainMenu().setVisible(true);
					}
				}
			});
		}
		return ivHome;
	}
	public StackPane getStack() {
		if(stack==null) {
			stack = new StackPane();
		}
		return stack;
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
		gameController.reset();
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
	
	private Label lblTitle;
	private Label lblPressToContinue;
	
	public WaitingScreen() {
		
		HBox hbTop = new HBox();
		hbTop.getChildren().addAll(this.getLblTitle());
		hbTop.setAlignment(Pos.TOP_CENTER);
		this.setTop(hbTop);		
		
		HBox hbBottom = new HBox();
		hbBottom.getChildren().addAll(this.getLblPressToContinue());
		hbBottom.setAlignment(Pos.TOP_CENTER);
		this.setBottom(hbBottom);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				MainController.this.getWaitingScreen().setVisible(false);
				MainController.this.getMainMenu().setVisible(true);
			}
			
		});
	}
	
	public Label getLblTitle() {
		if(lblTitle==null) {
			lblTitle = new Label("FOUR THE WIN");
			lblTitle.setId("lblTitleWaitingScreen");
			lblTitle.setPrefSize(800, 100);
		}
		return lblTitle;
	}
	
	public Label getLblPressToContinue() {
		if(lblPressToContinue==null) {
			lblPressToContinue = new Label("CLICK TO CONTINUE");
			lblPressToContinue.setId("lblClickContinueWaitingScreen");
			lblPressToContinue.setPrefSize(500, 50);
		}
		return lblPressToContinue;
	}
}
class MainMenu extends BorderPane {
	
	private Button btnPlay;
	private Button btnHighscores;
	private Button btnCredits;
	private Button btnRules;
	
	private ImageView ivSettings;
	
	public MainMenu() {
		
		this.setId("mainMenu");
		
		//CENTER
		VBox vbCenter = new VBox();
		vbCenter.getChildren().addAll(getBtnPlay(), getBtnHighScores(), getBtnRules(), getBtnCredits(), getIvSettings());
		vbCenter.setAlignment(Pos.CENTER);
		vbCenter.setSpacing(15);
		this.setCenter(vbCenter);
		vbCenter.setTranslateY(70);
		
	}

	
	public Button getBtnPlay() {
		if(btnPlay==null) {
			btnPlay = new Button("PLAY");
			btnPlay.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnPlay.setOnAction(e -> MainController.this.showElement(MainController.this.getGameController()));
			btnPlay.getStyleClass().add("btnMainMenu");
		}
		return btnPlay;
	}
	
	public Button getBtnHighScores() {
		if(btnHighscores==null) {
			btnHighscores = new Button("HIGHSCORES");
			btnHighscores.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnHighscores.setOnAction(e -> MainController.this.showElement(MainController.this.getHighscore()));
			btnHighscores.getStyleClass().add("btnMainMenu");
		}
		return btnHighscores;
	}
	
	public Button getBtnCredits() {
		if(btnCredits==null) {
			btnCredits = new Button("CREDITS");
			btnCredits.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnCredits.setOnAction(e -> MainController.this.showElement(MainController.this.getCredit()));
			btnCredits.getStyleClass().add("btnMainMenu");
		}
		return btnCredits;
	}
	public Button getBtnRules() {
		if(btnRules==null) {
			btnRules = new Button("RULES");
			btnRules.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnRules.setOnAction(e -> MainController.this.showElement(MainController.this.getRules()));
			btnRules.getStyleClass().add("btnMainMenu");
		}
		return btnRules;
	}
	public ImageView getIvSettings() {
		if(ivSettings==null) {
			ivSettings = new ImageView("file:./src/resources/images/settings_button.png");
			ivSettings.setOnMouseClicked(e -> MainController.this.showElement(MainController.this.getSettings()));
			ivSettings.setId("ivsettings");
		}
		return ivSettings;
	}
}

}