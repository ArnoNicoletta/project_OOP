package view;

import java.util.Locale;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Game;
import model.Player;


public class HighscoreView extends GridPane{
	private Label lblRank;
	private Label lblPseudo;
	private Label lblScore;
	private Label lblTime;
			
	public HighscoreView() {
		
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background_scores.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
		
		//Setup positioning
		this.setAlignment(Pos.CENTER);
		this.setHgap(100);
		this.setVgap(20);
		
		//Titles
		this.add(getLblRank(), 0, 0);
		this.add(getLblPseudo(), 1, 0);
		this.add(getLblScore(), 2, 0);
		this.add(getLblTime(), 3, 0);

		
		//Content
		this.addPlayers();
	}
	
	private void addPlayer(int rank, Player p) {
		
		Label lbl = new Label();
		lbl.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON - IGraphicConst.WIDTH_RANK, IGraphicConst.HEIGHT_BUTTON);
		lbl.setBackground(new Background(new BackgroundFill(Color.web("#793F54"), new CornerRadii(20, false) , null)));
		lbl.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
		
		this.add(lbl, 1, rank+1, 3, 1);
		
		ImageView ivPlayerRank = new ImageView(IGraphicConst.URL_PATH_IMG + "rank/rank_" + (rank+1) + ".png");
		ivPlayerRank.setFitWidth(IGraphicConst.WIDTH_RANK*0.9);
		ivPlayerRank.setFitHeight(IGraphicConst.HEIGHT_RANK*0.9);
		Label lblPlayerPseudo = IGraphicConst.styleLabel(new Label("  " + p.getPseudo()));
		Label lblPlayerScore = IGraphicConst.styleLabel(new Label(String.format(Locale.US, "%1d", p.getScore())));
		Label lblPlayerTime = IGraphicConst.styleLabel(new Label(String.format(Locale.US, "%-2.2f", p.getTime())));
		
		this.add(ivPlayerRank, 0, rank+1);
		this.add(lblPlayerPseudo, 1, rank+1);
		this.add(lblPlayerScore, 2, rank+1);
		this.add(lblPlayerTime, 3, rank+1);
	}
	
	private void addPlayers() {
		int i = 0;
		for(Player p : Game.getInstance().getHighscores()) {
			this.addPlayer(i++, p);
		}
	}
	
	public Label getLblRank() {
		if(lblRank==null) {
			lblRank = new Label("RANK");
			IGraphicConst.styleLabel(lblRank);
		}
		return lblRank;
	}
	public Label getLblPseudo() {
		if(lblPseudo==null) {
			lblPseudo = new Label("PSEUDO");
			IGraphicConst.styleLabel(lblPseudo);
		}
		return lblPseudo;
	}
	public Label getLblScore() {
		if(lblScore==null) {
			lblScore = new Label("SCORE");
			IGraphicConst.styleLabel(lblScore);
		}
		return lblScore;
	}
	public Label getLblTime() {
		if(lblTime==null) {
			lblTime = new Label("TIME");
			IGraphicConst.styleLabel(lblTime);
		}
		return lblTime;
	}
}
