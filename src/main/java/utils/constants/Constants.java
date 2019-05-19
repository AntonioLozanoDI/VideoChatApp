package utils.constants;

import java.io.File;

import application.Main;

public class Constants {
	public static final class Files {
		
		public static final File RESOURCES = new File(Main.class.getClassLoader().getResource(".").getFile());

		/*
		 * Constantes de los ficheros
		 * 
		 * Todos los ficheros al compilarse la aplicacion se copiaran a \bin en la raiz
		 * del proyecto
		 */
		public static class Database {
			/*
			 * Ficheros BBDD
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \db\
			 */
			public static final String databaseFile = "videoChatApp";
			public static final String testDatabaseFile = "testVideoChatApp";
			public static final String originalDatabaseFile = OSIndependentPath("original","videoChatApp");
			public static final String originalTestDatabaseFile = OSIndependentPath("original","testVideoChatApp");
		}

		public static class FXML {
			/*
			 * Ficheros FXML
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio
			 * \application\view\fxml\
			 */
			public static final String RootLayout = "RootLayout";
			public static final String MainView = "MainView";
			public static final String VideoScreenView = "VideoScreenView";
			public static final String ServerSettingsWindow = "ServerSettingsWindow";
			public static final String ContactCard = "ContactCard";
			public static final String AudioSettingsWindow = "AudioSettingsWindow";
			public static final String LoginWindow = "LoginWindow";
			public static final String RegisterUserWindow = "RegisterUserWindow";
			public static final String CreateContactCard = "CreateContactCard";
			public static final String EnterNameWindow = "EnterNameWindow";
			public static final String ContactForm = "ContactForm";
		}

		public static class Properties {
			/*
			 * Ficheros PROPERTIES
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \cfg\properties\
			 */
			public static final String sqliteProperties = "SQLiteDatabase";
			public static final String testingSqliteProperties = "SQLiteDatabaseTesting";
		}

		public static class CSS {
			/*
			 * Ficheros CSS
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \styles\
			 */
			public static final String defaultTheme = "application";
			public static final String darkTheme = "DarkTheme";
		}

		public static class Images {
			/*
			 * Ficheros de imagenes
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \images\
			 */
			public static final String black = "black";
			public static final String camIcon = "icon-cam";
			public static final String activeVideoCall = "active-videocall";
			public static final String pauseVideoCall = "pause-videocall";
			public static final String stopVideoCall = "stop-videocall";
			public static final String mutedMic = "muted-mic";
			public static final String unmutedMic = "unmuted-mic";
			public static final String mutedSpeaker = "muted-speaker";
			public static final String unmutedSpeaker = "unmuted-speaker";
			public static final String contactAdd = "contact-add";
		}

		public static class Logs {
			/*
			 * Ficheros de logs
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \tmp\logs\
			 */
			public static final String applicationLogFile = "videochatapp";
			public static final String viewLogFile = "view-videochatapp";
			public static final String modelLogFile = "model-videochatapp";
			public static final String controllerLogFile = "controller-videochatapp";
			public static final String utilsLogFile = "utils-videochatapp";
		}
	}

	public static final class Views {
		/*
		 * Constantes de las vistas
		 */
		public static final String VideoChatAppTitle = "VideoChatAppTitle";
//		public static final String VideoChatAppTitle = "";
	}

	public static final class Paths {
		/*
		 * Constantes de las rutas que van a ser utilizadas
		 */

		//======================================================================================================
		
		// ##########################      Origins     ################################
		
		// ############################################################################
		// ################## Rutas que se posicionan en bin ##########################
		/*
		 * Desde Main.class (Main.class esta localizada en el directorio
		 * \bin\application)
		 */
		public static final String fromMainToBin = "..";

		/*
		 * Desde C:\ ó /root hasta \bin dentro del directorio del proyecto
		 */
		public static final String fromRootDirToBin = Constants.Files.RESOURCES.getAbsolutePath();
		// ############################################################################

		
		
		// ############################################################################
		// ############# Rutas que se posicionan en la raiz del projecto ###############
		/*
		 * Desde Main.class (Main.class esta localizada en el directorio
		 * \bin\application)
		 */
		public static final String fromMainToProjectDir = OSIndependentPath("..","..","..",".."); // project , src , main , java (desde src/main/java/application)

		/*
		 * Desde C:\ ó /root hasta la raiz del projecto
		 */
		public static final String fromRootDirToProjectDir = System.getProperty("user.dir");
		// ############################################################################

		//=========================================================================================================
		
		// ##########################     Filepaths    ################################
		
		// ############################################################################
		// ################ Rutas que se posicionan a partir de bin ###################

		public static final String FXML = OSIndependentPath("application","view","fxml");

		public static final String CSS = "styles";

		public static final String Properties = OSIndependentPath("cfg","properties");

		public static final String Database = "db";

		public static final String images = "images";
		// ############################################################################

		
		// ############################################################################
		// ############ Rutas que se posicionan en la raiz del projecto ###############
		
		public static final String logs = OSIndependentPath("tmp","logs");
		
		// ############################################################################
		
	}

	public static final class Extensions {
		/*
		 * Constantes de las extensiones de los ficheros utilizados
		 */
		public static final String CSS = "css";

		public static final String Properties = "properties";

		public static final String FXML = "fxml";

		public static final String Database = "db";

		public static final String PNG = "png";

		public static final String LOG = "log";
	}
	
	public static String OSIndependentPath(String...dirs) {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(String dir : dirs) {
			sb.append( dir ).append( ++i == dirs.length ? "" : File.separator );
		}
		return sb.toString();
	}
}
