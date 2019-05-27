package application.view.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class CreateContactCard extends ContactListComponent<AnchorPane> {

	public static AnchorPane create() {
		AnchorPane pane = null;
		try {
			// Load the fxml file and load root pane.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ApplicationResourceProvider.getFXMLFile(Constants.Files.FXML.CreateContactCard).toURL());
			pane = (AnchorPane) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pane;
	}

}
