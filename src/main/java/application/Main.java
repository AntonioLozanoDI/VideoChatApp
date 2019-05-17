package application;

import application.view.ApplicationView;
import application.view.VideoChatAppView;
import application.view.modal.ApplicationModal;
import application.view.modal.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.database.DatabaseHelper;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		DatabaseHelper.askApplicationDatabase();
		DatabaseHelper.ensureDatabaseExists();
		if(Math.random() + 3 <2)
			ApplicationModal.build(LoginWindow.class, null).showView();
		ApplicationView.launchView(VideoChatAppView.class, primaryStage, true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
