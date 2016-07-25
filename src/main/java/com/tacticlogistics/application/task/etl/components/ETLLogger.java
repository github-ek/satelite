package com.tacticlogistics.application.task.etl.components;

import com.tacticlogistics.domain.model.common.SeveridadType;

public interface ETLLogger<E> {
    void Log(SeveridadType severidad, E target, String property, String texto);
}
