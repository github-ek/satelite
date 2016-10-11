package com.tacticlogistics.application.tasks.schedules.oms;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.services.clientes.dicermex.OrdenesDeCompraService;
import com.tacticlogistics.application.services.clientes.dicermex.ValidadorOrdenesDeCompraService;
import com.tacticlogistics.application.tasks.etl.ETLApplicationService;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.Regla;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.almacenamiento.ReglaBodegaDestino;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.almacenamiento.ReglaBodegaOrigen;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.almacenamiento.ReglaCatidadesPlanificadas;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.almacenamiento.ReglaLineas;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.almacenamiento.ReglaProductos;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.destinatario.ReglaTercero;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.distribucion.ReglaCitaEntrega;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.distribucion.ReglaCitaRecogida;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.distribucion.ReglaPuntoEntrega;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.distribucion.ReglaPuntoRecogida;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.estados.ReglaTransicionConfirmadaAceptada;
import com.tacticlogistics.domain.model.ordenes.Orden;

import lombok.val;

@Component
public class ValidadorDeOrdenesDeCompraSchedule {
    @Autowired
    private ValidadorOrdenesDeCompraService service;

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void cron() {
    	MensajesDto mensajes = new MensajesDto();
        try{
        	val reglas = getReglas();
        	service.validarOrdenesConfirmadas(reglas);
        }catch(RuntimeException e){
            
        }
    }

	private Set<Regla<Orden>> getReglas() {
		Set<Regla<Orden>> reglas = new HashSet<>();
		
		reglas.add(new ReglaTransicionConfirmadaAceptada());
		reglas.add(new ReglaTercero());
		
		reglas.add(new ReglaCitaRecogida());
		reglas.add(new ReglaPuntoRecogida());

		reglas.add(new ReglaCitaEntrega());
		reglas.add(new ReglaPuntoEntrega());

		//TODO REGLA RECAUDO DEBE SER NULL PARA OC
		//TODO REGLA TIEMPO MINIMO/MAXIMO PARA PRESTAR EL SERVICIO
		//TODO REGLAS TIPO DE VEHICULO Y VALOR FLETE

		reglas.add(new ReglaLineas());
		reglas.add(new ReglaCatidadesPlanificadas());
		reglas.add(new ReglaProductos());
		
		reglas.add(new ReglaBodegaOrigen());
		reglas.add(new ReglaBodegaDestino());
		
		return reglas;
	}
}
