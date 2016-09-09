package com.tacticlogistics.application.deprecated;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

//@Component("ordenes.ALTIPAL")
public class OrdenesAltipal extends ETLPDFFileStrategy<Object> {

    @Override
    protected String limpiar(final String texto) {
        StringBuffer sb = new StringBuffer();
        
        String t = super.limpiar(texto);
        String paginas[] = t.split("ALTIPAL S\\.A\\. NIT: 800\\.186\\.960-6");
        
        for (String pagina : paginas) {
            if (!"".equals(pagina)) {
                pagina = pagina
                        .replaceAll("OFICINAS PRINCIPALES EN COLOMBIA.*BOGOTA, D\\.C\\. 110931", "\n@orden:")
                        .replaceAll("CODIGO DESCRIPCION MEDIDA CANTIDAD PRECIO DTO1 DTO2 DCOM SUBTOTAL IVA NETO P.UNIT. NETOEAN CELULAR: DPP","\n@lineas:")
                        .replaceAll("FACTURA DE VENTA NO\\.", " ")
                        .replaceAll("TELEFONO : CODIGO : CLIENTE : ESTABLECIM. DE COMERCIO : DIRECCION : CIUDAD : NIT : ZONA : BARRIO : VENDEDOR : O.COMPRA : FECHA EXPEDICION : CONDICIONES : COMENTARIOS : PEDIDO : PLAZO : FECHA PEDIDO : VENCIMIENTO : "," ")
                        .replaceAll("\\+ I\\.V\\.A\\. .* \\+ I\\.V\\.A\\.", " ")
                        .replaceAll("(\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})[AP]\\.M\\. TOTAL UNIDADES: \\d+"," ")
                        .replaceAll("[A-Z]{3}/\\d{1,2}/\\d{1,4}", " ")
                        .replaceAll("NO. OBLIGACION BODEGA", " ")
                        .replaceAll("\\p{Blank}+", " ")
                        .trim();

                sb.append(pagina);
            }
        }

        return sb.toString();
    }
    
    @Override
    protected Map<String, Object> transformar(String texto) throws IllegalStateException{
        Map<String, Object> model = new HashMap<>();
        String paginas[] = texto.split("\n");

        int i = 0;
        for (String p : paginas) {
            Scanner scanner = new Scanner(p);
            Map<String, Object> pagina = new HashMap<>();
            model.put(String.valueOf(i), pagina);
            i++;

            try{
                parsePagina(scanner, pagina);    
            }catch(IllegalStateException e){
                throw e;
            }finally{
                scanner.close();    
            }
        }

        return model;
    }

    protected void cargar(Map<String, Object> model) {
        for (Iterator<Map.Entry<String, Object>> it = model.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = it.next();

            @SuppressWarnings("unchecked")
            Map<String, Object> pagina = (Map<String, Object>) entry.getValue();
            if ("CLIENTE".equals(pagina.get("fin"))) {
                it.remove();
            }
            //TODO MERGE de lineas
        }
    }

    private void parsePagina(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        while (scanner.hasNext()) {
            String token = scanner.next();
            switch (token) {
            case "@orden:":
                parseNumeroOrden(scanner, model);
                parseDestinoDireccion(scanner, model);
                parseDestinoCodigoAlterno(scanner, model);
                parseDestinatarioNumeroIdentificacionAlterno(scanner, model);
                parseUsuarioConfirmacion(scanner, model);
                parseNotas(scanner, model);
                break;
            case "@lineas:":
                int i = 0;
                while (scanner.hasNext()) {
                    if (!scanner.hasNext("(ORIGINAL|CLIENTE)")) {
                        Map<String, Object> linea = new HashMap<>();

                        model.put(String.valueOf(i), linea);
                        parseLineaOrden(scanner, linea);
                    } else {
                        break;
                    }
                    i++;
                }
                model.put("numeroLineas", i);
                break;
            case "ORIGINAL":
            case "CLIENTE":
                model.put("fin", token);
                scanner.next();
                scanner.next();
                scanner.next();
                scanner.next();
                break;
            default:
                throw new IllegalStateException(token);
            }
        }
    }

    private void parseNumeroOrden(Scanner scanner, Map<String, Object> model)
            throws IllegalStateException {
        StringBuffer sb = new StringBuffer();
        sb.append(scanner.next());
        sb.append(scanner.next());
        sb.append(scanner.next());
        model.put("numeroOrden", sb.toString());
    }

    private void parseDestinoDireccion(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        boolean foundDIAS = false;
        boolean foundDIGIT = false;

        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext() && !foundDIGIT) {

            if (!foundDIAS && scanner.hasNext("DIAS\\.")) {
                foundDIAS = true;
            } else if (foundDIAS && scanner.hasNextInt()) {
                foundDIGIT = true;
                break;
            }

            sb.append(scanner.next()).append(" ");
        }

        model.put("destinoDireccion", sb.toString());

        if (!foundDIGIT) {
            throw new IllegalStateException("Se esperaba un DIGITO d{5}");
        }
    }

    private void parseDestinoCodigoAlterno(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        model.put("destinoCodigoAlterno", scanner.next());
    }

    private void parseDestinatarioNumeroIdentificacionAlterno(Scanner scanner, Map<String, Object> model)
            throws IllegalStateException {

        model.put("destinatarioNumeroIdentificacionAlterno", scanner.next());
    }

    private void parseUsuarioConfirmacion(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        boolean foundDIGIT = false;

        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext() && !foundDIGIT) {

            if (scanner.hasNextInt()) {
                foundDIGIT = true;
                break;
            }

            sb.append(scanner.next()).append(" ");
        }

        model.put("usuarioConfirmacion", sb.toString());

        if (!foundDIGIT) {
            throw new IllegalStateException("Se esperaba un DIGITO d{5}");
        }
    }

    private void parseNotas(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext()) {
            if (scanner.hasNext("@lineas:")) {
                break;
            }

            sb.append(scanner.next()).append(" ");
        }

        model.put("notas", sb.toString());
    }

    private void parseLineaOrden(Scanner scanner, Map<String, Object> model) throws IllegalStateException {

        while (scanner.hasNext()) {
            if (scanner.hasNext(":")) {
                break;
            }
            scanner.next();
        }
        scanner.next();

        model.put("productoCodigoAlterno", scanner.next());

        String[] parts;

        String token;

        token = scanner.next();
        parts = token.split("\\p{L}");
        if (parts.length == 0) {
            throw new IllegalStateException("Se esperaba un digito");
        }
        model.put("cantidad", parts[0]);
        model.put("unidadCodigoAlterno", token.replaceAll(parts[0], ""));

        for (int i = 0; i < 5; i++) {
            System.out.println(scanner.next());
        }

        token = scanner.next();

        String numeroPattern = "(((\\d{1,3}\\,)*)(\\d{1,3}))";
        String sinNumero = token.replaceFirst(numeroPattern, "");
        String numero = token.replaceFirst(sinNumero, "");

        if (!numero.matches(numeroPattern)) {
            throw new IllegalStateException("Se esperaba un digito");
        }
        model.put("valorUnitarioDeclarado", numero);

        StringBuffer sb;
        sb = new StringBuffer(sinNumero);
        sb.append(" ");

        while (scanner.hasNext()) {
            if (!scanner.hasNext(numeroPattern)) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("descripcion", sb.toString());

        for (int i = 0; i < 8; i++) {
            scanner.next();
        }
    }
}
