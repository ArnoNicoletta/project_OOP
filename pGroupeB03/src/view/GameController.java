package view;

import java.util.ArrayList;
import java.util.List;

import controller.Game;
import exception.IdenticalPseudoException;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Deck;
import model.IRulesConst;
import model.Player;

/**
 * {@link StackPane} of the game which includes all views needed to play the game
 * @author ArRaLo
 * @see {@link PlayerSelection}
 * @see {@link ThemeSelection}
 * @see {@link GamePane}
 * @see {@link Ranking}
 */
public class GameController extends StackPane {
	
	//Game vars
	private Game game;
	private int currentPlayer;
	
	//Pane vars
	private PlayerSelection playerSelection;
	private ThemeSelection themeSelection;
	private GamePane gamePane;
	private Ranking ranking;
	
	public GameController() {
		this.game = Game.getInstance();
		this.getChildren().addAll(getPlayerSelection());
		this.showElement(getPlayerSelection());
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
	public void setCurrentPlayer(int currentPlayer) {
		if(currentPlayer>=0 && currentPlayer<getGame().getNumberOfPlayers()) this.currentPlayer = currentPlayer;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
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
	 * This {@link BorderPane} manages selection of players.
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
			if(this.count>=IRulesConst.MAX_PLAYER) {
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
								GameController.this.getGame().addPlayer(txtP.getText());
							} catch (IdenticalPseudoException e) {
								MsgBox.dispalyOk(e.getClass().toString(), e.getMessage());
								GameController.this.getGame().removeAllPlayers();
								return;
							}
						}
						GameController.this.setCurrentPlayer(0);
						GameController.this.getChildren().add(getThemeSelection());
						GameController.this.showElement(getThemeSelection());
					}
				});
			}
			return btnPlay;
		}
	}
	
	
	
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
				lblPlayer = new Label(GameController.this.getGame().getPlayer(GameController.this.getCurrentPlayer()) + 
						" , SELECT A THEME ");
				lblPlayer.setId("lblPlayerTheme");
			}
			return lblPlayer;
		}
		public List<Button> getlBtnTheme() {
			if(lBtnTheme==null) {
				lBtnTheme = new ArrayList<>();
				GameController.this.getGame().updateAllDecks(
						GameController.this.getGame().randomChoice(
								GameController.this.getGame().getNumberOfPlayers()));
				
				for(int i=0;i<=GameController.this.getGame().getNumberOfPlayers();i++) {
					Deck d = GameController.this.getGame().getDeck(i);
					Button b = new Button(d.getTheme());
					if(i==GameController.this.getGame().getNumberOfPlayers())
						b.setText(txtMysteryTheme);
					if(GameController.this.getGame().hasBeenUsed(d))
						b.setDisable(true);
					b.setMinWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
					b.setPrefWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
					b.setPrefWidth(IGraphicConst.HEIGHT_BUTTON);
					b.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(b.getText().equals(txtMysteryTheme)) {
								GameController.this.getGame().addUsedDeck(getGame().getDeck(getGame().getNumberOfPlayers()));
							}
							else {
								GameController.this.getGame().addUsedDeck(b.getText());
							}
							GameController.this.getChildren().add(getGamePane());
							GameController.this.showElement(getGamePane());
						}
					});
					lBtnTheme.add(b);
				}
			}
			return lBtnTheme;
		}
		
	}
	
	
	/**
	 * The view of the game itself.
	 * @author ArRaLo
	 *
	 */
	class GamePane extends BorderPane {
		
		//Game vars
		private double timer = IRulesConst.ROUND_TIME;
		
		//GUI vars
		private Label lblPlayer;
		private ImageView ivScore;
		private Timeline timeline;
		private Label lblTimer;
		
		private ImageView ivJokerFirstLetter;
		private ImageView ivJokerExtraPass;
		private ImageView ivJokerBonusTime;
		
		private Label lblClues;
		private TextField txtAnswer;
		private Button btnPass;
		private Button btnValidate;
		
		public GamePane() {
			//TOP
			HBox hbTop = new HBox(30);
			hbTop.setAlignment(Pos.BASELINE_CENTER);
			hbTop.getChildren().addAll(getLblPlayer(), getIvScore(), getLblTimer());
			this.setTop(hbTop);
			
			//LEFT
			VBox vbLeft = new VBox(15);
			vbLeft.setAlignment(Pos.CENTER);
			vbLeft.getChildren().addAll(getIvJokerFirstLetter(), getIvJokerExtraPass(), getIvJokerBonusTime());
			this.setLeft(vbLeft);
			
			//CENTER
			VBox vbCenter = new VBox(10);
			vbCenter.setAlignment(Pos.CENTER);
			
			HBox hbCenterBottom = new HBox(15);
			hbCenterBottom.setAlignment(Pos.CENTER);
			hbCenterBottom.getChildren().addAll(getBtnPass(), getBtnValidate());
			

			vbCenter.getChildren().addAll(getLblClues(), getTxtAnswer(), hbCenterBottom);
			this.setCenter(vbCenter);
		}
		
		public Label getLblPlayer() {
			if(lblPlayer==null) {
				lblPlayer = new Label();
				lblPlayer.setText(GameController.this.getGame().getPlayer(currentPlayer).getPseudo());
			}
			return lblPlayer;
		}
		public ImageView getIvScore() {
			if(ivScore==null) {
				ivScore = new ImageView("file:./src/resources/speedometer/start.png");
				ivScore.setFitHeight(100);
				ivScore.setFitWidth(100);
			}
			return ivScore;
		}
		public Timeline getTimeline() {
			if(timeline==null) {
				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
				//TODO
			}
			return timeline;
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
				ivJokerFirstLetter.setOnMouseClicked(e -> System.out.println("ivJokerFirstLetter"));
			}
			return ivJokerFirstLetter;
		}
		public ImageView getIvJokerExtraPass() {
			if(ivJokerExtraPass==null) {
				ivJokerExtraPass = new ImageView("file:./src/resources/images/logo.png");
				Tooltip.install(ivJokerExtraPass, new Tooltip("Pass for free !"));
				ivJokerExtraPass.setOnMouseClicked(e -> System.out.println("ivJokerExtraPass"));
			}
			return ivJokerExtraPass;
		}
		public ImageView getIvJokerBonusTime() {
			if(ivJokerBonusTime==null) {
				ivJokerBonusTime = new ImageView("file:./src/resources/images/logo.png");
				Tooltip.install(ivJokerBonusTime, new Tooltip("More time !"));
				ivJokerBonusTime.setOnMouseClicked(e -> System.out.println("ivJokerBonusTime"));
			}
			return ivJokerBonusTime;
		}
		public Label getLblClues() {
			if(lblClues==null) {
				lblClues = new Label();
				lblClues.setText("CLUES"); 
				lblClues.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL, IGraphicConst.HEIGHT_LARGE_LBL);
				//TODO
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
				btnPass.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						event.consume();
					}
				});
			}
			return btnPass;
		}
		public Button getBtnValidate() {
			if(btnValidate==null) {
				btnValidate = new Button("VALIDATE");
				btnValidate.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println(game.getLastUsedDeck());
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
			//TODO add different logos retive to rank
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
			for(int i=0; i<=getGame().getNumberOfPlayers(); i++) {
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