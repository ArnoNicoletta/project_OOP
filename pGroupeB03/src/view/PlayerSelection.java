package view;


import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.IRulesConst;

/**
 * This {@link BorderPane} manages selection of players.
 * @author ArRaLo
 * 
 */
public class PlayerSelection extends BorderPane{
	
	private int count = 0;
	
	private GridPane gpCenter;
	
	private List<Label> lLblPlayer;
	private List<TextField> lTxtPlayer;
	private List<ImageView> lIvPlayer;
	
	private Button btnPlay;
	
	public PlayerSelection() {
		
		addPlayer();
		//CENTER
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
		getlLblPlayer().add(new Label("Player " + (this.count+1)));
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
		ImageView iv = new ImageView("file:./src/resources/images/logo.png");
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
		ImageView ivDel = new ImageView("file:./src/resources/images/Icon_25px.png");
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
			btnPlay = new Button("Play");
		}
		return btnPlay;
	}
}