package application.controller.fxml;

import com.sp.fxutils.validation.FXUtils;

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
		boolean userIdValid = FXUtils.textfieldTextIsNotNullOrEmpty(userIdTF);
		boolean nombreValid = FXUtils.textfieldTextIsNotNullOrEmpty(nombreTF);
		boolean apellido1Valid = FXUtils.textfieldTextIsNotNullOrEmpty(apellido1TF);
		boolean apellido2Valid = FXUtils.textfieldTextIsNotNullOrEmpty(apellido2TF);
		if(userIdValid && nombreValid && apellido1Valid && apellido2Valid) {
			contact = new ContactModel();
			contact.setNombre(nombreTF.getText());
			contact.setPrimerApellido(apellido1TF.getText());
			contact.setSegundoApellido(apellido2TF.getText());
			contact.setLogin(userIdTF.getText());
			formStage.close();
		}
	}

	public void setWindowStage(Stage formStage) {
		this.formStage = formStage;
	}

	public ContactModel getContact() {
		return contact;
	}
}
