package com.tacticlogistics.application.deprecated;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import com.tacticlogistics.infrastructure.services.Basic;

//@Component("ordenes.AMBEV.DISLICORES")
public class OrdenesDistribuidorDislicores extends ETLPDFFileStrategy<Object> {

    @Override
    protected String limpiar(String texto) {
        String t = super.limpiar(texto);

        t = t.replaceAll("FECHA: NÚMERO: NIT ORDEN DE COMPRA PROVEEDOR", "\n@orden:")
                .replaceAll("REFERENCIA BOD. U.M. CANTIDAD PREC. UNI / ICO IVA % DESCUENTOS VALOR TOTALDESCRIPCION DEL PRODUCTO / COD.BARRAS LOC. RECEPCION","@lineas:")
                .replaceAll("TOTAL BRUTO .+ TOTAL IVA TOTAL ICO", "@fin:").replaceAll("PAG\\. \\d+/\\d+", " ")
                .replaceAll("\\*\\*REIMPRESION\\*\\* \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}", " ")
                .replaceAll("DIRECCIÓN: TELÉFONO:", "TELÉFONO:")
                .replaceAll("DISTRIBUIDORA DE VINOS Y LICORES S.A.", " ")
                .replaceAll("DIRECCIÓN: PROVEEDOR: CONTACTO: CIUDAD: TELÉFONO: FAX:", " ")
                .replaceAll("COMPRADOR: FORMA DE PAGO: MONEDA:", "notas:")
                .replaceAll("900818921-5 AMBEV COLOMBIA SAS AMBEV COLOMBIA SAS CL 90 12 28 SANTAFE DE BOGOTA D.C. 6582265"," ")
                .replaceAll("\\p{Blank}+", " ")
                .trim();

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
        String token;

        model.put("numeroOrden", scanner.next());
        model.put("destinatarioNumeroIdentificacionAlterno", scanner.next().substring(11));
        scanner.next();

        StringBuffer sb = new StringBuffer();

        String destinoContactoTelefonos = "";
        sb = new StringBuffer();

        token = scanner.next();
        if (token.matches("\\d+")) {
            destinoContactoTelefonos = token;
        } else {
            sb.append(token).append(" ");
        }

        while (scanner.hasNext()) {
            if (!scanner.hasNext("FAX:")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        
        model.put("destinoContactoDireccion", sb.toString());

        sb = new StringBuffer();
        sb.append(destinoContactoTelefonos).append(" ");
        while (scanner.hasNext()) {
            if (!scanner.hasNext("notas:")) {
                sb.append(scanner.next()).append(" ");
            } else {
                destinoContactoTelefonos = sb.toString().trim();
                if (destinoContactoTelefonos.endsWith("FAX:")) {
                    destinoContactoTelefonos = destinoContactoTelefonos.replaceAll("FAX:", "").trim();
                }
                scanner.next();
                break;
            }
        }
        model.put("destinoContactoTelefonos", destinoContactoTelefonos);

        sb = new StringBuffer();
        while (scanner.hasNext()) {
            if (!scanner.hasNext("@lineas:")) {
                sb.append(scanner.next()).append(" ");
            } else {
                break;
            }
        }
        model.put("notas", sb.toString());
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
        model.put("cantidad", Basic.ajustarNumero(scanner.next()));
        scanner.next();

        Stack<String> stack = new Stack<>();
        String moneyPattern = "((\\d{1,3}\\,)*)(\\d{1,3})((\\.\\d{1,4})?)";

        int i = 0;
        while (scanner.hasNext()) {
            String token = scanner.next();

            stack.push(token);
            token = Basic.ajustarNumero(token);

            if (token.matches("\\d+")) {
                switch (i) {
                case 3:
                    i = 4;
                    break;
                case 4:
                    i = 5;
                    break;
                default:
                    i = 0;
                    break;
                }
                if (i == 5) {
                    model.put("unidadCodigoAlterno", stack.pop());
                    model.put("productoCodigoAlterno", stack.pop());
                    stack.pop();
                    stack.pop();
                    model.put("valorUnitarioDeclarado", Basic.ajustarNumero(stack.pop()));
                    break;
                }
            } else if (token.matches(moneyPattern)) {
                switch (i) {
                case 0:
                    i = 1;
                    break;
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
            } else {
                i = 0;
            }
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

}
