package com.tacticlogistics.application.tasks.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.tasks.etl.ETLApplicationService;

@Component
public class NotificacionSchedule {
    @Autowired
    private ETLApplicationService ordenesETLApplicationService;

    @Scheduled(fixedDelay = 1000 * 60 * 1)
    public void cron() {
        try{
            ordenesETLApplicationService.run();
        }catch(RuntimeException e){
            
        }
    }
}
