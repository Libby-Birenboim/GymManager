package fitness;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * GymManagerMain class extends java Application class and is a javaFX class that launches the Gym Manager
 * 		user interface
 * @author Selin Altiparmak, Libby Birenboim
 */
public class GymManagerMain extends Application {
	/**
	 * start class sets the stage of the Gym Manager GUI and catches exceptions
	 * @param primaryStage Stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GymManagerView.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Welcome to Gym Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 * main class launches the Gym Manager GUI and catches exceptions
	 * @param args String array
	 */
	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			
		}
	}
}
