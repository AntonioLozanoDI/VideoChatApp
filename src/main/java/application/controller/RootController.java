package application.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RootController {

	private Stage primaryStage;
	
	@FXML
	public void setFullScreen() {
		primaryStage.setFullScreen(true);
	}

	@FXML
	public void maximize() {
		primaryStage.setFullScreen(false);
		primaryStage.setMaximized(true);
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
