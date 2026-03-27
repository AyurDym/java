package com.epam.gradebook;

import com.epam.gradebook.model.GradeBook;
import com.epam.gradebook.model.Student;
import com.epam.gradebook.service.GradeBookService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Зачетная книжка студента ===\n");

        // Создание студента
        System.out.print("Введите имя студента: ");
        String name = scanner.nextLine();
        System.out.print("Введите номер студенческого билета: ");
        String studentId = scanner.nextLine();
        System.out.print("Введите факультет: ");
        String faculty = scanner.nextLine();

        Student student = new Student(name, studentId, faculty);
        GradeBook gradeBook = new GradeBook(student);
        GradeBookService service = new GradeBookService(gradeBook);

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addSession(service, scanner);
                    break;
                case 2:
                    addExamToSession(service, scanner);
                    break;
                case 3:
                    addCreditToSession(service, scanner);
                    break;
                case 4:
                    showAllSessions(service);
                    break;
                case 5:
                    showCurrentSession(service);
                    break;
                case 6:
                    showAverageScore(service);
                    break;
                case 7:
                    showDebts(service);
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

    private static void printMainMenu() {
        System.out.println("\n--- Меню зачетной книжки ---");
        System.out.println("1. Добавить новую сессию");
        System.out.println("2. Добавить экзамен в текущую сессию");
        System.out.println("3. Добавить зачет в текущую сессию");
        System.out.println("4. Показать все сессии");
        System.out.println("5. Показать текущую сессию");
        System.out.println("6. Показать средний балл");
        System.out.println("7. Показать задолженности");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void addSession(GradeBookService service, Scanner scanner) {
        System.out.print("\nВведите номер семестра: ");
        int semester = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите учебный год (например, 2023-2024): ");
        String academicYear = scanner.nextLine();

        service.addNewSession(semester, academicYear);
        System.out.println("Сессия успешно создана!");
    }

    private static void addExamToSession(GradeBookService service, Scanner scanner) {
        System.out.print("\nВведите название предмета: ");
        String subject = scanner.nextLine();
        System.out.print("Введите оценку (2-5): ");
        int score = scanner.nextInt();
        scanner.nextLine();

        if (service.addExamToCurrentSession(subject, score)) {
            System.out.println("Экзамен добавлен!");
        } else {
            System.out.println("Сначала создайте сессию!");
        }
    }

    private static void addCreditToSession(GradeBookService service, Scanner scanner) {
        System.out.print("\nВведите название предмета: ");
        String subject = scanner.nextLine();
        System.out.print("Статус зачета (зачет/незачет): ");
        String status = scanner.nextLine();

        if (service.addCreditToCurrentSession(subject, status)) {
            System.out.println("Зачет добавлен!");
        } else {
            System.out.println("Сначала создайте сессию!");
        }
    }

    private static void showAllSessions(GradeBookService service) {
        System.out.println("\n=== Все сессии ===");
        service.displayAllSessions();
    }

    private static void showCurrentSession(GradeBookService service) {
        System.out.println("\n=== Текущая сессия ===");
        service.displayCurrentSession();
    }

    private static void showAverageScore(GradeBookService service) {
        double average = service.calculateAverageScore();
        System.out.printf("\nСредний балл по всем экзаменам: %.2f\n", average);
    }

    private static void showDebts(GradeBookService service) {
        System.out.println("\n=== Задолженности ===");
        service.displayDebts();
    }
}