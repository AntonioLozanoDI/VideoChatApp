package utils.resources;

import utils.constants.Constants;

public class DevelopmentConstants {
	
	public static final String fromMainToBin = "..";

	public static final String fromRootDirToBin = Constants.Files.RESOURCES.getAbsolutePath();

	public static final String fromMainToProjectDir = Constants.OSIndependentPath("..", "..", "..", ".."); 

	public static final String fromRootDirToProjectDir = System.getProperty("user.dir");
}
