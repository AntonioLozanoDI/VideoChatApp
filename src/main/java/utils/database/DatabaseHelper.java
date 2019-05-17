package utils.database;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import com.sp.dialogs.DialogBuilder;

import javafx.scene.control.ButtonType;
import utils.constants.Constants;
import utils.logging.ApplicationLoggers;
import utils.logging.LoggingUtils;
import utils.resources.ApplicationResourceProvider;

public final class DatabaseHelper {
	
	static Logger logger = ApplicationLoggers.utilsLogger;
	
	private static String propertiesToLoad = null;
	
	private static boolean useTestingDB = false;
	
	private DatabaseHelper() {}
	
	public static void askApplicationDatabase() {
		Optional<ButtonType> btn = DialogBuilder.confirmation()
		.title("Database initialization")
		.header("Use testing database?")
		.finish().alert().showAndWait();
		
		useTestingDB = btn.isPresent() && btn.get().equals(ButtonType.OK);
		
		logger.info(String.format("Using %s database.", useTestingDB ? "testing" : "application"));
		
		String fileName = useTestingDB ? Constants.Files.Properties.testingSqliteProperties : Constants.Files.Properties.sqliteProperties;
		propertiesToLoad = ApplicationResourceProvider.getPropertiesFile(fileName).toString();
	}
	
	public static void ensureDatabaseExists() {
		String dbPath = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.databaseFile).toString();
		if(!Files.exists(Paths.get(dbPath),LinkOption.NOFOLLOW_LINKS) && !useTestingDB) {
			logger.info("Creating application database.");
			createDatabase();
		}
		if(useTestingDB) 
			createTestingDatabase();
	}

	private static void createTestingDatabase() {
		String inPathFile = null;
		String ouPathFile = null;
		try {
			//Path desde donde se copiara la base de datos original
	        inPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.originalTestDatabaseFile).toString();
	        //Path donde se copiara la base de datos
	        ouPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.testDatabaseFile).toString();
	        
	        File testDb = new File(ouPathFile);
	        if(Files.exists(testDb.toPath(),LinkOption.NOFOLLOW_LINKS)) {
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
			//Path desde donde se copiara la base de datos original
	        inPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.originalDatabaseFile).toString();
	        //Path donde se copiara la base de datos
	        ouPathFile = ApplicationResourceProvider.getDatabaseFile(Constants.Files.Database.databaseFile).toString();

	        copyOriginalDataBase(inPathFile, ouPathFile);
	        
	        logger.info("Created application database");
		} catch (Exception e) {
			logger.severe("error copying application database file. Files located in \norigin: " + inPathFile + "\noutput: " + ouPathFile + "\n" + LoggingUtils.getStackTrace(e));
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
}
