package view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Deck;
import model.Game;
import model.RulesConst;
import model.Player;


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
	
	//Pane vars
	private PlayerSelection playerSelection;
	private ThemeSelection themeSelection;
	private GamePane gamePane;
	private Ranking ranking;
	
	public GameView() {
		
		this.game = Game.getInstance();
		this.getChildren().addAll(getPlayerSelection());
		this.showElement(getPlayerSelection());
	}
	public void reset() {
		//TODO reset
	}
	private void hideVisible() {
		for(Node n : this.getChildren()) {
			if(n.isVisible()) {
				n.setVisible(false);
			}
		}
	}
	private void showElement(Node element) {
		hideVisible();
		element.setVisible(true);
	}
	// Game elements
	public Game getGame() {
		return game;
	}
	
	// GUI elements
	public PlayerSelection getPlayerSelection() {
		if(playerSelection==null) {
			playerSelection = new PlayerSelection();
			playerSelection.setId("playerSelection");
		}
		return playerSelection;
	}
	public ThemeSelection getThemeSelection() {
		if(themeSelection==null) {
			themeSelection = new ThemeSelection();
			themeSelection.setId("themeSelection");
		}
		return themeSelection;
	}
	public GamePane getGamePane() {
		if(gamePane==null) {
			gamePane = new GamePane();
		}
		return gamePane;
	}
	public Ranking getRanking() {
		if(ranking==null) {
			ranking = new Ranking();
			ranking.setId("ranking");
		}
		return ranking;
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
			
			addPlayer();
			//CENTER
			getGpCenter().setTranslateY(50);
			this.setCenter(getGpCenter());
			
			//BOTTOM
			HBox hbBottom = new HBox(getBtnPlay());
			hbBottom.setAlignment(Pos.BASELINE_RIGHT);
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
			getlLblPlayer().add(new Label("PLAYER " + (this.count+1)));
			getlTxtPlayer().add(new TextField());
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
			ImageView iv = new ImageView("file:./src/resources/images/icon_add_player.png");
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
			ImageView ivDel = new ImageView("file:./src/resources/images/icon_remove_player.png");
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
				gpCenter.setHgap(10);
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
				btnPlay.setId("btnPlaySelectPlayers");
				btnPlay.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						for(TextField txtP : getlTxtPlayer()) {
							try {
								GameView.this.getGame().addPlayer(txtP.getText());
							} catch (Exception e) {
								MsgBox.dispalyException(e);
								GameView.this.getGame().removeAllPlayers();
								return;
							}
						}
						getGame().setCurrentPlayer(0);
						GameView.this.getChildren().add(getThemeSelection());
						GameView.this.showElement(getThemeSelection());
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
				lblPlayer.setId("lblPlayerTheme");
			}
			return lblPlayer;
		}
		public List<Button> getlBtnTheme() {
			if(lBtnTheme==null) {
				lBtnTheme = new ArrayList<>();
				
				if(getGame().getCurrentPlayer()==0) {
					getGame().updateAllDecks(getGame().randomChoice(getGame().getNumberOfPlayers()));
				}
				
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
					b.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(b.getText().equals(txtMysteryTheme)) {
								GameView.this.getGame().addUsedDeck(getGame().getDeck(getGame().getNumberOfPlayers()));
							}
							else {
								GameView.this.getGame().addUsedDeck(b.getText());
							}
							GameView.this.getChildren().add(getGamePane());
							GameView.this.showElement(getGamePane());
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
		Game g = Game.getInstance();
		private SimpleDoubleProperty timer = new SimpleDoubleProperty(RulesConst.getRound_time_seconds());
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
		
		private Timeline timelineClues;
		private Label lblClues;
		private TextField txtAnswer;
		private Button btnPass;
		private Button btnValidate;
		
		public GamePane() {
			
			scorePos = 0;
			cluesPos = 0;
			
			//TOP
			HBox hbTop = new HBox(30);
			hbTop.setAlignment(Pos.BASELINE_CENTER);
			hbTop.getChildren().addAll(getLblPlayer(), getIvScore(), getLblTimer());
			this.setTop(hbTop);
			
			//LEFT
			VBox vbLeft = new VBox(15);
			vbLeft.setPadding(new Insets(0, 5, 10, 25));
			vbLeft.setAlignment(Pos.CENTER);
			vbLeft.getChildren().addAll(getIvJokerFirstLetter(), getIvJokerExtraPass(), getIvJokerBonusTime());
			this.setLeft(vbLeft);
			
			//CENTER
			VBox vbCenter = new VBox(10);
			vbCenter.setAlignment(Pos.CENTER);
			vbCenter.setPadding(new Insets(5, 50, 15, 10));
			
			HBox hbCenterBottom = new HBox(15);
			hbCenterBottom.setAlignment(Pos.CENTER);
			hbCenterBottom.getChildren().addAll(getBtnPass(), getBtnValidate());
			

			vbCenter.getChildren().addAll(getLblClues(), getTxtAnswer(), hbCenterBottom);
			this.setCenter(vbCenter);
			
//			Task<Timeline> taskTimer = new Task<Timeline>() {
//				@Override
//				protected Timeline call() throws Exception {
//					return getTimelineTimer();
//				}
//			};
//			new Thread(taskTimer).start();
//			Task<Timeline> taskClues = new Task<Timeline>() {
//				@Override
//				protected Timeline call() throws Exception {
//					return getTimelineClues();
//				}
//			};
//			new Thread(taskClues).start();
			getTimelineTimer().playFromStart();
			getTimelineClues().playFromStart();
		}
		
		private void updateIvScore(int score, int position) {
			getIvScore().setImage(new Image("file:./src/resources/speedometer/score" + score + "position" + position + ".png"));
		}
		
		private void nextQuestion() {
			if(g.isFinished(g.getUsingDeck())) {
				finishThisRound();
				return;
			}
			
			g.nextCurrentQuestion();
			updateIvScore(g.getPlayer().getScore(), scorePos);
			cluesPos = 0;
			clues.setValue("");
			getTxtAnswer().setText("");
			getTimelineClues().playFromStart();
			System.out.println(g.getPlayer() +"\t"+ g.getPlayer().getScore());
			
		}
		
		private void finishThisRound() {
			if(g.isFinished()) {
				getGamePane().setVisible(false);
				GameView.this.getChildren().add(getRanking());
				getRanking().setVisible(true);
				return;
			}
			//Setting up next Player
			g.getPlayer().setTime(RulesConst.getRound_time_seconds() - Integer.parseInt(getLblTimer().getText()));
			g.nextCurrentPlayer();
			g.setCurrentQuestion(0);
			//Setting up GUI
			GameView.this.getChildren().remove(getGamePane());
			GameView.this.gamePane = null;
			GameView.this.themeSelection = null;;
			GameView.this.getChildren().add(getThemeSelection());
			getThemeSelection().setVisible(true);
			
		}
		
		public Label getLblPlayer() {
			if(lblPlayer==null) {
				lblPlayer = new Label();
				lblPlayer.setText(getGame().getPlayer().getPseudo());
			}
			return lblPlayer;
		}
		public ImageView getIvScore() {
			if(ivScore==null) {
				ivScore = new ImageView("file:./src/resources/speedometer/score0position0.png");
				ivScore.setFitHeight(100);
				ivScore.setFitWidth(100);
			}
			return ivScore;
		}
		public Timeline getTimelineTimer() {
			if(timelineTimer==null) {
				timelineTimer = new Timeline();
				timelineTimer.setCycleCount(Timeline.INDEFINITE);
				getLblTimer().textProperty().bind(timer.asString("%.0f"));
				timelineTimer.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						timer.setValue(timer.get()-1);
						if(timer.get() <= 0) {
							timelineTimer.stop();
						}
					}
				}));
				//TODO include in a new Thread or something
			}
			return timelineTimer;
		}
		public Label getLblTimer() {
			if(lblTimer==null) {
				lblTimer = new Label();
			}
			return lblTimer;
		}
		public ImageView getIvJokerFirstLetter() {
			if(ivJokerFirstLetter==null) {
				ivJokerFirstLetter = new ImageView("file:./src/resources/images/logo.png");
				Tooltip.install(ivJokerFirstLetter, new Tooltip("First letter of the answer !"));
				ivJokerFirstLetter.setOnMouseClicked(e -> {
					System.out.println("ivJokerFirstLetter");
					ivJokerFirstLetter.setDisable(true);
					//TODO JOKER
				}); 
			}
			return ivJokerFirstLetter;
		}
		public ImageView getIvJokerExtraPass() {
			if(ivJokerExtraPass==null) {
				ivJokerExtraPass = new ImageView("file:./src/resources/images/logo.png");
				Tooltip.install(ivJokerExtraPass, new Tooltip("Pass for free !"));
				ivJokerExtraPass.setOnMouseClicked(e -> {
					System.out.println("ivJokerExtraPass");
					ivJokerExtraPass.setDisable(true);
					//TODO JOKER
				}); 
			}
			return ivJokerExtraPass;
		}
		public ImageView getIvJokerBonusTime() {
			if(ivJokerBonusTime==null) {
				ivJokerBonusTime = new ImageView("file:./src/resources/images/logo.png");
				Tooltip.install(ivJokerBonusTime, new Tooltip("More time !"));
				ivJokerBonusTime.setOnMouseClicked(e -> {
					timer.setValue(timer.get() + 10);
					ivJokerBonusTime.setDisable(true);
				});
			}
			return ivJokerBonusTime;
		}
		public Timeline getTimelineClues() {
			if(timelineClues==null) {
				timelineClues = new Timeline();
				timelineClues.setCycleCount(3);
				getLblClues().textProperty().bind(clues);
				timelineClues.getKeyFrames().add(new KeyFrame(Duration.millis(1200), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								if(clues.isEmpty().getValue()) {
									clues.set(getGame().getClues(cluesPos++));
								}
								else{
									clues.set(clues.get() + " " + getGame().getClues(cluesPos++));
								}
							}
						}).run();
					}
				})); //TODO OPTI
			}
			return timelineClues;
		}
		public Label getLblClues() {
			if(lblClues==null) {
				lblClues = new Label(); 
				lblClues.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL, IGraphicConst.HEIGHT_LARGE_LBL);
				lblClues.setWrapText(true);
				lblClues.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
			}
			return lblClues;
		}
		public TextField getTxtAnswer() {
			if(txtAnswer==null) {
				txtAnswer = new TextField();
				txtAnswer.setMinWidth(IGraphicConst.WIDTH_LARGE_LBL);
				txtAnswer.setMaxWidth(IGraphicConst.WIDTH_LARGE_LBL);
			}
			return txtAnswer;
		}
		public Button getBtnPass() {
			if(btnPass==null) {
				btnPass = new Button("PASS");
				btnPass.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL/2, IGraphicConst.HEIGHT_BUTTON);
				btnPass.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						getGame().nextCurrentQuestion();
						scorePos = 0;
						
					}
				});
			}
			return btnPass;
		}
		public Button getBtnValidate() {
			if(btnValidate==null) {
				btnValidate = new Button("VALIDATE");
				btnValidate.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL/2, IGraphicConst.HEIGHT_BUTTON);
				btnValidate.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
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
				
		public Ranking() {
			
			//Setup positioning
			this.setAlignment(Pos.CENTER);
			this.setHgap(10);
			this.setVgap(15);
			
			//Titles
			this.add(getLblRank(), 0, 0);
			this.add(getLblPseudo(), 1, 0);
			this.add(getLblScore(), 2, 0);
			this.add(getLblTime(), 3, 0);

			
			//Content
			this.addPlayers();
		}
		
		private void addPlayer(int pos) {
			Player p = getGame().getPlayer(pos);
			ImageView ivPlayerRank = new ImageView("file:./src/resources/images/logo_first.png");
			//TODO add different logo's relative to rank
			Label lblPlayerPseudo = new Label(p.getPseudo());
			Label lblPlayerScore = new Label(""+p.getScore());
			Label lblPlayerTime = new Label(""+p.getTime());
			ivPlayerRank.getStyleClass().add("positionRank");
			lblPlayerPseudo.getStyleClass().addAll("positionPseudo", "lblRanking");
			lblPlayerScore.getStyleClass().addAll("positionScore", "lblRanking");
			lblPlayerTime.getStyleClass().addAll("positionTime", "lblRanking");
			
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
		}
		
		public Label getLblRank() {
			if(lblRank==null) {
				lblRank = new Label("RANK");
				lblRank.getStyleClass().add("positionRank");
				lblRank.getStyleClass().add("titleRanking");
			}
			return lblRank;
		}
		public Label getLblPseudo() {
			if(lblPseudo==null) {
				lblPseudo = new Label("PSEUDO");
				lblPseudo.getStyleClass().add("positionPseudo");
				lblPseudo.getStyleClass().add("titleRanking");
			}
			return lblPseudo;
		}
		public Label getLblScore() {
			if(lblScore==null) {
				lblScore = new Label("SCORE");
				lblScore.getStyleClass().add("positionScore");
				lblScore.getStyleClass().add("titleRanking");
			}
			return lblScore;
		}
		public Label getLblTime() {
			if(lblTime==null) {
				lblTime = new Label("TIME");
				lblTime.getStyleClass().add("positionTime");
				lblTime.getStyleClass().add("titleRanking");
			}
			return lblTime;
		}
	}
}