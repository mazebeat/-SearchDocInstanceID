package cl.intelidata.movistar.beans;

import cl.intelidata.movistar.main.SearchDocInstanceID;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MessageUtils {

	private static final Logger LOGGER = Logger.getLogger(SearchDocInstanceID.class.getName());

	public MessageUtils() {
		initializeLogger();
	}

	public static void initializeLogger() {
		Properties logProperties = new Properties();
		try {
			logProperties.load(new FileInputStream(Text.LOG_PROPERTIES_FILE));
			PropertyConfigurator.configure(logProperties);
			LOGGER.info("LOGGING INITIALIZED.");
		} catch (IOException e) {
			throw new RuntimeException("UNABLE TO LOAD LOGGING PROPERTY " + Text.LOG_PROPERTIES_FILE);
		}
	}

	public static void info(String message) {
		LOGGER.info(message);
	}

	public static void debug(String message) {
		LOGGER.debug(message);
	}

	public static void trace(String message) {
		LOGGER.trace(message);
	}

	public static void warm(String message) {
		LOGGER.warn(message);
	}

	public static void error(String message) {
		LOGGER.error(message);
	}

	public static void fatal(String message) {
		LOGGER.fatal(message);
	}
}
