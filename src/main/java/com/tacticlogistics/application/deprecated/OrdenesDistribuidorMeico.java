package com.tacticlogistics.application.deprecated;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import com.tacticlogistics.infrastructure.services.Basic;

//@Component("ordenes.AMBEV.MEICO")
public class OrdenesDistribuidorMeico extends ETLPDFFileStrategy<Object> {

    @Override
    protected String limpiar(String texto) {
        String t = super.limpiar(texto);

        t = t.replaceAll("MEICO S.A. N.I.T. NO.", "\n@orden:")
                .replaceAll("BOGOTA CODIGO DESCRIPCION REFERENCIA UDM DESCUENTO VR COSTO CANTIDAD", "@lineas:")
                .replaceAll("FIRMA AUTORIZADA Y SELLO TOTAL :", "@fin:")
                .replaceAll("SRAMBEV COLOMBIA S\\.A\\.S\\.", " ")
                .replaceAll("ORDEN DE COMPRA NO.", "numeroOrden")
                .replaceAll("NIT \\d+ FECHA DE ORDEN .+ CONDICION PAGO", "condicionPago")
                // .replaceAll("NIT \\d+ FECHA DE ORDEN : .+ CONDICION PAGO",
                // "condicionPago")
                .replaceAll("FIN TEL\\.", "TEL.").replaceAll("_", " ").replaceAll("\\p{Blank}+", " ").trim();

        return t;
    }

    @Override
    protected Map<String, Object> transformar(String texto) {
        Map<String, Object> model = new HashMap<>();
        String paginas[] = texto.split("\n");

        int i = 0;
        for (String p : paginas) {
            Scanner scanner = new Scanner(p);
            Map<String, Object> pagina = new HashMap<>();
            model.put(String.valueOf(i), pagina);
            i++;

            try {
                parsePagina(scanner, pagina);
            } catch (IllegalStateException e) {
                throw e;
            } finally {
                scanner.close();
            }
        }

        return model;
    }

    @Override
    protected void cargar(Map<String, Object> model) {
        // TODO Auto-generated method stub
    }

    private void parsePagina(Scanner scanner, Map<String, Object> model) throws IllegalStateException {
        while (scanner.hasNext()) {
            String token = scanner.next();
            switch (token) {
            case "@orden:":
                parseOrden(scanner, model);
                break;
            case "@lineas:":
                parseLineasOrden(scanner, model);
                break;
            case "@fin:":
                parseFin(scanner, model);
                break;
            default:
                throw new IllegalStateException(token);
            }
        }
    }

    private void parseOrden(Scanner scanner, Map<String, Object> model) {
        StringBuffer sb;
        model.put("destinatarioNumeroIdentificacionAlterno", scanner.next());

        sb = new StringBuffer();
        while (scanner.hasNext()) {
            if (!scanner.hasNext("numeroOrden")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("destinoContactoDireccion", sb.toString());

        if (scanner.hasNext()) {
            scanner.next();
            model.put("numeroOrden", scanner.next());
        }

        sb = new StringBuffer();
        scanner.next();
        while (scanner.hasNext()) {
            if (!scanner.hasNext("TEL.")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("notas", sb.toString());

        sb = new StringBuffer();
        while (scanner.hasNext()) {
            if (!scanner.hasNext("@lineas:")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("destinoContactoTelefonos", sb.toString());
    }

    private void parseLineasOrden(Scanner scanner, Map<String, Object> model) {
        int i = 0;
        while (scanner.hasNext()) {
            if (!scanner.hasNext("@fin:")) {
                Map<String, Object> linea = new HashMap<>();

                model.put(String.valueOf(i), linea);
                parseLineaOrden(scanner, linea);
            } else {
                break;
            }
            i++;
        }
        model.put("numeroLineas", i);
    }

    private void parseLineaOrden(Scanner scanner, Map<String, Object> model) {
        model.put("productoCodigoAlterno", Basic.ajustarNumero(scanner.next()));

        Stack<String> stack = new Stack<>();
        String numberPattern = "((\\d{1,3}\\,)*)(\\d{1,3})((\\.\\d{1,2})?)";

        int i = 0;
        while (scanner.hasNext()) {
            String token = scanner.next();

            stack.push(token);

            if (token.matches(numberPattern)) {
                switch (i) {
                case 0:
                    i = 1;
                    break;
                case 1:
                    i = 2;
                    break;
                default:
                    i = 0;
                    break;
                }
            } else {
                i = 0;
            }

            if (i == 2) {
                break;
            }
        }
        if (i == 2) {
            model.put("cantidad", stack.pop());
            model.put("valorUnitarioDeclarado", stack.pop());
            model.put("unidadCodigoAlterno", stack.pop());
            final StringBuffer sb = new StringBuffer();
            stack.forEach(a -> {
                sb.append(a).append(" ");
            });
            model.put("descripcion", sb.toString());
        }
    }

    private void parseFin(Scanner scanner, Map<String, Object> model) {
        StringBuffer sb;

        sb = new StringBuffer();
        scanner.next();
        sb.append(model.get("notas"));
        while (scanner.hasNext()) {
            sb.append(scanner.next()).append(" ");
        }

        model.put("notas", sb.toString());
    }

}
