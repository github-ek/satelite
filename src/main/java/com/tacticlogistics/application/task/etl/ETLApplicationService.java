package com.tacticlogistics.application.task.etl;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tacticlogistics.application.task.etl.components.ETLStrategy;
import com.tacticlogistics.application.task.etl.components.ETLStrategyFactory;

@Service
public class ETLApplicationService {
	private static final Logger log = LoggerFactory.getLogger(ETLApplicationService.class);

	@Autowired
	private ETLStrategyFactory factory;

	@Autowired
	private PreProcesadorArchivosCargadosPorExcel preProcesadorArchivosCargadosPorExcel;

	public void run() {
		try {
			preProcesar();
		} catch (RuntimeException e) {
			onPreProcesarError(e);
			throw e;
		}

		try {
			procesar();
		} catch (RuntimeException e) {
			onProcesarError(e);
			throw e;
		}

		try {
			postProcesar();
		} catch (RuntimeException e) {
			onPostProcesarError(e);
			throw e;
		}
	}

	private void preProcesar() {
		preProcesadorArchivosCargadosPorExcel.run();
	}

	private void procesar() {
		final Path raiz = Paths.get(getNombreDirectorioRaiz());
		final Path subdirectorioBackup = getSubdirectorioBackup();

		log.info("Leyendo directorio raiz {}", raiz.toAbsolutePath());

		File directorios[] = raiz.toFile().listFiles(getFileFilterSoloDirectorios());

		for (File d : directorios) {
			final Path entrada = Paths.get(d.getAbsolutePath()).resolve(getNombreDirectorioEntrada());
			final Path salida = Paths.get(d.getAbsolutePath()).resolve(getNombreDirectorioSalida());
			final Path procesados = Paths.get(d.getAbsolutePath()).resolve(getNombreDirectorioProcesados());
			final Path errores = Paths.get(d.getAbsolutePath()).resolve(getNombreDirectorioErrores());

			checkExistenciaDeDirectorios(entrada, salida, procesados, errores);

			procesarSubdirectorios(entrada, entrada, salida, procesados, errores, subdirectorioBackup);
		}
	}

	private void postProcesar() {

	}

	private void onPreProcesarError(RuntimeException e) {
		log.error("onPreProcesarError", e);
	}

	private void onProcesarError(RuntimeException e) {
		log.error("onProcesarError", e);
	}

	private void onPostProcesarError(RuntimeException e) {
		log.error("onPostProcesarError", e);
	}

	// ----------------------------------------------------------------------------------------------------------------
	protected void checkExistenciaDeDirectorios(final Path entrada, final Path salida, final Path procesados,
			final Path errores) {
		final Path paths[] = new Path[] { entrada, salida, procesados, errores };

		for (Path path : paths) {
			File file = path.toFile();
			if (!file.exists()) {
				file.mkdirs();
				log.info("Creando directorio " + file.getAbsolutePath());
			}
		}
	}

	protected void procesarSubdirectorios(final Path raiz, final Path entrada, final Path salida, final Path procesados,
			final Path errores, final Path subdirectorioBackup) {
		procesarArchivos(raiz, entrada, salida.resolve(subdirectorioBackup), procesados.resolve(subdirectorioBackup),
				errores.resolve(subdirectorioBackup));

		File directorios[] = entrada.toFile().listFiles(getFileFilterSoloDirectorios());
		for (File directorio : directorios) {
			procesarSubdirectorios(
					raiz, 
					directorio.toPath(), 
					salida.resolve(directorio.getName()),
					procesados.resolve(directorio.getName()), 
					errores.resolve(directorio.getName()),
					subdirectorioBackup);
		}
	}

	private void procesarArchivos(Path raiz, Path entrada, Path salida, Path procesados, Path errores) {
		File archivos[] = entrada.toFile().listFiles(getFileFilterSoloArchivos());
		if(archivos != null){
			if (archivos.length > 0) {
				Path subdirectorio = raiz.relativize(entrada);
				
				String codigoComponente = raiz.getParent().getParent().relativize(raiz.getParent()) + "."
						+ subdirectorio.toString().replace("\\", ".");

				if (getCodigosDeComponenteNoExistentes().contains(codigoComponente)) {
					return;
				}

				try {
					ETLStrategy<?> etl = null;
					etl = factory.getETLStrategy(codigoComponente);
					etl.run(entrada, salida, procesados, errores);
				} catch (NoSuchBeanDefinitionException ex) {
					getCodigosDeComponenteNoExistentes().add(codigoComponente);
					log.error("NO EXISTE UN COMPONENTE PARA EL COMPONENTE {}, DIRECTORIO {}", codigoComponente,
							entrada.toString());
				}
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------
	@Value("${etl.directorio.raiz}")
	private String nombreDirectorioRaiz;

	@Value("${etl.directorio.entradas}")
	private String nombreDirectorioEntrada;

	@Value("${etl.directorio.salidas}")
	private String nombreDirectorioSalida;

	@Value("${etl.directorio.procesados}")
	private String nombreDirectorioProcesados;

	@Value("${etl.directorio.errores}")
	private String nombreDirectorioErrores;

	public String getNombreDirectorioRaiz() {
		return nombreDirectorioRaiz;
	}

	protected String getNombreDirectorioEntrada() {
		return nombreDirectorioEntrada;
	}

	protected String getNombreDirectorioSalida() {
		return nombreDirectorioSalida;
	}

	protected String getNombreDirectorioProcesados() {
		return nombreDirectorioProcesados;
	}

	protected String getNombreDirectorioErrores() {
		return nombreDirectorioErrores;
	}

	// ----------------------------------------------------------------------------------------------------------------
	private SimpleDateFormat formatter = null;

	protected SimpleDateFormat getFormatter() {
		if (formatter == null) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		return formatter;
	}

	private FileFilter fileFilterSoloDirectorios = null;

	protected FileFilter getFileFilterSoloDirectorios() {
		if (fileFilterSoloDirectorios == null) {
			fileFilterSoloDirectorios = new FileFilter() {
				public boolean accept(File pathname) {
					return (pathname.isDirectory());
				}
			};
		}
		return fileFilterSoloDirectorios;
	}

	private FileFilter fileFilterSoloArchivos = null;

	protected FileFilter getFileFilterSoloArchivos() {
		if (fileFilterSoloArchivos == null) {
			fileFilterSoloArchivos = new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.isFile();
				}
			};
		}
		return fileFilterSoloArchivos;
	}

	protected Path getSubdirectorioBackup() {
		String value = getFormatter().format(new Date());
		return Paths.get(value.substring(0, 6)).resolve(value.substring(0, 8));
	}

	private final List<String> codigoComponentesNoExistentes = new ArrayList<>();

	protected List<String> getCodigosDeComponenteNoExistentes() {
		return codigoComponentesNoExistentes;
	}
}