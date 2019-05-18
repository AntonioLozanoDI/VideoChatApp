package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProfileModel {

	private IntegerProperty idProperty;
	private StringProperty userNameProperty;

	public ProfileModel() {
		super();
		idProperty = new SimpleIntegerProperty();
		userNameProperty = new SimpleStringProperty();
	}

	public int getId() {
		return idProperty.get();
	}

	public void setId(int id) {
		idProperty.set(id);
	}

	public String getUserName() {
		return userNameProperty.get();
	}

	public void setUserName(String userName) {
		userNameProperty.set(userName);
	}

}
