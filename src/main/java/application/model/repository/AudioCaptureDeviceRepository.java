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
import application.model.CaptureDeviceModel;
import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class AudioCaptureDeviceRepository {

	private Logger logger = ApplicationLoggers.modelLogger;

	private final String insertStmt = "INSERT INTO AudioCaptureDevice (device_name,config_id) VALUES (?,?);";

	private final String deleteStmt = "DELETE FROM AudioCaptureDevice WHERE config_id = ?;";

	private final String selectAllStmt = "SELECT * FROM AudioCaptureDevice;";
	
	private final String updateStmt = "UPDATE AudioCaptureDevice SET device_name = ? WHERE config_id = ?;";

	private Connection con = null;

	public AudioCaptureDeviceRepository() {
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
	
	public List<CaptureDeviceModel> findAll() {
		List<CaptureDeviceModel> devices = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			devices = new ArrayList<>();
			while (rs.next()) {
				CaptureDeviceModel device = new CaptureDeviceModel();
				device.setDeviceName(rs.getString(1));
				device.setConfigId(rs.getInt(2));
				devices.add(device);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into AudioCaptureDevice table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return devices == null ? Collections.emptyList() : devices;
	}
	
	public void insert(AudioSettingsModel model) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt); 
			st.setString(1, model.getCaptureDevice());
			st.setInt(2, model.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into AudioCaptureDevice table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(AudioSettingsModel model) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setInt(1, model.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into AudioCaptureDevice table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void update(AudioSettingsModel model) {
		try {
			PreparedStatement st = con.prepareStatement(updateStmt); 
			st.setString(1, model.getCaptureDevice());
			st.setInt(2, model.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute UPDATE statement into AudioCaptureDevice table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

}
