package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	private List<Player> players;
	private List<Deck> decks;
	private Deck selectedDeck;
	private int currentPlayer;
	
	//Pane vars
	private PlayerSelection playerSelection;
	private ThemeSelection themeSelection;
	private GamePane gamePane;
	private Ranking ranking;
	
	public GameController() {
		getDecks();
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
	public List<Player> getPlayers() {
		List<Player> ret = new ArrayList<>();
		for(Player p : players) {
			ret.add(p.clone());
		}
		return ret;
	}
	public void setPlayers(List<Player> players) {
		this.players = new ArrayList<Player>();
		for(Player p : players) {
			this.players.add(p);
		}
	}
	public List<Deck> getDecks() {
		if(decks==null) {
			decks = new ArrayList<>();
			for(File f : new File("./src/resources/questions").listFiles()) {
				decks.add(Deck.fromJson(f));
			}
		}
		List<Deck> ret = new ArrayList<>();
		for(Deck d : decks) {
			ret.add(d);
		}
		return ret;
	}
	public void setSelectedDeck(Deck selectedDeck) {
		this.selectedDeck = selectedDeck;
	}
	public Deck getSelectedDeck() {
		return selectedDeck.clone();
	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
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
						HashSet<Player> tmp = new HashSet<>();
						for(TextField p : getlTxtPlayer()) {
							if(!tmp.add(new Player(p.getText()))){
								Exception e = new IdenticalPseudoException(p.getText());
								MsgBox.dispalyOk("Error", e.getMessage());
								return;
							}
						}
						GameController.this.setPlayers(new ArrayList<>(tmp));
						GameController.this.getChildren().add(GameController.this.getThemeSelection());
						GameController.this.showElement(getThemeSelection());
					}
				});
			}
			return btnPlay;
		}
	}
	
	
	
	class ThemeSelection extends BorderPane{
		
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
				lblPlayer = new Label(GameController.this.getPlayers().get(GameController.this.getCurrentPlayer()) + 
						" , SELECT A THEME ");
			}
			return lblPlayer;
		}
		public List<Button> getlBtnTheme() {
			if(lBtnTheme==null) {
				lBtnTheme = new ArrayList<>();
				for(int i=1;i<=getPlayers().size();i++) {
					//Random rand = new Random();
					Button b = new Button(GameController.this.getDecks().
							get(i).getTheme());
					b.setPrefWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
					b.setPrefWidth(IGraphicConst.HEIGHT_BUTTON);
					b.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							//GameController.this.setSelectedDeck(GameController.this.getDecks()
									//.get());
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
		
		private Label lblPlayer;
		private ImageView ivScore;
		private Timeline timeline;
		private double timer = IRulesConst.ROUND_TIME;
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
			vbLeft.setAlignment(Pos.CENTER_LEFT);
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
				lblPlayer.setText(GameController.this.getPlayers().get(GameController.this.getCurrentPlayer()).getPseudo());
			}
			return lblPlayer;
		}
		public ImageView getIvScore() {
			if(ivScore==null) {
				ivScore = new ImageView("file:./src/resources/speedometer/start.png");
				//TODO
			}
			return ivScore;
		}
		public Timeline getTimeline() {
			if(timeline==null) {
				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
			}
			return timeline;
		}
		public Label getLblTimer() {
			if(lblTimer==null) {
				lblTimer = new Label();
				lblTimer.setText(""+timer);
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
				lblClues.setText("CLUES"); //TODO
				lblClues.setPrefSize(IGraphicConst.WIDTH_LARGE_LBL, IGraphicConst.HEIGHT_LARGE_LBL);
			}
			return lblClues;
		}
		public TextField getTxtAnswer() {
			if(txtAnswer==null) {
				txtAnswer = new TextField();
				txtAnswer.setPrefWidth(IGraphicConst.WIDTH_LARGE_LBL);
			}
			return txtAnswer;
		}
		public Button getBtnPass() {
			if(btnPass==null) {
				btnPass = new Button("PASS");
				btnPass.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						System.out.println("btnPass");
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
						System.out.println("btnValidate");
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
			this.addPlayer(1);
		}
		
		private void addPlayer(int rank) {
			ImageView ivPlayerRank = new ImageView("file:./src/resources/images/logo_first.png");
			Label lblPlayerPseudo = new Label("ARNO");
			Label lblPlayerScore = new Label("4");
			Label lblPlayerTime = new Label("23:45");
			ivPlayerRank.getStyleClass().add("positionRank");
			lblPlayerPseudo.getStyleClass().addAll("positionPseudo", "lblRanking");
			lblPlayerScore.getStyleClass().addAll("positionScore", "lblRanking");
			lblPlayerTime.getStyleClass().addAll("positionTime", "lblRanking");
			
			this.add(ivPlayerRank, 0, rank);
			this.add(lblPlayerPseudo, 1, rank);
			this.add(lblPlayerScore, 2, rank);
			this.add(lblPlayerTime, 3, rank);
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