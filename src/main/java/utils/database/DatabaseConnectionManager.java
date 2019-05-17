package utils.database;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnectionManager {
	private String hostname;
	private String username;
	private String password;
	protected String connectorProtocol;
	private String schema;
	private String urlParam;

	protected Properties properties;
	protected Reader propertiesReader;
	protected Connection con;

	public DatabaseConnectionManager(String propertiesFilePath) throws IOException {
		properties = new Properties();
		propertiesReader = new FileReader(propertiesFilePath);
		loadPropertiesFromFile();
	}

	protected void loadPropertiesFromFile() throws IOException {
		properties.load(propertiesReader);
		hostname = properties.getProperty("database.hostname");
		username = properties.getProperty("database.username");
		password = properties.getProperty("database.password");
		connectorProtocol = properties.getProperty("database.connectorProtocol");
		schema = properties.getProperty("database.schema");
		urlParam = properties.getProperty("database.urlParameter");
	}

	public Connection getConnection() {
		String databaseUrl = connectorProtocol + "//" + hostname + "/" + schema + urlParam;
		Statement p;
		try {
			con = DriverManager.getConnection(databaseUrl, username, password);
			if (con == null) {
				throw new NullPointerException();
			}
			p = con.createStatement();
			p.executeQuery("use " + schema);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectorProtocol() {
		return connectorProtocol;
	}

	public void setConnectorProtocol(String protocol) {
		this.connectorProtocol = protocol;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String sslParam) {
		this.urlParam = sslParam;
	}

	@Override
	public String toString() {
		return "DatabaseConnectionManager [hostname=" + hostname + ", username=" + username + ", password=" + password
				+ ", protocol=" + connectorProtocol + ", properties=" + properties + "]";
	}
}
