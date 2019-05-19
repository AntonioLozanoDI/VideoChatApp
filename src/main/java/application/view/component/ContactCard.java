package application.view.component;

import application.controller.ContactCardController;
import application.model.ContactModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class ContactCard extends ContactListComponent<AnchorPane> {

	private ContactCardController controller;
	
	private ContactModel contactInfo;
	
	public static ContactCard fromContact(ContactModel contact) {
		ContactCard cc = null;
		try {
			// Load the fxml file and load root pane.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.ContactCard).toURL());
			AnchorPane pane = (AnchorPane) loader.load();

			// Set the data into the controller.
			ContactCardController controller = loader.getController();
			controller.setContactData(contact);
			
			// Create contact card.
			cc = new ContactCard();
			cc.contactInfo = contact;
			cc.controller = controller;
			cc.parent = pane;
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
}
