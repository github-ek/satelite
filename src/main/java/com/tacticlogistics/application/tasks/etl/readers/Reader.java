package com.tacticlogistics.application.tasks.etl.readers;

import java.io.IOException;

public interface Reader<T,S> {
    S read(final T input) throws IOException;
}