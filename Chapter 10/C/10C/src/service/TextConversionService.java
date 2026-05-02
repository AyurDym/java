package service;

import model.WordProcessor;
import exception.FileProcessException;
import java.io.File;
import java.util.List;

public class TextConversionService {

    private FileService fileService;

    public TextConversionService() {
        this.fileService = new FileService();
    }

    public void processJavaFile(String inputPath, String outputDir) throws FileProcessException {
        System.out.println("\n=== НАЧАЛО ОБРАБОТКИ ===");
        System.out.println("Входной файл: " + inputPath);
        System.out.println("Выходная директория: " + outputDir);

        // Проверяем входной файл
        if (!fileService.fileExists(inputPath)) {
            throw new FileProcessException("Входной файл не существует: " + inputPath);
        }

        // Читаем исходный файл
        List<String> originalLines = fileService.readFile(inputPath);
        System.out.println("Прочитано строк: " + originalLines.size());

        // Обрабатываем текст
        List<String> processedLines = WordProcessor.processText(originalLines);

        // Создаем имя выходного файла
        File inputFile = new File(inputPath);
        String baseName = inputFile.getName();
        String outputFileName = "converted_" + baseName;
        String outputPath = outputDir + File.separator + outputFileName;

        // Сохраняем результат
        fileService.writeFile(outputPath, processedLines);

        // Выводим информацию
        System.out.println("\n=== РЕЗУЛЬТАТ ОБРАБОТКИ ===");
        System.out.println("Преобразовано строк: " + processedLines.size());

        System.out.println("\n=== ОБРАБОТКА ЗАВЕРШЕНА ===");
    }
}
