package com.tacticlogistics.application.task.etl.deprecated.components.club;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.task.etl.OrdenDtoAtributos;
import com.tacticlogistics.application.task.etl.deprecated.components.ETLPDFFileStrategy;
import com.tacticlogistics.infrastructure.services.Basic;

//@Component("CLUB.FACTURAS")
public class OrdenesClub extends ETLPDFFileStrategy<ETLOrdenDto> {
    private DecimalFormat cantidadSolicitadaFormat = null;
    private DecimalFormat valorFormat = null;

    @Override
    protected String limpiar(String texto) {
        boolean encontradoDestinatarioNombreAlterno = false;
        boolean encontradoDestinoDireccion = false;
        boolean encontradoDestinatarioNumeroIdentificacion = false;
        boolean encontradoRequerimientoEntregaFormaPago = false;
        boolean encontradoNumeroOrden = false;
        boolean encontradoClienteCodigoAlterno = false;
        boolean encontradoTokenInicioDeLineas = false;
        boolean encontradoTokenFinDeLineas = false;
        boolean encontradoTokenObservaciones = false;

        StringBuffer sb = new StringBuffer("@orden\n");
        texto = super.limpiar(texto);
        String lineas[] = texto.split("\r\n");

        for (int i = 0; i < lineas.length; i++) {
            lineas[i] = lineas[i].trim();
            if(lineas[i].contains("RES. DIAN POR COMPUTADOR  NO.")){
                lineas[i] = "";
            }
            if(lineas[i].matches(".* PREFIJO .* DESDE .* HASTA .*")){
                lineas[i] = "";
            }
        }

        for (int i = 0; i < lineas.length; i++) {
            if (!encontradoDestinatarioNombreAlterno) {
                encontradoDestinatarioNombreAlterno = true;

                sb.append(OrdenDtoAtributos.DESTINATARIO_NOMBRE.toString()).append("\t").append(lineas[i]).append("\n");
            } else {
                if (!encontradoDestinoDireccion) {
                    encontradoDestinoDireccion = true;

                    StringBuffer sbDireccion = new StringBuffer();
                    StringBuffer sbTelefonos = new StringBuffer();
                    String values[] = lineas[i].split(" ");

                    for (int k = values.length - 1; k >= 0; k--) {
                        if (values[k].matches("\\d{7}\\d*")) {
                            sbTelefonos.append(values[k]).append(" ");
                        } else {
                            for (int j = 0; j <= k; j++) {
                                sbDireccion.append(values[j]).append(" ");
                            }
                            break;
                        }
                    }

                    sb.append(OrdenDtoAtributos.DESTINO_DIRECCION.toString()).append("\t")
                            .append(sbDireccion.toString().trim()).append("\n");
                    sb.append(OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS.toString()).append("\t")
                            .append(sbTelefonos.toString().trim()).append("\n");
                } else {
                    if (!encontradoDestinatarioNumeroIdentificacion) {
                        encontradoDestinatarioNumeroIdentificacion = true;
                        String value[] = lineas[i].split("-");
                        for (int j = 0; j < value.length; j++) {
                            value[j] = value[j].trim();
                            switch (j) {
                            case 0:
                                sb.append(OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION.toString()).append("\t")
                                        .append(value[j]).append("\n");
                                break;
                            case 1:
//                                sb.append(OrdenDtoAtributos.DESTINATARIO_DIGITO_VERIFICACION.toString()).append("\t")
//                                        .append(value[j]).append("\n");
                                break;
                            default:
                                break;
                            }
                        }
                    } else {
                        if (!encontradoRequerimientoEntregaFormaPago) {
                            if (lineas[i].startsWith("F.P.")) {
                                encontradoRequerimientoEntregaFormaPago = true;
                                String value = lineas[i].substring("F.P.".length()).trim();
                                sb.append("REQUERIMIENTO_ENTREGA").append("\t").append(value).append("\n");
                            }
                        } else {
                            if (!encontradoNumeroOrden) {
                                if (lineas[i].startsWith("NO.")) {
                                    encontradoNumeroOrden = true;

                                    String value = lineas[i].substring("NO.".length()).replaceAll("\\s+", "");
                                    sb.append(OrdenDtoAtributos.NUMERO_ORDEN.toString()).append("\t")
                                            .append(value).append("\n");
                                }
                            } else {
                                if (!encontradoClienteCodigoAlterno) {
                                    if (lineas[i].endsWith("FECHA VENCIMIENTO")) {
                                        encontradoClienteCodigoAlterno = true;

                                        String value;
                                        if (lineas[i + 2].equals("WWW.CLUBDELVINO.COM.CO")) {
                                            value = lineas[i + 1];
                                        } else {
                                            value = "NO DIRECCION ALTERNA";
                                        }

                                        sb.append("DESTINO_DIRECCION_ALTERNA").append("\t").append(value).append("\n");
                                    }
                                } else {
                                    if (!encontradoTokenInicioDeLineas) {
                                        if (lineas[i].equals("TOTAL")) {
                                            encontradoTokenInicioDeLineas = true;
                                        }
                                    } else {
                                        if (!encontradoTokenFinDeLineas) {
                                            if(lineas[i].isEmpty()){
                                                continue;
                                            }
                                            
                                            if (i + 1 < lineas.length) {
                                                if (lineas[i + 1].startsWith(
                                                        "POR HABER RECIBIDO A SATISFACCION LA ENTREGA REAL Y MATERIAL DE LAS MERCANCIAS AQUI DESCRITAS")) {
                                                    encontradoTokenFinDeLineas = true;
                                                } else {
                                                    sb.append("LINEAS").append("\t").append(lineas[i]).append("\n");
                                                }
                                            }
                                        } else {
                                            if (!encontradoTokenObservaciones) {
                                                if (lineas[i].startsWith("OBSERVACIONES")) {
                                                    encontradoTokenObservaciones = true;
                                                    String value = lineas[i].substring("OBSERVACIONES".length()).trim();
                                                    sb.append(OrdenDtoAtributos.NOTAS.toString())
                                                            .append("\t").append(value).append("\n");
                                                    break;
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

                if (!key.equals("LINEAS")) {
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
        StringBuffer sb = new StringBuffer();
        sb.append("\n\n");
        sb.append("---------------------------------------------------------------------------------------------\n");
        sb.append("---------------------------------------------------------------------------------------------\n");
        //int i = 0;
        for (ETLOrdenDto dto : map.values()) {
            try {
//                Orden model = ordenesService.saveOrden(dto);
//                if (model == null) {
//                    sb.append(dto.getNumeroOrden()).append("\t").append("EXISTENTE").append("\n");
//                }
//                System.out.println(i++);
            } catch (Exception e) {
                sb.append(dto.getNumeroOrden()).append("\t").append(e.getMessage()).append("\n");
            }
        }
        System.out.println(sb.toString());
        System.out.println(map);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private String generarIdentificadorRegistro(Map<String, String> orden) {
        return orden.get(OrdenDtoAtributos.NUMERO_ORDEN.toString());
    }

    private void adicionar(String key, Map<String, ETLOrdenDto> map, Map<String, String> orden, List<String> lineas) {
        if (!map.containsKey(key)) {
            ETLOrdenDto dto = new ETLOrdenDto();

            dto.setTipoServicioCodigoAlterno(getTipoServicioCodigoAlterno());
            dto.setClienteCodigo(getClienteCodigoAlterno());

//            dto.setDestinatarioNombre(orden.get(OrdenDtoAtributos.DESTINATARIO_NOMBRE.toString()));
//            dto.setDestinoDireccion(orden.get(OrdenDtoAtributos.DESTINO_DIRECCION.toString()));
//            dto.setDestinoContactoTelefonos(orden.get(OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS.toString()));
            dto.setDestinatarioNumeroIdentificacion(
                    orden.get(OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION.toString()));
//            dto.setDestinatarioDigitoVerificacion(
//                    orden.get(OrdenDtoAtributos.DESTINATARIO_DIGITO_VERIFICACION.toString()));
            dto.getRequerimientosDistribucion().add(orden.get("REQUERIMIENTO_ENTREGA"));
            dto.setNumeroOrden(orden.get(OrdenDtoAtributos.NUMERO_ORDEN.toString()));
            // DESTINO_DIRECCION_ALTERNA
            dto.setNotasConfirmacion(orden.get(OrdenDtoAtributos.NOTAS.toString()));
            dto.setUsuarioConfirmacion(getClienteCodigoAlterno());

            for (String linea : lineas) {
                dto.getLineas().add(extraerLinea(orden, linea));
            }

            map.put(key, dto);
        }
    }

    private ETLLineaOrdenDto extraerLinea(Map<String, String> orden, String linea) {
        boolean encontradoTokenValorDeclaradoPorUnidad = false;
        boolean encontradoTokenValorDeclaradoTotal = false;
        boolean encontradoTokenInicioDescripcion = false;
        boolean encontradoTokenFinDescripcion = false;

        ETLLineaOrdenDto dto = new ETLLineaOrdenDto();
        StringBuffer sb = new StringBuffer();

        linea = linea.replaceAll("\\s+", " ");
        String campos[] = linea.split(" ");

        for (int i = 0; i < campos.length; i++) {
            if (!encontradoTokenValorDeclaradoPorUnidad) {
                encontradoTokenValorDeclaradoPorUnidad = true;
                try {
                    dto.setValorDeclaradoPorUnidad(Basic.parseEntero(campos[i], getValorFormat()));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                if (!encontradoTokenValorDeclaradoTotal) {
                    encontradoTokenValorDeclaradoTotal = true;
                } else {
                    if (!encontradoTokenInicioDescripcion) {
                        encontradoTokenInicioDescripcion = true;
                        sb.append(campos[i]).append(" ");
                    } else {
                        if (!encontradoTokenFinDescripcion) {
                            if (campos[i].matches("(\\d{1,3}\\,)*\\d{3}")) {
                                encontradoTokenFinDescripcion = true;
                                break;
                            } else {
                                sb.append(campos[i]).append(" ");
                            }
                        }
                    }
                }
            }
        }
        
        parseLineaDescripcion(sb.toString().trim(), dto);

        return dto;
    }

    private String getTipoServicioCodigoAlterno() {
        return "OVP-PRODUCTOS";
    }

    private String getClienteCodigoAlterno() {
        return "CLUB";
    }

    private void modificar(String key, Map<String, ETLOrdenDto> map, Map<String, String> orden, List<String> lineas) {
        // TODO Auto-generated method stub

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private void parseLineaDescripcion(String texto, ETLLineaOrdenDto dto) {
        StringBuffer sb = new StringBuffer();
        String cantidadSolicitada = "";
        String descripcion = "";
        String productoCodigoAlterno = "";

        boolean encontradoTokenInicioDeNumeros = false;
        //Introduce un caracter tab cada vez que cambie de digito a !digito y viceversa
        for (int i = 0; i < texto.length(); i++) {
            if (Character.isDigit(texto.charAt(i))) {
                if (!encontradoTokenInicioDeNumeros) {
                    encontradoTokenInicioDeNumeros = true;
                    if (i > 0) {
                        sb.append("\t");
                    }
                }
            } else {
                if (encontradoTokenInicioDeNumeros) {
                    encontradoTokenInicioDeNumeros = false;
                    sb.append("\t");
                }
            }
            sb.append(texto.charAt(i));
        }

        String values[] = sb.toString().split("\t");
        if (values.length > 0) {
            cantidadSolicitada = values[0];
        }
        if (values.length > 2) {
            productoCodigoAlterno = values[values.length - 1];
        }

        sb = new StringBuffer();
        for (int i = 1; i < values.length - 1; i++) {
            sb.append(values[i]).append(" ");
        }

        if (productoCodigoAlterno.length() > 13) {
            sb.append(productoCodigoAlterno.substring(0, productoCodigoAlterno.length() - 13));
            productoCodigoAlterno = productoCodigoAlterno.substring(productoCodigoAlterno.length() - 13);
        }
        descripcion = Basic.limpiarEspacios(sb.toString().trim());

        try {
            dto.setCantidadSolicitada(Basic.parseEntero(cantidadSolicitada, getCantidadSolicitadaFormat()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dto.setDescripcion(descripcion);
        //dto.setProductoCodigoAlterno(productoCodigoAlterno);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    public DecimalFormat getCantidadSolicitadaFormat() {
        if (cantidadSolicitadaFormat == null) {
            cantidadSolicitadaFormat = new DecimalFormat("########0");
        }
        return cantidadSolicitadaFormat;
    }

    public DecimalFormat getValorFormat() {
        if (valorFormat == null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setCurrencySymbol("");
            symbols.setGroupingSeparator(',');
            symbols.setDecimalSeparator('.');

            valorFormat = new DecimalFormat("###,###,##0", symbols);
        }
        return valorFormat;
    }


    // --------------------------------------------------------------------------------------------
//    private void extraerDestinatarioNombreAlterno(String texto, Map<String, Object> model) {
//        model.put("destinatario_nombre_alterno", texto);
//    }
//
//    private void extraerDireccion(String texto, Map<String, Object> model) {
//        StringBuffer sbDireccion = new StringBuffer();
//        StringBuffer sbTelefonos = new StringBuffer();
//        String values[] = texto.split(" ");
//
//        for (int i = values.length - 1; i >= 0; i--) {
//            if (values[i].matches("\\d{7}\\d*")) {
//                sbTelefonos.append(values[i]).append(" ");
//                ;
//            } else {
//                for (int j = 0; j <= i; j++) {
//                    sbDireccion.append(values[j]).append(" ");
//                }
//                break;
//            }
//        }
//
//        model.put("direccion", sbDireccion.toString().trim());
//        model.put("destino_contacto_telefonos", sbTelefonos.toString().trim());
//    }
//
//    private void extraerDestinatarioNumeroIdentificacionAlterno(String texto, Map<String, Object> model) {
//        String values[] = texto.split("-");
//
//        if (values.length > 0) {
//            model.put("destinatario_numero_identificacion_alterno", values[0].trim());
//        }
//        if (values.length > 1) {
//            model.put("destinatario_digito_verificacion_alterno", values[1].trim());
//        }
//    }
//
//    private void extraerNota(String texto, Map<String, Object> model) {
//        if (!model.containsKey("notas")) {
//            model.put("notas", "");
//        }
//
//        StringBuffer sb = new StringBuffer((String) model.get("notas"));
//        if (sb.length() > 0) {
//            sb.append(".\n");
//        }
//        sb.append(texto);
//
//        model.put("notas", sb.toString());
//    }
//
//    private void extraerClienteCodigoAlterno(String texto, Map<String, Object> model) {
//        model.put("cliente_codigo_alterno", texto);
//    }
//
//    private void extraerLineas(String texto, Map<String, Object> model) {
//        if (!model.containsKey("lineas")) {
//            model.put("lineas", new ArrayList<Map<String, Object>>());
//        }
//
//        List<Map<String, Object>> lineas = (List<Map<String, Object>>) model.get("lineas");
//        Map<String, Object> linea = new HashMap<>();
//
//        extraerLinea(texto, linea);
//
//        lineas.add(linea);
//    }
//
//    private void extraerLinea(String texto, Map<String, Object> model) {
//        boolean encontradoTokenValorUnitarioDeVenta = false;
//        boolean encontradoTokenInicioDeTexto = false;
//
//        StringBuffer sb = new StringBuffer();
//
//        Scanner scanner = new Scanner(texto);
//        try {
//            while (scanner.hasNext()) {
//                String token = scanner.next();
//
//                boolean matchNumero = token.matches("(\\d{1,3}\\,)*\\d{3}");
//
//                if (!encontradoTokenInicioDeTexto) {
//                    if (matchNumero) {
//                        if (!encontradoTokenValorUnitarioDeVenta) {
//                            encontradoTokenValorUnitarioDeVenta = true;
//                            model.put("valor_unitario_venta", token);
//                        } else if (encontradoTokenValorUnitarioDeVenta && !encontradoTokenInicioDeTexto) {
//                            encontradoTokenInicioDeTexto = true;
//                        }
//                    }
//                } else {
//                    if (!matchNumero) {
//                        sb.append(token).append(" ");
//                    } else {
//                        break;
//                    }
//                }
//            }
//
//            extraerLineaDescripcion(sb.toString().trim(), dto);
//        } catch (IllegalStateException e) {
//            throw e;
//        } finally {
//            scanner.close();
//        }
//    }
}
