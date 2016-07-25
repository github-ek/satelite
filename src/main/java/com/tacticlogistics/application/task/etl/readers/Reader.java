package com.tacticlogistics.application.task.etl.readers;

import java.io.IOException;

public interface Reader<E,V> {
    V read(final E input) throws IOException;
}
