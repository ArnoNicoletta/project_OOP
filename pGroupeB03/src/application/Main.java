package application;
	
import exception.IdenticalPseudoException;
import exception.TooMuchCharException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
			primaryStage.setResizable(false);
			primaryStage.getIcons().addAll(new Image("file:./src/resources/images/logo.png"));
			primaryStage.setTitle("FOUR THE WIN");
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					event.consume();
					if(MsgBox.displayYesNO("Exit", "Are you sure you want to exit?")) {
						primaryStage.close();
					}
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
