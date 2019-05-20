package application.controller.fxml;

import com.diproject.commons.model.Origin;
import com.diproject.commons.model.Payload;
import com.diproject.commons.model.message.types.Message;
import com.diproject.commons.utils.Utils;
import com.diproject.commons.utils.payload.PayloadFactory;
import com.diproject.commons.utils.ws.PayloadHandler;
import com.diproject.commons.utils.ws.WebSocketClient;

import application.controller.session.SessionController;
import application.controller.ws.VideoChatHandler;
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

	private ContactDAO contactDAO;

	private StackPane videoScreen;
	
	private Callback callback;
	
	private ContactModel selectedContact;
	
	private SessionController sc;
	
	private VideoChatHandler vch;
	
	private boolean alreadyAdded;

	@FXML
	private void initialize() {
		contactDAO = ContactDAO.getInstance();
		camIcon.setImage(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.camIcon).toImage());
		camIcon.setFitHeight(80);
		camIcon.setFitWidth(80);
		
		sc = SessionController.getInstance();
		vch = VideoChatHandler.getInstance();
		
		contactDAO.addOnContactCreated((contact) -> addContact(contact));

		callback = (contact) -> { 
			selectedContact = contact; 
			setupVideoScreen();
		};
		
		setFirstContactCard();
		setupContacts();
		
		listView.setOnMouseClicked((event) -> {
			if (event.getButton().equals(MouseButton.SECONDARY)) {
				listView.getSelectionModel().clearSelection();
				selectedContact = null;
				hideVideoScreen();
			}
		});
	}
	
	private void addContact(ContactModel contactModel) {
		contactCards.add(ContactCard.fromContact(contactModel,callback).getParent());
	}

	private void setFirstContactCard() {
		contactCards.add(CreateContactCard.create());
	}

	private void setupContacts() {
		contactDAO.getAllContacts().stream().forEach(cc -> contactCards.add(ContactCard.fromContact(cc,callback).getParent()));
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
		
		if(!alreadyAdded) {
			rightPane.getChildren().add(videoScreen);
			alreadyAdded = true;
		}
	}

	private void initCall() {
		//envio peticion
		Message msg = new Message();
		msg.setDestination("jose");
		msg.setOrigin("Toni");
		msg.setMessage("ole");
		WebSocketClient wsc = new WebSocketClient("Toni", Origin.CHAT);
		wsc.setPayloadHandler(new PayloadHandler() {
			@Override
			public void setWebSocketClient(WebSocketClient webSocketClient) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handlePayload(Payload payload) {
				Message ex = PayloadFactory.extract(payload);
				System.out.println(Utils.JSON.toJson(ex));
			}
		});
		wsc.connect();
		wsc.send(PayloadFactory.create(msg));
		//espero peticion
		
		
	}


	public void hideVideoScreen() {
		rightPane.getChildren().clear();
		alreadyAdded = false;
	}
}
