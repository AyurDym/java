package com.epam.salad;

import com.epam.salad.model.Vegetable;
import com.epam.salad.service.SaladService;
import com.epam.salad.util.FileUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SaladService saladService = new SaladService();
        Scanner scanner = new Scanner(System.in);

        // Загрузка овощей из файла
        List<Vegetable> availableVegetables = FileUtil.loadVegetablesFromFile("resources/vegetables.txt");

        if (availableVegetables.isEmpty()) {
            System.out.println("Не удалось загрузить овощи из файла. Используются овощи по умолчанию.");
            saladService.addDefaultVegetables();
        } else {
            for (Vegetable vegetable : availableVegetables) {
                saladService.addVegetableToSalad(vegetable);
            }
        }

        System.out.println("=== Шеф-повар. Приготовление салата ===\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showSaladIngredients(saladService);
                    break;
                case 2:
                    calculateTotalCalories(saladService);
                    break;
                case 3:
                    sortVegetables(saladService, scanner);
                    break;
                case 4:
                    findByCalorieRange(saladService, scanner);
                    break;
                case 5:
                    addVegetable(saladService, scanner);
                    break;
                case 0:
                    System.out.println("До свидания!");
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Показать ингредиенты салата");
        System.out.println("2. Рассчитать общую калорийность салата");
        System.out.println("3. Сортировать овощи");
        System.out.println("4. Найти овощи по диапазону калорийности");
        System.out.println("5. Добавить овощ в салат");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void showSaladIngredients(SaladService saladService) {
        System.out.println("\n=== Ингредиенты салата ===");
        saladService.displaySalad();
    }

    private static void calculateTotalCalories(SaladService saladService) {
        double totalCalories = saladService.calculateTotalCalories();
        System.out.printf("\nОбщая калорийность салата: %.2f ккал\n", totalCalories);
    }

    private static void sortVegetables(SaladService saladService, Scanner scanner) {
        System.out.println("\nСортировка по:");
        System.out.println("1. Калорийности");
        System.out.println("2. Весу");
        System.out.println("3. Названию");
        System.out.print("Выберите параметр: ");
        int sortBy = scanner.nextInt();
        scanner.nextLine();

        switch (sortBy) {
            case 1:
                saladService.sortByCalories();
                System.out.println("Салат отсортирован по калорийности");
                break;
            case 2:
                saladService.sortByWeight();
                System.out.println("Салат отсортирован по весу");
                break;
            case 3:
                saladService.sortByName();
                System.out.println("Салат отсортирован по названию");
                break;
            default:
                System.out.println("Неверный выбор");
                return;
        }
        saladService.displaySalad();
    }

    private static void findByCalorieRange(SaladService saladService, Scanner scanner) {
        System.out.print("\nВведите минимальную калорийность (ккал/100г): ");
        double minCalories = scanner.nextDouble();
        System.out.print("Введите максимальную калорийность (ккал/100г): ");
        double maxCalories = scanner.nextDouble();
        scanner.nextLine();

        List<Vegetable> foundVegetables = saladService.findVegetablesByCalorieRange(minCalories, maxCalories);

        if (foundVegetables.isEmpty()) {
            System.out.println("Овощи с калорийностью от " + minCalories + " до " + maxCalories + " ккал не найдены.");
        } else {
            System.out.println("\nНайденные овощи:");
            for (Vegetable vegetable : foundVegetables) {
                System.out.println(vegetable);
            }
        }
    }

    private static void addVegetable(SaladService saladService, Scanner scanner) {
        System.out.println("\nДобавление овоща в салат:");
        System.out.print("Введите название овоща: ");
        String name = scanner.nextLine();
        System.out.print("Введите вес (грамм): ");
        double weight = scanner.nextDouble();
        System.out.print("Введите калорийность (ккал/100г): ");
        double caloriesPer100g = scanner.nextDouble();
        scanner.nextLine();

        saladService.addVegetableToSalad(name, weight, caloriesPer100g);
        System.out.println("Овощ добавлен в салат!");
    }
}