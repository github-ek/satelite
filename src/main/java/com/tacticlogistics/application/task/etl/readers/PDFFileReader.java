package com.tacticlogistics.application.task.etl.readers;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class PDFFileReader implements Reader<File,String> {

    @Override
    public String read(File archivo) throws IOException {
        String texto = null;

        PDDocument documento = PDDocument.load(archivo);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        texto = pdfStripper.getText(documento);

        documento.close();

        return texto;
    }

}
