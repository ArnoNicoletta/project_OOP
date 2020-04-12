package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import model.Game;
import model.Player;
import model.RulesConst;


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
	private Game game;
	
	public GameView() {
		
		this.game = Game.getInstance();
		this.showElement(new PlayerSelection());
	}
	
	private void showElement(Node element) {
		this.getChildren().removeAll(this.getChildren());
		this.getChildren().add(element);
		element.setVisible(true);
	}
	// Game elements
	public Game getGame() {
		return game;
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
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_select_players.png", false), 
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
			if(this.count>=RulesConst.MAX_PLAYER) {
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
			ImageView iv = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/icon_add_player.png");
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
			ImageView ivDel = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/icon_remove_player.png");
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
						getGame().setCurrentPlayer(0);
						getGame().randomChoice();
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
			vbCenter.getChildren().add(getLblPlayer());
			vbCenter.getChildren().addAll(getlBtnTheme());
			vbCenter.setAlignment(Pos.CENTER);
			this.setCenter(vbCenter);
		}
		
		public Label getLblPlayer() {
			if(lblPlayer==null) {
				lblPlayer = new Label(getGame().getPlayer(getGame().getCurrentPlayer()) + 
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
		private Game g = Game.getInstance();
		private SimpleDoubleProperty timer = new SimpleDoubleProperty(RulesConst.getRound_time_seconds());
		private String fullClues = g.getClues(0) + " " + g.getClues(1) + " " + g.getClues(2);
		private int scorePos;
		private SimpleStringProperty clues = new SimpleStringProperty();
		private int cluesPos;
		//GUI vars
		private Label lblPlayer;
		private ImageView ivScore;
		private Timeline timelineTimer;
		private Label lblTimer;
		
		private ImageView ivJokerFirstLetter;
		private ImageView ivJokerExtraPass;
		private ImageView ivJokerBonusTime;
		
		private Label lblClues;
		private TextField txtAnswer;
		private Button btnPass;
		private Button btnPause;
		private Button btnValidate;
		
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
			
			//LEFT
			VBox vbLeft = new VBox(15);
			vbLeft.setPadding(new Insets(0, 5, 10, 25));
			vbLeft.setAlignment(Pos.CENTER_RIGHT);
			vbLeft.getChildren().addAll(getIvJokerFirstLetter(), getIvJokerExtraPass(), getIvJokerBonusTime());
			vbLeft.toFront();
			this.setLeft(vbLeft);
			
			//CENTER
			VBox vbCenter = new VBox(10);
			vbCenter.setAlignment(Pos.CENTER);
			vbCenter.setPadding(new Insets(5, 50, 15, 10));
			vbCenter.setTranslateX(35);
			HBox hbCenterBottom = new HBox(10);
			hbCenterBottom.setAlignment(Pos.CENTER);
			hbCenterBottom.getChildren().addAll(getBtnPass(), getBtnPause(), getBtnValidate());
			

			vbCenter.getChildren().addAll(getLblClues(), getTxtAnswer(), hbCenterBottom);
			this.setCenter(vbCenter);
			
			clues.setValue("" + fullClues.charAt(cluesPos++));
			getTimelineTimer().playFromStart();
			pause();
		}
		
		private void play() {
			if(getTimelineTimer().getStatus() == Status.STOPPED || getTimelineTimer().getStatus() == Status.PAUSED) {
				getTimelineTimer().play();
			}
		}
		private void pause() {
			if(getTimelineTimer().getStatus() == Status.RUNNING) {
				getTimelineTimer().pause();
				if(MsgBox.displayPause(g.getPlayer().toString(), g.getUsingDeck().getTheme())) {
					play();
				}
				else {
					//TODO back to menu
				}
			}
		}
		
		private void updateIvScore(int score, int position) {
			getIvScore().setImage(new Image(IGraphicConst.URL_PATH_IMG + "speedometer/score" + score + "position" + position + ".png"));
		}
		
		private void checkAnswer() {
			String s = getTxtAnswer().getText();
			if(g.isRightAnswer(s)) {
				if(scorePos<g.getPlayer().getScore()) {
					scorePos++;
				}
				else {
					g.addPoint();
					scorePos++;
				}
			}
			else {
				getTxtAnswer().setText("");
				scorePos = 0;
			}
		}
		
		private void nextQuestion() {
			if(g.isFinished(g.getUsingDeck())) {
				finishThisRound();
				return;
			}
			
			g.nextCurrentQuestion();
			updateIvScore(g.getPlayer().getScore(), scorePos);
			cluesPos = 0;
			fullClues = g.getClues(0) + " " + g.getClues(1) + " " + g.getClues(2);
			clues.setValue("" + fullClues.charAt(cluesPos++));
			getTxtAnswer().setText("");
			getTxtAnswer().requestFocus();
		}
		
		private void finishThisRound() {
			//Stop the timeline
			if(getTimelineTimer().getStatus() == Status.RUNNING || getTimelineTimer().getStatus() == Status.PAUSED) {
				getTimelineTimer().stop();
			}
			
			g.getPlayer().setTime(RulesConst.getRound_time_seconds() - Double.parseDouble(getLblTimer().getText()));
			if(g.isFinished()) {
				showElement(new Ranking());
				return;
			}
			//Setting up next Player
			g.nextCurrentPlayer();
			g.setCurrentQuestion(0);
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
				timelineTimer.getKeyFrames().add(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						timer.setValue(timer.get() - 0.025);
						if(timer.get() <= 0.025) {
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
		public ImageView getIvJokerFirstLetter() {
			if(ivJokerFirstLetter==null) {
				ivJokerFirstLetter = new ImageView();
				if(RulesConst.getFaced_joker()) ivJokerFirstLetter.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/arno.png"));
				else ivJokerFirstLetter.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/joker.png"));
				ivJokerFirstLetter.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerFirstLetter.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				ivJokerFirstLetter.setCursor(Cursor.HAND);
//				ivJokerFirstLetter.setTranslateX(200); //TODO joker
				Tooltip.install(ivJokerFirstLetter, new Tooltip("First letter of the answer !"));
				ivJokerFirstLetter.setOnMouseClicked(e -> {
					getTxtAnswer().setText(g.getUsingDeck().getQuestion(g.getCurrentQuestion()).getAnswer().substring(0, 1));
					ivJokerFirstLetter.setDisable(true);
					ivJokerFirstLetter.setOpacity(0.5);
				}); 
			}
			return ivJokerFirstLetter;
		}
		public ImageView getIvJokerExtraPass() {
			if(ivJokerExtraPass==null) {
				ivJokerExtraPass = new ImageView();
				if(RulesConst.getFaced_joker()) ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/rayan.png"));
				else ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/joker.png"));
				ivJokerExtraPass.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerExtraPass.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				ivJokerExtraPass.setCursor(Cursor.HAND);
//				ivJokerExtraPass.setTranslateX(200); //TODO joker
				Tooltip.install(ivJokerExtraPass, new Tooltip("Pass for free !"));
				ivJokerExtraPass.setOnMouseClicked(e -> {
					nextQuestion();
					ivJokerExtraPass.setDisable(true);
					ivJokerExtraPass.setOpacity(0.5);
				});
			}
			return ivJokerExtraPass;
		}
		public ImageView getIvJokerBonusTime() {
			if(ivJokerBonusTime==null) {
				ivJokerBonusTime = new ImageView();
				if(RulesConst.getFaced_joker()) ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/loic.png"));
				else ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/joker.png"));
				ivJokerBonusTime.setFitWidth(IGraphicConst.WIDTH_JOKER);
				ivJokerBonusTime.setFitHeight(IGraphicConst.HEIGHT_JOKER);
				ivJokerBonusTime.setCursor(Cursor.HAND);
				ivJokerBonusTime.toFront();
//				ivJokerBonusTime.setTranslateX(200); //TODO joker
				Tooltip.install(ivJokerBonusTime, new Tooltip("More time !"));
				ivJokerBonusTime.setOnMouseClicked(e -> {
					timer.setValue(timer.get() + RulesConst.JOKER_TIME);
					ivJokerBonusTime.setDisable(true);
					ivJokerBonusTime.setOpacity(0.5);
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
		
	}
	
	
	/**
	 * The ranking of the current game played.
	 * @author ArRaLo
	 *
	 */
	class Ranking extends GridPane {
		
		private Label lblRank;
		private Label lblPseudo;
		private Label lblScore;
		private Label lblTime;
		
		private Button btnReplay;
				
		public Ranking() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_scores.png", false), 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER, 
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			//Setup positioning
			this.setAlignment(Pos.CENTER);
			this.setHgap(50);
			this.setVgap(25);
			
			//Titles
			this.add(getLblRank(), 0, 0);
			this.add(getLblPseudo(), 1, 0);
			this.add(getLblScore(), 2, 0);
			this.add(getLblTime(), 3, 0);
			
			//Content
			this.addPlayers();
		}
		
		private void addPlayer(int pos) {

			Label lbl = new Label();
			lbl.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON - IGraphicConst.WIDTH_RANK, IGraphicConst.HEIGHT_BUTTON);
			lbl.setBackground(new Background(new BackgroundFill(Color.web("#793F54"), new CornerRadii(20, false) , null)));
			lbl.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
			
			this.add(lbl, 1, pos+1, 3, 1);
			
			Player p = getGame().getPlayer(pos);
			ImageView ivPlayerRank = new ImageView(IGraphicConst.URL_PATH_IMG + "rank/rank_"+ (pos+1) + ".png");
			ivPlayerRank.setFitWidth(IGraphicConst.WIDTH_RANK);
			ivPlayerRank.setFitHeight(IGraphicConst.HEIGHT_RANK);
			Label lblPlayerPseudo = IGraphicConst.styleLabel(new Label("  " +p.getPseudo()));
			Label lblPlayerScore = IGraphicConst.styleLabel(new Label(""+p.getScore()));
			Label lblPlayerTime = IGraphicConst.styleLabel(new Label(""+String.format(Locale.US, "%-2.2f",p.getTime())));
			
			this.add(ivPlayerRank, 0, pos+1);
			this.add(lblPlayerPseudo, 1, pos+1);
			this.add(lblPlayerScore, 2, pos+1);
			this.add(lblPlayerTime, 3, pos+1);
			
		}
		
		private void addPlayers() {
			getGame().sortPlayers();
			for(int i=0; i<getGame().getNumberOfPlayers(); i++) {
				this.addPlayer(i);
			}
			this.add(getBtnReplay(), 0, getGame().getNumberOfPlayers() + 1, 4, 1);
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
						getGame().replay();
						showElement(new ThemeSelection());
					}
				});
			}
			return btnReplay;
		}
	}
}