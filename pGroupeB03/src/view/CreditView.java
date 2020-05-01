package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class CreditView extends BorderPane {

	private Label lblCredit;
	private Button btnOK;
	
	public CreditView() {
		this.setBackground(new Background(new BackgroundImage(
				new Image(IGraphicConst.URL_PATH_IMG + "background/background_mainmenu.png", false), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, 
				BackgroundPosition.CENTER,  
				new BackgroundSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND, false, false, false, false))));
		this.setCenter(getLblCredit());
	}
	
	public Label getLblCredit() {
		if(lblCredit==null) {
			lblCredit = new Label();
			IGraphicConst.styleBiggerLabel(lblCredit);
			lblCredit.setPrefSize(IGraphicConst.WIDTH_BACKGROUND, IGraphicConst.HEIGHT_BACKGROUND);
			lblCredit.setPadding(new Insets(25));
			lblCredit.setWrapText(true);
			lblCredit.setText("\n\nThis game was produced as part of the Project course given by Mr. ALTARES MENENDEZ "
					+ "in the second year of Bachelor in Computer Science at Helha in Mons.\r\n" + 
					"\nThe group that produced it is made up of:\r\n" + 
					"	Arno NICOLETTA\r\n" + 
					"	Rayan TAHRI\r\n" + 
					"	Loïc LÉCOLIER");
		}
		return lblCredit;
	}
	public Button getBtnOK() {
		if(btnOK==null) {
			btnOK = new Button("OK");
		}
		return btnOK;
	}
	
	
}
