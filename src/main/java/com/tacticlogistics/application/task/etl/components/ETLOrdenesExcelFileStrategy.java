package com.tacticlogistics.application.task.etl.components;

import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.infrastructure.services.Basic;

public abstract class ETLOrdenesExcelFileStrategy extends ETLFlatFileStrategy<ETLOrdenDto> {

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String limpiar(String texto) {
        return Basic.limpiarCaracterEspecialDeEspacioDeExcel(super.limpiar(texto));
    }
}