import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Создаем массив абитуриентов
        Abiturient[] students = new Abiturient[6];

        students[0] = new Abiturient(1, "Иванов", "Иван", "Иванович",
                "ул. Ленина 1", "111-111", new int[]{5, 4, 5, 3, 4});
        students[1] = new Abiturient(2, "Петров", "Петр", "Петрович",
                "ул. Гагарина 2", "222-222", new int[]{2, 3, 4, 3, 3});
        students[2] = new Abiturient(3, "Сидоров", "Сидор", "Сидорович",
                "ул. Пушкина 3", "333-333", new int[]{5, 5, 5, 4, 5});
        students[3] = new Abiturient(4, "Кузнецов", "Алексей", "Алексеевич",
                "ул. Советская 4", "444-444", new int[]{3, 2, 4, 3, 2});
        students[4] = new Abiturient(5, "Смирнова", "Анна", "Сергеевна",
                "ул. Мира 5", "555-555", new int[]{4, 5, 4, 5, 4});
        students[5] = new Abiturient(6, "Попов", "Дмитрий", "Дмитриевич",
                "ул. Садовая 6", "666-666", new int[]{5, 5, 4, 5, 5});

        // a) список абитуриентов, имеющих неудовлетворительные оценки
        System.out.println("\n=== а) Абитуриенты с неудовлетворительными оценками ===");
        boolean found = false;
        for (Abiturient s : students) {
            if (s.hasUnsatisfactory()) {
                s.print();
                found = true;
            }
        }
        if (!found) System.out.println("Нет таких абитуриентов");

        // b) список абитуриентов, у которых сумма баллов выше заданной
        System.out.print("\n=== б) Введите сумму баллов для сравнения: ");
        int minSum = scanner.nextInt();
        System.out.println("Абитуриенты с суммой баллов выше " + minSum + ":");
        found = false;
        for (Abiturient s : students) {
            if (s.getSum() > minSum) {
                s.print();
                found = true;
            }
        }
        if (!found) System.out.println("Нет таких абитуриентов");

        // c) выбрать заданное число n абитуриентов, имеющих самую высокую сумму баллов
        System.out.print("\n=== в) Введите количество лучших абитуриентов (n): ");
        int n = scanner.nextInt();

        // Сортируем по сумме баллов (пузырьковая сортировка)
        Abiturient[] sorted = students.clone();
        for (int i = 0; i < sorted.length - 1; i++) {
            for (int j = 0; j < sorted.length - 1 - i; j++) {
                if (sorted[j].getSum() < sorted[j + 1].getSum()) {
                    Abiturient temp = sorted[j];
                    sorted[j] = sorted[j + 1];
                    sorted[j + 1] = temp;
                }
            }
        }

        // Выводим n лучших
        System.out.println("\nТоп " + n + " лучших абитуриентов:");
        for (int i = 0; i < Math.min(n, sorted.length); i++) {
            System.out.print((i + 1) + ". ");
            sorted[i].print();
        }

        // Выводим всех с полупроходной суммой (сумма >= сумме последнего из топ n)
        if (n <= sorted.length) {
            int thresholdSum = sorted[n - 1].getSum();
            System.out.println("\nАбитуриенты с полупроходной суммой (сумма >= " + thresholdSum + "):");
            for (Abiturient s : students) {
                if (s.getSum() >= thresholdSum) {
                    s.print();
                }
            }
        }

        scanner.close();
    }
}
