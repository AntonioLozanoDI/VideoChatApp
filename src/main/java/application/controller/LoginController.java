package application.controller;

import com.sp.fxutils.validation.FXUtils;

import application.view.modal.ApplicationModal;
import application.view.modal.RegisterUserWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.constants.Styles;

public class LoginController {

	@FXML
	private TextField loginField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField serverField;
	@FXML
	private Label loginWarning;
	@FXML
	private Label registerLabel;

	private Stage primaryStage;
	
	private Stage windowStage;
	
	private boolean valid = false;
	
	private RegisterUserWindow registerWindow;;

	@FXML
	private void initialize() {
		
		registerLabel.setId(Styles.Login.registerLabelDefault);
		loginWarning.setText("");
		loginWarning.setId(Styles.Common.warningLabel);
	}

	@FXML
	private void validateLogin() {
		boolean loginValid = FXUtils.textfieldTextIsNotNullOrEmpty(loginField);
		boolean passValid = FXUtils.textfieldTextIsNotNullOrEmpty(passwordField);
		if (loginValid && passValid) {
		
			valid  = true;
			windowStage.close();
		} else {
			loginWarning.setText("Pro favor introduzca un login y una contraseña para poder iniciar sesión en la aplicacion");
		}
	}
	
	@FXML
	private void registerUser() {
		if(registerWindow == null) 
			registerWindow = ApplicationModal.build(RegisterUserWindow.class, primaryStage);
		
		registerWindow.showView();
	}
	
	@FXML
	private void mouseEnter() {
		registerLabel.setId(Styles.Login.registerLabelEnter);
	}
	
	@FXML
	private void mouseExit() {
		registerLabel.setId(Styles.Login.registerLabelDefault);
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	public void setWindowStage(Stage stage) {
		this.windowStage = stage;
	}

	public void checkLogin() {
		if(!valid) 
			Platform.exit();
	}
}
