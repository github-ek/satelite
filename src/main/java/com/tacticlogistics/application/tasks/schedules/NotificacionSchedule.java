package com.tacticlogistics.application.tasks.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.ETLService;
import com.tacticlogistics.application.tasks.etl.ETLApplicationService;

@Component
public class NotificacionSchedule {
    @Autowired
    private ETLApplicationService ordenesETLApplicationService;

    @Autowired
    private ETLService etlService;

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void cron() {
        try{
            ordenesETLApplicationService.run();
        	//etlService.run();
        }catch(RuntimeException e){
            
        }
    }
}
