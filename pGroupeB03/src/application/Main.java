package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.GameController;
import view.MainMenu;
<<<<<<< HEAD
=======
import view.PlayerSelection;
import view.WaitingScreen;
>>>>>>> branch 'master' of https://arnonicoletta@bitbucket.org/arralo/groupeb03.git


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//test des interfaces
			GameController gc = new GameController();
<<<<<<< HEAD
			Scene scene = new Scene(gc,700,500);
=======
			PlayerSelection ps = new PlayerSelection();
			MainMenu mm = new MainMenu();
			WaitingScreen ws = new WaitingScreen();
			
			//test des interfaces
			
			Scene scene = new Scene(ws,700,500);
>>>>>>> branch 'master' of https://arnonicoletta@bitbucket.org/arralo/groupeb03.git
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
