import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод количества полиномов
        System.out.print("Введите количество полиномов (m): ");
        int m = scanner.nextInt();

        // Создаем массив полиномов
        Polynomial[] polynomials = new Polynomial[m];

        // Ввод полиномов
        for (int i = 0; i < m; i++) {
            System.out.println("\nВведите данные для полинома " + (i + 1) + ":");
            System.out.print("Введите степень полинома: ");
            int degree = scanner.nextInt();

            polynomials[i] = new Polynomial(degree);

            System.out.println("Введите коэффициенты (от свободного члена до старшего):");
            for (int j = 0; j <= degree; j++) {
                System.out.print("a" + j + " = ");
                double coef = scanner.nextDouble();
                polynomials[i].setCoefficient(j, coef);
            }

            System.out.println("Полином " + (i + 1) + ": " + polynomials[i]);
        }

        // Вычисление суммы полиномов
        Polynomial sum = new Polynomial(new double[]{0}); // начинаем с нулевого полинома

        System.out.println("\n=== Суммирование полиномов ===");
        for (int i = 0; i < m; i++) {
            System.out.println("Добавляем полином " + (i + 1) + ": " + polynomials[i]);
            sum = sum.add(polynomials[i]);
            System.out.println("Текущая сумма: " + sum);
            System.out.println();
        }

        System.out.println("=== Результат ===");
        System.out.println("Сумма всех " + m + " полиномов:");
        System.out.println(sum);

        // Дополнительно: вычисление значения суммы в точке
        System.out.print("\nХотите вычислить значение суммы в точке? (y/n): ");
        String answer = scanner.next();

        if (answer.equalsIgnoreCase("y")) {
            System.out.print("Введите значение x: ");
            double x = scanner.nextDouble();
            double result = sum.evaluate(x);
            System.out.println("Значение полинома в точке x = " + x + ": " + result);
        }

        scanner.close();
    }
}
