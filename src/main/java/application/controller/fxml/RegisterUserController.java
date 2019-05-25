package application.controller.fxml;

import com.diproject.commons.model.Origin;
import com.diproject.commons.model.User;
import com.diproject.commons.utils.rest.clients.ConfigurationClient;
import com.diproject.commons.utils.rest.clients.UserClient;
import com.diproject.commons.utils.ws.WebSocketClient;
import com.sp.dialogs.DialogBuilder;
import com.sp.fxutils.validation.FXUtils;

import application.controller.session.SessionController;
import application.model.ProfileModel;
import application.model.dao.LogonServersDAO;
import application.model.dao.ProfileDAO;
import http.status.exceptions.Http409ConflictException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.constants.Styles;

public class RegisterUserController {

	public interface RegisterOperationListener {
		void notifyListener();
	}
	
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
	
	private UserClient userClient;
	
	private ConfigurationClient configClient;
	
	private ProfileDAO profileDAO;
	
	private LogonServersDAO logonServersDAO;
	
	private SessionController sc;
	
	private RegisterOperationListener registerListener;

	@FXML
	private void initialize() {
		registerWarning.setText("");
		registerWarning.setId(Styles.Common.warningLabel);
		
		profileDAO = ProfileDAO.getInstance();
		logonServersDAO = LogonServersDAO.getInstance();
		
		sc = SessionController.getInstance(); 
		userClient = new UserClient();
		configClient = new ConfigurationClient();
		
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
	private void registerUser() {
		showFailedRegister(false);
		boolean ok = false;
		boolean validParams = checkTextFields();
		if (validParams) {
			User user = new User();
			user.setUsername(String.format("%s %s %s", nombreField.getText(), apellido1Field.getText(), apellido2Field.getText()));
			user.setLogin(loginField.getText());
			user.setPassword(passwordField.getText());
			try {
				configClient.configureServer(serverField.getText());
				userClient.signup(user);
				logonServersDAO.saveServer(serverField.getText());
				saveUser(user);
				registerListener.notifyListener();
				ok = true;
			} catch (Http409ConflictException e) {	
				registerWarning.setText("Ya existe un usuario con el login que has introducido");
			} catch (Exception e) {
				DialogBuilder.warn().exceptionContent(e).alert().showAndWait();
			}
			
			if(ok) 
				windowStage.close();
		} else {
			showWrongParams(true);
		}
	}
	
	private void saveUser(User user) {
		ProfileModel prof = ProfileModel.fromUser(user);
		boolean saved = profileDAO.saveProfile(prof);
		if(!saved) {
			ProfileModel.editUserFromProfile(user, prof);
			userClient.update(user);
		}
		sc.setLoggedUser(prof);
		sc.setServerAddress(serverField.getText());
		sc.setClient(new WebSocketClient(user.getLogin(), Origin.VIDEO));
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
	
	private void showWrongParams(boolean validParams) {
		String warn = "Por favor rellene todos los campos";
		registerWarning.setText(validParams ? "" : warn);
	}
	
	private void showFailedRegister(boolean show) {
		String warn = "Ha habido un problema a la hora de registrarse en el servidor";
		registerWarning.setText(show ? warn : "");
	}

	public void setWindowStage(Stage stage) {
		this.windowStage = stage;
	}

	public void setOnSuccessfulRegister(RegisterOperationListener listener) {
		registerListener = listener;
	}
}
