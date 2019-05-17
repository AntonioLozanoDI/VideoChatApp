package application.model.repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import application.model.AudioSettingsModel;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class DefaultAudioSettingsRepository {

	private int defaultConfigId;
	
	private Logger logger = ApplicationLoggers.modelLogger;

	private final String selectAllStmt = "SELECT * FROM DefaultAudioSettings;";
	
	private final String updateStmt = "UPDATE DefaultAudioSettings SET ConfigId = ? WHERE ConfigId = ?;";

	private Connection con = null;

	public DefaultAudioSettingsRepository() {
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
	
	public Integer getDefault() {
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				defaultConfigId = rs.getInt(1);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into DefaultAudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return defaultConfigId;
	}
	
	public void setDefault(AudioSettingsModel setting) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			st.setInt(1, setting.getId());
			st.setInt(2, defaultConfigId);
			st.executeUpdate();
			defaultConfigId = setting.getId();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into DefaultAudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
