package com.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Создание массива абитуриентов
        Abiturient[] abiturientsArray = {
                new Abiturient(1, "Иванов", "Иван", "Иванович",
                        "ул. Ленина, 1", "+7-900-111-2233", new int[]{5, 4, 5, 4, 5}),

                new Abiturient(2, "Петров", "Петр", "Петрович",
                        "ул. Гагарина, 2", "+7-900-222-3344", new int[]{2, 3, 4, 3, 2}),

                new Abiturient(3, "Сидорова", "Анна", "Ивановна",
                        "ул. Пушкина, 3", "+7-900-333-4455", new int[]{5, 5, 5, 4, 5}),

                new Abiturient(4, "Козлов", "Дмитрий", "Сергеевич",
                        "ул. Лермонтова, 4", "+7-900-444-5566", new int[]{3, 2, 2, 3, 2}),

                new Abiturient(5, "Новикова", "Елена", "Александровна",
                        "ул. Тургенева, 5", "+7-900-555-6677", new int[]{4, 4, 5, 5, 4}),

                new Abiturient(6, "Морозов", "Алексей", "Владимирович",
                        "ул. Достоевского, 6", "+7-900-666-7788", new int[]{5, 5, 4, 5, 5}),

                new Abiturient(7, "Волкова", "Мария", "Петровна",
                        "ул. Чехова, 7", "+7-900-777-8899", new int[]{2, 2, 3, 2, 2}),

                new Abiturient(8, "Соколов", "Андрей", "Николаевич",
                        "ул. Гоголя, 8", "+7-900-888-9900", new int[]{4, 5, 4, 4, 5}),

                new Abiturient(9, "Лебедева", "Татьяна", "Дмитриевна",
                        "ул. Есенина, 9", "+7-900-999-0011", new int[]{5, 5, 5, 5, 5}),

                new Abiturient(10, "Павлов", "Константин", "Игоревич",
                        "ул. Маяковского, 10", "+7-900-000-1122", new int[]{3, 4, 3, 4, 3})
        };

        // Создание базы данных
        AbiturientDatabase database = new AbiturientDatabase(abiturientsArray);

        // Вывод полного списка
        database.printAllAbiturients();

        // a) список абитуриентов, имеющих неудовлетворительные оценки
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ЗАДАНИЕ A: АБИТУРИЕНТЫ С НЕУДОВЛЕТВОРИТЕЛЬНЫМИ ОЦЕНКАМИ:");
        System.out.println("=".repeat(80));

        List<Abiturient> unsatisfactory = database.getAbiturientsWithUnsatisfactoryGrades();
        if (unsatisfactory.isEmpty()) {
            System.out.println("Абитуриентов с неудовлетворительными оценками нет.");
        } else {
            for (Abiturient a : unsatisfactory) {
                System.out.printf("• %s (Оценки: %s)%n",
                        a.getFullName(), Arrays.toString(a.getGrades()));
            }
        }

        // b) список абитуриентов, у которых сумма баллов выше заданной
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ЗАДАНИЕ B: АБИТУРИЕНТЫ С СУММОЙ БАЛЛОВ ВЫШЕ 20:");
        System.out.println("=".repeat(80));

        List<Abiturient> above20 = database.getAbiturientsWithSumAbove(20);
        if (above20.isEmpty()) {
            System.out.println("Абитуриентов с суммой баллов выше 20 нет.");
        } else {
            for (Abiturient a : above20) {
                System.out.printf("• %s (Сумма: %d)%n",
                        a.getFullName(), a.getSumGrades());
            }
        }

        // c) выбрать заданное число n абитуриентов, имеющих самую высокую сумму баллов
        int n = 3; // Заданное число n
        database.printTopNAndThreshold(n);

        // Дополнительная демонстрация с другим значением n
        System.out.println("\n\n" + "=".repeat(80));
        System.out.println("ДОПОЛНИТЕЛЬНАЯ ДЕМОНСТРАЦИЯ:");
        System.out.println("=".repeat(80));

        int n2 = 5;
        database.printTopNAndThreshold(n2);

        // Статистика
        database.printStatistics();

        // Пример использования различных конструкторов
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ДЕМОНСТРАЦИЯ РАЗЛИЧНЫХ КОНСТРУКТОРОВ:");
        System.out.println("=".repeat(80));

        // Конструктор 2: без отчества и телефона
        Abiturient student1 = new Abiturient(11, "Смирнов", "Олег",
                "ул. Новая, 1", new int[]{4, 4, 4, 4});
        System.out.println("Конструктор 2 (без отчества и телефона):");
        System.out.println("  " + student1);

        // Конструктор 3: только обязательные поля
        Abiturient student2 = new Abiturient(12, "Кузнецова", "Ольга",
                new int[]{5, 5, 5, 5});
        System.out.println("\nКонструктор 3 (только обязательные поля):");
        System.out.println("  " + student2);

        // Конструктор 4: копирования
        Abiturient student3 = new Abiturient(student2);
        student3.setId(13);
        student3.setLastName("Васильева");
        System.out.println("\nКонструктор 4 (копирование с изменениями):");
        System.out.println("  Оригинал: " + student2.getFullName());
        System.out.println("  Копия: " + student3.getFullName());

        // Демонстрация работы с динамическим добавлением
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ДИНАМИЧЕСКОЕ ДОБАВЛЕНИЕ НОВЫХ АБИТУРИЕНТОВ:");
        System.out.println("=".repeat(80));

        AbiturientDatabase dynamicDB = new AbiturientDatabase();
        dynamicDB.addAbiturient(101, "Михайлов", "Михаил", "Михайлович",
                "ул. Центральная, 1", "+7-901-123-4567", new int[]{4, 5, 4, 5});
        dynamicDB.addAbiturient(102, "Алексеева", "Анна", "Алексеевна",
                "ул. Школьная, 2", "+7-901-234-5678", new int[]{5, 5, 5, 5});
        dynamicDB.addAbiturient(new Abiturient(103, "Дмитриев", "Дмитрий",
                new int[]{3, 4, 3, 4}));

        dynamicDB.printAllAbiturients();

        // Пример интерактивного режима (раскомментировать для использования)
        // interactiveMode();
    }

    // Интерактивный режим для пользовательского ввода
    private static void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        AbiturientDatabase database = new AbiturientDatabase();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("ИНТЕРАКТИВНЫЙ РЕЖИМ");
        System.out.println("=".repeat(80));

        // Ввод данных абитуриентов
        System.out.print("Сколько абитуриентов хотите добавить? ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Абитуриент " + (i + 1) + " ---");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Фамилия: ");
            String lastName = scanner.nextLine();
            System.out.print("Имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Отчество: ");
            String patronymic = scanner.nextLine();
            System.out.print("Адрес: ");
            String address = scanner.nextLine();
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Количество оценок: ");
            int gradesCount = scanner.nextInt();
            int[] grades = new int[gradesCount];
            System.out.print("Оценки (через пробел): ");
            for (int j = 0; j < gradesCount; j++) {
                grades[j] = scanner.nextInt();
            }

            database.addAbiturient(new Abiturient(id, lastName, firstName, patronymic,
                    address, phone, grades));
        }

        // Вывод результатов
        database.printAllAbiturients();

        System.out.print("\nВведите сумму баллов для поиска абитуриентов выше этой суммы: ");
        int sum = scanner.nextInt();
        List<Abiturient> aboveSum = database.getAbiturientsWithSumAbove(sum);
        System.out.println("\nАбитуриенты с суммой баллов выше " + sum + ":");
        for (Abiturient a : aboveSum) {
            System.out.println("  " + a.getFullName() + " (Сумма: " + a.getSumGrades() + ")");
        }

        System.out.print("\nВведите n для поиска топ-n абитуриентов: ");
        int n = scanner.nextInt();
        database.printTopNAndThreshold(n);

        scanner.close();
    }
}