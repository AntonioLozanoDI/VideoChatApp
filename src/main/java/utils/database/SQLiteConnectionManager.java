package utils.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.resources.ApplicationResourceProvider;

public class SQLiteConnectionManager extends DatabaseConnectionManager {

	private String dbLocation;
	//TODO refactorizar
	public SQLiteConnectionManager(String propertiesFilePath) throws IOException {
		super(propertiesFilePath);
	}
	
	@Override
	protected void loadPropertiesFromFile() throws IOException {
		//asign sqlite.properties to the variables
		properties.load(propertiesReader);
		connectorProtocol = properties.getProperty("database.connectorProtocol");
		dbLocation = ApplicationResourceProvider.getDatabaseFile(properties.getProperty("database.dbfile")).toString();
	}
	
	@Override
	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(connectorProtocol + dbLocation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
