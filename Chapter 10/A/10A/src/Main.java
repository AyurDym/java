import exception.TextProcessingException;
import service.FileService;
import service.TextProcessorService;
import service.AnalysisService;
import model.FrequencyInfo;

import java.util.*;

public class Main {

    private static final FileService fileService = new FileService();
    private static final TextProcessorService processor = new TextProcessorService();
    private static final AnalysisService analyzer = new AnalysisService();

    public static void main(String[] args) {
        try {
            // Создание тестовых файлов
            createTestFiles();

            System.out.println("========== ОБРАБОТКА ТЕКСТА ==========\n");

            // Задание 1: Удаление подстроки
            task1RemoveSubstring("test_input.txt", "output_task1.txt", "тестовая");

            // Задание 2: Замена подстроки
            task2ReplaceSubstring("test_input.txt", "output_task2.txt", "строка", "предложение");

            // Задание 3: Поиск слов на гласную
            task3FindVowelWords("test_input.txt", "output_task3.txt");

            // Задание 4: Поиск цепочек слов
            task4FindWordChains("test_input.txt", "output_task4.txt");

            // Задание 5: Поиск максимальной последовательности цифр
            task5FindMaxDigits("test_input.txt", "output_task5.txt");

            // Задание 6: Подсчет частоты слов
            task6CountWordFrequency("poem_input.txt", "output_task6.txt",
                    Arrays.asList("звезды", "небе", "сердце", "песня", "жизнь"));

            // Задание 7: Замена первой буквы на прописную
            task7CapitalizeFirstLetter("poem_input.txt", "output_task7.txt");

            // Задание 8: Анализ частоты букв и слов
            task8AnalyzeFrequency("poem_input.txt", "output_task8.txt");

            System.out.println("\n========== ВСЕ ЗАДАНИЯ ВЫПОЛНЕНЫ ==========");

        } catch (TextProcessingException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static void task1RemoveSubstring(String input, String output, String substring)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var result = processor.removeSubstring(lines, substring);
        fileService.writeFile(output, result);
        System.out.println("✓ Задание 1: Удалена подстрока \"" + substring + "\"");
    }

    private static void task2ReplaceSubstring(String input, String output, String oldSub, String newSub)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var result = processor.replaceSubstring(lines, oldSub, newSub);
        fileService.writeFile(output, result);
        System.out.println("✓ Задание 2: Заменено \"" + oldSub + "\" на \"" + newSub + "\"");
    }

    private static void task3FindVowelWords(String input, String output)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var result = processor.findWordsStartingWithVowel(lines);
        fileService.writeFile(output, result);
        System.out.println("✓ Задание 3: Найдены слова на гласную букву");
    }

    private static void task4FindWordChains(String input, String output)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var chains = processor.findWordChains(lines);

        List<String> outputLines = new ArrayList<>();
        for (String[] chain : chains) {
            outputLines.add(chain[0] + " -> " + chain[1]);
        }
        fileService.writeFile(output, outputLines);
        System.out.println("✓ Задание 4: Найдено " + chains.size() + " цепочек слов");
    }

    private static void task5FindMaxDigits(String input, String output)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var maxDigits = processor.findMaxDigitSequence(lines);

        List<String> outputLines = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : maxDigits.entrySet()) {
            outputLines.add("Строка: \"" + entry.getKey() + "\" -> максимум цифр: " + entry.getValue());
        }
        fileService.writeFile(output, outputLines);
        System.out.println("✓ Задание 5: Найдены максимальные последовательности цифр");
    }

    private static void task6CountWordFrequency(String input, String output, List<String> targetWords)
            throws TextProcessingException {
        String text = fileService.readFileAsString(input);
        var frequency = processor.countWordFrequency(text, targetWords);
        var sorted = analyzer.sortByFrequencyAscending(frequency);

        List<String> outputLines = new ArrayList<>();
        outputLines.add("Частота слов (в порядке возрастания):");
        for (FrequencyInfo info : sorted) {
            outputLines.add("  " + info.getWord() + ": " + info.getFrequency());
        }
        fileService.writeFile(output, outputLines);
        System.out.println("✓ Задание 6: Подсчитана частота слов из заданного списка");
    }

    private static void task7CapitalizeFirstLetter(String input, String output)
            throws TextProcessingException {
        var lines = fileService.readFile(input);
        var result = processor.capitalizeFirstLetter(lines);
        fileService.writeFile(output, result);
        System.out.println("✓ Задание 7: Первая буква каждого слова заменена на прописную");
    }

    private static void task8AnalyzeFrequency(String input, String output)
            throws TextProcessingException {
        String text = fileService.readFileAsString(input);
        var letterFreq = analyzer.analyzeLetterFrequency(text);
        var wordFreq = analyzer.analyzeWordFrequency(text);

        List<String> outputLines = new ArrayList<>();
        outputLines.add("=== ЧАСТОТА БУКВ ===");
        for (Map.Entry<Character, Integer> entry : letterFreq.entrySet()) {
            outputLines.add(entry.getKey() + ": " + entry.getValue());
        }

        outputLines.add("\n=== ЧАСТОТА СЛОВ ===");
        for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
            outputLines.add(entry.getKey() + ": " + entry.getValue());
        }

        fileService.writeFile(output, outputLines);
        System.out.println("✓ Задание 8: Проанализирована частота букв и слов");
    }

    private static void createTestFiles() throws TextProcessingException {
        // Тестовый файл для заданий 1-5
        List<String> testLines = Arrays.asList(
                "Привет мир! Это тестовая строка для удаления.",
                "Java программирование 12345 очень интересно 6789.",
                "Апельсин, яблоко, утка, арбуз, индюк.",
                "Кот танцует. Топор работает. Робот танцует.",
                "В строке 12 цифр 345 и 67890 и 1."
        );
        fileService.writeFile("test_input.txt", testLines);

        // Тестовый файл для заданий 6-8
        List<String> poemLines = Arrays.asList(
                "В небе звезды горят ясно,",
                "В сердце песня звучит прекрасно.",
                "В небе звезды, в сердце песня,",
                "Жизнь прекрасна и чудесна.",
                "В небе звезды, в небе звезды,",
                "Сердце бьется, песня льется."
        );
        fileService.writeFile("poem_input.txt", poemLines);

        System.out.println("✓ Тестовые файлы созданы");
    }
}