import config.AppConfig;
import exception.SaladException;
import model.Salad;
import model.vegetable.*;
import service.SaladService;
import storage.SaladConnector;
import java.util.Scanner;

public class Main {
    private static AppConfig config;
    private static SaladConnector connector;
    private static SaladService saladService;
    private static Salad currentSalad;
    private static Scanner scanner;

    public static void main(String[] args) {
        initialize();
        runMenu();
    }

    private static void initialize() {
        config = new AppConfig();
        connector = new SaladConnector(config.getStorageDirectory());
        saladService = new SaladService();
        scanner = new Scanner(System.in);

        System.out.println("=== " + config.getAppName() + " ===");
        System.out.println("Директория хранения: " + config.getStorageDirectory());
        System.out.println();
    }

    private static void runMenu() {
        boolean exit = false;

        while (!exit) {
            printMenu();
            int choice = readInt("Выберите действие: ");

            try {
                switch (choice) {
                    case 1:
                        createNewSalad();
                        break;
                    case 2:
                        loadSalad();
                        break;
                    case 3:
                        saveCurrentSalad();
                        break;
                    case 4:
                        addVegetableToSalad();
                        break;
                    case 5:
                        viewCurrentSalad();
                        break;
                    case 6:
                        sortVegetables();
                        break;
                    case 7:
                        filterByCalories();
                        break;
                    case 8:
                        listAllSalads();
                        break;
                    case 9:
                        deleteSalad();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("До свидания!");
                        break;
                    default:
                        System.out.println("Неверный выбор!");
                }
            } catch (SaladException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Создать новый салат");
        System.out.println("2. Загрузить салат");
        System.out.println("3. Сохранить текущий салат");
        System.out.println("4. Добавить овощ в салат");
        System.out.println("5. Просмотреть текущий салат");
        System.out.println("6. Сортировать овощи");
        System.out.println("7. Найти овощи по диапазону калорийности");
        System.out.println("8. Список всех салатов");
        System.out.println("9. Удалить салат");
        System.out.println("0. Выход");
    }

    private static void createNewSalad() throws SaladException {
        System.out.print("Введите название салата: ");
        String name = scanner.nextLine();
        currentSalad = new Salad(name);
        System.out.println("Создан новый салат: " + name);
    }

    private static void loadSalad() throws SaladException {
        String[] salads = connector.getAllSaladNames();
        if (salads.length == 0) {
            System.out.println("Нет сохраненных салатов");
            return;
        }

        System.out.println("Доступные салаты:");
        for (int i = 0; i < salads.length; i++) {
            System.out.println((i + 1) + ". " + salads[i]);
        }

        int choice = readInt("Выберите салат: ", 1, salads.length);
        currentSalad = connector.loadSalad(salads[choice - 1]);
        System.out.println("Загружен салат: " + currentSalad.getName());
        saladService.printSaladInfo(currentSalad);
    }

    private static void saveCurrentSalad() throws SaladException {
        if (currentSalad == null) {
            System.out.println("Нет текущего салата. Сначала создайте или загрузите салат.");
            return;
        }

        connector.saveSalad(currentSalad, currentSalad.getName());
        System.out.println("Салат '" + currentSalad.getName() + "' сохранен");
    }

    private static void addVegetableToSalad() {
        if (currentSalad == null) {
            System.out.println("Нет текущего салата. Сначала создайте или загрузите салат.");
            return;
        }

        System.out.println("\nВыберите овощ:");
        System.out.println("1. Морковь (41 ккал/100г)");
        System.out.println("2. Картофель (77 ккал/100г)");
        System.out.println("3. Капуста (25 ккал/100г)");
        System.out.println("4. Салат листовой (15 ккал/100г)");
        System.out.println("5. Помидор (18 ккал/100г)");
        System.out.println("6. Огурец (15 ккал/100г)");

        int choice = readInt("Ваш выбор: ", 1, 6);
        double weight = readDouble("Введите вес в граммах: ");

        Vegetable vegetable;
        switch (choice) {
            case 1:
                vegetable = new Carrot();
                break;
            case 2:
                vegetable = new Potato();
                break;
            case 3:
                vegetable = new Cabbage();
                break;
            case 4:
                vegetable = new Lettuce();
                break;
            case 5:
                vegetable = new Tomato();
                break;
            case 6:
                vegetable = new Cucumber();
                break;
            default:
                return;
        }

        saladService.addVegetable(currentSalad, vegetable, weight);
        System.out.println("Добавлен: " + vegetable.getName() + " (" + weight + "г)");
    }

    private static void viewCurrentSalad() {
        if (currentSalad == null) {
            System.out.println("Нет текущего салата. Сначала создайте или загрузите салат.");
            return;
        }

        saladService.printSaladInfo(currentSalad);
    }

    private static void sortVegetables() {
        if (currentSalad == null || currentSalad.getIngredients().isEmpty()) {
            System.out.println("Салат пуст или не загружен");
            return;
        }

        System.out.println("\nСортировать по:");
        System.out.println("1. Калорийности (по возрастанию)");
        System.out.println("2. Калорийности (по убыванию)");
        System.out.println("3. Названию (по возрастанию)");
        System.out.println("4. Названию (по убыванию)");
        System.out.println("5. Весу (по возрастанию)");
        System.out.println("6. Весу (по убыванию)");

        int choice = readInt("Выберите: ", 1, 6);

        switch (choice) {
            case 1:
                saladService.sortByCalories(currentSalad, true);
                break;
            case 2:
                saladService.sortByCalories(currentSalad, false);
                break;
            case 3:
                saladService.sortByName(currentSalad, true);
                break;
            case 4:
                saladService.sortByName(currentSalad, false);
                break;
            case 5:
                saladService.sortByWeight(currentSalad, true);
                break;
            case 6:
                saladService.sortByWeight(currentSalad, false);
                break;
        }

        System.out.println("Сортировка выполнена!");
        viewCurrentSalad();
    }

    private static void filterByCalories() {
        if (currentSalad == null || currentSalad.getIngredients().isEmpty()) {
            System.out.println("Салат пуст или не загружен");
            return;
        }

        double min = readDouble("Введите минимальную калорийность: ");
        double max = readDouble("Введите максимальную калорийность: ");

        var found = saladService.findVegetablesByCalorieRange(currentSalad, min, max);

        System.out.println("\nОвощи в диапазоне [" + min + ", " + max + "] ккал:");
        if (found.isEmpty()) {
            System.out.println("Не найдено");
        } else {
            found.forEach(ing -> System.out.println("  - " + ing));
        }
    }

    private static void listAllSalads() throws SaladException {
        String[] salads = connector.getAllSaladNames();

        if (salads.length == 0) {
            System.out.println("Нет сохраненных салатов");
        } else {
            System.out.println("\nСохраненные салаты:");
            for (String salad : salads) {
                System.out.println("  - " + salad);
            }
        }
    }

    private static void deleteSalad() throws SaladException {
        String[] salads = connector.getAllSaladNames();
        if (salads.length == 0) {
            System.out.println("Нет салатов для удаления");
            return;
        }

        System.out.println("Выберите салат для удаления:");
        for (int i = 0; i < salads.length; i++) {
            System.out.println((i + 1) + ". " + salads[i]);
        }

        int choice = readInt("Выберите: ", 1, salads.length);

        if (connector.deleteSalad(salads[choice - 1])) {
            System.out.println("Салат удален");
            if (currentSalad != null && currentSalad.getName().equals(salads[choice - 1])) {
                currentSalad = null;
            }
        } else {
            System.out.println("Не удалось удалить салат");
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    private static int readInt(String prompt, int min, int max) {
        int value;
        do {
            value = readInt(prompt);
            if (value < min || value > max) {
                System.out.println("Введите число от " + min + " до " + max);
            }
        } while (value < min || value > max);
        return value;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        double result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }
}