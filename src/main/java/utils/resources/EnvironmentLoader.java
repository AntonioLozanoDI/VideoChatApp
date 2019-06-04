package utils.resources;

import utils.resources.OriginPaths.Paths;

public class EnvironmentLoader {
	enum Environment {
		DEVELOPMENT,PRODUCTION
	}
	
	private static Environment loaded = Environment.PRODUCTION;
	
	public static void configure(String[] args) {
		for(String arg : args) {
			String[] splitted = arg.split("=");
			if(splitted.length > 0 && splitted[0].equals("environment"))
				loaded = Environment.valueOf(splitted[1]);
		}
	}
	
	public static Paths getPaths() {
		Paths paths = new Paths();
		if(loaded.equals(Environment.PRODUCTION)) {
			paths.fromMainClass = OriginPaths.OriginsProduction.FROM_MAIN_CLASS;
			paths.fromRoot = OriginPaths.OriginsProduction.FROM_FILESYSTEM_ROOT;
		} else {
			paths.fromMainClass = OriginPaths.OriginsDevelop.FROM_MAIN_CLASS;
			paths.fromRoot = OriginPaths.OriginsDevelop.FROM_FILESYSTEM_ROOT;
		}
		return paths;
	}
}
