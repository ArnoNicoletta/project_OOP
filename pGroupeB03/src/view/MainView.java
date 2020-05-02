package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.GameDecksAndPlayers;

/**
 * {@link BorderPane} that manages all the views.
 * @author ArRaLo
 * @see {@link WaitingScreen}
 * @see {@link MainMenu}
 * @see {@link GameView}
 * @see {@link HighscoreView}
 * @see {@link RulesView}
 * @see {@link CreditView}
 * @see {@link SettingsView}
 */
public class MainView extends BorderPane {
	
	// Element of this pane
	private ImageView ivHome;
	private StackPane stack;
	
	// Other Panes included in stack
	private WaitingScreen waitingScreen;
	private MainMenu mainMenu;
	
	
	public MainView() {
		
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background.png", 1035, 587, false, true), 
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
		getStack().getChildren().clear();
		getIvHome().setVisible(true);
		getIvHome().toFront();
		getStack().getChildren().add(element);
		element.setVisible(true);
	}
	
	public ImageView getIvHome() {
		if(ivHome==null) {
			ivHome = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_home.png");
			Tooltip.install(ivHome, new Tooltip("Back to the menu"));
			ivHome.setTranslateX(20);
			ivHome.setTranslateY(20);
			ivHome.setCursor(Cursor.HAND);
			ivHome.toFront();
			ivHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					boolean sure = MsgBox.displayYesNO("Back to menu?", "Are you sure you want to go back to menu?");
					if(sure) {
						if(getStack().getChildren().get(0) instanceof GameView || 
								getStack().getChildren().get(0) instanceof SettingsView) {
							GameDecksAndPlayers.reset();
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
			lblTitle.setCache(true);
			lblTitle.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 7, 1, 0, 0));
			lblTitle.setAlignment(Pos.BASELINE_CENTER);
		}
		return lblTitle;
	}
	
	public Label getLblPressToContinue() {
		if(lblPressToContinue==null) {
			lblPressToContinue = new Label("CLICK TO CONTINUE");
			lblPressToContinue.setId("lblClickContinueWaitingScreen");
			lblPressToContinue.setPrefSize(500, 50);
			lblPressToContinue.setTranslateY(-40);
			lblPressToContinue.setStyle("-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
										"-fx-font-size: 30px;\r\n" + 
										"-fx-font-weight: bold;");
			lblPressToContinue.setTextFill(Color.WHITE);
			lblPressToContinue.setPadding(new Insets(15));
			lblPressToContinue.setBackground(new Background(new BackgroundFill(Color.web("#793F54"), new CornerRadii(45, false) , null)));
			lblPressToContinue.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(40,  false), new BorderWidths(5))));
			lblPressToContinue.setAlignment(Pos.CENTER);
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
		
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background_mainmenu.png", false), 
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
			btnHighscores.setOnAction(e -> MainView.this.showElement(new HighscoreView()));
			IGraphicConst.styleButton(btnHighscores);
			btnHighscores.getStyleClass().add("btnMainMenu");
		}
		return btnHighscores;
	}
	
	public Button getBtnCredits() {
		if(btnCredits==null) {
			btnCredits = new Button("CREDITS");
			btnCredits.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnCredits.setOnAction(e -> MainView.this.showElement(new CreditView()));
			IGraphicConst.styleButton(btnCredits);
			btnCredits.getStyleClass().add("btnMainMenu");
		}
		return btnCredits;
	}
	public Button getBtnRules() {
		if(btnRules==null) {
			btnRules = new Button("RULES");
			btnRules.setPrefSize(IGraphicConst.WIDTH_BUTTON, IGraphicConst.HEIGHT_BUTTON);
			btnRules.setOnAction(e -> MainView.this.showElement(new RulesView()));
			IGraphicConst.styleButton(btnRules);
			btnRules.getStyleClass().add("btnMainMenu");
		}
		return btnRules;
	}
	public ImageView getIvSettings() {
		if(ivSettings==null) {
			ivSettings = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_settings.png");
			ivSettings.setOnMouseClicked(e -> MainView.this.showElement(new SettingsView()));
			ivSettings.setTranslateX(IGraphicConst.WIDTH_BUTTON*0.8);
			IGraphicConst.styleImageView(ivSettings);
		}
		return ivSettings;
	}
}

}