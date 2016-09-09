package com.tacticlogistics.application.tasks.etl.readers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.tacticlogistics.infrastructure.services.FileSystemService;

@Component
public class FlatFileReader implements Reader<File,String> {

    @Override
    public String read(File archivo) throws IOException {
        
        String texto = FileSystemService.read(archivo.getAbsolutePath());

        return texto;
    }
}
