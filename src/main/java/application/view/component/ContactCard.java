package application.view.component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import application.controller.ContactCardController;
import application.model.ContactModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class ContactCard {

	private ContactCardController controller;
	
	private ContactModel contactInfo;

	private Pane pane; 
	
	public static ContactCard fromContact(ContactModel contact) {
		ContactCard cc = null;
		try {
			// Load the fxml file and load root pane.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.contactCard).toURL());
			AnchorPane pane = (AnchorPane) loader.load();

			// Set the data into the controller.
			ContactCardController controller = loader.getController();
			controller.setContactData(contact);
			
			// Create contact card.
			cc = new ContactCard();
			cc.contactInfo = contact;
			cc.controller = controller;
			cc.pane = pane;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cc;
	}

	public ContactCardController getController() {
		return controller;
	}

	public ContactModel getContactInfo() {
		return contactInfo;
	}
	
	public Pane getPane() {
		return pane;
	}

	public static List<ContactCard> demoData() {
		ContactModel c = new ContactModel("Maria", "Diaz", "Mu�oz");
		ContactModel c2 = new ContactModel("David", "Robles", "Garcia");
		ContactModel c3 = new ContactModel("Susana", "Marquez", "Gonzalez");
		ContactModel c4 = new ContactModel("Carlos", "Ortega", "Martinez");
		
		return Arrays.asList(c,c2,c3,c4).stream().map(ContactCard::fromContact).collect(Collectors.toList());
	}
}
