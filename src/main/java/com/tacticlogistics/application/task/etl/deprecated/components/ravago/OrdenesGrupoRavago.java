package com.tacticlogistics.application.task.etl.deprecated.components.ravago;

import java.util.HashMap;
import java.util.Map;

import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.task.etl.OrdenDtoAtributos;
import com.tacticlogistics.application.task.etl.deprecated.components.ETLPDFFileStrategy;

public class OrdenesGrupoRavago extends ETLPDFFileStrategy<ETLOrdenDto> {

    @Override
    protected String limpiar(String texto) {
        boolean encontradoNumeroDocumentoOrdenCliente = false;
        boolean encontradoDestinatarioNombre = false;
        boolean encontradoDestinoDireccion = false;
        boolean encontradoDestinoCiudadNombreAlterno = false;
        boolean encontradoDestinoContactoNombres = false;
        boolean encontradoDestinatarioNumeroIdentificacion = false;
        boolean encontradoUsuarioAprobacionCliente = false;
        boolean encontradoInicioLineas = false;
        int index = 0;

        StringBuffer sb = new StringBuffer("@orden\n");
        texto = super.limpiar(texto);
        String lineas[] = texto.split("\r\n");

        for (int i = 1; i < lineas.length; i++) {
            if (!encontradoNumeroDocumentoOrdenCliente) {
                if (lineas[i - 1].equals("REMISION")) {
                    encontradoNumeroDocumentoOrdenCliente = true;

                    String value;
                    value = lineas[i].replaceAll("\\s+", "");
                    sb.append(OrdenDtoAtributos.NUMERO_ORDEN.toString()).append("\t").append(value)
                            .append("\n");
                }
            } else {
                if (!encontradoDestinatarioNombre) {
                    encontradoDestinatarioNombre = true;

                    lineas[i] = reduce(lineas[i]);
                    sb.append(OrdenDtoAtributos.DESTINATARIO_NOMBRE.toString()).append("\t").append(lineas[i])
                            .append("\n");

                } else {
                    if (!encontradoDestinoDireccion) {
                        encontradoDestinoDireccion = true;

                        lineas[i] = reduce(lineas[i]);
                        sb.append(OrdenDtoAtributos.DESTINO_DIRECCION.toString()).append("\t").append(lineas[i])
                                .append("\n");
                    } else {
                        if (!encontradoDestinoCiudadNombreAlterno) {
                            encontradoDestinoCiudadNombreAlterno = true;

                            lineas[i] = reduce(lineas[i]);
                            sb.append(OrdenDtoAtributos.DESTINO_CIUDAD_NOMBRE_ALTERNO.toString()).append("\t")
                                    .append(lineas[i]).append("\n");

                            index = 0;
                        } else {
                            if (!encontradoDestinoContactoNombres) {
                                if (index == 2) {
                                    encontradoDestinoContactoNombres = true;

                                    sb.append(OrdenDtoAtributos.DESTINO_CONTACTO_NOMBRES.toString()).append("\t")
                                            .append(lineas[i]).append("\n");
                                }
                            } else {

                                if (!encontradoDestinatarioNumeroIdentificacion) {
                                    encontradoDestinatarioNumeroIdentificacion = true;

                                    lineas[i] = reduce(lineas[i] + " ").trim();
                                    sb.append(OrdenDtoAtributos.DESTINATARIO_NUMERO_IDENTIFICACION.toString())
                                            .append("\t").append(lineas[i]).append("\n");
                                } else {
                                    if (!encontradoUsuarioAprobacionCliente) {
                                        if (index == 5) {
                                            encontradoUsuarioAprobacionCliente = true;
                                            String usuarioAprobacionCliente = lineas[i].substring(0, lineas[i].length() - 8);
                                            String fechaSugeridaEntregaMaxima =  lineas[i].substring(lineas[i].length() - 8);
                                            
                                            sb.append(OrdenDtoAtributos.USUARIO_APROBACION_CLIENTE.toString())
                                            .append("\t").append(usuarioAprobacionCliente).append("\n");

                                            sb.append(OrdenDtoAtributos.FECHA_SUGERIDA_ENTREGA_MAXIMA.toString())
                                            .append("\t").append(fechaSugeridaEntregaMaxima).append("\n");
                                        }
                                    }else{
                                        if(!encontradoInicioLineas){
                                            encontradoInicioLineas =true;

                                            sb.append(OrdenDtoAtributos.NOTAS_APROBACION_CLIENTE.toString())
                                            .append("\t").append(lineas[i]).append("\n");
                                            
                                            index = 0;
                                        }else{
                                            if(index == 2){
                                                String values[];
                                                StringBuffer linea;
                                                
                                                linea = new StringBuffer();
                                                values = lineas[i-1].replaceAll("KBS", " ").split(" ");

                                                String cantidad = values[0];
                                                String productoCodigo =values[values.length-2];
                                                String unidadCodigoAlterno = values[values.length-1];
                                                
                                                values[0] = "";
                                                values[1] = "";
                                                values[values.length-2] = "";
                                                values[values.length-1] = "";
                                                
                                                for (String value : values) {
                                                    linea.append(value).append(" ");
                                                }

                                                String tolva = linea.toString().trim();

                                                linea = new StringBuffer();
                                                values = lineas[i].split(" ");

                                                String lote = values[0];
                                                values[0] = "";

                                                for (String value : values) {
                                                    linea.append(value).append(" ");
                                                }

                                                String notas = linea.toString().trim();

                                                sb.append(OrdenDtoAtributos.LINEAS.toString()).append("\t");
                                                sb.append(productoCodigo).append("\t");
                                                sb.append(cantidad).append("\t");
                                                sb.append(unidadCodigoAlterno).append("\t");
                                                sb.append(lote).append("\t");
                                                sb.append(tolva).append("\t");
                                                sb.append(notas).append("\t");
                                                sb.append("\n");

                                                index = 0;
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

        System.out.println(sb.toString());
        System.out.println();
        return sb.toString();
    }

    protected String reduce(String linea) {
        if ((linea.length() % 2) == 0) {
            String value1, value2;
            value1 = linea.substring(0, linea.length() / 2);
            value2 = linea.substring(linea.length() / 2);

            if (value1.equals(value2)) {
                return value1;
            }
        }
        return linea;
    }

    @Override
    protected Map<String, ETLOrdenDto> transformar(String texto) {
        // TODO Auto-generated method stub
        return new HashMap<>();
    }

    @Override
    protected void cargar(Map<String, ETLOrdenDto> model) {
        // TODO Auto-generated method stub

    }

}
