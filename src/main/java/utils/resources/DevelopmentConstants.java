package utils.resources;

import utils.constants.Constants;

public class DevelopmentConstants {
	/*
	 * Constantes de las rutas que van a ser utilizadas
	 */

	// ======================================================================================================

	// ########################## Origins ################################

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
	public static final String fromMainToProjectDir = Constants.OSIndependentPath("..", "..", "..", ".."); // project , src , main , java (desde src/main/java/application)

	/*
	 * Desde C:\ ó /root hasta la raiz del projecto
	 */
	public static final String fromRootDirToProjectDir = System.getProperty("user.dir");
	// ############################################################################
}
