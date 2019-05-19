package application.controller;

import application.model.ContactModel;
import application.model.dao.ContactDAO;
import application.view.modal.ApplicationModal;
import application.view.modal.ContactForm;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public class CreateContactCardController {

	@FXML
	private AnchorPane createContactPane;
	
	@FXML
	private ImageView iconContactView;

	private Stage formStage;

	private ContactDAO contactDAO;
	
	@FXML
	private void initialize() {
		contactDAO = ContactDAO.getInstance();
		iconContactView.setImage(ApplicationResourceProvider.getPNGFile(Constants.Files.Images.contactAdd).toImage());
		createContactPane.setOnMouseClicked((event) ->{
			if(event.getButton().equals(MouseButton.PRIMARY))
				showCreateContactForm();
		});
	}

	private void showCreateContactForm() {
		ContactForm contactForm = ApplicationModal.build(ContactForm.class, formStage);
		contactForm.showView();
		ContactFormController controller = contactForm.getController();
		ContactModel contact = controller.getContact();
		contactDAO.saveContact(contact);
	}

	public void setWindowStage(Stage formStage) {
		this.formStage =formStage;
	}
}
