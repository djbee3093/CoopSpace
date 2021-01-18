package application; // Package with the rest of the application

import java.io.FileNotFoundException;	// Throws file not found
import javafx.application.Application;	// Application for JFX
import javafx.stage.Stage;				// Stage for JFX

public class driver extends Application{ // Driver extends the application

	public static void main(String[] args) { // Main entry point
		launch(); //Launch application
		
	}
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException // Application / stage start
	{
		Game game = new Game(); // Launch new game
	}
	
}
