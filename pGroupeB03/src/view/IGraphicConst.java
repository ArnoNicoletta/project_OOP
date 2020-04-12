package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * This interface includes all graphic constants.
 * These constants can be the width and the height of the components as well as the style.
 * @author ArRaLo
 */
public interface IGraphicConst {
	
	//Button
	
	final double WIDTH_BUTTON = 350, HEIGHT_BUTTON = 35;
	final double WIDTH_LARGE_BUTTON = 500;
	final String STYLE_BUTTON = "-fx-font-size: 24;\r\n" + 
								"-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
								"-fx-font-weight: bold;";
	
	public static Button styleButton(Button btn) {
		btn.setStyle(STYLE_BUTTON);
		btn.setTextFill(Color.WHITE);
		btn.setBackground(new Background(new BackgroundFill(Color.web("#793F54"), new CornerRadii(20, false) , null)));
		btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20,  false), new BorderWidths(2))));
		return btn;
	}
	
	
	//LABEL
	
	final double WIDTH_LARGE_LBL = 500, HEIGHT_LARGE_LBL = 250;
	final String STYLE_LBL ="-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
							"-fx-font-size: 20px;\r\n" + 
							"-fx-font-weight: bold;";
	public static Label styleLabel(Label lbl) {
		lbl.setStyle(STYLE_LBL);
		lbl.setTextFill(Color.WHITE);
		return lbl;
	}
	
	
	//TextField (and PasswordField)
	
	final double WIDTH_TXT = 200, HEIGHT_TXT = 60;
	final String STYLE_TXT= "-fx-font-family: \"Roboto Black\", sans-serif;\r\n" + 
							"-fx-font-size: 15px;\r\n" + 
							"-fx-font-weight: bold;\r\n";
	public static TextField styleTextField(TextField txt) {
		txt.setStyle(STYLE_TXT);
		txt.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10, false) , new Insets(10))));
		txt.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10,  false), null ,new Insets(10))));
		return txt;
	}
	//Images
	final double WIDTH_JOKER = 100, HEIGHT_JOKER = 100;
	
	
}
