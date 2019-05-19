package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.model.ContactModel;
import application.model.dao.ContactDAO;
import application.view.component.ContactCard;
import application.view.component.CreateContactCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
		
		contactDAO.setOnContactCreated((contact) -> addContact(contact));
		
		listView.setOnMouseClicked( (event) -> {		
			if(event.getButton().equals(MouseButton.SECONDARY))
				listView.getSelectionModel().clearSelection();	
		});
	}
	
	private void addContact(ContactModel contactModel) {
		contactCards.add(ContactCard.fromContact(contactModel).getParent());
	}

	private void setFirstContactCard() {		
		contactCards.add(CreateContactCard.create());
	}
	
	private void setupContacts() {
		contactDAO.getAllContacts().stream().forEach(cc -> contactCards.add(ContactCard.fromContact(cc).getParent()));
		listView.setItems(contactCards);
	}

	public void setupVideoScreen() {
		if(videoScreen == null) {
			try {
				FXMLLoader screenLoader = new FXMLLoader();
				screenLoader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.VideoScreenView).toURL());
				videoScreen = screenLoader.load();
				videoScreen.prefHeightProperty().bind(rightPane.heightProperty());
				videoScreen.prefWidthProperty().bind(rightPane.widthProperty());
				videoStreamingController = screenLoader.getController();
				videoStreamingController.setMainController(this);
			} catch (Exception e) {
				System.out.println(LoggingUtils.getStackTrace(e));
			}
		}
		rightPane.getChildren().add(videoScreen);
	}
	
	public void hideVideoScreen() {
		rightPane.getChildren().clear();
	}
}
