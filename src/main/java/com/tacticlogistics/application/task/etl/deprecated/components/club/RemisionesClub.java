package com.tacticlogistics.application.task.etl.deprecated.components.club;

import java.util.HashMap;
import java.util.Map;

import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.task.etl.OrdenDtoAtributos;
import com.tacticlogistics.application.task.etl.deprecated.components.ETLPDFFileStrategy;

//@Component("ordenes.CLUB.REMISIONES")
public class RemisionesClub extends ETLPDFFileStrategy<ETLOrdenDto> {

    @Override
    protected String limpiar(String texto) {
        boolean encontradoBodegaOrigen = false;
        boolean encontradoDestinoContactoTelefono = false;
        boolean encontradoBodegaDestino = false;
        boolean encontradoInicioLineas = false;
        boolean encontradoFinLineas = false;
        boolean encontradoObservaciones = false;

        StringBuffer sb = new StringBuffer("@orden\n");
        texto = super.limpiar(texto);
        String lineas[] = texto.split("\r\n");

        for (int i = 0; i < lineas.length; i++) {

            lineas[i] = lineas[i].replaceAll("\\s+", " ");

            if (!encontradoBodegaOrigen) {
                encontradoBodegaOrigen = true;

                sb.append(OrdenDtoAtributos.LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO.toString()).append("\t")
                        .append(lineas[i]).append("\n");
            } else {
                if (!encontradoDestinoContactoTelefono) {
                    if (lineas[i].startsWith("TELEFONO :")) {
                        encontradoDestinoContactoTelefono = true;
                        String values[] = lineas[i].split(" : ");

                        for (int j = 0; j < values.length; j++) {
                            switch (j) {
                            case 1:
                                values[j] = values[j].replaceAll("ELABORADO POR", "").trim();
                                
                                sb.append(OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS.toString()).append("\t")
                                        .append(values[j]).append("\n");
                                break;
                            case 2:
                                values[j] = values[j].trim();
                                
                                sb.append("USUARIO_CONFIRMACION_CLIENTE").append("\t")
                                        .append(values[j].trim()).append("\n");
                                break;
                            default:
                                break;
                            }
                        }
                    }
                } else {
                    if (!encontradoBodegaDestino) {
                        if (lineas[i].startsWith("BODEGA DESTINO")) {
                            encontradoBodegaDestino = true;

                            String value;
                            
                            value = lineas[i - 1].replaceAll("\\s+", "");
                            sb.append(OrdenDtoAtributos.NUMERO_ORDEN.toString()).append("\t")
                                    .append(value).append("\n");

                            value = lineas[i].replaceAll("BODEGA DESTINO :", "").trim();
                            sb.append(OrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO_ALTERNO.toString()).append("\t")
                                    .append(value).append("\n");
                        }
                    } else {

                        if (!encontradoInicioLineas) {
                            if (lineas[i].startsWith("ORDEN COMPRA")) {
                                encontradoInicioLineas = true;

                                String value;
                                value = lineas[i - 1].substring(0, lineas[i-1].length() - "ALMACEN :".length());
                                sb.append(OrdenDtoAtributos.DESTINO_DIRECCION.toString()).append("\t")
                                        .append(value).append("\n");

                                value = lineas[i].replaceAll("ORDEN COMPRA :", "").trim();
                                sb.append(OrdenDtoAtributos.TIPO_SERVICIO_CODIGO_ALTERNO.toString()).append("\t")
                                        .append(value).append("\n");
                            }
                        } else {
                            if (!encontradoFinLineas) {
                                if (lineas[i].startsWith("RECIBIOENTREGO")) {
                                    encontradoFinLineas = true;
                                } else {
                                    sb.append("LINEAS").append("\t").append(lineas[i])
                                            .append("\n");
                                }
                            }else{
                                if (!encontradoObservaciones) {
                                    if(lineas[i].startsWith("OBSERVACIONES :")){
                                        encontradoObservaciones= true;
                                        
                                        String value = lineas[i].replaceAll("OBSERVACIONES :", "").trim();
                                        sb.append(OrdenDtoAtributos.NOTAS.toString()).append("\t").append(value).append("\n");
                                        
                                        break;
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }

        System.out.println(sb.toString());
        System.out.println();
        return sb.toString();
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
