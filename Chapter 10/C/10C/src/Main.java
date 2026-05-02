import exception.FileProcessException;
import service.FileService;
import service.TextConversionService;
import java.io.File;
import java.util.Scanner;

public class Main {

    private static final String DEFAULT_OUTPUT_DIR = "converted_results";
    private static final String DEFAULT_INPUT_FILE = "sample.java";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextConversionService conversionService = new TextConversionService();
        FileService fileService = new FileService();

        System.out.println("=== ПРЕОБРАЗОВАНИЕ JAVA ФАЙЛА ===");
        System.out.println("Правила: В каждом слове длиннее 2 символов");
        System.out.println("все строчные символы заменяются на прописные.\n");

        try {
            // Создаем пример Java файла для тестирования
            createSampleJavaFile();

            // Выбор режима работы
            System.out.println("Выберите режим:");
            System.out.println("1. Обработать файл по умолчанию (sample.java)");
            System.out.println("2. Указать свой файл");
            System.out.print("Ваш выбор: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            String inputFilePath;

            if (choice == 2) {
                System.out.print("Введите путь к Java файлу: ");
                inputFilePath = scanner.nextLine();
            } else {
                inputFilePath = DEFAULT_INPUT_FILE;
            }

            // Проверяем существование файла
            if (!fileService.fileExists(inputFilePath)) {
                System.err.println("Ошибка: Файл не найден - " + inputFilePath);
                System.err.println("Будет использован пример файла.");
                inputFilePath = DEFAULT_INPUT_FILE;
            }

            // Обрабатываем файл
            conversionService.processJavaFile(inputFilePath, DEFAULT_OUTPUT_DIR);

        } catch (FileProcessException e) {
            System.err.println("Ошибка при обработке: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    // Создание примера Java файла для тестирования
    private static void createSampleJavaFile() {
        String samplePath = DEFAULT_INPUT_FILE;
        File sampleFile = new File(samplePath);

        if (sampleFile.exists()) {
            return;
        }

        System.out.println("Создание примера Java файла: " + samplePath);

        String[] sampleContent = {
                "public class HelloWorld {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Hello, World!\");",
                "        int number = 42;",
                "        String message = \"This is a test message\";",
                "        for (int i = 0; i < 10; i++) {",
                "            System.out.println(\"Counter: \" + i);",
                "        }",
                "    }",
                "}"
        };

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(sampleFile))) {
            for (String line : sampleContent) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Пример файла создан: " + sampleFile.getAbsolutePath());
        } catch (java.io.IOException e) {
            System.err.println("Не удалось создать пример файла: " + e.getMessage());
        }
    }
}