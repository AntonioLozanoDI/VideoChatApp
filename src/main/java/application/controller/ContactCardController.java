package application.controller;

import application.model.ContactModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ContactCardController {

	@FXML
	private Label nameLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private ImageView contactIcon;
	
	public void setContactData(ContactModel contact) {
		contact.fullNameProperty().addListener(
				(observable,oldValue,newValue) -> {	nameLabel.setText(newValue); });
		
		nameLabel.setText(contact.getNombreCompleto());
	}
	
}
