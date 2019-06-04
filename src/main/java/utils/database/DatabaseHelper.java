package utils.database;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import com.sp.dialogs.DialogBuilder;

import javafx.scene.control.ButtonType;
import utils.EnvironmentLoader;
import utils.constants.Constants;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public final class DatabaseHelper {

	private static Logger logger = ApplicationLoggers.utilsLogger;

	private static String propertiesToLoad = null;
	
	private static boolean developEnvironment = false;

	private static boolean useTestingDB = false;
	
	static {
		developEnvironment = EnvironmentLoader.isDevelopEnvironment();
	}
	
	private DatabaseHelper() {}

	public static void askApplicationDatabase() {
		if(developEnvironment) {
			Optional<ButtonType> btn = DialogBuilder.confirmation()
					.title("Database initialization")
					.header("Use testing database?")
					.finish().alert().showAndWait();

			useTestingDB = btn.isPresent() && btn.get().equals(ButtonType.OK);
		}
		logger.info(String.format("Using %s database.", useTestingDB ? "testing" : "application"));
		String fileName = useTestingDB ? Constants.Files.Properties.testingSqliteProperties : Constants.Files.Properties.sqliteProperties;
		propertiesToLoad = ApplicationResourceProvider.getPropertiesFile(fileName).toString();
	}

	public static void ensureDatabaseExists() {
		String dbPath = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.databaseFile).toString();
		if (!Files.exists(Paths.get(dbPath), LinkOption.NOFOLLOW_LINKS) && !useTestingDB) {
			logger.info("Creating application database.");
			createDatabase();
		}
		if (useTestingDB)
			createTestingDatabase();
	}

	private static void createTestingDatabase() {
		String inPathFile = null;
		String ouPathFile = null;
		try {
			// Path desde donde se copiara la base de datos original
			inPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.originalTestDatabaseFile).toString();
			// Path donde se copiara la base de datos
			ouPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.testDatabaseFile).toString();

			File testDb = new File(ouPathFile);
			if (Files.exists(testDb.toPath(), LinkOption.NOFOLLOW_LINKS)) {
				testDb.delete();
				logger.info("Deleted old testing database.");
			}
			copyOriginalDataBase(inPathFile, ouPathFile);

			logger.info("Created new testing database.");
		} catch (Exception e) {
			logger.severe("error copying testing database file. Files located in \norigin: " + inPathFile + "\noutput: " + ouPathFile + "\n" + LoggingUtils.getStackTrace(e));
			logger.severe("Exiting from application.");
			System.exit(-1);
		}
	}

	private static void createDatabase() {
		String inPathFile = null;
		String ouPathFile = null;
		try {
			// Path desde donde se copiara la base de datos original
			inPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.originalDatabaseFile).toString();
			// Path donde se copiara la base de datos
			ouPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.databaseFile).toString();

			copyOriginalDataBase(inPathFile, ouPathFile);

			logger.info("Created application database");
		} catch (Exception e) {
			logger.severe("error copying application database file. Files located in \norigin: " + inPathFile
					+ "\noutput: " + ouPathFile + "\n" + LoggingUtils.getStackTrace(e));
			logger.severe("Exiting from application.");
			System.exit(-1);
		}

	}

	private static void copyOriginalDataBase(String origin, String output) throws Exception {
		Path in = Paths.get(origin);
		Path out = Paths.get(output);
		Files.write(out, Files.readAllBytes(in));
	}

	public static String getPropertiesToLoad() {
		return propertiesToLoad;
	}

	public static void showTables(Connection con) throws SQLException {
		DatabaseMetaData metaDatos = con.getMetaData();
		ResultSet rs = metaDatos.getTables(null, null, "%", null);
		while (rs.next()) {
			// Ver getTables en la API de DataBaseMetaData.
			String tabla = rs.getString("TABLE_NAME");
			System.out.printf("TABLA = %s%n", tabla);

			// Mostramos las columnas de la tabla
			showColumns(con, tabla);
		}
	}

	public static void showColumns(Connection con, String tabla) throws SQLException {
		DatabaseMetaData metaDatos = con.getMetaData();
		ResultSet rs = metaDatos.getColumns(null, null, tabla, null);
		while (rs.next()) {
			// Ver getColumns() en la API de DataBaseMetaData
			String nombre = rs.getString("COLUMN_NAME");
			String tipo = rs.getString("TYPE_NAME");
			System.out.printf("\tCAMPO = %s (%s)%n", nombre, tipo);
		}
	}
}
