package view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Highscore extends BorderPane{

	private List<HBox>lhbox;
	private int i;
	private Label lblname;
	private Label lblscore;
	private Label lbltime;
	
//	public Highscore() {
//		
//		VBox vb = new VBox();
//		addHbox(lhbox);
//		vb.getChildren().addAll(lhbox);
//		this.setCenter(vb);	
//	}
	
	//création d'une liste de 5 hbox que l'on va insérer dans une vbox
	public List<HBox> getLhbox(){
		if(lhbox == null) {
			lhbox = new ArrayList<HBox>();
		}
		return lhbox;
	}

	public void addHbox(List<HBox> l) {
		for(i=0;i<5;i++) {
			HBox hb = new HBox();
			//il faut faire en sorte que les get renvoient les infomations sur 1 joueur spécifique
			
			hb.getChildren().addAll(getLblname(),getLblscore(),getLbltime());
			l.add(hb);
		}
	}
	
	public Label getLblname() {
		if(lblname == null) {
			lblname = new Label("ok");
		}
		return lblname;
	}

	public Label getLblscore() {
		if(lblscore == null) {
			lblscore = new Label("score");
		}
		return lblscore;
	}

	public Label getLbltime() {
		if(lbltime == null) {
			lbltime = new Label("time");
		}
		return lbltime;
	}
	
}
