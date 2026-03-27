package com.epam.greenhouse;

import com.epam.greenhouse.model.*;
import com.epam.greenhouse.service.GreenhouseService;

import java.util.Scanner;

public class Main {
    private static GreenhouseService service;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        Greenhouse greenhouse = new Greenhouse("Тропическая оранжерея", 25.0, 70);
        service = new GreenhouseService(greenhouse);

        // Добавляем несколько растений для демонстрации
        addDemoPlants();

        System.out.println("=== Система управления оранжереей ===\n");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showAllPlants();
                    break;
                case 2:
                    showPlantsByType();
                    break;
                case 3:
                    showPlantsByOrigin();
                    break;
                case 4:
                    addNewPlant();
                    break;
                case 5:
                    editPlant();
                    break;
                case 6:
                    waterPlants();
                    break;
                case 7:
                    setTemperature();
                    break;
                case 8:
                    setLighting();
                    break;
                case 9:
                    removePlant();
                    break;
                case 10:
                    showGreenhouseInfo();
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

    private static void addDemoPlants() {
        service.addPlant(new BushPlant("Роза", "Европа", 2, 1.5, true, true));
        service.addPlant(new BushPlant("Сирень", "Азия", 3, 2.0, true, false));
        service.addPlant(new FloweringPlant("Тюльпан", "Нидерланды", 0.3, "красный", "весна"));
        service.addPlant(new FloweringPlant("Орхидея", "Юго-Восточная Азия", 0.5, "фиолетовый", "круглый год"));
        service.addPlant(new IndoorPlant("Фикус", "Африка", 1.2, true, "умеренный", 25.0));
        service.addPlant(new IndoorPlant("Сансевиерия", "Африка", 0.8, false, "низкий", 22.0));
        service.addPlant(new BushPlant("Смородина", "Европа", 1.5, 1.2, false, true));
    }

    private static void printMainMenu() {
        System.out.println("\n--- Меню управления оранжереей ---");
        System.out.println("1. Показать все растения");
        System.out.println("2. Показать растения по виду");
        System.out.println("3. Показать растения по месту происхождения");
        System.out.println("4. Закупить новое растение");
        System.out.println("5. Редактировать информацию о растении");
        System.out.println("6. Произвести полив");
        System.out.println("7. Задать температуру в оранжерее");
        System.out.println("8. Установить освещение");
        System.out.println("9. Выкопать/удалить растение");
        System.out.println("10. Информация об оранжерее");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void showAllPlants() {
        System.out.println("\n=== Все растения в оранжерее ===");
        service.displayAllPlants();
    }

    private static void showPlantsByType() {
        System.out.println("\nВыберите тип растения:");
        System.out.println("1. Кустовое");
        System.out.println("2. Цветковое");
        System.out.println("3. Комнатное");
        System.out.print("Ваш выбор: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        String plantType;
        switch (type) {
            case 1:
                plantType = "Кустовое";
                break;
            case 2:
                plantType = "Цветковое";
                break;
            case 3:
                plantType = "Комнатное";
                break;
            default:
                System.out.println("Неверный выбор");
                return;
        }

        System.out.println("\n=== Растения типа \"" + plantType + "\" ===");
        service.displayPlantsByType(plantType);
    }

    private static void showPlantsByOrigin() {
        System.out.print("\nВведите место происхождения: ");
        String origin = scanner.nextLine();
        System.out.println("\n=== Растения из \"" + origin + "\" ===");
        service.displayPlantsByOrigin(origin);
    }

    private static void addNewPlant() {
        System.out.println("\n=== Закупка нового растения ===");
        System.out.println("Выберите тип растения:");
        System.out.println("1. Кустовое");
        System.out.println("2. Цветковое");
        System.out.println("3. Комнатное");
        System.out.print("Ваш выбор: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Название растения: ");
        String name = scanner.nextLine();
        System.out.print("Место происхождения: ");
        String origin = scanner.nextLine();
        System.out.print("Высота (м): ");
        double height = scanner.nextDouble();
        scanner.nextLine();

        Plant plant = null;

        switch (type) {
            case 1:
                System.out.print("Наличие шипов (true/false): ");
                boolean hasThorns = scanner.nextBoolean();
                System.out.print("Плодоносит (true/false): ");
                boolean fruits = scanner.nextBoolean();
                scanner.nextLine();
                plant = new BushPlant(name, origin, height, 0, hasThorns, fruits);
                break;
            case 2:
                System.out.print("Цвет: ");
                String color = scanner.nextLine();
                System.out.print("Период цветения: ");
                String floweringPeriod = scanner.nextLine();
                plant = new FloweringPlant(name, origin, height, color, floweringPeriod);
                break;
            case 3:
                System.out.print("Требует опоры (true/false): ");
                boolean needsSupport = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("Требования к освещению: ");
                String lightRequirement = scanner.nextLine();
                System.out.print("Оптимальная температура (°C): ");
                double optimalTemp = scanner.nextDouble();
                scanner.nextLine();
                plant = new IndoorPlant(name, origin, height, needsSupport, lightRequirement, optimalTemp);
                break;
            default:
                System.out.println("Неверный тип растения");
                return;
        }

        if (service.addPlant(plant)) {
            System.out.println("Растение \"" + name + "\" успешно закуплено и добавлено в оранжерею!");
        } else {
            System.out.println("Растение с таким именем уже существует!");
        }
    }

    private static void editPlant() {
        System.out.print("\nВведите название растения для редактирования: ");
        String name = scanner.nextLine();
        Plant plant = service.findPlantByName(name);

        if (plant == null) {
            System.out.println("Растение не найдено!");
            return;
        }

        System.out.println("Текущая информация: " + plant);
        System.out.println("\nЧто хотите отредактировать?");
        System.out.println("1. Название");
        System.out.println("2. Место происхождения");
        System.out.println("3. Высоту");
        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Новое название: ");
                String newName = scanner.nextLine();
                service.editPlantName(name, newName);
                System.out.println("Название изменено!");
                break;
            case 2:
                System.out.print("Новое место происхождения: ");
                String newOrigin = scanner.nextLine();
                service.editPlantOrigin(name, newOrigin);
                System.out.println("Место происхождения изменено!");
                break;
            case 3:
                System.out.print("Новая высота (м): ");
                double newHeight = scanner.nextDouble();
                scanner.nextLine();
                service.editPlantHeight(name, newHeight);
                System.out.println("Высота изменена!");
                break;
            default:
                System.out.println("Неверный выбор");
        }
    }

    private static void waterPlants() {
        System.out.println("\n=== Полив растений ===");
        service.waterAllPlants();
    }

    private static void setTemperature() {
        System.out.print("\nВведите температуру в оранжерее (°C): ");
        double temperature = scanner.nextDouble();
        scanner.nextLine();
        service.setTemperature(temperature);
        System.out.printf("Температура в оранжерее установлена на %.1f°C\n", temperature);
    }

    private static void setLighting() {
        System.out.print("\nВведите уровень освещения (низкий/умеренный/высокий): ");
        String lighting = scanner.nextLine();
        service.setLighting(lighting);
        System.out.println("Освещение установлено на уровень: " + lighting);
    }

    private static void removePlant() {
        System.out.print("\nВведите название растения для удаления: ");
        String name = scanner.nextLine();

        if (service.removePlant(name)) {
            System.out.println("Растение \"" + name + "\" выкопано и удалено из оранжереи!");
        } else {
            System.out.println("Растение не найдено!");
        }
    }

    private static void showGreenhouseInfo() {
        service.displayGreenhouseInfo();
    }
}
