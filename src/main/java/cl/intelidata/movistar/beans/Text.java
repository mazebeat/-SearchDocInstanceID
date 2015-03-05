package cl.intelidata.movistar.beans;

import java.text.SimpleDateFormat;

public interface Text {
	//	APP UTILS
	public static final String           LOG_EXT             = ".log";
	public static final String           ERROR_BDD           = "ERROR NO REGISTRO EN BASE DE DATOS";
	public static final String           ERROR_BDD_SEPARATOR = "Message-ID";
	public static final String           ERROR_CODE          = "Cod Error";
	public static final String           ERROR_DESCRIPTION   = "Cod MSG Error";
	//	LOG4J
	public final static String           LOG_PROPERTIES_FILE = "resources/log4j.properties";
	//	DATE FORMAT
	public              SimpleDateFormat DATE_FORMAT         = new SimpleDateFormat("yyyyMMdd");
}
