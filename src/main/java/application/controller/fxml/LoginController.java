package application.controller.fxml;

import java.util.Optional;

import com.diproject.commons.model.Origin;
import com.diproject.commons.model.User;
import com.diproject.commons.utils.rest.ConfigurationHTTPClient;
import com.diproject.commons.utils.rest.UserHTTPClient;
import com.diproject.commons.utils.ws.WebSocketClient;
import com.sp.dialogs.DialogBuilder;
import com.sp.fxutils.validation.FXUtils;

import application.controller.session.SessionController;
import application.model.ProfileModel;
import application.model.dao.ProfileDAO;
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
	
	private RegisterUserWindow registerWindow;
	
	private UserHTTPClient userClient;
	
	private ConfigurationHTTPClient configClient;
	
	private ProfileDAO profileDAO;
	
	private SessionController sc;;

	@FXML
	private void initialize() {
		registerLabel.setId(Styles.Login.registerLabelDefault);
		loginWarning.setText("");
		loginWarning.setId(Styles.Common.warningLabel);
		profileDAO = ProfileDAO.getInstance();
		sc = SessionController.getInstance(); 
		userClient = new UserHTTPClient();
		configClient = new ConfigurationHTTPClient();
	}

	@FXML
	private void validateLogin() {
		boolean loginValid = FXUtils.textfieldTextIsNotNullOrEmpty(loginField);
		boolean passValid = FXUtils.textfieldTextIsNotNullOrEmpty(passwordField);
		boolean serverValid = FXUtils.textfieldTextIsNotNullOrEmpty(serverField);
		if (loginValid && passValid && serverValid) {
			User user = new User();
			user.setLogin(loginField.getText());
			user.setPassword(passwordField.getText());
			try {
				configClient.configureServer(serverField.getText());
				userClient.login(user);
				sc.setLoggerUser(user);
				sc.setClient(new WebSocketClient(user.getLogin(), Origin.CHAT));
				valid = true;
				windowStage.close();
			} catch (Exception e) {
				DialogBuilder.warn().exceptionContent(e).alert().showAndWait();
			}
		} else {
			loginWarning.setText("Por favor introduzca un login, una contraseña y la direccion de un servidor para poder iniciar sesión en la aplicacion");
		}
	}
	
	@FXML
	private void registerUser() {
		if(registerWindow == null) {
			registerWindow = ApplicationModal.build(RegisterUserWindow.class, primaryStage);
			RegisterUserController c = registerWindow.getController();
			c.setOnSuccessfulRegister(() -> {
				valid = true;
				windowStage.close();
			});
		}
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
