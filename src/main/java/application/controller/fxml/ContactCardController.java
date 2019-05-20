package application.controller.fxml;

import application.controller.fxml.MainController.Callback;
import application.model.ContactModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ContactCardController {

	@FXML
	private AnchorPane contactPane;
	@FXML
	private Label nameLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private ImageView contactIcon;
	
	private ContactModel contact;
	
	private Callback callback;
	
	public void setContactData(ContactModel contact) {
		this.contact = contact;
		contact.fullNameProperty().addListener(
				(observable,oldValue,newValue) -> {	nameLabel.setText(newValue); });
		
		nameLabel.setText(contact.getNombreCompleto());
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
		contactPane.setOnMouseClicked((event) -> {
			this.callback.call(contact);
		});
	}
	
}
