package application.model;

import java.util.Comparator;

import javafx.beans.property.StringProperty;

public class ContactModel {

	public static final Comparator<ContactModel> ID_COMPARATOR = (model1, model2) -> Integer.compare(model1.getDataId(),model2.getDataId());

	private int contactId;

	private int profileId;
	
	private PersonData personData;

	public ContactModel() {
		this.personData = new PersonData();
	}

	public ContactModel(int profileId) {
		this.profileId = profileId;
		this.personData = new PersonData();
	}
	
	public boolean isNew() {
		return contactId < 1;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public PersonData getPersonData() {
		return personData;
	}

	public void setPersonData(PersonData personData) {
		this.personData = personData;
	}

	public int getDataId() {
		return personData.getDataId();
	}

	public String getLogin() {
		return personData.getLogin();
	}

	public String getNombre() {
		return personData.getNombre();
	}

	public String getPrimerApellido() {
		return personData.getPrimerApellido();
	}

	public String getSegundoApellido() {
		return personData.getSegundoApellido();
	}

	public String getNombreCompleto() {
		return personData.getNombreCompleto();
	}

	public StringProperty fullNameProperty() {
		return personData.fullNameProperty();
	}

	public void setDataId(int id) {
		personData.setDataId(id);
	}

	public void setLogin(String login) {
		personData.setLogin(login);
	}

	public void setNombre(String nombre) {
		personData.setNombre(nombre);
	}

	public void setPrimerApellido(String apellido1) {
		personData.setPrimerApellido(apellido1);
	}

	public void setSegundoApellido(String apellido2) {
		personData.setSegundoApellido(apellido2);
	}

}
