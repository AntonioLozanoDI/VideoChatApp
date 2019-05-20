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

import application.model.ProfileModel;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class ProfileRepository {
	
	private Logger logger = ApplicationLoggers.modelLogger;
	
	private final String insertStmt = "INSERT INTO Profile (UserId,Nombre,Apellido1,Apellido2) VALUES (?,?,?,?);";

	private final String deleteStmt = "DELETE FROM Profile WHERE id = ? AND UserId = ? AND Nombre = ? AND Apellido1 = ? AND Apellido2 = ?;";

	private final String selectAllStmt = "SELECT * FROM Profile;";

	private final String lastIdStmt = "SELECT seq FROM sqlite_sequence WHERE name = ?;";
	
	private final String updateStmt = "UPDATE Profile SET UserId = ?, Nombre = ?, Apellido1 = ?, Apellido2 = ?  WHERE id = ?;";

	private Connection con = null;
	
	public ProfileRepository() {
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
	public List<ProfileModel> findAll() {
		List<ProfileModel> profiles = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			profiles = new ArrayList<>();
			while (rs.next()) {
				ProfileModel profile = new ProfileModel();
				profile.setId(rs.getInt(1));
				profile.setUserId(rs.getString(2));
				profile.setNombre(rs.getString(3));
				profile.setApellido1(rs.getString(4));
				profile.setApellido2(rs.getString(5));
				profiles.add(profile);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into AudioSettings table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return profiles == null ? Collections.emptyList() : profiles;
	}

	public int findLastSettingId() {
		int id = -1;
		try {
			PreparedStatement st = con.prepareStatement(lastIdStmt);
			st.setString(1, "Profile");
			ResultSet rs = st.executeQuery();
			id = rs.getInt(1);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement to sqlite_sequence table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return id;
	}

	public void insert(ProfileModel profile) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt);
			st.setString(1, profile.getUserId());
			st.setString(2, profile.getNombre());
			st.setString(3, profile.getApellido1());
			st.setString(4, profile.getApellido2());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into AudioSettings table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(ProfileModel profile) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setInt(1, profile.getId());
			st.setString(2, profile.getUserId());
			st.setString(3, profile.getNombre());
			st.setString(4, profile.getApellido1());
			st.setString(5, profile.getApellido2());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into AudioSettings table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void update(ProfileModel profile) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			st.setString(1, profile.getUserId());
			st.setString(2, profile.getNombre());
			st.setString(3, profile.getApellido1());
			st.setString(4, profile.getApellido2());
			st.setInt(5, profile.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into AudioSettings table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
