package cl.intelidata.movistar.main;

import cl.intelidata.movistar.beans.MessageUtils;
import cl.intelidata.movistar.beans.SearchUtils;

public class SearchDocInstanceID {
	public static void main(String[] args) {
		if (args.length < 1) {
			MessageUtils.info("CANTIDAD INVALIDA DE ARGUMENTOS - [DIRECTORIO DE ENTRADA] [DIRECTORIO DE SALIDA]");
			System.exit(1);
		}
		//		MessageUtils.initializeLogger();
		MessageUtils.info("INICIANDO APLICACION");
		MessageUtils.debug("ARGUMENTOS UTILIZADOS EN LA CONSULTA\r\n\t\t\tARGUMENTO 0 [DIR ENTRADA]: " + args[0] + "\r\n\t\t\tARGUMENTO 1 [DIR SALIDA]: " + args[1]);
		MessageUtils.info("INICIANDO PROCESO");
		SearchUtils util = new SearchUtils(args[0], args[1]);
		util.process();
		MessageUtils.info("PROCESO FINALIZADO");
		MessageUtils.info("=========================================================================");
	}
}
