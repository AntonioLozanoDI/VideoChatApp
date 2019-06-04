package utils.resources;

public class ProductionConstants {
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
	public static final String fromMainToBin = "";

	/*
	 * Desde C:\ � /root hasta \bin dentro del directorio del proyecto
	 */
	public static final String fromRootDirToBin = System.getProperty("user.dir") + "/resources";
	// ############################################################################

	// ############################################################################
	// ############# Rutas que se posicionan en la raiz del projecto ###############
	/*
	 * Desde Main.class (Main.class esta localizada en el directorio
	 * \bin\application)
	 */
	public static final String fromMainToProjectDir = ""; 

	/*
	 * Desde C:\ � /root hasta la raiz del projecto
	 */
	public static final String fromRootDirToProjectDir = System.getProperty("user.dir") + "/resources";
	// ############################################################################

}
