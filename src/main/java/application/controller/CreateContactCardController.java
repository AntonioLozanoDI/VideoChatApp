package application.controller;

import com.sp.dialogs.DialogBuilder;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class CreateContactCardController {

	@FXML
	private AnchorPane createContactPane;
	
	@FXML
	private ImageView iconContactView;
	
	@FXML
	private void initialize() {
		iconContactView.setImage(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.contactAdd).toImage());
		createContactPane.setOnMouseClicked((event) ->{
			if(event.getButton().equals(MouseButton.PRIMARY))
				showCreateContactForm();
		});
	}

	private void showCreateContactForm() {
		DialogBuilder.info().finish().alert().showAndWait();
	}
}
