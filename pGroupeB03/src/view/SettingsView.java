package view;

import exception.WrongRuleValueException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.RulesSettings;

public class SettingsView extends StackPane {
	
	
	public SettingsView() {
		this.showElement(new MenuSettings());
	}
	
	private void showElement(Node element) {
		this.getChildren().clear();
		this.getChildren().add(element);
		element.setVisible(true);
	}
	
	
	/*
	 * *****************************
	 * 		  INNER CLASSES
	 * *****************************
	 */
	class MenuSettings extends GridPane{
		private Button btnUser;
		private Button btnAdmin;
		
		public MenuSettings() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			this.setPadding(new Insets(10));
			this.setAlignment(Pos.CENTER);
			this.setHgap(5);
			this.setVgap(5);
			
			GridPane.setHalignment(getBtnUser(), HPos.CENTER);
			this.add(getBtnUser(), 0, 0);
			GridPane.setHalignment(getBtnAdmin(), HPos.CENTER);
			this.add(getBtnAdmin(), 0, 1);
		}

		public Button getBtnUser() {
			if(btnUser == null) {
				btnUser = new Button("USER SETTINGS");
				
				btnUser.minWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnUser.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						showElement(new UserSettings());
					}
				});
			}
			return btnUser;
		}

		public Button getBtnAdmin() {
			if(btnAdmin == null) {
				btnAdmin = new Button("ADMIN SETTINGS");
				
				btnAdmin.minWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnAdmin.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						showElement(new AdminSettingsView());
					}
				});
			}
			return btnAdmin;
		}
		
	}
	class UserSettings extends GridPane{
		
		private Label lblNbRound;
		private TextField txtNbRound;
		private Label lblTime;
		private TextField txtTime;
		private CheckBox cbJoker;
		private CheckBox cbSound;
		private Button btnSave;
		
		public UserSettings() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			this.setAlignment(Pos.CENTER);
			this.setHgap(5);
			this.setVgap(5);
			
			GridPane.setHalignment(getLblNbRound(),HPos.LEFT);
			this.add(getLblNbRound(), 0, 0);
			
			GridPane.setHalignment(getTxtNbRound(),HPos.CENTER);
			this.add(getTxtNbRound(), 1, 0);
			
			GridPane.setHalignment(getLblTime(),HPos.LEFT);
			this.add(getLblTime(), 0, 1);
			
			GridPane.setHalignment(getTxtTime(),HPos.CENTER);
			this.add(getTxtTime(), 1, 1);
			
			GridPane.setHalignment(getCbJoker(), HPos.CENTER);
			this.add(getCbJoker(), 0, 2);
			
			GridPane.setHalignment(getCbSound(), HPos.CENTER);
			this.add(getCbSound(), 1, 2);
			
			GridPane.setHalignment(getBtnSave(),HPos.RIGHT);
			this.add(getBtnSave(), 2, 3);
			
		}

		public Label getLblNbRound() {
			if( lblNbRound == null) {
				lblNbRound = new Label("NUMBER OF ROUNDS :");
			}
			return lblNbRound;
		}

		public TextField getTxtNbRound() {
			if(txtNbRound == null) {
				txtNbRound = new TextField(""+RulesSettings.getNumber_round());
//				txtNbRound.setTooltip(new Tooltip("Insert a value between 1 and 5"));
			}
			return txtNbRound;
		}

		public Label getLblTime() {
			if(lblTime == null) {
				lblTime = new Label("ROUND TIME (second) :");
			}
			return lblTime;
		}

		public TextField getTxtTime() {
			if( txtTime == null) {
				txtTime = new TextField(""+RulesSettings.getRound_time_seconds());
//				txtTime.setTooltip(new Tooltip("Insert a value between 15 and 90"));
			}
			return txtTime;
		}
		
		public CheckBox getCbJoker() {
			if(cbJoker==null) {
				cbJoker = new CheckBox("Jokers with special faces");
				cbJoker.setSelected(RulesSettings.getFaced_joker());
			}
			return cbJoker;
		}
		
		public CheckBox getCbSound() {
			if(cbSound==null) {
				cbSound = new CheckBox("Sound");
				cbSound.setSelected(RulesSettings.getSound_enabled());
			}
			return cbSound;
		}
		
		public Button getBtnSave() {
			if( btnSave== null) {
				btnSave = new Button("SAVE");
				btnSave.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							RulesSettings.setNumber_round(Integer.parseInt(getTxtNbRound().getText()));
							RulesSettings.setRound_time_seconds(Double.parseDouble(getTxtTime().getText()));
						} catch (NumberFormatException e) {
							MsgBox.dispalyOk("Wrong input number", "This is not a number ! "
									+ "\n" + e.getMessage());
						} catch (WrongRuleValueException e) {
							MsgBox.dispalyException(e);
						}
						if(getCbJoker().isSelected()) RulesSettings.setFaced_joker(true);
						else RulesSettings.setFaced_joker(false);
						if(getCbSound().isSelected()) RulesSettings.setSound_enabled(true);
						else RulesSettings.setSound_enabled(false);
						MsgBox.dispalyOk("Saved", "Changes have been saved !");
					}
				});
			}
			return btnSave;
		}
		
		
		
	}
	
}
