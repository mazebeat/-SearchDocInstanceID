@ECHO Off
TITLE BUSCAR DOCINSTANCEID - BY DIEGO F.
REM @AUTHOR	DIEGO F.
REM @DATE	20150228	
REM @DESC	APLICATIVO PARA EJECUTAR ARCHIVO JAR QUE BUSCA LOS DOCINSTANCEID DENTRO DE ARCHIVOS LOG GENERADOS POR SERVICIOS WRAPPER

REM SETEANDO VARIABLES
:: JAR VARS
SET jarDirectory=E:\Projects\Java\SearchDocInstanceID\out\artifacts\SearchDocInstanceID_jar
SET jarName=SearchDocInstanceID.jar
SET jarClass=cl.intelidata.movistar.main.SearchDocInstanceID
:: DIRECTORIES VARS --------- ESTAS SON LAS VARIBLES A MODIFICAR
SET dirEntrada=E:\LogRebotes\Fija
SET dirSalida=E:\Projects\Java\SearchDocInstanceID\out\artifacts\SearchDocInstanceID_jar
SETLOCAL EnableDelayedExpansion


ECHO ======================================================================
ECHO 	BUSCAR FALLAS DE BDD DENTRO DEL PROCESO DE REBOTES 
ECHO 	UTILIZADO EN EL REPORTE WEB DE MOVISAR
ECHO ======================================================================
ECHO.

ECHO EJECUTANDO ARCHIVO JAR
ECHO.
java -cp %jarDirectory%/%jarname% %jarclass% %dirEntrada% %dirSalida%
ECHO.

ECHO EJECUCION FINALIZADA
ECHO PRESIONE UNA TECLA PARA CONTINUAR
PAUSE >NUL
EXIT 0;