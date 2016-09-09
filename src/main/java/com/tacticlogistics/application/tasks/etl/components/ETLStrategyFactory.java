package com.tacticlogistics.application.tasks.etl.components;

public interface ETLStrategyFactory {
    ETLStrategy<?> getETLStrategy(String componenteId);
}
