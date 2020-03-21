package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import exception.IdenticalPseudoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private int currentPlayer;
	
	//Pane vars
	private PlayerSelection playerSelection;
	private ThemeSelection themeSelection;
	private GamePane gamePane;
	private Ranking ranking;
	
	public GameController() {
		this.getChildren().addAll(getPlayerSelection(), getThemeSelection(), getGamePane(), getRanking());
		this.showElement(getRanking());
	}
	public void reset() {
		
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
		if(players==null) {
			players = new ArrayList<>();
		}
		List<Player> ret = new ArrayList<>();
		for(Player p : players) {
			ret.add(p.clone());
		}
		return ret;
	}
	public List<Deck> getDecks() {
		if(decks==null) {
			System.out.println("GETDECKS");
			decks = new ArrayList<>();
			for(File f : new File("./src/resources/questions").listFiles()) {
				System.out.println(f);
				decks.add(Deck.fromJson(f));
			}
			System.out.println(decks);
		}
		List<Deck> ret = new ArrayList<>();
		for(Deck d : decks) {
			ret.add(d);
		}
		return ret;
	}
	public PlayerSelection getPlayerSelection() {
		if(playerSelection==null) {
			playerSelection = new PlayerSelection();
			playerSelection.setId("playerSelection");
			playerSelection.setTranslateY(-30);
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
				btnPlay.setTranslateX(-220);
				btnPlay.setTranslateY(-45);
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
						GameController.this.getPlayers().addAll(tmp);
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
				lblPlayer = new Label("********, SELECT A THEME ");
			}
			return lblPlayer;
		}
		public List<Button> getlBtnTheme() {
			if(lBtnTheme==null) {
				lBtnTheme = new ArrayList<>();
				for(int i=1;i<=getPlayers().size();i++) {
					Random rand = new Random();
					Button b = new Button(GameController.this.getDecks().
							get(rand.nextInt(GameController.this.getDecks().size())).getTheme());
					b.setOnAction(e -> GameController.this.showElement(getGamePane()));
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
		private Label lblTimer;
		
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
		
		private List<HBox> hboxes;
		
		public Ranking() {
			
			//Setup positioning
			this.setAlignment(Pos.CENTER);
			this.setHgap(10);
			this.setVgap(15);
			//Setup elements
			this.add(getLblRank(), 0, 0);
			this.add(getLblPseudo(), 1, 0);
			this.add(getLblScore(), 2, 0);
			this.add(getLblTime(), 3, 0);
		}
		
		public Label getLblRank() {
			if(lblRank==null) {
				lblRank = new Label("Rank");
			}
			return lblRank;
		}
		public Label getLblPseudo() {
			if(lblPseudo==null) {
				lblPseudo = new Label("Pseudo");
			}
			return lblPseudo;
		}
		public Label getLblScore() {
			if(lblScore==null) {
				lblScore = new Label("Score");
			}
			return lblScore;
		}
		public Label getLblTime() {
			if(lblTime==null) {
				lblTime = new Label("Time");
			}
			return lblTime;
		}
		public List<HBox> getHboxes() {
			if(hboxes==null) {
				
			}
			return hboxes;
		}
	}
}