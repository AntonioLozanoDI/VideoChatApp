package application.view;

import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;

import application.controller.MainController;
import application.controller.RootController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class VideoChatAppView extends ApplicationView {

	private Stage primaryStage;
	
	private BorderPane rootPane;
	private FXMLLoader rootLoader;
	private RootController rootController;
	
	private BorderPane mainPane;
	private FXMLLoader mainLoader;
	private MainController mainController;


	/*
	 * Es necesario dejar un constructor por defecto visible, para que se pueda
	 * invocar en el metodo <T extends ApplicationView> T launchView(Class<T>
	 * viewClass, Stage primaryStage) la creacion de una instancia de la vista
	 * deseada T view = viewClass.newInstance() en el tipo ApplicationView.
	 */
	
	public VideoChatAppView() {}

	@Override
	protected void buildParentView() {
		URL resource = null;
		try {
			rootLoader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.RootLayout).toURL();
			rootLoader.setLocation(resource);
			rootPane = rootLoader.load();
			rootController = rootLoader.getController();
		} catch(Exception e) {
			String trace = String.format(
					"An error ocurred during %s parent view initialization.%n\t- resource: %s%n\t- loader: %s%n\t- layout: %s%n\t- controller: %s",
					getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath()), rootLoader, rootPane, rootController);
			logger.log(Level.SEVERE, trace, e);
		}
	}

	@Override
	protected void buildChildrenViews() {
		URL resource = null;
		try {
			mainLoader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.MainView).toURL();
			mainLoader.setLocation(resource);
			mainPane = mainLoader.load();
			mainController = mainLoader.getController();
		} catch (Exception e) {
			String trace = String.format(
					"An error ocurred during %s child view initialization.%n\t- resource: %s%n\t- loader: %s%n\t- layout: %s%n\t- controller: %s",
					getClass().getSimpleName(), LoggingUtils.cleanFXMLPath(resource.getPath()), mainLoader, mainPane, mainController);
			logger.log(Level.SEVERE, trace, e);
		}
	}

	@Override
	protected void setScene(Stage stage) {
		this.primaryStage = Objects.requireNonNull(stage);
		rootPane.setCenter(mainPane);
		rootController.setMainController(mainController);
		rootController.setPrimaryStage(primaryStage);
		Scene scene = new Scene(rootPane,800,600);
		scene.getStylesheets().add(ApplicationResourceProvider.getCSSFile(Constants.Files.CSS.defaultTheme).toURL().toExternalForm());
		primaryStage.setTitle(Constants.Views.VideoChatAppTitle);
//		primaryStage.getIcons().add(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.applicationIcon3).toImage());//TODO icon
		primaryStage.setScene(scene);
	}
}
