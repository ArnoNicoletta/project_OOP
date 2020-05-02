package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import model.RulesSettings;

public class RulesView extends BorderPane {

	private StackPane stack;
	private Button btnNext;
	private BorderPane bp1;
	private BorderPane bp2;
	private BorderPane bp3;
	
	public RulesView() {
		
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background_mainmenu.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER,  
				new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
		//CENTER
		showElement(getBp1());
		this.setCenter(getStack());
		//Bottom
		HBox hbBottom = new HBox(getBtnNext());
		hbBottom.setAlignment(Pos.BASELINE_RIGHT);
		this.setBottom(hbBottom);
	}
	
	private void showElement(Node elemet) {
		getStack().getChildren().clear();
		getStack().getChildren().add(elemet);
	}
	public StackPane getStack() {
		if(stack==null) {
			stack = new StackPane();
		}
		return stack;
	}
	public Button getBtnNext() {
		if(btnNext==null) {
			btnNext = new Button("NEXT");
			IGraphicConst.styleButton(btnNext);
			btnNext.setTranslateX(-20);
			btnNext.setTranslateY(-20);
		}
		return btnNext;
	}
	
	// Different panes included in the stack
	
	
	public BorderPane getBp1() {
		if(bp1==null) {
			
			//Set up label
			Label lblRules = new Label();
			IGraphicConst.styleBiggerLabel(lblRules);
			lblRules.setPrefSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND);
			lblRules.setPadding(new Insets(25));
			lblRules.setWrapText(true);
			lblRules.setText("The goal of the game is to make the highest score in a minimum time. Up to " + RulesSettings.getMax_player() + " players can play.\r\n" + 
					"\r\n" + 
					"For each game, up to " + RulesSettings.getMax_player() + " themes are offered to players including a mystery theme.\r\n" + 
					"Each in turn, they choose one they want to be asked about.");
			
			//Adding to the pane
			bp1 = new BorderPane(lblRules);
			bp1.setTranslateY(70);
		}
		//Set up btnNext
		getBtnNext().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showElement(getBp2());
			}
		});
		return bp1;
	}
	
	
	
	public BorderPane getBp2() {
		if(bp2==null) {
			
			//Set up label
			Label lblRules = new Label();
			IGraphicConst.styleBiggerLabel(lblRules);
			lblRules.setPrefSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND);
			lblRules.setPadding(new Insets(25));
			lblRules.setWrapText(true);
			lblRules.setText("They will have "+ RulesSettings.getRound_time_seconds() +" seconds to answer a maximum of questions. Each correct answer earns 1 point.\r\n" + 
					"If they make a mistake or pass, the score drops to 0.\r\n" + 
					"Once at "+ RulesSettings.getRound_time_seconds() +" seconds, the result is saved. The score ranges from 0 to "+ RulesSettings.getMax_score() + " and represents the number of correct answers in a row.\r\n" + 
					"If candidates succeed in making "+ RulesSettings.getMax_score() + " consecutive responses, they stop responding and their score is saved.");
			
			//Adding to the pane
			bp2 = new BorderPane(lblRules);
			bp2.setTranslateY(70);
		}
		//Set up btnNext
		getBtnNext().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showElement(getBp3());
			}
		});
		return bp2;
	}
	
	
	
	public BorderPane getBp3() {
		if(bp3==null) {
			
			GridPane gp = new GridPane();
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(20);
			gp.setVgap(10);
			
			// Joker 1
			// ImageView
			ImageView ivJokerLetters = new ImageView();
			if(RulesSettings.getFaced_joker()) ivJokerLetters.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/arno.png"));
			else ivJokerLetters.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
			ivJokerLetters.setFitWidth(IGraphicConst.WIDTH_JOKER);
			ivJokerLetters.setFitHeight(IGraphicConst.HEIGHT_JOKER);
			Tooltip.install(ivJokerLetters, new Tooltip("Hangman !"));
			GridPane.setHalignment(ivJokerLetters, HPos.CENTER);
			//Label
			Label lblJokerLetters = new Label("Letters: Give half the letters of the answer.");
			IGraphicConst.styleLabel(lblJokerLetters);
			lblJokerLetters.setWrapText(true);
			GridPane.setHalignment(lblJokerLetters, HPos.LEFT);
			//Adding to the GridPane
			gp.add(ivJokerLetters, 0, 1);
			gp.add(lblJokerLetters, 1, 1, 2, 1);
			
			
			//Joker 2
			ImageView ivJokerExtraPass = new ImageView();
			if(RulesSettings.getFaced_joker()) ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/rayan.png"));
			else ivJokerExtraPass.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
			ivJokerExtraPass.setFitWidth(IGraphicConst.WIDTH_JOKER);
			ivJokerExtraPass.setFitHeight(IGraphicConst.HEIGHT_JOKER);
			Tooltip.install(ivJokerExtraPass, new Tooltip("Pass for free !"));
			GridPane.setHalignment(ivJokerExtraPass, HPos.CENTER);
			//Label
			Label lblJokerPass = new Label("ExtraPass: Allows to pass without falling back to 0.");
			IGraphicConst.styleLabel(lblJokerPass);
			lblJokerPass.setWrapText(true);
			GridPane.setHalignment(lblJokerPass, HPos.LEFT);
			//Adding to the GridPane
			gp.add(ivJokerExtraPass, 0, 2);
			gp.add(lblJokerPass, 1, 2, 2, 1);
			
			
			//Joker 3
			ImageView ivJokerBonusTime = new ImageView();
			if(RulesSettings.getFaced_joker()) ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/loic.png"));
			else ivJokerBonusTime.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_joker.png"));
			ivJokerBonusTime.setFitWidth(IGraphicConst.WIDTH_JOKER);
			ivJokerBonusTime.setFitHeight(IGraphicConst.HEIGHT_JOKER);
			Tooltip.install(ivJokerBonusTime, new Tooltip("More time !"));
			GridPane.setHalignment(ivJokerExtraPass, HPos.CENTER);
			//Label
			Label lblJokerTime = new Label("BonusTime: Gives "+ RulesSettings.getJoker_time() + " more seconds to respond.");
			IGraphicConst.styleLabel(lblJokerTime);
			lblJokerTime.setWrapText(true);
			GridPane.setHalignment(lblJokerTime, HPos.LEFT);
			//Adding to the GridPane
			gp.add(ivJokerBonusTime, 0, 3);
			gp.add(lblJokerTime, 1, 3, 2, 1);
			
			//Adding to the pane
			bp3 = new BorderPane(gp);
			bp3.setTranslateY(70);
		}
		//Set up btnNext
		getBtnNext().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showElement(getBp1());
			}
		});
		return bp3;
	}
}
