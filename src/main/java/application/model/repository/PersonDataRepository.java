package application.model.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import application.model.PersonData;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class PersonDataRepository {

	private Logger logger = ApplicationLoggers.modelLogger;
	
	private final String insertStmt = "INSERT INTO PersonData (login,nombre,apellido1,apellido2) VALUES (?,?,?,?);";

	private final String deleteStmt = "DELETE FROM PersonData WHERE id = ? AND login = ? AND nombre = ? AND apellido1 = ? AND apellido2 = ?;";

	private final String selectAllStmt = "SELECT * FROM PersonData;";
	
	private final String selectByLogin = "SELECT * FROM PersonData WHERE login = ?;";

	private final String lastIdStmt = "SELECT seq FROM sqlite_sequence WHERE name = ?;";

	private final String updateStmt = "UPDATE PersonData SET login = ?, nombre = ?, apellido1 = ?, apellido2 = ? WHERE id = ?;";

	private Connection con = null;

	public PersonDataRepository() {//pass Connection by constructor
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
	
	public List<PersonData> findAll() {
		List<PersonData> profiles = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			profiles = new ArrayList<>();
			while (rs.next()) {
				PersonData data = new PersonData();
				data.setDataId(rs.getInt(1));
				data.setLogin(rs.getString(2));
				data.setNombre(rs.getString(3));
				data.setPrimerApellido(rs.getString(4));
				data.setSegundoApellido(rs.getString(5));
				profiles.add(data);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return profiles == null ? Collections.emptyList() : profiles;
	}
	
	public int findLastPersonId() {
		int id = -1;
		try {
			PreparedStatement st = con.prepareStatement(lastIdStmt);
			st.setString(1, "PersonData");
			ResultSet rs = st.executeQuery();
			id = rs.getInt(1);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement to sqlite_sequence table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return id;
	}
	
	public List<PersonData> findByLogin(String login) {
		List<PersonData> profiles = null;
		try {
			PreparedStatement st = con.prepareStatement(selectByLogin);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			profiles = new ArrayList<>();
			while (rs.next()) {
				PersonData data = new PersonData();
				data.setDataId(rs.getInt(1));
				data.setLogin(rs.getString(2));
				data.setNombre(rs.getString(3));
				data.setPrimerApellido(rs.getString(4));
				data.setSegundoApellido(rs.getString(5));
				profiles.add(data);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return profiles == null ? Collections.emptyList() : profiles;
	}
	
	public Optional<PersonData> exists(String login) {
		List<PersonData> data = null;
		try {
			PreparedStatement st = con.prepareStatement(selectByLogin);
			st.setString(1, login);
			ResultSet rs = st.executeQuery();
			data = new ArrayList<>();
			while (rs.next()) {
				PersonData person = new PersonData();
				person.setDataId(rs.getInt(1));
				person.setLogin(rs.getString(2));
				person.setNombre(rs.getString(3));
				person.setPrimerApellido(rs.getString(4));
				person.setSegundoApellido(rs.getString(5));
				data.add(person);
			}
			if(data.size() > 1) 
				throw new RuntimeException();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return data.isEmpty() ? Optional.empty() : Optional.of(data.get(0));
	}
	
	public void insert(PersonData person) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt);
			st.setString(1, person.getLogin());
			st.setString(2, person.getNombre());
			st.setString(3, person.getPrimerApellido());
			st.setString(4, person.getSegundoApellido());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
	
	public void update(PersonData person) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			st.setString(1, person.getLogin());
			st.setString(2, person.getNombre());
			st.setString(3, person.getPrimerApellido());
			st.setString(4, person.getSegundoApellido());
			st.setInt(5, person.getDataId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
	
	public void delete(PersonData person) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setInt(1, person.getDataId());
			st.setString(2, person.getLogin());
			st.setString(3, person.getNombre());
			st.setString(4, person.getPrimerApellido());
			st.setString(5, person.getSegundoApellido());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into PersonData table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
