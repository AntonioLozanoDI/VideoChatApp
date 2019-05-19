package application.controller;

import application.services.Service;
import application.view.modal.ApplicationModal;
import application.view.modal.AudioSettingsWindow;
import application.view.modal.ProfileSettingsWindow;
import application.view.modal.ServerSettingsWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class RootController {

	private Stage primaryStage;
	private MainController mainController;
	
	private ServerSettingsWindow serverSettingsWindow;
	private ProfileSettingsWindow profileSettingsWindow;
	private AudioSettingsWindow audioSettingsWindow;
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	@FXML
	private void configureProfile() {
		if(profileSettingsWindow == null)
			profileSettingsWindow = ApplicationModal.build(ProfileSettingsWindow.class, primaryStage);
		
		profileSettingsWindow.showView();
	}

	@FXML
	private void configureServer() {
		if(serverSettingsWindow == null)
			serverSettingsWindow = ApplicationModal.build(ServerSettingsWindow.class, primaryStage);
		
		serverSettingsWindow.showView();
	}
	
	@FXML
	private void configureAudio() {
		if(audioSettingsWindow == null)
			audioSettingsWindow = ApplicationModal.build(AudioSettingsWindow.class, primaryStage);
		
		audioSettingsWindow.showView();
	}
	
	@FXML
	public void action() {
		mainController.setupVideoScreen();
	}
	
	@FXML
	private void closeApplication() {
		Service.stopServices();
		System.exit(0);
	}
	
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
