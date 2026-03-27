import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод количества дробей
        System.out.print("Введите количество дробей (k): ");
        int k = scanner.nextInt();

        // Создаем массив дробей
        Fraction[] fractions = new Fraction[k];

        // Ввод значений для массива дробей
        System.out.println("\n=== Ввод дробей ===");
        for (int i = 0; i < k; i++) {
            System.out.println("\nДробь " + (i + 1) + ":");

            System.out.println("Введите числитель (комплексное число):");
            System.out.print("Действительная часть: ");
            double numReal = scanner.nextDouble();
            System.out.print("Мнимая часть: ");
            double numImag = scanner.nextDouble();
            Complex numerator = new Complex(numReal, numImag);

            System.out.println("Введите знаменатель (комплексное число):");
            System.out.print("Действительная часть: ");
            double denReal = scanner.nextDouble();
            System.out.print("Мнимая часть: ");
            double denImag = scanner.nextDouble();
            Complex denominator = new Complex(denReal, denImag);

            fractions[i] = new Fraction(numerator, denominator);
        }

        // Вывод исходного массива
        System.out.println("\n=== Исходный массив дробей ===");
        printArray(fractions);

        // Изменяем массив: для каждого элемента с четным индексом
        // добавляем следующий за ним элемент
        modifyArray(fractions);

        // Вывод измененного массива
        System.out.println("\n=== Измененный массив дробей ===");
        System.out.println("(Каждый элемент с четным индексом сложен со следующим)");
        printArray(fractions);

        scanner.close();
    }

    // Метод, который изменяет каждый элемент массива с четным индексом
    // путем добавления следующего за ним элемента
    static void modifyArray(Fraction[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // Если индекс четный и есть следующий элемент
            if (i % 2 == 0 && i + 1 < arr.length) {
                // Создаем новую дробь: текущая + следующая
                Fraction sum = addFractions(arr[i], arr[i + 1]);
                arr[i] = sum;
            }
        }
    }

    // Сложение двух дробей с комплексными числами
    static Fraction addFractions(Fraction f1, Fraction f2) {
        // f1 = a/b, f2 = c/d
        // Сумма = (a*d + c*b) / (b*d)

        Complex a = f1.numerator;
        Complex b = f1.denominator;
        Complex c = f2.numerator;
        Complex d = f2.denominator;

        // Вычисляем числитель: a*d + c*b
        Complex ad = multiplyComplex(a, d);
        Complex cb = multiplyComplex(c, b);
        Complex newNumerator = addComplex(ad, cb);

        // Вычисляем знаменатель: b*d
        Complex newDenominator = multiplyComplex(b, d);

        return new Fraction(newNumerator, newDenominator);
    }

    // Сложение комплексных чисел
    static Complex addComplex(Complex c1, Complex c2) {
        return new Complex(c1.real + c2.real, c1.imag + c2.imag);
    }

    // Умножение комплексных чисел
    static Complex multiplyComplex(Complex c1, Complex c2) {
        double real = c1.real * c2.real - c1.imag * c2.imag;
        double imag = c1.real * c2.imag + c1.imag * c2.real;
        return new Complex(real, imag);
    }

    // Вывод массива дробей
    static void printArray(Fraction[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("[" + i + "] " + arr[i]);
        }
    }
}
