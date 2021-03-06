package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.RulesSettings;
import view.IGraphicConst;
import view.MainView;
import view.MsgBox;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			MainView root = new MainView();
			Scene scene = new Scene(root,1024,576);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1030);
			primaryStage.setMinHeight(580);
			primaryStage.setMaxWidth(1057);
			primaryStage.setMaxHeight(627);
			primaryStage.getIcons().addAll(new Image(IGraphicConst.URL_PATH_IMG + "icons/icon_logo_50.png"));
			primaryStage.setTitle("FOUR THE WIN");
			primaryStage.sizeToScene();
			primaryStage.show();
			primaryStage.setOnCloseRequest(event -> {
				event.consume();
				if(MsgBox.displayYesNO("Exit", "Are you sure you want to exit?")) {
					primaryStage.close();
					RulesSettings.saveSettings();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//Loading user settings
		RulesSettings.loadSettings();
		//launching GUI
		launch(args);
		
	}
}
