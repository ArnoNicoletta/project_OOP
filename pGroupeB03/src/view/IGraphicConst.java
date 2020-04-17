package view;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * This interface includes all graphic constants and methods to apply style on components.
 * These constants can be the width and the height of the components as well as the style.
 * @author ArRaLo
 */
public interface IGraphicConst {
	
	final String BASE_COLOR = "#793F54";
	final String BACKGROUND_TOPCOLOR = "#AD6A65";
	final String BACKGROUND_BOTCOLOR = "#E7BC97";
	//Button
	
	final double WIDTH_BUTTON = 350, HEIGHT_BUTTON = 35;
	final double WIDTH_LARGE_BUTTON = 500;
	final String STYLE_BUTTON = "-fx-font-size: 24;\r\n" + 
								"-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
								"-fx-font-weight: bold;";
	
	public static Button styleButton(Button btn) {
		btn.setStyle(STYLE_BUTTON);
		btn.setTextFill(Color.WHITE);
		btn.setBackground(new Background(new BackgroundFill(Color.web(BASE_COLOR), new CornerRadii(20, false) , null)));
		btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
		btn.setCache(true);
		btn.setCursor(Cursor.HAND);
		return btn;
	}
	
	
	//LABEL
	
	final double WIDTH_LARGE_LBL = 450, HEIGHT_LARGE_LBL = 200;
	final String STYLE_LBL ="-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
							"-fx-font-size: 20px;\r\n" + 
							"-fx-font-weight: bold;";
	public static Label styleLabel(Label lbl) {
		lbl.setStyle(STYLE_LBL);
		lbl.setTextFill(Color.WHITE);
		lbl.setCache(true);
		lbl.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 1, 1, 0, 0));
		return lbl;
	}
	
	final String STYLE_BIGGER_LBL = "-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
									"-fx-font-size: 24px;\r\n" + 
									"-fx-font-weight: bold;";
	public static Label styleBiggerLabel(Label lbl) {
		lbl.setStyle(STYLE_BIGGER_LBL);
		lbl.setTextFill(Color.WHITE);
		lbl.setCache(true);
		lbl.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 1, 1, 0, 0));
		return lbl;
	}
	
	
	//TextField (and PasswordField)
	
	final double WIDTH_TXT = 200, HEIGHT_TXT = 60;
	final double WIDTH_TXT_ADMIN = 300;
	final String STYLE_TXT= "-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
							"-fx-font-size: 15px;\r\n" + 
							"-fx-font-weight: bold;\r\n";
	public static TextField styleTextField(TextField txt) {
		txt.setStyle(STYLE_TXT);
		txt.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10, false) , new Insets(10))));
		txt.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10,  false), null ,new Insets(10))));
		return txt;
	}
	
	
	//ImageView
	
	final String URL_PATH_IMG = "file:./src/resources/images/";
	final double WIDTH_BACKGROUND = 675, HEIGHT_BACKGROUND = 506;
	final double WIDTH_JOKER = 100, HEIGHT_JOKER = 100;
	final double WIDTH_RANK = 50, HEIGHT_RANK = 50;
	final double WIDTH_BACK = 50, HEIGHT_BACK = 50;
	
	public static ImageView styleImageView(ImageView iv) {
		iv.setFitWidth(WIDTH_BACK);
		iv.setFitHeight(HEIGHT_BACK);
		iv.setCursor(Cursor.HAND);
		iv.setOpacity(0.75);
		return iv;
	}
	
	
	//CheckBox
	
	final String STYLE_CB ="-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
			"-fx-font-size: 20px;\r\n" + 
			"-fx-font-weight: bold;";
	public static CheckBox styleCheckBox(CheckBox cb) {
		cb.setStyle(STYLE_CB);
		cb.setTextFill(Color.WHITE);
		cb.setCache(true);
		cb.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 1, 1, 0, 0));
		cb.setCursor(Cursor.HAND);
		return cb;
	}
	
	
	//TreeTableView
	
	final double WIDTH_TREE_TABLE = 650, HEIGHT_TREE_TABLE = 325;
	public static TreeTableView<?> styleTreeTableView(TreeTableView<?> tree){
		tree.setMinSize(WIDTH_TREE_TABLE, HEIGHT_TREE_TABLE);
		tree.setPrefSize(WIDTH_TREE_TABLE, HEIGHT_TREE_TABLE);
		tree.setMaxSize(WIDTH_TREE_TABLE, HEIGHT_TREE_TABLE);
		tree.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 1, 1, 0, 0));
		return tree;
	}
	
}
