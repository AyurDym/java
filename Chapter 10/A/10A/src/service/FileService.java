package service;

import exception.TextProcessingException;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class FileService {

    public List<String> readFile(String filePath) throws TextProcessingException {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new TextProcessingException("Ошибка чтения файла: " + filePath, e);
        }
    }

    public void writeFile(String filePath, List<String> lines) throws TextProcessingException {
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            throw new TextProcessingException("Ошибка записи файла: " + filePath, e);
        }
    }

    public String readFileAsString(String filePath) throws TextProcessingException {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new TextProcessingException("Ошибка чтения файла: " + filePath, e);
        }
    }
}