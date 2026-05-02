import exception.PoemException;
import model.Author;
import model.Poem;
import model.Line;
import service.PoemService;
import service.LineSorter;

import java.io.File;
import java.util.*;

public class Main {

    private static final String POEMS_FILE = "data/poems.txt";
    private static final String OUTPUT_FILE = "data/sorted_poems.txt";

    public static void main(String[] args) {
        PoemService poemService = new PoemService();
        LineSorter lineSorter = new LineSorter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== СТИХОТВОРЕНИЯ ОДНОГО АВТОРА ===\n");

        try {
            // Проверяем наличие файла со стихами
            File poemsFile = new File(POEMS_FILE);
            Author author;

            if (!poemsFile.exists()) {
                System.out.println("Файл со стихами не найден. Создаем пример стихотворений...");
                createExamplePoemsFile();
            }

            // Загружаем стихотворения
            author = poemService.loadPoemsFromFile(POEMS_FILE);
            System.out.println("Загружены стихотворения автора: " + author.getName());
            System.out.println("Количество стихотворений: " + author.getPoems().size());

            // Показываем меню
            boolean exit = false;
            while (!exit) {
                printMenu();
                int choice = readInt(scanner, "Выберите действие: ");

                switch (choice) {
                    case 1:
                        displayAllPoems(author);
                        break;
                    case 2:
                        sortAndDisplayPoemLines(author, scanner, lineSorter);
                        break;
                    case 3:
                        displayAllPoemsSorted(author);
                        break;
                    case 4:
                        addNewPoem(author, poemService, scanner);
                        break;
                    case 5:
                        comparePoems(author, scanner, lineSorter);
                        break;
                    case 6:
                        savePoems(author, poemService);
                        break;
                    case 0:
                        exit = true;
                        System.out.println("До свидания!");
                        break;
                    default:
                        System.out.println("Неверный выбор!");
                }
            }

        } catch (PoemException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Показать все стихотворения");
        System.out.println("2. Сортировать строки стихотворения по возрастанию длины");
        System.out.println("3. Показать все строки всех стихотворений (сортировка по длине)");
        System.out.println("4. Добавить новое стихотворение");
        System.out.println("5. Сравнить два стихотворения");
        System.out.println("6. Сохранить стихотворения в файл");
        System.out.println("0. Выход");
    }

    private static void displayAllPoems(Author author) {
        System.out.println("\n=== ВСЕ СТИХОТВОРЕНИЯ ===");
        for (Poem poem : author.getPoems()) {
            System.out.println(poem);
        }
    }

    private static void sortAndDisplayPoemLines(Author author, Scanner scanner, LineSorter lineSorter) {
        if (author.getPoems().isEmpty()) {
            System.out.println("Нет стихотворений для отображения");
            return;
        }

        System.out.println("\nВыберите стихотворение:");
        for (int i = 0; i < author.getPoems().size(); i++) {
            System.out.println((i + 1) + ". " + author.getPoems().get(i).getTitle());
        }

        int choice = readInt(scanner, "Ваш выбор: ", 1, author.getPoems().size());
        Poem selectedPoem = author.getPoems().get(choice - 1);

        System.out.println("\n=== СОРТИРОВКА СТРОК ПО ВОЗРАСТАНИЮ ДЛИНЫ ===");
        System.out.println("Стихотворение: " + selectedPoem.getTitle());
        System.out.println("Автор: " + selectedPoem.getAuthor());
        System.out.println("\nИсходные строки:");
        for (Line line : selectedPoem.getLines()) {
            System.out.println("  " + line.getText());
        }

        lineSorter.printSortedLines(selectedPoem, true);
    }

    private static void displayAllPoemsSorted(Author author) {
        System.out.println("\n=== ВСЕ СТРОКИ ВСЕХ СТИХОТВОРЕНИЙ (СОРТИРОВКА ПО ДЛИНЕ) ===");

        List<Line> allLines = new ArrayList<>();
        for (Poem poem : author.getPoems()) {
            for (Line line : poem.getLines()) {
                allLines.add(line);
            }
        }

        allLines.sort(Comparator.comparingInt(Line::getLength));

        System.out.println("\nВсе строки (от самой короткой к самой длинной):");
        for (Line line : allLines) {
            System.out.printf("%-3d | %s\n", line.getLength(), line.getText());
        }
    }

    private static void addNewPoem(Author author, PoemService poemService, Scanner scanner) {
        System.out.println("\n=== ДОБАВЛЕНИЕ НОВОГО СТИХОТВОРЕНИЯ ===");

        System.out.print("Введите название стихотворения: ");
        String title = scanner.nextLine();

        System.out.println("Введите строки стихотворения (для завершения введите пустую строку):");
        List<String> lines = new ArrayList<>();

        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty() && lines.isEmpty()) {
                continue;
            }
            if (line.isEmpty()) {
                break;
            }
            lines.add(line);
        }

        Poem newPoem = poemService.createPoem(title, author.getName(), lines);
        author.addPoem(newPoem);

        System.out.println("Стихотворение \"" + title + "\" успешно добавлено!");
        System.out.println("Количество строк: " + lines.size());
    }

    private static void comparePoems(Author author, Scanner scanner, LineSorter lineSorter) {
        if (author.getPoems().size() < 2) {
            System.out.println("Недостаточно стихотворений для сравнения (нужно минимум 2)");
            return;
        }

        System.out.println("\n=== СРАВНЕНИЕ ДВУХ СТИХОТВОРЕНИЙ ===");

        System.out.println("Выберите первое стихотворение:");
        for (int i = 0; i < author.getPoems().size(); i++) {
            System.out.println((i + 1) + ". " + author.getPoems().get(i).getTitle());
        }
        int choice1 = readInt(scanner, "Ваш выбор: ", 1, author.getPoems().size());

        System.out.println("Выберите второе стихотворение:");
        int choice2 = readInt(scanner, "Ваш выбор: ", 1, author.getPoems().size());

        Poem poem1 = author.getPoems().get(choice1 - 1);
        Poem poem2 = author.getPoems().get(choice2 - 1);

        lineSorter.comparePoemsLength(poem1, poem2);
    }

    private static void savePoems(Author author, PoemService poemService) {
        try {
            poemService.savePoemsToFile(author, OUTPUT_FILE);
            System.out.println("Стихотворения сохранены в файл: " + OUTPUT_FILE);
        } catch (PoemException e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return result;
    }

    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        int value;
        do {
            value = readInt(scanner, prompt);
            if (value < min || value > max) {
                System.out.println("Введите число от " + min + " до " + max);
            }
        } while (value < min || value > max);
        return value;
    }

    private static void createExamplePoemsFile() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String[] examplePoems = {
                "Автор: Александр Пушкин",
                "",
                "Название: Зимнее утро",
                "Год: 1829",
                "",
                "Мороз и солнце; день чудесный!",
                "Еще ты дремлешь, друг прелестный -",
                "Пора, красавица, проснись:",
                "Открой сомкнуты негой взоры",
                "Навстречу северной Авроры,",
                "Звездою севера явись!",
                "---",
                "",
                "Название: Я помню чудное мгновенье",
                "Год: 1825",
                "",
                "Я помню чудное мгновенье:",
                "Передо мной явилась ты,",
                "Как мимолетное виденье,",
                "Как гений чистой красоты.",
                "---",
                "",
                "Название: Узник",
                "Год: 1822",
                "",
                "Сижу за решеткой в темнице сырой.",
                "Вскормленный в неволе орел молодой,",
                "Мой грустный товарищ, махая крылом,",
                "Кровавую пищу клюет под окном,",
                "---"
        };

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(POEMS_FILE))) {
            for (String line : examplePoems) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Создан пример файла со стихами: " + POEMS_FILE);
        } catch (java.io.IOException e) {
            System.err.println("Ошибка создания примера: " + e.getMessage());
        }
    }
}