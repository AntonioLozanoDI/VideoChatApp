package application.model;

import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContactModel {

	private int id;
	
	private String userId;

	private StringProperty fullNameProperty;

	private StringProperty nombreProperty;

	private StringProperty primerApellidoProperty;

	private StringProperty segundoApellidoProperty;
	
	public static final Comparator<ContactModel> ID_COMPARATOR = (model1,model2) -> Integer.compare(model1.getId(), model2.getId()); 

	public ContactModel(String nombre, String apellido1, String apellido2) {
		super();

		nombreProperty = new SimpleStringProperty(nombre);
		primerApellidoProperty = new SimpleStringProperty(apellido1);
		segundoApellidoProperty = new SimpleStringProperty(apellido2);
		fullNameProperty = new SimpleStringProperty(apellido2);

		updateFullName();

		nombreProperty.addListener((observable, oldValue, newValue) -> {
			updateFullName();
		});
		primerApellidoProperty.addListener((observable, oldValue, newValue) -> {
			updateFullName();
		});
		segundoApellidoProperty.addListener((observable, oldValue, newValue) -> {
			updateFullName();
		});
	}

	private void updateFullName() {
		fullNameProperty.set(String.format("%s %s %s", getNombre(), getPrimerApellido(), getSegundoApellido()));
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombreProperty.get();
	}

	public String getPrimerApellido() {
		return primerApellidoProperty.get();
	}

	public String getSegundoApellido() {
		return segundoApellidoProperty.get();
	}

	public String getNombreCompleto() {
		return fullNameProperty.get();
	}

	public StringProperty fullNameProperty() {
		return fullNameProperty;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		nombreProperty.set(nombre);
	}

	public void setPrimerApellido(String apellido1) {
		primerApellidoProperty.set(apellido1);
	}

	public void setSegundoApellido(String apellido2) {
		segundoApellidoProperty.set(apellido2);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
