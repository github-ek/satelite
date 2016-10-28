package com.tacticlogistics.application.tasks.schedules.oms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dicermex.services.compras.planificacion.ValidadorOrdenesDeCompraService;

@Component
public class ValidadorDeOrdenesDeCompraSchedule {
    @Autowired
    private ValidadorOrdenesDeCompraService service;

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void cron() {
        try{
        	//service.validarOrdenesConfirmadas();
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }


}
