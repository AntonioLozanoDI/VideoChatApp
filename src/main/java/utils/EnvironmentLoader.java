package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import utils.resources.OriginPaths;
import utils.resources.OriginPaths.Paths;

public class EnvironmentLoader {
	enum Environment {
		DEVELOPMENT,PRODUCTION
	}
	private static Environment loaded;
	private static Map<String,String> params;
	
	static {
		loaded = Environment.PRODUCTION;
		params = new HashMap<>();
	}
	
	public static void configure(String[] args) {
		System.out.println(Arrays.toString(args));
		for(String arg : args) {
			String[] splitted = arg.split("=");
			params.put(splitted[0], splitted[1]);
		}
	}
	
	public static Paths getPaths() {
		Paths paths = new Paths();
		String value = params.get("environment");
		if(value != null)
			loaded = Environment.valueOf(value);
			
		if(loaded.equals(Environment.PRODUCTION)) {
			paths.fromMainClass = OriginPaths.OriginsProduction.FROM_MAIN_CLASS;
			paths.fromRoot = OriginPaths.OriginsProduction.FROM_FILESYSTEM_ROOT;
		} else {
			paths.fromMainClass = OriginPaths.OriginsDevelop.FROM_MAIN_CLASS;
			paths.fromRoot = OriginPaths.OriginsDevelop.FROM_FILESYSTEM_ROOT;
		}
		return paths;
	}
	
	public static boolean isDevelopEnvironment() {
		return loaded == Environment.DEVELOPMENT;
	}
}
