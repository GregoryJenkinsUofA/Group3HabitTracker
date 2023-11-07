package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TimeManagerUI extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	LoginCreateAccountPane loginPane;
	BorderPane everything;

	@Override
	public void start(Stage primaryStage) throws Exception {
		LayoutGUI();
		
		Scene scene = new Scene(everything, 600, 400);
		scene.getStylesheets().add("./css/basic.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("TimeManager 1st Ed");
		primaryStage.show();
	}
	
	private void LayoutGUI() {
		everything = new BorderPane();
		
		loginPane = LoginCreateAccountPane.getInstance();
//		saveLoad = new PersistenceMenu();
		
//		everything.setTop(saveLoad);
		everything.setCenter(loginPane);
	}
	
}
