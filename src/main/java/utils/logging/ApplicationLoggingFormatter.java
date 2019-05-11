package utils.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class ApplicationLoggingFormatter extends SimpleFormatter {
	// Create a DateFormat to format the logger timestamp.
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder(1000);
		builder.append(df.format(new Date(record.getMillis()))).append(" - ");
		builder.append("[").append(String.format("%60s", record.getSourceClassName() + "." +  record.getSourceMethodName())).append("()] - ");
		builder.append("[").append(String.format("%-7s", record.getLevel())).append("] - ");
		builder.append(record.getMessage());
		builder.append("\n");
		return builder.toString();
	}
}