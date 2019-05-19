package application.model.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import application.model.ContactModel;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class ContactRepository {

	private Logger logger = ApplicationLoggers.modelLogger;

	private final String insertStmt = "INSERT INTO Contact (nombre,apellido1,apellido2) VALUES (?,?,?);";

	private final String deleteStmt = "DELETE FROM Contact WHERE id = ? AND nombre = ? AND apellido1 = ? AND apellido2 = ?;";

	private final String selectAllStmt = "SELECT * FROM Contact;";

	private final String lastIdStmt = "SELECT seq FROM sqlite_sequence WHERE name = ?;";

	private final String updateStmt = "UPDATE Contact SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE id = ?;";

	private Connection con = null;

	public ContactRepository() {
		try {
			SQLiteConnectionManager man = new SQLiteConnectionManager(DatabaseHelper.getPropertiesToLoad());
			con = man.getConnection();
			logger.finer(String.format("%s successfully initialized.", getClass().getSimpleName()));
		} catch (IOException e) {
			String trace = String.format(
					"An error ocurred in %s during initialization. Unexpected error trying to initialize %s.%n%s",
					getClass().getSimpleName(), SQLiteConnectionManager.class.getSimpleName(),
					LoggingUtils.getStackTrace(e));
			logger.severe(trace);
			logger.severe("Exiting from application.");
			System.exit(-1);
		}
	}

	public List<ContactModel> findAll() {
		List<ContactModel> contacts = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			contacts = new ArrayList<>();
			while (rs.next()) {
				ContactModel contact = new ContactModel(rs.getString(2), rs.getString(3), rs.getString(4));
				contact.setId(rs.getInt(1));
				contacts.add(contact);
			}
			Collections.sort(contacts, ContactModel.ID_COMPARATOR);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into Contact table in %s%n%s",
					getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return contacts == null ? Collections.emptyList() : contacts;
	}

	public int findLastSettingId() {
		int id = -1;
		try {
			PreparedStatement st = con.prepareStatement(lastIdStmt);
			st.setString(1, "Contact");
			ResultSet rs = st.executeQuery();
			id = rs.getInt(1);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement to sqlite_sequence table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return id;
	}

	public void insert(ContactModel contact) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt);
			st.setString(1, contact.getNombre());
			st.setString(2, contact.getPrimerApellido());
			st.setString(3, contact.getSegundoApellido());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into Contact table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(ContactModel contact) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setInt(1, contact.getId());
			st.setString(2, contact.getNombre());
			st.setString(3, contact.getPrimerApellido());
			st.setString(4, contact.getSegundoApellido());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into Contact table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void update(ContactModel contact) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			st.setString(1, contact.getNombre());
			st.setString(2, contact.getPrimerApellido());
			st.setString(3, contact.getSegundoApellido());
			st.setInt(4, contact.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into Contact table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
