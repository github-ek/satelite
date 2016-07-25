package com.tacticlogistics.application.task.etl.components;

import java.nio.file.Path;

public interface ETLStrategy<E> {
    public void run(final Path entrada, final Path salida, final Path procesados, final Path errores);

    public void logInfo(Object key, String property, String text);

    public void logWarning(Object key, String property, String text);

    public void logError(Object key, String property, String text);

    public void logFatal(Object key, String property, String text);
}
