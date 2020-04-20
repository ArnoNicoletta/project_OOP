package view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import exception.DeckNotFoundException;
import exception.QuestionAlreadyExistException;
import exception.QuestionNotFoundException;
import exception.WrongDeckFormatException;
import exception.WrongLoginException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.Admin;
import model.Deck;
import model.Game;
import model.Question;
import model.RulesSettings;

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
		
		private Button btnAdminSettings;
		private Button btnModify;
		private Button btnImport;
		private Button btnExport;
		
		public AdminMenu() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			VBox vb = new VBox();
			vb.getChildren().addAll(getBtnAdminSettings(), getBtnModify(),getBtnImport(), getBtnExport());
			vb.setSpacing(20);
			vb.setAlignment(Pos.CENTER);
			vb.setTranslateY(50);
			this.setCenter(vb);
		}
		public Button getBtnAdminSettings() {
			if(btnAdminSettings==null) {
				btnAdminSettings = new Button("GAME SETTINGS");
				btnAdminSettings.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnAdminSettings);
				btnAdminSettings.setOnAction(e -> showElement(new AdminGameSettings()));
			}
			return btnAdminSettings;
		}
		public Button getBtnModify() {
			if(btnModify == null) {
				btnModify = new Button("ADD/REMOVE/MODIFY QUESTIONS");
				btnModify.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnModify);
				btnModify.setOnAction(e -> showElement(new AdminTreeTable()));
			}
			return btnModify;
		}
		public Button getBtnImport() {
			if(btnImport == null) {
				btnImport = new Button("IMPORT QUESTIONS (json format)");
				btnImport.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnImport);
				
				btnImport.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						FileChooser fc = new FileChooser();
						fc.setTitle("Import JSON file");
						fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Deck file", "*.json"));
						File f = fc.showOpenDialog(getScene().getWindow());
						if(f == null || !f.exists()) {
							MsgBox.dispalyOk("Error while selecting file", "An eror ocured while selecting file."
																		+ "\nTry again.");
							return;
						}
						try {
							g.addDeck(f);
							g.saveAllDecks();
							MsgBox.dispalyOk("Import done !", "The deck has been imported.");
						} catch (WrongDeckFormatException e) {
							MsgBox.dispalyException(e);
							return;
						}
					}
				});
			}
			return btnImport;
		}
		public Button getBtnExport() {
			if(btnExport == null) {
				btnExport = new Button("EXPORT ALL QUESTIONS");
				btnExport.setPrefSize(IGraphicConst.WIDTH_LARGE_BUTTON, IGraphicConst.HEIGHT_BUTTON);
				IGraphicConst.styleButton(btnExport);
				
				btnExport.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						DirectoryChooser dc = new DirectoryChooser();
						dc.setTitle("Select a folder");
						File dir = dc.showDialog(getScene().getWindow());
						if(dir==null || !dir.exists()) {
							MsgBox.dispalyOk("Error while selecting folder", "An eror ocured while selecting folder."
									+ "\nTry again.");
							return;
						}
						File f = new File(dir.getAbsolutePath()+ "/decks.json");
						try {
							f.createNewFile();
						} catch (IOException e) {
							MsgBox.dispalyException(e);
						}
						g.exportAllDecks(f);
						MsgBox.dispalyOk("Export done ! ", "The questions have been exported"
								+ "\nFile : " + f.getAbsolutePath());
					}
				});
			}
			return btnExport;
		}
		
	}
	
	
class AdminGameSettings extends BorderPane {
		
		private List<Label> lLblInputSettings = new ArrayList<>();
		private Map<TextField, Method> mInputSettings = new LinkedHashMap<>();
		private Map<CheckBox, Method> mChoiceSettings = new LinkedHashMap<>();
		
		private GridPane gp;
		private Button btnSave;
		private ImageView ivBack;
		
		public AdminGameSettings() {
			
			this.setBackground(new Background(new BackgroundImage(
					new Image(IGraphicConst.URL_PATH_IMG + "background/background_settings.png", false), 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
					BackgroundPosition.CENTER,  
					new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
			
			
			//CENTER
			getGp().setTranslateY(50);
			this.setCenter(getGp());
			
			
			//Bottom
			HBox hbBottom = new HBox();
			hbBottom.setAlignment(Pos.TOP_CENTER);
			hbBottom.getChildren().addAll(getIvBack());
			this.setBottom(getIvBack());
			
		}
		
		private void initElements() {
			
			for(Field f : RulesSettings.class.getDeclaredFields()) {
				if(f.getType() == double.class || f.getType() == int.class) {
					Label lbl = new Label(f.getName());
					GridPane.setHalignment(lbl, HPos.LEFT);
					TextField txt = new TextField();
					GridPane.setHalignment(txt, HPos.CENTER);
					Method m=null;
					try  {
						m = RulesSettings.class.getDeclaredMethod(
								"set"+f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), 
								f.getType());
						txt.setText(RulesSettings.class.getDeclaredMethod(
								"get"+ f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1))
								.invoke(null).toString());
					} catch (Exception e) { e.printStackTrace();}
					lLblInputSettings.add(lbl);
					mInputSettings.put(txt, m);
				}
				else if(f.getType() == boolean.class) {
					CheckBox cb = new CheckBox(f.getName());
					GridPane.setHalignment(cb, HPos.CENTER);
					Method m = null;
					try {
						cb.setSelected((boolean) RulesSettings.class.getDeclaredMethod(
								"get"+ f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1))
								.invoke(null));
						m = RulesSettings.class.getDeclaredMethod(
								"set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), 
								f.getType());
					} catch (Exception e) { e.printStackTrace();}
					mChoiceSettings.put(cb, m);
				}
			}
		}
		
		private void initGp() {
			initElements();
			int x = 0, y = 0, i;
			for(i=0;i<lLblInputSettings.size();i++) {
				getGp().add(lLblInputSettings.get(i), x++, y);
				getGp().add((TextField) mInputSettings.keySet().toArray()[i], x++, y);
				if(x>=4) {
					x=0;
					y++;
				}
				else getGp().add(new Label("  "), x++, y);
			}
			for(i=0;i<mChoiceSettings.size();i++) {
				getGp().add((CheckBox) mChoiceSettings.keySet().toArray()[i], x, y, 2, 1);
				x +=3;
				if(x>=4) {
					x=0;
					y++;
				}
			}
			getGp().add(getBtnSave(), 4, ++y);
		}
		
		private void save() {
			mInputSettings.forEach((txt, m) -> {
				try {
					if(m.getGenericParameterTypes()[0] == int.class) {
						m.invoke(null, Integer.parseInt(txt.getText()));
					}
					else if(m.getGenericParameterTypes()[0] == double.class) {
						m.invoke(null, Double.parseDouble(txt.getText()));
					}
				} catch (Exception e) {
					MsgBox.dispalyException(e);
					return;
				}
			});
			mChoiceSettings.forEach((cb, m) -> {
				try {
					m.invoke(m, cb.isSelected());
				} catch (Exception e) {
					MsgBox.dispalyException(e);
					return;
				}
			});
			MsgBox.dispalyOk("Saved !", "Settings have been saved !");
		}
		
		public GridPane getGp() {
			if(gp==null) {
				gp = new GridPane();
				gp.setAlignment(Pos.CENTER);
				gp.setHgap(10);
				gp.setVgap(10);
				initGp();
			}
			return gp;
		}
		
		public Button getBtnSave() {
			if(btnSave==null) {
				btnSave = new Button("SAVE");
				IGraphicConst.styleButton(btnSave);
				GridPane.setHalignment(btnSave, HPos.RIGHT);
				btnSave.setOnAction(e -> save());
			}
			return btnSave;
		}
		
		public ImageView getIvBack() {
			if(ivBack == null) {
				ivBack = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_back.png");
				IGraphicConst.styleImageView(ivBack);
				ivBack.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/button_back_nobackground.png"));
				ivBack.setTranslateX(20);
				ivBack.setTranslateY(-20);
				ivBack.setOnMouseClicked(e -> showElement(new AdminMenu()));
			}
			return ivBack;
		}
	}
	
	
	class AdminTreeTable extends BorderPane {
		
		private TreeTableView<Question> table;
		private TreeItem<Question> root;
		private ContextMenu contextMenu;
		
		private ImageView ivBack;
		
		public AdminTreeTable() {
			
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
			getTable().getSelectionModel().clearSelection();
		}
		
		@SuppressWarnings("unchecked")
		public TreeTableView<Question> getTable() {
			if(table==null) {
				table = new TreeTableView<>();
				IGraphicConst.styleTreeTableView(table);
				table.setTranslateY(80);
				
				TreeTableColumn<Question, String> theme = new TreeTableColumn<>("THEME");
				theme.setCellValueFactory(new TreeItemPropertyValueFactory<>("theme"));
				theme.setEditable(true);
				
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
						if(getTable().getSelectionModel().getSelectedItems().size()>1) {
							MsgBox.dispalyOk("Select a single one question", "You can't modify multiple questions.");
							return;
						}
						if(getTable().getSelectionModel().getSelectedItem().getValue().getAnswer().equalsIgnoreCase("")) {
							MsgBox.dispalyOk("Select a question", "You can't modify a deck.");
							return;
						}
						showElement(new AdminModify(getTable().getSelectionModel().getSelectedItem().getValue()));
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
				ivBack.setImage(new Image(IGraphicConst.URL_PATH_IMG + "icons/button_back_nobackground.png"));
				ivBack.setTranslateX(20);
				ivBack.setTranslateY(-20);
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
			getLblClue1().requestFocus();
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
				ivBack.setOnMouseClicked(e -> showElement(new AdminTreeTable()));
			}
			return ivBack;
		}
	}
	
	class AdminModify extends BorderPane {

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
		private Button btnModify;
		private ImageView ivBack;
		
		Question q;
		
		public AdminModify(Question q) {
			
			this.q = q;
			
			getCbbTheme().getSelectionModel().select(q.getTheme());
			getCbbTheme().setDisable(true);
			getTxtClue1().setText(q.getClues().get(0));
			getTxtClue2().setText(q.getClues().get(1));
			getTxtClue3().setText(q.getClues().get(2));
			getTxtAnswer().setText(q.getAnswer());
			
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
			
			GridPane.setHalignment(getBtnModify(), HPos.RIGHT);
			gp.add(getBtnModify(), 1, 6);
			
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
		public Button getBtnModify() {
			if(btnModify == null) {
				btnModify = new Button("MODIFY");
				IGraphicConst.styleButton(btnModify);
				btnModify.setOnAction(new EventHandler<ActionEvent>() {
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
						Question newQ = new Question(admin.getUsername(), theme, clues, answer);
						if(!MsgBox.displayYesNO("Modify this question?", "This question will replaced the other one : "
								+"\nAuthor :\t" + newQ.getAuthor()
								+ "\nTheme :\t" + newQ.getTheme()
								+ "\nClues :\t" + newQ.getClues().get(0)
								+ "\n\t\t" + newQ.getClues().get(1)
								+ "\n\t\t" + newQ.getClues().get(2)
								+ "\nAnswer :\t" + newQ.getAnswer())) {
							return;
						}
						try {
							admin.modifyQuestion(q, newQ);
							MsgBox.dispalyOk("Question modify !", "Question modify ! New question :  "
									+"\nAuthor :\t" + newQ.getAuthor()
									+ "\nTheme :\t" + newQ.getTheme()
									+ "\nClues :\t" + newQ.getClues().get(0)
									+ "\n\t\t" + newQ.getClues().get(1)
									+ "\n\t\t" + newQ.getClues().get(2)
									+ "\nAnswer :\t" + newQ.getAnswer() );
							getCbbTheme().getItems().clear();
							for(Deck d : g.getDecks()) {
								getCbbTheme().getItems().add(d.getTheme());
							}
						} catch (QuestionNotFoundException | DeckNotFoundException  e) {
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
			return btnModify;
		}
		public ImageView getIvBack() {
			if(ivBack == null) {
				ivBack = new ImageView(IGraphicConst.URL_PATH_IMG + "icons/button_back.png");
				IGraphicConst.styleImageView(ivBack);
				ivBack.setOnMouseClicked(e -> showElement(new AdminTreeTable()));
			}
			return ivBack;
		}
	}
}
