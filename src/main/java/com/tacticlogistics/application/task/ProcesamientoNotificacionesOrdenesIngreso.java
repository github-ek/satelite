package com.tacticlogistics.application.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.tacticlogistics.application.dto.ingresos.EstadoOrdenIngresoType;
import com.tacticlogistics.application.dto.ingresos.LineaOrdenIngresoRepository;
import com.tacticlogistics.application.dto.ingresos.OrdenIngreso;
import com.tacticlogistics.application.dto.ingresos.OrdenIngresoRepository;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;

@Service
public class ProcesamientoNotificacionesOrdenesIngreso {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    OrdenIngresoRepository ordenRepository;

    @Autowired
    LineaOrdenIngresoRepository lineaOrdenRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    BodegaRepository bodegaRepository;

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public void procesar() {
        List<OrdenIngreso> list = ordenRepository.findByEstado(EstadoOrdenIngresoType.RECEPCIONADO);
        // String url = "http://satelite.tacticlogistics.com:8080/";
        String url = "http://tactic-montevideo-hnwrrjgtwc.dynamic-m.com:8585/";
        for (OrdenIngreso orden : list) {
            Usuario usuario = usuarioRepository.findByUsuarioIgnoringCase(orden.usuario);
            Bodega bodega = bodegaRepository.findByCodigoIgnoringCase(orden.bodega);
            Ciudad ciudad = ciudadRepository.findOne(bodega.getDireccion().getCiudadId());
            Cliente cliente = clienteRepository.findByCodigoIgnoringCase(orden.getCliente());

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("orden", orden);
            model.put("supervisor", usuario);
            model.put("bodega", bodega);
            model.put("bodegaCiudad", ciudad);
            model.put("lineasOrden", lineaOrdenRepository.findByIdOrden(orden.getId()));
            model.put("url_img", url + "TacticReportWeb/img/");
            model.put("url_reporte", url + "TacticReportWeb/#");

            String subject = "Tactic Logistics REF: "
                    + (orden.getNumeroDocumentoCliente() == null ? "" : orden.getNumeroDocumentoCliente())
                    + " SE HA CONFIRMADO LA RECEPCIÓN DE SU PRODUCTO EN NUESTRAS BODEGAS TACTIC";

            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "templates/velocity/email-ordenes-ingreso-confirmacion.vm", "UTF-8", model);
            System.out.println(text);
            // TODO FILTRAR POR TIPO NOTIFICACION
            cliente.getSuscriptores().forEach((a) -> {

                sendConfirmationEmail(new String[] { a.getContacto().getEmail() }, "notificaciones@tacticlogistics.com",
                        subject, text);
            });

            Date now = new Date();
            orden.setEstado(EstadoOrdenIngresoType.INTEGRADO_WMS);
            orden.setFechaNotificacion(now);
            orden.setNotificado(true);

        }
        ordenRepository.save(list);
    }

    private void sendConfirmationEmail(final String[] emailTo, final String emailFrom, final String subJect,
            final String text) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setBcc(emailTo);
                message.setFrom(emailFrom);
                message.setSubject(subJect);
                message.setText(text, true);

            }
        };
        this.mailSender.send(preparator);
    }
}
