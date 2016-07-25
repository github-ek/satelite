package com.tacticlogistics.application.task.etl.components;

public interface ETLStrategyFactory {
    ETLStrategy<?> getETLStrategy(String componenteId);
}
