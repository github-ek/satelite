package com.tacticlogistics.application.etl.core;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DirectorioClienteHandler extends AbstractHandler<Path> {
	@Value("${etl.directorio.entradas}")
	private String subDirectorioDeEntradas;

	@Override
	protected boolean canHandleRequest(Path directorio) {
		if (directorio == null) {
			return false;
		}

		return directorio.endsWith(getWorkingDirectory());
	}

	@Override
	protected void handleRequest(Path directorio) {

		ArchivoHandler rootChain = getArchivoETLHandlerChain();
		if (rootChain == null) {
			return;
		}

		procesarDirectorio(rootChain, getSubDirectorioDeEntradas(directorio));
	}

	private void procesarDirectorio(ArchivoHandler rootChain, Path directorio) {
		log.info("Inicio de lectura del directorio {}", directorio);
		try (DirectoryStream<Path> stream = getArchivos(directorio)) {
			for (Path path : stream) {
				if (Files.isDirectory(path)) {
					procesarDirectorio(rootChain, path);
				} else {
					rootChain.receiveRequest(path);
				}
			}
		} catch (IOException | DirectoryIteratorException e) {
			log.error("Ocurrio el siguiente error: {} \nEl tipo de excepción es {}", e.getMessage(),
					e.getClass().getName());
		}
		log.info("Fin de lectura del directorio {}", directorio);
	}

	// -----------------------------------------------------------------------------------------------
	abstract protected String getWorkingDirectory();

	abstract protected ArchivoHandler getArchivoETLHandlerChain();

	// -----------------------------------------------------------------------------------------------
	protected Path getSubDirectorioDeEntradas(Path directorioRaiz) {
		Path path = Paths.get(directorioRaiz.toAbsolutePath().toString(), subDirectorioDeEntradas);
		if (Files.notExists(path)) {
			throw new RuntimeException(String.format("El directorio de entrada {} no existe", path.toAbsolutePath()));
		}
		return path;
	}

	private DirectoryStream<Path> getArchivos(Path directorio) throws IOException {
		return Files.newDirectoryStream(directorio);
	}
}