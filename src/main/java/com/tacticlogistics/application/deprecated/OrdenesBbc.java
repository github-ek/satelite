package com.tacticlogistics.application.deprecated;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos;
import com.tacticlogistics.infrastructure.services.Basic;

//@Component("ordenes.BBC.ORDENES")
public class OrdenesBbc extends ETLPDFFileStrategy<ETLOrdenDto> {

    private final static String _NUMERO_DOCUMENTO_ORDEN_CLIENTE = OrdenDtoAtributos.NUMERO_ORDEN
            .toString();
    private final static String _DESTINATARIO_NUMERO_IDENTIFICACION = OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION
            .toString();
    private final static String _DESTINO_CODIGO = "DESTINO_CODIGO";
    private final static String _DESTINO_NOMBRE = OrdenDtoAtributos.DESTINO_NOMBRE.toString();
    private final static String _LINEA_ORIGEN_CODIGO = "LINEA_ORIGEN_CODIGO";
    private final static String _LINEA_ORIGEN_NOMBRE = "LINEA_ORIGEN_NOMBRE";
    private final static String _NOTAS_APROBACION_CLIENTE = OrdenDtoAtributos.NOTAS.toString();
    private final static String _LINEAS = "LINEAS";
    private final static String _USUARIO_APROBACION_CLIENTE = "USUARIO_CONFIRMACION_CLIENTE";

    private DecimalFormat cantidadSolicitadaFormat = null;

    @Override
    protected String limpiar(String texto) {
        boolean encontradoNuevaOrden = false;
        boolean encontradoNumeroOrden = false;
        boolean encontradoDestinatarioNumeroIdentificacion = false;
        boolean encontradoOrigen = false;
        boolean encontradoDestino = false;
        boolean encontradoNotas = false;
        boolean encontradoInicioLineas = false;
        boolean encontradoFinLineas = false;
        boolean encontradoFinPagina = false;

        StringBuffer sb = new StringBuffer();
        texto = super.limpiar(texto);
        String lineas[] = texto.split("\r\n");

        for (int i = 0; i < lineas.length; i++) {
            lineas[i] = lineas[i].trim();
        }

        int index = 0;
        for (int i = 0; i < lineas.length; i++) {
            if (!encontradoNuevaOrden) {
                encontradoNuevaOrden = true;
                encontradoNumeroOrden = false;
                encontradoDestinatarioNumeroIdentificacion = false;
                encontradoOrigen = false;
                encontradoDestino = false;
                encontradoNotas = false;
                encontradoInicioLineas = false;
                encontradoFinLineas = false;
                encontradoFinPagina = false;

                sb.append("@orden").append("\n");
            } else {
                if (!encontradoNumeroOrden) {
                    if (index == 4) {
                        encontradoNumeroOrden = true;

                        sb.append(_NUMERO_DOCUMENTO_ORDEN_CLIENTE).append("\t").append(lineas[i]).append("\n");
                    }
                } else {
                    if (!encontradoDestinatarioNumeroIdentificacion) {
                        if (index == 7) {
                            encontradoDestinatarioNumeroIdentificacion = true;

                            sb.append(_DESTINATARIO_NUMERO_IDENTIFICACION).append("\t").append(lineas[i]).append("\n");
                        }
                    } else {
                        if (!encontradoOrigen) {
                            if (lineas[i].endsWith("BODEGA SALIDA:")) {
                                encontradoOrigen = true;

                                lineas[i] = lineas[i].substring(0, lineas[i].length() - "BODEGA SALIDA:".length());

                                sb.append(extraerDestinoOrigen(lineas[i], _LINEA_ORIGEN_CODIGO, _LINEA_ORIGEN_NOMBRE));
                            }
                        } else {
                            if (!encontradoDestino) {
                                if (lineas[i].startsWith("BODEGA ENTRADA:")) {
                                    encontradoDestino = true;

                                    lineas[i] = lineas[i].substring("BODEGA ENTRADA:".length()).trim();

                                    sb.append(extraerDestinoOrigen(lineas[i], _DESTINO_CODIGO, _DESTINO_NOMBRE));
                                }
                            } else {
                                if (!encontradoNotas) {
                                    if (lineas[i].startsWith("NOTAS:")) {
                                        encontradoNotas = true;

                                        lineas[i] = lineas[i].substring("NOTAS:".length()).trim();
                                        sb.append(_NOTAS_APROBACION_CLIENTE).append("\t").append(lineas[i]).append("\n");
                                    }
                                } else {
                                    if (!encontradoInicioLineas) {
                                        if (lineas[i].equals(
                                                "ITEM BODEGA MOTIVO U.M. CANTIDAD E/S LOTE C.O. U.N. C.COSTO")) {
                                            encontradoInicioLineas = true;
                                        }
                                    } else {
                                        if (!encontradoFinLineas) {
                                            if (!lineas[i].equals("RECIBIDOELABORADO POR APROBADO")) {
                                                if (!lineas[i].isEmpty()) {
                                                    if (!lineas[i - 1].isEmpty()) {
                                                        sb.append(_LINEAS).append("\t");
                                                    }

                                                    sb.append(lineas[i]);

                                                    if ((i + 1 < lineas.length) && (!lineas[i + 1].isEmpty())) {
                                                        sb.append("\n");
                                                    }
                                                } else {
                                                    sb.append(" ");
                                                }
                                            } else {
                                                encontradoFinLineas = true;
                                            }
                                        } else {
                                            if (!encontradoFinPagina) {
                                                if (lineas[i].startsWith("PAG.")) {
                                                    sb.append(_USUARIO_APROBACION_CLIENTE).append("\t").append(lineas[i - 1])
                                                            .append("\n");
                                                    encontradoNuevaOrden = false;
                                                    encontradoFinPagina = true;
                                                    index = -1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            index++;
        }

        return sb.toString();
    }

    @Override
    protected Map<String, ETLOrdenDto> transformar(String texto) {
        Map<String, ETLOrdenDto> map = new HashMap<>();

        String paginas[] = texto.split("@orden\n");

        for (String pagina : paginas) {
            if (pagina.isEmpty()) {
                continue;
            }

            Map<String, String> orden = new HashMap<>();
            List<String> lineas = new ArrayList<>();

            configurarMapping(pagina, orden, lineas);

            String key = generarIdentificadorRegistro(orden);
            if (!map.containsKey(key)) {
                adicionar(key, map, orden, lineas);
            }
            modificar(key, map, orden, lineas);
        }

        return map;
    }

    protected void configurarMapping(String pagina, Map<String, String> orden, List<String> lineas) {
        for (String campo : pagina.split("\n")) {
            campo = campo.trim();
            String keyValue[] = campo.split("\t");

            if (keyValue.length <= 2) {
                String key = keyValue[0];
                String value = (keyValue.length == 2) ? keyValue[1] : "";

                if (!key.equals(_LINEAS)) {
                    orden.put(key, value);
                } else {
                    lineas.add(value);
                }
            } else {
                throw new RuntimeException("Error al identificar una combinación (atributo,valor)");
            }
        }

        checkCamposRequeridos(orden);
    }

    private void checkCamposRequeridos(Map<String, String> orden) {
        // TODO
    }

    @Override
    protected void cargar(Map<String, ETLOrdenDto> map) {
        System.out.println(map);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private String generarIdentificadorRegistro(Map<String, String> orden) {
        return orden.get(_NUMERO_DOCUMENTO_ORDEN_CLIENTE);
    }

    private void adicionar(String key, Map<String, ETLOrdenDto> map, Map<String, String> orden, List<String> lineas) {
        if (!map.containsKey(key)) {
            ETLOrdenDto dto = new ETLOrdenDto();

            dto.setTipoServicioCodigoAlterno(getTipoServicioCodigoAlterno());
            dto.setClienteCodigo(getClienteCodigoAlterno());

            dto.setNumeroOrden(orden.get(_NUMERO_DOCUMENTO_ORDEN_CLIENTE));
            dto.setDestinatarioNumeroIdentificacion(orden.get(_DESTINATARIO_NUMERO_IDENTIFICACION));
            //dto.setDestinoCodigo(orden.get(_DESTINO_CODIGO));
            dto.setDestinoNombre(orden.get(_DESTINO_NOMBRE));
            dto.setNotasConfirmacion(orden.get(_NOTAS_APROBACION_CLIENTE));
            dto.setUsuarioConfirmacion(orden.get(_USUARIO_APROBACION_CLIENTE));

            for (String linea : lineas) {
                dto.getLineas().add(extraerLinea(orden, linea));
            }

            map.put(key, dto);
        }
    }

    private ETLLineaOrdenDto extraerLinea(Map<String, String> orden, String linea) {
        linea = linea.replaceAll("\\s+", " ");
        String campos[] = linea.split(" ");
        boolean encontradoCantidadSolicitada = false;
        boolean encontradoIndicadorSalida = false;
        boolean encontradoCO = false;
        boolean encontradoUN = false;
        boolean encontradoProductoCodigoAlterno = false;

        ETLLineaOrdenDto dto = new ETLLineaOrdenDto();

        StringBuffer sb = new StringBuffer();
        //dto.setOrigenCodigo(orden.get(_LINEA_ORIGEN_CODIGO));
        //dto.setOrigenNombre(orden.get(_LINEA_ORIGEN_NOMBRE));

        for (int i = 0; i < campos.length; i++) {
            if (!encontradoCantidadSolicitada) {
                encontradoCantidadSolicitada = true;
                try {
                    dto.setCantidadSolicitada(Basic.parseEntero(campos[i], getCantidadSolicitadaFormat()));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                if (!encontradoIndicadorSalida) {
                    if (campos[i].equals("S")) {
                        encontradoIndicadorSalida = true;
                        //dto.setUnidadCodigoAlterno(campos[i - 1]);
                    }
                } else {
                    if (!encontradoCO) {
                        encontradoCO = true;
                    } else {
                        if (!encontradoUN) {
                            encontradoUN = true;
                        } else {
                            if (!encontradoProductoCodigoAlterno) {
                                if (!campos[i].isEmpty()) {
                                    encontradoProductoCodigoAlterno = true;
                                    //dto.setProductoCodigoAlterno(campos[i]);
                                }
                            } else {
                                sb.append(campos[i]).append(" ");
                            }
                        }
                    }
                }
            }
        }

        dto.setDescripcion(sb.toString().trim());
        return dto;
    }

    private String getTipoServicioCodigoAlterno() {
        return "TRANSPORTE";
    }

    private String getClienteCodigoAlterno() {
        return "BBC";
    }

    private void modificar(String key, Map<String, ETLOrdenDto> map, Map<String, String> orden, List<String> lineas) {
        // TODO Auto-generated method stub

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private String extraerDestinoOrigen(String texto, String keyCodigo, String keyNombre) {
        String campos[] = texto.split(" ");

        StringBuffer sb = new StringBuffer();
        sb.append(keyCodigo).append("\t").append(campos[0]).append("\n");

        sb.append(keyNombre).append("\t");
        for (int i = 1; i < campos.length; i++) {
            sb.append(campos[i]);
            if (i + 1 < campos.length) {
                sb.append(" ");
            }
        }
        sb.append("\n");

        return sb.toString();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    public DecimalFormat getCantidadSolicitadaFormat() {
        if (cantidadSolicitadaFormat == null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');

            cantidadSolicitadaFormat = new DecimalFormat("###,###,##0.######", symbols);
        }
        return cantidadSolicitadaFormat;
    }
}
