package application.model;

import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonData {

	private int id;
	
	private String login;

	private StringProperty fullNameProperty;

	private StringProperty nombreProperty;

	private StringProperty primerApellidoProperty;

	private StringProperty segundoApellidoProperty;
	
	public static final Comparator<PersonData> ID_COMPARATOR = (model1,model2) -> Integer.compare(model1.getDataId(), model2.getDataId()); 

	public PersonData() {
		this(null,null,null);
	}
	
	public PersonData(String nombre, String apellido1, String apellido2) {
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
	
	public boolean isNew() {
		return id < 1;
	}

	private void updateFullName() {
		fullNameProperty.set(String.format("%s %s %s", getNombre(), getPrimerApellido(), getSegundoApellido()));
	}

	public int getDataId() {
		return id;
	}

	public String getLogin() {
		return login;
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

	public void setDataId(int id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
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
}
