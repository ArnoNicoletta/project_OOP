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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.RulesConst;

public class Settings extends StackPane {
	private MenuSettings menuSettings;
	private UserSettings userSettings;
	private AdminSettings adminSettings;
	
	
	public Settings() {
		this.getChildren().addAll(getMenuSettings(),getUserSettings(), getAdminSettings());
		this.showElement(getMenuSettings());
	}
	
	public MenuSettings getMenuSettings() {
		if(menuSettings == null) {
			menuSettings = new MenuSettings();
			menuSettings.setId("menuSettingsChoice");
		}
		return menuSettings;
	}
	public UserSettings getUserSettings() {
		if(userSettings == null) {
			userSettings = new UserSettings();
			userSettings.setId("userSettings");
		}
		return userSettings;
	}
	public AdminSettings getAdminSettings() {
		if(adminSettings == null) {
			adminSettings = new AdminSettings();
		}
		return adminSettings;
	}
	public void hideVisible() {
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
	
	/*
	 * *****************************
	 * 		  INNER CLASSES
	 * *****************************
	 */
	class MenuSettings extends GridPane{
		private Button btnUser;
		private Button btnAdmin;
		
		public MenuSettings() {
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
				btnUser.setId("btnUserSettingChoice");
				
				btnUser.minWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnUser.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						hideVisible();
						getUserSettings().setVisible(true);
					}
				});
			}
			return btnUser;
		}

		public Button getBtnAdmin() {
			if(btnAdmin == null) {
				btnAdmin = new Button("ADMIN SETTINGS");
				btnAdmin.setId("btnAdminSettingChoice");
				
				btnAdmin.minWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnAdmin.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						hideVisible();
						Settings.this.showElement(Settings.this.getAdminSettings());
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
				lblNbRound.getStyleClass().addAll("positionUserSettings", "positionTxtUserSettings");
			}
			return lblNbRound;
		}

		public TextField getTxtNbRound() {
			if(txtNbRound == null) {
				txtNbRound = new TextField(""+RulesConst.getNumber_round());
//				txtNbRound.setTooltip(new Tooltip("Insert a value between 1 and 5"));
				txtNbRound.getStyleClass().add("positionUserSettings");
			}
			return txtNbRound;
		}

		public Label getLblTime() {
			if(lblTime == null) {
				lblTime = new Label("ROUND TIME (second) :");
				lblTime.getStyleClass().addAll("positionUserSettings", "positionTxtUserSettings");
			}
			return lblTime;
		}

		public TextField getTxtTime() {
			if( txtTime == null) {
				txtTime = new TextField(""+RulesConst.getRound_time_seconds());
//				txtTime.setTooltip(new Tooltip("Insert a value between 15 and 90"));
				txtTime.getStyleClass().add("positionUserSettings");
			}
			return txtTime;
		}
		
		public CheckBox getCbJoker() {
			if(cbJoker==null) {
				cbJoker = new CheckBox("Jokers with special faces");
				cbJoker.setSelected(RulesConst.getFaced_joker());
			}
			return cbJoker;
		}
		
		public CheckBox getCbSound() {
			if(cbSound==null) {
				cbSound = new CheckBox("Sound");
				cbSound.setSelected(RulesConst.getSound_enabled());
			}
			return cbSound;
		}
		
		public Button getBtnSave() {
			if( btnSave== null) {
				btnSave = new Button("SAVE");
				btnSave.setId("btnSaveUserSettings");
				btnSave.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							RulesConst.setNumber_round(Integer.parseInt(getTxtNbRound().getText()));
							RulesConst.setRound_time_seconds(Double.parseDouble(getTxtTime().getText()));
						} catch (NumberFormatException e) {
							MsgBox.dispalyOk("Wrong input number", "This is not a number ! "
									+ "\n" + e.getMessage());
						} catch (WrongRuleValueException e) {
							MsgBox.dispalyException(e);
						}
						if(getCbJoker().isSelected()) RulesConst.setFaced_joker(true);
						else RulesConst.setFaced_joker(false);
						if(getCbSound().isSelected()) RulesConst.setSound_enabled(true);
						else RulesConst.setSound_enabled(false);
						MsgBox.dispalyOk("Saved", "Changes have been saved !");
					}
				});
			}
			return btnSave;
		}
		
		
		
	}
	
}
