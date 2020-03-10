package view;

import java.awt.font.ImageGraphicAttribute;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.EnumNumberPlayer;

public class PlayerSelection extends BorderPane{

	private Label labelTop;
	
	private List<RadioButton>listRadPlayer;
	private ToggleGroup tgPlayer;
	
	private Label labelPlayerOne;
	private TextField txtPlayerOne;
	private Label labelPlayerTwo;
	private TextField txtPlayerTwo;
	private Label labelPlayerThree;
	private TextField txtPlayerThree;
	
	private Button btnOK;
	//UTILISE UN TOOGLEGROUPE POUR LES RADIOBUTTON + ENUM 
	
	public PlayerSelection() {
		
		//TOP
		HBox hbTop = new HBox();
		hbTop.getChildren().add(getLabelTop());
		hbTop.setAlignment(Pos.TOP_CENTER);
		hbTop.setPadding(new Insets(10,10,10,10));
		
		this.setTop(hbTop);
		//CENTER
		VBox vbCenter = new VBox();
		vbCenter.getChildren().addAll(getListRadPlayer());
		vbCenter.setSpacing(10);
		vbCenter.setAlignment(Pos.CENTER);
		this.setCenter(vbCenter);
	}

	public Label getLabelTop() {
		if(labelTop==null) {
			labelTop = new Label("ENTREZ LE NOMBRE DE JOUEUR");
		}
		return labelTop;
	}
	

	public List<RadioButton> getListRadPlayer() {
		if(listRadPlayer==null) {
			listRadPlayer = new ArrayList<RadioButton>();
			for(EnumNumberPlayer tmp : EnumNumberPlayer.values()) {
				RadioButton r = new RadioButton(tmp.toString());
				r.setToggleGroup(getTgPlayer());
				r.setPrefWidth(IGraphicConst.WIDTH_RADIOBUTTON);
				listRadPlayer.add(r);
			}
		}
		return listRadPlayer;
	}

	public ToggleGroup getTgPlayer() {
		if(tgPlayer==null) {
			tgPlayer = new ToggleGroup();
		}
		return tgPlayer;
	}

	public Label getLabelPlayerOne() {
		if(labelPlayerOne == null) {
			labelPlayerOne = new Label("Pseudo player one");
		}
		return labelPlayerOne;
	}

	public TextField getTxtPlayerOne() {
		if(txtPlayerOne==null) {
			txtPlayerOne = new TextField();
		}
		return txtPlayerOne;
	}

	public Label getLabelPlayerTwo() {
		if(labelPlayerTwo == null) {
			labelPlayerTwo = new Label("Pseudo player two");
		}
		return labelPlayerTwo;
	}

	public TextField getTxtPlayerTwo() {
		if(txtPlayerTwo==null) {
			txtPlayerTwo = new TextField();
		}
		return txtPlayerTwo;
	}

	public Label getLabelPlayerThree() {
		if(labelPlayerThree == null) {
			labelPlayerThree = new Label("Pseudo player three");
		}
		return labelPlayerThree;
	}

	public TextField getTxtPlayerThree() {
		if(txtPlayerThree==null) {
			txtPlayerThree = new TextField();
		}
		return txtPlayerThree;
	}

	public Button getBtnOK() {
		if(btnOK==null) {
			btnOK = new Button("OK");
		}
		return btnOK;
	}
	
	
}
