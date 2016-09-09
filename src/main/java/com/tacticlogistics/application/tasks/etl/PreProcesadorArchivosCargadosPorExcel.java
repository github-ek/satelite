package com.tacticlogistics.application.tasks.etl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.tacticlogistics.infrastructure.services.Basic;

@Component
public class PreProcesadorArchivosCargadosPorExcel {
    private static final Logger log = LoggerFactory.getLogger(PreProcesadorArchivosCargadosPorExcel.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    public void run() {
        List<SimpleEntry<String, Path>> list = getContenidoDeArchivosCargadosPorExcel();

        for (SimpleEntry<String, Path> keyValue : list) {
            String key = keyValue.getKey();
            Path path = keyValue.getValue();

            File directory = path.toFile().getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            log.info("Generando archivo: {} ", path.toString());

            boolean OK = false;
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                List<String> rows = namedParameterJdbcTemplate.query(getQueryTextoArchivoCargado(),
                        new MapSqlParameterSource("cargaId", key), (rs, rowNum) -> rs.getString("texto"));

                for (String row : rows) {
                    writer.write(row);
                    writer.newLine();
                }

                OK = true;
            } catch (IOException e) {
                log.debug("Error al generar el archivo {}", key, e);
            }

            String sql = getQueryUpdateArchivoCarga();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("cargaId", key);
            parameters.put("estado", OK ? "PROCESADO" : "ERROR");
            parameters.put("fecha", new Date());

            namedParameterJdbcTemplate.update(sql, parameters);
        }
    }

    // -------------------------------------------------------------------------------------------------------
    protected List<SimpleEntry<String, Path>> getContenidoDeArchivosCargadosPorExcel() {
        final Path raiz = Paths.get(getNombreDirectorioRaiz()).resolve(getNombreDirectorioEntradaSolicitudesCargadasPorExcel());
        final String fechaProcesamiento = getFormatter().format(new Date());

        return namedParameterJdbcTemplate.query(getQueryArchivosCargados(), (rs, rowNum) -> {
            String codigoCliente = Basic.coalesce(rs.getString("codigoCliente"), "").trim().toUpperCase();
            String usuario = Basic.coalesce(rs.getString("usuario"), "").trim().toLowerCase();
            String cargaId = Basic.coalesce(rs.getString("cargaId"), "").trim();

            String subdirectorio = "SALIDAS";
            String tokens[] = cargaId.split("_");

            if (tokens.length > 0) {
                if (tokens[0].equalsIgnoreCase("INGRESOS")) {
                    subdirectorio = "INGRESOS";
                }else if(tokens[0].equalsIgnoreCase("SALIDAS")) {
                    subdirectorio = "SALIDAS"; 
                }
            }

            String nombreArchivo = MessageFormat.format("{0}\\{1}_{2}_{3}_{4}.TXT", subdirectorio, fechaProcesamiento,
                    codigoCliente, usuario, cargaId).replaceAll(" ", "-");

            return new SimpleEntry<String, Path>(cargaId, raiz.resolve(nombreArchivo));
        });
    }
    
    protected String getQueryArchivosCargados() {
        return " SELECT "
                + "     MAX(CASE WHEN COALESCE(a.id_cliente,'') = 'ID_CLIENTE' THEN NULL ELSE COALESCE(a.id_cliente,'') END) AS  codigoCliente,"
                + "     MAX(a.usuario) AS usuario," + "     a.id_carga AS cargaId," + "     MAX(a.fecha) AS fechaCargue"
                + " FROM dbo.carga_texto a " 
                + " WHERE 0 = 0 "
                + " AND a.estado = 'CARGADO' "
                //+ " AND a.id_carga = '705453667_20160830161942' "
                //+ " AND a.id_carga IN ('INGRESOS_705453667_20160601161634') "
                //+ " AND 1 = 0 "
                + " GROUP BY a.id_carga "
                + " ORDER BY MAX(a.fecha), a.id_carga";
    }

    protected String getQueryTextoArchivoCargado() {
        return " SELECT " 
        		+ "   a.texto " 
        		+ " FROM dbo.carga_texto a " 
        		+ " WHERE 0 = 0 " 
        		+ " AND a.id_carga = :cargaId "
                + " ORDER BY " 
        		+ "   a.id_linea_carga";
    }

    protected String getQueryUpdateArchivoCarga() {
        return " UPDATE dbo.carga_texto " 
        		+ " SET " 
        		+ "     estado = :estado, " 
        		+ "     fecha = :fecha " 
        		+ " WHERE "
                + "     id_carga = :cargaId ";
    }    
    
    // -------------------------------------------------------------------------------------------------------
    @Value("${etl.directorio.raiz}")
    private String nombreDirectorioRaiz;

    @Value("${etl.directorio.entradas.solicitudes.cargadas-x-excel}")
    private String nombreDirectorioEntradaSolicitudesCargadasPorExcel;

    public String getNombreDirectorioRaiz() {
        return nombreDirectorioRaiz;
    }

    protected String getNombreDirectorioEntradaSolicitudesCargadasPorExcel() {
        return nombreDirectorioEntradaSolicitudesCargadasPorExcel;
    }
    
    private SimpleDateFormat formatter = null;

    protected SimpleDateFormat getFormatter() {
        if (formatter == null) {
            formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        }
        return formatter;
    }
}
