package com.tacticlogistics.application.tasks.etl;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tacticlogistics.application.tasks.etl.components.ETLStrategyFactory;

@Configuration
public class AppConfig {

    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(ETLStrategyFactory.class);
        return factoryBean;
    }
}
