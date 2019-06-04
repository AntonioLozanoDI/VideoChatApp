package application;

import application.services.Service;
import application.view.ApplicationView;
import application.view.VideoChatAppView;
import application.view.modal.ApplicationModal;
import application.view.modal.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.database.DatabaseHelper;
import utils.resources.EnvironmentLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {		
		DatabaseHelper.askApplicationDatabase();
		DatabaseHelper.ensureDatabaseExists();
		ApplicationModal.build(LoginWindow.class, null).showView();
		ApplicationView.launchView(VideoChatAppView.class, primaryStage, true);
	}
	
	@Override
	public void stop() throws Exception {
		Service.killServices();
		System.exit(0);
	}

	public static void main(String[] args) {
		EnvironmentLoader.configure(args);
		launch(args);
	}
}