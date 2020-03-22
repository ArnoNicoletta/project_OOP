package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class Highscore extends GridPane{
	private Label lblRank;
	private Label lblPseudo;
	private Label lblScore;
	private Label lblTime;
			
	public Highscore() {
		
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
		for(int i=1;i<=5;i++) {
			this.addPlayer(i);
		}
		
	}
	
	private void addPlayer(int rank) {
		ImageView ivPlayerRank = new ImageView("file:./src/resources/images/logo_first.png");
		Label lblPlayerPseudo = new Label("joueur" + rank);
		Label lblPlayerScore = new Label("sonScore");
		Label lblPlayerTime = new Label("sonTemps");
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
