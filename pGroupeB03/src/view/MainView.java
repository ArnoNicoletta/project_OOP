package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Game;

/**
 * {@link BorderPane} that manages all the views.
 * @author ArRaLo
 * @see {@link WaitingScreen}
 * @see {@link MainMenu}
 * @see {@link GameView}
 * @see {@link Highscore}
 * @see {@link Rules}
 * @see {@link Credit}
 * @see {@link Settings}
 */
public class MainView extends BorderPane {
	
	// Element of this pane
	private ImageView ivHome;
	private StackPane stack;
	
	// Other Panes included in stack
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
//	private GameView gameView;
//	private Highscore highscore;
//	private Rules rules;
//	private Credit credit;
//	private Settings settings;
	
	
	public MainView() {
		
		this.setId("maincontroller");
		this.setBackground(new Background(new BackgroundImage(
				new Image("file:./src/resources/images/background.png", 1035, 587, false, true), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		
		//TOP
		this.setTop(getIvHome());
		
		//CENTER
		showElement(getWaitingScreen());
		this.setCenter(getStack());
		
		getIvHome().setVisible(false);
		
	}
	
	private void showElement(Node element) {
		getStack().getChildren().removeAll(getStack().getChildren());
		getIvHome().setVisible(true);
		getIvHome().toFront();
		getStack().getChildren().add(element);
		element.setVisible(true);
	}
	
	public ImageView getIvHome() {
		if(ivHome==null) {
			ivHome = new ImageView("file:./src/resources/images/icon_home.png");
			Tooltip.install(ivHome, new Tooltip("Back to the menu"));
			ivHome.setTranslateX(20);
			ivHome.setTranslateY(20);
			ivHome.toFront();
			ivHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					boolean sure = MsgBox.displayYesNO("Back to menu?", "Are you sure you want to go back to menu?");
					if(sure) {
						if(getStack().getChildren().get(0) instanceof GameView) {
							Game.reset();
						}
						showElement(getMainMenu());
						ivHome.setVisible(false);
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
//	public GameView getGameView() {
//		if(gameView==null) {
//			gameView = new GameView();
//		}
//		gameView.reset();
//		return gameView;
//	}
//	public Highscore getHighscore() {
//		if(highscore==null) {
//			highscore = new Highscore();
//		}
//		return highscore;
//	}
//	public Rules getRules() {
//		if(rules==null) {
//			rules = new Rules();
//		}
//		return rules;
//	}
//	public Credit getCredit() {
//		if(credit==null) {
//			credit = new Credit();
//		}
//		return credit;
//	}
//	public Settings getSettings() {
//		if(settings==null) {
//			settings = new Settings();
//		}
//		return settings;
//	}
//	
	
	
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
				showElement(getMainMenu());
				getIvHome().setVisible(false);
			}
		});
	}
	
	public Label getLblTitle() {
		if(lblTitle==null) {
			lblTitle = new Label("FOUR THE WIN");
			lblTitle.setId("lblTitleWaitingScreen");
			lblTitle.setPrefSize(800, 100);
			lblTitle.setTextFill(Color.web("#FBC680"));
			lblTitle.setStyle("-fx-font-family: \"K2D Medium\", sans-serif;\r\n" + 
						"-fx-font-weight: bold;\r\n" + 
						"-fx-font-size: 100px;");
			lblTitle.setAlignment(Pos.BASELINE_CENTER);
		}
		return lblTitle;
	}
	
	public Label getLblPressToContinue() {
		if(lblPressToContinue==null) {
			lblPressToContinue = new Label("CLICK TO CONTINUE");
			lblPressToContinue.setId("lblClickContinueWaitingScreen");
			lblPressToContinue.setPrefSize(500, 50);
			lblPressToContinue.setAlignment(Pos.BASELINE_CENTER);
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
		
		this.setBackground(new Background(new BackgroundImage(
				new Image("file:./src/resources/images/background_mainmenu.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
		
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
			btnPlay.setOnAction(e -> MainView.this.showElement(new GameView()));
			IGraphicConst.styleButton(btnPlay);
			btnPlay.getStyleClass().add("btnMainMenu");
		}
		return btnPlay;
	}
	
	public Button getBtnHighScores() {
		if(btnHighscores==null) {
			btnHighscores = new Button("HIGHSCORES");
			btnHighscores.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnHighscores.setOnAction(e -> MainView.this.showElement(new Highscore()));
			IGraphicConst.styleButton(btnHighscores);
			btnHighscores.getStyleClass().add("btnMainMenu");
		}
		return btnHighscores;
	}
	
	public Button getBtnCredits() {
		if(btnCredits==null) {
			btnCredits = new Button("CREDITS");
			btnCredits.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnCredits.setOnAction(e -> MainView.this.showElement(new Credit()));
			IGraphicConst.styleButton(btnCredits);
			btnCredits.getStyleClass().add("btnMainMenu");
		}
		return btnCredits;
	}
	public Button getBtnRules() {
		if(btnRules==null) {
			btnRules = new Button("RULES");
			btnRules.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnRules.setOnAction(e -> MainView.this.showElement(new Rules()));
			IGraphicConst.styleButton(btnRules);
			btnRules.getStyleClass().add("btnMainMenu");
		}
		return btnRules;
	}
	public ImageView getIvSettings() {
		if(ivSettings==null) {
			ivSettings = new ImageView("file:./src/resources/images/settings_button.png");
			ivSettings.setOnMouseClicked(e -> MainView.this.showElement(new Settings()));
			ivSettings.setId("ivsettings");
			ivSettings.setTranslateX(IGraphicConst.WIDTH_BUTTON*0.8);
		}
		return ivSettings;
	}
}

}