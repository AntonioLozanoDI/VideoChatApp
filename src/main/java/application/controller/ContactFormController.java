package application.controller;

import application.model.ContactModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContactFormController {

	@FXML
	private TextField userIdTF;
	@FXML
	private TextField nombreTF;
	@FXML
	private TextField apellido1TF;
	@FXML
	private TextField apellido2TF;

	private Stage formStage;
	
	private ContactModel contact;

	@FXML
	private void createContact() {

		formStage.close();
	}

	public void setWindowStage(Stage formStage) {
		this.formStage = formStage;
	}

	public ContactModel getContact() {
		return contact;
	}
}
