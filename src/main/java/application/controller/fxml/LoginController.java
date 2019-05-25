package application.controller.fxml;

import java.util.stream.Collectors;

import com.diproject.commons.model.Origin;
import com.diproject.commons.model.User;
import com.diproject.commons.utils.rest.clients.ConfigurationClient;
import com.diproject.commons.utils.rest.clients.UserClient;
import com.diproject.commons.utils.ws.WebSocketClient;
import com.sp.dialogs.DialogBuilder;
import com.sp.fxutils.validation.FXUtils;

import application.controller.session.SessionController;
import application.model.ProfileModel;
import application.model.dao.ProfileDAO;
import application.view.modal.ApplicationModal;
import application.view.modal.RegisterUserWindow;
import http.status.exceptions.Http404NotFoundException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.constants.Styles;

public class LoginController {

	@FXML
	private ComboBox<String> loginField;
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
	
	private UserClient userClient;
	
	private ConfigurationClient configClient;
	
	private ProfileDAO profileDAO;
	
	private SessionController sc;

	@FXML
	private void initialize() {
		registerLabel.setId(Styles.Login.registerLabelDefault);
		loginWarning.setText("");
		loginWarning.setId(Styles.Common.warningLabel);
		profileDAO = ProfileDAO.getInstance();
		loginField.setEditable(true);
		loginField.setItems(FXCollections.observableArrayList(profileDAO.readAllProfiles().stream().map(prof -> prof.getLogin()).collect(Collectors.toList())));
		loginField.getSelectionModel().selectFirst();
		sc = SessionController.getInstance(); 
		userClient = new UserClient();
		configClient = new ConfigurationClient();
	}

	@FXML
	private void validateLogin() {
		String login = loginField.getSelectionModel().getSelectedItem();
		boolean passValid = FXUtils.textfieldTextIsNotNullOrEmpty(passwordField);
		boolean serverValid = FXUtils.textfieldTextIsNotNullOrEmpty(serverField);
		if (login != null && !login.isEmpty() && passValid && serverValid) {
			User user = new User();
			user.setLogin(login);
			user.setPassword(passwordField.getText());
			try {
				configClient.configureServer(serverField.getText());
				userClient.login(user);
				loadProfile(user);
				valid = true;
				windowStage.close();
			} catch (Http404NotFoundException e) {
				loginWarning.setText("No se ha encontrado el usuario en el sistema");
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

	private void loadProfile(User user) {
		if(!userPreviouslyLoggedOn(user.getLogin())) {
			User found = userClient.find(user.getLogin());
			ProfileModel newLoggedUser = ProfileModel.fromUser(found);
			profileDAO.saveProfile(newLoggedUser);
			sc.setLoggedUser(newLoggedUser);
		}
		sc.setServerAddress(serverField.getText());
		sc.setClient(new WebSocketClient(user.getLogin(), Origin.VIDEO));
	}
	
	private boolean userPreviouslyLoggedOn(String login) {
		boolean userPreviouslyLoggedOn = false;
		ProfileModel profile = profileDAO.findByLogin(login);
		if(profile != null) {
			sc.setLoggedUser(profile);
			userPreviouslyLoggedOn = true;
		}	
		return userPreviouslyLoggedOn;
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
			System.exit(0);
	}
}
