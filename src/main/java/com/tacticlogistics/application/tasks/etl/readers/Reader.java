package com.tacticlogistics.application.tasks.etl.readers;

import java.io.IOException;

public interface Reader<E,V> {
    V read(final E input) throws IOException;
}
