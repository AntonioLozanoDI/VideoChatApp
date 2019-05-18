package application.controller;

import com.sp.fxutils.validation.FXUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.constants.Styles;

public class RegisterUserController {


	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellido1Field;
	@FXML
	private TextField apellido2Field;
	@FXML
	private TextField loginField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField serverField;
	@FXML
	private Label registerWarning;

	private Stage windowStage;

	@FXML
	private void initialize() {
		registerWarning.setText("");
		registerWarning.setId(Styles.Common.warningLabel);
		loginField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		nombreField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		apellido1Field.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		apellido2Field.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		passwordField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
		serverField.textProperty().addListener(
				(observable, oldValue, newValue) -> {	showWrongParams(checkTextFields());  });
	}

	@FXML
	private void registerUser() {//TODO
		showFailedRegister(false);
		boolean ok = false;
		boolean validParams = checkTextFields();
		if (validParams) {
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
		boolean validNombre = FXUtils.textfieldTextIsNotNullOrEmpty(nombreField);
		boolean validApellido1= FXUtils.textfieldTextIsNotNullOrEmpty(apellido1Field);
		boolean validApellido2 = FXUtils.textfieldTextIsNotNullOrEmpty(apellido2Field);
		boolean validPassword = FXUtils.textfieldTextIsNotNullOrEmpty(passwordField);
		boolean validServer = FXUtils.textfieldTextIsNotNullOrEmpty(serverField);
		
		return validLogin && validNombre && validApellido1 && validApellido2 && validPassword && validServer;
	}
	
	private void showWrongParams(boolean show) {
		String warn = "Por favor rellene todos los campos";
		registerWarning.setText(show ? warn : "");
	}
	
	private void showFailedRegister(boolean show) {
		String warn = "Ha habido un problema a la hora de registrarse en el servidor";
		registerWarning.setText(show ? warn : "");
	}

	public void setWindowStage(Stage stage) {
		this.windowStage = stage;
	}
}
