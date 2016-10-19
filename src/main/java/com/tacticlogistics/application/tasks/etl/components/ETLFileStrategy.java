package com.tacticlogistics.application.tasks.etl.components;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.infrastructure.services.FileSystemService;

import ch.qos.logback.classic.Logger;

public abstract class ETLFileStrategy<E> implements ETLStrategy<E> {
	private static final Logger log = (Logger) LoggerFactory.getLogger(ETLFileStrategy.class);

	protected static final String SI = "S";
	protected static final String NO = "N";

	protected static final Pattern PATTERN_PDF = Pattern.compile("(?i:.*\\.pdf)");
	protected static final Pattern PATTERN_TXT = Pattern.compile("(?i:.*\\.(txt|rpt|csv))");
	protected static final Pattern PATTERN_XLS = Pattern.compile("(?i:.*\\.(xlsx|xls))");

	protected static final String VALOR_NO_CONCUERDA_CON_EL_FORMATO_ESPERADO = "El valor del campo {0} es {1} y no corresponde al formato esperado el cual es {2}.";

	// --------------------------------------------------------------------------------------------
	protected abstract Pattern getPattern();

	protected abstract Reader<File, String> getReader();

	// --------------------------------------------------------------------------------------------
	@Override
	public void run(final Path entrada, final Path salida, final Path procesados, final Path errores) {
		setDirectorioEntrada(entrada.toFile());
		setDirectorioSalida(salida.toFile());
		setDirectorioProcesados(procesados.toFile());
		setDirectorioErrores(errores.toFile());

		try {
			preProcesarDirectorio();
		} catch (RuntimeException e) {
			onPreProcesarDirectorioError(e);
			throw e;
		}

		try {
			procesarDirectorio();
		} catch (RuntimeException e) {
			onProcesarDirectorioError(e);
			throw e;
		}

		try {
			postProcesarDirectorio();
		} catch (RuntimeException e) {
			onPostProcesarDirectorioError(e);
			throw e;
		}

		setDirectorioEntrada(null);
		setDirectorioSalida(null);
		setDirectorioProcesados(null);
		setDirectorioErrores(null);
	}

	/**
	 * Utilize este metodo para inicializar o acceder a recursos costosos y que
	 * no cambiararn durante el procesamiento de todo el contenido del
	 * directorio
	 */
	protected void preProcesarDirectorio() {

	}

	/**
	 * * Realiza el procesamiento de todos los archivos en el directorio de
	 * entrada
	 */
	protected void procesarDirectorio() {
		File archivos[] = getDirectorioEntrada().listFiles(getFilenameFilter());

		for (File a : archivos) {
			procesar(a);
		}
	}

	protected void postProcesarDirectorio() {

	}

	protected void onPreProcesarDirectorioError(RuntimeException e) {
		log.error("onPreProcesarDirectorioError", e);
	}

	protected void onProcesarDirectorioError(RuntimeException e) {
		log.error("onProcesarDirectorioError", e);
	}

	protected void onPostProcesarDirectorioError(RuntimeException e) {
		log.error("onPostProcesarDirectorioError", e);
	}

	// ----------------------------------------------------------------------------------------------------------------
	/**
	 * Realiza el procesamiento de un archivo ubicado dentro del directorio de
	 * entrada
	 * 
	 * @param file
	 */
	protected void procesar(File file) {
		// TODO
		// Inicializar Map<Archivo,MetaData>
		// Numero de filas (ignoradas, error, OK)
		// Numero de paginas (ignoradas, error, OK)
		// Numero de entidades OK, Warning, Errores, No Cargadas

		MensajesDto mensajes = new MensajesDto();
		setArchivo(file);

		log.info("\n");
		log.info("-------------------------------------------------------------------------------------------------");
		log.info("INICIO {}", getArchivo().getName());
		log.info("-------------------------------------------------------------------------------------------------");

		boolean procesar = false;
		try {
			procesar = preProcesarArchivo(mensajes);
		} catch (RuntimeException e) {
			onPreProcesarArchivoError(mensajes, e);
			return;
		}

		if (procesar) {
			try {
				procesarArchivo(mensajes);
			} catch (RuntimeException e) {
				onProcesarArchivoError(mensajes, e);
			}

			try {
				postProcesarArchivo(mensajes);
			} catch (RuntimeException e) {
				onPostProcesarArchivoError(mensajes, e);
			}

			try {
				backUp(mensajes);
			} catch (RuntimeException e) {
				log.error("Durante backUp()", e);
			}

			try {
				notificar(mensajes);
			} catch (RuntimeException e) {
				log.error("Durante notificar()", e);
			}
		}

		log.info("-------------------------------------------------------------------------------------------------");
		log.info("{} {}", (procesar ? "FIN" : "IGNORADO"), getArchivo().getName());
		log.info("-------------------------------------------------------------------------------------------------");
		log.info("\n");

		setArchivo(null);
	}

	// ----------------------------------------------------------------------------------------------------------------
	protected boolean preProcesarArchivo(MensajesDto mensajes) {
		return true;
	}

	protected void procesarArchivo(MensajesDto mensajes) {
		String etapa = "INICIO";

		String texto;
		Map<String, E> map;
		try {
			etapa = "EXTRACCIÓN DEL CONTENIDO DEL ARCHIVO";
			texto = extraer();

			etapa = "LIMPIEZA DE CARACTERES NO PERMITIDOS";
			texto = limpiar(texto);

			etapa = "MODIFICACIÓN DEL TEXTO ORIGINAL ANTES DE APLICAR TRANSFORMACIONES";
			texto = preTransformar(texto, mensajes);

			etapa = "TRANSFORMACIÓN DEL TEXTO ORIGINAL";
			map = transformar(texto, mensajes);

			etapa = "OPERACIONES PREVIAS AL CARGUE";
			map = preCargar(map, mensajes);

			etapa = "CARGUE";
			cargar(map, mensajes);
		} catch (RuntimeException e) {
			throw new RuntimeException(MessageFormat.format(
					"Durante el procesamiento del archivo {0}, ocurrio el siguiente error en la etapa {1}: Error {2}.",
					getArchivo().getName(), etapa, e.getMessage()), e);
		}
	}

	protected void postProcesarArchivo(MensajesDto mensajes) {

	}

	protected void onPreProcesarArchivoError(MensajesDto mensajes, Exception e) {
		log.error("onPreProcesarArchivoError", e);
		logFatal(mensajes, e.getMessage(), getArchivo().getName(), "", null);
	}

	protected void onProcesarArchivoError(MensajesDto mensajes, Exception e) {
		log.error("onProcesarArchivoError", e);
		logFatal(mensajes, e.getMessage(), getArchivo().getName(), "", null);
	}

	protected void onPostProcesarArchivoError(MensajesDto mensajes, Exception e) {
		logFatal(mensajes, e.getMessage(), getArchivo().getName(), "", null);
	}

	// ----------------------------------------------------------------------------------------------------------------
	protected String extraer() {
		try {
			return getReader().read(getArchivo());
		} catch (IOException e) {
			throw new RuntimeException("Error al extraer el contenido del archivo: " + getArchivo().getAbsolutePath());
		}
	}

	protected String limpiar(String texto) {
		return texto.toUpperCase();
	}

	protected String preTransformar(String texto, MensajesDto mensajes) {
		return texto;
	}

	protected abstract Map<String, E> transformar(String texto, MensajesDto mensajes);

	protected Map<String, E> preCargar(Map<String, E> map, MensajesDto mensajes) {
		return map;
	}

	protected abstract void cargar(Map<String, E> map, MensajesDto mensajes);

	protected void backUp(MensajesDto mensajes) {
		boolean error = mensajes.getSeveridadMaxima().equals(SeveridadType.FATAL);
		Path path = (error ? getDirectorioErrores() : getDirectorioProcesados()).toPath();

		DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
		try {
			FileSystemService.move(getArchivo(), path);
			File newFile = path.resolve(getArchivo().getName()).toFile();
			File renamedFile = path.resolve(sdf.format(LocalDateTime.now()) + "-" + getArchivo().getName()).toFile();
			newFile.renameTo(renamedFile);
		} catch (IOException ioe) {
			log.error("Error al mover el archivo {} al directorio {}.", getArchivo().getName(), path);
			log.error("", ioe);
		}
	}

	private void notificar(MensajesDto mensajes) {
		// TODO Auto-generated method stub

	}

	// --------------------------------------------------------------------------------------------
	public void logInfo(MensajesDto mensajes, String text, String objeto, String atributo, String data) {
		mensajes.add(SeveridadType.INFO, text, objeto, atributo, data);
	}

	public void logWarning(MensajesDto mensajes, String text, String objeto, String atributo, String data) {
		mensajes.add(SeveridadType.WARN, text, objeto, atributo, data);
	}

	public void logError(MensajesDto mensajes, String text, String objeto, String atributo, String data) {
		mensajes.add(SeveridadType.ERROR, text, objeto, atributo, data);
	}

	public void logFatal(MensajesDto mensajes, String text, String objeto, String atributo, String data) {
		mensajes.add(SeveridadType.FATAL, text, objeto, atributo, data);
	}

	// --------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------
	private File archivo;
	private File directorioEntrada;
	private File directorioSalida;
	private File directorioProcesados;
	private File directorioErrores;

	public File getArchivo() {
		return archivo;
	}

	public File getDirectorioEntrada() {
		return directorioEntrada;
	}

	public File getDirectorioSalida() {
		return directorioSalida;
	}

	public File getDirectorioProcesados() {
		return directorioProcesados;
	}

	public File getDirectorioErrores() {
		return directorioErrores;
	}

	protected void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public void setDirectorioEntrada(File directorioEntrada) {
		this.directorioEntrada = directorioEntrada;
	}

	public void setDirectorioSalida(File directorioSalida) {
		this.directorioSalida = directorioSalida;
	}

	public void setDirectorioProcesados(File directorioProcesados) {
		this.directorioProcesados = directorioProcesados;
	}

	public void setDirectorioErrores(File directorioErrores) {
		this.directorioErrores = directorioErrores;
	}

	// --------------------------------------------------------------------------------------------
	protected FilenameFilter getFilenameFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return match(name);
			}
		};
	}

	protected boolean match(final String name) {
		return getPattern().matcher(name).matches();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private DateTimeFormatter formatoFechaCorta = null;
	private DateTimeFormatter formatoFechaLarga = null;
	private DecimalFormat formatoEntero = null;
	private DecimalFormat formatoMoneda = null;
	private DecimalFormat formatoCoordenada = null;
	private DateTimeFormatter formatoHoraHH = null;
	private DateTimeFormatter formatoHoraHHmm = null;

	protected DateTimeFormatter getFormatoHoraHH() {
		if (formatoHoraHH == null) {
			formatoHoraHH = DateTimeFormatter.ofPattern("H");
		}
		return formatoHoraHH;
	}

	protected DateTimeFormatter getFormatoHoraHHmm() {
		if (formatoHoraHHmm == null) {
			formatoHoraHHmm = DateTimeFormatter.ofPattern("H:mm");
		}
		return formatoHoraHHmm;
	}

	protected DateTimeFormatter getFormatoFechaCorta() {
		if (formatoFechaCorta == null) {
			formatoFechaCorta = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		}
		return formatoFechaCorta;
	}

	protected DateTimeFormatter getFormatoFechaLarga() {
		if (formatoFechaLarga == null) {
			formatoFechaLarga = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm:ss");
		}
		return formatoFechaLarga;
	}

	protected DecimalFormat getFormatoEntero() {
		if (formatoEntero == null) {
			formatoEntero = new DecimalFormat("########0");
		}
		return formatoEntero;
	}

	protected DecimalFormat getFormatoModeda() {
		if (formatoMoneda == null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setCurrencySymbol("");
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');

			formatoMoneda = new DecimalFormat("###,###,###,##0", symbols);
		}
		return formatoMoneda;
	}

	protected DecimalFormat getFormatoCoordenada() {
		if (formatoCoordenada == null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setCurrencySymbol("");
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');

			formatoCoordenada = new DecimalFormat("##0.#######", symbols);
		}
		return formatoCoordenada;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Deprecated
	protected void logParseException(MensajesDto mensajes, String key, Enum<?> property, String value, String format,
			String data) {
		logParseException(mensajes, key, property.toString(), value, format, data);
	}

	@Deprecated
	protected void logParseException(MensajesDto mensajes, String key, String property, String value, String format,
			String data) {
		String texto = MessageFormat.format(VALOR_NO_CONCUERDA_CON_EL_FORMATO_ESPERADO, key, property.toString(),
				format);
		logError(mensajes, texto, key, property, data);
	}
}
