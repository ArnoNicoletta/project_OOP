package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.GameController;
import view.MainMenu;
import view.PlayerSelection;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GameController gc = new GameController();
			PlayerSelection ps = new PlayerSelection();
			Scene scene = new Scene(ps,700,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			//primaryStage.setFullScreen(true);
			primaryStage.getIcons().add(new Image(("file:./src/resources/images/logo.png")));
			primaryStage.setTitle("The 4TUNE");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
//		Game g = Game.getInstance();
//		g.addDeck(new File("./src/resources/questions/deck_Sport.json"));
//		g.addDeck(new File("./src/resources/questions/deck_Social Networks.json"));
//		g.addDeck(new File("./src/resources/questions/deck_Academy Awards winners.json"));
//		g.play();
	}
}
