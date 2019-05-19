package application.model.repository;

import java.io.IOException;
import java.sql.Connection;
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

	private final String insertStmt = "INSERT INTO AudioCaptureDevice (config_name,out_sample_rate,out_sample_size_bits,out_channels,out_signed,out_big_endian,in_sample_rate,in_sample_size_bits,in_channels,in_signed,in_big_endian) VALUES (?,?,?,?,?,?,?,?,?,?,?);";

	private final String deleteStmt = "DELETE FROM AudioCaptureDevice WHERE config_name = ? AND out_sample_rate = ? AND out_sample_size_bits = ? AND out_channels = ? AND out_signed = ? AND out_big_endian = ? AND in_sample_rate = ? AND in_sample_size_bits = ? AND in_channels = ? AND in_signed = ? AND in_big_endian = ? AND id = ?;";

	private final String selectAllStmt = "SELECT * FROM AudioCaptureDevice;";
	
	private final String updateStmt = "UPDATE AudioCaptureDevice SET config_name = ?, out_sample_rate = ?, out_sample_size_bits = ?, out_channels = ?, out_signed = ?, out_big_endian = ?, in_sample_rate = ?, in_sample_size_bits = ?, in_channels = ?, in_signed = ?, in_big_endian = ? WHERE id = ?;";

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
	
	public void insert(AudioSettingsModel model) {
		// TODO Auto-generated method stub
		
	}

	public void delete(AudioSettingsModel model) {
		// TODO Auto-generated method stub
		
	}

	public List<CaptureDeviceModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(AudioSettingsModel model) {
		// TODO Auto-generated method stub
		
	}

}
