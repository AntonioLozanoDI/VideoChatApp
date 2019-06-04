package utils.resources;

import utils.constants.Constants;
import utils.resources.OriginPaths.Paths;

public class ResourcePathFactory {
	
	private static Paths paths;
	
	static {
		paths = EnvironmentLoader.getPaths();
	}
	
	public enum OriginPathToBin {
		ROOT_DIRECTORY,MAIN_CLASS
	}
	
	public enum OriginPathToProjectDir {
		ROOT_DIRECTORY,MAIN_CLASS
	}
	
	private static String fullPath(String pathWithoutExt, String ext) {
		return String.format("%s.%s", pathWithoutExt, ext);
	}
	
	private static String formatFileNameFromBin(OriginPathToBin origin, String directory, String name, String ext) {
		String fullPath = null;
		switch (origin) {
		case ROOT_DIRECTORY:
			fullPath = fullPath(Constants.OSIndependentPath(paths.fromRoot.projectClassesDir(), directory, name), ext);
			break;
		case MAIN_CLASS:
			fullPath = fullPath(Constants.OSIndependentPath(paths.fromMainClass.projectClassesDir(), directory, name), ext);
			break;
		}
//		System.out.println(fullPath);//TODO remove
		return fullPath;
	}
	
	private static String formatFileNameFromProjectDir(OriginPathToProjectDir origin, String directory, String name, String ext) {
		String fullPath = null;
		switch (origin) {
		case ROOT_DIRECTORY:
			fullPath = fullPath(Constants.OSIndependentPath(paths.fromRoot.rootProjectDir(), directory, name), ext);
			break;
		case MAIN_CLASS:
			fullPath = fullPath(Constants.OSIndependentPath(paths.fromMainClass.rootProjectDir(), directory, name), ext);
			break;
		}
//		System.out.println(fullPath);//TODO remove
		return fullPath;
	}
	
	public static String getDatabaseFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.FilePaths.Database, fileName, Constants.Extensions.Database);
	}
	
	public static String getCSSFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.FilePaths.CSS, fileName, Constants.Extensions.CSS);
	}
	
	public static String getFXMLFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.FilePaths.FXML, fileName, Constants.Extensions.FXML);
	}
	
	public static String getPropertiesFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.FilePaths.Properties, fileName, Constants.Extensions.Properties);
	}
	
	public static String getPNGFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.FilePaths.images, fileName, Constants.Extensions.PNG);
	}
	
	public static String getLogFilePath(OriginPathToProjectDir origin, String fileName) {
		return formatFileNameFromProjectDir(origin, Constants.FilePaths.logs, fileName, Constants.Extensions.LOG);
	}
}
