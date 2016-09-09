package com.tacticlogistics.application.deprecated;

import java.io.File;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.tacticlogistics.application.tasks.etl.components.ETLFileStrategy;
import com.tacticlogistics.application.tasks.etl.readers.PDFFileReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;

public abstract class ETLPDFFileStrategy<E> extends ETLFileStrategy<E> {
    
    @Autowired
    private PDFFileReader reader;

    // --------------------------------------------------------------------------------------------
    @Override
    public Pattern getPattern() {
        return PATTERN_PDF;
    }

    @Override
    protected Reader<File,String> getReader() {
        return reader;
    }
    
    
}