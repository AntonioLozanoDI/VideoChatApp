package application.view.component;

import application.controller.CreateContactCardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class CreateContactCard extends ContactListComponent<AnchorPane> {

	private CreateContactCardController controller;

	public static AnchorPane create() {
		AnchorPane pane = null;
		try {
			// Load the fxml file and load root pane.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.CreateContactCard).toURL());
			pane = (AnchorPane) loader.load();

			// Set the data into the controller.
			CreateContactCardController controller = loader.getController();
			
			//ccc.data = data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pane;
	}

}
