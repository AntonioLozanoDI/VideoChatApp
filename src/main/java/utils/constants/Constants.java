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
			public static final String databaseFile = "contactApp";
			public static final String testDatabaseFile = "testContactApp";
			public static final String originalDatabaseFile = OSIndependentPath("original","contactApp");
			public static final String originalTestDatabaseFile = OSIndependentPath("original","testContactApp");
		}

		public static class FXML {
			/*
			 * Ficheros FXML
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio
			 * \application\view\fxml\
			 */
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
		}

		public static class Logs {
			/*
			 * Ficheros de logs
			 * 
			 * Solo indicar el nombre del fichero a partir del directorio \tmp\logs\
			 */
			public static final String applicationLogFile = "contactapp";
			public static final String viewLogFile = "view-contactapp";
			public static final String modelLogFile = "model-contactapp";
			public static final String controllerLogFile = "controller-contactapp";
			public static final String utilsLogFile = "utils-contactapp";
		}
	}

	public static final class Views {
		/*
		 * Constantes de las vistas
		 */
	}

	public static final class Paths {
		/*
		 * Constantes de las rutas que van a ser utilizadas
		 */

		//==========================================================================================================================================================
		
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

		//==========================================================================================================================================================
		
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
