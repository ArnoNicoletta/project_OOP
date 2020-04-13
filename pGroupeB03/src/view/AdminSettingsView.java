package view;

import exception.WrongLoginException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Admin;
import model.Deck;
import model.Game;

public class AdminSettingsView extends StackPane {

	private Admin admin;
	private Game g = Game.getInstance();
	
	public AdminSettingsView() {
		this.showElement(new AdminLogin());
	}
	
	private void showElement(Node element) {
		this.getChildren().removeAll(this.getChildren());
		this.getChildren().add(element);
		element.setVisible(true);
	}
	
	
	/*
	 * *****************************
	 * 		  INNER CLASSES
	 * *****************************
	 */
	
	class AdminLogin extends GridPane {
		private Label lblTitre;
		
		private Label lblLog;
		private TextField txtLog;
		
		private Label lblPass;
		private PasswordField pwfPass;
		
		private Button btnOk;
		
		public AdminLogin() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_admin_login.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			this.setPadding(new Insets(10));
			this.setAlignment(Pos.CENTER);
			this.setHgap(5);
			this.setVgap(5);
			//this.setGridLinesVisible(true);
			
			GridPane.setHalignment(getLblTitre(),HPos.CENTER);
			this.add(getLblTitre(), 0, 0, 2, 1);
			GridPane.setHalignment(getLblLog(), HPos.LEFT);
			this.add(getLblLog(), 0, 1);
			GridPane.setHalignment(getTxtLog(), HPos.CENTER);
			this.add(getTxtLog(), 1, 1);
			GridPane.setHalignment(getLblPass(), HPos.LEFT);
			this.add(getLblPass(), 0, 2);
			GridPane.setHalignment(getPwfPass(), HPos.CENTER);
			this.add(getPwfPass(), 1, 2);
			GridPane.setHalignment(getBtnOk(), HPos.RIGHT);
			this.add(getBtnOk(), 0, 3, 2, 1);
		}
		
		public Label getLblTitre() {
			if(lblTitre==null) {
				lblTitre = new Label("ADMIN LOGIN");
			}
			return lblTitre;
		}
		
		public Label getLblLog() {
			if(lblLog==null) {
				lblLog = new Label("USER NAME : ");
			}
			return lblLog;
		}
		public TextField getTxtLog() {
			if(txtLog==null) {
				txtLog = new TextField();
				txtLog.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER) {
							getPwfPass().requestFocus();
						}
					}
				});
			}
			return txtLog;
		}
		public Label getLblPass() {
			if(lblPass==null) {
				lblPass = new Label("PASSWORD : ");
			}
			return lblPass;
		}
		public PasswordField getPwfPass() {
			if(pwfPass==null) {
				pwfPass = new PasswordField();
				pwfPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER) {
							getBtnOk().fire();
						}
					}
				});
			}
			return pwfPass;
		}
		public Button getBtnOk() {
			if(btnOk==null) {
				btnOk = new Button("LOGIN");
				btnOk.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							admin = new Admin(getTxtLog().getText(), getPwfPass().getText());
						} catch (WrongLoginException e) {
							MsgBox.dispalyException(e);
							getTxtLog().clear();
							getPwfPass().clear();
							getTxtLog().requestFocus();
							return;
						}
						showElement(new AdminMenu());
					}
				});
			}
			return btnOk;
		}
	}

	class AdminMenu extends BorderPane{
		private Button btnAdd;
		private Button btnDelete;
		private Button btnImport;
		
		public AdminMenu() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_user_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			VBox vb = new VBox();
			vb.getChildren().addAll(getBtnAdd(),getBtnDelete(),getBtnImport());
			vb.setSpacing(30);
			vb.setAlignment(Pos.CENTER);
			vb.setTranslateY(40);
			this.setCenter(vb);
		}
		
		public Button getBtnAdd() {
			if(btnAdd == null) {
				btnAdd = new Button("ADD QUESTIONS");
				btnAdd.setMinWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnAdd.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						showElement(new AdminAdd());
					}
				});
			}
			return btnAdd;
		}
		public Button getBtnDelete() {
			if(btnDelete == null) {
				btnDelete = new Button("DELETE QUESTIONS");
				btnDelete.setMinWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
				
				btnDelete.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						showElement(new AdminDelete());
					}
				});
			}
			return btnDelete;
		}
		public Button getBtnImport() {
			if(btnImport == null) {
				btnImport = new Button("IMPORT QUESTIONS (json format)");
				btnImport.setMinWidth(IGraphicConst.WIDTH_LARGE_BUTTON);
			}
			return btnImport;
		}
		
		
	}
	class AdminDelete extends GridPane{
		private Label lblTheme;
		private Label lblRiddle;
		
		private TextField txtTheme;
		private TextField txtRiddle;
		
		private Button btnDelete;
		
		public AdminDelete() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_user_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			this.setPadding(new Insets(10));
			this.setAlignment(Pos.CENTER);
			this.setHgap(5);
			this.setVgap(5);
			//this.setGridLinesVisible(true);
			
			GridPane.setHalignment(getLblTheme(), HPos.LEFT);
			this.add(getLblTheme(), 0, 0);
			GridPane.setHalignment(getTxtTheme(), HPos.CENTER);
			this.add(getTxtTheme(), 1, 0);
			GridPane.setHalignment(getLblRiddle(), HPos.LEFT);
			this.add(getLblRiddle(), 0, 1);
			GridPane.setHalignment(getTxtRiddle(), HPos.CENTER);
			this.add(getTxtRiddle(), 1, 1);
			GridPane.setHalignment(getBtnDelete(), HPos.RIGHT);
			this.add(getBtnDelete(), 0, 2, 2, 1);
		}

		public Label getLblTheme() {
			if(lblTheme == null) {
				lblTheme = new Label("SELECT A THEME : ");
			}
			return lblTheme;
		}

		public Label getLblRiddle() {
			if(lblRiddle == null) {
				lblRiddle = new Label("SELECT A RIDDLE : ");
			}
			return lblRiddle;
		}

		public TextField getTxtTheme() {
			if(txtTheme == null) {
				txtTheme = new TextField();
			}
			return txtTheme;
		}

		public TextField getTxtRiddle() {
			if(txtRiddle == null) {
				txtRiddle = new TextField();
			}
			return txtRiddle;
		}
		public Button getBtnDelete() {
			if(btnDelete == null) {
				btnDelete = new Button("DELETE");
			}
			return btnDelete;
		}
		
		
	}
	class AdminAdd extends BorderPane {
		
		private Label lblTheme;
		private ComboBox<String> cbbTheme;
		private Label lblClue1;
		private TextField txtClue1;
		private Label lblClue2;
		private TextField txtClue2;
		private Label lblClue3;
		private TextField txtClue3;
		private Label lblAnswer;
		private TextField txtAnswer;
		private Label lblInfo;
		private Button btnAdd;
		
		
		public AdminAdd() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_user_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			GridPane gp = new GridPane();
			
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(10);
			gp.setVgap(0);
			gp.setTranslateY(50);
			
			GridPane.setHalignment(getLblTheme(), HPos.LEFT);
			gp.add(getLblTheme(), 0, 0);
			GridPane.setHalignment(getCbbTheme(), HPos.CENTER);
			gp.add(getCbbTheme(), 1, 0);
			
			GridPane.setHalignment(getLblClue1(), HPos.LEFT);
			gp.add(getLblClue1(), 0, 1);
			GridPane.setHalignment(getTxtClue1(), HPos.CENTER);
			gp.add(getTxtClue1(), 1, 1);
			
			GridPane.setHalignment(getLblClue2(), HPos.LEFT);
			gp.add(getLblClue2(), 0, 2);
			GridPane.setHalignment(getTxtClue2(), HPos.CENTER);
			gp.add(getTxtClue2(), 1, 2);
			
			GridPane.setHalignment(getLblClue3(), HPos.LEFT);
			gp.add(getLblClue3(), 0, 3);
			GridPane.setHalignment(getTxtClue3(), HPos.CENTER);
			gp.add(getTxtClue3(), 1, 3);
			
			GridPane.setHalignment(getLblAnswer(), HPos.LEFT);
			gp.add(getLblAnswer(), 0, 4);
			GridPane.setHalignment(getTxtAnswer(), HPos.CENTER);
			gp.add(getTxtAnswer(), 1, 4);
			
			GridPane.setHalignment(getLblInfo(), HPos.CENTER);
			gp.add(getLblInfo(), 0, 5, 2, 1);
			
			GridPane.setHalignment(getBtnAdd(), HPos.RIGHT);
			gp.add(getBtnAdd(), 1, 6);
			
			this.setCenter(gp);
			
		}

		public Label getLblTheme() {
			if(lblTheme == null) {
				lblTheme = new Label("SELECT A THEME : ");
				IGraphicConst.styleLabel(lblTheme);
			}
			return lblTheme;
		}

		public ComboBox<String> getCbbTheme() {
			if(cbbTheme == null) {
				cbbTheme = new ComboBox<>();
				for(Deck d : g.getDecks()) {
					cbbTheme.getItems().add(d.getTheme());
				}
				//TODO style cbbTheme
				cbbTheme.setEditable(true);
				cbbTheme.setMinWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				cbbTheme.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				cbbTheme.setStyle(IGraphicConst.STYLE_TXT);
				cbbTheme.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10, false) , new Insets(10))));
				cbbTheme.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10,  false), null ,new Insets(10))));
			}
			return cbbTheme;
		}
		public Label getLblClue1() {
			if(lblClue1 == null) {
				lblClue1 = new Label("CLUE 1 : ");
				IGraphicConst.styleLabel(lblClue1);
			}
			return lblClue1;
		}

		public TextField getTxtClue1() {
			if(txtClue1 == null) {
				txtClue1 = new TextField();
				IGraphicConst.styleTextField(txtClue1);
				txtClue1.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				txtClue1.setMaxWidth(IGraphicConst.WIDTH_TXT_ADMIN);
			}
			return txtClue1;
		}

		public Label getLblClue2() {
			if(lblClue2 == null) {
				lblClue2 = new Label("CLUE 2 : ");
				IGraphicConst.styleLabel(lblClue2);
			}
			return lblClue2;
		}

		public TextField getTxtClue2() {
			if(txtClue2 == null) {
				txtClue2 = new TextField();
				IGraphicConst.styleTextField(txtClue2);
				txtClue2.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				txtClue2.setMaxWidth(IGraphicConst.WIDTH_TXT_ADMIN);
			}
			return txtClue2;
		}

		public Label getLblClue3() {
			if(lblClue3 == null) {
				lblClue3 = new Label("CLUE 3 : ");
				IGraphicConst.styleLabel(lblClue3);
			}
			return lblClue3;
		}

		public TextField getTxtClue3() {
			if(txtClue3 == null) {
				txtClue3 = new TextField();
				IGraphicConst.styleTextField(txtClue3);
				txtClue3.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				txtClue3.setMaxWidth(IGraphicConst.WIDTH_TXT_ADMIN);
			}
			return txtClue3;
		}
		
		public Label getLblAnswer() {
			if(lblAnswer==null) {
				lblAnswer = new Label("ANSWER :");
				IGraphicConst.styleLabel(lblAnswer);
			}
			return lblAnswer;
		}
		public TextField getTxtAnswer() {
			if(txtAnswer == null) {
				txtAnswer = new TextField();
				IGraphicConst.styleTextField(txtAnswer);
				txtAnswer.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				txtAnswer.setMaxWidth(IGraphicConst.WIDTH_TXT_ADMIN);
			}
			return txtAnswer;
		}
		public Label getLblInfo() {
			if(lblInfo == null) {
				lblInfo = new Label("It is necessary to have a minimum of 10 questions per theme for it to be playable");
				lblInfo.setStyle("-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
								"-fx-font-size: 11px;\r\n" + 
								"-fx-font-weight: bold;");
				lblInfo.setTextFill(Color.WHITE);
				lblInfo.setCache(true);
				lblInfo.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 1, 1, 0, 0));
			}
			return lblInfo;
		}

		public Button getBtnAdd() {
			if(btnAdd == null) {
				btnAdd = new Button("ADD");
				IGraphicConst.styleButton(btnAdd);
			}
			return btnAdd;
		}
		
	}
}
