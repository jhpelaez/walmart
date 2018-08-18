package com.bancolombia.citrus.reto_tecnico.walmart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que se encarga de imprimir todos los mensajes
 * @author John Hader Pelaez Noreña - jhpelaezn@gmail.com
 *
 */
public class Printer {
	private static final  Logger LOGGER = LoggerFactory.getLogger(Printer.class);
	private static final String SEPARATOR = "---------------------------------------";
	
	public static void separator(String string) {
		LOGGER.info("");
		LOGGER.info(SEPARATOR+string+SEPARATOR);
	}

	public static void message(String message, String value) {
		LOGGER.info(message+value);
	}

	public static void message(String message) {
		LOGGER.info(message);
	}
}
