package com.tacticlogistics.application.tasks.etl.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class FlatFileReaderUTF16 implements Reader<File,String> {

    @Override
    public String read(File archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (
                new FileInputStream(archivo.getAbsolutePath()), "utf-16"
                )
                )) {
            StringBuilder sb = new StringBuilder();

            br.lines().forEach(line -> {
                sb.append(line);
                sb.append(System.lineSeparator());
            });

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw e;
        }
        return "";
    }
}
