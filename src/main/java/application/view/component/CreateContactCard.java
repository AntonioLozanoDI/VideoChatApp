package application.view.component;

import application.controller.ContactCardController;
import application.controller.CreateContactCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class CreateContactCard extends ContactListComponent<AnchorPane> {

	private CreateContactCardController controller;

	public static CreateContactCard create() {
		CreateContactCard ccc = null;
		try {
			// Load the fxml file and load root pane.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.contactCard).toURL());
			AnchorPane pane = (AnchorPane) loader.load();

			// Set the data into the controller.
			CreateContactCardController controller = loader.getController();
			
			// Create contact card.
			ccc = new CreateContactCard();
			ccc.controller = controller;
			ccc.parent = pane;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ccc;
	}

}
