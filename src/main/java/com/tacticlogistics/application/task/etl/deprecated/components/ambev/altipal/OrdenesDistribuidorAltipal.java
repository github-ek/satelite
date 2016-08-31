package com.tacticlogistics.application.task.etl.deprecated.components.ambev.altipal;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.tacticlogistics.application.task.etl.deprecated.components.ETLPDFFileStrategy;

//@Component("ordenes.AMBEV.ALTIPAL")
public class OrdenesDistribuidorAltipal extends ETLPDFFileStrategy<Object> {

    @Override
    protected String limpiar(final String texto) {
        String t = super.limpiar(texto);

        t = t.replaceAll("ALTIPA S.A", "\n@orden:")
                .replaceAll(
                        "ELABORADO POR : ARTICULO DESCRIPCIÓN UNIDAD CANTIDAD UNIDADES PRECIO DCTO1 DCTO2 IVA NETO DES_COM COMERCIAL DES_ESP",
                        "@lineas:")
                .replaceAll("(\\*)+ ((\\d{1,3}\\,)*)(\\d{1,3})((\\.\\d{1,3})?) NOTA :", "@fin:")
                .replaceAll("FECHA : \\d{1,2}/\\d{1,2}/\\d{4}", " ")
                .replaceAll("PROVEEDOR : DIAS INVENTARIO: \\d+", " ").replaceAll("PEDIDOS DE COMPRA DETALLE C.E.A", " ")
                .replaceAll("913 - AMBEV COLOMBIA SAS", "@usuarioConfirmacion:")
                .replaceAll("\\p{Blank}+", " ")
                .trim();

        return t;
    }

    @Override
    protected Map<String, Object> transformar(final String texto) throws IllegalStateException {
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
            case "@usuarioConfirmacion:":
                parseSolicitadoPor(scanner, model);
                break;
            default:
                throw new IllegalStateException(token);
            }
        }
    }

    private void parseOrden(Scanner scanner, Map<String, Object> model) {
        model.put("destinatarioNumeroIdentificacionAlterno", scanner.next());

        while (scanner.hasNext()) {
            if (!scanner.hasNext("PEDIDO")) {
                scanner.next();
            } else {
                break;
            }
        }

        if (scanner.hasNext("PEDIDO")) {
            scanner.next();
        }

        model.put("numeroOrden", scanner.next());

        while (scanner.hasNext()) {
            if (!scanner.hasNext("@lineas:")) {
                scanner.next();
            } else {
                break;
            }
        }
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
        model.put("productoCodigoAlterno", scanner.next());

        Stack<String> stack = new Stack<>();

        int i = 0;
        while (scanner.hasNext()) {
            String token = scanner.next();

            stack.push(token);
            if (token.matches("\\d++")) {
                i = 1;
            } else if (token.matches("((\\d{1,3}\\,)*)(\\d{1,3})((\\.\\d{1,3})?)")) {
                switch (i) {
                case 1:
                    i = 2;
                    break;
                case 2:
                    i = 3;
                    break;
                default:
                    i = 0;
                    break;
                }
                if (i == 3) {
                    break;
                }
            } else {
                i = 0;
            }
        }

        if (i == 3) {
            model.put("valorUnitarioDeclarado", stack.pop());
            stack.pop();
            model.put("cantidad", stack.pop());
            model.put("unidadCodigoAlterno", stack.pop());
        }

        StringBuffer sb = new StringBuffer();
        stack.forEach(a -> {
            sb.append(a).append(" ");
        });
        model.put("descripcion", sb.toString());

        for (int j = 0; j < 7; j++) {
            scanner.next();
        }
    }

    private void parseFin(Scanner scanner, Map<String, Object> model) {
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext()) {
            if (!scanner.hasNext("@usuarioConfirmacion:")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("notas", sb.toString());
    }

    private void parseSolicitadoPor(Scanner scanner, Map<String, Object> model) {
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNext()) {
            sb.append(scanner.next()).append(" ");
        }
        model.put("usuarioConfirmacion", sb.toString());
    }
}
