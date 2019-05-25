package application.controller.fxml;

import application.controller.session.SessionController;
import application.model.ContactModel;
import application.model.dao.ContactDAO;
import application.view.component.ContactCard;
import application.view.component.CreateContactCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utils.constants.Constants;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public class MainController {

	@FunctionalInterface
	public interface Callback {
		void call(ContactModel contact);
	}

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

	private SessionController sc;

	private ContactDAO contactDAO;

	private StackPane videoScreen;

	private Callback callback;

	private ContactModel selectedContact;

	private boolean alreadyAdded;

	private boolean liveStreaming;

	@FXML
	private void initialize() {
		contactDAO = ContactDAO.getInstance();
		sc = SessionController.getInstance();

		camIcon.setImage(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.camIcon).toImage());
		camIcon.setFitHeight(80);
		camIcon.setFitWidth(80);

		contactDAO.addOnContactCreated((contact) -> addContact(contact));

		callback = (contact) -> {
			selectedContact = contact;
			setupVideoScreen();
			videoStreamingController.setSelectedContact(selectedContact.getLogin());
		};

		setFirstContactCard();
		setupContacts();

		listView.setOnMouseClicked((event) -> {
			if (event.getButton().equals(MouseButton.SECONDARY) && !liveStreaming) {
				listView.getSelectionModel().clearSelection();
				selectedContact = null;
				hideVideoScreen();
			}
		});
	}

	private void addContact(ContactModel contactModel) {
		contactCards.add(ContactCard.fromContact(contactModel, callback).getParent());
	}

	private void setFirstContactCard() {
		contactCards.add(CreateContactCard.create());
	}

	private void setupContacts() {
		contactDAO.readAllContactsFromLoggedUser(sc.getLoggerUser()).stream().forEach(cc -> contactCards.add(ContactCard.fromContact(cc, callback).getParent()));
		listView.setItems(contactCards);
	}

	public void setupVideoScreen() {
		if (videoScreen == null) {
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

		if (!alreadyAdded) {
			rightPane.getChildren().add(videoScreen);
			alreadyAdded = true;
		}
	}

	public void hideVideoScreen() {
		rightPane.getChildren().clear();
		alreadyAdded = false;
	}
}
