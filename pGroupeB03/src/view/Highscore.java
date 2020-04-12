package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;


public class Highscore extends GridPane{
	private Label lblRank;
	private Label lblPseudo;
	private Label lblScore;
	private Label lblTime;
			
	public Highscore() {
		
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background_scores.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER, 
				new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
		
		//Setup positioning
		this.setAlignment(Pos.BOTTOM_CENTER);
		this.setHgap(100);
		this.setVgap(25);
		
		//Titles
		this.add(getLblRank(), 0, 0);
		this.add(getLblPseudo(), 1, 0);
		this.add(getLblScore(), 2, 0);
		this.add(getLblTime(), 3, 0);

		
		//Content
		for(int i=1;i<=5;i++) {
			this.addPlayer(i);
		}
		
	}
	
	private void addPlayer(int rank) {
		ImageView ivPlayerRank = new ImageView(IGraphicConst.URL_PATH_IMG + "rank/rank_" + (rank) + ".png");
		Label lblPlayerPseudo = IGraphicConst.styleLabel(new Label("joueur" + rank));
		Label lblPlayerScore = IGraphicConst.styleLabel(new Label("sonScore"));
		Label lblPlayerTime = IGraphicConst.styleLabel(new Label("sonTemps"));
		ivPlayerRank.getStyleClass().add("positionRank");
		lblPlayerPseudo.getStyleClass().addAll("positionPseudo", "lblRanking");
		lblPlayerScore.getStyleClass().addAll("positionScore", "lblRanking");
		lblPlayerTime.getStyleClass().addAll("positionTime", "lblRanking");
		
		this.add(ivPlayerRank, 0, rank);
		this.add(lblPlayerPseudo, 1, rank);
		this.add(lblPlayerScore, 2, rank);
		this.add(lblPlayerTime, 3, rank);
		//idem pour les 4 suivants
	}
	
	public Label getLblRank() {
		if(lblRank==null) {
			lblRank = new Label("RANK");
			IGraphicConst.styleLabel(lblRank);
			lblRank.getStyleClass().add("positionRank");
			lblRank.getStyleClass().add("titleRanking");
		}
		return lblRank;
	}
	public Label getLblPseudo() {
		if(lblPseudo==null) {
			lblPseudo = new Label("PSEUDO");
			IGraphicConst.styleLabel(lblPseudo);
			lblPseudo.getStyleClass().add("positionPseudo");
			lblPseudo.getStyleClass().add("titleRanking");
		}
		return lblPseudo;
	}
	public Label getLblScore() {
		if(lblScore==null) {
			lblScore = new Label("SCORE");
			IGraphicConst.styleLabel(lblScore);
			lblScore.getStyleClass().add("positionScore");
			lblScore.getStyleClass().add("titleRanking");
		}
		return lblScore;
	}
	public Label getLblTime() {
		if(lblTime==null) {
			lblTime = new Label("TIME");
			IGraphicConst.styleLabel(lblTime);
			lblTime.getStyleClass().add("positionTime");
			lblTime.getStyleClass().add("titleRanking");
		}
		return lblTime;
	}
}
