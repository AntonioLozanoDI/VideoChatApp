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

import application.model.AudioSettingsModel;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class AudioSettingsRepository {
	
	private Logger logger = ApplicationLoggers.modelLogger;

	private final String insertStmt = "INSERT INTO AudioSettings (config_name,out_sample_rate,out_sample_size_bits,out_channels,out_signed,out_big_endian,in_sample_rate,in_sample_size_bits,in_channels,in_signed,in_big_endian) VALUES (?,?,?,?,?,?,?,?,?,?,?);";

	private final String deleteStmt = "DELETE FROM AudioSettings WHERE config_name = ? AND out_sample_rate = ? AND out_sample_size_bits = ? AND out_channels = ? AND out_signed = ? AND out_big_endian = ? AND in_sample_rate = ? AND in_sample_size_bits = ? AND in_channels = ? AND in_signed = ? AND in_big_endian = ?;";

	private final String selectAllStmt = "SELECT * FROM AudioSettings;";

	private final String lastIdStmt = "SELECT seq FROM sqlite_sequence WHERE name = ?;";
	
	private final String updateStmt = "UPDATE AudioSettings SET out_sample_rate = ?, out_sample_size_bits = ?, out_channels = ?, out_signed = ?, out_big_endian = ?, in_sample_rate = ?, in_sample_size_bits = ?, in_channels = ?, in_signed = ?, in_big_endian = ? WHERE config_name = ?;";

	private Connection con = null;

	public AudioSettingsRepository() {
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

	public List<AudioSettingsModel> findAll() {
		List<AudioSettingsModel> settings = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			settings = new ArrayList<>();
			while (rs.next()) {
				AudioSettingsModel audio = new AudioSettingsModel();
				audio.setId(rs.getInt(1));
				audio.setConfigName(rs.getString(2));
				
				audio.setOutSampleRate(rs.getFloat(3));
				audio.setOutBitSize(rs.getInt(4));
				audio.setOutChannels(rs.getInt(5));
				
				audio.setOutSigned(rs.getString(6));
				audio.setOutBigEndian(rs.getString(7));
				
				audio.setInSampleRate(rs.getFloat(8));
				audio.setInBitSize(rs.getInt(9));
				audio.setInChannels(rs.getInt(10));
				
				audio.setInSigned(rs.getString(11));
				audio.setInBigEndian(rs.getString(12));

				settings.add(audio);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into AudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return settings == null ? Collections.emptyList() : settings;
	}

	public int findLastSettingId() {
		int id = -1;
		try {
			PreparedStatement st = con.prepareStatement(lastIdStmt);
			st.setString(1, "AudioSettings");
			ResultSet rs = st.executeQuery();
			id = rs.getInt(1);
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement to sqlite_sequence table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return id;
	}

	public void insert(AudioSettingsModel setting) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt);
			
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into AudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(AudioSettingsModel setting) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into AudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void update(AudioSettingsModel setting) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt);
			
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into AudioSettings table in %s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
}
