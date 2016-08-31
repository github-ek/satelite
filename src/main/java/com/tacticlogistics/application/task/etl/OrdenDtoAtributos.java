package com.tacticlogistics.application.task.etl;

public enum OrdenDtoAtributos {
    ID_CARGA    ("ID_CARGA"),
    TIPO_SERVICIO_CODIGO_ALTERNO ("TIPO_SERVICIO_CODIGO_ALTERNO"),
    
    CLIENTE_CODIGO ("CLIENTE"),
    NUMERO_CONSOLIDADO ("NUMERO_CONSOLIDADO"),
    NUMERO_ORDEN ("NUMERO_ORDEN"),

    DESTINATARIO_CANAL_CODIGO_ALTERNO ("DESTINATARIO_CANAL"),
    DESTINATARIO_IDENTIFICACION ("DESTINATARIO_IDENTIFICACION"),
    DESTINATARIO_NOMBRE ("DESTINATARIO_NOMBRE"),

    DESTINO_NOMBRE ("DESTINO_NOMBRE"),
    DESTINO_CONTACTO_EMAIL ("DESTINO_EMAIL"),
    DESTINO_CONTACTO_NOMBRES ("DESTINO_CONTACTO"),
    DESTINO_CONTACTO_TELEFONOS ("DESTINO_TELEFONOS"),

    DESTINO_CIUDAD_CODIGO ("DESTINO_CIUDAD_CODIGO"),
    DESTINO_CIUDAD_CODIGO_ALTERNO ("DESTINO_CIUDAD_CODIGO_ALTERNO"),
    DESTINO_CIUDAD_NOMBRE_ALTERNO   ("DESTINO_CIUDAD"),
    DESTINO_DIRECCION ("DESTINO_DIRECCION"),
    
    ORIGEN_CIUDAD_CODIGO ("ORIGEN_CIUDAD_CODIGO"),
    ORIGEN_CIUDAD_CODIGO_ALTERNO ("ORIGEN_CIUDAD_CODIGO_ALTERNO"),
    ORIGEN_CIUDAD_NOMBRE_ALTERNO   ("ORIGEN_CIUDAD"),
    ORIGEN_DIRECCION   ("ORIGEN_DIRECCION"),

    CLIENTE_RECOGE ("CLIENTE_RECOGE"),
    CONFIRMAR_CITA ("CONFIRMAR_CITA"),
    
    FECHA_ENTREGA_MINIMA   ("FECHA_SUGERIDA_ENTREGA_MINIMA"),
    FECHA_ENTREGA_MAXIMA   ("FECHA_SUGERIDA_ENTREGA_MAXIMA"),
    HORA_ENTREGA_MINIMA ("HORA_SUGERIDA_ENTREGA_MINIMA"),
    HORA_ENTREGA_MAXIMA ("HORA_SUGERIDA_ENTREGA_MAXIMA"),

    VALOR_RECAUDO  ("VALOR_RECAUDO"),
    NOTAS ("NOTAS"),

    LINEA_NUMERO_ITEM   ("LINEA_NUMERO_ITEM"),
    LINEA_PRODUCTO_DESCRIPCION   ("LINEA_PRODUCTO_DESCRIPCION"),
    LINEA_PRODUCTO_CODIGO   ("LINEA_PRODUCTO_CODIGO"),
    LINEA_CANTIDAD_SOLICITADA   ("LINEA_CANTIDAD_SOLICITADA"),

    LINEA_BODEGA_ORIGEN_CODIGO  ("LINEA_BODEGA_ORIGEN_CODIGO"),
    LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO  ("LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO"),
    LINEA_ESTADO_ORIGEN ("LINEA_ESTADO"),

    LINEA_BODEGA_DESTINO_CODIGO ("BODEGA_DESTINO_CODIGO"),
    LINEA_BODEGA_DESTINO_CODIGO_ALTERNO ("LINEA_BODEGA_DESTINO_CODIGO_ALTERNO"),
    LINEA_ESTADO_DESTINO ("LINEA_ESTADO"),
    
    LINEA_LOTE  ("LINEA_LOTE"),
    LINEA_ESTAMPILLADO ("LINEA_ESTAMPILLADO"),
    LINEA_SALUD ("LINEA_SALUD"),
    LINEA_IMPORTE ("LINEA_IMPORTE"),
    LINEA_DISTRIBUIDO ("LINEA_DISTRIBUIDO"),
    LINEA_NUTRICIONAL ("LINEA_NUTRICIONAL"),
    LINEA_ORIGEN ("LINEA_ORIGEN"),
    LINEA_SERIAL ("LINEA_SERIAL"),
    LINEA_BL ("LINEA_BL"),
    LINEA_FONDOCUENTA ("LINEA_FONDOCUENTA"),

    LINEA_VALOR_DECLARADO_POR_UNIDAD    ("LINEA_VALOR_DECLARADO_POR_UNIDAD"),

    LINEA_PREDISTRIBUCION_DESTINO_FINAL ("LINEA_PREDISTRIBUCION_DESTINO_FINAL"),
    LINEA_PREDISTRIBUCION_ROTULO ("LINEA_PREDISTRIBUCION_ROTULO"),
    
    PREFIJO_NUMERO_ORDEN ("PREFIJO_NUMERO_DOCUMENTO_ORDEN_CLIENTE"),
    REQUIERE_RECAUDO  ("REQUIERE_RECAUDO"),
    REQUERIMIENTOS_DISTRIBUCION ("REQUERIMIENTOS_DISTRIBUCION"),
    REQUERIMIENTOS_ALISTAMIENTO ("REQUERIMIENTOS_ALISTAMIENTO"),

    ;

    
    private final String text;

    private OrdenDtoAtributos(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
