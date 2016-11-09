package com.tacticlogistics.application.etl;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tacticlogistics.application.etl.core.DirectorioClienteHandler;
import com.tacticlogistics.application.etl.dicermex.DicermexDirectorioClienteETLHandler;
import com.tacticlogistics.application.etl.gws.GWSDirectorioClienteETLHandler;
import com.tacticlogistics.application.etl.tactic.TacticDirectorioClienteETLHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ETLService {

	@Value("${etl.directorio.raiz}")
	private String directorioRaiz;

	@Autowired
	private DicermexDirectorioClienteETLHandler dicermexETLHandler;

	@Autowired
	private GWSDirectorioClienteETLHandler gwsETLHandler;

	@Autowired
	private TacticDirectorioClienteETLHandler tacticETLHandler;

	public void run() {
		DirectorioClienteHandler rootChain = getDirectorioETLHandlerChain();
		if (rootChain == null) {
			return;
		}
		
		try (DirectoryStream<Path> stream = getSubDirectorios()) {

			for (Path path : stream) {
				log.info("Procesando directorio {}", path);
				rootChain.receiveRequest(path);
			}

		} catch (IOException | DirectoryIteratorException e) {
			log.error("Ocurrio el siguiente error: {} \nEl tipo de excepción es {}", e.getMessage(),
					e.getClass().getName());
		}
	}

	// -----------------------------------------------------------------------------------------------
	private DirectorioClienteHandler getDirectorioETLHandlerChain() {
		DirectorioClienteHandler rootChain = dicermexETLHandler;
		rootChain.setNextHandler(gwsETLHandler);
		rootChain.setNextHandler(tacticETLHandler);
		return rootChain;
	}

	// -----------------------------------------------------------------------------------------------
	private Path getDirectorioRaiz() {
		Path path = Paths.get(directorioRaiz);
		if (Files.notExists(path)) {
			throw new RuntimeException(String.format("El directorio de clientes {} no existe", path.toAbsolutePath()));
		}

		return path;
	}

	private DirectoryStream<Path> getSubDirectorios() throws IOException {
		return Files.newDirectoryStream(getDirectorioRaiz(), getFilter());
	}

	private Filter<Path> getFilter() {
		return new DirectoryStream.Filter<Path>() {
			public boolean accept(Path path) throws IOException {
				return (Files.isDirectory(path));
			}
		};
	}
}