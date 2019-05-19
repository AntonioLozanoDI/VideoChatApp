package application;

import application.view.ApplicationView;
import application.view.VideoChatAppView;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.database.DatabaseHelper;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		DatabaseHelper.askApplicationDatabase();
		DatabaseHelper.ensureDatabaseExists();
		//	ApplicationModal.build(LoginWindow.class, null).showView();
		ApplicationView.launchView(VideoChatAppView.class, primaryStage, true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
