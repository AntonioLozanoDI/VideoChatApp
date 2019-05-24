package application.model;

import java.util.Comparator;

import com.diproject.commons.model.User;

import javafx.beans.property.StringProperty;

public class ProfileModel  {

	public static final Comparator<ProfileModel> ID_COMPARATOR = (model1,model2) -> Integer.compare(model1.getProfileId(), model2.getProfileId()); 
	
	private int profileId;
	
	private PersonData personData;

	public ProfileModel() {
		this.personData = new PersonData();
	}
	
	public boolean isNew() {
		return profileId < 1;
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

	public static ProfileModel fromUser(User user) {
		String[] params = user.getUsername().split(" ");
		ProfileModel model = new ProfileModel();
		model.setLogin(user.getLogin());
		model.setNombre(params[0]);
		model.setPrimerApellido(params[1]);
		model.setSegundoApellido(params[2]);
		return model;
	}
	
	public static void editUserFromProfile(User user, ProfileModel profileModel) {
		user.setUsername(String.format("%s %s %s", profileModel.getNombre(),profileModel.getPrimerApellido(),profileModel.getSegundoApellido()));
	}
}
