package view;

import exception.WrongRuleValueException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.layout.StackPane;
import model.RulesSettings;
import view.AdminSettingsView.AdminMenu;

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
	class MenuSettings extends BorderPane {
		
		private ImageView ivUser;
		private Button btnUser;
		private ImageView ivAdmin;
		private Button btnAdmin;
		
		public MenuSettings() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			GridPane gp = new GridPane();
			
			gp.setPadding(new Insets(10));
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(5);
			gp.setVgap(0);
			gp.setTranslateY(25);
			
			GridPane.setHalignment(getIvUser(), HPos.LEFT);
			gp.add(getIvUser(), 0, 0);
			GridPane.setHalignment(getBtnUser(), HPos.CENTER);
			gp.add(getBtnUser(), 0, 1);
			
			gp.add(new Label(), 0, 2);
			
			GridPane.setHalignment(getIvAdmin(), HPos.RIGHT);
			gp.add(getIvAdmin(), 0, 3);
			GridPane.setHalignment(getBtnAdmin(), HPos.CENTER);
			gp.add(getBtnAdmin(), 0, 4);
			
			this.setCenter(gp);
		}
		
		public ImageView getIvUser() {
			if(ivUser == null) {
				ivUser = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/icon_user.png");
				ivUser.setFitWidth(100);
				ivUser.setFitHeight(100);
			}
			return ivUser;
		}
		public Button getBtnUser() {
			if(btnUser == null) {
				btnUser = new Button("USER SETTINGS");
				btnUser.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnUser);
				btnUser.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						showElement(new UserSettings());
					}
				});
			}
			return btnUser;
		}
		public ImageView getIvAdmin() {
			if(ivAdmin == null) {
				ivAdmin = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/icon_admin.png");
				ivAdmin.setFitWidth(100);
				ivAdmin.setFitHeight(100);
			}
			return ivAdmin;
		}
		public Button getBtnAdmin() {
			if(btnAdmin == null) {
				btnAdmin = new Button("ADMIN SETTINGS");
				btnAdmin.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnAdmin);
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
	class UserSettings extends BorderPane {
		
		private Label lblNbRound;
		private TextField txtNbRound;
		private Label lblTime;
		private TextField txtTime;
		private CheckBox cbJoker;
		private CheckBox cbSound;
		private Button btnSave;
		private ImageView ivBack;
		
		public UserSettings() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			GridPane gp = new GridPane();
			
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(5);
			gp.setVgap(15);
			gp.setTranslateY(50);
			
			GridPane.setHalignment(getLblNbRound(),HPos.LEFT);
			gp.add(getLblNbRound(), 0, 0);
			
			GridPane.setHalignment(getTxtNbRound(),HPos.CENTER);
			gp.add(getTxtNbRound(), 1, 0);
			
			GridPane.setHalignment(getLblTime(),HPos.LEFT);
			gp.add(getLblTime(), 0, 1);
			
			GridPane.setHalignment(getTxtTime(),HPos.CENTER);
			gp.add(getTxtTime(), 1, 1);
			
			GridPane.setHalignment(getCbJoker(), HPos.CENTER);
			gp.add(getCbJoker(), 0, 2);
			
			GridPane.setHalignment(getCbSound(), HPos.CENTER);
			gp.add(getCbSound(), 1, 2);
			
			gp.add(new Label(), 0, 3);
			
			GridPane.setHalignment(getIvBack(), HPos.LEFT);
			gp.add(getIvBack(), 0, 4);
			
			GridPane.setHalignment(getBtnSave(),HPos.RIGHT);
			gp.add(getBtnSave(), 1, 4);
			
			this.setCenter(gp);
		}

		public Label getLblNbRound() {
			if( lblNbRound == null) {
				lblNbRound = new Label("NUMBER OF ROUNDS :");
				IGraphicConst.styleLabel(lblNbRound);
			}
			return lblNbRound;
		}

		public TextField getTxtNbRound() {
			if(txtNbRound == null) {
				txtNbRound = new TextField(""+RulesSettings.getNumber_round());
				txtNbRound.setTooltip(new Tooltip("Insert a value between 1 and 5"));
				IGraphicConst.styleTextField(txtNbRound);
				txtNbRound.setAlignment(Pos.BASELINE_RIGHT);
			}
			return txtNbRound;
		}

		public Label getLblTime() {
			if(lblTime == null) {
				lblTime = new Label("ROUND TIME (second) :");
				IGraphicConst.styleLabel(lblTime);
			}
			return lblTime;
		}

		public TextField getTxtTime() {
			if( txtTime == null) {
				txtTime = new TextField(""+RulesSettings.getRound_time_seconds());
				txtTime.setTooltip(new Tooltip("Insert a value between 15 and 90"));
				IGraphicConst.styleTextField(txtTime);
				txtTime.setAlignment(Pos.BASELINE_RIGHT);
			}
			return txtTime;
		}
		
		public CheckBox getCbJoker() {
			if(cbJoker==null) {
				cbJoker = new CheckBox("Jokers with special faces");
				cbJoker.setSelected(RulesSettings.getFaced_joker());
				IGraphicConst.styleCheckBox(cbJoker);
			}
			return cbJoker;
		}
		
		public CheckBox getCbSound() {
			if(cbSound==null) {
				cbSound = new CheckBox("Sound");
				cbSound.setSelected(RulesSettings.getSound_enabled());
				IGraphicConst.styleCheckBox(cbSound);
			}
			return cbSound;
		}
		
		public Button getBtnSave() {
			if( btnSave== null) {
				btnSave = new Button("SAVE");
				IGraphicConst.styleButton(btnSave);
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
		
		public ImageView getIvBack() {
			if(ivBack == null) {
				ivBack = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_back.png");
				IGraphicConst.styleImageView(ivBack);
				ivBack.setOnMouseClicked(e -> showElement(new MenuSettings()));
			}
			return ivBack;
		}
		
	}
	
}
