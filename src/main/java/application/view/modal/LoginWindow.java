package application.view.modal;

import java.io.IOException;
import java.net.URL;

import com.sp.dialogs.DialogBuilder;

import application.controller.fxml.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class LoginWindow extends ApplicationModal {

	private Stage formStage;
	private LoginController controller;

	@Override
	protected void buildModal(Stage owner) {
		URL resource = null;
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.LoginWindow).toURL();
			loader.setLocation(resource);
			BorderPane page = loader.load();

			// Create the dialog Stage.
			formStage = new Stage();
			formStage.initModality(Modality.WINDOW_MODAL);
			formStage.initOwner(owner);
			
			Scene scene = new Scene(page);
			scene.getStylesheets().add(ApplicationResourceProvider.getCSSFile(Constants.Files.CSS.defaultTheme).toURL().toExternalForm());
			formStage.setScene(scene);
			formStage.setResizable(false);

			// Set the stage into the controller.
			controller = (LoginController) loader.getController();
			controller.setPrimaryStage(owner);
			controller.setWindowStage(formStage);

			logger.finer(String.format("%s successfully loaded. Resource loaded: %s", getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath())));

		} catch (IOException e) {
			DialogBuilder.error()
			.title(String.format("FXML view load error", e.getClass().getSimpleName()))
			.header(String.format("An error ocurred while attempting to load \'.fxml\' file: %n%s", LoggingUtils.cleanFXMLPath(resource.getPath())))
			.exceptionContent(e)
			.alert().showAndWait();

			logger.severe(String.format("FXMLLoader couldn\'t load view from %s", LoggingUtils.cleanFXMLPath(resource.getPath())));
		}
	}
	
	@Override
	public void showView() {
		formStage.showAndWait();
		controller.checkLogin();
	}

}
