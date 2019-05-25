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

import utils.database.DatabaseHelper;
import utils.database.SQLiteConnectionManager;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;

public class LogonServersRepository {

	private Logger logger = ApplicationLoggers.modelLogger;	

	private final String insertStmt = "INSERT INTO LogonServers (address) VALUES (?);";

	private final String deleteStmt = "DELETE FROM LogonServers WHERE address = ?;";

	private final String selectAllStmt = "SELECT * FROM LogonServers;";
	
	private final String selectSame = "SELECT * FROM LogonServers WHERE address = ?;";

	private Connection con = null;

	
	public LogonServersRepository() {
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
	
	public List<String> findAll() {
		List<String> addresses = null;
		try {
			PreparedStatement st = con.prepareStatement(selectAllStmt);
			ResultSet rs = st.executeQuery();
			addresses = new ArrayList<>();
			while (rs.next()) {
				addresses.add(rs.getString(1));
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute SELECT statement into LogonServers table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return addresses == null ? Collections.emptyList() : addresses;
	}
	
	public void insert(String address) {
		try {
			PreparedStatement st = con.prepareStatement(insertStmt); 
			st.setString(1, address);
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute INSERT statement into LogonServers table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}

	public void delete(String address) {
		try {
			PreparedStatement st = con.prepareStatement(deleteStmt);
			st.setString(1, address);
			st.executeUpdate();
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into LogonServers table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
	}
	
	public boolean exists(String address) {
		boolean found = false;
		try {
			PreparedStatement st = con.prepareStatement(selectSame);
			st.setString(1, address);
			ResultSet rs = st.executeQuery();
			List<String> addresses = new ArrayList<>();
			while (rs.next()) {
				addresses.add(rs.getString(1));
			}
			
			for(int i = 0, size = addresses.size() ; i < size && !found ; i++) {
				found = addresses.get(i).equals(address);
			}
		} catch (SQLException e) {
			String trace = String.format("Error trying to execute DELETE statement into LogonServers table in %s%n%s", getClass().getSimpleName(), LoggingUtils.getStackTrace(e));
			logger.severe(trace);
		}
		return found;
	}

}
