public class Fraction {
    Complex numerator;   // числитель
    Complex denominator; // знаменатель

    // Конструкторы
    Fraction() {
        this.numerator = new Complex(0, 0);
        this.denominator = new Complex(1, 0);
    }

    Fraction(Complex numerator, Complex denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    // Вывод дроби
    public String toString() {
        return "(" + numerator + ") / (" + denominator + ")";
    }
}
