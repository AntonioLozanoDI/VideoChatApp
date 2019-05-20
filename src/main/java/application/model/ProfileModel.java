package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProfileModel {

	private IntegerProperty idProperty;
	private StringProperty userIdProperty;
	private StringProperty nombreProperty;
	private StringProperty apellido1Property;
	private StringProperty apellido2Property;

	public ProfileModel() {
		super();
		idProperty = new SimpleIntegerProperty();
		userIdProperty = new SimpleStringProperty();
		nombreProperty = new SimpleStringProperty();
		apellido1Property = new SimpleStringProperty();
		apellido2Property = new SimpleStringProperty();
	}

	public int getId() {
		return idProperty.get();
	}

	public void setId(int id) {
		idProperty.set(id);
	}

	public String getUserId() {
		return userIdProperty.get();
	}

	public void setUserId(String userName) {
		userIdProperty.set(userName);
	}

	public String getNombre() {
		return nombreProperty.get();
	}

	public void setNombre(String nombre) {
		nombreProperty.set(nombre);
	}

	public String getApellido1() {
		return apellido1Property.get();
	}

	public void setApellido1(String apellido1) {
		apellido1Property.set(apellido1);
	}

	public String getApellido2() {
		return apellido2Property.get();
	}

	public void setApellido2(String apellido2) {
		apellido2Property.set(apellido2);
	}
}
