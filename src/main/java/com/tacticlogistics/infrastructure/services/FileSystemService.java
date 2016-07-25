package com.tacticlogistics.infrastructure.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileSystemService {
    public static List<File> listDirectories(final String folderName) {
        return listDirectories(new File(folderName));
    }

    public static List<File> listDirectories(final File folder) {
        List<File> list = new ArrayList<>(0);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                list.add(fileEntry);
            }
        }

        return list;
    }

    public static List<File> listFiles(final File folder, final String extension) {
        List<File> list = new ArrayList<File>(0);

        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                if (fileEntry.getName().toUpperCase().endsWith(extension.toUpperCase())) {
                    list.add(fileEntry);
                }
            }
        }

        return list;
    }
    
    public static void move(final File archivo, final Path destino) throws IOException {
        File directorioDestino = destino.toFile();
        if (!directorioDestino.exists()) {
            directorioDestino.mkdirs();
        }

        if (directorioDestino.exists()) {
            Files.move(archivo.toPath(), destino.resolve(archivo.toPath().getFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }


    public static String read(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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

    public static String[] readLines(String fileName) throws IOException {
        String texto = read(fileName);
        
        return texto.split(System.lineSeparator());
    }

}
