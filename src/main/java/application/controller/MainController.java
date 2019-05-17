package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import application.model.ContactModel;
import application.model.dao.ContactDAO;
import application.view.component.ContactCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class MainController {

	@FXML
	private VBox leftPaneVBox;
	@FXML
	private ListView<Pane> listView;
	@FXML
	private AnchorPane listPane;
	@FXML
	private AnchorPane rightPane;
	@FXML
	private ImageView camIcon;

	private Stage primaryStage;

	private List<ContactModel> contacts = new ArrayList<>();

	private ObservableList<Pane> contactCards = FXCollections.<Pane>observableArrayList();
	
	private VideoStreamingController videoStreamingController;
	
	private ContactDAO contactDAO;
	
	private StackPane videoScreen;

	@FXML
	private void initialize() {
		contactDAO = ContactDAO.getInstance();
		camIcon.setImage(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.camIcon).toImage());
		camIcon.setFitHeight(80);
		camIcon.setFitWidth(80);
		setFirstContactCard();
		setupContacts();
	}

	private void setFirstContactCard() {
		// TODO Auto-generated method stub
		
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void setupContacts() {
		contactDAO.getAllContacts();
		List<ContactCard> demo = ContactCard.demoData();
		for(ContactCard cc : demo)
			contactCards.add(cc.getPane());
		
		listView.setItems(contactCards);
	}

	public void setupVideoScreen() {
		URL resource = null;
		try {
			FXMLLoader screenLoader = new FXMLLoader();
			resource = ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.videoScreenView).toURL();
			screenLoader.setLocation(resource);
			videoScreen = screenLoader.load();
			videoScreen.prefHeightProperty().bind(rightPane.heightProperty());
			videoScreen.prefWidthProperty().bind(rightPane.widthProperty());
			videoStreamingController = screenLoader.getController();
			videoStreamingController.setMainController(this);
			rightPane.getChildren().add(videoScreen);
		} catch (Exception e) {
			System.out.println(LoggingUtils.getStackTrace(e));
		}
	}
	
	public void hideVideoScreen() {
		rightPane.getChildren().clear();
	}
}
