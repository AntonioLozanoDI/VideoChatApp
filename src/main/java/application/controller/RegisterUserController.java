package application.controller;

import com.diproject.commons.apiclient.UserClient;
import com.diproject.commons.model.UserNew;
import com.sp.fxutils.validation.FXUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.constants.Styles;

public class RegisterUserController {

	@FXML
	private TextField loginField;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField serverField;
	@FXML
	private Label registerWarning;

	private Stage primaryStage;

	private Stage windowStage;

	@FXML
	private void initialize() {
		registerWarning.setText("");
		registerWarning.setId(Styles.Common.warningLabel);
		loginField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		usernameField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		passwordField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		serverField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
	}

	@FXML
	private void registerUser() {
		showFailedRegister(false);
		UserNew user = null;
		boolean ok = false;
		boolean validParams = checkTextFields();
		if (validParams) {
			user = new UserNew();
			String server = serverField.getText();

			//ok = client.signup(user);
			if(ok) {
				
				windowStage.close();
			} else {
				showFailedRegister(true);
			}
		} else {
			showWrongParams(true);
		}
	}
	
	private boolean checkTextFields() {
		boolean validLogin = FXUtils.textfieldTextIsNotNullOrEmpty(loginField);
		boolean validUsername = FXUtils.textfieldTextIsNotNullOrEmpty(usernameField);
		boolean validPassword = FXUtils.textfieldTextIsNotNullOrEmpty(passwordField);
		boolean validServer = FXUtils.textfieldTextIsNotNullOrEmpty(serverField);
		return validLogin && validUsername && validPassword && validServer;
	}
	
	private void showWrongParams(boolean show) {
		String warn = "Por favor rellene todos los campos";
		registerWarning.setText(show ? warn : "");
	}
	
	private void showFailedRegister(boolean show) {
		String warn = "Ha habido un problema a la hora de registrarse en el servidor";
		registerWarning.setText(show ? warn : "");
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}

	public void setWindowStage(Stage stage) {
		this.windowStage = stage;
	}
}
