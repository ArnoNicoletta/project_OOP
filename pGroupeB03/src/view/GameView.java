package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import exception.NotEnoughDeckException;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Deck;
import model.GameDecksAndPlayers;
import model.Player;
import model.RulesSettings;


/**
 * {@link StackPane} of the game which includes all views needed to play the game
 * @author ArRaLo
 * @see {@link PlayerSelection}
 * @see {@link ThemeSelection}
 * @see {@link GamePane}
 * @see {@link Ranking}
 */
public class GameView extends StackPane {
	
	//Game vars
	private GameDecksAndPlayers g;
	
	public GameView() {
		
		GameDecksAndPlayers.reset();
		this.g = GameDecksAndPlayers.getInstance();
		this.showElement(new PlayerSelection());
	}
	
	private void showElement(Node element) {
		this.getChildren().clear();
		this.getChildren().add(element);
		element.setVisible(true);
	}
	// Game elements
	public GameDecksAndPlayers getGame() {
		return g;
	}
	
	
	/*
	 * *****************************
	 * 		  INNER CLASSES
	 * *****************************
	 */
	
	
	
	
	/**
	 * This {@link BorderPane} displays selection of players.
	 * @author ArRaLo
	 * 
	 */
	class PlayerSelection extends BorderPane{
		
		private int count = 0;
		
		private GridPane gpCenter;
		
		
		private List<Label> lLblPlayer;
		private List<TextField> lTxtPlayer;
		private List<ImageView> lIvPlayer;
		
		private Button btnPlay;
		
		public PlayerSelection() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_selectplayers.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			addPlayer();
			//CENTER
			getGpCenter().setTranslateY(50);
			this.setCenter(getGpCenter());
			
			//BOTTOM
			HBox hbBottom = new HBox(getBtnPlay());
			hbBottom.setAlignment(Pos.BASELINE_RIGHT);
			hbBottom.setTranslateX(-220);
			hbBottom.setTranslateY(-55);
			this.setBottom(hbBottom);
		}
		
		/**
		 * Adds a player.
		 * Called by an {@link ImageView} from getIvAdd()
		 * @see getIvAdd()
		 */
		private void addPlayer() {
			if(this.count>=RulesSettings.getMax_player()) {
				return;
			}
			getlLblPlayer().add(IGraphicConst.styleLabel(new Label("PLAYER " + (this.count+1))));
			TextField txtP = new TextField();
			txtP.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if(event.getCode() == KeyCode.ENTER) {
						getBtnPlay().fire();
						event.consume();
					}
				}
			});
			getlTxtPlayer().add(IGraphicConst.styleTextField(txtP));
			getlIvPlayer().add(getIvAdd());
			
			getGpCenter().add(getlLblPlayer().get(count), 0, count);
			getGpCenter().add(getlTxtPlayer().get(count), 1, count);
			getGpCenter().add(getlIvPlayer().get(count), 2, count);
			
			if(count!=0) {
				getGpCenter().getChildren().remove(getlIvPlayer().get(count-1));
				getlIvPlayer().set(count-1, getIvDel());
				getGpCenter().add(getlIvPlayer().get(count-1), 2, count-1);
			}
			
			count++;
		}
		
		/**
		 * Removes a player.
		 * Called by an {@link ImageView} from getIvDel()
		 * @see getIvDel()
		 */
		private void removePlayer() {
			if(count<=1) {
				return;
			}
			count--;
			
			getGpCenter().getChildren().remove(getlLblPlayer().get(count));
			getGpCenter().getChildren().remove(getlTxtPlayer().get(count));
			getGpCenter().getChildren().remove(getlIvPlayer().get(count));
			
			getlLblPlayer().remove(count);
			getlTxtPlayer().remove(count);
			getlIvPlayer().remove(count);
			
			getGpCenter().getChildren().remove(getlIvPlayer().get(count-1));
			getlIvPlayer().set(count-1, getIvAdd());
			getGpCenter().add(getlIvPlayer().get(count-1), 2, count-1);
		}
		
		/**
		 * Gives an ImageView with an {@link EventHandler} that calls add a player.
		 * @see addPlayer
		 * @return {@link ImageView}.
		 */
		private ImageView getIvAdd() {
			ImageView iv = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_add.png");
			iv.setCursor(Cursor.HAND);
			iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					addPlayer();
					setCenter(getGpCenter());
				}
			});
			return iv;
		}
		
		/**
		 * Gives an ImageView with an {@link EventHandler} that calls removePlayer.
		 * @see removePlayer
		 * @return {@link ImageView}.
		 */
		private ImageView getIvDel() {
			ImageView ivDel = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_remove.png");
			ivDel.setCursor(Cursor.HAND);
			ivDel.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					removePlayer();
					setCenter(getGpCenter());
				}
			});
			return ivDel;
		}
		
		public GridPane getGpCenter() {
			if(gpCenter==null) {
				gpCenter = new GridPane();
				gpCenter.setAlignment(Pos.CENTER);
				gpCenter.setVgap(10);
				gpCenter.setHgap(20);
			}
			return gpCenter;
		}
		public List<Label> getlLblPlayer() {
			if(lLblPlayer==null) {
				lLblPlayer = new ArrayList<>();
			}
			return lLblPlayer;
		}
		public List<TextField> getlTxtPlayer() {
			if(lTxtPlayer==null) {
				lTxtPlayer = new ArrayList<>();
			}
			return lTxtPlayer;
		}
		public List<ImageView> getlIvPlayer() {
			if(lIvPlayer==null) {
				lIvPlayer = new ArrayList<>();
			}
			return lIvPlayer;
		}
		public Button getBtnPlay() {
			if(btnPlay==null) {
				btnPlay = new Button("PLAY");
				btnPlay.setPrefSize(200, 40);
				IGraphicConst.styleButton(btnPlay);
				btnPlay.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						for(TextField txtP : getlTxtPlayer()) {
							try {
								GameView.this.getGame().addPlayer(txtP.getText());
							} catch (Exception e) {
								MsgBox.dispalyException(e);
								getGame().removeAllPlayers();
								getlTxtPlayer().get(0).requestFocus();
								return ;
							}
						}
						
						try {
							getGame().randomChoice();
						} catch (NotEnoughDeckException e) {
							MsgBox.dispalyException(e);
							return;
						}
						getGame().setPosPlayer(0);
						showElement(new ThemeSelection());
					}
				});
			}
			return btnPlay;
		}
	}
	
	
	
	/**
	 * This {@link BorderPane} displays the selection of available themes.
	 * @author ArRaLo
	 *
	 */
	class ThemeSelection extends BorderPane{
		
		private final String txtMysteryTheme = "? Mystery theme ?";
		
		private Label lblPlayer;
		private List<Button> lBtnTheme;
		
		public ThemeSelection() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_theme.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			//Center
			VBox vbCenter = new VBox(10);
			vbCenter.setTranslateY(50);
			vbCenter.getChildren().add(getLblPlayer());
			vbCenter.getChildren().addAll(getlBtnTheme());
			vbCenter.setAlignment(Pos.CENTER);
			this.setCenter(vbCenter);
		}
		
		public Label getLblPlayer() {
			if(lblPlayer==null) {
				lblPlayer = new Label(getGame().getPlayer(getGame().getPosPlayer()) + 
						" , SELECT A THEME ");
				IGraphicConst.styleLabel(lblPlayer);
			}
			return lblPlayer;
		}
		public List<Button> getlBtnTheme() {
			if(lBtnTheme==null) {
				lBtnTheme = new ArrayList<>();
				
				for(int i=0;i<=GameView.this.getGame().getNumberOfPlayers();i++) {
					Deck d = GameView.this.getGame().getDeck(i);
					Button b = new Button(d.getTheme());
					if(i==GameView.this.getGame().getNumberOfPlayers())
						b.setText(txtMysteryTheme);
					if(GameView.this.getGame().hasBeenUsed(d))
						b.setDisable(true);
					b.setMinWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
					b.setPrefWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
					b.setPrefWidth(IGraphicConst.HEIGHT_BUTTON);
					IGraphicConst.styleButton(b);
					b.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(b.getText().equals(txtMysteryTheme)) {
								getGame().addUsedDeck(getGame().getDeck(getGame().getNumberOfPlayers()));
							}
							else {
								getGame().addUsedDeck(b.getText());
							}
							showElement(new GamePane());
						}
					});
					lBtnTheme.add(b);
				}
			}
			return lBtnTheme;
		}
		
	}
	
	
	/**
	 * {@link BorderPane} that displays the view of the game.
	 * @author ArRaLo
	 *
	 */
	class GamePane extends BorderPane {
		
		//Game vars
		private SimpleDoubleProperty timer = new SimpleDoubleProperty(RulesSettings.getRound_time_seconds());
		private String fullClues;
		private int scorePos = 0;
		private SimpleStringProperty clues = new SimpleStringProperty();
		private int cluesPos = 0;
		List<String> gradient;
		int index = 0;
		//GUI vars
		private Label lblPlayer;
		private ImageView ivScore;
		private Timeline timelineTimer;
		private Label lblTimer;
		private Timeline transition = new Timeline();
		private ImageView ivJokerLetters;
		private ImageView ivJokerExtraPass;
		private ImageView ivJokerBonusTime;
		
		private Label lblClues;
		private TextField txtAnswer;
		private Button btnPass;
		private Button btnPause;
		private Button btnValidate;
		
		private Label lblAnswer;
		
		public GamePane() {
			
			scorePos = 0;
			cluesPos = 0;
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_blank.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			//TOP
			HBox hbTop = new HBox(30);
			hbTop.setAlignment(Pos.BASELINE_CENTER);
			hbTop.getChildren().addAll(getLblPlayer(), getIvScore(), getLblTimer());
			this.setTop(hbTop);
			
			//CENTER LEFT
			VBox vbLeft = new VBox(15);
			vbLeft.setPadding(new Insets(0, 5, 10, 25));
			vbLeft.setAlignment(Pos.CENTER_RIGHT);
			vbLeft.getChildren().addAll(getIvJokerLetters(), getIvJokerExtraPass(), getIvJokerBonusTime());
			vbLeft.toFront();
			
			//CENTER RIGHT
			VBox vbCenter = new VBox(10);
			vbCenter.setAlignment(Pos.CENTER);
			vbCenter.setPadding(new Insets(5, 50, 15, 10));
			vbCenter.setTranslateX(35);
			HBox hbCenterBottom = new HBox(10);
			hbCenterBottom.setAlignment(Pos.CENTER);
			hbCenterBottom.getChildren().addAll(getBtnPass(), getBtnPause(), getBtnValidate());
			
			vbCenter.getChildren().addAll(getLblClues(), getTxtAnswer(), hbCenterBottom);
			
			
			//CENTER
			HBox hbCenter = new HBox();
			hbCenter.setAlignment(Pos.CENTER);
			hbCenter.getChildren().addAll(vbLeft, vbCenter);
			this.setCenter(hbCenter);
			
			
			//Init game stuffs
			fullClues = g.getClue(0) + " " + g.getClue(1) + " " + g.getClue(2);
			clues.setValue("" + fullClues.charAt(cluesPos++));
			getTimelineTimer().playFromStart();
			pause();
			
			

			//Bottom
			getLblAnswer().setText(g.getUsingDeck().getQuestion(g.getPosQuestion()).getAnswer());
			if(RulesSettings.getShow_answer()) this.setBottom(getLblAnswer());
		}
		
		private void play() {
			if(transition.getStatus() == Status.PAUSED) {
				transition.play();
				return;
			}
			if(getTimelineTimer().getStatus() == Status.STOPPED || getTimelineTimer().getStatus() == Status.PAUSED) {
				getTimelineTimer().play();
			}
		}
		private void pause() {
			if(getTimelineTimer().getStatus() == Status.RUNNING) {
				getTimelineTimer().pause();
			}
			else if(transition.getStatus() == Status.RUNNING) {
				transition.pause();
			}
			
			if(MsgBox.displayPause(g.getPlayer().toString(), g.getUsingDeck().getTheme())) {
				play();
			}
		}
		
		private void updateIvScore(int score, int position) {
			getIvScore().setImage(new Image(IGraphicConst.URL_PATH_IMG + "speedometer/score" + score + "position" + position + ".png"));
		}
		
		private void checkAnswer() {
			getTimelineTimer().pause();
			gradient = Arrays.asList("#000000");
			List<String> fromBlackToGreen = Arrays.asList("#000000", "#001700", "#002E00", "#004600", "#005D00", "#007400", 
														  "#008B00", "#00A200", "#00B900", "#00D100", "#00E800", "#00FF00");
			List<String> fromBlackToRed = Arrays.asList("#000000", "#170000", "#2E0000", "#460000", "#5D0000", "#740000", 
					  									"#8B0000", "#A20000", "#B90000", "#D10000", "#E80000", "#FF0000");
			index = 0;
			String s = getTxtAnswer().getText();
			getTxtAnswer().setText(g.getUsingDeck().getQuestion(g.getPosQuestion()).getAnswer());
			if(g.isRightAnswer(s)) {
				if(scorePos<g.getPlayer().getScore()) {
					scorePos++;
				}
				else {
					g.addPoint();
					scorePos++;
				}
				gradient = fromBlackToGreen;
			}
			else {
				scorePos = 0;
				gradient = fromBlackToRed;
			}
			transition = new Timeline();
			transition.setCycleCount(gradient.size());
			transition.getKeyFrames().add(new KeyFrame(Duration.millis(RulesSettings.getTime_gap_answer()), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
				getTxtAnswer().setStyle("-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
										"-fx-font-size: 15px;\r\n" + 
										"-fx-font-weight: bold;\r\n" + 
										"-fx-text-inner-color : " + gradient.get(index++) +";");
				}
			}));
			transition.playFromStart();
			transition.setOnFinished(e -> {
				getTxtAnswer().setStyle(IGraphicConst.STYLE_TXT);
				getTxtAnswer().setText("");
				getTxtAnswer().setPromptText("");
				getTxtAnswer().requestFocus();
				getTimelineTimer().play();
			});
		}
		
		private void nextQuestion() {
			if(g.isDeckFinished()) {
				finishThisRound();
				return;
			}
			
			g.nextPosQuestion();
			updateIvScore(g.getPlayer().getScore(), scorePos);
			cluesPos = 0;
			fullClues = g.getClue(0) + " " + g.getClue(1) + " " + g.getClue(2);
			clues.setValue("" + fullClues.charAt(cluesPos++));
			getLblAnswer().setText(g.getUsingDeck().getQuestion(g.getPosQuestion()).getAnswer());
		}
		
		private void finishThisRound() {
			//Stop the timeline
			if(getTimelineTimer().getStatus() == Status.RUNNING || getTimelineTimer().getStatus() == Status.PAUSED) {
				getTimelineTimer().stop();
			}
			
			g.getPlayer().setTime(RulesSettings.getRound_time_seconds() - Double.parseDouble(getLblTimer().getText()));
			if(g.isGameFinished()) {
				GameDecksAndPlayers.reset();
				showElement(new Ranking());
				return;
			}
			//Setting up next Player
			g.nextPosPlayer();
			g.setPosQuestion(0);
			//Setting up GUI
			showElement(new ThemeSelection());
		}
		
		public Label getLblPlayer() {
			if(lblPlayer==null) {
				lblPlayer = new Label();
				lblPlayer.setText(getGame().getPlayer().getPseudo());
				lblPlayer.setPrefSize(250, 20);
				lblPlayer.setTranslateX(-40);
				lblPlayer.setTranslateY(40);
				lblPlayer.setAlignment(Pos.BASELINE_LEFT);
				IGraphicConst.styleBiggerLabel(lblPlayer);
			}
			return lblPlayer;
		}
		public ImageView getIvScore() {
			if(ivScore==null) {
				ivScore = new ImageView(IGraphicConst.URL_PATH_IMG + "speedometer/score0position0.png");
				ivScore.setFitHeight(125);
				ivScore.setFitWidth(125);
				ivScore.setTranslateX(-100);
				ivScore.setTranslateY(40);
			}
			return ivScore;
		}
		public Timeline getTimelineTimer() {
			if(timelineTimer==null) {
				timelineTimer = new Timeline();
				timelineTimer.setCycleCount(Timeline.INDEFINITE);
				timelineTimer.getKeyFrames().add(new KeyFrame(Duration.millis(RulesSettings.getTime_gap_millis()), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						timer.setValue(timer.get() - RulesSettings.getTime_gap_millis()/1000);
						if(timer.get() <= RulesSettings.getTime_gap_millis()/1000) {
							timelineTimer.stop();
							finishThisRound();
						}
						if(cluesPos<fullClues.length()) {
							clues.setValue(clues.get() + fullClues.charAt(cluesPos++));
						}
					}
				}));
			}
			return timelineTimer;
		}
		public Label getLblTimer() {
			if(lblTimer==null) {
				lblTimer = new Label();
				IGraphicConst.styleBiggerLabel(lblTimer);
				lblTimer.setPrefSize(80, 20);
				lblTimer.setTranslateX(40);
				lblTimer.setTranslateY(40);
				lblTimer.textProperty().bind(timer.asString(Locale.US, "%-2.2f"));
			}
			return lblTimer;
		}
		private void disableJokers() {
			getIvJokerLetters().setDisable(true);
			getIvJokerLetters().setOpacity(0.5);
			getIvJokerExtraPass().setDisable(true);
			getIvJokerExtraPass().setOpacity(0.5);
			getIvJokerBonusTime().setDisable(true);
			getIvJokerBonusTime().setOpacity(0.5);
		}
		public ImageView getIvJokerLetters() {
			if(ivJokerLetters==null) {
				ivJokerLetters = new ImageView();
				if(RulesSettings.getFaced_joker()) ivJokerLetters.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/arno.png"));
				else ivJokerLetters.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
				ivJokerLetters.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerLetters.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				Tooltip.install(ivJokerLetters, new Tooltip("Hangman !"));
				ivJokerLetters.setCursor(Cursor.HAND);
				ivJokerLetters.setOnMouseClicked(e -> {
					String answer = g.getUsingDeck().getQuestion(g.getPosQuestion()).getAnswer();
//					getTxtAnswer().setPromptText(answer
//					.replaceAll("[a,e,i,o,u,y]", "_"));
//					getTxtAnswer().setPromptText(answer
//							.replaceAll("[b, c, d, f, g, h, j, k, l, m, n, p, q, r, s, t, v, w, x, z ]", "_"));
					String tmp="";
					for(int i=0;i<answer.length();i++) {
						if(i%2!=0 && answer.charAt(i) != ' ') {
							tmp += "_";
						}
						else {
							tmp += answer.charAt(i);
						}
					}
					getTxtAnswer().setPromptText(tmp);
					getBtnPause().requestFocus();
					disableJokers();
				});
			}
			return ivJokerLetters;
		}
		public ImageView getIvJokerExtraPass() {
			if(ivJokerExtraPass==null) {
				ivJokerExtraPass = new ImageView();
				if(RulesSettings.getFaced_joker()) ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/rayan.png"));
				else ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
				ivJokerExtraPass.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerExtraPass.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				Tooltip.install(ivJokerExtraPass, new Tooltip("Pass for free !"));
				ivJokerExtraPass.setCursor(Cursor.HAND);
				ivJokerExtraPass.setOnMouseClicked(e -> {
					nextQuestion();
					disableJokers();
				});
			}
			return ivJokerExtraPass;
		}
		public ImageView getIvJokerBonusTime() {
			if(ivJokerBonusTime==null) {
				ivJokerBonusTime = new ImageView();
				if(RulesSettings.getFaced_joker()) ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/loic.png"));
				else ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
				ivJokerBonusTime.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerBonusTime.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				Tooltip.install(ivJokerBonusTime, new Tooltip("More time !"));
				ivJokerBonusTime.setCursor(Cursor.HAND);
				ivJokerBonusTime.setOnMouseClicked(e -> {
					timer.setValue(timer.get() + RulesSettings.getJoker_time());
					disableJokers();
				});
			}
			return ivJokerBonusTime;
		}
		public Label getLblClues() {
			if(lblClues==null) {
				lblClues = new Label(); 
				lblClues.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL, IGraphicConst.HEIGHT_LARGE_LBL);
				lblClues.setStyle("-fx-font-size: 16;\r\n" + 
								"-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
								"-fx-font-weight: bold;");
				lblClues.setTextFill(Color.WHITE);
				lblClues.setBackground(new Background(new BackgroundFill(
						Color.web("#793F54"), new CornerRadii(20, false) , null)));
				lblClues.setBorder(new Border(new BorderStroke(
						Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
				lblClues.setPadding(new Insets(10));
				lblClues.setWrapText(true);
				lblClues.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
				lblClues.textProperty().bind(clues);
			}
			return lblClues;
		}
		public TextField getTxtAnswer() {
			if(txtAnswer==null) {
				txtAnswer = new TextField();
				txtAnswer.setMinWidth(IGraphicConst.WIDTH_LARGE_LBL+10);
				txtAnswer.setMaxWidth(IGraphicConst.WIDTH_LARGE_LBL+10);
				txtAnswer.setStyle(IGraphicConst.STYLE_TXT);
				txtAnswer.setBackground(new Background(new BackgroundFill(
						Color.WHITE, new CornerRadii(20, false) , new Insets(10))));
				txtAnswer.setBorder(new Border(new BorderStroke(
						Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2), new Insets(10))));
				txtAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER) {
							checkAnswer();
							nextQuestion();
							event.consume();
						}
					}
				});
			}
			return txtAnswer;
		}
		public Button getBtnPass() {
			if(btnPass==null) {
				btnPass = new Button("PASS");
				btnPass.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL *0.4, IGraphicConst.HEIGHT_BUTTON*0.2);
				IGraphicConst.styleButton(btnPass);
				btnPass.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						scorePos = 0;
						nextQuestion();
					}
				});
			}
			return btnPass;
		}
		public Button getBtnPause() {
			if(btnPause==null) {
				btnPause = new Button(" ll ");
				btnPause.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL *0.15, IGraphicConst.HEIGHT_BUTTON*0.2);
				IGraphicConst.styleButton(btnPause);
				btnPause.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						pause();
					}
				});
			}
			return btnPause;
		}
		public Button getBtnValidate() {
			if(btnValidate==null) {
				btnValidate = new Button("VALIDATE");
				btnValidate.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL *0.4, IGraphicConst.HEIGHT_BUTTON*0.2);
				IGraphicConst.styleButton(btnValidate);
				btnValidate.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						checkAnswer();
						nextQuestion();
					}
				});
			}
			return btnValidate;
		}
		public Label getLblAnswer() {
			if(lblAnswer==null) {
				lblAnswer = new Label();
				IGraphicConst.styleLabel(lblAnswer);
				lblAnswer.setAlignment(Pos.BASELINE_CENTER);
			}
			return lblAnswer;
		}
		
	}
	
	
	/**
	 * The ranking of the current game played.
	 * @author ArRaLo
	 *
	 */
	class Ranking extends BorderPane {
		
		private Label lblRank;
		private Label lblPseudo;
		private Label lblScore;
		private Label lblTime;
		
		private GridPane gp;
		
		private Button btnReplay;
				
		public Ranking() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_scores.png", false), 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			//Titles
			getGp().add(getLblRank(), 0, 0);
			getGp().add(getLblPseudo(), 1, 0);
			getGp().add(getLblScore(), 2, 0);
			getGp().add(getLblTime(), 3, 0);
			
			//Content
			this.addPlayers();
			
			this.setCenter(getGp());
		}
		
		private void addPlayer(int pos) {

			Label lbl = new Label();
			lbl.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON - IGraphicConst.WIDTH_RANK, IGraphicConst.HEIGHT_BUTTON);
			lbl.setBackground(new Background(new BackgroundFill(Color.web("#793F54"), new CornerRadii(20, false) , null)));
			lbl.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
			
			getGp().add(lbl, 1, pos+1, 3, 1);
			
			Player p = getGame().getPlayer(pos);
			ImageView ivPlayerRank = new ImageView(IGraphicConst.URL_PATH_IMG + "rank/rank_"+ (pos+1) + ".png");
			ivPlayerRank.setFitWidth(IGraphicConst.WIDTH_RANK);
			ivPlayerRank.setFitHeight(IGraphicConst.HEIGHT_RANK);
			Label lblPlayerPseudo = IGraphicConst.styleLabel(new Label("  " +p.getPseudo()));
			Label lblPlayerScore = IGraphicConst.styleLabel(new Label(""+p.getScore()));
			Label lblPlayerTime = IGraphicConst.styleLabel(new Label(""+String.format(Locale.US, "%-2.2f",p.getTime())));
			
			getGp().add(ivPlayerRank, 0, pos+1);
			getGp().add(lblPlayerPseudo, 1, pos+1);
			getGp().add(lblPlayerScore, 2, pos+1);
			getGp().add(lblPlayerTime, 3, pos+1);
			
		}
		
		private void addPlayers() {
			getGame().endGame();
			for(int i=0; i<getGame().getNumberOfPlayers(); i++) {
				this.addPlayer(i);
			}
			getGp().add(getBtnReplay(), 0, getGame().getNumberOfPlayers() + 1, 4, 1);
		}
		
		public GridPane getGp() {
			if(gp==null) {
				gp = new GridPane();
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(50);
				gp.setVgap(25);
				gp.setTranslateY(50);
			}
			return gp;
		}
		
		public Label getLblRank() {
			if(lblRank==null) {
				lblRank = new Label("RANK");
				IGraphicConst.styleBiggerLabel(lblRank);
			}
			return lblRank;
		}
		public Label getLblPseudo() {
			if(lblPseudo==null) {
				lblPseudo = new Label(" PSEUDO");
				IGraphicConst.styleBiggerLabel(lblPseudo);
			}
			return lblPseudo;
		}
		public Label getLblScore() {
			if(lblScore==null) {
				lblScore = new Label("SCORE");
				IGraphicConst.styleBiggerLabel(lblScore);
			}
			return lblScore;
		}
		public Label getLblTime() {
			if(lblTime==null) {
				lblTime = new Label("TIME");
				IGraphicConst.styleBiggerLabel(lblTime);
			}
			return lblTime;
		}
		public Button getBtnReplay() {
			if(btnReplay==null) {
				btnReplay = new Button("REPLAY");
				IGraphicConst.styleButton(btnReplay);
				btnReplay.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				btnReplay.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							getGame().replay();
						} catch (NotEnoughDeckException e) {
							MsgBox.dispalyException(e);
							return;
						}
						showElement(new ThemeSelection());
					}
				});
			}
			return btnReplay;
		}
	}
}