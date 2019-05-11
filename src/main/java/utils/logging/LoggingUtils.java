package utils.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggingUtils {
		
	public static String getStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	public static String cleanFXMLPath(String toClean) {
		return toClean != null ? toClean.replace("application/..%5c", "").replace("%5c", "/").substring(1) : null;

	}
}
