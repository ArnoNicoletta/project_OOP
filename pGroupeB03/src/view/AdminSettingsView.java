package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import exception.DeckNotFoundException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongLoginException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Admin;
import model.Deck;
import model.Game;
import model.Question;

public class AdminSettingsView extends StackPane {

	private Admin admin;
	private Game g = Game.getInstance();
	
	public AdminSettingsView() {
		this.showElement(new AdminLogin());
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
	
	class AdminLogin extends BorderPane {
		private Label lblTitre;
		
		private Label lblLog;
		private TextField txtLog;
		
		private Label lblPass;
		private PasswordField pwfPass;
		
		private Button btnOk;
		
		public AdminLogin() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_adminlogin.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			GridPane gp = new GridPane();
			gp.setPadding(new Insets(10));
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(5);
			gp.setVgap(5);
			gp.setTranslateY(50);
			
			GridPane.setHalignment(getLblTitre(),HPos.CENTER);
			gp.add(getLblTitre(), 0, 0, 2, 1);
			gp.add(new Label(), 0, 1);
			GridPane.setHalignment(getLblLog(), HPos.LEFT);
			gp.add(getLblLog(), 0, 2);
			GridPane.setHalignment(getTxtLog(), HPos.CENTER);
			gp.add(getTxtLog(), 1, 2);
			GridPane.setHalignment(getLblPass(), HPos.LEFT);
			gp.add(getLblPass(), 0, 3);
			GridPane.setHalignment(getPwfPass(), HPos.CENTER);
			gp.add(getPwfPass(), 1, 3);
			GridPane.setHalignment(getBtnOk(), HPos.RIGHT);
			gp.add(getBtnOk(), 0, 4, 2, 1);
			
			this.setCenter(gp);
		}
		
		public Label getLblTitre() {
			if(lblTitre==null) {
				lblTitre = new Label("ADMIN LOGIN");
				IGraphicConst.styleBiggerLabel(lblTitre);
			}
			return lblTitre;
		}
		
		public Label getLblLog() {
			if(lblLog==null) {
				lblLog = new Label("USER NAME : ");
				IGraphicConst.styleBiggerLabel(lblLog);
			}
			return lblLog;
		}
		public TextField getTxtLog() {
			if(txtLog==null) {
				txtLog = new TextField();
				IGraphicConst.styleTextField(txtLog);
				txtLog.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER) {
							getPwfPass().requestFocus();
							event.consume();
						}
					}
				});
			}
			return txtLog;
		}
		public Label getLblPass() {
			if(lblPass==null) {
				lblPass = new Label("PASSWORD : ");
				IGraphicConst.styleBiggerLabel(lblPass);
			}
			return lblPass;
		}
		public PasswordField getPwfPass() {
			if(pwfPass==null) {
				pwfPass = new PasswordField();
				IGraphicConst.styleTextField(pwfPass);
				pwfPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER) {
							getBtnOk().fire();
							event.consume();
						}
					}
				});
			}
			return pwfPass;
		}
		public Button getBtnOk() {
			if(btnOk==null) {
				btnOk = new Button("LOGIN");
				IGraphicConst.styleButton(btnOk);
				btnOk.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							admin = new Admin(getTxtLog().getText(), getPwfPass().getText());
						} catch (WrongLoginException e) {
							MsgBox.dispalyException(e);
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
		
		private Button btnModify;
		private Button btnImport;
		
		public AdminMenu() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			VBox vb = new VBox();
			vb.getChildren().addAll(getBtnModify(),getBtnImport());
			vb.setSpacing(30);
			vb.setAlignment(Pos.CENTER);
			vb.setTranslateY(40);
			this.setCenter(vb);
		}
		
		public Button getBtnModify() {
			if(btnModify == null) {
				btnModify = new Button("ADD/REMOVE/MODIFY QUESTIONS");
				btnModify.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnModify);
				btnModify.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						showElement(new AdminModify());
					}
				});
			}
			return btnModify;
		}
		public Button getBtnImport() {
			if(btnImport == null) {
				btnImport = new Button("IMPORT QUESTIONS (json format)");
				btnImport.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnImport);
			}
			return btnImport;
		}
		
		
	}
	class AdminModify extends BorderPane {
		
		private TreeTableView<Question> table;
		private TreeItem<Question> root;
		private ContextMenu contextMenu;
		
		private ImageView ivBack;
		
		public AdminModify() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			
			//Center
			updateTable();
			this.setCenter(getTable());
			
			//Bottom
			HBox hbBottom = new HBox();
			hbBottom.setAlignment(Pos.TOP_CENTER);
			hbBottom.getChildren().addAll(getIvBack());
			this.setBottom(getIvBack());
		}
		
		private void updateTable() {
			getRoot().getChildren().clear();
			for(Deck d : g.getDecks()) {
				TreeItem<Question> di = new TreeItem<Question>(new Question("", d.getTheme(), Arrays.asList("","",""), ""));
				for(Question q : d.getQuestions()) {
					TreeItem<Question> qi = new TreeItem<Question>(q);
					di.getChildren().add(qi);
				}
				getRoot().getChildren().add(di);
			}
		}
		
		public TreeTableView<Question> getTable() {
			if(table==null) {
				table = new TreeTableView<>();
				IGraphicConst.styleTreeTableView(table);
				table.setTranslateY(80);
				
				TreeTableColumn<Question, String> theme = new TreeTableColumn<>("THEME");
				theme.setCellValueFactory(new TreeItemPropertyValueFactory<>("theme"));
				
				TreeTableColumn<Question, String> answer = new TreeTableColumn<>("ANSWER");
				answer.setCellValueFactory(new TreeItemPropertyValueFactory<>("answer"));
				
				//CLues
				TreeTableColumn<Question, String> clue1 = new TreeTableColumn<>("1st clue");
				clue1.setCellValueFactory(q -> new SimpleObjectProperty<>(q.getValue().getValue().getClues().get(0)));
				
				TreeTableColumn<Question, String> clue2 = new TreeTableColumn<>("2nd clue");
				clue2.setCellValueFactory(q -> new SimpleObjectProperty<>(q.getValue().getValue().getClues().get(1)));
				
				TreeTableColumn<Question, String> clue3 = new TreeTableColumn<>("3d clue");
				clue3.setCellValueFactory(q -> new SimpleObjectProperty<>(q.getValue().getValue().getClues().get(2)));
				
				//Adding clues to a parent columns
				TreeTableColumn<Question, String> clues = new TreeTableColumn<>("CLUES");
				clues.getColumns().addAll(clue1, clue2, clue3);
				
				TreeTableColumn<Question, String> author = new TreeTableColumn<>("AUTHOR");
				author.setCellValueFactory(new TreeItemPropertyValueFactory<>("author"));
				
				table.getColumns().addAll(theme, answer, clues, author);
				table.setShowRoot(false);
				table.setRoot(getRoot());
				table.setContextMenu(getContextMenu());
			}
			return table;
		}
		
		public TreeItem<Question> getRoot() {
			if(root == null) {
				root = new TreeItem<>();
				root.setExpanded(true);
			}
			return root;
		}
		
		public ContextMenu getContextMenu() {
			if(contextMenu == null) {
				contextMenu = new ContextMenu();
				
				MenuItem add = new MenuItem("Add");
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(getTable().getSelectionModel().getSelectedItem() == null) {
							showElement(new AdminAdd());
							return;
						}
						showElement(new AdminAdd(getTable().getSelectionModel().getSelectedItem().getValue().getTheme()));
					}
				});
				MenuItem del = new MenuItem("Delete");
				del.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(getTable().getSelectionModel().getSelectedItem() == null) {
							MsgBox.dispalyOk("No item selected", "Select an element to delete it.");
							return;
						}
						List<Question> toDel = new ArrayList<>();
						for( TreeItem<Question> tq : getTable().getSelectionModel().getSelectedItems()) {
							toDel.add(tq.getValue());
						}
						for(Question q : toDel) {
							// if it's a deck
							if(q.getAnswer().equalsIgnoreCase("")) {
								if(MsgBox.displayYesNO("Delete ?", "This deck will be deleted : "
										+"\nTheme : " + q.getTheme())) {
									try {
										admin.deleteDeck(q.getTheme());
									} catch (DeckNotFoundException e) {
										MsgBox.dispalyException(e);
									} finally {
										updateTable();
									}
								}
							}
							// if it's a question
							else{
								if(MsgBox.displayYesNO("Delete ?", "This question will be deleted : "
										+"\nAuthor :\t" + q.getAuthor()
										+ "\nTheme :\t" + q.getTheme() 
										+ "\nClues :\t" + q.getClues().get(0)
										+ "\n\t\t" + q.getClues().get(1)
										+ "\n\t\t" + q.getClues().get(2)
										+ "\nAnswer :\t" + q.getAnswer())) {
									try {
										admin.deleteQuestion(q);
									} catch (QuestionNotFoundException | DeckNotFoundException e) {
										MsgBox.dispalyException(e);
									} finally {
										updateTable();
									}
								}
							}
						}
					}
				});
				MenuItem mod = new MenuItem("Modify");
				mod.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(getTable().getSelectionModel().getSelectedItem() == null) {
							MsgBox.dispalyOk("No item selected", "Select an element to modify it.");
							return;
						}
					}
				});
				
				contextMenu.getItems().addAll(add, del, mod);
			}
			return contextMenu;
		}
		
		public ImageView getIvBack() {
			if(ivBack == null) {
				ivBack = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_back.png");
				IGraphicConst.styleImageView(ivBack);
				ivBack.setOnMouseClicked(e -> showElement(new AdminMenu()));
			}
			return ivBack;
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
		private ImageView ivBack;
		
		public AdminAdd(String theme) {
			this();
			getCbbTheme().getSelectionModel().select(theme);
		}
		
		public AdminAdd() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			GridPane gp = new GridPane();
			
			gp.setAlignment(Pos.CENTER);
			gp.setHgap(10);
			gp.setVgap(0);
			gp.setTranslateY(70);
			
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
			
			GridPane.setHalignment(getIvBack(), HPos.LEFT);
			gp.add(getIvBack(), 0, 6);
			
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
				cbbTheme.setTooltip(new Tooltip("Select a theme or create a new one."));
				cbbTheme.setPromptText("Select a theme or create a new one.");
				for(Deck d : g.getDecks()) {
					cbbTheme.getItems().add(d.getTheme());
				}
				cbbTheme.setEditable(true);
				cbbTheme.setVisibleRowCount(5);
				cbbTheme.setMinWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				cbbTheme.setPrefWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				cbbTheme.setMaxWidth(IGraphicConst.WIDTH_TXT_ADMIN);
				cbbTheme.setStyle(IGraphicConst.STYLE_TXT);
				cbbTheme.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5, false) , new Insets(10))));
				cbbTheme.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5,  false), null ,new Insets(10))));
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
				btnAdd.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						if(getCbbTheme().getValue().isEmpty() || getTxtClue1().getText().isEmpty() 
								|| getTxtClue2().getText().isEmpty() || getTxtClue3().getText().isEmpty() 
								|| getTxtAnswer().getText().isEmpty()) {
							MsgBox.dispalyOk("Missing input", "One or more required fields haven't been filled");
							return;
						}
						String theme = getCbbTheme().getValue(), answer = getTxtAnswer().getText();
						List<String> clues = Arrays.asList(getTxtClue1().getText(), getTxtClue2().getText(), getTxtClue3().getText());
						if(!MsgBox.displayYesNO("Add this question?", "This question will be added : "
								+"\nAuthor :\t" + admin.getUsername()
								+ "\nTheme :\t" + theme 
								+ "\nClues :\t" + clues.get(0)
								+ "\n\t\t" + clues.get(1)
								+ "\n\t\t" + clues.get(2)
								+ "\nAnswer :\t" + answer)) {
							return;
						}
						try {
							admin.addQuestion(theme, clues, answer);
							MsgBox.dispalyOk("Question added !", "This question has been added : "
									+"\nAuthor :\t" + admin.getUsername()
									+ "\nTheme :\t" + theme 
									+ "\nClues :\t" + clues.get(0)
									+ "\n\t\t" + clues.get(1)
									+ "\n\t\t" + clues.get(2)
									+ "\nAnswer :\t" + answer );
							getCbbTheme().getItems().clear();
							for(Deck d : g.getDecks()) {
								getCbbTheme().getItems().add(d.getTheme());
							}
						} catch (QuestionAlreadyExistException e) {
							MsgBox.dispalyException(e);
						} finally {
							getTxtClue1().setText("");
							getTxtClue2().setText("");
							getTxtClue3().setText("");
							getTxtAnswer().setText("");
						}
					}
				});
			}
			return btnAdd;
		}
		public ImageView getIvBack() {
			if(ivBack == null) {
				ivBack = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_back.png");
				IGraphicConst.styleImageView(ivBack);
				ivBack.setOnMouseClicked(e -> showElement(new AdminModify()));
			}
			return ivBack;
		}
	}
}
