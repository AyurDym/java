package service;

import exception.FileProcessException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    // Чтение файла
    public List<String> readFile(String filePath) throws FileProcessException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new FileProcessException("Файл не найден: " + filePath, e);
        } catch (IOException e) {
            throw new FileProcessException("Ошибка при чтении файла: " + filePath, e);
        }

        return lines;
    }

    // Запись файла с созданием директории
    public void writeFile(String filePath, List<String> lines) throws FileProcessException {
        try {
            // Создаем объект File для пути
            File outputFile = new File(filePath);

            // Создаем директорию, если она не существует
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (created) {
                    System.out.println("Создана директория: " + parentDir.getAbsolutePath());
                }
            }

            // Записываем файл
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            System.out.println("Файл сохранен: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            throw new FileProcessException("Ошибка при записи файла: " + filePath, e);
        }
    }

    // Проверка существования файла
    public boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    // Получение информации о файле
    public void printFileInfo(String filePath) throws FileProcessException {
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileProcessException("Файл не существует: " + filePath);
        }

        System.out.println("\n=== Информация о файле ===");
        System.out.println("Имя: " + file.getName());
        System.out.println("Путь: " + file.getAbsolutePath());
        System.out.println("Размер: " + file.length() + " байт");
        System.out.println("Директория: " + file.getParent());
    }
}
