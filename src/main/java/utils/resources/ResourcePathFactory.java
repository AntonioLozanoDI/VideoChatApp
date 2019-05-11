package utils.resources;

import utils.constants.Constants;

public class ResourcePathFactory {
	
	public enum OriginPathToBin {
		ROOT_DIRECTORY,MAIN_CLASS
	}
	
	public enum OriginPathToProjectDir {
		ROOT_DIRECTORY,MAIN_CLASS
	}
	
	private static String fullPath(String pathWithoutExt, String ext) {
		String fullPath = String.format("%s.%s", pathWithoutExt, ext);
		System.out.println(fullPath);
		return fullPath;
	}
	
	private static String formatFileNameFromBin(OriginPathToBin origin, String directory, String name, String ext) {
		String fullPath = null;
		switch (origin) {
		case ROOT_DIRECTORY:
			fullPath = fullPath(Constants.OSIndependentPath(Constants.Paths.fromRootDirToBin, directory, name), ext);
			break;
		case MAIN_CLASS:
			fullPath = fullPath(Constants.OSIndependentPath(Constants.Paths.fromMainToBin, directory, name), ext);
			break;
		}
		return fullPath;
	}
	
	private static String formatFileNameFromProjectDir(OriginPathToProjectDir origin, String directory, String name, String ext) {
		String fullPath = null;
		switch (origin) {
		case ROOT_DIRECTORY:
			fullPath = fullPath(Constants.OSIndependentPath(Constants.Paths.fromRootDirToProjectDir, directory, name), ext);
			break;
		case MAIN_CLASS:
			fullPath = fullPath(Constants.OSIndependentPath(Constants.Paths.fromMainToProjectDir, directory, name), ext);
			break;
		}
		return fullPath;
	}
	
	public static String getDatabaseFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.Paths.Database, fileName, Constants.Extensions.Database);
	}
	
	public static String getCSSFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.Paths.CSS, fileName, Constants.Extensions.CSS);
	}
	
	public static String getFXMLFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.Paths.FXML, fileName, Constants.Extensions.FXML);
	}
	
	public static String getPropertiesFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.Paths.Properties, fileName, Constants.Extensions.Properties);
	}
	
	public static String getPNGFilePath(OriginPathToBin origin, String fileName) {
		return formatFileNameFromBin(origin, Constants.Paths.images, fileName, Constants.Extensions.PNG);
	}
	
	public static String getLogFilePath(OriginPathToProjectDir origin, String fileName) {
		return formatFileNameFromProjectDir(origin, Constants.Paths.logs, fileName, Constants.Extensions.LOG);
	}
}
