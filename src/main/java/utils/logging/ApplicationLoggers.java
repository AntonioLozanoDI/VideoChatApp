package utils.logging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import utils.constants.Constants;
import utils.resources.ApplicationResourceProvider;

public final class ApplicationLoggers {

	private static final Logger logger = Logger.getLogger(ApplicationLoggers.class.getSimpleName());
	
	private static Handler consoleHandler;
	private static Handler applicationHandler;
	private static Handler modelHandler;
	private static Handler controllerHandler;
	private static Handler viewHandler;
	private static Handler utilsHandler;
	
	private static Formatter formatter = new ApplicationLoggingFormatter();
	
	static {
		try {
			configureLogManager();
			configureRootLogger();
			
			consoleHandler =  new ConsoleHandler();
			applicationHandler = new FileHandler(ApplicationResourceProvider.getLogFile(Constants.Files.Logs.applicationLogFile).toString());
			modelHandler = new FileHandler(ApplicationResourceProvider.getLogFile(Constants.Files.Logs.modelLogFile).toString());
			controllerHandler = new FileHandler(ApplicationResourceProvider.getLogFile(Constants.Files.Logs.controllerLogFile).toString());
			viewHandler = new FileHandler(ApplicationResourceProvider.getLogFile(Constants.Files.Logs.viewLogFile).toString());
			utilsHandler = new FileHandler(ApplicationResourceProvider.getLogFile(Constants.Files.Logs.utilsLogFile).toString());

			logger.info("Application loggers successfully configured.");
		} catch (SecurityException e) {
			logger.severe("Error during logging setup. SecurityException: " + e.getLocalizedMessage());
		} catch (IOException e) {
			logger.severe("Error during logging setup. IOException: " + e.getLocalizedMessage());
		}
	}
	
	public static Logger appLogger 			= create("VideoChat.logger", consoleHandler, applicationHandler);
	public static Logger modelLogger 		= create("VideoChat.logger.model", modelHandler);
	public static Logger controllerLogger 	= create("VideoChat.logger.controller", controllerHandler);
	public static Logger viewLogger 		= create("VideoChat.logger.view", viewHandler);
	public static Logger utilsLogger 		= create("VideoChat.logger.utils", utilsHandler);

	private static Logger create(String name, Handler...handlers) {
		Logger l = Logger.getLogger(name);
		for (Handler handler : handlers) {
			handler.setFormatter(formatter);
			l.addHandler(handler);
		}
		return l;
	}

	private static void configureLogManager() throws SecurityException, FileNotFoundException, IOException {
		LogManager.getLogManager().readConfiguration(ApplicationResourceProvider.getPropertiesFile("logging").toInputStream());
	}

	private static void configureRootLogger() {
		Logger.getLogger("").setLevel(Level.FINEST);
	}
}
